package com.tokuda.attachecase.dialog;

import com.jfoenix.controls.JFXDialog;
import com.tokuda.attachecase.SystemData;

import javafx.scene.control.Label;

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
		SystemData.stack.setVisible(true);
		SystemData.pane.setDisable(true);
		super.show(SystemData.stack);
	}

	@Override
	public void close() {
		super.close();
		SystemData.stack.setVisible(false);
		SystemData.pane.setDisable(false);
	}
}
