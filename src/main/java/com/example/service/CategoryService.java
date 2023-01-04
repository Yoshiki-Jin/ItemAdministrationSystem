package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.domein.Category;
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

	/**
	 * 大カテゴリ１０件を全件検索するメソッド.
	 * 
	 * @return 大カテゴリ１０件
	 */
	public List<Category> showAllLargeCategory() {
		List<Category> largeCategoryList = categoryRepository.showAllLargeCategory();
		return largeCategoryList;
	}

	/**
	 * 中カテゴリを検索するメソッド.
	 * 
	 * @param parent 大カテゴリのid(親id)
	 * @return 中カテゴリのリスト
	 */
	public List<Category> showMediumCategory(int parent) {
		List<Category> mediumCategoryList = categoryRepository.showMediumCategory(parent);
		return mediumCategoryList;
	}

	/**
	 * 小カテゴリを検索するメソッド.
	 * 
	 * @param parent 中カテゴリのid(親id)
	 * @return 小カテゴリのリスト
	 */
	public List<Category> showSmallCategory(int parent) {
		List<Category> smallCategoryList = categoryRepository.showSmallCategory(parent);
		return smallCategoryList;
	}

}
