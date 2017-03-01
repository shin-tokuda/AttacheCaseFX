package com.tokuda.attachecase.jfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItem extends HBox {

	/**
	 * The action handler associated with this text field, or <tt>null</tt> if
	 * no action handler is assigned. The action handler is normally called when
	 * the user types the ENTER key.
	 */
	private final ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {

		@Override
		protected void invalidated() {
			setEventHandler(ActionEvent.ACTION, get());
		}

		@Override
		public Object getBean() {
			return MenuItem.this;
		}

		@Override
		public String getName() {
			return "onAction";
		}
	};

	public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
		return onAction;
	}

	public final EventHandler<ActionEvent> getOnAction() {
		return onActionProperty().get();
	}

	public final void setOnAction(EventHandler<ActionEvent> value) {
		onActionProperty().set(value);
	}

	/**
	 * The accelerator property enables accessing the associated action in one
	 * keystroke. It is a convenience offered to perform quickly a given action.
	 */
	private ObjectProperty<KeyCombination> accelerator;

	public final void setAccelerator(KeyCombination value) {
		acceleratorProperty().set(value);
	}

	public final KeyCombination getAccelerator() {
		return accelerator == null ? null : accelerator.get();
	}

	public final ObjectProperty<KeyCombination> acceleratorProperty() {
		if (accelerator == null) {
			accelerator = new SimpleObjectProperty<KeyCombination>(this, "accelerator");
		}
		return accelerator;
	}
}