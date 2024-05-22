package com.thunder.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User DTO")
public class User {
	private String id;
	private String password;
	private String name;
	private String favorite;
	private String image;
	private String orgImage;
	
	public User() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getOrgImage() {
		return orgImage;
	}

	public void setOrgImage(String orgImage) {
		this.orgImage = orgImage;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", name=" + name + ", favorite=" + favorite + ", image="
				+ image + ", orgImage=" + orgImage + "]";
	}
}