package com.example.stripe.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stripe.entity.Merchant;
import com.example.stripe.entity.Orders;
import com.example.stripe.entity.RechargeHistory;
import com.example.stripe.entity.SessionRecord;
import com.example.stripe.entity.Transaction;
import com.example.stripe.entity.User;
import com.example.stripe.entity.UserSession;
import com.example.stripe.entity.Wallet;
import com.example.stripe.enums.CheckoutStatus;
import com.example.stripe.enums.OrderStatus;
import com.example.stripe.enums.PaySessionMode;
import com.example.stripe.enums.RechargeStatus;
import com.example.stripe.enums.RechargeType;
import com.example.stripe.enums.TransactionCredit;
import com.example.stripe.enums.TransactionType;
import com.example.stripe.exception.BadRequestException;
import com.example.stripe.exception.MerchantException;
import com.example.stripe.exception.OrderException;
import com.example.stripe.exception.StripeException;
import com.example.stripe.exception.UserException;
import com.example.stripe.exception.WalletException;
import com.example.stripe.repository.MerchantRepository;
import com.example.stripe.repository.OrderRepository;
import com.example.stripe.repository.RechargeRepository;
import com.example.stripe.repository.SessionRecordRepository;
import com.example.stripe.repository.TransactionRepository;
import com.example.stripe.repository.UserRepository;
import com.example.stripe.repository.UserSessionRepository;
import com.example.stripe.repository.WalletRepository;
import com.example.stripe.service.PaymentService;
import com.example.stripe.service.dto.LineItemDto;
import com.example.stripe.service.dto.StripeProductDto;
import com.example.stripe.service.dto.request.CheckoutCustomSessionRequestDto;
import com.example.stripe.service.dto.request.CheckoutSessionRequestDto;
import com.example.stripe.service.dto.request.PaymentIntentRequestDto;
import com.example.stripe.service.dto.request.PaymentPaypalRequestDto;
import com.example.stripe.service.dto.response.CheckoutStatusResponse;
import com.example.stripe.service.dto.response.PaySessionResponseDto;
import com.example.stripe.service.dto.response.PaymentIntentResponseDto;
import com.example.stripe.service.dto.response.PaymentStatusInfo;
import com.example.stripe.util.SecurityUtil;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    private final MerchantRepository merchantRepository;
    private final OrderRepository orderRepository;
    private final WalletRepository walletRepository;
    private final UserSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final SessionRecordRepository recordRepository;
    private final RechargeRepository rechargeRepository;

    @Override
    public PaySessionResponseDto createSession(CheckoutSessionRequestDto dto) {

        Stripe.apiKey = API_SECRET_KEY;
        List<Object> lineItems = new ArrayList<>();

        for (StripeProductDto product : dto.getLineItems()) {
            Map<String, Object> lineItem = new HashMap<>();

            lineItem.put("price", product.getPrice());
            lineItem.put("quantity", product.getQuantity());

            lineItems.add(lineItem);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("success_url", dto.getSuccessUrl());
        params.put("cancel_url", dto.getCancelUrl());
        params.put("line_items", lineItems);
        params.put("mode", PaySessionMode.payment);
        try {
            Session session = Session.create(params);
            return PaySessionResponseDto.builder().url(session.getUrl()).build();
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public PaySessionResponseDto createCustomSession(HttpServletRequest httpServletRequest,
            CheckoutCustomSessionRequestDto dto) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken).get();

        Stripe.apiKey = API_SECRET_KEY;
        List<Object> lineItems = new ArrayList<>();

        for (LineItemDto item : dto.getLineItems()) {
            Map<String, Object> lineItem = new HashMap<>();
            Map<String, Object> priceData = new HashMap<>();
            priceData.put("currency", item.getPriceData().getCurrency());
            priceData.put("unit_amount", item.getPriceData().getUnitAmount());

            Map<String, Object> productData = new HashMap<>();
            productData.put("name", item.getPriceData().getProductData().getName());
            if (item.getPriceData().getProductData().getDescription() != null)
                productData.put("description", item.getPriceData().getProductData().getDescription());
            if (item.getPriceData().getProductData().getImages() != null)
                productData.put("images", item.getPriceData().getProductData().getImages());

            priceData.put("product_data", productData);

            lineItem.put("price_data", priceData);
            lineItem.put("quantity", item.getQuantity());

            lineItems.add(lineItem);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("success_url", dto.getSuccessUrl());
        params.put("cancel_url", dto.getCancelUrl());
        params.put("line_items", lineItems);
        params.put("mode", PaySessionMode.payment);
        try {
            Session session = Session.create(params);
            recordRepository.save(SessionRecord.builder()
                    .user(userSession.getUser())
                    .checkoutSessionId(session.getId())
                    .paymentIntentId(session.getPaymentIntent())
                    .build());
            return PaySessionResponseDto.builder().url(session.getUrl()).sessionId(session.getId()).build();
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    public PaymentStatusInfo checkCustomSessionStatus(String dto) {
        Stripe.apiKey = API_SECRET_KEY;
        try {
            Session session = Session.retrieve(dto);
            PaymentStatusInfo statusInfo = PaymentStatusInfo.builder().status(session.getPaymentStatus())
                    .amountTotal(session.getAmountTotal() / 100).build();
            return statusInfo;
        } catch (Exception e) {
            throw new BadRequestException("get session detail fail");
        }
    }

    @Override
    @Transactional
    public CheckoutStatusResponse payment(Long orderId, HttpServletRequest httpServletRequest) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken)
                .orElseThrow(() -> new UserException("User session is not found"));

        User user = userRepository.findById(userSession.getUser().getId())
                .orElseThrow(() -> new UserException("User is not found"));

        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new OrderException("Order is not found"));

        if (orders.getStatus() == OrderStatus.PENDING) {
            Merchant merchant = merchantRepository.findById(orders.getMerchant().getId()).orElseThrow(() -> {
                orders.setStatus(OrderStatus.ERROR);
                return new MerchantException("Merchant is not found");
            });

            if (orders.getAddress() == null)
                throw new OrderException("Address is not found");

            Wallet merchantWallet = walletRepository.findByUserId(merchant.getUser().getId()).orElseThrow(() -> {
                orders.setStatus(OrderStatus.ERROR);
                return new WalletException("Merchant wallet is not found");
            });

            Wallet userWallet = walletRepository.findByUserId(user.getId()).orElseThrow(() -> {
                orders.setStatus(OrderStatus.PENDING);
                return new WalletException("User wallet is not found");
            });

            if (merchantWallet.getCoin().getName() != userWallet.getCoin().getName()) {
                orders.setStatus(OrderStatus.ERROR);
                throw new WalletException("Currencies are not the same");
            }

            if (userWallet.getBalance().compareTo(orders.getAmount()) < 0) {
                orders.setStatus(OrderStatus.PENDING);
                throw new WalletException("User balance is not enough");
            }

            Transaction merchantTxn = Transaction.builder()
                    .wallet(merchantWallet)
                    .orders(orders)
                    .type(TransactionType.PAY)
                    .credit(TransactionCredit.YES)
                    .amount(orders.getAmount())
                    .build();

            Transaction userTxn = Transaction.builder()
                    .wallet(userWallet)
                    .orders(orders)
                    .type(TransactionType.PAY)
                    .credit(TransactionCredit.NO)
                    .amount(orders.getAmount().multiply(BigDecimal.valueOf(-1)))
                    .build();

            merchantWallet.setBalance(merchantWallet.getBalance().add(merchantTxn.getAmount()));
            userWallet.setBalance(userWallet.getBalance().add(userTxn.getAmount()));

            transactionRepository.save(merchantTxn);
            transactionRepository.save(userTxn);
            orders.setStatus(OrderStatus.PAID);

            return CheckoutStatusResponse.builder().status(CheckoutStatus.SUCCESS).build();
        } else {
            throw new OrderException("Order is not available for payment");
        }
    }

    @Override
    @Transactional
    public PaymentIntentResponseDto createPaymentIntent(HttpServletRequest httpServletRequest,
            PaymentIntentRequestDto dto) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken).get();

        Stripe.apiKey = API_SECRET_KEY;
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(dto.getAmount() * 100)
                .setCurrency("usd")
                .build();
        try {
            PaymentIntent intent = PaymentIntent.create(params);
            String clientSecret = intent.getClientSecret();
            recordRepository.save(SessionRecord.builder()
                    .user(userSession.getUser())
                    .paymentIntentId(intent.getId())
                    .build());
            return PaymentIntentResponseDto.builder().clientSecret(clientSecret).build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean createPaymentPaypal(PaymentPaypalRequestDto dto) {
        Long userId = SecurityUtil.getCurrentUserLogin();

        // Update wallet with userId and currency
        Wallet userWallet = walletRepository.findByUserIdAndCoinShortName(userId, dto.getCurrency())
                .orElseThrow(() -> new BadRequestException("Wallet not found"));
        userWallet.setBalance(userWallet.getBalance().add(dto.getAmount()));
        walletRepository.save(userWallet);

        // Save recharge history
        RechargeHistory history = new RechargeHistory();
        history.setAmount(dto.getAmount());
        history.setCreatedDate(dto.getCreatedDate());
        history.setCurrency(dto.getCurrency());
        history.setUser(userWallet.getUser());
        history.setRechargeType(RechargeType.PAYPAL);
        if (!RechargeStatus.COMPLETED.equals(RechargeStatus.valueOf(dto.getStatus()))) {
            return false;
        }
        history.setRechargeStatus(RechargeStatus.COMPLETED);
        rechargeRepository.save(history);
        return true;
    }
}
