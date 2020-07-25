package com.kongmu373.accounting.config;

import static com.kongmu373.accounting.service.impl.UserInfoServiceImpl.HASH_ITERATIONS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.service.UserInfoService;

import lombok.val;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRealmTest {

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private HashedCredentialsMatcher matcher;

    private UserRealm userRealm;

    @BeforeEach
    void setUp() {
        userRealm = new UserRealm(userInfoService, matcher);
    }

    @AfterEach
    void tearDown() {
        reset(userInfoService);
        reset(matcher);
    }

    @Test
    void testDoGetAuthorizationInfo() {
        // Arrange
        val principal = new SimplePrincipalCollection();
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> userRealm.doGetAuthorizationInfo(principal));
    }

    @Test
    void testDoGetAuthenticationInfo() {
        // Arrange
        val username = "test";
        val password = "test";
        val token = new UsernamePasswordToken(username, password);
        //simulate a user account with a SHA-1 hashed and salted password:
        val salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
        val encodePassword = new Sha256Hash(password, salt, HASH_ITERATIONS).toBase64();

        val userInfo = UserInfoDto.builder()
                           .id(1L)
                           .username(username)
                           .password(encodePassword)
                           .salt(salt)
                           .build();
        when(userInfoService.getUserInfoByUserName(username)).thenReturn(userInfo);
        // Act
        val result = userRealm.doGetAuthenticationInfo(token);
        // Assert
        assertThat(result).isNotNull();
    }
}
