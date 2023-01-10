package com.example.controller;

import java.util.List;

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

import com.example.domein.Category;
import com.example.domein.Item;
import com.example.domein.ItemInsert;
import com.example.form.ItemForm;
import com.example.service.CategoryService;
import com.example.service.ItemService;

/**
 * 商品情報を操作するコントローラ.
 * 
 * @author 熊沢良樹
 *
 */
@Controller
@RequestMapping("/")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoryService categoryService;

	private int nowPage = 0;

	/**
	 * 商品追加画面を表示するメソッド. 大カテゴリ１０件を検索するメソッド.
	 * 
	 * @param model
	 * @return 大カテゴリリスト１０件
	 */
	@GetMapping("/showAdd")
	public String showAdd(Model model, ItemForm itemForm) {
		List<Category> largeCategoryList = categoryService.showAllLargeCategory();
		model.addAttribute("largeCategoryList", largeCategoryList);
		return "add";

	}

	/**
	 * 商品編集画面を表示するメソッド. 大カテゴリ１０件を検索するメソッド.
	 * 
	 * @param model
	 * @return 大カテゴリリスト１０件
	 */
	@GetMapping("/showEdit")
	public String showEdit(Model model, ItemForm itemForm,Integer id) {
		List<Category> largeCategoryList = categoryService.showAllLargeCategory();
		model.addAttribute("largeCategoryList", largeCategoryList);
		
		//編集する商品を検索する.
		Item item = itemService.showItemDetail(id);
		model.addAttribute("item", item);
		
		return "edit";

	}

	/**
	 * 商品情報一覧を表示するメソッド.
	 * 
	 * @param model
	 * @return 商品情報一覧
	 */
	@RequestMapping("/showItemList")
	public String showItemList(Model model) {

		List<Item> itemList = itemService.showItemList();
		model.addAttribute("itemList", itemList);

		// カテゴリー情報を表示する
		List<Category> largeCategoryList = categoryService.showAllLargeCategory();
		model.addAttribute("largeCategoryList", largeCategoryList);

		return "list";

	}

	/**
	 * 商品一覧を表示するメソッド.前のページを30件を表示する.
	 * 
	 * @return 次のページの商品一覧
	 */
	@RequestMapping("/next")
	public String showNextPage(Model model) {
		nowPage = nowPage + 1;

		List<Item> itemList = itemService.showNextPage(nowPage);
		model.addAttribute("itemList", itemList);

		// カテゴリー情報を表示する
		List<Category> largeCategoryList = categoryService.showAllLargeCategory();
		model.addAttribute("largeCategoryList", largeCategoryList);

		return "list";
	}

	/**
	 * 商品一覧を表示するメソッド.前のページを30件を表示する.
	 * 
	 * @return 前のページの商品一覧
	 */
	@RequestMapping("/forward")
	public String showForwardPage(Model model) {
		if (nowPage == 0) {
			nowPage = 0;
		} else {
			nowPage = nowPage - 1;
		}

		List<Item> itemList = itemService.showForwardPage(nowPage);
		model.addAttribute("itemList", itemList);

		// カテゴリー情報を表示する
		List<Category> largeCategoryList = categoryService.showAllLargeCategory();
		model.addAttribute("largeCategoryList", largeCategoryList);

		return "list";
	}

	/**
	 * 商品詳細を表示するメソッド.
	 * 
	 * @param id
	 * @param model
	 * @return 商品詳細画面
	 */
	@RequestMapping("/detail")
	public String showItemDetail(int id, Model model) {
		Item item = itemService.showItemDetail(id);
		model.addAttribute("item", item);
		return "detail";
	}

	/**
	 * itemの追加を行います.
	 * 
	 * @param itemForm
	 * @return 商品一覧画面
	 */
	@PostMapping("/insert")
	public String insert(@Validated ItemForm itemForm, BindingResult result, RedirectAttributes redirectAttributes,
			Model model) {
		if (result.hasErrors()) {
			return showAdd(model, itemForm);
		}

		ItemInsert itemInsert = new ItemInsert();
		BeanUtils.copyProperties(itemForm, itemInsert);
		itemService.save(itemInsert);
		return "redirect:/showItemList";

	}

	/**
	 * itemの編集を行います.
	 * 
	 * @param itemForm
	 * @return 商品一覧画面
	 */
	@PostMapping("/update")
	public String update(@Validated ItemForm itemForm, BindingResult result, RedirectAttributes redirectAttributes,
			Model model,Integer id) {
		if (result.hasErrors()) {
			return showEdit(model, itemForm,id);
		}

		ItemInsert itemInsert = new ItemInsert();
		BeanUtils.copyProperties(itemForm, itemInsert);
		itemService.update(itemInsert);
		return "redirect:/showItemList";

	}

}
