/*
 * Project code name "ORCA"
 * 給付管理台帳ソフト QKANCHO（JMA care benefit management software）
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "QKANCHO (JMA care benefit management software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * アプリ: QKANCHO
 * 開発者: 藤原　伸
 * 作成日: 2012/09/20  日本コンピューター株式会社 藤原　伸 新規作成
 * 更新日: ----/--/--
 * システム 主治医意見書 (I)
 * サブシステム ログ (L)
 * プロセス ログビューア (001)
 * プログラム ログビューア (IL001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.viewer.il.il001;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.logging.VRLogger;
import jp.or.med.orca.ikensho.affair.IkenshoBrowseLogger;
import jp.or.med.orca.ikensho.affair.IkenshoBrowseLoggerFormatter;
import jp.or.med.orca.ikensho.affair.IkenshoFrameEventProcesser;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * ログビューア(IL001)
 */
public class IL001 extends IL001Event {
	
	private static final long serialVersionUID = 1L;
	/**
	 * コンストラクタです。
	 */
	public IL001() {
	}

	public void initAffair(ACAffairInfo affair) throws Exception {
		super.initAffair(affair);
		initAction(affair);
	}

	/**
	 * 初期化処理を行ないます。
	 * 
	 * @param affair
	 *            業務情報
	 * @throws Exception
	 *             処理例外
	 */
	protected void initAction(ACAffairInfo affair) throws Exception {
		
		//データベース接続オブジェクトを設定
		setDBManager(new IkenshoFirebirdDBManager());
		
		// DB接続確認 DB接続可能かどうか
        if (canConnect()) {
        	//DB接続が可能であれば、canDBConnectにtrue
            setCanDBConnect(true);
            
        	//canDBConnectがtrueの場合、データベースから業務名称を取得する
        	initAffairNameMap();
        	
        } else {
        	//不可能であればcanDBConnectにfalse
            setCanDBConnect(false);
        }
        
		// ログファイルのリストを取得する
        initLogFileNameList();
        
		// ログファイルが存在しない場合
        if (getLogFileList().getDataSize() == 0) {
        	// ボタンが押せない状態に変更
        	setState_MOVE_NONE();
        	
        	// ログファイルが存在しないメッセージを表示し、処理を中断する。
        	ACMessageBox.show(MESSAGE_LOG_NOT_FOUND, ACMessageBox.BUTTON_OK , ACMessageBox.ICON_INFOMATION);
        	return;
        }
        
        //readLogFileNumberを0にする。
        setReadLogFileNumber(0);
        
		// ログファイル数の表示を変更する
        getHeaderMap().setData(FILE_COUNT, ACCastUtilities.toString(getLogFileList().getDataSize()));
        
		// 画面上のログファイル数を上記処理で取得した個数で更新する。
        getFileInfoGroup().setSource(getHeaderMap());
        getFileInfoGroup().bindSource();
        
        // ログ表示テーブルの初期化
        String[] logDataTableSchema = new String[] {
    		LOG_DATETIME,
    		USER_NAME,
    		AFFAIR_ID,
    		AFFAIR_NAME,
    		PATIENT_NAME,
    		PATIENT_NAME_KANA
        };

        // 格納
        ACTableModelAdapter logDataTableModel = new ACTableModelAdapter();
        logDataTableModel.setColumns(logDataTableSchema);

        // csvDataTableにcsvDataTableModelをセットする
        getLogDataTable().setModel(logDataTableModel);

        // モデルにデータをセットする
        logDataTableModel.setAdaptee(getLogFileContentList());
        
		// テーブルへログファイルの表示を行う。
		readLogFile();

	}

	public boolean canClose() throws Exception {
		if (!super.canClose()) {
			return false;
		}
        // ※終了処理
        // 終了確認のメッセージを表示する。CSVビューアを終了してもよろしいですか？
        if (ACMessageBox.showOkCancel(MESSAGE_PROGRAM_EXIT) == ACMessageBox.RESULT_OK) {
            // 「OK」選択時
        	//データベース接続終了
        	if (getCanDBConnect()) {
        		getDBManager().releaseAll();
        	}
            // プログラムを終了する。（基盤にtrueを返す）
            return true;
        }
        // 「キャンセル」選択時
        // 処理を中断する。(基盤にfalseを返す)
        return false;
	}

	// コンポーネントイベント

	/**
	 * 「ひとつ前のログファイルを開く」イベントです。
	 * 
	 * @param e
	 *            イベント情報
	 * @throws Exception
	 *             処理例外
	 */
	protected void prevButtonActionPerformed(ActionEvent e) throws Exception {
		// readLogFileNumberを-1する。
		setReadLogFileNumber(getReadLogFileNumber() - 1);
		
		// テーブルへログファイルの表示を行う。
		readLogFile();

	}

