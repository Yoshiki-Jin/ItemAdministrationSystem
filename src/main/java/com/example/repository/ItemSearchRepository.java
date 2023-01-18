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
import com.example.domein.MaxRecord;
import com.example.form.ItemSearchForm;

/**
 * 商品検索を行うリポジトリ.
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
	// 最大件数検索ためのマッパー
	private static final RowMapper<MaxRecord> MAXRECORD_ROW_MAPPER = (rs, i) -> {
		MaxRecord maxRecord = new MaxRecord();
		maxRecord.setMaxRecord(rs.getInt("max_record"));
		return maxRecord;

	};

	/**
	 * 上部フォームで検索するメソッド.
	 * 
	 * @param itemSearch
	 * @return
	 */
	public List<Item> search(Integer MAX_ITEM_NUM, ItemSearch itemSearch) {
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
		sql += " ORDER BY i_id LIMIT " + MAX_ITEM_NUM + ";";

		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * ページングした際に検索するメソッド.
	 * 
	 * @param front,rear
	 * @return 商品一覧
	 */
	public List<Item> searchItemsTurnPage(Integer MAX_ITEM_NUM, Integer offSet, ItemSearchForm itemSearchFormLog) {
		String sql = new String(
				"SELECT i.id as i_id,i.name as i_name,i.price as i_price,c.name_all as c_category,c.id as c_id,c.parent as c_parent,"
						+ "i.category as i_category,i.brand as i_brand,i.condition as i_condition,i.description as i_description "
						+ "FROM items as i INNER JOIN category as c ON i.category = c.id " + "WHERE 1=1");

		// categoryがない場合(大中小すべて)
		if (itemSearchFormLog.getSmallCategory() == 0) {
			sql += " AND i.name ILIKE" + "'%" + itemSearchFormLog.getName() + "%'" + " AND i.brand ILIKE '%"
					+ itemSearchFormLog.getBrand() + "%'";
		}
		// categoryがある場合(大中小すべて)
		if (itemSearchFormLog.getSmallCategory() != 0) {
			sql += " AND i.name ILIKE" + "'%" + itemSearchFormLog.getName() + "%'" + " AND i.brand ILIKE '%"
					+ itemSearchFormLog.getBrand() + "%'" + " AND i.smallCategory = "
					+ itemSearchFormLog.getSmallCategory();
		}
		sql += "ORDER BY i_id LIMIT " + MAX_ITEM_NUM + "OFFSET " + offSet + ";";

		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 最大件数検索
	 * 
	 * @return 最大件数
	 */
	public Integer maxRecord() {
		String sql = "SELECT COUNT(id) AS max_record FROM items;";
		List<MaxRecord> maxRecordList = template.query(sql, MAXRECORD_ROW_MAPPER);
		return maxRecordList.get(0).getMaxRecord();

	}

}
