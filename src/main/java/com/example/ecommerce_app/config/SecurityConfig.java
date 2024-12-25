package com.example.ecommerce_app.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.ecommerce_app.exception.CustomAccessDeniedHandler;
import com.example.ecommerce_app.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private JwtAuthenticationFilter jwtAuthFilter;

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Autowired
        private CustomAccessDeniedHandler accessDeniedHandler;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                // Disable CSRF
                                .csrf(csrf -> csrf.disable())

                                // Enable CORS
                                // .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                                // Set session management to stateless
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .authenticationProvider(authenticationProvider()) // Set authentication provider

                                // Set permissions on endpoints
                                .authorizeHttpRequests(auth -> auth
                                                // Permit access to static resources (CSS, JS, images, etc.)
                                                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico",
                                                                "scss/**")
                                                .permitAll()
                                                // Our public endpoints
                                                .requestMatchers("/", "/auth/**", "/products", "/services", "/contact",
                                                                "/about")
                                                .permitAll()
                                                // Protected routes - require authentication
                                                .requestMatchers("/cart/**", "/checkout/**", "/auth/me/**",
                                                                "/orders/**", "/logout/**")
                                                .authenticated()
                                                .requestMatchers("/api/auth/**", "/api/products/**").permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/api/user/**").hasRole("USER")
                                                // Our private endpoints
                                                .anyRequest().authenticated()) // All other requests require
                                                                               // authentication

                                // Customize the exception handling
                                .exceptionHandling(exception -> exception
                                                .accessDeniedHandler(
                                                                accessDeniedHandler)
                                                .accessDeniedPage("/auth/access-denied"))

                                // Login configuration
                                .formLogin(
                                                form -> form
                                                                .loginPage("/auth/login")
                                                                .loginProcessingUrl("/auth/login")
                                                                .defaultSuccessUrl("/auth/me")
                                                                .failureUrl("/auth/login?error")
                                                                .permitAll())

                                // Logout configuration
                                .logout(
                                                logout -> logout
                                                                .logoutRequestMatcher(new AntPathRequestMatcher(
                                                                                "/auth/logout"))
                                                                .logoutSuccessUrl("/auth/login?logout")
                                                                .clearAuthentication(true)
                                                                .deleteCookies("jwt")
                                                                .deleteCookies("JSESSIONID")
                                                                .permitAll());

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(
                                Arrays.asList("Authorization", "Content-Type", "Access-Control-Allow-Origin",
                                                "x-auth-token"));
                configuration.setExposedHeaders(Arrays.asList("Authorization", "x-auth-token"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailsService);
                authProvider.setPasswordEncoder(passwordEncoder());
                return authProvider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}