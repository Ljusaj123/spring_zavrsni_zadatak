package com.example.spring_zavrsni_zadatak.dto;

import com.example.spring_zavrsni_zadatak.domain.Upis;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpisDTO {

    @NotBlank(message = "Polaznik je obavezan")
    @Size(max = 101, message = "Ime i prezime mogu imati najviše 101 znak")
    private String polaznik;

    @NotBlank(message = "Naziv programa je obavezan")
    @Size(max = 100, message = "Naziv programa može imati najviše 100 znakova")
    private String nazivPrograma;

    public UpisDTO(Upis upis) {
        this.polaznik = upis.getPolaznik().getIme()
                + " " + upis.getPolaznik().getPrezime();
        this.nazivPrograma = upis.getProgram().getNaziv();
    }
}
