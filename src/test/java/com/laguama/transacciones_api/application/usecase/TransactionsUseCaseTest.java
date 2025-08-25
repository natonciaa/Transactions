package com.laguama.transacciones_api.application.usecase;

import com.laguama.transacciones_api.domain.model.transaccion.Estado;
import com.laguama.transacciones_api.domain.model.transaccion.Transaction;
import com.laguama.transacciones_api.domain.model.transaccion.gateway.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TransactionsUseCaseTest {

    @Mock
    private TransactionRepository repository;

    private CreateTransactionUseCase createUseCase;

    private UpdateTransactionUseCase updateUseCase;

    private DeleteTransactionUseCase deleteUseCase;

    private GetAllTransactionsUseCase getAllUseCase;

    private PayTransactionsUseCase payTransactionsUseCase;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        createUseCase = new CreateTransactionUseCase(repository);
        updateUseCase = new UpdateTransactionUseCase(repository);
        getAllUseCase = new GetAllTransactionsUseCase(repository);
        deleteUseCase = new DeleteTransactionUseCase(repository);
        payTransactionsUseCase = new PayTransactionsUseCase(repository);
    }

    @Test
    void createTransaction() {
        Transaction transactionResp = new Transaction(1L, "EPM", LocalDateTime.now(), new BigDecimal(130500), Estado.PENDIENTE);
        when(repository.save(any(Transaction.class))).thenReturn(transactionResp);
        Transaction result = createUseCase.create("EPM", new BigDecimal(130500));
        assertThat(result.getId()).isEqualTo(1L);
        verify(repository).save(any(Transaction.class));

    }

    @Test
    void shouldUpdateIfExists() {
        Transaction existing = new Transaction(1L, "Viejo", LocalDateTime.now(), new BigDecimal(1000), Estado.PENDIENTE);
        Transaction update = new Transaction(1L, "Nuevo", LocalDateTime.now(), new BigDecimal(200), Estado.PENDIENTE);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Transaction.class))).thenReturn(update);
        Transaction result = updateUseCase.update(1L, update.getNombre(), update.getValor());
        assertThat(result.getNombre()).isEqualTo("Nuevo");
        verify(repository).save(any(Transaction.class));
    }

    @Test
    void shouldReturnAllWhenNoFilters() {
        Transaction t = new Transaction(1L, "Test", LocalDateTime.now(), new BigDecimal(100), Estado.PENDIENTE);
        when(repository.findAll(null,null,null)).thenReturn(List.of(t));
        List<Transaction> result = getAllUseCase.getAll(null, null, null);
        assertThat(result).hasSize(1);
        verify(repository).findAll(null,null,null);
    }

    @Test
    void shouldReturnByIdWhenExists() {
        Transaction t = new Transaction(1L, "Test", LocalDateTime.now(), new BigDecimal(100), Estado.PENDIENTE);
        when(repository.findById(1L)).thenReturn(Optional.of(t));
        Optional<Transaction> result = getAllUseCase.findById("1");
        assertThat(result).isNotNull();
        verify(repository).findById(1L);
    }

    @Test
    void shouldReturnFilteredByNombre() {
        Transaction t = new Transaction(1L, "Juan", LocalDateTime.now(), new BigDecimal(200), Estado.PENDIENTE);
        when(repository.findAll("Juan",null,null)).thenReturn(List.of(t));
        List<Transaction> result = getAllUseCase.getAll("Juan", null, null);
        assertThat(result.get(0).getNombre()).isEqualTo("Juan");
        verify(repository).findAll("Juan",null,null);
    }

    @Test
    void shouldDeleteById() {
        Transaction t = new Transaction(1L, "Test", LocalDateTime.now(), new BigDecimal(100), Estado.PENDIENTE);
        when(repository.findById(1L)).thenReturn(Optional.of(t));
        doNothing().when(repository).deleteById(1L);
        deleteUseCase.delete(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldPayOldestTransactionsFirst() {
        Transaction t1 = new Transaction(1L, "Compra1", LocalDateTime.now().minusDays(2), new BigDecimal(100), Estado.PENDIENTE);
        Transaction t2 = new Transaction(2L, "Compra2", LocalDateTime.now().minusDays(1), new BigDecimal(200), Estado.PENDIENTE);
        when(repository.findPendingOrderByFechaAsc())
                .thenReturn(List.of(t1, t2));
        payTransactionsUseCase.pay(new BigDecimal(100));
        verify(repository).save(argThat(tx -> tx.getId() == 1L && tx.getEstado() == Estado.PAGO));
        verify(repository, never()).save(argThat(tx -> tx.getId() == 2L));
    }

}
