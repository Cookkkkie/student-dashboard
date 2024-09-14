package com.example.main.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/auth/login", "/auth/register").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
//                                .loginPage("/auth/login")
                                .defaultSuccessUrl("/dashboard", true)
                                .failureUrl("/auth/login?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/logout")
                                .permitAll()
                )
                .build();
    }
}


