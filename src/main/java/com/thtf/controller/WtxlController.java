package com.thtf.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.thtf.bean.WtxlEntity;
import com.thtf.constant.CodeType;
import com.thtf.constant.Constant;
import com.thtf.service.WtxlServiceImpl;

import jxl.Sheet;
import jxl.Workbook;

@RestController
@RequestMapping("/wtxl")
@CrossOrigin
public class WtxlController {

	@Autowired
	private WtxlServiceImpl wtxlserviceimpl;

	@RequestMapping(value = "/uploadimg1", method = RequestMethod.POST)
	public @ResponseBody CodeType uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
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
			List<WtxlEntity> lists = getAllByExcel(newFile);
			for (WtxlEntity wtxlentity : lists) {

				wtxlentity.setReState('0');
				wtxlentity.setXlbzText(wtxlentity.getXlbz());
//				if (wtxlentity.getWid() == null || wtxlentity.getWid().toString() == "") {
//					wtxlentity.setState('0');
//				} else {
//					wtxlentity.setState('1');
//				}
				wtxlentity.setPv(0);

				try {
					WtxlEntity en = wtxlserviceimpl.save(wtxlentity);
					System.out.println(en);
					successMessage += wtxlentity.getWtbh() + ";";
					successCount++;
				} catch (Exception e) {
					failMessage += wtxlentity.getWtbh() + ";";
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
	public List<WtxlEntity> getAllByExcel(File file) {
		List<WtxlEntity> list = new ArrayList<WtxlEntity>();
		try {
			Workbook rwb = Workbook.getWorkbook(file);// new File(file)
			Sheet rs = rwb.getSheet("Sheet1");// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(clos + " rows:" + rows);
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					String zbbh = rs.getCell(j++, i).getContents();// 装备编号
					String wtbh = rs.getCell(j++, i).getContents();// 问题编号
					String wtnr = rs.getCell(j++, i).getContents();// 问题内容
					String xlbh = rs.getCell(j++, i).getContents();// 修理编号
					String xlbz = rs.getCell(j++, i).getContents();// 修理步骤
					list.add(new WtxlEntity(zbbh, wtbh, wtnr, xlbh, xlbz, df.format(new Date())));
				}
			}
		} catch (Exception e) {
			return null;
		}
		return list;

	}

	@RequestMapping("/findAllWtxl")
	@ResponseBody
	public List<WtxlEntity> wtxlfindAll() {
		return wtxlserviceimpl.findAll();
	}

	/**
	 * 查询正在生效的
	 * 
	 * @return
	 */
	@RequestMapping("/findByStateAndReStateWtxl")
	@ResponseBody
	public List<WtxlEntity> findByStateAndReState(@RequestParam char reState) {
		if ((Constant.RESTATE_APPROVE + "").equals(reState + "")) {
			return wtxlserviceimpl.findByStateAndReState(Constant.STATE_VALID, reState);
		}
		return wtxlserviceimpl.findByReState(reState);
	}

	/**
	 * 查询待审核记录
	 * 
	 * @return
	 */
	@RequestMapping("/findByReStateWtxl")
	@ResponseBody
	public List<WtxlEntity> findByReState(@RequestParam char reState) {
		return wtxlserviceimpl.findByReState(reState);
	}

	/**
	 * 审核通过
	 * 
	 * @return
	 */
	@RequestMapping("/approveWtxl/{ids}")
	@ResponseBody
	public String approveWtts(@PathVariable String ids) {
		if (null != ids || ids.equals("")) {
			String[] idsArr = ids.split(",");
			for (String id : idsArr) {
				WtxlEntity entity = wtxlserviceimpl.findById(Integer.valueOf(id));
				entity.setReState(Constant.RESTATE_APPROVE);
				if ((Constant.STATE_DELETE + "").equals(entity.getState() + "")) {

				} else {
					entity.setState(Constant.STATE_VALID);
				}
				wtxlserviceimpl.save(entity);
			}
		}
		return "success";
	}

