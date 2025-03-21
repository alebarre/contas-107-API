package com.contas107.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name = "tb_lancamento")
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	Date dataLancamento;
	
	@Column(nullable = false, length = 255)
	String empresa;
	
	@ManyToOne
	@JoinColumn(name = "banco_id", nullable = false)
	Banco banco;
	
	@Column(nullable = false)
	BigDecimal valor;
	
	@Column(length = 255)
	String observacao;
	
	@Column(nullable = false)
	Boolean atraso;

}
