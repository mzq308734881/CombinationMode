public class Client {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Classic c1;
		Equipment e[]=new Factorial[5];
		c1=new Classic();
		for (int i = 0; i < e.length; i++) {
			e[i]=new Factorial(i+1);
			c1.add(e[i]);
		}		
		c1.fun();	
		System.out.println(e.length+"�Ľ׳˽��Ϊ��"+c1.getCount());		
	}
}
