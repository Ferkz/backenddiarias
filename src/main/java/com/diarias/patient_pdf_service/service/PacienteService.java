package com.diarias.patient_pdf_service.service;

import com.diarias.patient_pdf_service.dto.PacienteRequest;
import com.diarias.patient_pdf_service.model.Paciente;
import com.diarias.patient_pdf_service.repository.PacienteRepository;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
            String logo = new ClassPathResource("images/nova-logo.png").getFile().getPath();
            ImageData imageData = ImageDataFactory.create(logo);
            Image image = new Image(imageData);

            image.setWidth(200);
            image.setHeight(80);
            image.setTextAlignment(TextAlignment.CENTER);
            document.add(image);

            document.setMargins(20, 20, 20, 20);
            document.add(new Paragraph("DEMONSTRATIVO DE DESPESAS\n").setTextAlignment(TextAlignment.CENTER).setFontSize(20));
            document.add(new Paragraph("Nome: " + pacienteRequest.getNome()));
            document.add(new Paragraph("Número de Prontuário: " + pacienteRequest.getNumeroProntuario()));
            document.add(new Paragraph("Tipo de Alta: " + pacienteRequest.getTipoAlta()));
            document.add(new Paragraph("Data de Entrada: " + pacienteRequest.getDataEntrada()));
            document.add(new Paragraph("Data de Saída: " + pacienteRequest.getDataSaida()));
            document.add(new Paragraph("Hora de Entrada: " + pacienteRequest.getHoraEntrada()));
            document.add(new Paragraph("Hora de Saída: " + pacienteRequest.getHoraSaida()));
            document.add(new Paragraph("Quantidade de dias internado: "+ pacienteRequest.getDiasInternado()));
            document.add((new Paragraph("Valor da diária: "+ pacienteRequest.getValorDiario() )));
            document.add((new Paragraph("Valor total da internação: " + pacienteRequest.getValorTotal() )));

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
            pacientePdf.setDiasInternado(pacienteRequest.getDiasInternado());
            pacientePdf.setValorDiario(pacienteRequest.getValorDiario());
            pacientePdf.setValorTotal(pacienteRequest.getValorTotal());
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
