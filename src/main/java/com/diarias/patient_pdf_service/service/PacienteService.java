package com.diarias.patient_pdf_service.service;

import com.diarias.patient_pdf_service.dto.PacienteRequestDTO;
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
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import  com.itextpdf.layout.property.UnitValue;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    private static final Map<String, Integer>MESES;
    static {
        MESES = IntStream.range(0,12).boxed().collect(Collectors.toMap(
                i -> new java.text.DateFormatSymbols(new java.util.Locale("pt", "BR")).getMonths()[i].toLowerCase(),
                i -> i + 1
        ));
    }

    private byte[] gerarPdfBytes(PacienteRequestDTO pacienteRequest) throws IOException {
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
            //document.add(new Paragraph("Nº da AIH: " + pacienteRequest.getNumeroAih()));
            document.add(new Paragraph("Tipo de Alta: " + pacienteRequest.getTipoAlta()));
            document.add(new Paragraph("Competência: " + pacienteRequest.getCompetencia()));
            document.add(new Paragraph("Data de Entrada: " + pacienteRequest.getDataEntrada()));
            document.add(new Paragraph("Data de Saída: " + pacienteRequest.getDataSaida()));
            document.add(new Paragraph("Hora de Entrada: " + pacienteRequest.getHoraEntrada()));
            document.add(new Paragraph("Hora de Saída: " + pacienteRequest.getHoraSaida()));
            document.add(new Paragraph("Quantidade de dias internado: "+ pacienteRequest.getDiasInternado()));
            document.add((new Paragraph("Valor da diária: "+ pacienteRequest.getValorDiario() )));
            document.add((new Paragraph("Valor total da internação: " + pacienteRequest.getValorTotal() )));
            document.close();

            return byteArrayOutputStream.toByteArray();
        }
    }

    public Paciente generateAndSavePdf(PacienteRequestDTO pacienteRequest) {
        try {
            byte[] pdfBytes = gerarPdfBytes(pacienteRequest);

            Paciente pacientePdf = new Paciente();
            pacientePdf.setNome(pacienteRequest.getNome());
            pacientePdf.setNumeroProntuario(pacienteRequest.getNumeroProntuario());
            pacientePdf.setTipoAlta(pacienteRequest.getTipoAlta());
            pacientePdf.setDataEntrada(pacienteRequest.getDataEntrada());
            pacientePdf.setDataSaida(pacienteRequest.getDataSaida());
            pacientePdf.setHoraEntrada(pacienteRequest.getHoraEntrada());
            pacientePdf.setHoraSaida(pacienteRequest.getHoraSaida());
            pacientePdf.setDiasInternado(pacienteRequest.getDiasInternado());
            pacientePdf.setValorDiario(pacienteRequest.getValorDiario());
            pacientePdf.setValorTotal(pacienteRequest.getValorTotal());
            pacientePdf.setCompetencia(pacienteRequest.getCompetencia());
            //pacientePdf.setNumeroAih(pacienteRequest.getNumeroAih());
            pacientePdf.setPdfData(pdfBytes);

            return pacienteRepository.save(pacientePdf);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
    public List<PacienteRequestDTO> getAllPdfs() {
        return pacienteRepository.findAllPacientesAsDTO();
    }
    public Paciente findById (Long id){
        return pacienteRepository.findById(id).orElse(null);
    }
    public  boolean findByIdAndDelete(Long id){
        if (pacienteRepository.existsById(id)){
            pacienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Optional<Long> getLastPacienteId() {
        return pacienteRepository.findTopId();
    }
    @Transactional(readOnly = true)
    public byte[] generateXlsx(String competencia) throws IOException {
        if (competencia == null || competencia.isBlank()) {
            throw new IllegalArgumentException("O campo competência é obrigatório para gerar o relatório.");
        }
        List<Paciente> pacientes = pacienteRepository.findByCompetenciaIgnoreCase(competencia);

        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XSSFSheet sheet = workbook.createSheet("Pacientes - " + competencia);

            String[] columns = {"ID", "Nome", "Nº Prontuário", "Nº AIH", "Competência", "Tipo Alta", "Data Entrada", "Data Saída", "Hora Entrada", "Hora Saída", "Dias Internado", "Valor Diário", "Valor Total"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (Paciente paciente : pacientes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(paciente.getId());
                row.createCell(1).setCellValue(paciente.getNome());
                row.createCell(2).setCellValue(paciente.getNumeroProntuario() != null ? paciente.getNumeroProntuario().toString() : "");
                row.createCell(3).setCellValue(paciente.getNumeroAih());
                row.createCell(4).setCellValue(paciente.getCompetencia());
                row.createCell(5).setCellValue(paciente.getTipoAlta());
                row.createCell(6).setCellValue(paciente.getDataEntrada());
                row.createCell(7).setCellValue(paciente.getDataSaida());
                row.createCell(8).setCellValue(paciente.getHoraEntrada());
                row.createCell(9).setCellValue(paciente.getHoraSaida());
                row.createCell(10).setCellValue(paciente.getDiasInternado() != null ? paciente.getDiasInternado().toString() : "");
                row.createCell(11).setCellValue(paciente.getValorDiario());
                row.createCell(12).setCellValue(paciente.getValorTotal());
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    @Transactional
    public void updatePaciente(Long id, PacienteRequestDTO pacienteRequest) {
        // 1. Encontra o paciente existente no banco
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com o id: " + id));

        // 2. Atualiza todos os campos do paciente existente com os novos dados
        pacienteExistente.setNome(pacienteRequest.getNome());
        pacienteExistente.setNumeroProntuario(pacienteRequest.getNumeroProntuario());
        pacienteExistente.setNumeroAih(pacienteRequest.getNumeroAih());
        pacienteExistente.setTipoAlta(pacienteRequest.getTipoAlta());
        pacienteExistente.setCompetencia(pacienteRequest.getCompetencia());
        pacienteExistente.setDataEntrada(pacienteRequest.getDataEntrada());
        pacienteExistente.setDataSaida(pacienteRequest.getDataSaida());
        pacienteExistente.setHoraEntrada(pacienteRequest.getHoraEntrada());
        pacienteExistente.setHoraSaida(pacienteRequest.getHoraSaida());
        pacienteExistente.setDiasInternado(pacienteRequest.getDiasInternado());
        pacienteExistente.setValorDiario(pacienteRequest.getValorDiario());
        pacienteExistente.setValorTotal(pacienteRequest.getValorTotal());

        // 3. Regenera o PDF com os dados atualizados
        try {
            byte[] novoPdfData = gerarPdfBytes(pacienteRequest);
            pacienteExistente.setPdfData(novoPdfData);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao regenerar o PDF durante a atualização", e);
        }
        pacienteRepository.save(pacienteExistente);
    }

}

