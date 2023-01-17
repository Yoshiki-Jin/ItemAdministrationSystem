package com.example.domein;

public class User {

	/** ユーザーID */
	private Integer id;
	/** ユーザー名(メールアドレス) */
	private String username;
	/** メールアドレス */
	private String mailAddress;
	/** パスワード */
	private String password;
	/** 権限情報 */
	private Integer authority;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", mailAddress=" + mailAddress + ", password=" + password
				+ ", authority=" + authority + "]";
	}

}
