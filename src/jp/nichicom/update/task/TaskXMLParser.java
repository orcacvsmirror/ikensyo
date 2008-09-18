package jp.nichicom.update.task;

import java.util.ArrayList;

import jp.nichicom.update.util.XMLDocumentUtil;

public class TaskXMLParser extends Thread {
    private String url;
    private boolean run=true;
    private ArrayList taskArray;
    private Exception runException; 

    /**
     * run ��Ԃ��܂��B
     * @return run
     */
    public boolean isRun() {
        return run;
    }

    /**
     * taskArray ��Ԃ��܂��B
     * @return taskArray
     */
    public ArrayList getTaskArray() {
        return taskArray;
    }

    /**
     * url ��Ԃ��܂��B
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * �R���X�g���N�^�ł��B
     * @param url URL
     */
    public TaskXMLParser(String url) {
        super();
        this.url = url;
    }
    public void run(){
        run = true;
        try {
            XMLDocumentUtil doc = new XMLDocumentUtil(url);
            this.taskArray = doc.parseTask();
        } catch (Exception ex) {
            runException = ex;
        } finally {
            run = false;
        }
    }

    /**
     * runException ��Ԃ��܂��B
     * @return runException
     */
    public Exception getRunException() {
        return runException;
    }
}
