package com.laguama.transacciones_api.application.usecase;

import com.laguama.transacciones_api.domain.model.transaccion.Transaction;
import com.laguama.transacciones_api.domain.model.transaccion.gateway.TransactionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteTransactionUseCase {
    private final TransactionRepository repository;

    public void delete(Long id) {
        Transaction tx = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe"));
        if (tx.getEstado().name().equals("PAGO")) throw new IllegalStateException("No se puede borrar una transacción pagada");
        repository.deleteById(id);
    }
}
