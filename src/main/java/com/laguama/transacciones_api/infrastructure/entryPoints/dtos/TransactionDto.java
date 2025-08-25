package com.laguama.transacciones_api.infrastructure.entryPoints.dtos;

import com.laguama.transacciones_api.domain.model.transaccion.Estado;
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
public class TransactionDto {
    private String nombre;
    private BigDecimal valor;
}
