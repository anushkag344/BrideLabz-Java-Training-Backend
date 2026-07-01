package com.greet.repository;

import com.greet.model.Greeting;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class GreetingRepositoryImpl implements GreetingRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    private final RowMapper<Greeting> greetingRowMapper = new RowMapper<Greeting>() {

        @Override
        public Greeting mapRow(ResultSet rs, int rowNum) throws SQLException {

            Greeting greeting = new Greeting();

            greeting.setId(rs.getInt("id"));
            greeting.setMessage(rs.getString("message"));
            greeting.setImagePath(rs.getString("image_path"));
            greeting.setCreatedById(rs.getInt("created_by"));
            greeting.setCreatedByName(rs.getString("username"));

            return greeting;
        }
    };

    @Override
    public List<Greeting> getAllGreetings() {

        String sql =
                "SELECT g.id,g.message,g.image_path,g.created_by,u.username " +
                        "FROM greetings g " +
                        "LEFT JOIN users u ON g.created_by=u.id " +
                        "ORDER BY g.id DESC";

        return jdbcTemplate.query(sql, greetingRowMapper);
    }

    @Override
    public Greeting getGreetingById(int id) {

        String sql =
                "SELECT g.id,g.message,g.image_path,g.created_by,u.username " +
                        "FROM greetings g " +
                        "LEFT JOIN users u ON g.created_by=u.id " +
                        "WHERE g.id=?";

        try {

            return jdbcTemplate.queryForObject(
                    sql,
                    greetingRowMapper,
                    id
            );

        } catch (Exception e) {

            return null;

        }

    }

    @Override
    public boolean createGreeting(Greeting greeting) {

        String sql = "INSERT INTO greetings(message,image_path,created_by) VALUES(?,?,?)";

        int rows = jdbcTemplate.update(
                sql,
                greeting.getMessage(),
                greeting.getImagePath(),
                greeting.getCreatedById()
        );

        return rows > 0;

    }

    @Override
    public boolean updateGreeting(Greeting greeting) {

        String sql = "UPDATE greetings SET message=?, image_path=? WHERE id=?";

        int rows = jdbcTemplate.update(
                sql,
                greeting.getMessage(),
                greeting.getImagePath(),
                greeting.getId()
        );

        return rows > 0;

    }

    @Override
    public boolean deleteGreeting(int id) {

        String sql = "DELETE FROM greetings WHERE id=?";

        int rows = jdbcTemplate.update(sql, id);

        return rows > 0;

    }

}
