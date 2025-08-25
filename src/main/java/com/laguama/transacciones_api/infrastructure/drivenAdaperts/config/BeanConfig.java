package com.laguama.transacciones_api.infrastructure.drivenAdaperts.config;

import com.laguama.transacciones_api.application.usecase.*;
import com.laguama.transacciones_api.domain.model.transaccion.gateway.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CreateTransactionUseCase createUC(TransactionRepository transactionRepository) {
        return new CreateTransactionUseCase(transactionRepository);}
    @Bean
    public UpdateTransactionUseCase updateUC(TransactionRepository transactionRepository) {
        return new UpdateTransactionUseCase(transactionRepository); }
    @Bean
    public DeleteTransactionUseCase deleteUC(TransactionRepository transactionRepository) {
        return new DeleteTransactionUseCase(transactionRepository); }
    @Bean
    public PayTransactionsUseCase payUC(TransactionRepository transactionRepository) {
        return new PayTransactionsUseCase(transactionRepository); }
    @Bean
    public GetAllTransactionsUseCase getAllUC(TransactionRepository transactionRepository) {
        return new GetAllTransactionsUseCase(transactionRepository);}
}
