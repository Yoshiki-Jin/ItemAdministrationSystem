package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domein.Item;
import com.example.domein.ItemSearch;
import com.example.domein.MaxRecord;
import com.example.form.ItemSearchForm;
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

	public List<Item> search(Integer MAX_ITEM_NUM, ItemSearch itemSearch) {
		List<Item> itemList = itemSearchRepository.search(MAX_ITEM_NUM, itemSearch);
		return itemList;
	}

	public List<Item> turnPage(Integer MAX_ITEM_NUM, Integer offSet, ItemSearchForm itemSearchFormLog) {
		List<Item> itemList = itemSearchRepository.searchItemsTurnPage(MAX_ITEM_NUM, offSet, itemSearchFormLog);
		return itemList;
	}

	public Integer maxRecord() {
		Integer maxRecord = itemSearchRepository.maxRecord();
		return maxRecord;
	}

}
