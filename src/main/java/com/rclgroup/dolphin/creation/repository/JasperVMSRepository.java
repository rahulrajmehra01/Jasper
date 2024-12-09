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
public class JasperVMSRepository extends NamedParameterJdbcDaoSupport{
	
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

	public JasperVMSRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	protected class VMS101_SOC_COC_PDF_ROW_MAPPER implements RowMapper<Object> 
	{
		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			
			return new JSONObject(DTLUtils.vms101SocCocResultSet(rs)).toString();
		}		
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<String> getJasperReportList(JasperMainModel request) {
		
		final Map<String, Object> map = new HashMap<>();
		final String reportName = request.getReportName();
		
        try {
            switch (reportName) {
              
                case "VMS101_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_VMS + "." + DBConstants.PRR_GET_VMS101_RSE)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DIRECTION,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EXPORT_IMPORT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_POL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_POD,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TERMINAL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_LINE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TRADE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_AGENT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_SESSION_ID,OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
    					map.put(DBConstants.P_I_SERVICE,request.getVmsList().get(0).getService().trim().equals("")?null:request.getVmsList().get(0).getService());
    					map.put(DBConstants.P_I_VESSEL,request.getVmsList().get(0).getVessel().trim().equals("")?null:request.getVmsList().get(0).getVessel());
    					map.put(DBConstants.P_I_VOYAGE,request.getVmsList().get(0).getVoyage().trim().equals("")?null:request.getVmsList().get(0).getVoyage());
    					map.put(DBConstants.P_I_DIRECTION,request.getVmsList().get(0).getDirection().trim().equals("")?null:request.getVmsList().get(0).getDirection());
    					map.put(DBConstants.P_I_EXPORT_IMPORT,request.getVmsList().get(0).getExportImport().trim().equals("")?null:request.getVmsList().get(0).getExportImport());
    					map.put(DBConstants.P_I_POL,request.getVmsList().get(0).getPol().trim().equals("")?null:request.getVmsList().get(0).getPol());
    					map.put(DBConstants.P_I_POD,request.getVmsList().get(0).getPod().trim().equals("")?null:request.getVmsList().get(0).getPod());
    					map.put(DBConstants.P_I_TERMINAL,request.getVmsList().get(0).getTerminal().trim().equals("")?null:request.getVmsList().get(0).getTerminal());
    					map.put(DBConstants.P_I_USER_ID,request.getVmsList().get(0).getUserID().trim().equals("")?null:request.getVmsList().get(0).getUserID());
    					map.put(DBConstants.P_I_LINE,request.getVmsList().get(0).getLine().trim().equals("")?null:request.getVmsList().get(0).getLine());
    					map.put(DBConstants.P_I_TRADE,request.getVmsList().get(0).getTrade().trim().equals("")?null:request.getVmsList().get(0).getTrade());
    					map.put(DBConstants.P_I_AGENT,request.getVmsList().get(0).getAgent().trim().equals("")?null:request.getVmsList().get(0).getAgent());
    					map.put(DBConstants.P_I_FSC,request.getVmsList().get(0).getFsc().trim().equals("")?null:request.getVmsList().get(0).getFsc());
    					map.put(DBConstants.P_I_SESSION_ID,request.getVmsList().get(0).getSessionID().trim().equals("")?null:request.getVmsList().get(0).getSessionID());
    					
                break;
                	
                case "VMS102":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_VMS_V2 + "." + DBConstants.PRR_GET_VMS102_LIST)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL_TERMINAL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PROF_STATUS,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PROFORMA_NO,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_LINE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_TRADE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_AGENT,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_FSC_CODE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getVmsList().get(0).getService().trim().equals("")?null:request.getVmsList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getVmsList().get(0).getVessel().trim().equals("")?null:request.getVmsList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getVmsList().get(0).getVoyage().trim().equals("")?null:request.getVmsList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getVmsList().get(0).getDirection().trim().equals("")?null:request.getVmsList().get(0).getDirection());
                	map.put(DBConstants.P_I_POL,request.getVmsList().get(0).getPol().trim().equals("")?null:request.getVmsList().get(0).getPol());
                	map.put(DBConstants.P_I_POL_TERMINAL,request.getVmsList().get(0).getPolTerminal().trim().equals("")?null:request.getVmsList().get(0).getPolTerminal());
                	map.put(DBConstants.P_I_PROF_STATUS,request.getVmsList().get(0).getProfStatus().trim().equals("")?null:request.getVmsList().get(0).getProfStatus());
                	map.put(DBConstants.P_I_PROFORMA_NO,request.getVmsList().get(0).getProfNo().trim().equals("")?null:request.getVmsList().get(0).getProfNo());
                	map.put(DBConstants.P_I_FSC,request.getVmsList().get(0).getFsc().trim().equals("")?null:request.getVmsList().get(0).getFsc());
                	map.put(DBConstants.P_I_LINE,request.getVmsList().get(0).getLine().trim().equals("")?null:request.getVmsList().get(0).getLine());
                	map.put(DBConstants.P_I_TRADE,request.getVmsList().get(0).getTrade().trim().equals("")?null:request.getVmsList().get(0).getTrade());
                	map.put(DBConstants.P_I_AGENT,request.getVmsList().get(0).getAgent().trim().equals("")?null:request.getVmsList().get(0).getAgent());
                	map.put(DBConstants.P_I_FSC_CODE,request.getVmsList().get(0).getFscCode().trim().equals("")?null:request.getVmsList().get(0).getFscCode());
                	map.put(DBConstants.P_I_USER_ID,request.getVmsList().get(0).getUserID().trim().equals("")?null:request.getVmsList().get(0).getUserID());
                	
            	break;
                	
                case "VMS102_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_VMS_V2 + "." + DBConstants.PRR_GET_VMS102_EXCEL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL_TERMINAL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PROF_STATUS,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PROFORMA_NO,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_LINE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_TRADE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_AGENT,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_FSC_CODE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getVmsList().get(0).getService().trim().equals("")?null:request.getVmsList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getVmsList().get(0).getVessel().trim().equals("")?null:request.getVmsList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getVmsList().get(0).getVoyage().trim().equals("")?null:request.getVmsList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getVmsList().get(0).getDirection().trim().equals("")?null:request.getVmsList().get(0).getDirection());
                	map.put(DBConstants.P_I_POL,request.getVmsList().get(0).getPol().trim().equals("")?null:request.getVmsList().get(0).getPol());
                	map.put(DBConstants.P_I_POL_TERMINAL,request.getVmsList().get(0).getPolTerminal().trim().equals("")?null:request.getVmsList().get(0).getPolTerminal());
                	map.put(DBConstants.P_I_PROF_STATUS,request.getVmsList().get(0).getProfStatus().trim().equals("")?null:request.getVmsList().get(0).getProfStatus());
                	map.put(DBConstants.P_I_PROFORMA_NO,request.getVmsList().get(0).getProfNo().trim().equals("")?null:request.getVmsList().get(0).getProfNo());
                	map.put(DBConstants.P_I_FSC,request.getVmsList().get(0).getFsc().trim().equals("")?null:request.getVmsList().get(0).getFsc());
                	map.put(DBConstants.P_I_LINE,request.getVmsList().get(0).getLine().trim().equals("")?null:request.getVmsList().get(0).getLine());
                	map.put(DBConstants.P_I_TRADE,request.getVmsList().get(0).getTrade().trim().equals("")?null:request.getVmsList().get(0).getTrade());
                	map.put(DBConstants.P_I_AGENT,request.getVmsList().get(0).getAgent().trim().equals("")?null:request.getVmsList().get(0).getAgent());
                	map.put(DBConstants.P_I_FSC_CODE,request.getVmsList().get(0).getFscCode().trim().equals("")?null:request.getVmsList().get(0).getFscCode());
                	map.put(DBConstants.P_I_USER_ID,request.getVmsList().get(0).getUserID().trim().equals("")?null:request.getVmsList().get(0).getUserID());
                	
            	break;
            	
                case "VMS107_PERIOD":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate()) 
                	.withProcedureName(DBConstants.PCR_JASPER_VMS_V2 + "." + DBConstants.PRR_GET_VMS107_PERIOD)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addVMS107PeriodParameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateVMS107PeriodParameters(map, request);
                	
