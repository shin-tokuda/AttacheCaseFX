package com.tokuda.attachecase.gui.psnl001;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.tokuda.attachecase.DefaultController;
import com.tokuda.attachecase.dialog.MessageDialog;
import com.tokuda.common.constant.MessageConst;
import com.tokuda.common.util.UtilMessage;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

@Getter
public class Psnl001Controller extends DefaultController {

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private AnchorPane pane;

	@FXML
	public void initialize() {
	}

	// -----------------------------------------------------------------
	// タスク管理
	// -----------------------------------------------------------------

	private Task01 task01;

	private class Task01 extends Task<Void> {

		private File directory;

		public Task01(final File directory) {
			this.directory = directory;
		}

		@Override
		public Void call() {
			final int max = 3;
			updateProgress(1, max);

			try (Workbook book = WorkbookFactory.create(directory)) {
				updateProgress(2, max);
			} catch (EncryptedDocumentException | InvalidFormatException | IOException ex) {
				ex.printStackTrace();
			}
			updateProgress(3, max);
			new MessageDialog(UtilMessage.build(MessageConst.InfoMsg003)).show();
			return null;
		}
	};
}
