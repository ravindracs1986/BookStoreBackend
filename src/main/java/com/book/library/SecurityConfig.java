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
	
	@Bean
	
	public WebSecurityCustomizer ignoringCustomizer() {
	    return (web) -> web.ignoring().requestMatchers("/book/createBook","/book/createUser","/book/allbooks","/book/{bookname}","/book/borrowedBook","/book/returnBook");
	}

    // Bean for password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
