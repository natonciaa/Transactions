package com.laguama.transacciones_api.infrastructure.drivenAdaperts.adapters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.laguama.transacciones_api.domain.model.transaccion.Estado;
import com.laguama.transacciones_api.domain.model.transaccion.Transaction;
import com.laguama.transacciones_api.domain.model.transaccion.gateway.TransactionRepository;
import com.laguama.transacciones_api.infrastructure.drivenAdaperts.jpaRepository.entities.TransactionEntity;
import com.laguama.transacciones_api.infrastructure.drivenAdaperts.jpaRepository.repository.JpaTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final JpaTransactionRepository jpa;
    private final ObjectMapper mapper;

    @Override public Transaction save(Transaction tx) {
        TransactionEntity entity = mapper.convertValue(tx, TransactionEntity.class);
        return mapper.convertValue(jpa.save(entity), Transaction.class);
    }

    @Override public Optional<Transaction> findById(Long id) {
        return jpa.findById(id).map(t -> mapper.convertValue(t, Transaction.class));
    }

    @Override public List<Transaction> findAll(String nombre, LocalDateTime fecha, Estado estado) {
        return jpa.findAll().stream().map(t ->  mapper.convertValue(t, Transaction.class))
                .filter(t -> nombre == null || t.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(t -> fecha == null || t.getFecha().toLocalDate().equals(fecha.toLocalDate()))
                .filter(t -> estado == null || t.getEstado() == estado)
                .collect(Collectors.toList());
    }

    @Override public void deleteById(Long id) { jpa.deleteById(id); }

    @Override public long countCreatedSince(LocalDateTime since) { return jpa.countCreatedSince(since); }

    @Override public List<Transaction> findPendingOrderByFechaAsc() {
        return jpa.findByEstadoOrderByFechaAsc(Estado.PENDIENTE).stream()
                .map(t ->mapper.convertValue(t,Transaction.class)).collect(Collectors.toList());
    }
}
