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
public class JasperMNRRepository extends NamedParameterJdbcDaoSupport{
	
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

	public JasperMNRRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<String> getJasperReportList(JasperMainModel request) {
		
		final Map<String, Object> map = new HashMap<>();
		final String reportName = request.getReportName();
		
        try {
            switch (reportName) {

                case "MNR101_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_MNR + "." + DBConstants.PRR_GET_MNR101_DETAIL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_DEPOT_CODE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EOR_STATUS,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EQUIP_I_NO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_COMPLETE_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_COMPLETE_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VENDOR_CODE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_ORG_CODE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_READ_ONLY,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_CURRENCY,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EOR_NO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_POINT_CODE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EQUIP_NO,OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
    					map.put(DBConstants.P_I_DEPOT_CODE,request.getMnrList().get(0).getDepotCode().trim().equals("")?null:request.getMnrList().get(0).getDepotCode());
    					map.put(DBConstants.P_I_EOR_STATUS,request.getMnrList().get(0).getEorStatus().trim().equals("")?null:request.getMnrList().get(0).getEorStatus());
    					map.put(DBConstants.P_I_EQUIP_I_NO,request.getMnrList().get(0).getEquipINo().trim().equals("")?null:request.getMnrList().get(0).getEquipINo());
    					map.put(DBConstants.P_I_FROM,request.getMnrList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getFromDate()));
    					map.put(DBConstants.P_I_TO,request.getMnrList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getToDate()));
    					map.put(DBConstants.P_I_COMPLETE_FROM,request.getMnrList().get(0).getCompleteFrom().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getCompleteFrom()));
    					map.put(DBConstants.P_I_COMPLETE_TO,request.getMnrList().get(0).getCompleteTo().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getCompleteTo()));
    					map.put(DBConstants.P_I_USER_ID,request.getMnrList().get(0).getUserID().trim().equals("")?null:request.getMnrList().get(0).getUserID());
    					map.put(DBConstants.P_I_VENDOR_CODE,request.getMnrList().get(0).getVendorCode().trim().equals("")?null:request.getMnrList().get(0).getVendorCode());
    					map.put(DBConstants.P_I_FSC,request.getMnrList().get(0).getFscCode().trim().equals("")?null:request.getMnrList().get(0).getFscCode());
    					map.put(DBConstants.P_I_ORG_CODE,request.getMnrList().get(0).getOrgCode().trim().equals("")?null:request.getMnrList().get(0).getOrgCode());
    					map.put(DBConstants.P_I_READ_ONLY,request.getMnrList().get(0).getReadOnly().trim().equals("")?null:request.getMnrList().get(0).getReadOnly());
    					map.put(DBConstants.P_I_CURRENCY,request.getMnrList().get(0).getCurrency().trim().equals("")?null:request.getMnrList().get(0).getCurrency());
    					map.put(DBConstants.P_I_EOR_NO,request.getMnrList().get(0).getEorNo().trim().equals("")?null:request.getMnrList().get(0).getEorNo());
    					map.put(DBConstants.P_I_POINT_CODE,request.getMnrList().get(0).getPoint().trim().equals("")?null:request.getMnrList().get(0).getPoint());
    					map.put(DBConstants.P_I_EQUIP_NO,request.getMnrList().get(0).getEquipNo().trim().equals("")?null:request.getMnrList().get(0).getEquipNo());
                  break;
                  
                case "MNR102_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_MNR + "." + DBConstants.PRR_GET_MNR102_DETAIL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_FSC_CODE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_ORG_CODE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EQUIP_NO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EOR_NO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_SIZE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TYPE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_STATUS,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_CREATE_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_CREATE_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_APPROVE_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_APPROVE_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_COMPLETE_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_COMPLETE_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_GATEIN_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_GATEIN_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_INSTR_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_INSTR_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DEPOT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_READ_ONLY,OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_FSC_CODE,request.getMnrList().get(0).getFscCode().trim().equals("")?null:request.getMnrList().get(0).getFscCode());
                	map.put(DBConstants.P_I_ORG_CODE,request.getMnrList().get(0).getOrgCode().trim().equals("")?null:request.getMnrList().get(0).getOrgCode());
                	map.put(DBConstants.P_I_EQUIP_NO,request.getMnrList().get(0).getEquipNo().trim().equals("")?null:request.getMnrList().get(0).getEquipNo());
                	map.put(DBConstants.P_I_EOR_NO,request.getMnrList().get(0).getEorNo().trim().equals("")?null:request.getMnrList().get(0).getEorNo());
                	map.put(DBConstants.P_I_SIZE,request.getMnrList().get(0).getSize().trim().equals("")?null:request.getMnrList().get(0).getSize());
                	map.put(DBConstants.P_I_TYPE,request.getMnrList().get(0).getType().trim().equals("")?null:request.getMnrList().get(0).getType());
                	map.put(DBConstants.P_I_STATUS,request.getMnrList().get(0).getStatus().trim().equals("")?null:request.getMnrList().get(0).getStatus());
                	map.put(DBConstants.P_I_CREATE_FROM,request.getMnrList().get(0).getCreateFrom().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getCreateFrom()));
                	map.put(DBConstants.P_I_CREATE_TO,request.getMnrList().get(0).getCreateTo().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getCreateTo()));
                	map.put(DBConstants.P_I_APPROVE_FROM,request.getMnrList().get(0).getApproveFrom().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getApproveFrom()));
                	map.put(DBConstants.P_I_APPROVE_TO,request.getMnrList().get(0).getApproveTo().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getApproveTo()));
                	map.put(DBConstants.P_I_COMPLETE_FROM,request.getMnrList().get(0).getCompleteFrom().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getCompleteFrom()));
                	map.put(DBConstants.P_I_COMPLETE_TO,request.getMnrList().get(0).getCompleteTo().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getCompleteTo()));
                	map.put(DBConstants.P_I_GATEIN_FROM,request.getMnrList().get(0).getGateinFrom().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getGateinFrom()));
                	map.put(DBConstants.P_I_GATEIN_TO,request.getMnrList().get(0).getGateinTo().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getGateinTo()));
                	map.put(DBConstants.P_I_INSTR_FROM,request.getMnrList().get(0).getInstrFrom().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getInstrFrom()));
                	map.put(DBConstants.P_I_INSTR_TO,request.getMnrList().get(0).getInstrTo().trim().equals("")?null:DateHelper.convertDateFormat(request.getMnrList().get(0).getInstrTo()));
                	map.put(DBConstants.P_I_DEPOT,request.getMnrList().get(0).getDepotCode().trim().equals("")?null:request.getMnrList().get(0).getDepotCode());
                	map.put(DBConstants.P_I_USER_ID,request.getMnrList().get(0).getUserID().trim().equals("")?null:request.getMnrList().get(0).getUserID());
                	map.put(DBConstants.P_I_READ_ONLY,request.getMnrList().get(0).getReadOnly().trim().equals("")?null:request.getMnrList().get(0).getReadOnly());
                	
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
