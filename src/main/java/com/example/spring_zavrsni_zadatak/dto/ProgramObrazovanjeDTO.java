package com.example.spring_zavrsni_zadatak.dto;

import com.example.spring_zavrsni_zadatak.domain.ProgramObrazovanja;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProgramObrazovanjeDTO {
    @NotBlank(message = "Naziv programa je obavezan")
    @Size(max = 100, message = "Naziv programa može imati najviše 100 znakova")
    private String naziv;

    @NotNull(message = "Broj CSVET bodova je obavezan")
    @Min(value = 1, message = "Broj CSVET bodova mora biti najmanje 1")
    private Integer csvet;

    public ProgramObrazovanjeDTO(ProgramObrazovanja program) {
        this.naziv = program.getNaziv();
        this.csvet = program.getCsvet();
    }
}
