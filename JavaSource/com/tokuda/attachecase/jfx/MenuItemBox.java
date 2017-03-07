package com.tokuda.attachecase.jfx;

import java.util.Collections;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("restriction")
public class MenuItemBox extends HBox {

	public MenuItemBox(final String label, final Image image, final KeyCodeCombination accelerator, final EventHandler<ActionEvent> handler) {
		super();

		setCursor(Cursor.HAND);
		setPrefWidth(300);

		BorderPane imagePane = new BorderPane();
		imagePane.setPrefWidth(30);

		MenuLabel title = new MenuLabel(label);
		title.setPrefWidth(170);

		Label shortcut = new Label();
		shortcut.setPrefWidth(100);

		getChildren().add(imagePane);
		getChildren().add(title);
		getChildren().add(shortcut);

		if (image != null) {
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imagePane.setCenter(imageView);
		}

		if (handler != null) {
			// addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			// handler.handle(new ActionEvent());
			// });
			title.setOnAction(handler);
		}

		if (accelerator != null) {
			title.setAccelerator(accelerator);
			shortcut.setText(accelerator.getDisplayText());
		}

		this.setOnMouseEntered(event -> {
			setStyle("-fx-background-color: #F5F5F5;");
		});

		this.setOnMouseExited(event -> {
			setStyle("-fx-background-color:transparent;");
		});
	}

	/**
	 * Menu label for make materialized menu.
	 *
	 * @author Toast kid
	 */
	private class MenuLabel extends Label {

		/**
		 * The action handler associated with this text field, or <tt>null</tt>
		 * if no action handler is assigned. The action handler is normally
		 * called when the user types the ENTER key.
		 */
		private final ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {

			@Override
			protected void invalidated() {
				setEventHandler(ActionEvent.ACTION, get());
			}

			@Override
			public Object getBean() {
				return MenuLabel.this;
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
		 * The accelerator property enables accessing the associated action in
		 * one keystroke. It is a convenience offered to perform quickly a given
		 * action.
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

		@Override
		protected Skin<?> createDefaultSkin() {
			return new MenuLabelSkin(this);
		}

		public MenuLabel(final String text) {
			super(text);
			setOnMouseClicked(event -> getOnAction().handle(new ActionEvent()));
		}
	}

	/**
	 * Overwrite for register shortcut.
	 *
	 * @author Toast kid
	 */
	private class MenuLabelSkin extends LabeledSkinBase<Label, BehaviorBase<Label>> {

		/**
		 * Initialize with {@link MenuLabel}.
		 *
		 * @param label
		 *            {@link MenuLabel}
		 */
		public MenuLabelSkin(final MenuLabel label) {
			super(label, new BehaviorBase<>(label, Collections.emptyList()));

			// Labels do not block the mouse by default, unlike most other UI
			// Controls.
			consumeMouseEvents(false);

			registerChangeListener(label.labelForProperty(), "LABEL_FOR");

			if (label.getAccelerator() == null) {
				return;
			}
			getSkinnable().getScene().getAccelerators().put(label.getAccelerator(), () -> label.getOnAction().handle(new ActionEvent()));
		}

		@Override
		protected void handleControlPropertyChanged(final String p) {
			super.handleControlPropertyChanged(p);
			if ("LABEL_FOR".equals(p)) {
				mnemonicTargetChanged();
			}
		}
	}
}
