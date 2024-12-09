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
public class JasperSTRRepository extends NamedParameterJdbcDaoSupport{
	
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

	public JasperSTRRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<String> getJasperReportList(JasperMainModel request) {
		
		final Map<String, Object> map = new HashMap<>();
		final String reportName = request.getReportName();
		
        try {
            switch (reportName) {

                case "STR113_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_STR + "." + DBConstants.PRR_GET_STR113_RSE_EXCEL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_DEPOT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_COUNTRY,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_AGREEMENT_NO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_POINT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VENDOR,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EXCL_EXPIRE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
					map.put(DBConstants.P_I_DEPOT,request.getStrList().get(0).getDepot().trim().equals("")?null:request.getStrList().get(0).getDepot());
                	map.put(DBConstants.P_I_COUNTRY,request.getStrList().get(0).getCountry().trim().equals("")?null:request.getStrList().get(0).getCountry());
                	map.put(DBConstants.P_I_AGREEMENT_NO,request.getStrList().get(0).getAgreementNo().trim().equals("")?null:request.getStrList().get(0).getAgreementNo());
                	map.put(DBConstants.P_I_POINT,request.getStrList().get(0).getPoint().trim().equals("")?null:request.getStrList().get(0).getPoint());
                	map.put(DBConstants.P_I_VENDOR,request.getStrList().get(0).getVendor().trim().equals("")?null:request.getStrList().get(0).getVendor());
                	map.put(DBConstants.P_I_EXCL_EXPIRE,request.getStrList().get(0).getExcelExpire().trim().equals("")?null:request.getStrList().get(0).getExcelExpire());
                	map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getStrList().get(0).getUserLoginFSC().trim().equals("")?null:request.getStrList().get(0).getUserLoginFSC());
                	map.put(DBConstants.P_I_USER_ID,request.getStrList().get(0).getUserID().trim().equals("")?null:request.getStrList().get(0).getUserID());
                  break;
                 
                case "STR112_RSE":
                    jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                        .withProcedureName(DBConstants.PCR_JASPER_STR + "." + DBConstants.PRR_GET_STR112_RSE_EXCEL)
                        .withoutProcedureColumnMetaDataAccess()
                        .declareParameters(
                                new SqlParameter(DBConstants.P_I_DEPOT, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_COUNTRY, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_AGREEMENT_NO, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_POINT, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_VENDOR, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_EXCL_EXPIRE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
            					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
            					).returningResultSet(parameterName, new RootTypeMapper());
                    map.put(DBConstants.P_I_DEPOT,request.getStrList().get(0).getDepot().trim().equals("")?null:request.getStrList().get(0).getDepot());
        			map.put(DBConstants.P_I_COUNTRY,request.getStrList().get(0).getCountry().trim().equals("")?null:request.getStrList().get(0).getCountry());
                    map.put(DBConstants.P_I_AGREEMENT_NO,request.getStrList().get(0).getAgreementNo().trim().equals("")?null:request.getStrList().get(0).getAgreementNo());
        			map.put(DBConstants.P_I_POINT,request.getStrList().get(0).getPoint().trim().equals("")?null:request.getStrList().get(0).getPoint());
                    map.put(DBConstants.P_I_VENDOR,request.getStrList().get(0).getVendor().trim().equals("")?null:request.getStrList().get(0).getVendor());
        			map.put(DBConstants.P_I_EXCL_EXPIRE,request.getStrList().get(0).getExcelExpire().trim().equals("")?null:request.getStrList().get(0).getExcelExpire());
        			map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getStrList().get(0).getUserLoginFSC().trim().equals("")?null:request.getStrList().get(0).getUserLoginFSC());
        			map.put(DBConstants.P_I_USER_ID,request.getStrList().get(0).getUserID().trim().equals("")?null:request.getStrList().get(0).getUserID());
                 break;
                 
                case "STR115_RSE_EXCEL"://with DEVBKK01 User Data from FSC ....DEV_TEAM run successfully
                    jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                        .withProcedureName(DBConstants.PCR_JASPER_STR + "." + DBConstants.PRR_GET_STR115_RSE_EXCEL)
                        .withoutProcedureColumnMetaDataAccess()
                        .declareParameters(
                                new SqlParameter(DBConstants.P_I_DEPOT, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_COUNTRY, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_AGREEMENT_NO, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_PORT, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_POINT, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_VENDOR, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_EXCL_EXPIRE, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC, OracleTypes.VARCHAR),
                                new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
            					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
            					).returningResultSet(parameterName, new RootTypeMapper());
                    map.put(DBConstants.P_I_DEPOT,request.getStrList().get(0).getDepot().trim().equals("")?null:request.getStrList().get(0).getDepot());
        			map.put(DBConstants.P_I_COUNTRY,request.getStrList().get(0).getCountry().trim().equals("")?null:request.getStrList().get(0).getCountry());
                    map.put(DBConstants.P_I_AGREEMENT_NO,request.getStrList().get(0).getAgreementNo().trim().equals("")?null:request.getStrList().get(0).getAgreementNo());
        			map.put(DBConstants.P_I_PORT,request.getStrList().get(0).getPort().trim().equals("")?null:request.getStrList().get(0).getPort());
        			map.put(DBConstants.P_I_POINT,request.getStrList().get(0).getPoint().trim().equals("")?null:request.getStrList().get(0).getPoint());
                    map.put(DBConstants.P_I_VENDOR,request.getStrList().get(0).getVendor().trim().equals("")?null:request.getStrList().get(0).getVendor());
        			map.put(DBConstants.P_I_EXCL_EXPIRE,request.getStrList().get(0).getExcelExpire().trim().equals("")?null:request.getStrList().get(0).getExcelExpire());
        			map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getStrList().get(0).getUserLoginFSC().trim().equals("")?null:request.getStrList().get(0).getUserLoginFSC());
        			map.put(DBConstants.P_I_USER_ID,request.getStrList().get(0).getUserID().trim().equals("")?null:request.getStrList().get(0).getUserID());
        			
                 break;
                 
                case "STR118_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_STR + "." + DBConstants.PRR_GET_STR118_RSE_EXCEL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DEPOT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_AGREEMENT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EMPTY_LADEN,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_FROM_DATE,request.getStrList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getStrList().get(0).getFromDate()));
                	map.put(DBConstants.P_I_TO_DATE,request.getStrList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getStrList().get(0).getToDate()));
                	map.put(DBConstants.P_I_DEPOT,request.getStrList().get(0).getDepot().trim().equals("")?null:request.getStrList().get(0).getDepot());
                	map.put(DBConstants.P_I_AGREEMENT,request.getStrList().get(0).getAgreementNo().trim().equals("")?null:request.getStrList().get(0).getAgreementNo());
                	map.put(DBConstants.P_I_EMPTY_LADEN,request.getStrList().get(0).getEmptyLaden().trim().equals("")?null:request.getStrList().get(0).getEmptyLaden());
                	map.put(DBConstants.P_I_FSC,request.getStrList().get(0).getFsc().trim().equals("")?null:request.getStrList().get(0).getFsc());
                	map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getStrList().get(0).getUserLoginFSC().trim().equals("")?null:request.getStrList().get(0).getUserLoginFSC());
                	map.put(DBConstants.P_I_USER_ID,request.getStrList().get(0).getUserID().trim().equals("")?null:request.getStrList().get(0).getUserID());
                  break;
                  
                case "STR117_RSE":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_STR + "." + DBConstants.PRR_GET_STR117_RSE_EXCEL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_COUNTRY, OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PORT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DEPOT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PROFORMA_NO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PROF_STATUS,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_CHARGE_CODE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_POINT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_FROM_DATE,request.getStrList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getStrList().get(0).getFromDate()));
                	map.put(DBConstants.P_I_TO_DATE,request.getStrList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getStrList().get(0).getToDate()));
                	map.put(DBConstants.P_I_SERVICE,request.getStrList().get(0).getService().trim().equals("")?null:request.getStrList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getStrList().get(0).getVessel().trim().equals("")?null:request.getStrList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getStrList().get(0).getVoyage().trim().equals("")?null:request.getStrList().get(0).getVoyage());
                	map.put(DBConstants.P_I_COUNTRY,request.getStrList().get(0).getCountry().trim().equals("")?null:request.getStrList().get(0).getCountry());
                	map.put(DBConstants.P_I_FSC,request.getStrList().get(0).getFsc().trim().equals("")?null:request.getStrList().get(0).getFsc());
                	map.put(DBConstants.P_I_PORT,request.getStrList().get(0).getPort().trim().equals("")?null:request.getStrList().get(0).getPort());
                	map.put(DBConstants.P_I_DEPOT,request.getStrList().get(0).getDepot().trim().equals("")?null:request.getStrList().get(0).getDepot());
                	map.put(DBConstants.P_I_PROFORMA_NO,request.getStrList().get(0).getProfNo().trim().equals("")?null:request.getStrList().get(0).getProfNo());
                	map.put(DBConstants.P_I_PROF_STATUS,request.getStrList().get(0).getProfStatus().trim().equals("")?null:request.getStrList().get(0).getProfStatus());
                	map.put(DBConstants.P_I_CHARGE_CODE,request.getStrList().get(0).getChargeCode().trim().equals("")?null:request.getStrList().get(0).getChargeCode());
                	map.put(DBConstants.P_I_POINT,request.getStrList().get(0).getPoint().trim().equals("")?null:request.getStrList().get(0).getPoint());
                	map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getStrList().get(0).getUserLoginFSC().trim().equals("")?null:request.getStrList().get(0).getUserLoginFSC());                	
                	map.put(DBConstants.P_I_USER_ID,request.getStrList().get(0).getUserID().trim().equals("")?null:request.getStrList().get(0).getUserID());
                  break;
                  
                case "STR119_RSE_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_STR + "." + DBConstants.PRR_GET_STR119_RSE_EXCEL_NEW_1)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DEPOT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_AGREEMENT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EMPTY_LADEN,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC, OracleTypes.VARCHAR),            
        					new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),            
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_FROM_DATE,request.getStrList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getStrList().get(0).getFromDate()));
                	map.put(DBConstants.P_I_TO_DATE,request.getStrList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getStrList().get(0).getToDate()));
                	map.put(DBConstants.P_I_SERVICE,request.getStrList().get(0).getService().trim().equals("")?null:request.getStrList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getStrList().get(0).getVessel().trim().equals("")?null:request.getStrList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getStrList().get(0).getVoyage().trim().equals("")?null:request.getStrList().get(0).getVoyage());
                	map.put(DBConstants.P_I_FSC,request.getStrList().get(0).getFsc().trim().equals("")?null:request.getStrList().get(0).getFsc());
                	map.put(DBConstants.P_I_DEPOT,request.getStrList().get(0).getDepot().trim().equals("")?null:request.getStrList().get(0).getDepot());
                	map.put(DBConstants.P_I_AGREEMENT,request.getStrList().get(0).getAgreementNo().trim().equals("")?null:request.getStrList().get(0).getAgreementNo());
                	map.put(DBConstants.P_I_EMPTY_LADEN,request.getStrList().get(0).getEmptyLaden().trim().equals("")?null:request.getStrList().get(0).getEmptyLaden());
                	map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getStrList().get(0).getUserLoginFSC().trim().equals("")?null:request.getStrList().get(0).getUserLoginFSC());
                	map.put(DBConstants.P_I_USER_ID,request.getStrList().get(0).getUserID().trim().equals("")?null:request.getStrList().get(0).getUserID());
                break;
                
                case "STR119_RSE_PDF":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_STR + "." + DBConstants.PRR_GET_STR119_RSE_PDF)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_FROM_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_TO_DATE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_SERVICE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VESSEL,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VOYAGE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_FSC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_DEPOT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_AGREEMENT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EMPTY_LADEN,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC, OracleTypes.VARCHAR),            
        					new SqlParameter(DBConstants.P_I_USER_ID, OracleTypes.VARCHAR),            
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_FROM_DATE,request.getStrList().get(0).getFromDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getStrList().get(0).getFromDate()));
                	map.put(DBConstants.P_I_TO_DATE,request.getStrList().get(0).getToDate().trim().equals("")?null:DateHelper.convertDateFormat(request.getStrList().get(0).getToDate()));
                	map.put(DBConstants.P_I_SERVICE,request.getStrList().get(0).getService().trim().equals("")?null:request.getStrList().get(0).getService());
                	map.put(DBConstants.P_I_VESSEL,request.getStrList().get(0).getVessel().trim().equals("")?null:request.getStrList().get(0).getVessel());
                	map.put(DBConstants.P_I_VOYAGE,request.getStrList().get(0).getVoyage().trim().equals("")?null:request.getStrList().get(0).getVoyage());
                	map.put(DBConstants.P_I_FSC,request.getStrList().get(0).getFsc().trim().equals("")?null:request.getStrList().get(0).getFsc());
                	map.put(DBConstants.P_I_DEPOT,request.getStrList().get(0).getDepot().trim().equals("")?null:request.getStrList().get(0).getDepot());
                	map.put(DBConstants.P_I_AGREEMENT,request.getStrList().get(0).getAgreementNo().trim().equals("")?null:request.getStrList().get(0).getAgreementNo());
                	map.put(DBConstants.P_I_EMPTY_LADEN,request.getStrList().get(0).getEmptyLaden().trim().equals("")?null:request.getStrList().get(0).getEmptyLaden());
                	map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getStrList().get(0).getUserLoginFSC().trim().equals("")?null:request.getStrList().get(0).getUserLoginFSC());
                	map.put(DBConstants.P_I_USER_ID,request.getStrList().get(0).getUserID().trim().equals("")?null:request.getStrList().get(0).getUserID());
                break;

                case "STR116_RSE_EXCEL":
                	jdbcCall=new SimpleJdbcCall(getJdbcTemplate())
        			.withProcedureName(DBConstants.PCR_JASPER_STR + "." + DBConstants.PRR_GET_STR116_RSE_EXCEL)
        			.withoutProcedureColumnMetaDataAccess()
        			.declareParameters(
        					new SqlParameter(DBConstants.P_I_DEPOT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_COUNTRY,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_AGREEMENT_NO,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_PORT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_POINT,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_VENDOR,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_EXCL_EXPIRE,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_LOGIN_FSC,OracleTypes.VARCHAR),
        					new SqlParameter(DBConstants.P_I_USER_ID,OracleTypes.VARCHAR),
        					new SqlOutParameter(parameterName,OracleTypes.CURSOR)
        					).returningResultSet(parameterName, new RootTypeMapper());
                	map.put(DBConstants.P_I_DEPOT,request.getStrList().get(0).getDepot().trim().equals("")?null:request.getStrList().get(0).getDepot());
                	map.put(DBConstants.P_I_COUNTRY,request.getStrList().get(0).getCountry().trim().equals("")?null:request.getStrList().get(0).getCountry());
                	map.put(DBConstants.P_I_AGREEMENT_NO,request.getStrList().get(0).getAgreementNo().trim().equals("")?null:request.getStrList().get(0).getAgreementNo());
                	map.put(DBConstants.P_I_PORT,request.getStrList().get(0).getPort().trim().equals("")?null:request.getStrList().get(0).getPort());
                	map.put(DBConstants.P_I_POINT,request.getStrList().get(0).getPoint().trim().equals("")?null:request.getStrList().get(0).getPoint());
                	map.put(DBConstants.P_I_VENDOR,request.getStrList().get(0).getVendor().trim().equals("")?null:request.getStrList().get(0).getVendor());
                	map.put(DBConstants.P_I_EXCL_EXPIRE,request.getStrList().get(0).getExcelExpire().trim().equals("")?null:request.getStrList().get(0).getExcelExpire());
                	map.put(DBConstants.P_I_USER_LOGIN_FSC,request.getStrList().get(0).getUserLoginFSC().trim().equals("")?null:request.getStrList().get(0).getUserLoginFSC());
                	map.put(DBConstants.P_I_USER_ID,request.getStrList().get(0).getUserID().trim().equals("")?null:request.getStrList().get(0).getUserID());
                	
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
