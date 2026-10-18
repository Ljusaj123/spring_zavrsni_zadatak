package com.example.spring_zavrsni_zadatak.repository;

import com.example.spring_zavrsni_zadatak.domain.Upis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUpisRepository extends JpaRepository<Upis, Integer> {
}
