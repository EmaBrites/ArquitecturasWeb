package com.exa.gatewayservice.config;

import com.exa.gatewayservice.entity.AuthUser;
import com.exa.gatewayservice.repository.AuthUserRepository;
import com.exa.gatewayservice.security.AuthorityConstant;
import com.exa.gatewayservice.security.JwtFilter;
import com.exa.gatewayservice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private AuthUserRepository authUserRepository;

    public SecurityConfig(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Swagger público
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // Auth público
                        .requestMatchers("/auth/**").permitAll()

                        // Reglas Accounts
                        .requestMatchers("/accounts/**").hasAnyAuthority(AuthorityConstant.ADMIN.name(),AuthorityConstant.USER.name())

                        // Reglas Reports
                        .requestMatchers("/reports/**").hasAuthority(AuthorityConstant.ADMIN.name())

                        // Reglas Scooters
                        .requestMatchers("/scooters/**").hasAuthority(AuthorityConstant.USER.name())

                        // Reglas Stops
                        .requestMatchers("/stops/**").hasAuthority(AuthorityConstant.USER.name())

                        // Reglas Trips
                        .requestMatchers("/trips/**").hasAuthority(AuthorityConstant.USER.name())

                        // Reglas Users
                        .requestMatchers("/users/**").hasAuthority(AuthorityConstant.USER.name())
                        .anyRequest().authenticated()


                )
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(this.authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailService(){
        return username -> {
            AuthUser user = authUserRepository.findUserEntityByUsername(username);
            if (user ==null){
                throw new UsernameNotFoundException("AuthUser not found");
            }
            return user;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public JwtFilter jwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        return new JwtFilter(jwtService, userDetailsService);
    }
}
