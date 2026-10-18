package com.example.spring_zavrsni_zadatak.controller;

import com.example.spring_zavrsni_zadatak.dto.PolaznikDTO;
import com.example.spring_zavrsni_zadatak.service.PolaznikService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/polaznik")
@RequiredArgsConstructor
public class PolaznikController {

    private final PolaznikService polaznikService;


    @GetMapping
    public List<PolaznikDTO> getAll() {
        return polaznikService.getAll().stream().toList();
    }

    @GetMapping("/{id}")
    public Optional<PolaznikDTO> getByName(@PathVariable Integer id) {
        return polaznikService.getById(id);
    }

    @PostMapping(produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> savePolaznik(@Valid @RequestBody PolaznikDTO polaznikDTO) {
        polaznikService.savePolaznik(polaznikDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Polaznik je uspješno kreiran");
    }

    @PutMapping(value = "/{polaznikId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> updatePolaznik(@Valid @RequestBody PolaznikDTO polaznikDTO, @PathVariable Integer polaznikId) {
        polaznikService.updatePolaznik(polaznikDTO, polaznikId);

        return ResponseEntity.ok("Polaznik je uspješno ažuriran");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{polaznikId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> deletePolaznik(@PathVariable Integer polaznikId) {
        polaznikService.deletePolaznikById(polaznikId);

        return ResponseEntity.ok("Polaznik je uspješno obrisan");
    }
}
