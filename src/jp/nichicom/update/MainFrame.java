package jp.nichicom.update;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.util.ArrayList;
import java.io.*;

import jp.nichicom.update.task.AbstractTask;
import jp.nichicom.update.task.TaskProcesser;
import jp.nichicom.update.task.TaskXMLParser;
import jp.nichicom.update.util.HttpConnecter;
import jp.nichicom.update.util.Log;
import jp.nichicom.update.util.PropertyUtil;
import jp.nichicom.update.util.XMLDocumentUtil;

/**
 * 自動更新ツール
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class MainFrame extends JFrame implements ActionListener {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
	 * プログレスバー
	 */
	private JProgressBar progress;
	private JLabel status;

    private String lineSeparator = System.getProperty("line.separator");

	/**
	 * LookAndFeelの設定
	 */
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable tw) { }
	}

	/**
	 * アップデートツールの実行
	 * @param args 起動時オプション
	 */
	public static void main(String[] args) {
		// 自分自身のインスタンス化
	    JFrame frame = new MainFrame();
		// フレームのセットアップ
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 終了時処理
		//画面のサイズを決定します。
		frame.setSize(new Dimension(500, 110));
		// 画面位置の設定を行います。
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds( (screenSize.width - frame.getWidth()) / 2,
			(screenSize.height - frame.getHeight()) /
			2, frame.getWidth(), frame.getHeight());
		// 可視化（リアライズ）
		frame.setVisible(true);
	}

	// コンストラクタ
	public MainFrame() {
		// スーパークラスのコンストラクタ呼び出し
		super("アップデートツール");
		
		//プロキシサーバ名称を設定する
		HttpConnecter.setProxyHost(PropertyUtil.getProperty("http.proxyHost"));
		//プロキシのポート番号を設定する。
		HttpConnecter.setProxyPort(PropertyUtil.getProperty("http.proxyPort"));
		//プロキシを使わないサーバの設定
		HttpConnecter.setNonProxyHosts(PropertyUtil.getProperty("http.nonProxyHosts"));
		//ログレベルの設定
		Log.setLogLevel(PropertyUtil.getProperty("log.level"));

		//実行ボタンの作成
		JButton update = new JButton("アップデート実行(U)");
		update.setMnemonic('U');
		update.setActionCommand("update");
		update.addActionListener(this);

		//プロキシボタンの作成
		JButton proxy = new JButton("プロキシ設定(P)");
		proxy.setMnemonic('P');
		proxy.setActionCommand("proxy");
		proxy.addActionListener(this);
		
		// 作成ボタンの作成
		JButton cancel = new JButton("終了(E)");
		cancel.setMnemonic('E');
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);;
	
		// パネルに部品を追加
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
		panelBottom.add(status);
        status.setHorizontalAlignment(SwingConstants.CENTER);
		
		// コンテント・ペインの取得
		Container cnt = getContentPane();
		cnt.setLayout(new BorderLayout());
		// コンテント・ペインに部品を追加
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
	 * ボタン押下時のイベント処理
	 */
	public void actionPerformed(ActionEvent ae) {
		if ("update".equals(ae.getActionCommand())) {
            //確認メッセージ表示処理を追加 2006.04.16
            if(JOptionPane.showConfirmDialog(this,"アップデート処理は長時間にわたる場合があります。\n（処理中に画面が反応しなくなることもあります。）\n実行してもよろしいですか？","アップデートツール",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION){
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
     * XMLファイルで記述されたアップデートタスクを実行する
     *
     */
    private void getTaskXML(){
        
        setEnabledButtons(getContentPane().getComponents(),false);
        try{
            
//            XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("update.url"));
//            ArrayList taskArray = doc.parseTask();
 
            status.setText("アップデート内容を取得中..");
            TaskXMLParser parser = new TaskXMLParser(PropertyUtil
                    .getProperty("update.url"));
            parser.start();
            while (parser.isRun()) {
                ((JComponent) this.getContentPane())
                        .paintImmediately(((JComponent) this.getContentPane())
                                .getVisibleRect());
                Thread.sleep(500);
            }
            if (parser.getRunException() != null) {
                throw parser.getRunException();
            }
            ArrayList taskArray = parser.getTaskArray();

            if ((taskArray == null) || (taskArray.isEmpty())) {
                JOptionPane.showConfirmDialog(this, "既に最新バージョンです。",
                        "アップデートツール", JOptionPane.CLOSED_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
            } else {

                TaskProcesser tp = new TaskProcesser(taskArray, this, progress,
                        status);
                tp.start();
                while (tp.isRun()) {
                    ((JComponent) this.getContentPane())
                            .paintImmediately(((JComponent) this
                                    .getContentPane()).getVisibleRect());
                    Thread.sleep(500);
                }

                if (tp.getRunException() != null) {
                    throw tp.getRunException();
                }

                // 更新処理実行
                if (tp.isUpdate()) {
                    JOptionPane.showConfirmDialog(this, "アップデートが完了しました。",
                            "アップデートツール", JOptionPane.CLOSED_OPTION,
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showConfirmDialog(this, "既に最新バージョンです。",
                            "アップデートツール", JOptionPane.CLOSED_OPTION,
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch(Exception e){
            Log.warning("アップデート実行エラー:" + e.getLocalizedMessage());
            JOptionPane.showConfirmDialog(this, "アップデート実行時にエラーが発生しました。"
                    + lineSeparator + "処理を続行できません。" + lineSeparator
                    + "※インターネットに接続可能であるかご確認ください。", "アップデートツール",
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
//    /**
//	 * XMLファイルで記述されたアップデートタスクを実行する
//	 *
//	 */
//	private void getTaskXML(){
//		
//		try{
//			boolean update = false;
//			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("update.url"));
//			ArrayList taskArray = doc.parseTask();
//			progress.setMaximum(taskArray.size() - 1);
//			
//			for(int i = 0; i < taskArray.size(); i++){
//				AbstractTask task = (AbstractTask)taskArray.get(i);
//				if(task.runTask()){
//					update = true;
//				}
//				progress.setValue(i);
//				progress.paintImmediately(progress.getVisibleRect());
//			}
//			//更新処理実行
//			if(update){
//				JOptionPane.showConfirmDialog(this,"アップデートが完了しました。","アップデートツール",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
//			} else {
//				JOptionPane.showConfirmDialog(this,"既に最新バージョンです。","アップデートツール",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
//			}
//		} catch(Exception e){
//			Log.warning("アップデート実行エラー:" + e.getLocalizedMessage());
//			JOptionPane.showConfirmDialog(this,"アップデート実行時にエラーが発生しました。\n処理を続行できません。","アップデートツール",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
//		}
//		progress.setValue(0);
//	}
}
