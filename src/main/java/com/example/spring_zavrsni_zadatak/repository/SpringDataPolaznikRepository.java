package com.example.spring_zavrsni_zadatak.repository;

import com.example.spring_zavrsni_zadatak.domain.Polaznik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataPolaznikRepository extends JpaRepository<Polaznik, Integer> {
    Optional<Polaznik> findByImeAndPrezime(String ime, String prezime);
}
