package com.example.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domein.LoginUser;
import com.example.domein.User;

@Repository
public class LoginUserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<User> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

	private static final RowMapper<LoginUser> LOGINUSER_ROW_MAPPER = new BeanPropertyRowMapper<>(LoginUser.class);

	/**
	 * ユーザー情報を登録する.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		String sql = "INSERT INTO users (username,password) VALUES(:username,:password)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}

	/**
	 * nameでユーザー情報を検索する.
	 * 
	 * @param username
	 * @return ユーザー情報
	 */
	public LoginUser findByName(String name) {
		String sql = "SELECT username,password FROM users WHERE name=:name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);

		LoginUser loginUser = template.queryForObject(sql, param, LOGINUSER_ROW_MAPPER);

		return loginUser;
	}
}
