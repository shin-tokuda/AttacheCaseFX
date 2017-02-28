package com.tokuda.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.mozilla.universalchardet.UniversalDetector;

/**
 * Created by s-tokuda on 2017/03/01.
 */
public class UtilFile {

	/**
	 * 文字コードを検出します。
	 *
	 * @param file
	 *            検査対象ファイル
	 * @return 文字コード
	 */
	public static String charSetDetection(File file) throws java.io.IOException {
		String result;

		try (FileInputStream fis = new FileInputStream(file)) {

			// 文字コード判定ライブラリの実装
			UniversalDetector detector = new UniversalDetector(null);

			// 判定開始
			int nread;
			byte[] buffer = new byte[4096];

			while ((nread = fis.read(buffer)) > 0 && !detector.isDone()) {
				detector.handleData(buffer, 0, nread);
			}

			// 判定終了
			detector.dataEnd();

			// 文字コード判定
			result = detector.getDetectedCharset();

			// 判定の初期化
			detector.reset();
		}
		return result;
	}

	/**
	 * 対象ファイルに対するBufferedReaderを取得します。<br>
	 * 文字コードを自動検出して取得するため、文字化けの可能性はかなり低いです。
	 *
	 * @param file
	 *            対象ファイル
	 * @return 対象ファイルに対するBufferedReader
	 * @throws IOException
	 *             入出力例外
	 */
	public static BufferedReader getBufferedReader(File file) throws IOException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(file), charSetDetection(file)));
	}

	/**
	 * 対象ファイルに対するBufferedWriterを取得します。<br>
	 * 既存ファイルの文字コードを自動検出して取得するため、ファイル内容の一部書き換えなどを行う際に便利です。
	 *
	 * @param file
	 *            対象ファイル
	 * @return 対象ファイルに対するBufferedWriter
	 * @throws IOException
	 *             入出力例外
	 */
	public static BufferedWriter getBufferedWriter(File file) throws IOException {
		return getBufferedWriter(file, charSetDetection(file));
	}

	/**
	 * 対象ファイルに対するBufferedWriterを取得します。
	 *
	 * @param file
	 *            対象ファイル
	 * @param charSet
	 *            文字コード
	 * @return 対象ファイルに対するBufferedWriter
	 * @throws IOException
	 *             入出力例外
	 */
	public static BufferedWriter getBufferedWriter(File file, String charSet) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charSet));
	}
}
