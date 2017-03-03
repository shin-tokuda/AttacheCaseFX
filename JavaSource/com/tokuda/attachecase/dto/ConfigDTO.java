package com.tokuda.attachecase.dto;

import java.util.List;

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
public class ConfigDTO {

	@JsonProperty("app_name")
	private String title;

	@JsonProperty("applications")
	private List<ApplicationDTO> applications;

	@JsonProperty("drivers")
	private List<DriverDTO> drivers;
}
