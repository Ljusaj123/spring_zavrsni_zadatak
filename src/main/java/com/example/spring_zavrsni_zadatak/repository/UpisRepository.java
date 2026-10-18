package com.example.spring_zavrsni_zadatak.repository;

import com.example.spring_zavrsni_zadatak.domain.ProgramObrazovanja;
import com.example.spring_zavrsni_zadatak.domain.Upis;
import com.example.spring_zavrsni_zadatak.dto.UpisDTO;

import java.util.List;
import java.util.Optional;

public interface UpisRepository {
    List<Upis> getAll();
    Optional<Upis> getById(Integer id);

    void saveUpis(UpisDTO upisDTO);

    Optional<Upis> updateUpis(Upis upis, Integer id);

    boolean deleteUpis(Integer id);
}
