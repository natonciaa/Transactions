package com.laguama.transacciones_api.infrastructure.drivenAdaperts.jpaRepository.entities;

import com.laguama.transacciones_api.domain.model.transaccion.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private LocalDateTime fecha;

    @Column(precision = 15, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private Estado estado;

}

