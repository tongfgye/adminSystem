package com.thtf.service;

import java.util.List;

import com.thtf.bean.WtxlEntity;
import com.thtf.bean.ZtreeEntity;

public interface ZtreeService {
	ZtreeEntity save(ZtreeEntity ztreeEntity);

	List<ZtreeEntity> findAll();

	List<ZtreeEntity> findBypId(Integer pId);

	void deleteBypId(Integer pId);

	void deleteById(Integer id);

	void delete(ZtreeEntity ztreeEntity);

	int getZtreeId();

	ZtreeEntity findById(Integer id);

}
