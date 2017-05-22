package pack;

import java.io.BufferedReader;
import java.io.*;

public class GetFileContent {
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        File file = new File("F:\\Eclipse IDE for Java EE Developers\\���ı�XML��д\\bin\\pack\\Test.xml");
        System.out.println(txt2String(file));
	}

}
