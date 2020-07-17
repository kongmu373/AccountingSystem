package com.kongmu373.accounting.converter.c2s;

import com.kongmu373.accounting.model.common.UserInfoDTO;
import com.kongmu373.accounting.model.service.UserInfoVO;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class UserInfoC2SConverterTest {

    private UserInfoC2SConverter c2sConverter = new UserInfoC2SConverter();

    long userId = 100L;
    String username = "admin";
    String password = "admin";
    Instant now = Instant.now();

    @Test
    void testDoForward() {
        // Arrange
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                                          .id(userId)
                                          .username(username)
                                          .password(password)
                                          .build();
        // Act
        val result = c2sConverter.convert(userInfoDTO);
        // Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username);
    }

    @Test
    void testDoBackward() {
        // Arrange
        UserInfoVO userInfoVO = UserInfoVO.builder()
                                        .id(userId)
                                        .username(username)
                                        .password(password)
                                        .build();
        // Act
        UserInfoDTO result = c2sConverter.reverse().convert(userInfoVO);
        // Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);
    }
}