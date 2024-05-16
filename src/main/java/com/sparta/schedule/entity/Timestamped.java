package com.sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class Timestamped {

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate createdDate;

}