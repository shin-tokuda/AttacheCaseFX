package com.tokuda.common.constant;

import javafx.scene.image.Image;
import lombok.Getter;

public enum IconConst {
	// アプリケーションアイコン
	Application(new Image("/image/24x24/briefcase.png")),
	// フルスクリーンアイコン
	FullResize(new Image("./image/16x16/application-resize-full.png")),
	// ターミナルアイコン
	Terminal(new Image("./image/16x16/application-terminal.png")),
	// トレイアイコン
	Tray(new Image("./image/16x16/briefcase.png")),
	// ファイルオープンアイコン
	FileOpen(new Image("./image/16x16/folder-horizontal-open.png")),
	// ファイル保存アイコン
	FileSave(new Image("./image/16x16/disk-black.png")),
	// ファイルクローズアイコン
	FileClose(new Image("./image/16x16/minus-circle-frame.png")),
	// ドキュメントアイコン
	Document(new Image("./image/16x16/document-search-result.png")),
	// ディレクトリアイコン
	Directory(new Image("./image/16x16/folder-search-result.png"));

	@Getter
	private Image image;

	/**
	 * コンストラクタ
	 *
	 * @param image
	 */
	private IconConst(final Image image) {
		this.image = image;
	}
}
