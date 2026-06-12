package com.ttknp.understandspringbootactuator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user =
                User.withUsername("user")
                        .password("{noop}12345")
                        .roles("USER")
                        .build();
        UserDetails admin =
                User.withUsername("admin")
                        .password("{noop}12345")
                        .roles("ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .formLogin().disable() // If you don't want a testing with html default form login
                .httpBasic(Customizer.withDefaults()) // httpBasic() enable it. If you want to test api with http header , it's easier when do unit testing ex, on header *** Authorization : Basic YWRtaW46MTIzNDU=
                .authorizeHttpRequests(authorize -> authorize
                        // Note. if permitAll() or hasAnyRole(...) , ... didn't work
                        // Maybe you set server.servlet.context-path="" on properties file *** use  @RequestMapping("") instead
                        .requestMatchers(HttpMethod.GET,"/api/server").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/robot").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.GET,"/api/admin/info_application").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/admin/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                );
        return httpSecurity.build();
    }
}