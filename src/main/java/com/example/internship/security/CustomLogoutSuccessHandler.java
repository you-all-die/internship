package com.example.internship.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Sergey Lapshin
 */

// Хэндлер для обработки логики после успешной авторизации
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//         Создаем пустую куку идентификатора пользователя
        Cookie cookie = new Cookie("customerId", "");
//        Устанавливаем время ее жизни в 0
        cookie.setMaxAge(0);
//        Добавляем ee в response
        httpServletResponse.addCookie(cookie);
//        Переходим на главную страницу
        httpServletResponse.sendRedirect("/");
    }
}

