package com.laguama.transacciones_api.infrastructure.entryPoints;

import com.laguama.transacciones_api.application.usecase.*;
import com.laguama.transacciones_api.domain.model.transaccion.Estado;
import com.laguama.transacciones_api.domain.model.transaccion.Transaction;
import com.laguama.transacciones_api.infrastructure.entryPoints.dtos.TransactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private GetAllTransactionsUseCase getAllUC;
    @Mock
    private CreateTransactionUseCase createUC;
    @Mock
    private UpdateTransactionUseCase updateUC;
    @Mock
    private DeleteTransactionUseCase deleteUC;
    @Mock
    private PayTransactionsUseCase paymentUC;

    @InjectMocks
    private TransactionController controller;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnFilteredTransactions() {
        Transaction t = new Transaction(1L, "Test", LocalDateTime.now(), new BigDecimal(100), Estado.PENDIENTE);
        when(getAllUC.getAll("Test", null, null)).thenReturn(List.of(t));

        ResponseEntity<List<Transaction>> res = controller.getAll("Test", null, null);

        assertThat(res.getBody()).hasSize(1);
        verify(getAllUC).getAll("Test", null, null);
    }

    @Test
    void shouldCreateTransaction() {
        TransactionDto input = new TransactionDto("Nuevo", new BigDecimal(200));
        Transaction saved = new Transaction(1L, "Nuevo", LocalDateTime.now(), new BigDecimal(200), Estado.PENDIENTE);
        when(createUC.create("Nuevo",new BigDecimal(200))).thenReturn(saved);

        ResponseEntity<?> res = controller.create(input);

        verify(createUC).create("Nuevo",new BigDecimal(200));
    }

    @Test
    void shouldUpdateTransaction() {
        Transaction updated = new Transaction(1L, "Editado", LocalDateTime.now(), new BigDecimal(300), Estado.PENDIENTE);
        when(updateUC.update(1L, "Editado",new BigDecimal(300))).thenReturn(updated);

        ResponseEntity<?> res = controller.update(1L, updated);
        verify(updateUC).update(1L, "Editado",new BigDecimal(300));
    }

    @Test
    void shouldDeleteTransaction() {
        Transaction t1 = new Transaction(1L, "Compra1", LocalDateTime.now().minusDays(2), new BigDecimal(100), Estado.PENDIENTE);
        when(getAllUC.findById("1")).thenReturn(Optional.of(t1));
        doNothing().when(deleteUC).delete(1L);
        ResponseEntity<?> res = controller.deleteById("1L");
        assertThat(res.getStatusCode().is2xxSuccessful()).isTrue();
        verify(deleteUC).delete(1L);
    }

    @Test
    void shouldMakePayment() {
        Transaction t1 = new Transaction(1L, "Compra1", LocalDateTime.now().minusDays(2), new BigDecimal(100), Estado.PENDIENTE);
        Transaction t2 = new Transaction(2L, "Compra2", LocalDateTime.now().minusDays(1), new BigDecimal(200), Estado.PENDIENTE);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(t1);
        transactions.add(t2);

        when(paymentUC.pay(new BigDecimal(100))).thenReturn(transactions);
        ResponseEntity<?> res = controller.pay(new BigDecimal(100));
        assertThat(res.getStatusCode().is2xxSuccessful()).isTrue();
        verify(paymentUC).pay(new BigDecimal(100));
    }
}
