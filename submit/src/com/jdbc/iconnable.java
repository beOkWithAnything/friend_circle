package com.jdbc;

import java.sql.Connection;

import com.jdbc.Mysql;

public interface iconnable {
	// �������������򣬲����õ����ݿ������
	Connection conn = Mysql.getConnection();
}
