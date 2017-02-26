package com.tokuda.common.constant;

import lombok.Getter;

/**
 * 共通文字列定義<br/>
 * <p>
 * Created by s-tokuda on 2015/08/27.
 */
public enum CommonConst {
	// ブランク
	Blank(""),
	// スラッシュ
	Slash("/"),
	// 半角スペース
	HalfSpace(" "),
	// アスタリスク
	Asterisk("*"),
	// クエスチョン・マーク
	Question("?"),
	// コロン
	Colon(":"),
	// イコール
	Equal("="),
	// アンド
	And("&"),
	// パイプ文字
	Pipe("\\|");

	@Getter
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 */
	private CommonConst(final String value) {
		this.value = value;
	}
}
