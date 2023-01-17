package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domein.Category;
import com.example.form.ItemForm;
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
	 * list.htmlを表示するメソッド.
	 * 大カテゴリ１０件を検索するメソッド.
	 * 
	 * @param model
	 * @return 大カテゴリリスト１０件
	 */
	@GetMapping("/showAllLargeCategoryToList")
	public String showAllLargeCategoryToList(Model model) {
		List<Category> largeCategoryList = categoryService.showAllLargeCategory();
		model.addAttribute("largeCategoryList", largeCategoryList);
		return "list";
		
	}

	/**
	 * 中カテゴリを検索するメソッド.
	 * 
	 * @param model  モデル
	 * @param parent 大カテゴリID（親ID）
	 * @return 中カテゴリリスト
	 */
	@ResponseBody
	@PostMapping("/showMediumCategory")
	public List<Category> showMediumCategory(int parent) {
		System.out.println(parent);
		List<Category> mediumCategoryList = categoryService.showMediumCategory(parent);
		return mediumCategoryList;

	}

	/**
	 * 小カテゴリを検索するメソッド.
	 * 
	 * @param model  モデル
	 * @param parent 中カテゴリID（親ID）
	 * @return 小カテゴリリスト
	 */
	@ResponseBody
	@PostMapping("/showSmallCategory")
	public List<Category> showSmallCategory(int parent) {
		List<Category> smallCategoryList = categoryService.showSmallCategory(parent);
		return smallCategoryList;

	}

}
