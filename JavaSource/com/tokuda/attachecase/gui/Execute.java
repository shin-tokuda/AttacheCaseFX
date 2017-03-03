package com.tokuda.attachecase.gui;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.constant.CharSetConst;
import com.tokuda.attachecase.dto.ColorPatternDTO01;
import com.tokuda.attachecase.dto.ConfigDTO;
import com.tokuda.attachecase.dto.SettingDTO;
import com.tokuda.attachecase.gui.main.MainController;
import com.tokuda.attachecase.jfx.ResizeHelper;
import com.tokuda.common.constant.IconConst;
import com.tokuda.common.util.UtilFile;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Execute extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			SystemData.config = mapper.readValue(Paths.get("conf", "config.json").toFile(), ConfigDTO.class);
			SystemData.colorPattern = mapper.readValue(Paths.get("conf", "color_pattern.json").toFile(), ColorPatternDTO01.class);
			SystemData.setting = mapper.readValue(Paths.get("conf", "setting.json").toFile(), SettingDTO.class);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		SystemData.stage = primaryStage;
		MainController.getInstance().show();
		ResizeHelper.addResizeListener(primaryStage);
		primaryStage.setTitle(SystemData.config.getTitle());
		primaryStage.getIcons().add(IconConst.Application.getImage());
		primaryStage.setWidth(SystemData.setting.getInitialWidth());
		primaryStage.setHeight(SystemData.setting.getInitialHeight());
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
	}

	@Override
	public void stop() {
		SystemData.setting.setInitialWidth(SystemData.stage.getWidth());
		SystemData.setting.setInitialHeight(SystemData.stage.getHeight());

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.writeValue(UtilFile.getBufferedWriter(Paths.get("conf", "setting.json").toFile(), CharSetConst.Utf8.getValue()), SystemData.setting);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
