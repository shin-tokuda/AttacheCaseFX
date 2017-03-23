package com.tokuda.attachecase.gui.psnl005;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.tokuda.attachecase.gui.DefaultController;
import com.tokuda.attachecase.jfx.CustomTask;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class Psnl005Controller extends DefaultController<Psnl005SaveDTO> {

	private Connection connection;

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private GridPane pane;

	@FXML
	private ImageView image01;

	@FXML
	private Label label01;

	@FXML
	private Label label02;

	@FXML
	private Label label03;

	@FXML
	private Label label04;

	@FXML
	private Label label05;

	@FXML
	private Label label06;

	@FXML
	private Label label07;

	@FXML
	private Label label08;

	@FXML
	private Label label09;

	@FXML
	private Label label10;

	@FXML
	private JFXTextField text01;

	@FXML
	private JFXTextField text02;

	@FXML
	private JFXTextField text03;

	@FXML
	private JFXTextField text04;

	@FXML
	private JFXTextField text05;

	@FXML
	private JFXTextField text06;

	@FXML
	private JFXToggleButton toggle01;

	@FXML
	private JFXToggleButton toggle02;

	@FXML
	private JFXToggleButton toggle03;

	@FXML
	private JFXToggleButton toggle04;

	@FXML
	private JFXToggleButton toggle05;

	@FXML
	private JFXToggleButton toggle06;

	@FXML
	private Spinner spinner01;

	@FXML
	private Spinner spinner02;

	@FXML
	private JFXComboBox combo01;

	@FXML
	private JFXComboBox combo02;

	@FXML
	private JFXButton button01;

	@FXML
	private JFXButton button02;

	@FXML
	private JFXButton button03;

	@FXML
	private TreeTableView<Record> table01;

	@FXML
	public void initialize() {
	}

	@FXML
	public void onClickButton01(ActionEvent event) {
	}

	@FXML
	public void onClickButton02(ActionEvent event) {
	}

	@FXML
	public void onClickButton03(ActionEvent event) {
	}

	@Override
	public void open(final File file) {
		Psnl005SaveDTO saveDTO = open(file, Psnl005SaveDTO.class);

		if (saveDTO != null) {
			text01.setText(saveDTO.getText01());
			text02.setText(saveDTO.getText02());
		}
	}

	@Override
	protected void preSave() {
		setSaveDTO(new Psnl005SaveDTO(text01.getText(), text02.getText()));
	}

	// -----------------------------------------------------------------
	// タスク管理
	// -----------------------------------------------------------------

	private class Task01 extends CustomTask<Void> {

		@Override
		protected Void call() throws Exception {
			return null;
		}
	};

	private class Task02 extends CustomTask<SearchResponse> {

		@Override
		protected SearchResponse call() throws Exception {
			return null;
		}
	};

	private class Task03 extends CustomTask<Void> {

		@Override
		protected Void call() throws Exception {
			return null;
		}
	};

	// -----------------------------------------------------------------
	// その他
	// -----------------------------------------------------------------

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
}
