package com.example.spring_zavrsni_zadatak.repository;

import com.example.spring_zavrsni_zadatak.domain.ProgramObrazovanja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataProgramRepository extends JpaRepository<ProgramObrazovanja, Integer> {
    Optional<ProgramObrazovanja> findByNaziv(String naziv);
}
