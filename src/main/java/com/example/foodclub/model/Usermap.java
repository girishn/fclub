package com.example.foodclub.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"purchases", "promos", "userKeys"}, callSuper = false)
@ToString(exclude = {"purchases", "promos", "userKeys"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedEntityGraph(
        name = "usermap-userKeys-graph",
        attributeNodes = {
                @NamedAttributeNode("userKeys")
        }
)
@Table(name = "usermap")
public class Usermap extends AuditModel<Long> {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "usermap")
    private List<UserKey> userKeys = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "usermap")
    private List<Purchase> purchases = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "usermap")
    private List<Promo> promos = new ArrayList<>();

    public Usermap(String palsId, String pgrId, long wcsId) {
        addUserKey(new UserKey(palsId, pgrId, wcsId));
    }

    public void addUserKey(UserKey userKey) {
        this.userKeys.add(userKey);
        userKey.setUsermap(this);
    }

    public void removeUserKey(UserKey userKey) {
        userKey.setUsermap(null);
        this.userKeys.remove(userKey);
    }

    public void removeUserKeys() {
        Iterator<UserKey> iterator = this.userKeys.iterator();
        while (iterator.hasNext()) {
            UserKey userKey = iterator.next();
            userKey.setUsermap(null);
            iterator.remove();
        }
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
        purchase.setUsermap(this);
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

    public void addPromo(Promo promo) {
        this.promos.add(promo);
        promo.setUsermap(this);
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
