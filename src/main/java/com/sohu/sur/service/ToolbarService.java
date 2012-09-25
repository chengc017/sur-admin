package com.sohu.sur.service;

import com.sohu.sur.model.Toolbar;

public interface ToolbarService {

	/**
	 * 保存
	 * @param toolbar
	 */
	void saveToolbar(Toolbar toolbar);

	Toolbar  findToolbar();
}
