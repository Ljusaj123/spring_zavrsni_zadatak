package com.example.spring_zavrsni_zadatak.service;

import com.example.spring_zavrsni_zadatak.dto.PolaznikDTO;
import com.example.spring_zavrsni_zadatak.dto.UpisDTO;

import java.util.List;
import java.util.Optional;

public interface UpisService {
    List<UpisDTO> getAll();

    Optional<UpisDTO> getById(Integer Id);

    void saveUpis(UpisDTO upisDTO);

    void updateUpis(UpisDTO upisDTO, Integer upisId);

    void deleteUpisById(Integer upisId);
}
