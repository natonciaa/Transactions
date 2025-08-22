package com.laguama.transacciones_api.domain.model.transaccion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long id;
    private String nombre;
    private LocalDateTime fecha;
    private BigDecimal valor;
    private Estado estado;
}
