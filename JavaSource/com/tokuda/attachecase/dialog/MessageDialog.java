package com.tokuda.attachecase.dialog;

import com.jfoenix.controls.JFXDialog;
import com.tokuda.attachecase.gui.ControllerManager;
import com.tokuda.attachecase.gui.main.MainController;

import javafx.scene.control.Label;

/**
 * メッセージダイアログ表示クラス
 *
 * @author s-tokuda
 */
public class MessageDialog extends JFXDialog {

	/**
	 * コンストラクタ
	 *
	 * @param message
	 */
	public MessageDialog(String message) {
		super();

		Label label = new Label(message);
		label.setStyle("-fx-padding: 20 20 20 20; -fx-background-color: #323232; -fx-text-fill: WHITE;");

		setContent(label);
	}

	/**
	 * メッセージダイアログを表示します。
	 */
	public void show() {
		MainController main = ControllerManager.getController(MainController.class);
		main.getStack().setVisible(true);
		main.getPane().setDisable(true);
		super.show(main.getStack());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		super.close();
		MainController main = ControllerManager.getController(MainController.class);
		main.getStack().setVisible(false);
		main.getPane().setDisable(false);
	}
}
