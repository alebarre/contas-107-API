package com.contas107.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.contas107.model.Banco;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoRequestDTO {

    @JsonProperty
    private Long id;
        
    @JsonProperty(required = true)
    private LocalDate dataLancamento;
    
    @JsonProperty(required = true)
    private LocalDate dataVencimento;

    @JsonProperty(required = true)
    private String empresa;

    @JsonProperty(required = true)
    private Banco banco;
    
    @JsonProperty(required = true)
    private BigDecimal valor;

    @JsonProperty(required = true)
    private String observacao;

    @JsonProperty(required = true)
    private Boolean atraso;
    
}
