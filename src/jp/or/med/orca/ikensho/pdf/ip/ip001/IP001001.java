
/*
 * Project code name "ORCA"
 * ���t�Ǘ��䒠�\�t�g QKANCHO�iJMA care benefit management software�j
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
 * �A�v��: QKANCHO
 * �J����: �c���@����
 * �쐬��: 2006/04/25  ���{�R���s���[�^�[������� �c���@���� �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� �厡��ӌ��� (I)
 * �T�u�V�X�e�� ���[ (P)
 * �v���Z�X �ȈՒ��[�J�X�^�}�C�Y�c�[�� (001)
 * �v���O���� ���[�I�� (IP001001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
import java.awt.event.ActionEvent;
import java.io.File;

import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.ac.core.ACPDFCreatable;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;

/**
 * ���[�I��(IP001001) 
 */
public class IP001001 extends IP001001Event {
  /**
   * �R���X�g���N�^�ł��B
   */
  public IP001001(){
  }

  //�R���|�[�l���g�C�x���g

  /**
   * �uOK�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected void okActionPerformed(ActionEvent e) throws Exception{
    // �ҏW���钠�[���J���B
    // �R���{�̑I����e����I�������t�@�C���I�u�W�F�N�g(selectedFile)��ݒ肷��B
        String fileName = "";
        switch (getFormats().getSelectedIndex()) {
        case 0:
            fileName = "NewIkensho1.xml";
            break;
        case 1:
            fileName = "NewIkensho2.xml";
            break;
        case 2:
            fileName = "Shijisho.xml";
            break;
        case 3:
            fileName = "ShijishoB.xml";
            break;
        case 4:
            fileName = "SeikyuIchiran.xml";
            break;
        case 5:
            fileName = "SeikyuIchiranTotal.xml";
            break;
        case 6:
            fileName = "Soukatusho.xml";
            break;
        case 7:
            fileName = "IkenshoMeisai.xml";
            break;
        case 8:
            fileName = "PatientList.xml";
            break;
        case 9:
            fileName = "SeikyuIkenshoIchiran.xml";
            break;
        case 10:
            fileName = "CSVFileOutputPatientList.xml";
            break;
        case 11:
            fileName = "IkenshoShien1.xml";
            break;
        case 12:
            fileName = "IkenshoShien2.xml";
            break;
        // [ID:0000514][Masahiko Higuchi] 2009/09/16 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
        case 13:
            fileName = "SpecialShijisho.xml";
            break;
        // [ID:0000514][Masahiko Higuchi] 2009/09/16 add end
        }

        ACFrameEventProcesser processer = ACFrame.getInstance()
                .getFrameEventProcesser();
        if (processer instanceof ACPDFCreatable) {
            ACPDFCreatable pdfCreatable = (ACPDFCreatable) processer;
            fileName = pdfCreatable.getPrintFormatDirectory() + fileName;
        }
        setSelectedFile(new File(fileName));

        // ��ʂ�j������B
        dispose();
  }

  /**
   * �u�L�����Z���v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected void cancelActionPerformed(ActionEvent e) throws Exception{
    // ��ʂ�j������B
      dispose();
  }

  public static void main(String[] args) {
    //�f�t�H���g�f�o�b�O�N��
    VRMap param = new VRHashMap();
    //param�ɓn��p�����^���l�߂Ď��s���邱�ƂŁA�ȈՃf�o�b�O���\�ł��B
    ACFrame.debugStart(new ACAffairInfo(IP001001.class.getName(), param));
  }

  //�����֐�

  /**
   * �u���[�I�������v�Ɋւ��鏈�����s�Ȃ��܂��B
   * @throws Exception ������O
   */
  public File showModal(File openedFile) throws Exception{
    // ���[�I�����������s����B
    // �@�����̃t�@�C���I�u�W�F�N�g����A�R���{�̑I����e��ύX����B
      int formatIndex = 0;
        if (openedFile != null) {
            String fileName = openedFile.getName().toLowerCase();
            if (fileName.endsWith("newikensho1.xml")) {
                formatIndex = 0;
            } else if (fileName.endsWith("newikensho2.xml")) {
                formatIndex = 1;
                // [ID:0000514][Masahiko Higuchi] 2009/09/16 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
            } else if ("specialshijisho.xml".equals(fileName)) {
                formatIndex = 13;
            // [ID:0000514][Masahiko Higuchi] 2009/09/16 add end
            } else if (fileName.endsWith("shijisho.xml")) {
                formatIndex = 2;
            } else if (fileName.endsWith("shijishob.xml")) {
                formatIndex = 3;
            } else if (fileName.endsWith("seikyuichiran.xml")) {
                formatIndex = 4;
            } else if (fileName.endsWith("seikyuichirantotal.xml")) {
                formatIndex = 5;
            } else if (fileName.endsWith("soukatusho.xml")) {
                formatIndex = 6;
            } else if (fileName.endsWith("ikenshomeisai.xml")) {
                formatIndex = 7;
            } else if (fileName.endsWith("csvfileoutputpatientlist.xml")) {
                formatIndex = 10;
            } else if (fileName.endsWith("patientlist.xml")) {
                formatIndex = 8;
            } else if (fileName.endsWith("seikyuikenshoichiran.xml")) {
                formatIndex = 9;
            } else if (fileName.endsWith("ikenshoshien1.xml")) {
                formatIndex = 11;
            } else if (fileName.endsWith("ikenshoshien2.xml")) {
                formatIndex = 12;
            }
        }
        getFormats().setSelectedIndex(formatIndex);
        
        setTitle("���[�I��");
    // �@��ʂ�\������B
      setVisible(true);
    // �@�I�������t�@�C���I�u�W�F�N�g��Ԃ��B
return getSelectedFile();
  }

}
