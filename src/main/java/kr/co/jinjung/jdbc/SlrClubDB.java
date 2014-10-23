package kr.co.jinjung.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;

import com.slrclub.ext.OMGSlrContentsData;


public class SlrClubDB {
	
	static Connection gConn = null;
//	static String     gOutput = "./output/";
//	static int        gCount  = 20;
	
//	public static Properties loadConfig() {
//		URL location = SlrClubDB.class.getProtectionDomain().getCodeSource().getLocation();
//		
//		System.out.println("location.getFile() : " + location.getFile());
//		
//		String configpath = location.getFile() + File.separator + "config.properties";
//		File file = new File(configpath);
//		FileInputStream fis = null;
//		Properties config = null;
//
//		try {
//			fis    = new FileInputStream(file);
//			config = new Properties();
//			config.load(fis);
//			fis.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (fis != null) {
//				try {
//					fis.close();
//				} catch (IOException e1) {}
//			}
//		}
//
//		return config;
//	}

//	public static void main(String[] args) {
//		ArrayList<BestImage> list = queryBestImage(100);
//		
//		System.out.println("list:" + list);
//	}
	/*
	 * db.jdbc_driver=com.mysql.jdbc.Driver
db.url=jdbc:mysql://192.168.0.2/slrclub
db.user=test
db.password=test
output=/Users/oracle/Documents/workspace/images/JAR/test/
count=20
	 */
	public static Connection setupConnection() {
		if(gConn == null) {
//			Properties config = loadConfig();
//			System.out.println("config:" + config);

			String jdbc_driver = null;
			String url = null;
			String user = null;
			String password = null;

//			if (config != null) {
				jdbc_driver = "com.mysql.jdbc.Driver"; // config.getProperty("db.jdbc_driver");
				url = "jdbc:mysql://192.168.0.2/petclinic";// config.getProperty("db.url");
				user = "pc"; // config.getProperty("db.user");
				password = "pc"; // config.getProperty("db.password");
				
//				jdbc_driver = "com.mysql.jdbc.Driver"; // config.getProperty("db.jdbc_driver");
//				url = "jdbc:mysql://127.0.0.1:3306/employees";// config.getProperty("db.url");
//				user = "test_user"; // config.getProperty("db.user");
//				password = "1234"; // config.getProperty("db.password");
				
//				gOutput   = config.getProperty("output");
//				try {
//					gCount = Integer.parseInt(config.getProperty("count"));
//				} catch (Exception e) {
//					gCount = 20;
//				}
//				if(gOutput == null || gOutput.equals(""))
//					gOutput = "./output/";
//			}
			
//			if(jdbc_driver == null || url == null || user == null || password == null) {
//				return null;
//			}
			
			System.out.println("["+jdbc_driver+"]");
			try {
				Class.forName(jdbc_driver);
				System.out.println("Connecting to database...");
				gConn = DriverManager.getConnection(url, user, password);
			} catch (Exception e) {
				gConn = null;
				e.printStackTrace();
			}
		}
		
		return gConn;
	}
	
//	/**
//	 * @return the output
//	 */
//	public static String getOutput() {
//		return gOutput;
//	}

//	public static ArrayList<BestImage> queryBestImage(int limitCount) {
//		ArrayList<BestImage> bestImageList = null;
//		Connection conn = setupConnection();
//		
//		if(gCount > limitCount)
//			limitCount = gCount;
//		
//		if(conn == null)
//			return null;
//		
//		Statement stmt = null;
//		try {
//			stmt = conn.createStatement();
//			String sql = "SELECT idx, no, id, image, title FROM slrclub.slrtable WHERE (downyes is NULL OR downyes = '') AND image IS NOT NULL AND image != '' ORDER BY idx DESC LIMIT " + limitCount + ";";
//			System.out.println("sql statement:" + sql);
//			ResultSet rs = stmt.executeQuery(sql);
//
//			bestImageList = new ArrayList<BestImage>();
//			
//			// STEP 5: Extract data from result set
//			while (rs.next()) {
//				// Retrieve by column name
//				long idx = rs.getInt("idx");
//				long no = rs.getInt("no");
//				int uid = rs.getInt("id");
//				String images = rs.getString("image");
//
//				String image_array[] = images.split(Pattern.quote("##**##**"));
//				List<String> mList = new ArrayList<String>();
//
//				for(String one_image : image_array) {
//					if(one_image.startsWith("//"))
//						one_image = "http:" + one_image;
//					
//					if(one_image.startsWith("/"))
//						continue;
//					mList.add(one_image);
//				}
//				
//				int size = mList.size();
//				if(size > 0x00) {
//					BestImage data = new BestImage();
//					data.setIndex(idx);
//					data.setUrl(mList);
//					data.setCount(size);
//					data.setNo(no);
//	
//					bestImageList.add(data);
//					data = null;
//				}
//				
//			}
//			// STEP 6: Clean-up environment
//			rs.close();
//			stmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (stmt != null)
//					stmt.close();
//			} catch (SQLException se2) { }
//		}
//		System.out.println("Goodbye!");
//		return bestImageList;
//	}
//	
	
	private static String sql = "INSERT INTO mx_slrissuefree (track_no,createdate,hotissue,ontext,uid,uname,subject,issuetext) \n" +
			 "VALUES(?,?,'N','Y',?,?,?,?);";
	
	
	public static void putSlrIssueFreeData(ArrayList<OMGSlrContentsData> issue) {
		Connection conn = setupConnection();
		if(conn == null) return;

		PreparedStatement stmt = null;
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String yymmdd = format.format(now); // 20090529

		for(int i = 0; i < issue.size(); i++) {
			OMGSlrContentsData data = issue.get(i);
			try {
				if(stmt == null)
					stmt = conn.prepareStatement(sql);
				
				if(stmt == null) continue;
				
				int track_no       = data.getTrack_no();
				int uid            = data.getUid();
				String createdate  = yymmdd + " " + data.getCreatedate();
				
				String uname = data.getUname();
				String subject = StringEscapeUtils.unescapeXml(data.getSubject());
				String issuetext = StringEscapeUtils.unescapeXml(data.getIssuetext());
				
				stmt.setInt(1,    track_no);
				stmt.setString(2, createdate);
				stmt.setInt(3,    uid);
				//
				//
				stmt.setString(4, uname);
				stmt.setString(5, subject);
				stmt.setString(6, issuetext);

				// System.out.println("sql statement:" + sql);
				//int rowCount = stmt.executeUpdate(sql);
				int rowCount = stmt.executeUpdate();
				// ResultSet rs = stmt.executeQuery();
				// System.out.println("insert : " + rowCount);
				
				// stmt.close();				
				System.out.println("put no : " + track_no + ", update count : " + rowCount);
				
			} catch (SQLException e) {
				// if(stmt != null)
				System.out.println("SQL : " + stmt.toString());
				System.out.println("SQLException : " + e.toString());
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
