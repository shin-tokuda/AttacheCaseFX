package com.tokuda.attachecase.dialog;

import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.tokuda.attachecase.gui.ControllerManager;
import com.tokuda.attachecase.gui.main.MainController;

/**
 * メッセージスナックバー表示クラス
 *
 * @author s-tokuda
 */
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
		MainController main = ControllerManager.getController(MainController.class);
		main.getSnack().fireEvent(new SnackbarEvent(message));
	}
}
