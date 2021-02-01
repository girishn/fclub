package com.example.foodclub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "userKey")
public class UserKey extends AuditModel<Long> {

    @Column(name = "palsId")
    private String palsId;

    @Column(name = "pgrId")
    private String pgrId;

    @Column(name = "wcsId")
    private long wcsId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usermapId")
    private Usermap usermap;

    public UserKey(String palsId, String pgrId, long wcsId) {
        this.palsId = palsId;
        this.pgrId = pgrId;
        this.wcsId = wcsId;
    }
}
