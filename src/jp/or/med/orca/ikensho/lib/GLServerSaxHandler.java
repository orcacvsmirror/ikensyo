package jp.or.med.orca.ikensho.lib;

import java.util.HashSet;
import java.util.Set;

import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GLServerSaxHandler extends DefaultHandler {
	private String informationDate;
	private String informationTime;
	private String apiResult;
	private String apiResultMessage;
	private String reskey;
	
	private int targetPatientCount = 0;
	private int processingCount = 0;
	private boolean isResultEnd = false;
	private int recordDepth = 0;
	private boolean isPatientInformation = false;
	
	private String name = null;
	private StringBuffer value = new StringBuffer();
	
	private VRList result = new VRArrayList();
	private VRMap row;
	
	private static Set wantTag = new HashSet();
	
	public static final int MODE_LIST = 1;
	public static final int MODE_MAP = 2;
	
	
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		if (isResultEnd) {
			return;
		}
		
		if ("string".equals(qName)) {
			name = atts.getValue("name");
			value = new StringBuffer();
		} else if (isPatientInformation && "record".equals(qName)) {
			
			if (targetPatientCount <= processingCount) {
				isResultEnd = true;
				return;
			}
			
			if (recordDepth == 0) {
				row = new VRHashMap();
			}
			recordDepth++;
		} else if ("array".equals(qName)) {
			
			//���ҏ��̈�̊J�n
			if ("Patient_Information".equals(atts.getValue("name"))) {
				isPatientInformation = true;
			}
			
		}
		
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (isResultEnd) {
			return;
		}
		
		if (name != null) {
			
			//������
			if ("Information_Date".equals(name)) {
				informationDate = value.toString();
				
			//��������
			} else if ("Information_Time".equals(name)) {
				informationTime = value.toString();
				
			//API�̏������ʃR�[�h
			} else if ("Api_Result".equals(name)) {
				apiResult = value.toString();
				
			//API�̏������ʃ��b�Z�[�W
			} else if ("Api_Result_Message".equals(name)) {
				apiResultMessage = value.toString();
				
			//�������e
			} else if ("Reskey".equals(name)) {
				reskey = value.toString();
			
			//��������
			} else if ("Target_Patient_Count".equals(name)) {
				targetPatientCount = Integer.parseInt(value.toString());
				
			//�������ʖ���
			} else if ("No_Target_Patient_Count".equals(name)) {
				//System.out.println(value.toString());
				
			//�K�v�ȏ�񂾂��擾
			} else if (wantTag.contains(name)){
				row.put(name, value.toString());
				
			}
		}
		
		if (isPatientInformation && "record".equals(qName)) {
			
			recordDepth--;
			
			if (recordDepth == 0) {
				
				result.add(row);
				processingCount++;
				
				//�z�肵�Ă��錟�����ʌ������擾������A�ȍ~�̉�͂̓L�����Z������
				if (targetPatientCount <= processingCount) {
					isResultEnd = true;
				}
			}
			
		}
		
		name = null;
    }
    
    public void characters(char[] ch, int start, int length) throws SAXException {
		if (isResultEnd) {
			return;
		}
		
		if (name != null) {
			value.append(ch, start, length);
		}
    }

	
	public VRList getResult() {
		return result;
	}
	
	public String getAPIResult() {
		return apiResult;
	}
	public String getAPIResultMessage() {
		return apiResultMessage;
	}
	
	public int getTargetPatientCount() {
		return targetPatientCount;
	}
	
	public void printStatus() {
		System.out.println("������:" + informationDate);
		System.out.println("��������:" + informationTime);
		System.out.println("API�̏������ʃR�[�h:" + apiResult);
		System.out.println("API�̏������ʃ��b�Z�[�W:" + apiResultMessage);
		System.out.println("�������e:" + reskey);
		System.out.println("��������:" + targetPatientCount);
		System.out.println("�������ʖ���:" + "");
	}
	
	
	static {
		wantTag.add("Patient_ID");
		wantTag.add("WholeName");
		wantTag.add("WholeName_inKana");
		wantTag.add("BirthDate");
		wantTag.add("Sex");
		wantTag.add("Address_ZipCode");
		wantTag.add("WholeAddress1");
		wantTag.add("WholeAddress2");
		wantTag.add("PhoneNumber1");
		wantTag.add("PhoneNumber2");
		wantTag.add("Outpatient_Class");
	}

}
