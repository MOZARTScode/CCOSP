package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.internal.registry.Handle;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import projectionCombiner.NormalFolder;

/**
 * 项目启动类： 1. 读取配置文件 2. 对待处理项目进行文件读入 3. 建立项目文件处理Unit树：判断文件的类型 
 * 4.启动最外层Unit的operation函数
 * 
 * @author 廖智勇
 *
 */
public class StartWorking {

	public final static String ROOT_PATH = "D:\\大三文件\\CCOSP\\资源文档\\AllData";
	public static File processonFile;

	public static void main(String[] args) {
//		startExtract();
		
		String path = "D:\\大三文件\\CCOSP\\资源文档\\AllData\\output\\java";
		dealProjectSet(path);
	}

	
	/**
	 * 开启提取工作！
	 */
	public static void startExtract() {
		File rootFile = new File(ROOT_PATH);
		NormalFolder root = new NormalFolder(rootFile);
		System.out.println("----------------------------------------");
		root.operation();
	}
	

	/**
	 * 将某个文件夹下面的zip文档全部解压，同时删除相应zip文件
	 * @param path
	 */
	public static void unzipInFolder(String path) {
		File[] fileList = new File(path).listFiles();

		for (File file : fileList) {
			if (file.isFile()) {
				unzip(file, path+"\\"+file.getName().split("\\.")[0]);
				System.out.println(file.getName() + "解压完成！");
				// 删除掉归档文件
				file.delete();
				System.out.println(file.getName() + "删除完成！");
			}
		}
	}

	public static void unzip(File zipFile, String outFilePath) {
		try {
			ZipFile zFile = new ZipFile(zipFile);
			if (!zFile.isValidZipFile()) {
				throw new ZipException("压缩文件不合法,可能被损坏.");
			}
			File outFile = new File(outFilePath);
			if (outFile.isDirectory() && !outFile.exists())
				outFile.mkdir();
			zFile.extractAll(outFilePath);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * 处理一个项目集合文件夹
	 * @param projectsPath
	 */
	public static void dealProjectSet(String projectsPath) {
		for (File file : new File(projectsPath).listFiles()) {
			String path = file.getAbsolutePath();
			processonFile = new File(path);
			integration(path);
			// 删除掉目录下的目录
			for (File tempFile : processonFile.listFiles()) {
				if (tempFile.isDirectory()) {
					deleteDir(tempFile);
					System.out.println("目录已删除！");
				}
			}
		}
	}
	
	
	public static FileInputStream fileInput = null;
	public static DataInputStream dataInput = null;
	public static FileOutputStream fileOutput = null;
	public static DataOutputStream dataOutput = null;
	
	/**
	 * 将path某个目录下的所有目录全部去掉，文件全部放在该目录下
	 * 使用递归实现
	 * @param path
	 */
	public static void integration(String path) {
		File file = new File(path);
		if (file.exists()){
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹为空");
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory())
						integration(file2.getAbsolutePath());
					else {
						// 是文件，需要复制到目标目录下
						File targetFile = new File(processonFile.getAbsolutePath()+"\\"+file2.getName());
						try {
							targetFile.createNewFile();
							
							fileInput = new FileInputStream(file2);
							dataInput = new DataInputStream(fileInput);
							fileOutput = new FileOutputStream(targetFile);
							dataOutput = new DataOutputStream(fileOutput);
							int temp = 0;
							while ((temp = dataInput.read()) != -1) {
								dataOutput.write(temp);
							}
							dataInput.close();
							dataOutput.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * 递归删除文件夹
	 * @param dir
	 */
	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.isDirectory())
					deleteDir(file);
				else
					file.delete();
			}
		} else
			dir.delete();
		dir.delete();
	}
}
