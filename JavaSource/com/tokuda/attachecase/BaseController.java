package com.tokuda.attachecase;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public abstract class BaseController {

	/**
	 * instance(singleton)
	 */
	protected static BaseController instance;

	/**
	 * Scene(singleton)
	 */
	protected static Scene scene;

	/**
	 * 表示する
	 */
	public void show() {
		SystemData.stage.setScene(scene);
	}

	/**
	 * GUIをロードする
	 *
	 * @param fxmlLoader
	 */
	public static void load(final Class<?> cls, final String fxmlName, final String cssName) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(cls.getResource(fxmlName));
			fxmlLoader.load();
			scene = new Scene(fxmlLoader.getRoot());
			scene.getStylesheets().add(cls.getResource(cssName).toExternalForm());
			instance = fxmlLoader.getController();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
