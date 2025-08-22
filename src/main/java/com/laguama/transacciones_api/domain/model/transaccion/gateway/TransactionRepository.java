package com.laguama.transacciones_api.domain.model.transaccion.gateway;


import com.laguama.transacciones_api.domain.model.transaccion.Transaction;

import java.time.LocalDateTime;


public interface TransactionRepository {
    Transaction save(Transaction tx);

    long countCreatedSince(LocalDateTime since);

}
