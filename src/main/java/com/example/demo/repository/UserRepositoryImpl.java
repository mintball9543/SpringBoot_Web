package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{
    private final JdbcTemplate jdbcTemplate;
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        return user;
    };

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, userRowMapper);

        return users;
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = jdbcTemplate.queryForObject(sql, userRowMapper, id);
        return user;
    }

    @Override
    public boolean existsByEmail(String email) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ?",
                Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection ->{
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users(name, email) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());

        return user;
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update(
                "UPDATE users SET name = ?, email = ? WHERE id = ?",
                user.getName(), user.getEmail(), user.getId()
        );
        return user;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    @Override
    public List<User> searchUserByName(String name) {
        String sql = "SELECT * FROM users WHERE name LIKE ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, "%"+name+"%");
        return users;
    }

    @Override
    public Integer updateEmail(Long id, String email) {
        Integer result = jdbcTemplate.update(
                "UPDATE users SET email = ? WHERE id = ?",
                email, id
        );
        return result;
    }
}
