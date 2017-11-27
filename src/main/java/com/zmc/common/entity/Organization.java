package com.zmc.common.entity;

import com.zmc.common.BaseEntity;

/**
 * Created by zhongmc on 2017/6/27.
 */
public class Organization extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 名称
     */
    private String name;

    /**
     * 是否可用
     * 0：不可用
     * 1：可用
     */
    private  Integer available;

    /**
     * 描述
     */
    private String mydescribe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
 

    public String getMydescribe() {
		return mydescribe;
	}

	public void setMydescribe(String mydescribe) {
		this.mydescribe = mydescribe;
	}

	@Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", available=" + available +
                '}';
    }
}
