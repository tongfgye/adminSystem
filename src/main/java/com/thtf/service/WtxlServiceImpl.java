package com.thtf.service;

import com.thtf.bean.WttsEntity;
import com.thtf.bean.WtxlEntity;
import com.thtf.dao.WtxlRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.print.DocFlavor.STRING;

@Service
public class WtxlServiceImpl implements WtxlService {
	@Autowired
	private WtxlRepository wtxlrepository;

	public WtxlEntity save(WtxlEntity wtxlentity) {
		return wtxlrepository.save(wtxlentity);
	}

	@Override
	public List<WtxlEntity> saveAll(List list) {
		return null;
	}

	public List<WtxlEntity> findAll() {
		return wtxlrepository.findAll();
	}

	public WtxlEntity findByZbbh(Integer zbbh) {
		return wtxlrepository.findByZbbh(zbbh);
	}

	public WtxlEntity findByWtbh(Integer wtbh) {

		return null;
	}

	public WtxlEntity findById(Integer id) {
		// TODO Auto-generated method stub
		return wtxlrepository.findByWid(id);
	}

	public WtxlEntity findByXlbh(String xlbh) {
		
		return wtxlrepository.findByXlbh(xlbh);
	}

	public List<WtxlEntity> findByStateAndReState(char stateValid, char reState) {
		// TODO Auto-generated method stub
		return wtxlrepository.findByStateAndReState(stateValid,reState);
	}

	public List<WtxlEntity> findByReState(char reState) {
		// TODO Auto-generated method stub
		return wtxlrepository.findByReState(reState);
	}

}
