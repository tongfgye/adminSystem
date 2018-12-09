package com.thtf.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/edit")
@CrossOrigin
public class CkController {

	@Value("${image.path}")
	private String imagePath;
	@Value("${image.serverpath}")
	private String serverPath;
	@Value("${image.zhongzhuanhtml}")
	private String zhongzhuanhtml;

	// 注意路径格式，一般为项目路径下的一个文件夹里边，项目发布到linux服务器上又得改了
	@Value("${image.path}")
	String imageFilePath = "D:\\Development\\apache-tomcat-7.0.81-windows-x64\\apache-tomcat-7.0.81\\webapps\\adminsystem\\easyui_demo\\easyuidemo\\image\\";

	/**
	 * 进入编辑器页面
	 * 
	 * @return
	 */
	@RequestMapping("/ckeditor")
	public String editor() {
		return "edit";
	}

	/**
	 * 编辑器图片上传实现
	 * 
	 * @param file
	 * @param CKEditorFuncNum
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/ckeditorUpload1")
	// 名字upload是固定的，有兴趣，可以打开浏览器查看元素验证
	public String ckeditorUpload(@RequestParam("upload") MultipartFile file, String CKEditorFuncNum) throws Exception {
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		// 实际处理肯定是要加上一段唯一的字符串（如现在时间），这里简单加 cun
		String newFileName = "cun" + new Date().getTime() + suffixName;
		// 使用架包 common-io实现图片上传
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imageFilePath + newFileName));
		// 实现图片回显，基本上是固定代码，只需改路劲即可
		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\">");
		// adminsystem\easyui_demo\easyuidemo\image
		sb.append("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'"
				+ "http://localhost:8080/adminsystem/easyui_demo/easyuidemo/image/" + newFileName + "','')");
		sb.append("</script>");

		return sb.toString();

	}

	@PostMapping("/ckeditorUpload")
	public void ckeditor(@RequestParam("upload") MultipartFile filePath, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
//		System.out.println(imagePath);
//		String imageUrl = "http://localhost:8080/adminsystem/easyui_demo/easyuidemo/image/" + upload(filePath);
//		String callback = request.getParameter("CKEditorFuncNum");
//		// 获取请求地址，拼接static目录下与index.html同级的getimage页面
//		String backUrl = "http://localhost:8080/adminsystem/easyui_demo/easyuidemo/temp/getimage.html";// request.getHeader("Origin")+
//																										// "/getimage.html";
//		response.sendRedirect(backUrl + "?ImageUrl=" + new String(imageUrl.getBytes("UTF-8"), "ISO-8859-1")
//				+ "&CKEditorFuncNum=" + callback);

		System.out.println(imagePath);
		String imageUrl = serverPath + upload(filePath);// "http://localhost:8080/adminsystem/easyui_demo/easyuidemo/image/"
														// + upload(filePath);
		String callback = request.getParameter("CKEditorFuncNum");
		// 获取请求地址，拼接static目录下与index.html同级的getimage页面
		String backUrl = zhongzhuanhtml;// "http://localhost:8080/adminsystem/easyui_demo/easyuidemo/temp/getimage.html";//
										// request.getHeader("Origin")+
										// "/getimage.html";
		response.sendRedirect(backUrl + "?ImageUrl=" + new String(imageUrl.getBytes("UTF-8"), "ISO-8859-1")
				+ "&CKEditorFuncNum=" + callback);
	}

	/**
	 * 图片上传
	 *
	 */
	public String upload(@RequestParam("upload") MultipartFile file) throws IllegalStateException, IOException {
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		// 实际处理肯定是要加上一段唯一的字符串（如现在时间），这里简单加 cun
		String newFileName = "cun" + new Date().getTime() + suffixName;
		// 使用架包 common-io实现图片上传
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imageFilePath + newFileName));
		return newFileName;
	}

}
