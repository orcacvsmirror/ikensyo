package jp.nichicom.update.task;

import java.util.ArrayList;

import jp.nichicom.update.util.XMLDocumentUtil;

public class TaskXMLParser extends Thread {
    private String url;
    private boolean run=true;
    private ArrayList taskArray;
    private Exception runException; 

    /**
     * run を返します。
     * @return run
     */
    public boolean isRun() {
        return run;
    }

    /**
     * taskArray を返します。
     * @return taskArray
     */
    public ArrayList getTaskArray() {
        return taskArray;
    }

    /**
     * url を返します。
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * コンストラクタです。
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
     * runException を返します。
     * @return runException
     */
    public Exception getRunException() {
        return runException;
    }
}
