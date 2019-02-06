package com.github.mailservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringUtils_Local;

import com.github.files.WritingStirngToFile;
import com.github.wrappers.AppacheStringUtils;

/**
 * A pull request to <a href="https://github.com/apache/commons-lang/pull/393">apache/commons-lang</a>
 * 
 * https://travis-ci.org/apache/commons-lang/builds/475193675
 * 
 * @author yashwanth.m
 *
 */
public class TabularData_from_List extends AppacheStringUtils {
	public static void main(String[] args) throws Exception {
		
		//stringArrayData();
		mapObjectData();
	}
	public static void mapObjectData() {
		Map<String, String> projectMap = new HashMap<String, String>();
		projectMap.put("project", "Open Cart ");
		projectMap.put("module", "Home ");
		projectMap.put("suite", "Login to Application");
		projectMap.put("url", "https://demo.opencart.com/ ");
		projectMap.put("platform", "Windows 7 ");
		projectMap.put("browser", "Chrome - 50");
		
		List<Map<String, String>> stepsForMail = new ArrayList<Map<String, String>>();
			Map<String, String> stepDetails = new HashMap<String, String>();
			stepDetails.put("Name", "User Name");
			stepDetails.put("Action", "Text Box");
			stepDetails.put("Input", "Yashwanth2357@gmail.com");
			stepDetails.put("Status","Pass");
			stepDetails.put("Message", "");
			
			Map<String, String> stepDetails2 = new HashMap<String, String>();
			stepDetails2.put("Name", "Password");
			stepDetails2.put("Action", "Text Box");
			stepDetails2.put("Input", "Yash@123");
			stepDetails2.put("Status","Pass");
			stepDetails2.put("Message", "");
		stepsForMail.add(stepDetails);
		stepsForMail.add(stepDetails2);
		
		List<String> caseNames = new ArrayList<>();
		caseNames.add("Login Page 1");
		caseNames.add("Login Page 2");
		
		List<List<Map<String, String>>> caseRecords = new ArrayList<>();
		caseRecords.add(stepsForMail);
		caseRecords.add(stepsForMail);
		dynamicTabularMail(projectMap, caseRecords, caseNames);
	}
	
	public static void dynamicTabularMail(Map<String, String> projectMap,
			List<List<Map<String, String>>> caseRecords, List<String> caseNames) {
		try {
			
		if (caseRecords.size() == caseNames.size()) {
			String filePath = "./ReportsMail.html";
			String data = new String(Files.readAllBytes(Paths.get(filePath)));
			data = data.replace("[{~Suite~}]", projectMap.get("suite"));
			data = data.replace("[{~URL~}]", projectMap.get("url"));
			data = data.replace("[{~Platform~}]", projectMap.get("platform"));
			data = data.replace("[{~Browser~}]", projectMap.get("browser"));
			data = data.replace("[{~Project~}]", projectMap.get("project"));
			data = data.replace("[{~Module~}]", projectMap.get("module"));
			
			// Tables - Test Case, Test Steps
			String token = "<!-- [{(~Steps~)}] -->", caseNameToken = "[{(~Case_Name~)}]", caseName = "Login Page";
			data = data.replace(caseNameToken, caseName);
			String substringBetween = StringUtils.substringBetween(data, token, token);
			String substringDynamic = dynamicData(substringBetween, caseRecords.get(1));
			data = StringUtils_Local.replaceSubstringInBetween(data, substringDynamic, token, token);
			
			// Table - Test Cases
			String repeatTableString = "<!-- [{(~MultiTable~)}] -->";
			String tableToken = "<!-- [{(~TableSteps~)}] -->", tableCase = "[{(~Table_Case_Name~)}]";
			String tableSubString = StringUtils.substringBetween(data, repeatTableString, repeatTableString);
			
			String tablesBreak = "<p class=\"MsoNormal\"><o:p>&nbsp;</o:p></p>";
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < caseNames.size(); i++) {
				String repeat = repeatedTables(tableSubString, caseNames.get(i), tableToken, tableCase, caseRecords.get(i));
				buffer.append(repeat + tablesBreak);
			}
			String finalReport = StringUtils_Local.replaceSubstringInBetween(data, buffer.toString(), repeatTableString, repeatTableString);
			MailSenderSMTPGmail_Client.sendMail("Dynamic Mail Report.", finalReport, MailDomain.USER_NAME.getValue() );
		} else {
			System.out.println("Records mismatch.");
		}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String repeatedTables(String tableSubString, String tableCaseName,
			String tableToken, String tableCase, List<Map<String, String>> stepsForMail) {
		
		String repeat1 = tableSubString;
		repeat1 = repeat1.replace(tableCase, tableCaseName);
		String repeat1_record = StringUtils.substringBetween(repeat1, tableToken, tableToken);
		String repeat1_dynamicRecords = dynamicData(repeat1_record, stepsForMail);
		repeat1 = StringUtils_Local.replaceSubstringInBetween(repeat1, repeat1_dynamicRecords, tableToken, tableToken);
		
		return repeat1;
	}
	public static String dynamicData(String substringBetween, List<Map<String, String>> stepsForMail) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < stepsForMail.size(); i++) {
			Map<String, String> map = stepsForMail.get(i);
			String rowData = substringBetween;
			rowData = rowData.replace("[{(~ID~)}]", "TS_"+i);
			rowData = rowData.replace("[{(~Name~)}]", map.get("Name"));
			rowData = rowData.replace("[{(~Action~)}]", map.get("Action"));
			rowData = rowData.replace("[{(~Input~)}]", map.get("Input"));
			rowData = rowData.replace("[{(~Status~)}]", map.get("Status"));
			rowData = rowData.replace("[{(~Message~)}]", map.get("Message"));
			buffer.append(rowData);
		}
		return buffer.toString();
	}
	
	public static void stringArrayData() throws Exception {
		String data = new String(Files.readAllBytes(Paths.get("./html/Mail_Templates/dynamicReport.html")));
		String rowSaperator = "<!--  [$$ListRow$$] -->", rowDataSaperator = "[~$List$~]";
		// String data = "~~d1[~]d2[~]d3~~", rowSaperator = "~~", rowDataSaperator = "[~]";
		
		List<String[]> dynamicRecords = new ArrayList<>();
		dynamicRecords.add( new String[]{ "1", "1" } );
		dynamicRecords.add( new String[]{ "1", "1" } );
		dynamicRecords.add( new String[]{ "2", "1" } );
		dynamicRecords.add( new String[]{ "1", "2" } );
		// System.out.println( "List of Records : "+ dynamicRecords.size());
		
		// https://stackoverflow.com/a/38238785/5081877
		String finalTableData = dynamicRecordData(data, rowSaperator, rowDataSaperator, dynamicRecords);
		// System.out.println("Final :\n"+ finalTableData);
		
		String fileName = "D:/filename.txt";
		WritingStirngToFile.writeLines(finalTableData, fileName);
	}
	
}
