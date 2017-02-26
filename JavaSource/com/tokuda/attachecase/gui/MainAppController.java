package com.tokuda.attachecase.gui;

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
import com.tokuda.attachecase.constant.StyleClassConst;
import com.tokuda.attachecase.dialog.MessageDialog;
import com.tokuda.common.constant.ItemConst;
import com.tokuda.common.constant.MessageConst;
import com.tokuda.common.util.UtilMessage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;

public class MainAppController {

	@FXML
	private Pane pane;

	@FXML
	private StackPane stack;

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
		SystemData.pane = pane;
		SystemData.stack = stack;

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
	}

	@FXML
	public void onClickButton01(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle(UtilMessage.build(MessageConst.InfoMsg002));
		File directory = chooser.showDialog(SystemData.stage);
		text01.validate();

		if (directory != null) {
			text01.setText(directory.getAbsolutePath());
		} else {
			MessageDialog dialog = new MessageDialog(UtilMessage.build(MessageConst.ErrMsg002));
			dialog.show();
		}
	}

	@FXML
	public void onClickButton02(ActionEvent event) {

		if (Files.exists(Paths.get(text01.getText()))) {
			File directory = new File(text01.getText());

			try (Workbook book = WorkbookFactory.create(directory)) {

			} catch (EncryptedDocumentException | InvalidFormatException | IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
