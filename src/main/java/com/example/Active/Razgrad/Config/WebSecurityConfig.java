package com.example.Active.Razgrad.Config;

import com.example.Active.Razgrad.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/index","/users/**","/communities/**","/user_register","/community_register").permitAll()
                        .requestMatchers("/actors/**", "/nationality/**").hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("userNameOrEmail")
                        .defaultSuccessUrl("/index",true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .permitAll()
                        .logoutSuccessUrl("/"))
                .exceptionHandling().accessDeniedPage("/access-denied");


        return http.build();
    }

    @Bean
    public SecurityFilterChain communitySecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/activity/list", "/home", "/register").permitAll()
                        .requestMatchers("/community/details", "/events/add").authenticated()
                        .requestMatchers("/activity/edit", "/activity/delete", "/activity/add").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/community-login")
                        .usernameParameter("usernameOrEmail")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .permitAll()
                        .logoutSuccessUrl("/"))
                .exceptionHandling().accessDeniedPage("/access-denied");

        return http.build();
    }
}
