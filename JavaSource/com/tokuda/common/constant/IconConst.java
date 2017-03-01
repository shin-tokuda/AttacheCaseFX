package com.tokuda.common.constant;

import com.tokuda.common.util.UtilFile;

import javafx.scene.image.Image;
import lombok.Getter;

public enum IconConst {
	// アプリケーションアイコン
	Application(UtilFile.getImage("resource/image/24x24/briefcase.png")),
	// フルスクリーンアイコン
	FullResize(UtilFile.getImage("resource/image/16x16/application-resize-full.png")),
	// ターミナルアイコン
	Terminal(UtilFile.getImage("resource/image/16x16/application-terminal.png")),
	// トレイアイコン
	Tray(UtilFile.getImage("resource/image/16x16/briefcase.png")),
	// ファイルオープンアイコン
	FileOpen(UtilFile.getImage("resource/image/16x16/folder-horizontal-open.png")),
	// ファイル保存アイコン
	FileSave(UtilFile.getImage("resource/image/16x16/disk-black.png")),
	// ファイルクローズアイコン
	FileClose(UtilFile.getImage("resource/image/16x16/minus-circle-frame.png")),
	// ドキュメントアイコン
	Document(UtilFile.getImage("resource/image/16x16/document-search-result.png")),
	// ディレクトリアイコン
	Directory(UtilFile.getImage("resource/image/16x16/folder-search-result.png"));

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
