package com.example.spring_zavrsni_zadatak.repository;

import com.example.spring_zavrsni_zadatak.domain.ProgramObrazovanja;
import com.example.spring_zavrsni_zadatak.dto.ProgramObrazovanjeDTO;
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
public class JdbcProgramRepository implements ProgramRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<ProgramObrazovanja> getAll() {
        return jdbcTemplate.query("SELECT * FROM ProgramObrazovanja", new ProgramMapper());
    }

    @Override
    public Optional<ProgramObrazovanja> getByNaziv(String naziv) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("naziv", naziv);
        return jdbcTemplate.query("SELECT * FROM ProgramObrazovanja WHERE Naziv = :naziv", params, new ProgramMapper()).stream().findFirst();
    }

    @Override
    public Optional<ProgramObrazovanja> getById(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.query("SELECT * FROM ProgramObrazovanja WHERE ProgramObrazovanjaID = :id", params, new ProgramMapper()).stream().findFirst();
    }

    @Override
    public void saveProgram(ProgramObrazovanjeDTO programDTO) {
        String sql = """
                INSERT INTO ProgramObrazovanja (naziv, csvet)
                VALUES (:naziv, :csvet)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("naziv", programDTO.getNaziv())
                .addValue("csvet", programDTO.getCsvet());

        jdbcTemplate.update(sql, params);

    }

    @Override
    public Optional<ProgramObrazovanja> updateProgram(ProgramObrazovanja program, Integer id) {
        if (getById(id).isEmpty()) {
            return Optional.empty();
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("naziv", program.getNaziv())
                .addValue("csvet", program.getCsvet());

        String sql = """
            UPDATE ProgramObrazovanja
            SET naziv = :naziv, csvet = :csvet
            WHERE ProgramObrazovanjaID = :id
            """;

        jdbcTemplate.update(sql, params);

        program.setId(id);

        return Optional.of(program);
    }

    @Override
    public boolean deleteProgram(Integer id) {
        if (getById(id).isEmpty()) return false;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        String sql = "DELETE FROM ProgramObrazovanja WHERE ProgramObrazovanjaID = :id";

        jdbcTemplate.update(sql, params);
        return true;
    }

    private static class ProgramMapper implements RowMapper<ProgramObrazovanja> {

        public ProgramObrazovanja mapRow(ResultSet rs, int i) throws SQLException {

            ProgramObrazovanja newProgram = new ProgramObrazovanja();
            newProgram.setId(rs.getInt("id"));
            newProgram.setNaziv(rs.getString("naziv"));
            newProgram.setCsvet(rs.getInt("csvet"));

            return newProgram;
        }
    }
}
