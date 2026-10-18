package com.example.spring_zavrsni_zadatak.service;

import com.example.spring_zavrsni_zadatak.domain.Polaznik;
import com.example.spring_zavrsni_zadatak.dto.PolaznikDTO;
import com.example.spring_zavrsni_zadatak.repository.PolaznikRepository;
import com.example.spring_zavrsni_zadatak.repository.SpringDataPolaznikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PolaznikServiceImpl implements PolaznikService {

    private final PolaznikRepository polaznikRepository;
    private final SpringDataPolaznikRepository springDataPolaznikRepository;

    @Override
    public List<PolaznikDTO> getAll() {
//     return polaznikRepository.getAll().stream().map(PolaznikDTO::new).toList();

        return springDataPolaznikRepository.findAll().stream()
                .map(PolaznikDTO::new)
                .toList();
    }

    @Override
    public Optional<PolaznikDTO> getById(Integer id) {
//        Polaznik polaznik = polaznikRepository.getById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Polaznik ne postoji"));
//        return Optional.of(new PolaznikDTO(polaznik));


        return springDataPolaznikRepository.findById(id).map(PolaznikDTO::new);
    }

    @Override
    public void savePolaznik(PolaznikDTO polaznikDTO) {
//                polaznikRepository.savePolaznik(polaznikDTO);

        Polaznik polaznik = convertPolaznikDtoToPolaznik(polaznikDTO);
        springDataPolaznikRepository.save(polaznik);

    }

    @Override
    public void updatePolaznik(PolaznikDTO polaznikDTO, Integer polaznikId) {
//        Polaznik polaznik = convertPolaznikDtoToPolaznik(polaznikDTO);
//
//        Optional<Polaznik> updatedPolaznik =
//                polaznikRepository.updatePolaznik(polaznik, polaznikId);
//
//        if (updatedPolaznik.isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Polaznik sa ID-em " + polaznikId + " ne postoji"
//            );
//        }


        Optional<Polaznik> polaznikToUpdate = springDataPolaznikRepository.findById(polaznikId);

        if (polaznikToUpdate.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Polaznik sa ID-em " + polaznikId + " ne postoji"
            );
        }


        Polaznik polaznik = polaznikToUpdate.get();
        polaznik.setIme(polaznikDTO.getIme());
        polaznik.setPrezime(polaznikDTO.getPrezime());

        springDataPolaznikRepository.save(polaznik);

    }

    @Override
    public void deletePolaznikById(Integer polaznikId) {

//        boolean deleted = polaznikRepository.deletePolaznik(polaznikId);
//
//        if (!deleted) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Polaznik sa ID-em " + polaznikId + " ne postoji"
//            );
//        }

        Optional<Polaznik> polaznik = springDataPolaznikRepository.findById(polaznikId);

        if (polaznik.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Polaznik sa ID-em " + polaznikId + " ne postoji"
            );
        }

        springDataPolaznikRepository.deleteById(polaznikId);

    }

    private Polaznik convertPolaznikDtoToPolaznik(PolaznikDTO polaznikDTO) {
        Polaznik polaznik = new Polaznik();

        polaznik.setIme(polaznikDTO.getIme());
        polaznik.setPrezime(polaznikDTO.getPrezime());

        return polaznik;

    }
}
