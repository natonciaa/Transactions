package com.laguama.transacciones_api.infrastructure.entryPoints;

import com.laguama.transacciones_api.application.usecase.CreateTransactionUseCase;
import com.laguama.transacciones_api.domain.model.transaccion.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lists") // se mantiene el contrato del enunciado
public class TransactionController {

    private final CreateTransactionUseCase createUC;

    // POST /lists
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Transaction body) {
        try {
            Transaction created = createUC.create(body.getNombre(), body.getValor());
            URI location = URI.create("/lists/" + created.getNombre());
            return ResponseEntity.created(location).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(429).body(e.getMessage()); // rate limit
        }
    }

}
