package com.example.foodclub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Purchase extends AuditModel<Long> {

    private long transactionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "purchaseDate", columnDefinition = "DATE")
    private LocalDate purchaseDate;

    private long skuId;

    private String skuName;

    private double skuPrice;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usermapId")
    private Usermap usermap;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "promoId")
    @Nullable
    private Promo promo;

    public Purchase(LocalDate purchaseDate, long skuId, String skuName, double skuPrice, Usermap usermap) {
        this.purchaseDate = purchaseDate;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuPrice = skuPrice;
        this.usermap = usermap;
    }
}
