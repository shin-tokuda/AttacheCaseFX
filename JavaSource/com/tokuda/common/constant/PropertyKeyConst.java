package com.tokuda.common.constant;

import lombok.Getter;

public enum PropertyKeyConst {
	// "&"
	Item_And("item.and"),
	// "*"
	Item_Asterisk("item.asterisk"),
	// ""
	Item_Blank("item.blank"),
	// ":"
	Item_Colon("item.colon"),
	// "="
	Item_Equal("item.equal"),
	// "' '"
	Item_HalfSpace("item.halfSpace"),
	// "\n"
	Item_LineBreak("item.lineBreak"),
	// "|"
	Item_Pipe("item.pipe"),
	// "?"
	Item_Question("item.question"),
	// "/"
	Item_Slash("item.slash"),
	// "_"
	Item_UnderScore("item.underScore"),
	// "必須入力です"
	Msg_Err001("msg.err001"),
	// "必須選択です"
	Msg_Err002("msg.err002"),
	// "処理が異常終了しました"
	Msg_Err003("msg.err003"),
	// "ファイルパスを入力"
	Msg_Info001("msg.info001"),
	// "フォルダパスを入力"
	Msg_Info002("msg.info002"),
	// "対象ファイルを選択してください"
	Msg_Info003("msg.info003"),
	// "対象フォルダを選択してください"
	Msg_Info004("msg.info004"),
	// "処理が終了しました"
	Msg_Info005("msg.info005"),
	// "処理を中断しました"
	Msg_Info006("msg.info006"),
	// "実行"
	Psnl001_Button01_Text("psnl001.button01.text"),
	// "エンコード元文字列を入力して下さい"
	Psnl001_Msg_Err001("psnl001.msg.err001"),
	// "【エンコード結果】{0}"
	Psnl001_Msg_Info001("psnl001.msg.info001"),
	// "【デコード結果】{0}"
	Psnl001_Msg_Info002("psnl001.msg.info002"),
	// "参照"
	Sample_Button01_Text("sample.button01.text"),
	// "実行"
	Sample_Button02_Text("sample.button02.text"),
	// "ファイルパスを入力"
	Sample_Text01_Prompt("sample.text01.prompt");

	@Getter
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 */
	private PropertyKeyConst(final String value) {
		this.value = value;
	}
}
