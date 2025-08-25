package com.laguama.transacciones_api.application.usecase;

import com.laguama.transacciones_api.domain.model.transaccion.Estado;
import com.laguama.transacciones_api.domain.model.transaccion.Transaction;
import com.laguama.transacciones_api.domain.model.transaccion.gateway.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class GetAllTransactionsUseCase {
    private final TransactionRepository repository;
    public GetAllTransactionsUseCase(TransactionRepository repository) { this.repository = repository; }

    public List<Transaction> getAll(String nombre, LocalDateTime fecha, Estado estado) {
        return repository.findAll(nombre, fecha, estado);
    }

    public Optional<Transaction> findById(String id) {
        return repository.findById(Long.parseLong(id));
    }
}
