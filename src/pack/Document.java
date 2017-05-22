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
	 * ����xml���ַ�������,����InputStream,Reader������ת��ΪString������빹�췽��.
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
	 * ���ĵ�������ָ����Ԫ��,���ط���������Ԫ������.
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
	 * ��xpathģʽ��ȡԪ��,��#Ϊ�ָ��� �� ROOT#PARENT#CHILD��ʾ��ȡROOTԪ���µ�PARENTԪ���µ�CHILDԪ��
	 * 
	 * @param singlePath
	 *            String
	 * @return String
	 */
	public String getElementBySinglePath(String singlePath) {
		String[] path = singlePath.split("#");
		String lastTag = path[path.length - 1];
		String tmp = "(<" + lastTag + "[^>]*?((>.*?</" + lastTag + ">)|(/>)))";
		// ���һ��Ԫ��,������<x>v</x>��ʽ��<x/>��ʽ
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
	 * ��xpathģʽ��ȡԪ�شӶ���Ԫ���л�ȡָ����Ԫ��,��#Ϊ�ָ��� Ԫ�غ������������Ĭ��Ϊ0: ROOT#PARENT[2]#CHILD[1]
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
	 * �ڸ�����Ԫ��������ָ����Ԫ��,���ط���������Ԫ������.���ڲ�ͬ�����ͬ��Ԫ����������,������
	 * ����Ԫ��A�е���Ԫ��C.������Ԫ��B����Ԫ��C�����,ͨ���༶�޶�����׼ȷ��λ.
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
	 * ��ָ���ĸ�Ԫ���и���xpathģʽ��ȡ��Ԫ��,singlePath��#Ϊ�ָ��� ��
	 * ROOT#PARENT#CHILD��ʾ��ȡROOTԪ���µ�PARENTԪ���µ�CHILDԪ��
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
		// ���һ��Ԫ��,������<x>v</x>��ʽ��<x/>��ʽ
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
	 * ��xpathģʽ��ȡԪ�ش�ָ���Ķ���Ԫ���л�ȡָ����Ԫ��,��#Ϊ�ָ���
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
	 * �ڸ�����Ԫ���л�ȡ�������Եļ���.��Ԫ��Ӧ�ô�getElementsByTag�����л�ȡ
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
	 * �ڸ�����Ԫ���л�ȡָ�����Ե�ֵ.��Ԫ��Ӧ�ô�getElementsByTag�����л�ȡ
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
	 * ��ȡָ��Ԫ�ص��ı�����
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
	 * �õ�XML�ļ�����
	 * 
	 * @param file �ļ�·��
	 * @return �ļ�����
	 * 
	 * */
	public static String XmlString(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// ����һ��BufferedReader������ȡ�ļ�
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	
    /*----------------------------------- 
    ��������String s = "��Ҫȥ�����ַ���";
            1.ȥ���ո�s = s.replace('\\s','');
            2.ȥ���س���s = s.replace('\n','');
    ����Ҳ���԰ѿո�ͻس�ȥ��������Ҳ��������������
 
    ע��\n �س�(\u000a) 
    \t ˮƽ�Ʊ��(\u0009) 
    \s �ո�(\u0008) 
    \r ����(\u000d)*/
	/**
	 * 
	 * ȥ���ո񡢻��С��س����Ʊ��
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
	 * ɾ����������
	 * */
	public String deleteNode(String xmlElement, String oldInfo,String replacestr, File file) {
		String string = xmlElement.replaceAll(oldInfo, replacestr);
		//��֮ǰ����ȫ��ɾ��
		if (file.exists()) {
			file.delete();
		}
		//���滻�����������д��
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(string);
			writer.close();
		} catch (Exception e) {
			System.out.printf("�쳣��"+e.getMessage());
		}
		return string;
	}
	
	/** 
	 * 
	 * ��ӽ�����
	 * */
	public String addNode(String xmlElement,String nodeName,String value,File file) {
		//�����ų�Ҫ����Ľ���Ƿ��Ѵ���
		//
		if (xmlElement.indexOf("<"+nodeName+">"+value+"</"+nodeName+">")!=-1) {
			System.out.printf("�������"+nodeName+"�Ѵ��ڣ�����\n");
			return xmlElement;		
		}
		
		String string=xmlElement.replace("<person>","<person>"+"<"+nodeName+">"+value+"</"+nodeName+">");
		//��֮ǰ����ȫ��ɾ��
		if (file.exists()) {
			file.delete();
		}
		//���滻�����������д��
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(string);
			writer.close();
		} catch (Exception e) {
			System.out.printf("�쳣��"+e.getMessage());
		}
		return string;
	}

	/** 
	 * 
	 * �޸Ľ�����/Ҳ���滻������
	 * */
	public String editNode(String xmlElement,String oldNodes,String newNode,String newValue,File file ) {
		//�����жϸý���Ƿ���ڣ������ھͷ���
		if (xmlElement.indexOf("<"+oldNodes+">")==-1){
			System.out.printf("Ҫ�޸Ľ��"+oldNodes+"�����ڣ�������ȷ�ϣ�����");
			return xmlElement;
		}				
		String string="";		
		return string;
	}
		
    /**
     * ���ļ���ָ�����ݵĵ�һ���滻Ϊ�������� . 
     * 
     * @param oldStr 
     *                �������� 
     * @param replaceStr 
     *                �滻���� 
     */ 
    public  void replaceTxtByStr(String oldStr,String replaceStr,File file) { 
        String temp = ""; 
        try { 
            FileInputStream fis = new FileInputStream(file); 
            InputStreamReader isr = new InputStreamReader(fis); 
            BufferedReader br = new BufferedReader(isr); 
            StringBuffer buf = new StringBuffer(); 

            // �������ǰ������� 
            for (int j = 1; (temp = br.readLine()) != null 
                    && !temp.equals(oldStr); j++) { 
                buf = buf.append(temp); 
                buf = buf.append(System.getProperty("line.separator")); 
            } 

            // �����ݲ��� 
            buf = buf.append(replaceStr); 

            // ������к�������� 
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
		File file = new File("F:\\Eclipse IDE for Java EE Developers\\���ı�XML��д\\bin\\pack\\Test1.xml");
		Document document = new Document(replaceBlank(XmlString(file)));
		//�õ�ָ������ֵ
		String string = document.getElementByMultiPath("root[0]#person[0]#name");
		//��ȡ��� һ������ֵ
		String string2 = document.getElementBySinglePath("root#person#age"); 
		//String string3=document.getElementText("root[0]");
		System.out.println(string);
		System.out.println(string2);
		
		//��ӽ��
		System.out.printf("��ӽ��:"+document.addNode(replaceBlank(XmlString(file)), "Sex", "��", file));		
		//ɾ�����
		System.out.printf("ɾ����㣺"+document.deleteNode(replaceBlank(XmlString(file)),string,"",file));
		//�޸Ľ��
						
		System.out.printf("���/ɾ�����ַ�����"+replaceBlank(XmlString(file)));
		//System.out.printf(string3);
		// �ο��������ԣ�http://www.cnblogs.com/lbnnbs/p/4784593.html
	}
}