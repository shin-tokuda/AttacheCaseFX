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
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dialog.ExceptionDialog;
import com.tokuda.attachecase.dialog.MessageDialog;
import com.tokuda.attachecase.gui.DefaultController;
import com.tokuda.attachecase.gui.TaskManager;
import com.tokuda.attachecase.jfx.CustomTask;
import com.tokuda.common.constant.PropertyKeyConst;
import com.tokuda.common.util.UtilMessage;
import com.tokuda.common.util.UtilProperty;
import com.tokuda.common.util.UtilString;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lombok.Getter;

@Getter
public class SampleController extends DefaultController<SampleSaveDTO> {

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

		text01.setPromptText(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Sample_Text01_Prompt.getValue())));
		button01.setText(UtilProperty.getValue(PropertyKeyConst.Sample_Button01_Text.getValue()));
		button02.setText(UtilProperty.getValue(PropertyKeyConst.Sample_Button02_Text.getValue()));

		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Err001.getValue())));
		validator.setErrorStyleClass("error-msg");
		text01.getValidators().add(validator);
		text01.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				text01.validate();
			}
		});
	}

	@FXML
	public void onClickButton01(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Info002.getValue())));
		File directory = chooser.showOpenDialog(SystemData.stage);
		text01.validate();

		if (directory != null) {
			text01.setText(directory.getAbsolutePath());
		} else {
			new MessageDialog(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Err002.getValue()))).show();
		}
	}

	@FXML
	public void onClickButton02(ActionEvent event) {

		if (!UtilString.isEmpty(text01.getText()) && Files.exists(Paths.get(text01.getText()))) {
			task01 = new Task01(new File(text01.getText()));
			TaskManager.start(task01);
		}
	}

	@Override
	public void open(final File file) {
		SampleSaveDTO saveDTO = open(file, SampleSaveDTO.class);

		if (saveDTO != null) {
			text01.setText(saveDTO.getText01());
			text02.setText(saveDTO.getText02());
		}
	}

	@Override
	protected void preSave() {
		setSaveDTO(new SampleSaveDTO(text01.getText(), text02.getText()));
	}

	// -----------------------------------------------------------------
	// タスク管理
	// -----------------------------------------------------------------

	private Task01 task01;

	private class Task01 extends CustomTask<Void> {

		private File directory;

		public Task01(final File directory) {
			this.directory = directory;
		}

		@Override
		public Void call() {
			new ExceptionDialog(new NullPointerException()).showAndWait();

			try (Workbook book = WorkbookFactory.create(directory)) {
			} catch (EncryptedDocumentException | InvalidFormatException | IOException ex) {
				new ExceptionDialog(ex).showAndWait();
				throw new RuntimeException();
			}
			return null;
		}
	};
}
