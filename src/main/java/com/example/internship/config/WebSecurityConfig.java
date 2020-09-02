package com.example.internship.config;

import com.example.internship.security.CustomAuthenticationSuccessHandler;
import com.example.internship.security.CustomLogoutSuccessHandler;
import com.example.internship.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler = new CustomAuthenticationSuccessHandler();

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler = new CustomLogoutSuccessHandler();

//    @Autowired
//    private RdirectToOriginalUrlAuthenticationSuccessHandler rdirectToOriginalUrlAuthenticationSuccessHandler;

    /*
	 	Кодирование пароля пользователей - с помощью BCrypt.
	 	Алгоритм BCrypt, генерирует случайную соль, для каждого пароля и хранит соль внутри самого хеш-значения.
	 	BCrypt генерирует строку длиной 60.
	*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
//                    Устанавливаем хэндлер для обработки логики после успешной авторизации
                .successHandler(customAuthenticationSuccessHandler)
                .and()
//                Если явно не указано, при logout переходим не страницу login
                .logout()
//                    Устанавливаем хэндлер для обработки логики после успешной авторизации
                .logoutSuccessHandler(customLogoutSuccessHandler)
//                    После logout удаляем куку с сессией
                .deleteCookies("JSESSIONID");

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}