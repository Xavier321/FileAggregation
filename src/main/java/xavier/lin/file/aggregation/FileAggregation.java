package xavier.lin.file.aggregation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class FileAggregation {
	
	public static String aggregationFileName;

	public static void main(String[] args) {

		String folderPath;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Folder name (EX: pic or D:/pic)：");
		folderPath = scanner.next();
		// getFileList("D:/eclipse_osgi/OSGI_EZB/Karaf-My-Test/JavaSE-Test/pic");
		// getFileList("pic1");
		// new
		// File("D:/eclipse_osgi/OSGI_EZB/Karaf-My-Test/JavaSE-Test/pic/image(0).php").renameTo(
		// new
		// File("D:/eclipse_osgi/OSGI_EZB/Karaf-My-Test/JavaSE-Test/pic/001.jpg"));
		// getFileList2(folderPath);
		//getFileList3("D:/eclipse_osgi/OSGI_EZB/Karaf-My-Test/JavaSE-Test/FileAggregation/com");
		aggregationFileName = folderPath + ".txt";
		getFileList3(folderPath);
		System.out.println("File Aggregation OK");

	}

	/**
	 * 輸入資料夾的路徑, 顯示得該資料夾下的所有檔案
	 * 
	 * @param String
	 *            folderPath
	 * @author Lupin
	 **/
	public static void getFileList(String folderPath) {
		// String folderPath = "C:\\";//資料夾路徑
		StringBuffer fileList = new StringBuffer();
		try {
			File folder = new File(folderPath);
			String[] list = folder.list();
			for (int i = 0; i < list.length; i++) {
				fileList.append(list[i]).append(", ");
			}
		} catch (Exception e) {
			System.out.println("'" + folderPath + "'此資料夾不存在");
		}
		System.out.println(fileList);
	}

	public static void getFileList2(String folderPath) {
		// String folderPath = "C:\\";//資料夾路徑
		String[] list = null;
		try {
			File folder = new File(folderPath);
			list = folder.list();
			for (int i = 0; i < list.length; i++) {
				// fileList.append(list[i]).append(", ");
				if (i < 10) {
					new File(folderPath + "/" + list[i]).renameTo(new File(
							folderPath + "/" + "00" + i + ".jpg"));
				} else if (i < 100) {
					new File(folderPath + "/" + list[i]).renameTo(new File(
							folderPath + "/" + "0" + i + ".jpg"));
				} else {
					new File(folderPath + "/" + list[i]).renameTo(new File(
							folderPath + "/" + i + ".jpg"));
				}
			}
		} catch (Exception e) {
			System.out.println("'" + folderPath + "'此資料夾不存在");
		}
	}

	public static void getFileList3(String folderPath) {
		File a = new File(folderPath);

		String[] filenames;
		String fullpath = a.getAbsolutePath();

		if (a.isDirectory()) {
			filenames = a.list();
			for (int i = 0; i < filenames.length; i++) {
				File tempFile = new File(fullpath + "\\" + filenames[i]);
				if (tempFile.isDirectory()) {
					System.out.println("目錄:" + fullpath + "\\" + filenames[i]);
					getFileList3(fullpath + "\\" + filenames[i]);
				} else {
					System.out.println("檔案:" + filenames[i]);
					try {
						//copyFileUsingFileChannels(fullpath + "\\" + filenames[i],
						//		"D:/eclipse_osgi/OSGI_EZB/Karaf-My-Test/JavaSE-Test/FileAggregation/test.txt");
						copyFileUsingFileChannels(filenames[i], aggregationFileName, fullpath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("[" + a + "] 不是目錄");
		}
	}

	private static void copyFileUsingFileChannels(String sourcePath,
			String destPath, String fullPath) throws IOException {
		
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./" + destPath, true)));
	    out.println("");
	    out.println("");
	    out.println("////////////////////////////////////////////////////////////////////////");
	    out.println(fullPath + "\\" + sourcePath);
	    out.close();
		
		File source = new File(fullPath + "\\" + sourcePath);
		File dest = new File("./" + destPath);
		//File spaceTxt = new File("./" + "space.txt");
		
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		//FileChannel spaceChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest, true).getChannel();
			//spaceChannel = new FileInputStream(spaceTxt).getChannel();
			outputChannel.transferFrom(inputChannel, outputChannel.size(), inputChannel.size());
			//outputChannel.transferFrom(spaceChannel, outputChannel.size(), spaceChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
			//spaceChannel.close();
		}
	}

}
