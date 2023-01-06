package com.example.form;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ItemForm {

	/** id */
	private Integer id;
	/** 名前 */
	@NotBlank(message = "error:may not be empty")
	private String name;
	/** 値段 */
	@NotNull(message = "error:may not be empty")
	private double price;
	/** 大カテゴリ */
	private Integer largeCategory;
	/** 中カテゴリ */
	private Integer mediumCategory;
	/** 小カテゴリ */
	@NotNull(message = "error:may not be empty")
	//@Range(min=0,max=9999,message = "error:may not be empty")
	private Integer smallCategory;
	/** ブランド */
	private String brand;
	/** コンディション */
	@NotNull(message = "error:may not be empty")
	private Integer condition;
	/** 説明 */
	@NotBlank(message = "error:may not be empty")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getLargeCategory() {
		return largeCategory;
	}

	public void setLargeCategory(Integer largeCategory) {
		this.largeCategory = largeCategory;
	}

	public Integer getMediumCategory() {
		return mediumCategory;
	}

	public void setMediumCategory(Integer mediumCategory) {
		this.mediumCategory = mediumCategory;
	}

	public Integer getSmallCategory() {
		return smallCategory;
	}

	public void setSmallCategory(Integer smallCategory) {
		this.smallCategory = smallCategory;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
