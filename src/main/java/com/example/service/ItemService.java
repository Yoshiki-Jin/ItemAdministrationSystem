package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domein.Item;
import com.example.domein.ItemInsert;
import com.example.repository.ItemRepository;

/**
 * 商品情報を操作するサービス.
 * 
 * @author 熊沢良樹
 *
 */
@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品一覧を表示するメソッド.
	 * 
	 * @return 商品一覧表示
	 */
	public List<Item> showItemList() {
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}

	/**
	 * 選んだページの商品一覧を表示するメソッド.
	 * 
	 * @param num1 1ページの最大表示件数（３０件）
	 * @param num2 指定したページの最初の件目
	 * @return 商品一覧
	 */
	public List<Item> showSelectedPage(int num1, int num2) {
		return itemRepository.showSelectedPage(num1, num2);
	}

	/**
	 * 商品一覧を表示するメソッド.次のページで30件を表示する.
	 * 
	 * @return 商品一覧表示
	 */
	public List<Item> showNextPage(int nowPage) {
		List<Item> itemList = itemRepository.showNextPage(nowPage);
		return itemList;
	}

	/**
	 * 商品一覧を表示するメソッド.前のページを30件を表示する.
	 * 
	 * @return 商品一覧表示
	 */
	public List<Item> showForwardPage(int nowPage) {
		List<Item> itemList = itemRepository.showForwardPage(nowPage);
		return itemList;
	}

	/**
	 * 商品詳細情報を検索するメソッド.
	 * 
	 * @param id
	 * @return 商品詳細情報
	 */
	public Item showItemDetail(int id) {
		Item item = itemRepository.load(id);
		return item;
	}

	/**
	 * 商品追加を行うメソッド.
	 * 
	 * @param itemInsert
	 */
	public void save(ItemInsert itemInsert) {
		itemRepository.save(itemInsert);
	}

	/**
	 * 商品編集を行うメソッド.
	 * 
	 * @param itemInsert
	 */
	public void update(ItemInsert itemInsert) {
		itemRepository.update(itemInsert);
	}

}
