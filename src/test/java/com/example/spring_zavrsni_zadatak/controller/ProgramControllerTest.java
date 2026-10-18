package com.example.spring_zavrsni_zadatak.controller;

import com.example.spring_zavrsni_zadatak.dto.ProgramObrazovanjeDTO;
import com.example.spring_zavrsni_zadatak.service.ProgramService;
import com.example.spring_zavrsni_zadatak.controller.ProgramObrazovanjaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

class ProgramControllerTest {
    @Mock
    private ProgramService programService;
    private MockMvc mockMvc;

    @InjectMocks
    private ProgramObrazovanjaController programController;

    private List<ProgramObrazovanjeDTO> programList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(programController).build();

        ProgramObrazovanjeDTO programDTO1 = new ProgramObrazovanjeDTO("Programiranje u Javi", 5);
        ProgramObrazovanjeDTO programDTO2 = new ProgramObrazovanjeDTO("Programiranje u Pythonu", 4);

       programList = Arrays.asList(programDTO1, programDTO2);
    }

    @Test
    void testGetAll() throws Exception {
        when(programService.getAll())
                .thenReturn(programList);

        mockMvc.perform(get("/program"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(2)));

        verify(programService).getAll();
    }


    @Test
    void testGetByNaziv_Found() throws Exception {
        when(programService.getByNaziv("Programiranje u Javi"))
                .thenReturn(Optional.ofNullable(programList.get(0)));

        mockMvc.perform(get(
                        "/program/{naziv}",
                        "Programiranje u Javi"))
                .andExpect(status().isOk());

        verify(programService).getByNaziv("Programiranje u Javi");
    }

    @Test
    void testGetByNaziv_NotFound() throws Exception {
        when(programService.getByNaziv("Nepostojeci"))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Program ne postoji"
                ));

        mockMvc.perform(get("/program/{naziv}", "Nepostojeci"))
                .andExpect(status().isNotFound());

        verify(programService).getByNaziv("Nepostojeci");
    }

    @Test
    void testSaveProgram() throws Exception {
        String programJson = """
                {
                    "naziv": "Java",
                    "csvet": 5
                }
                """;
        mockMvc.perform(post("/program")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(programJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        "Program je uspješno kreiran"));

        verify(programService)
                .saveProgram(any(ProgramObrazovanjeDTO.class));
    }

    @Test
    void testUpdateProgram() throws Exception {
        String programJson = """
                {
                    "naziv": "Java",
                    "csvet": 5
                }
                """;
        mockMvc.perform(put("/program/{programId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(programJson))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Program je uspješno ažuriran"));

        verify(programService)
                .updateProgram(any(ProgramObrazovanjeDTO.class), eq(1));
    }

    @Test
    void testDeleteProgram() throws Exception {
        mockMvc.perform(delete("/program/{programId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Program je uspješno obrisan"));

        verify(programService).deleteProgramById(1);
    }
}