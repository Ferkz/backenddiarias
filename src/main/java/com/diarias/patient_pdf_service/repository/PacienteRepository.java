package com.diarias.patient_pdf_service.repository;
import java.util.List;
import java.util.Optional;
import com.diarias.patient_pdf_service.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findTopByOrderByIdDesc();
    @Query(value = "SELECT p.id FROM Paciente p ORDER BY p.id DESC LIMIT 1", nativeQuery = true)
    Optional<Long> findTopId();
    List<Paciente> findByCompetenciaIgnoreCase(String competencia);


}



