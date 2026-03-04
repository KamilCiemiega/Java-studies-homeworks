package org.kamil.librarymanager.repository;

import org.kamil.librarymanager.config.DatabaseConfig;
import org.kamil.librarymanager.model.Role;
import org.kamil.librarymanager.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    User user = new User(
                            rs.getLong("id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            Role.valueOf(rs.getString("role"))
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getRole().name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }

}
