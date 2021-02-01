package com.example.foodclub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "purchase")
public class Purchase extends AuditModel<Long> {

    @Column(name = "transactionId")
    private long transactionId;

    @Column(name = "itemSeqNumber")
    private int itemSeqNumber;

    @Column(name = "returnFlag")
    private boolean returnFlag;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "transactionDate", columnDefinition = "DATE")
    private LocalDate transactionDate;

    @Column(name = "skuId")
    private long skuId;

    @Column(name = "skuName")
    private String skuName;

    @Column(name = "netSalesAmount")
    private double netSalesAmount;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usermapId")
    private Usermap usermap;

    @JsonBackReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "purchasePromo",
            joinColumns = @JoinColumn(name = "purchaseId"),
            inverseJoinColumns = @JoinColumn(name = "promoId")
    )
    private Set<Promo> promos = new HashSet<>();

    public Purchase(LocalDate transactionDate, long skuId, String skuName, double netSalesAmount, Usermap usermap) {
        this.transactionDate = transactionDate;
        this.skuId = skuId;
        this.skuName = skuName;
        this.netSalesAmount = netSalesAmount;
        this.usermap = usermap;
    }

    public void addPromo(Promo promo) {
        this.promos.add(promo);
        promo.getPurchases().add(this);
    }

    public void removePromo(Promo promo) {
        promo.setUsermap(null);
        this.promos.remove(promo);
    }

    public void removePromos() {
        Iterator<Promo> iterator = this.promos.iterator();
        while (iterator.hasNext()) {
            Promo promo = iterator.next();
            promo.setUsermap(null);
            iterator.remove();
        }
    }
}
