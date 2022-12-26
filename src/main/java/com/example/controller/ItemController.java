package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domein.Item;
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

	private int nowPage = 0;

	/**
	 * 商品情報一覧を表示するメソッド.
	 * 
	 * @param model
	 * @return 商品情報一覧
	 */
	@RequestMapping("/show")
	public String showItemList(Model model) {

		List<Item> itemList = itemService.showItemList();
		model.addAttribute("itemList", itemList);

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

}