	/**
	 * 审核拒绝
	 * 
	 * @return
	 */
	@RequestMapping("/refuseWtxl/{ids}")
	@ResponseBody
	public String refuseWtxl(@PathVariable String ids) {
		if (null != ids || ids.equals("")) {
			String[] idsArr = ids.split(",");
			for (String id : idsArr) {
				WtxlEntity entity = wtxlserviceimpl.findById(Integer.valueOf(id));
				entity.setReState(Constant.RESTATE_REFUSE);
				wtxlserviceimpl.save(entity);
			}
		}
		return "success";
	}

	@RequestMapping("/findById")
	@ResponseBody
	public WtxlEntity wtxlfindById(@RequestParam("id") Integer id) {

		WtxlEntity wtxlEntity = wtxlserviceimpl.findById(id);
		return wtxlEntity;
	}

	@RequestMapping("/wtxlSave")
	@ResponseBody
	public WtxlEntity wtxlSave(@ModelAttribute WtxlEntity wtxlEntity) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null == wtxlEntity) {
			return null;
		}
		wtxlEntity.setReState('0');
//		if (wtxlEntity.getWid() == null || wtxlEntity.getWid().toString() == "") {
//			wtxlEntity.setState('0');
//			wtxlEntity.setPv(0);
//		} else {
//			wtxlEntity.setState('1');
//		}
		wtxlEntity.setCreateTime(df.format(new Date()));
		WtxlEntity newEntity = wtxlserviceimpl.save(wtxlEntity);
		return newEntity;
	}

	@RequestMapping("/findByXlbh/{xlbh}")
	@ResponseBody
	public WtxlEntity findByXlbh(@PathVariable String xlbh) {

		WtxlEntity wtxlEntity = wtxlserviceimpl.findByXlbh(xlbh);
		char reState = wtxlEntity.getReState();
		char state = wtxlEntity.getState();
		if ((Constant.RESTATE_APPROVE + "").equals(reState + "") && (Constant.STATE_VALID + "").equals(state + "")) {
			return wtxlEntity;
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@RequestMapping("/deleteWtxl/{id}")
	@ResponseBody
	public WtxlEntity deleteWtts(@PathVariable Integer id) {
		WtxlEntity entity = wtxlserviceimpl.findById(id);
		entity.setState(Constant.STATE_DELETE);
		entity.setReState(Constant.RESTATE_TODO);
		WtxlEntity newEntity = wtxlserviceimpl.save(entity);

		return newEntity;

	}

	/**
	 * 知识库的树结构
	 * 
	 * @return
	 */
	@RequestMapping("/findTreeList")
	@ResponseBody
	public List<WtxlEntity> findTreeList() {
		List<WtxlEntity> list = wtxlserviceimpl.findAll();
		return list;
	}

	/**
	 * 获取 树的最大id
	 * 
	 * @return
	 */
	@RequestMapping("/getZtreeId")
	public int getZtreeId() {
		return wtxlserviceimpl.getZtreeId();

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
	public WtxlEntity saveZtree(Integer id, Integer pId, String name, char reState) {
		WtxlEntity en = new WtxlEntity();
		en.setId(id);
		en.setpId(pId);
		en.setName(name);
		en.setReState(reState);
		WtxlEntity reEn = wtxlserviceimpl.save(en);
		return reEn;
	}

	@RequestMapping("/editTreeNodeName")
	public void editTreeNodeName(Integer id, String name) {
		WtxlEntity entity = wtxlserviceimpl.findById(id);

		entity.setName(name);

		wtxlserviceimpl.save(entity);
	}

	@RequestMapping("/delete")
	public void deleteNode(Integer id) {
		List<WtxlEntity> ls = wtxlserviceimpl.findBypId(id);
		if (ls != null) {
			// 删除父id
			for (WtxlEntity ztreeEntity : ls) {
				wtxlserviceimpl.deleteById(ztreeEntity.getId());
				;
			}
		}
		// 删除id
		wtxlserviceimpl.deleteById(id);
	}

	@RequestMapping("/findByIdinfo")
	public WtxlEntity findById(@RequestParam Integer id) {
//		System.out.println("111");
		WtxlEntity en = wtxlserviceimpl.findById(id);
		return en;

	}
}
