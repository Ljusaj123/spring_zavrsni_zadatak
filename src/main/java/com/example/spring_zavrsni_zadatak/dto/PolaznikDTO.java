package com.example.spring_zavrsni_zadatak.dto;

import com.example.spring_zavrsni_zadatak.domain.Polaznik;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PolaznikDTO {
    @NotBlank(message = "Ime je obavezno")
    @Size(max = 50, message = "Ime može imati najviše 50 znakova")
    private String ime;

    @NotBlank(message = "Prezime je obavezno")
    @Size(max = 50, message = "Prezime može imati najviše 50 znakova")
    private String prezime;

    public PolaznikDTO(Polaznik polaznik) {
        this.ime = polaznik.getIme();
        this.prezime = polaznik.getPrezime();
    }
}
