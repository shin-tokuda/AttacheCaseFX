package com.tokuda.attachecase;

import com.jfoenix.controls.JFXSnackbar;
import com.tokuda.attachecase.dto.ConfigDTO;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * システム全体から参照するフィールドを定義します。
 *
 * @author s-tokuda
 */
public class SystemData {

	/**
	 * ベースディレクトリ
	 */
	public static String baseDirectory;

	/**
	 * 設定情報
	 */
	public static ConfigDTO config;

	/**
	 * ステージ
	 */
	public static Stage stage;

	/**
	 * ペイン
	 */
	public static Pane pane;

	/**
	 * スタック
	 */
	public static StackPane stack;

	/**
	 * スナックバー
	 */
	public static JFXSnackbar snack;
}