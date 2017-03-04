package com.tokuda.attachecase.dialog;

import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.tokuda.attachecase.ControllerManager;
import com.tokuda.attachecase.gui.main.MainController;

/**
 * 注意！！スレッドからの呼び出しはできません。
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
