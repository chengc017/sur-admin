package com.sohu.sur.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * excel读取工具类
 * 
 * @author xuewuhao
 * 
 */
public class ExcelUtil {
	private static Log log = LogFactory.getLog(ExcelUtil.class);

	/**
	 * 读取excel内容,返回（List）
	 * 
	 * @param fileName
	 * @return
	 */
	public static List readExcel(String fileName) {
		List listTmp = new ArrayList();
		try {
			Workbook book = Workbook.getWorkbook(new File(fileName));
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			// 得到第一列第一行的单元格
			int columnum = sheet.getColumns(); // 得到列数
			int rownum = sheet.getRows(); // 得到行数
			for (int i = 0; i < rownum; i++){ // 循环进行读写
				for (int j = 0; j < columnum; j++) {
					Cell cell1 = sheet.getCell(j, i);
					String result = cell1.getContents();
					if (!listTmp.contains(result.trim())) {
						listTmp.add(result);
					}
				}
			}
			log.info("read excel over  size="+listTmp.size());
			book.close();
		} catch (Exception e) {
			log.equals(e);
		}
		return listTmp;
	}
}
