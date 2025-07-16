package com.diarias.patient_pdf_service.repository;
import java.util.List;
import java.util.Optional;

import com.diarias.patient_pdf_service.dto.PacienteRequestDTO;
import com.diarias.patient_pdf_service.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findTopByOrderByIdDesc();
    @Query(value = "SELECT p.id FROM Paciente p ORDER BY p.id DESC LIMIT 1", nativeQuery = true)
    Optional<Long> findTopId();
    List<Paciente> findByCompetenciaIgnoreCase(String competencia);

    @Query("SELECT new com.diarias.patient_pdf_service.dto.PacienteRequestDTO(p.id, p.nome, p.numeroProntuario, p.tipoAlta, p.dataEntrada, p.dataSaida, p.horaEntrada, p.horaSaida, p.diasInternado, p.valorDiario, p.valorTotal, p.createdAt, p.numeroAih, p.competencia) FROM Paciente p ORDER BY p.id DESC")
    List<PacienteRequestDTO> findAllPacientesAsDTO();


}



