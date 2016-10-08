package com.dao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dbcp.util.DbcpPool;

/**
 * 数据库连接Dao 实现类
 * 
 * @author xr
 *
 */
public class DaoImpl implements Dao {

	public void save(String sql) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// 获取数据库连接
			conn = DbcpPool.getConnection();
			st = conn.prepareStatement(sql);
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			DbcpPool.release(conn, st, rs);
		}
	}

	public List<Map<String, Object>> select(String sql) {
		// 结果集存储
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// 获取数据库连接
			conn = DbcpPool.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			ResultSetMetaData rsmd = st.getMetaData();
			// 取得结果集列数
			int columnCount = rsmd.getColumnCount();
			// 构造泛型结果集
			Map<String, Object> data = null;
			// 循环结果集
			while (rs.next()) {
				data = new TreeMap<String, Object>();
				// 每循环一条将列名和列值存入Map
				for (int i = 1; i <= columnCount; i++) {
					data.put(rsmd.getColumnLabel(i), rs.getObject(rsmd.getColumnLabel(i)));
				}
				// 将整条数据的Map存入到List中
				list.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			DbcpPool.release(conn, st, rs);
		}
		return list;
	}
	
	public void delete(String sql){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// 获取数据库连接
			conn = DbcpPool.getConnection();
			st = conn.prepareStatement(sql);
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			DbcpPool.release(conn, st, rs);
		}
	}
}
