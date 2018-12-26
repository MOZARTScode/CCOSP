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
		System.out.println("�������ļ�Unit��" + file.getName());
	}
	
	/**
	 * ͨ��Դ�ļ������͵õ������࣬ͬʱ������Ӧ�Ĵ�����
	 */
	@Override
	public void operation() {
		// ��ȡ��׺��
		String[] temp = file.getName().split("\\.");
		String type = null;
		if (temp.length >= 2)
			type = temp[temp.length - 1];
		else
			return;
		System.out.println("�����ļ�����Ϊ��" + type);
		// ����õ��ļ��������
		if (XMLUtil.getBean(type) != null) {
			FileInfoExtration fileInfoExtration = (FileInfoExtration)XMLUtil.getBean(type);
			fileInfoExtration.dealFile(file);
		}
	}
	
}
