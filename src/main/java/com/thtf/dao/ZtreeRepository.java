package com.thtf.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.thtf.bean.WtxlEntity;
import com.thtf.bean.ZtreeEntity;

public interface ZtreeRepository extends JpaRepository<ZtreeEntity, Integer> {

	List<ZtreeEntity> findAll();

	List<ZtreeEntity> findBypId(Integer pId);

	@Transactional
	ZtreeEntity deleteBypId(int pId);

	@Transactional
	ZtreeEntity deleteById(int id);

	@Query(value = "SELECT MAX(z.id) FROM   ZtreeEntity z")
	int getZtreeId();

	// ZtreeEntity findByXlbh(String xlbh);
	//
	// List<ZtreeEntity> findByStateAndReState(char stateValid, char reState);
	//
	// List<ZtreeEntity> findByReState(char reState);
	
}
