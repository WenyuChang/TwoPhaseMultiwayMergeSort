package Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class FileOperator {
	
	private String FILE_PATH = "testfile.txt";
	private String FILE_PATH_SORTED = "testfile(sorted).txt";
	private int ITEM_COUNT = 1000;
	private int REPEAT_TIMES = 1;
	private File oriFile;
	
	public FileOperator(int itemCount)
	{
		ITEM_COUNT = itemCount;
	}
	
	public void Sort() throws IOException
	{
		System.out.println("######## Now Starting The System(RAM Sort)... ########");
 		long startCreateFile = System.currentTimeMillis();
		
 		FileInputStream fr = new FileInputStream(FILE_PATH);
 		BufferedInputStream bin = new BufferedInputStream(fr,4096);
 		DataInputStream din=new DataInputStream(fr);
		
		int[] items = new int[ITEM_COUNT];
		for(int i=0;i<ITEM_COUNT;i++)
		{
			items[i] = Integer.parseInt(din.readLine());
		}
		
		System.out.println("  @@ Now Starting To Sort... @@");
		long startSort = System.currentTimeMillis();
		Arrays.sort(items);
		long endSort = System.currentTimeMillis();
		System.out.println("  @@ End The Sort. Costs:" + (endSort - startSort) + "ms @@");
		
		long endCreateFile = System.currentTimeMillis();
		System.out.println("######## End The System(RAM Sort). Costs:" + (endCreateFile - startCreateFile) + "ms ######## ");
	}
	
	public void createFile() throws IOException
	{
		File file = new File("");
		//File file = new File(FILE_PATH);
		if(!file.exists())
		{
			FileOutputStream fw = new FileOutputStream(FILE_PATH);
			BufferedOutputStream bout = new BufferedOutputStream(fw);
			DataOutputStream dout=new DataOutputStream(bout);
			System.out.println("******************************************************************");
			System.out.println("  @@ Now Starting To Create File¡­¡­ @@");
	               
	        long startCreateFile = System.currentTimeMillis();
			for (int i = 0; i < ITEM_COUNT; i++)
			{
				int ger = (int) (Math.random() * 10000000 + 1);
				ger = ger < 0 ? -ger : ger;
				String item = "";
				for(int j=0;j<REPEAT_TIMES;j++)
				{
					item += String.valueOf(ger);
				}
				dout.writeBytes(item+"\r\n");
			}
			
			dout.flush();
			bout.flush();
			fw.flush();
			
			dout.close();
			bout.close();
			fw.close();
			long endCreateFile = System.currentTimeMillis();
			System.out.println("  @@ End, Creating File. Costs:" + (endCreateFile - startCreateFile) + "ms @@");
			System.out.println("******************************************************************\n");
			
			oriFile = new File(FILE_PATH);
		}
		else
		{
			System.out.println("File Exists...");
			System.out.println("*************************************************");
			oriFile = new File(FILE_PATH);
		}
	}
	
	public void File_Split(int tmpFileCount) throws IOException
	{
		if(oriFile != null)
		{
			int itemCount = ITEM_COUNT/tmpFileCount;
			FileInputStream fr = new FileInputStream(FILE_PATH);
	 		BufferedInputStream bin = new BufferedInputStream(fr,4096);
	 		DataInputStream din=new DataInputStream(fr);
			
	 		int fileCount = 1;
	 		int tmpItemCount = 0;
	 		long[] buffer =  new long[itemCount];
	 		
	 		System.out.println("  @@ Now Starting To Split And Sort The File... @@");
	 		long startCreateFile = System.currentTimeMillis();
	 		for(int k=1;k<=ITEM_COUNT;k++)
	 		{
	 			buffer[tmpItemCount++] = Integer.parseInt(din.readLine());
	 			if(k%itemCount==0)
	 			{
	 				Arrays.sort(buffer);
	 				tmpItemCount = 0;
	 				
	 				File tmpFile = new File("tempFile\\tmp_" + fileCount + ".txt");
	 				FileOutputStream fw = new FileOutputStream(tmpFile);
	 				BufferedOutputStream bout = new BufferedOutputStream(fw);
	 				DataOutputStream dout=new DataOutputStream(bout);
	 				
	 				for(int j=0;j<itemCount;j++)
	 				{
	 					String item_tmp = String.valueOf(buffer[j]);
	 					dout.writeBytes(item_tmp+"\r\n");
	 				}
	 				fileCount++;
	 				
	 				dout.flush();
	 				bout.flush();
	 				fw.flush();
	 				fw.close();
	 				bout.close();
	 				dout.close();
	 			}
	 		}
	 		long endCreateFile = System.currentTimeMillis();
			System.out.println("  @@ End, Splitting File. Costs:" + (endCreateFile - startCreateFile) + "ms @@");
			System.out.println("*************************************************");
		}
	}
	
	public boolean mergeSort(int tmpFileCount)
	{
		File[] fileList = new File[tmpFileCount];
		FileInputStream[] fr = new FileInputStream[tmpFileCount];
 		BufferedInputStream[] bin = new BufferedInputStream[tmpFileCount];
 		DataInputStream[] din = new DataInputStream[tmpFileCount];
 		int[] items =  new int[tmpFileCount];
		for(int i=0;i<tmpFileCount;i++)
		{
			try{
				fileList[i] = new File("tempFile\\tmp_" + String.valueOf(i+1) + ".txt");
				if(!fileList[i].exists())
					return false;
				fr[i] = new FileInputStream(fileList[i]);
		 		bin[i] = new BufferedInputStream(fr[i],4096);
		 		din[i] =new DataInputStream(fr[i]);
		 		items[i] = Integer.parseInt(din[i].readLine());
				
			}catch(Exception e){
				return false;
			}
		}
		
		try{
			FileOutputStream fw = new FileOutputStream(FILE_PATH_SORTED);
			BufferedOutputStream bout = new BufferedOutputStream(fw);
			DataOutputStream dout=new DataOutputStream(bout);
			
			System.out.println("  @@ Now Starting To Merge File¡­¡­ @@");
			long startCreateFile = System.currentTimeMillis();
			for(int k=0;k<ITEM_COUNT;k++)
			{
				int minItem = items[0];
				int minItemIndex = 0;
				for(int j=1;j<tmpFileCount;j++)
				{
					if(items[j]<minItem)
					{
						minItem = items[j];
						minItemIndex = j;
					}
				}
				
				String item_tmp = String.valueOf(items[minItemIndex]);
				dout.writeBytes(item_tmp+"\r\n");
				
				String nextItem = din[minItemIndex].readLine();
				if(nextItem!=null)
					items[minItemIndex] = Integer.parseInt(nextItem);
				else
					items[minItemIndex] = Integer.MAX_VALUE;
			}
			dout.flush();
			bout.flush();
			fw.flush();
			fw.close();
			bout.close();
			dout.close();
			
			long endCreateFile = System.currentTimeMillis();
			System.out.println("  @@ End, Merging File. Costs:" + (endCreateFile - startCreateFile) + "ms @@");
			System.out.println("*************************************************");
		}catch(Exception e){
			return false;
		}
		return true;
	}
}
