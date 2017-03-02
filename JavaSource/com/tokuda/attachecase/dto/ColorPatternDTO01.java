package com.tokuda.attachecase.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * カラーパターンDTO - 第1階層
 *
 * @author s-tokuda
 */
@Getter
@Setter
public class ColorPatternDTO01 {

	@JsonProperty("selected")
	private String selected;

	@JsonProperty("patterns")
	private List<ColorPatternDTO02> patterns;
}
