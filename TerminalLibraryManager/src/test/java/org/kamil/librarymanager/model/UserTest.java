package org.kamil.librarymanager.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {
    @Test
    void testUserBuilderAndGetters() {
        // 1. Test the Builder (This covers the Builder class logic)
        User user = User.builder()
                .id(1L)
                .username("kamil")
                .passwordHash("hashed_password")
                .role(Role.ADMIN)
                .build();

        // 2. Test Getters (Covers @Data generated getters)
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("kamil");
        assertThat(user.getPasswordHash()).isEqualTo("hashed_password");
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    void testUserSettersAndConstructors() {
        // 3. Test No-Args Constructor (Covers @NoArgsConstructor)
        User user = new User();

        // 4. Test Setters (Covers @Data generated setters)
        user.setId(10L);
        user.setUsername("testuser");
        user.setPasswordHash("new_hash");
        user.setRole(Role.USER);

        assertThat(user.getId()).isEqualTo(10L);
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getPasswordHash()).isEqualTo("new_hash");
        assertThat(user.getRole()).isEqualTo(Role.USER);

        // 5. Test All-Args Constructor (Covers @AllArgsConstructor)
        User fullUser = new User(2L, "admin", "adminhash", Role.ADMIN);
        assertThat(fullUser.getId()).isEqualTo(2L);
    }

    @Test
    void testLombokSpecialMethods() {
        // These methods are hidden but count toward line/branch coverage
        User user1 = User.builder().id(1L).username("admin").build();
        User user2 = User.builder().id(1L).username("admin").build();
        User user3 = User.builder().id(2L).username("user").build();

        // 6. Test toString() (Covers the string generation branch)
        assertThat(user1.toString()).contains("username=admin");

        // 7. Test equals() and hashCode() (Covers equality branch logic)
        assertThat(user1).isEqualTo(user2);
        assertThat(user1).isNotEqualTo(user3);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
        assertThat(user1.hashCode()).isNotEqualTo(user3.hashCode());

        // Coverage for comparing with null or different class
        assertThat(user1.equals(null)).isFalse();
        assertThat(user1.equals("not a user")).isFalse();
    }

}