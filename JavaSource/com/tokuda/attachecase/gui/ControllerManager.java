package com.tokuda.attachecase.gui;

import java.util.HashMap;
import java.util.Map;

import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dto.ConfigDTO;
import com.tokuda.attachecase.dto.ConfigDTO.ApplicationDTO;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * コントローラー管理クラス
 *
 * @author s-tokuda
 */
public class ControllerManager {

	private static final Map<String, ConfigDTO.ApplicationDTO> applications = new HashMap<>();

	private static final Map<String, Object> singletonControllers = new HashMap<>();

	static {
		SystemData.config.getApplications().stream().forEach(app -> {
			applications.put("com.tokuda.attachecase.gui." + app.getAppId().toLowerCase() + "." + app.getAppId() + "Controller", app);
		});
	}

	public static <T extends BaseController<?>> T getController(final Class<?> cls) {
		final String key = cls.getName();
		return (T) singletonControllers.get(key);
	}

	/**
	 * GUIをロードする
	 *
	 * @param cls クラス
	 * @param stage ロード先ステージ
	 */
	public static <T extends BaseController<?>> T load(final Class<?> cls, final Stage stage) {
		return load(cls, stage, null);
	}

	/**
	 * GUIをロードする
	 *
	 * @param cls クラス
	 * @param parent ロード先ノード
	 */
	public static <T extends BaseController<?>> T load(final Class<?> cls, final Parent parent) {
		return load(cls, null, parent);
	}

	/**
	 * GUIをロードする
	 *
	 * @param cls クラス
	 * @param stage ロード先ステージ
	 * @param parent ロード先ノード
	 */
	private static <T extends BaseController<?>> T load(final Class<?> cls, final Stage stage, final Parent parent) {
		T result = null;

		final String key = cls.getName();
		final String clsName = cls.getSimpleName();
		final String fxmlName = clsName.substring(0, clsName.lastIndexOf("Controller")) + ".fxml";
		final String cssName = clsName.substring(0, clsName.lastIndexOf("Controller")) + ".css";

		if (!SingletonController.class.isAssignableFrom(cls) || !singletonControllers.containsKey(key)) {

			try {
				FXMLLoader fxmlLoader = new FXMLLoader(cls.getResource(fxmlName));
				fxmlLoader.load();
				result = fxmlLoader.getController();

				for (ApplicationDTO app : SystemData.config.getApplications()) {

					if (app.getAppId().equals(clsName.substring(0, clsName.lastIndexOf("Controller")))) {
						result.setAppDTO(app);
						break;
					}
				}

				if (stage != null) {
					Scene scene = new Scene(fxmlLoader.getRoot());
					scene.getStylesheets().clear();
					scene.getStylesheets().add(cls.getResource(cssName).toExternalForm());
					stage.setScene(scene);
				}

				if (parent != null) {

					// TODO コンポーネントの種類を追加していく必要あり
					if (parent instanceof TabPane) {
						Tab tab = new Tab();

						// タブ内で最大表示させる為の設定
						AnchorPane pane = new AnchorPane();
						AnchorPane.setTopAnchor(fxmlLoader.getRoot(), 0.0);
						AnchorPane.setRightAnchor(fxmlLoader.getRoot(), 0.0);
						AnchorPane.setLeftAnchor(fxmlLoader.getRoot(), 0.0);
						AnchorPane.setBottomAnchor(fxmlLoader.getRoot(), 0.0);
						pane.getChildren().add(fxmlLoader.getRoot());

						// 固有スタイルを設定
						pane.getStylesheets().add(cls.getResource(cssName).toExternalForm());

						// タイトルを設定
						tab.setText(applications.get(key).getTitle());

						// タブ追加
						tab.setContent(pane);
						tab.setClosable(true);
						((TabPane) parent).getTabs().add(tab);

						// 追加したタブを選択状態に変更
						SingleSelectionModel<Tab> selectionModel = ((TabPane) parent).getSelectionModel();
						selectionModel.select(tab);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (SingletonController.class.isAssignableFrom(cls)) {
				singletonControllers.put(key, result);
			}

		} else {
			result = (T) singletonControllers.get(key);
		}
		return result;
	}
}
