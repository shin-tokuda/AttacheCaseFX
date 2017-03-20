package com.tokuda.attachecase.gui.psnl003;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tokuda.attachecase.gui.BaseSaveDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Psnl003SaveDTO extends BaseSaveDTO {

	@JsonProperty("combo01")
	private String combo01;

	@JsonProperty("text01")
	private String text01;

	@JsonProperty("text02")
	private String text02;

	@JsonProperty("text03")
	private String text03;

	@JsonProperty("text04")
	private String text04;
}
