package com.tokuda.attachecase.gui.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dialog.ExceptionDialog;
import com.tokuda.attachecase.dialog.MessageSnackBar;
import com.tokuda.attachecase.dto.ConfigDTO;
import com.tokuda.attachecase.gui.BaseController;
import com.tokuda.attachecase.gui.BaseSaveDTO;
import com.tokuda.attachecase.gui.ControllerManager;
import com.tokuda.attachecase.gui.SingletonController;
import com.tokuda.attachecase.jfx.MenuItemBox;
import com.tokuda.common.constant.IconConst;
import com.tokuda.common.constant.PropertyKeyConst;
import com.tokuda.common.util.UtilFile;
import com.tokuda.common.util.UtilMessage;
import com.tokuda.common.util.UtilProperty;
import com.tokuda.common.util.UtilString;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lombok.Getter;

@Getter
public class MainController extends SingletonController<BaseSaveDTO> {

	// -----------------------------------------------------------------
	// GUI管理
	// -----------------------------------------------------------------

	private double xOffset = 0;

	private double yOffset = 0;

	private List<BaseController<BaseSaveDTO>> applications = new ArrayList<>();

	private List<File> saveFiles = new ArrayList<>();

	private HamburgerBackArrowBasicTransition burgerTask01;

	@FXML
	private Pane pane;

	@FXML
	private GridPane headerBar;

	@FXML
	private JFXHamburger burger;

	@FXML
	private Label appTitle;

	@FXML
	private JFXButton hideButton;

	@FXML
	private JFXButton resizeButton;

	@FXML
	private JFXButton closeButton;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private Pane sidePane;

	@FXML
	private Label sideTitle;

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

		ImageView appImage = new ImageView(IconConst.AttacheCase.getImage());
		appImage.setFitWidth(24);
		appImage.setFitHeight(24);

		appTitle.setText(SystemData.config.getTitle());
		appTitle.setGraphic(appImage);
		sideTitle.setText(SystemData.config.getTitle());

		burgerTask01 = new HamburgerBackArrowBasicTransition(burger);
		burgerTask01.setRate(-1);

		drawer.setSidePane(sidePane);
		drawer.open();
		drawer.close();
		drawer.setOnDrawerClosed(handler -> {

			if (burgerTask01.getRate() != -1) {
				burgerTask01.setRate(-1);
				burgerTask01.play();
			}
		});

