package com.rclgroup.dolphin.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class DefaultRowMapper implements RowMapper<JSONObject>{

	@Override
	public JSONObject mapRow(ResultSet rs, int rowNum) throws SQLException {
		int total_Columns = rs.getMetaData().getColumnCount();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < total_Columns; i++) {
			obj.put(rs.getMetaData().getColumnLabel(i + 1), (rs.getObject(i + 1) == null) ? "" : rs.getObject(i + 1));
		}
		return obj;
	}
}
