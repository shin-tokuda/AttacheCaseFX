package com.tokuda.attachecase.gui;

import java.util.LinkedList;
import java.util.List;

import com.tokuda.attachecase.gui.main.MainController;

import javafx.concurrent.Task;

/**
 * タスク管理クラス
 *
 * @author s-tokuda
 */
public class TaskManager {

	/**
	 * タスクキュー
	 */
	private static final List<Task<?>> tasks = new LinkedList<>();

	/**
	 * タスクを実行します。
	 *
	 * @param task タスク
	 */
	public static synchronized void start(Task<?> task) {

		if (tasks.isEmpty()) {
			MainController main = ControllerManager.getController(MainController.class);
			main.getProgress().progressProperty().bind(task.progressProperty());
			new Thread(task).start();
		}
		tasks.add(task);
	}

	/**
	 * タスクが終了した際の処理です。キューが残っていれば、続けてタスクを実行します。
	 */
	public static synchronized void stop() {
		MainController main = ControllerManager.getController(MainController.class);
		main.getProgress().progressProperty().unbind();
		main.getProgress().setProgress(0);
		tasks.remove(0);

		if (!tasks.isEmpty()) {
			start(tasks.get(0));
		}
	}
}
