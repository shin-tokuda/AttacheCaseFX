package com.tokuda.attachecase.gui.psnl004;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tokuda.attachecase.gui.BaseSaveDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Psnl004SaveDTO extends BaseSaveDTO {

	@JsonProperty("text01")
	private String text01;

	@JsonProperty("text02")
	private String text02;
}
