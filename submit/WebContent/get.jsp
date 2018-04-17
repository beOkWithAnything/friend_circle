<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!-- EL表达式使用:先导入jstl.jar和standard.jar包，然后uri自动补全   -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>获取头像</title>
</head>
<body>
<!--	选择图片的表单
	<form action="${pageContext.request.contextPath}/servlet/getPictureServlet">
		<input type="file"></input>
		<input type="submit"></input>
	</form>
-->
	<div>
        <c:if test="${ message==null }">
			<form action="${pageContext.request.contextPath}/servlet/getMessageServlet">
				下拉刷新好友动态:<br>
				<input type="text" name="uid" placeholder="传入正在下拉刷新的用户的id">
				<input type="submit" placeholder="下拉刷新">
			</form>
        </c:if>
<!--
 	<img src="${ li.location }${ li.picture }" > <br>
 	跳转网页显示图片时,图片的src只需要写相对于当前应用的相对路径.注意是当前应用.全路径是带着http协议,ip及端口号还有项目名称的.
	比如:http://192.168.10.12:8080/day20_product/images/a.jpg
	在jsp页面中,也可以写成是src="${pageContext.request.contextPath}/images/${product.purl}";
	在product对象的purl属性中保存着图片的名字,同时在数据库中purl也是保存着图片的名称.
	另外也可以通过在jsp头部设置:
	//String path = request.getContextPath();
	//String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/";
	<base href= "basePath %>">
	之后,在jsp页面中,也可以写成是src="images/${product.purl}";
-->
        <c:if test="${ message!=null }" >
        <c:forEach items="${message.als}" var="li">
            muid    : ${li.muid} <br>
			Content: ${li.content} <br>
			picture: <br><img src="${pageContext.request.contextPath}/${ li.location }/${ li.picture }"> <br>
			Love   : ${li.love}  <br>
			isLove : ${li.isLove} <br>
			comment: <br>
					 <c:forEach items="${li.comlt}" var="comli">
						${comli.uid} : ${comli.comment} <br>
					 </c:forEach>
			<br>
		</c:forEach>
        </c:if>
    </div>
    	<br><br><br>
    <div>
    	<form action="${pageContext.request.contextPath}/servlet/pushMessageServlet" enctype="multipart/form-data" method="post">
			发布朋友圈:<br>
			传入用户id:<input type="text" name="uid" placeholder="传入正在发动态的用户的id"><br>
			写动态的内容:<input type="text" name="content" placeholder="请输入你此时的心情"><br>
			选择要发布的图片:<input type="file" name="picture" placeholder="选择图片" ></input>
		<input type="submit" placeholder="发布"></input>
		</form>
    	
    </div>
    
	
</body>
</html>