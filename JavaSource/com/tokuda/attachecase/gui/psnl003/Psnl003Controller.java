package com.tokuda.attachecase.gui.psnl003;

import java.io.File;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dialog.ConfirmDialog;
import com.tokuda.attachecase.dto.ConfigDTO;
import com.tokuda.attachecase.gui.DefaultController;
import com.tokuda.attachecase.gui.TaskManager;
import com.tokuda.attachecase.jfx.CustomTask;
import com.tokuda.common.constant.PropertyKeyConst;
import com.tokuda.common.util.UtilMessage;
import com.tokuda.common.util.UtilProperty;
import com.tokuda.common.util.UtilString;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import lombok.Getter;

@Getter
public class Psnl003Controller extends DefaultController<Psnl003SaveDTO> {

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private SplitPane pane;

	@FXML
	private Label label01;

	@FXML
	private Label label02;

	@FXML
	private Label label03;

	@FXML
	private JFXComboBox<Label> combo01;

	@FXML
	private JFXTextField text01;

	@FXML
	private JFXTextField text02;

	@FXML
	private JFXTextField text03;

	@FXML
	private JFXButton button01;

	@FXML
	private JFXTextArea text04;

	@FXML
	private Tab tab01;

	@FXML
	private Tab tab02;

	@FXML
	private JFXTreeTableView table01;

	@FXML
	public void initialize() {
		combo01.setPromptText(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Combo01_Prompt.getValue())));
		SystemData.config.getDrivers().stream().forEach(dto -> {
			combo01.getItems().add(new Label(dto.getType()));
		});

		label01.setText(UtilProperty.getValue(PropertyKeyConst.Psnl003_Label01_Text.getValue()));
		label02.setText(UtilProperty.getValue(PropertyKeyConst.Psnl003_Label02_Text.getValue()));
		label03.setText(UtilProperty.getValue(PropertyKeyConst.Psnl003_Label03_Text.getValue()));
		text01.setPromptText(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Text01_Prompt.getValue())));
		text02.setPromptText(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Text02_Prompt.getValue())));
		text03.setPromptText(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Text03_Prompt.getValue())));
		button01.setText(UtilProperty.getValue(PropertyKeyConst.Psnl003_Button01_Text.getValue()));
		tab01.setText(UtilProperty.getValue(PropertyKeyConst.Psnl003_Tab01_Text.getValue()));
		tab02.setText(UtilProperty.getValue(PropertyKeyConst.Psnl003_Tab02_Text.getValue()));
	}

	@FXML
	public void onChangeCombo01(ActionEvent event) {
		String url = null;

		for (ConfigDTO.DriverDTO driver : SystemData.config.getDrivers()) {

			if (combo01.getValue().getText().equals(driver.getType())) {
				url = driver.getUrl();
				break;
			}
		}
		boolean changeFlg = true;

		if (!UtilString.isEmpty(text01.getText())) {
			Optional<ButtonType> result = new ConfirmDialog(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Msg_Warn001.getValue())))
					.showAndWait();

			if (result.isPresent() && result.get() != ButtonType.OK) {
				changeFlg = false;
			}
		}

		if (changeFlg) {
			text01.setText(url);
		}
	}

	@FXML
	public void onClickButton01(ActionEvent event) {
		task01 = new Task01();
		TaskManager.start(task01);
	}

	@Override
	public void open(final File file) {
		Psnl003SaveDTO saveDTO = open(file, Psnl003SaveDTO.class);

		if (saveDTO != null) {
			combo01.setValue(new Label(saveDTO.getCombo01()));
			text01.setText(saveDTO.getText01());
			text02.setText(saveDTO.getText02());
			text03.setText(saveDTO.getText03());
			text04.setText(saveDTO.getText04());
		}
	}

	@Override
	protected void preSave() {
		setSaveDTO(new Psnl003SaveDTO(combo01.getValue().getText(), text01.getText(), text02.getText(), text03.getText(), text04.getText()));
	}

	// -----------------------------------------------------------------
	// タスク管理
	// -----------------------------------------------------------------

	private Task01 task01;

	private class Task01 extends CustomTask<Void> {

		@Override
		public Void call() {
			return null;
		}
	};
}
