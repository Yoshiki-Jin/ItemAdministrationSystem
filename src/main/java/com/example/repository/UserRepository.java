package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domein.User;

@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<User> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

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
	 * usernameでユーザー情報を検索する.
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		String sql = "SELECT username,password FROM users WHERE username=:username";
		SqlParameterSource param = new MapSqlParameterSource().addValue("username", username);

		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);

		if (userList.size() == 0) { // 検索結果が０だった場合「null」を返す
			return null;
		}

		return userList.get(0);
	}
}
