package com.tokuda.attachecase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tokuda.attachecase.dto.ColorPatternDTO;
import com.tokuda.attachecase.dto.ConfigDTO;
import com.tokuda.attachecase.dto.SettingDTO;
import com.tokuda.common.constant.PropertyKeyConst;
import com.tokuda.common.util.UtilFile;
import com.tokuda.common.util.UtilProperty;
import com.tokuda.common.util.UtilString;

/**
 * 自動生成ツール
 *
 * @author s-tokuda
 */
public class AutomaticGeneration {

	public static void main(String[] args) {
		System.out.println(AutomaticGeneration.class.getName() + " is start.");

		// Main.cssを自動生成
		createMainCss();

		// PropertyKey.javaを自動生成
		createPropertyKey();

		System.out.println(AutomaticGeneration.class.getName() + " is end.");
	}

	/**
	 * Main.cssを自動生成します。
	 */
	protected static void createMainCss() {
		File tmplFile = Paths.get("template", "MainCss.tmpl").toFile();
		File outputFile = Paths.get("JavaSource/com/tokuda/attachecase/gui/main/Main.css").toFile();

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			ColorPatternDTO colorPattern = mapper.readValue(Paths.get("conf", "color_pattern.json").toFile(), ColorPatternDTO.class);
			SettingDTO setting = mapper.readValue(Paths.get("conf", "setting.json").toFile(), SettingDTO.class);

			if (colorPattern != null) {
				ColorPatternDTO.PatternDTO pattern = null;
				ColorPatternDTO.PatternDTO black = null;
				ColorPatternDTO.PatternDTO white = null;

				for (ColorPatternDTO.PatternDTO dto : colorPattern.getPatterns()) {

					if (UtilString.cnvNull(dto.getPattern()).equals(setting.getColorPattern())) {
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

					try (BufferedReader reader = UtilFile.getBufferedReader(tmplFile);
							PrintWriter writer = new PrintWriter(UtilFile.getBufferedWriter(outputFile))) {

						String line;
						while ((line = reader.readLine()) != null) {
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
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * PropertyKey.javaを自動生成します。
	 */
	protected static void createPropertyKey() {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			ConfigDTO config = mapper.readValue(Paths.get("conf", "config.json").toFile(), ConfigDTO.class);

			if (config != null) {
				SystemData.config = config;

				// -------------------------------------------------
				// propertiesファイルの読み込み
				// -------------------------------------------------
				Map<String, String> properties = new TreeMap<>();

				ResourceBundle common = ResourceBundle.getBundle("resource/property/common", Locale.JAPANESE);
				Enumeration<String> commonKeys = common.getKeys();

				while (commonKeys.hasMoreElements()) {
					String key = commonKeys.nextElement();
					properties.put(key, common.getString(key));
				}

				config.getProducts().stream().forEach(product -> {
					ResourceBundle resource = ResourceBundle.getBundle("resource/property/" + product, Locale.JAPANESE);
					Enumeration<String> resourceKeys = resource.getKeys();

					while (resourceKeys.hasMoreElements()) {
						String key = resourceKeys.nextElement();
						properties.put(key, resource.getString(key));
					}
				});

				// -------------------------------------------------
				// propertiesファイルの読み込み
				// -------------------------------------------------
				File tmplFile = Paths.get("template", "PropertyKeyConst.tmpl").toFile();
				File outputFile = Paths.get("JavaSource/com/tokuda/common/constant/PropertyKeyConst.java").toFile();

				try (BufferedReader reader = UtilFile.getBufferedReader(tmplFile);
						PrintWriter writer = new PrintWriter(UtilFile.getBufferedWriter(outputFile))) {

					String line;
					while ((line = reader.readLine()) != null) {

						if (!line.equals("{@slot}")) {
							writer.println(line);
						} else {
							List<Map.Entry<String, String>> entries = new ArrayList<>(properties.entrySet());

							for (int i = 0; i < entries.size(); i++) {
								Map.Entry<String, String> entry = entries.get(i);
								writer.println("\t// \"" + cnvEscape(UtilString.trim(entry.getValue(), "\"")) + "\"");

								if (i < entries.size() - 1) {
									writer.println("\t" + cnvForUpperCamelCase(entry.getKey()) + "(\"" + entry.getKey() + "\"),");
								} else {
									writer.println("\t" + cnvForUpperCamelCase(entry.getKey()) + "(\"" + entry.getKey() + "\");");
								}
							}
						}
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 対象の文字列をアッパーキャメルケースに変換します。
	 *
	 * @param str 対象文字列
	 * @return 変換結果
	 */
	protected static String cnvForUpperCamelCase(final String str) {
		StringBuilder builder = new StringBuilder();

		String[] splits = str.split("\\.");

		for (int i = 0; i < splits.length; i++) {
			String split = splits[i];

			if (i > 0) {
				builder.append(UtilProperty.getValue(PropertyKeyConst.Item_UnderScore.getValue()));
			}

			if (split.length() > 0) {
				String first = split.substring(0, 1);
				builder.append(first.toUpperCase());

				if (split.length() > 1) {
					String second = split.substring(1);
					builder.append(second);
				}
			}
		}
		return builder.toString();
	}

	/**
	 * 対象の文字列をエスケープ変換します。
	 *
	 * @param str 対象文字列
	 * @return 変換結果
	 */
	protected static String cnvEscape(final String str) {
		return str.replace("\n", "\\n");
	}
}
