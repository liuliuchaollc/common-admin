package com.zmc.common.entity;

import java.util.List;

import com.zmc.common.BaseEntity;

public class YcPageEntity extends BaseEntity {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
 
	/**
	 * 当前页码
	 */
    private int pageIndex;
    /**
     * 总页数
     */
    private int pagecount;
    /**
     * 总记录数
     */
    private int allSize;
    
    /**
     * 每页记录数
     */
    private int pageSize;
    
    /**
     * 记录
     */
    private List<?> list;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public int getAllSize() {
		return allSize;
	}

	public void setAllSize(int allSize) {
		this.allSize = allSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
 
    
}
