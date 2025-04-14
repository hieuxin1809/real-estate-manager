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
@EnableWebSecurity // Bật cơ chế bảo mật của Spring Security
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // Định nghĩa bean cho UserDetailsService, dùng để load user từ DB (check username, role, trạng thái, ...)
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    // Định nghĩa bean cho password encoder (mã hóa mật khẩu bằng BCrypt)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cấu hình Provider cho quá trình xác thực, kết hợp giữa userDetailsService và passwordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Xác định nguồn lấy user
        authProvider.setPasswordEncoder(passwordEncoder()); // Dùng BCrypt để kiểm tra mật khẩu
        return authProvider;
    }

    // Cấu hình AuthenticationManager dùng provider ở trên
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    // Cấu hình các URL được phép truy cập và cơ chế bảo mật
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Tạm thời tắt CSRF (nên bật ở production)
                .authorizeRequests()
                // Phân quyền truy cập theo HTTP method + URL
                .antMatchers(HttpMethod.POST, "/api/assingments").hasRole("MANAGER")
                .antMatchers(HttpMethod.DELETE, "/api/buildings/{ids}").hasRole("MANAGER")
                .antMatchers("/admin/user-list", "/admin/user-edit", "/admin/user-edit-{id}").hasRole("MANAGER")
                .antMatchers("/admin/building-edit", "/admin/building-edit-{id}").hasAnyRole("MANAGER", "STAFF")
                .antMatchers("/admin/**").hasAnyRole("MANAGER", "STAFF") // Các trang /admin cần đăng nhập với role MANAGER hoặc STAFF
                .antMatchers("/login", "/resource/**", "/trang-chu", "/api/**").permitAll() // Các đường dẫn được public, không cần đăng nhập
                .and()
                .formLogin()
                .loginPage("/login") // Trang đăng nhập
                .usernameParameter("j_username") // Tên tham số username
                .passwordParameter("j_password") // Tên tham số password
                .permitAll()
                .loginProcessingUrl("/j_spring_security_check") // Đường dẫn xử lý login
                .successHandler(myAuthenticationSuccessHandler()) // Khi login thành công thì gọi handler để chuyển trang phù hợp
                .failureUrl("/login?incorrectAccount") // Khi login thất bại
                .and()
                .logout()
                .logoutUrl("/logout") // URL xử lý logout
                .deleteCookies("JSESSIONID") // Xóa session cookie
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied") // Trang lỗi khi người dùng không có quyền truy cập
                .and()
                .sessionManagement()
                .maximumSessions(1) // Chỉ cho phép 1 user đăng nhập 1 phiên 1 lúc
                .expiredUrl("/login?sessionTimeout"); // Khi session hết hạn thì chuyển hướng về login
    }

    // Bean xử lý sau khi đăng nhập thành công (redirect theo role, v.v.)
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new CustomSuccessHandler();
    }
}
