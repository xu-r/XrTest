package com.dao.util;

import java.util.List;
import java.util.Map;

/**在线修改5
 * 数据库连接Dao
 * 
 * @author xr
 *
 */
public interface Dao {
	/**
	 * 新增
	 * 
	 * @param sql
	 */
	public void save(String sql);

	/**
	 * 查询
	 * 
	 * @param sql
	 * @return list对象
	 */
	public List<Map<String, Object>> select(String sql);
	
	/**删除
	 * 
	 * @param sql
	 */
	public void delete(String sql);

}
