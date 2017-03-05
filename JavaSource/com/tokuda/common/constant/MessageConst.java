package com.tokuda.common.constant;

import lombok.Getter;

public enum MessageConst {
	// InfoMsg001
	InfoMsg001("ファイルパスを入力"),
	// InfoMsg002
	InfoMsg002("フォルダパスを入力"),
	// InfoMsg003
	InfoMsg003("対象ファイルを選択してください"),
	// InfoMsg004
	InfoMsg004("対象フォルダを選択してください"),
	// InfoMsg003
	InfoMsg005("処理が終了しました"),
	// ErrMsg001
	ErrMsg001("必須入力です"),
	// ErrMsg002
	ErrMsg002("必須選択です"),
	// ErrMsg003
	ErrMsg003("エラーが発生しました");

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
