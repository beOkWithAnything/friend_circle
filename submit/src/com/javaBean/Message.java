package com.javaBean;

import java.util.List;

public class Message {

	private int uid;//此时登录的用户id
	private int muid;//发此条动态的用户的id
	private String content;//此条动态的内容
	private String picture;//此条动态的图片名字
	private String location;//动态图片的位置
	private String love;//此属性表示给此条动态点赞的所有用户的id，之后要用函数取出其中的单个数字，(可以在信息表中调出名字、头像、个签)传到前端显示
	private int isLove;//此属性表示当前登录的用户是否给此条动态点了赞，之前点过赞，下次要看到为已赞状态，前端设置链接，调用love方法来改变值
	private List<Comment> comlt;//评论属性(结构体)，保存多条
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLove() {
		return love;
	}
	public void setLove(String love) {
		this.love = love;
	}
	public int getIsLove() {
		return isLove;
	}
	public void setIslove(int isLove) {
		this.isLove = isLove;
	}
	public List<Comment> getComlt() {
		return comlt;
	}
	public void setComlt(List<Comment> cls) {
		this.comlt = cls;
	}
	public int getMuid() {
		return muid;
	}
	public void setMuid(int muid) {
		this.muid = muid;
	}
	
}
