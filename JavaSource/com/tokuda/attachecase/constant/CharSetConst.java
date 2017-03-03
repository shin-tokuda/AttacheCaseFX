package com.tokuda.attachecase.constant;

import lombok.Getter;

public enum CharSetConst {
	// Shift_JIS
	ShiftJIS("Shift_JIS"),
	// UTF-8
	Utf8("UTF-8");

	@Getter
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 */
	private CharSetConst(final String value) {
		this.value = value;
	}
}
