package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domein.Category;
import com.example.domein.Item;
import com.example.repository.CategoryRepository;

/**
 * 商品情報を操作するサービス.
 * 
 * @author 熊沢良樹
 *
 */
@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * カテゴリー名を表示するメソッド.
	 * 
	 * @return 商品一覧表示
	 */
	public Category showCategoryName(int id) {
		Category category = categoryRepository.load(id);
		return category;
	}

}
