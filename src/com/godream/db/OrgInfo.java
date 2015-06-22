package com.godream.db;

import java.io.Serializable;

public class OrgInfo implements Serializable{
	/**组织结构id*/
	private String id;
	/**当前组织的父id*/
	private String parentId;
	/**当前组织的名字*/
	private String name;
	/**当前组织的简称*/
	private String shortName;
	/**同步时间戳*/
	private String synchro;
	/**当前使用状态 0：取消 1：正在使用*/
	private String status;
	/**当前组织的跟目录guid*/
	private String rootGuid;
	public OrgInfo()
	{
		
	}
	public OrgInfo(String id, String parentId, String name, String synchro, String status, String rootGuid){
		this.id=id;
		this.parentId=parentId;
		this.name=name;
		this.synchro=synchro;
		this.status=status;
		this.rootGuid=rootGuid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSynchro() {
		return synchro;
	}
	public void setSynchro(String synchro) {
		this.synchro = synchro;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRootGuid() {
		return rootGuid;
	}
	public void setRootGuid(String rootGuid) {
		this.rootGuid = rootGuid;
	}
	
	
}
