package com.example.foodclub.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"purchases", "promos"}, callSuper = false)
@ToString(exclude = {"purchases", "promos"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Usermap extends AuditModel<Long> {

    private String palsId;
    private String pgrId;
    private long wcsId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "usermap")
    private Set<Purchase> purchases;

    @OneToOne(targetEntity = Enrollment.class, cascade = CascadeType.ALL)
    private Enrollment enrollment;

    @OneToOne(targetEntity = Counter.class, cascade = CascadeType.ALL)
    private Counter counter;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "usermap")
    private List<Promo> promos;

    public Usermap(String palsId, String pgrId, long wcsId) {
        this.palsId = palsId;
        this.pgrId = pgrId;
        this.wcsId = wcsId;
    }
}
