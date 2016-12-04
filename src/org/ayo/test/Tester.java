package org.ayo.test;

public abstract class Tester {
	private String name;

	public Tester(String name) {
		this.name = name;
	}

	public void runTest() {
		System.out.println(name + ": ");
		try {
			long start = System.nanoTime();
			System.out.println("------begin: " + start + "------");
			test();
			double duration = System.nanoTime() - start;
			//System.out.println("------耗时: " + duration / 1.0e9 + "-------");
			System.out.format("------耗时: %.2fs-------\n", duration / 1.0e9);
			System.out.format("------耗时: %fms-------\n", duration / 1.0e6);
			System.out.format("------耗时: %fus-------\n", duration / 1000);
			System.out.format("------耗时: %fns-------\n", duration);
			System.out.println();
			System.out.println();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public abstract void test();
	
	public static void main(String[] args) {
		new Tester("测试System.out.pringln的性能") {
			
			@Override
			public void test(){
				System.out.println("打印了");
			}
		}.runTest();
		
		new Tester("测试Thread.sleep的耗时") {
			
			@Override
			public void test(){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.runTest();
	}


}
