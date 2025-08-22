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

    @Override public long countCreatedSince(LocalDateTime since) { return jpa.countCreatedSince(since); }


}
