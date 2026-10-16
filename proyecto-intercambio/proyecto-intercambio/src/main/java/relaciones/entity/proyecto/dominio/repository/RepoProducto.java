package relaciones.entity.proyecto.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import relaciones.entity.proyecto.dominio.entity.Producto;

public interface RepoProducto extends JpaRepository<Producto, Integer> {

    /**
     * Busca el producto y BLOQUEA su fila en la base (SELECT ... FOR UPDATE).
     * Otra transaccion que pida el mismo producto con este metodo espera hasta
     * que la primera termine (commit o rollback).
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Producto p where p.id = :id")
    Optional<Producto> findByIdForUpdate(@Param("id") Integer id);
}
