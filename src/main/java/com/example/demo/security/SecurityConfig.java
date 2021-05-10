package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.demo.configurer.UrlConfig;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String LOGIN_PAGE = UrlConfig.ROOT_URL + "/login";
	
	@Autowired
	AuthenticationProviderImpl authenticationProviderImpl;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                //ログイン不要でアクセス可能に設定
                .antMatchers(LOGIN_PAGE,UrlConfig.ROOT_URL + "/signup").permitAll()
                //上記以外は直リンク禁止
                .anyRequest().authenticated()
            .and()
            .formLogin()
                //ログイン処理のパス
                .loginProcessingUrl(LOGIN_PAGE)
                //ログインページ
                .loginPage(LOGIN_PAGE)
                //ログインエラー時の遷移先 ※パラメーターに「error」を付与
                .failureUrl(LOGIN_PAGE)
                //ログイン成功時の遷移先
                .defaultSuccessUrl(UrlConfig.ROOT_URL + "/see/space/day",true)
                //ログイン時のキー：ユーザー名
                .usernameParameter("userIdName")
                //ログイン時のパスワード
                .passwordParameter("password")
            .and()
            .logout()
            	.logoutUrl(UrlConfig.ROOT_URL + "/logout")
                //ログアウト時の遷移先 POSTでアクセス
                .logoutSuccessUrl(LOGIN_PAGE);
    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        // 静的リソースに対するアクセスはセキュリティ設定を無視する
        web.ignoring().antMatchers("/style.css");
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProviderImpl);
    }
}
