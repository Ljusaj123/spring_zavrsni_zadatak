package com.example.spring_zavrsni_zadatak.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table()
public class Upis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_polaznik")
    private Polaznik polaznik;

    @ManyToOne
    @JoinColumn(name = "id_program_obrazovanja")
    private ProgramObrazovanja program;
}
