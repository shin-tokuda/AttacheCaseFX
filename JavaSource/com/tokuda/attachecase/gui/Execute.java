package com.tokuda.attachecase.gui;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.dto.ColorPatternDTO01;
import com.tokuda.attachecase.dto.ConfigDTO;
import com.tokuda.attachecase.gui.main.MainController;
import com.tokuda.attachecase.jfx.ResizeHelper;
import com.tokuda.common.constant.IconConst;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Execute extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			SystemData.config = mapper.readValue(Paths.get("conf", "config.json").toFile(), ConfigDTO.class);
			SystemData.colorPattern = mapper.readValue(Paths.get("conf", "color_pattern.json").toFile(), ColorPatternDTO01.class);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		SystemData.stage = primaryStage;
		MainController.getInstance().show();
		ResizeHelper.addResizeListener(primaryStage);
		primaryStage.setTitle(SystemData.config.getTitle());
		primaryStage.getIcons().add(IconConst.Application.getImage());
		primaryStage.setWidth(SystemData.config.getInitialWidth());
		primaryStage.setHeight(SystemData.config.getInitialHeight());
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
