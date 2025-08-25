package com.laguama.transacciones_api.application.usecase;

import com.laguama.transacciones_api.domain.model.transaccion.Estado;
import com.laguama.transacciones_api.domain.model.transaccion.Transaction;
import com.laguama.transacciones_api.domain.model.transaccion.gateway.TransactionRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PayTransactionsUseCase {
    private final TransactionRepository repository;

    public List<Transaction> pay(BigDecimal monto) {
        if (monto == null || monto.signum() <= 0) throw new IllegalArgumentException("Monto debe ser positivo");

        List<Transaction> pendientes = repository.findPendingOrderByFechaAsc();
        List<Transaction> pagadas = new ArrayList<>();

        BigDecimal restante = monto;
        for (Transaction t : pendientes) {
            if (restante.compareTo(t.getValor()) >= 0) {
                restante = restante.subtract(t.getValor());
                t.setEstado(Estado.PAGO);
                repository.save(t);
                pagadas.add(t);
            } else {
                break;
            }
        }
        return pagadas;
    }
}
