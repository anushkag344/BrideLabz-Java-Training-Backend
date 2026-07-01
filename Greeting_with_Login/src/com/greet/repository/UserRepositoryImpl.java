package com.greet.repository;

import com.greet.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;


@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {

            User user = new User();

            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));

            return user;
        }
    };

    @Override
    public User login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try {

            User user = jdbcTemplate.queryForObject(
                    sql,
                    userRowMapper,
                    username,
                    password
            );

            System.out.println("Login Success");

            return user;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    @Override
    public boolean register(User user) {

        if (getUserByUsername(user.getUsername()) != null) {
            return false;
        }

        String sql =
                "INSERT INTO users(username,password,role,email) VALUES(?,?,?,?)";

        int rows = jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getEmail()
        );

        return rows > 0;
    }

    @Override
    public User getUserById(int id) {

        String sql =
                "SELECT * FROM users WHERE id=?";

        try {

            return jdbcTemplate.queryForObject(
                    sql,
                    userRowMapper,
                    id
            );

        } catch (Exception e) {

            return null;

        }

    }

    @Override
    public User getUserByUsername(String username) {

        String sql =
                "SELECT * FROM users WHERE username=?";

        try {

            return jdbcTemplate.queryForObject(
                    sql,
                    userRowMapper,
                    username
            );

        } catch (Exception e) {

            return null;

        }
    }
}
