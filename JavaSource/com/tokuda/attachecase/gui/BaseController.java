package com.tokuda.attachecase.gui;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tokuda.attachecase.SystemData;
import com.tokuda.attachecase.constant.CharSet;
import com.tokuda.attachecase.dialog.MessageSnackBar;
import com.tokuda.attachecase.dto.ApplicationDTO;
import com.tokuda.common.constant.MessageConst;
import com.tokuda.common.util.UtilFile;
import com.tokuda.common.util.UtilMessage;

import javafx.stage.FileChooser;
import lombok.Setter;

public abstract class BaseController<T extends BaseSaveDTO> {

	@Setter
	protected ApplicationDTO appDTO;

	private T _saveDTO;

	protected <E extends BaseSaveDTO> E getSaveDTO() {
		return (E) _saveDTO;
	}

	protected void setSaveDTO(T saveDTO) {
		this._saveDTO = saveDTO;
	}

	protected T open(final File file, Class<T> cls) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			_saveDTO = mapper.readValue(file, cls);
		} catch (IOException ex) {
			ex.printStackTrace();
			new MessageSnackBar(UtilMessage.build(MessageConst.ErrMsg003)).show();
		}
		return _saveDTO;
	}

	public File save() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle(UtilMessage.build(MessageConst.InfoMsg003));
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(appDTO.getTitle() + " (*." + appDTO.getExtension() + ")", appDTO.getExtension()));
		File file = chooser.showSaveDialog(SystemData.stage);

		if (file != null) {
			preSave();

			try {
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.INDENT_OUTPUT);
				mapper.writeValue(UtilFile.getBufferedWriter(file, CharSet.Utf8.getValue()), _saveDTO);
				new MessageSnackBar(UtilMessage.build(MessageConst.InfoMsg005)).show();
			} catch (IOException ex) {
				ex.printStackTrace();
				new MessageSnackBar(UtilMessage.build(MessageConst.ErrMsg003)).show();
			}
		}
		return file;
	}

	public File save(final File file) {
		preSave();

		if (file != null) {

			try {
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.INDENT_OUTPUT);
				mapper.writeValue(UtilFile.getBufferedWriter(file, CharSet.Utf8.getValue()), _saveDTO);
				new MessageSnackBar(UtilMessage.build(MessageConst.InfoMsg005)).show();
			} catch (IOException ex) {
				ex.printStackTrace();
				new MessageSnackBar(UtilMessage.build(MessageConst.ErrMsg003)).show();
			}
		}
		return file;
	}

	public abstract void open(final File file);

	protected abstract void preSave();
}
