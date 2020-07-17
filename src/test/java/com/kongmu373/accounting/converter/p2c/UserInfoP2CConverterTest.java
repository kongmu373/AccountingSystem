package com.kongmu373.accounting.converter.p2c;

import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.persistence.UserInfoDo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserInfoP2CConverterTest {

    private UserInfoP2CConverter p2CConverter = new UserInfoP2CConverter();

    long userId = 100L;
    String username = "admin";
    String password = "admin";
    LocalDate now = LocalDate.now();

    @Test
    void testDoForward() {
        // Arrange
        UserInfoDo userInfoDO = UserInfoDo.builder()
                                        .id(userId)
                                        .username(username)
                                        .password(password)
                                        .createTime(now)
                                        .build();
        // Act
        UserInfoDto result = p2CConverter.convert(userInfoDO);
        // Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);
    }

    @Test
    void testDoBackward() {
        // Arrange
        UserInfoDto userInfoDTO = UserInfoDto.builder()
                                          .id(userId)
                                          .username(username)
                                          .password(password)
                                          .build();
        // Act
        UserInfoDo result = p2CConverter.reverse().convert(userInfoDTO);
        // Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password)
                .hasFieldOrPropertyWithValue("createTime", null);
    }
}