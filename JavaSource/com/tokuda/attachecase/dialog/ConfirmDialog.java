package com.tokuda.attachecase.dialog;

import java.util.Optional;

import com.tokuda.attachecase.SystemData;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 * 確認ダイアログ表示クラス
 *
 * @author s-tokuda
 */
public class ConfirmDialog {

	private final Alert alert;

	/**
	 * コンストラクタ
	 *
	 * @param message
	 */
	public ConfirmDialog(final String message) {
		alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.TRANSPARENT);
		alert.initOwner(SystemData.stage);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText(message);
	}

	/**
	 * 確認ダイアログを表示します。
	 */
	public Optional<ButtonType> showAndWait() {
		return alert.showAndWait();
	}
}
