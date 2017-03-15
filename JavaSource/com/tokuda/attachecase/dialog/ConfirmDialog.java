package com.tokuda.attachecase.dialog;

import java.util.Optional;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import com.tokuda.attachecase.SystemData;

import javafx.application.Platform;
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

	protected final String message;

	protected double xOffset = 0;

	protected double yOffset = 0;

	/**
	 * コンストラクタ
	 *
	 * @param message
	 */
	public ConfirmDialog(final String message) {
		this.message = message;
	}

	/**
	 * 確認ダイアログを表示します。
	 */
	public Optional<ButtonType> showAndWait() {
		Optional<ButtonType> result = null;

		try {

			if (Platform.isFxApplicationThread()) {
				result = createAlert().showAndWait();
			} else {
				RunnableFuture<Optional<ButtonType>> future = new FutureTask<>(() -> {
					return createAlert().showAndWait();
				});
				Platform.runLater(future);
				result = future.get();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * Alertダイアログを作成します。
	 *
	 * @return Alertダイアログ
	 */
	protected Alert createAlert() {
		Alert result = new Alert(AlertType.CONFIRMATION);
		result.initStyle(StageStyle.TRANSPARENT);
		result.initOwner(SystemData.stage);
		result.setTitle(null);
		result.setHeaderText(null);
		result.setContentText(message);

		result.getDialogPane().setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		result.getDialogPane().setOnMouseDragged(event -> {
			result.setX(event.getScreenX() - xOffset);
			result.setY(event.getScreenY() - yOffset);
		});
		return result;
	}
}
