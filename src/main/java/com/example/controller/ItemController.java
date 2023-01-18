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
import com.example.form.ItemSearchForm;
import com.example.repository.ItemRepository;
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
	private ItemRepository itemRepository;

	@Autowired
	private CategoryService categoryService;

	// 現在のページ
	private int nowPage = 0;

	// 最大件数
	private int maxRecord = 30;
	// 最大件数
	private final int showItemMaxByPage = 30;

	// 全レコードの件数から算出されるページ数（recordNum/outputNum）
	private int maxPage;

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
	public String showEdit(Model model, ItemForm itemForm, Integer id) {
		List<Category> largeCategoryList = categoryService.showAllLargeCategory();
		model.addAttribute("largeCategoryList", largeCategoryList);

		// 編集する商品を検索する.
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
	public String showItemList(Model model, ItemSearchForm itemSearchForm) {

		List<Item> itemList = itemService.showItemList();
		model.addAttribute("itemList", itemList);

		// 最大ページを表示する
		maxRecord = itemRepository.maxRecord();
		maxPage = maxRecord / showItemMaxByPage;
		model.addAttribute("maxPage", maxPage);

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
	 * @param selectedPage 入力されたページ
	 * @param model
	 * @return
	 */
	@PostMapping("/showSelectedPage")
	public String showSelectedPage(Integer selectedPage, Model model) {
		System.out.println(";;;;;;;;;;;;;" + selectedPage);
		nowPage = 0;

		// LimitOffsetのため、ー１しておく(始まりの件数を算出するため)
		nowPage = selectedPage - 1;
		if (nowPage < 0) {
			nowPage = 0;
		}
		// num2は指定したページの始まりの件数
		int num2 = showItemMaxByPage * nowPage;
		model.addAttribute("maxPage", maxPage);
		List<Item> itemList = itemService.showSelectedPage(showItemMaxByPage, num2);
		System.out.println(showItemMaxByPage);
		model.addAttribute("itemList", itemList);
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
			Model model, Integer id) {
		if (result.hasErrors()) {
			return showEdit(model, itemForm, id);
		}

		ItemInsert itemInsert = new ItemInsert();
		BeanUtils.copyProperties(itemForm, itemInsert);
		itemService.update(itemInsert);
		return "redirect:/showItemList";

	}

}
