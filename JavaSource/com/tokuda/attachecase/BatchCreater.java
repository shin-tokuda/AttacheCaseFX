package com.tokuda.attachecase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 実行バッチ作成ツール
 *
 * @author s-tokuda
 */
public class BatchCreater {

	private static final List<String> PATHS = new ArrayList<String>();

	public static void main(String[] args) {

		try {
			setPaths();
			File batch = Paths.get("AttacheCaseFX.bat").toFile();

			try (PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(batch), "UTF-8")))) {

				StringBuilder builder = new StringBuilder();
				PATHS.stream().forEach(path -> {
					builder.append("%toolPath%" + path + ";");
				});

				writer.println("@echo off");
				writer.println("REM TITLE=アタッシュケース");
				writer.println();
				writer.println("set toolPath=%~dp0\\");
				writer.println("set toolClassPath=%toolPath%classes;");
				writer.println();
				writer.println("start javaw -cp \"%toolPath%classes;" + builder.toString() + "\" com.tokuda.attachecase.gui.MainApp");
				writer.println("exit");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void setPaths() throws IOException {
		File lib = new File("lib");

		if (lib.exists()) {
			addFile(lib);
		}
	}

	private static void addFile(File file) throws IOException {
		String[] fileList = file.list();
		for (int i = 0; i < fileList.length; i++) {
			File childFile = new File(file.getPath() + File.separator + fileList[i]);

			if (childFile.isDirectory()) {
				addFile(childFile);
			} else {
				PATHS.add(childFile.getPath());
			}
		}
	}
}
