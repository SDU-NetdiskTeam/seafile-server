package com.moss.edu;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
public class DataBase {
	Statement stmt=null;
	 Connection conn=null;
	 boolean Connection() {
		 File f=new File("infor.mdb");
		 if(!f.exists())
		 {
			return false; 
		 }
		 else{
		  try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  String dburl ="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=infor.mdb";
			  conn=DriverManager.getConnection(dburl);
			   stmt=conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  return true;
		 }
	}
	 ArrayList QuerySql(String SqlStr)
	 {
		  ResultSet rs;
		  ArrayList al=new ArrayList();
		try {
			rs = stmt.executeQuery(SqlStr);
			al.add("��ҵ�ļ���1               ��ҵ�ļ���2          ���ƶ�   ƥ�������  �ۺ�");
			while(rs.next()){
				   al.add(rs.getString(1)+" "+rs.getString(2)+"	"+rs.getString(3)+"    "+rs.getString(4)+"   "+rs.getString(5));
				  }
				  rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return al;
	 }
	 void exeSql(String SqlStr){
		 try {
			stmt.execute(SqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 void close(){
		 try {
			stmt.close();
			 conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		DataBase db=new DataBase();
		db.Connection();
		System.out.println("��ҵ�ļ���1               ��ҵ�ļ���2          ���ƶ�   ƥ�������  �ۺ�");
		ArrayList al=db.QuerySql("select * from two");
		for(int i=0;i<al.size();i++)
			System.out.println(al.get(i));
		db.close();
	}
}
