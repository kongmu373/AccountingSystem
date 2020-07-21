package com.kongmu373.accounting.config;

import static com.kongmu373.accounting.exception.BizErrorCode.NO_AUTHORIZED;

import com.kongmu373.accounting.exception.ErrorResponse;
import com.kongmu373.accounting.exception.ServiceException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        val errorResponse = ErrorResponse.builder()
                                .code(NO_AUTHORIZED)
                                .errorType(ServiceException.ErrorType.Client)
                                .statusCode(HttpStatus.UNAUTHORIZED.value())
                                .message("No access for related url")
                                .build();
        try (val writer = response.getWriter()) {
            writer.write(new ObjectMapper().writeValueAsString(errorResponse));
        } catch (IOException ex) {
            log.error("The IO exception is thrown");
        }
    }
}
