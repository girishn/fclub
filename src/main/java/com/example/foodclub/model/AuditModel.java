package com.example.foodclub.model;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel<PK extends Serializable> extends AbstractPersistable<PK> {

    private static final long serialVersionUID = 141481953116476081L;

    @CreatedDate
    @Nullable
    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Nullable
    @Column(name = "lastModifiedDate")
    private LocalDateTime lastModifiedDate;

}