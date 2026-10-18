package com.example.spring_zavrsni_zadatak.controller;

import com.example.spring_zavrsni_zadatak.dto.ProgramObrazovanjeDTO;
import com.example.spring_zavrsni_zadatak.service.ProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/program")
@RequiredArgsConstructor
public class ProgramObrazovanjaController {

    private final ProgramService programService;

    @GetMapping
    public List<ProgramObrazovanjeDTO> getAll() {
        return programService.getAll().stream().toList();
    }

    @GetMapping("/{naziv}")
    public Optional<ProgramObrazovanjeDTO> getByNaziv(@PathVariable String naziv) {
        return programService.getByNaziv(naziv);
    }

    @PostMapping(produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> saveProgram(@Valid @RequestBody ProgramObrazovanjeDTO programObrazovanjeDTO) {
        programService.saveProgram(programObrazovanjeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Program je uspješno kreiran");
    }

    @PutMapping(value = "/{programId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> updateProgram(@Valid @RequestBody ProgramObrazovanjeDTO programObrazovanjeDTO, @PathVariable Integer programId) {
        programService.updateProgram(programObrazovanjeDTO, programId);

        return ResponseEntity.ok("Program je uspješno ažuriran");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{programId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> deleteProgram(@PathVariable Integer programId) {
        programService.deleteProgramById(programId);

        return ResponseEntity.ok("Program je uspješno obrisan");
    }
}
