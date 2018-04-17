package com.jdbc;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.javaBean.Comment;
import com.javaBean.CommentList;
import com.javaBean.Message;
import com.jdbc.iconnable;
import com.mysql.jdbc.Statement;

//导入mysql的驱动jar包，mysql-connector-java-5.1.8-bin.jar
public class Updata  implements iconnable{

	//从数据中读取朋友圈消息(好友动态)
	/**		传入当前登录的用户的id，要拿出的信息有:
	 * 1.此用户可见的所有动态的messageList集:包括 message的content、picture、location、love、islove、commentList(comid,comuid,comcont)
	 */
	public ArrayList<Message> getMessage(int uid) throws Exception{
		//访问message表,得到所有的 mid，content，picture，location，love 的值
		String sql = "select id,content,picture,location,love from t_friend_circle_message "
				+ "where id=any(select msgid from t_friend_circle_attentionmsg where uid="+uid+") "
						+ "order by create_time desc";
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ResultSet rs = ptmt.executeQuery();
		//访问comment表，得到 各条mid的评论的uid和comment
		String sql1 = "select uid,comment from t_friend_circle_comment where mid="+rs.getInt("id");
		PreparedStatement ptmt1 = conn.prepareStatement(sql1);
		ResultSet rs1 = ptmt1.executeQuery();
		CommentList comlt = new CommentList();
		while(rs1.next()){
			Comment com = new Comment();
			com.setMid(rs.getInt("id"));
			com.setUid(rs1.getInt("uid"));
			com.setComment(rs1.getString("comment"));
			comlt.getAlc().add(com);
		}
		//访问attention表，返回此uid用户是否关注了此条动态
		String sql2 = "select islove from t_friend_circle_attention where uid="+uid+" and mid="+rs.getInt("id");
		PreparedStatement ptmt2 = conn.prepareStatement(sql2);
		ResultSet rs2 = ptmt2.executeQuery();
		//将message表和comment表和attention表中的数据都保存到一个Message对象中，并放到<Message>list中
		ArrayList<Message> ls=new ArrayList<Message>();
		while(rs.next()){
			Message message=new Message();
			message.setContent(rs.getString("content"));
			message.setPicture(rs.getString("picture"));
			message.setLocation(rs.getString("location"));
			message.setLove(rs.getString("love"));
			message.setIslove(rs2.getInt("islove"));
			message.setComlt(comlt);
			ls.add(message);
		}
		
		return ls;
	}
	
	//发布动态，同时要“通知”关注了动态发布者的用户
	public void setMessage(int uid,String content,String pictureName,String pictureLocation,String createTime) throws Exception{
		
		String sql = "insert into t_friend_circle_message(uid,content,picture,location,create_time) values(?,?,?,?,?) ";//4个量，加时间时记得变
		// 通过数据库的连接 操作数据库，实现//增
		// 且得到刚插入的信息的索引id
		PreparedStatement ptmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		ptmt.setInt(1, uid);
		ptmt.setString(2, content);
		ptmt.setString(3, pictureName);
		ptmt.setString(4, pictureLocation);
		ptmt.setString(5, createTime);
		ptmt.execute();
		ResultSet rs = ptmt.getGeneratedKeys();  
		int id=0;//保存生成的ID  
		if (rs != null&&rs.next()) {  
		    id=rs.getInt(1);  
		}  
		//寻找发布的动态能被哪些用户看见(B在发朋友圈时，寻找哪些人关注了B)
		int i=0;
		int[] fid = new int[50] ;
		String sql2 = "select uid from t_friend_circle_attention where fid=?";
		PreparedStatement ptmt2 = conn.prepareStatement(sql2);
		ptmt2.setInt(1, uid);
		ptmt2.execute();
		ResultSet rs2 = ptmt2.executeQuery();
		fid[i]=0;
		while(rs2.next()){
			fid[i]=rs2.getInt("uid");
			i++;
		}
		//A关注了B,B在发朋友圈时，把本条动态加入A的刷新列表里
		int j=0;
		while(j<i && fid!=null){
		String sql3 = "insert into t_friend_circle_attentionmsg(uid,msgid) values(?,?) ";
		PreparedStatement ptmt3 = conn.prepareStatement(sql3);
		ptmt3.setInt(1, fid[j]);
		ptmt3.setInt(2, id);
		ptmt3.execute();
		j++;
		}
	}
}









