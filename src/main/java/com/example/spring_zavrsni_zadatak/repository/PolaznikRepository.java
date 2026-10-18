package com.example.spring_zavrsni_zadatak.repository;

import com.example.spring_zavrsni_zadatak.domain.Polaznik;
import com.example.spring_zavrsni_zadatak.dto.PolaznikDTO;

import java.util.List;
import java.util.Optional;

public interface PolaznikRepository {
    List<Polaznik> getAll();
    Optional<Polaznik> getById(Integer id);
    void savePolaznik(PolaznikDTO polaznikDTO);

    Optional<Polaznik> updatePolaznik(Polaznik polaznik, Integer id);

    boolean deletePolaznik(Integer id);
}
