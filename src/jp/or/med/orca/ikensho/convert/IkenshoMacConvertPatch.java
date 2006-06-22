/*
 * Project code name "ORCA"
 * �厡��ӌ����쐬�\�t�g ITACHI�iJMA IKENSYO software�j
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "ITACHI (JMA IKENSYO software)".
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
 * �A�v��: ITACHI
 * �J����: �����L
 * �쐬��: 2006/01/30  ���{�R���s���[�^������� �����L �V�K�쐬
 * �X�V��: ----/--/--
 *****************************************************************
 */

package jp.or.med.orca.ikensho.convert;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.text.ACDateFormat;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.logging.VRLogger;
import jp.or.med.orca.ikensho.affair.IkenshoFrameEventProcesser;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * �厡��ӌ���CSV�o�͂ŏo�͂��ꂽCSV�t�@�C���̎�荞�݂��s���܂��B
 * 
 * 1.IkensyoMacConvert�Ŋ��Ƀf�[�^�ڍs�����s����Ă��邱�Ƃ��O��ł��B
 * 2.Mac�ŏo�͂��ꂽ�厡��ӌ���CSV��Windows�ŏo�͂����厡��ӌ���CSV��
 * ���J�쎮�_���Ɋւ���t�H�[�}�b�g���قȂ邽�߁AWindows�ŏo�͂���
 * �厡��ӌ���CSV�̎�荞�݂͍s���܂���B
 * 3.���Ҋ�{��񂩂�A�����A���Ȏ����A���N�����A���ʂ����v����
 * �f�[�^���������܂��B
 * �@�Y�����銳�҂����݂���ꍇ
 * �@�@�Ώۊ��҂Ɋ֘A����㌩���Ƃ��ăf�[�^�ڍs���s���܂��B
 * �@�Y�����銳�҂����݂��Ȃ��ꍇ
 * �@�@�ӌ����̃f�[�^���犳�Ҋ�{�����쐬���A�o�^���܂��B
 * 
 * @author shin fujihara
 * @version 1.0 2006/01/30
 */
public class IkenshoMacConvertPatch extends JFrame {

    private static final String MSG_INFO = "Information";
    private static final String FILTER_BUTTON_NAME = "�ݒ�";
    private static final String FILTER_CSV = ".*\\.[Cc][Ss][Vv]$";

    private static final int PATIENT_PATIENT_NM = 30;
    private static final int PATIENT_PATIENT_KN = 60;
    private static final int PATIENT_AGE = 5;
    private static final int PATIENT_POST_CD = 10;
    private static final int PATIENT_ADDRESS = 100;
    private static final int PATIENT_TEL1 = 10;
    private static final int PATIENT_TEL2 = 20;

    private static final int COMMON_IKN_SIS_PATIENT_NM = 30;
    private static final int COMMON_IKN_SIS_PATIENT_KN = 60;
    private static final int COMMON_IKN_SIS_AGE = 5;
    private static final int COMMON_IKN_SIS_POST_CD = 10;
    private static final int COMMON_IKN_SIS_ADDRESS = 100;
    private static final int COMMON_IKN_SIS_TEL1 = 10;
    private static final int COMMON_IKN_SIS_TEL2 = 10;
    private static final int COMMON_IKN_SIS_SINDAN_NM1 = 100;
    private static final int COMMON_IKN_SIS_SINDAN_NM2 = 100;
    private static final int COMMON_IKN_SIS_SINDAN_NM3 = 100;
    private static final int COMMON_IKN_SIS_MT_STS = 2000;
    private static final int COMMON_IKN_SIS_MEDICINE = 30;
    private static final int COMMON_IKN_SIS_DOSAGE = 10;
    private static final int COMMON_IKN_SIS_UNIT = 10;
    private static final int COMMON_IKN_SIS_USAGE = 50;
    private static final int COMMON_IKN_SIS_DR_NM = 30;
    private static final int COMMON_IKN_SIS_MI_NM = 60;
    private static final int COMMON_IKN_SIS_MI_POST_CD = 10;
    private static final int COMMON_IKN_SIS_MI_ADDRESS = 100;
    private static final int COMMON_IKN_SIS_MI_TEL1 = 10;
    private static final int COMMON_IKN_SIS_MI_TEL2 = 20;
    private static final int COMMON_IKN_SIS_MI_FAX1 = 10;
    private static final int COMMON_IKN_SIS_MI_FAX2 = 20;
    
    private static final int IKN_ORIGIN_TAKA_OTHER = 30;
    private static final int IKN_ORIGIN_MONDAI_OTHER_NM = 30;
    private static final int IKN_ORIGIN_SEISIN_NM = 100;
    private static final int IKN_ORIGIN_SENMONI_NM = 30;
    private static final int IKN_ORIGIN_WEIGHT = 10;
    private static final int IKN_ORIGIN_HEIGHT = 10;
    private static final int IKN_ORIGIN_SISIKESSON_BUI = 20;
    private static final int IKN_ORIGIN_KINRYOKU_TEIKA_BUI = 20;
    private static final int IKN_ORIGIN_JOKUSOU_BUI = 20;
    private static final int IKN_ORIGIN_HIFUSIKKAN_BUI = 20;
    private static final int IKN_ORIGIN_BYOUTAITA_NM = 30;
    private static final int IKN_ORIGIN_IGAKUTEKIKANRI_OTHER_NM = 30;
    private static final int IKN_ORIGIN_KETUATU_RYUIJIKOU = 60;
    private static final int IKN_ORIGIN_SESHOKU_RYUIJIKOU = 60;
    private static final int IKN_ORIGIN_ENGE_RYUIJIKOU = 60;
    private static final int IKN_ORIGIN_IDOU_RYUIJIKOU = 60;
    private static final int IKN_ORIGIN_KAIGO_OTHER = 100;
    private static final int IKN_ORIGIN_KANSENSHOU_NM = 100;
    private static final int IKN_ORIGIN_IKN_TOKKI = 1000;
    private static final int IKN_ORIGIN_HASE_SCORE = 10;
    private static final int IKN_ORIGIN_P_HASE_SCORE = 10;
    private static final int IKN_ORIGIN_INST_SEL_PR1 = 20;
    private static final int IKN_ORIGIN_INSURED_NO = 30;
    private static final int IKN_ORIGIN_REQ_NO = 16;
    private static final int IKN_ORIGIN_INSURER_NO = 20;
    private static final int IKN_ORIGIN_INSURER_NM = 100;

    private String title;
    private BorderLayout bl = new BorderLayout();
    private FlowLayout fl = new FlowLayout();
    private static ACDateFormat dateFormatter = new ACDateFormat("yyyy/MM/dd");
    private static ACDateFormat timeFormatter = new ACDateFormat(
            "yyyy/MM/dd HH:mm:ss");

    private JButton btnStart = new JButton();
    private JFileChooser chooser = new JFileChooser();
    private JPanel plCsv = new JPanel();
    private JTextField txtDb = new JTextField();
    private JTextField txtIkensho = new JTextField();
    private JLabel lblDb = new JLabel();
    private JLabel lblIkensho = new JLabel();
    private JButton btnDb = new JButton();
    private JButton btnIkensho = new JButton();

    public IkenshoMacConvertPatch() {

    }

    public IkenshoMacConvertPatch(String title) {
        this.title = title;
        try {
            // ACFrame.getInstance().setFrameEventProcesser(new
            // IkenshoFrameEventProcesser());
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(bl);
        this.getContentPane().add(btnStart, BorderLayout.SOUTH);
        this.getContentPane().add(plCsv, BorderLayout.CENTER);
        this.setSize(new Dimension(780, 200));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - this.getWidth()) / 2,
                (screenSize.height - this.getHeight()) / 2, this.getWidth(),
                this.getHeight());
        this.setVisible(true);
        this.setTitle(title);
        this.setResizable(false);
        plCsv.setLayout(fl);
        plCsv.add(lblIkensho, null);
        plCsv.add(txtIkensho, null);
        plCsv.add(btnIkensho, null);
        lblDb.setText("�ڍs��f�[�^�x�[�X");
        lblDb.setMaximumSize(new Dimension(180, 15));
        lblDb.setPreferredSize(new Dimension(180, 15));
        lblDb.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIkensho.setText("�厡��ӌ���");
        lblIkensho.setMaximumSize(new Dimension(180, 15));
        lblIkensho.setPreferredSize(new Dimension(180, 15));
        lblIkensho.setHorizontalAlignment(SwingConstants.RIGHT);
        txtDb.setColumns(40);
        txtIkensho.setColumns(40);
        btnDb.setText("...");
        btnIkensho.setText("...");
        
        btnStart.addActionListener(new IkenshoMacConvert_buttonSearch_actionAdapter(this));
        btnIkensho.addActionListener(new IkenshoMacConvert_btnIkensho_actionAdapter(this));

        btnStart.setText("�f�[�^�ڍs�J�n");
        btnStart.setPreferredSize(new Dimension(700, 100));
    }

    public static void main(String[] args) {

        try {
            ACFrame.setVRLookAndFeel();
            ACFrame.getInstance().setFrameEventProcesser(
                    new IkenshoFrameEventProcesser());
            IkenshoMacConvertPatch ikenshoMacConvert = new IkenshoMacConvertPatch(
                    "�㌩���f�[�^�ڍs�c�[��");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // try {
        //        
        // ACFrame.setVRLookAndFeel();
        //        
        // ACFrame.getInstance().setFrameEventProcesser(
        // new IkenshoFrameEventProcesser());
        // ACFrame.getInstance().next(
        // new ACAffairInfo(IkenshoMacConvert.class.getName(),
        // new VRHashMap(), "�㌩���f�[�^�ڍs�c�[��"));
        // ACFrame frame = ACFrame.getInstance();
        // frame.setVisible(true);
        // // frame.show();
        // } catch (Exception ex) {
        // ex.printStackTrace();
        // }

    }

    /**
     * ���ҏ��f�[�^�ڍs����
     * 
     * @param file File
     * @param nowDate Date
     * @param dbManager NCFirebirdDBManager
     */
    private int convertPatient(String[] values, Date nowDate,
            IkenshoFirebirdDBManager dbManager) {
        
        int result = Integer.MIN_VALUE;
        
        try {
            String[] tel = splitTelNumber(values[21]);

            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO PATIENT (");
            //���Җ�
            if (isValidValue(values[15])) {
                sb.append("PATIENT_NM,");
            }
            //���Җ�����
            if (isValidValue(values[14])) {
                sb.append("PATIENT_KN,");
            }
            //����
            if (isValidValue(values[18])) {
                sb.append("SEX,");
            }
            //���N����
            if (isValidValue(values[16])) {
                sb.append("BIRTHDAY,");
            }
            //�N��
            if (isValidValue(values[17])) {
                sb.append("AGE,");
            }
            //�X�֔ԍ�
            if (isValidValue(values[19])) {
                sb.append("POST_CD,");
            }
            //�Z��
            if (isValidValue(values[20])) {
                sb.append("ADDRESS,");
            }
            //�d�b�ǔ�
            if (isValidValue(tel[0])) {
                sb.append("TEL1,");
            }
            //�d�b
            if (isValidValue(tel[1])) {
                sb.append("TEL2,");
            }
            //�ŏI�X�V��
            sb.append("LAST_TIME");
            sb.append(")");

            sb.append(" VALUES (");
            
            //PATIENT_NM
            setValue(sb,values[15],PATIENT_PATIENT_NM);
            //PATIENT_KN
            setValue(sb,values[14],PATIENT_PATIENT_KN);
            //SEX
            setValue(sb,values[18]);
            //BIRTHDAY
            if (isValidValue(values[16])) {
                sb.append("'"
                        + dateFormatter.format(dateFormatter.parse(values[16]))
                        + "',");

            }
            //AGE
            setValue(sb,values[17],PATIENT_AGE);
            //POST_CD
            setValue(sb,values[19],PATIENT_POST_CD);
            //ADDRESS
            setValue(sb,values[20],PATIENT_ADDRESS);
            //TEL1
            setValue(sb,tel[0],PATIENT_TEL1);
            //TEL2
            setValue(sb,tel[1],PATIENT_TEL2);
            
            sb.append("'" + timeFormatter.format(nowDate) + "'");
            sb.append(")");

            // SQL�����s
            executeUpdateConvert(dbManager,sb.toString());
            
            //�o�^�������҂�ID���擾
            VRList list = (VRList)dbManager.executeQuery("SELECT GEN_ID(GEN_PATIENT,0) FROM RDB$DATABASE");
            
            if((list == null) || (list.size() > 0)){
                VRMap map = (VRMap)list.get(0);
                result = Integer.parseInt(String.valueOf(map.get("GEN_ID")));
            }
            
        } catch (Exception ex) {
            VRLogger.warning(ex);
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * �厡��ӌ����f�[�^�ڍs����
     * 
     * @param file File
     * @param nowDate Date
     * @param dbManager NCFirebirdDBManager
     */
    private void convertIkensho(File file, Date nowDate,
            IkenshoFirebirdDBManager dbManager) {
        try {
            
            // �f�[�^��������
            ArrayList list = splitCsvList(file);
            // �f�[�^�x�[�X�ɑ}��
            for (int i = 0; i < list.size(); i++) {

                String[] values = (String[]) list.get(i);
                // �t�@�C�������m�F
                if (values.length != 104)
                    continue;
                
                //����ID���擾
                int patientNo = getPatientNo(values,dbManager,nowDate);
                //����ID�̎擾�Ɏ��s�����珈�����s��Ȃ�
                if(patientNo == Integer.MIN_VALUE) {
                    VRLogger.warning("���ɍŐV�̈ӌ��������݂��܂��B�����𒆎~���܂����B:line " + (i +1));
                    continue;
                }

                //COMMON_IKN_SIS�X�V�p��SQL���쐬
                String common_ikn_sis_query = getCommonIknSisSQL(values,patientNo,nowDate);
                //IKN_ORIGIN�X�V�p��SQL���쐬
                String ikn_origin_query = getIknOriginSQL(values,patientNo,nowDate);
                //IKN_BILL�X�V�p��SQL���쐬
                String ikn_bill_query = getIknBillSQL(patientNo,nowDate);

                // SQL�����s(COMMON_IKN_SIS�X�V)
                executeUpdateConvert(dbManager,common_ikn_sis_query);
                //SQL�����s(IKN_ORIGIN�X�V)
                executeUpdateConvert(dbManager,ikn_origin_query);
                //SQL�����s(IKN_BILL���R�[�h�쐬)
                dbManager.executeUpdate(ikn_bill_query);

            }
        } catch (Exception ex) {
            VRLogger.warning(ex.toString());
            ex.printStackTrace();
        }
    }
    
    /**
     * COMMON_IKN_SIS�e�[�u���ւ̃C���T�[�g�����쐬���܂��B
     * @param values
     * @return
     * @throws Exception
     */
    private String getCommonIknSisSQL(String[] values,int patientNo,Date nowDate) throws Exception {
        StringBuffer sb = new StringBuffer();
        
        String[] tel = splitTelNumber(values[21]);
        String[] mi_tel = splitTelNumber(values[26]);
        String[] mi_fax = splitTelNumber(values[27]);
        String[] remedy = getRemedyData(values[42]);
        
        sb.append("INSERT INTO COMMON_IKN_SIS (");
        //����ID
        sb.append("PATIENT_NO,");
        sb.append("EDA_NO,");
        sb.append("DOC_KBN,");
        
        if(isValidValue(values[15])){
            sb.append("PATIENT_NM,");
        }
        if(isValidValue(values[14])){
            sb.append("PATIENT_KN,");
        }
        if(isValidValue(values[18])){
            sb.append("SEX,");
        }
        if(isValidValue(values[16])){
            sb.append("BIRTHDAY,");
        }
        if(isValidValue(values[17])){
            sb.append("AGE,");
        }
        if(isValidValue(values[19])){
            sb.append("POST_CD,");
        }
        if(isValidValue(values[20])){
            sb.append("ADDRESS,");
        }
        if(isValidValue(tel[0])){
            sb.append("TEL1,");
        }
        if(isValidValue(tel[1])){
            sb.append("TEL2,");
        }
        if(isValidValue(values[34])){
            sb.append("SINDAN_NM1,");
        }
        if(isValidValue(values[36])){
            sb.append("SINDAN_NM2,");
        }
        if(isValidValue(values[38])){
            sb.append("SINDAN_NM3,");
        }
        if(isValidValue(values[35])){
            sb.append("HASHOU_DT1,");
        }
        if(isValidValue(values[37])){
            sb.append("HASHOU_DT2,");
        }
        if(isValidValue(values[39])){
            sb.append("HASHOU_DT3,");
        }
        //���a�̌o��
        if(isValidValue(remedy[0])){
            sb.append("MT_STS,");
        }
        if(isValidValue(remedy[1])){
            sb.append("MEDICINE1,");
        }
        if(isValidValue(remedy[2])){
            sb.append("DOSAGE1,");
        }
        if(isValidValue(remedy[3])){
            sb.append("UNIT1,");
        }
        if(isValidValue(remedy[4])){
            sb.append("USAGE1,");
        }
        if(isValidValue(remedy[5])){
            sb.append("MEDICINE2,");
        }
        if(isValidValue(remedy[6])){
            sb.append("DOSAGE2,");
        }
        if(isValidValue(remedy[7])){
            sb.append("UNIT2,");
        }
        if(isValidValue(remedy[8])){
            sb.append("USAGE2,");
        }
        if(isValidValue(remedy[9])){
            sb.append("MEDICINE3,");
        }
        if(isValidValue(remedy[10])){
            sb.append("DOSAGE3,");
        }
        if(isValidValue(remedy[11])){
            sb.append("UNIT3,");
        }
        if(isValidValue(remedy[12])){
            sb.append("USAGE3,");
        }
        if(isValidValue(remedy[13])){
            sb.append("MEDICINE4,");
        }
        if(isValidValue(remedy[14])){
            sb.append("DOSAGE4,");
        }
        if(isValidValue(remedy[15])){
            sb.append("UNIT4,");
        }
        if(isValidValue(remedy[16])){
            sb.append("USAGE4,");
        }
        if(isValidValue(remedy[17])){
            sb.append("MEDICINE5,");
        }
        if(isValidValue(remedy[18])){
            sb.append("DOSAGE5,");
        }
        if(isValidValue(remedy[19])){
            sb.append("UNIT5,");
        }
        if(isValidValue(remedy[20])){
            sb.append("USAGE5,");
        }
        if(isValidValue(remedy[21])){
            sb.append("MEDICINE6,");
        }
        if(isValidValue(remedy[22])){
            sb.append("DOSAGE6,");
        }
        if(isValidValue(remedy[23])){
            sb.append("UNIT6,");
        }
        if(isValidValue(remedy[24])){
            sb.append("USAGE6,");
        }
        if(isValidValue(values[46])){
            sb.append("NETAKIRI,");
        }
        if(isValidValue(values[47])){
            sb.append("CHH_STS,");
        }
        if(isValidValue(values[40])){
            sb.append("SHJ_ANT,");
        }
        //�_�H�Ǘ�
        sb.append("TNT_KNR,");
        //���S�Ö��h�{
        sb.append("CHU_JOU_EIYOU,");
        //����
        sb.append("TOUSEKI,");
        //�l�H���
        sb.append("JINKOU_KOUMON,");
        //�_�f�Ö@
        sb.append("OX_RYO,");
        //�l�H�ċz��
        sb.append("JINKOU_KOKYU,");
        //�C�ǐ؊J���u
        sb.append("KKN_SEK_SHOCHI,");
        //�u�ɊŌ�
        sb.append("TOUTU,");
        //�o�ǉh�{
        sb.append("KEKN_EIYOU,");
        
        //���j�^�[����
        sb.append("MONITOR,");
        //��n���u
        sb.append("JOKUSOU_SHOCHI,");
        
        //�J�e�[�e��
        sb.append("CATHETER,");
        
        if(isValidValue(values[22])){
            sb.append("DR_NM,");
        }
        if(isValidValue(values[23])){
            sb.append("MI_NM,");
        }
        if(isValidValue(values[24])){
            sb.append("MI_POST_CD,");
        }
        if(isValidValue(values[25])){
            sb.append("MI_ADDRESS,");
        }
        if(isValidValue(mi_tel[0])){
            sb.append("MI_TEL1,");
        }
        if(isValidValue(mi_tel[1])){
            sb.append("MI_TEL2,");
        }
        if(isValidValue(mi_fax[0])){
            sb.append("MI_FAX1,");
        }
        if(isValidValue(mi_fax[1])){
            sb.append("MI_FAX2,");
        }
        sb.append("LAST_TIME");
        sb.append(")");

        sb.append(" VALUES (");
        
        //PATIENT_NO
        sb.append(patientNo + ",");
        //EDA_NO
        sb.append("1,");
        //DOC_KBN
        sb.append("1,");
        //PATIENT_NM
        setValue(sb, values[15],COMMON_IKN_SIS_PATIENT_NM);
        //PATIENT_KN
        setValue(sb,values[14],COMMON_IKN_SIS_PATIENT_KN);
        //SEX
        setValue(sb,values[18]);
        //BIRTHDAY
        if(isValidValue(values[16])){
            sb.append("'");
            sb.append(dateFormatter.format(dateFormatter.parseObject(values[16])));
            sb.append("',");
        }
        //AGE
        setValue(sb,values[17],COMMON_IKN_SIS_AGE);
        //POST_CD
        setValue(sb,values[19],COMMON_IKN_SIS_POST_CD);
        //ADDRESS
        setValue(sb,values[20],COMMON_IKN_SIS_ADDRESS);
        //TEL1
        setValue(sb,tel[0],COMMON_IKN_SIS_TEL1);
        //TEL2
        setValue(sb,tel[1],COMMON_IKN_SIS_TEL2);
        //SINDAN_NM1
        setValue(sb,values[34],COMMON_IKN_SIS_SINDAN_NM1);
        //SINDAN_NM2
        setValue(sb,values[36],COMMON_IKN_SIS_SINDAN_NM2);
        //SINDAN_NM3
        setValue(sb,values[38],COMMON_IKN_SIS_SINDAN_NM3);
        //HASHOU_DT1
        if(isValidValue(values[35])){
            if("�s��".equals(values[35])){
                sb.append("'�s��00�N00��00��',");
            } else {
                sb.append("'" + values[35] + "',");
            }
        }
        //HASHOU_DT2
        if(isValidValue(values[37])){
            if("�s��".equals(values[37])){
                sb.append("'�s��00�N00��00��',");
            } else {
                sb.append("'" + values[37] + "',");
            }
        }
        //HASHOU_DT3
        if(isValidValue(values[39])){
            if("�s��".equals(values[39])){
                sb.append("'�s��00�N00��00��',");
            } else {
                sb.append("'" + values[39] + "',");
            }
        }
        //MT_STS
        setValue(sb,remedy[0],COMMON_IKN_SIS_MT_STS);
        //MEDICINE1
        setValue(sb,remedy[1],COMMON_IKN_SIS_MEDICINE);
        //DOSAGE1
        setValue(sb,remedy[2],COMMON_IKN_SIS_DOSAGE);
        //UNIT1
        setValue(sb,remedy[3],COMMON_IKN_SIS_UNIT);
        //USAGE1
        setValue(sb,remedy[4],COMMON_IKN_SIS_USAGE);
        //MEDICINE2
        setValue(sb,remedy[5],COMMON_IKN_SIS_MEDICINE);
        //DOSAGE2
        setValue(sb,remedy[6],COMMON_IKN_SIS_DOSAGE);
        //UNIT2
        setValue(sb,remedy[7],COMMON_IKN_SIS_UNIT);
        //USAGE2
        setValue(sb,remedy[8],COMMON_IKN_SIS_USAGE);
        //MEDICINE3
        setValue(sb,remedy[9],COMMON_IKN_SIS_MEDICINE);
        //DOSAGE3
        setValue(sb,remedy[10],COMMON_IKN_SIS_DOSAGE);
        //UNIT3
        setValue(sb,remedy[11],COMMON_IKN_SIS_UNIT);
        //USAGE3
        setValue(sb,remedy[12],COMMON_IKN_SIS_USAGE);
        //MEDICINE4
        setValue(sb,remedy[13],COMMON_IKN_SIS_MEDICINE);
        //DOSAGE4
        setValue(sb,remedy[14],COMMON_IKN_SIS_DOSAGE);
        //UNIT4
        setValue(sb,remedy[15],COMMON_IKN_SIS_UNIT);
        //USAGE4
        setValue(sb,remedy[16],COMMON_IKN_SIS_USAGE);
        //MEDICINE5
        setValue(sb,remedy[17],COMMON_IKN_SIS_MEDICINE);
        //DOSAGE5
        setValue(sb,remedy[18],COMMON_IKN_SIS_DOSAGE);
        //UNIT5
        setValue(sb,remedy[19],COMMON_IKN_SIS_UNIT);
        //USAGE5
        setValue(sb,remedy[20],COMMON_IKN_SIS_USAGE);
        //MEDICINE6
        setValue(sb,remedy[21],COMMON_IKN_SIS_MEDICINE);
        //DOSAGE6
        setValue(sb,remedy[22],COMMON_IKN_SIS_DOSAGE);
        //UNIT6
        setValue(sb,remedy[23],COMMON_IKN_SIS_UNIT);
        //USAGE6
        setValue(sb,remedy[24],COMMON_IKN_SIS_USAGE);
        //NETAKIRI
        setValue(sb,values[46]);
        //CHH_STS
        setValue(sb,values[47]);
        //SHJ_ANT
        setValue(sb,values[40]);
        
        char[] value43 = values[43].toCharArray();
        //TNT_KNR
        sb.append(value43[0] + ",");
        //CHU_JOU_EIYOU
        sb.append(value43[1] + ",");
        //TOUSEKI
        sb.append(value43[2] + ",");
        //JINKOU_KOUMON
        sb.append(value43[3] + ",");
        //OX_RYO
        sb.append(value43[4] + ",");
        //JINKOU_KOKYU
        sb.append(value43[5] + ",");
        //KKN_SEK_SHOCHI
        sb.append(value43[6] + ",");
        //TOUTU
        sb.append(value43[7] + ",");
        //KEKN_EIYOU
        sb.append(value43[8] + ",");
        
        char[] value44 = values[44].toCharArray();
        //MONITOR
        sb.append(value44[0] + ",");
        //JOKUSOU_SHOCHI
        sb.append(value44[1] + ",");
        //CATHETER
        sb.append(values[45] + ",");
        //DR_NM
        setValue(sb,values[22],COMMON_IKN_SIS_DR_NM);
        //MI_NM
        setValue(sb,values[23],COMMON_IKN_SIS_MI_NM);
        //MI_POST_CD
        setValue(sb,values[24],COMMON_IKN_SIS_MI_POST_CD);
        //MI_ADDRESS
        setValue(sb,values[25],COMMON_IKN_SIS_MI_ADDRESS);
        //MI_TEL1
        setValue(sb,mi_tel[0],COMMON_IKN_SIS_MI_TEL1);
        //MI_TEL2
        setValue(sb,mi_tel[1],COMMON_IKN_SIS_MI_TEL2);
        //MI_FAX1
        setValue(sb,mi_fax[0],COMMON_IKN_SIS_MI_FAX1);
        //MI_FAX2
        setValue(sb,mi_fax[1],COMMON_IKN_SIS_MI_FAX2);
        //LAST_TIME
        sb.append("'" + timeFormatter.format(nowDate) + "'");
        sb.append(")");
        
        return sb.toString();
    }
    
    /**
     * IKN_ORIGIN�e�[�u���ւ̃C���T�[�g�����쐬���܂��B
     * @param values
     * @return
     * @throws Exception
     */
    private String getIknOriginSQL(String[] values,int patientNo, Date nowDate) throws Exception {
        StringBuffer sb = new StringBuffer();
        String[] tokki = getTokki(values[103]);
        
        sb.append("INSERT INTO IKN_ORIGIN (");
        sb.append("PATIENT_NO,");
        sb.append("EDA_NO,");
        sb.append("FORMAT_KBN,");
        //��t����
        sb.append("DR_CONSENT,");
        //�L����
        if(isValidValue(values[13])){
            sb.append("KINYU_DT,");
        }
        //�쐬��
        if(isValidValue(values[30])){
            sb.append("IKN_CREATE_CNT,");
        }
        //�ŏI�f�Ó�
        if(isValidValue(values[29])){
            sb.append("LASTDAY,");
        }
        //���Ȏ�f�̗L��
        if(isValidValue(values[32])){
            sb.append("TAKA,");
        }
        //���Ȏ�f�E��f���ځE���̑����e
        if(isValidValue(values[33])){
            sb.append("TAKA_OTHER,");
        }
        //�Z���L��
        if(isValidValue(values[48])){
            sb.append("TANKI_KIOKU,");
        }
        //����̈ӎv������s�����߂̔F�m�\��
        if(isValidValue(values[49])){
            sb.append("NINCHI,");
        }
        //�����̈ӎv�̓`�B�\��
        if(isValidValue(values[50])){
            sb.append("DENTATU,");
        }
        //�H��
        if(isValidValue(values[51])){
            sb.append("SHOKUJI,");
        }
        //���s���L��
        sb.append("GNS_GNC,");
        sb.append("MOUSOU,");
        sb.append("CHUYA,");
        sb.append("BOUGEN,");
        sb.append("BOUKOU,");
        sb.append("TEIKOU,");
        sb.append("HAIKAI,");
        sb.append("FUSIMATU,");
        sb.append("FUKETU,");
        sb.append("ISHOKU,");
        sb.append("SEITEKI_MONDAI,");
        sb.append("MONDAI_OTHER,");
        //���s�����̑�
        if(isValidValue(values[54])){
            sb.append("MONDAI_OTHER_NM,");
        }
        //���_�_�o�Ǐ�E�L��
        if(isValidValue(values[55])){
            sb.append("SEISIN,");
        }
        //���_�_�o�Ǐ�E�Ǐ�
        if(isValidValue(values[56])){
            sb.append("SEISIN_NM,");
        }
        //�����f�E�L��
        if(isValidValue(values[57])){
            sb.append("SENMONI,");
        }
        //�����f�E�ڍ�
        if(isValidValue(values[58])){
            sb.append("SENMONI_NM,");
        }
        //�����r
        if(isValidValue(values[59])){
            sb.append("KIKIUDE,");
        }
        //�̏d
        if(isValidValue(values[60])){
            sb.append("WEIGHT,");
        }
        //�g��
        if(isValidValue(values[61])){
            sb.append("HEIGHT,");
        }
        //�l������
        if(isValidValue(values[62])){
            sb.append("SISIKESSON,");
        }
        //�l����������
        if(isValidValue(values[63])){
            sb.append("SISIKESSON_BUI,");
        }
        //�l���������x
        if(isValidValue(values[64])){
            sb.append("SISIKESSON_TEIDO,");
        }
        //�ؗ͒ቺ
        if(isValidValue(values[68])){
            sb.append("KINRYOKU_TEIKA,");
        }
        //�ؗ͒ቺ����
        if(isValidValue(values[69])){
            sb.append("KINRYOKU_TEIKA_BUI,");
        }
        //�ؗ͒ቺ���x
        if(isValidValue(values[70])){
            sb.append("KINRYOKU_TEIKA_TEIDO,");
        }
        //���
        if(isValidValue(values[71])){
            sb.append("JOKUSOU,");
        }
        //��ጕ���
        if(isValidValue(values[72])){
            sb.append("JOKUSOU_BUI,");
        }
        //��ጒ��x
        if(isValidValue(values[73])){
            sb.append("JOKUSOU_TEIDO,");
        }
        //�畆����
        if(isValidValue(values[74])){
            sb.append("HIFUSIKKAN,");
        }
        //�畆��������
        if(isValidValue(values[75])){
            sb.append("HIFUSIKKAN_BUI,");
        }
        //�畆�������x
        if(isValidValue(values[76])){
            sb.append("HIFUSIKKAN_TEIDO,");
        }
        //�㎈
        if(isValidValue(values[83])){
            sb.append("JOUSI_SICCHOU_MIGI,");
            sb.append("JOUSI_SICCHOU_HIDARI,");
        }
        //����
        if(isValidValue(values[85])){
            sb.append("KASI_SICCHOU_MIGI,");
            sb.append("KASI_SICCHOU_HIDARI,");
        }
        //�̊�
        if(isValidValue(values[84])){
            sb.append("TAIKAN_SICCHOU_MIGI,");
            sb.append("TAIKAN_SICCHOU_HIDARI,");
        }
        //���݁A�����̉\���������a��
        sb.append("NYOUSIKKIN,");
        sb.append("TENTOU_KOSSETU,");
        sb.append("HAIKAI_KANOUSEI,");
        sb.append("JOKUSOU_KANOUSEI,");
        sb.append("ENGESEIHAIEN,");
        sb.append("CHOUHEISOKU,");
        sb.append("EKIKANKANSEN,");
        sb.append("SINPAIKINOUTEIKA,");
        sb.append("ITAMI,");
        sb.append("DASSUI,");
        sb.append("BYOUTAITA,");
        
        if(isValidValue(values[88])){
            sb.append("BYOUTAITA_NM,");
        }
        
        sb.append("NYOUSIKKIN_TAISHO_HOUSIN,");
        sb.append("TENTOU_KOSSETU_TAISHO_HOUSIN,");
        sb.append("HAIKAI_KANOUSEI_TAISHO_HOUSIN,");
        sb.append("JOKUSOU_KANOUSEI_TAISHO_HOUSIN,");
        sb.append("ENGESEIHAIEN_TAISHO_HOUSIN,");
        sb.append("CHOUHEISOKU_TAISHO_HOUSIN,");
        sb.append("EKIKANKANSEN_TAISHO_HOUSIN,");
        sb.append("SINPAIKINOUTEIKA_TAISHO_HOUSIN,");
        sb.append("ITAMI_TAISHO_HOUSIN,");
        sb.append("DASSUI_TAISHO_HOUSIN,");
        sb.append("BYOUTAITA_TAISHO_HOUSIN,");
        
        sb.append("HOUMON_SINRYOU,");
        sb.append("HOUMON_SINRYOU_UL,");
        sb.append("HOUMON_KANGO,");
        sb.append("HOUMON_KANGO_UL,");
        sb.append("HOUMON_REHA,");
        sb.append("HOUMON_REHA_UL,");
        sb.append("TUUSHO_REHA,");
        sb.append("TUUSHO_REHA_UL,");
        sb.append("TANKI_NYUSHO_RYOUYOU,");
        sb.append("TANKI_NYUSHO_RYOUYOU_UL,");
        sb.append("HOUMONSIKA_SINRYOU,");
        sb.append("HOUMONSIKA_SINRYOU_UL,");
        sb.append("HOUMONSIKA_EISEISIDOU,");
        sb.append("HOUMONSIKA_EISEISIDOU_UL,");
        sb.append("HOUMONYAKUZAI_KANRISIDOU,");
        sb.append("HOUMONYAKUZAI_KANRISIDOU_UL,");
        sb.append("HOUMONEIYOU_SHOKUJISIDOU,");
        sb.append("HOUMONEIYOU_SHOKUJISIDOU_UL,");
        sb.append("IGAKUTEKIKANRI_OTHER,");
        sb.append("IGAKUTEKIKANRI_OTHER_UL,");
        
        if(isValidValue(values[91])){
            sb.append("IGAKUTEKIKANRI_OTHER_NM,");
        }
        //�����ɂ���
        sb.append("KETUATU,");
        //
        if(isValidValue(values[93])){
            sb.append("KETUATU_RYUIJIKOU,");
        }
        //�ېH�ɂ���
        sb.append("SESHOKU,");
        if(isValidValue(values[97])){
            sb.append("SESHOKU_RYUIJIKOU,");
        }
        //�����ɂ���
        sb.append("ENGE,");
        if(isValidValue(values[95])){
            sb.append("ENGE_RYUIJIKOU,");
        }
        //�ړ��ɂ���
        sb.append("IDOU,");
        if(isValidValue(values[99])){
            sb.append("IDOU_RYUIJIKOU,");
        }
        //���̑�
        if(isValidValue(values[100])){
            sb.append("KAIGO_OTHER,");
        }
        //�����ǂ̗L��
        if(isValidValue(values[101])){
            sb.append("KANSENSHOU,");
        }
        //�����ǖ�
        if(isValidValue(values[102])){
            sb.append("KANSENSHOU_NM,");
        }
        //���J�쎮�_��
        if(isValidValue(tokki[0])){
            sb.append("HASE_SCORE,");
        }
        //�L����
        if(isValidValue(tokki[1])){
            sb.append("HASE_SCR_DT,");
        }
        //�O�񒷒J��
        if(isValidValue(tokki[2])){
            sb.append("P_HASE_SCORE,");
        }
        //�O��͐���L����
        if(isValidValue(tokki[3])){
            sb.append("P_HASE_SCR_DT,");
        }
        //�{��1
        if(isValidValue(tokki[4])){
            sb.append("INST_SEL_PR1,");
        }
        //�{��2
        if(isValidValue(tokki[5])){
            sb.append("INST_SEL_PR2,");
        }
        //���̑����L����
        if(isValidValue(tokki[6])){
            sb.append("IKN_TOKKI,");
        }

        //��ی��Ҕԍ�
        if(isValidValue(values[5])){
            sb.append("INSURED_NO,");
        }
        //�쐬�˗���
        if(isValidValue(values[8])){
            sb.append("REQ_DT,");
        }
        //�˗��ԍ�
        if(isValidValue(values[10])){
            sb.append("REQ_NO,");
        }
        //���t��
        if(isValidValue(values[9])){
            sb.append("SEND_DT,");
        }
        //���
        if(isValidValue(values[12])){
            sb.append("KIND,");
        }
        //�ی��Ҕԍ�
        if(isValidValue(values[3])){
            sb.append("INSURER_NO,");
        }
        //�ی��Җ���
        if(isValidValue(values[4])){
            sb.append("INSURER_NM,");
        }
        
        //�ŏI�X�V��
        sb.append("LAST_TIME");
        
        sb.append(")");

        sb.append(" VALUES (");
        
        //PATIENT_NO
        sb.append(patientNo + ",");
        //EDA_NO
        sb.append("1,");
        //DOC_KBN
        sb.append("1,");
        //DR_CONSENT
        sb.append(values[28] + ",");
        //KINYU_DT
        if(isValidValue(values[13])){
            sb.append("'"
                    + dateFormatter.format(dateFormatter.parse(values[13]))
                    + "',");
        }
        //IKN_CREATE_CNT
        setValue(sb,values[30]);
        //LASTDAY
        if(isValidValue(values[29])){
            sb.append("'"
                    + dateFormatter.format(dateFormatter.parse(values[29]))
                    + "',");
        }
        
        //TAKA
        StringBuffer rev = new StringBuffer(values[32]);
        setValue(sb,Integer.toString(Integer.parseInt(rev.reverse().toString(),2)));
        //TAKA_OTHER
        setValue(sb,values[33],IKN_ORIGIN_TAKA_OTHER);
        //TANKI_KIOKU
        setValue(sb,values[48]);
        //NINCHI
        setValue(sb,values[49]);
        //DENTATU
        setValue(sb,values[50]);
        //SHOKUJI
        setValue(sb,values[51]);
        //���s��
        char[] charAry = values[53].toCharArray();
        //GNS_GNC
        sb.append(charAry[0] + ",");
        //MOUSOU
        sb.append(charAry[1] + ",");
        //CHUYA
        sb.append(charAry[2] + ",");
        //BOUGEN
        sb.append(charAry[3] + ",");
        //BOUKOU
        sb.append(charAry[4] + ",");
        //TEIKOU
        sb.append(charAry[5] + ",");
        //HAIKAI
        sb.append(charAry[6] + ",");
        //FUSIMATU
        sb.append(charAry[7] + ",");
        //FUKETU
        sb.append(charAry[8] + ",");
        //ISHOKU
        sb.append(charAry[9] + ",");
        //SEITEKI_MONDAI
        sb.append(charAry[10] + ",");
        
        if(isValidValue(values[54])){
            sb.append("1,");
            setValue(sb,values[54],IKN_ORIGIN_MONDAI_OTHER_NM);
        } else {
            sb.append("0,");
        }
        
        //SEISIN
        setValue(sb,values[55]);
        //SEISIN_NM
        setValue(sb,values[56],IKN_ORIGIN_SEISIN_NM);
        //SENMONI
        setValue(sb,values[57]);
        //SENMONI_NM
        setValue(sb,values[58],IKN_ORIGIN_SENMONI_NM);
        //KIKIUDE
        setValue(sb,values[59]);
        //WEIGHT
        setValue(sb,values[60],IKN_ORIGIN_WEIGHT);
        //HEIGHT
        setValue(sb,values[61],IKN_ORIGIN_HEIGHT);
        //SISIKESSON
        setValue(sb,values[62]);
        //SISIKESSON_BUI
        setValue(sb,values[63],IKN_ORIGIN_SISIKESSON_BUI);
        //SISIKESSON_TEIDO
        setValue(sb,values[64]);
        //KINRYOKU_TEIKA
        setValue(sb,values[68]);
        //KINRYOKU_TEIKA_BUI
        setValue(sb,values[69],IKN_ORIGIN_KINRYOKU_TEIKA_BUI);
        //KINRYOKU_TEIKA_TEIDO
        setValue(sb,values[70]);
        //JOKUSOU
        setValue(sb,values[71]);
        //JOKUSOU_BUI
        setValue(sb,values[72],IKN_ORIGIN_JOKUSOU_BUI);
        //JOKUSOU_TEIDO
        setValue(sb,values[73]);
        //HIFUSIKKAN
        setValue(sb,values[74]);
        //HIFUSIKKAN_BUI
        setValue(sb,values[75],IKN_ORIGIN_HIFUSIKKAN_BUI);
        //HIFUSIKKAN_TEIDO
        setValue(sb,values[76]);
        if(isValidValue(values[83])){
            charAry = values[83].toCharArray();
            sb.append(charAry[0] + ",");
            sb.append(charAry[1] + ",");
        }
        if(isValidValue(values[85])){
            charAry = values[85].toCharArray();
            sb.append(charAry[0] + ",");
            sb.append(charAry[1] + ",");
        }
        //�̊�
        if(isValidValue(values[84])){
            charAry = values[84].toCharArray();
            sb.append(charAry[0] + ",");
            sb.append(charAry[1] + ",");
        }
        charAry = values[87].toCharArray();
        //���݁A�����̉\���������a��
        sb.append(charAry[0] + ",");
        sb.append(charAry[1] + ",");
        sb.append(charAry[2] + ",");
        sb.append(charAry[3] + ",");
        sb.append(charAry[4] + ",");
        sb.append(charAry[5] + ",");
        sb.append(charAry[6] + ",");
        sb.append(charAry[7] + ",");
        sb.append(charAry[8] + ",");
        sb.append(charAry[9] + ",");
        sb.append(charAry[10] + ",");
        //BYOUTAITA_NM
        setValue(sb,values[88],IKN_ORIGIN_BYOUTAITA_NM);
        
        //���݁A�����̉\���������a�ԋL��
        String[] temp = values[89].split("�A");
        int count = 0;
        for(int i = 0; i < charAry.length; i++){
            //2006/02/09[Shin Fujihara] : replace begin
            //out of index�`�F�b�N��ǉ�
            //if(charAry[i] == '1'){
            if((charAry[i] == '1') && (count < temp.length)){
            //2006/02/09[Shin Fujihara] : replace end
                sb.append("'" + temp[count] + "',");
                count++;
            } else {
                sb.append("'',");
            }
        }
        
        charAry = values[90].toCharArray();
        //��w�I�Ǘ��̕K�v��
        //0���I�� 1:�I�� 2:����
        //HOUMON_SINRYOU
        for(int i =0; i < charAry.length; i++){
            switch(charAry[i]){
                case '0':
                    sb.append("0,0,");
                    break;
                case '1':
                    sb.append("1,0,");
                    break;
                case '2':
                    sb.append("1,1,");
                    break;
                default:
                    sb.append("0,0,");
            }
        }
        //IGAKUTEKIKANRI_OTHER_NM
        setValue(sb,values[91],IKN_ORIGIN_IGAKUTEKIKANRI_OTHER_NM);
        
        //KETUATU(����)
        sb.append(values[92] + ",");
        //KETUATU_RYUIJIKOU
        setValue(sb,values[93],IKN_ORIGIN_KETUATU_RYUIJIKOU);
        //SESHOKU
        sb.append(values[96] + ",");
        //SESHOKU_RYUIJIKOU
        setValue(sb,values[97],IKN_ORIGIN_SESHOKU_RYUIJIKOU);
        //ENGE
        sb.append(values[94] + ",");
        //ENGE_RYUIJIKOU
        setValue(sb,values[95],IKN_ORIGIN_ENGE_RYUIJIKOU);
        //IDOU
        sb.append(values[98] + ",");
        //IDOU_RYUIJIKOU
        setValue(sb,values[99],IKN_ORIGIN_IDOU_RYUIJIKOU);
        //KAIGO_OTHER
        setValue(sb,values[100],IKN_ORIGIN_KAIGO_OTHER);
        //KANSENSHOU
        setValue(sb,values[101]);
        //KANSENSHOU_NM
        setValue(sb,values[102],IKN_ORIGIN_KANSENSHOU_NM);
        
        //HASE_SCORE
        setValue(sb,tokki[0],IKN_ORIGIN_HASE_SCORE);
        //HASE_SCR_DT
        if(isValidValue(tokki[1])){
            sb.append("'" + tokki[1] + "',");
        }
        //P_HASE_SCORE
        setValue(sb,tokki[2],IKN_ORIGIN_P_HASE_SCORE);
        //P_HASE_SCR_DT
        if(isValidValue(tokki[3])){
            sb.append("'" + tokki[3] + "',");
        }
        //INST_SEL_PR1
        setValue(sb,tokki[4],IKN_ORIGIN_INST_SEL_PR1);
        //INST_SEL_PR2
        setValue(sb,tokki[5],IKN_ORIGIN_INST_SEL_PR1);
        //IKN_TOKKI
        setValue(sb,tokki[6],IKN_ORIGIN_IKN_TOKKI);
        
        //INSURED_NO
        setValue(sb,values[5],IKN_ORIGIN_INSURED_NO);
        //REQ_DT
        if(isValidValue(values[8])){
            sb.append("'" + dateFormatter.format(dateFormatter.parse(values[8])) + "',");
        }
        //REQ_NO
        setValue(sb,values[10],IKN_ORIGIN_REQ_NO);
        //SEND_DT
        if(isValidValue(values[9])){
            sb.append("'" + dateFormatter.format(dateFormatter.parse(values[9])) + "',");
        }
        //KIND
        setValue(sb,values[12]);
        
        //INSURER_NO
        setValue(sb,values[3],IKN_ORIGIN_INSURER_NO);
        //INSURER_NM
        setValue(sb,values[4],IKN_ORIGIN_INSURER_NM);
        
        //LAST_TIME
        sb.append("'" + timeFormatter.format(nowDate) + "'");
        sb.append(")");
        
        return sb.toString();
    }
    
    private int getPatientNo(String[] values,IkenshoFirebirdDBManager dbManager,Date nowDate) throws Exception {
        int result = Integer.MIN_VALUE;
        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT");
        sb.append(" PATIENT.PATIENT_NO");
        sb.append(" ,IKN_ORIGIN.EDA_NO");
        sb.append(" FROM");
        sb.append(" PATIENT");
        sb.append(" LEFT OUTER JOIN IKN_ORIGIN");
        sb.append(" ON");
        sb.append(" PATIENT.PATIENT_NO = IKN_ORIGIN.PATIENT_NO");
        sb.append(" WHERE");
        //���Ҏ���
        sb.append(" (PATIENT_NM = '" + values[15] + "')");
        sb.append(" AND (PATIENT_KN = '" + values[14] + "')");
        if(isValidValue(values[16])){
            if(values[16].length() == 8){
                sb.append(" AND (BIRTHDAY = '" + values[16].substring(0,4) + "/" + values[16].substring(4,6) + "/" + values[16].substring(6) + "')");
            }
        }
        sb.append(" AND (SEX = " + values[18] + ")");
        
        VRList list = dbManager.executeQuery(sb.toString());
        
        //���҃f�[�^�����݂���ꍇ
        if((list != null) && (list.size() > 0)){
            VRMap map = (VRMap)list.get(0);
            //�ӌ����̗��������݂��Ȃ��ꍇ
            if((map.get("EDA_NO") == null) || ("".equals(map.get("EDA_NO")))){
                result = Integer.parseInt(String.valueOf(map.get("PATIENT_NO")));
            //�ӌ����̗��������݂���ꍇ
            } else {
                //������~
                result = Integer.MIN_VALUE;
            }
        //���҃f�[�^�����݂��Ȃ��ꍇ
        } else {
            result = convertPatient(values,nowDate,dbManager);
        }
        
        return result;
    }
    /**
     * 0-���a�̌o��
     * 1-��ܖ�1,2-�e�ʐ��l1,3-�e�ʒP��1,4-�p�@1
     * 5-��ܖ�2,6-�e�ʐ��l2,7-�e�ʒP��2,8-�p�@2
     * 9-��ܖ�3,10-�e�ʐ��l3,11-�e�ʒP��3,12-�p�@3
     * 13-��ܖ�4,14-�e�ʐ��l4,15-�e�ʒP��4,16-�p�@4
     * 17-��ܖ�5,18-�e�ʐ��l5,19-�e�ʒP��5,20-�p�@5
     * 21-��ܖ�6,22-�e�ʐ��l6,23-�e�ʒP��6,24-�p�@6
     * @return
     */
    private String[] getRemedyData(String data) throws Exception{
        String[] result = new String[25];
        Arrays.fill(result,"");
        if(!isValidValue(data)){
            return result;
        }
        //�f�[�^�𕪊�
        String[] temp = data.split(String.valueOf('\u000b'));
        //�����f�[�^�̃`�F�b�N
        if(temp == null){
            return result;
        }
        //���a�̌o��
        int offset = 0;
        result[0] = "";
        for(int i = 0; i < temp.length; i++){
            if(isYakuzai(temp[i])) break;
            result[0] += temp[i].trim() + "\n";
            offset++;
        }
        int count = 0;
        for(int i = 0; i < temp.length - offset; i++){
            String[] splitValue = temp[i + offset].split("/");
            for(int j = 0; j < splitValue.length; j++){
                String[] drag = getDrag(splitValue[j]);
                if(drag != null){
                    result[(count * 4) + 1] = drag[0];
                    result[(count * 4) + 2] = drag[1];
                    result[(count * 4) + 3] = drag[2];
                    result[(count * 4) + 4] = drag[3];
                    count++;
                }
            }
        }
        
        return result;
    }
    //��܃f�[�^�񂩔���
    private boolean isYakuzai(String value){
        //�P�D�g/�h��split�����Ƃ���2���������B
        String[] temp = value.split("/");
        if(temp.length != 2){
            return false;
        }
        //�Q�D�X�v���b�g�����f�[�^���󔒁A�܂��́g �h��3�ɕ��������B
        for(int i = 0; i < temp.length; i++){
            if(!"".equals(temp[i].replaceAll(" |�@",""))){
                String[] temp2 = temp[i].trim().split(" ");
                if(temp2.length != 3) return false;
            }
        }
        return true;
    }
    /**
     * ��ܖ����𕪊�
     * @param data
     * @return 0-��ܖ� 1-�e�ʐ��l 2-�e�ʒP�� 3-�p�@
     * @throws Exception
     */
    private String[] getDrag(String data) throws Exception {
        String[] result = new String[4];
        Arrays.fill(result,"");
        if(data != null){
            data = data.trim();
        }
        if(!isValidValue(data)){
            return null;
        }
        
        //�f�[�^�𕪊�
        String[] temp = new String[3];
        //2006/02/09[Shin Fujihara] : replace begin
        //�z���������
        Arrays.fill(temp,"");
        //2006/02/09[Shin Fujihara] : replace end
        char[] charAry = data.toCharArray();
        int count = 0;
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < charAry.length; i++){
            if(charAry[i] == ' '){
                temp[count] = buf.toString();
                buf = new StringBuffer();
                count++;
            } else {
                buf.append(charAry[i]);
            }
        }
        temp[2] = buf.toString();
        //��ܖ�
        result[0] = temp[0];
        
        //�e��
        charAry = temp[1].toCharArray();
        buf = new StringBuffer();
        for(int i = 0; i < charAry.length; i++){
            //���l�͗e�ʐ��l�Ƃ݂Ȃ�
            //2006/02/09[Shin Fujihara] : replace begin
            //if("0123456789�O�P�Q�R�S�T�U�V�W�X".indexOf(charAry[i]) == -1){
            if(".0123456789�D�O�P�Q�R�S�T�U�V�W�X".indexOf(charAry[i]) == -1){
            //2006/02/09[Shin Fujihara] : replace end
                //���l�ȊO�̒l�������Ă�������ŕ���
                result[2] = temp[1].substring(i);
                break;
            } else {
                buf.append(charAry[i]);
            }
        }
        //�e�ʐ��l
        result[1] = buf.toString();
        
        //�p�@
        result[3] = temp[2];
        
        return result;
    }
    
    /** 
     * 0 �_��1
     * 1 �N��1
     * 2 �_��2
     * 3 �N��3
     * 4 �{��1
     * 5 �{��2
     * 6 ���L����
     * @param value
     * @return
     * @throws Exception
     */
    private String[] getTokki(String value)throws Exception {
        String[] result = new String[7];
        Arrays.fill(result,"");
        
        if(!isValidValue(value)){
            return result;
        }
        int count = 0;
        
        //�����^�u�ŕ���
        String[] temp = value.split(String.valueOf('\u000b'));
        
        if(temp.length < 2){
            return result;
        }
        
        //���J�쎮�_���̊m�F
        if(temp[0].indexOf("���J�쎮") != -1){
            temp[0] = temp[0].replaceAll("���J�쎮 ��","");
            
            //���J�쎮
            String[] hasegawa = temp[0].split("�_�@�i|�j�@�@�O��@|�N|��");
            result[0] = hasegawa[0].replaceAll(" |�@","");
            result[1] = getDay(hasegawa[1],hasegawa[2]);
            result[2] = hasegawa[4].replaceAll(" |�@","");;
            result[3] = getDay(hasegawa[5],hasegawa[6]);
            count++;
        }
        
        //�{�ݑI���̊m�F
        if((temp[1].indexOf("�P�D") != -1) && (temp[1].indexOf("�Q�D") != -1)){
            //�{��
            String[] shisetsu = temp[1].split("�P�D|�Q�D");
            if(shisetsu.length >= 2){
                result[4] = shisetsu[1].replaceAll(" |�@","");
            }
            
            if(shisetsu.length >= 3){
                result[5] = shisetsu[2].replaceAll(" |�@","");
            }
            count = 2;
        }
        if(count == 0){
            count++;
        }
        
        result[6] = "";
        for(int i = count; i < temp.length; i++){
            result[6] += temp[i] + "\n";
        }
        
        return result;
    }
    
    private String getDay(String year,String month) {
        StringBuffer result = new StringBuffer();
        year = year.replaceAll(" |�@","");
        switch(year.length()){
            case 0:
                result.append("0000");
                break;
            case 1:
                result.append("000" + year);
                break;
            case 2:
                result.append(year + "00");
                break;
            case 3:
                result.append(year.substring(0,2) + "0" + year.substring(2));
                break;
            case 4:
                result.append(year);
        }
        result.append("�N");
        month = month.replaceAll(" |�@","");
        switch(month.length()){
            case 0:
                result.append("00");
                break;
            case 1:
                result.append("0" + month);
                break;
            case 2:
                result.append(month);
                break;
        }
        result.append("��00��");
        
        if(result.toString().equals("����00�N00��00��")){
            return "0000�N00��00��";
        }
        
        return result.toString();
    }
    
    
    private String getIknBillSQL(int patientNo, Date nowDate) throws Exception {
        StringBuffer sb = new StringBuffer();
        
        sb.append("INSERT INTO IKN_BILL (");
        sb.append("PATIENT_NO,");
        sb.append("EDA_NO,");
        sb.append("BANK_NM,");
        sb.append("BANK_SITEN_NM,");
        sb.append("KOUZA_NO,");
        sb.append("KOUZA_KIND,");
        sb.append("KOUZA_MEIGI,");
        sb.append("JIGYOUSHA_NO,");
        sb.append("KAISETUSHA_NM,");
        sb.append("DR_NO,");
        sb.append("IKN_CHARGE,");
        sb.append("SHOSIN_TAISHOU,");
        sb.append("SHOSIN,");
        sb.append("SHOSIN_TEKIYOU,");
        sb.append("XRAY_TANJUN_SATUEI,");
        sb.append("XRAY_SHASIN_SINDAN,");
        sb.append("XRAY_FILM,");
        sb.append("XRAY_TEKIYOU,");
        sb.append("BLD_SAISHU,");
        sb.append("BLD_IPPAN_MASHOU_KETUEKI,");
        sb.append("BLD_IPPAN_EKIKAGAKUTEKIKENSA,");
        sb.append("BLD_IPPAN_TEKIYOU,");
        sb.append("BLD_KAGAKU_KETUEKIKAGAKUKENSA,");
        sb.append("BLD_KAGAKU_SEIKAGAKUTEKIKENSA,");
        sb.append("BLD_KAGAKU_TEKIYOU,");
        sb.append("NYO_KENSA,");
        sb.append("NYO_KENSA_TEKIYOU,");
        sb.append("ZAITAKU_SINKI_CHARGE,");
        sb.append("ZAITAKU_KEIZOKU_CHARGE,");
        sb.append("SISETU_SINKI_CHARGE,");
        sb.append("SISETU_KEIZOKU_CHARGE,");
        sb.append("SHOSIN_SINRYOUJO,");
        sb.append("SHOSIN_HOSPITAL,");
        sb.append("SHOSIN_OTHER,");
        sb.append("EXP_KS,");
        sb.append("EXP_KIK_MKI,");
        sb.append("EXP_KIK_KEKK,");
        sb.append("EXP_KKK_KKK,");
        sb.append("EXP_KKK_SKK,");
        sb.append("EXP_NITK,");
        sb.append("EXP_XRAY_TS,");
        sb.append("EXP_XRAY_SS,");
        sb.append("EXP_XRAY_FILM,");
        sb.append("TAX,");
        sb.append("OUTPUT_PATTERN,");
        sb.append("ISS_INSURER_NO,");
        sb.append("ISS_INSURER_NM,");
        sb.append("SKS_INSURER_NO,");
        sb.append("SKS_INSURER_NM,");
        sb.append("FD_OUTPUT_KBN,");
        sb.append("HAKKOU_KBN,");
        sb.append("LAST_TIME");
        sb.append(")");
        
        
        sb.append(" VALUES (");
        sb.append(patientNo + ",");
        sb.append("1,");
        sb.append("'',");
        sb.append("'',");
        sb.append("'',");
        sb.append("2,");
        sb.append("'',");
        sb.append("'',");
        sb.append("'',");
        sb.append("'',");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("'',");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("'',");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("'',");
        sb.append("0,");
        sb.append("0,");
        sb.append("'',");
        sb.append("0,");
        sb.append("'',");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("0,");
        sb.append("'',");
        sb.append("'',");
        sb.append("'',");
        sb.append("'',");
        sb.append("0,");
        sb.append("0,");
        sb.append("'" + timeFormatter.format(nowDate) + "'");
        sb.append(")");
        
        return sb.toString();
    }
    
    private void setValue(StringBuffer sb,String value) throws Exception {
        if (isValidValue(value)) {
            sb.append(value);
            sb.append(",");
        }
    }
    
    private void setValue(StringBuffer sb,String value,int length) throws Exception {
        if (isValidValue(value)) {
            if (value.length() <= length) {
                sb.append("'" + value + "',");
            } else {
                sb.append("'"
                        + value.substring(0, length)
                        + "',");
            }
        }
    }
    
    private void executeUpdateConvert(IkenshoFirebirdDBManager dbManager, String query) throws Exception {
        //�s���A�s���ǂ̕������F�m�ǂɒu������
        query = query.replaceAll("�s����","�F�m��");
        query = query.replaceAll("�s��","�F�m��");
        //�a����ύX
        query = query.replaceAll("�p�[�L���\���a","�p�[�L���\���a�֘A����");
        query = query.replaceAll("�V���C�E�h���[�K�[�ǌ�Q","���n���ޏk��");
        query = query.replaceAll("�V���C�h���[�K�[�ǌ�Q","���n���ޏk��");
        query = query.replaceAll("�����֐߃��E�}�`","�֐߃��E�}�`");
        //�����^�u������
        query = query.replaceAll(String.valueOf('\u000b'),"");
        
        dbManager.executeUpdate(query);
    }
    
    //2006-1-26 add end shin.fujihara
    

    private String[] splitTelNumber(String telNumber) {
        String[] telNumbers = new String[2];
        Arrays.fill(telNumbers,"");
        if (telNumber != null && telNumber.length() > 0) {
            String[] temp = telNumber.split("-");
            switch(temp.length){
                case 1:
                    telNumbers[0] = temp[0];
                    break;
                case 2:
                    if(temp[0].length() > 4){
                        temp[0] = temp[0].substring(0,4);
                    }
                    if(temp[1].length() > 4){
                        temp[1] = temp[1].substring(0,4);
                    }
                    telNumbers[1] = temp[0] + "-" + temp[1];
                    break;
                case 3:
                    if(temp[1].length() > 4){
                        temp[1] = temp[1].substring(0,4);
                    }
                    if(temp[2].length() > 4){
                        temp[2] = temp[2].substring(0,4);
                    }
                    telNumbers[0] = temp[0];
                    telNumbers[1] = temp[1] + "-" + temp[2];
                    break;
            
            }
//            if (telNumber.indexOf("-") != -1) {
//                telNumbers[0] = telNumber.substring(0, telNumber.indexOf("-"));
//                telNumbers[1] = telNumber.replaceAll("^" + telNumbers[0] + "-",
//                        "");
//            } else {
//                telNumbers[0] = telNumber;
//            }
        }
        return telNumbers;
    }

    private boolean isCheck() {
        String pathIkensho = txtIkensho.getText().trim();
        if (pathIkensho.equals("")) {
            JOptionPane.showMessageDialog(this, "�ڍs���f�[�^���ݒ肳��Ă��܂���B");
            return false;
        }
        // �厡��ӌ���
        if (!pathIkensho.equals("")) {
            if (!new File(pathIkensho).exists()) {
                JOptionPane.showMessageDialog(this, lblIkensho.getText()
                        + "�ɐݒ肳�ꂽ�t�@�C���͑��݂��܂���B�ݒ肵�����ĉ������B");
                btnIkensho.requestFocus();
                return false;
            }
        }
        
        return true;
    }

    private ArrayList splitCsvList(File file) {
        ArrayList cvsList = new ArrayList();
        try {
            // ���������Ή�
            // BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "SJIS"));

            while (br.ready()) {
                String value = br.readLine();
                String[] values = value.split(",");
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("\"", "");
                }
                cvsList.add(values);
            }
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
        return cvsList;
    }

//    private String formatSexType(String source) {
//        String value = "0";
//        if (isValidValue(source)) {
//            if ("�j".equals(source)) {
//                value = "1";
//            } else if ("��".equals(source)) {
//                value = "2";
//            }
//        }
//        return value;
//    }

    private boolean isValidValue(String source) {
        if (source != null && source.length() > 0) {
            return true;
        }
        return false;
    }

    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    public void btnStart_actionPerformed(ActionEvent e) {
        try {
            if (isCheck()) {
                int optionValue = JOptionPane.showConfirmDialog(this,
                        "�f�[�^�x�[�X�ڍs�������J�n���Ă�낵���ł����H", MSG_INFO,
                        JOptionPane.YES_NO_OPTION);
                if (optionValue == JOptionPane.YES_OPTION) {
                    // �f�[�^�x�[�X�}�l�[�W���[����
                    IkenshoFirebirdDBManager dbManager = new IkenshoFirebirdDBManager();
                    
                    // ���ݓ��t�擾
                    Date nowDate = new Date();
                    
                    // �厡��ӌ����f�[�^�ڍs����
                    if (!txtIkensho.getText().equals("")) {
                        convertIkensho(new File(txtIkensho.getText()), nowDate,
                                dbManager);
                    }
                    JOptionPane.showMessageDialog(this, "�f�[�^�x�[�X�ڍs�������������܂����B");
                    dbManager = null;
                } else {
                    return;
                }
            }
        } catch (HeadlessException ex) {
            VRLogger.warning(ex.toString());
            ex.printStackTrace();
        } catch (Exception ex) {
            VRLogger.warning(ex.toString());
            ex.printStackTrace();
        }
    }

    public void btnIkensho_actionPerformed(ActionEvent e) {
        FileFilter[] filters = chooser.getChoosableFileFilters();
        for (int i = 0; i < filters.length; i++) {
            chooser.removeChoosableFileFilter(filters[i]);
        }
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new IkenshoMacConvertRegexFileFilter(
                FILTER_CSV, lblIkensho.getText() + "�i*.csv�j"));
        chooser.showDialog(this, FILTER_BUTTON_NAME);
        if (chooser.getSelectedFile() != null) {
            txtIkensho.setText(chooser.getSelectedFile().getPath());
        }
    }

    private class IkenshoMacConvertRegexFileFilter extends FileFilter {
        private String regex;
        String description;

        public IkenshoMacConvertRegexFileFilter(String regex, String description) {
            this.regex = regex;
            this.description = description;
        }

        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            } else {
                return (f.getName().matches(regex));
            }
        }

        public String getDescription() {
            return description;
        }

    }

    private class IkenshoMacConvert_buttonSearch_actionAdapter implements
            java.awt.event.ActionListener {
        IkenshoMacConvertPatch adaptee;

        IkenshoMacConvert_buttonSearch_actionAdapter(IkenshoMacConvertPatch adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnStart_actionPerformed(e);
        }
    }
    
    private class IkenshoMacConvert_btnIkensho_actionAdapter implements
		    java.awt.event.ActionListener {
    	IkenshoMacConvertPatch adaptee;
		
    	IkenshoMacConvert_btnIkensho_actionAdapter(IkenshoMacConvertPatch adaptee) {
		    this.adaptee = adaptee;
    	}
		
		public void actionPerformed(ActionEvent e) {
		    adaptee.btnIkensho_actionPerformed(e);
		}
	}
}