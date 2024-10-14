package com.diarias.patient_pdf_service.repository;

import com.diarias.patient_pdf_service.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}



