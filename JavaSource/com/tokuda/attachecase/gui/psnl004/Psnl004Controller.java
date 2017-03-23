package com.tokuda.attachecase.gui.psnl004;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dialog.ConfirmDialog;
import com.tokuda.attachecase.dialog.ExceptionDialog;
import com.tokuda.attachecase.dialog.MessageDialog;
import com.tokuda.attachecase.dialog.MessageSnackBar;
import com.tokuda.attachecase.gui.DefaultController;
import com.tokuda.attachecase.gui.TaskManager;
import com.tokuda.attachecase.jfx.CustomTask;
import com.tokuda.common.constant.PropertyKeyConst;
import com.tokuda.common.util.UtilConnection;
import com.tokuda.common.util.UtilMessage;
import com.tokuda.common.util.UtilProperty;
import com.tokuda.common.util.UtilSQL;

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
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class Psnl004Controller extends DefaultController<Psnl004SaveDTO> {

	private Connection connection;

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private SplitPane pane;

	@FXML
	private Label label01;

	@FXML
	private JFXTextField text01;

	@FXML
	private JFXButton button01;

	@FXML
	private JFXButton button02;

	@FXML
	private JFXButton button03;

	@FXML
	private JFXButton button04;

	@FXML
	private JFXButton button05;

	@FXML
	private JFXTextArea text02;

	@FXML
	private TreeTableView<Record> table01;

	@FXML
	public void initialize() {
		label01.setText(UtilProperty.getValue(PropertyKeyConst.Psnl004_Label01_Text.getValue()));
		text01.setPromptText(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl004_Text01_Prompt.getValue())));
		button01.setText(UtilProperty.getValue(PropertyKeyConst.Psnl004_Button01_Text.getValue()));
		button02.setText(UtilProperty.getValue(PropertyKeyConst.Psnl004_Button02_Text.getValue()));
		button03.setText(UtilProperty.getValue(PropertyKeyConst.Psnl004_Button03_Text.getValue()));
		button04.setText(UtilProperty.getValue(PropertyKeyConst.Psnl004_Button04_Text.getValue()));
		button05.setText(UtilProperty.getValue(PropertyKeyConst.Psnl004_Button05_Text.getValue()));

		text02.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
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
						String input = text02.getSelectedText();
						if (input == null || input.length() == 0) {
							input = text02.getText();
							select = false;
						}

						// 対象文字列からコメント行を除去
						String sql = UtilSQL.removeComment(input);
						// SQLを整形
						sql = UtilSQL.editSqlIndent(sql);

						// 整形後の文字列をテキストエリアに設定
						if (!select) {
							text02.setText(sql);
						} else {
							text02.replaceSelection(sql);
						}

						// 正常終了
						new MessageSnackBar(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl004_Msg_Info001.getValue()))).show();
					} catch (Exception ex) {
						new ExceptionDialog(ex).showAndWait();
					}
				}
			}
		});
		setComponentsEnabled(false);
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
		String directory = text01.getText();

		if (directory != null && (new File(directory).isDirectory())) {
			boolean create = false;
			File h2 = new File(directory + File.separator + "DB.mv.db");

			if (h2.exists()) {
				Optional<ButtonType> confirm = new ConfirmDialog(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl004_Msg_Info002.getValue())))
						.showAndWait();

				if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
					h2.delete();
					File trace = new File(directory + File.separator + "DB.trace.db");

					if (trace.exists()) {
						trace.delete();
					}
					File lock = new File(directory + File.separator + "DB.lock.db");

					if (lock.exists()) {
						lock.delete();
					}
					create = true;
					connection = UtilConnection.getDBConnection(connection, directory);

				} else {
					connection = UtilConnection.getDBConnection(connection, directory);
				}

			} else {
				connection = UtilConnection.getDBConnection(connection, directory);
				create = true;
			}

			if (create) {

				try {
					// ---------------------------------
					// xmlから設定を読み込み
					// ---------------------------------
					// 設定を格納するリスト
					List<XmlPattern> patternList = new ArrayList<XmlPattern>();

					// xmlを読み込み
					Document document = new SAXBuilder()
							.build(new File(new File(".").getAbsoluteFile().getParent() + File.separator + "conf" + File.separator + "analysis.xml"));
					Element docElement = document.getRootElement();
					List<Element> patterns = docElement.getChildren("pattern");

					for (Element element : patterns) {
						XmlPattern pattern = new XmlPattern(element.getAttributeValue("naming"), element.getAttributeValue("tableId"),
								element.getAttributeValue("encoding"), element.getAttributeValue("breakMark"), null);
						List<Element> columns = element.getChildren();
						XmlColumn[] columnItem = new XmlColumn[columns.size()];

						for (int i = 0; i < columns.size(); i++) {
							element = columns.get(i);
							XmlColumn item = new XmlColumn(element.getAttributeValue("name"), element.getAttributeValue("max"),
									element.getAttributeValue("split"));
							columnItem[i] = item;
						}
						pattern.setColumns(columnItem);
						patternList.add(pattern);
					}

					// ---------------------------------------
					// 読み込んだ情報を元にテーブルを作成
					// ---------------------------------------
					AnalysisDAO dao = new AnalysisDAO();
					for (XmlPattern pattern : patternList) {
						// テーブルを作成
						dao.createTable(connection, pattern);

						// 必要項目にインデックスを作成
						int i = 1;

						for (XmlColumn column : pattern.getColumns()) {

							if (column.getName().equals("DATE") || column.getName().equals("BROWSER")) {
								dao.createIndex(connection, pattern.getTableId(), i, column.getName());
								i++;
							}
						}
					}

					// ---------------------------------------
					// 解析対象のログを読み込み
					// ---------------------------------------
					TaskManager.start(new Task01(new File(directory), patternList));

				} catch (JDOMException | IOException | SQLException ex) {
					new ExceptionDialog(ex).showAndWait();
				}

			} else {
				setComponentsEnabled(true);
			}

		} else {
			new MessageDialog(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Psnl004_Msg_Err001.getValue()))).show();
		}
	}

	@FXML
	public void onClickButton03(ActionEvent event) {
		setComponentsEnabled(false);
		UtilConnection.close(connection);
	}

	@FXML
	public void onClickButton04(ActionEvent event) {
		TaskManager.start(new Task02());
	}

	@FXML
	public void onClickButton05(ActionEvent event) {
		final String extension = "xls";
		FileChooser chooser = new FileChooser();
		chooser.setTitle(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Info001.getValue())));
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XLSファイル (*" + extension + ")", "*" + extension));
		File file = chooser.showSaveDialog(SystemData.stage);

		if (file != null) {

			// ファイル名を取得
			String name = file.getName();

			// 拡張子が入力されていなければ自動挿入
			if (name.indexOf(".") < 0) {
				name = name + "." + extension;
			}
			TaskManager.start(new Task03(new File(file.getParent() + File.separator + name)));
		}
	}

	@Override
	public void open(final File file) {
		Psnl004SaveDTO saveDTO = open(file, Psnl004SaveDTO.class);

		if (saveDTO != null) {
			text01.setText(saveDTO.getText01());
			text02.setText(saveDTO.getText02());
		}
	}

	@Override
	protected void preSave() {
		setSaveDTO(new Psnl004SaveDTO(text01.getText(), text02.getText()));
	}

	// -----------------------------------------------------------------
	// タスク管理
	// -----------------------------------------------------------------

	private class Task01 extends CustomTask<Void> {

		private File directory;

		private List<XmlPattern> patternList;

		public Task01(final File directory, final List<XmlPattern> patternList) {
			this.directory = directory;
			this.patternList = patternList;
		}

		@Override
		public Void call() throws Exception {
			FileVisitor<Path> fv = new FileVisitor01<Path>();
			Files.walkFileTree(directory.toPath(), fv);
			return null;
		}

		@Override
		protected void succeeded() {
			setComponentsEnabled(true);
			super.succeeded();
		}

		@Override
		protected void cancelled() {
			setComponentsEnabled(true);
			super.cancelled();
		}

		@Override
		protected void failed() {
			setComponentsEnabled(true);
			super.failed();
		}

		private class FileVisitor01<T> extends SimpleFileVisitor<T> {

			@Override
			public FileVisitResult visitFile(T f, BasicFileAttributes attribs) {
				Path path = (Path) f;
				File file = path.toFile();

				// パターンに当てはまるか否か
				for (int i = 0; i < patternList.size(); i++) {
					XmlPattern pattern = patternList.get(i);
					XmlColumn[] columns = pattern.getColumns();

					// ファイル名の部分一致
					if (file.getName().toLowerCase().indexOf(pattern.getNaming().toLowerCase()) >= 0) {
						String fileName = file.getPath().substring(directory.getAbsolutePath().length() + 1);

						AnalysisDAO dao = new AnalysisDAO();

						StringBuffer buffer = new StringBuffer();
						String[] record = new String[columns.length + 1];
						record[0] = fileName + "(1)";
						int colIndex = 1;
						int start = 0;
						int end = 0;

						try (InputStream is = new FileInputStream(file);
								Reader r = new InputStreamReader(is, pattern.getEncoding());
								BufferedReader br = new BufferedReader(r)) {
							String line = null;
							int row = 0;

							while ((line = br.readLine()) != null) {
								// --------------------------------------------------------
								// 行の切り替わり
								// --------------------------------------------------------
								row++;
								start = 0;
								end = 0;

								if (colIndex > columns.length) {
									// レコードをDBに登録
									record[colIndex] = buffer.toString().trim();
									dao.insert(connection, pattern.getTableId(), record);

									// 各変数を初期化
									buffer = new StringBuffer();
									record = new String[columns.length + 1];
									record[0] = fileName + "(" + NumberFormat.getNumberInstance().format(row) + ")";
									colIndex = 1;
								} else if (colIndex < columns.length) {
									// 最終カラム以外
									if (line.indexOf(columns[colIndex].getSplit()) >= 0) {
										// 行の切り替わりで、現カラムの区切り文字列が存在する場合、
										// カラム値を配列に設定

										end = line.indexOf(columns[colIndex - 1].getSplit());
										if (buffer.length() > 0)
											buffer.append(System.lineSeparator());
										buffer.append(line.substring(start, end));
										record[colIndex] = buffer.toString().trim();
										buffer = new StringBuffer();
										start = end + columns[colIndex - 1].getSplit().length();
										colIndex++;
									}
								} else {
									// 最終カラム
									if (line.indexOf(pattern.getBreakMark()) > 0) {
										// レコードをDBに登録
										record[colIndex] = buffer.toString().trim();
										dao.insert(connection, pattern.getTableId(), record);

										// 各変数を初期化
										buffer = new StringBuffer();
										record = new String[columns.length + 1];
										record[0] = fileName + "(" + NumberFormat.getNumberInstance().format(row) + ")";
										colIndex = 1;
									}
								}

								while (colIndex < columns.length + 1) {
									// --------------------------------------------------------
									// カラムの切り替わり
									// ※(1)(3)の場合は、カラムはそのままで次行の処理となる。
									// --------------------------------------------------------

									XmlColumn column = columns[colIndex - 1];
									if (colIndex < columns.length) {
										end = line.indexOf(column.getSplit(), start);
									} else {
										end = line.length();
									}

									if (colIndex == columns.length) {
										// (1)

										if (buffer.length() > 0)
											buffer.append(System.lineSeparator());
										buffer.append(line.substring(start));
										break;
									} else if (end >= 0) {
										// (2)

										if (buffer.length() > 0)
											buffer.append(System.lineSeparator());
										buffer.append(line.substring(start, end));
										record[colIndex] = buffer.toString().trim();
										buffer = new StringBuffer();
										start = end + columns[colIndex - 1].getSplit().length();
										colIndex++;
									} else {
										// (3)

										if (buffer.length() > 0)
											buffer.append(System.lineSeparator());
										buffer.append(line.substring(start));
										break;
									}
								}
							}

							if (buffer.length() > 0) {
								// 最終行が途中で途切れていた場合も、レコードをDBに登録
								record[colIndex] = buffer.toString().trim();
								dao.insert(connection, pattern.getTableId(), record);
							}

						} catch (Exception ex) {
							new ExceptionDialog(ex).showAndWait();
						}
						break;
					}
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attribs) {
				// ディレクトリの場合はスルー
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(T file, IOException exception) {
				// ファイルへのアクセスに失敗した場合はスルー
				return FileVisitResult.CONTINUE;
			}
		}
	};

	private class Task02 extends CustomTask<SearchResponse> {

		@Override
		public SearchResponse call() throws Exception {
			SearchResponse result = new SearchResponse();

			String input = text02.getSelectedText();
			if (input == null || input.length() == 0) {
				input = text02.getText();
			}

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				// ｢//｣から始まる行については除外した上でSQLを実行する
				String[] lines = input.split("\n");
				StringBuffer buffer = new StringBuffer();

				for (String line : lines) {
					if (line.startsWith("//"))
						continue;
					buffer.append(line.trim() + " ");
				}

				String sql = buffer.toString().trim();
				if (!sql.equals("")) {

					// SQL実行
					if (!sql.toLowerCase().startsWith("select")) {
						// select以外は不許可
					} else {
						// selectのSQL実行

						pstmt = connection.prepareStatement(sql);
						rs = pstmt.executeQuery();
						ResultSetMetaData rsm = rs.getMetaData();

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
					}
				}

			} finally {

				try {

					if (rs != null) {
						rs.close();
					}

					if (pstmt != null) {
						pstmt.close();
					}

				} catch (Exception ex) {
					throw ex;
				}
			}
			return result;
		}

		@Override
		protected void succeeded() {
			SearchResponse response = getValue();

			if (!response.getColumns().isEmpty()) {
				table01.setRoot(new RecursiveTreeItem<Record>(response.getRecords(), RecursiveTreeObject::getChildren));
				table01.setShowRoot(false);
				table01.setEditable(false);
				table01.getColumns().clear();
				table01.getColumns().addAll(response.getColumns());
			}
			super.succeeded();
		}
	};

	private class Task03 extends CustomTask<Void> {

		private File file;

		public Task03(final File file) {
			this.file = file;
		}

		@Override
		public Void call() throws Exception {

			// 新規ワークブックを作成する
			try (FileOutputStream stream = new FileOutputStream(file); HSSFWorkbook wb = new HSSFWorkbook()) {

				// 新規ワークシートを作成する
				HSSFSheet sheet = wb.createSheet();

				// 作成したシート名を変更
				wb.setSheetName(0, "ログ解析結果");

				final short borderStyle = CellStyle.BORDER_THIN;
				// ヘッダーのスタイルを設定
				CellStyle headerStyle = wb.createCellStyle();
				headerStyle.setAlignment(CellStyle.ALIGN_LEFT);
				headerStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
				headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				headerStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
				headerStyle.setBorderTop(borderStyle);
				headerStyle.setBorderLeft(borderStyle);
				headerStyle.setBorderBottom(borderStyle);
				headerStyle.setBorderRight(borderStyle);
				// レコードのスタイルを設定
				CellStyle recStyle = wb.createCellStyle();
				recStyle.setAlignment(CellStyle.ALIGN_LEFT);
				recStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
				recStyle.setBorderTop(borderStyle);
				recStyle.setBorderLeft(borderStyle);
				recStyle.setBorderBottom(borderStyle);
				recStyle.setBorderRight(borderStyle);

				// ヘッダー出力
				{
					// 行オブジェクトの作成
					HSSFRow row = sheet.createRow(0);

					for (int i = 0; i < table01.getColumns().size(); i++) {
						String col = table01.getColumns().get(i).getText();

						// セルオブジェクトの作成
						HSSFCell cell = row.createCell(i);

						// セルに値を設定する
						cell.setCellValue(col);

						// セルにスタイルを設定する。
						cell.setCellStyle(headerStyle);
					}
				}

				// レコード出力
				for (int i = 1; i <= table01.getRoot().getChildren().size(); i++) {
					int heightMax = 1;
					// 行オブジェクトの作成
					HSSFRow row = sheet.createRow(i);

					for (int j = 0; j < table01.getColumns().size(); j++) {
						String col = table01.getColumns().get(i - 1).getText();
						int height = col.split("\n").length;

						if (heightMax < height) {
							heightMax = height;
						}

						// セルオブジェクトの作成
						HSSFCell cell = row.createCell(j);

						// セルに値を設定する
						cell.setCellValue(col);

						// セルにスタイルを設定する。
						cell.setCellStyle(recStyle);
					}
					row.setHeightInPoints(heightMax * sheet.getDefaultRowHeightInPoints());
				}

				// 列幅を自動調整
				for (int i = 0; i < table01.getColumns().size(); i++) {
					sheet.autoSizeColumn(i);
				}

				// 作成したワークブックを保存する
				wb.write(stream);
			}
			return null;
		}

		@Override
		protected void succeeded() {
			setComponentsEnabled(false);
			super.succeeded();
		}
	};

	// -----------------------------------------------------------------
	// その他
	// -----------------------------------------------------------------

	public void setComponentsEnabled(final boolean dirSelect) {
		text01.setDisable(dirSelect);
		button01.setDisable(dirSelect);
		button02.setDisable(dirSelect);
		button03.setDisable(!dirSelect);
		button04.setDisable(!dirSelect);
		button05.setDisable(!dirSelect);
		text02.setDisable(!dirSelect);
		table01.setDisable(!dirSelect);
	}

	@Data
	protected static class SearchResponse {

		protected List<TreeTableColumn<Record, String>> columns = new ArrayList<>();

		protected ObservableList<Record> records = FXCollections.observableArrayList();
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	protected static class Record extends RecursiveTreeObject<Record> {

		protected List<StringProperty> cells = new ArrayList<>();
	}

	@AllArgsConstructor
	@Data
	protected static class XmlPattern {
		private String naming;
		private String tableId;
		private String encoding;
		private String breakMark;
		private XmlColumn[] columns;
	}

	@AllArgsConstructor
	@Data
	protected static class XmlColumn {
		private String name;
		private String max;
		private String split;
	}

	protected class AnalysisDAO {

		/**
		 * テーブルを作成する.
		 *
		 * @param Connection connection
		 * @param PatternItem pattern
		 * @return boolean ret
		 */
		public boolean createTable(final Connection connection, final XmlPattern pattern) throws SQLException {
			boolean ret = false;

			StringBuffer buffer = new StringBuffer();
			buffer.append("create table " + pattern.getTableId() + "( ");
			buffer.append(" LOG_INFO VARCHAR(256), ");

			for (int i = 0; i < pattern.getColumns().length; i++) {
				XmlColumn column = pattern.getColumns()[i];
				buffer.append("  " + column.getName() + " VARCHAR(" + column.getMax() + ")");

				if (i < pattern.getColumns().length - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(",constraint P_" + pattern.getTableId() + " primary key (LOG_INFO)");
			buffer.append(")");

			try (PreparedStatement pstmt = connection.prepareStatement(buffer.toString())) {
				ret = pstmt.execute();
			}
			return ret;
		}

		/**
		 * インデックスを作成する.
		 *
		 * @param Connection connection
		 * @param String tableId
		 * @param int indexNumber
		 * @param String columnName
		 * @return boolean ret
		 */
		public boolean createIndex(final Connection connection, final String tableId, final int indexNumber, final String columnName) throws SQLException {
			boolean ret = false;

			StringBuffer buffer = new StringBuffer();
			buffer.append("create index I_" + tableId + indexNumber + " on " + tableId + "(" + columnName + ")");

			try (PreparedStatement pstmt = connection.prepareStatement(buffer.toString())) {
				ret = pstmt.execute();
			}
			return ret;
		}

		/**
		 * レコードを追加する.
		 *
		 * @param Connection connection
		 * @param String tableId
		 * @param String[] record
		 * @return boolean ret
		 */
		public boolean insert(final Connection connection, final String tableId, final String[] record) throws SQLException {
			boolean ret = false;

			StringBuffer buffer = new StringBuffer();
			buffer.append("insert into " + tableId + " values(");

			for (int i = 0; i < record.length; i++) {

				if (i != 0) {
					buffer.append(",");
				}
				buffer.append("?");
			}
			buffer.append(")");

			try (PreparedStatement pstmt = connection.prepareStatement(buffer.toString())) {
				int index = 1;

				for (String value : record) {
					pstmt.setString(index++, value);
				}
				ret = pstmt.execute();
			}
			return ret;
		}
	}
}
