package com.insonix.klv.threads;

import com.insonix.klv.dao.KlvDataDao;
import com.insonix.klv.model.KLVProperties;

/**
 * @author Pramod Maurya
 * @since 24 April-2018
 */
public class KlvDataSave implements Runnable {

	private final KLVProperties klvProperties;

	public KlvDataSave(KLVProperties klvProperties) {
		this.klvProperties = klvProperties;
	}

	@Override
	public void run() {
		KlvDataDao klvDataDao = new KlvDataDao();
		klvDataDao.saveKlvData(klvProperties);
	}

}
