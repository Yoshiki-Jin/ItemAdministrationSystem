package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domein.User;
import com.example.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザー情報を登録するメソッド.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {

		userRepository.insert(user);
	}

	/**
	 * メールアドレスからユーザー情報を検索するメソッド. *
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User searchByUsername(String username) {
		return userRepository.findByUsername(username);

	}
}
