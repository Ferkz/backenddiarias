package com.diarias.patient_pdf_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String numeroAih;
    private String competencia;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Lob
    private byte[] pdfData;

    /**

     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}