package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domein.Category;
import com.example.domein.Item;
import com.example.service.CategoryService;

/**
 * カテゴリー情報を操作するコントローラ.
 * 
 * @author 熊沢良樹
 *
 */
@Controller
@RequestMapping("/")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	/**
	 * カテゴリー情報を表示するメソッド.
	 * 
	 * @param model
	 * @return カテゴリー情報
	 */
	@RequestMapping("/showcategory")
	public String showCategoryName(int id, Model model) {
		Category category = categoryService.showCategoryName(id);
		model.addAttribute("category", category);
		return "list";

	}

}
