package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.im.InputSubset;
import java.io.File;

import javax.swing.JPanel;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.filechooser.ACFileChooser;
import jp.nichicom.ac.filechooser.ACFileFilter;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoSettingPDF extends IkenshoDialog {

	// �x�[�X�p�l��
	private JPanel contentPane;
	private VRPanel client = new VRPanel();

	// �t�@�C���p�X�w��
	private ACGroupBox adobeGroup = new ACGroupBox();
	private ACLabelContainer fileNameContainer = new ACLabelContainer();
	private ACTextField fileName = new ACTextField();
	private ACButton fileChoose = new ACButton();
	// ������
	private ACLabel info = new ACLabel();
	
	
	// �d�q�����pPDF�p�X�w��
	private ACGroupBox signPdfGroup = new ACGroupBox();
	private ACCheckBox signUseCheck = new ACCheckBox();
	private ACLabelContainer signNameContainer = new ACLabelContainer();
	private ACTextField signName = new ACTextField();
	private ACButton signChoose = new ACButton();
	// �d�q����������
	private ACLabel signInfo = new ACLabel();
	

	// �ݒ�/�L�����Z���{�^��
	private VRPanel btnGrp = new VRPanel();
	private ACButton submit = new ACButton();
	private ACButton cancel = new ACButton();

	private static String separator = System.getProperty("file.separator");

	public IkenshoSettingPDF(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		try {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			jbInit();
			pack();
			init();
			event();

			// �X�i�b�v�V���b�g�B�e
			IkenshoSnapshot.getInstance().snapshot();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public IkenshoSettingPDF() {
		this(ACFrame.getInstance(), "PDF�̐ݒ�", true);
	}

	private void jbInit() {
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(client, BorderLayout.CENTER);
		client.setLayout(new VRLayout());

		// ----- Adobe Reader �ݒ�O���[�v
		// Adobe Reader�̐ݒ�
		adobeGroup.setLayout(new VRLayout());
		((VRLayout)adobeGroup.getLayout()).setAutoWrap(false);
		adobeGroup.setText("Adobe Reader�̐ݒ�");

		fileNameContainer.setText("�t�@�C����");
		fileNameContainer.add(fileName);
		
		fileName.setColumns(32);
		fileName.setMaxLength(1000);
		fileName.setIMEMode(InputSubset.LATIN_DIGITS);
		fileName.setBindPath("FILE_NAME");
		
		fileChoose.setText("�Q��(L)");
		fileChoose.setMnemonic('L');
		
		// �Q�ƃ{�^���ݒ�
		fileNameContainer.add(fileChoose);
		
		// ������
		info.setText("Adobe Reader�̎��s�t�@�C����I�����Ă��������B"
				+ IkenshoConstants.LINE_SEPARATOR
				+ "��(Windows) �� C:\\Program Files\\Adobe\\Reader 11.0\\Reader\\AcroRd32.exe");
		info.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
		
		adobeGroup.add(fileNameContainer, VRLayout.FLOW_RETURN);
		adobeGroup.add(info);
		
		client.add(adobeGroup, VRLayout.NORTH);

		// ----- �d�q�����ݒ�O���[�v
		signPdfGroup.setLayout(new VRLayout());
		((VRLayout)signPdfGroup.getLayout()).setAutoWrap(false);
		signPdfGroup.setText("�d�q�����\�t�g�̐ݒ�");
		
		signUseCheck.setText("�d�q�����@�\���g�p����");
		signUseCheck.setBindPath("USE_SING");
		
		signNameContainer.setText("�t�@�C����");
		signNameContainer.add(signName);
		
		signName.setColumns(32);
		signName.setMaxLength(1000);
		signName.setIMEMode(InputSubset.LATIN_DIGITS);
		signName.setBindPath("SIGN_FILE_NAME");
		
		signChoose.setText("�Q��(R)");
		signChoose.setMnemonic('R');
		
		// �Q�ƃ{�^���ݒ�
		signNameContainer.add(signChoose);
		
		// ������
		signInfo.setText("�d�q�����\�t�g�̎��s�t�@�C����I�����Ă��������B");
		signInfo.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
		
		signPdfGroup.add(signUseCheck, VRLayout.FLOW_RETURN);
		signPdfGroup.add(signNameContainer, VRLayout.FLOW_RETURN);
		signPdfGroup.add(signInfo);
		
		// TODO PDF�����\�t�g�o�׌�ɃR�����g���O���ċ@�\��L����
		//client.add(signPdfGroup, VRLayout.NORTH);
		
		
		// ����{�^��
		submit.setText("�ݒ�(S)");
		submit.setMnemonic('S');
		cancel.setText("�L�����Z��(C)");
		cancel.setMnemonic('C');
		
		btnGrp.add(submit);
		btnGrp.add(cancel);
		
		client.add(btnGrp, VRLayout.SOUTH);

	}
	

	private void init() throws Exception {
		// �E�B���h�E�T�C�Y
		// setSize(new Dimension(480, 160));
		// �E�B���h�E�𒆉��ɔz�u
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		this.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

		// �ݒ�����[�h
		loadSetting();

		// �X�i�b�v�V���b�g�B�e�Ώۂ�ݒ�
		IkenshoSnapshot.getInstance().setRootContainer(contentPane);
	}

	private void event() throws Exception {
		// �ݒ�
		submit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					if (canSubmit()) { // ���̓`�F�b�N
						if (writeSetting()) { // XML�X�V
							closeWindow();
						}
					}
				} catch (Exception ex) {
					IkenshoCommon.showExceptionMessage(ex);
				}
			}
		});

		// �L�����Z��
		cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (canClose()) {
					closeWindow();
				}
			}
		});

		// �Q��
		fileChoose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doSelectFile();
			}
		});
		
		// �d�q�����g�p
		signUseCheck.addItemListener(new ItemListener() {			
			public void itemStateChanged(ItemEvent e) {
				changeSignState();
			}
		});
		
		// �d�q����exe�Q��
		signChoose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doSelecSigntFile();
			}
		});
	
	}
	
	// �d�q�����\�t�g�̓��͐���
	private void changeSignState() {
		boolean select = signUseCheck.isSelected();
		signNameContainer.setEnabled(select);
		signName.setEnabled(select);
		signChoose.setEnabled(select);
	}
	
	

	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			if (canClose()) {
				closeWindow();
			} else {
				return;
			}
		}
		super.processWindowEvent(e);
	}

	protected void closeWindow() {
		this.dispose();
	}

	/**
	 * �o�^�������\���ǂ������`�F�b�N���܂��B
	 * 
	 * @return true:OK, false:NG
	 */
	private boolean canSubmit() {
		// ���̓`�F�b�N
		if (IkenshoCommon.isNullText(fileName.getText())) {
			fileName.requestFocus();
			ACMessageBox.showExclamation("�t�@�C�����������͂ł��B");
			return false;
		}

		// �t�@�C�����݃`�F�b�N
		File tmp = new File(fileName.getText());
		if (!tmp.exists()) {
			fileName.requestFocus();
			ACMessageBox.showExclamation("�t�@�C�������s���ł��B");
			return false;
		}
		
		// �d�q�ؖ��@�\���g�p����ꍇ�̓��̓`�F�b�N
		if (signUseCheck.isSelected()) {
			// ���̓`�F�b�N
			if (IkenshoCommon.isNullText(signName.getText())) {
				signName.requestFocus();
				ACMessageBox.showExclamation("�t�@�C�����������͂ł��B");
				return false;
			}

			// �t�@�C�����݃`�F�b�N
			tmp = new File(signName.getText());
			if (!tmp.exists()) {
				signName.requestFocus();
				ACMessageBox.showExclamation("�t�@�C�������s���ł��B");
				return false;
			}
		}
		
		return true;
	}

	/**
	 * ���Ă悢���ǂ����𔻒肵�܂��B
	 * 
	 * @return boolean
	 */
	private boolean canClose() {
		try {
			if (IkenshoSnapshot.getInstance().isModified()) {
				int result = ACMessageBox.show("�ύX���e���j������܂��B\n��낵���ł����H",
						ACMessageBox.BUTTON_OKCANCEL,
						ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
				if (result == ACMessageBox.RESULT_OK) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	/**
	 * �ݒ�t�@�C������l���擾���܂��B
	 * 
	 * @param key
	 *            String
	 * @return String
	 */
	private String getProperty(String key) {
		String value = "";
		try {
			// �^�O�̑��݃`�F�b�N
			if (!ACFrame.getInstance().hasProperty(key)) {
				return value;
			}
			
			value = IkenshoCommon.getProperity(key);
		} catch (Exception ex) {
			IkenshoCommon.showExceptionMessage(ex);
		}
		return value;
	}

	/**
	 * �ݒ�����[�h���܂��B
	 * 
	 * @throws Exception
	 */
	private void loadSetting() throws Exception {
		// �t�@�C���p�X
		fileName.setText(getProperty("Acrobat/Path").toLowerCase());
		
		// �d�q�����t�@�C���p�X
		signName.setText(getProperty("SignPDF/Path").toLowerCase());
		
		// �d�q�����p��exe�̃p�X�L���ŁA�@�\�g�p�𔻒�
		if (IkenshoCommon.isNullText(signName.getText())) {
			signUseCheck.setSelected(false);
		} else {
			signUseCheck.setSelected(true);
		}
		
		changeSignState();
	}

	/**
	 * �t�@�C����I�����܂��B
	 */
	private void doSelectFile() {
		
		File current = new File(getProperty("Acrobat/Path"));
		File selectedFile = getSelectFilePath(current, "Adobe acrobat(*.exe)");
		
		if (selectedFile == null) {
			return;
		}
		
		// �p�X����ʂɔ��f
		fileName.setText(selectedFile.getPath());
	}
	
	
	/**
	 * �d�q�����p��exe��I�����܂��B
	 */
	private void doSelecSigntFile() {
		File current = new File(getProperty("SignPDF/Path"));
		File selectedFile = getSelectFilePath(current, "�d�q�����\�t�g(*.exe)");
		
		if (selectedFile == null) {
			return;
		}
		
		// �p�X����ʂɔ��f
		signName.setText(selectedFile.getPath());
	}
	
	private File getSelectFilePath(File current, String description) {
		// �t�@�C���w��
		ACFileChooser fileChooser = new ACFileChooser();
		ACFileFilter filter = new ACFileFilter();
		filter.setFileExtensions(new String[] { "exe" });
		//filter.setDescription("Adobe acrobat(*.exe)");
		filter.setDescription(description);

		//File fileOld = new File(getProperty("Acrobat/Path"));
		File file = null;
		boolean loopFlg = false;
		do {
			loopFlg = false;

			// �t�@�C���I��
			file = fileChooser.showOpenDialog(current.getParent(),
					current.getName(), "�J��", filter);

			// �L�����Z�����A���f
			if (file == null) {
				return null;
			}

			// �g���q��⊮
			file = new File(
					file.getParent()
							+ separator
							+ ((ACFileFilter) fileChooser.getFileFilter())
									.getFilePathWithExtension(file.getName()));

			// �t�@�C�����݃`�F�b�N
			if (!file.exists()) {
				ACMessageBox.showExclamation("�t�@�C�������s���ł��B");
				loopFlg = true;
				continue;
			}
		} while (loopFlg);

		return file;
	}

	/**
	 * �ݒ���������݂܂��B
	 * 
	 * @return boolean
	 */
	private boolean writeSetting() {
		try {
			ACFrame.getInstance().getPropertyXML()
					.setForceValueAt("Acrobat/Path", fileName.getText());
			
			String signExePath = "";
			// �d�q�ؖ��@�\�g�p�̏ꍇ�̓p�X��ݒ�
			// �g�p���Ȃ��ꍇ�͋󔒕ۑ�
			if (signUseCheck.isSelected()) {
				signExePath = signName.getText();
			}
			
			ACFrame.getInstance().getPropertyXML().setForceValueAt("SignPDF/Path", signExePath);
			
			// 2006/02/12[Tozo Tanaka] : replace begin
			// ACFrame.getInstance().getProperityXML().write();
			if (!ACFrame.getInstance().getPropertyXML().writeWithCheck()) {
				return false;
			}
			// 2006/02/12[Tozo Tanaka] : replace end
		} catch (Exception ex) {
			// ��O�����itry�����ɌĂяo�������\�b�h��throws���Ă��悢�j
			IkenshoCommon.showExceptionMessage(ex);
		}

		return true;
	}
}
