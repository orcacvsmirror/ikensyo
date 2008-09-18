package jp.nichicom.update.task;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class TaskProcesser extends Thread {
    private boolean update=false;
    private Exception runException; 
    private boolean run=true;
    private ArrayList taskArray;
    private JFrame frame;
    private JProgressBar progress;
    private JLabel status;
    private String title="";
    private int progressValue=0;
    
    /**
     * progressValue を返します。
     * @return progressValue
     */
    public int getProgressValue() {
        return progressValue;
    }
    public String getTitle() {
        return title;
    }

    public TaskProcesser(ArrayList taskArray, JFrame frame, JProgressBar progress, JLabel status){
        this.progress = progress;
        this.taskArray = taskArray;
        this.frame = frame;
        this.progress = progress;
        this.status = status;
        
        int count = 0;
        Iterator it=taskArray.iterator();
        while(it.hasNext()){
            count += ((AbstractTask) it.next()).size();
        }
        progress.setMaximum(count);
    }
    
    public void run(){
        run = true;
        try {
            for (int i = 0; i < taskArray.size(); i++) {
                AbstractTask task = (AbstractTask) taskArray.get(i);
                if (task.runTask(this)) {
                    update = true;
                }
            }
        } catch (Exception ex) {
            runException = ex;
        } finally {
            run = false;
        }
    }
    /**
     * 進捗を更新します。
     * @param title
     */
    public void setStatus(String title) {
        this.title = title;
        status.setText(title);
        ((JComponent) frame.getContentPane())
                .paintImmediately(((JComponent) frame.getContentPane())
                        .getVisibleRect());
    }
    public void addProgress(){
        progressValue++;
        progress.setValue(progressValue);
        ((JComponent) frame.getContentPane())
        .paintImmediately(((JComponent) frame.getContentPane())
                .getVisibleRect());
    }
    public void skipTask(AbstractTask task){
        progressValue += task.size();
        progress.setValue(progressValue);
        ((JComponent) frame.getContentPane())
        .paintImmediately(((JComponent) frame.getContentPane())
                .getVisibleRect());
        
    }
    
    /**
     * run を返します。
     * @return run
     */
    public boolean isRun() {
        return run;
    }
    /**
     * runException を返します。
     * @return runException
     */
    public Exception getRunException() {
        return runException;
    }
    /**
     * update を返します。
     * @return update
     */
    public boolean isUpdate() {
        return update;
    }
}
