package com.example.internship.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sergey Lapshin
 */

// Обработчик логики после успешной авторизации
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//       Получаем авторизованного пользователя
        CustomerPrincipal principal = (CustomerPrincipal) authentication.getPrincipal();
//        Создаем куку с его Id
        Cookie cookie = new Cookie("customerId", principal.getUserId());
//        Устанавливаем куку
        httpServletResponse.addCookie(cookie);
//        Делаем редирект на главную страницу
        httpServletResponse.sendRedirect("/");

    }

}

