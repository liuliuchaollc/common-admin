package com.zmc.common.entity;

import com.zmc.common.BaseEntity;

public class YcResourceServer extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 文件名称
     */
    private String name;
    private String type;
    private String ip;
    private String ostype;
    private String usedesc;
    private String username;
    private String password;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	 
	public String getUsedesc() {
		return usedesc;
	}

	public void setUsedesc(String usedesc) {
		this.usedesc = usedesc;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
