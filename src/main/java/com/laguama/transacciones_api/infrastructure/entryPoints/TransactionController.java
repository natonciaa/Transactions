package com.laguama.transacciones_api.infrastructure.entryPoints;

import com.laguama.transacciones_api.application.usecase.*;
import com.laguama.transacciones_api.domain.model.transaccion.Estado;
import com.laguama.transacciones_api.domain.model.transaccion.Transaction;
import com.laguama.transacciones_api.infrastructure.entryPoints.dtos.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/transactions")
public class TransactionController {

    private final CreateTransactionUseCase createUC;
    private final GetAllTransactionsUseCase getAllUC;
    private final UpdateTransactionUseCase updateUC;
    private final DeleteTransactionUseCase deleteUC;
    private final PayTransactionsUseCase payUC;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionDto body) {
        try {
            Transaction created = createUC.create(body.getNombre(), body.getValor());
            URI location = URI.create("/transactions/" + created.getNombre());
            return ResponseEntity.created(location).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(429).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
            @RequestParam(required = false) Estado estado
    ) {
        return ResponseEntity.ok(getAllUC.getAll(nombre, fecha, estado));
    }

    @GetMapping("/{transactionsName}")
    public ResponseEntity<?> getByName(@PathVariable String transactionsName) {
        return getAllUC.getAll(transactionsName, null, null).stream()
                .filter(t -> t.getNombre().equalsIgnoreCase(transactionsName))
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("No existe la transccion: " + transactionsName));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Transaction body) {
        try {
            Transaction updated = updateUC.update(id, body.getNombre(), body.getValor());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(429).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        Optional<Transaction> transaction = getAllUC.findById(id);
        try {
            if(!transaction.isPresent()) {
                return ResponseEntity.status(404).body("No existe la transaccion: " + id);
            }
                deleteUC.delete(transaction.get().getId());
                return ResponseEntity.noContent().build();
            } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/payments")
    public ResponseEntity<?> pay(@RequestParam("amount") BigDecimal amount) {
        try {
            return ResponseEntity.ok(payUC.pay(amount));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
