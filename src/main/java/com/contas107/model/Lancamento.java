package com.contas107.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_lancamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_LANCAMENTO", nullable = false)
	LocalDate dataLancamento;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_VENCIMENTO", nullable = false)
	LocalDate dataVencimento;

	@ManyToOne
	@JoinColumn(name = "EMPRESA_ID", nullable = false)
	Empresa empresa;
	
	@ManyToOne
	@JoinColumn(name = "BANCO_ID", nullable = false)
	Banco banco;
	
	@Column(name = "VALOR", nullable = false)
	BigDecimal valor;
	
	@Column(name = "OBSERVACAO", length = 255)
	String observacao;
	
	@Column(name = "ATRASO", nullable = false)
	Boolean atraso;

}
