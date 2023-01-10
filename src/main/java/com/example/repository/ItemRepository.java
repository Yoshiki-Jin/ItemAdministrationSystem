package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domein.Item;
import com.example.domein.ItemInsert;

/**
 * 商品一覧表示、商品詳細表示を行うリポジトリ
 * 
 * @author 熊沢良樹
 *
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	// 商品情報一覧表示のためのマッパー
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("i_id"));
		item.setName(rs.getString("i_name"));
		item.setPrice(rs.getInt("i_price"));
		item.setCategory(rs.getString("c_category"));
		item.setBrand(rs.getString("i_brand"));
		item.setCondition(rs.getInt("i_condition"));
		item.setDescription(rs.getString("i_description"));
		return item;

	};

	/**
	 * 商品情報一覧を検索するメソッド.
	 * 
	 * @return アイテム一覧(30件)←後程sql文の、where以降を修正マスト※※
	 */
	public List<Item> findAll() {
		String sql = "SELECT i.id as i_id,i.name as i_name,i.price as i_price,c.name_all as c_category,i.brand as i_brand,i.condition as i_condition,i.description as i_description FROM items as i INNER JOIN category as c ON i.category = c.id WHERE i.id <= 25 ORDER BY i_id;";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;

	}

	/**
	 * 商品情報一覧を検索するメソッド。次のページで30件を表示する.
	 * 
	 * @return アイテム一覧(30件)←後程sql文の、where以降を修正マスト※※
	 */
	public List<Item> showNextPage(int nowPage) {

		String sql = "SELECT i.id as i_id,i.name as i_name,i.price as i_price,c.name_all as c_category,i.brand as i_brand,i.condition as i_condition,i.description as i_description FROM items as i INNER JOIN category as c ON i.category = c.id ORDER BY i_id LIMIT 25 OFFSET 25*:nowPage;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("nowPage", nowPage);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;

	}

	/**
	 * 商品情報一覧を検索するメソッド。前のページを30件を表示する.
	 * 
	 * @return アイテム一覧(30件)←後程sql文の、where以降を修正マスト※※
	 */
	public List<Item> showForwardPage(int nowPage) {
		String sql = "SELECT i.id as i_id,i.name as i_name,i.price as i_price,c.name_all as c_category,i.brand as i_brand,i.condition as i_condition,i.description as i_description FROM items as i INNER JOIN category as c ON i.category = c.id ORDER BY i_id LIMIT 25 OFFSET 25*:nowPage";
		SqlParameterSource param = new MapSqlParameterSource().addValue("nowPage", nowPage);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;

	}

	/**
	 * IDから商品情報を一件検索します.
	 * 
	 * @param id ID
	 * @return 検索された商品情報
	 */

	public Item load(int id) {
		String sql = "SELECT i.id as i_id,i.name as i_name,i.price as i_price,c.name_all as c_category,i.brand as i_brand,i.condition as i_condition,i.description as i_description FROM items as i INNER JOIN category as c ON i.category = c.id WHERE i.id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);

		return item;
	}

	/**
	 * itemの追加を行います.
	 * 
	 * @param itemForm
	 */
	public void save(ItemInsert itemInsert) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(itemInsert);

		String insertSql = "INSERT INTO items(name,condition,category,brand,price,description)VALUES(:name,:condition,:smallCategory,:brand,:price,:description);";
		template.update(insertSql, param);

	}

	/**
	 * itemの編集を行います.
	 * 
	 * @param itemForm
	 */
	public void update(ItemInsert itemInsert) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(itemInsert);
		String updateSql = "UPDATE items SET name=:name,condition=:condition,category=:smallCategory,brand=:brand,price=:price,description=:description WHERE id=:id;";
		template.update(updateSql, param);

	}

}
