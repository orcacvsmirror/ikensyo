package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;

/** <HEAD_IKENSYO> */
public class IkenshoWaitingForm extends IkenshoDialog {
    private JPanel contentPane = new JPanel();
    private VRPanel contents = new VRPanel();
    private JProgressBar progressBar = null;

    public IkenshoWaitingForm(Frame owner, String title) {
        super(owner, title);
        try {
            jbInit();
            pack();
            init();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        contentPane = (JPanel)this.getContentPane();
        contentPane.add(contents);

        contents.setLayout(new VRLayout());
        contents.add(progressBar, VRLayout.FLOW);

    }

    private void init() {
        //ウィンドウのサイズ
        setSize(new Dimension(200, 70));
        //ウィンドウを中央に配置
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
   }

    public void setProgressValue(int value) {
        progressBar.setValue(value);
        progressBar.setString(Integer.toString(value) + " / " + Integer.toString(progressBar.getMaximum()));
        progressBar.paintImmediately(progressBar.getVisibleRect());
        if (progressBar.getMaximum() <= value) {
            this.dispose();
        }
    }

    public void setMaxCount(int max) {
        progressBar.setMaximum(max);
        this.repaint();
    }
}
