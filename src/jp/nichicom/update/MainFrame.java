package jp.nichicom.update;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import java.util.ArrayList;
import java.io.*;

import jp.nichicom.update.task.AbstractTask;
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
		status = new JLabel(" ");
		panelBottom.add(status);
		
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
			getTaskXML();
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
		
		try{
			boolean update = false;
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("update.url"));
			ArrayList taskArray = doc.parseTask();
			progress.setMaximum(taskArray.size() - 1);
			
			for(int i = 0; i < taskArray.size(); i++){
				AbstractTask task = (AbstractTask)taskArray.get(i);
				if(task.runTask()){
					update = true;
				}
				progress.setValue(i);
				progress.paintImmediately(progress.getVisibleRect());
			}
			//�X�V�������s
			if(update){
				JOptionPane.showConfirmDialog(this,"�A�b�v�f�[�g���������܂����B","�A�b�v�f�[�g�c�[��",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showConfirmDialog(this,"���ɍŐV�o�[�W�����ł��B","�A�b�v�f�[�g�c�[��",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
			}
		} catch(Exception e){
			Log.warning("�A�b�v�f�[�g���s�G���[:" + e.getLocalizedMessage());
			JOptionPane.showConfirmDialog(this,"�A�b�v�f�[�g���s���ɃG���[���������܂����B\n�����𑱍s�ł��܂���B","�A�b�v�f�[�g�c�[��",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
		}
		progress.setValue(0);
	}
}
