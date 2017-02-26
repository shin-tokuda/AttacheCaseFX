package com.tokuda.attachecase.dialog;

import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.tokuda.attachecase.SystemData;

public class MessageSnackBar {

	private final String message;

	/**
	 * コンストラクタ
	 *
	 * @param message
	 */
	public MessageSnackBar(String message) {
		this.message = message;
	}

	/**
	 * メッセージスナックバーを表示します。
	 */
	public void show() {
		SystemData.snack.fireEvent(new SnackbarEvent(message));
	}
}
