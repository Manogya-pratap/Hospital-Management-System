package com.vgnit.medical.security_configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vgnit.medical.service.CustomSuccessHandler;
import com.vgnit.medical.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
            .authorizeHttpRequests(request -> request
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/doctor/**").hasAnyAuthority("DOCTOR", "ADMIN")
                .requestMatchers("/patient/**").hasAnyAuthority("PATIENT", "ADMIN")
                .requestMatchers("/staff/**").hasAnyAuthority("STAFF", "ADMIN")
                .requestMatchers("/nurse/**").hasAnyAuthority("NURSE", "ADMIN")
                .requestMatchers("/accounts/**").hasAnyAuthority("ACCOUNTS", "ADMIN")
                .requestMatchers("/hr/**").hasAnyAuthority("HR", "ADMIN")
                .requestMatchers("/registration").permitAll()
                .requestMatchers("/resources/**", "/static/**", "/css/**", "/assets/**",
                        "/js/**", "/img/**", "/icon/**", "/fonts/**", "/plugins/**",
                        "/doctorimages/**").permitAll()
                .anyRequest().authenticated())

            .formLogin(form -> form.loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(customSuccessHandler).permitAll())

            .logout(form -> form.invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll());

        return http.build();
    }

    @Bean
     public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configur(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}