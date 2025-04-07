package com.contas107.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contas107.model.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> { 

    @Query("SELECT l FROM Lancamento l WHERE MONTH(l.dataLancamento) = :month")
    List<Lancamento> lancamentosNoMes(@Param("month") int month);

}
