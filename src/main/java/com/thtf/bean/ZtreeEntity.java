package com.thtf.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "T_ZTREE")
public class ZtreeEntity {

	@Id
	public Integer id;
	@Column
	public Integer pId;
	@Column
	public String name;
	@Column
	public String open;

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

}
