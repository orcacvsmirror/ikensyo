package jp.nichicom.update;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import jp.nichicom.update.task.TaskProcesser;
import jp.nichicom.update.util.HttpConnecter;
import jp.nichicom.update.util.Log;
import jp.nichicom.update.util.PropertyUtil;
import jp.nichicom.update.util.XMLDocumentUtil;

/**
 * �����X�V�c�[��
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class MainFrame extends JFrame implements ActionListener {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
	 * �v���O���X�o�[
	 */
	private JProgressBar progress;
	private JLabel status;

    private String lineSeparator = System.getProperty("line.separator");

	/**
	 * LookAndFeel�̐ݒ�
	 */
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable tw) { }
	}

	/**
	 * �A�b�v�f�[�g�c�[���̎��s
	 * @param args �N�����I�v�V����
	 */
	public static void main(String[] args) {
		// �������g�̃C���X�^���X��
	    JFrame frame = new MainFrame();
		// �t���[���̃Z�b�g�A�b�v
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �I��������
		//��ʂ̃T�C�Y�����肵�܂��B
		frame.setSize(new Dimension(500, 110));
		// ��ʈʒu�̐ݒ���s���܂��B
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds( (screenSize.width - frame.getWidth()) / 2,
			(screenSize.height - frame.getHeight()) /
			2, frame.getWidth(), frame.getHeight());
		// �����i���A���C�Y�j
		frame.setVisible(true);
	}

	// �R���X�g���N�^
	public MainFrame() {
		// �X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super("�A�b�v�f�[�g�c�[��");
		
		//�v���L�V�T�[�o���̂�ݒ肷��
		HttpConnecter.setProxyHost(PropertyUtil.getProperty("http.proxyHost"));
		//�v���L�V�̃|�[�g�ԍ���ݒ肷��B
		HttpConnecter.setProxyPort(PropertyUtil.getProperty("http.proxyPort"));
		//�v���L�V���g��Ȃ��T�[�o�̐ݒ�
		HttpConnecter.setNonProxyHosts(PropertyUtil.getProperty("http.nonProxyHosts"));
		//���O���x���̐ݒ�
		Log.setLogLevel(PropertyUtil.getProperty("log.level"));

		//���s�{�^���̍쐬
		JButton update = new JButton("�A�b�v�f�[�g���s(U)");
		update.setMnemonic('U');
		update.setActionCommand("update");
		update.addActionListener(this);

		//�v���L�V�{�^���̍쐬
		JButton proxy = new JButton("�v���L�V�ݒ�(P)");
		proxy.setMnemonic('P');
		proxy.setActionCommand("proxy");
		proxy.addActionListener(this);
		
		// �쐬�{�^���̍쐬
		JButton cancel = new JButton("�I��(E)");
		cancel.setMnemonic('E');
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);;
	
		// �p�l���ɕ��i��ǉ�
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel panel2 = new JPanel();
		panel2.add(update,BorderLayout.NORTH);
		panel2.add(proxy,BorderLayout.NORTH);
		panel2.add(cancel,BorderLayout.NORTH);
		panel.add(panel2, BorderLayout.NORTH);
	
		progress = new JProgressBar(0,10);
		progress.setStringPainted(true);
		progress.setIndeterminate(false);
		
		JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new BorderLayout());
		status = new JLabel(" ");
		panelBottom.add(status, BorderLayout.CENTER);
        status.setHorizontalAlignment(SwingConstants.CENTER);
		
		// �R���e���g�E�y�C���̎擾
		Container cnt = getContentPane();
		cnt.setLayout(new BorderLayout());
		// �R���e���g�E�y�C���ɕ��i��ǉ�
		cnt.add(panel, BorderLayout.NORTH);
		cnt.add(progress,BorderLayout.CENTER);
		cnt.add(panelBottom,BorderLayout.SOUTH);
		
		File file = new File("temp");
		if(file.exists()){
			file.delete();
		}
		file.mkdir();
	}

	/**
	 * �{�^���������̃C�x���g����
	 */
	public void actionPerformed(ActionEvent ae) {
		if ("update".equals(ae.getActionCommand())) {
            //�m�F���b�Z�[�W�\��������ǉ�
            if(JOptionPane.showConfirmDialog(this,"�A�b�v�f�[�g�����͒����Ԃɂ킽��ꍇ������܂��B\n�i�������ɉ�ʂ��������Ȃ��Ȃ邱�Ƃ�����܂��B�j\n���s���Ă���낵���ł����H","�A�b�v�f�[�g�c�[��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION){
                getTaskXML();
            }
		} else if ("proxy".equals(ae.getActionCommand()) ) {
			ProxySelect select = new ProxySelect(this);
			select.setVisible(true);
		} else if ("cancel".equals(ae.getActionCommand())) {
			System.exit(0);
		}
	}
	
	/**
	 * XML�t�@�C���ŋL�q���ꂽ�A�b�v�f�[�g�^�X�N�����s����
	 *
	 */
	private void getTaskXML(){
		
        setEnabledButtons(getContentPane().getComponents(),false);
		try{
			//boolean update = false;
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("update.url"));
			ArrayList taskArray = doc.parseTask();

			// progress.setMaximum(taskArray.size()-1);
            //			
            // for(int i = 0; i < taskArray.size(); i++){
            // AbstractTask task = (AbstractTask)taskArray.get(i);
            // if(task.runTask()){
            // update = true;
            // }
            // progress.setValue(i);
            //				progress.paintImmediately(progress.getVisibleRect());
            //			}

            TaskProcesser tp = new TaskProcesser(taskArray, this, progress, status);
            tp.start();
            while (tp.isRun()) {
//                this.paintComponents(this.getGraphics());
                ((JComponent) this.getContentPane())
                        .paintImmediately(((JComponent) this.getContentPane())
                                .getVisibleRect());
                Thread.sleep(500);
            }

            if (tp.getRunException() != null) {
                throw tp.getRunException();
            }
                
                            
			//�X�V�������s
            if(tp.isUpdate()){
			//if(update){
				JOptionPane.showConfirmDialog(this,"�A�b�v�f�[�g���������܂����B","�A�b�v�f�[�g�c�[��",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showConfirmDialog(this,"���ɍŐV�o�[�W�����ł��B","�A�b�v�f�[�g�c�[��",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
			}
		} catch(Exception e){
			Log.warning("�A�b�v�f�[�g���s�G���[:" + e.getLocalizedMessage());
			JOptionPane.showConfirmDialog(this, "�A�b�v�f�[�g���s���ɃG���[���������܂����B"
                    + lineSeparator + "�����𑱍s�ł��܂���B" + lineSeparator
                    + "���C���^�[�l�b�g�ɐڑ��\�ł��邩���m�F���������B", "�A�b�v�f�[�g�c�[��",
                    JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		}
        setEnabledButtons(getContentPane().getComponents(),true);
		progress.setValue(0);
        status.setText(" ");
	}
    

    private void setEnabledButtons(Component[] comps,boolean enabled){
        for(int i = 0; i < comps.length; i++){
            if(comps[i] instanceof JButton){
                ((JButton)comps[i]).setEnabled(enabled);
            } else if(comps[i] instanceof Container) {
                setEnabledButtons( ((Container)comps[i]).getComponents(),enabled);
            }
        }
        
        
    }
    
}
