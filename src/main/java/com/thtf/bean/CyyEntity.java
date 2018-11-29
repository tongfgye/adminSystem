package com.thtf.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * 
 * 常用语实体
 */

@Entity
@Table(name = "T_CYY")
public class CyyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 主键id
	@Column
	private String cyy;// 常用语
	@Column()
	@Temporal(TemporalType.TIMESTAMP)
	private Date cjsj;// 创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCyy() {
		return cyy;
	}

	public void setCyy(String cyy) {
		this.cyy = cyy;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	@Override
	public String toString() {
		return "CyyEntity [id=" + id + ", cyy=" + cyy + ", cjsj=" + cjsj + "]";
	}

}
