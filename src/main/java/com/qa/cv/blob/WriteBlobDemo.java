package com.qa.cv.blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author www.luv2code.com
 *
 */
public class WriteBlobDemo {


	public static void main(String[] args) throws IOException, SQLException {

		FileInputStream input = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection(
					"jdbc:sqlserver://qacserver.database.windows.net:1433;database=QAUsers;user=qacadmin@qacserver;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30",
					"qacadmin",
					"Pass_Word");

//			// 2. Prepare statement
			String sql = "update cv set cv_link=? where cv_id="+54;
			myStmt = myConn.prepareStatement(sql);
			// 3. Set parameter for resume file name
			File theFile = new File("tester.txt");
//
			System.out.println("Reading input file: " + theFile.getAbsolutePath());
			input = new FileInputStream(theFile);
			myStmt.setBinaryStream(1, input);
			
			// 4. Execute statement
			System.out.println("\nStoring resume in database: " + theFile);
			System.out.println(sql);
			
			myStmt.executeUpdate();
			
			System.out.println("\nCompleted successfully!");
			
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {			
			if (input != null) {
				input.close();
			}
			
			close(myConn, myStmt);			
		}
	}

	private static void close(Connection myConn, Statement myStmt)
			throws SQLException {

		if (myStmt != null) {
			myStmt.close();
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}

}
