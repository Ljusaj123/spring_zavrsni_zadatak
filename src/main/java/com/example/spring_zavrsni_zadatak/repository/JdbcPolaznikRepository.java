package com.example.spring_zavrsni_zadatak.repository;

import com.example.spring_zavrsni_zadatak.domain.Polaznik;
import com.example.spring_zavrsni_zadatak.dto.PolaznikDTO;
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
public class JdbcPolaznikRepository implements PolaznikRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Polaznik> getAll() {
        return jdbcTemplate.query("SELECT * FROM Polaznik", new PolaznikMapper());
    }

    @Override
    public Optional<Polaznik> getById(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.query("SELECT * FROM Polaznik WHERE PolaznikID = :id", params, new PolaznikMapper()).stream().findFirst();
    }


    @Override
    public void savePolaznik(PolaznikDTO polaznikDTO) {
        String sql = """
                INSERT INTO Polaznik (ime, prezime)
                VALUES (:ime, :prezime)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("ime", polaznikDTO.getIme())
                .addValue("prezime", polaznikDTO.getPrezime());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Polaznik> updatePolaznik(Polaznik polaznik, Integer id) {
        if (getById(id).isEmpty()) {
            return Optional.empty();
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("ime", polaznik.getIme())
                .addValue("prezime", polaznik.getPrezime());

        String sql = """
            UPDATE Polaznik
            SET ime = :ime, prezime = :prezime
            WHERE PolaznikID = :id
            """;

        jdbcTemplate.update(sql, params);

        polaznik.setId(id);

        return Optional.of(polaznik);
    }

    @Override
    public boolean deletePolaznik(Integer id) {
        if (getById(id).isEmpty()) return false;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        String sql = "DELETE FROM Polaznik WHERE PolaznikID = :id";

        jdbcTemplate.update(sql, params);
        return true;
    }

    private static class PolaznikMapper implements RowMapper<Polaznik> {

        public Polaznik mapRow(ResultSet rs, int i) throws SQLException {

            Polaznik newPolaznik = new Polaznik();
            newPolaznik.setId(rs.getInt("id"));
            newPolaznik.setIme(rs.getString("ime"));
            newPolaznik.setPrezime(rs.getString("prezime"));

            return newPolaznik;
        }
    }
}
