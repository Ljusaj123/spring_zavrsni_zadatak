package com.example.spring_zavrsni_zadatak.repository;

import com.example.spring_zavrsni_zadatak.domain.Polaznik;
import com.example.spring_zavrsni_zadatak.domain.ProgramObrazovanja;
import com.example.spring_zavrsni_zadatak.dto.ProgramObrazovanjeDTO;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository {
    List<ProgramObrazovanja> getAll();
    Optional<ProgramObrazovanja> getByNaziv(String naziv);
    Optional<ProgramObrazovanja> getById(Integer id);

    void saveProgram(ProgramObrazovanjeDTO programDTO);

    Optional<ProgramObrazovanja> updateProgram(ProgramObrazovanja program, Integer id);

    boolean deleteProgram(Integer id);
}
