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
	// "対象ファイル指定"
	Msg_Info001("msg.info001"),
	// "対象フォルダ指定"
	Msg_Info002("msg.info002"),
	// "対象ファイルを指定して下さい"
	Msg_Info003("msg.info003"),
	// "対象フォルダを指定して下さい"
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
	Psnl002_Button01_Text("psnl002.button01.text"),
	// "ファイル抽出"
	Psnl002_Button02_Text("psnl002.button02.text"),
	// "フォルダパス"
	Psnl002_Label01_Text("psnl002.label01.text"),
	// "コピー対象フォルダパスを入力"
	Psnl002_Text01_Prompt("psnl002.text01.prompt"),
	// "実行"
	Psnl003_Button01_Text("psnl003.button01.text"),
	// "DBタイプ選択"
	Psnl003_Combo01_Prompt("psnl003.combo01.prompt"),
	// "DB接続URL"
	Psnl003_Label01_Text("psnl003.label01.text"),
	// "ユーザID"
	Psnl003_Label02_Text("psnl003.label02.text"),
	// "パスワード"
	Psnl003_Label03_Text("psnl003.label03.text"),
	// "DBタイプを選択して下さい"
	Psnl003_Msg_Err001("psnl003.msg.err001"),
	// "SQLの整形が完了しました"
	Psnl003_Msg_Info001("psnl003.msg.info001"),
	// "更新がコミットされました。"
	Psnl003_Msg_Info002("psnl003.msg.info002"),
	// "更新がロールバックされました"
	Psnl003_Msg_Info003("psnl003.msg.info003"),
	// "[処理時間]{0}msec."
	Psnl003_Msg_Info004("psnl003.msg.info004"),
	// "[検索結果]{0}件"
	Psnl003_Msg_Info005("psnl003.msg.info005"),
	// "選択したDBタイプのデフォルトURLで、DB接続URLを上書きしますか？"
	Psnl003_Msg_Warn001("psnl003.msg.warn001"),
	// "コミットします。よろしいですか？"
	Psnl003_Msg_Warn002("psnl003.msg.warn002"),
	// "実行結果"
	Psnl003_Tab01_Text("psnl003.tab01.text"),
	// "検索結果"
	Psnl003_Tab02_Text("psnl003.tab02.text"),
	// "DB接続URLを入力"
	Psnl003_Text01_Prompt("psnl003.text01.prompt"),
	// "ユーザIDを入力"
	Psnl003_Text02_Prompt("psnl003.text02.prompt"),
	// "パスワードを入力"
	Psnl003_Text03_Prompt("psnl003.text03.prompt"),
	// "指定"
	Psnl004_Button01_Text("psnl004.button01.text"),
	// "DB接続"
	Psnl004_Button02_Text("psnl004.button02.text"),
	// "DB切断"
	Psnl004_Button03_Text("psnl004.button03.text"),
	// "ログ検索SQLを実行"
	Psnl004_Button04_Text("psnl004.button04.text"),
	// "検索結果をExcel形式で出力"
	Psnl004_Button05_Text("psnl004.button05.text"),
	// "解析対象フォルダ"
	Psnl004_Label01_Text("psnl004.label01.text"),
	// "解析対象のフォルダを指定して下さい"
	Psnl004_Msg_Err001("psnl004.msg.err001"),
	// "SQLの整形が完了しました"
	Psnl004_Msg_Info001("psnl004.msg.info001"),
	// "既に解析済みのフォルダです。再解析しますか？"
	Psnl004_Msg_Info002("psnl004.msg.info002"),
	// "解析対象フォルダを指定"
	Psnl004_Text01_Prompt("psnl004.text01.prompt"),
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
