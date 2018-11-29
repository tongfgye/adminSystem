package com.thtf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.thtf.bean.ZtreeEntity;
import com.thtf.dao.ZtreeRepository;

@Service
public class ZtreeServiceImpl implements ZtreeService {

	@Autowired
	private ZtreeRepository dao;

	@Override
	public ZtreeEntity save(ZtreeEntity ztreeEntity) {

		ZtreeEntity en = dao.save(ztreeEntity);

		return en;
	}

	@Override
	public List<ZtreeEntity> findAll() {

		List<ZtreeEntity> en = dao.findAll();

		return en;
	}

	@Override
	public List<ZtreeEntity> findBypId(Integer pId) {
		List<ZtreeEntity> en = dao.findBypId(pId);
		return en;
	}

	@Override
	public void deleteBypId(Integer pId) {
		dao.deleteBypId(pId);
		// return null;
	}

	@Transactional
	public void deleteById(Integer id) {
		dao.deleteById(id);
		// return null;
	}

	@Override
	public void delete(ZtreeEntity ztreeEntity) {
		// TODO Auto-generated method stub
		dao.delete(ztreeEntity);
	}

	@Override
	public int getZtreeId() {
		int re = dao.getZtreeId();
		return re;
	}

	@Override
	public ZtreeEntity findById(Integer id) {
		// TODO Auto-generated method stub

		ZtreeEntity en = dao.findById(id).get();

		return en;
	}

}
