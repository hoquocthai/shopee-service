package com.example.stripe.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "merchant")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "busuness_name")
    private String businessName;

    @Column(name = "business_industry")
    private String businessIndustry;

    @Column(name = "mcc_code")
    private String mccCode;

    @Column(name = "business_place")
    private String businessPlace;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "store_type")
    private String storeType;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "store_address")
    private String storeAddress;

    @Column(name = "store_photo")
    private String storePhoto;

    @Column(name = "annual_payment")
    private String annualPayment;

    @Column(name = "monthly_payment")
    private String monthlyPayment;

    @Column(name = "orther_amount")
    private BigDecimal ortherAmount;

    @Column(name = "monthly_txn_number")
    private String monthlyTxnNumber;

    @Column(name = "average_delivery_time")
    private String averageDeliveryTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "merchant")
    private List<Orders> orders;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> product;
}
