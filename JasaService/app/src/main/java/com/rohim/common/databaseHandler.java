package com.rohim.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class databaseHandler {
//		extends SQLiteOpenHelper {
//
//	// Database Version
//    private static final int DATABASE_VERSION = 1;
//
//    // Database Name
//    private static final String DATABASE_NAME = "JS";
	/*
    // Login table name
    private static final String TABLE_LOGIN = "LOGIN";
    public static final String TABLE_FACILITY_TASK = "TABLE_FACILITY";
    public static final String TABLE_FACILITY_HISTORY = "TABLE_FACILITY_HISTORY";
    public static final String TABLE_SETTING = "TABLE_SETTING";
    public static final String TABLE_USER = "TABLE_USER";
    
    // Login Table Columns names
    public static final String KEY_ID = "ID";
    public static final String COLUMN_TASK_ID = "TASK_ID";
    public static final String COLUMN_FACILITY_NO = "FACILITY_NO";
    public static final String COLUMN_LESSE_NAME = "LESSE_NAME";
    public static final String COLUMN_NET_FINANCE = "NET_FINANCE";
    public static final String COLUMN_TENOR = "TENOR";
    public static final String COLUMN_MARKETING = "MARKETING";
    public static final String COLUMN_CREDIT_SCORING = "CREDIT_SCORING";
    public static final String COLUMN_FACILITY_STATUS = "FACILITY_STATUS";
    public static final String COLUMN_APPLICATION_DATE = "APPLICATION_DATE";
    public static final String COLUMN_OPEN_DATE = "OPEN_DATE";
    public static final String COLUMN_APPROVE_DATE = "APPROVE_DATE";
    public static final String COLUMN_TOTAL_PRINCIPAL = "TOTAL_PRINCIPAL";
    public static final String COLUMN_TOTAL_INSTALLMENT = "TOTAL_INSTALLMENT";
    public static final String COLUMN_TOTAL_OS_PRINCIPAL = "TOTAL_OS_PRINCIPAL";
    public static final String COLUMN_TOTAL_OS_INSTALLMENT = "TOTAL_OS_INSTALLMENT";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_ACTION = "ACTION";
    public static final String COLUMN_ACTION_DATE = "ACTION_DATE";
    public static final String COLUMN_SENT_DATE = "SENT_DATE";
    public static final String COLUMN_SUCCES_DATE = "SUCCES_DATE";
    public static final String COLUMN_CREATE_DATE = "CREATE_DATE";
    
    public static final String COLUMN_PARAM_SETTING = "PARAM_SETTING";
    public static final String COLUMN_SETTING_VALUE = "SETTING_VALUE";
    
    public static final String COLUMN_ID_USER = "ID_USER";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_STATUS_USER = "STATUS_USER";
    */
//	public databaseHandler(Context context) {
//		super(context, DATABASE_NAME, null, DATABASE_VERSION);
//		String DBPATH = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
//	    Log.e("Path 1", DBPATH);
//	}

//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		String createTableFacilityTask = createTableTaskFacility();
//		db.execSQL(createTableFacilityTask);
		
//		String createTableFacilityHistory = createTableHistoryFacility();
//		db.execSQL(createTableFacilityHistory);
		
	/*	String createTableSetting = "CREATE TABLE "+TABLE_SETTING+"( "
				+COLUMN_PARAM_SETTING+ " VARCHAR(255) PRIMARY KEY, "
				+COLUMN_SETTING_VALUE+ " VARCHAR(255) )";
		db.execSQL(createTableSetting);
		
		String createTableUser = "CREATE TABLE "+TABLE_USER+"( "
				+COLUMN_ID_USER + " VARCHAR(25) PRIMARY KEY, "
				+COLUMN_USER_NAME+ " VARCHAR(50), "
				+COLUMN_PASSWORD+ " VARCHAR(50), "
				+COLUMN_STATUS_USER+ " VARCHAR(50) )";
		db.execSQL(createTableUser);*/
//	}
	


//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
    /*    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACILITY_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
*/
        // Create tables again
//        onCreate(db);
//	}
	
	/*
    public void addFacility(String taskId, String facilityNo, String lesseName, String marketing, String facilityStatus, int tenor,
							BigDecimal creditScoring, BigDecimal netFinance, BigDecimal totalPrincipal, BigDecimal totalInstallment, BigDecimal totalOsPrincipal, BigDecimal totalOsInstallment,
							Date applicationDate, Date openDate, Date approveDate, String status, String action){
    	SQLiteDatabase db = this.getWritableDatabase();

    	SimpleDateFormat formatSql = new SimpleDateFormat("yyyy/MM/dd");
    	
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_ID, taskId);
        values.put(COLUMN_FACILITY_NO, facilityNo);
        values.put(COLUMN_LESSE_NAME, lesseName);
        values.put(COLUMN_MARKETING, marketing);
        values.put(COLUMN_FACILITY_STATUS, facilityStatus);
        values.put(COLUMN_TENOR, String.valueOf(tenor));
        values.put(COLUMN_CREDIT_SCORING, creditScoring.toString());
        values.put(COLUMN_NET_FINANCE, netFinance.toString());
        values.put(COLUMN_TOTAL_PRINCIPAL, totalPrincipal.toString());
        values.put(COLUMN_TOTAL_INSTALLMENT, totalInstallment.toString());
        values.put(COLUMN_TOTAL_OS_PRINCIPAL, totalOsPrincipal.toString());
        values.put(COLUMN_TOTAL_OS_INSTALLMENT, totalOsInstallment.toString());
        values.put(COLUMN_APPLICATION_DATE, formatSql.format(applicationDate));
        values.put(COLUMN_OPEN_DATE, formatSql.format(openDate));
        values.put(COLUMN_APPROVE_DATE, formatSql.format(approveDate));
        if(!status.isEmpty() || status!=null){
        	values.put(COLUMN_STATUS, status);
        }
        if(!action.isEmpty() || action!=null)
        	values.put(COLUMN_ACTION, action);
        

        // Inserting Row
        db.insert(TABLE_FACILITY_TASK, null, values);
        db.close(); // Closing database connection
    }
    
    public void addFacility(Facility fclt){
    	SQLiteDatabase db = this.getWritableDatabase();

    	SimpleDateFormat formatSql = new SimpleDateFormat("yyyy/MM/dd");
    	
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_ID, fclt.getTaskId());
        values.put(COLUMN_FACILITY_NO, fclt.getFacilityNo());
        values.put(COLUMN_LESSE_NAME, fclt.getLesseName());
        values.put(COLUMN_MARKETING, fclt.getMarketing());
        values.put(COLUMN_FACILITY_STATUS, fclt.getFacilityStatus());
        values.put(COLUMN_TENOR, fclt.getTenor());
        values.put(COLUMN_CREDIT_SCORING, fclt.getCreditScoring());
        values.put(COLUMN_NET_FINANCE, fclt.getNetFinance());
        values.put(COLUMN_TOTAL_PRINCIPAL, fclt.getTotalPrincipal());
        values.put(COLUMN_TOTAL_INSTALLMENT, fclt.getTotalInstallment());
        values.put(COLUMN_TOTAL_OS_PRINCIPAL, fclt.getTotalOsPrincipal());
        values.put(COLUMN_TOTAL_OS_INSTALLMENT, fclt.getTotalOsInstallment());
        values.put(COLUMN_APPLICATION_DATE, fclt.getApplicationDate());
        values.put(COLUMN_OPEN_DATE, fclt.getOpenDate());
        values.put(COLUMN_APPROVE_DATE, fclt.getApproveDate());
        values.put(COLUMN_CREATE_DATE, fclt.getCreateDate());
        if(!fclt.getStatus().isEmpty() || fclt.getStatus()!=null){
        	values.put(COLUMN_STATUS, fclt.getStatus());
        }
        if(!fclt.getAction().isEmpty() || fclt.getAction()!=null)
        	values.put(COLUMN_ACTION, fclt.getAction());
        

        // Inserting Row
        db.insert(TABLE_FACILITY_TASK, null, values);
        db.close(); // Closing database connection
    }
    
    public ArrayList<Facility> getFacilityDetails(String sqlQuery){
    	ArrayList<Facility> arrayList = new ArrayList<Facility>();
    	
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        // Move to first row
        
        if(cursor.moveToFirst()){
        	do{
        		Facility fclt = new Facility();
        		fclt.setId(cursor.getString(0));
        		fclt.setTaskId(cursor.getString(1));
        		fclt.setFacilityNo(cursor.getString(2));
        		fclt.setLesseName(cursor.getString(3));
        		fclt.setMarketing(cursor.getString(4));
        		fclt.setFacilityStatus(cursor.getString(5));
        		fclt.setTenor(cursor.getString(6));
        		fclt.setCreditScoring(cursor.getString(7));
        		fclt.setNetFinance(cursor.getString(8));
        		fclt.setTotalPrincipal(cursor.getString(9));
        		fclt.setTotalInstallment(cursor.getString(10));
        		fclt.setTotalOsPrincipal(cursor.getString(11));
        		fclt.setTotalOsInstallment(cursor.getString(12));
        		fclt.setApplicationDate(cursor.getString(13));
        		fclt.setOpenDate(cursor.getString(14));
        		fclt.setApproveDate(cursor.getString(15));
        		fclt.setStatus(cursor.getString(16));
        		fclt.setAction(cursor.getString(17));
        		fclt.setActionDate(cursor.getString(18));
        		fclt.setSentDate(cursor.getString(19));
        		fclt.setSuccesDate(cursor.getString(20));
        		arrayList.add(fclt);
        	}while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user
        return arrayList;
    }
    
    public long getCountFacilityStatus(String status){
    	long count = 0;
    	SQLiteDatabase db = this.getReadableDatabase();
    	SQLiteStatement statementCount = db.compileStatement(" SELECT COUNT(*) FROM "+TABLE_FACILITY_TASK+" WHERE "+ COLUMN_STATUS +" = '"+status+"'");
    	count = statementCount.simpleQueryForLong();
    	db.close();
    	return count;
    }
    
    public void insertDataToTableFacility(){
		for (int i = 0; i < 10; i++) {
			addFacility("","00001/CV16/0000"+i, "lesseName "+i, "marketing "+i, "NEW", 
					24, new BigDecimal(100.00), new BigDecimal(1000000000.00), new BigDecimal(10000000.00), new BigDecimal(1500000.00),
					new BigDecimal(24000000.00), new BigDecimal(36000000.00), new Date(), new Date(),
					new Date(), "", "");
		}
		for (int i = 10; i < 20; i++) {
			String status = "";
			String action = "";
			
			if(i==13){
				status = "sent";
				action = "Send Back";
			}else if(i==12){
				status = "retry";
				action = "Send Back";
			}else if(i==14){
				status = "succes";
				action = "Send Back";
			}else if(i==15){
				status = "sent";
				action = "Approve";
			}else if(i==16){
				status = "retry";
				action = "Approve";
			}else{
				status = "succes";
				action = "Approve";
			}
			addFacility("","00001/CV16/0000"+i, "lesseName "+i, "marketing "+i, "NEW", 
					24, new BigDecimal(100.00), new BigDecimal(1000000000.00), new BigDecimal(10000000.00), new BigDecimal(1500000.00),
					new BigDecimal(24000000.00), new BigDecimal(36000000.00), new Date(), new Date(),
					new Date(), status, action);
		}
	}
    
    public void updateActionInFacility(int id, String facilityNo, String action){
    	Date actionDate = new Date();
    	SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy/MM/dd");
    	SimpleDateFormat sqlDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String SQL = "";
    	if(action.equals(EnumAction.Approve.getVal()))
    		SQL = "UPDATE "+TABLE_FACILITY_TASK+" SET "+COLUMN_ACTION+" = '"+action +"', "+COLUMN_ACTION_DATE+" = '"+sqlDateTime.format(actionDate)+"' , "+COLUMN_APPROVE_DATE+" = '"+sqlDate.format(actionDate)+"' , "+COLUMN_STATUS+" = '"+EnumStatus.Retry.getVal()+"'"
    			+" WHERE "+KEY_ID+" = "+id+" AND "+COLUMN_FACILITY_NO+" = '"+facilityNo+"'";
    	else 
    		SQL = "UPDATE "+TABLE_FACILITY_TASK+" SET "+COLUMN_ACTION+" = '"+action +"', "+COLUMN_ACTION_DATE+" = '"+sqlDateTime.format(actionDate) +"' , "+COLUMN_STATUS+" = '"+EnumStatus.Retry.getVal()+"'"
    				+" WHERE "+KEY_ID+" = "+id+" AND "+COLUMN_FACILITY_NO+" = '"+facilityNo+"'";
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	SQLiteStatement statementCount = db.compileStatement(SQL);
    	statementCount.execute();
    	db.close();
    }
    
    public void saveSettingData(String ipAddress){
    	String ipAddres = getIPAddres();
    	if(ipAddres==null || ipAddres.isEmpty()){
    		
	    	SQLiteDatabase db = this.getWritableDatabase();
	
	    	ContentValues values = new ContentValues();
	        values.put(COLUMN_PARAM_SETTING, EnumSetting.Ip.getVal());
	        values.put(COLUMN_SETTING_VALUE, ipAddress);
	
	        // Inserting Row
	        db.insert(TABLE_SETTING, null, values);
	        db.close(); // Closing database connection
    	}
    }
    
    public void updateSettingData(String ipAddress){
    	String SQL = "Update "+TABLE_SETTING
    			+" set "+COLUMN_SETTING_VALUE+" = '"+ipAddress
    			+"' where "+COLUMN_PARAM_SETTING+" = '"+EnumSetting.Ip.getVal()+"'";
    	SQLiteDatabase db = this.getReadableDatabase();
    	SQLiteStatement statementCount = db.compileStatement(SQL);
    	statementCount.execute();
    	db.close();
    }
    
    public String getIPAddres(){
    	String ipAddress = null;
    	try {
    		String SQL = "Select "+COLUMN_SETTING_VALUE+" from "+TABLE_SETTING+" where "+COLUMN_PARAM_SETTING+" = '"+EnumSetting.Ip.getVal()+"'";
        	SQLiteDatabase db = this.getReadableDatabase();
        	SQLiteStatement statementCount = db.compileStatement(SQL);
        	ipAddress = statementCount.simpleQueryForString();
        	db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ipAddress;		
    }
    
    public void saveUser(String userId, String UserName, String password, String status){
		SQLiteDatabase db = this.getWritableDatabase();

    	ContentValues values = new ContentValues();
        values.put(COLUMN_ID_USER, userId);
        values.put(COLUMN_USER_NAME, UserName);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_STATUS_USER, status);

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }
    
    public String getUserNameActive(){
    	String userName = "";
    	try {
			String SQL = "Select " + COLUMN_USER_NAME + " from " + TABLE_USER + " where " + COLUMN_STATUS_USER + " = 'active'";
			SQLiteDatabase db = this.getReadableDatabase();
			SQLiteStatement statementCount = db.compileStatement(SQL);
			userName = statementCount.simpleQueryForString();
			db.close();
		}catch (SQLiteDoneException e){
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return userName;		
    }
    
    public String getIdUserActive(){
    	String userName = "";
    	try {
    		String SQL = "Select "+COLUMN_ID_USER+" from "+TABLE_USER+" where "+COLUMN_STATUS_USER+" = 'active'";
        	SQLiteDatabase db = this.getReadableDatabase();
        	SQLiteStatement statementCount = db.compileStatement(SQL);
        	userName = statementCount.simpleQueryForString();
        	db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return userName;		
    }
    
    public void updateUserData(String userName){
    	String SQL = "Update "+TABLE_USER
    			+" set "+COLUMN_STATUS_USER+" = 'incative' where "+COLUMN_ID_USER+" = '"+userName+"'";
    	SQLiteDatabase db = this.getReadableDatabase();
    	SQLiteStatement statementCount = db.compileStatement(SQL);
    	statementCount.execute();
    	db.close();
    }
    
    public long getCountUser(String status){
    	long count = 0;
    	SQLiteDatabase db = this.getReadableDatabase();
    	SQLiteStatement statementCount = db.compileStatement(" SELECT COUNT(*) FROM "+TABLE_USER+" WHERE "+ COLUMN_STATUS_USER +" = '"+status+"'");
    	count = statementCount.simpleQueryForLong();
    	db.close();
    	return count;
    }
    
    public void deleteUserData(String userName){
    	String SQL = "Delete FROM "+TABLE_USER
    			+" where "+COLUMN_ID_USER+" = '"+userName+"'";
    	SQLiteDatabase db = this.getReadableDatabase();
    	SQLiteStatement statementCount = db.compileStatement(SQL);
    	statementCount.execute();
    	db.close();
    }*/
}