            	break;
                	
                case "VMS107_PERIOD_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate()) 
                	.withProcedureName(DBConstants.PCR_JASPER_VMS_V2 + "." + DBConstants.PRR_GET_VMS107_PERIOD_EXCEL)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addVMS107PeriodParameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateVMS107PeriodParameters(map, request);
                	
            	break;
            	
                case "VMS107_VOYAGE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate()) 
                	.withProcedureName(DBConstants.PCR_JASPER_VMS_V2 + "." + DBConstants.PRR_GET_VMS107_VOYAGE )
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addVMS107VoyageParameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateVMS107VoyageParameters(map, request);
                	
            	break;
                	
                case "VMS107_VOYAGE_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate()) 
                	.withProcedureName(DBConstants.PCR_JASPER_VMS_V2 + "." + DBConstants.PRR_GET_VMS107_VOYAGE_EXCEL)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addVMS107VoyageParameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateVMS107VoyageParameters(map, request);
                	
            	break;
                
            	// SOC Inbound and Outbound
                case "VMS101_SOC":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_VMS_V2 + "." + DBConstants.PRR_GET_VMS101_SOC)
                	.withoutProcedureColumnMetaDataAccess();
  					jdbcCall = addVMS101CocSocParameters(jdbcCall, parameterName, new VMS101_SOC_COC_PDF_ROW_MAPPER());
  					populateVMS101CocSocParameters(map, request);
  					
            	break;
            	
                case "VMS101_SOC_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_VMS_V2 + "." + DBConstants.PRR_GET_VMS101_SOC_EXCEL)
                	.withoutProcedureColumnMetaDataAccess();
      					jdbcCall = addVMS101CocSocParameters(jdbcCall, parameterName, new RootTypeMapper());
      					populateVMS101CocSocParameters(map, request);
    				
            	break;

                case "VMS101_COC":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_VMS_V2 + "." + DBConstants.PRR_GET_VMS101_COC)
                	.withoutProcedureColumnMetaDataAccess();
  					jdbcCall = addVMS101CocSocParameters(jdbcCall, parameterName, new VMS101_SOC_COC_PDF_ROW_MAPPER());
  					populateVMS101CocSocParameters(map, request);
  					
            	break;
            	
                case "VMS101_COC_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_VMS_V2 + "." + DBConstants.PRR_GET_VMS101_COC_EXCEL)
                	.withoutProcedureColumnMetaDataAccess();
      					jdbcCall = addVMS101CocSocParameters(jdbcCall, parameterName, new RootTypeMapper());
      					populateVMS101CocSocParameters(map, request);
    				
            	break;
                  
                case "VMS108_SUMMARY_AGENCY_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			  .withProcedureName(DBConstants.PCR_JASPER_VMS + "." + DBConstants.PRR_GET_VMS108_AGC_SUM_EXCEL)
        			  .withoutProcedureColumnMetaDataAccess();
					jdbcCall = addVMS108SumParameters(jdbcCall, parameterName, new RootTypeMapper());
					populateVMS108SumParameters(map, request);
    					
                break;
                
                case "VMS108_DETAIL_AGENCY_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			  .withProcedureName(DBConstants.PCR_JASPER_VMS + "." + DBConstants.PRR_GET_VMS108_AGC_DTL_EXCEL)
        			  .withoutProcedureColumnMetaDataAccess();
					jdbcCall = addVMS108DtlParameters(jdbcCall, parameterName, new RootTypeMapper());
					populateVMS108DtlParameters(map, request);
    					
                break;

                case "VMS108_SUMMARY_FREIGHT_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			  .withProcedureName(DBConstants.PCR_JASPER_VMS + "." + DBConstants.PRR_GET_VMS108_FRT_SUM_EXCEL)
        			  .withoutProcedureColumnMetaDataAccess();
					jdbcCall = addVMS108SumParameters(jdbcCall, parameterName, new RootTypeMapper());
					populateVMS108SumParameters(map, request);
    					
                break;
                  
                case "VMS108_DETAIL_FREIGHT_EXCEL":	
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			  .withProcedureName(DBConstants.PCR_JASPER_VMS + "." + DBConstants.PRR_GET_VMS108_FRT_DTL_EXCEL)
        			  .withoutProcedureColumnMetaDataAccess();
					jdbcCall = addVMS108DtlParameters(jdbcCall, parameterName, new RootTypeMapper());
					populateVMS108DtlParameters(map, request);
    					
                break;
                  
                case "VMS108_SUMMARY_FREIGHT_PDF":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	  .withProcedureName(DBConstants.PCR_JASPER_VMS + "." + DBConstants.PRR_GET_VMS108_FRT_SUM)
        			  .withoutProcedureColumnMetaDataAccess();
					jdbcCall = addVMS108SumParameters(jdbcCall, parameterName, new RootTypeMapper());
					populateVMS108SumParameters(map, request);
                	
                	break;
                	
                case "VMS108_DETAIL_FREIGHT_PDF":	
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			  .withProcedureName(DBConstants.PCR_JASPER_VMS + "." + DBConstants.PRR_GET_VMS108_FRT_DTL)
        			  .withoutProcedureColumnMetaDataAccess();
					jdbcCall = addVMS108DtlParameters(jdbcCall, parameterName, new RootTypeMapper());
					populateVMS108DtlParameters(map, request);
    					
                  break;
                  
                case "VMS108_SUMMARY_AGENCY_PDF":
                	 jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	   .withProcedureName(DBConstants.PCR_JASPER_VMS + "." + DBConstants.PRR_GET_VMS108_AGC_SUM)
        			   .withoutProcedureColumnMetaDataAccess();
					 jdbcCall = addVMS108SumParameters(jdbcCall, parameterName, new RootTypeMapper());
					 populateVMS108SumParameters(map, request);
                	
                break;
               
                case "VMS108_DETAIL_AGENCY_PDF":	
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			  .withProcedureName(DBConstants.PCR_JASPER_VMS + "." + DBConstants.PRR_GET_VMS108_AGC_DTL)
        			  .withoutProcedureColumnMetaDataAccess();
        			jdbcCall = addVMS108DtlParameters(jdbcCall, parameterName, new RootTypeMapper());
        			populateVMS108DtlParameters(map, request);
    					
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
	
	private SimpleJdbcCall addVMS108DtlParameters(SimpleJdbcCall jdbcCall, String parameterName, RowMapper<Object> rowMapper) {
	    return jdbcCall.declareParameters(
			new SqlParameter(DBConstants.P_I_PROF_NO,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_TRADE,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_LINE,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_AGENT,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
			new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
	        new SqlOutParameter(parameterName, OracleTypes.CURSOR, rowMapper)
	    );
	}
	
	private SimpleJdbcCall addVMS101CocSocParameters(SimpleJdbcCall jdbcCall, String parameterName, RowMapper<Object> rowMapper) {
		return jdbcCall.declareParameters(
				new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_DIRECTION,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_PROFORMA_NO,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_COMM_TYPE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_PORT,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_TERMINAL,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_LINE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_TRADE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_AGENT,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
				new SqlOutParameter(parameterName,OracleTypes.CURSOR,rowMapper)
		);
	}
		
	private SimpleJdbcCall addVMS108SumParameters(SimpleJdbcCall jdbcCall, String parameterName, RowMapper<Object> rowMapper) {
	    return jdbcCall.declareParameters(
    		new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_DIRECTION,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_TERMINAL,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_PROF_STATUS,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_PROF_NO,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_PORT,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_TRADE,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_LINE,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_AGENT,OracleTypes.VARCHAR),
    		new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
	        new SqlOutParameter(parameterName, OracleTypes.CURSOR, rowMapper)
	    );
	}
	
	private SimpleJdbcCall addVMS107PeriodParameters(SimpleJdbcCall jdbcCall, String parameterName, RowMapper<Object> rowMapper) {
		return jdbcCall.declareParameters(
				new SqlParameter(DBConstants.P_I_FROM_YEAR,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_FROM_MONTH,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_TO_YEAR,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_TO_MONTH,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_AGENT,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_MON_REIM,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_PROF_STATUS,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_INVOICE_STATUS,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_INVOICE_NO,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
				new SqlOutParameter(parameterName,OracleTypes.CURSOR, rowMapper)
				);
	
	}

	private SimpleJdbcCall addVMS107VoyageParameters(SimpleJdbcCall jdbcCall, String parameterName, RowMapper<Object> rowMapper) {
		return jdbcCall.declareParameters(
				new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
	    		new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
	    		new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
	    		new SqlParameter(DBConstants.P_I_DIRECTION,OracleTypes.VARCHAR),
	    		new SqlParameter(DBConstants.P_I_AGENT,OracleTypes.VARCHAR),
	    		new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
	    		new SqlParameter(DBConstants.P_I_PROF_STATUS,OracleTypes.VARCHAR),
	    		new SqlParameter(DBConstants.P_I_INVOICE_STATUS,OracleTypes.VARCHAR),
	    		new SqlParameter(DBConstants.P_I_INVOICE_NO,OracleTypes.VARCHAR),
	    		new SqlParameter(DBConstants.P_I_COM_FRT_TAX,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
				new SqlOutParameter(parameterName,OracleTypes.CURSOR, rowMapper)
				);
		
	}
	
	private void populateVMS107VoyageParameters(Map<String, Object> map, JasperMainModel request) {
		map.put(DBConstants.P_I_SERVICE,request.getVmsList().get(0).getService().trim().equals("")?null:request.getVmsList().get(0).getService());
		map.put(DBConstants.P_I_VESSEL,request.getVmsList().get(0).getVessel().trim().equals("")?null:request.getVmsList().get(0).getVessel());
		map.put(DBConstants.P_I_VOYAGE,request.getVmsList().get(0).getVoyage().trim().equals("")?null:request.getVmsList().get(0).getVoyage());
		map.put(DBConstants.P_I_DIRECTION,request.getVmsList().get(0).getDirection().trim().equals("")?null:request.getVmsList().get(0).getDirection());
		map.put(DBConstants.P_I_AGENT,request.getVmsList().get(0).getAgent().trim().equals("")?null:request.getVmsList().get(0).getAgent());
		map.put(DBConstants.P_I_FSC,request.getVmsList().get(0).getFsc().trim().equals("")?null:request.getVmsList().get(0).getFsc());
		map.put(DBConstants.P_I_PROF_STATUS,request.getVmsList().get(0).getProfStatus().trim().equals("")?null:request.getVmsList().get(0).getProfStatus());
		map.put(DBConstants.P_I_INVOICE_STATUS,request.getVmsList().get(0).getInvoiceStatus().trim().equals("")?null:request.getVmsList().get(0).getInvoiceStatus());
		map.put(DBConstants.P_I_INVOICE_NO,request.getVmsList().get(0).getInvoiceNo().trim().equals("")?null:request.getVmsList().get(0).getInvoiceNo());
		map.put(DBConstants.P_I_COM_FRT_TAX,request.getVmsList().get(0).getCommissionFreightTax().trim().equals("")?null:request.getVmsList().get(0).getCommissionFreightTax());
		map.put(DBConstants.P_I_FROM_DATE,request.getVmsList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getVmsList().get(0).getFromDate()));
    	map.put(DBConstants.P_I_TO_DATE,request.getVmsList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getVmsList().get(0).getToDate()));
    	map.put(DBConstants.P_I_USER_ID,request.getVmsList().get(0).getUserID().trim().equals("")?null:request.getVmsList().get(0).getUserID());
		
	}

	private void populateVMS107PeriodParameters(Map<String, Object> map, JasperMainModel request) {
		map.put(DBConstants.P_I_FROM_YEAR,request.getVmsList().get(0).getFromYear().trim().equals("")?null:request.getVmsList().get(0).getFromYear());
		map.put(DBConstants.P_I_FROM_MONTH,request.getVmsList().get(0).getFromMonth().trim().equals("")?null:request.getVmsList().get(0).getFromMonth());
		map.put(DBConstants.P_I_TO_YEAR,request.getVmsList().get(0).getToYear().trim().equals("")?null:request.getVmsList().get(0).getToYear());
		map.put(DBConstants.P_I_TO_MONTH,request.getVmsList().get(0).getToMonth().trim().equals("")?null:request.getVmsList().get(0).getToMonth());
		map.put(DBConstants.P_I_AGENT,request.getVmsList().get(0).getAgent().trim().equals("")?null:request.getVmsList().get(0).getAgent());
		map.put(DBConstants.P_I_FSC,request.getVmsList().get(0).getFsc().trim().equals("")?null:request.getVmsList().get(0).getFsc());
		map.put(DBConstants.P_I_MON_REIM,request.getVmsList().get(0).getMonthlyReimbursement().trim().equals("")?null:request.getVmsList().get(0).getMonthlyReimbursement());
		map.put(DBConstants.P_I_PROF_STATUS,request.getVmsList().get(0).getProfStatus().trim().equals("")?null:request.getVmsList().get(0).getProfStatus());
		map.put(DBConstants.P_I_INVOICE_STATUS,request.getVmsList().get(0).getInvoiceStatus().trim().equals("")?null:request.getVmsList().get(0).getInvoiceStatus());
		map.put(DBConstants.P_I_INVOICE_NO,request.getVmsList().get(0).getInvoiceNo().trim().equals("")?null:request.getVmsList().get(0).getInvoiceNo());
		map.put(DBConstants.P_I_USER_ID,request.getVmsList().get(0).getUserID().trim().equals("")?null:request.getVmsList().get(0).getUserID());
		
	}

	private void populateVMS101CocSocParameters(Map<String, Object> map, JasperMainModel request) {
		map.put(DBConstants.P_I_SERVICE,request.getVmsList().get(0).getService().trim().equals("")?null:request.getVmsList().get(0).getService());
		map.put(DBConstants.P_I_VESSEL,request.getVmsList().get(0).getVessel().trim().equals("")?null:request.getVmsList().get(0).getVessel());
		map.put(DBConstants.P_I_VOYAGE,request.getVmsList().get(0).getVoyage().trim().equals("")?null:request.getVmsList().get(0).getVoyage());
		map.put(DBConstants.P_I_DIRECTION,request.getVmsList().get(0).getDirection().trim().equals("")?null:request.getVmsList().get(0).getDirection());
		map.put(DBConstants.P_I_PROFORMA_NO,request.getVmsList().get(0).getProfNo().trim().equals("")?null:request.getVmsList().get(0).getProfNo());
		map.put(DBConstants.P_I_COMM_TYPE,request.getVmsList().get(0).getCommissionType().trim().equals("")?null:request.getVmsList().get(0).getCommissionType());
		map.put(DBConstants.P_I_PORT,request.getVmsList().get(0).getPort().trim().equals("")?null:request.getVmsList().get(0).getPort());
		map.put(DBConstants.P_I_TERMINAL,request.getVmsList().get(0).getTerminal().trim().equals("")?null:request.getVmsList().get(0).getTerminal());
		map.put(DBConstants.P_I_LINE,request.getVmsList().get(0).getLine().trim().equals("")?null:request.getVmsList().get(0).getLine());
		map.put(DBConstants.P_I_TRADE,request.getVmsList().get(0).getTrade().trim().equals("")?null:request.getVmsList().get(0).getTrade());
		map.put(DBConstants.P_I_AGENT,request.getVmsList().get(0).getAgent().trim().equals("")?null:request.getVmsList().get(0).getAgent());
		map.put(DBConstants.P_I_FSC,request.getVmsList().get(0).getFsc().trim().equals("")?null:request.getVmsList().get(0).getFsc());
		map.put(DBConstants.P_I_USER_ID,request.getVmsList().get(0).getUserID().trim().equals("")?null:request.getVmsList().get(0).getUserID());
	}
	
	private void populateVMS108DtlParameters(Map<String, Object> map, JasperMainModel request) {
    	map.put(DBConstants.P_I_PROF_NO,request.getVmsList().get(0).getProfNo().trim().equals("")?null:request.getVmsList().get(0).getProfNo());
		map.put(DBConstants.P_I_TRADE,request.getVmsList().get(0).getTrade().trim().equals("")?null:request.getVmsList().get(0).getTrade());
		map.put(DBConstants.P_I_LINE,request.getVmsList().get(0).getLine().trim().equals("")?null:request.getVmsList().get(0).getLine());
		map.put(DBConstants.P_I_AGENT,request.getVmsList().get(0).getAgent().trim().equals("")?null:request.getVmsList().get(0).getAgent());
		map.put(DBConstants.P_I_FSC,request.getVmsList().get(0).getFsc().trim().equals("")?null:request.getVmsList().get(0).getFsc());
		map.put(DBConstants.P_I_USER_ID,request.getVmsList().get(0).getUserID().trim().equals("")?null:request.getVmsList().get(0).getUserID());
	}
	
	private void populateVMS108SumParameters(Map<String, Object> map, JasperMainModel request) {
    	map.put(DBConstants.P_I_SERVICE,request.getVmsList().get(0).getService().trim().equals("")?null:request.getVmsList().get(0).getService());
    	map.put(DBConstants.P_I_VESSEL,request.getVmsList().get(0).getVessel().trim().equals("")?null:request.getVmsList().get(0).getVessel());
    	map.put(DBConstants.P_I_VOYAGE,request.getVmsList().get(0).getVoyage().trim().equals("")?null:request.getVmsList().get(0).getVoyage());
    	map.put(DBConstants.P_I_DIRECTION,request.getVmsList().get(0).getDirection().trim().equals("")?null:request.getVmsList().get(0).getDirection());
    	map.put(DBConstants.P_I_TERMINAL,request.getVmsList().get(0).getTerminal().trim().equals("")?null:request.getVmsList().get(0).getTerminal());
    	map.put(DBConstants.P_I_PROF_STATUS,request.getVmsList().get(0).getProfStatus().trim().equals("")?null:request.getVmsList().get(0).getProfStatus());
    	map.put(DBConstants.P_I_PROF_NO,request.getVmsList().get(0).getProfNo().trim().equals("")?null:request.getVmsList().get(0).getProfNo());
    	map.put(DBConstants.P_I_FROM_DATE,request.getVmsList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getVmsList().get(0).getFromDate()));
    	map.put(DBConstants.P_I_TO_DATE,request.getVmsList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getVmsList().get(0).getToDate()));
    	map.put(DBConstants.P_I_FSC,request.getVmsList().get(0).getFsc().trim().equals("")?null:request.getVmsList().get(0).getFsc());
    	map.put(DBConstants.P_I_PORT,request.getVmsList().get(0).getPort().trim().equals("")?null:request.getVmsList().get(0).getPort());
    	map.put(DBConstants.P_I_TRADE,request.getVmsList().get(0).getTrade().trim().equals("")?null:request.getVmsList().get(0).getTrade());
    	map.put(DBConstants.P_I_LINE,request.getVmsList().get(0).getLine().trim().equals("")?null:request.getVmsList().get(0).getLine());
    	map.put(DBConstants.P_I_AGENT,request.getVmsList().get(0).getAgent().trim().equals("")?null:request.getVmsList().get(0).getAgent());
    	map.put(DBConstants.P_I_USER_ID,request.getVmsList().get(0).getUserID().trim().equals("")?null:request.getVmsList().get(0).getUserID());
	}
    
}
