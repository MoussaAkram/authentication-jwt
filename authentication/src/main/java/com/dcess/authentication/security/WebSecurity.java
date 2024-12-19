package com.dcess.authentication.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurity {

    private final JwtConfigurationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // Configures the security filter chain for HTTP requests
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/auth/**" ).permitAll()
                        .anyRequest().authenticated())

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
