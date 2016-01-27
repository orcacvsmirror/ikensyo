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

	// ベースパネル
	private JPanel contentPane;
	private VRPanel client = new VRPanel();

	// ファイルパス指定
	private ACGroupBox adobeGroup = new ACGroupBox();
	private ACLabelContainer fileNameContainer = new ACLabelContainer();
	private ACTextField fileName = new ACTextField();
	private ACButton fileChoose = new ACButton();
	// 説明文
	private ACLabel info = new ACLabel();
	
	
	// 電子署名用PDFパス指定
	private ACGroupBox signPdfGroup = new ACGroupBox();
	private ACCheckBox signUseCheck = new ACCheckBox();
	private ACLabelContainer signNameContainer = new ACLabelContainer();
	private ACTextField signName = new ACTextField();
	private ACButton signChoose = new ACButton();
	// 電子署名説明文
	private ACLabel signInfo = new ACLabel();
	

	// 設定/キャンセルボタン
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

			// スナップショット撮影
			IkenshoSnapshot.getInstance().snapshot();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public IkenshoSettingPDF() {
		this(ACFrame.getInstance(), "PDFの設定", true);
	}

	private void jbInit() {
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(client, BorderLayout.CENTER);
		client.setLayout(new VRLayout());

		// ----- Adobe Reader 設定グループ
		// Adobe Readerの設定
		adobeGroup.setLayout(new VRLayout());
		((VRLayout)adobeGroup.getLayout()).setAutoWrap(false);
		adobeGroup.setText("Adobe Readerの設定");

		fileNameContainer.setText("ファイル名");
		fileNameContainer.add(fileName);
		
		fileName.setColumns(32);
		fileName.setMaxLength(1000);
		fileName.setIMEMode(InputSubset.LATIN_DIGITS);
		fileName.setBindPath("FILE_NAME");
		
		fileChoose.setText("参照(L)");
		fileChoose.setMnemonic('L');
		
		// 参照ボタン設定
		fileNameContainer.add(fileChoose);
		
		// 説明文
		info.setText("Adobe Readerの実行ファイルを選択してください。"
				+ IkenshoConstants.LINE_SEPARATOR
				+ "例(Windows) → C:\\Program Files\\Adobe\\Reader 11.0\\Reader\\AcroRd32.exe");
		info.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
		
		adobeGroup.add(fileNameContainer, VRLayout.FLOW_RETURN);
		adobeGroup.add(info);
		
		client.add(adobeGroup, VRLayout.NORTH);

		// ----- 電子署名設定グループ
		signPdfGroup.setLayout(new VRLayout());
		((VRLayout)signPdfGroup.getLayout()).setAutoWrap(false);
		signPdfGroup.setText("電子署名ソフトの設定");
		
		signUseCheck.setText("電子署名機能を使用する");
		signUseCheck.setBindPath("USE_SING");
		
		signNameContainer.setText("ファイル名");
		signNameContainer.add(signName);
		
		signName.setColumns(32);
		signName.setMaxLength(1000);
		signName.setIMEMode(InputSubset.LATIN_DIGITS);
		signName.setBindPath("SIGN_FILE_NAME");
		
		signChoose.setText("参照(R)");
		signChoose.setMnemonic('R');
		
		// 参照ボタン設定
		signNameContainer.add(signChoose);
		
		// 説明文
		signInfo.setText("電子署名ソフトの実行ファイルを選択してください。");
		signInfo.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
		
		signPdfGroup.add(signUseCheck, VRLayout.FLOW_RETURN);
		signPdfGroup.add(signNameContainer, VRLayout.FLOW_RETURN);
		signPdfGroup.add(signInfo);
		
		// TODO PDF署名ソフト出荷後にコメントを外して機能を有効化
		//client.add(signPdfGroup, VRLayout.NORTH);
		
		
		// 操作ボタン
		submit.setText("設定(S)");
		submit.setMnemonic('S');
		cancel.setText("キャンセル(C)");
		cancel.setMnemonic('C');
		
		btnGrp.add(submit);
		btnGrp.add(cancel);
		
		client.add(btnGrp, VRLayout.SOUTH);

	}
	

	private void init() throws Exception {
		// ウィンドウサイズ
		// setSize(new Dimension(480, 160));
		// ウィンドウを中央に配置
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

		// 設定をロード
		loadSetting();

		// スナップショット撮影対象を設定
		IkenshoSnapshot.getInstance().setRootContainer(contentPane);
	}

	private void event() throws Exception {
		// 設定
		submit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					if (canSubmit()) { // 入力チェック
						if (writeSetting()) { // XML更新
							closeWindow();
						}
					}
				} catch (Exception ex) {
					IkenshoCommon.showExceptionMessage(ex);
				}
			}
		});

		// キャンセル
		cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (canClose()) {
					closeWindow();
				}
			}
		});

		// 参照
		fileChoose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doSelectFile();
			}
		});
		
		// 電子署名使用
		signUseCheck.addItemListener(new ItemListener() {			
			public void itemStateChanged(ItemEvent e) {
				changeSignState();
			}
		});
		
		// 電子署名exe参照
		signChoose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doSelecSigntFile();
			}
		});
	
	}
	
	// 電子署名ソフトの入力制御
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
	 * 登録処理が可能かどうかをチェックします。
	 * 
	 * @return true:OK, false:NG
	 */
	private boolean canSubmit() {
		// 入力チェック
		if (IkenshoCommon.isNullText(fileName.getText())) {
			fileName.requestFocus();
			ACMessageBox.showExclamation("ファイル名が未入力です。");
			return false;
		}

		// ファイル存在チェック
		File tmp = new File(fileName.getText());
		if (!tmp.exists()) {
			fileName.requestFocus();
			ACMessageBox.showExclamation("ファイル名が不正です。");
			return false;
		}
		
		// 電子証明機能を使用する場合の入力チェック
		if (signUseCheck.isSelected()) {
			// 入力チェック
			if (IkenshoCommon.isNullText(signName.getText())) {
				signName.requestFocus();
				ACMessageBox.showExclamation("ファイル名が未入力です。");
				return false;
			}

			// ファイル存在チェック
			tmp = new File(signName.getText());
			if (!tmp.exists()) {
				signName.requestFocus();
				ACMessageBox.showExclamation("ファイル名が不正です。");
				return false;
			}
		}
		
		return true;
	}

	/**
	 * 閉じてよいかどうかを判定します。
	 * 
	 * @return boolean
	 */
	private boolean canClose() {
		try {
			if (IkenshoSnapshot.getInstance().isModified()) {
				int result = ACMessageBox.show("変更内容が破棄されます。\nよろしいですか？",
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
	 * 設定ファイルから値を取得します。
	 * 
	 * @param key
	 *            String
	 * @return String
	 */
	private String getProperty(String key) {
		String value = "";
		try {
			// タグの存在チェック
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
	 * 設定をロードします。
	 * 
	 * @throws Exception
	 */
	private void loadSetting() throws Exception {
		// ファイルパス
		fileName.setText(getProperty("Acrobat/Path").toLowerCase());
		
		// 電子署名ファイルパス
		signName.setText(getProperty("SignPDF/Path").toLowerCase());
		
		// 電子署名用のexeのパス有無で、機能使用を判定
		if (IkenshoCommon.isNullText(signName.getText())) {
			signUseCheck.setSelected(false);
		} else {
			signUseCheck.setSelected(true);
		}
		
		changeSignState();
	}

	/**
	 * ファイルを選択します。
	 */
	private void doSelectFile() {
		
		File current = new File(getProperty("Acrobat/Path"));
		File selectedFile = getSelectFilePath(current, "Adobe acrobat(*.exe)");
		
		if (selectedFile == null) {
			return;
		}
		
		// パスを画面に反映
		fileName.setText(selectedFile.getPath());
	}
	
	
	/**
	 * 電子署名用のexeを選択します。
	 */
	private void doSelecSigntFile() {
		File current = new File(getProperty("SignPDF/Path"));
		File selectedFile = getSelectFilePath(current, "電子署名ソフト(*.exe)");
		
		if (selectedFile == null) {
			return;
		}
		
		// パスを画面に反映
		signName.setText(selectedFile.getPath());
	}
	
	private File getSelectFilePath(File current, String description) {
		// ファイル指定
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

			// ファイル選択
			file = fileChooser.showOpenDialog(current.getParent(),
					current.getName(), "開く", filter);

			// キャンセル時、中断
			if (file == null) {
				return null;
			}

			// 拡張子を補完
			file = new File(
					file.getParent()
							+ separator
							+ ((ACFileFilter) fileChooser.getFileFilter())
									.getFilePathWithExtension(file.getName()));

			// ファイル存在チェック
			if (!file.exists()) {
				ACMessageBox.showExclamation("ファイル名が不正です。");
				loopFlg = true;
				continue;
			}
		} while (loopFlg);

		return file;
	}

	/**
	 * 設定を書き込みます。
	 * 
	 * @return boolean
	 */
	private boolean writeSetting() {
		try {
			ACFrame.getInstance().getPropertyXML()
					.setForceValueAt("Acrobat/Path", fileName.getText());
			
			String signExePath = "";
			// 電子証明機能使用の場合はパスを設定
			// 使用しない場合は空白保存
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
			// 例外処理（tryせずに呼び出し元メソッドにthrowsしてもよい）
			IkenshoCommon.showExceptionMessage(ex);
		}

		return true;
	}
}
