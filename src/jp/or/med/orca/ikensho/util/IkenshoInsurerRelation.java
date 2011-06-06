package jp.or.med.orca.ikensho.util;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JTextField;

import jp.nichicom.ac.component.event.ACFollowContainerFormatEventListener;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.text.ACSQLSafeStringFormat;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.bridge.sql.BridgeFirebirdDBManager;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.event.VRFormatEvent;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

/**
 *****************************************************************
 * アプリ: Ikensho
 * 開発者: 樋口　雅彦
 * 作成日: 2009  日本コンピューター株式会社 樋口 雅彦 新規作成
 * @since V3.0.9
 *
 *****************************************************************
 */
public class IkenshoInsurerRelation implements  KeyListener, FocusListener, VRBindEventListener {
    // DB
    private ACDBManager dbManager;
    
    private boolean convertNo = false;
    private boolean convertName = false;
    private boolean checkNo = false;
    private boolean checkName = false;
    
    private boolean isCommitInsurer = false;
    
    // テキストフィールド
    private JTextField insurerNoText;
    private JTextField insurerNameText;
    // テキスト文字列の保持用
    private String insurerNoValue;
    private String insurerNameValue;
    // 突合結果
    private VRList validInsurerListToNo;
    private VRList validInsurerListToName;
    
    private ACDBManager insurerDbm;
    // フォーマットイベントリスナーの生成
    private ACFollowContainerFormatEventListener noFollowFormatListener = new ACFollowContainerFormatEventListener(
            true);
    private ACFollowContainerFormatEventListener nameFollowFormatListener = new ACFollowContainerFormatEventListener(
            true);
    
    /**
     * 
     */
    public IkenshoInsurerRelation() {
       super(); 
    }
    
    /**
     * コンストラクタ
     * 
     * @param insurerNoText 保険者番号テキスト
     * @param insurerNameText 保険者名称テキスト
     * @param isChangeNo 保険者番号から保険者名称に変換するか
     * @param isChangeName 保険者名称から保険者番号に変換するか
     * @param isCheckNo 保険者番号のチェックを行うか。
     * @param isCheckName 保険者名称のチェックを行うか。
     */
    public IkenshoInsurerRelation(ACDBManager insurerDbm,
            JTextField insurerNoText, JTextField insurerNameText,
            boolean isChangeNo, boolean isChangeName, boolean isCheckNo,
            boolean isCheckName) throws Exception {
        super();
        // データベースの設定
        setInsurerDbm(insurerDbm);
        // 最初は全て正常としておく
        if (insurerNoText != null) {
            validDataNo(insurerNoText, insurerNoText.getText());
        }
        if (insurerNameText != null) {
            validDataName(insurerNameText, insurerNameText.getText());
        }
        // データ設定
        buildData(insurerNoText, insurerNameText, isChangeNo, isChangeName ,isCheckNo , isCheckName);
    }
    
    /**
     * 確認用のデータ作成を行います。
     * 
     * @param insurerNoText 保険者番号テキスト
     * @param insurerNameText 保険者名称テキスト
     * @param isChangeNo 保険者番号から保険者名称に変換するか
     * @param isChangeName 保険者名称から保険者番号に変換するか
     * @param isCheckNo 保険者番号のチェックを行うか。
     * @param isCheckName 保険者名称のチェックを行うか。
     * @throws Exception
     */
    public void buildData(JTextField insurerNoText, JTextField insurerNameText,
            boolean isChangeNo, boolean isChangeName , boolean isCheckNo, boolean isCheckName) throws Exception {
        // 必要変数の設定
        setInsurerNoText(insurerNoText);
        setInsurerNameText(insurerNameText);
        setConvertNo(isChangeNo);
        setConvertName(isChangeName);
        setCheckNo(isCheckNo);
        setCheckName(isCheckName);
        // SQLにより結果を取得します。
        buildInsurerList();
        
    }

