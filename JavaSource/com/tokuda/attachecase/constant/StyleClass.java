package com.tokuda.attachecase.constant;

import lombok.Getter;

public enum StyleClass {
	// error-msg
	ErrorMsg("error-msg");

	@Getter
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 */
	private StyleClass(final String value) {
		this.value = value;
	}
}
