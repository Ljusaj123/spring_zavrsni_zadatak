package com.example.spring_zavrsni_zadatak.repository;

import com.example.spring_zavrsni_zadatak.domain.Polaznik;
import com.example.spring_zavrsni_zadatak.domain.ProgramObrazovanja;
import com.example.spring_zavrsni_zadatak.domain.Upis;
import com.example.spring_zavrsni_zadatak.dto.UpisDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUpisRepository implements UpisRepository{

    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<Upis> getAll() {
        return jdbcTemplate.query("SELECT * FROM Upis", new UpisMapper());
    }

    @Override
    public Optional<Upis> getById(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.query("SELECT * FROM Upis WHERE UpisID = :id", params, new UpisMapper()).stream().findFirst();
    }

    @Override
    public void saveUpis(UpisDTO upisDTO) {
        String[] imePrezime = upisDTO.getPolaznik().split(" ", 2);

        MapSqlParameterSource polaznikParams = new MapSqlParameterSource()
                .addValue("ime", imePrezime[0])
                .addValue("prezime", imePrezime[1]);


        String sqlPolaznik = """
            SELECT PolaznikID
            FROM Polaznik
            WHERE ime = :ime AND prezime = :prezime
            """;

        Long polaznikId = jdbcTemplate.queryForObject(
                sqlPolaznik, polaznikParams, Long.class);

        MapSqlParameterSource programParams = new MapSqlParameterSource()
                .addValue("naziv", upisDTO.getNazivPrograma());

        String sqlProgram = """
            SELECT IDProgramObrazovanja
            FROM ProgramObrazovanja
            WHERE naziv = :naziv
            """;

        Long programId = jdbcTemplate.queryForObject(
                sqlProgram, programParams, Long.class);


        String sql = """
            INSERT INTO Upis (IDPolaznik, IDProgramObrazovanja)
            VALUES (:idPolaznik, :idProgramObrazovanja)
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idPolaznik", polaznikId)
                .addValue("idProgramObrazovanja", programId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Upis> updateUpis(Upis upis, Integer id) {
        if (getById(id).isEmpty()) {
            return Optional.empty();
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("IDPolaznik", upis.getPolaznik().getId())
                .addValue("IDProgramObrazovanja", upis.getProgram().getId());

        String sql = """
            UPDATE Upis
            SET IDPolaznik = :IDPolaznik, IDProgramObrazovanja = :IDProgramObrazovanja
            WHERE upisID = :id
            """;

        jdbcTemplate.update(sql, params);

        upis.setId(id);

        return Optional.of(upis);
    }

    @Override
    public boolean deleteUpis(Integer id) {
        if (getById(id).isEmpty()) return false;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        String sql = "DELETE FROM Upis WHERE UpisID = :id";

        jdbcTemplate.update(sql, params);
        return true;
    }


    private static class UpisMapper implements RowMapper<Upis> {

        public Upis mapRow(ResultSet rs, int i) throws SQLException {

            Upis newUpis = new Upis();

            newUpis.setId(rs.getInt("id"));

            Polaznik polaznik = new Polaznik();
            polaznik.setId(rs.getInt("IDPolaznik"));
            newUpis.setPolaznik(polaznik);

            ProgramObrazovanja program = new ProgramObrazovanja();
            program.setId(rs.getInt("IDProgramObrazovanja"));
            newUpis.setProgram(program);

            return newUpis;
        }
    }
}
