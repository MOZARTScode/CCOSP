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
 * ��Ŀ�����ࣺ 1. ��ȡ�����ļ� 2. �Դ�������Ŀ�����ļ����� 3. ������Ŀ�ļ�����Unit�����ж��ļ������� 
 * 4.���������Unit��operation����
 * 
 * @author ������
 *
 */
public class StartWorking {

	public final static String ROOT_PATH = "D:\\�����ļ�\\CCOSP\\��Դ�ĵ�\\AllData";
	public static File processonFile;

	public static void main(String[] args) {
//		startExtract();
		
		String path = "D:\\�����ļ�\\CCOSP\\��Դ�ĵ�\\AllData\\output\\java";
		dealProjectSet(path);
	}

	
	/**
	 * ������ȡ������
	 */
	public static void startExtract() {
		File rootFile = new File(ROOT_PATH);
		NormalFolder root = new NormalFolder(rootFile);
		System.out.println("----------------------------------------");
		root.operation();
	}
	

	/**
	 * ��ĳ���ļ��������zip�ĵ�ȫ����ѹ��ͬʱɾ����Ӧzip�ļ�
	 * @param path
	 */
	public static void unzipInFolder(String path) {
		File[] fileList = new File(path).listFiles();

		for (File file : fileList) {
			if (file.isFile()) {
				unzip(file, path+"\\"+file.getName().split("\\.")[0]);
				System.out.println(file.getName() + "��ѹ��ɣ�");
				// ɾ�����鵵�ļ�
				file.delete();
				System.out.println(file.getName() + "ɾ����ɣ�");
			}
		}
	}

	public static void unzip(File zipFile, String outFilePath) {
		try {
			ZipFile zFile = new ZipFile(zipFile);
			if (!zFile.isValidZipFile()) {
				throw new ZipException("ѹ���ļ����Ϸ�,���ܱ���.");
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
	 * ����һ����Ŀ�����ļ���
	 * @param projectsPath
	 */
	public static void dealProjectSet(String projectsPath) {
		for (File file : new File(projectsPath).listFiles()) {
			String path = file.getAbsolutePath();
			processonFile = new File(path);
			integration(path);
			// ɾ����Ŀ¼�µ�Ŀ¼
			for (File tempFile : processonFile.listFiles()) {
				if (tempFile.isDirectory()) {
					deleteDir(tempFile);
					System.out.println("Ŀ¼��ɾ����");
				}
			}
		}
	}
	
	
	public static FileInputStream fileInput = null;
	public static DataInputStream dataInput = null;
	public static FileOutputStream fileOutput = null;
	public static DataOutputStream dataOutput = null;
	
	/**
	 * ��pathĳ��Ŀ¼�µ�����Ŀ¼ȫ��ȥ�����ļ�ȫ�����ڸ�Ŀ¼��
	 * ʹ�õݹ�ʵ��
	 * @param path
	 */
	public static void integration(String path) {
		File file = new File(path);
		if (file.exists()){
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("�ļ���Ϊ��");
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory())
						integration(file2.getAbsolutePath());
					else {
						// ���ļ�����Ҫ���Ƶ�Ŀ��Ŀ¼��
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
	 * �ݹ�ɾ���ļ���
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
