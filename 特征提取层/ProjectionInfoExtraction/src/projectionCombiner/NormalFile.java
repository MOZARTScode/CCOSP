package projectionCombiner;

import java.io.File;

import main.FileInfoExtration;
import main.XMLUtil;

public class NormalFile implements Unit{

	File file;
	
	public NormalFile() {
	}
	
	public NormalFile(File file) {
		this.file = file;
		System.out.println("创建了文件Unit：" + file.getName());
	}
	
	/**
	 * 通过源文件的类型得到处理类，同时调用相应的处理方法
	 */
	@Override
	public void operation() {
		// 获取后缀名
		String[] temp = file.getName().split("\\.");
		String type = null;
		if (temp.length >= 2)
			type = temp[temp.length - 1];
		else
			return;
		System.out.println("处理文件类型为：" + type);
		// 反射得到文件处理对象
		if (XMLUtil.getBean(type) != null) {
			FileInfoExtration fileInfoExtration = (FileInfoExtration)XMLUtil.getBean(type);
			fileInfoExtration.dealFile(file);
		}
	}
	
}
