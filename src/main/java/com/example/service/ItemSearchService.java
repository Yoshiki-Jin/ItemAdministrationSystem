package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domein.Item;
import com.example.domein.ItemSearch;
import com.example.repository.ItemSearchRepository;

/**
 * 商品一覧を検索するサービス.
 * 
 * @author 熊沢良樹
 *
 */
@Service
public class ItemSearchService {

	@Autowired
	private ItemSearchRepository itemSearchRepository;

	public List<Item> search(ItemSearch itemSearch) {
		List<Item> itemList = itemSearchRepository.search(itemSearch);
		return itemList;
	}

}
