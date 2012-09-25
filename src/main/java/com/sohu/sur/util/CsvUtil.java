package com.sohu.sur.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author leiyangbj6779
 * 
 */
public class CsvUtil {

	private String filename = null;
	private BufferedReader bufferedreader = null;
	private List list = new ArrayList();

	public CsvUtil() {
	}

	public CsvUtil(String filename) throws IOException {
		this.filename = filename;
		bufferedreader = new BufferedReader(new FileReader(filename));
		String stemp;
		while ((stemp = bufferedreader.readLine()) != null) {
			list.add(stemp);
		}
	}

	public CsvUtil(File file) throws IOException {
		bufferedreader = new BufferedReader(new FileReader(file));
		String stemp;
		while ((stemp = bufferedreader.readLine()) != null) {
			list.add(stemp);
		}
	}

	public List getList() throws IOException {
		return list;
	}

	// 得到csv文件的行数
	public int getRowNum() {
		return list.size();
	}

	// 得到csv文件的列数
	public int getColNum() {
		if (!list.toString().equals("[]")) {
			if (list.get(0).toString().contains(",")) { // csv文件中，每列之间的是用','来分隔的
				return list.get(0).toString().split(",").length;
			} else if (list.get(0).toString().trim().length() != 0) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 取得指定行的值

	public String getRow(int index) {
		if (this.list.size() != 0)
			return (String) list.get(index);
		else
			return null;
	}

	// 取得指定列的值
	public String getCol(int index) {
		if (this.getColNum() == 0) {
			return null;
		}
		StringBuffer scol = new StringBuffer();
		String temp = null;
		int colnum = this.getColNum();
		if (colnum > 1) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				temp = it.next().toString();
				scol = scol.append(temp.split(",")[index] + ",");
			}
		} else {
			for (Iterator it = list.iterator(); it.hasNext();) {
				temp = it.next().toString();
				scol = scol.append(temp + ",");
			}
		}
		String str = new String(scol.toString());
		str = str.substring(0, str.length() - 1);
		return str;
	}

	// 取得指定行，指定列的值
	public String getString(int row, int col) {
		String temp = null;
		int colnum = this.getColNum();
		if (colnum > 1) {
			temp = list.get(row).toString().split(",")[col];
		} else if (colnum == 1) {
			temp = list.get(row).toString();
		} else {
			temp = null;
		}
		return temp;
	}

	public void CsvClose() throws IOException {
		this.bufferedreader.close();
	}

	public void run(String filename) throws IOException {
		CsvUtil cu = new CsvUtil(filename);
		for (int i = 0; i < cu.getRowNum(); i++) {

			String name = cu.getString(i, 0);// 得到第i行.第一列的数据.
			System.out.println(name);
		}
		cu.CsvClose();
	}

	public static List<String> getFirstColDataList(String filename) throws IOException {
		List<String> list = new ArrayList<String>();
		CsvUtil cu = new CsvUtil(filename);
		for (int i = 0; i < cu.getRowNum(); i++) {
			String name = cu.getString(i, 0);// 得到第i行.第一列的数据.
			if (null == name || "".equals(name)) {
				continue;
			}
			list.add(name.trim());
		}
		cu.CsvClose();
		return list;
	}

	/**
	 * 获得第col列的 数据，col从0列开始
	 * @param col
	 * @return
	 * @throws IOException
	 */
	public List<String> getColData(int col) throws IOException {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < this.getRowNum(); i++) {
			String name = this.getString(i, col);// 得到第i行.第一列的数据.
			if (null == name || "".equals(name)) {
				continue;
			}
			list.add(name.trim());
		}
		return list;
	}

	public static void main(String[] args) throws IOException {
		CsvUtil test = new CsvUtil();
		List<String> list = test.getFirstColDataList("D:/banned.csv");
		for (String s : list) {
			System.out.println(s);
		}

	}
}
