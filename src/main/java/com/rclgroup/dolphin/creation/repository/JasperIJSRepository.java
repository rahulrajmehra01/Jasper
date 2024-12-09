package com.rclgroup.dolphin.creation.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.rclgroup.dolphin.creation.dto.JasperMainModel;
import com.rclgroup.dolphin.exception.ResponseQtn;
import com.rclgroup.dolphin.utils.DBConstants;
import com.rclgroup.dolphin.utils.DTLUtils;
import com.rclgroup.dolphin.utils.DateHelper;

import oracle.jdbc.OracleTypes;

@Repository
public class JasperIJSRepository extends NamedParameterJdbcDaoSupport{
	
	private SimpleJdbcCall jdbcCall = null;
	private JSONObject json = null;
	private Map<String, Object> out = null;
	private MapSqlParameterSource in = null;
	final String parameterName = "P_O_DATA";

	public class RootTypeMapper implements RowMapper<Object> {

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			//return rs.getString("DATA_RESULT");
			return new JSONObject(DTLUtils.mergeResultSetResponse(rs)).toString();
		}
	}

	public JasperIJSRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<String> getJasperReportList(JasperMainModel request) {
		
		final Map<String, Object> map = new HashMap<>();
		final String reportName = request.getReportName();
		
        try {
            switch (reportName) {
                
                case "IJS101_RSE_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_IJS + "." + DBConstants.PRR_GET_IJS101_DETAIL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_JOB_ORDER_NO,OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_FROM_DATE,request.getIjsList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getIjsList().get(0).getFromDate()));
                	map.put(DBConstants.P_I_TO_DATE,request.getIjsList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getIjsList().get(0).getToDate()));
                	map.put(DBConstants.P_I_JOB_ORDER_NO,request.getIjsList().get(0).getSjobOrder().trim().equals("")?null:request.getIjsList().get(0).getSjobOrder());
					
                  break;
                  
                case "IJS103_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_IJS + "." + DBConstants.PRR_GET_IJS103_DETAIL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_FROM_LOCATION,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TO_LOCATION,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_MODE_TRANSPORT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VALID_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_RATE_TY,OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
                    map.put(DBConstants.P_I_FROM_LOCATION,request.getIjsList().get(0).getFromLocation().trim().equals("")?null:request.getIjsList().get(0).getFromLocation());
                    map.put(DBConstants.P_I_TO_LOCATION,request.getIjsList().get(0).getToLocation().trim().equals("")?null:request.getIjsList().get(0).getToLocation());
                    map.put(DBConstants.P_I_MODE_TRANSPORT,request.getIjsList().get(0).getTransportMode().trim().equals("")?null:request.getIjsList().get(0).getTransportMode());
                    map.put(DBConstants.P_I_VALID_DATE,request.getIjsList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getIjsList().get(0).getToDate()));
                    map.put(DBConstants.P_I_RATE_TY,request.getIjsList().get(0).getRateTY().trim().equals("")?null:request.getIjsList().get(0).getRateTY());
					
                  break;
                  
                case "IJS104_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_IJS + "." + DBConstants.PRR_GET_IJS104_DETAIL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_FROM_LOCATION,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TO_LOCATION,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_MODE_TRANSPORT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VALID_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_RATE_TY,OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
        			map.put(DBConstants.P_I_FROM_LOCATION,request.getIjsList().get(0).getFromLocation().trim().equals("")?null:request.getIjsList().get(0).getFromLocation());
                    map.put(DBConstants.P_I_TO_LOCATION,request.getIjsList().get(0).getToLocation().trim().equals("")?null:request.getIjsList().get(0).getToLocation());
                    map.put(DBConstants.P_I_MODE_TRANSPORT,request.getIjsList().get(0).getTransportMode().trim().equals("")?null:request.getIjsList().get(0).getTransportMode());
                    map.put(DBConstants.P_I_VALID_DATE,request.getIjsList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getIjsList().get(0).getToDate()));
                    map.put(DBConstants.P_I_RATE_TY,request.getIjsList().get(0).getRateTY().trim().equals("")?null:request.getIjsList().get(0).getRateTY());
        	
                  break;
                  
                default:
                    throw new IllegalArgumentException("Invalid report name: " + request.getReportName());
            }

            in = new MapSqlParameterSource(map);
            out = jdbcCall.execute(in);
            List<String> result = (List<String>) out.get(parameterName);
            
            json = ResponseQtn.messageJson(result.get(0));
            
            System.out.println("data: " + json);
            
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("JasperReport Exception in Service for ReportName : " + request.getReportName() + " Error "+ e.getMessage());
        }

        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
   
}
