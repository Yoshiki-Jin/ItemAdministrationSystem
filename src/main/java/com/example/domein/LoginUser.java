package com.example.domein;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * ユーザーのログイン情報を格納するドメイン.
 * 
 * @author 熊沢良樹
 *
 */
//ライブラリのUserクラスを継承
public class LoginUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L; // ?????誰もわからない
	private final  User user;

	/**
	 * 通常のユーザ情報に加え、認可用ロールを設定する.
	 * 
	 * @param user          ユーザ情報(名前)
	 * @param authorityList 権限情報が入ったリスト
	 */
	public LoginUser(User user, Collection<GrantedAuthority> authorityList) {
		super(user.getUsername(), user.getPassword(), authorityList);
		this.user = user;
	}

	/**
	 * ユーザ情報を返します.
	 * 
	 * @return ユーザ情報
	 */
	public User getUser() {
		System.out.println("ログインユーザー情報" + user);
		return user;
	}
}
