package com.example.foodclub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.collect.Sets;
import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"purchases"}, callSuper = false)
@ToString(exclude = {"purchases"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Promo extends AuditModel<Long> {

    private String promoName;

    private LocalDate issueDate;

    private LocalDate expirationDate;

    private String couponCode;

    private String couponStatus;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usermapId")
    private Usermap usermap;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Purchase> purchases;

//    public Promo(String promoName, LocalDate issueDate, LocalDate expirationDate, String couponCode, String couponStatus, Usermap usermap) {
//        this.promoName = promoName;
//        this.issueDate = issueDate;
//        this.expirationDate = expirationDate;
//        this.couponCode = couponCode;
//        this.couponStatus = couponStatus;
//        this.usermap = usermap;
//    }

//    public void addPurchase(Purchase purchase) {
//        if (CollectionUtils.isEmpty(purchases)) {
//            purchases = Sets.newHashSet(purchase);
//            return;
//        }
//        purchases.add(purchase);
//    }
}
