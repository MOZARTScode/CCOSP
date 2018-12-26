package extractionMethod.javaFileInfoExtration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;

import main.FileInfoExtration;
import main.StartWorking;
import main.Utils;

public class JavaInfoExtraction implements FileInfoExtration {

	/**
	 * ͨ���ַ�����ȡע�� û�д���ע���е�������Ϣ
	 * 
	 * @param file
	 * @param start
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public String getCharacterOfComments(File file, int start, int length) throws IOException {
		@SuppressWarnings("resource")
		FileReader reader = new FileReader(file);
		reader.skip(start);
		char[] cbuf = new char[length];
		reader.read(cbuf, 0, length);
		return new String(cbuf).replace("\t", "").replace("/", "").replace("*", "").replaceAll("@.*\\r\\n", "")
				.replaceAll("\\r\\n\\s*", " ").trim();
	}

	// ����һ��Դ�ļ�
	public void getComments(CompilationUnit comp, File file) throws IOException {

		// ��ȡд����
		BufferedWriter out = Utils.outStream;

		// д��Դ�ļ�ע����Ϣ
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
	 * ��ȡ����������
	 * 
	 * @param sourceFile
	 * @throws IOException
	 */
	public void getDescriptionInfo(File sourceFile) throws IOException {

		// ��ȡд����
		BufferedWriter out = Utils.outStream;

		// ʹ��ת���ַ�\\
		out.write("className: " + sourceFile.getName().split("\\.")[0] + "\r\n");

		String[] packageName = sourceFile.getPath().split("\\\\");
		out.write("packageName: ");
		for (int i = 1; i < packageName.length - 1; i++)
			out.write(packageName[i] + " ");
		out.write("\r\n");

	}

	@Override
	public void dealFile(File file) {
		
		// �������txt�ļ���·��
		String temp1 = file.getPath().replace(StartWorking.ROOT_PATH, StartWorking.ROOT_PATH + "\\output");
		String outPath = temp1.split("\\.")[0]+".txt";
		System.out.println("����ļ�����·��Ϊ��" + outPath);
		// ���������
		Utils.createNewWriteStream(outPath);
		
		try {
			// ��sourceFile��ȡ����������
			getDescriptionInfo(file);
			
			// ��ȡ�������
			CompilationUnit comp = Utils.getCompilationUnit(file.getAbsolutePath());
			CommonVisitor visitor = new CommonVisitor();
			comp.accept(visitor);
			
			Utils.outStream.write("\r\n");
			
			// ��ȡע��
			getComments(comp, file);
			
			// �ر��������ɾ��·���Լ������
			Utils.closeOutWriteStream(Utils.outStream);
			Utils.outPathName = null;
			Utils.outStream = null;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
