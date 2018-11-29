package com.thtf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.thtf.bean.WttsEntity;
import com.thtf.dao.WttsRepository;

import java.util.List;


@Service
public class WttsServiceImpl implements WttsService {
	@Autowired
	private WttsRepository wttsrepository;

	public WttsEntity save(WttsEntity wttsentity) {
		return wttsrepository.save(wttsentity);
	}

	@Override
	public List<WttsEntity> saveAll(List list) {
		return null;
	}

	public List<WttsEntity> findAll() {
		return wttsrepository.findAll();
	}

	public WttsEntity findById(Integer id) {
		// TODO Auto-generated method stub
		return wttsrepository.findById(id).get();

	}

	public void deleteById(Integer id) {
		wttsrepository.deleteById(id);
	}

	public List<WttsEntity> findByStateAndReState(char state, char reState) {
		// TODO Auto-generated method stub
		return wttsrepository.findByStateAndReState(state, reState);
	}

	public List<WttsEntity> findByReState(char reState) {
		// TODO Auto-generated method stub
		return wttsrepository.findByReState(reState);
	}

	public List<WttsEntity> findBypId(Integer id) {
		// TODO Auto-generated method stub
		return wttsrepository.findByPId(id);
	}

	public int getZtreeId() {
		// TODO Auto-generated method stub
		return wttsrepository.getZtreeId();
	}

}
