package com.rclgroup.dolphin.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rclgroup.dolphin.util.dto.MergedResponseFetchResultContent;
import com.rclgroup.dolphin.util.dto.StatusListResponse;

public class DTLUtils {

	
	public static StatusListResponse<MergedResponseFetchResultContent> mergeResultSetResponse(ResultSet rs) throws JSONException, SQLException {
		
		final StatusListResponse<MergedResponseFetchResultContent> response = new StatusListResponse<MergedResponseFetchResultContent>();
		final MergedResponseFetchResultContent resultContent = new MergedResponseFetchResultContent();
		JSONObject temp = new JSONObject(rs.getString("DATA_RESULT"));
		
		if (!temp.get("resultStatus").equals("F")) {
			List<Object> combinedList = new ArrayList<>();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount=metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				if (!rs.getString(metaData.getColumnName(i)).isEmpty()) {
					temp = new JSONObject(rs.getString(metaData.getColumnName(i)));
					combinedList.addAll(temp.getJSONArray("resultContent").toList());
				}	
			}
			resultContent.setMergedList(combinedList);
		}
		
		response.setResultStatus(temp.getString("resultStatus"));
		response.setResultMessage(temp.getString("resultMessage"));
		response.setResultContent(resultContent.getMergedList());
		return response;
	}
	
	public static StatusListResponse<MergedResponseFetchResultContent> vms101SocCocResultSet(ResultSet rs) throws JSONException, SQLException {

	    final StatusListResponse<MergedResponseFetchResultContent> response = new StatusListResponse<>();
	    final MergedResponseFetchResultContent resultContent = new MergedResponseFetchResultContent();
	    JSONObject temp = new JSONObject(rs.getString("DATA_RESULT"));

	    if (!temp.get("resultStatus").equals("F")) {
	        List<Object> combinedList = new ArrayList<>();

	        List<Map<String, Object>> dataResultList = new ArrayList<>();
	        if (!rs.getString("DATA_RESULT").isEmpty()) {
	            temp = new JSONObject(rs.getString("DATA_RESULT"));
	            JSONArray dataResultArray = temp.getJSONArray("resultContent");
	            for (int i = 0; i < dataResultArray.length(); i++) {
	                dataResultList.add(dataResultArray.getJSONObject(i).toMap());
	            }
	        }

	        List<Map<String, Object>> dataResult1List = new ArrayList<>();
	        if (!rs.getString("DATA_RESULT_1").isEmpty()) {
	            temp = new JSONObject(rs.getString("DATA_RESULT_1"));
	            JSONArray dataResult1Array = temp.getJSONArray("resultContent");
	            for (int i = 0; i < dataResult1Array.length(); i++) {
	                dataResult1List.add(dataResult1Array.getJSONObject(i).toMap());
	            }
	        }

	        if (dataResult1List.isEmpty()) {
	            combinedList.addAll(dataResultList);
	        } else {
	            if (!dataResultList.isEmpty()) {
	                Map<String, Object> firstDataResult1Item = dataResult1List.get(0);
	                firstDataResult1Item.putAll(dataResultList.get(0));
	            }
	            combinedList.addAll(dataResult1List);
	        }

	        resultContent.setMergedList(combinedList);
	    }

	    response.setResultStatus(temp.getString("resultStatus"));
	    response.setResultMessage(temp.getString("resultMessage"));
	    response.setResultContent(resultContent.getMergedList());

	    return response;
	}
	
	public static StatusListResponse<MergedResponseFetchResultContent> tos120PdfResultSet(ResultSet rs) throws JSONException, SQLException {
	    final StatusListResponse<MergedResponseFetchResultContent> response = new StatusListResponse<>();
	    final MergedResponseFetchResultContent resultContent = new MergedResponseFetchResultContent();
	    JSONObject temp = new JSONObject(rs.getString("DATA_RESULT"));
	    
	    if (!temp.get("resultStatus").equals("F")) {
	        List<Object> combinedList = new ArrayList<>();
	        List<Object> combinedList2 = new ArrayList<>();
	        
	        ResultSetMetaData metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        
	        for (int i = 1; i <= columnCount; i++) {
	            if (!rs.getString(metaData.getColumnName(i)).isEmpty()) {
	                temp = new JSONObject(rs.getString(metaData.getColumnName(i)));
	                combinedList.addAll(temp.getJSONArray("resultContent").toList());
	            }
	        }
	        
	        JSONArray jsonArray = new JSONArray(combinedList);
	        
	        Map<String, Map<String, List<JSONObject>>> groupedMap = new LinkedHashMap<>();
	        
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject jsonObject = jsonArray.getJSONObject(i);
	            String terminal = jsonObject.optString("terminal", "");
	            String service = jsonObject.optString("service", "***");
	            
	            groupedMap
	                .computeIfAbsent(terminal, k -> new LinkedHashMap<>())
	                .computeIfAbsent(service, k -> new ArrayList<>())
	                .add(jsonObject);
	        }

	        List<JSONObject> mergeList = new ArrayList<>();
	        
	        for (Map<String, List<JSONObject>> serviceMap : groupedMap.values()) {
	            for (List<JSONObject> group : serviceMap.values()) {
	                mergeList.addAll(group);
	            }
	        }

	        combinedList2.addAll(mergeList);
	        resultContent.setMergedList(combinedList2);
	    }
	    
	    response.setResultStatus(temp.getString("resultStatus"));
	    response.setResultMessage(temp.getString("resultMessage"));
	    response.setResultContent(resultContent.getMergedList());
	    
	    return response;
	}
	
	public static StatusListResponse<MergedResponseFetchResultContent> tos120ExcelResultSet(ResultSet rs) throws JSONException, SQLException {
		
		final StatusListResponse<MergedResponseFetchResultContent> response = new StatusListResponse<>();
	    final MergedResponseFetchResultContent resultContent = new MergedResponseFetchResultContent();
	    JSONObject temp = new JSONObject(rs.getString("DATA_RESULT"));
			int slot=0,portTariff = 0,customer=0,vendor=0;
			
			
			if (!temp.get("resultStatus").equals("F")) {
				List<Object> combinedList = new ArrayList<>();
				
				
				if (!rs.getString("DATA_RESULT").isEmpty()) {
					temp = new JSONObject(rs.getString("DATA_RESULT"));
					portTariff=temp.getJSONArray("resultContent").length();	
					combinedList.addAll(temp.getJSONArray("resultContent").toList());
				}
				
				if (!rs.getString("DATA_RESULT_1").isEmpty()) {
					temp = new JSONObject(rs.getString("DATA_RESULT_1"));
					slot=temp.getJSONArray("resultContent").length();
					combinedList.addAll(temp.getJSONArray("resultContent").toList());
				}
				
				if (!rs.getString("DATA_RESULT_2").isEmpty()) {
					temp = new JSONObject(rs.getString("DATA_RESULT_2"));
					customer=temp.getJSONArray("resultContent").length();
					combinedList.addAll(temp.getJSONArray("resultContent").toList());
				}
				
				if (!rs.getString("DATA_RESULT_3").isEmpty()) {
					temp = new JSONObject(rs.getString("DATA_RESULT_3"));
					vendor=temp.getJSONArray("resultContent").length();
					combinedList.addAll(temp.getJSONArray("resultContent").toList());
				}
				
				String str="{\"slotCount\":"+slot+",\"portTariffCount\":"+portTariff+",\"customerCount\":"+customer+",\"vendorCount\":"+vendor+"}";
				
				combinedList.add(0,new JSONObject(str));
				resultContent.setMergedList(combinedList);
			}
			
			response.setResultStatus(temp.getString("resultStatus"));
			response.setResultMessage(temp.getString("resultMessage"));
			response.setResultContent(resultContent.getMergedList());
			return response;
		}
	public static StatusListResponse<MergedResponseFetchResultContent> tos118SHSPdfResultSet(ResultSet rs) throws JSONException, SQLException {
		
		final StatusListResponse<MergedResponseFetchResultContent> response = new StatusListResponse<MergedResponseFetchResultContent>();
		final MergedResponseFetchResultContent resultContent = new MergedResponseFetchResultContent();
		JSONObject temp = new JSONObject(rs.getString("DATA_RESULT"));
		
		if (!temp.get("resultStatus").equals("F")) {
			List<Object> combinedList = new ArrayList<>();
            List<Object> combinedList2 = new ArrayList<>();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount=metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				if (!rs.getString(metaData.getColumnName(i)).isEmpty()) {
					temp = new JSONObject(rs.getString(metaData.getColumnName(i)));
					combinedList.addAll(temp.getJSONArray("resultContent").toList());
				}	
			}
			
			JSONArray jsonArray = new JSONArray(combinedList);
			Map<String, List<JSONObject>> groupedMap = new LinkedHashMap<>();
			for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject jsonObject = jsonArray.getJSONObject(i);
	            String key = jsonObject.optString("proformaRef", "");
	            groupedMap.computeIfAbsent(key, k -> new ArrayList<>()).add(jsonObject);
	        }
	        
			List<JSONObject> mergeList = new ArrayList<>();
	        for (List<JSONObject> group : groupedMap.values()) {
	        	mergeList.addAll(group);
	        }
	        
	        combinedList2.addAll(mergeList);
			resultContent.setMergedList(combinedList2);
		}
				
		response.setResultStatus(temp.getString("resultStatus"));
		response.setResultMessage(temp.getString("resultMessage"));
		response.setResultContent(resultContent.getMergedList());
		return response;
	}
	
	public static StatusListResponse<MergedResponseFetchResultContent> tos118ShsPdfResultSet(ResultSet rs) throws JSONException, SQLException {
	    final StatusListResponse<MergedResponseFetchResultContent> response = new StatusListResponse<>();
	    final MergedResponseFetchResultContent resultContent = new MergedResponseFetchResultContent();

	    JSONObject temp = new JSONObject(rs.getString("DATA_RESULT"));
	    String vesselName = null;

	    if (!rs.getString("DATA_RESULT").isEmpty()) {
	        JSONArray resultContentArray = temp.getJSONArray("resultContent");
	        if (resultContentArray.length() > 0) {
	            vesselName = resultContentArray.getJSONObject(0).optString("vesselName", null);
	        }
	    }

	    List<Object> combinedList = new ArrayList<>();

	    
	    if (!rs.getString("DATA_RESULT_1").isEmpty()) {
	        JSONObject temp1 = new JSONObject(rs.getString("DATA_RESULT_1"));
	        JSONArray resultContentArray1 = temp1.getJSONArray("resultContent");

	        for (int i = 0; i < resultContentArray1.length(); i++) {
	            JSONObject entry = resultContentArray1.getJSONObject(i);

	            if (vesselName != null && !vesselName.isEmpty()) {
	                entry.put("vesselName", vesselName);
	            }

	            combinedList.add(entry.toMap());
	        }
	    }
	    if(combinedList.isEmpty()) {
		    if (!rs.getString("DATA_RESULT").isEmpty()) {
		        JSONArray resultContentArray = temp.getJSONArray("resultContent");
		        for (int i = 0; i < resultContentArray.length(); i++) {
		            JSONObject entry = resultContentArray.getJSONObject(i);
		            combinedList.add(entry.toMap());
		        }
		    }
	    }
	    resultContent.setMergedList(combinedList);
	    response.setResultStatus(temp.getString("resultStatus"));
	    response.setResultMessage(temp.getString("resultMessage"));
	    response.setResultContent(resultContent.getMergedList());

	    return response;
	}
	
	public static StatusListResponse<MergedResponseFetchResultContent> tos118TosPdfResultSet(ResultSet rs) throws JSONException, SQLException {
	    final StatusListResponse<MergedResponseFetchResultContent> response = new StatusListResponse<>();
	    final MergedResponseFetchResultContent resultContent = new MergedResponseFetchResultContent();

	    JSONObject temp = new JSONObject(rs.getString("DATA_RESULT"));
	    String vesselName = null;

	    if (!rs.getString("DATA_RESULT").isEmpty()) {
	        JSONArray resultContentArray = temp.getJSONArray("resultContent");
	        if (resultContentArray.length() > 0) {

	            vesselName = resultContentArray.getJSONObject(0).optString("vesselName", null);
	        }
	    }

	    List<Object> combinedList = new ArrayList<>();

	    if(!rs.getString("DATA_RESULT_2").isEmpty())
	    {
		    if (!rs.getString("DATA_RESULT_2").isEmpty()) {
		        JSONObject temp1 = new JSONObject(rs.getString("DATA_RESULT_2"));
		        JSONArray resultContentArray1 = temp1.getJSONArray("resultContent");
	
		        for (int i = 0; i < resultContentArray1.length(); i++) {
		            JSONObject entry = resultContentArray1.getJSONObject(i);
	
		            if (vesselName != null && !vesselName.isEmpty()) {
		                entry.put("vesselName", vesselName);
		            }
	
		            combinedList.add(entry.toMap());
		        }
		    }
	    }
	    if(!rs.getString("DATA_RESULT_1").isEmpty())
	    {
		    if (!rs.getString("DATA_RESULT_1").isEmpty()) {
		        JSONObject temp1 = new JSONObject(rs.getString("DATA_RESULT_1"));
		        JSONArray resultContentArray1 = temp1.getJSONArray("resultContent");
	
		        for (int i = 0; i < resultContentArray1.length(); i++) {
		            JSONObject entry = resultContentArray1.getJSONObject(i);
	
		            if (vesselName != null && !vesselName.isEmpty()) {
		                entry.put("vesselName", vesselName);
		            }
	
		            combinedList.add(entry.toMap());
		        }
		    }
	    }
	    
	    
	    resultContent.setMergedList(combinedList);

	    response.setResultStatus(temp.getString("resultStatus"));
	    response.setResultMessage(temp.getString("resultMessage"));
	    response.setResultContent(resultContent.getMergedList());

	    return response;
	}
}