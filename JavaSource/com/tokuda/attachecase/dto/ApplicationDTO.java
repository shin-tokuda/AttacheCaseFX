package com.tokuda.attachecase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * アプリケーション情報DTO
 *
 * @author s-tokuda
 */
@Data
public class ApplicationDTO {

	@JsonProperty("app_id")
	private String appId;

	@JsonProperty("title")
	private String title;

	@JsonProperty("icon")
	private String icon;

	@JsonProperty("extension")
	private String extension;
}
