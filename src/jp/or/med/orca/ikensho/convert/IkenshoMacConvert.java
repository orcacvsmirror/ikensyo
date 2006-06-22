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
 * �J����: �|�{�z��
 * �쐬��: 2005/12/01  ���{�R���s���[�^������� �|�{�z�� �V�K�쐬
 * �X�V��: 2006/02/03�@���{�R���s���[�^������� �����L
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
 * Mac�p�f�[�^�ڍs�c�[���ł��B<br>
 * Mac�ň㌩��Ver1.6����o�͂��ꂽ<br>
 * �E���Ҋ�{���<br>
 * �E��Ë@�֏��<br>
 * �E�A�g����<br>
 * �E�ی��ҏ��<br>
 * �E�K��Ō�X�e�[�V�������<br>
 * ��CSV�t�@�C����ǂݍ��݁ADB�ɓo�^���܂��B<br>
 * 2006/02/03�ǉ��d�l<br>
 * �厡��ӌ�����CSV�t�@�C����ǂݍ��݁ADB�ɓo�^����@�\��ǉ��B<br>
 * ��������<br>
 * �P�DMac�ŏo�͂��ꂽ�厡��ӌ���CSV��Windows�ŏo�͂����厡��ӌ���CSV��<br>
 * ���J�쎮�_���Ɋւ���t�H�[�}�b�g���قȂ邽�߁AWindows�ŏo�͂���<br>
 * �厡��ӌ���CSV�̎�荞�݂͍s���܂���B<br>
 * �Q�D���Ҋ�{��񂩂�A�����A���Ȏ����A���N�����A���ʂ����v����<br>
 * �f�[�^���������܂��B<br>
 * �Y�����銳�҂����݂���ꍇ-�Ώۊ��҂Ɋ֘A����㌩���Ƃ��ăf�[�^�ڍs���s���܂��B<br>
 * �Y�����銳�҂����݂��Ȃ��ꍇ-�ӌ����̃f�[�^���犳�Ҋ�{�����쐬���A�o�^���܂��B<r>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * @author Yousuke Takemoto
 * @author Shin Fujihara
 * @version 1.1 2006/02/03
 */
public class IkenshoMacConvert extends JFrame {

    private static final String MSG_INFO = "Information";
    private static final String FILTER_BUTTON_NAME = "�ݒ�";
    private static final String FILTER_CSV = ".*\\.[Cc][Ss][Vv]$";

    private static final String SEIKYUSHO_ISS_SKS = "1";
    private static final String SEIKYUSHO_ISS_AND_SKS = "2";
    private static final String SEIKYUSHO_ISS_ONLY = "3";
    private static final String SEIKYUSHO_SKS_ONLY = "4";

    private static final int DOCTOR_DR_NM = 30;
    private static final int DOCTOR_MI_NM = 60;
    private static final int DOCTOR_MI_POST_CD = 10;
    private static final int DOCTOR_MI_ADDRESS = 100;
    private static final int DOCTOR_MI_TEL1 = 10;
    private static final int DOCTOR_MI_TEL2 = 20;
    private static final int DOCTOR_MI_FAX1 = 10;
    private static final int DOCTOR_MI_FAX2 = 20;
    private static final int DOCTOR_MI_CEL_TEL1 = 10;
    private static final int DOCTOR_MI_CEL_TEL2 = 20;
    private static final int DOCTOR_KINKYU_RENRAKU = 100;
    private static final int DOCTOR_FUZAIJI_TAIOU = 100;
    private static final int DOCTOR_BIKOU = 2000;
    private static final int DOCTOR_KAISETUSHA_NM = 30;
    private static final int DOCTOR_DR_NO = 30;
    private static final int DOCTOR_BANK_NM = 50;
    private static final int DOCTOR_BANK_SITEN_NM = 50;
    private static final int DOCTOR_BANK_KOUZA_NO = 20;
    private static final int DOCTOR_FURIKOMI_MEIGI = 30;

    private static final int RENKEII_DR_NM = 30;
    private static final int RENKEII_SINRYOUKA = 20;
    private static final int RENKEII_MI_NM = 60;
    private static final int RENKEII_MI_POST_CD = 10;
    private static final int RENKEII_MI_ADDRESS = 100;
    private static final int RENKEII_MI_TEL1 = 10;
    private static final int RENKEII_MI_TEL2 = 20;
    private static final int RENKEII_MI_FAX1 = 10;
    private static final int RENKEII_MI_FAX2 = 20;
    private static final int RENKEII_MI_CEL_TEL1 = 10;
    private static final int RENKEII_MI_CEL_TEL2 = 20;
    private static final int RENKEII_KINKYU_RENRAKU = 100;
    private static final int RENKEII_FUZAIJI_TAIOU = 100;
    private static final int RENKEII_BIKOU = 2000;

    private static final int STATION_DR_NM = 30;
    private static final int STATION_MI_NM = 60;
    private static final int STATION_MI_POST_CD = 10;
    private static final int STATION_MI_ADDRESS = 100;
    private static final int STATION_MI_TEL1 = 10;
    private static final int STATION_MI_TEL2 = 20;
    private static final int STATION_MI_FAX1 = 10;
    private static final int STATION_MI_FAX2 = 20;
    private static final int STATION_MI_CEL_TEL1 = 10;
    private static final int STATION_MI_CEL_TEL2 = 20;
    private static final int STATION_KINKYU_RENRAKU = 100;
    private static final int STATION_FUZAIJI_TAIOU = 100;
    private static final int STATION_BIKOU = 200;

    private static final int PATIENT_CHART_NO = 20;
    private static final int PATIENT_PATIENT_NM = 30;
    private static final int PATIENT_PATIENT_KN = 60;
    private static final int PATIENT_AGE = 5;
    private static final int PATIENT_POST_CD = 10;
    private static final int PATIENT_ADDRESS = 100;
    private static final int PATIENT_TEL1 = 10;
    private static final int PATIENT_TEL2 = 20;

    private static final int INSURER_INSURER_NO = 20;
    private static final int INSURER_INSURER_NM = 100;
    private static final int INSURER_ISS_INSURER_NO = 20;
    private static final int INSURER_ISS_INSURER_NM = 100;
    private static final int INSURER_SKS_INSURER_NO = 20;
    private static final int INSURER_SKS_INSURER_NM = 100;
    
    //add start shin fujihara 2006.2.3
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
    //add end shin fujihara 2006.2.3

    private String title;
    private BorderLayout bl = new BorderLayout();
    private FlowLayout fl = new FlowLayout();
    private static ACDateFormat dateFormatter = new ACDateFormat("yyyy/MM/dd");
    //2006/02/09[Shin Fujihara] : replace begin
//    private static ACDateFormat timeFormatter = new ACDateFormat(
//            "yyyy/MM/dd kk:mm:ss");
    private static ACDateFormat timeFormatter = new ACDateFormat(
    "yyyy/MM/dd HH:mm:ss");
    //2006/02/09[Shin Fujihara] : replace end

    private JButton btnStart = new JButton();
    private JFileChooser chooser = new JFileChooser();
    private JPanel plCsv = new JPanel();
    private JTextField txtDb = new JTextField();
    private JTextField txtPatient = new JTextField();
    private JTextField txtDoctor = new JTextField();
    private JTextField txtRenkeii = new JTextField();
    private JTextField txtInsurer = new JTextField();
    private JTextField txtStation = new JTextField();
    private JLabel lblDb = new JLabel();
    private JLabel lblPatient = new JLabel();
    private JLabel lblDoctor = new JLabel();
    private JLabel lblRenkeii = new JLabel();
    private JLabel lblInsurer = new JLabel();
    private JLabel lblStation = new JLabel();
    private JButton btnDb = new JButton();
    private JButton btnPatient = new JButton();
    private JButton btnDoctor = new JButton();
    private JButton btnRenkeii = new JButton();
    private JButton btnInsurer = new JButton();
    private JButton btnStation = new JButton();
    
    //add start shin fujihara 2006.2.3
    private JTextField txtIkensho = new JTextField();
    private JLabel lblIkensho = new JLabel();
    private JButton btnIkensho = new JButton();
    //add end shin fujihara 2006.2.3

    //2006/02/11[Shin Fujihara] : add begin
    private String charVT = String.valueOf('\u000b');
    private boolean doWindows = false;
    private String csvVersion = "";
    //2006/02/11[Shin Fujihara] : add end
    
    public IkenshoMacConvert() {

    }

    public IkenshoMacConvert(String title) {
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
        //edit start shin fujihara 2006.2.3
        //this.setSize(new Dimension(780, 300));
        this.setSize(new Dimension(780, 380));
        //edit end shin fujihara 2006.2.3
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - this.getWidth()) / 2,
                (screenSize.height - this.getHeight()) / 2, this.getWidth(),
                this.getHeight());
        this.setVisible(true);
        this.setTitle(title);
        this.setResizable(false);
        plCsv.setLayout(fl);
        plCsv.add(lblPatient, null);
        plCsv.add(txtPatient, null);
        plCsv.add(btnPatient, null);
        plCsv.add(lblDoctor, null);
        plCsv.add(txtDoctor, null);
        plCsv.add(btnDoctor, null);
        plCsv.add(lblRenkeii, null);
        plCsv.add(txtRenkeii, null);
        plCsv.add(btnRenkeii, null);
        plCsv.add(lblInsurer, null);
        plCsv.add(txtInsurer, null);
        plCsv.add(btnInsurer, null);
        plCsv.add(lblStation, null);
        plCsv.add(txtStation, null);
        plCsv.add(btnStation, null);
        //add start shin fujihara 2006.2.3
        plCsv.add(lblIkensho, null);
        plCsv.add(txtIkensho, null);
        plCsv.add(btnIkensho, null);
        //add end shin fujihara 2006.2.3
        lblDb.setText("�ڍs��f�[�^�x�[�X");
        lblDb.setMaximumSize(new Dimension(180, 15));
        lblDb.setPreferredSize(new Dimension(180, 15));
        lblDb.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPatient.setText("���Ҋ�{���");
        lblPatient.setMaximumSize(new Dimension(180, 15));
        lblPatient.setPreferredSize(new Dimension(180, 15));
        lblPatient.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDoctor.setText("��Ë@�֏��");
        lblDoctor.setMaximumSize(new Dimension(180, 15));
        lblDoctor.setPreferredSize(new Dimension(180, 15));
        lblDoctor.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRenkeii.setText("�A�g����");
        lblRenkeii.setMaximumSize(new Dimension(180, 15));
        lblRenkeii.setPreferredSize(new Dimension(180, 15));
        lblRenkeii.setHorizontalAlignment(SwingConstants.RIGHT);
        lblInsurer.setText("�ی��ҏ��");
        lblInsurer.setMaximumSize(new Dimension(180, 15));
        lblInsurer.setPreferredSize(new Dimension(180, 15));
        lblInsurer.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStation.setText("�K��Ō�X�e�[�V�������");
        lblStation.setMaximumSize(new Dimension(180, 15));
        lblStation.setPreferredSize(new Dimension(180, 15));
        lblStation.setHorizontalAlignment(SwingConstants.RIGHT);
        //add start shin fujihara 2006.2.3
        lblIkensho.setText("�厡��ӌ���");
        lblIkensho.setMaximumSize(new Dimension(180, 15));
        lblIkensho.setPreferredSize(new Dimension(180, 15));
        lblIkensho.setHorizontalAlignment(SwingConstants.RIGHT);
        //add end shin fujihara 2006.2.3
        txtDb.setColumns(40);
        txtPatient.setColumns(40);
        txtDoctor.setColumns(40);
        txtRenkeii.setColumns(40);
        txtInsurer.setColumns(40);
        txtStation.setColumns(40);
        //add start shin fujihara 2006.2.3
        txtIkensho.setColumns(40);
        //add end shin fujihara 2006.2.3
        btnDb.setText("...");
        btnPatient.setText("...");
        btnDoctor.setText("...");
        btnRenkeii.setText("...");
        btnInsurer.setText("...");
        btnStation.setText("...");
        //add start shin fujihara 2006.2.3
        btnIkensho.setText("...");
        //add end shin fujihara 2006.2.3
        
        btnStart
                .addActionListener(new IkenshoMacConvert_buttonSearch_actionAdapter(
                        this));
        btnPatient
                .addActionListener(new IkenshoMacConvert_btnPatient_actionAdapter(
                        this));
        btnDoctor
                .addActionListener(new IkenshoMacConvert_btnDoctor_actionAdapter(
                        this));
        btnRenkeii
                .addActionListener(new IkenshoMacConvert_btnRenkeii_actionAdapter(
                        this));
        btnInsurer
                .addActionListener(new IkenshoMacConvert_btnInsurer_actionAdapter(
                        this));
        btnStation
                .addActionListener(new IkenshoMacConvert_btnStation_actionAdapter(
                        this));

        //add start shin fujihara 2006.2.3
        btnIkensho.addActionListener(new IkenshoMacConvert_btnIkensho_actionAdapter(this));
        //add start shin fujihara 2006.2.3
        //2006/02/11[Shin Fujihara] : add begin
        String osName = System.getProperty("os.name");
        //Mac�ȊO�ł���Ε����R�[�h�ϊ����s���B
        if ((osName != null) && (osName.indexOf("Mac") < 0)) {
            doWindows = true;
        }
        //2006/02/11[Shin Fujihara] : add end
        
        btnStart.setText("�f�[�^�ڍs�J�n");
        btnStart.setPreferredSize(new Dimension(700, 100));
    }

    public static void main(String[] args) {

        try {
            ACFrame.setVRLookAndFeel();
            ACFrame.getInstance().setFrameEventProcesser(
                    new IkenshoFrameEventProcesser());
            IkenshoMacConvert ikenshoMacConvert = new IkenshoMacConvert(
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
    //2006/02/11[Shin Fujihara] : replace begin
//    private void convertPatient(File file, Date nowDate,
//            IkenshoFirebirdDBManager dbManager) {
    private int convertPatient(File file, Date nowDate,
            IkenshoFirebirdDBManager dbManager) {
    //2006/02/11[Shin Fujihara] : replace end
        int errorCount = 0;
        try {
            // �f�[�^��������
            ArrayList list = splitCsvList(file);
            // �f�[�^�x�[�X�ɑ}��
            for (int i = 0; i < list.size(); i++) {
                //2006/02/11[Shin Fujihara] : add begin
                //try catch��ǉ�-�P�����R�~�b�g����悤�ɕύX
                try{
                //2006/02/11[Shin Fujihara] : add end
    
                    String[] values = (String[]) list.get(i);
                    // �t�@�C�������m�F
                    if (values.length != 9){
                        errorCount++;
                        continue;
                    }
    
                    //2006/02/11[Shin Fujihara] : add begin
                    //�����^�u��u��
                    for(int j = 0 ; j < values.length; j++){
                        if(values[j] != null){
                            values[j] = values[j].replaceAll(charVT,"");
                        }
                    }
                    //2006/02/11[Shin Fujihara] : add end
                    //2006/02/11[Shin Fujihara] : replace begin
                    //String[] tel = splitTelNumber(values[8]);
                    String[] tel = splitTelNumberDetail(values[8]);
                    //2006/02/11[Shin Fujihara] : replace end
    
                    StringBuffer sqlBuff = new StringBuffer();
                    sqlBuff.append("INSERT INTO PATIENT (");
                    if (isValidValue(values[0])) {
                        sqlBuff.append("CHART_NO,");
                    }
                    if (isValidValue(values[1])) {
                        sqlBuff.append("PATIENT_NM,");
                    }
                    if (isValidValue(values[2])) {
                        sqlBuff.append("PATIENT_KN,");
                    }
                    if (isValidValue(values[3])) {
                        sqlBuff.append("SEX,");
                    }
                    if (isValidValue(values[4])) {
                        sqlBuff.append("BIRTHDAY,");
                    }
                    if (isValidValue(values[5])) {
                        sqlBuff.append("AGE,");
                    }
                    if (isValidValue(values[6])) {
                        sqlBuff.append("POST_CD,");
                    }
                    if (isValidValue(values[7])) {
                        sqlBuff.append("ADDRESS,");
                    }
                    if (isValidValue(tel[0])) {
                        sqlBuff.append("TEL1,");
                    }
                    if (isValidValue(tel[1])) {
                        sqlBuff.append("TEL2,");
                    }
                    sqlBuff.append("LAST_TIME");
                    sqlBuff.append(")");
    
                    sqlBuff.append(" VALUES (");
                    if (isValidValue(values[0])) {
                        if (values[0].length() <= PATIENT_CHART_NO) {
                            sqlBuff.append("'" + values[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[0].substring(0, PATIENT_CHART_NO)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[1])) {
                        if (values[1].length() <= PATIENT_PATIENT_NM) {
                            sqlBuff.append("'" + values[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[1].substring(0, PATIENT_PATIENT_NM)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[2])) {
                        if (values[2].length() <= PATIENT_PATIENT_KN) {
                            sqlBuff.append("'" + values[2] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[2].substring(0, PATIENT_PATIENT_KN)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[3])) {
                        sqlBuff.append(formatSexType(values[3]) + ",");
                    }
    
                    if (isValidValue(values[4])) {
                        // ���N�Ή�
                        sqlBuff.append("'"
                                + dateFormatter.format(dateFormatter
                                        .parse(values[4].replaceAll("��", "1")))
                                + "',");
    
                    }
                    if (isValidValue(values[5])) {
                        if (values[5].length() <= PATIENT_AGE) {
                            sqlBuff.append("'" + values[5] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[5].substring(0, PATIENT_AGE) + "',");
                        }
                    }
                    if (isValidValue(values[6])) {
                        if (values[6].length() <= PATIENT_POST_CD) {
                            sqlBuff.append("'" + values[6] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[6].substring(0, PATIENT_POST_CD)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[7])) {
                        if (values[7].length() <= PATIENT_ADDRESS) {
                            sqlBuff.append("'" + values[7] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[7].substring(0, PATIENT_ADDRESS)
                                    + "',");
                        }
                    }
                    if (isValidValue(tel[0])) {
                        if (tel[0].length() <= PATIENT_TEL1) {
                            sqlBuff.append("'" + tel[0] + "',");
                        } else {
                            sqlBuff.append("'" + tel[0].substring(0, PATIENT_TEL1)
                                    + "',");
                        }
                    }
                    if (isValidValue(tel[1])) {
                        if (tel[1].length() <= PATIENT_TEL2) {
                            sqlBuff.append("'" + tel[1] + "',");
                        } else {
                            sqlBuff.append("'" + tel[1].substring(0, PATIENT_TEL2)
                                    + "',");
                        }
                    }
                    sqlBuff.append("'" + timeFormatter.format(nowDate) + "'");
                    sqlBuff.append(")");
    
                    // SQL�����s
                    //2006/02/09[Shin Fujihara] : replace begin
                    //dbManager.executeUpdate(sqlBuff.toString());
                    executeUpdateConvert(dbManager,sqlBuff.toString());
                    //2006/02/09[Shin Fujihara] : replace end
                    //2006/02/11[Shin Fujihara] : add begin
                } catch(Exception e) {
                    VRLogger.warning(e);
                    errorCount++;
                }
                //2006/02/11[Shin Fujihara] : add end
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //2006/02/11[Shin Fujihara] : add begin
            VRLogger.warning(ex);
            //2006/02/11[Shin Fujihara] : add end
        }
        //2006/02/11[Shin Fujihara] : add begin
        return errorCount;
        //2006/02/11[Shin Fujihara] : add end
    }

    /**
     * �A�g����f�[�^�ڍs����
     * 
     * @param file File
     * @param nowDate Date
     * @param dbManager NCFirebirdDBManager
     */
    //2006/02/11[Shin Fujihara] : replace begin
//    private void convertRenkeii(File file, Date nowDate,
//            IkenshoFirebirdDBManager dbManager) {
    private int convertRenkeii(File file, Date nowDate,
            IkenshoFirebirdDBManager dbManager) {
        int errorCount = 0;
    //2006/02/11[Shin Fujihara] : replace end
        try {
            // �f�[�^��������
            ArrayList list = splitCsvList(file);
            // �f�[�^�x�[�X�ɑ}��
            for (int i = 0; i < list.size(); i++) {
                
                //2006/02/11[Shin Fujihara] : add begin
                try{
                //2006/02/11[Shin Fujihara] : add begin

                    String[] values = (String[]) list.get(i);
    
                    // �t�@�C�������m�F
                    //2006/02/11[Shin Fujihara] : replace begin
                    if (values.length != 11){
                        errorCount++;
                        continue;
                    }
                    //2006/02/11[Shin Fujihara] : replace begin
    
                    //2006/02/11[Shin Fujihara] : add begin
                    //�����^�u��u��
                    for(int j = 0 ; j < values.length; j++){
                        if(values[j] != null){
                            values[j] = values[j].replaceAll(charVT,"");
                        }
                    }
                    //2006/02/11[Shin Fujihara] : add end
                    //2006/02/11[Shin Fujihara] : replace begin
    //                String[] tel = splitTelNumber(values[5]);
    //                String[] fax = splitTelNumber(values[6]);
    //                String[] cel_tel = splitTelNumber(values[7]);
                    String[] tel = splitTelNumberDetail(values[5]);
                    String[] fax = splitTelNumberDetail(values[6]);
                    String[] cel_tel = splitTelNumberDetail(values[7]);
                    //2006/02/11[Shin Fujihara] : replace end
    
                    StringBuffer sqlBuff = new StringBuffer();
                    sqlBuff.append("INSERT INTO RENKEII (");
                    if (isValidValue(values[0])) {
                        sqlBuff.append("DR_NM,");
                    }
                    if (isValidValue(values[1])) {
                        sqlBuff.append("SINRYOUKA,");
                    }
                    if (isValidValue(values[2])) {
                        sqlBuff.append("MI_NM,");
                    }
                    if (isValidValue(values[3])) {
                        sqlBuff.append("MI_POST_CD,");
                    }
                    if (isValidValue(values[4])) {
                        sqlBuff.append("MI_ADDRESS,");
                    }
                    if (isValidValue(tel[0])) {
                        sqlBuff.append("MI_TEL1,");
                    }
                    if (isValidValue(tel[1])) {
                        sqlBuff.append("MI_TEL2,");
                    }
                    if (isValidValue(fax[0])) {
                        sqlBuff.append("MI_FAX1,");
                    }
                    if (isValidValue(fax[1])) {
                        sqlBuff.append("MI_FAX2,");
                    }
                    if (isValidValue(cel_tel[0])) {
                        sqlBuff.append("MI_CEL_TEL1,");
                    }
                    if (isValidValue(cel_tel[1])) {
                        sqlBuff.append("MI_CEL_TEL2,");
                    }
                    if (isValidValue(values[8])) {
                        sqlBuff.append("KINKYU_RENRAKU,");
                    }
                    if (isValidValue(values[9])) {
                        sqlBuff.append("FUZAIJI_TAIOU,");
                    }
                    if (isValidValue(values[10])) {
                        sqlBuff.append("BIKOU,");
                    }
                    sqlBuff.append("LAST_TIME");
                    sqlBuff.append(")");
    
                    sqlBuff.append(" VALUES (");
                    if (isValidValue(values[0])) {
                        if (values[0].length() <= RENKEII_DR_NM) {
                            sqlBuff.append("'" + values[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[0].substring(0, RENKEII_DR_NM) + "',");
                        }
                    }
                    if (isValidValue(values[1])) {
                        if (values[1].length() <= RENKEII_SINRYOUKA) {
                            sqlBuff.append("'" + values[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[1].substring(0, RENKEII_SINRYOUKA)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[2])) {
                        if (values[2].length() <= RENKEII_MI_NM) {
                            sqlBuff.append("'" + values[2] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[2].substring(0, RENKEII_MI_NM) + "',");
                        }
                    }
                    if (isValidValue(values[3])) {
                        if (values[3].length() <= RENKEII_MI_POST_CD) {
                            sqlBuff.append("'" + values[3] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[3].substring(0, RENKEII_MI_POST_CD)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[4])) {
                        if (values[4].length() <= RENKEII_MI_ADDRESS) {
                            sqlBuff.append("'" + values[4] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[4].substring(0, RENKEII_MI_ADDRESS)
                                    + "',");
                        }
                    }
                    if (isValidValue(tel[0])) {
                        if (tel[0].length() <= RENKEII_MI_TEL1) {
                            sqlBuff.append("'" + tel[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + tel[0].substring(0, RENKEII_MI_TEL1) + "',");
                        }
                    }
                    if (isValidValue(tel[1])) {
                        if (tel[1].length() <= RENKEII_MI_TEL2) {
                            sqlBuff.append("'" + tel[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + tel[1].substring(0, RENKEII_MI_TEL2) + "',");
                        }
                    }
                    if (isValidValue(fax[0])) {
                        if (fax[0].length() <= RENKEII_MI_FAX1) {
                            sqlBuff.append("'" + fax[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + fax[0].substring(0, RENKEII_MI_FAX1) + "',");
                        }
                    }
                    if (isValidValue(fax[1])) {
                        if (fax[1].length() <= RENKEII_MI_FAX2) {
                            sqlBuff.append("'" + fax[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + fax[1].substring(0, RENKEII_MI_FAX2) + "',");
                        }
                    }
                    if (isValidValue(cel_tel[0])) {
                        if (cel_tel[0].length() <= RENKEII_MI_CEL_TEL1) {
                            sqlBuff.append("'" + cel_tel[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + cel_tel[0].substring(0, RENKEII_MI_CEL_TEL1)
                                    + "',");
                        }
                    }
                    if (isValidValue(cel_tel[1])) {
                        if (cel_tel[1].length() <= RENKEII_MI_CEL_TEL2) {
                            sqlBuff.append("'" + cel_tel[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + cel_tel[1].substring(0, RENKEII_MI_CEL_TEL2)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[8])) {
                        if (values[8].length() <= RENKEII_KINKYU_RENRAKU) {
                            sqlBuff.append("'" + values[8] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[8]
                                            .substring(0, RENKEII_KINKYU_RENRAKU)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[9])) {
                        if (values[9].length() <= RENKEII_FUZAIJI_TAIOU) {
                            sqlBuff.append("'" + values[9] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[9].substring(0, RENKEII_FUZAIJI_TAIOU)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[10])) {
                        if (values[10].length() <= RENKEII_BIKOU) {
                            sqlBuff.append("'" + values[10] + "',");
                        } else {
                            sqlBuff
                                    .append("'"
                                            + values[10]
                                                    .substring(0, RENKEII_BIKOU)
                                            + "',");
                        }
                    }
                    sqlBuff.append("'" + timeFormatter.format(nowDate) + "'");
                    sqlBuff.append(")");
    
                    // SQL�����s
                    //2006/02/09[Shin Fujihara] : replace begin
                    //dbManager.executeUpdate(sqlBuff.toString());
                    executeUpdateConvert(dbManager,sqlBuff.toString());
                //2006/02/09[Shin Fujihara] : replace end
                } catch(Exception e) {
                    VRLogger.warning(e);
                    errorCount++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return errorCount;
    }

    /**
     * �ی��ҏ��f�[�^�ڍs����
     * 
     * @param file File
     * @param nowDate Date
     * @param dbManager NCFirebirdDBManager
     */
    //2006/02/11[Shin Fujihara] : replace begin
//    private void convertInsurer(File file, Date nowDate,
//            IkenshoFirebirdDBManager dbManager) {
    private int convertInsurer(File file, Date nowDate,
            IkenshoFirebirdDBManager dbManager) {
        int errorCount = 0;
    //2006/02/11[Shin Fujihara] : replace end
        try {
            // �f�[�^��������
            ArrayList list = splitCsvList(file);
            // �f�[�^�x�[�X�ɑ}��
            for (int i = 0; i < list.size(); i++) {
                
                //2006/02/11[Shin Fujihara] : add begin
                try{
                //2006/02/11[Shin Fujihara] : add end

                    String[] values = (String[]) list.get(i);
                    // �t�@�C�������m�F
                    if (values.length != 35){
                        errorCount++;
                        continue;
                    }
    
                    //2006/02/11[Shin Fujihara] : replace begin
                    //�����^�u��u��
                    for(int j = 0 ; j < values.length; j++){
                        if(values[j] != null){
                            values[j] = values[j].replaceAll(charVT,"");
                        }
                    }
                    
                    //�ی��Ҕԍ��d���`�F�b�N
                    //�o�^�������҂�ID���擾
                    String insID = "";
                    if (isValidValue(values[1])) {
                        if (values[1].length() <= INSURER_INSURER_NO) {
                            insID = values[1];
                        } else {
                            insID = values[1].substring(0, INSURER_INSURER_NO);
                        }
                    }
                    int count = 0;
                    while(true){
                        VRList inslist = (VRList)dbManager.executeQuery("SELECT INSURER_NO FROM INSURER WHERE INSURER_NO = '" + insID + "'");
                        
                        if((inslist != null) && (inslist.size() > 0)){
                            insID =insID.substring(0,insID.length() - 2) + count;
                            count++;
                        } else {
                            break;
                        }
                    }
                    //2006/02/11[Shin Fujihara] : replace end
                    
                    String seikyushoType = formatSeikyushoType(values[2]);
    
                    StringBuffer sqlBuff = new StringBuffer();
                    sqlBuff.append("INSERT INTO INSURER (");
                    if (isValidValue(values[0])) {
                        sqlBuff.append("INSURER_NM,");
                    }
                    //if (isValidValue(values[1])) {
                    if (isValidValue(insID)) {
                        sqlBuff.append("INSURER_NO,");
                    }
                    if (isValidValue(values[2])) {
                        sqlBuff.append("SEIKYUSHO_OUTPUT_PATTERN,");
                    }
    
                    // �ӌ����쐬��
                    if (SEIKYUSHO_ISS_SKS.equals(seikyushoType)
                            || SEIKYUSHO_ISS_AND_SKS.equals(seikyushoType)
                            || SEIKYUSHO_ISS_ONLY.equals(seikyushoType)) {
                        if (isValidValue(values[3])) {
                            sqlBuff.append("ISS_INSURER_NM,");
                        }
                        if (isValidValue(values[4])) {
                            sqlBuff.append("ISS_INSURER_NO,");
                        }
                        if (isValidValue(values[5])) {
                            sqlBuff.append("SOUKATUHYOU_PRT,");
                        }
                        if (isValidValue(values[6])) {
                            sqlBuff.append("SOUKATU_FURIKOMI_PRT,");
                        }
                        if (isValidValue(values[7])) {
                            sqlBuff.append("MEISAI_KIND,");
                        }
                        if (isValidValue(values[8])) {
                            sqlBuff.append("FURIKOMISAKI_PRT,");
                        }
                    }
    
                    // ������
                    if (SEIKYUSHO_ISS_AND_SKS.equals(seikyushoType)
                            || SEIKYUSHO_SKS_ONLY.equals(seikyushoType)) {
                        if (isValidValue(values[9])) {
                            sqlBuff.append("SKS_INSURER_NM,");
                        }
                        if (isValidValue(values[10])) {
                            sqlBuff.append("SKS_INSURER_NO,");
                        }
                        if (isValidValue(values[11])) {
                            sqlBuff.append("SOUKATUHYOU_PRT2,");
                        }
                        if (isValidValue(values[12])) {
                            sqlBuff.append("SOUKATU_FURIKOMI_PRT2,");
                        }
                        if (isValidValue(values[13])) {
                            sqlBuff.append("MEISAI_KIND2,");
                        }
                        if (isValidValue(values[14])) {
                            sqlBuff.append("FURIKOMISAKI_PRT2,");
                        }
                    }
                    // ��t�����o��
                    if (isValidValue(values[17])) {
                        sqlBuff.append("DR_NM_OUTPUT_UMU,");
                    }
                    // �w�b�_�[�o�͂P
                    if (isValidValue(values[18])) {
                        sqlBuff.append("HEADER_OUTPUT_UMU1,");
                    }
                    // �w�b�_�[�o�͂Q
                    if (isValidValue(values[19])) {
                        sqlBuff.append("HEADER_OUTPUT_UMU2,");
                    }
                    // FD�쐬
                    if (isValidValue(values[16])) {
                        sqlBuff.append("FD_OUTPUT_UMU,");
                    }
                    // ���������s�L��
                    if (isValidValue(values[15])) {
                        sqlBuff.append("SEIKYUSHO_HAKKOU_PATTERN,");
                    }
                    if (isValidValue(values[20])) {
                        sqlBuff.append("ZAITAKU_SINKI_CHARGE,");
                    }
                    if (isValidValue(values[21])) {
                        sqlBuff.append("ZAITAKU_KEIZOKU_CHARGE,");
                    }
                    if (isValidValue(values[22])) {
                        sqlBuff.append("SISETU_SINKI_CHARGE,");
                    }
                    if (isValidValue(values[23])) {
                        sqlBuff.append("SISETU_KEIZOKU_CHARGE,");
                    }
                    if (isValidValue(values[24])) {
                        sqlBuff.append("SHOSIN_SINRYOUJO,");
                    }
                    if (isValidValue(values[25])) {
                        sqlBuff.append("SHOSIN_HOSPITAL,");
                    }
                    if (isValidValue(values[26])) {
                        sqlBuff.append("EXP_KS,");
                    }
                    if (isValidValue(values[27])) {
                        sqlBuff.append("EXP_KIK_MKI,");
                    }
                    if (isValidValue(values[28])) {
                        sqlBuff.append("EXP_KIK_KEKK,");
                    }
                    if (isValidValue(values[29])) {
                        sqlBuff.append("EXP_KKK_KKK,");
                    }
                    if (isValidValue(values[30])) {
                        sqlBuff.append("EXP_KKK_SKK,");
                    }
                    if (isValidValue(values[31])) {
                        sqlBuff.append("EXP_NITK,");
                    }
                    if (isValidValue(values[32])) {
                        sqlBuff.append("EXP_XRAY_TS,");
                    }
                    if (isValidValue(values[33])) {
                        sqlBuff.append("EXP_XRAY_SS,");
                    }
                    if (isValidValue(values[34])) {
                        sqlBuff.append("EXP_XRAY_FILM,");
                    }
                    sqlBuff.append("LAST_TIME");
                    sqlBuff.append(")");
    
                    sqlBuff.append(" VALUES (");
                    if (isValidValue(values[0])) {
                        if (values[0].length() <= INSURER_INSURER_NM) {
                            sqlBuff.append("'" + values[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[0].substring(0, INSURER_INSURER_NM)
                                    + "',");
                        }
                    }
    //                if (isValidValue(values[1])) {
    //                    if (values[1].length() <= INSURER_INSURER_NO) {
    //                        sqlBuff.append("'" + values[1] + "',");
    //                    } else {
    //                        sqlBuff.append("'"
    //                                + values[1].substring(0, INSURER_INSURER_NO)
    //                                + "',");
    //                    }
    //                }
                    if (isValidValue(insID)) {
                        if (insID.length() <= INSURER_INSURER_NO) {
                            sqlBuff.append("'" + insID + "',");
                        } else {
                            sqlBuff.append("'"
                                    + insID.substring(0, INSURER_INSURER_NO)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[2])) {
                        sqlBuff.append(seikyushoType + ",");
                    }
    
                    // �ӌ����쐬��
                    if (SEIKYUSHO_ISS_SKS.equals(seikyushoType)
                            || SEIKYUSHO_ISS_AND_SKS.equals(seikyushoType)
                            || SEIKYUSHO_ISS_ONLY.equals(seikyushoType)) {
                        if (isValidValue(values[3])) {
                            if (values[3].length() <= INSURER_ISS_INSURER_NM) {
                                sqlBuff.append("'" + values[3] + "',");
                            } else {
                                sqlBuff.append("'"
                                        + values[3].substring(0,
                                                INSURER_ISS_INSURER_NM) + "',");
                            }
                        }
                        if (isValidValue(values[4])) {
                            if (values[4].length() <= INSURER_ISS_INSURER_NO) {
                                sqlBuff.append("'" + values[4] + "',");
                            } else {
                                sqlBuff.append("'"
                                        + values[4].substring(0,
                                                INSURER_ISS_INSURER_NO) + "',");
                            }
                        }
                        if (isValidValue(values[5])) {
                            sqlBuff.append(formatSoukatuhyouType(values[5]) + ",");
                        }
                        if (isValidValue(values[6])) {
                            sqlBuff.append(values[6] + ",");
                        }
                        if (isValidValue(values[7])) {
                            sqlBuff.append(values[7] + ",");
                        }
                        if (isValidValue(values[8])) {
                            sqlBuff.append(values[8] + ",");
                        }
                    }
    
                    // ������
                    if (SEIKYUSHO_ISS_AND_SKS.equals(seikyushoType)
                            || SEIKYUSHO_SKS_ONLY.equals(seikyushoType)) {
                        if (isValidValue(values[9])) {
                            if (values[9].length() <= INSURER_SKS_INSURER_NM) {
                                sqlBuff.append("'" + values[9] + "',");
                            } else {
                                sqlBuff.append("'"
                                        + values[9].substring(0,
                                                INSURER_SKS_INSURER_NM) + "',");
                            }
                        }
                        if (isValidValue(values[10])) {
                            if (values[10].length() <= INSURER_SKS_INSURER_NO) {
                                sqlBuff.append("'" + values[10] + "',");
                            } else {
                                sqlBuff.append("'"
                                        + values[10].substring(0,
                                                INSURER_SKS_INSURER_NO) + "',");
                            }
                        }
                        // �f�@�E������������ ���������
                        if (isValidValue(values[11])) {
                            // sqlBuff.append(values[11] + ",");
                            sqlBuff.append(formatSoukatuhyouType(values[11]) + ",");
                        }
    
                        if (isValidValue(values[12])) {
                            sqlBuff.append(values[12] + ",");
                        }
                        if (isValidValue(values[13])) {
                            sqlBuff.append(values[13] + ",");
                        }
                        if (isValidValue(values[14])) {
                            sqlBuff.append(values[14] + ",");
                        }
                    }
    
                    if (isValidValue(values[17])) {
                        sqlBuff.append(values[17] + ",");
                    }
                    if (isValidValue(values[18])) {
                        sqlBuff.append(values[18] + ",");
                    }
                    if (isValidValue(values[19])) {
                        sqlBuff.append(values[19] + ",");
                    }
                    if (isValidValue(values[16])) {
                        sqlBuff.append(values[16] + ",");
                    }
                    if (isValidValue(values[15])) {
                        sqlBuff.append(values[15] + ",");
                    }
                    //2006/02/11[Shin Fujihara] : replace begin
                    if (isValidValue(values[20])) {
                        //sqlBuff.append(values[20] + ",");
                        sqlBuff.append(getFormatNumver(values[20]) + ",");
                    }
                    if (isValidValue(values[21])) {
                        //sqlBuff.append(values[21] + ",");
                        sqlBuff.append(getFormatNumver(values[21]) + ",");
                    }
                    if (isValidValue(values[22])) {
                        //sqlBuff.append(values[22] + ",");
                        sqlBuff.append(getFormatNumver(values[22]) + ",");
                    }
                    if (isValidValue(values[23])) {
                        //sqlBuff.append(values[23] + ",");
                        sqlBuff.append(getFormatNumver(values[23]) + ",");
                    }
                    if (isValidValue(values[24])) {
                        //sqlBuff.append(values[24] + ",");
                        sqlBuff.append(getFormatNumver(values[24]) + ",");
                    }
                    if (isValidValue(values[25])) {
                        //sqlBuff.append(values[25] + ",");
                        sqlBuff.append(getFormatNumver(values[25]) + ",");
                    }
                    if (isValidValue(values[26])) {
                        //sqlBuff.append(values[26] + ",");
                        sqlBuff.append(getFormatNumver(values[26]) + ",");
                    }
                    if (isValidValue(values[27])) {
                        //sqlBuff.append(values[27] + ",");
                        sqlBuff.append(getFormatNumver(values[27]) + ",");
                    }
                    if (isValidValue(values[28])) {
                        ///sqlBuff.append(values[28] + ",");
                        sqlBuff.append(getFormatNumver(values[28]) + ",");
                    }
                    if (isValidValue(values[29])) {
                        //sqlBuff.append(values[29] + ",");
                        sqlBuff.append(getFormatNumver(values[29]) + ",");
                    }
                    if (isValidValue(values[30])) {
                        //sqlBuff.append(values[30] + ",");
                        sqlBuff.append(getFormatNumver(values[30]) + ",");
                    }
                    if (isValidValue(values[31])) {
                        //sqlBuff.append(values[31] + ",");
                        sqlBuff.append(getFormatNumver(values[31]) + ",");
                    }
                    if (isValidValue(values[32])) {
                        //sqlBuff.append(values[32] + ",");
                        sqlBuff.append(getFormatNumver(values[32]) + ",");
                    }
                    if (isValidValue(values[33])) {
                        //sqlBuff.append(values[33] + ",");
                        sqlBuff.append(getFormatNumver(values[33]) + ",");
                    }
                    if (isValidValue(values[34])) {
                        //sqlBuff.append(values[34] + ",");
                        sqlBuff.append(getFormatNumver(values[34]) + ",");
                    }
                    //2006/02/11[Shin Fujihara] : replace end
                    sqlBuff.append("'" + timeFormatter.format(nowDate) + "'");
                    sqlBuff.append(")");
    
                    // SQL�����s
                    //2006/02/09[Shin Fujihara] : replace begin
                    //dbManager.executeUpdate(sqlBuff.toString());
                    executeUpdateConvert(dbManager,sqlBuff.toString());
                    //2006/02/09[Shin Fujihara] : replace end
                
                //2006/02/11[Shin Fujihara] : add begin
                } catch(Exception e) {
                    VRLogger.warning(e);
                    errorCount++;
                }
                //2006/02/11[Shin Fujihara] : add end

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return errorCount;
    }

    /**
     * ��Ë@�֏��f�[�^�ڍs����
     * 
     * @param file File
     * @param nowDate Date
     * @param dbManager NCFirebirdDBManager
     */
    //2006/02/11[Shin Fujihara] : replace begin
//    private void convertDoctor(File file, Date nowDate,
//            IkenshoFirebirdDBManager dbManager) {
    private int convertDoctor(File file, Date nowDate,
            IkenshoFirebirdDBManager dbManager) {
    int errorCount = 0;
    //2006/02/11[Shin Fujihara] : replace end
        try {
            // �f�[�^��������
            ArrayList list = splitCsvList(file);
            // �f�[�^�x�[�X�ɑ}��
            for (int i = 0; i < list.size(); i++) {
                
                //2006/02/11[Shin Fujihara] : add begin
                //try catch��ǉ�
                try{
                //2006/02/11[Shin Fujihara] : add end

                    String[] values = (String[]) list.get(i);
                    // �t�@�C�������m�F
                    if (values.length != 18){
                        errorCount++;
                        continue;
                    }
    
                    //2006/02/11[Shin Fujihara] : add begin
                    //�����^�u��u��
                    for(int j = 0 ; j < values.length; j++){
                        if(values[j] != null){
                            values[j] = values[j].replaceAll(charVT,"");
                        }
                    }
                    //2006/02/11[Shin Fujihara] : add end
                    //2006/02/11[Shin Fujihara] : replace begin
    //                String[] tel = splitTelNumber(values[4]);
    //                String[] fax = splitTelNumber(values[5]);
    //                String[] cel_tel = splitTelNumber(values[6]);
                    String[] tel = splitTelNumberDetail(values[4]);
                    String[] fax = splitTelNumberDetail(values[5]);
                    String[] cel_tel = splitTelNumberDetail(values[6]);
                    //2006/02/11[Shin Fujihara] : replace begin
    
                    StringBuffer sqlBuff = new StringBuffer();
                    sqlBuff.append("INSERT INTO DOCTOR (");
                    if (isValidValue(values[0])) {
                        sqlBuff.append("DR_NM,");
                    }
                    if (isValidValue(values[1])) {
                        sqlBuff.append("MI_NM,");
                    }
                    if (isValidValue(values[2])) {
                        sqlBuff.append("MI_POST_CD,");
                    }
                    if (isValidValue(values[3])) {
                        sqlBuff.append("MI_ADDRESS,");
                    }
                    if (isValidValue(tel[0])) {
                        sqlBuff.append("MI_TEL1,");
                    }
                    if (isValidValue(tel[1])) {
                        sqlBuff.append("MI_TEL2,");
                    }
                    if (isValidValue(fax[0])) {
                        sqlBuff.append("MI_FAX1,");
                    }
                    if (isValidValue(fax[1])) {
                        sqlBuff.append("MI_FAX2,");
                    }
                    if (isValidValue(cel_tel[0])) {
                        sqlBuff.append("MI_CEL_TEL1,");
                    }
                    if (isValidValue(cel_tel[1])) {
                        sqlBuff.append("MI_CEL_TEL2,");
                    }
                    if (isValidValue(values[7])) {
                        sqlBuff.append("KINKYU_RENRAKU,");
                    }
                    if (isValidValue(values[8])) {
                        sqlBuff.append("FUZAIJI_TAIOU,");
                    }
                    if (isValidValue(values[9])) {
                        sqlBuff.append("BIKOU,");
                    }
                    if (isValidValue(values[10])) {
                        sqlBuff.append("KAISETUSHA_NM,");
                    }
                    if (isValidValue(values[11])) {
                        sqlBuff.append("DR_NO,");
                    }
                    sqlBuff.append("MI_KBN,");
                    if (isValidValue(values[13])) {
                        sqlBuff.append("BANK_NM,");
                    }
                    if (isValidValue(values[14])) {
                        sqlBuff.append("BANK_SITEN_NM,");
                    }
                    if (isValidValue(values[15])) {
                        sqlBuff.append("BANK_KOUZA_NO,");
                    }
                    sqlBuff.append("BANK_KOUZA_KIND,");
                    if (isValidValue(values[17])) {
                        sqlBuff.append("FURIKOMI_MEIGI,");
                    }
                    sqlBuff.append("LAST_TIME");
                    sqlBuff.append(")");
    
                    sqlBuff.append(" VALUES (");
                    if (isValidValue(values[0])) {
                        if (values[0].length() <= DOCTOR_DR_NM) {
                            sqlBuff.append("'" + values[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[0].substring(0, DOCTOR_DR_NM) + "',");
                        }
                    }
                    if (isValidValue(values[1])) {
                        if (values[1].length() <= DOCTOR_MI_NM) {
                            sqlBuff.append("'" + values[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[1].substring(0, DOCTOR_MI_NM) + "',");
                        }
                    }
                    if (isValidValue(values[2])) {
                        if (values[2].length() <= DOCTOR_MI_POST_CD) {
                            sqlBuff.append("'" + values[2] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[2].substring(0, DOCTOR_MI_POST_CD)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[3])) {
                        if (values[3].length() <= DOCTOR_MI_ADDRESS) {
                            sqlBuff.append("'" + values[3] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[3].substring(0, DOCTOR_MI_ADDRESS)
                                    + "',");
                        }
                    }
                    if (isValidValue(tel[0])) {
                        if (tel[0].length() <= DOCTOR_MI_TEL1) {
                            sqlBuff.append("'" + tel[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + tel[0].substring(0, DOCTOR_MI_TEL1) + "',");
                        }
                    }
                    if (isValidValue(tel[1])) {
                        if (tel[1].length() <= DOCTOR_MI_TEL2) {
                            sqlBuff.append("'" + tel[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + tel[1].substring(0, DOCTOR_MI_TEL2) + "',");
                        }
                    }
                    if (isValidValue(fax[0])) {
                        if (fax[0].length() <= DOCTOR_MI_FAX1) {
                            sqlBuff.append("'" + fax[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + fax[0].substring(0, DOCTOR_MI_FAX1) + "',");
                        }
                    }
                    if (isValidValue(fax[1])) {
                        if (fax[1].length() <= DOCTOR_MI_FAX2) {
                            sqlBuff.append("'" + fax[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + fax[1].substring(0, DOCTOR_MI_FAX2) + "',");
                        }
                    }
                    if (isValidValue(cel_tel[0])) {
                        if (cel_tel[0].length() <= DOCTOR_MI_CEL_TEL1) {
                            sqlBuff.append("'" + cel_tel[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + cel_tel[0].substring(0, DOCTOR_MI_CEL_TEL1)
                                    + "',");
                        }
                    }
                    if (isValidValue(cel_tel[1])) {
                        if (cel_tel[1].length() <= DOCTOR_MI_CEL_TEL2) {
                            sqlBuff.append("'" + cel_tel[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + cel_tel[1].substring(0, DOCTOR_MI_CEL_TEL2)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[7])) {
                        if (values[7].length() <= DOCTOR_KINKYU_RENRAKU) {
                            sqlBuff.append("'" + values[7] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[7].substring(0, DOCTOR_KINKYU_RENRAKU)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[8])) {
                        if (values[8].length() <= DOCTOR_FUZAIJI_TAIOU) {
                            sqlBuff.append("'" + values[8] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[8].substring(0, DOCTOR_FUZAIJI_TAIOU)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[9])) {
                        if (values[9].length() <= DOCTOR_BIKOU) {
                            sqlBuff.append("'" + values[9] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[9].substring(0, DOCTOR_BIKOU) + "',");
                        }
                    }
                    if (isValidValue(values[10])) {
                        if (values[10].length() <= DOCTOR_KAISETUSHA_NM) {
                            sqlBuff.append("'" + values[10] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[10].substring(0, DOCTOR_KAISETUSHA_NM)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[11])) {
                        if (values[11].length() <= DOCTOR_DR_NO) {
                            sqlBuff.append("'" + values[11] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[11].substring(0, DOCTOR_DR_NO) + "',");
                        }
                    }
    
                    sqlBuff.append(formatShinryoujyoType(values[12]) + ",");
    
                    if (isValidValue(values[13])) {
                        if (values[13].length() <= DOCTOR_BANK_NM) {
                            sqlBuff.append("'" + values[13] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[13].substring(0, DOCTOR_BANK_NM)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[14])) {
                        if (values[14].length() <= DOCTOR_BANK_SITEN_NM) {
                            sqlBuff.append("'" + values[14] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[14].substring(0, DOCTOR_BANK_SITEN_NM)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[15])) {
                        if (values[15].length() <= DOCTOR_BANK_KOUZA_NO) {
                            sqlBuff.append("'" + values[15] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[15].substring(0, DOCTOR_BANK_KOUZA_NO)
                                    + "',");
                        }
                    }
    
                    sqlBuff.append(formatBankType(values[16]) + ",");
    
                    if (isValidValue(values[17])) {
                        if (values[17].length() <= DOCTOR_FURIKOMI_MEIGI) {
                            sqlBuff.append("'" + values[17] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[17]
                                            .substring(0, DOCTOR_FURIKOMI_MEIGI)
                                    + "',");
                        }
                    }
                    sqlBuff.append("'" + timeFormatter.format(nowDate) + "'");
                    sqlBuff.append(")");
    
                    // SQL�����s
                    //2006/02/09[Shin Fujihara] : replace begin
                    //dbManager.executeUpdate(sqlBuff.toString());
                    executeUpdateConvert(dbManager,sqlBuff.toString());
                    //2006/02/09[Shin Fujihara] : replace end
                
                //2006/02/11[Shin Fujihara] : add begin
                } catch(Exception e) {
                    VRLogger.warning(e);
                    errorCount++;
                }
                //2006/02/11[Shin Fujihara] : add end

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return errorCount;
    }

    /**
     * �K����X�e�[�V�����f�[�^�ڍs����
     * 
     * @param file File
     * @param nowDate Date
     * @param dbManager NCFirebirdDBManager
     */
    //2006/02/11[Shin Fujihara] : replace begin
//    private void convertStation(File file, Date nowDate,
//            IkenshoFirebirdDBManager dbManager) {
    private int convertStation(File file, Date nowDate,
            IkenshoFirebirdDBManager dbManager) {
        int errorCount = 0;
    //2006/02/11[Shin Fujihara] : replace end
        try {
            // �f�[�^��������
            ArrayList list = splitCsvList(file);
            // �f�[�^�x�[�X�ɑ}��
            for (int i = 0; i < list.size(); i++) {
                
                //2006/02/11[Shin Fujihara] : add begin
                try{
                //2006/02/11[Shin Fujihara] : add end
    
                    String[] values = (String[]) list.get(i);
                    // �t�@�C�������m�F
                    if (values.length != 10){
                        errorCount++;
                        continue;
                    }
                    //2006/02/11[Shin Fujihara] : add begin
                    //�����^�u��u��
                    for(int j = 0 ; j < values.length; j++){
                        if(values[j] != null){
                            values[j] = values[j].replaceAll(charVT,"");
                        }
                    }
                    //2006/02/11[Shin Fujihara] : add end
                    //2006/02/11[Shin Fujihara] : replace begin
                    String[] tel = splitTelNumberDetail(values[4]);
                    String[] fax = splitTelNumberDetail(values[5]);
                    String[] cel_tel = splitTelNumberDetail(values[6]);
                    //2006/02/11[Shin Fujihara] : replace end
    
                    StringBuffer sqlBuff = new StringBuffer();
                    sqlBuff.append("INSERT INTO STATION (");
                    if (isValidValue(values[0])) {
                        sqlBuff.append("DR_NM,");
                    }
                    if (isValidValue(values[1])) {
                        sqlBuff.append("MI_NM,");
                    }
                    if (isValidValue(values[2])) {
                        sqlBuff.append("MI_POST_CD,");
                    }
                    if (isValidValue(values[3])) {
                        sqlBuff.append("MI_ADDRESS,");
                    }
                    if (isValidValue(tel[0])) {
                        sqlBuff.append("MI_TEL1,");
                    }
                    if (isValidValue(tel[1])) {
                        sqlBuff.append("MI_TEL2,");
                    }
                    if (isValidValue(fax[0])) {
                        sqlBuff.append("MI_FAX1,");
                    }
                    if (isValidValue(fax[1])) {
                        sqlBuff.append("MI_FAX2,");
                    }
                    if (isValidValue(cel_tel[0])) {
                        sqlBuff.append("MI_CEL_TEL1,");
                    }
                    if (isValidValue(cel_tel[1])) {
                        sqlBuff.append("MI_CEL_TEL2,");
                    }
                    if (isValidValue(values[7])) {
                        sqlBuff.append("KINKYU_RENRAKU,");
                    }
                    if (isValidValue(values[8])) {
                        sqlBuff.append("FUZAIJI_TAIOU,");
                    }
                    if (isValidValue(values[9])) {
                        sqlBuff.append("BIKOU,");
                    }
                    sqlBuff.append("LAST_TIME");
                    sqlBuff.append(")");
    
                    sqlBuff.append(" VALUES (");
                    if (isValidValue(values[0])) {
                        if (values[0].length() <= STATION_DR_NM) {
                            sqlBuff.append("'" + values[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[0].substring(0, STATION_DR_NM) + "',");
                        }
                    }
                    if (isValidValue(values[1])) {
                        if (values[1].length() <= STATION_MI_NM) {
                            sqlBuff.append("'" + values[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[1].substring(0, STATION_MI_NM) + "',");
                        }
                    }
                    if (isValidValue(values[2])) {
                        if (values[2].length() <= STATION_MI_POST_CD) {
                            sqlBuff.append("'" + values[2] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[2].substring(0, STATION_MI_POST_CD)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[3])) {
                        if (values[3].length() <= STATION_MI_ADDRESS) {
                            sqlBuff.append("'" + values[3] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[3].substring(0, STATION_MI_ADDRESS)
                                    + "',");
                        }
                    }
                    if (isValidValue(tel[0])) {
                        if (tel[0].length() <= STATION_MI_TEL1) {
                            sqlBuff.append("'" + tel[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + tel[0].substring(0, STATION_MI_TEL1) + "',");
                        }
                    }
                    if (isValidValue(tel[1])) {
                        if (tel[1].length() <= STATION_MI_TEL2) {
                            sqlBuff.append("'" + tel[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + tel[1].substring(0, STATION_MI_TEL2) + "',");
                        }
                    }
                    if (isValidValue(fax[0])) {
                        if (fax[0].length() <= STATION_MI_FAX1) {
                            sqlBuff.append("'" + fax[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + fax[0].substring(0, STATION_MI_FAX1) + "',");
                        }
                    }
                    if (isValidValue(fax[1])) {
                        if (fax[1].length() <= STATION_MI_FAX2) {
                            sqlBuff.append("'" + fax[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + fax[1].substring(0, STATION_MI_FAX2) + "',");
                        }
                    }
                    if (isValidValue(cel_tel[0])) {
                        if (cel_tel[0].length() <= STATION_MI_CEL_TEL1) {
                            sqlBuff.append("'" + cel_tel[0] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + cel_tel[0].substring(0, STATION_MI_CEL_TEL1)
                                    + "',");
                        }
                    }
                    if (isValidValue(cel_tel[1])) {
                        if (cel_tel[1].length() <= STATION_MI_CEL_TEL2) {
                            sqlBuff.append("'" + cel_tel[1] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + cel_tel[1].substring(0, STATION_MI_CEL_TEL2)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[7])) {
                        if (values[7].length() <= STATION_KINKYU_RENRAKU) {
                            sqlBuff.append("'" + values[7] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[7]
                                            .substring(0, STATION_KINKYU_RENRAKU)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[8])) {
                        if (values[8].length() <= STATION_FUZAIJI_TAIOU) {
                            sqlBuff.append("'" + values[8] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[8].substring(0, STATION_FUZAIJI_TAIOU)
                                    + "',");
                        }
                    }
                    if (isValidValue(values[9])) {
                        if (values[9].length() <= STATION_BIKOU) {
                            sqlBuff.append("'" + values[9] + "',");
                        } else {
                            sqlBuff.append("'"
                                    + values[9].substring(0, STATION_BIKOU) + "',");
                        }
                    }
                    sqlBuff.append("'" + timeFormatter.format(nowDate) + "'");
                    sqlBuff.append(")");
    
                    // SQL�����s
                    //2006/02/09[Shin Fujihara] : replace begin
                    //dbManager.executeUpdate(sqlBuff.toString());
                    executeUpdateConvert(dbManager,sqlBuff.toString());
                //2006/02/09[Shin Fujihara] : replace end
                } catch(Exception e) {
                    VRLogger.warning(e);
                    errorCount++;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return errorCount;
    }
    
    //add start shin fujihara 2006.2.3
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
            String[] tel = splitTelNumberDetail(values[21]);

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
            //2006/02/09[Shin Fujihara] : replace begin
            //executeUpdateConvert(dbManager,sb.toString());
            executeUpdateConvert(dbManager,sb.toString());
            //2006/02/09[Shin Fujihara] : replace end
            
            //�o�^�������҂�ID���擾
            VRList list = (VRList)dbManager.executeQuery("SELECT GEN_ID(GEN_PATIENT,0) FROM RDB$DATABASE");
            
            if((list != null) && (list.size() > 0)){
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
    //2006/02/11[Shin Fujihara] : replace begin
//    private void convertIkensho(File file, Date nowDate,
//            IkenshoFirebirdDBManager dbManager) {
    private int convertIkensho(File file, Date nowDate,
            IkenshoFirebirdDBManager dbManager) {
        int errorCount = 0;
    //2006/02/11[Shin Fujihara] : replace end
        try {
            
            // �f�[�^��������
            ArrayList list = splitCsvList(file);
            // �f�[�^�x�[�X�ɑ}��
            for (int i = 0; i < list.size(); i++) {

                //2006/02/11[Shin Fujihara] : add begin
                try{
                //2006/02/11[Shin Fujihara] : add end
                
                    String[] values = (String[]) list.get(i);
                    // �t�@�C�������m�F
                    if (values.length != 104){
                        errorCount++;
                        continue;
                    }
                    //2006/02/11[Shin Fujihara] : replace begin
                    //�����^�u��u��
                    for(int j = 0 ; j < values.length; j++){
                        if(values[j] != null){
                            //��ܖ�(42)�͒u�����Ȃ��B
                            //���L����(103)�͒u�����Ȃ��B
                            if((j != 42) && (j != 103)){
                                values[j] = values[j].replaceAll(charVT,"");
                            }
                        }
                    }
                    //2006/02/11[Shin Fujihara] : replace end
                    
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
                
                //2006/02/11[Shin Fujihara] : add begin
                } catch(Exception e){
                    VRLogger.warning(e);
                    e.printStackTrace();
                    errorCount++;
                }
                //2006/02/11[Shin Fujihara] : add end

            }
        } catch (Exception ex) {
            VRLogger.warning(ex.toString());
            ex.printStackTrace();
        }
        return errorCount;
    }
    
    /**
     * COMMON_IKN_SIS�e�[�u���ւ̃C���T�[�g�����쐬���܂��B
     * @param values
     * @return
     * @throws Exception
     */
    private String getCommonIknSisSQL(String[] values,int patientNo,Date nowDate) throws Exception {
        StringBuffer sb = new StringBuffer();
        
        String[] tel = splitTelNumberDetail(values[21]);
        String[] mi_tel = splitTelNumberDetail(values[26]);
        String[] mi_fax = splitTelNumberDetail(values[27]);
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
                //2006/02/11[Shin Fujihara] : replace begin
                //sb.append("'" + values[35] + "',");
                sb.append("'" + values[35].replaceAll("��","") + "',");
                //2006/02/11[Shin Fujihara] : replace end
            }
        }
        //HASHOU_DT2
        if(isValidValue(values[37])){
            if("�s��".equals(values[37])){
                sb.append("'�s��00�N00��00��',");
            } else {
                //2006/02/11[Shin Fujihara] : replace begin
                //sb.append("'" + values[37] + "',");
                sb.append("'" + values[37].replaceAll("��","") + "',");
                //2006/02/11[Shin Fujihara] : replace end
            }
        }
        //HASHOU_DT3
        if(isValidValue(values[39])){
            if("�s��".equals(values[39])){
                sb.append("'�s��00�N00��00��',");
            } else {
                //2006/02/11[Shin Fujihara] : replace begin
                //sb.append("'" + values[39] + "',");
                sb.append("'" + values[39].replaceAll("��","") + "',");
                //2006/02/11[Shin Fujihara] : replace end
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
        //2006/02/11[Shin Fujihara] : replace begin
        //String[] tokki = getTokki(values[103]);
        String[] tokki = getTokki(values[103],values[1]);
        //2006/02/11[Shin Fujihara] : replace end
        
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
        if(isValidValue(getFormatNumverNoCut(values[5]))){
            sb.append("INSURED_NO,");
        }
        //�쐬�˗���
        if(isValidValue(values[8])){
            sb.append("REQ_DT,");
        }
        //�˗��ԍ�
        if(isValidValue(getFormatNumverNoCut(values[10]))){
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
        //2006/02/11[Shin Fujihara] : add begin
        sb.append("CREATE_DT,");
        //2006/02/11[Shin Fujihara] : add end
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
            if((charAry[i] == '1') && (count < temp.length)){
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
        setValue(sb,getFormatNumverNoCut(values[5]),IKN_ORIGIN_INSURED_NO);
        //REQ_DT
        if(isValidValue(values[8])){
            sb.append("'" + dateFormatter.format(dateFormatter.parse(values[8])) + "',");
        }
        //REQ_NO
        setValue(sb,getFormatNumverNoCut(values[10]),IKN_ORIGIN_REQ_NO);
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
        //2006/02/11[Shin Fujihara] : add begin
        sb.append("'" + timeFormatter.format(nowDate) + "',");
        //2006/02/11[Shin Fujihara] : add end
        //LAST_TIME
        sb.append("'" + timeFormatter.format(nowDate) + "'");
        sb.append(")");
        
        return sb.toString();
    }
    
    private int getPatientNo(String[] values,IkenshoFirebirdDBManager dbManager,Date nowDate) throws Exception {
        int result = Integer.MIN_VALUE;
        StringBuffer sb = new StringBuffer();
        //2006/02/11[Shin Fujihara] : add begin
        boolean addAnd = false;
        //2006/02/11[Shin Fujihara] : add end
        
        sb.append(" SELECT");
        sb.append(" PATIENT.PATIENT_NO");
        sb.append(" ,IKN_ORIGIN.EDA_NO");
        sb.append(" FROM");
        sb.append(" PATIENT");
        sb.append(" LEFT OUTER JOIN IKN_ORIGIN");
        sb.append(" ON");
        sb.append(" PATIENT.PATIENT_NO = IKN_ORIGIN.PATIENT_NO");
        sb.append(" WHERE");
        
        //2006/02/11[Shin Fujihara] : replace begin
        //���Ҏ���
        if(isValidValue(values[15])) {
            sb.append(" (PATIENT_NM = '");
            if(values[15].length() <= PATIENT_PATIENT_NM){
                sb.append(values[15]);
            } else {
                sb.append(values[15].substring(0,PATIENT_PATIENT_NM));
            }
            sb.append("')");
            addAnd = true;
        }
        //���҂��Ȏ���
        if(isValidValue(values[14])){
            if(addAnd){
                sb.append(" AND");
            }
            sb.append(" (PATIENT_KN = '");
            if(values[14].length() <= PATIENT_PATIENT_KN){
                sb.append(values[14]);
            } else {
                sb.append(values[14].substring(0,PATIENT_PATIENT_KN));
            }
            sb.append("')");
            addAnd = true;
        }
        //����
        if(isValidValue(values[18]) && (!"0".equals(values[18]))){
            if(addAnd){
                sb.append(" AND");
            }
            sb.append(" (SEX = ");
            sb.append(values[18]);
            sb.append(")");
            addAnd = true;
        }
        //���N����
        if(isValidValue(values[16])){
            if(addAnd){
                sb.append(" AND");
            }
            sb.append(" (BIRTHDAY = '");
            sb.append(dateFormatter.format(dateFormatter.parse(values[16])));
            sb.append("')");
        }
//        sb.append(" (PATIENT_NM = '" + values[15] + "')");
//        sb.append(" AND (PATIENT_KN = '" + values[14] + "')");
//        if(isValidValue(values[16])){
//            if(values[16].length() == 8){
//                sb.append(" AND (BIRTHDAY = '" + values[16].substring(0,4) + "/" + values[16].substring(4,6) + "/" + values[16].substring(6) + "')");
//            }
//        }
//        sb.append(" AND (SEX = " + values[18] + ")");
        
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
            if((i < 5) && (result[0].length() <= 250)){
                result[0] += temp[i].trim() + "\n";
            }
            offset++;
        }
        int count = 0;
        int runCount = 0;
        for(int i = 0; i < temp.length - offset; i++){
            //2006/02/11[Shin Fujihara] : replace begin
            //String[] splitValue = temp[i + offset].split("/");
            String[] splitValue = temp[i + offset].split(" / ");
            
            //2006/02/11[Shin Fujihara] : replace end
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
            runCount++;
            if(runCount > 2){
                break;
            }
        }
        
        return result;
    }
    //��܃f�[�^�񂩔���
    private boolean isYakuzai(String value){
        //�P�D�g/�h��split�����Ƃ���2���������B
        //2006/02/11[Shin Fujihara] : replace begin
        value += "end";
        String[] temp = value.split(" / ");
        //2006/02/11[Shin Fujihara] : replace end
        if(temp.length != 2){
            return false;
        }
        //2006/02/11[Shin Fujihara] : add begin
        //end���ʂ�
        temp[1] = temp[1].substring(0,temp[1].length() - 3);
        //2006/02/11[Shin Fujihara] : add end
        //�Q�D�X�v���b�g�����f�[�^���󔒁A�܂��́g �h��3�ɕ��������B
        for(int i = 0; i < temp.length; i++){
            if(!"".equals(temp[i].replaceAll(" |�@",""))){
                //try
                //String[] temp2 = temp[i].trim().split(" ");
                String[] temp2 = spaceSplit(temp[i]);
                //try
                if(temp2.length != 3) return false;
            }
        }
        return true;
    }
    
    private String[] spaceSplit(String value){
        char[] charAry = value.toCharArray();
        ArrayList ary = new ArrayList();
        StringBuffer esc = new StringBuffer();
        for(int i = 0; i < charAry.length; i++){
            switch(charAry[i]){
                case ' ':
                    ary.add(esc.toString());
                    esc = new StringBuffer();
                    break;
                default:
                    esc.append(charAry[i]);
                    break;
                
            }
        }
        ary.add(esc.toString());
        String[] result = new String[ary.size()];
        for(int i = 0; i < ary.size(); i++){
            result[i] = (String)ary.get(i);
        }
        return result;
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
        //2006/02/11[Shin Fujihara] : del begin
//        if(data != null){
//            data = data.trim();
//        }
        //2006/02/11[Shin Fujihara] : del begin
        if(!isValidValue(data)){
            return null;
        }
        
        //�f�[�^�𕪊�
        String[] temp = new String[3];
        Arrays.fill(temp,"");
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
            if(".0123456789�D�O�P�Q�R�S�T�U�V�W�X".indexOf(charAry[i]) == -1){
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
    //
    //private String[] getTokki(String value)throws Exception {
    private String[] getTokki(String value,String csvVersion)throws Exception {
    //
        String[] result = new String[7];
        Arrays.fill(result,"");
        
        //2006/02/11[Shin Fujihara] : add begin
        if(csvVersion != null){
            csvVersion = csvVersion.toUpperCase();
            //windows�t�H�[�}�b�g�ł���΁A�ڍs���s��Ȃ�
            if(csvVersion.indexOf("WIN") != -1){
                return result;
            }
        }
        //2006/02/11[Shin Fujihara] : add end
        
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
        int limitCount = 0;
        for(int i = count; i < temp.length; i++){
            result[6] += temp[i] + "\n";
            limitCount++;
            if((limitCount > 8) || (result[6].length() > 400)){
                break;
            }
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

    private String[] splitTelNumberDetail(String telNumber) {
        String[] telNumbers = new String[2];
        Arrays.fill(telNumbers,"");
        if (telNumber != null && telNumber.length() > 0) {
            //�S�p���l������ꍇ���l��
            telNumber = telNumber.replaceAll("�P","1");
            telNumber = telNumber.replaceAll("�Q","2");
            telNumber = telNumber.replaceAll("�R","3");
            telNumber = telNumber.replaceAll("�S","4");
            telNumber = telNumber.replaceAll("�T","5");
            telNumber = telNumber.replaceAll("�U","6");
            telNumber = telNumber.replaceAll("�V","7");
            telNumber = telNumber.replaceAll("�W","8");
            telNumber = telNumber.replaceAll("�X","9");
            telNumber = telNumber.replaceAll("�O","0");
            
            String[] temp = telNumber.split("-");
            switch(temp.length){
                case 1:
                    if(temp[0].length() > 5){
                        telNumbers[0] = temp[0].substring(0,5);
                    } else {
                        telNumbers[0] = temp[0];
                    }
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
                    if(temp[0].length() > 5){
                        temp[0] = temp[0].substring(0,5); 
                    }
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
        }
        return telNumbers;
    }
    
    //add end shin fujihara 2006.2.3

//    private String[] splitTelNumber(String telNumber) {
//        String[] telNumbers = new String[2];
//        if (telNumber != null && telNumber.length() > 0) {
//            if (telNumber.indexOf("-") != -1) {
//                telNumbers[0] = telNumber.substring(0, telNumber.indexOf("-"));
//                telNumbers[1] = telNumber.replaceAll("^" + telNumbers[0] + "-",
//                        "");
//            } else {
//                telNumbers[0] = telNumber;
//            }
//        }
//        return telNumbers;
//    }

    private boolean isCheck() {
        String pathPatient = txtPatient.getText().trim();
        String pathDoctor = txtDoctor.getText().trim();
        String pathRenkeii = txtRenkeii.getText().trim();
        String pathInsurer = txtInsurer.getText().trim();
        String pathStation = txtStation.getText().trim();
        //add start shin fujihara 2006.2.3
        String pathIkensho = txtIkensho.getText().trim();
        //add end shin fujihara 2006.2.3
        if (pathPatient.equals("") && pathDoctor.equals("")
                && pathRenkeii.equals("") && pathInsurer.equals("")
                && pathStation.equals("")
                //add start shin fujihara 2006.2.3
                && pathIkensho.equals("")
                //add end shin fujihara 2006.2.3
                ) {
            JOptionPane.showMessageDialog(this, "�ڍs���f�[�^������ݒ肳��Ă��܂���B");
            return false;
        }
        // ����
        if (!pathPatient.equals("")) {
            if (!new File(pathPatient).exists()) {
                JOptionPane.showMessageDialog(this, lblPatient.getText()
                        + "�ɐݒ肳�ꂽ�t�@�C���͑��݂��܂���B�ݒ肵�����ĉ������B");
                btnPatient.requestFocus();
                return false;
            }
        }
        // ��Ë@��
        if (!pathDoctor.equals("")) {
            if (!new File(pathDoctor).exists()) {
                JOptionPane.showMessageDialog(this, lblDoctor.getText()
                        + "�ɐݒ肳�ꂽ�t�@�C���͑��݂��܂���B�ݒ肵�����ĉ������B");
                btnDoctor.requestFocus();
                return false;
            }
        }
        // �A�g��
        if (!pathRenkeii.equals("")) {
            if (!new File(pathRenkeii).exists()) {
                JOptionPane.showMessageDialog(this, lblRenkeii.getText()
                        + "�ɐݒ肳�ꂽ�t�@�C���͑��݂��܂���B�ݒ肵�����ĉ������B");
                btnRenkeii.requestFocus();
                return false;
            }
        }
        // �ی���
        if (!pathInsurer.equals("")) {
            if (!new File(pathInsurer).exists()) {
                JOptionPane.showMessageDialog(this, lblInsurer.getText()
                        + "�ɐݒ肳�ꂽ�t�@�C���͑��݂��܂���B�ݒ肵�����ĉ������B");
                btnInsurer.requestFocus();
                return false;
            }
        }
        // �K����X�e�[�V����
        if (!pathStation.equals("")) {
            if (!new File(pathStation).exists()) {
                JOptionPane.showMessageDialog(this, lblStation.getText()
                        + "�ɐݒ肳�ꂽ�t�@�C���͑��݂��܂���B�ݒ肵�����ĉ������B");
                btnStation.requestFocus();
                return false;
            }
        }
        //add start shin fujihara 2006.2.3
        // �厡��ӌ���
        if (!pathIkensho.equals("")) {
            if (!new File(pathIkensho).exists()) {
                JOptionPane.showMessageDialog(this, lblIkensho.getText()
                        + "�ɐݒ肳�ꂽ�t�@�C���͑��݂��܂���B�ݒ肵�����ĉ������B");
                btnIkensho.requestFocus();
                return false;
            }
        }
        //add end shin fujihara 2006.2.3
        return true;
    }

    private ArrayList splitCsvList(File file) {
        ArrayList cvsList = new ArrayList();
        try {
            // ���������Ή�
            // BufferedReader br = new BufferedReader(new FileReader(file));
            //2006/02/11[Shin Fujihara] : replace begin
            //Mac��
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    new FileInputStream(file), "SJIS"));
            BufferedReader br = null;
            if(doWindows){
                br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), "MS932"));
            } else {
                br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), "SJIS"));
            }
            //2006/02/11[Shin Fujihara] : replace end

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

    private String formatShinryoujyoType(String source) {
        String value = "0";
        if (isValidValue(source)) {
            if ("�f�Ï�".equals(source)) {
                value = "1";
            } else if ("�a�@".equals(source)) {
                value = "2";
            } else if ("���̑��̎{��".equals(source)) {
                value = "3";
            }
        }
        return value;
    }

    private String formatSoukatuhyouType(String source) {
        String value = "0";
        if (isValidValue(source)) {
            if ("2".equals(source)) {
                value = "1";
            } else if ("3".equals(source)) {
                value = "2";
            }
        }
        return value;
    }

    private String formatSeikyushoType(String source) {
        String value = "0";
        if (isValidValue(source)) {
            if ("0".equals(source)) {
                value = SEIKYUSHO_ISS_SKS;
            } else if ("1".equals(source)) {
                value = SEIKYUSHO_ISS_AND_SKS;
            } else if ("2".equals(source)) {
                value = SEIKYUSHO_ISS_ONLY;
            } else if ("3".equals(source)) {
                value = SEIKYUSHO_SKS_ONLY;
            }
        }
        return value;
    }

    private String formatSexType(String source) {
        String value = "0";
        if (isValidValue(source)) {
            if ("�j".equals(source)) {
                value = "1";
            } else if ("��".equals(source)) {
                value = "2";
            }
        }
        return value;
    }

    private String formatBankType(String source) {
        String value = "2";
        if (isValidValue(source)) {
            if ("����".equals(source)) {
                value = "1";
            } else if ("����".equals(source)) {
                value = "0";
            }
        }
        return value;
    }
    
    //2006/02/11[Shin Fujihara] : add begin
    private String getFormatNumverNoCut(String source){
        String result = source;
        if(!isValidValue(source)){
            return "";
        }
        //�S�p���l��u��
        result = result.replaceAll("�P","1");
        result = result.replaceAll("�Q","2");
        result = result.replaceAll("�R","3");
        result = result.replaceAll("�S","4");
        result = result.replaceAll("�T","5");
        result = result.replaceAll("�U","6");
        result = result.replaceAll("�V","7");
        result = result.replaceAll("�W","8");
        result = result.replaceAll("�X","9");
        result = result.replaceAll("�O","0");
        result = result.replaceAll("�D",".");
        
        char[] charAry = result.toCharArray();
        StringBuffer esc = new StringBuffer();
        for(int i = 0; i < charAry.length; i++){
            switch(charAry[i]){
                case'1':case'2':case'3':case'4':case'5':case'6':case'7':case'8':case'9':case'0':case'.':
                    esc.append(charAry[i]);
                    break;
            }
        }
        result = esc.toString();
        
        return result;
    }
    
    
    private String getFormatNumver(String source){
        String result = source;
        if(!isValidValue(source)){
            return "0";
        }
        //�S�p���l��u��
        result = result.replaceAll("�P","1");
        result = result.replaceAll("�Q","2");
        result = result.replaceAll("�R","3");
        result = result.replaceAll("�S","4");
        result = result.replaceAll("�T","5");
        result = result.replaceAll("�U","6");
        result = result.replaceAll("�V","7");
        result = result.replaceAll("�W","8");
        result = result.replaceAll("�X","9");
        result = result.replaceAll("�O","0");
        result = result.replaceAll("�D",".");
        
        char[] charAry = result.toCharArray();
        StringBuffer esc = new StringBuffer();
        for(int i = 0; i < charAry.length; i++){
            switch(charAry[i]){
                case'1':case'2':case'3':case'4':case'5':case'6':case'7':case'8':case'9':case'0':case'.':
                    esc.append(charAry[i]);
                    break;
            }
        }
        result = esc.toString();
        if(result.length() > 9){
            result = result.substring(0,9);
        }
        
//        try{
//            Integer.parseInt(result);
//        } catch(Exception e){
//            result = "0";
//        }
        
        return result;
    }
    //2006/02/11[Shin Fujihara] : add end

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
        //2006/02/11[Shin Fujihara] : add begin
        //�G���[�����̕\����ǉ�
        int patient = 0;
        int doctor = 0;
        int renkeii = 0;
        int insurer = 0;
        int station = 0;
        int ikensho = 0;
        StringBuffer errorMessage = new StringBuffer();
        //2006/02/11[Shin Fujihara] : add end
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
                    // ���Ҋ�{���f�[�^�ڍs����
                    if (!txtPatient.getText().equals("")) {
                        //2006/02/11[Shin Fujihara] : replace begin
//                        convertPatient(new File(txtPatient.getText()), nowDate,
//                                dbManager);
                        patient = convertPatient(new File(txtPatient.getText()), nowDate,
                                dbManager);
                        //�G���[�����������ꍇ
                        if(patient > 0){
                            errorMessage.append("���Ҋ�{���F�t�@�C���t�H�[�}�b�g�s����" + patient + "����荞�݂Ɏ��s���܂����B\n");
                        }
                        //2006/02/11[Shin Fujihara] : replace end
                    }
                    // ��Ë@�֏��f�[�^�ڍs����
                    if (!txtDoctor.getText().equals("")) {
                        //2006/02/11[Shin Fujihara] : add begin
//                        convertDoctor(new File(txtDoctor.getText()), nowDate,
//                                dbManager);
                        doctor = convertDoctor(new File(txtDoctor.getText()), nowDate,
                                dbManager);
                        //�G���[�����������ꍇ
                        if(doctor > 0){
                            errorMessage.append("��Ë@�֏��F�t�@�C���t�H�[�}�b�g�s����" + doctor + "����荞�݂Ɏ��s���܂����B\n");
                        }
                        //2006/02/11[Shin Fujihara] : add end
                    }
                    // �A�g����f�[�^�ڍs����
                    if (!txtRenkeii.getText().equals("")) {
                        //2006/02/11[Shin Fujihara] : replace begin
//                        convertRenkeii(new File(txtRenkeii.getText()), nowDate,
//                                dbManager);
                        renkeii = convertRenkeii(new File(txtRenkeii.getText()), nowDate,
                                dbManager);
                        //�G���[�����������ꍇ
                        if(renkeii > 0){
                            errorMessage.append("�A�g����F�t�@�C���t�H�[�}�b�g�s����" + renkeii + "����荞�݂Ɏ��s���܂����B\n");
                        }
                        //2006/02/11[Shin Fujihara] : replace end
                    }
                    // �ی��҃f�[�^�ڍs����
                    if (!txtInsurer.getText().equals("")) {
                        //2006/02/11[Shin Fujihara] : replace begin
//                        convertInsurer(new File(txtInsurer.getText()), nowDate,
//                                dbManager);
                        insurer = convertInsurer(new File(txtInsurer.getText()), nowDate,
                                dbManager);
                        //�G���[�����������ꍇ
                        if(insurer > 0){
                            errorMessage.append("�ی��ҏ��F�t�@�C���t�H�[�}�b�g�s����" + insurer + "����荞�݂Ɏ��s���܂����B\n");
                        }
                        //2006/02/11[Shin Fujihara] : replace end
                    }
                    // �K����X�e�[�V�����f�[�^�ڍs����
                    if (!txtStation.getText().equals("")) {
                        //2006/02/11[Shin Fujihara] : replace begin
//                        convertStation(new File(txtStation.getText()), nowDate,
//                                dbManager);
                        station = convertStation(new File(txtStation.getText()), nowDate,
                                dbManager);
                        //�G���[�����������ꍇ
                        if(station > 0){
                            errorMessage.append("�K����X�e�[�V�������F�t�@�C���t�H�[�}�b�g�s����" + station + "����荞�݂Ɏ��s���܂����B\n");
                        }
                        //2006/02/11[Shin Fujihara] : replace end
                    }
                    // �厡��ӌ����f�[�^�ڍs����
                    if (!txtIkensho.getText().equals("")) {
                        //2006/02/11[Shin Fujihara] : add begin
//                        convertIkensho(new File(txtIkensho.getText()), nowDate,
//                                dbManager);
                        ikensho = convertIkensho(new File(txtIkensho.getText()), nowDate,
                                dbManager);
                        //�G���[�����������ꍇ
                        if(ikensho > 0){
                            errorMessage.append("�厡��ӌ����F�t�@�C���t�H�[�}�b�g�s����" + ikensho + "����荞�݂Ɏ��s���܂����B\n");
                        }
                        //2006/02/11[Shin Fujihara] : add end
                    }
                    //2006/02/11[Shin Fujihara] : add begin
                    if(errorMessage.length() > 0){
                        JOptionPane.showMessageDialog(this, errorMessage.toString());
                    }
                    //2006/02/11[Shin Fujihara] : add end
                    JOptionPane.showMessageDialog(this, "�f�[�^�x�[�X�ڍs�������������܂����B");
                    dbManager = null;
                } else {
                    return;
                }
            }
        } catch (HeadlessException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void btnPatient_actionPerformed(ActionEvent e) {
        FileFilter[] filters = chooser.getChoosableFileFilters();
        for (int i = 0; i < filters.length; i++) {
            chooser.removeChoosableFileFilter(filters[i]);
        }
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new IkenshoMacConvertRegexFileFilter(
                FILTER_CSV, lblPatient.getText() + "�i*.csv�j"));
        chooser.showDialog(this, FILTER_BUTTON_NAME);
        if (chooser.getSelectedFile() != null) {
            txtPatient.setText(chooser.getSelectedFile().getPath());
        }
    }

    public void btnDoctor_actionPerformed(ActionEvent e) {
        FileFilter[] filters = chooser.getChoosableFileFilters();
        for (int i = 0; i < filters.length; i++) {
            chooser.removeChoosableFileFilter(filters[i]);
        }
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new IkenshoMacConvertRegexFileFilter(
                FILTER_CSV, lblDoctor.getText() + "�i*.csv�j"));
        chooser.showDialog(this, FILTER_BUTTON_NAME);
        if (chooser.getSelectedFile() != null) {
            txtDoctor.setText(chooser.getSelectedFile().getPath());
        }
    }

    public void btnRenkeii_actionPerformed(ActionEvent e) {
        FileFilter[] filters = chooser.getChoosableFileFilters();
        for (int i = 0; i < filters.length; i++) {
            chooser.removeChoosableFileFilter(filters[i]);
        }
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new IkenshoMacConvertRegexFileFilter(
                FILTER_CSV, lblRenkeii.getText() + "�i*.csv�j"));
        chooser.showDialog(this, FILTER_BUTTON_NAME);
        if (chooser.getSelectedFile() != null) {
            txtRenkeii.setText(chooser.getSelectedFile().getPath());
        }
    }

    public void btnInsurer_actionPerformed(ActionEvent e) {
        FileFilter[] filters = chooser.getChoosableFileFilters();
        for (int i = 0; i < filters.length; i++) {
            chooser.removeChoosableFileFilter(filters[i]);
        }
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new IkenshoMacConvertRegexFileFilter(
                FILTER_CSV, lblInsurer.getText() + "�i*.csv�j"));
        chooser.showDialog(this, FILTER_BUTTON_NAME);
        if (chooser.getSelectedFile() != null) {
            txtInsurer.setText(chooser.getSelectedFile().getPath());
        }
    }

    public void btnStation_actionPerformed(ActionEvent e) {
        FileFilter[] filters = chooser.getChoosableFileFilters();
        for (int i = 0; i < filters.length; i++) {
            chooser.removeChoosableFileFilter(filters[i]);
        }
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new IkenshoMacConvertRegexFileFilter(
                FILTER_CSV, lblStation.getText() + "�i*.csv�j"));
        chooser.showDialog(this, FILTER_BUTTON_NAME);
        if (chooser.getSelectedFile() != null) {
            txtStation.setText(chooser.getSelectedFile().getPath());
        }
    }
    //add start shin fujihara 2006.2.3
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
    //add end shin fujihara 2006.2.3

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
        IkenshoMacConvert adaptee;

        IkenshoMacConvert_buttonSearch_actionAdapter(IkenshoMacConvert adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnStart_actionPerformed(e);
        }
    }

    private class IkenshoMacConvert_btnPatient_actionAdapter implements
            java.awt.event.ActionListener {
        IkenshoMacConvert adaptee;

        IkenshoMacConvert_btnPatient_actionAdapter(IkenshoMacConvert adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnPatient_actionPerformed(e);
        }
    }

    private class IkenshoMacConvert_btnDoctor_actionAdapter implements
            java.awt.event.ActionListener {
        IkenshoMacConvert adaptee;

        IkenshoMacConvert_btnDoctor_actionAdapter(IkenshoMacConvert adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnDoctor_actionPerformed(e);
        }
    }

    // class IkenshoMacConvert_btnDb_actionAdapter
    // implements java.awt.event.ActionListener {
    // IkenshoMacConvert adaptee;
    //
    // IkenshoMacConvert_btnDb_actionAdapter(IkenshoMacConvert adaptee) {
    // this.adaptee = adaptee;
    // }
    //
    // public void actionPerformed(ActionEvent e) {
    // adaptee.btnDb_actionPerformed(e);
    // }
    // }

    private class IkenshoMacConvert_btnRenkeii_actionAdapter implements
            java.awt.event.ActionListener {
        IkenshoMacConvert adaptee;

        IkenshoMacConvert_btnRenkeii_actionAdapter(IkenshoMacConvert adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnRenkeii_actionPerformed(e);
        }
    }

    private class IkenshoMacConvert_btnInsurer_actionAdapter implements
            java.awt.event.ActionListener {
        IkenshoMacConvert adaptee;

        IkenshoMacConvert_btnInsurer_actionAdapter(IkenshoMacConvert adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnInsurer_actionPerformed(e);
        }
    }

    private class IkenshoMacConvert_btnStation_actionAdapter implements
            java.awt.event.ActionListener {
        IkenshoMacConvert adaptee;

        IkenshoMacConvert_btnStation_actionAdapter(IkenshoMacConvert adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnStation_actionPerformed(e);
        }
    }
    //add start shin fujihara 2006.2.3
    private class IkenshoMacConvert_btnIkensho_actionAdapter implements
            java.awt.event.ActionListener {
        IkenshoMacConvert adaptee;

        IkenshoMacConvert_btnIkensho_actionAdapter(
                IkenshoMacConvert adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnIkensho_actionPerformed(e);
        }
    }
    //add start shin fujihara 2006.2.3
}