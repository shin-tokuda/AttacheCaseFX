package com.tokuda.attachecase.dialog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import com.tokuda.attachecase.SystemData;
import com.tokuda.common.constant.PropertyKeyConst;
import com.tokuda.common.util.UtilMessage;
import com.tokuda.common.util.UtilProperty;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;

/**
 * 例外表示ダイアログ表示クラス
 *
 * @author s-tokuda
 */
public class ExceptionDialog {

	protected Throwable throwable;

	protected double xOffset = 0;

	protected double yOffset = 0;

	/**
	 * コンストラクタ
	 *
	 * @param throwable
	 */
	public ExceptionDialog(final Throwable throwable) {
		this.throwable = throwable;
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
		Alert result = new Alert(AlertType.ERROR);
		result.initStyle(StageStyle.TRANSPARENT);
		result.initOwner(SystemData.stage);
		result.setTitle(null);
		result.setHeaderText(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Err003.getValue())));
		result.setContentText(throwable.getClass().getName());

		result.getDialogPane().setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		result.getDialogPane().setOnMouseDragged(event -> {
			result.setX(event.getScreenX() - xOffset);
			result.setY(event.getScreenY() - yOffset);
		});

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		result.getDialogPane().setExpandableContent(expContent);

		return result;
	}
}
