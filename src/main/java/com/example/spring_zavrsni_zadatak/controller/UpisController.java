package com.example.spring_zavrsni_zadatak.controller;

import com.example.spring_zavrsni_zadatak.dto.UpisDTO;
import com.example.spring_zavrsni_zadatak.service.UpisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/upis")
@RequiredArgsConstructor
public class UpisController {

    private final UpisService upisService;

    @GetMapping
    public List<UpisDTO> getAll() {
        return upisService.getAll().stream().toList();
    }

    @GetMapping("/{id}")
    public Optional<UpisDTO> getById(@PathVariable Integer id) {
        return upisService.getById(id);
    }

    @PostMapping(produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> saveUpis(@Valid @RequestBody UpisDTO upisDTO) {
        upisService.saveUpis(upisDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Upis je uspješno kreiran");
    }

    @PutMapping(value = "/{upisId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> updateUpis(@Valid @RequestBody UpisDTO upisDTO, @PathVariable Integer upisId) {
        upisService.updateUpis(upisDTO, upisId);

        return ResponseEntity.ok("Upis je uspješno ažuriran");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{upisId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> deleteUpis(@PathVariable Integer upisId) {
        upisService.deleteUpisById(upisId);

        return ResponseEntity.ok("Upis je uspješno obrisan");
    }
}
