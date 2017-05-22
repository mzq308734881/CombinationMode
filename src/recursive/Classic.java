import java.util.ArrayList;
import java.util.List;

public class Classic extends Equipment {

	private int count=1;  //起始值为1
	private List<Equipment> list = new ArrayList<Equipment>();

	public void add(Equipment equipment) {
		list.add(equipment);
	}

	@Override
	public void fun() {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			((Equipment) list.get(i)).fun();
		}
	}

	@Override
	public double getCount() {
		// TODO 递归计算阶乘
		for (int i = 0; i < list.size(); i++) {
			count *= ((Equipment) list.get(i)).getCount();
		}
		return count;
	}

}
