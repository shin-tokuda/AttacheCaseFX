package com.tokuda.attachecase.gui;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dto.ConfigDTO;
import com.tokuda.attachecase.gui.main.MainController;
import com.tokuda.common.constant.IconConst;

import javafx.application.Application;
import javafx.stage.Stage;

public class Execute extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			SystemData.config = mapper.readValue(Paths.get("conf", "config.json").toFile(), ConfigDTO.class);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// try {
		// String fileName = "config_sjis.json";
		// ObjectMapper mapper = new ObjectMapper();
		// mapper.enable(SerializationFeature.INDENT_OUTPUT);
		// ConfigDTO config =
		// mapper.readValue(UtilFile.getBufferedReader(Paths.get("conf",
		// fileName).toFile()), ConfigDTO.class);
		// config.getApplications().get(0).setAppId(fileName + "だよ");
		// mapper.writeValue(UtilFile.getBufferedWriter(Paths.get("conf",
		// fileName).toFile()), config);
		// } catch (IOException ex) {
		// ex.printStackTrace();
		// }

		SystemData.stage = primaryStage;
		MainController.getInstance().show();
		primaryStage.getIcons().add(IconConst.Application.getImage());
		primaryStage.setTitle("sample");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
