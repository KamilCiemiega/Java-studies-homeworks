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
        String sql = "SELECT id, username, password_hash, role FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Mapping DB row to User object
                    User user = User.builder()
                            .id(rs.getLong("id"))
                            .username(rs.getString("username"))
                            .passwordHash(rs.getString("password_hash"))
                            .role(Role.valueOf(rs.getString("role").toUpperCase()))
                            .build();

                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            // This is the unreachable line during successful H2 tests
            System.err.println("Database error: " + e.getMessage());
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
