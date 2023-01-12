package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domein.Item;
import com.example.domein.ItemSearch;

/**
 * 商品検索結果を行うリポジトリ.
 * 
 * @author 熊沢良樹
 *
 */
@Repository
public class ItemSearchRepository {

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
	 * ブランド名で検索するメソッド.
	 * 
	 * @param brand
	 * @return
	 */
	public List<Item> search(ItemSearch itemSearch) {
		System.out.println("レポジトリID" + itemSearch.getSmallCategory());

		String sql = new String(
				"SELECT i.id as i_id,i.name as i_name,i.price as i_price,c.name_all as c_category,c.id as c_id,c.parent as c_parent,"
						+ "i.category as i_category,i.brand as i_brand,i.condition as i_condition,i.description as i_description "
						+ "FROM items as i INNER JOIN category as c ON i.category = c.id " + "WHERE 1=1");

		// categoryがない場合(大中小すべて)
		if (itemSearch.getSmallCategory() == 0) {
			sql += " AND i.name ILIKE" + "'%" + itemSearch.getName() + "%'" + " AND i.brand ILIKE '%"
					+ itemSearch.getBrand() + "%'";
		}
		// categoryがある場合(大中小すべて)
		if (itemSearch.getSmallCategory() != 0) {
			sql += " AND i.name ILIKE" + "'%" + itemSearch.getName() + "%'" + " AND i.brand ILIKE '%"
					+ itemSearch.getBrand() + "%'" + " AND i.smallCategory = " + itemSearch.getSmallCategory();
		}

		// 名前であいまい検索
//		if (!itemSearch.getName().equals("")) {
//			sql += " AND i.name ILIKE '%" + itemSearch.getName() + "%'";
//		}
		// 中カテゴリ
//		if (itemSearch.getMediumCategory() != null) {
//			sql += " AND c.parent = " + itemSearch.getMediumCategory();
//		}
		// 小カテゴリで検索
//		if (itemSearch.getSmallCategory() != null) {
//			sql += " AND i.category = " + itemSearch.getSmallCategory();
//		}
		// ブランドであいまい検索
//		if (!itemSearch.getBrand().equals("")) {
//			sql += " AND i.name ILIKE" + "'%" + itemSearch.getName() + "%' AND i.brand ILIKE" + "'%"
//					+ itemSearch.getBrand() + "%'";
//		}

		sql += " ORDER BY i_id LIMIT 20;";

		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * ブランド名で検索するメソッド.
	 * 
	 * @param brand
	 * @return
	 */
	public List<Item> findByBrand(String brand) {
		String sql = "SELECT i.id as i_id,i.name as i_name,i.price as i_price,c.id as c_id,c.parent as c_parent,c.name_all as c_category,"
				+ "i.brand as i_brand,i.condition as i_condition,i.description as i_description "
				+ "FROM items as i INNER JOIN category as c ON i.category = c.id "
				+ "WHERE i.brand LIKE :brand ORDER BY i_id LIMIT 20;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("brand", "%" + brand + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

}
