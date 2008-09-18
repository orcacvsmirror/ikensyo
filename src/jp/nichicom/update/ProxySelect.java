package jp.nichicom.update;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import jp.nichicom.update.util.HttpConnecter;
import jp.nichicom.update.util.PropertyUtil;

/**
 * �v���L�V�T�[�o�ݒ���
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class ProxySelect  extends JDialog implements ActionListener {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
	 * �v���L�V�T�[�o���̓���
	 */
	JTextField server = new JTextField(20);
	/**
	 * �v���L�V�|�[�g�̓���
	 */
	JTextField port = new JTextField(20);
	
	/**
	 * LookAndFeel��ݒ�
	 */
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable tw) { }
	}
	
	/**
	 * �R���X�g���N�^
	 */
	public ProxySelect(JFrame frame) {
		// �X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super(frame,true);
		setTitle("�v���L�V�T�[�o�̐ݒ�");

		//���s�{�^���̍쐬
		JButton commit = new JButton("�ݒ�(E)");
		commit.setMnemonic('E');
		commit.setActionCommand("commit");
		commit.addActionListener(this);

		// �쐬�{�^���̍쐬
		JButton cancel = new JButton("�L�����Z��(C)");
		cancel.setMnemonic('C');
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);;
	
		// �p�l���ɕ��i��ǉ�
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panel2 = new JPanel(new FlowLayout());
		JPanel panelButton = new JPanel(new FlowLayout());
		panelButton.add(commit);
		panelButton.add(cancel);
		
		panel2.add(new JLabel("�T�[�o���F"));
		panel2.add(server);
		panel.add(panel2,BorderLayout.NORTH);
		panel2 = new JPanel();
		panel2.add(new JLabel("�|�[�gNo�F"));
		panel2.add(port);
		panel.add(panel2,BorderLayout.SOUTH);
		
		server.setText(PropertyUtil.getProperty("http.proxyHost"));
		port.setText(PropertyUtil.getProperty("http.proxyPort"));
	
		// �R���e���g�E�y�C���̎擾
		Container cnt = getContentPane();
		// �R���e���g�E�y�C���ɕ��i��ǉ�
		cnt.add(panel, BorderLayout.NORTH);
		cnt.add(panelButton);
		
		//��ʂ̃T�C�Y�����肵�܂��B
		setSize(new Dimension(300, 150));
		// ��ʈʒu�̐ݒ���s���܂��B
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds( (screenSize.width - getWidth()) / 2,
			(screenSize.height - getHeight()) /
			2, getWidth(), getHeight());
        pack();
	}
	
	/**
	 * �{�^���������̏���
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if("commit".equals(command)){
			PropertyUtil.setProperty("http.proxyHost",server.getText().trim());
			PropertyUtil.setProperty("http.proxyPort",port.getText().trim());
			//�v���L�V�T�[�o���̂�ݒ肷��
			HttpConnecter.setProxyHost(server.getText().trim());
			//�v���L�V�̃|�[�g�ԍ���ݒ肷��B
			HttpConnecter.setProxyPort(port.getText().trim());
		}
		dispose();
	}

}
