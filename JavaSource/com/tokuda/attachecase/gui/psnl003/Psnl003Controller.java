package com.tokuda.attachecase.gui.psnl003;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dialog.ConfirmDialog;
import com.tokuda.attachecase.dialog.ExceptionDialog;
import com.tokuda.attachecase.dialog.MessageDialog;
import com.tokuda.attachecase.dialog.MessageSnackBar;
import com.tokuda.attachecase.dto.ConfigDTO;
import com.tokuda.attachecase.gui.DefaultController;
import com.tokuda.attachecase.gui.TaskManager;
import com.tokuda.attachecase.jfx.CustomTask;
import com.tokuda.common.constant.PropertyKeyConst;
import com.tokuda.common.util.UtilConnection;
import com.tokuda.common.util.UtilMessage;
import com.tokuda.common.util.UtilProperty;
import com.tokuda.common.util.UtilSQL;
import com.tokuda.common.util.UtilString;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

		text04.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			final KeyCombination ctrlEnter = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);
			final KeyCombination ctrlQ = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);

			@Override
			public void handle(KeyEvent event) {

				if (ctrlEnter.match(event)) {
					onClickButton01(new ActionEvent());

				} else if (ctrlQ.match(event)) {
					boolean select = true;

					try {

						// 整形元の文字列を取得
						String input = text04.getSelectedText();
						if (input == null || input.length() == 0) {
							input = text04.getText();
							select = false;
						}

						// 対象文字列からコメント行を除去
						String sql = UtilSQL.removeComment(input);
						// SQLを整形
						sql = UtilSQL.editSqlIndent(sql);

						// 整形後の文字列をテキストエリアに設定
						if (!select) {
							text04.setText(sql);
						} else {
							text04.replaceSelection(sql);
						}

						// 正常終了
						new MessageSnackBar(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Msg_Info001.getValue()))).show();
					} catch (Exception ex) {
						new ExceptionDialog(ex).showAndWait();
					}
				}
			}
		});
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
		String type = combo01.getValue() != null ? combo01.getValue().getText() : null;

		if (type != null) {
			task01 = new Task01();
			TaskManager.start(task01);
		} else {
			new MessageDialog(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Msg_Err001.getValue()))).show();
		}
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

	private class Task01 extends CustomTask<SearchResponse> {

		@Override
		public SearchResponse call() throws Exception {
			SearchResponse result = new SearchResponse();

			String driver = null;
			for (ConfigDTO.DriverDTO info : SystemData.config.getDrivers()) {

				if (info.getType().equals(combo01.getValue().getText())) {
					driver = info.getDriver();
					break;
				}
			}

			String input = text04.getSelectedText();
			if (input == null || input.length() == 0) {
				input = text04.getText();
			}

			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				// DB接続を確立
				connection = UtilConnection.getDBConnection(connection, driver, text01.getText(), text02.getText(), text03.getText());
				UtilConnection.setAutoCommit(connection, false);

				// ｢//｣から始まる行については除外した上でSQLを実行する
				String[] lines = input.split(System.lineSeparator());
				StringBuffer buffer = new StringBuffer();
				for (String line : lines) {
					if (line.startsWith("//"))
						continue;
					buffer.append(line.trim() + " ");
				}

				String sql = buffer.toString().trim();
				if (!sql.equals("")) {
					pstmt = connection.prepareStatement(sql);

					// SQL実行
					if (!sql.toLowerCase().startsWith("select")) {
						// select以外のSQL実行

						// SQLを実行し、実行時間を実行結果タブに出力
						long startTime = System.currentTimeMillis();
						pstmt.executeUpdate();
						long endTime = System.currentTimeMillis();
						result.getMessage().append(
								UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Msg_Info004.getValue()), String.valueOf(endTime - startTime)));
						result.getMessage().append(System.lineSeparator());

						// DB更新をコミットまたはロールバックする
						Optional<ButtonType> confirm = new ConfirmDialog(
								UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Msg_Warn002.getValue()))).showAndWait();

						if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
							UtilConnection.commit(connection);
							result.getMessage().append(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Msg_Info002.getValue())));
						} else {
							UtilConnection.rollback(connection);
							result.getMessage().append(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Msg_Info003.getValue())));
						}

						// TODO 未実装
						// 実行結果タブを表示
						// getTabbedPane1().setSelectedComponent(getScrollPane2());

					} else {
						// selectのSQL実行

						// SQLを実行し、実行前後の時間を取得
						long startTime = System.currentTimeMillis();
						rs = pstmt.executeQuery();
						ResultSetMetaData rsm = rs.getMetaData();
						long endTime = System.currentTimeMillis();

						// select結果のヘッダーを取得
						for (int i = 0; i < rsm.getColumnCount(); i++) {
							int index = i;

							TreeTableColumn<Record, String> header = new TreeTableColumn<>(rsm.getColumnName(i + 1));
							header.setCellValueFactory((TreeTableColumn.CellDataFeatures<Record, String> param) -> {
								return param.getValue().getValue().getCells().get(index);
							});
							result.getColumns().add(header);
						}

						// select結果を検索結果タブの表示用テーブルに設定
						while (rs.next()) {
							Record record = new Record();

							for (int i = 0; i < rsm.getColumnCount(); i++) {
								record.getCells().add(new SimpleStringProperty(rs.getString(i + 1)));
							}
							result.getRecords().add(record);
						}

						// 実行時間及び検索結果件数を実行結果タブに出力
						result.getMessage().append(
								UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Msg_Info004.getValue()), String.valueOf(endTime - startTime)));
						result.getMessage().append(System.lineSeparator());
						result.getMessage().append(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl003_Msg_Info005.getValue()),
								String.valueOf(result.getRecords().size())));

						// TODO 未実装
						// 検索結果タブを表示
						// getTabbedPane1().setSelectedComponent(getScrollPane3());
					}
				}

			} finally {
				// 異常終了を考慮し、このタイミングでトランザクションのアクティブ状態を解除する。
				UtilConnection.rollback(connection);

				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (connection != null) {
					UtilConnection.close(connection);
				}
			}
			return result;
		}

		@Override
		protected void succeeded() {
			SearchResponse response = getValue();

			if (response.getMessage().length() > 0) {
				tab01.setContent(new JFXTextArea(response.getMessage().toString()));
			}

			if (!response.getColumns().isEmpty()) {
				TreeTableView<Record> table = new TreeTableView<>();
				tab02.setContent(table);

				table.setRoot(new RecursiveTreeItem<Record>(response.getRecords(), RecursiveTreeObject::getChildren));
				table.setShowRoot(false);
				table.setEditable(false);
				table.getColumns().addAll(response.getColumns());
			}
			super.succeeded();
		}
	};

	// -----------------------------------------------------------------
	// その他
	// -----------------------------------------------------------------

	@Data
	protected static class SearchResponse {

		protected StringBuilder message = new StringBuilder();

		protected List<TreeTableColumn<Record, String>> columns = new ArrayList<>();

		protected ObservableList<Record> records = FXCollections.observableArrayList();
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	protected static class Record extends RecursiveTreeObject<Record> {

		protected List<StringProperty> cells = new ArrayList<>();
	}
}
