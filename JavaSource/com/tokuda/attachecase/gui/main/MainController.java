package com.tokuda.attachecase.gui.main;

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
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.tokuda.attachecase.BaseController;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dialog.MessageSnackBar;
import com.tokuda.attachecase.jfx.MenuItemBox;
import com.tokuda.common.constant.MessageConst;
import com.tokuda.common.util.UtilMessage;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController extends BaseController {

	// -----------------------------------------------------------------
	// インスタンス管理
	// -----------------------------------------------------------------

	static {
		load(MainController.class, "Main.fxml", "Main.css");
	}

	public static BaseController getInstance() {
		return instance;
	}

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	private double xOffset = 0;

	private double yOffset = 0;

	@FXML
	private Pane pane;

	@FXML
	private GridPane headerBar;

	@FXML
	private JFXHamburger burger;

	@FXML
	private Label appTitle;

	@FXML
	private JFXButton resizeButton;

	@FXML
	private JFXButton hideButton;

	@FXML
	private JFXButton closeButton;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private Pane sidePane;

	@FXML
	private JFXTabPane tabPane;

	@FXML
	private Pane menus;

	@FXML
	private JFXProgressBar progress;

	@FXML
	private StackPane stack;

	@FXML
	private JFXSnackbar snack;

	@FXML
	public void initialize() {
		snack.registerSnackbarContainer(pane);
		SystemData.pane = pane;
		SystemData.stack = stack;
		SystemData.snack = snack;

		appTitle.setText(SystemData.config.getTitle());

		HamburgerBackArrowBasicTransition burgerTask01 = new HamburgerBackArrowBasicTransition(burger);
		burgerTask01.setRate(-1);

		drawer.setSidePane(sidePane);
		drawer.setOnDrawerClosed(handler -> {

			if (burgerTask01.getRate() != -1) {
				burgerTask01.setRate(-1);
				burgerTask01.play();
			}
		});

		burger.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {

			if (drawer.isShown()) {
				burgerTask01.setRate(-1);
				burgerTask01.play();
				drawer.close();
			} else {
				burgerTask01.setRate(1);
				burgerTask01.play();
				drawer.open();
			}
		});

		headerBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		headerBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SystemData.stage.setX(event.getScreenX() - xOffset);
				SystemData.stage.setY(event.getScreenY() - yOffset);
			}
		});

		resizeButton.setOnAction(event -> {
			SystemData.stage.setMaximized(!SystemData.stage.isMaximized());
		});

		hideButton.setOnAction(event -> {
			SystemData.stage.setIconified(true);
		});

		closeButton.setOnAction(event -> {
			SystemData.stage.close();
		});

		// メニュー生成
		createMenu2();

		for (int i = 1; i <= 10; i++) {
			Tab tab = new Tab();
			tab.setText("テスト" + i);
			tab.setContent(new Label("Content"));
			tab.setClosable(true);
			tabPane.getTabs().add(tab);
		}
		progress.setProgress(0);
	}

	/**
	 * メニューを生成します。
	 */
	private void createMenu2() {

		Accordion accordion = new Accordion();
		accordion.setPrefWidth(200);

		VBox createList = new VBox();
		SystemData.config.getApplications().stream().forEach(app -> {
			createList.getChildren().add(new MenuItemBox(app.getTitle(), null, new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN), event -> {
				new MessageSnackBar(app.getTitle()).show();
			}));
		});
		accordion.getPanes().add(new TitledPane("新規作成", createList));

		VBox fileList = new VBox();
		fileList.getChildren().add(new MenuItemBox("開く", null, new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN), event -> {
			new MessageSnackBar("開く").show();
		}));
		fileList.getChildren().add(new MenuItemBox("保存", null, new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), event -> {
			new MessageSnackBar("開く").show();
		}));
		fileList.getChildren()
				.add(new MenuItemBox("名前を付けて保存", null, new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN), event -> {
					new MessageSnackBar("名前を付けて保存").show();
				}));
		fileList.getChildren().add(new MenuItemBox("閉じる", null, new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN), event -> {
			new MessageSnackBar("閉じる").show();
		}));
		accordion.getPanes().add(new TitledPane("ファイル", fileList));

		menus.getChildren().add(accordion);
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
