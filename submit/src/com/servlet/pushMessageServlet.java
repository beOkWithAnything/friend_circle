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
			/**    �ڵõ��ϴ��ļ�֮ǰ������Ҫ�жϿͻ���<form>��ǵ�enctype�����Ƿ��ǡ�multipart/form-data"��
			 *	Ҳ����˵���ж�����ͨ�������Ǵ��ļ��ϴ��ı����ļ��ϴ��ı�ֵ���ܰ���ͨ������ֵ����ֱ�ӻ�ȡ��
			 *  �� fileupload ���У� HTTP �����еĸ��ӱ�Ԫ�ض�������һ�� FileItem����;
			 *  FileItem ��������� ServletFileUpload ���е� parseRequest() �������� HTTP ���󣨼�����װ֮��� 
			 *	HttpServletRequest ���󣩣��������������ı������ϴ��ļ�;
			 *  �� ServletFileUpload ����Ĵ�����Ҫ������FileItemFactory��������õ��ϴ��ļ� FileItem���󱣴���������Ӳ�̣��� DiskFileItem ����
			 */
			// ��������ɹ�
			if (ServletFileUpload.isMultipartContent(request)) {
				// ��ô����ļ���Ŀ����
				FileItemFactory factory = new DiskFileItemFactory();
				// ��ˮƽ��API�ļ��ϴ��������ļ������ڷ�����Ӳ��
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("ISO8859_1"); 
				// �����ϴ��ĵ����ļ��Ĵ�С20M
				upload.setSizeMax(20 * 1024 * 1024);
				// �����ϴ������ļ��Ĵ�С40M
				upload.setFileSizeMax(40 * 1024 * 1024);
				// FileItem��������� ServletFileUpload���е� parseRequest()�������� HTTP ����������
				List<FileItem> fileItemList = upload.parseRequest(request);
				// ��������ݲ�Ϊ��
				if (fileItemList != null) {
					String uid = null ;
					String content = null ;
					String nowTime = null ;
					String pictureName = null ;
					// ��������
					for (FileItem fileItem : fileItemList) {
						// getFieldName�����������ر���ǩ��name���Ե�ֵ
						if (fileItem.getFieldName().equals("uid")) {
							uid = fileItem.getString();
						}
						if (fileItem.getFieldName().equals("content")){
							content = fileItem.getString();
							content = new String(content.getBytes("ISO8859_1"),"utf-8");
						}
						// getFieldName�����������ر���ǩ��name���Ե�ֵ
						if (fileItem.getFieldName().equals("picture")) {
							//InputStream  getInputStream()��������ʽ�����ϴ��ļ�����������
							InputStream in = fileItem.getInputStream();
							//��ȡ��ǰʱ����ļ�������
							Date day=new Date();    
							SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
							nowTime = df.format(day);
							pictureName = df.format(day)+".jpg";
							// д��ָ��Ŀ¼��(��!666)ǰ���Ҫ��һ��/ ? why? 
							FileOutputStream fos = new FileOutputStream("/java/submit/WebContent/img/"+pictureName);
							byte[] buffer = new byte[1024];
							int length;
							while ((length = in.read(buffer)) != -1) {
								fos.write(buffer, 0, length);
							}
							in.close();
							fos.close();
							// ���Ӧ�÷������µ���ʱ�ļ�
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