		burger.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			// drawerの開閉切り替え
			drawerToggle();
		});

		headerBar.setOnMouseClicked(event -> {

			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				SystemData.stage.setMaximized(!SystemData.stage.isMaximized());
			}
		});

		headerBar.setOnMousePressed(event -> {

			if (!SystemData.stage.isMaximized()) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		headerBar.setOnMouseDragged(event -> {

			if (!SystemData.stage.isMaximized()) {
				SystemData.stage.setX(event.getScreenX() - xOffset);
				SystemData.stage.setY(event.getScreenY() - yOffset);
			}
		});

		hideButton.setFocusTraversable(false);
		hideButton.setOnAction(event -> {
			SystemData.stage.setIconified(true);
		});

		resizeButton.setFocusTraversable(false);
		resizeButton.setOnAction(event -> {
			SystemData.stage.setMaximized(!SystemData.stage.isMaximized());
		});

		closeButton.setFocusTraversable(false);
		closeButton.setOnAction(event -> {
			SystemData.stage.close();
		});

		// メニュー生成
		createMenu();

		// ショートカット登録
		createShortcut();

		progress.setProgress(0);
	}

	/**
	 * メニューを生成します。
	 */
	private void createMenu() {
		Accordion accordion = new Accordion();
		accordion.setPrefWidth(300);

		// 新規作成メニューの設定

		VBox createList = new VBox();
		SystemData.config.getApplications().stream().forEach(app -> {
			createList.getChildren().add(new MenuItemBox(app.getTitle(), UtilFile.getImage(app.getIcon()), null, event -> {

				try {
					BaseController<BaseSaveDTO> controller = ControllerManager
							.load(Class.forName("com.tokuda.attachecase.gui." + app.getAppId().toLowerCase() + "." + app.getAppId() + "Controller"), tabPane);
					applications.add(controller);
					saveFiles.add(null);
				} catch (ClassNotFoundException ex) {
					new ExceptionDialog(ex).showAndWait();
				}
				new MessageSnackBar(app.getTitle()).show();
			}));
		});
		accordion.getPanes().add(new TitledPane("新規作成", createList));

		// ファイルメニューの設定

		VBox fileList = new VBox();
		fileList.getChildren()
				.add(new MenuItemBox("開く", IconConst.FileOpen.getImage(), new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN), event -> {
					FileChooser chooser = new FileChooser();
					chooser.setTitle(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Info001.getValue())));
					File file = chooser.showOpenDialog(SystemData.stage);

					if (file != null && file.exists()) {
						String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);

						for (ConfigDTO.ApplicationDTO app : SystemData.config.getApplications()) {

							if (UtilString.cnvNull(app.getExtension()).equals(extension)) {

								try {
									BaseController<BaseSaveDTO> controller = ControllerManager.load(
											Class.forName("com.tokuda.attachecase.gui." + app.getAppId().toLowerCase() + "." + app.getAppId() + "Controller"),
											tabPane);
									controller.open(file);
									applications.add(controller);
									saveFiles.add(file);
								} catch (ClassNotFoundException ex) {
									new ExceptionDialog(ex).showAndWait();
								}
								break;
							}
						}
					}
				}));

		fileList.getChildren()
				.add(new MenuItemBox("保存", IconConst.FileSave.getImage(), new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), event -> {

					if (!tabPane.getTabs().isEmpty()) {
						SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
						BaseController<BaseSaveDTO> controller = applications.get(selectionModel.getSelectedIndex());
						File file = saveFiles.get(selectionModel.getSelectedIndex());

						if (file != null) {
							file = controller.save(file);
						} else {
							file = controller.save();
						}
						saveFiles.remove(selectionModel.getSelectedIndex());
						saveFiles.add(selectionModel.getSelectedIndex(), file);
					}
				}));

		fileList.getChildren().add(new MenuItemBox("名前を付けて保存", IconConst.FileSave.getImage(),
				new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN), event -> {

					if (!tabPane.getTabs().isEmpty()) {
						SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
						BaseController<BaseSaveDTO> controller = applications.get(selectionModel.getSelectedIndex());
						File file = controller.save();
						saveFiles.remove(selectionModel.getSelectedIndex());
						saveFiles.add(selectionModel.getSelectedIndex(), file);
					}
				}));

		fileList.getChildren()
				.add(new MenuItemBox("閉じる", IconConst.FileClose.getImage(), new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN), event -> {

					if (!tabPane.getTabs().isEmpty()) {
						SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
						applications.remove(selectionModel.getSelectedIndex());
						saveFiles.remove(selectionModel.getSelectedIndex());
						tabPane.getTabs().remove(selectionModel.getSelectedIndex());
					}
				}));
		accordion.getPanes().add(new TitledPane("ファイル", fileList));

		menus.getChildren().add(accordion);
	}

	/**
	 * ショートカットを登録します。
	 */
	private void createShortcut() {

		pane.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			final KeyCombination ctrlD = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
			final KeyCombination ctrlT = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
			final KeyCombination ctrlShiftT = new KeyCodeCombination(KeyCode.T, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN);

			@Override
			public void handle(KeyEvent event) {

				if (ctrlD.match(event)) {

					// drawerの開閉切り替え
					drawerToggle();

				} else if (ctrlT.match(event)) {
					SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

					if (selectionModel.getSelectedIndex() < (tabPane.getTabs().size() - 1)) {
						selectionModel.selectNext();
					} else {
						selectionModel.selectFirst();
					}

				} else if (ctrlShiftT.match(event)) {
					SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

					if (selectionModel.getSelectedIndex() != 0) {
						selectionModel.selectPrevious();
					} else {
						selectionModel.selectLast();
					}
				}
			}
		});
	}

	/**
	 * drawerの開閉切り替えを行います。
	 */
	private void drawerToggle() {

		if (drawer.isShown()) {
			burgerTask01.setRate(-1);
			burgerTask01.play();
			drawer.close();
		} else {
			burgerTask01.setRate(1);
			burgerTask01.play();
			drawer.open();
		}
	}

	@Override
	public void open(final File file) {
	}

	@Override
	protected void preSave() {
	}
}