    /**
     * 指定された区分を元にチェックを行います。
     * 
     * @return
     * @throws Exception
     */
    public boolean isValidInsurer() throws Exception{
        boolean resultNo = false;
        boolean resultName = false;
        // データの再設定（クラス生成後の再チェックに対応）
        buildData(getInsurerNoText(), getInsurerNameText(), isConvertNo(),
                isConvertName(), isCheckNo(), isCheckName());
        // 結果が空ならエラーにする。
        if(isConvertNo() && getValidInsurerListToNo().isEmpty()) {
            return false;
        }
        if(isConvertName() && getValidInsurerListToName().isEmpty()) {
            return false; 
        }
        
        if(isConvertName()) {
            // サイズ1件。。。広域の場合に複数存在するケースあり
            if(getValidInsurerListToNo().size() == 1) {
                VRMap insurerRecord = (VRMap)getValidInsurerListToNo().getData(0);
                String masterName = ACCastUtilities.toString(insurerRecord
                        .getData("INSURER_NAME"), "");
                // 画面の保険者名称とマスタから取得した保険者名称が一致する場合
                if(masterName.equals(getInsurerNameValue())) {
                    resultName = true;
                } else {
                    // 一致しない場合はエラー
                    resultName = false;
                }
            } else {
                resultName = false;
            }
        }
        
        if(isConvertNo()) {
            // 名称は空でないか
            if(getValidInsurerListToName().size() == 1) {
                VRMap insurerRecord = (VRMap)getValidInsurerListToName().getData(0);
                String masterNo = ACCastUtilities.toString(insurerRecord
                        .getData("INSURER_NO"), "");
                if(masterNo.equals(getInsurerNoValue())) {
                    resultNo = true;
                } else {
                    // 一致しない場合はエラー
                    resultNo = false;
                }
            } else {
                resultNo = false;
            }
        }
        // 双方のチェックを行う場合
        if(isConvertName() && isConvertNo()) {
            return (resultName && resultNo);
        }
        
        // 結果の返却
        if(isConvertName()) {
            return resultName;
            
        } else if(isConvertNo()) {
            return resultNo;
            
        } else {
            return false;
            
        }
    }
    
    /**
     * 突合結果の作成
     * 
     * @throws Exception
     */
    protected void buildInsurerList() throws Exception {
        if(getInsurerDbm() != null) {
            // マスタ突合用のSQL
            // 結果の格納（無駄なSQL発行は防止）
            if(isConvertNo() && !"".equals(getInsurerNoValue())) {
                StringBuffer sbNoSql = new StringBuffer();
                sbNoSql.append("SELECT ");
                sbNoSql.append("M_INSURER.INSURER_NAME ");
                sbNoSql.append("FROM ");
                sbNoSql.append("M_INSURER ");
                sbNoSql.append("WHERE ");
                sbNoSql.append("M_INSURER.INSURER_NO =");
                sbNoSql.append(ACSQLSafeStringFormat.getInstance().format(getInsurerNoValue()));
                sbNoSql.append(" ORDER BY ");
                sbNoSql.append("M_INSURER.INSURER_NAME_KANA ");
                sbNoSql.append("ASC");
                
                // Noを基準に突合するSQL
                setValidInsurerListToNo(getInsurerDbm().executeQuery(sbNoSql.toString()));
            } else {
                setValidInsurerListToNo(new VRArrayList());
            }
            
            if(isConvertName() && !"".equals(getInsurerNameValue()) ) {
                StringBuffer sbNameSql = new StringBuffer();
                sbNameSql.append("SELECT ");
                sbNameSql.append("M_INSURER.INSURER_NO ");
                sbNameSql.append("FROM ");
                sbNameSql.append("M_INSURER ");
                sbNameSql.append("WHERE ");
                sbNameSql.append("M_INSURER.INSURER_NAME =");
                sbNameSql.append(ACSQLSafeStringFormat.getInstance().format(getInsurerNameValue()));
                sbNameSql.append(" ORDER BY ");
                sbNameSql.append("M_INSURER.INSURER_NAME_KANA ");
                sbNameSql.append("ASC");
                
                // Nameを基準に突合するSQL
                setValidInsurerListToName(getInsurerDbm().executeQuery(sbNameSql.toString()));
            } else {
                setValidInsurerListToName(new VRArrayList());
            }
        }
    }
    
    /**
     * 使用するかは謎だが自動サポートする場合の処理
     */
    public void focusGained(FocusEvent e) {

    }
    
    /**
     * フォーカスロストイベント
     */
    public void focusLost(FocusEvent e) {
        // コンポーネントのNullチェック
        if (e == null && getInsurerNoText() == null
                || getInsurerNameText() == null) {
            return;
        }
        
        applyInsurerData(e);
    }
    
