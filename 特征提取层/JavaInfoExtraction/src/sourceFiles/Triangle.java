package sourceFiles;

import randoop.org.checkerframework.common.reflection.qual.GetClass;

public class Triangle implements Cloneable, Comparable<Triangle>{
	
	private static final int nonTriangle = 0;
	private static final int normalTriangle = 1;
	private static final int isoscelesTriangle = 2;
	private static final int equilateralTriangle = 3;
	
	private double a;
	private double b;
	private double c;
	
	public Triangle(double a, double b, double c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}
	
	public void setTriangle (double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	
	/**
	 * 有错误的方法
	 * @return
	 */
	public double getPerimeter() {
		return this.getA() + this.getB() + 2*this.getC();
	}
	
	/**
	 * 检查三角形的三边是否非负
	 * @return boolean
	 */
	public boolean isPositiveNum() {
		if (a <= 0 || b <= 0 || c <= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 用于比较两个三角形的周长
	 * @param o
	 * @return 
	 */
	@Override
	public int compareTo(Triangle o) {
		// TODO Auto-generated method stub
		double perimeter1 = this.getPerimeter();
		double perimeter2 = o.getPerimeter();
		if (perimeter1 > perimeter2)
			return 1;
		else if (perimeter1 < perimeter2)
			return -1;
		else
			return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		double perimeter1 = this.getPerimeter();
		double perimeter2 = ((Triangle)o).getPerimeter();
		if (perimeter1 == perimeter2)
			return true;
		return false;
	}
	
	/**
	 * 判断三角形的类型
	 * @return
	 */
	public int judgeType () {
		if (a+b<=c || a+c<=b || c+b<=a || !isPositiveNum()) {
			return nonTriangle;
		}
		if (a==b && b==c) {
			return equilateralTriangle;
		} else if (a==b || a==c || b==c) {
			return isoscelesTriangle;
		} else {
			return normalTriangle;
		}
	}

	
	public static void main(String[] args) {
		Triangle t = new Triangle(20, 32, 17);
		System.out.println("输入三边为："+t.getA()+" ,"+t.getB()+" ,"+t.getC());
		System.out.println(t.judgeType());
//		System.out.println((double) 100L + " " + (double) (byte)-1 + " " + 10.0d);
	}

}
