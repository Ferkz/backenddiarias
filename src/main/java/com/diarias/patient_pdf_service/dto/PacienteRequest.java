package com.diarias.patient_pdf_service.dto;

import java.time.LocalDate;

public class PacienteRequest {
    private String nome;
    private Number numeroProntuario;
    private String tipoAlta;
    private String dataEntrada;
    private String dataSaida;
    private String horaEntrada;
    private  String horaSaida;
    private Number diasInternado;
    private String valorDiario;
    private String valorTotal;
    private LocalDate created_at;
    public String getNome(){
        return nome;
    }
    public Number getNumeroProntuario(){
        return numeroProntuario;
    }
    public String getTipoAlta (){
        return  tipoAlta;
    }
    public String getDataEntrada (){
        return dataEntrada;
    }
    public String getDataSaida (){
        return  dataSaida;
    }
    public String getHoraEntrada (){
        return horaEntrada;
    }
    public String getHoraSaida(){
        return horaSaida;
    }
    public Number getDiasInternado() {
        return diasInternado;
    }
    public String getValorDiario(){
        return valorDiario;
    }
    public String getValorTotal(){
        return valorTotal;
    }
    public LocalDate getCreated_at() {
        return created_at;
    }
}
