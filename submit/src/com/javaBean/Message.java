package com.javaBean;

import java.util.List;

public class Message {

	private int uid;//��ʱ��¼���û�id
	private int muid;//��������̬���û���id
	private String content;//������̬������
	private String picture;//������̬��ͼƬ����
	private String location;//��̬ͼƬ��λ��
	private String love;//�����Ա�ʾ��������̬���޵������û���id��֮��Ҫ�ú���ȡ�����еĵ������֣�(��������Ϣ���е������֡�ͷ�񡢸�ǩ)����ǰ����ʾ
	private int isLove;//�����Ա�ʾ��ǰ��¼���û��Ƿ��������̬�����ޣ�֮ǰ����ޣ��´�Ҫ����Ϊ����״̬��ǰ���������ӣ�����love�������ı�ֵ
	private List<Comment> comlt;//��������(�ṹ��)���������
	
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
