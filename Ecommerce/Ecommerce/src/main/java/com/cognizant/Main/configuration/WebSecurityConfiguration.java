package com.cognizant.Main.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognizant.Main.filters.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    @Autowired
    private JwtRequestFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/authenticate", "/sign-up").permitAll()
                .requestMatchers("/api/admin/**").permitAll()
                .requestMatchers("/api/admin/orders**").permitAll()//.hasRole("ADMIN")-it should permit only ADMIN to add catrgories but for project im using permitAll()
                .requestMatchers("/api/customer/products/**").permitAll()//same for this doubt!!!!!!!!!!
                .requestMatchers("/api/customer/product/search/{title}**").permitAll()
                .requestMatchers("api/customer/cart/**").permitAll()
                .requestMatchers("/api/customer/cart/{userId}/deduct/{productId}**").permitAll()
                .requestMatchers("/api/customer/cart/{userId}/add/{productId}**").permitAll()
                .requestMatchers("/api/customer/placeOrder**").permitAll()
                .requestMatchers("/api/customer/orders/{userId}**").permitAll()
                .requestMatchers("/api/**").authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
            )
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
