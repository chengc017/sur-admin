package com.sohu.sur.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sohu.sur.dao.ScoreLogDao;
import com.sohu.sur.dao.ToolbarDao;
import com.sohu.sur.model.ScoreLog;
import com.sohu.sur.model.Toolbar;
import com.sohu.sur.service.ToolbarService;
import com.sohu.sur.util.Page;

public class ToolbarServiceImpl implements ToolbarService {

	private ToolbarDao toolbarDao;

	public ToolbarServiceImpl() {
	}

	public ToolbarServiceImpl(ToolbarDao toolbarDao) {
		this.toolbarDao = toolbarDao;
	}

	@Override
	public void saveToolbar(Toolbar toolbar) {
		// TODO Auto-generated method stub
		this.toolbarDao.update(toolbar);
	}

	@Override
	public Toolbar findToolbar() {
		// TODO Auto-generated method stub
		List<Toolbar> listToolbar = this.toolbarDao.selectAll();
		if (listToolbar == null || listToolbar.size() == 0) {
			return null;
		} else {
			return this.toolbarDao.selectAll().get(0);
		}
	}

}