	/**
	 * 「次のログファイルを開く」イベントです。
	 * 
	 * @param e
	 *            イベント情報
	 * @throws Exception
	 *             処理例外
	 */
	protected void nextButtonActionPerformed(ActionEvent e) throws Exception {
		// readLogFileNumberを+1する。
		setReadLogFileNumber(getReadLogFileNumber() + 1);
		
		// テーブルへログファイルの表示を行う。
		readLogFile();

	}

	// 内部関数

	/**
	 * 「ログデータ取得」に関する処理を行ないます。
	 * 
	 * @throws Exception
	 *             処理例外
	 */
	public void readLogFile() throws Exception {
		// logFileContentListの内容をクリア
		getLogFileContentList().clear();
		
		// logFileListからreadLogFileNumber番目のファイル名称を取得する。
		File log = (File)getLogFileList().getData(getReadLogFileNumber());
		
		BufferedReader br = null;
		String line = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(log), "UTF-8"));
			
			String[] ary = null;
			int length = 0;
			while((line = br.readLine()) != null) {
				
				//一行をスペースで分割
				ary = line.split(" ");
				
				//木定数未満の分割行は不正とみなしスキップ
				length = ary.length;
				if (length < 4) {
					continue;
				}
				
				//VRMapオブジェクトを生成
				VRMap row = new VRHashMap();
				
				// Array[0] + " " + Array[1]をキーLOG_DATETIMEで、VRMapに設定する。
				row.put(LOG_DATETIME, ary[0] + " " + ary[1]);
				// Array[2]をキーUSER_NAMEで、VRMapに設定する。
				row.put(USER_NAME, ary[2]);
				// Array[3]をキーAFFAIR_IDで、VRMapに設定する。
				row.put(AFFAIR_ID, ary[3]);
				
				// arrairMapから該当の業務名称を取得し、キーAFFAIR_NAMEでVRMapに設定する。
				row.put(AFFAIR_NAME, getAffairMap().get(ary[3]));
				
				
				// canDBConnectがfalseの場合は処理終了
				if (!getCanDBConnect()) {
					continue;
				}
				
				// QkanBrowseLoggerFormatter.AFFAIR_PERSONALにAFFAIR_IDが存在する場合(個人情報を閲覧する業務の場合))
				if (IkenshoBrowseLoggerFormatter.AFFAIR_PERSONAL.contains(ary[3])) {
					
					//利用者の情報を設定する
					setUserInfo(row, ary[length - 1]);
					
				// QkanBrowseLoggerFormatter.AFFAIR_PERSONALにAFFAIR_IDが存在しない場合
				} else {
					// 空白をキーPATIENT_NAMEで、VRMapに設定する。
					row.put(PATIENT_NAME, "");
					// 空白をキーINSURED_IDで、VRMapに設定する。
					row.put(PATIENT_NAME_KANA, "");
				}
				
				//作成したVRMapをlogFileContentListへ追加
				getLogFileContentList().add(row);
			}
			
			
		} catch (Exception e) {
			System.out.println(line);
			throw e;
		} finally {
			if (br != null) {
				br.close();
			}
		}
		
		// ログファイル数の表示を変更する
        getHeaderMap().setData(VIEW_FILE_NUMBER, ACCastUtilities.toString(getReadLogFileNumber() + 1) + "番目");
        getFileInfoGroup().bindSource();

		// logFileListのサイズが1の場合
		if (getLogFileList().size() == 1) {
			// ボタン状態変更
			setState_MOVE_NONE();
			return;
		}
		
		// readLogFileNumberが0の場合
		if (getReadLogFileNumber() == 0) {
			// ボタン状態変更
			setState_MOVE_NEXT_ONLY();
			return;
		}
		
		// readLogFileNumberがlogFileListのサイズ-1の場合
		if (getLogFileList().size() - 1 <= getReadLogFileNumber()) {
			// ボタン状態変更
			setState_MOVE_PREV_ONLY();
			return;
		}
		
		// ボタン状態変更
		setState_MOVE_ALL();

	}

	/**
	 * 「業務名称翻訳初期化」に関する処理を行ないます。
	 * 
	 * @throws Exception
	 *             処理例外
	 */
	public void initAffairNameMap() throws Exception {
		
		//マスタがないので、手動で対応表を作成
		getAffairMap().put("IkenshoPatientList", "登録患者情報一覧");
		getAffairMap().put("IkenshoPatientInfo", "患者最新基本情報");
		getAffairMap().put("IkenshoIkenshoInfoH18", "主治医意見書");
		getAffairMap().put("IkenshoIshiIkenshoInfo", "医師意見書");
		getAffairMap().put("IkenshoHoumonKangoShijisho", "訪問看護指示書");
		getAffairMap().put("IkenshoTokubetsuHoumonKangoShijisho", "特別訪問看護指示書");
		getAffairMap().put("IkenshoSeikyuIchiran", "請求対象意見書一覧");
		getAffairMap().put("IkenshoRenkeiIJyouhouIchiran", "連携医情報一覧");
		getAffairMap().put("IkenshoRenkeiIJyouhouShousai", "連携医詳細情報");
		getAffairMap().put("IkenshoHoumonKangoStationJouhouIchiran", "訪問看護ステーション情報一覧");
		getAffairMap().put("IkenshoHoumonKangoStationJouhouShousai", "訪問看護ステーション情報詳細");
		getAffairMap().put("IkenshoHokenshaIchiran", "保険者一覧");
		getAffairMap().put("IkenshoHokenshaShousai", "保険者詳細");
		getAffairMap().put("IkenshoIryouKikanJouhouIchiran", "医療機関情報一覧");
		getAffairMap().put("IkenshoIryouKikanJouhouShousai", "医療機関情報詳細");
		getAffairMap().put("IkenshoTeikeibunList", "特記事項一覧");
		getAffairMap().put("IkenshoOtherCSVOutput", "「主治医意見書・医師意見書」CSVファイル出力");
		getAffairMap().put("IkenshoReceiptSoftAccess", "レセプトソフト連携");
		
		getAffairMap().put("IL001", "ログファイルビューアー");
	}

	/**
	 * 「ログファイルの名称取得」に関する処理を行ないます。
	 * 
	 * @throws Exception
	 *             処理例外
	 */
	public void initLogFileNameList() throws Exception {
		// QkanBrowseLogger.LOG_FILE + ".[数値]"形式で複数のログファイルが出力されている
		// ログファイルの名称をすべて取得し、logFileListに設定する。
		
		File baseLog = new File(IkenshoBrowseLogger.LOG_FILE);
		File logDir = baseLog.getParentFile();
		
		if (!logDir.isDirectory()) {
			VRLogger.warning("ログ出力フォルダの取得に失敗しました。");
			return;
		}
		
		VRList list = new VRArrayList();
		String baseLogName = baseLog.getName();
		
		File f = null;
		for (int i = 0; i < logDir.listFiles().length; i++) {
			f = logDir.listFiles()[i];
			
			String fileName = f.getName();
			
			//指定のログファイル名で始まっているか
			if (!fileName.startsWith(baseLogName)) {
				continue;
			}
			
			//lckファイルは除外する
			if (fileName.endsWith("lck")) {
				continue;
			}
			
			//該当ログファイルを退避
			list.add(f);
		}
		
		setLogFileList(list);
	}

	/**
	 * 「ユーザー情報の設定」に関する処理を行ないます。
	 * 
	 * @throws Exception
	 *             処理例外
	 */
	public void setUserInfo(VRMap row, String patient_id) throws Exception {
		
		VRMap info = null;
		
		//patientMapに既に退避したpatient_idの情報がある場合
		if (getPatientMap().containsKey(patient_id)) {
			//Mapの内容を設定する
			info = (VRMap)getPatientMap().get(patient_id);
			row.putAll(info);
			return;
		}
		
		//patientMapに既に退避したpatient_idの情報がない場合
		//patient_idが数値であるか確認　数値では無い場合は処理終了
		if (ACCastUtilities.toInt(patient_id, -1) == -1) {
			return;
		}
		
		
		//個人情報取得用のSQL文を作成する
		VRMap param = new VRHashMap();
		param.put("PATIENT_NO", patient_id);
		
		//作成したSQL文を実行する。
		VRList list = getDBManager().executeQuery(getSQL_GET_PATIENT_INFO(param));
		info = new VRHashMap();
		
		//データが取得できない場合は、"不明"を設定
		if (list.size() == 0) {
			info.put(PATIENT_NAME, "不明");
			info.put(PATIENT_NAME_KANA, "不明");
			
		} else {
			VRMap map = (VRMap)list.get(0);
			info.put(PATIENT_NAME, map.get("PATIENT_NM"));
			info.put(PATIENT_NAME_KANA, map.get("PATIENT_KN"));
		}
		
		getPatientMap().put(patient_id, info);
		row.putAll(info);

	}
	
    private boolean canConnect() {
        try {
            // 通信テスト
            if (getDBManager().isAvailableDBConnection()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
        return false;
    }

	public static void main(String[] args) {

		try {
			ACFrame.setVRLookAndFeel();
			ACFrameEventProcesser processer = new IkenshoFrameEventProcesser();
			ACFrame.getInstance().setFrameEventProcesser(processer);
			ACFrame.getInstance().next(new ACAffairInfo(IL001.class.getName()));
			ACFrame.getInstance().setTitle(MESSAGE_PROGRAM_TITLE);
			ACFrame.getInstance().setExtendedState(Frame.MAXIMIZED_BOTH);
			ACFrame.getInstance().setVisible(true);
		} catch (Exception ex) {
			VRLogger.info(ex);
		}
	}
}
