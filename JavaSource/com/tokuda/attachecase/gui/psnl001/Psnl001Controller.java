package com.tokuda.attachecase.gui.psnl001;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.tokuda.attachecase.BaseController;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dialog.MessageSnackBar;
import com.tokuda.common.constant.MessageConst;
import com.tokuda.common.util.UtilMessage;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Psnl001Controller extends BaseController {

	// -----------------------------------------------------------------
	// インスタンス管理
	// -----------------------------------------------------------------

	static {
		load(Psnl001Controller.class);
	}

	public static BaseController getInstance() {
		return instance;
	}

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	@FXML
	private Pane pane;

	@FXML
	private JFXHamburger burger;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private VBox sidePane;

	@FXML
	private StackPane stack;

	@FXML
	private JFXSnackbar snack;

	@FXML
	private TabPane tabPane;

	@FXML
	private JFXProgressBar progress;

	@FXML
	public void initialize() {
		snack.registerSnackbarContainer(pane);
		SystemData.pane = pane;
		SystemData.stack = stack;
		SystemData.snack = snack;

		drawer.setSidePane(sidePane);
		drawer.open();

		HamburgerBackArrowBasicTransition burgerTask01 = new HamburgerBackArrowBasicTransition(burger);
		burgerTask01.setRate(1);
		burger.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			burgerTask01.setRate(burgerTask01.getRate() * -1);
			burgerTask01.play();

			if (drawer.isShown()) {
				drawer.close();
			} else {
				drawer.open();
			}
		});

		for (int i = 1; i <= 10; i++) {
			JFXButton button = new JFXButton("test111111111111111" + i);
			button.setMinHeight(40);
			sidePane.getChildren().add(button);
		}

		for (int i = 1; i <= 10; i++) {
			Tab tab = new Tab();
			tab.setText("test" + i);
			tab.setContent(new Label("Content"));
			tab.setClosable(true);
			tabPane.getTabs().add(tab);
		}
		progress.setProgress(0);
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
			new MessageSnackBar(UtilMessage.build(MessageConst.InfoMsg003)).show();
			return null;
		}
	};
}
