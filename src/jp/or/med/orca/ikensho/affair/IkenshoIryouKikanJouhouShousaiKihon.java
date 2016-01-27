package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.im.InputSubset;

import javax.swing.JScrollPane;

import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACRowMaximumableTextArea;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.border.VRFrameBorder;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.component.IkenshoZipTextField;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIryouKikanJouhouShousaiKihon extends IkenshoTabbableChildAffairContainer {
    private VRPanel kihonPnl1 = new VRPanel();
    private VRLabel note1 = new VRLabel();
    private VRPanel kihonPnl2 = new VRPanel();
    private ACLabelContainer drNmContainer = new ACLabelContainer();
    private ACTextField drNm = new ACTextField();
    private ACLabelContainer miNmContainer = new ACLabelContainer();
    private ACTextField miNm = new ACTextField();
    private ACLabelContainer miPostCdContainer = new ACLabelContainer();
    private IkenshoZipTextField miPostCd = new IkenshoZipTextField();
    private ACLabelContainer miAddressContainer = new ACLabelContainer();
    private ACTextField miAddress = new ACTextField();
    private ACLabelContainer miTelContainer = new ACLabelContainer();
    private IkenshoTelTextField miTel = new IkenshoTelTextField();
    private ACLabelContainer miFaxContainer = new ACLabelContainer();
    private IkenshoTelTextField miFax = new IkenshoTelTextField();
    private ACLabelContainer miCelTelContainer = new ACLabelContainer();
    private VRPanel kihonPnl3 = new VRPanel();
    private IkenshoTelTextField miCelTel = new IkenshoTelTextField();
    private VRLabel note2 = new VRLabel();
    private VRPanel kihonPnl4 = new VRPanel();
    private ACLabelContainer kinkyuRenrakuContainer = new ACLabelContainer();
    private ACTextField kinkyuRenraku = new ACTextField();
    private ACLabelContainer fuzaijiTaiouContainer = new ACLabelContainer();
    private ACTextField fuzaijiTaiou = new ACTextField();
    private VRPanel kihonPnl5 = new VRPanel();
    private ACLabelContainer bikouContainer = new ACLabelContainer();
    private JScrollPane bikouScr = new JScrollPane();
    private ACRowMaximumableTextArea bikou = new ACRowMaximumableTextArea();
    private VRPanel kihonPnl6 = new VRPanel();
    private ACIntegerCheckBox miDefault = new ACIntegerCheckBox();
    private VRLabel note3 = new VRLabel();
    private ACLabelContainer miDefaultDummyContainer1 = new ACLabelContainer();
    private ACLabelContainer miDefaultDummyContainer2 = new ACLabelContainer();
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start 医療機関情報の無効化対応
    private ACIntegerCheckBox miInvalid = new ACIntegerCheckBox();
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
  
    public IkenshoIryouKikanJouhouShousaiKihon() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        //基本情報
        VRLayout tabKihonLayout = new VRLayout();
        tabKihonLayout.setLabelMargin(90);
        this.setLayout(tabKihonLayout);
        this.add(kihonPnl1, VRLayout.NORTH);
        this.add(kihonPnl2, VRLayout.NORTH);
        this.add(kihonPnl3, VRLayout.NORTH);
        this.add(kihonPnl4, VRLayout.NORTH);
        this.add(kihonPnl5, VRLayout.CLIENT);
        this.add(kihonPnl6, VRLayout.SOUTH);

        //基本情報1
// [ID:0000787][Satoshi Tokusari] 2014/10 edit-Start 医療機関情報の無効化対応
//        kihonPnl1.setLayout(new BorderLayout());
        VRLayout kihonPnl1Layout = new VRLayout();
        kihonPnl1Layout.setLabelMargin(96);
        kihonPnl1.setLayout(kihonPnl1Layout);
        kihonPnl1.add(miInvalid, VRLayout.FLOW_INSETLINE);
        miInvalid.setText("無効にする");
        miInvalid.setBindPath("INVALID_FLAG");
        miInvalid.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
// [ID:0000787][Satoshi Tokusari] 2014/10 edit-End
        kihonPnl1.add(note1, BorderLayout.EAST);
//      [ID:0000514][Tozo TANAKA] 2009/09/16 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        note1.setText("↓「主治医意見書」「医師医見書」「訪問看護指示書」に印刷される項目");
        note1.setText("↓「主治医意見書」「医師医見書」「（特別）訪問看護指示書」に印刷される項目");
//      [ID:0000514][Tozo TANAKA] 2009/09/16 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        note1.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);

        //基本情報2
        VRLayout kihonPnl2Layout = new VRLayout();
        kihonPnl2Layout.setLabelMargin(90);
        kihonPnl2.setLayout(kihonPnl2Layout);
        kihonPnl2.setBorder(new VRFrameBorder("",new Color(204, 204, 255)));
        kihonPnl2.add(drNmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kihonPnl2.add(miNmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kihonPnl2.add(miPostCdContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kihonPnl2.add(miAddressContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kihonPnl2.add(miTelContainer, VRLayout.FLOW_INSETLINE);
        kihonPnl2.add(miFaxContainer, VRLayout.FLOW_RETURN);
        drNmContainer.setText("医師氏名");
        drNmContainer.add(drNm, null);
        drNmContainer.setHorizontalAlignment(ACLabelContainer.RIGHT);
        drNm.setColumns(15);
        drNm.setMaxLength(15);
        drNm.setIMEMode(InputSubset.KANJI);
        drNm.setBindPath("DR_NM");
        miNmContainer.setText("医療機関名");
        miNmContainer.add(miNm, null);
        miNm.setColumns(30);
        miNm.setMaxLength(30);
        miNm.setIMEMode(InputSubset.KANJI);
        miNm.setBindPath("MI_NM");
        miPostCdContainer.setText("郵便番号");
        miPostCdContainer.add(miPostCd, null);
        miPostCd.setAddressTextField(miAddress);
        miPostCd.setBindPath("MI_POST_CD");
        miAddressContainer.setText("所在地");
        miAddressContainer.add(miAddress, null);
        miAddress.setColumns(45);
        miAddress.setMaxLength(45);
        miAddress.setIMEMode(InputSubset.KANJI);
        miAddress.setBindPath("MI_ADDRESS");
        miTelContainer.setText("連絡先(TEL)");
        miTelContainer.add(miTel, null);
        miTel.setBindPath("MI_TEL1", "MI_TEL2");
        miFaxContainer.setText("連絡先(FAX)");
        miFaxContainer.add(miFax, null);
        miFax.setBindPath("MI_FAX1", "MI_FAX2");

        //基本情報3
        VRLayout kihonPnl3Layout=new VRLayout();
        kihonPnl3Layout.setLabelMargin(96);
        kihonPnl3.setLayout(kihonPnl3Layout);
        kihonPnl3.add(miCelTelContainer, VRLayout.FLOW_DOUBLEINSETLINE);
        kihonPnl3.add(note2, VRLayout.EAST);
        miCelTelContainer.setText("連絡先(携帯)");
        miCelTelContainer.add(miCelTel, null);
        miCelTel.setBindPath("MI_CEL_TEL1", "MI_CEL_TEL2");
//      [ID:0000514][Tozo TANAKA] 2009/09/16 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        note2.setText("↓「訪問看護指示書」に印刷される項目");
        note2.setText("↓「（特別）訪問看護指示書」に印刷される項目");
//      [ID:0000514][Tozo TANAKA] 2009/09/16 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        note2.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);

        //基本情報4
        VRLayout kihonPnl4Layout = new VRLayout();
        kihonPnl4Layout.setLabelMargin(90);
        kihonPnl4.setLayout(kihonPnl4Layout);
        kihonPnl4.setBorder(new VRFrameBorder("",new Color(204, 204, 255)));
        kihonPnl4.add(kinkyuRenrakuContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kihonPnl4.add(fuzaijiTaiouContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kinkyuRenrakuContainer.setText("緊急時連絡先");
        kinkyuRenrakuContainer.add(kinkyuRenraku, null);
        kinkyuRenraku.setColumns(40);
        kinkyuRenraku.setMaxLength(40);
        kinkyuRenraku.setIMEMode(InputSubset.KANJI);
        kinkyuRenraku.setBindPath("KINKYU_RENRAKU");
        fuzaijiTaiouContainer.setText("不在時対応法");
        fuzaijiTaiouContainer.add(fuzaijiTaiou, null);
        fuzaijiTaiou.setColumns(40);
        fuzaijiTaiou.setMaxLength(40);
        fuzaijiTaiou.setIMEMode(InputSubset.KANJI);
        fuzaijiTaiou.setBindPath("FUZAIJI_TAIOU");

        //基本情報5
        VRLayout kihonPnl5Layout=new VRLayout();
        kihonPnl5Layout.setLabelMargin(96);
        kihonPnl5Layout.setFitHLast(true);
        kihonPnl5Layout.setFitVLast(true);
        kihonPnl5.setLayout(kihonPnl5Layout);
        kihonPnl5.add(bikouContainer, VRLayout.FLOW_INSETLINE);
        
        bikouContainer.setText("備考");
        bikouContainer.add(bikouScr, null);
        bikouScr.getViewport().add(bikou);
        bikou.setIMEMode(InputSubset.KANJI);
        bikou.setBindPath("BIKOU");
        bikou.setMaxLength(200);
        bikou.setLineWrap(true);

        //基本情報6
        VRLayout kihonPnl6Layout = new VRLayout();
        kihonPnl6Layout.setLabelMargin(66);
        kihonPnl6.setLayout(kihonPnl6Layout);
        kihonPnl6.add(miDefaultDummyContainer1, VRLayout.FLOW_INSETLINE);
        kihonPnl6.add(miDefault, VRLayout.FLOW_RETURN);
        kihonPnl6.add(miDefaultDummyContainer2, VRLayout.FLOW_INSETLINE);
        kihonPnl6.add(note3, VRLayout.FLOW_RETURN);
        miDefault.setText("この医師・医療機関が主として使用します。");
        miDefault.setBindPath("MI_DEFAULT");
        miDefault.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
//      [ID:0000514][Tozo TANAKA] 2009/09/16 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        note3.setText("(チェックすると「主治医意見書」「医師医見書」「訪問看護指示書」作成時に予め選択されます)");
        note3.setText("(チェックすると「主治医意見書」「医師医見書」「（特別）訪問看護指示書」作成時に予め選択されます)");
//      [ID:0000514][Tozo TANAKA] 2009/09/16 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        note3.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
    }

    public Component getFirstFocusComponent() {
        return drNm;
    }

    public boolean noControlError() {
        //医師氏名 / 未入力チェック
        if (isNullText(drNm.getText())) {
            ACMessageBox.show("医師氏名を入力してください。",
                                   ACMessageBox.BUTTON_OK,
                                   ACMessageBox.ICON_EXCLAMATION);
            drNm.requestFocus();
            return false;
        }

        return true;
    }
}
