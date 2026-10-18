package com.example.spring_zavrsni_zadatak.service;

import com.example.spring_zavrsni_zadatak.dto.ProgramObrazovanjeDTO;

import java.util.List;
import java.util.Optional;

public interface ProgramService {
    List<ProgramObrazovanjeDTO> getAll();

    Optional<ProgramObrazovanjeDTO> getByNaziv(String naziv);

    void saveProgram(ProgramObrazovanjeDTO programDTO);

    void updateProgram(ProgramObrazovanjeDTO programDTO, Integer programId);

    void deleteProgramById(Integer programId);
}
