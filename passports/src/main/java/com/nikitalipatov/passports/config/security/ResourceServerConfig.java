//package com.nikitalipatov.passports.config.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//public class ResourceServerConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.
//                authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/passport/**").hasRole("ROLE_ADMIN")
//                        .anyRequest()
//                        .authenticated()
//                )
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
////                .httpBasic().disable()
////                .csrf().disable()
////                .formLogin().disable()
////                .logout().disable();
//        return http.build();
//    }
//}