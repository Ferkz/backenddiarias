package com.diarias.patient_pdf_service.service;

import com.diarias.patient_pdf_service.dto.PacienteRequest;
import com.diarias.patient_pdf_service.model.Paciente;
import com.diarias.patient_pdf_service.repository.PacienteRepository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente generateAndSavePdf(PacienteRequest pacienteRequest) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.setMargins(20, 20, 20, 20);
            document.add(new Paragraph("Patient Report"));
            document.add(new Paragraph("Name: " + pacienteRequest.getNome()));
            document.add(new Paragraph("Record Number: " + pacienteRequest.getNumeroProntuario()));
            document.add(new Paragraph("Date: " + pacienteRequest.getTipoAlta()));
            document.add(new Paragraph("Data de entrada: " + pacienteRequest.getDataEntrada()));
            document.add(new Paragraph("Date de saida: " + pacienteRequest.getDataSaida()));
            document.add(new Paragraph("Hora de entrada: " + pacienteRequest.getHoraEntrada()));
            document.add(new Paragraph("gora de saida: " + pacienteRequest.getHoraSaida()));
            document.close();

            byte[] pdfBytes = byteArrayOutputStream.toByteArray();

            Paciente pacientePdf = new Paciente();
            pacientePdf.setNome(pacienteRequest.getNome());
            pacientePdf.setNumeroProntuario(pacienteRequest.getNumeroProntuario());
            pacientePdf.setTipoAlta(pacienteRequest.getTipoAlta());
            pacientePdf.setDataEntrada(pacienteRequest.getDataEntrada());
            pacientePdf.setDataSaida(pacienteRequest.getDataSaida());
            pacientePdf.setHoraEntrada(pacienteRequest.getHoraEntrada());
            pacientePdf.setHoraSaida(pacienteRequest.getDataSaida());
            pacientePdf.setPdfData(pdfBytes);

            return pacienteRepository.save(pacientePdf);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
    public List<Paciente> getAllPdfs() {
        return pacienteRepository.findAll();
    }
}
