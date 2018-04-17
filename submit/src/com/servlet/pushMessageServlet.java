package com.servlet;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jdbc.Updata;

public class pushMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			/**    在得到上传文件之前，首先要判断客户端<form>标记的enctype属性是否是“multipart/form-data"。
			 *	也可以说是判断是普通表单，还是带文件上传的表单。文件上传的表单值不能按普通表单接收值那样直接获取。
			 *  在 fileupload 包中， HTTP 请求中的复杂表单元素都被看做一个 FileItem对象;
			 *  FileItem 对象必须由 ServletFileUpload 类中的 parseRequest() 方法解析 HTTP 请求（即被包装之后的 
			 *	HttpServletRequest 对象），即分离出具体的文本表单和上传文件;
			 *  而 ServletFileUpload 对象的创建需要依赖于FileItemFactory工厂将获得的上传文件 FileItem对象保存至服务器硬盘，即 DiskFileItem 对象。
			 */
			// 如果解析成功
			if (ServletFileUpload.isMultipartContent(request)) {
				// 获得磁盘文件条目工厂
				FileItemFactory factory = new DiskFileItemFactory();
				// 高水平的API文件上传处理，将文件保存在服务器硬盘
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("ISO8859_1"); 
				// 设置上传的单个文件的大小20M
				upload.setSizeMax(20 * 1024 * 1024);
				// 设置上传的总文件的大小40M
				upload.setFileSizeMax(40 * 1024 * 1024);
				// FileItem对象必须由 ServletFileUpload类中的 parseRequest()方法解析 HTTP 请求来创建
				List<FileItem> fileItemList = upload.parseRequest(request);
				// 如果表单内容不为空
				if (fileItemList != null) {
					String uid = null ;
					String content = null ;
					String nowTime = null ;
					String pictureName = null ;
					// 遍历内容
					for (FileItem fileItem : fileItemList) {
						// getFieldName方法用来返回表单标签的name属性的值
						if (fileItem.getFieldName().equals("uid")) {
							uid = fileItem.getString();
						}
						if (fileItem.getFieldName().equals("content")){
							content = fileItem.getString();
							content = new String(content.getBytes("ISO8859_1"),"utf-8");
						}
						// getFieldName方法用来返回表单标签的name属性的值
						if (fileItem.getFieldName().equals("picture")) {
							//InputStream  getInputStream()以流的形式返回上传文件的主体内容
							InputStream in = fileItem.getInputStream();
							//获取当前时间给文件重命名
							Date day=new Date();    
							SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
							nowTime = df.format(day);
							pictureName = df.format(day)+".jpg";
							// 写入指定目录下(嚯!666)前面非要有一个/ ? why? 
							FileOutputStream fos = new FileOutputStream("/java/submit/WebContent/img/"+pictureName);
							byte[] buffer = new byte[1024];
							int length;
							while ((length = in.read(buffer)) != -1) {
								fos.write(buffer, 0, length);
							}
							in.close();
							fos.close();
							// 清除应用服务器下的临时文件
							fileItem.delete();
						}
					}
					Updata updata = new Updata();
					int i = Integer.parseInt(uid);
					updata.setMessage(i, content, pictureName, "img", nowTime);
				}
				request.getRequestDispatcher("/get.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
