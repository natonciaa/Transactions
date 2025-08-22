package com.laguama.transacciones_api.infrastructure.drivenAdaperts.config;

import com.laguama.transacciones_api.application.usecase.CreateTransactionUseCase;
import com.laguama.transacciones_api.domain.model.transaccion.gateway.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CreateTransactionUseCase createUC(TransactionRepository transactionRepository) {
        return new CreateTransactionUseCase(transactionRepository);
    }
}
