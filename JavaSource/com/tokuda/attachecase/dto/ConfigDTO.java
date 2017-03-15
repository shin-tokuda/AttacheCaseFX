package com.tokuda.attachecase.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 設定情報DTO
 *
 * @author s-tokuda
 */
@Data
public class ConfigDTO {

	@JsonProperty("app_name")
	private String title;

	@JsonProperty("products")
	private List<String> products;

	@JsonProperty("applications")
	private List<ApplicationDTO> applications;

	@JsonProperty("drivers")
	private List<DriverDTO> drivers;

	/**
	 * アプリケーション情報DTO
	 */
	@Data
	public static class ApplicationDTO {

		@JsonProperty("app_id")
		private String appId;

		@JsonProperty("title")
		private String title;

		@JsonProperty("icon")
		private String icon;

		@JsonProperty("icon_color")
		private String iconColor;

		@JsonProperty("extension")
		private String extension;
	}

	/**
	 * DBドライバー情報DTO
	 */
	@Data
	public static class DriverDTO {

		@JsonProperty("type")
		private String type;

		@JsonProperty("driver")
		private String driver;

		@JsonProperty("url")
		private String url;
	}
}
