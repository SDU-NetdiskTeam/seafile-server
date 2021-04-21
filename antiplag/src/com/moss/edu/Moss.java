package com.oss.edu;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.io.*;

import data.plag.edu.SimData;
public class Moss {
	String url; //�ϴ��ļ��󣬷��ؽ������ȡ��url
	public static void main(String[] args) {
		System.out.println("��ѡ�����������ʽ:\n1�������ƶ�\n2����ƥ����������\n3���ۺ�ָ��");
		Moss m=new Moss();
		m.menu("mossout.txt");
	}
	void menu(String filename){
		String[] SortStyle=new String[]{"one","two","three"};
		ArrayList ResultList=new ArrayList();
		Scanner sc=new Scanner(System.in);
		int input=0;
		try {
			input=sc.nextInt();
		} catch (Exception e) {
			System.out.println("����������������룡");
			menu(filename);
		}
		if(input>=1 && input<=3){
			ResultList=analy(filename,SortStyle[input-1]);
			echoArrList(ResultList);
			}
			else{
			System.out.println("����������������룡");
		menu(filename);
		}
	}
	void echoArrList(ArrayList ArrList)
	{
		//System.out.println("��ҵ�ļ���1               ��ҵ�ļ���2          ���ƶ�   ƥ�������  �ۺ�");
		for(int i=0;i<ArrList.size();i++)
			System.out.println(ArrList.get(i));
	}
	
	
	//�����ϴ�����ļ�����ȡurl��������ҳ����ȡ��Ϣ�������ⷵ��-1���н������0���޽������1
	 public int analyMoss(String filename,float threshold, List<SimData> lists ){
			int res = -1;
		    Text t = new Text();
			File f = new File(filename);
			if (!f.exists()){//�ж��ļ��Ƿ����
				System.out.println("���ڳ�������Ŀ¼�·���"+filename+"�ļ���");
				return -1;
			}else {
				
				String[] ArrStr = t.SplitString(filename, "\n");
				url = ArrStr[ArrStr.length - 1]; //���һ����url
				if (url.matches("http://moss.stanford.edu/results/\\d+"))// ��������URL�Ƿ�ƥ��˹̹���Ľ����ҳ��ַ
				{
					Http http = new Http();
					String Result = http.Get(url);
					if (Result != "") //�ж������Ƿ���ͨ
					{
						Regex r = new Regex();
						int i,count;
					/*	Matcher m=r.CreateRegex(
								"<TR><TD><A HREF=.*?html.*?>.*?src/(.*?)\\((\\d+)%\\)</A>\\n.*?<TD><A HREF=.*?html.*?>.*?src/(.*?)\\((\\d+%)\\)</A>\\n<TD ALIGN=right>(\\d+)",
								Result);  */
						Matcher m=r.CreateRegex(
								"<TR><TD><A HREF=.*?html.*?>.*?[/|\\\\](.*?\\..*?)\\((\\d+)%\\)</A>\\n.*?<TD><A HREF=.*?html.*?>.*?[/|\\\\](.*?\\..*?)\\((\\d+)%\\)</A>\\n<TD ALIGN=right>(\\d+)",
								Result);

					
						while(m.find())
						{
						//	db.exeSql("insert into homework values('"+m.group(1)+"','"+m.group(3)+"',"+m.group(2)+","+m.group(5)+","+((Integer.parseInt(m.group(2))*Integer.parseInt(m.group(5)))/100.0)+")");	
							SimData sd = new SimData();
							sd.setFile1(m.group(1));
							sd.setFile2(m.group(3));
							float simf1 = 0.0f;
							float simf2 = 0.0f;
							float maxsim = 0.0f;
							try {
								simf1 = Float.valueOf(m.group(2)); //ȡ��һ�����ưٷ���
								String strtemp = m.group(4);
								strtemp = m.group(5);
								simf2 = Float.valueOf(m.group(4));
								maxsim = simf1>simf2?simf1:simf2;
								sd.setSimilar(maxsim); //�����������ƶ�������
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(maxsim>=threshold) //�������ޣ��ű���
							   lists.add(sd);
						}
						
					} else{
						System.out.println("���������Ƿ���ͨ��");
						return -1;
					}
				}
			}		 
		 
		if(lists.size()>0){  //������1�����
			Collections.sort(lists);  //����
			res = 0;
		}else if(lists.size()==0) { //û�з�������ֵ�����Ľ��
			res = 1;  
		}
	    return res;
	 }
	public ArrayList analy(String filename,String sortStyle)
	{
		ArrayList ResultList=new ArrayList();
		Text t = new Text();
		File f = new File(filename);
		if (!f.exists())//�ж��ļ��Ƿ����
			System.out.println("���ڳ�������Ŀ¼�·���"+filename+"�ļ���");
		else {
			String[] ArrStr = t.SplitString(filename, "\n");
			String Url = ArrStr[ArrStr.length - 1];
			if (Url.matches("http://moss.stanford.edu/results/\\d+"))// ��������URL�Ƿ�ƥ��˹̹���Ľ����ҳ��ַ
			{
				Http http = new Http();
				String Result = http.Get(Url);
				if (Result != "") //�ж������Ƿ���ͨ
				{
					Regex r = new Regex();
					int i,count;
					Matcher m=r.CreateRegex(
							"<TR><TD><A HREF=.*?html.*?>.*?src/(.*?)\\((\\d+)%\\)</A>\\n.*?<TD><A HREF=.*?html.*?>.*?src/(.*?)\\((\\d+%)\\)</A>\\n<TD ALIGN=right>(\\d+)",
							Result);
					DataBase db=new DataBase();
					if(db.Connection()){
					db.exeSql("delete from homework");
					while(m.find())
					{
						db.exeSql("insert into homework values('"+m.group(1)+"','"+m.group(3)+"',"+m.group(2)+","+m.group(5)+","+((Integer.parseInt(m.group(2))*Integer.parseInt(m.group(5)))/100.0)+")");	
					}
					ResultList=db.QuerySql("select * from "+sortStyle);
					db.close();
					}
					else
					{
						System.out.println("���ݿ��ļ�ȱʧ�������޷����У�");
					}
				} else
					System.out.println("���������Ƿ���ͨ��");
			}
		}
		return ResultList;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}