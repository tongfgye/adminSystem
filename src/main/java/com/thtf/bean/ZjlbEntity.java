package com.thtf.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 专家类别
 */

@Entity
@Table(name = "T_ZJLB")
public class ZjlbEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 主键id
	@Column
	private String zjmc;// 专家名称
	@Column
	private Integer zjId;// 专家id
	@Column
	private String zjjs;// 专家介绍

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZjmc() {
		return zjmc;
	}

	public void setZjmc(String zjmc) {
		this.zjmc = zjmc;
	}

	public Integer getZjId() {
		return zjId;
	}

	public void setZjId(Integer zjId) {
		this.zjId = zjId;
	}

	public String getZjjs() {
		return zjjs;
	}

	public void setZjjs(String zjjs) {
		this.zjjs = zjjs;
	}

	@Override
	public String toString() {
		return "ZjlbEntity [id=" + id + ", zjmc=" + zjmc + ", zjId=" + zjId + ", zjjs=" + zjjs + "]";
	}

}
