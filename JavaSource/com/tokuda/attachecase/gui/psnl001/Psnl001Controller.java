package com.tokuda.attachecase.gui.psnl001;

import java.io.File;

import org.apache.commons.codec.binary.Base64;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.control.SplitPane;
import lombok.Getter;

@Getter
public class Psnl001Controller extends DefaultController<Psnl001SaveDTO> {

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private SplitPane pane;

	@FXML
	private JFXTextArea text01;

	@FXML
	private JFXButton button01;

	@FXML
	private JFXTextArea text02;

	@FXML
	public void initialize() {
		button01.setText(UtilProperty.getValue(PropertyKeyConst.Psnl001_Button01_Text.getValue()));
	}

	@Override
	public void open(final File file) {
		Psnl001SaveDTO saveDTO = open(file, Psnl001SaveDTO.class);

		if (saveDTO != null) {
			text01.setText(saveDTO.getText01());
			text02.setText(saveDTO.getText02());
		}
	}

	@Override
	protected void preSave() {
		setSaveDTO(new Psnl001SaveDTO(text01.getText(), text02.getText()));
	}

	@FXML
	public void onClickButton01(ActionEvent event) {

		if (!UtilString.isEmpty(text01.getText())) {
			task01 = new Task01();
			TaskManager.start(task01);
		} else {
			// エンコード元文字列が入力されていなければエラー。
			new MessageDialog(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl001_Msg_Err001.getValue()))).show();
		}
	}

	// -----------------------------------------------------------------
	// タスク管理
	// -----------------------------------------------------------------

	private Task01 task01;

	private class Task01 extends CustomTask<Void> {

		@Override
		public Void call() {

			text01.getText();
			StringBuffer resultBuff = new StringBuffer();
			String input = text01.getText();

			byte[] inputStringByte = input.getBytes();

			// 文字列をBase64にエンコード
			byte[] resultStringBtye = Base64.encodeBase64(inputStringByte);

			// Base64にエンコードした結果を出力
			String resultString = new String(resultStringBtye);
			resultBuff.append(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl001_Msg_Info001.getValue()), resultString)
					+ UtilProperty.getValue(PropertyKeyConst.Item_LineBreak.getValue()));

			// Base64にエンコードした結果をデコード
			resultStringBtye = resultString.getBytes();
			inputStringByte = Base64.decodeBase64(resultStringBtye);

			// デコードした結果を出力
			resultBuff.append(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl001_Msg_Info002.getValue()), new String(inputStringByte))
					+ UtilProperty.getValue(PropertyKeyConst.Item_LineBreak.getValue()) + UtilProperty.getValue(PropertyKeyConst.Item_LineBreak.getValue()));

			// 結果をテキストエリアへ設定
			text02.setText(resultBuff.toString());

			return null;
		}
	};
}
