package com.jdbc;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.javaBean.Comment;
import com.javaBean.Message;
import com.jdbc.iconnable;
import com.mysql.jdbc.Statement;

//����mysql������jar����mysql-connector-java-5.1.8-bin.jar
public class Updata  implements iconnable{

	//�������ж�ȡ����Ȧ��Ϣ(���Ѷ�̬)
	/**		���뵱ǰ��¼���û���id��Ҫ�ó�����Ϣ��:
	 * 1.���û��ɼ������ж�̬��messageList��:���� message��content��picture��location��love��islove��commentList(comid,comuid,comcont)
	 */
	public ArrayList<Message> getMessage(int uid) throws Exception{
		
		//����message��,�õ����е� mid��content��picture��location��love ��ֵ
		String sql = "select id,uid,content,picture,location,love from t_friend_circle_message "
				+ "where id=any(select mid from t_friend_circle_attentionmsg where uid="+uid+") "
						+ "order by create_time desc";
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ResultSet rs = ptmt.executeQuery();
		
		ArrayList<Message> mls=new ArrayList<Message>();		//����message ��list ����������Ϊһ���û�Ҫ����������̬��������
		while(rs.next()){
			/**
			 * 		Ϊʲô��sql1��sql2����while()�
			 * 1.sql1��sql2�õ���rs.getint()��������Ҫ��rs.next()��������п�ָ���쳣;
			 * 2.һ��while����һ����̬��һ����̬�����Լ���islove��comment
			 */
			//����attention�����ش�uid�û��Ƿ��ע�˴�����̬
			String sql2 = "select islove from t_friend_circle_attentionmsg where uid="+uid+" and mid="+rs.getInt("id");
			PreparedStatement ptmt2 = conn.prepareStatement(sql2);
			ResultSet rs2 = ptmt2.executeQuery();
			//����comment���õ� ����mid�����۵�uid��comment
			String sql1 = "select uid,comment from t_friend_circle_comment where mid="+rs.getInt("id");
			PreparedStatement ptmt1 = conn.prepareStatement(sql1);
			ResultSet rs1 = ptmt1.executeQuery();
			
			List<Comment> cls=new ArrayList<Comment>();			//����comment��list  ����������Ϊһ����̬Ӧ���ж������ۡ�������
			while(rs1.next()){
				Comment com = new Comment();
				com.setMid(rs.getInt("id"));
				com.setUid(rs1.getInt("uid"));
				com.setComment(rs1.getString("comment"));
				cls.add(com);
			}
			
			//��content��picture��location��love��comlist��islove���浽һ��message�����У������浽һ��Message�����У����ŵ�<Message>list��
			Message message=new Message();
			message.setMuid(rs.getInt("uid"));
			message.setContent(rs.getString("content"));
			message.setPicture(rs.getString("picture"));
			message.setLocation(rs.getString("location"));
			message.setLove(rs.getString("love"));
			while(rs2.next()){
			message.setIslove(rs2.getInt("islove"));
			}
			message.setComlt(cls);
			mls.add(message);
		}
		
		return mls;
	}
	
	//������̬��ͬʱҪ��֪ͨ����ע�˶�̬�����ߵ��û�
	public void setMessage(int uid,String content,String pictureName,String pictureLocation,String createTime) throws Exception{
		
		String sql = "insert into t_friend_circle_message(uid,content,picture,location,create_time) values(?,?,?,?,?) ";//4��������ʱ��ʱ�ǵñ�
		// ͨ�����ݿ������ �������ݿ⣬ʵ��//��
		// �ҵõ��ղ������Ϣ������id
		PreparedStatement ptmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		ptmt.setInt(1, uid);
		ptmt.setString(2, content);
		ptmt.setString(3, pictureName);
		ptmt.setString(4, pictureLocation);
		ptmt.setString(5, createTime);
		ptmt.execute();
		ResultSet rs = ptmt.getGeneratedKeys();  
		int id=0;//�������ɵ�ID  
		if (rs != null&&rs.next()) {  
		    id=rs.getInt(1);  
		}  
		//Ѱ�ҷ����Ķ�̬�ܱ���Щ�û�����(B�ڷ�����Ȧʱ��Ѱ����Щ�˹�ע��B)
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
		//A��ע��B,B�ڷ�����Ȧʱ���ѱ�����̬����A��ˢ���б���
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









