package com.diarias.patient_pdf_service.model;
import jakarta.persistence.*;

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
    public byte[] getPdfData(){
        return pdfData;
    }
    public void setPdfData(byte[] pdfData){
        this.pdfData = pdfData;
    }
}


