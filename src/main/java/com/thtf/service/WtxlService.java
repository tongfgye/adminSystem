package com.thtf.service;


import org.springframework.stereotype.Service;

import com.thtf.bean.WtxlEntity;

import java.util.List;

public interface WtxlService {
	// Users save(Users u);
	// List<Users> saveAll(List list);

	WtxlEntity save(WtxlEntity wtxlentity);

	List<WtxlEntity> saveAll(List list);
}
