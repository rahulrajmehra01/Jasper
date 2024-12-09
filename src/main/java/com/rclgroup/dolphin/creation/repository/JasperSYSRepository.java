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
public class JasperSYSRepository extends NamedParameterJdbcDaoSupport{
	
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

	public JasperSYSRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<String> getJasperReportList(JasperMainModel request) {
		
		final Map<String, Object> map = new HashMap<>();
		final String reportName = request.getReportName();
		
        try {
            switch (reportName) {

                case "SYS105_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_SYS + "." + DBConstants.PRR_GET_SYS105_DETAIL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_REPORT_MOD,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_MODULE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PERIOD_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PERIOD_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_ETD_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_ETD_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_CR_PERIOD_FR,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_CR_PERIOD_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_LOCATION,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PORT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_POINT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PROF_STATUS,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_LOGIN_ID,OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
    					map.put(DBConstants.P_I_REPORT_MOD,request.getSysList().get(0).getReportMod().trim().equals("")?null:request.getSysList().get(0).getReportMod());
    					map.put(DBConstants.P_I_MODULE,request.getSysList().get(0).getModule().trim().equals("")?null:request.getSysList().get(0).getModule());
    					map.put(DBConstants.P_I_PERIOD_FROM,request.getSysList().get(0).getPeriodFrom().trim().equals("")?null:DateHelper.convertDateFormat(request.getSysList().get(0).getPeriodFrom()));
    					map.put(DBConstants.P_I_PERIOD_TO,request.getSysList().get(0).getPeriodTo().trim().equals("")?null:DateHelper.convertDateFormat(request.getSysList().get(0).getPeriodTo()));
    					map.put(DBConstants.P_I_ETD_FROM,request.getSysList().get(0).getEtdFrom().trim().equals("")?null:DateHelper.convertDateFormat(request.getSysList().get(0).getEtdFrom()));
    					map.put(DBConstants.P_I_ETD_TO,request.getSysList().get(0).getEtdTo().trim().equals("")?null:DateHelper.convertDateFormat(request.getSysList().get(0).getEtdTo()));
    					map.put(DBConstants.P_I_VESSEL,request.getSysList().get(0).getVessel().trim().equals("")?null:request.getSysList().get(0).getVessel());
    					map.put(DBConstants.P_I_VOYAGE,request.getSysList().get(0).getVoyage().trim().equals("")?null:request.getSysList().get(0).getVoyage());
    					map.put(DBConstants.P_I_CR_PERIOD_FR,request.getSysList().get(0).getProformaCreatedFrom().trim().equals("")?null:DateHelper.convertDateFormat(request.getSysList().get(0).getProformaCreatedFrom()));
    					map.put(DBConstants.P_I_CR_PERIOD_TO,request.getSysList().get(0).getProformaCreatedTo().trim().equals("")?null:DateHelper.convertDateFormat(request.getSysList().get(0).getProformaCreatedTo()));
    					map.put(DBConstants.P_I_LOCATION,request.getSysList().get(0).getLocation().trim().equals("")?null:request.getSysList().get(0).getLocation());
    					map.put(DBConstants.P_I_PORT,request.getSysList().get(0).getPort().trim().equals("")?null:request.getSysList().get(0).getPort());
    					map.put(DBConstants.P_I_POINT,request.getSysList().get(0).getPoint().trim().equals("")?null:request.getSysList().get(0).getPoint());
    					map.put(DBConstants.P_I_PROF_STATUS,request.getSysList().get(0).getProfStatus().trim().equals("")?null:request.getSysList().get(0).getProfStatus());
    					map.put(DBConstants.P_I_USER_LOGIN_ID,request.getSysList().get(0).getUserID().trim().equals("")?null:request.getSysList().get(0).getUserID());
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
