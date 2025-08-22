package com.laguama.transacciones_api.application.usecase;

import com.laguama.transacciones_api.domain.model.transaccion.Estado;
import com.laguama.transacciones_api.domain.model.transaccion.Transaction;
import com.laguama.transacciones_api.domain.model.transaccion.gateway.TransactionRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CreateTransactionUseCase {

    private final TransactionRepository repository;

    public Transaction create(String nombre, BigDecimal valor) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre requerido");
        if (valor == null || valor.signum() <= 0) throw new IllegalArgumentException("Valor debe ser positivo");

        LocalDateTime since = LocalDateTime.now().minusSeconds(60);
        long count = repository.countCreatedSince(since);
        if (count >= 1) throw new IllegalStateException("Rate limit: solo 1 transacción por minuto");

        Transaction tx = new Transaction(null, nombre, LocalDateTime.now(), valor, Estado.PENDIENTE);
        return repository.save(tx);
    }
}
