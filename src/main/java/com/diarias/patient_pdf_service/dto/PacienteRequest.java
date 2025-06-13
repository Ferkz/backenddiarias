package com.diarias.patient_pdf_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PacienteRequest {

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
    private LocalDateTime created_at;
    private String numeroAih;
    private String competencia;
}