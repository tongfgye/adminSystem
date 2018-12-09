package com.thtf.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/*
 * 知识库
 */
@Entity
//@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "T_WTXL")
public class WtxlEntity {

	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// private Long id;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer wid;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;// 叶子节点 private String csbh;// 测试编号
	@Column
	public Integer pId;// 父节点 问题编号 private String fucsbh;// 测试编号
	@Column
	public String name;// 问题描述，相当于pid的name
	@Column
	public String open;// 节点是否打开
	@Column
	private String zbbh;// 装备编号

	@Column
	private String wtbh;// 问题编号

	@Column(length = 300)
	private String wtnr;// 问题内容
	@Column(unique = true)
	private String xlbh;// 修理编号
	@Column(length = 2000)
	private String xlbz;// 修理步骤

	@Column(length = 2000)
	private String xlbzText;// 修理步骤

	@CreatedDate
	private String createTime;// 创建时间

	@Column
	private char state;// 记录状态 0 新增 1 修改 2 删除 3 生效

	@Column
	private char reState;// 0 审核中 1审核通过 2 审核拒绝

	@Column
	private Integer pv;// 0 审核中 1审核通过 2 审核拒绝

	public WtxlEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WtxlEntity(String zbbh, String wtbh, String wtnr, String xlbh, String xlbz) {
		super();
		this.zbbh = zbbh;
		this.wtbh = wtbh;
		this.wtnr = wtnr;
		this.xlbh = xlbh;
		this.xlbz = xlbz;
	}

	public WtxlEntity(String zbbh, String wtbh, String wtnr, String xlbh, String xlbz, String createTime) {
		super();
		this.zbbh = zbbh;
		this.wtbh = wtbh;
		this.wtnr = wtnr;
		this.xlbh = xlbh;
		this.xlbz = xlbz;
		this.createTime = createTime;

	}

//	public Integer getWid() {
//		return wid;
//	}
//
//	public void setWid(Integer wid) {
//		this.wid = wid;
//	}

	public String getZbbh() {
		return zbbh;
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

	public void setZbbh(String zbbh) {
		this.zbbh = zbbh;
	}

	public String getWtbh() {
		return wtbh;
	}

	public void setWtbh(String wtbh) {
		this.wtbh = wtbh;
	}

	public String getWtnr() {
		return wtnr;
	}

	public void setWtnr(String wtnr) {
		this.wtnr = wtnr;
	}

	public String getXlbh() {
		return xlbh;
	}

	public void setXlbh(String xlbh) {
		this.xlbh = xlbh;
	}

	public String getXlbz() {
		return xlbz;
	}

	public void setXlbz(String xlbz) {
		this.xlbz = xlbz;
	}

	public String getXlbzText() {
		return xlbzText;
	}

	public void setXlbzText(String xlbzText) {
		this.xlbzText = xlbzText;
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
