package com.rachid.Crud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
public class SecConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private  DataSource dataSource;

    @Autowired
    private  UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        PasswordEncoder passwordEncoder = passwordEncoder();
//        System.out.println(passwordEncoder.encode("123"));
//        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("123")).roles("USER");
//        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("123")).roles("USER","ADMIN");

//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
//                .authoritiesByUsernameQuery("select u.username as principal, r.role_name as role from " +
//                        "users u,roles r, users_roles ur " +
//                        "where u.username=? " +
//                        "and u.id=ur.users_id " +
//                        "and r.id=ur.roles_id ")
//                .passwordEncoder(passwordEncoder)
//                .rolePrefix("ROLE_");

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    //toute les resource sans securiser
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login");
        http.authorizeRequests().antMatchers("/login", "/register/**","/css**/**","/resources**/**").permitAll();
        http.authorizeRequests().antMatchers("/new**/**","/edit**/**","/delete**/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        http.exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
