package projectionCombiner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// �����ģʽ���֣��ܹ��Լ����������﷨��
public class NormalFolder implements Unit{
	
	File folder;
	
	List<Unit> unitList = new ArrayList<>();
	
	public NormalFolder() {
		// TODO Auto-generated constructor stub
	}
	
	public NormalFolder(File folder) {
		this.folder = folder;
		for (File f : folder.listFiles()) {
			if (f.isDirectory())
				unitList.add(new NormalFolder(f));
			if (f.isFile())
				unitList.add(new NormalFile(f));
		}
		System.out.println("�������ļ���Unit��" + folder.getName());
	}

	@Override
	public void operation() {
		unitList.stream().forEach(unit -> {
			unit.operation();
		});
	}

}
