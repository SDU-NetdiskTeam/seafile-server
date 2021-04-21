package com.moss.edu;
import java.util.regex.*;
public class Regex
{
	Pattern pt=null;
	Matcher match=null;
	/**
	 * 
	 * @param RegString ������ʽ
	 * @param SearchString	Ҫƥ����ı�
	 */
	Matcher CreateRegex(String RegString,String SearchString)
	{
		pt=Pattern.compile(RegString);
		match=pt.matcher(SearchString);
		return match;
	}
	/**
	 * 
	 * @param index ƥ����ı���λ��
	 * @return �����ı�
	 */
	String GetString(int index)
	{		
			int count=0;
			while(match.find())
			{
				if(index==count)
				{
					return match.group();
					}
				count++;
			}
			return "";
	}
	/**
	 * 
	 * @param index ƥ����ı�λ��
	 * @param subIndex ƥ������ı�λ��
	 * @return �������ı�
	 */
	String GetSubString(int index,int subIndex)
	{
		int count=0;
		while(match.find())
		{
			if(index==count)
			{
				return match.group(subIndex);
				}
			count++;
		}
		return "";
	}
	/**
	 * 
	 * @return ����ƥ�������
	 */
	int GetCount()
	{
		int count=0;
		while(match.find())//�ж��Ƿ���ƥ��
		{
			count++;
		}
		match=match.reset();
		return count;
	}
	public static void main(String[] args)
	{
		//��������
		Regex r=new Regex();
		r.CreateRegex("(\\d+)", "ass123456asd1");
		System.out.println(r.GetString(1));
		System.out.println(r.GetCount());
		
		/*
		 * ƥ��˹̹��ϵͳ���������
		 * <TR><TD><A HREF=.*?html.*?>.*?src/(.*?)\((\d+%)\)</A>\n.*?<TD><A HREF=.*?html.*?>.*?src/(.*?)\((\d+%)\)</A>\n<TD ALIGN=right>(\d+)
		 */
	}
}
