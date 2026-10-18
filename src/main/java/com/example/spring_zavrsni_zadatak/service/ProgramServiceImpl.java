package com.example.spring_zavrsni_zadatak.service;

import com.example.spring_zavrsni_zadatak.domain.ProgramObrazovanja;
import com.example.spring_zavrsni_zadatak.dto.ProgramObrazovanjeDTO;
import com.example.spring_zavrsni_zadatak.repository.ProgramRepository;
import com.example.spring_zavrsni_zadatak.repository.SpringDataProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final SpringDataProgramRepository springDataProgramRepository;

    @Override
    public List<ProgramObrazovanjeDTO> getAll() {
//     return programRepository.getAll().stream().map(ProgramObrazovanjeDTO::new).toList();

        return springDataProgramRepository.findAll().stream()
                .map(ProgramObrazovanjeDTO::new)
                .toList();
    }

    @Override
    public Optional<ProgramObrazovanjeDTO> getByNaziv(String naziv) {
//        ProgramObrazovanja program = programRepository.getByNaziv(naziv)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program ne postoji"));
//        return Optional.of(new ProgramObrazovanjeDTO(program));


        return springDataProgramRepository.findByNaziv(naziv).map(ProgramObrazovanjeDTO::new);
    }

    @Override
    public void saveProgram(ProgramObrazovanjeDTO programDTO) {
//        programRepository.saveProgram(programDTO);

        ProgramObrazovanja program = convertProgramDtoToProgram(programDTO);
        springDataProgramRepository.save(program);

    }

    @Override
    public void updateProgram(ProgramObrazovanjeDTO programDTO, Integer programId) {
//        ProgramObrazovanja program = convertProgramDtoToProgram(programDTO);
//
//        Optional<ProgramObrazovanja> updatedProgram =
//                programRepository.updateProgram(program, programId);
//
//        if (updatedProgram.isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Program sa ID-em " + programId + " ne postoji"
//            );
//        }


        Optional<ProgramObrazovanja> programToUpdate = springDataProgramRepository.findById(programId);

        if (programToUpdate.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Program sa ID-em " + programId + " ne postoji"
            );
        }


        ProgramObrazovanja program = programToUpdate.get();
        program.setNaziv(programDTO.getNaziv());
        program.setCsvet(programDTO.getCsvet());

        springDataProgramRepository.save(program);

    }

    @Override
    public void deleteProgramById(Integer programId) {

//        boolean deleted = programRepository.deleteProgram(programId);
//
//        if (!deleted) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Program sa ID-em " + programId + " ne postoji"
//            );
//        }

        Optional<ProgramObrazovanja> program = springDataProgramRepository.findById(programId);

        if (program.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Program sa ID-em " + programId + " ne postoji"
            );
        }

        springDataProgramRepository.deleteById(programId);

    }

    private ProgramObrazovanja convertProgramDtoToProgram(ProgramObrazovanjeDTO programDTO) {
        ProgramObrazovanja program = new ProgramObrazovanja();

        program.setNaziv(programDTO.getNaziv());
        program.setCsvet(programDTO.getCsvet());

        return program;

    }
}
