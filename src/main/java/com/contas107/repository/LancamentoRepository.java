package com.contas107.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contas107.model.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> { }
