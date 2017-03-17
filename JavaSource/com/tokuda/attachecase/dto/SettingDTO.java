package com.tokuda.attachecase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 設定情報DTO
 *
 * @author s-tokuda
 */
@Getter
@Setter
public class SettingDTO {

	@JsonProperty("initial_width")
	private double initialWidth;

	@JsonProperty("initial_height")
	private double initialHeight;

	@JsonProperty("primary_color")
	private String primaryColor;

	@JsonProperty("secondary_color")
	private String secondaryColor;
}
