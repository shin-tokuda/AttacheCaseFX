package com.tokuda.attachecase.gui.psnl001;

import java.io.File;

import com.tokuda.attachecase.gui.DefaultController;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

@Getter
public class Psnl001Controller extends DefaultController<Psnl001SaveDTO> {

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private AnchorPane pane;

	@FXML
	public void initialize() {
	}

	@Override
	public void open(final File file) {
		open(file, Psnl001SaveDTO.class);
	}

	@Override
	protected void preSave() {
		setSaveDTO(new Psnl001SaveDTO());
	}

	// -----------------------------------------------------------------
	// タスク管理
	// -----------------------------------------------------------------
}
