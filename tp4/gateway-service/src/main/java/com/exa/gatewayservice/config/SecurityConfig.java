package com.exa.gatewayservice.config;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        UserDetails user =
//                User.builder()
//                        .username("user")
//                        .password(encoder.encode("password"))
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("Configuring security filter chain");
        http
                .securityMatcher("/**")
                .authorizeHttpRequests((requests) -> requests
                        //.requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        //.requestMatchers("/users/**").permitAll()
                        //.requestMatchers("/users/username/{username}").permitAll()
                        //.requestMatchers("/users/**").hasAuthority(RoleEnum.CUSTOMER.name())
                        .anyRequest().permitAll()
                );
        return http.build();
    }


}
