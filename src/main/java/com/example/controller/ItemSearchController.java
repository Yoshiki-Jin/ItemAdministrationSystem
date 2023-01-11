package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domein.Item;
import com.example.domein.ItemSearch;
import com.example.form.ItemSearchForm;
import com.example.service.ItemSearchService;

/**
 * 商品一覧を検索するメソッド.
 * 
 * @author 熊沢良樹
 *
 */
@Controller
@RequestMapping("/")
public class ItemSearchController {

	@Autowired
	ItemSearchService itemSearchService;

	@PostMapping("/search")
	public String search(Model model, ItemSearchForm itemSearchForm) {

		ItemSearch itemSearch = new ItemSearch();
		BeanUtils.copyProperties(itemSearchForm, itemSearch);

		List<Item> itemList = itemSearchService.search(itemSearch);
		model.addAttribute("itemList", itemList);
		return "list";
	};

}
