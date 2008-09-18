package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JLabel;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.ACValueArrayRadioButtonGroup;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoApplicant
    extends IkenshoDocumentAffairApplicant {
  private ACGroupBox sijishoKind = new ACGroupBox();
  private ACLabelContainer sijishoKindContainer = new ACLabelContainer();
  private ACIntegerCheckBox houmonSijiSyo = new ACIntegerCheckBox();
  private ACIntegerCheckBox tentekiSijiSyo = new ACIntegerCheckBox();
  private ACGroupBox sijiKikan = new ACGroupBox();
  private ACLabelContainer sijiKikanContainer = new ACLabelContainer();
  private IkenshoEraDateTextField sijiKikanFrom = new IkenshoEraDateTextField();
  private JLabel sijiKikanSep = new JLabel();
  private IkenshoEraDateTextField sijiKikanTo = new IkenshoEraDateTextField();
  private ACButton sijiKikanClear = new ACButton();
  private ACGroupBox tenteki = new ACGroupBox();
  private ACLabelContainer tentekiContainer = new ACLabelContainer();
  private IkenshoEraDateTextField tentekiFrom = new IkenshoEraDateTextField();
  private JLabel tentekiSep = new JLabel();
  private IkenshoEraDateTextField tentekiTo = new IkenshoEraDateTextField();
  private ACButton tentekiClear = new ACButton();
  private ACGroupBox youkaigoJoukyouGrp = new ACGroupBox();
  private ACLabelContainer youkaigoJoukyouContainer = new ACLabelContainer();
  private ACValueArrayRadioButtonGroup youkaigoJoukyou = new ACValueArrayRadioButtonGroup();
  private ACGroupBox hiddenGroup = new ACGroupBox();
  private ACIntegerCheckBox createCount = new ACIntegerCheckBox();
  private ACIntegerCheckBox validSpan = new ACIntegerCheckBox();
  private ACIntegerCheckBox kangoKubun = new ACIntegerCheckBox();
  private ACTextField shyoubyouChiryouJyoutaiOther = new ACTextField();
  private ACLabelContainer sijiKikanFroms = new ACLabelContainer();
  private ACLabelContainer sijiKikanTos = new ACLabelContainer();
  private ACLabelContainer tentekiFroms = new ACLabelContainer();
  private ACLabelContainer tentekiTos = new ACLabelContainer();


  public boolean noControlError() throws Exception {
     //エラーチェック
     if(!super.noControlError()){
       return false;
     }


     switch (writeDate.getInputStatus()) {
       case IkenshoEraDateTextField.STATE_VALID:
       case IkenshoEraDateTextField.STATE_FUTURE:
         break;
       default:
         ACMessageBox.showExclamation("日付に誤りがあります。");
         writeDate.requestChildFocus();
         return false;
     }

     if(!(houmonSijiSyo.isSelected() || tentekiSijiSyo.isSelected())){
       ACMessageBox.show("指示書種類、「訪問看護指示書」、または「在宅患者訪問点滴注射指示書」を\n選択してください。");
       houmonSijiSyo.requestFocus();
       return false;
     }
     // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
     sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
     if(!"".equals(sijiKikanFrom.getEra())&& !"".equals(sijiKikanFrom.getYear())){
         sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
         // 2007/10/25 [Masahiko Higuchi] Addition - end
         switch (sijiKikanFrom.getInputStatus()) {
           case IkenshoEraDateTextField.STATE_EMPTY:
               // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
               sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
               // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           case IkenshoEraDateTextField.STATE_VALID:
             // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
             sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             if (sijiKikanTo.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) {
               if (sijiKikanFrom.getDate().compareTo(sijiKikanTo.getDate()) >= 0) {
                 ACMessageBox.showExclamation("指示期間「開始日付」と「終了日付」の範囲に誤りがあります。");
                 sijiKikanFrom.requestChildFocus();
                 // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
                 sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                 sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                 // 2007/10/25 [Masahiko Higuchi] Addition - end
                 return false;
               }
             }
             // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
             sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           default:
             ACMessageBox.show("日付に誤りがあります。");
             sijiKikanFrom.requestChildFocus();
             // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
             sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             return false;
         }
     // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
     }
     // 2007/10/25 [Masahiko Higuchi] Addition - end

     // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
     sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
     if(!"".equals(sijiKikanTo.getEra())&& !"".equals(sijiKikanTo.getYear())){
         sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
     // 2007/10/25 [Masahiko Higuchi] Addition - end
         switch (sijiKikanTo.getInputStatus()) {
           case IkenshoEraDateTextField.STATE_EMPTY:
           case IkenshoEraDateTextField.STATE_VALID:
               // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
               sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
               // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           default:
             ACMessageBox.show("日付に誤りがあります。");
             sijiKikanTo.requestChildFocus();
             // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
             sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             return false;
         }
     // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
     }
     // 2007/10/25 [Masahiko Higuchi] Addition - end
     
     // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
     tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
     if(!"".equals(tentekiFrom.getEra())&& !"".equals(tentekiFrom.getYear())){
         tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
         // 2007/10/25 [Masahiko Higuchi] Addition - end
         switch (tentekiFrom.getInputStatus()) {
           case IkenshoEraDateTextField.STATE_EMPTY:
               // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
               tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
               // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           case IkenshoEraDateTextField.STATE_VALID:
             // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
             tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             if (tentekiTo.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) {
               if (tentekiFrom.getDate().compareTo(tentekiTo.getDate()) >= 0) {
                 ACMessageBox.showExclamation("指示期間「開始日付」と「終了日付」の範囲に誤りがあります。");
                 tentekiFrom.requestChildFocus();
                 // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
                 tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                 tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                 // 2007/10/25 [Masahiko Higuchi] Addition - end
                 return false;
               }
             }
             // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
             tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           default:
             ACMessageBox.show("日付に誤りがあります。");
             tentekiFrom.requestChildFocus();
             // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
             tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             return false;
         }
     // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
     }
     // 2007/10/25 [Masahiko Higuchi] Addition - end
     
     // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
     tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
     if(!"".equals(tentekiTo.getEra())&& !"".equals(tentekiTo.getYear())){
         tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
         // 2007/10/25 [Masahiko Higuchi] Addition - end
         switch (tentekiTo.getInputStatus()) {
           case IkenshoEraDateTextField.STATE_VALID:
               // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
               tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
               // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           default:
             ACMessageBox.show("日付に誤りがあります。");
             tentekiTo.requestChildFocus();
             // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
             tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             return false;
         }
     // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
     }
     // 2007/10/25 [Masahiko Higuchi] Addition - end
     return true;
  }


  public IkenshoHoumonKangoShijishoApplicant() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    hiddenGroup.setVisible(false);

    sijiKikanClear.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        sijiKikanFrom.clear();
        sijiKikanTo.clear();
      }
    });
    tentekiClear.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        tentekiFrom.clear();
        tentekiTo.clear();
      }
    });
  }

  private void jbInit() throws Exception {
    applicantTitle.setText("患者");
    sijiKikanClear.setToolTipText("「開始日付」と「終了日付」を消去します。");
    tentekiClear.setToolTipText("「開始日付」と「終了日付」を消去します。");
    hiddenGroup.setText("隠しパラメータ");
    createCount.setText("指示書作成回数");
    createCount.setBindPath("SIJI_CREATE_CNT");
    validSpan.setBindPath("SIJI_YUKOU_KIKAN");
    validSpan.setText("指示書有効期間");
    kangoKubun.setText("指示書看護区分");
    kangoKubun.setBindPath("SIJI_KANGO_KBN");
    youkaigoJoukyou.setBindPath("YOUKAIGO_JOUKYOU");
    youkaigoJoukyou.setValues(new int[] {1, 11, 21, 22, 23, 24, 25});
    houmonSijiSyo.setSelectValue(-1);
    tentekiSijiSyo.setSelectValue(-1);
    shyoubyouChiryouJyoutaiOther.setBindPath("MT_STS_OTHER");
    tentekiFroms.add(tentekiFrom, null);
    tentekiTos.add(tentekiTo, null);
    hiddenGroup.add(shyoubyouChiryouJyoutaiOther, null);
    hiddenGroup.add(kangoKubun, null);
    applicantEasts.add(writeDateCaption, null);
    writeDate.setAllowedFutureDate(true);

    //患者
    this.add(sijishoKind, VRLayout.NORTH);
    this.add(sijiKikan, VRLayout.NORTH);
    this.add(tenteki, VRLayout.NORTH);
    this.add(youkaigoJoukyouGrp, VRLayout.NORTH);
    //患者 / 指示書種類
    sijishoKind.setLayout(new BorderLayout());
    sijishoKind.add(sijishoKindContainer, BorderLayout.CENTER);
    sijishoKindContainer.setText("指示書種類");
    VRLayout sijishoKindContainerLayout = new VRLayout();
    sijishoKindContainerLayout.setHgap(0);
    sijishoKindContainerLayout.setVgap(0);
    sijishoKindContainer.setLayout(sijishoKindContainerLayout);
    sijishoKindContainer.add(houmonSijiSyo, VRLayout.FLOW);
    sijishoKindContainer.add(tentekiSijiSyo, VRLayout.FLOW);
    houmonSijiSyo.setText("訪問看護指示書");
    houmonSijiSyo.setBindPath("HOUMON_SIJISYO");
    tentekiSijiSyo.setText("在宅患者訪問点滴注射指示書");
    tentekiSijiSyo.setBindPath("TENTEKI_SIJISYO");
    //患者 / 訪問看護指示期間
    sijiKikan.setLayout(new BorderLayout());
    sijiKikan.add(sijiKikanContainer);
    sijiKikanContainer.setText("訪問看護指示期間");
    sijiKikanContainer.setLayout(new VRLayout());
    sijiKikanContainer.setFocusForeground(IkenshoConstants.
                                            COLOR_BACK_PANEL_FOREGROUND);
    sijiKikanContainer.setFocusBackground(IkenshoConstants.
                                            COLOR_BACK_PANEL_BACKGROUND);
    sijiKikanContainer.setContentAreaFilled(true);
    sijiKikanFrom.setAgeVisible(false);
    sijiKikanFrom.setAllowedFutureDate(true);
    // 2007/10/25 [Masahiko Higuchi] Delete - begin 平成デフォルト表示対応
    //sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    // 2007/10/25 [Masahiko Higuchi] Delete - end
    sijiKikanFrom.setBindPath("SIJI_KIKAN_FROM");
    // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
    sijiKikanFrom.setEraPreclusion("平成");
    sijiKikanTo.setEraPreclusion("平成");
    sijiKikanFrom.setDateTypeConv(false);
    sijiKikanTo.setDateTypeConv(false);
    // 2007/10/25 [Masahiko Higuchi] Addition - end
    sijiKikanSep.setText("から");
    sijiKikanTo.setAllowedFutureDate(true);
    sijiKikanTo.setAgeVisible(false);
    // 2007/10/25 [Masahiko Higuchi] Delete - begin 平成デフォルト表示対応
    //sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    // 2007/10/25 [Masahiko Higuchi] Delete - end
    sijiKikanTo.setBindPath("SIJI_KIKAN_TO");
    sijiKikanClear.setText("日付消去(C)");
    sijiKikanClear.setMnemonic('C');
    sijiKikanContainer.add(sijiKikanFroms, VRLayout.FLOW);
    sijiKikanFroms.add(sijiKikanFrom, null);
    sijiKikanContainer.add(sijiKikanSep, VRLayout.FLOW);
    sijiKikanContainer.add(sijiKikanTos, VRLayout.FLOW);
    sijiKikanTos.add(sijiKikanTo, null);
    sijiKikanContainer.add(sijiKikanClear, VRLayout.FLOW);
    //患者 / 点滴注射指示期間
    tenteki.setLayout(new BorderLayout());
    tenteki.add(tentekiContainer);
    tentekiContainer.setText("点滴注射指示期間");
    tentekiContainer.setLayout(new VRLayout());
    tentekiContainer.setFocusForeground(IkenshoConstants.
                                            COLOR_BACK_PANEL_FOREGROUND);
    tentekiContainer.setFocusBackground(IkenshoConstants.
                                            COLOR_BACK_PANEL_BACKGROUND);
    tentekiContainer.setContentAreaFilled(true);
    tentekiFrom.setAgeVisible(false);
    tentekiFrom.setAllowedFutureDate(true);
    // 2007/10/25 [Masahiko Higuchi] Delete - begin 平成デフォルト表示対応
    //tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    // 2007/10/25 [Masahiko Higuchi] Delete - end
    tentekiFrom.setBindPath("TENTEKI_FROM");
    // 2007/10/25 [Masahiko Higuchi] Addition - begin 平成デフォルト表示対応
    tentekiFrom.setEraPreclusion("平成");
    tentekiTo.setEraPreclusion("平成");
    tentekiFrom.setDateTypeConv(false);
    tentekiTo.setDateTypeConv(false);
    tentekiFrom.clear();
    tentekiTo.clear();
    // 2007/10/25 [Masahiko Higuchi] Addition - end
    tentekiSep.setText("から");
    tentekiTo.setAgeVisible(false);
    // 2007/10/25 [Masahiko Higuchi] Delete - begin 平成デフォルト表示対応
    //tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    // 2007/10/25 [Masahiko Higuchi] Delete - end
    tentekiTo.setAllowedFutureDate(true);
    tentekiTo.setBindPath("TENTEKI_TO");
    tentekiClear.setText("日付消去(C)");
    tentekiClear.setMnemonic('C');
    tentekiContainer.add(tentekiFroms, VRLayout.FLOW);
    tentekiContainer.add(tentekiSep, VRLayout.FLOW);
    tentekiContainer.add(tentekiTos, VRLayout.FLOW);
    tentekiContainer.add(tentekiClear, VRLayout.FLOW);


    ( (VRLayout) sijiKikanContainer.getLayout()).setAutoWrap(false);
    ( (VRLayout) tentekiContainer.getLayout()).setAutoWrap(false);
    ( (VRLayout) sijishoKindContainer.getLayout()).setAutoWrap(false);
    //患者 / 要介護認定の状況
    youkaigoJoukyouGrp.setLayout(new BorderLayout());
    youkaigoJoukyouGrp.add(youkaigoJoukyouContainer, BorderLayout.CENTER);
    youkaigoJoukyouContainer.setText("要介護認定の状況");
    youkaigoJoukyouContainer.setLayout(new VRLayout());
    youkaigoJoukyouContainer.add(youkaigoJoukyou, VRLayout.FLOW);
    this.add(hiddenGroup, null);
    hiddenGroup.add(validSpan, null);
    hiddenGroup.add(createCount, null);
    youkaigoJoukyou.setModel(new VRListModelAdapter(
        new VRArrayList(Arrays.asList(new String[] {
                                      "自立",
                                      "要支援　　要介護（",
                                      "１",
                                      "２",
                                      "３",
                                      "４",
                                      "５　）"}))));

  }
}
