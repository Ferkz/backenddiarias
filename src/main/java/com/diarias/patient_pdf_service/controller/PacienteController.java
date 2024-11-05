package com.diarias.patient_pdf_service.controller;
import com.diarias.patient_pdf_service.dto.PacienteRequest;
import com.diarias.patient_pdf_service.model.Paciente;
import com.diarias.patient_pdf_service.repository.PacienteRepository;
import com.diarias.patient_pdf_service.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/diaria")
public class PacienteController {
    private final PacienteService pacienteService;
    private final PacienteRepository pacienteRepository;
    @Autowired
    public PacienteController(PacienteService pacienteService, PacienteRepository pacienteRepository) {
        this.pacienteService = pacienteService;
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping("/cria-diaria")
    public ResponseEntity<Void> generatePdf(@RequestBody PacienteRequest pacienteRequest) {
        pacienteService.generateAndSavePdf(pacienteRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/lista-diaria")
    public ResponseEntity<List<Paciente>> getAllPdfs() {
        return ResponseEntity.ok(pacienteService.getAllPdfs());
    }
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<byte[]> getPdf(@PathVariable Long id) {
        Optional<Paciente> pdfOpt = pacienteRepository.findById(id);
        if (pdfOpt.isPresent()) {
            Paciente paciente = pdfOpt.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", paciente.getNome() + ".pdf");  // Exibe o PDF inline em uma nova guia
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(paciente.getPdfData());
        }
        return ResponseEntity.notFound().build();
    }
}
