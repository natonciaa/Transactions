package com.laguama.transacciones_api.infrastructure.drivenAdaperts.jpaRepository.repository;

import com.laguama.transacciones_api.domain.model.transaccion.Estado;
import com.laguama.transacciones_api.infrastructure.drivenAdaperts.jpaRepository.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, Long> {

    Optional<TransactionEntity> findByNombre(String nombre);

    @Query("select count(t) from TransactionEntity t where t.fecha >= :since")
    long countCreatedSince(LocalDateTime since);

    List<TransactionEntity> findByEstadoOrderByFechaAsc(Estado estado);
}
