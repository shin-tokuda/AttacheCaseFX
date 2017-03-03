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
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.tokuda.attachecase.BaseController;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.constant.StyleClassConst;
import com.tokuda.attachecase.dialog.MessageDialog;
import com.tokuda.attachecase.dialog.MessageSnackBar;
import com.tokuda.common.constant.ItemConst;
import com.tokuda.common.constant.MessageConst;
import com.tokuda.common.util.UtilMessage;
import com.tokuda.common.util.UtilString;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

public class SampleController extends BaseController {

	// -----------------------------------------------------------------
	// インスタンス管理
	// -----------------------------------------------------------------

	static {
		load(SampleController.class);
	}

	public static BaseController getInstance() {
		return instance;
	}

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private Pane pane;

	@FXML
	private StackPane stack;

	@FXML
	private JFXSnackbar snack;

	@FXML
	private JFXTextField text01;

	@FXML
	private JFXTextArea text02;

	@FXML
	private JFXButton button01;

	@FXML
	private JFXButton button02;

	@FXML
	private JFXProgressBar progress;

	@FXML
	public void initialize() {
		snack.registerSnackbarContainer(pane);
		SystemData.pane = pane;
		SystemData.stack = stack;
		SystemData.snack = snack;

		text01.setPromptText(UtilMessage.build(MessageConst.InfoMsg001));
		button01.setText(ItemConst.Item001.getValue());
		button02.setText(ItemConst.Item002.getValue());

		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage(UtilMessage.build(MessageConst.ErrMsg001));
		validator.setErrorStyleClass(StyleClassConst.ErrorMsg.getValue());
		text01.getValidators().add(validator);
		text01.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				text01.validate();
			}
		});

		progress.setProgress(0);
	}

	@FXML
	public void onClickButton01(ActionEvent event) {
		// DirectoryChooser chooser = new DirectoryChooser();
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

		if (!UtilString.isEmpty(text01.getText()) && Files.exists(Paths.get(text01.getText()))) {
			task01 = new Task01(new File(text01.getText()));
			progress.progressProperty().bind(task01.progressProperty());
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
			new MessageSnackBar(UtilMessage.build(MessageConst.InfoMsg003)).show();
			return null;
		}
	};
}
