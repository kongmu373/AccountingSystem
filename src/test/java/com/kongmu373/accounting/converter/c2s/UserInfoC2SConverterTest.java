package com.kongmu373.accounting.converter.c2s;

import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.service.UserInfoVo;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserInfoC2SConverterTest {

    private UserInfoC2SConverter c2sConverter = new UserInfoC2SConverter();

    long userId = 100L;
    String username = "admin";
    String password = "admin";

    @Test
    void testDoForward() {
        // Arrange
        UserInfoDto userInfoDTO = UserInfoDto.builder()
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
        UserInfoVo userInfoVO = UserInfoVo.builder()
                                        .id(userId)
                                        .username(username)
                                        .password(password)
                                        .build();
        // Act
        UserInfoDto result = c2sConverter.reverse().convert(userInfoVO);
        // Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);
    }
}