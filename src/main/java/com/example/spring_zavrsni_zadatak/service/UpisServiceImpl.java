package com.example.spring_zavrsni_zadatak.service;

import com.example.spring_zavrsni_zadatak.domain.Polaznik;
import com.example.spring_zavrsni_zadatak.domain.ProgramObrazovanja;
import com.example.spring_zavrsni_zadatak.domain.Upis;
import com.example.spring_zavrsni_zadatak.dto.UpisDTO;
import com.example.spring_zavrsni_zadatak.repository.SpringDataPolaznikRepository;
import com.example.spring_zavrsni_zadatak.repository.SpringDataProgramRepository;
import com.example.spring_zavrsni_zadatak.repository.SpringDataUpisRepository;
import com.example.spring_zavrsni_zadatak.repository.UpisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpisServiceImpl implements UpisService {
    private final UpisRepository upisRepository;
    private final SpringDataUpisRepository springDataUpisRepository;
    private final SpringDataPolaznikRepository springDataPolaznikRepository;
    private final SpringDataProgramRepository springDataProgramRepository;

    @Override
    public List<UpisDTO> getAll() {
//        return upisRepository.getAll().stream().map(UpisDTO::new).toList();


        return springDataUpisRepository.findAll().stream()
                .map(UpisDTO::new)
                .toList();
    }

    @Override
    public Optional<UpisDTO> getById(Integer id) {
//        Upis upis = upisRepository.getById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Upis ne postoji"));
//        return Optional.of(new UpisDTO(upis));


        return springDataUpisRepository.findById(id).map(UpisDTO::new);
    }

    @Override
    public void saveUpis(UpisDTO upisDTO) {
//        upisRepository.saveUpis(upisDTO);

        Upis upis = convertUpisDTOToUpis(upisDTO);
        springDataUpisRepository.save(upis);
    }

    @Override
    public void updateUpis(UpisDTO upisDTO, Integer upisId) {
//        Upis upis = convertUpisDTOToUpis(upisDTO);
//
//        Optional<Upis> updatedUpis = upisRepository.updateUpis(upis, upisId);
//
//        if (updatedUpis.isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Upis sa ID-em " + upisId + " ne postoji"
//            );
//        }


        Optional<Upis> upisToUpdate = springDataUpisRepository.findById(upisId);

        if (upisToUpdate.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Upis sa ID-em " + upisId + " ne postoji"
            );
        }


        Upis upis = upisToUpdate.get();
        Polaznik polaznik =
                springDataPolaznikRepository.findByImeAndPrezime(
                        upisDTO.getPolaznik().split(" ")[0],
                        upisDTO.getPolaznik().split(" ")[1]
                ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Polaznik ne postoji"));


        ProgramObrazovanja program = springDataProgramRepository.findByNaziv(upisDTO.getNazivPrograma())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program ne postoji"));

        upis.setPolaznik(polaznik);
        upis.setProgram(program);

        springDataUpisRepository.save(upis);

    }

    @Override
    public void deleteUpisById(Integer upisId) {
//        boolean deleted = upisRepository.deleteUpis(upisId);
//
//        if (!deleted) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Upis sa ID-em " + upisId + " ne postoji"
//            );
//        }

        Optional<Upis> upis = springDataUpisRepository.findById(upisId);

        if (upis.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Upis sa ID-em " + upisId + " ne postoji"
            );
        }

        springDataUpisRepository.deleteById(upisId);

    }

    private Upis convertUpisDTOToUpis(UpisDTO upisDTO) {
        Upis upis = new Upis();

        String[] imePrezime = upisDTO.getPolaznik().trim().split("\\s+", 2);

        Polaznik polaznik =
                springDataPolaznikRepository.findByImeAndPrezime(imePrezime[0], imePrezime[1])
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Polaznik ne postoji"));


        ProgramObrazovanja program = springDataProgramRepository.findByNaziv(upisDTO.getNazivPrograma())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program ne postoji"));

        upis.setPolaznik(polaznik);
        upis.setProgram(program);

        return upis;
    }
}
