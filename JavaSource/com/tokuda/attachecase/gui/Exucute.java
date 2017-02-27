package com.tokuda.attachecase.gui;

import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.gui.main.MainController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Exucute extends Application {

	@Override
	public void start(Stage primaryStage) {
		SystemData.stage = primaryStage;
		MainController.getInstance().show();
		// TODO eclipseから起動できなくなるため、一旦コメントアウト
		// primaryStage.getIcons().add(IconConst.Application.getImage());
		primaryStage.setTitle("sample");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
