package com.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaBean.AttentionMessage;
import com.javaBean.Message;
import com.jdbc.Updata;

public class getMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s=request.getParameter("uid");
		 int uid = Integer.parseInt(s);
		Updata updata = new Updata();
		AttentionMessage am = new AttentionMessage();
		try {
			am.setAls(updata.getMessage(uid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ÇëÇó×ª·¢      
    	request.setAttribute("message", am);
    	request.getRequestDispatcher("../get.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
