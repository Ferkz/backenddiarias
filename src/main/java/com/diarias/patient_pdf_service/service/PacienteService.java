package com.diarias.patient_pdf_service.service;

import com.diarias.patient_pdf_service.dto.PacienteRequest;
import com.diarias.patient_pdf_service.model.Paciente;
import com.diarias.patient_pdf_service.repository.PacienteRepository;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import  com.itextpdf.layout.property.UnitValue;


@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente generateAndSavePdf(PacienteRequest pacienteRequest) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            ClassPathResource imgFile = new ClassPathResource("images/nova-logo.png");
            ClassPathResource imgSus = new ClassPathResource("images/sus-logo.png");
            InputStream inputStream = imgFile.getInputStream();
            InputStream InputLogoSus = imgSus.getInputStream();

            ImageData imageData = ImageDataFactory.create(inputStream.readAllBytes());
            ImageData imgLogoSus = ImageDataFactory.create(InputLogoSus.readAllBytes());

            Image logoSamar = new Image(imageData);
            Image logoSus = new Image(imgLogoSus);

            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            logoSamar.setWidth(180);
            logoSus.setWidth(160);
            logoSamar.setHeight(80);
            logoSus.setHeight(80);

            Table table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();

            table.addCell(new Cell().add(logoSamar).setBorder(null).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(logoSus).setBorder(null).setTextAlignment(TextAlignment.RIGHT));

            document.add(table);

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
