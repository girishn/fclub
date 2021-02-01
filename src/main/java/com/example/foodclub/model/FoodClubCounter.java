package com.example.foodclub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "foodClubCounter")
public class FoodClubCounter extends AuditModel<Long> {

    @Column(name = "punchCount")
    private int punchCount;

    @Column(name = "resetCount")
    private int resetCount;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usermapid")
    private Usermap usermap;

}
