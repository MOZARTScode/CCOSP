package javaRealization;

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
	 * ��ȡ��ĳ���ļ���д����
	 * �ļ���·��ΪpathName
	 * @return
	 * @throws IOException 
	 */
	public static BufferedWriter getWriteStream() throws IOException {
		File file = new File(Utils.outPathName.toString());
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		return out;
	}
	
	/**
	 * �ر�д����
	 * @param out
	 * @throws IOException
	 */
	public static void closeOutWriteStream(BufferedWriter out) throws IOException {
		out.flush();
		out.close();
	}
	
	/**
	 * �õ�sourceFiles��������Դ�ļ�
	 * @return
	 */
	public static File[] getSourceFileList() {
		String path = "src/sourceFiles";
		File sourcefile = new File(path);
		return sourcefile.listFiles();
	}
}
