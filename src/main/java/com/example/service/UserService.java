package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domein.User;
import com.example.repository.LoginUserRepository;

@Service
public class UserService {

	@Autowired
	private LoginUserRepository loginUserRepository;

	/**
	 * ユーザー情報を登録するメソッド.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {

		loginUserRepository.insert(user);
	}

}
