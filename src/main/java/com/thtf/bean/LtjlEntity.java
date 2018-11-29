package com.thtf.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

/**
 * 聊天记录
 * 
 * @author admin
 *
 */
public class LtjlEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 主键id

	@Column
	private Integer fqyhid;// 发起用户id
	@Column
	private Integer jsyhid;// 接受用户id
	@Column
	private String ltxx;// 连天信息
	@Column
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date ltsj;// 聊天时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFqyhid() {
		return fqyhid;
	}

	public void setFqyhid(Integer fqyhid) {
		this.fqyhid = fqyhid;
	}

	public Integer getJsyhid() {
		return jsyhid;
	}

	public void setJsyhid(Integer jsyhid) {
		this.jsyhid = jsyhid;
	}

	public String getLtxx() {
		return ltxx;
	}

	public void setLtxx(String ltxx) {
		this.ltxx = ltxx;
	}

	public Date getLtsj() {
		return ltsj;
	}

	public void setLtsj(Date ltsj) {
		this.ltsj = ltsj;
	}

	@Override
	public String toString() {
		return "LtjlEntity [id=" + id + ", fqyhid=" + fqyhid + ", jsyhid=" + jsyhid + ", ltxx=" + ltxx + ", ltsj="
				+ ltsj + "]";
	}

}
