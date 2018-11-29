package com.thtf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thtf.bean.WttsEntity;
import com.thtf.bean.WtxlEntity;

public interface WtxlRepository extends JpaRepository<WtxlEntity, Long> {

	WtxlEntity findByZbbh(Integer zbbh);
	WtxlEntity findByWid(Integer wid);
	WtxlEntity findByXlbh(String xlbh);
	List<WtxlEntity> findByStateAndReState(char stateValid, char reState);
	List<WtxlEntity> findByReState(char reState);
	
	
}
