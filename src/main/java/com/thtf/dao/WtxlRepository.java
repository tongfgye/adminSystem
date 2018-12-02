package com.thtf.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//import com.thtf.bean.WttsEntity;
//import org.springframework.data.jpa.repository;
import com.thtf.bean.WtxlEntity;

@Repository
public interface WtxlRepository extends JpaRepository<WtxlEntity, Integer>, CrudRepository<WtxlEntity, Integer>,
		JpaSpecificationExecutor<WtxlEntity> {

	WtxlEntity findByZbbh(Integer zbbh);

//	WtxlEntity findByWid(Integer wid);

	WtxlEntity findByid(Integer id);

	WtxlEntity findByXlbh(String xlbh);

	List<WtxlEntity> findByStateAndReState(char stateValid, char reState);

	List<WtxlEntity> findByReState(char reState);

	@Query(value = "SELECT MAX(z.id) FROM   WtxlEntity z")
	int getZtreeId();

	List<WtxlEntity> findByPId(Integer pid);
}
