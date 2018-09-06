package net.eric.bigdata.storm.day1;

public class Persion {
	private String name;

	public Persion(String name) {
		this.name = name;
		//System.out.println(name + "对象生成了！");
	}

	@Override
	protected void finalize() throws Throwable {
		// 重写object类的finalize方法
		super.finalize();
		System.out.println("对象" + name + "回收了！");
	}
}
