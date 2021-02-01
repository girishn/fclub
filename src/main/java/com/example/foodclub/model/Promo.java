package com.example.foodclub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"purchases"}, callSuper = false)
@ToString(exclude = {"purchases"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "promo")
public class Promo extends AuditModel<Long> {

    @Column(name = "palsId")
    private String palsId;

    @Column(name = "promoName")
    private String promoName;

    @Column(name = "issueDate")
    private LocalDate issueDate;

    @Column(name = "expirationDate")
    private LocalDate expirationDate;

    @Column(name = "couponCode")
    private String couponCode;

    @Column(name = "couponStatus")
    private String couponStatus;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usermapId")
    private Usermap usermap;

    @ManyToMany(mappedBy = "promos")
    @OrderBy("purchaseDate DESC")
    private Set<Purchase> purchases = new HashSet<>();

    public Promo(String palsId, String promoName, LocalDate issueDate, LocalDate expirationDate, String couponCode, String couponStatus, Usermap usermap) {
        this.palsId = palsId;
        this.promoName = promoName;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.couponCode = couponCode;
        this.couponStatus = couponStatus;
        this.usermap = usermap;
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
        purchase.getPromos().add(this);
    }

    public void removePurchase(Purchase purchase) {
        purchase.setUsermap(null);
        this.purchases.remove(purchase);
    }

    public void removePurchases() {
        Iterator<Purchase> iterator = this.purchases.iterator();
        while (iterator.hasNext()) {
            Purchase purchase = iterator.next();
            purchase.setUsermap(null);
            iterator.remove();
        }
    }

}
