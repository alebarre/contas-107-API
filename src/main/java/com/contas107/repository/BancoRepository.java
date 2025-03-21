package com.contas107.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contas107.model.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> { }
