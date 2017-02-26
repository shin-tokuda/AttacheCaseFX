package com.tokuda.common.constant;

import lombok.Getter;

public enum MessageConst {
	// InfoMsg001
	InfoMsg001("フォルダパスを入力"),
	// InfoMsg002
	InfoMsg002("対象フォルダを選択してください"),
	// ErrMsg001
	ErrMsg001("必須入力です"),
	// ErrMsg002
	ErrMsg002("必須選択です");

	@Getter
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 */
	private MessageConst(final String value) {
		this.value = value;
	}
}
