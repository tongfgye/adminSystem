/*package com.thtf.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/edit")
@CrossOrigin
调试分模块部署。
public class CkeditorController2 {

	// 注意路径格式，一般为项目路径下的一个文件夹里边，项目发布到linux服务器上又得改了
//	String imageFilePath = "./src/main/webapp/static/myImage/";

	*//**
	 * 进入编辑器页面
	 * 
	 * @return
	 *//*
	@RequestMapping("/ckeditor")
	public String editor() {
		return "edit";
	}

	*//**
	 * 编辑器图片上传实现
	 * 
	 * @param file
	 * @param CKEditorFuncNum
	 * @return
	 * @throws Exception
	 *//*
	@ResponseBody
	@RequestMapping("/ckeditorUpload")
	// 名字upload是固定的，有兴趣，可以打开浏览器查看元素验证
	public void ckeditorUpload(@RequestParam("upload") MultipartFile file, String CKEditorFuncNum, 
			HttpServletRequest req ,HttpServletResponse rep) throws Exception {
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		// 实际处理肯定是要加上一段唯一的字符串（如现在时间），这里简单加 cun
		String origin = req.getHeader("Origin");
		String newFileName = "cun" + new Date().getTime() + suffixName;
		String pathname =origin+ "/zjglxt/zjglxt/static/myImage";
		File imgPath = new File(pathname);
		if (!imgPath.exists()) imgPath.mkdir();
		
		// 使用架包 common-io实现图片上传
//		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imgPath.getAbsolutePath() + "/" + newFileName));
		// 实现图片回显，基本上是固定代码，只需改路劲即可
		rep.sendRedirect(origin + "/zjglxt/zjglxt/ck_getimage.html?ImageUrl=" + pathname+"//cun1540878406320.jpg" + "&CKEditorFuncNum=" + CKEditorFuncNum);
		
//		StringBuffer sb = new StringBuffer();
//		sb.append("<script type=\"text/javascript\">");
//		sb.append("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + "http://localhost:8093/static/myImage/"
//				+ newFileName + "','')");
//		sb.append("</script>");
//
//		return sb.toString();

	}

//	@PostMapping("/upload")
//	public void ckeditor(@RequestParam("upload") MultipartFile filePath, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		String imageUrl = upload(filePath);
//		String callback = request.getParameter("CKEditorFuncNum");
//		// 获取请求地址，拼接static目录下与index.html同级的getimage页面
//		String backUrl = request.getHeader("Origin") + "/getimage.html";
//		response.sendRedirect(backUrl + "?ImageUrl=" + new String(imageUrl.getBytes("UTF-8"), "ISO-8859-1")
//				+ "&CKEditorFuncNum=" + callback);
//	}

}
*/