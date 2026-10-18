package com.example.spring_zavrsni_zadatak.service;

import com.example.spring_zavrsni_zadatak.dto.PolaznikDTO;

import java.util.List;
import java.util.Optional;

public interface PolaznikService {
    List<PolaznikDTO> getAll();

    Optional<PolaznikDTO> getById(Integer Id);

    void savePolaznik(PolaznikDTO polaznikDTO);

    void updatePolaznik(PolaznikDTO polaznikDTO, Integer polaznikId);

    void deletePolaznikById(Integer polaznikId);
}
