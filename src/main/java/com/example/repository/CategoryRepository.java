package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domein.Category;

@Repository
public class CategoryRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = new BeanPropertyRowMapper<>(Category.class);

	/**
	 * カテゴリーの名前を検索するメソッド.
	 * 
	 * @param id
	 * @return
	 */
	public Category load(int id) {
		String sql = "SELECT id,parent,name,nameAll FROM items WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Category category = template.queryForObject(sql, param, CATEGORY_ROW_MAPPER);

		return category;

	}

	/**
	 * 大カテゴリの１０件を全件検索するメソッド.
	 * 
	 * @return 大カテゴリリスト
	 */
	public List<Category> showAllLargeCategory() {
		String sql = "SELECT id,parent,name,name_All FROM category WHERE parent is null AND name_all is null ORDER BY id;";
		List<Category> largeCategoryList = template.query(sql, CATEGORY_ROW_MAPPER);

		return largeCategoryList;
	}

	/**
	 * 中カテゴリを検索するメソッド.
	 * 
	 * @param parent 大カテゴリのid(親id)
	 * @return 中カテゴリのリスト
	 */
	public List<Category> showMediumCategory(int parent) {
		String sql = "SELECT id,parent,name,name_All FROM category WHERE parent=:parent ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parent", parent);
		List<Category> mediumCategoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);

		return mediumCategoryList;
	}
	/**
	 * 小カテゴリを検索するメソッド.
	 * 
	 * @param parent 中カテゴリのid(親id)
	 * @return 小カテゴリのリスト
	 */
	public List<Category> showSmallCategory(int parent) {
		String sql = "SELECT id,parent,name,name_All FROM category WHERE parent=:parent ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parent", parent);
		List<Category> smallCategoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		
		return smallCategoryList;
	}

}
