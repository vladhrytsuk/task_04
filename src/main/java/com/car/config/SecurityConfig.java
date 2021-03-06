package com.car.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                    .antMatchers("/resources/**", "/**").permitAll()
                    .antMatchers("/" ,"/registration", "/makeRegistration").permitAll()
                    .antMatchers("/mechanic*").access("hasRole('ROLE_MECHANIC') and hasRole('ROLE_USER')")
                    .antMatchers("/user*").access("hasRole('ROLE_USER')")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .permitAll()
                    .loginPage("/authorization")
                    .loginProcessingUrl("/j_spring_security_check")
                    .defaultSuccessUrl("/login")
                    .failureUrl("/authorization?error=true")
                    .usernameParameter("f_username").passwordParameter("f_password")
                    .and()
                .logout()
                    .permitAll()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/authorization?logout")
                    .invalidateHttpSession(true)
                    .and()
                .exceptionHandling().accessDeniedPage("/403")
                    .and()
                .csrf().disable();
    }
}
