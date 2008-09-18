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
 * プロキシサーバ設定画面
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class ProxySelect  extends JDialog implements ActionListener {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
	 * プロキシサーバ名の入力
	 */
	JTextField server = new JTextField(20);
	/**
	 * プロキシポートの入力
	 */
	JTextField port = new JTextField(20);
	
	/**
	 * LookAndFeelを設定
	 */
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable tw) { }
	}
	
	/**
	 * コンストラクタ
	 */
	public ProxySelect(JFrame frame) {
		// スーパークラスのコンストラクタ呼び出し
		super(frame,true);
		setTitle("プロキシサーバの設定");

		//実行ボタンの作成
		JButton commit = new JButton("設定(E)");
		commit.setMnemonic('E');
		commit.setActionCommand("commit");
		commit.addActionListener(this);

		// 作成ボタンの作成
		JButton cancel = new JButton("キャンセル(C)");
		cancel.setMnemonic('C');
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);;
	
		// パネルに部品を追加
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panel2 = new JPanel(new FlowLayout());
		JPanel panelButton = new JPanel(new FlowLayout());
		panelButton.add(commit);
		panelButton.add(cancel);
		
		panel2.add(new JLabel("サーバ名："));
		panel2.add(server);
		panel.add(panel2,BorderLayout.NORTH);
		panel2 = new JPanel();
		panel2.add(new JLabel("ポートNo："));
		panel2.add(port);
		panel.add(panel2,BorderLayout.SOUTH);
		
		server.setText(PropertyUtil.getProperty("http.proxyHost"));
		port.setText(PropertyUtil.getProperty("http.proxyPort"));
	
		// コンテント・ペインの取得
		Container cnt = getContentPane();
		// コンテント・ペインに部品を追加
		cnt.add(panel, BorderLayout.NORTH);
		cnt.add(panelButton);
		
		//画面のサイズを決定します。
		setSize(new Dimension(300, 150));
		// 画面位置の設定を行います。
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds( (screenSize.width - getWidth()) / 2,
			(screenSize.height - getHeight()) /
			2, getWidth(), getHeight());
        pack();
	}
	
	/**
	 * ボタン押下時の処理
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if("commit".equals(command)){
			PropertyUtil.setProperty("http.proxyHost",server.getText().trim());
			PropertyUtil.setProperty("http.proxyPort",port.getText().trim());
			//プロキシサーバ名称を設定する
			HttpConnecter.setProxyHost(server.getText().trim());
			//プロキシのポート番号を設定する。
			HttpConnecter.setProxyPort(port.getText().trim());
		}
		dispose();
	}

}
