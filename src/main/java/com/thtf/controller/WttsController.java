package com.thtf.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thtf.bean.WttsEntity;
import com.thtf.constant.CodeType;
import com.thtf.constant.Constant;
import com.thtf.service.WttsServiceImpl;

import jxl.Sheet;
import jxl.Workbook;

@RestController
@RequestMapping("/wtts")
@CrossOrigin
public class WttsController {

	@Autowired
	private WttsServiceImpl wttsServiceimpl;

	@RequestMapping(value = "/uploadWtts", method = RequestMethod.POST)
	public @ResponseBody CodeType uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		CodeType code = new CodeType();
		code.setCode("1");
		String failMessage = "";
		String successMessage = "";

		int failCount = 0;
		int successCount = 0;
		// 指定文件存放路径，可以是相对路径或者绝对路径
		String filePath = "./src/main/resources/templates/imgupload/";
		try {
			uploadFile(file.getBytes(), filePath, fileName);
			File newFile = new File(filePath, fileName);
			List<WttsEntity> lists = getAllByExcel(newFile);
			for (WttsEntity wttsEntity : lists) {
				wttsEntity.setReState('0');
				if (wttsEntity.getId() == null || wttsEntity.getId().toString() == "") {
					wttsEntity.setState('0');
				} else {
					wttsEntity.setState('1');
				}
				wttsEntity.setPv(0);
				wttsEntity.setCreateTime(df.format(new Date()));
				// wttsServiceimpl.save(wttsEntity);
				try {
					WttsEntity en = wttsServiceimpl.save(wttsEntity);
					System.out.println(en);
					successCount++;
				} catch (Exception e) {
					failCount++;
				}
			}
		} catch (Exception e) {
			// return "fail";
			return code;
		}
		// 返回json
		// return "success";
		code.setMessage("成功问题编号:" + (successMessage.equals("") ? "无" : successMessage) + "\n失败问题编号："
				+ (failMessage.equals("") ? "无" : failMessage));
		code.setFailCount("导入失败条数：" + String.valueOf(failCount));
		code.setSuccessCount("导入成功条数：" + String.valueOf(successCount));
		return code;
	}

	/**
	 * 上传文件到服务器
	 * 
	 * @param file
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	/**
	 * 查询指定目录中电子表格中所有的数据
	 * 
	 * @param file 文件完整路径
	 * @return
	 */
	public List<WttsEntity> getAllByExcel(File file) {
		List<WttsEntity> list = new ArrayList<WttsEntity>();
		try {
			Workbook rwb = Workbook.getWorkbook(file);// new File(file)
			Sheet rs = rwb.getSheet("Sheet1");// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			System.out.println(clos + " rows:" + rows);
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					// 装备编号 问题编号 问题描述 父编号 测试编号 测试任务

					String id = rs.getCell(j++, i).getContents();//

					String pId = rs.getCell(j++, i).getContents();//
					String name = rs.getCell(j++, i).getContents();//
					String open = rs.getCell(j++, i).getContents();//
					String zbbh = rs.getCell(j++, i).getContents();//
					String csrw = rs.getCell(j++, i).getContents();//

					/*
					 * public WttsEntity(Integer id, Integer pId, String name, String open, String
					 * zbbh, String csrw, String createTime, char state, char reState, Integer pv) {
					 */
					list.add(new WttsEntity(Integer.valueOf(id), Integer.valueOf(pId), name, open, zbbh, csrw,
							df.format(new Date()), '3', '1', new Integer(0)));

				}
			}
		} catch (Exception e) {
			return null;
		}
		return list;

	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	@RequestMapping("/findAllWtts")
	@ResponseBody
	public List<WttsEntity> wtxlfindAll() {
		return wttsServiceimpl.findAll();
	}

	/**
	 * 查询正在生效的
	 * 
	 * @return
	 */
	@RequestMapping("/findByStateAndReState")
	@ResponseBody

	public List<WttsEntity> findByStateAndReState(@RequestParam char reState) {

		if ((Constant.RESTATE_APPROVE + "").equals(reState + "")) {
			return wttsServiceimpl.findByStateAndReState(Constant.STATE_VALID, reState);
		}
		return wttsServiceimpl.findByReState(reState);

	}

	/**
	 * 查询待审核记录
	 * 
	 * @return
	 */
	@RequestMapping("/findByReState")
	@ResponseBody
	public List<WttsEntity> findByReState(@RequestParam char reState) {

		Set<Integer> pids = new HashSet<>();
		Integer pid = 0;
		// 找到需要显示的数据
		List<WttsEntity> en = wttsServiceimpl.findByReState(reState);

		for (WttsEntity wttsEntity : en) {

			pid = wttsEntity.getpId();
			pids.add(pid);
		}

		// 找到父亲节点
		for (Integer pidd : pids) {
			if (pidd != 0) {
				WttsEntity entity = wttsServiceimpl.findById(pidd);
				if (!en.contains(entity))
					en.add(entity);
			}
		}
		// wttsServiceimpl.findByReState(reState);
		return en;
	}

	/**
	 * 审核通过
	 * 
	 * @return
	 */
	@RequestMapping("/approveWtts/{ids}")
	public String approveWtts(@PathVariable String ids) {
		if (null != ids || !ids.equals("")) {
			String[] idsArr = ids.split(",");
			for (String id : idsArr) {
				WttsEntity entity = wttsServiceimpl.findById(Integer.valueOf(id));
				entity.setReState(Constant.RESTATE_APPROVE);
				if ((Constant.STATE_DELETE + "").equals(entity.getState() + "")) {

				} else {
					entity.setState(Constant.STATE_VALID);
				}
				wttsServiceimpl.save(entity);
			}
		}
		return "success";

	}

	/**
	 * 审核拒绝
	 * 
	 * @return
	 */
	@RequestMapping("/refuseWtts/{ids}")
	@ResponseBody
	public String refuseWtts(@PathVariable String ids) {
		if (null != ids || ids.equals("")) {
			String[] idsArr = ids.split(",");
			for (String id : idsArr) {
				WttsEntity entity = wttsServiceimpl.findById(Integer.valueOf(id));
				entity.setReState(Constant.RESTATE_REFUSE);
				wttsServiceimpl.save(entity);
			}
		}
		return "success";
	}

	@RequestMapping("/findByTsId")
	@ResponseBody
	public WttsEntity wttsfindByTsId(@RequestParam("id") Integer id) {

		WttsEntity wttsEntity = wttsServiceimpl.findById(id);
		return wttsEntity;
	}

	/**
	 * 新增或修改
	 * 
	 * @param wttsEntity
	 * @return
	 */
	@RequestMapping("/wttsSave")
	@ResponseBody
	public WttsEntity wttsSave(@ModelAttribute WttsEntity wttsEntity) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null == wttsEntity) {
			return null;
		}
		// state;// 记录状态 0 新增 1 修改 2 删除 3 生效
		// reState;// 0 审核中 1审核通过 2 审核拒绝

		wttsEntity.setReState('0');
		if (wttsEntity.getId() == null || wttsEntity.getId().toString() == "") {
			wttsEntity.setState('0');
			wttsEntity.setPv(0);
		} else {
			wttsEntity.setState('1');
		}
		wttsEntity.setCreateTime(df.format(new Date()));
		WttsEntity newEntity = wttsServiceimpl.save(wttsEntity);
		return newEntity;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@RequestMapping("/deleteWtts/{id}")
	@ResponseBody
	public WttsEntity deleteWtts(@PathVariable Integer id) {
		WttsEntity wttsEntity = wttsServiceimpl.findById(id);
		wttsEntity.setState(Constant.STATE_DELETE);
		wttsEntity.setReState(Constant.RESTATE_TODO);
		WttsEntity newEntity = wttsServiceimpl.save(wttsEntity);

		return newEntity;

	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/findTreeList")
	@ResponseBody
	public List<WttsEntity> findTreeList() {
		List<WttsEntity> list = wttsServiceimpl.findAll();
		return list;
	}

	/**
	 * 保存ztree树的信息
	 * 
	 * @param id
	 * @param pId
	 * @param name
	 * @param reState
	 * @return
	 */
	@RequestMapping("/savaztree")
	public WttsEntity saveZtree(Integer id, Integer pId, String name, char reState) {
		WttsEntity en = new WttsEntity();
		en.setId(id);
		en.setpId(pId);
		en.setName(name);
		en.setReState(reState);
		en.setOpen("true");
		WttsEntity reEn = wttsServiceimpl.save(en);
		return reEn;
	}

	/**
	 * 保存规则库的详细信息 id_guizeku, zbbh_guizeku,
	 * name_guizeku,re_state_guizeku,csrw_guizeku
	 * 
	 * @param id
	 * @param pId
	 * @param name
	 * @param reState
	 * @return
	 */
	@RequestMapping("/savaGuiZeKuInfo")
	public WttsEntity savaGuiZeKuInfo(Integer id_guizeku, String zbbh_guizeku, String name_guizeku,
			char re_state_guizeku, String csrw_guizeku, String gzj, String gjz, String gzjsm) {
		WttsEntity en = wttsServiceimpl.findById(id_guizeku);
		en.setZbbh(zbbh_guizeku);
		en.setName(name_guizeku);
		en.setReState(re_state_guizeku);
		en.setCsrw(csrw_guizeku);
		en.setGjz(gjz);
		en.setGzj(gzj);
		en.setGzjsm(gzjsm);
		WttsEntity reEn = wttsServiceimpl.save(en);
		return reEn;
	}

	/**
	 * 保存规则库的详细信息 id_guizeku, zbbh_guizeku,
	 * name_guizeku,re_state_guizeku,csrw_guizeku
	 * 
	 * @param id
	 * @param pId
	 * @param name
	 * @param reState
	 * @return
	 */
	@RequestMapping("/savaGuiZeKuInforegong")
	public WttsEntity savaGuiZeKuInfo(Integer id_guizeku, String zbbh_guizeku, String name_guizeku,
			char re_state_guizeku, String csrw_guizeku) {
		WttsEntity en = wttsServiceimpl.findById(id_guizeku);
		en.setZbbh(zbbh_guizeku);
		en.setName(name_guizeku);
		en.setReState(re_state_guizeku);
		en.setCsrw(csrw_guizeku);

		WttsEntity reEn = wttsServiceimpl.save(en);
		return reEn;
	}

	@RequestMapping("/passorrefuse")
	public WttsEntity passOrRefuse(Integer id, char restate) {
		WttsEntity en = wttsServiceimpl.findById(id);
		en.setReState(restate);
		WttsEntity reEn = wttsServiceimpl.save(en);
		return reEn;
	}

	/**
	 * 删除节点，如果节点 包含子节点，先删除子节点
	 * 
	 * @param id
	 */
	@RequestMapping("/delete")
	public void deleteNode(Integer id) {
		List<WttsEntity> ls = wttsServiceimpl.findBypId(id);
		if (ls != null) {
			// 删除父id
			for (WttsEntity ztreeEntity : ls) {
				wttsServiceimpl.deleteById(ztreeEntity.getId());
				;
			}
		}
		// 删除id
		wttsServiceimpl.deleteById(id);
	}

	/**
	 * 删除节点，如果节点 包含子节点，先删除子节点
	 * 
	 * @param id
	 */
	@RequestMapping("/deleteall")
	public void deleteNode(String id) {
		String[] idArray = null;
		if (id.contains(",")) {
			idArray = id.split(",");
			for (String strid : idArray) {
				wttsServiceimpl.deleteById(Integer.valueOf(strid));
			}
		} else {
			wttsServiceimpl.deleteById(Integer.valueOf(id));
		}

	}

	@RequestMapping("/editTreeNodeName")
	public void editTreeNodeName(Integer id, String name) {
		WttsEntity entity = wttsServiceimpl.findById(id);

		entity.setName(name);

		wttsServiceimpl.save(entity);
	}

	@RequestMapping("/getZtreeId")
	public int getZtreeId() {
		return wttsServiceimpl.getZtreeId();

	}

	@RequestMapping("/findByIdWtts")
	public WttsEntity findByIdWtts(@RequestParam Integer id) {
		WttsEntity en = wttsServiceimpl.findById(id);
		return en;

	}

}
