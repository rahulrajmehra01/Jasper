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
public class JasperIPVRepository extends NamedParameterJdbcDaoSupport{
	
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

	public JasperIPVRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<String> getJasperReportList(JasperMainModel request) {
		
		final Map<String, Object> map = new HashMap<>();
		final String reportName = request.getReportName();
		
        try {
            switch (reportName) {
                  
                case "IPV103_RSE_PDF":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_IPV + "." + DBConstants.PRR_GET_IPV103_PO)
        			.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addIPV103Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateIPV103Parameters(map, request);
					
                break;
                
                case "IPV103_RSE_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_IPV + "." + DBConstants.PRR_GET_IPV103_PO_EXCEL)
        			.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addIPV103Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateIPV103Parameters(map, request);
					
                break;
                
                case "IPV101_RSE_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_IPV + "." + DBConstants.PRR_GET_IPV101_RSE_EXCEL)
        			.withoutProcedureColumnMetaDataAccess();
        			jdbcCall = addIPV101Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateIPV101Parameters(map, request);
                	
                break;
        		
                case "IPV101_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_IPV + "." + DBConstants.PRR_GET_IPV101_RSE)
        			.withoutProcedureColumnMetaDataAccess();
        			jdbcCall = addIPV101Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateIPV101Parameters(map, request);

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
   
	private SimpleJdbcCall addIPV101Parameters(SimpleJdbcCall jdbcCall, String parameterName, RowMapper<Object> rowMapper) {
		return jdbcCall.declareParameters(
				new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_PORT,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_TERMINAL,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_MODULE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_VENDOR,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
				new SqlOutParameter(parameterName,OracleTypes.CURSOR,rowMapper)
		);	
	}

	private void populateIPV101Parameters(Map<String, Object> map, JasperMainModel request) {
		map.put(DBConstants.P_I_FROM_DATE,request.getIpvList().get(0).getIpvFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getIpvList().get(0).getIpvFromDate()));
		map.put(DBConstants.P_I_TO_DATE,request.getIpvList().get(0).getIpvToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getIpvList().get(0).getIpvToDate()));
    	map.put(DBConstants.P_I_SERVICE,request.getIpvList().get(0).getService().trim().equals("")?null:request.getIpvList().get(0).getService());
    	map.put(DBConstants.P_I_VESSEL,request.getIpvList().get(0).getVessel().trim().equals("")?null:request.getIpvList().get(0).getVessel());
    	map.put(DBConstants.P_I_VOYAGE,request.getIpvList().get(0).getVoyage().trim().equals("")?null:request.getIpvList().get(0).getVoyage());
    	map.put(DBConstants.P_I_PORT,request.getIpvList().get(0).getPort().trim().equals("")?null:request.getIpvList().get(0).getPort());
    	map.put(DBConstants.P_I_TERMINAL,request.getIpvList().get(0).getTerminal().trim().equals("")?null:request.getIpvList().get(0).getTerminal());
    	map.put(DBConstants.P_I_MODULE,request.getIpvList().get(0).getModule().trim().equals("")?null:request.getIpvList().get(0).getModule());
    	map.put(DBConstants.P_I_VENDOR,request.getIpvList().get(0).getVendorCode().trim().equals("")?null:request.getIpvList().get(0).getVendorCode());
    	map.put(DBConstants.P_I_FSC,request.getIpvList().get(0).getFsc().trim().equals("")?null:request.getIpvList().get(0).getFsc());
    	map.put(DBConstants.P_I_USER_ID,request.getIpvList().get(0).getUserID().trim().equals("")?null:request.getIpvList().get(0).getUserID());
   
	}
	
	private SimpleJdbcCall addIPV103Parameters(SimpleJdbcCall jdbcCall, String parameterName, RowMapper<Object> rowMapper) {
		return jdbcCall.declareParameters(
			new SqlParameter(DBConstants.P_I_IPV_FROM_DATE,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_IPV_TO_DATE,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_PORT,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_TERMINAL,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_VENDOR_CODE,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_IPV_STATUS,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
			new SqlOutParameter(parameterName,OracleTypes.CURSOR, rowMapper)
		);
	}
	
	private void populateIPV103Parameters(Map<String, Object> map, JasperMainModel request) {
		map.put(DBConstants.P_I_IPV_FROM_DATE,request.getIpvList().get(0).getIpvFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getIpvList().get(0).getIpvFromDate()));
		map.put(DBConstants.P_I_IPV_TO_DATE,request.getIpvList().get(0).getIpvToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getIpvList().get(0).getIpvToDate()));
    	map.put(DBConstants.P_I_SERVICE,request.getIpvList().get(0).getService().trim().equals("")?null:request.getIpvList().get(0).getService());
    	map.put(DBConstants.P_I_VESSEL,request.getIpvList().get(0).getVessel().trim().equals("")?null:request.getIpvList().get(0).getVessel());
    	map.put(DBConstants.P_I_VOYAGE,request.getIpvList().get(0).getVoyage().trim().equals("")?null:request.getIpvList().get(0).getVoyage());
    	map.put(DBConstants.P_I_PORT,request.getIpvList().get(0).getPort().trim().equals("")?null:request.getIpvList().get(0).getPort());
    	map.put(DBConstants.P_I_TERMINAL,request.getIpvList().get(0).getTerminal().trim().equals("")?null:request.getIpvList().get(0).getTerminal());
    	map.put(DBConstants.P_I_FROM_DATE,request.getIpvList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getIpvList().get(0).getFromDate()));
    	map.put(DBConstants.P_I_TO_DATE,request.getIpvList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getIpvList().get(0).getToDate()));
    	map.put(DBConstants.P_I_VENDOR_CODE,request.getIpvList().get(0).getVendorCode().trim().equals("")?null:request.getIpvList().get(0).getVendorCode());
    	map.put(DBConstants.P_I_IPV_STATUS,request.getIpvList().get(0).getIpvStatus().trim().equals("")?null:request.getIpvList().get(0).getIpvStatus());
    	map.put(DBConstants.P_I_FSC,request.getIpvList().get(0).getFsc().trim().equals("")?null:request.getIpvList().get(0).getFsc());
    	map.put(DBConstants.P_I_USER_ID,request.getIpvList().get(0).getUserID().trim().equals("")?null:request.getIpvList().get(0).getUserID());
	}
}