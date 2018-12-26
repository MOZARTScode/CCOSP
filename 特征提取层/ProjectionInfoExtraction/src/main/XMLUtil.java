package main;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtil {

	public static Object getBean(String type) {
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;							
            doc = builder.parse(new File("src//resource//fileType.xml")); 
		
            //获取包含类名的文本结点
            NodeList n;
            Node classNode;
            n = doc.getElementsByTagName(type);
            classNode = n.item(0).getFirstChild();
            String cName=classNode.getNodeValue();
            
            //通过类名生成实例对象并将其返回
            Class c=Class.forName(cName);
            Object obj=c.newInstance();
            return obj;
        }   
        catch(Exception e) {
        	System.out.println("不处理类型：" + type);
            return null;
        }
    }
	
	public static void main(String[] args) {
		getBean("gitignore");
		getBean("xml");
		System.out.println(getBean("java"));
	}
}