    private void applyInsurerData(ComponentEvent e) {
        // 処理用に値を取り出す。
        setInsurerNoValue(getInsurerNoText().getText());
        setInsurerNameValue(getInsurerNameText().getText());

        try{
            // 変換成功かフラグ
            boolean isConverted = false;
            // 入力反映フラグ
            boolean isInput = false;
            // データの検索
            buildData(getInsurerNoText(), getInsurerNameText(), isConvertNo(),
                    isConvertName(), isCheckNo(), isCheckName());
            // イベント元が保険者番号テキストである場合
            if(e.getSource() == getInsurerNoText()) {
                // 入力保険者番号から結果が取得できている場合
                if(!getValidInsurerListToNo().isEmpty()) {
                    VRMap map = (VRMap)getValidInsurerListToNo().getData(0);
                    // 保険者番号からのロストフォーカス以外（キー入力イベント）の場合のみ
                    if (!(e instanceof FocusEvent)){
                        // 変換結果を反映する
                        getInsurerNameText().setText(
                                ACCastUtilities.toString(ACCastUtilities.toString(map
                                                .getData("INSURER_NAME"), "")));
                        isInput = true;
                    }
                    // 変換完了
                    isConverted = true;
                    
                }
                
                if(isCheckNo()) {
                    // 未入力の場合
                    if(getInsurerNoValue().length() == 0) {
                        validDataNo(getInsurerNoText(), getInsurerNoValue());
                        return;
                    } else if(getInsurerNoValue().length() != 6) {
                        // 桁数不正
                        warningDataNo(getInsurerNoText(), getInsurerNoValue());
                        return;
                    }
                    if(isConverted) {
                        // 入力が反映された場合のみ反映先を有効にする。
                        if(isInput) {
                            validDataName(getInsurerNameText(), getInsurerNameValue());
                        }
                        validDataNo(getInsurerNoText(), getInsurerNoValue());
                        
                    } else {
                        // 変換に成功していない
                        warningDataNo(getInsurerNoText(), getInsurerNoValue());
                    }
                    
                } else {
                    // チェックしない場合は、一律正常
                    validDataNo(getInsurerNoText(), getInsurerNoValue());
                }
                return;
                
            }
            // 保険者名称からのロストフォーカスの場合
            if(e.getSource() == getInsurerNameText() && isConvertName()) {
                if(!getValidInsurerListToName().isEmpty()) {
                    VRMap map = (VRMap)getValidInsurerListToName().getData(0);
                    if (!(e instanceof FocusEvent)){
                        // 市町村名⇒番号の変換から先に
                        getInsurerNoText().setText(
                                ACCastUtilities.toString(ACCastUtilities.toString(map
                                                .getData("INSURER_NO"), "")));
                        isInput = true;
                    }
                    isConverted = true;
                    
                }
                
                // チェック処理
                if(isCheckName()) {
                    // 桁数チェック
                    if(getInsurerNameValue().length() == 0) {
                        validDataName(getInsurerNameText(), getInsurerNameValue());
                        return;                
                    }
                    if(isConverted) {
                        // 入力が反映された場合のみ反映先を有効にする。
                        if(isInput) {
                            validDataNo(getInsurerNoText(), getInsurerNoValue());
                        }
                        validDataName(getInsurerNameText(), getInsurerNameValue());
                        
                    } else {
                        // 変換に成功していない
                        warningDataName(getInsurerNameText(), getInsurerNameValue());
                    }
                    
                } else {
                    // チェックしない場合は、一律正常
                    validDataName(getInsurerNameText(), getInsurerNameValue());
                }
                return;
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void applySource(VRBindEvent e) {
    }

    public void bindSource(VRBindEvent e) {
        // バインド時に内部で保持する文字列と入力文字に差異が出ないように反映する
        if (getInsurerNoText() != null) {
            setInsurerNoValue(getInsurerNoText().getText());
            // 空白ならOK
            if("".equals(getInsurerNoValue())) {
                validDataNo(getInsurerNoText(), getInsurerNoValue());
            }
        }
        if (getInsurerNameText() != null) {
            setInsurerNameValue(getInsurerNameText().getText());
            // 空白ならOK
            if("".equals(getInsurerNameValue())) {
                validDataName(getInsurerNameText(), getInsurerNameValue());
            }
        }
    }
    
    /**
     * コンポーネントのコンテナを警告状態にします。
     * 
     * @param comp 対象コンポーネント
     * @param data 設定データ
     */
    public void warningDataNo(Component comp, String data) {
        noFollowFormatListener.formatWarning(new VRFormatEvent(comp, null, data));
    }
    
    /**
     * コンポーネントのコンテナを有効状態にします。
     * 
     * @param comp 対象コンポーネント
     * @param data 設定データ
     */
    public void validDataNo(Component comp, String data) {
        noFollowFormatListener.formatValid(new VRFormatEvent(comp, null, data));
    }
    
    /**
     * コンポーネントのコンテナを警告状態にします。
     * 
     * @param comp 対象コンポーネント
     * @param data 設定データ
     */
    public void warningDataName(Component comp, String data) {
        nameFollowFormatListener.formatWarning(new VRFormatEvent(comp, null, data));
    }
    
    /**
     * コンポーネントのコンテナを有効状態にします。
     * 
     * @param comp 対象コンポーネント
     * @param data 設定データ
     */
    public void validDataName(Component comp, String data) {
        nameFollowFormatListener.formatValid(new VRFormatEvent(comp, null, data));
    }

    /**
     * DBManagerの生成
     * 
     * @return
     * @throws Exception
     */
    public ACDBManager getDbManager() throws Exception{
        if(dbManager == null) {
            dbManager = new BridgeFirebirdDBManager();
        }
        return dbManager;
    }

    /**
     * DBManagerを設定する。
     * @param dbManager
     */
    public void setDbManager(ACDBManager dbManager) {
        this.dbManager = dbManager;
    }

    public ACFollowContainerFormatEventListener getNoFollowFormatListener() {
        if(noFollowFormatListener == null) {
            noFollowFormatListener = new ACFollowContainerFormatEventListener();
        }
        return noFollowFormatListener;
    }

    public void setNoFollowFormatListener(
            ACFollowContainerFormatEventListener followFormatListener) {
        this.noFollowFormatListener = followFormatListener;
    }

    /**
     * 保険者名テキストフィールドを取得します。
     * @return
     */
    public JTextField getInsurerNameText() {
        return insurerNameText;
    }

    /**
     * 保険者名テキスト設定
     * @param insurerNameText
     */
    public void setInsurerNameText(JTextField insurerNameText) {
        // テキストの文字列は保持しておく
        if(getInsurerNameText() != null) {
            // これまでに設定されていたテキストからフォーカスリスナを除く
            getInsurerNameText().removeFocusListener(this);
            getInsurerNameText().removeKeyListener(this);
            if (getInsurerNameText() instanceof VRBindable) {
                ((VRBindable) getInsurerNameText()).removeBindEventListener(this);
            }

        }
        this.insurerNameText = insurerNameText;
        // 新たに設定されたテキストにフォーカスリスナを追加
        if (getInsurerNameText() != null) {
            getInsurerNameText().addFocusListener(this);
            getInsurerNameText().addKeyListener(this);
            setInsurerNameValue(getInsurerNameText().getText());
            if (getInsurerNameText() instanceof VRBindable) {
                ((VRBindable) getInsurerNameText()).addBindEventListener(this);
            }
        }
        
    }

    /**
     * 保険者番号テキストフィールドを取得します。
     * @return
     */
    public JTextField getInsurerNoText() {
        return insurerNoText;
    }

    /**
     * 保険者番号テキストフィールドを設定します。
     * @param insurerNoText
     */
    public void setInsurerNoText(JTextField insurerNoText) {
        // テキストの文字列は保持しておく
        if(getInsurerNoText() != null) {
            // これまでに設定されていたテキストからフォーカスリスナを除く
            getInsurerNoText().removeFocusListener(this);
            getInsurerNoText().removeKeyListener(this);
            if (getInsurerNoText() instanceof VRBindable) {
                ((VRBindable) getInsurerNoText()).removeBindEventListener(this);
            }

        } 
        this.insurerNoText = insurerNoText;
        // 新たに設定されたテキストにフォーカスリスナを追加
        if (getInsurerNoText() != null) {
            getInsurerNoText().addFocusListener(this);
            getInsurerNoText().addKeyListener(this);
            setInsurerNoValue((getInsurerNoText().getText()));
            if (getInsurerNoText() instanceof VRBindable) {
                ((VRBindable) getInsurerNoText()).addBindEventListener(this);
            }
        }
    }
    
    /**
     * 保険者番号テキストに設定されているイベントを削除します。
     * 
     * @throws Exception
     */
    public void deleteInsurerNoListener() throws Exception {
        if(getInsurerNoText() != null) {
            // これまでに設定されていたテキストからフォーカスリスナを除く
            getInsurerNoText().removeFocusListener(this);
            getInsurerNoText().removeKeyListener(this);
            if (getInsurerNoText() instanceof VRBindable) {
                ((VRBindable) getInsurerNoText()).removeBindEventListener(this);
            }
            validDataNo(getInsurerNoText(), "");
        } 
    }
    
    /**
     * 保険者名称テキストに設定されているイベントを削除します。
     * 
     * @throws Exception
     */
    public void deleteInsurerNameListener() throws Exception {
        if(getInsurerNameText() != null) {
            // これまでに設定されていたテキストからフォーカスリスナを除く
            getInsurerNameText().removeFocusListener(this);
            getInsurerNameText().removeKeyListener(this);
            if (getInsurerNameText() instanceof VRBindable) {
                ((VRBindable) getInsurerNameText()).removeBindEventListener(this);
            }
            validDataName(getInsurerNameText(), "");
        }
    }
    
    /**
     * 保険者番号チェック用の結果セットを取得します。
     * @return
     */
    public VRList getValidInsurerListToNo() {
        if(validInsurerListToNo == null) {
            validInsurerListToNo = new VRArrayList();
        }
        return validInsurerListToNo;
    }
    
    /**
     * 保険者番号チェック用の結果セットを設定します。
     * @param validInsurerList
     */
    public void setValidInsurerListToNo(VRList validInsurerList) {
        this.validInsurerListToNo = validInsurerList;
    }

    /**
     * 保険者名称を取得します。
     * @return
     */
    public String getInsurerNameValue() {
        if(insurerNameValue == null) {
            insurerNameValue = new String();
        }
        return insurerNameValue;
    }

    /**
     * 保険者名称を設定します。
     * @param insurerNameValue
     */
    public void setInsurerNameValue(String insurerNameValue) {
        this.insurerNameValue = insurerNameValue;
    }

    /**
     * 保険者番号を取得します。
     * @return
     */
    public String getInsurerNoValue() {
        if(insurerNoValue == null) {
            insurerNoValue = new String();
        }
        return insurerNoValue;
    }

    /**
     * 保険者番号を設定します。
     * @param insurerNoValue
     */
    public void setInsurerNoValue(String insurerNoValue) {
        this.insurerNoValue = insurerNoValue;
    }

    /**
     * 保険者名称チェックの結果セットを取得します。
     * @return
     */
    public VRList getValidInsurerListToName() {
        if(validInsurerListToName == null) {
            validInsurerListToName = new VRArrayList();
        }
        return validInsurerListToName;
    }

    /**
     * 保険者名称チェックの結果セットを設定します。
     * @param validInsurerListToName
     */
    public void setValidInsurerListToName(VRList validInsurerListToName) {
        this.validInsurerListToName = validInsurerListToName;
    }

    /**
     * 名称を基準にチェックを行うか。
     * @return
     */
    public boolean isConvertName() {
        return convertName;
    }

    /**
     * 番号を基準にチェックを行うか。
     * @param isCheckName
     */
    public void setConvertName(boolean isCheckName) {
        this.convertName = isCheckName;
    }

    /**
     * 保険者番号チェックのフラグを取得します。
     * 
     * @return
     */
    public boolean isConvertNo() {
        return convertNo;
    }

    /**
     * 保険者番号チェックのフラグを設定します
     * 
     * @param isCheckNo
     */
    public void setConvertNo(boolean isCheckNo) {
        this.convertNo = isCheckNo;
    }

    /**
     * 保険者番号マスタを取得します。
     * @return
     */
    public ACDBManager getInsurerDbm() {
        return insurerDbm;
    }

    /**
     * 保険者番号マスタを設定します。
     * @param insurerDbm
     */
    public void setInsurerDbm(ACDBManager insurerDbm) {
        this.insurerDbm = insurerDbm;
    }

    /**
     * キーを押しているときに呼び出されます。
     */
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * キーを離したときに呼び出されます。
	 */
	public void keyReleased(KeyEvent e) {
		
        // コンポーネントのNullチェック
        if (e == null && getInsurerNoText() == null
                || getInsurerNameText() == null) {
            return;
        }
		
        if(e.getSource() == null) {
        	return;
        }
        
        // 保険者番号の入力
        if(e.getSource().equals(getInsurerNoText())) {
    		if (getInsurerNoText().getText().length() != 6){
    			isCommitInsurer = false;
    			return;
    		}
            
    		if (!isCommitInsurer) {
    	        applyInsurerData(e);
    	        isCommitInsurer = true;
    		}
            return;
        }
        
        // 保険者名入力
        if(e.getSource().equals(getInsurerNameText())) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                // エンターキーが押された
                applyInsurerData(e);
            }
            
            return;
        }
        
	}

	/**
	 * キーをタイプすると呼び出されます。
	 */
	public void keyTyped(KeyEvent e) {
	}

	/**
     * 保険者番号に関するチェック処理
     *  
     * @throws Exception
	 */
    public void checkInsurerNo() throws Exception {
        boolean isConverted = false;
        // 処理用に値を取り出す。
        setInsurerNoValue(getInsurerNoText().getText());
        
        // データの検索
        buildData(getInsurerNoText(), getInsurerNameText(), isConvertNo(),
                isConvertName(), isCheckNo(), isCheckName());
        
        // 入力保険者番号から結果が取得できている場合
        if(!getValidInsurerListToNo().isEmpty()) {
            isConverted = true;
        }
        
        if(isCheckNo()) {
            // 未入力の場合
            if(getInsurerNoValue().length() == 0) {
                validDataNo(getInsurerNoText(), getInsurerNoValue());
                return;
            } else if(getInsurerNoValue().length() != 6) {
                // 桁数不正
                warningDataNo(getInsurerNoText(), getInsurerNoValue());
                return;
            }
            if(isConverted) {
                // 変換に成功したので双方正常
                validDataNo(getInsurerNoText(), getInsurerNoValue());
            } else {
                // 変換に成功していない
                warningDataNo(getInsurerNoText(), getInsurerNoValue());
            }
            
        } else {
            // チェックしない場合は、一律正常
            validDataNo(getInsurerNoText(), getInsurerNoValue());
        }
        
        getInsurerNoText().revalidate();
        getInsurerNoText().repaint();
    
    }
    
    /**
     * 保険者名称に関するチェック処理
     *  
     * @throws Exception
     */
    public void checkInsurerName() throws Exception {
        boolean isConverted = false;
        
        // 処理用に値を取り出す。
        setInsurerNameValue(getInsurerNameText().getText());
        // データの検索
        buildData(getInsurerNoText(), getInsurerNameText(), isConvertNo(),
                isConvertName(), isCheckNo(), isCheckName());

        if(!getValidInsurerListToName().isEmpty()) {
            isConverted = true;
        }
        
        // チェック処理
        if(isCheckName()) {
            // 桁数チェック
            if(getInsurerNameValue().length() == 0) {
                validDataName(getInsurerNameText(), getInsurerNameValue());
                return;                
            }
            if(isConverted) {
                // 変換に成功したので双方正常
                validDataName(getInsurerNameText(), getInsurerNameValue());
            } else {
                // 変換に成功していない
                warningDataName(getInsurerNameText(), getInsurerNameValue());
            }
            
        } else {
            // チェックしない場合は、一律正常
            validDataName(getInsurerNameText(), getInsurerNameValue());
        }

        getInsurerNameText().revalidate();
        getInsurerNameText().repaint();
        
    }

    /**
     * 保険者名称のチェックを行うかを判別するフラグを取得します。
     * 
     * @return
     */
    public boolean isCheckName() {
        return checkName;
    }

    /**
     * 保険者名称のチェックを行うかを判別するフラグを設定します。
     * 
     * @param checkName
     */
    public void setCheckName(boolean checkName) {
        this.checkName = checkName;
    }

    /**
     * 保険者番号のチェックを行うかを判別するフラグを設定します。
     * 
     * @return
     */
    public boolean isCheckNo() {
        return checkNo;
    }

    /**
     * 保険者番号のチェックを行うかを判別するフラグを設定します。
     * 
     * @param checkNo
     */
    public void setCheckNo(boolean checkNo) {
        this.checkNo = checkNo;
    }
    
}
