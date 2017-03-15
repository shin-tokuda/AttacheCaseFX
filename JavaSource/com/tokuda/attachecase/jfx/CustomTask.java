package com.tokuda.attachecase.jfx;

import com.tokuda.attachecase.dialog.ExceptionDialog;
import com.tokuda.attachecase.dialog.MessageSnackBar;
import com.tokuda.attachecase.gui.TaskManager;
import com.tokuda.common.constant.PropertyKeyConst;
import com.tokuda.common.util.UtilMessage;
import com.tokuda.common.util.UtilProperty;

import javafx.concurrent.Task;

/**
 * タスクを拡張したクラスです。
 *
 * @author s-tokuda
 */
public abstract class CustomTask<T> extends Task<T> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void succeeded() {
		super.succeeded();
		new MessageSnackBar(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Info005.getValue()))).show();
		TaskManager.stop();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void cancelled() {
		super.cancelled();
		new MessageSnackBar(UtilMessage.build(UtilProperty.getValue(PropertyKeyConst.Msg_Info006.getValue()))).show();
		TaskManager.stop();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void failed() {
		super.failed();
		new ExceptionDialog(getException()).showAndWait();
		TaskManager.stop();
	}
}
