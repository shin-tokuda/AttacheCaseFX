package com.tokuda.attachecase.gui.psnl001;

import java.io.File;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.tokuda.attachecase.dialog.ConfirmDialog;
import com.tokuda.attachecase.dialog.ExceptionDialog;
import com.tokuda.attachecase.dialog.MessageSnackBar;
import com.tokuda.attachecase.gui.DefaultController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lombok.Getter;

@Getter
public class Psnl001Controller extends DefaultController<Psnl001SaveDTO> {

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private AnchorPane pane;

	@FXML
	private JFXButton button01;

	@FXML
	private JFXButton button02;

	@FXML
	private WebView web01;

	@FXML
	public void initialize() {
		button01.setText("ダイアログ表示");
		button02.setText("内部ダイアログ表示");

		final WebEngine eng = web01.getEngine();
		eng.load("/resource/image/ic_add_circle_black_24px.svg");
	}

	@Override
	public void open(final File file) {
		open(file, Psnl001SaveDTO.class);
	}

	@Override
	protected void preSave() {
		setSaveDTO(new Psnl001SaveDTO());
	}

	@FXML
	public void onClickButton01(ActionEvent event) {

		Optional<ButtonType> result = new ConfirmDialog("aaa").showAndWait();
		result.ifPresent(consumer -> {

			if (consumer == ButtonType.OK) {
				new MessageSnackBar("bbb").show();
			}
		});
	}

	@FXML
	public void onClickButton02(ActionEvent event) {

		Optional<ButtonType> result = new ExceptionDialog(new NullPointerException()).showAndWait();
		result.ifPresent(consumer -> {

			if (consumer == ButtonType.OK) {
				new MessageSnackBar("bbb").show();
			}
		});
	}

	// -----------------------------------------------------------------
	// タスク管理
	// -----------------------------------------------------------------
}
