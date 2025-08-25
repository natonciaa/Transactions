package com.laguama.transacciones_api.domain.model.transaccion.gateway;


import com.laguama.transacciones_api.domain.model.transaccion.Estado;
import com.laguama.transacciones_api.domain.model.transaccion.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction tx);
    Optional<Transaction> findById(Long id);
    List<Transaction> findAll(String nombre, LocalDateTime fecha, Estado estado);
    void deleteById(Long id);
    long countCreatedSince(LocalDateTime since);
    List<Transaction> findPendingOrderByFechaAsc();
}
