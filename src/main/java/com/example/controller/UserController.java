package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domein.User;
import com.example.form.InsertUserForm;
import com.example.form.LoginForm;
import com.example.service.LoginUserDetailsService;
import com.example.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginUserDetailsService userDetailsService;

	/**
	 * ユーザー情報登録画面に遷移する.
	 * 
	 * @param form ユーザー情報を登録する際のフォーム.
	 * @return ユーザー情報登録画面
	 */
	@GetMapping("/toInsert")
	public String toInsert(Model model, InsertUserForm insertUserForm) {
		return "register";
	}

	/**
	 * 会員登録を行うメソッド.
	 * 
	 * @param insertUserForm
	 * @param result
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@PostMapping("/insert")
	public String insert(@Validated InsertUserForm insertUserForm, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		// 入力したメールアドレスが既に登録されていた場合、エラーメッセージを返す
		if (result.hasErrors()) {
			return toInsert(model, insertUserForm);
		}

		User user = new User();
		BeanUtils.copyProperties(insertUserForm, user);
		userService.insert(user);

		return "redirect:/user/login";
	}

	/**
	 * ログイン画面に遷移する.
	 * 
	 * @return ログイン画面
	 */
	@GetMapping("/toLogin")
	public String tologin() {
		return "login";
	}
	/**
	 * ログアウト処理を行う.
	 * 
	 * @return ログイン画面
	 */
	@GetMapping("/logout")
	public String logout() {
		return "login";
	}
	@RequestMapping("/login")
	public String login(LoginForm loginForm) {
//		if(!userService.login(form)){
//			System.out.println("false側");
//			return "login";
//		}
//		System.out.println("true側");
		
		return "redirect:/showItemList";
	}


}
