package com.example.domein;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final LoginUser loginUser;
	private final Collection<? extends GrantedAuthority> authorities;

	public LoginUserDetails(LoginUser loginUser) {
		this.loginUser = loginUser;
		this.authorities = null;
		System.out.println("GHI");
//		this.authorities = loginUser.roleList()
//				.stream()
//				.map(role -> new SimpleGrantedAuthority(role.toString()))//勝手にtoString()した。
//				.toList();
		System.out.println("JKL");

	}

//	public LoginUserDetails(loginUser) {
//		this.loginUser = new LoginUser();
//		.stream()
//		.map
//		// TODO Auto-generated constructor stub
//	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return loginUser.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return loginUser.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}

//以下は以前書いたコード

//package com.example.domein;
//
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class LoginUserDetails implements UserDetails {
//
//	private final LoginUser loginUser;
//	// Roleのリスト
//	private final Collection<? extends GrantedAuthority> authorities;
//
//	public LoginUserDetails(LoginUser loginUser) {
//		this.loginUser = loginUser;
//		this.authorities = loginUser.getRoleList().stream().map(role -> new SimpleGrantedAuthority(role)).toList();
//	}
//
//	public LoginUser getLoginUser() {
//		return loginUser;
//	}
//
//	@Override // ハッシュ化済みのパスワードを返す
//	public String getPassword() {
//		return loginUser.getPassword();
//	}
//
//	@Override // ログインで利用するユーザーネーム
//	public String getUsername() {
//		return loginUser.getUsername();
//	}
//
//	@Override // ロールのコレクションを返す
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return authorities;
//	}
//
//	@Override //ユーザーが期限切れでなければtrue
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override //ユーザーがロックされていなければtrue
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override //ユーザーのパスワードが期限切れでなければtrue
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override //ユーザーが有効であればtrue
//	public boolean isEnabled() {
//		return true;
//	}
//
//}
