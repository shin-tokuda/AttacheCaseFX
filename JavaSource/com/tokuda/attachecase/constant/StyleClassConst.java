package com.tokuda.attachecase.constant;

import lombok.Getter;

public enum StyleClassConst {
	// error-msg
	ErrorMsg("error-msg");

	@Getter
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 */
	private StyleClassConst(final String value) {
		this.value = value;
	}
}
