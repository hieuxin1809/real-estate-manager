package com.javaweb.config;

import com.javaweb.security.CustomSuccessHandler;
import com.javaweb.service.impl.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        // check username va role
        return new CustomUserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // check password
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable()
                .authorizeRequests()
                        .antMatchers(HttpMethod.POST, "/api/assingments").hasRole("MANAGER")
                        .antMatchers(HttpMethod.DELETE, "/api/buildings/{ids}").hasRole("MANAGER")
                        .antMatchers("/admin/user-list" , "/admin/user-edit","/admin/user-edit-{id}").hasRole("MANAGER")
                        .antMatchers("/admin/building-edit","/admin/building-edit-{id}").hasAnyRole("MANAGER" , "STAFF")
                        .antMatchers("/admin/**").hasAnyRole("MANAGER","STAFF") // dùng để khai báo những url cần phân quyền
                        .antMatchers("/login", "/resource/**", "/trang-chu", "/api/**").permitAll() // những trang này không cần đăng nhâp
                .and()
                .formLogin().loginPage("/login").usernameParameter("j_username").passwordParameter("j_password").permitAll()
                 .loginProcessingUrl("/j_spring_security_check") // trong tiến trình log in
                .successHandler(myAuthenticationSuccessHandler())
                .failureUrl("/login?incorrectAccount").and()
                .logout().logoutUrl("/logout").deleteCookies("JSESSIONID")
                .and().exceptionHandling().accessDeniedPage("/access-denied").and() //  ngăn người dùng truy cập khi không có quyền
                .sessionManagement().maximumSessions(1).expiredUrl("/login?sessionTimeout"); // 1 người dùng chỉ được dăng nhập 1 phiên 1 máy 
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        // dùng để định hướng trang
        return new CustomSuccessHandler();
    }
}
