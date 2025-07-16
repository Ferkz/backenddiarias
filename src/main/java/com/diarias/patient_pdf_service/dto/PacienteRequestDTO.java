package com.diarias.patient_pdf_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestDTO {

    private Long id;
    private String nome;
    private Number numeroProntuario;
    private String tipoAlta;
    private String dataEntrada;
    private String dataSaida;
    private String horaEntrada;
    private String horaSaida;
    private Number diasInternado;
    private String valorDiario;
    private String valorTotal;
    private LocalDateTime createdAt;
    private String numeroAih;
    private String competencia;
}