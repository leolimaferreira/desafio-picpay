package com.desafiopicpay.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transfers")
@Setter
@Getter
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "payer_id", nullable = false)
    private User payer;

    @OneToOne
    @JoinColumn(name = "payee_id", nullable = false)
    private User payee;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal value;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
