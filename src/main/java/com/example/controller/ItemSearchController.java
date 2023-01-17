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

	// １ページの最大表示件数
	public static final int MAX_ITEM_NUM = 30;

	// 現在のページ
	private int nowPage = 0;

	// 検索項目の保持用
	private ItemSearchForm itemSearchFormLog;

	// 最大件数
	private int maxRecord;

	@Autowired
	ItemSearchService itemSearchService;
	@Autowired
	ItemController itemController;
	@Autowired
	CategoryService categoryService;

	/**
	 * 一番最初に検索した際の処理.
	 * 
	 * @param itemSearchForm
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("")
	public String search(@Validated ItemSearchForm itemSearchForm, BindingResult result, Model model) {
		System.out.println(itemSearchForm);

		// カテゴリー情報を表示する
		List<Category> largeCategoryList = categoryService.showAllLargeCategory();
		model.addAttribute("largeCategoryList", largeCategoryList);

		// ページング用に、検索項目を記録してる.
		itemSearchFormLog = null;
		itemSearchFormLog = itemSearchForm;
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

		List<Item> itemList = itemSearchService.search(MAX_ITEM_NUM, itemSearch);
		model.addAttribute("itemList", itemList);
		return "afterSearchList";
	};

	/**
	 * 検索語にページングを行う処理.
	 * 
	 * @param turnPage
	 * @param model
	 * @param itemSearchForm
	 * @return
	 */
	@RequestMapping("/turnPage")
	public String turnPage(Integer turnPage, Model model, ItemSearchForm itemSearchForm) {

		// カテゴリー情報を表示する
		List<Category> largeCategoryList = categoryService.showAllLargeCategory();
		model.addAttribute("largeCategoryList", largeCategoryList);

		// ★★★★★以下検索後のページング処理★★★★★

		// 現在のページに加算する(prevの場合－1、nextの場合+1。turnPageに入る値はこの二通りのみ。)
		nowPage = nowPage + turnPage;

		// １ページ目より前に行こうとしたときに、１ページ目を表示する.
		if (nowPage <= 0) {
			nowPage = 0;
		}

		// 検索開始行 = ３０×現在のページ
		int offSet = MAX_ITEM_NUM * nowPage;

		// 以下、最後のページ以降に行こうとすると直前のページに戻る処理。
		// 検索を行い、itemListが空だったら、最後のページより後に到達したと考える.
		List<Item> itemList = itemSearchService.turnPage(MAX_ITEM_NUM, offSet, itemSearchFormLog);

		// 最後のページより後だったら、直前のページに戻る.
		if (itemList == null) {
			nowPage -= 1;
			offSet = MAX_ITEM_NUM * nowPage;
			itemList = itemSearchService.turnPage(MAX_ITEM_NUM, offSet, itemSearchFormLog);
		}
		model.addAttribute("itemList", itemList);

		// 検索後の商品一覧ページに飛ばす
		return "afterSearchList";
	}

}
