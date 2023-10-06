package com.example.task.config;

import com.example.task.security.CustomSuccessHandler;
import com.example.task.service.impl.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/template/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()

                .authorizeRequests()
                .antMatchers("/admin-home").hasAuthority("ADMIN")
                .antMatchers("/dang-nhap").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/dang-nhap")
                .loginProcessingUrl("/process-login")
                .successHandler(customSuccessHandler)
                .failureUrl("/dang-nhap?incorrectAccount")
                .usernameParameter("username")
                .passwordParameter("password")
                //Denied
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
                .and()
                .sessionManagement().invalidSessionUrl("/dang-nhap?sessionTimeout")
                // Cấu hình cho Logout Page.
                .and()
                .logout().logoutUrl("/logout").deleteCookies().clearAuthentication(true);

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}