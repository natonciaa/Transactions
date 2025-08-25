package com.laguama.transacciones_api.application.usecase;

import com.laguama.transacciones_api.domain.model.transaccion.Transaction;
import com.laguama.transacciones_api.domain.model.transaccion.gateway.TransactionRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UpdateTransactionUseCase {
    private final TransactionRepository repository;

    public Transaction update(Long id, String nuevoNombre, BigDecimal nuevoValor) {
        Transaction tx = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("La transacción no existe"));
        if (tx.getEstado().name().equals("PAGO")) throw new IllegalStateException("No se puede editar una transacción pagada");
        if (nuevoNombre != null && !nuevoNombre.isBlank()) tx.setNombre(nuevoNombre);
        if (nuevoValor != null && nuevoValor.signum() > 0) tx.setValor(nuevoValor);
        return repository.save(tx);
    }
}
