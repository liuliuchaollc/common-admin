package com.zmc.common.entity;

import com.zmc.common.BaseEntity;

public class YcSearchEntity extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 文件名称
     */
    private String title;
    /**
     * 命中加红
     */
    private String content;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
