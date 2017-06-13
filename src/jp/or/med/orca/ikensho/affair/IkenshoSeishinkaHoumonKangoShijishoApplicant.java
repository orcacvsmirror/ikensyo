package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaHoumonKangoShijishoApplicant extends IkenshoHoumonKangoShijishoApplicant {

    // �{�ݖ�
    protected ACGroupBox sisetuNameGrp = new ACGroupBox();
    private ACLabelContainer sisetuNameContainer = new ACLabelContainer();
    private IkenshoOptionComboBox sisetuName = new IkenshoOptionComboBox();

    /**
     * �R���X�g���N�^
     */
    public IkenshoSeishinkaHoumonKangoShijishoApplicant() {
        super();
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ��������
     */
    private void jbInit() throws Exception {
        // �w������ށA�_�H�w�����ԁA�v���F��󋵂��\��
        getShijishoKind().setVisible(false);
        getTenteki().setVisible(false);
        youkaigoJoukyouGrp.setVisible(false);
        //����
        this.add(sisetuNameGrp, VRLayout.NORTH);
        // �w������
        sijiKikanContainer.setText(getSijiKikanContainerText());
        // �{�ݖ�
        sisetuName.setMaxLength(30);
        sisetuName.setColumns(30);
        sisetuName.setIMEMode(InputSubset.KANJI);
        sisetuName.setBindPath("SISETU_NM");
        addInnerBindComponent(sisetuName);
        sisetuNameContainer.setText("�{�ݖ�");
        sisetuNameContainer.add(sisetuName, VRLayout.CLIENT);
        sisetuNameGrp.add(sisetuNameContainer, VRLayout.FLOW_INSETLINE_RETURN);
    }

    /**
     * �R���{�ւ̒�^���ݒ�Ȃ�DB�ւ̃A�N�Z�X��K�v�Ƃ��鏉���������𐶐����܂��B
     * @param dbm DBManager
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        applyPoolTeikeibun(sisetuName, IkenshoCommon.TEIKEI_FACILITY_NAME);
        sisetuName.setOptionComboBoxParameters("�{�ݖ�", IkenshoCommon.TEIKEI_FACILITY_NAME, 30);
    }

    /**
     * �G���[�`�F�b�N����
     */
    /* (�� Javadoc)
     * @see jp.or.med.orca.ikensho.affair.IkenshoHoumonKangoShijishoApplicant#noControlError()
     */
    public boolean noControlError() throws Exception {
        // �����K�{�`�F�b�N
        if (isNullText(getApplicantName().getText())) {
          ACMessageBox.show("��������͂��Ă��������B");
          getApplicantName().requestFocus();
          return false;
        }
        // ���ʕK�{�`�F�b�N
        if (getApplicantSex().getSelectedIndex() == getApplicantSex().getNoSelectIndex()) {
          ACMessageBox.show("���ʂ�I�����Ă��������B");
          getApplicantSex().requestChildFocus();
          return false;
        }
        // ���N�����`�F�b�N
        switch (getApplicantBirth().getInputStatus()) {
          case IkenshoEraDateTextField.STATE_VALID:
            break;
          case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("�����̓��t�ł��B");
            getApplicantBirth().requestChildFocus();
            return false;
          default:
            ACMessageBox.showExclamation("���t�Ɍ�肪����܂��B");
            getApplicantBirth().requestChildFocus();
            getApplicantBirth().setParentColor(false);
            return false;
        }
        // �L�����`�F�b�N
        switch (writeDate.getInputStatus()) {
          case IkenshoEraDateTextField.STATE_VALID:
          case IkenshoEraDateTextField.STATE_FUTURE:
            break;
          default:
            ACMessageBox.showExclamation("���t�Ɍ�肪����܂��B");
            writeDate.requestChildFocus();
            return false;
        }
        // �w�����ԁi�J�n���j�`�F�b�N
        sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        if(!"".equals(sijiKikanFrom.getEra())&& !"".equals(sijiKikanFrom.getYear())){
            sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            switch (sijiKikanFrom.getInputStatus()) {
              case IkenshoEraDateTextField.STATE_EMPTY:
                  sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                break;
              case IkenshoEraDateTextField.STATE_VALID:
                sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
                if (sijiKikanTo.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) {
                  if (sijiKikanFrom.getDate().compareTo(sijiKikanTo.getDate()) >= 0) {
                    ACMessageBox.showExclamation("�w�����ԁu�J�n���t�v�Ɓu�I�����t�v�͈̔͂Ɍ�肪����܂��B");
                    sijiKikanFrom.requestChildFocus();
                    sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                    sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                    return false;
                  }
                }
                sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                break;
              default:
                ACMessageBox.show("���t�Ɍ�肪����܂��B");
                sijiKikanFrom.requestChildFocus();
                sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
        }
        // �w�����ԁi�I�����j�`�F�b�N
        sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        if(!"".equals(sijiKikanTo.getEra())&& !"".equals(sijiKikanTo.getYear())){
            sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            switch (sijiKikanTo.getInputStatus()) {
              case IkenshoEraDateTextField.STATE_EMPTY:
              case IkenshoEraDateTextField.STATE_VALID:
                  sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                break;
              default:
                ACMessageBox.show("���t�Ɍ�肪����܂��B");
                sijiKikanTo.requestChildFocus();
                sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
        }
        return true;
    }

    /**
     * sijiKikanContainer�ɐݒ肷��Text��Ԃ��܂��B
     * @return sijiKikanContainer�ɐݒ肷��Text
     */
    protected String getSijiKikanContainerText() {
        return "�w������";
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End