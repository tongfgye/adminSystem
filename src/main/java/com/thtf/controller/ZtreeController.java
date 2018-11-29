/*package com.thtf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thtf.bean.ZtreeEntity;
import com.thtf.service.ZtreeService;
import com.thtf.service.ZtreeServiceImpl;

@RestController
@CrossOrigin
public class ZtreeController {

	@Autowired
	private ZtreeService service;

	@RequestMapping("/savaztree")
	public ZtreeEntity saveZtree(Integer id, Integer pId, String name) {
		ZtreeEntity en = new ZtreeEntity();
		en.setId(id);
		en.setpId(pId);
		en.setName(name);
		ZtreeEntity reEn = service.save(en);
		return reEn;
	}

	@RequestMapping("/getztree")
	@ResponseBody
	public List<ZtreeEntity> getData() {
		List<ZtreeEntity> ls = new ArrayList<>();
		ls = service.findAll();
		return ls;
	}

	@RequestMapping("/delete")
	public void deleteNode(Integer id) {
		List<ZtreeEntity> ls = service.findBypId(id);
		if (ls != null) {
			// 删除父id
			for (ZtreeEntity ztreeEntity : ls) {
				service.delete(ztreeEntity);
			}
		}
		// 删除id
		service.deleteById(id);
	}

	@RequestMapping("/editTreeNodeName")
	public void deleteNode(Integer id,String name) {
		ZtreeEntity entity = service.findById(id);
		// 删除id
		entity.setName(name);
		
		service.save(entity);
	}
	
	@RequestMapping("/getZtreeId")
	public int getZtreeId() {
		return service.getZtreeId();

	}

	@RequestMapping("/rename")
	public String reName(Integer id, String name) {

		ZtreeEntity en = service.findById(id);
		en.setName(name);
		service.save(en);
		return "success";
	}

	@RequestMapping("/gethello")
	public String showHello() {

		return "hello world";
	}

}
*/