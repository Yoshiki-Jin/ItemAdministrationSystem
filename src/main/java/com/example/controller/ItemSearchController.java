package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domein.Category;
import com.example.domein.Item;
import com.example.domein.ItemSearch;
import com.example.form.ItemSearchForm;
import com.example.service.CategoryService;
import com.example.service.ItemSearchService;

/**
 * 商品一覧を検索するメソッド.
 * 
 * @author 熊沢良樹
 *
 */
@Controller
@RequestMapping("/search")
public class ItemSearchController {

	@Autowired
	ItemSearchService itemSearchService;
	@Autowired
	ItemController itemController;
	@Autowired
	CategoryService categoryService;

	@PostMapping("")
	public String search(@Validated ItemSearchForm itemSearchForm, BindingResult result, Model model) {
		System.out.println(itemSearchForm);

		// カテゴリー情報を表示する
		List<Category> largeCategoryList = categoryService.showAllLargeCategory();
		model.addAttribute("largeCategoryList", largeCategoryList);

		if (result.hasErrors()) {
			System.out.println("error");
			// 各項目にてエラーが発生しているかどうかを確認する
			boolean isSmallCatgeoryErrored = result.hasFieldErrors("smallCategory");
			// エラーが発生している場合、エラーメッセージを表示
			if (isSmallCatgeoryErrored == true) {
				model.addAttribute("smallCategory", "categoryの選択は必須です");
			}
			return itemController.showItemList(model, itemSearchForm);
		}

		ItemSearch itemSearch = new ItemSearch();
		BeanUtils.copyProperties(itemSearchForm, itemSearch);

		List<Item> itemList = itemSearchService.search(itemSearch);
		model.addAttribute("itemList", itemList);
		return "list";
	};

}
