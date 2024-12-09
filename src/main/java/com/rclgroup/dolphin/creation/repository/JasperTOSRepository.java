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
public class JasperTOSRepository extends NamedParameterJdbcDaoSupport{
	
	private SimpleJdbcCall jdbcCall = null;
	private JSONObject json = null;
	private Map<String, Object> out = null;
	private MapSqlParameterSource in = null;
	final String parameterName = "P_O_DATA";

	public class RootTypeMapper implements RowMapper<Object> {

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new JSONObject(DTLUtils.mergeResultSetResponse(rs)).toString();
		}
	}
	
	public class TOS118TOSPdfTypeMapper implements RowMapper<Object> {
		
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new JSONObject(DTLUtils.tos118TosPdfResultSet(rs)).toString();
		}
	}
//TOS118TOSPdfTypeMapper
//	public class TOS118SHSPdfTypeMapper implements RowMapper<Object> {
//		
//		@Override
//		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//			return new JSONObject(DTLUtils.tos118SHSPdfResultSet(rs)).toString();
//		}
//	}
//	
	public class TOS118_SHSPdfTypeMapper implements RowMapper<Object> {
		
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new JSONObject(DTLUtils.tos118ShsPdfResultSet(rs)).toString();
		}
	}
	public class TOS120PdfTypeMapper implements RowMapper<Object> {
		
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new JSONObject(DTLUtils.tos120PdfResultSet(rs)).toString();
		}
	}
	
	public class TOS120ExcelTypeMapper implements RowMapper<Object> {
		
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new JSONObject(DTLUtils.tos120ExcelResultSet(rs)).toString();
		}
	}
	
	public class DefaultRootTypeMapper implements RowMapper<Object> {

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("DATA_RESULT");
		}
	}

	public JasperTOSRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<String> getJasperReportList(JasperMainModel request) {
		
		final Map<String, Object> map = new HashMap<>();
		final String reportName = request.getReportName();
		
        try {
            switch (reportName) {
                case "TOS111":
                    jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                        .withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS111)
                        .withoutProcedureColumnMetaDataAccess()
                        .declareParameters(
                            new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POL_TERMINAL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POD, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POD_TERMINAL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_ETA_FROM_DATE, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_ETA_TO_DATE, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_USER_PERM,OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
                            new SqlOutParameter(parameterName, OracleTypes.CURSOR)
                        )
                        .returningResultSet(parameterName, new RootTypeMapper());
                    map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
        			map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                    map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
        			map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                    map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
        			map.put(DBConstants.P_I_POL_TERMINAL,request.getTosList().get(0).getPolTerminal().trim().equals("")?null:request.getTosList().get(0).getPolTerminal());
                    map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
        			map.put(DBConstants.P_I_POD_TERMINAL,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
                    map.put(DBConstants.P_I_ETA_FROM_DATE,request.getTosList().get(0).getEtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdFromDate()));
        			map.put(DBConstants.P_I_ETA_TO_DATE,request.getTosList().get(0).getEtdToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdToDate()));
        			map.put(DBConstants.P_I_USER_PERM,request.getTosList().get(0).getUserPerm().trim().equals("")?null:request.getTosList().get(0).getUserPerm());
        			map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                 break;

                case "TOS112":
                    jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                        .withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS112)
                        .withoutProcedureColumnMetaDataAccess()
                        .declareParameters(
                                new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_POL, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_POL_TERMINAL, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
                                new SqlOutParameter(parameterName, OracleTypes.CURSOR)
                            )
                            .returningResultSet(parameterName, new RootTypeMapper());
                        map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
            			map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                        map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
            			map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                        map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
            			map.put(DBConstants.P_I_POL_TERMINAL,request.getTosList().get(0).getPolTerminal().trim().equals("")?null:request.getTosList().get(0).getPolTerminal());
            			map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                 break;
                 
                case "TOS110":
                    jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                        .withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS110)
                        .withoutProcedureColumnMetaDataAccess()
                        .declareParameters(
                                new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_POL, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_POL_TERMINAL, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_BL_NO, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_BOOKING_NO, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_ETA_FROM_DATE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_ETA_TO_DATE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_SOC_COC, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_FILTER_BY, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_USER_PERM,OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
                                new SqlOutParameter(parameterName, OracleTypes.CURSOR)
                            )
                            .returningResultSet(parameterName, new RootTypeMapper());
                        map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
            			map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                        map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
            			map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                        map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
            			map.put(DBConstants.P_I_POL_TERMINAL,request.getTosList().get(0).getPolTerminal().trim().equals("")?null:request.getTosList().get(0).getPolTerminal());
                        map.put(DBConstants.P_I_BL_NO,request.getTosList().get(0).getBlNo().trim().equals("")?null:request.getTosList().get(0).getBlNo());
            			map.put(DBConstants.P_I_BOOKING_NO,request.getTosList().get(0).getBookingNo().trim().equals("")?null:request.getTosList().get(0).getBookingNo());
                        map.put(DBConstants.P_I_ETA_FROM_DATE,request.getTosList().get(0).getEtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdFromDate()));
            			map.put(DBConstants.P_I_ETA_TO_DATE,request.getTosList().get(0).getEtdToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdToDate()));
                        map.put(DBConstants.P_I_SOC_COC,request.getTosList().get(0).getSocCoc().trim().equals("")?null:request.getTosList().get(0).getSocCoc());
            			map.put(DBConstants.P_I_FILTER_BY,request.getTosList().get(0).getFilterBy().trim().equals("")?null:request.getTosList().get(0).getFilterBy());
            			map.put(DBConstants.P_I_USER_PERM,request.getTosList().get(0).getUserPerm().trim().equals("")?null:request.getTosList().get(0).getUserPerm());
            			map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                 break;

                case "TOS116"://incomplete
                    jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                        .withProcedureName(DBConstants.PCR_JASPER_TOS_T2 + "." + DBConstants.PRR_GET_TOS116_DETAIL)
                        .withoutProcedureColumnMetaDataAccess()
                        .declareParameters(
                        		new SqlParameter(DBConstants.P_I_SESSION_ID, OracleTypes.VARCHAR),
                        		new SqlParameter(DBConstants.P_I_USER_NAME, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_PORT_TERMINAL, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_FROM_DATE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_TO_DATE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_LIST_STATUS_BY, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_USR_PERM, OracleTypes.VARCHAR),
                                new SqlOutParameter(parameterName, OracleTypes.CURSOR)
                            )
                            .returningResultSet(parameterName, new RootTypeMapper());
                    map.put(DBConstants.P_I_SESSION_ID,request.getTosList().get(0).getSessionID().trim().equals("")?null:request.getTosList().get(0).getSessionID());
                    map.put(DBConstants.P_I_USER_NAME,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
        			map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                    map.put(DBConstants.P_I_PORT_TERMINAL,request.getTosList().get(0).getPortTerminal().trim().equals("")?null:request.getTosList().get(0).getPortTerminal());
        			map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:request.getTosList().get(0).getFromDate());
                    map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:request.getTosList().get(0).getToDate());
                    map.put(DBConstants.P_I_LIST_STATUS_BY,request.getTosList().get(0).getListStatusBy().trim().equals("")?null:request.getTosList().get(0).getListStatusBy());
                    map.put(DBConstants.P_I_USR_PERM,request.getTosList().get(0).getUserPerm().trim().equals("")?null:request.getTosList().get(0).getUserPerm());
                 break;
                 
                case "TOS116_EXCEL"://Not Completed
                    jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                        .withProcedureName(DBConstants.PCR_JASPER_TOS_T2 + "." + DBConstants.PRR_GET_TOS116_EXCEL_DETAIL)
                        .withoutProcedureColumnMetaDataAccess()
                        .declareParameters(
                        		new SqlParameter(DBConstants.P_I_SESSION_ID, OracleTypes.VARCHAR),
                        		new SqlParameter(DBConstants.P_I_USER_NAME, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_LIST_STATUS_BY, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_PORT_TERMINAL, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_FROM_DATE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_TO_DATE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_USR_PERM, OracleTypes.VARCHAR),
                                new SqlOutParameter(parameterName, OracleTypes.CURSOR)
                            )
                            .returningResultSet(parameterName, new RootTypeMapper());
                    map.put(DBConstants.P_I_SESSION_ID,request.getTosList().get(0).getSessionID().trim().equals("")?null:request.getTosList().get(0).getSessionID());
                    map.put(DBConstants.P_I_USER_NAME,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                    map.put(DBConstants.P_I_LIST_STATUS_BY,request.getTosList().get(0).getListStatusBy().trim().equals("")?null:request.getTosList().get(0).getListStatusBy());
        			map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                    map.put(DBConstants.P_I_PORT_TERMINAL,request.getTosList().get(0).getPortTerminal().trim().equals("")?null:request.getTosList().get(0).getPortTerminal());
        			map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:request.getTosList().get(0).getFromDate());
                    map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:request.getTosList().get(0).getToDate());
                    map.put(DBConstants.P_I_USR_PERM,request.getTosList().get(0).getUserPerm().trim().equals("")?null:request.getTosList().get(0).getUserPerm());
                 break;
                 
                case "TOS122":
                	jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
	                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS122_EXCEL_LIST)
	                	.withoutProcedureColumnMetaDataAccess()
	                	.declareParameters(
	                			new SqlParameter(DBConstants.P_I_INV_FR, OracleTypes.VARCHAR),
	                			new SqlParameter(DBConstants.P_I_INV_TO, OracleTypes.VARCHAR),
	                			new SqlParameter(DBConstants.P_I_INB_OUB, OracleTypes.VARCHAR),
	                			new SqlParameter(DBConstants.P_I_FSC, OracleTypes.VARCHAR),
	                			new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
	                			new SqlParameter(DBConstants.P_I_USER_NAME, OracleTypes.VARCHAR),
	                			new SqlOutParameter(parameterName, OracleTypes.CURSOR)
                			)
                	.returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_INV_FR,request.getTosList().get(0).getInvoiceFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getInvoiceFromDate()));
                	map.put(DBConstants.P_I_INV_TO,request.getTosList().get(0).getInvoiceToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getInvoiceToDate()));
                	map.put(DBConstants.P_I_INB_OUB,request.getTosList().get(0).getInOutbound().trim().equals("")?null:request.getTosList().get(0).getInOutbound());
                	map.put(DBConstants.P_I_FSC,request.getTosList().get(0).getFsc().trim().equals("")?null:request.getTosList().get(0).getFsc());
                	map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                	map.put(DBConstants.P_I_USER_NAME,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	break;

                case "TOS108_SUMMARY":
                	jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS108_SUMMARY)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			 new SqlParameter(DBConstants.P_I_FROM_DATE, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_TO_DATE, OracleTypes.VARCHAR),
                			 new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_POD, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_OPERATOR_CODE, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_CONTAINER_TYPE, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_SELECT_BY, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_POD_TERMINAL, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_POL, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_FULL_EMPTY, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_SOC_COC, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_LOCAL_TRANSHIPMENT, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
                             new SqlOutParameter(parameterName, OracleTypes.CURSOR)
                	      )
                	.returningResultSet(parameterName, new RootTypeMapper());
               	 map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getFromDate()));
        	     map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getToDate()));
               	 map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
        		 map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                 map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
        		 map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                 map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
        		 map.put(DBConstants.P_I_OPERATOR_CODE,request.getTosList().get(0).getOperatorCode().trim().equals("")?null:request.getTosList().get(0).getOperatorCode());
                 map.put(DBConstants.P_I_CONTAINER_TYPE,request.getTosList().get(0).getContainerType().trim().equals("")?null:request.getTosList().get(0).getContainerType());
        	     map.put(DBConstants.P_I_SELECT_BY,request.getTosList().get(0).getSelectBy().trim().equals("")?null:request.getTosList().get(0).getSelectBy());
                 map.put(DBConstants.P_I_POD_TERMINAL,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
        	     map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
        	     map.put(DBConstants.P_I_FULL_EMPTY,request.getTosList().get(0).getFullEmpty().trim().equals("")?null:request.getTosList().get(0).getFullEmpty());
        		 map.put(DBConstants.P_I_SOC_COC,request.getTosList().get(0).getSocCoc().trim().equals("")?null:request.getTosList().get(0).getSocCoc());
        		 map.put(DBConstants.P_I_LOCAL_TRANSHIPMENT,request.getTosList().get(0).getLocalTranshipment().trim().equals("")?null:request.getTosList().get(0).getLocalTranshipment());
        		 map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                  break;
                	
                 
                case "TOS108_DETAIL":
                	jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS108_DETAIL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			 new SqlParameter(DBConstants.P_I_FROM_DATE, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_TO_DATE, OracleTypes.VARCHAR),
                			 new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_POD, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_POD_TERMINAL, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_POL, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_OPERATOR_CODE, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_FULL_EMPTY, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_CONTAINER_TYPE, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_SELECT_BY, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_SOC_COC, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_LOCAL_TRANSHIPMENT, OracleTypes.VARCHAR),
                             new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
                             new SqlOutParameter(parameterName, OracleTypes.CURSOR)
                	      )
                	.returningResultSet(parameterName, new RootTypeMapper());
               	     map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getFromDate()));
        			 map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getToDate()));
               	     map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
        			 map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                     map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
        			 map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                     map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
                     map.put(DBConstants.P_I_POD_TERMINAL,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
                     map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
        			 map.put(DBConstants.P_I_OPERATOR_CODE,request.getTosList().get(0).getOperatorCode().trim().equals("")?null:request.getTosList().get(0).getOperatorCode());
        			 map.put(DBConstants.P_I_FULL_EMPTY,request.getTosList().get(0).getFullEmpty().trim().equals("")?null:request.getTosList().get(0).getFullEmpty());
                     map.put(DBConstants.P_I_CONTAINER_TYPE,request.getTosList().get(0).getContainerType().trim().equals("")?null:request.getTosList().get(0).getContainerType());
        		 	 map.put(DBConstants.P_I_SELECT_BY,request.getTosList().get(0).getSelectBy().trim().equals("")?null:request.getTosList().get(0).getSelectBy());
        			 map.put(DBConstants.P_I_SOC_COC,request.getTosList().get(0).getSocCoc().trim().equals("")?null:request.getTosList().get(0).getSocCoc());
        			 map.put(DBConstants.P_I_LOCAL_TRANSHIPMENT,request.getTosList().get(0).getLocalTranshipment().trim().equals("")?null:request.getTosList().get(0).getLocalTranshipment());
        			 map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                  break;

                case "TOS102":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS102)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_ETD_FROM, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_ETD_TO, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_COUNTRY_CODE, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_POL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
         					new SqlParameter(DBConstants.P_I_DIRECTION,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EMPTY_LADEN, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_SOC_COC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
        	        map.put(DBConstants.P_I_ETD_FROM,request.getTosList().get(0).getEtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdFromDate()));
        	        map.put(DBConstants.P_I_ETD_TO,request.getTosList().get(0).getEtdToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdToDate()));
        	        map.put(DBConstants.P_I_COUNTRY_CODE,request.getTosList().get(0).getCountryCode().trim().equals("")?null:request.getTosList().get(0).getCountryCode());
        	        map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
        	        map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
        	        map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
        	        map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
        	        map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
        	        map.put(DBConstants.P_I_EMPTY_LADEN,request.getTosList().get(0).getEmptyLaden().trim().equals("")?null:request.getTosList().get(0).getEmptyLaden());
        	        map.put(DBConstants.P_I_SOC_COC,request.getTosList().get(0).getSocCoc().trim().equals("")?null:request.getTosList().get(0).getSocCoc());
        	        map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                  break;

                case "TOS124_RSE_PDF":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS_124_RSE)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_PORT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DATE_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DATE_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TERMINAL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC, OracleTypes.VARCHAR),            
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
    					map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
    					map.put(DBConstants.P_I_DATE_FROM,request.getTosList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getFromDate()));
    					map.put(DBConstants.P_I_DATE_TO,request.getTosList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getToDate()));
    					map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
    					map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
    					map.put(DBConstants.P_I_TERMINAL,request.getTosList().get(0).getTerminal().trim().equals("")?null:request.getTosList().get(0).getTerminal());
    					map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getTosList().get(0).getUserLoginFsc().trim().equals("")?null:request.getTosList().get(0).getUserLoginFsc());
                  break;
                  
                case "TOS124_RSE_EXCEL": 
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS_124_EXCEL_RSE)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_PORT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DATE_FROM,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DATE_TO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TERMINAL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC, OracleTypes.VARCHAR),            
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
    					map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
    					map.put(DBConstants.P_I_DATE_FROM,request.getTosList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getFromDate()));
    					map.put(DBConstants.P_I_DATE_TO,request.getTosList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getToDate()));
    					map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
    					map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
    					map.put(DBConstants.P_I_TERMINAL,request.getTosList().get(0).getTerminal().trim().equals("")?null:request.getTosList().get(0).getTerminal());
    					map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getTosList().get(0).getUserLoginFsc().trim().equals("")?null:request.getTosList().get(0).getUserLoginFsc());
                  break;
                  
                case "TOS114":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS114)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETA_FROM,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETA_TO,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD_TERMINAL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL_TERMINAL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                	map.put(DBConstants.P_I_ETA_FROM,request.getTosList().get(0).getEtaFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtaFromDate()));
                	map.put(DBConstants.P_I_ETA_TO,request.getTosList().get(0).getEtaToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtaToDate()));
                	map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
                	map.put(DBConstants.P_I_POD_TERMINAL,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
                	map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
        			map.put(DBConstants.P_I_POL_TERMINAL,request.getTosList().get(0).getPolTerminal().trim().equals("")?null:request.getTosList().get(0).getPolTerminal());
        			map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	
    			break;
                
                case "TOS114_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS114_EXCEL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETA_FROM,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETA_TO,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD_TERMINAL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL_TERMINAL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                	map.put(DBConstants.P_I_ETA_FROM,request.getTosList().get(0).getEtaFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtaFromDate()));
                	map.put(DBConstants.P_I_ETA_TO,request.getTosList().get(0).getEtaToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtaToDate()));
                	map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
                	map.put(DBConstants.P_I_POD_TERMINAL,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
                	map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
        			map.put(DBConstants.P_I_POL_TERMINAL,request.getTosList().get(0).getPolTerminal().trim().equals("")?null:request.getTosList().get(0).getPolTerminal());
        			map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	
                	break;
    			
                case "TOS105":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS105_DETAIL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_SERVICE_CODE, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PORT_CODE, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TERMINAL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PROFORMA_REF_NO, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER, OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
    					map.put(DBConstants.P_I_SERVICE_CODE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
    					map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
    					map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
    					map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
    					map.put(DBConstants.P_I_PORT_CODE,request.getTosList().get(0).getPortCode().trim().equals("")?null:request.getTosList().get(0).getPortCode());
    					map.put(DBConstants.P_I_TERMINAL,request.getTosList().get(0).getTerminal().trim().equals("")?null:request.getTosList().get(0).getTerminal());
    					map.put(DBConstants.P_I_PROFORMA_REF_NO,request.getTosList().get(0).getProformaRefNo().trim().equals("")?null:request.getTosList().get(0).getProformaRefNo());
    					map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getFromDate()));
    					map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getToDate()));
    					map.put(DBConstants.P_I_USER,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
    					
                  break;
                  
                case "TOS105_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS105_EXCEL_DETAIL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_SERVICE_CODE, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PORT_CODE, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TERMINAL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PROFORMA_REF_NO, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER, OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
    					map.put(DBConstants.P_I_SERVICE_CODE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
    					map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
    					map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
    					map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
    					map.put(DBConstants.P_I_PORT_CODE,request.getTosList().get(0).getPortCode().trim().equals("")?null:request.getTosList().get(0).getPortCode());
    					map.put(DBConstants.P_I_TERMINAL,request.getTosList().get(0).getTerminal().trim().equals("")?null:request.getTosList().get(0).getTerminal());
    					map.put(DBConstants.P_I_PROFORMA_REF_NO,request.getTosList().get(0).getProformaRefNo().trim().equals("")?null:request.getTosList().get(0).getProformaRefNo());
    					map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getFromDate()));
    					map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getToDate()));
    					map.put(DBConstants.P_I_USER,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
    					
                  break;
                  
                case "TOS106_DETAIL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS106_DETAIL_LIST)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS106Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateTOS106Parameters(map, request);
                	
                break;
                  
                case "TOS106_DETAIL_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T4 + "." + DBConstants.PRR_GET_TOS106_DETAIL_EXCEL)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS106Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateTOS106Parameters(map, request);
                	
                break;
                
                case "TOS106_SUMMARY":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T4 + "." + DBConstants.PRR_GET_TOS106_SUMMARY_LIST)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS106Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateTOS106Parameters(map, request);
                	
                break;
                  
                case "TOS106_SUMMARY_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T4 + "." + DBConstants.PRR_GET_TOS106_SUMMARY_EXCEL)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS106Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateTOS106Parameters(map, request);
                	
                break;
                
                case "TOS102_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS102_RSE_DETAIL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_TERMINAL,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PORT,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PORT_SEQ, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                	map.put(DBConstants.P_I_TERMINAL,request.getTosList().get(0).getTerminal().trim().equals("")?null:request.getTosList().get(0).getTerminal());
                	map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                	map.put(DBConstants.P_I_PORT_SEQ,request.getTosList().get(0).getPortSeq().trim().equals("")?null:request.getTosList().get(0).getPortSeq());
                	map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getTosList().get(0).getUserLoginFsc().trim().equals("")?null:request.getTosList().get(0).getUserLoginFsc());
                	
                break;
                
                case "TOS115_LOADLIST_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS_115_LOAD_LIST_EXCEL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POL_TERMINAL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POD, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POD_TERMINAL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_ETA, OracleTypes.VARCHAR),                            
                            new SqlParameter(DBConstants.P_I_ETD, OracleTypes.VARCHAR),                            
                            new SqlParameter(DBConstants.P_I_SOC_COC, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_FILTER_BY, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_EX_VESSEL, OracleTypes.VARCHAR),                            
                            new SqlParameter(DBConstants.P_I_EX_VOYAGE, OracleTypes.VARCHAR),                            
                            new SqlParameter(DBConstants.P_I_USR_PERM, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_USER_NAME, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
            		map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
        			map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                    map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
        			map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                    map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
        			map.put(DBConstants.P_I_POL_TERMINAL,request.getTosList().get(0).getPolTerminal().trim().equals("")?null:request.getTosList().get(0).getPolTerminal());
                    map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
        			map.put(DBConstants.P_I_POD_TERMINAL,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
        			map.put(DBConstants.P_I_ETA,request.getTosList().get(0).getEtaFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtaFromDate()));
        			map.put(DBConstants.P_I_ETD,request.getTosList().get(0).getEtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdFromDate()));
        			map.put(DBConstants.P_I_SOC_COC,request.getTosList().get(0).getSocCoc().trim().equals("")?null:request.getTosList().get(0).getSocCoc());
        			map.put(DBConstants.P_I_FILTER_BY,request.getTosList().get(0).getFilterBy().trim().equals("")?null:request.getTosList().get(0).getFilterBy());
        			map.put(DBConstants.P_I_EX_VESSEL,request.getTosList().get(0).getExVessel().trim().equals("")?null:request.getTosList().get(0).getExVessel());
        			map.put(DBConstants.P_I_EX_VOYAGE,request.getTosList().get(0).getExVoyage().trim().equals("")?null:request.getTosList().get(0).getExVoyage());
        			map.put(DBConstants.P_I_USR_PERM,request.getTosList().get(0).getUserPerm().trim().equals("")?null:request.getTosList().get(0).getUserPerm());
        			map.put(DBConstants.P_I_USER_NAME,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                break;
                
                case "TOS115_LOADLIST":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS_115_LOAD_LIST)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POL_TERMINAL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POD, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_POD_TERMINAL, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_ETA, OracleTypes.VARCHAR),                            
                            new SqlParameter(DBConstants.P_I_ETD, OracleTypes.VARCHAR),                            
                            new SqlParameter(DBConstants.P_I_SOC_COC, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_FILTER_BY, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_EX_VESSEL, OracleTypes.VARCHAR),                            
                            new SqlParameter(DBConstants.P_I_EX_VOYAGE, OracleTypes.VARCHAR),                            
                            new SqlParameter(DBConstants.P_I_USR_PERM, OracleTypes.VARCHAR),
                            new SqlParameter(DBConstants.P_I_USER_NAME, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
            		map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
        			map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                    map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
        			map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                    map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
        			map.put(DBConstants.P_I_POL_TERMINAL,request.getTosList().get(0).getPolTerminal().trim().equals("")?null:request.getTosList().get(0).getPolTerminal());
                    map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
        			map.put(DBConstants.P_I_POD_TERMINAL,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
        			map.put(DBConstants.P_I_ETA,request.getTosList().get(0).getEtaFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtaFromDate()));
        			map.put(DBConstants.P_I_ETD,request.getTosList().get(0).getEtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdFromDate()));
        			map.put(DBConstants.P_I_SOC_COC,request.getTosList().get(0).getSocCoc().trim().equals("")?null:request.getTosList().get(0).getSocCoc());
        			map.put(DBConstants.P_I_FILTER_BY,request.getTosList().get(0).getFilterBy().trim().equals("")?null:request.getTosList().get(0).getFilterBy());
        			map.put(DBConstants.P_I_EX_VESSEL,request.getTosList().get(0).getExVessel().trim().equals("")?null:request.getTosList().get(0).getExVessel());
        			map.put(DBConstants.P_I_EX_VOYAGE,request.getTosList().get(0).getExVoyage().trim().equals("")?null:request.getTosList().get(0).getExVoyage());
        			map.put(DBConstants.P_I_USR_PERM,request.getTosList().get(0).getUserPerm().trim().equals("")?null:request.getTosList().get(0).getUserPerm());
        			map.put(DBConstants.P_I_USER_NAME,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                break;
                
                case "TOS131_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T2 + "." + DBConstants.PRR_GET_TOS131_RSE_EXCEL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_TO_DATE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETA_ATA, OracleTypes.VARCHAR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getFromDate()));
                	map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getToDate()));
                	map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                	map.put(DBConstants.P_I_ETA_ATA,request.getTosList().get(0).getEtaAta().trim().equals("")?null:request.getTosList().get(0).getEtaAta());
            	break;
            	
                case "TOS130_RSE_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T2 + "." + DBConstants.PRR_GET_TOS130_RSE_EXCEL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_TO_DATE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETA_ATA, OracleTypes.VARCHAR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getFromDate()));
                	map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getToDate()));
                	map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                	map.put(DBConstants.P_I_ETA_ATA,request.getTosList().get(0).getEtaAta().trim().equals("")?null:request.getTosList().get(0).getEtaAta());
            	break;
            	
                case "TOS118_SHS_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T2 + "." + DBConstants.PRR_GET_TOS118_01_SHS_EXCEL)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS118Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateTOS118Parameters(map, request);	
                	
                break;
                
                case "TOS118_SHS":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T2 + "." + DBConstants.PRR_GET_TOS118_01_SHS_EXCEL)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS118Parameters(jdbcCall, parameterName, new TOS118_SHSPdfTypeMapper());
                	populateTOS118Parameters(map, request);
                	
                break;

                case "TOS118_TOS_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS118_02_EXCEL_DETAIL)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS118Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateTOS118Parameters(map, request);
                	
                break;
                
                case "TOS118_TOS":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS118_02_LIST)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS118Parameters(jdbcCall, parameterName, new TOS118TOSPdfTypeMapper());
                	populateTOS118Parameters(map, request);
                	
                break;
                
                case "TOS117_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T3 + "." + DBConstants.PRR_GET_TOS117_EXCEL_DETAIL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_PERM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD_TERM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_COCSOC, OracleTypes.VARCHAR),                            
                			new SqlParameter(DBConstants.P_I_BL_NO, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_BOOKING_NO, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETD_FROM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETD_TO, OracleTypes.VARCHAR), 
                			new SqlParameter(DBConstants.P_I_USER_NAME, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_FILTER, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                	map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
                	map.put(DBConstants.P_I_USER_PERM,request.getTosList().get(0).getUserPerm().trim().equals("")?null:request.getTosList().get(0).getUserPerm());
                	map.put(DBConstants.P_I_POD_TERM,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
                	map.put(DBConstants.P_I_COCSOC,request.getTosList().get(0).getSocCoc().trim().equals("")?null:request.getTosList().get(0).getSocCoc());
                	map.put(DBConstants.P_I_BL_NO,request.getTosList().get(0).getBlNo().trim().equals("")?null:request.getTosList().get(0).getBlNo());
                	map.put(DBConstants.P_I_BOOKING_NO,request.getTosList().get(0).getBookingNo().trim().equals("")?null:request.getTosList().get(0).getBookingNo());
                	map.put(DBConstants.P_I_ETD_FROM,request.getTosList().get(0).getEtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdFromDate()));
                	map.put(DBConstants.P_I_ETD_TO,request.getTosList().get(0).getEtdToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdToDate()));
                	map.put(DBConstants.P_I_USER_NAME,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	map.put(DBConstants.P_I_FILTER,request.getTosList().get(0).getFilterBy().trim().equals("")?null:request.getTosList().get(0).getFilterBy());
                	
                	break;
                	
                case "TOS117":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS117)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_BL_NO, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_BOOKING_NO, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD_TERMINAL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETD_FROM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETD_TO, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_SOC_COC, OracleTypes.VARCHAR),   
                			new SqlParameter(DBConstants.P_I_FILTER, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_PERM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_NAME, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                	map.put(DBConstants.P_I_BL_NO,request.getTosList().get(0).getBlNo().trim().equals("")?null:request.getTosList().get(0).getBlNo());
                	map.put(DBConstants.P_I_BOOKING_NO,request.getTosList().get(0).getBookingNo().trim().equals("")?null:request.getTosList().get(0).getBookingNo());
                	map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
                	map.put(DBConstants.P_I_POD_TERMINAL,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
                	map.put(DBConstants.P_I_ETD_FROM,request.getTosList().get(0).getEtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdFromDate()));
                	map.put(DBConstants.P_I_ETD_TO,request.getTosList().get(0).getEtdToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdToDate()));
                	map.put(DBConstants.P_I_SOC_COC,request.getTosList().get(0).getSocCoc().trim().equals("")?null:request.getTosList().get(0).getSocCoc());
                	map.put(DBConstants.P_I_FILTER,request.getTosList().get(0).getFilterBy().trim().equals("")?null:request.getTosList().get(0).getFilterBy());
                	map.put(DBConstants.P_I_USER_PERM,request.getTosList().get(0).getUserPerm().trim().equals("")?null:request.getTosList().get(0).getUserPerm());
                	map.put(DBConstants.P_I_USER_NAME,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	
                	break;
                
                case "TOS120":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T2 + "." + DBConstants.PRR_GET_TOS120)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_TERMINAL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_RATE_REF, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_SLOT_PARTNER, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_CUSTOMER, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VENDOR, OracleTypes.VARCHAR),                            
                			new SqlParameter(DBConstants.P_I_PORT_TARIFF, OracleTypes.VARCHAR),                            
                			new SqlParameter(DBConstants.P_I_USER, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new TOS120PdfTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                	map.put(DBConstants.P_I_TERMINAL,request.getTosList().get(0).getTerminal().trim().equals("")?null:request.getTosList().get(0).getTerminal());
                	map.put(DBConstants.P_I_RATE_REF,request.getTosList().get(0).getRateRef().trim().equals("")?null:request.getTosList().get(0).getRateRef());
                	map.put(DBConstants.P_I_SLOT_PARTNER,request.getTosList().get(0).getSlotPartner().trim().equals("")?null:request.getTosList().get(0).getSlotPartner());
                	map.put(DBConstants.P_I_CUSTOMER,request.getTosList().get(0).getCustomer().trim().equals("")?null:request.getTosList().get(0).getCustomer());
                	map.put(DBConstants.P_I_VENDOR,request.getTosList().get(0).getVendor().trim().equals("")?null:request.getTosList().get(0).getVendor());
                	map.put(DBConstants.P_I_PORT_TARIFF,request.getTosList().get(0).getPortTariff().trim().equals("")?null:request.getTosList().get(0).getPortTariff());
                	map.put(DBConstants.P_I_USER,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	
                	break;
                	
                case "TOS120_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T2 + "." + DBConstants.PRR_GET_TOS120_EXCEL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_TERMINAL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_RATE_REF, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_SLOT_PARTNER, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_CUSTOMER, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VENDOR, OracleTypes.VARCHAR),                            
                			new SqlParameter(DBConstants.P_I_PORT_TARIFF, OracleTypes.VARCHAR),                            
                			new SqlParameter(DBConstants.P_I_USER, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new TOS120ExcelTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                	map.put(DBConstants.P_I_TERMINAL,request.getTosList().get(0).getTerminal().trim().equals("")?null:request.getTosList().get(0).getTerminal());
                	map.put(DBConstants.P_I_RATE_REF,request.getTosList().get(0).getRateRef().trim().equals("")?null:request.getTosList().get(0).getRateRef());
                	map.put(DBConstants.P_I_SLOT_PARTNER,request.getTosList().get(0).getSlotPartner().trim().equals("")?null:request.getTosList().get(0).getSlotPartner());
                	map.put(DBConstants.P_I_CUSTOMER,request.getTosList().get(0).getCustomer().trim().equals("")?null:request.getTosList().get(0).getCustomer());
                	map.put(DBConstants.P_I_VENDOR,request.getTosList().get(0).getVendor().trim().equals("")?null:request.getTosList().get(0).getVendor());
                	map.put(DBConstants.P_I_PORT_TARIFF,request.getTosList().get(0).getPortTariff().trim().equals("")?null:request.getTosList().get(0).getPortTariff());
                	map.put(DBConstants.P_I_USER,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	
            	break;
                
                case "TOS121_LOAD":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T2 + "." + DBConstants.PRR_GET_TOS121_LOAD_EXCEL_DETAIL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL_TERM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD_TERM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETD_FROM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETD_TO, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_NAME, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                	map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
                	map.put(DBConstants.P_I_POL_TERM,request.getTosList().get(0).getPolTerminal().trim().equals("")?null:request.getTosList().get(0).getPolTerminal());
                	map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
                	map.put(DBConstants.P_I_POD_TERM,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
                	map.put(DBConstants.P_I_ETD_FROM,request.getTosList().get(0).getEtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdFromDate()));
                	map.put(DBConstants.P_I_ETD_TO,request.getTosList().get(0).getEtdToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdToDate()));
                	map.put(DBConstants.P_I_USER_NAME,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	
                	break;
                	
                case "TOS121_DISCHARGE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T2 + "." + DBConstants.PRR_GET_TOS121_DISCH_EXCEL_DETAIL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POL_TERM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_POD_TERM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETD_FROM, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_ETD_TO, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_NAME, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                	map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
                	map.put(DBConstants.P_I_POL_TERM,request.getTosList().get(0).getPolTerminal().trim().equals("")?null:request.getTosList().get(0).getPolTerminal());
                	map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
                	map.put(DBConstants.P_I_POD_TERM,request.getTosList().get(0).getPodTerminal().trim().equals("")?null:request.getTosList().get(0).getPodTerminal());
                	map.put(DBConstants.P_I_ETD_FROM,request.getTosList().get(0).getEtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdFromDate()));
                	map.put(DBConstants.P_I_ETD_TO,request.getTosList().get(0).getEtdToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdToDate()));
                	map.put(DBConstants.P_I_USER_NAME,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	
                	break;
                	
                case "TOS101":  // PROC not completed
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T1 + "." + DBConstants.PRR_GET_TOS101)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_TERMINAL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_S, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
                	map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                	map.put(DBConstants.P_I_POD_TERMINAL,request.getTosList().get(0).getTerminal().trim().equals("")?null:request.getTosList().get(0).getTerminal());
                	map.put(DBConstants.P_I_S,request.getTosList().get(0).getS().trim().equals("")?null:request.getTosList().get(0).getS());
                	
            	break;
            	
                case "TOS109_ALL_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T4 + "." + DBConstants.PRR_GET_TOS_109_ALL_EXCEL)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS109Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateTOS109Parameters(map, request);
                	
                break;
				
                case "TOS109_ALL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T4 + "." + DBConstants.PRR_GET_TOS_109_ALL)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS109Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateTOS109Parameters(map, request);
                	
                break;

				
                case "TOS109_ERROR":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T4 + "." + DBConstants.PRR_GET_TOS_109_ERROR)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS109Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateTOS109Parameters(map, request);
                	
                break;
                	
                case "TOS109_ERROR_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T4 + "." + DBConstants.PRR_GET_TOS_109_ERROR_EXCEL)
                	.withoutProcedureColumnMetaDataAccess();
                	jdbcCall = addTOS109Parameters(jdbcCall, parameterName, new RootTypeMapper());
                	populateTOS109Parameters(map, request);
                	
                break;

                case "TOS101_RSE_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T4 + "." + DBConstants.PRR_GET_TOS101_RSE_EXCEL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_OPERATOR_TYPE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_STATUS, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                	map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                	map.put(DBConstants.P_I_OPERATOR_TYPE,request.getTosList().get(0).getOperatorType().trim().equals("")?null:request.getTosList().get(0).getOperatorType());
                	map.put(DBConstants.P_I_STATUS,request.getTosList().get(0).getStatus().trim().equals("")?null:request.getTosList().get(0).getStatus());
                	map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	
                	break;
                	
                case "TOS101_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T4 + "." + DBConstants.PRR_GET_TOS101_RSE)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_OPERATOR_TYPE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_STATUS, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
                	map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
                	map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
                	map.put(DBConstants.P_I_OPERATOR_TYPE,request.getTosList().get(0).getOperatorType().trim().equals("")?null:request.getTosList().get(0).getOperatorType());
                	map.put(DBConstants.P_I_STATUS,request.getTosList().get(0).getStatus().trim().equals("")?null:request.getTosList().get(0).getStatus());
                	map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	
                	break;

                case "TOS105_RSE_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
                	.withProcedureName(DBConstants.PCR_JASPER_TOS_T4 + "." + DBConstants.PRR_GET_TOS105_RSE_EXCEL)
                	.withoutProcedureColumnMetaDataAccess()
                	.declareParameters(
                			new SqlParameter(DBConstants.P_I_FROM_DATE, OracleTypes.VARCHAR),
                			new SqlParameter(DBConstants.P_I_TO_DATE, OracleTypes.VARCHAR),
                  			new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
                			new SqlOutParameter(parameterName,OracleTypes.CURSOR)
                			).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:request.getTosList().get(0).getFromDate());
                	map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:request.getTosList().get(0).getToDate());
                	map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
                	
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
	
	private SimpleJdbcCall addTOS118Parameters(SimpleJdbcCall jdbcCall, String parameterName, RowMapper<Object> rowMapper) {
	    return jdbcCall.declareParameters(
	        new SqlParameter(DBConstants.P_I_SERVICE, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_DIRECTION, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_ETD_FROM, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_ETD_TO, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_ATD_FROM, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_ATD_TO, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_PROF_NO, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_VENDOR, OracleTypes.VARCHAR),                            
	        new SqlParameter(DBConstants.P_I_COC_SOC, OracleTypes.VARCHAR),                            
	        new SqlParameter(DBConstants.P_I_IMPORT_EXPORT, OracleTypes.VARCHAR),                            
	        new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_PORT_TERMINAL, OracleTypes.VARCHAR),                      
	        new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC, OracleTypes.VARCHAR),
	        new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
	        new SqlOutParameter(parameterName,OracleTypes.CURSOR,rowMapper)
        );
	}
	
	private SimpleJdbcCall addTOS106Parameters(SimpleJdbcCall jdbcCall, String parameterName, RowMapper<Object> rowMapper) {
	    return jdbcCall.declareParameters(
    			new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_TO_DATE ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_SERVICE ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_VESSEL ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_VOYAGE ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_DIRECTION ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_POL ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_OPERATOR ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_SLOT_OPR ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_CONTAINER_TYPE ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_POL_TERMINAL ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_POD ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_FULL_EMPTY ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_SOC_COC ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_SELECT_BY ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_LOCAL_TRANSHIPMENT ,OracleTypes.VARCHAR),
				new SqlParameter(DBConstants.P_I_USER_ID ,OracleTypes.VARCHAR),
	            new SqlOutParameter(parameterName,OracleTypes.CURSOR,rowMapper)
        );
	}
	
	private SimpleJdbcCall addTOS109Parameters(SimpleJdbcCall jdbcCall, String parameterName, RowMapper<Object> rowMapper) {
	    return jdbcCall.declareParameters(
    			new SqlParameter(DBConstants.P_I_VESSEL, OracleTypes.VARCHAR),
    			new SqlParameter(DBConstants.P_I_VOYAGE, OracleTypes.VARCHAR),
    			new SqlParameter(DBConstants.P_I_POL, OracleTypes.VARCHAR),
    			new SqlParameter(DBConstants.P_I_NEXT_PORT, OracleTypes.VARCHAR),
    			new SqlParameter(DBConstants.P_I_EQ_NO, OracleTypes.VARCHAR),
    			new SqlParameter(DBConstants.P_I_FULL_EMPTY, OracleTypes.VARCHAR),
    			new SqlParameter(DBConstants.P_I_FROM_DATE, OracleTypes.VARCHAR),
    			new SqlParameter(DBConstants.P_I_TO_DATE, OracleTypes.VARCHAR),
    			new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
	            new SqlOutParameter(parameterName,OracleTypes.CURSOR,rowMapper)
        );
	}

	private void populateTOS118Parameters(Map<String, Object> map, JasperMainModel request) {
	    map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
	    map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
	    map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
	    map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
	    map.put(DBConstants.P_I_ETD_FROM,request.getTosList().get(0).getEtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdFromDate()));
	    map.put(DBConstants.P_I_ETD_TO,request.getTosList().get(0).getEtdToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getEtdToDate()));
	    map.put(DBConstants.P_I_ATD_FROM,request.getTosList().get(0).getAtdFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getAtdFromDate()));
	    map.put(DBConstants.P_I_ATD_TO,request.getTosList().get(0).getAtdToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getAtdToDate()));
	    map.put(DBConstants.P_I_PROF_NO,request.getTosList().get(0).getProfNo().trim().equals("")?null:request.getTosList().get(0).getProfNo());
	    map.put(DBConstants.P_I_VENDOR,request.getTosList().get(0).getVendor().trim().equals("")?null:request.getTosList().get(0).getVendor());
	    map.put(DBConstants.P_I_COC_SOC,request.getTosList().get(0).getSocCoc().trim().equals("")?null:request.getTosList().get(0).getSocCoc());
	    map.put(DBConstants.P_I_IMPORT_EXPORT,request.getTosList().get(0).getImpExp().trim().equals("")?null:request.getTosList().get(0).getImpExp());					
	    map.put(DBConstants.P_I_PORT,request.getTosList().get(0).getPort().trim().equals("")?null:request.getTosList().get(0).getPort());
	    map.put(DBConstants.P_I_PORT_TERMINAL,request.getTosList().get(0).getPortTerminal().trim().equals("")?null:request.getTosList().get(0).getPortTerminal());
	    map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getTosList().get(0).getUserLoginFsc().trim().equals("")?null:request.getTosList().get(0).getUserLoginFsc());
	    map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
	}
	
	private void populateTOS106Parameters(Map<String, Object> map, JasperMainModel request) {
    	map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getFromDate()));
    	map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getToDate()));
    	map.put(DBConstants.P_I_SERVICE,request.getTosList().get(0).getService().trim().equals("")?null:request.getTosList().get(0).getService());
    	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
    	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
    	map.put(DBConstants.P_I_DIRECTION,request.getTosList().get(0).getDirection().trim().equals("")?null:request.getTosList().get(0).getDirection());
    	map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
    	map.put(DBConstants.P_I_OPERATOR,request.getTosList().get(0).getOperatorCode().trim().equals("")?null:request.getTosList().get(0).getOperatorCode());
    	map.put(DBConstants.P_I_SLOT_OPR,request.getTosList().get(0).getSlotOprCode().trim().equals("")?null:request.getTosList().get(0).getSlotOprCode());
    	map.put(DBConstants.P_I_CONTAINER_TYPE,request.getTosList().get(0).getContainerType().trim().equals("")?null:request.getTosList().get(0).getContainerType());
    	map.put(DBConstants.P_I_POL_TERMINAL,request.getTosList().get(0).getPolTerminal().trim().equals("")?null:request.getTosList().get(0).getPolTerminal());
    	map.put(DBConstants.P_I_POD,request.getTosList().get(0).getPod().trim().equals("")?null:request.getTosList().get(0).getPod());
    	map.put(DBConstants.P_I_FULL_EMPTY,request.getTosList().get(0).getFullEmpty().trim().equals("")?null:request.getTosList().get(0).getFullEmpty());
    	map.put(DBConstants.P_I_SOC_COC,request.getTosList().get(0).getSocCoc().trim().equals("")?null:request.getTosList().get(0).getSocCoc());
    	map.put(DBConstants.P_I_SELECT_BY,request.getTosList().get(0).getSelectBy().trim().equals("")?null:request.getTosList().get(0).getSelectBy());
    	map.put(DBConstants.P_I_LOCAL_TRANSHIPMENT,request.getTosList().get(0).getLocalTranshipment().trim().equals("")?null:request.getTosList().get(0).getLocalTranshipment());
    	map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
	}
	
	private void populateTOS109Parameters(Map<String, Object> map, JasperMainModel request) {
    	map.put(DBConstants.P_I_VESSEL,request.getTosList().get(0).getVessel().trim().equals("")?null:request.getTosList().get(0).getVessel());
    	map.put(DBConstants.P_I_VOYAGE,request.getTosList().get(0).getVoyage().trim().equals("")?null:request.getTosList().get(0).getVoyage());
    	map.put(DBConstants.P_I_POL,request.getTosList().get(0).getPol().trim().equals("")?null:request.getTosList().get(0).getPol());
    	map.put(DBConstants.P_I_NEXT_PORT,request.getTosList().get(0).getNextPort().trim().equals("")?null:request.getTosList().get(0).getNextPort());
    	map.put(DBConstants.P_I_EQ_NO,request.getTosList().get(0).getEquipmentNo().trim().equals("")?null:request.getTosList().get(0).getEquipmentNo());
    	map.put(DBConstants.P_I_FULL_EMPTY,request.getTosList().get(0).getFullEmpty().trim().equals("")?null:request.getTosList().get(0).getFullEmpty());
    	map.put(DBConstants.P_I_FROM_DATE,request.getTosList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getFromDate()));
		map.put(DBConstants.P_I_TO_DATE,request.getTosList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getTosList().get(0).getToDate()));
		map.put(DBConstants.P_I_USER_ID,request.getTosList().get(0).getUserID().trim().equals("")?null:request.getTosList().get(0).getUserID());
	}
}
