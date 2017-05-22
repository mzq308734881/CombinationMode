package pack;

import java.io.BufferedReader;
import java.io.*;

public class GetFileContent {
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
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
        File file = new File("F:\\Eclipse IDE for Java EE Developers\\纯文本XML读写\\bin\\pack\\Test.xml");
        System.out.println(txt2String(file));
	}

}
