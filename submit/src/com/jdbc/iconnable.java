package com.jdbc;

import java.sql.Connection;

import com.jdbc.Mysql;

public interface iconnable {
	// 先载入驱动程序，并且拿到数据库的连接
	Connection conn = Mysql.getConnection();
}
