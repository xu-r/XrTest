package com.dbcp.util;

import java.util.List;
import java.util.Map;

import com.dao.util.Dao;
import com.dao.util.DaoImpl;

public class JdbcTest {
    
    public static void main(String args[]) {
    	
    	Dao dao = new DaoImpl();
    	List<Map<String,Object>> list = dao.select("select id,user_name,plate_number,money from card");
    	System.out.println("list="+list);
    }
}
