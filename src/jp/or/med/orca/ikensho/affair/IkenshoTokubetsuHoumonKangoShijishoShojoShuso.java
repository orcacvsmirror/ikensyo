package jp.or.med.orca.ikensho.affair;

import java.awt.Color;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JTextField;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACDateUtilities;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

//[ID:0000514][Masahiko Higuchi] 2009/09/08 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
public class IkenshoTokubetsuHoumonKangoShijishoShojoShuso extends
        IkenshoTabbableChildAffairContainer {

    protected IkenshoHoumonKangoShijishoInstructContainer tokubetsuShuso;
    protected ACGroupBox tokubetsuShusoGroup;
    protected IkenshoDocumentTabTitleLabel shojoShusoTitle;
    
    //[ID:0000687][Shin Fujihara] 2012/03/12 add begin 【2012年度対応：「一時的に訪問看護が頻回に必要な理由」追加】
    protected ACLabel adviceLabel1;
//    protected JTextField adviceText2;
    protected ACTextField adviceText2;
    protected ACLabel adviceLabel3;
    protected ACLabel adviceLabel4;
    //[ID:0000687][Shin Fujihara] 2012/03/12 add end
    
    public IkenshoTokubetsuHoumonKangoShijishoShojoShuso() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * 画面構築処理
     * 
     * @throws Exception
     */
    private void jbInit()throws Exception{
        this.setLayout(new VRLayout());
        getShojoShusoTitle().setText("症状・主訴");
        
        
        getTokubetsuShuso().setColumns(100);
        getTokubetsuShuso().setRows(14);
        getTokubetsuShuso().setMaxRows(14);
        getTokubetsuShuso().setMaxLength(700);
        getTokubetsuShuso().setShowSelectVisible(false);
        getTokubetsuShuso().setCheckVisible(false);
        getTokubetsuShuso().setTextBindPath("TOKUBETSU_SHOJO_SHUSO");
        getTokubetsuShuso().setCaption("症状・主訴");
        getTokubetsuShuso().setTitle("症状・主訴（700文字/14行以内）");
        getTokubetsuShuso().fitTextArea();
        //[ID:0000687][Shin Fujihara] 2012/03/12 add begin 【2012年度対応：「一時的に訪問看護が頻回に必要な理由」追加】
        VRLayout layout = new VRLayout();
        layout.setHgap(4);
        JPanel advicePanel = new JPanel(layout);
        advicePanel.add(getAdviceLabel1());
        advicePanel.add(getAdviceText2());
        advicePanel.add(getAdviceLabel3(), VRLayout.FLOW_RETURN);
        advicePanel.add(getAdviceLabel4()); 
        //[ID:0000687][Shin Fujihara] 2012/03/12 add end
        
        getTokubetsuShusoGroup().add(getTokubetsuShuso(), VRLayout.NORTH);
        //[ID:0000687][Shin Fujihara] 2012/03/12 add begin 【2012年度対応：「一時的に訪問看護が頻回に必要な理由」追加】
        getTokubetsuShusoGroup().add(advicePanel, VRLayout.NORTH);
        //[ID:0000687][Shin Fujihara] 2012/03/12 add end
        
        this.add(getShojoShusoTitle(), VRLayout.NORTH);
        this.add(getTokubetsuShusoGroup(), VRLayout.CLIENT);
        
    }
    
    /**
     * tokubetsuShuso を返します。
     * @return tokubetsuShuso
     */
    protected IkenshoHoumonKangoShijishoInstructContainer getTokubetsuShuso() {
        if(tokubetsuShuso==null){
            tokubetsuShuso = new IkenshoHoumonKangoShijishoInstructContainer();
        }
        return tokubetsuShuso;
    }
    
    /**
     * tokubetsuShusoGroup を返します。
     * @return tokubetsuShusoGroup
     */
    protected ACGroupBox getTokubetsuShusoGroup() {
        if(tokubetsuShusoGroup==null){
            tokubetsuShusoGroup = new ACGroupBox();
        }
        return tokubetsuShusoGroup;
    }
    
    /**
     * ryuuiShijiTitle を返します。
     * 
     * @return ryuuiShijiTitle
     */
    protected IkenshoDocumentTabTitleLabel getShojoShusoTitle() {
        if (shojoShusoTitle == null) {
            shojoShusoTitle = new IkenshoDocumentTabTitleLabel();
        }
        return shojoShusoTitle;
    }
    
    //[ID:0000687][Shin Fujihara] 2012/03/12 add begin 【2012年度対応：「一時的に訪問看護が頻回に必要な理由」追加】
    /**
     * 説明文テキスト<br>
     * [平成24年度診療報酬改定により、症状・主訴項目に「]までのラベル
     * @return
     */
    protected ACLabel getAdviceLabel1() {
        if (adviceLabel1 == null) {
            adviceLabel1 = new ACLabel();
            adviceLabel1.setText("平成24年度診療報酬改定により、症状・主訴項目に「");
        }
        return adviceLabel1;
    }
    
    /**
     * 説明文テキスト<br>
     * [一時的に訪問看護が頻回に必要な理由：]のテキスト
     * @return
     */
    protected JTextField getAdviceText2() {
        if (adviceText2 == null) {
//            adviceText2 = new JTextField();
        	adviceText2 = new ACTextField();
            adviceText2.setOpaque(false );
            adviceText2.setBorder(null);
            adviceText2.setForeground(Color.red);
            adviceText2.setEditable(false);
            
            adviceText2.setText("一時的に訪問看護が頻回に必要な理由：");
        }
        return adviceText2;
    }
    
    /**
     * 説明文テキスト<br>
     * [」が追加されました。]のラベル
     * @return
     */
    protected ACLabel getAdviceLabel3() {
        if (adviceLabel3 == null) {
            adviceLabel3 = new ACLabel();
            adviceLabel3.setText("」が追加されました。");
            
        }
        return adviceLabel3;
    }
    
    /**
     * 説明文テキスト<br>
     * [必要に応じて記入するようにしてください。]のラベル
     * @return
     */
    protected ACLabel getAdviceLabel4() {
        if (adviceLabel4 == null) {
            adviceLabel4 = new ACLabel();
            adviceLabel4.setText("必要に応じて記入するようにしてください。");
        }
        return adviceLabel4;
    }
    //[ID:0000687][Shin Fujihara] 2012/03/12 add end
    
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        getTokubetsuShuso().setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "MT_STS",
                "0",
                "読込(L)",
                'L',
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
//                "最新の訪問看護指示書に登録した「症状・治療状態」を読み込みます。" + ACConstants.LINE_SEPARATOR
                "最新の（精神科）訪問看護指示書に登録した「症状・治療状態」を読み込みます。" + ACConstants.LINE_SEPARATOR
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
                + "よろしいですか？", true);
        
    }
    
    //[ID:0000687][Shin Fujihara] 2012/03/12 add begin 【2012年度対応：「一時的に訪問看護が頻回に必要な理由」追加】
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        
        //新規作成モードの時
        if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getMasterAffair().getNowMode())) {
            if (ACTextUtilities.isNullText(getTokubetsuShuso().getText())) {
                StringBuffer sb = new StringBuffer();
                sb.append(ACConstants.LINE_SEPARATOR);
                sb.append(ACConstants.LINE_SEPARATOR);
                sb.append(ACConstants.LINE_SEPARATOR);
                sb.append(getAdviceText2().getText());
                
                getTokubetsuShuso().setText(sb.toString());
            }
        }
        
    }
    //[ID:0000687][Shin Fujihara] 2012/03/12 add end

}
//[ID:0000514][Masahiko Higuchi] 2009/09/08 add end