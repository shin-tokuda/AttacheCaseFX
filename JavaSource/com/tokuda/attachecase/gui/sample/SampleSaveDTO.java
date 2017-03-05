package com.tokuda.attachecase.gui.sample;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tokuda.attachecase.gui.BaseSaveDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SampleSaveDTO extends BaseSaveDTO {

	@JsonProperty("text01")
	private String text01;

	@JsonProperty("text02")
	private String text02;
}
