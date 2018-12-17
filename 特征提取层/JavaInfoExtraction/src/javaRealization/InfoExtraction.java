package javaRealization;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.rmi.CORBA.Util;

import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class InfoExtraction {

	/**
	 * 通过字符流读取注释
	 * 没有处理注释中的冗余信息
	 * @param file
	 * @param start
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public static String getCharacterOfComments(File file, int start, int length) throws IOException {
		@SuppressWarnings("resource")
		FileReader reader = new FileReader(file);
		reader.skip(start);
		char[] cbuf = new char[length];
		reader.read(cbuf, 0, length);
		return new String(cbuf).replace("\t", "").replace("/", "").replace("*", "").replaceAll("@.*\\r\\n", "")
				.replaceAll("\\r\\n\\s*", " ").trim();
	}


	// 处理一个源文件
	public static void getComments(CompilationUnit comp, File file) throws IOException {

		// 获取写入流
		BufferedWriter out = Utils.outStream;

		// 写入源文件注释信息
		@SuppressWarnings("unchecked")
		List<Comment> list = comp.getCommentList();
		for (Comment comment : list) {
			int start = comment.getStartPosition();
			int len = comment.getLength();
			out.write(getCharacterOfComments(file, start, len));
			out.write("\r\n");
		}
	}
	
		
	/**
	 * 获取包名和类名
	 * @param sourceFile
	 * @throws IOException
	 */
	public static void getDescriptionInfo(File sourceFile) throws IOException {
		
		// 获取写入流
		BufferedWriter out = Utils.outStream;
		
		// 使用转义字符\\
		out.write("className: " + sourceFile.getName().split("\\.")[0] + "\r\n");
		
		String[] packageName = sourceFile.getPath().split("\\\\");
		out.write("packageName: ");
		for (int i = 1; i < packageName.length - 1; i++)
			out.write(packageName[i] + " ");
		out.write("\r\n");

	}
	

	public static void main(String[] args) throws Exception {

		File[] sourceList = Utils.getSourceFileList();
		for (File soucefile : sourceList) {
			// 创建输出文档，设置输出流
			String outpath = "src/resultFiles/" + soucefile.getName().split("\\.")[0] + ".txt";
			Utils.outPathName = new StringBuffer();
			Utils.outPathName.append(outpath);
			File outfile = new File(outpath);
			if (outfile.exists())
				outfile.delete();
			outfile.createNewFile();
			Utils.outStream = Utils.getWriteStream();
			
			// 从sourceFile获取包名、类名
			getDescriptionInfo(soucefile);
			
			// 获取导入包名
			CompilationUnit comp = Utils.getCompilationUnit("src/sourceFiles/" + soucefile.getName());
			CommonVisitor visitor = new CommonVisitor();
			comp.accept(visitor);
			
			Utils.outStream.write("\r\n");
			
			// 获取注释
			getComments(comp, soucefile);
			
			// 关闭输出流、删除路径以及输出流
			Utils.closeOutWriteStream(Utils.outStream);
			Utils.outPathName = null;
			Utils.outStream = null;
		}
		
	}

}

