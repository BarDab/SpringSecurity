package com.bardab.security.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.bardab.security.security.ApplicationUserPermission.COURSE_WRITE;
import static com.bardab.security.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter {



    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ApplicationSecurityConfig (PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index,","/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }


    @Override
    @Bean
    //ideally retrieving user from db
    protected UserDetailsService userDetailsService() {
        UserDetails frodoUser = User.builder()
        .username("frodo")
        .password(passwordEncoder.encode( "password"))
                .authorities(STUDENT.getGrantedAuthorities())
//        .roles("STUDENT")
                .build();

        UserDetails lindaUser = User.builder()
                .username("Linda")
                .password(passwordEncoder.encode( "password123"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails markUser = User.builder()
                .username("Mark")
                .password(passwordEncoder.encode( "password123"))
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
//                .roles(ADMINTRAINEE.name())
                .build();


        return new InMemoryUserDetailsManager(
                frodoUser,
                lindaUser,
                markUser
        );
    }
}
