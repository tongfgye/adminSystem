package com.thtf.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thtf.bean.WttsEntity;

@Repository
public interface WttsRepository extends JpaRepository<WttsEntity, Integer>,CrudRepository<WttsEntity, Integer>,JpaSpecificationExecutor<WttsEntity>{

	Optional<WttsEntity> findById(Integer id);

	List<WttsEntity> findByStateAndReState(char state, char reState);

	List<WttsEntity> findByReState(char reState);

	List<WttsEntity> findByPId(Integer id);
	@Query(value = "SELECT MAX(z.id) FROM   WttsEntity z")
	int getZtreeId();
	

}
