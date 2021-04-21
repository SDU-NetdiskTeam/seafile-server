package com.moss.edu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class Text
{
	/**
	 * 
	 * @param FilePath �ļ�·��
	 * @param SplitStr �ָ��
	 * @return ���طָ����ַ�������
	 */
	String[] SplitString(String FilePath,String SplitStr){
		File f=new File(FilePath);
		String[] arrurl=null;
		if(!f.exists())//�ж��ļ��Ƿ����
			return arrurl;
		try
		{
			FileReader fr=new FileReader(f);
			char[] cbuf=new char[(int)f.length()];
			fr.read(cbuf);
			String t=new String(cbuf);
			arrurl=t.split(SplitStr);
			fr.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return arrurl;
	}
	public static void main(String[] args) {
		//��������
		Text txt=new Text();
		String[] StrArr;
		StrArr=txt.SplitString("C:\\Documents and Settings\\Administrator\\����\\1.txt", "\n");
		System.out.println(StrArr[4]);
	}
}
