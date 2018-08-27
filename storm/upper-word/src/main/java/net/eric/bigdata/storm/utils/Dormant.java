package net.eric.bigdata.storm.utils;

public class Dormant {
	public static void sleepForMillion(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// 休眠中断处理
			e.printStackTrace();
			System.err.println("休眠异常中断...");
		}
	}
	public static void sleepForSeconds(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// 休眠中断处理
			e.printStackTrace();
			System.err.println("休眠异常中断...");
		}
	}
}
