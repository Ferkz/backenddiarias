package com.diarias.patient_pdf_service.controller;
import com.diarias.patient_pdf_service.dto.PacienteRequestDTO;
import com.diarias.patient_pdf_service.model.Paciente;
import com.diarias.patient_pdf_service.repository.PacienteRepository;
import com.diarias.patient_pdf_service.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<Void> generatePdf(@RequestBody PacienteRequestDTO pacienteRequest) {
        pacienteService.generateAndSavePdf(pacienteRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/lista-diaria")
    public ResponseEntity<List<PacienteRequestDTO>> getAllPdfs() {
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
    @GetMapping("/pegarid")
    public ResponseEntity<Optional<Long>> getlastPacientId(){
        try {
            Optional<Long> lastId = pacienteService.getLastPacienteId();
            System.out.println("ultimo ID encontrado: "+ lastId);
            return  ResponseEntity.ok(lastId);
        }catch (RuntimeException e){
            System.out.println("erro ao pegar ultimo ID"+ e.getMessage());
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> findByIdAndDelete(@PathVariable Long id){
        boolean deleted = pacienteService.findByIdAndDelete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    @GetMapping("/download-xlsx")
    public ResponseEntity<?> downloadXlsx(@RequestParam("competencia") String competencia) {
        try {
            byte[] xlsx = pacienteService.generateXlsx(competencia);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "pacientes_" + competencia + ".xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(xlsx);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar o arquivo XLSX.");

        }
    }

}
