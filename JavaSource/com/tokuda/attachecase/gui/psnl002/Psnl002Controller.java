package com.tokuda.attachecase.gui.psnl002;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dialog.MessageDialog;
import com.tokuda.attachecase.gui.DefaultController;
import com.tokuda.attachecase.gui.TaskManager;
import com.tokuda.attachecase.jfx.CustomTask;
import com.tokuda.common.constant.PropertyKeyConst;
import com.tokuda.common.util.UtilMessage;
import com.tokuda.common.util.UtilProperty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import lombok.Getter;

@Getter
public class Psnl002Controller extends DefaultController<Psnl002SaveDTO> {

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private Pane pane;

	@FXML
	private Label label01;

	@FXML
	private JFXTextField text01;

	@FXML
	private JFXButton button01;

	@FXML
	private JFXButton button02;

	@FXML
	private JFXTextArea text02;

	@FXML
	public void initialize() {
		label01.setText(UtilProperty.getValue(PropertyKeyConst.Psnl002_Label01_Text.getValue()));
		text01.setPromptText(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl002_Text01_Prompt.getValue())));
		button01.setText(UtilProperty.getValue(PropertyKeyConst.Psnl002_Button01_Text.getValue()));
		button02.setText(UtilProperty.getValue(PropertyKeyConst.Psnl002_Button02_Text.getValue()));
	}

	@FXML
	public void onClickButton01(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Info002.getValue())));
		File directory = chooser.showDialog(SystemData.stage);

		if (directory != null) {
			text01.setText(directory.getAbsolutePath());
		}
	}

	@FXML
	public void onClickButton02(ActionEvent event) {
		File srcDir = new File(text01.getText());

		if (srcDir.exists() && srcDir.isDirectory()) {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Info002.getValue())));
			File directory = chooser.showDialog(SystemData.stage);

			if (directory != null) {
				task01 = new Task01(directory);
				TaskManager.start(task01);
			}
		} else {
			new MessageDialog(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Info004.getValue()))).show();
		}
	}

	@Override
	public void open(final File file) {
		Psnl002SaveDTO saveDTO = open(file, Psnl002SaveDTO.class);

		if (saveDTO != null) {
			text01.setText(saveDTO.getText01());
			text02.setText(saveDTO.getText02());
		}
	}

	@Override
	protected void preSave() {
		setSaveDTO(new Psnl002SaveDTO(text01.getText(), text02.getText()));
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
		public Void call() throws Exception {
			String[] rowData = text02.getText().split("\n");

			String srcDirectory = text01.getText();
			if (!srcDirectory.endsWith("\\")) {
				srcDirectory = srcDirectory + "\\";
			}

			for (String path : rowData) {

				if (isCancelled()) {
					break;
				}
				File copyFile = new File(path);

				if (!copyFile.exists() || !copyFile.isFile()) {
					continue;
				}
				String copyPath = (directory.getAbsolutePath() + path.substring((srcDirectory.substring(0, srcDirectory.lastIndexOf("\\"))).length()))
						.replace("\\\\", "\\");
				Files.copy(Paths.get(path), Paths.get(copyPath), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
			}
			return null;
		}
	};
}
