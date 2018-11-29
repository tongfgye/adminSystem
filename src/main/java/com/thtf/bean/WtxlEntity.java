package com.thtf.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/*
 * 知识库
 */
@Entity
//@Data
@Table(name = "T_WTXL")
public class WtxlEntity {

	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer wid;
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
	@Column
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

	public Integer getWid() {
		return wid;
	}

	public void setWid(Integer wid) {
		this.wid = wid;
	}

	public String getZbbh() {
		return zbbh;
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
