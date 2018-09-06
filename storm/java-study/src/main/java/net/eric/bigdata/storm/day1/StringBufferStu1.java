package net.eric.bigdata.storm.day1;

public class StringBufferStu1 {

	public static void main(String[] args) throws Exception {
		/*
		 * // 使用无参构造函数生成字符缓冲区，并获得初始容量 // StringBuffer sb = new StringBuffer();
		 * //线程安全的，效率低 StringBuilder sb = new StringBuilder(); //线程非安全的，效率高，推荐使用
		 * System.out.println("字符缓冲区已有字符个数："+sb.length());
		 * System.out.println("初始化字符缓冲区容量："+sb.capacity());
		 * 
		 * // 使用字符缓存增加的方法插入字符串，并获得字符长度、缓存容量信息 sb.append("abcde小明fghij");
		 * sb.append("123456"); System.out.println("字符缓冲区已有字符个数："+sb.length());
		 * //System.out.println("字符缓冲区已有字符个数超过初始已有容量么："+(sb.length() >= sb.capacity()));
		 * System.out.println("追加字符后，字符长度超过字符缓冲区容量，则容量自动增加1倍+2容量："+sb.capacity());
		 * 
		 * System.out.println("字符串为："+sb.toString());
		 * 
		 * //插入字符到现有字符缓存中 sb.insert(5, "我叫张");
		 * System.out.println("字符串为："+sb.toString());
		 * 
		 * //修改字符，大于等于start，小于end sb.replace(8, 10, "英");
		 * System.out.println("字符串为："+sb.toString());
		 * 
		 * //删除数据 sb.delete(14, 50); System.out.println("字符串为："+sb.toString());
		 */

		// 学习system类方法
/*		System.out.println("========================java.lang.system=======================");
		String[] sourceStr = { "hello ", "welcome ", "to ", "java ", "!" };
		String[] destStr = new String[10];

		System.arraycopy(sourceStr, 1, destStr, 1, 3);

		System.out.println("目标字符数组：" + Arrays.toString(destStr));
		
		 * for (int i = 0; i < destStr.length; i++) { System.out.println(""+destStr[i]);
		 * }
		 
		System.out.println("当前时间(1970年1月1日至今)：" + System.currentTimeMillis());

		
		 * Properties prop = System.getProperties(); prop.list(System.out);
		 * 
		 * System.out.println("环境变量path："+System.getenv("path"));
		 * System.out.println("系统参数os.name为："+prop.getProperty("os.name"));
		 * System.out.println("系统参数sun.boot.class.path为："+prop.getProperty(
		 * "sun.boot.class.path"));
		 
		System.out.println("环境变量os.name为：" + System.getProperty("os.name"));

		for (int i = 0; i < 4; i++) {
			new Persion("小明" + i);
		}
		System.gc();*/
		
		//学习runtime类，该类代表了应用程序运行环境
/*		Runtime runTime = Runtime.getRuntime();
		System.out.println("返回Java虚拟机中的可用内存量："+runTime.freeMemory()/1024/1024+"MB");
		System.out.println("返回Java虚拟机将尝试使用的最大内存量："+runTime.maxMemory()/1024/1024+"MB");
		System.out.println("返回Java虚拟机中的内存总量："+runTime.totalMemory()/1024/1024+"MB");
		try {
			Process proc = runTime.exec("C:\\Program Files\\VanDyke Software\\SecureCRT\\SecureCRT.exe");
			Thread.sleep(3000);  //让程序停止3秒
			proc.destroy();      //销毁进程
		} catch (IOException e) {
			// 调用执行程序失败，抛出异常
			e.printStackTrace();
		}*/
		
		//学习日期类 Date/Calendar,计算类 Math，日期格式化类 SimpleDateFormat
/*		Date date = new Date();
		System.out.println("当前日期(完整格式)："+date);
		
		Calendar calendar = Calendar.getInstance();
		System.out.println("当前日期(年份)："+calendar.get(Calendar.YEAR));
		System.out.println("当前日期(月份)："+(calendar.get(Calendar.MONTH)+1)); //国外从0开始计月份
		System.out.println("当前日期(日期)："+calendar.get(Calendar.DATE));
		
		System.out.println("当前日期(小时)："+calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println("当前日期(分钟)："+calendar.get(Calendar.MINUTE));
		System.out.println("当前日期(秒)："+calendar.get(Calendar.SECOND));
		
		//System.out.println("当前日期："+calendar);
		
		//日期格式化类
		SimpleDateFormat simpleDate = new SimpleDateFormat("YYYY年MM月dd日 HH时mm分ss秒");
		SimpleDateFormat simpleDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String time = simpleDate.format(date);
		System.out.println("当前日期："+time);
		
		String birthday = "2018-09-05 16:42:55"; //要与SimpleDateFormat("yyyy-MM-dd HH:mm:ss")格式一致
		System.out.println("生日为："+simpleDate1.parse(birthday));*/
		
		System.out.println("绝对值："+Math.abs(-129));
		System.out.println("返回较大值："+Math.max(234, 213));
		
	}
}
