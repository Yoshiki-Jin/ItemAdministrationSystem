package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/css/**", "/img_toy/**", "/js/**", "/fonts/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/shopCart/showCartList","/shopCart/insertItem","/", "/insertUser/toInsert", "/insertUser/insert","/login/", "/showItemDetail/showItemDetail","/contact/InsertContact","/contact/insertC","/contact_finish/").permitAll().anyRequest().authenticated();

		http.formLogin() // ログインに関する設定
				.loginPage("/login/") // ログイン画面に遷移させるパス(ログイン認証が必要なパスを指定してかつログインされていないとこのパスに遷移される)
				.loginProcessingUrl("/login/loginUser") // ログインボタンを押した際に遷移させるパス(ここに遷移させれば自動的にログインが行われる)
				.failureUrl("/login/?error=true") // ログイン失敗に遷移させるパス
				.defaultSuccessUrl("/", true) // 第1引数:デフォルトでログイン成功時に遷移させるパス
				// 第2引数: true :認証後常に第1引数のパスに遷移
				// false:認証されてなくて一度ログイン画面に飛ばされてもログインしたら指定したURLに遷移
				.usernameParameter("email") // 認証時に使用するユーザ名のリクエストパラメータ名(今回はメールアドレスを使用)
				.passwordParameter("password"); // 認証時に使用するパスワードのリクエストパラメータ名

		http.logout() // ログアウトに関する設定
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウトさせる際に遷移させるパス
				.logoutSuccessUrl("/login") // ログアウト後に遷移させるパス(ここではログイン画面を設定)
				.deleteCookies("JSESSIONID") // ログアウト後、Cookieに保存されているセッションIDを削除
				.invalidateHttpSession(true); // true:ログアウト後、セッションを無効にする false:セッションを無効にしない
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
