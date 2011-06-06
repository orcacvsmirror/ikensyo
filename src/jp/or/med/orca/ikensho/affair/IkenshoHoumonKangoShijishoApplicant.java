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
  // [ID:0000514][Masahiko Higuchi] 2009/09/08 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
  protected ACLabelContainer sijiKikanContainer = new ACLabelContainer();
  protected IkenshoEraDateTextField sijiKikanFrom = new IkenshoEraDateTextField();
  protected JLabel sijiKikanSep = new JLabel();
  protected IkenshoEraDateTextField sijiKikanTo = new IkenshoEraDateTextField();
  protected ACButton sijiKikanClear = new ACButton();
  // [ID:0000514][Masahiko Higuchi] 2009/09/08 replace end
  private ACGroupBox tenteki = new ACGroupBox();
  private ACLabelContainer tentekiContainer = new ACLabelContainer();
  private IkenshoEraDateTextField tentekiFrom = new IkenshoEraDateTextField();
  private JLabel tentekiSep = new JLabel();
  private IkenshoEraDateTextField tentekiTo = new IkenshoEraDateTextField();
  private ACButton tentekiClear = new ACButton();
  // [ID:0000514][Masahiko Higuchi] 2009/09/08 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
  protected ACGroupBox youkaigoJoukyouGrp = new ACGroupBox();
  // [ID:0000514][Masahiko Higuchi] 2009/09/08 replace end
  private ACLabelContainer youkaigoJoukyouContainer = new ACLabelContainer();
  private ACValueArrayRadioButtonGroup youkaigoJoukyou = new ACValueArrayRadioButtonGroup();
  private ACGroupBox hiddenGroup = new ACGroupBox();
  private ACIntegerCheckBox createCount = new ACIntegerCheckBox();
  private ACIntegerCheckBox validSpan = new ACIntegerCheckBox();
  private ACIntegerCheckBox kangoKubun = new ACIntegerCheckBox();
  private ACTextField shyoubyouChiryouJyoutaiOther = new ACTextField();
  // [ID:0000514][Masahiko Higuchi] 2009/09/08 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
  protected ACLabelContainer sijiKikanFroms = new ACLabelContainer();
  protected ACLabelContainer sijiKikanTos = new ACLabelContainer();
  // [ID:0000514][Masahiko Higuchi] 2009/09/08 replace end
  private ACLabelContainer tentekiFroms = new ACLabelContainer();
  private ACLabelContainer tentekiTos = new ACLabelContainer();

  public boolean noControlError() throws Exception {
     //�G���[�`�F�b�N
     if(!super.noControlError()){
       return false;
     }


     switch (writeDate.getInputStatus()) {
       case IkenshoEraDateTextField.STATE_VALID:
       case IkenshoEraDateTextField.STATE_FUTURE:
         break;
       default:
         ACMessageBox.showExclamation("���t�Ɍ�肪����܂��B");
         writeDate.requestChildFocus();
         return false;
     }

     if(!(houmonSijiSyo.isSelected() || tentekiSijiSyo.isSelected())){
         // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//       ACMessageBox.show("�w������ށA�u�K��Ō�w�����v�A�܂��́u�ݑ�ҖK��_�H���ˎw�����v��\n�I�����Ă��������B");
         ACMessageBox.show("�w������ށA�u"+getHoumonSijiSyoText()+"�v�A�܂��́u"+getTentekiSijiSyoText()+"�v��\n�I�����Ă��������B");
       // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
       houmonSijiSyo.requestFocus();
       return false;
     }
     // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
     sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
     if(!"".equals(sijiKikanFrom.getEra())&& !"".equals(sijiKikanFrom.getYear())){
         sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
         // 2007/10/25 [Masahiko Higuchi] Addition - end
         switch (sijiKikanFrom.getInputStatus()) {
           case IkenshoEraDateTextField.STATE_EMPTY:
               // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
               sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
               // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           case IkenshoEraDateTextField.STATE_VALID:
             // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
             sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             if (sijiKikanTo.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) {
               if (sijiKikanFrom.getDate().compareTo(sijiKikanTo.getDate()) >= 0) {
                 ACMessageBox.showExclamation("�w�����ԁu�J�n���t�v�Ɓu�I�����t�v�͈̔͂Ɍ�肪����܂��B");
                 sijiKikanFrom.requestChildFocus();
                 // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
                 sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                 sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                 // 2007/10/25 [Masahiko Higuchi] Addition - end
                 return false;
               }
             }
             // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
             sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           default:
             ACMessageBox.show("���t�Ɍ�肪����܂��B");
             sijiKikanFrom.requestChildFocus();
             // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
             sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             return false;
         }
     // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
     }
     // 2007/10/25 [Masahiko Higuchi] Addition - end

     // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
     sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
     if(!"".equals(sijiKikanTo.getEra())&& !"".equals(sijiKikanTo.getYear())){
         sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
     // 2007/10/25 [Masahiko Higuchi] Addition - end
         switch (sijiKikanTo.getInputStatus()) {
           case IkenshoEraDateTextField.STATE_EMPTY:
           case IkenshoEraDateTextField.STATE_VALID:
               // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
               sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
               // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           default:
             ACMessageBox.show("���t�Ɍ�肪����܂��B");
             sijiKikanTo.requestChildFocus();
             // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
             sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             return false;
         }
     // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
     }
     // 2007/10/25 [Masahiko Higuchi] Addition - end
     
     // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
     tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
     if(!"".equals(tentekiFrom.getEra())&& !"".equals(tentekiFrom.getYear())){
         tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
         // 2007/10/25 [Masahiko Higuchi] Addition - end
         switch (tentekiFrom.getInputStatus()) {
           case IkenshoEraDateTextField.STATE_EMPTY:
               // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
               tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
               // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           case IkenshoEraDateTextField.STATE_VALID:
             // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
             tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             if (tentekiTo.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) {
               if (tentekiFrom.getDate().compareTo(tentekiTo.getDate()) >= 0) {
                 ACMessageBox.showExclamation("�w�����ԁu�J�n���t�v�Ɓu�I�����t�v�͈̔͂Ɍ�肪����܂��B");
                 tentekiFrom.requestChildFocus();
                 // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
                 tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                 tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                 // 2007/10/25 [Masahiko Higuchi] Addition - end
                 return false;
               }
             }
             // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
             tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           default:
             ACMessageBox.show("���t�Ɍ�肪����܂��B");
             tentekiFrom.requestChildFocus();
             // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
             tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             return false;
         }
     // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
     }
     // 2007/10/25 [Masahiko Higuchi] Addition - end
     
     // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
     tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
     if(!"".equals(tentekiTo.getEra())&& !"".equals(tentekiTo.getYear())){
         tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
         // 2007/10/25 [Masahiko Higuchi] Addition - end
         switch (tentekiTo.getInputStatus()) {
           case IkenshoEraDateTextField.STATE_VALID:
               // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
               tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
               // 2007/10/25 [Masahiko Higuchi] Addition - end
             break;
           default:
             ACMessageBox.show("���t�Ɍ�肪����܂��B");
             tentekiTo.requestChildFocus();
             // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
             tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
             // 2007/10/25 [Masahiko Higuchi] Addition - end
             return false;
         }
     // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
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
    applicantTitle.setText("����");
    sijiKikanClear.setToolTipText("�u�J�n���t�v�Ɓu�I�����t�v���������܂��B");
    tentekiClear.setToolTipText("�u�J�n���t�v�Ɓu�I�����t�v���������܂��B");
    hiddenGroup.setText("�B���p�����[�^");
    createCount.setText("�w�����쐬��");
    createCount.setBindPath("SIJI_CREATE_CNT");
    validSpan.setBindPath("SIJI_YUKOU_KIKAN");
    validSpan.setText("�w�����L������");
    kangoKubun.setText("�w�����Ō�敪");
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

    //����
    this.add(sijishoKind, VRLayout.NORTH);
    this.add(sijiKikan, VRLayout.NORTH);
    this.add(tenteki, VRLayout.NORTH);
    this.add(youkaigoJoukyouGrp, VRLayout.NORTH);
    //���� / �w�������
    sijishoKind.setLayout(new BorderLayout());
    sijishoKind.add(sijishoKindContainer, BorderLayout.CENTER);
    sijishoKindContainer.setText("�w�������");
    VRLayout sijishoKindContainerLayout = new VRLayout();
    sijishoKindContainerLayout.setHgap(0);
    sijishoKindContainerLayout.setVgap(0);
    sijishoKindContainer.setLayout(sijishoKindContainerLayout);
    sijishoKindContainer.add(houmonSijiSyo, VRLayout.FLOW);
    sijishoKindContainer.add(tentekiSijiSyo, VRLayout.FLOW);
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    houmonSijiSyo.setText("�K��Ō�w����");
//    houmonSijiSyo.setBindPath("HOUMON_SIJISYO");
//    tentekiSijiSyo.setText("�ݑ�ҖK��_�H���ˎw����");
//    tentekiSijiSyo.setBindPath("TENTEKI_SIJISYO");
    houmonSijiSyo.setText(getHoumonSijiSyoText());
    houmonSijiSyo.setBindPath("HOUMON_SIJISYO");
    tentekiSijiSyo.setText(getTentekiSijiSyoText());
    tentekiSijiSyo.setBindPath("TENTEKI_SIJISYO");
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    
    //���� / �K��Ō�w������
    sijiKikan.setLayout(new BorderLayout());
    sijiKikan.add(sijiKikanContainer);
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    sijiKikanContainer.setText("�K��Ō�w������");
    sijiKikanContainer.setText(getSijiKikanContainerText());
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    sijiKikanContainer.setLayout(new VRLayout());
    sijiKikanContainer.setFocusForeground(IkenshoConstants.
                                            COLOR_BACK_PANEL_FOREGROUND);
    sijiKikanContainer.setFocusBackground(IkenshoConstants.
                                            COLOR_BACK_PANEL_BACKGROUND);
    sijiKikanContainer.setContentAreaFilled(true);
    sijiKikanFrom.setAgeVisible(false);
    sijiKikanFrom.setAllowedFutureDate(true);
    // 2007/10/25 [Masahiko Higuchi] Delete - begin �����f�t�H���g�\���Ή�
    //sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    // 2007/10/25 [Masahiko Higuchi] Delete - end
    sijiKikanFrom.setBindPath("SIJI_KIKAN_FROM");
    // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
    sijiKikanFrom.setEraPreclusion("����");
    sijiKikanTo.setEraPreclusion("����");
    sijiKikanFrom.setDateTypeConv(false);
    sijiKikanTo.setDateTypeConv(false);
    // 2007/10/25 [Masahiko Higuchi] Addition - end
    sijiKikanSep.setText("����");
    sijiKikanTo.setAllowedFutureDate(true);
    sijiKikanTo.setAgeVisible(false);
    // 2007/10/25 [Masahiko Higuchi] Delete - begin �����f�t�H���g�\���Ή�
    //sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    // 2007/10/25 [Masahiko Higuchi] Delete - end
    sijiKikanTo.setBindPath("SIJI_KIKAN_TO");
    sijiKikanClear.setText("���t����(C)");
    sijiKikanClear.setMnemonic('C');
    // [ID:0000514][Masahiko Higuchi] 2009/09/08 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\ TODO
    // sijiKikanContainer.add(sijiKikanFroms, VRLayout.FLOW);
    sijiKikanFroms.add(sijiKikanFrom, null);
    // sijiKikanContainer.add(sijiKikanSep, VRLayout.FLOW);
    // sijiKikanContainer.add(sijiKikanTos, VRLayout.FLOW);
    sijiKikanTos.add(sijiKikanTo, null);
    // sijiKikanContainer.add(sijiKikanClear, VRLayout.FLOW);
    addSijiKikanContainer();
    // [ID:0000514][Masahiko Higuchi] 2009/09/08 replace end
    //���� / �_�H���ˎw������
    tenteki.setLayout(new BorderLayout());
    tenteki.add(tentekiContainer);
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    tentekiContainer.setText("�_�H���ˎw������");
    tentekiContainer.setText(getTentekiContainerText());
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    tentekiContainer.setLayout(new VRLayout());
    tentekiContainer.setFocusForeground(IkenshoConstants.
                                            COLOR_BACK_PANEL_FOREGROUND);
    tentekiContainer.setFocusBackground(IkenshoConstants.
                                            COLOR_BACK_PANEL_BACKGROUND);
    tentekiContainer.setContentAreaFilled(true);
    tentekiFrom.setAgeVisible(false);
    tentekiFrom.setAllowedFutureDate(true);
    // 2007/10/25 [Masahiko Higuchi] Delete - begin �����f�t�H���g�\���Ή�
    //tentekiFrom.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    // 2007/10/25 [Masahiko Higuchi] Delete - end
    tentekiFrom.setBindPath("TENTEKI_FROM");
    // 2007/10/25 [Masahiko Higuchi] Addition - begin �����f�t�H���g�\���Ή�
    tentekiFrom.setEraPreclusion("����");
    tentekiTo.setEraPreclusion("����");
    tentekiFrom.setDateTypeConv(false);
    tentekiTo.setDateTypeConv(false);
    tentekiFrom.clear();
    tentekiTo.clear();
    // 2007/10/25 [Masahiko Higuchi] Addition - end
    tentekiSep.setText("����");
    tentekiTo.setAgeVisible(false);
    // 2007/10/25 [Masahiko Higuchi] Delete - begin �����f�t�H���g�\���Ή�
    //tentekiTo.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    // 2007/10/25 [Masahiko Higuchi] Delete - end
    tentekiTo.setAllowedFutureDate(true);
    tentekiTo.setBindPath("TENTEKI_TO");
    tentekiClear.setText("���t����(C)");
    tentekiClear.setMnemonic('C');
    tentekiContainer.add(tentekiFroms, VRLayout.FLOW);
    tentekiContainer.add(tentekiSep, VRLayout.FLOW);
    tentekiContainer.add(tentekiTos, VRLayout.FLOW);
    tentekiContainer.add(tentekiClear, VRLayout.FLOW);


    ( (VRLayout) sijiKikanContainer.getLayout()).setAutoWrap(false);
    ( (VRLayout) tentekiContainer.getLayout()).setAutoWrap(false);
    ( (VRLayout) sijishoKindContainer.getLayout()).setAutoWrap(false);
    //���� / �v���F��̏�
    youkaigoJoukyouGrp.setLayout(new BorderLayout());
    youkaigoJoukyouGrp.add(youkaigoJoukyouContainer, BorderLayout.CENTER);
    youkaigoJoukyouContainer.setText("�v���F��̏�");
    youkaigoJoukyouContainer.setLayout(new VRLayout());
    youkaigoJoukyouContainer.add(youkaigoJoukyou, VRLayout.FLOW);
    this.add(hiddenGroup, null);
    hiddenGroup.add(validSpan, null);
    hiddenGroup.add(createCount, null);
    youkaigoJoukyou.setModel(new VRListModelAdapter(
        new VRArrayList(Arrays.asList(new String[] {
                                      "����",
                                      "�v�x���@�@�v���i",
                                      "�P",
                                      "�Q",
                                      "�R",
                                      "�S",
                                      "�T�@�j"}))));

  }

  // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    /**
     * houmonSijiSyo�ɐݒ肷��Text ��Ԃ��܂��B
     * @return houmonSijiSyo�ɐݒ肷��Text
     */
    protected String getHoumonSijiSyoText() {
        return "�K��Ō�w����";
    }

    /**
     * tentekiSijiSyo�ɐݒ肷��Text ��Ԃ��܂��B
     * @return tentekiSijiSyo�ɐݒ肷��Text
     */
    protected String getTentekiSijiSyoText() {
        return "�ݑ�ҖK��_�H���ˎw����";
    }

    /**
     * sijiKikanContainer�ɐݒ肷��Text ��Ԃ��܂��B
     * @return sijiKikanContainer�ɐݒ肷��Text
     */
    protected String getSijiKikanContainerText() {
        return "�K��Ō�w������";
    }

    /**
     * tentekiContainer�ɐݒ肷��Text ��Ԃ��܂��B
     * @return tentekiContainer�ɐݒ肷��Text
     */
    protected String getTentekiContainerText() {
        return "�_�H���ˎw������";
    }
    
    /**
     * �K��Ō�w�����ԃR���e�i�ɃR���|�[�l���g��ǉ����܂��B
     *
     */
    protected void addSijiKikanContainer() {
        sijiKikanContainer.add(sijiKikanFroms, VRLayout.FLOW);
        sijiKikanContainer.add(sijiKikanSep, VRLayout.FLOW);
        sijiKikanContainer.add(sijiKikanTos, VRLayout.FLOW);
        sijiKikanContainer.add(sijiKikanClear, VRLayout.FLOW);
    }

    // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
  
}
