package com.thtf.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
//@Data
@Table(name = "T_WTTS")
public class WttsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;// 叶子节点 private String csbh;// 测试编号
	@Column
	public Integer pId;// 父节点 问题编号 private String fucsbh;// 测试编号
	@Column
	public String name;// 问题描述，相当于pid的name
	@Column
	public String open;// 节点是否打开
	@Column(length = 2000)
	private String zbbh;// 装备编号

	@Column(length = 2000)
	private String csrw;// 测试任务，调试步骤

	@Column
	private String createTime;// 创建时间

	@Column
	private char state;// 记录状态 0 新增 1 修改 2 删除 3 生效 4 重复
	@Column
	private char reState;// 审核状态 : 0 未审核 1审核通过 2 审核拒绝

	@Column
	private Integer pv;// 0 审核中 1审核通过 2 审核拒绝

	public WttsEntity() {
		super();

	}

	public WttsEntity(Integer id, Integer pId, String name, String open, String zbbh, String csrw, String createTime,
			char state, char reState, Integer pv) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
		this.zbbh = zbbh;
		this.csrw = csrw;
		this.createTime = createTime;
		this.state = state;
		this.reState = reState;
		this.pv = pv;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getZbbh() {
		return zbbh;
	}

	public void setZbbh(String zbbh) {
		this.zbbh = zbbh;
	}

	public String getCsrw() {
		return csrw;
	}

	public void setCsrw(String csrw) {
		this.csrw = csrw;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
	}

	public char getReState() {
		return reState;
	}

	public void setReState(char reState) {
		this.reState = reState;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

}
