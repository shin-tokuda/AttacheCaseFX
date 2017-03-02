package com.tokuda.attachecase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tokuda.attachecase.dto.ColorPatternDTO01;
import com.tokuda.attachecase.dto.ColorPatternDTO02;
import com.tokuda.common.util.UtilFile;
import com.tokuda.common.util.UtilString;

/**
 * 自動生成ツール
 *
 * @author s-tokuda
 */
public class AutomaticGeneration {

	public static void main(String[] args) {
		System.out.println(AutomaticGeneration.class.getName() + " is start.");

		File mainCssTmplFile = Paths.get("template", "MainCss.tmpl").toFile();
		File mainCssFile = Paths.get("JavaSource/com/tokuda/attachecase/gui/main/Main.css").toFile();

		ColorPatternDTO01 colorPattern = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			colorPattern = mapper.readValue(Paths.get("conf", "color_pattern.json").toFile(), ColorPatternDTO01.class);

			if (colorPattern != null) {
				ColorPatternDTO02 pattern = null;
				ColorPatternDTO02 black = null;
				ColorPatternDTO02 white = null;

				for (ColorPatternDTO02 dto : colorPattern.getPatterns()) {

					if (UtilString.cnvNull(dto.getPattern()).equals(colorPattern.getSelected())) {
						pattern = dto;
					}
					if (UtilString.cnvNull(dto.getPattern()).equals("Black")) {
						black = dto;
					}
					if (UtilString.cnvNull(dto.getPattern()).equals("White")) {
						white = dto;
					}
				}

				if (pattern != null) {

					try (BufferedReader reader = UtilFile.getBufferedReader(mainCssTmplFile);
							PrintWriter writer = new PrintWriter(UtilFile.getBufferedWriter(mainCssFile))) {

						String line;
						while ((line = reader.readLine()) != null) {

							if (!UtilString.isEmpty(line)) {
								line = line.replace("{@50}", pattern.getP50());
								line = line.replace("{@100}", pattern.getP100());
								line = line.replace("{@200}", pattern.getP200());
								line = line.replace("{@300}", pattern.getP300());
								line = line.replace("{@400}", pattern.getP400());
								line = line.replace("{@500}", pattern.getP500());
								line = line.replace("{@600}", pattern.getP600());
								line = line.replace("{@700}", pattern.getP700());
								line = line.replace("{@800}", pattern.getP800());
								line = line.replace("{@900}", pattern.getP900());
								line = line.replace("{@A100}", pattern.getPa100());
								line = line.replace("{@A200}", pattern.getPa200());
								line = line.replace("{@A400}", pattern.getPa400());
								line = line.replace("{@A700}", pattern.getPa700());
								line = line.replace("{@black}", black.getP500());
								line = line.replace("{@white}", white.getP500());
								writer.println(line);
							}
						}
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println(AutomaticGeneration.class.getName() + " is end.");
	}
}
