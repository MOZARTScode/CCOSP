package javaRealization;

import java.io.BufferedWriter;
import java.io.IOException;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ImportDeclaration;

public class CommonVisitor extends ASTVisitor{
	
	@Override
	public boolean visit(ImportDeclaration  node) {
		// 获取文件的写入流
		try {
			BufferedWriter out = Utils.outStream;
			out.write(node.getName() + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
}
