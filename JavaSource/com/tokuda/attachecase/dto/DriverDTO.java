package com.tokuda.attachecase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DBドライバー情報DTO
 *
 * @author s-tokuda
 */
@Data
public class DriverDTO {

	@JsonProperty("type")
	private String type;

	@JsonProperty("driver")
	private String driver;

	@JsonProperty("url")
	private String url;
}
