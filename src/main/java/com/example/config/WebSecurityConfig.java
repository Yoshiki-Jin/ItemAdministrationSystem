package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	// cssファイルなどへのアクセス制限に関する記述.
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/fonts/**");
	}

	
	/**
	 * @param http
	 * @return 成功時は商品一覧画面、失敗時はログイン画面へ.
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		//認証していなくても遷移できる。ログイン画面、ログイン処理、会員登録画面、会員登録処理.
		http.authorizeHttpRequests() // URLごとの認証設定
				.requestMatchers("/user/toLogin", "/user/login","/user/toInsert", "/user/insert")// ログイン画面、ログイン処理、会員登録画面、会員登録処理は認証不要
				.permitAll()
				.anyRequest()
				.authenticated();

		// ログイン時の認証設定
		http.formLogin() 
				.loginPage("/user/toLogin") // ログイン画面に遷移させるパス
				.loginProcessingUrl("/loginUser") // username、passwordの送信先ＵＲＬ
				.failureUrl("/login?error=true") // ログイン失敗後に遷移させるURL ??????/errorってどこに行ってる？
				.defaultSuccessUrl("/showItemList",true) // ログイン成功後、遷移させるURL　????　第二引数はなに？
				.usernameParameter("username") // 認証時に使用するユーザ名のリクエストパラメータ名(今回はメールアドレスを使用)
				.passwordParameter("password"); // 認証時に使用するパスワードのリクエストパラメータ名

		 // ログアウトに関する設定
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // ログアウトさせる際に遷移させるパス
		.logoutSuccessUrl("/user/toLogin") // ログアウト後に遷移させるパス(ここではログイン画面を設定)
		.deleteCookies("JSESSIONID") // ログアウト後、Cookieに保存されているセッションIDを削除
		.invalidateHttpSession(true); // true:ログアウト後、セッションを無効にする false:セッションを無効にしない
		
		//無効にする.ajax使用時にエラーが出るのを回避するため.
		http.csrf().disable();//

		return http.build();
	}

//パスワードハッシュ化
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
