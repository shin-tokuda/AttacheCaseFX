package com.tokuda.common.constant;

import lombok.Getter;

public enum ItemConst {
	// 参照
	Item001("参照"),
	// 実行
	Item002("実行");

	@Getter
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 */
	private ItemConst(final String value) {
		this.value = value;
	}
}
