package com.tokuda.attachecase.gui.sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.tokuda.attachecase.ControllerManager;
import com.tokuda.attachecase.DefaultController;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.constant.StyleClass;
import com.tokuda.attachecase.dialog.MessageDialog;
import com.tokuda.attachecase.gui.main.MainController;
import com.tokuda.common.constant.ItemConst;
import com.tokuda.common.constant.MessageConst;
import com.tokuda.common.util.UtilMessage;
import com.tokuda.common.util.UtilString;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lombok.Getter;

@Getter
public class SampleController extends DefaultController {

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private Pane pane;

	@FXML
	private JFXTextField text01;

	@FXML
	private JFXTextArea text02;

	@FXML
	private JFXButton button01;

	@FXML
	private JFXButton button02;

	@FXML
	public void initialize() {
		MainController main = ControllerManager.getController(MainController.class);

		text01.setPromptText(UtilMessage.build(MessageConst.InfoMsg001));
		button01.setText(ItemConst.Item001.getValue());
		button02.setText(ItemConst.Item002.getValue());

		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage(UtilMessage.build(MessageConst.ErrMsg001));
		validator.setErrorStyleClass(StyleClass.ErrorMsg.getValue());
		text01.getValidators().add(validator);
		text01.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				text01.validate();
			}
		});

		main.getProgress().setProgress(0);
	}

	@FXML
	public void onClickButton01(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle(UtilMessage.build(MessageConst.InfoMsg002));
		File directory = chooser.showOpenDialog(SystemData.stage);
		text01.validate();

		if (directory != null) {
			text01.setText(directory.getAbsolutePath());
		} else {
			new MessageDialog(UtilMessage.build(MessageConst.ErrMsg002)).show();
		}
	}

	@FXML
	public void onClickButton02(ActionEvent event) {
		MainController main = ControllerManager.getController(MainController.class);

		if (!UtilString.isEmpty(text01.getText()) && Files.exists(Paths.get(text01.getText()))) {
			task01 = new Task01(new File(text01.getText()));
			main.getProgress().progressProperty().bind(task01.progressProperty());
			new Thread(task01).start();
		}
	}

	// -----------------------------------------------------------------
	// タスク管理
	// -----------------------------------------------------------------

	private Task01 task01;

	private class Task01 extends Task<Void> {

		private File directory;

		public Task01(final File directory) {
			this.directory = directory;
		}

		@Override
		public Void call() {
			final int max = 3;
			updateProgress(1, max);

			try (Workbook book = WorkbookFactory.create(directory)) {
				updateProgress(2, max);
			} catch (EncryptedDocumentException | InvalidFormatException | IOException ex) {
				ex.printStackTrace();
			}
			updateProgress(3, max);
			new MessageDialog(UtilMessage.build(MessageConst.InfoMsg003)).show();
			return null;
		}
	};
}
