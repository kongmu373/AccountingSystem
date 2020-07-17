package com.kongmu373.accounting.converter.p2c;

import com.kongmu373.accounting.model.common.UserInfoDTO;
import com.kongmu373.accounting.model.persistence.UserInfoDO;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class UserInfoP2CConverterTest {

    private UserInfoP2CConverter p2CConverter = new UserInfoP2CConverter();

    long userId = 100L;
    String username = "admin";
    String password = "admin";
    Instant now = Instant.now();

    @Test
    void testDoForward() {
        // Arrange
        UserInfoDO userInfoDO = UserInfoDO.builder()
                                        .id(userId)
                                        .username(username)
                                        .password(password)
                                        .create_time(now)
                                        .build();
        // Act
        UserInfoDTO result = p2CConverter.convert(userInfoDO);
        // Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);
    }

    @Test
    void testDoBackward() {
        // Arrange
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                                          .id(userId)
                                          .username(username)
                                          .password(password)
                                          .build();
        // Act
        UserInfoDO result = p2CConverter.reverse().convert(userInfoDTO);
        // Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password)
                .hasFieldOrPropertyWithValue("create_time", null);
    }
}