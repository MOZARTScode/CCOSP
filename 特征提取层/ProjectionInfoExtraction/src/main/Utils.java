package main;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Utils {
	
	public static StringBuffer outPathName;
	public static BufferedWriter outStream;

	/**
	 * get compilation unit of source code
	 * 
	 * @param javaFilePath
	 * @return CompilationUnit
	 */
	public static CompilationUnit getCompilationUnit(String javaFilePath) {
		byte[] input = null;
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));
			input = new byte[bufferedInputStream.available()];
			bufferedInputStream.read(input);
			bufferedInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("deprecation")
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(new String(input).toCharArray());
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);

		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));

		return result;
	}

	/**
	 * 获取对某个文件的写入流
	 * 文件的路径为pathName
	 * @return
	 * @throws IOException 
	 */
	public static BufferedWriter getWriteStream() throws IOException {
		File file = new File(Utils.outPathName.toString());
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		return out;
	}
	
	/**
	 * 关闭写入流
	 * @param out
	 * @throws IOException
	 */
	public static void closeOutWriteStream(BufferedWriter out) throws IOException {
		out.flush();
		out.close();
	}
	
	/**
	 * 通过路径创建新的输出流
	 * @param outpath
	 */
	public static void createNewWriteStream(String outpath) {
		outPathName = new StringBuffer();
		outPathName.append(outpath);
		File outFile = new File(outpath);
		if (outFile.exists())
			outFile.delete();
		try {
			// 需要判断文件夹路径是否存在，否则要建立文件夹目录
			if (!outFile.getParentFile().exists())
				new File(outFile.getParent()).mkdirs();
			
			outFile.createNewFile();
			outStream = Utils.getWriteStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
