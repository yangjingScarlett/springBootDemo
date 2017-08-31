package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Yangjing
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()//授权
                .antMatchers("/", "/login").permitAll()//允许所有用户访问/和/index
                .anyRequest().authenticated()//认证：其他地址的访问均需验证权限
                .and()
                .formLogin()
                .loginPage("/login")//设置HttpSecurity登录页面的访问路径是"/login"
                .defaultSuccessUrl("/chat")//登录成功后转向/chat路径
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    //在内存中分别配置两个用户，用户名和密码一致，身份是USER
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("wyf").password("wyf").roles("USER")
                .and()
                .withUser("wisely").password("wisely").roles("USER");
    }

    ///resources/static目录下的静态资源，HttpSecurity不拦截
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/resources/static/**");
    }


}
