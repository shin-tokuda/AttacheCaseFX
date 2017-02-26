package com.tokuda.attachecase.gui;

import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.gui.sample.SampleController;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		SystemData.stage = primaryStage;
		// P001AppController.getInstance().show();
		SampleController.getInstance().show();
		// TODO eclipseから起動できなくなるため、一旦コメントアウト
		// primaryStage.getIcons().add(IconConst.Application.getImage());
		primaryStage.setTitle("sample");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
