package main;

import java.io.File;

/**
 * 所有的源文件处理都需要实现此接口：实现各自提取源文件信息的方法
 * @author 廖智勇
 *
 */
public interface FileInfoExtration {
	
	/*
	 * 文件处理流程（java为例）：
	 * 	！！获取输出流；
	 * 	写入包名和类名：通过file
	 * 	获取导入包名：通过file
	 * 	获取注释：通过file
	 * 	关闭同时清空输出流
	 */
	public void dealFile(File file);
	
}
