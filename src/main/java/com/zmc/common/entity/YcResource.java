package com.zmc.common.entity;

import com.zmc.common.BaseEntity;

public class YcResource extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 文件名称
     */
    private String name;
    /**
     * 文件描述
     */
    private String description;
    /**
     * 文件是否可用
     * 0：不可用
     * 1：可用
     */
    private Integer available;

  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	@Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", describtion='" + description + '\'' +
                ", available=" + available +
                '}';
    }
}
