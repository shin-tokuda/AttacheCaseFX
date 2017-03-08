package com.tokuda.common.constant;

import com.tokuda.common.util.UtilFile;

import javafx.scene.image.Image;
import lombok.Getter;

public enum IconConst {
	// アプリケーションアイコン
	Application(UtilFile.getImage("image/briefcase.png")),
	// アタッシュケースアイコン
	AttacheCase(UtilFile.getImage("image/ic_work_white_24dp_2x.png")),
	// フルスクリーンアイコン
	FullResize(UtilFile.getImage("image/ic_zoom_out_map_black_24dp_2x.png")),
	// ターミナルアイコン
	Terminal(UtilFile.getImage("image/ic_keyboard_black_24dp_2x.png")),
	// トレイアイコン
	Tray(UtilFile.getImage("image/briefcase.png")),
	// ファイルオープンアイコン
	FileOpen(UtilFile.getImage("image/ic_insert_drive_file_black_24dp_2x.png")),
	// ファイル保存アイコン
	FileSave(UtilFile.getImage("image/ic_save_black_24dp_2x.png")),
	// ファイルクローズアイコン
	FileClose(UtilFile.getImage("image/ic_remove_circle_black_24dp_2x.png")),
	// ドキュメントアイコン
	Document(UtilFile.getImage("image/ic_library_books_black_24dp_2x.png")),
	// ディレクトリアイコン
	Directory(UtilFile.getImage("image/ic_search_black_24dp_2x.png"));

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
