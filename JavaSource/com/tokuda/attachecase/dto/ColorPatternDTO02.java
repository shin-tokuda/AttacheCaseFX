package com.tokuda.attachecase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * カラーパターンDTO - 第2階層
 *
 * @author s-tokuda
 */
@Getter
@Setter
public class ColorPatternDTO02 {

	@JsonProperty("pattern")
	private String pattern;

	@JsonProperty("50")
	private String p50;

	@JsonProperty("100")
	private String p100;

	@JsonProperty("200")
	private String p200;

	@JsonProperty("300")
	private String p300;

	@JsonProperty("400")
	private String p400;

	@JsonProperty("500")
	private String p500;

	@JsonProperty("600")
	private String p600;

	@JsonProperty("700")
	private String p700;

	@JsonProperty("800")
	private String p800;

	@JsonProperty("900")
	private String p900;

	@JsonProperty("A100")
	private String pa100;

	@JsonProperty("A200")
	private String pa200;

	@JsonProperty("A400")
	private String pa400;

	@JsonProperty("A700")
	private String pa700;
}
