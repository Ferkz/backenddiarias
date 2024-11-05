package com.diarias.patient_pdf_service.model;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "patient_pdf")
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
    private LocalDate created_at;
    @Lob
    private byte[] pdfData;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Number getNumeroProntuario() {
        return numeroProntuario;
    }
    public void setNumeroProntuario(Number numeroProntuario) {
        this.numeroProntuario = numeroProntuario;
    }
    public String getTipoAlta() {
        return tipoAlta;
    }
    public void setTipoAlta(String tipoAlta) {
        this.tipoAlta = tipoAlta;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }
    public String getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    public String getHoraSaida() {
        return horaSaida;
    }
    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }
    public Number getDiasInternado() {
        return diasInternado;
    }
    public void setDiasInternado(Number dias){
        this.diasInternado = dias;
    }
    public String getValorDiario() {
        return valorDiario;
    }
    public void setValorDiario(String valorDiario){
       this.valorDiario = valorDiario;
    }
    public String getValorTotal (){
        return valorTotal;
    }
    public void setValorTotal(String valorTotal){
        this.valorTotal = valorTotal;
    }
    public LocalDate getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
    public byte[] getPdfData(){
        return pdfData;
    }
    public void setPdfData(byte[] pdfData){
        this.pdfData = pdfData;
    }
}


