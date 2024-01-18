package com.example.stripe.service.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.Address;
import com.example.stripe.entity.Merchant;
import com.example.stripe.entity.Orders;
import com.example.stripe.entity.Product;
import com.example.stripe.entity.User;
import com.example.stripe.enums.OrderStatus;
import com.example.stripe.exception.AddressException;
import com.example.stripe.exception.MerchantException;
import com.example.stripe.exception.ProductException;
import com.example.stripe.exception.UserException;
import com.example.stripe.repository.AddressRepository;
import com.example.stripe.repository.MerchantRepository;
import com.example.stripe.repository.ProductRepository;
import com.example.stripe.repository.UserRepository;
import com.example.stripe.service.dto.request.OrderRequestDto;
import com.example.stripe.service.dto.response.OrderResponseDto;
import static com.example.stripe.constant.Constants.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderMapper {

        private final MerchantRepository merchantRepository;
        private final ProductRepository productRepository;
        private final UserRepository userRepository;
        private final AddressRepository addressRepository;

        private final ProductMapper productMapper;

        public Orders toEntity(OrderRequestDto dto, Long userId) {

                Merchant merchant = merchantRepository.findById(dto.getMerchantId())
                                .orElseThrow(() -> new MerchantException("Merchant is not found"));

                Product product = productRepository.findById(dto.getProductInformation().getProductId())
                                .orElseThrow(() -> new ProductException("Product is not found"));

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new UserException("User is not found"));

                return Orders.builder()
                                .currency(USDC_SHORTNAME)
                                .amount(BigDecimal.valueOf(dto.getProductInformation().getQuantity())
                                                .multiply(product.getPrice()))
                                .product(product)
                                .quantity(dto.getProductInformation().getQuantity())
                                .status(OrderStatus.INITIAL)
                                .expireTime(Long.toString(new Timestamp(System.currentTimeMillis()).getTime() / 1000))
                                .merchant(merchant)
                                .user(user)
                                .build();
        }

        public OrderResponseDto toDto(Orders orders, String qrcodeLink) {
                if (orders.getAddress() != null) {
                        return OrderResponseDto.builder()
                                        .id(orders.getId())
                                        .status(orders.getStatus())
                                        .expireTime(orders.getExpireTime())
                                        .qrcodeLink(qrcodeLink)
                                        .qrContent(orders.getQrContent())
                                        .merchantId(orders.getMerchant().getId())
                                        .productInfo(productMapper.toDto(orders.getProduct()))
                                        .quantity(orders.getQuantity())
                                        .userId(orders.getUser().getId())
                                        .addressId(orders.getAddress().getId())
                                        .build();
                }

                return OrderResponseDto.builder()
                                .id(orders.getId())
                                .status(orders.getStatus())
                                .expireTime(orders.getExpireTime())
                                .qrcodeLink(qrcodeLink)
                                .qrContent(orders.getQrContent())
                                .merchantId(orders.getMerchant().getId())
                                .productInfo(productMapper.toDto(orders.getProduct()))
                                .quantity(orders.getQuantity())
                                .userId(orders.getUser().getId())
                                .build();
        }

        public OrderResponseDto toDtoNoProduct(Orders orders, String qrcodeLink) {
                if (orders.getAddress() != null) {
                        return OrderResponseDto.builder()
                                        .id(orders.getId())
                                        .status(orders.getStatus())
                                        .expireTime(orders.getExpireTime())
                                        .qrcodeLink(qrcodeLink)
                                        .qrContent(orders.getQrContent())
                                        .merchantId(orders.getMerchant().getId())
                                        .userId(orders.getUser().getId())
                                        .addressId(orders.getAddress().getId())
                                        .build();

                }

                return OrderResponseDto.builder()
                                .id(orders.getId())
                                .status(orders.getStatus())
                                .expireTime(orders.getExpireTime())
                                .qrcodeLink(qrcodeLink)
                                .qrContent(orders.getQrContent())
                                .merchantId(orders.getMerchant().getId())
                                .userId(orders.getUser().getId())
                                .build();
        }
}
