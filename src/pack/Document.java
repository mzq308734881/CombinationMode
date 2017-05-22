package pack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.*;
import java.util.*;

public class Document {
	private String xmlString;

	/**
	 * 传入xml的字符串内容,对于InputStream,Reader对象请转换为String对象后传入构造方法.
	 * 
	 * @param xmlString
	 *            String
	 * @throws IllegalArgumentException
	 */
	public Document(String xmlString) throws IllegalArgumentException {
		if (xmlString == null || xmlString.length() == 0)
			throw new IllegalArgumentException("Input string orrer!");
		this.xmlString = xmlString;
	}

	/**
	 * 在文档中搜索指定的元素,返回符合条件的元素数组.
	 * 
	 * @param tagName
	 *            String
	 * @return String[]
	 */
	public String[] getElementsByTag(String tagName) {
		Pattern p = Pattern.compile("<" + tagName + "[^>]*?((>.*?</" + tagName
				+ ">)|(/>))");
		Matcher m = p.matcher(this.xmlString);
		ArrayList<String> al = new ArrayList<String>();
		while (m.find())
			al.add(m.group());
		String[] arr = al.toArray(new String[al.size()]);
		al.clear();
		return arr;
	}

	/**
	 * 用xpath模式提取元素,以#为分隔符 如 ROOT#PARENT#CHILD表示提取ROOT元素下的PARENT元素下的CHILD元素
	 * 
	 * @param singlePath
	 *            String
	 * @return String
	 */
	public String getElementBySinglePath(String singlePath) {
		String[] path = singlePath.split("#");
		String lastTag = path[path.length - 1];
		String tmp = "(<" + lastTag + "[^>]*?((>.*?</" + lastTag + ">)|(/>)))";
		// 最后一个元素,可能是<x>v</x>形式或<x/>形式
		for (int i = path.length - 2; i >= 0; i--) {
			lastTag = path[i];
			tmp = "<" + lastTag + ">.*" + tmp + ".*</" + lastTag + ">";
		}
		Pattern p = Pattern.compile(tmp);
		Matcher m = p.matcher(this.xmlString);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	/**
	 * 用xpath模式提取元素从多重元素中获取指批定元素,以#为分隔符 元素后无索引序号则默认为0: ROOT#PARENT[2]#CHILD[1]
	 * 
	 * @param singlePath
	 *            String
	 * @return String
	 */
	public String getElementByMultiPath(String singlePath) {
		try {
			String[] path = singlePath.split("#");
			String input = this.xmlString;
			String[] ele = null;
			for (int i = 0; i < path.length; i++) {
				Pattern p = Pattern.compile("(\\w+)(\\[(\\d+)\\])?");
				Matcher m = p.matcher(path[i]);
				if (m.find()) {
					String tagName = m.group(1);
					System.out.println(input + "----" + tagName);
					int index = (m.group(3) == null) ? 0 : new Integer(
							m.group(3)).intValue();
					ele = getElementsByTag(input, tagName);
					input = ele[index];
				}
			}
			return input;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 在给定的元素中搜索指定的元素,返回符合条件的元素数组.对于不同级别的同名元素限制作用,即可以
	 * 搜索元素A中的子元素C.而对于元素B中子元素C则过虑,通过多级限定可以准确定位.
	 * 
	 * @param parentElementString
	 *            String
	 * @param tagName
	 *            String
	 * @return String[]
	 */
	public  String[] getElementsByTag(String parentElementString,
			String tagName) {
		Pattern p = Pattern.compile("<" + tagName + "[^>]*?((>.*?</" + tagName
				+ ">)|(/>))");
		Matcher m = p.matcher(parentElementString);
		ArrayList<String> al = new ArrayList<String>();
		while (m.find())
			al.add(m.group());
		String[] arr = al.toArray(new String[al.size()]);
		al.clear();
		return arr;
	}

	/**
	 * 从指定的父元素中根据xpath模式获取子元素,singlePath以#为分隔符 如
	 * ROOT#PARENT#CHILD表示提取ROOT元素下的PARENT元素下的CHILD元素
	 * 
	 * @param parentElementString
	 *            String
	 * @param singlePath
	 *            String
	 * @return String
	 */
	public  String getElementBySinglePath(String parentElementString,String singlePath) {
		String[] path = singlePath.split("#");
		String lastTag = path[path.length - 1];
		String tmp = "(<" + lastTag + "[^>]*?((>.*?</" + lastTag + ">)|(/>)))";
		// 最后一个元素,可能是<x>v</x>形式或<x/>形式
		for (int i = path.length - 2; i >= 0; i--) {
			lastTag = path[i];
			tmp = "<" + lastTag + ">.*" + tmp + ".*</" + lastTag + ">";
		}
		Pattern p = Pattern.compile(tmp);
		Matcher m = p.matcher(parentElementString);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	/**
	 * 用xpath模式提取元素从指定的多重元素中获取指批定元素,以#为分隔符
	 * 
	 * @param parentElementString
	 *            String
	 * @param singlePath
	 *            String
	 * @return String
	 */
	public  String getElementByMultiPath(String parentElementString,String singlePath) {
		try {
			String[] path = singlePath.split("#");
			String input = parentElementString;
			String[] ele = null;
			for (int i = 0; i < path.length; i++) {
				Pattern p = Pattern.compile("(\\w+)(\\[(\\d+)\\])?");
				Matcher m = p.matcher(path[i]);
				if (m.find()) {
					String tagName = m.group(1);
					int index = (m.group(3) == null) ? 0 : new Integer(
							m.group(3)).intValue();
					ele = getElementsByTag(input, tagName);
					input = ele[index];
				}
			}
			return input;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 在给定的元素中获取所有属性的集合.该元素应该从getElementsByTag方法中获取
	 * 
	 * @param elementString
	 *            String
	 * @return HashMap
	 */
	public HashMap<String, String> getAttributes(String elementString) {
		HashMap hm = new HashMap<String, String>();
		Pattern p = Pattern.compile("<[^>]+>");
		Matcher m = p.matcher(elementString);
		String tmp = m.find() ? m.group() : "";
		p = Pattern.compile("(\\w+)\\s*=\\s*\"([^\"]+)\"");
		m = p.matcher(tmp);
		while (m.find()) {
			hm.put(m.group(1).trim(), m.group(2).trim());
		}
		return hm;
	}

	/**
	 * 在给定的元素中获取指定属性的值.该元素应该从getElementsByTag方法中获取
	 * 
	 * @param elementString
	 *            String
	 * @param attributeName
	 *            String
	 * @return String
	 */
	public  String getAttribute(String elementString, String attributeName) {
		HashMap hm = new HashMap<String, String>();
		Pattern p = Pattern.compile("<[^>]+>");
		Matcher m = p.matcher(elementString);
		String tmp = m.find() ? m.group() : "";
		p = Pattern.compile("(\\w+)\\s*=\\s*\"([^\"]+)\"");
		m = p.matcher(tmp);
		while (m.find()) {
			if (m.group(1).trim().equals(attributeName))
				return m.group(2).trim();
		}
		return "";
	}

	/**
	 * 获取指定元素的文本内容
	 * 
	 * @param elementString
	 * String
	 * @return String
	 */
	public String getElementText(String elementString) {
		Pattern p = Pattern.compile(">([^<>]*)<");
		Matcher m = p.matcher(elementString);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	/**
	 * 得到XML文件内容
	 * 
	 * @param file 文件路径
	 * @return 文件内容
	 * 
	 * */
	public static String XmlString(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	
    /*----------------------------------- 
    笨方法：String s = "你要去除的字符串";
            1.去除空格：s = s.replace('\\s','');
            2.去除回车：s = s.replace('\n','');
    这样也可以把空格和回车去掉，其他也可以照这样做。
 
    注：\n 回车(\u000a) 
    \t 水平制表符(\u0009) 
    \s 空格(\u0008) 
    \r 换行(\u000d)*/
	/**
	 * 
	 * 去除空格、换行、回车和制表符
	 * */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	/** 
	 * 
	 * 删除结点操作，
	 * */
	public String deleteNode(String xmlElement, String oldInfo,String replacestr, File file) {
		String string = xmlElement.replaceAll(oldInfo, replacestr);
		//将之前内容全部删掉
		if (file.exists()) {
			file.delete();
		}
		//将替换后的内容重新写入
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(string);
			writer.close();
		} catch (Exception e) {
			System.out.printf("异常："+e.getMessage());
		}
		return string;
	}
	
	/** 
	 * 
	 * 添加结点操作
	 * */
	public String addNode(String xmlElement,String nodeName,String value,File file) {
		//首先排除要插入的结点是否已存在
		//
		if (xmlElement.indexOf("<"+nodeName+">"+value+"</"+nodeName+">")!=-1) {
			System.out.printf("新增结点"+nodeName+"已存在！！！\n");
			return xmlElement;		
		}
		
		String string=xmlElement.replace("<person>","<person>"+"<"+nodeName+">"+value+"</"+nodeName+">");
		//将之前内容全部删掉
		if (file.exists()) {
			file.delete();
		}
		//将替换后的内容重新写入
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(string);
			writer.close();
		} catch (Exception e) {
			System.out.printf("异常："+e.getMessage());
		}
		return string;
	}

	/** 
	 * 
	 * 修改结点操作/也叫替换结点操作
	 * */
	public String editNode(String xmlElement,String oldNodes,String newNode,String newValue,File file ) {
		//首先判断该结点是否存在，不存在就返回
		if (xmlElement.indexOf("<"+oldNodes+">")==-1){
			System.out.printf("要修改结点"+oldNodes+"不存在，请重新确认！！！");
			return xmlElement;
		}				
		String string="";		
		return string;
	}
		
    /**
     * 将文件中指定内容的第一行替换为其它内容 . 
     * 
     * @param oldStr 
     *                查找内容 
     * @param replaceStr 
     *                替换内容 
     */ 
    public  void replaceTxtByStr(String oldStr,String replaceStr,File file) { 
        String temp = ""; 
        try { 
            FileInputStream fis = new FileInputStream(file); 
            InputStreamReader isr = new InputStreamReader(fis); 
            BufferedReader br = new BufferedReader(isr); 
            StringBuffer buf = new StringBuffer(); 

            // 保存该行前面的内容 
            for (int j = 1; (temp = br.readLine()) != null 
                    && !temp.equals(oldStr); j++) { 
                buf = buf.append(temp); 
                buf = buf.append(System.getProperty("line.separator")); 
            } 

            // 将内容插入 
            buf = buf.append(replaceStr); 

            // 保存该行后面的内容 
            while ((temp = br.readLine()) != null) { 
                buf = buf.append(System.getProperty("line.separator")); 
                buf = buf.append(temp); 
            } 

            br.close(); 
            FileOutputStream fos = new FileOutputStream(file); 
            PrintWriter pw = new PrintWriter(fos); 
            pw.write(buf.toString().toCharArray()); 
            pw.flush(); 
            pw.close(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
		
	public static void main(String[] args) {     		
		File file = new File("F:\\Eclipse IDE for Java EE Developers\\纯文本XML读写\\bin\\pack\\Test1.xml");
		Document document = new Document(replaceBlank(XmlString(file)));
		//得到指定结点的值
		String string = document.getElementByMultiPath("root[0]#person[0]#name");
		//获取最后 一个结点的值
		String string2 = document.getElementBySinglePath("root#person#age"); 
		//String string3=document.getElementText("root[0]");
		System.out.println(string);
		System.out.println(string2);
		
		//添加结点
		System.out.printf("添加结点:"+document.addNode(replaceBlank(XmlString(file)), "Sex", "男", file));		
		//删除结点
		System.out.printf("删除结点："+document.deleteNode(replaceBlank(XmlString(file)),string,"",file));
		//修改结点
						
		System.out.printf("添加/删除后字符串："+replaceBlank(XmlString(file)));
		//System.out.printf(string3);
		// 参考文献来自：http://www.cnblogs.com/lbnnbs/p/4784593.html
	}
}