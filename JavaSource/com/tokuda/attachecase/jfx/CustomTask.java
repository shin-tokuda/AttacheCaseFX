package com.tokuda.attachecase.jfx;

import com.tokuda.attachecase.dialog.MessageSnackBar;
import com.tokuda.attachecase.gui.TaskManager;
import com.tokuda.common.constant.MessageConst;
import com.tokuda.common.util.UtilMessage;

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
		new MessageSnackBar(UtilMessage.build(MessageConst.InfoMsg005)).show();
		TaskManager.stop();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void cancelled() {
		super.cancelled();
		new MessageSnackBar(UtilMessage.build(MessageConst.InfoMsg006)).show();
		TaskManager.stop();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void failed() {
		super.failed();
		new MessageSnackBar(UtilMessage.build(MessageConst.ErrMsg003)).show();
		TaskManager.stop();
	}
}
