package com.ikun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration //标记当前是个配置类
@EnableWebSecurity //开启SpringSecurity的默认行为->自动生成登录页面
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启Controller中方法的权限控制
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //目前必须调用父类的configure方法，否则就不需要认证即可访问
//        super.configure(http);
        //允许iframe显示
        http.headers().frameOptions().sameOrigin();
        //登录设置
        http
                .authorizeRequests()
                .antMatchers("/static/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") //要在IndexController中配置这个映射
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .csrf().disable(); //关闭跨域请求伪造

        //添加自定义异常入口
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler());
    }
}
