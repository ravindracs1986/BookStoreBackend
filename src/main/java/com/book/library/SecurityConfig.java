package com.book.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//Configuration class for security settings
@Configuration
//Annotation to enable Spring Security
@EnableWebSecurity
public class SecurityConfig {
	
	/*
	 * @Bean
	 * 
	 * public WebSecurityCustomizer ignoringCustomizer() { return (web) ->
	 * web.ignoring().requestMatchers("/book/createBook","/book/createUser",
	 * "/book/allbooks","/book/{bookname}","/book/borrowedBook","/book/returnBook",
	 * "/swagger-ui/*","/v3/api-docs","/swagger-ui.html"); }
	 */

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().permitAll();
		return http.build();
	}
	
    // Bean for password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
