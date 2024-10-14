package com.diarias.patient_pdf_service.dto;

public class PacienteRequest {
    private String nome;
    private Number numeroProntuario;
    private String tipoAlta;
    private String dataEntrada;
    private String dataSaida;
    private String horaEntrada;
    private  String horaSaida;
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
}
