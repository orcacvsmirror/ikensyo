package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.io.VRCSVFile;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoOtherCSVOutputProgress extends JDialog {
    private JPanel contentPane = new JPanel();
    private VRPanel client = new VRPanel();
    private JLabel info = new JLabel();
    private JProgressBar pbar = new JProgressBar();
    private VRArrayList dataOrg;
    private File file;

    public IkenshoOtherCSVOutputProgress(VRArrayList dataOrg, File file) {
        super(ACFrame.getInstance(), "CSVファイル出力中", true);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            initComponent();

            this.dataOrg = (VRArrayList) dataOrg.clone();
            this.file = file;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        contentPane = (JPanel) this.getContentPane();
        contentPane.add(client);

        client.setLayout(new VRLayout());
        client.add(info, VRLayout.FLOW_RETURN);
        client.add(pbar, VRLayout.FLOW_RETURN);

        info.setText("1/1 出力");

        pbar.setBounds(10, 10, 160, 20);
        pbar.setMinimum(0);
        pbar.setMaximum(100);
    }

    private void initComponent() {
        // ウィンドウのサイズ
        setSize(new Dimension(200, 70));
        // ウィンドウを中央に配置
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    public void show() {
        try {
            doOutput(this.dataOrg, this.file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void processWindowEvent(WindowEvent e) {
    }

    /**
     * ウィンドウを閉じます。
     */
    protected void closeWindow() {
        // 自身を破棄する
        this.dispose();
    }

    /**
     * CSV出力処理を行います。
     *
     * @param data VRArrayList
     * @param file File
     * @throws Exception
     */
    public void doOutput(VRArrayList data, File file) throws Exception {
        // 出力対象でないデータを間引く
        for (int i = data.getDataSize() - 1; i >= 0; i--) {
            VRMap map = (VRMap) data.getData(i);
            if (String.valueOf(map.getData("OUTPUT_FLG")).equals("false")) {
                data.remove(i);
            }
        }

        // CSV出力用のデータをDBから取得する
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        VRArrayList dataDB = (VRArrayList) dbm.executeQuery(getSql());

        // CSV出力処理(下準備)
        int max = data.getDataSize();
        pbar.setMaximum(max);
        StringBuffer sbBuf = new StringBuffer();
        String formatVersion = IkenshoCommon
                .getProperity("Version/FormatVersion");
        String softName = IkenshoCommon.getProperity("Version/SoftName");
        VRCSVFile cvs = new VRCSVFile(file.getPath()); // CSV生成

        // CSV出力処理
        for (int i = 0; i < max; i++) {
            VRMap tmp = (VRMap) data.getData(i);
            String patientNo = tmp.getData("PATIENT_NO").toString();
            String edaNo = tmp.getData("EDA_NO").toString();
            int idx = matchingData(dataDB, patientNo, edaNo);
            if (idx < 0) {
                continue;
            }
            VRMap map = (VRMap) dataDB.getData(idx);
            String imgFileName = "";

            // 行生成
            ArrayList row = new ArrayList();
            // 001:FormatVersion
            row.add(formatVersion);
            // 002:SoftName
            row.add(softName);
            // 003:タイムスタンプ
            row.add(getDataString(map, "INSURED_NO")
                    + formatDDHHMMSS(map.getData("FD_TIMESTAMP")));
            // 004:保険者番号
            row.add(getDataString(map, "INSURER_NO"));
            // 005:保険者名称
            row.add(getDataString(map, "INSURER_NM"));
            // 006:被保険者番号
            row.add(getDataString(map, "INSURED_NO"));
            // 007:事業所番号
            row.add(getDataString(map, "JIGYOUSHA_NO"));
            // 008:申請日
            row.add(formatYYYYMMDD(map.getData("SINSEI_DT")));
            // 009:作成依頼日
            row.add(formatYYYYMMDD(map.getData("REQ_DT")));
            // 010:送付日
            row.add(formatYYYYMMDD(map.getData("SEND_DT")));
            // 011:依頼番号
            row.add(getDataString(map, "REQ_NO"));
            // 012:医師番号
            row.add(getDataString(map, "DR_NO"));
            // 013:種別
            row.add(getDataString(map, "KIND"));
            // 014:記入日
            row.add(formatYYYYMMDD(map.getData("KINYU_DT")));

            // 015:患者名かな
            row.add(getDataString(map, "PATIENT_KN"));
            // 016:患者名
            row.add(getDataString(map, "PATIENT_NM"));
            // 017:患者・生年月日
            row.add(formatYYYYMMDD(map.getData("BIRTHDAY")));
            // 018:患者・年齢
            row.add(getDataString(map, "AGE"));
            // 019:患者・性別
            row.add(getDataString(map, "SEX"));
            // 020:患者・郵便番号
            row.add(getDataString(map, "POST_CD"));
            // 021:患者・住民
            row.add(getDataString(map, "ADDRESS"));
            // 022:患者・電話番号
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TEL1"));
            if (getDataString(map, "TEL1").length() > 0) {
                if (getDataString(map, "TEL2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "TEL2"));
            row.add(sbBuf.toString());
            // 023:医師氏名
            row.add(getDataString(map, "DR_NM"));
            // 024:医療機関名
            row.add(getDataString(map, "MI_NM"));
            // 025:医療機関・郵便番号
            row.add(getDataString(map, "MI_POST_CD"));
            // 026:医療機関・所在地
            row.add(getDataString(map, "MI_ADDRESS"));
            // 027:医療機関・連絡先(TEL)
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MI_TEL1"));
            if (getDataString(map, "MI_TEL1").length() > 0) {
                if (getDataString(map, "MI_TEL2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "MI_TEL2"));
            row.add(sbBuf.toString());
            // 028:医療機関・連絡先(FAX)
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MI_FAX1"));
            if (getDataString(map, "MI_FAX1").length() > 0) {
                if (getDataString(map, "MI_FAX2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "MI_FAX2"));
            row.add(sbBuf.toString());
            // 029:医師同意
            row.add(getDataString(map, "DR_CONSENT"));
            // 030:最終診察日
            row.add(formatYYYYMMDD(map.getData("LASTDAY")));
            // 031:作成回数
            row.add(getDataString(map, "IKN_CREATE_CNT"));
            // 032:他科受診の有無
            if (map.getData("TAKA").toString().equals("0")) {
                row.add("1");
            } else {
                row.add("2");
            }
            // 033:他科受診・受診項目
            row.add(getBitFromRonriwa(getDataString(map, "TAKA"), 13));
            // 034:他科受診・受診項目・その他内容
            row.add(getDataString(map, "TAKA_OTHER"));

            // 035:診断名1
            row.add(getDataString(map, "SINDAN_NM1"));
            // 036:発症年月日1
            row.add(formatUnknownDate(map.getData("HASHOU_DT1")));
            // 037:診断名2
            row.add(getDataString(map, "SINDAN_NM2"));
            // 038:発症年月日2
            row.add(formatUnknownDate(map.getData("HASHOU_DT2")));
            // 039:診断名3
            row.add(getDataString(map, "SINDAN_NM3"));
            // 040:発症年月日3
            row.add(formatUnknownDate(map.getData("HASHOU_DT3")));
            // 041:症状としての安定性
            row.add(getDataString(map, "SHJ_ANT"));
            // 042:介護の必要の程度に関する予後の見通し
            row.add(getDataString(map, "YKG_YOGO"));
            // 043:傷病の経過・治療内容
            sbBuf = new StringBuffer();
            if (getDataString(map, "MI_STS").length() > 0) {
                sbBuf
                        .append(getDataString(map, "MI_STS").replaceAll("\n",
                                "")); // 0x0B
                sbBuf.append("");
            }
            if ((getDataString(map, "MEDICINE1").length()
                    + getDataString(map, "DOSAGE1").length()
                    + getDataString(map, "UNIT1").length()
                    + getDataString(map, "USAGE1").length()
                    + getDataString(map, "MEDICINE2").length()
                    + getDataString(map, "DOSAGE2").length()
                    + getDataString(map, "UNIT2").length() + getDataString(map,
                    "USAGE2").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE1") + " ");
                sbBuf.append(getDataString(map, "DOSAGE1"));
                sbBuf.append(getDataString(map, "UNIT1") + " ");
                sbBuf.append(getDataString(map, "USAGE1") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE2") + " ");
                sbBuf.append(getDataString(map, "DOSAGE2"));
                sbBuf.append(getDataString(map, "UNIT2") + " ");
                sbBuf.append(getDataString(map, "USAGE2") + "");
            }
            if ((getDataString(map, "MEDICINE3").length()
                    + getDataString(map, "DOSAGE3").length()
                    + getDataString(map, "UNIT3").length()
                    + getDataString(map, "USAGE3").length()
                    + getDataString(map, "MEDICINE4").length()
                    + getDataString(map, "DOSAGE4").length()
                    + getDataString(map, "UNIT4").length() + getDataString(map,
                    "USAGE4").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE3") + " ");
                sbBuf.append(getDataString(map, "DOSAGE3"));
                sbBuf.append(getDataString(map, "UNIT3") + " ");
                sbBuf.append(getDataString(map, "USAGE3") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE4") + " ");
                sbBuf.append(getDataString(map, "DOSAGE4"));
                sbBuf.append(getDataString(map, "UNIT4") + " ");
                sbBuf.append(getDataString(map, "USAGE4") + "");
            }
            if ((getDataString(map, "MEDICINE5").length()
                    + getDataString(map, "DOSAGE5").length()
                    + getDataString(map, "UNIT5").length()
                    + getDataString(map, "USAGE5").length()
                    + getDataString(map, "MEDICINE6").length()
                    + getDataString(map, "DOSAGE6").length()
                    + getDataString(map, "UNIT6").length() + getDataString(map,
                    "USAGE6").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE5") + " ");
                sbBuf.append(getDataString(map, "DOSAGE5"));
                sbBuf.append(getDataString(map, "UNIT5") + " ");
                sbBuf.append(getDataString(map, "USAGE5") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE6") + " ");
                sbBuf.append(getDataString(map, "DOSAGE6"));
                sbBuf.append(getDataString(map, "UNIT6") + " ");
                sbBuf.append(getDataString(map, "USAGE6") + "");
            }
            if (sbBuf.length() > 0) {
                sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // 最後の0x0Bを削除
            }
            row.add(sbBuf.toString());

            // 044:処置内容
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TNT_KNR"));
            sbBuf.append(getDataString(map, "CHU_JOU_EIYOU"));
            sbBuf.append(getDataString(map, "TOUSEKI"));
            sbBuf.append(getDataString(map, "JINKOU_KOUMON"));
            sbBuf.append(getDataString(map, "OX_RYO"));
            sbBuf.append(getDataString(map, "JINKOU_KOKYU"));
            sbBuf.append(getDataString(map, "KKN_SEK_SHOCHI"));
            sbBuf.append(getDataString(map, "SIS_KEKN_EIYOU"));
            row.add(sbBuf.toString());
            // 045:特別な措置
            row.add(getDataString(map, "MONITOR")
                    + getDataString(map, "JOKUSOU"));
            // 046:失禁への対応
            row.add(getDataString(map, "RYU_CATHETER"));

            // 047:障害老人の日常生活自立度(寝たきり度)
            row.add(getDataString(map, "NETAKIRI"));
            // 048:痴呆性老人の日常生活自立度
            row.add(getDataString(map, "CHH_STS"));
            // 049:短期記憶
            row.add(getDataString(map, "TANKI_KIOKU"));
            // 050:日常の意思決定を行うための認知能力
            row.add(getDataString(map, "NINCHI"));
            // 051:自分の意思の伝達能力
            row.add(getDataString(map, "DENTATU"));
            // 052:食事
            row.add(getDataString(map, "SHOKUJI"));
            // 053:問題行動有無
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "GNS_GNC"));
            sbBuf.append(getDataString(map, "MOUSOU"));
            sbBuf.append(getDataString(map, "CHUYA"));
            sbBuf.append(getDataString(map, "BOUGEN"));
            sbBuf.append(getDataString(map, "BOUKOU"));
            sbBuf.append(getDataString(map, "TEIKOU"));
            sbBuf.append(getDataString(map, "HAIKAI"));
            sbBuf.append(getDataString(map, "FUSIMATU"));
            sbBuf.append(getDataString(map, "FUKETU"));
            sbBuf.append(getDataString(map, "ISHOKU"));
            sbBuf.append(getDataString(map, "SEITEKI_MONDAI"));
            if (sbBuf.toString().equals("00000000000")) {
                row.add("2");
            } else {
                row.add("1");
            }
            // 054:問題行動・有の場合
            row.add(sbBuf.toString());
            // 055:問題行動その他
            row.add(getDataString(map, "MONDAI_OTHER_NM"));
            // 056:精神神経症状・有無
            row.add(getDataString(map, "SEISIN"));
            // 057:精神神経症状・症状名
            row.add(getDataString(map, "SEISIN_NM"));
            // 058:専門医受診・有無
            row.add(getDataString(map, "SENMONI"));
            // 059:専門医受診・詳細
            row.add(getDataString(map, "SENMONI_NM"));
            // 060:利き腕
            row.add(getDataString(map, "KIKIUDE"));
            // 061:体重
            row.add(getDataString(map, "WEIGHT"));
            // 062:身長
            row.add(getDataString(map, "HEIGHT"));
            // 063:四肢欠損
            row.add(getDataString(map, "SISIKESSON"));
            // 064:四肢欠損部位
            row.add(getDataString(map, "SISIKESSON_BUI"));
            // 065:四肢欠損程度
            row.add(getDataString(map, "SISIKESSON_TEIDO"));
            // 066:麻痺
            row.add(getDataString(map, "MAHI"));
            // 067:麻痺部位
            row.add(getDataString(map, "MAHI_BUI"));
            // 068:麻痺程度
            row.add(getDataString(map, "MAHI_TEIDO"));
            // 069:筋力低下
            row.add(getDataString(map, "KINRYOKU_TEIKA"));
            // 070:筋力低下部位
            row.add(getDataString(map, "KINRYOKU_TEIKA_BUI"));
            // 071:筋力低下程度
            row.add(getDataString(map, "KINRYOKU_TEIKA_TEIDO"));
            // 072:褥瘡
            row.add(getDataString(map, "JOKUSOU"));
            // 073:褥瘡部位
            row.add(getDataString(map, "JOKUSOU_BUI"));
            // 074:褥瘡程度
            row.add(getDataString(map, "JOKUSOU_TEIDO"));
            // 075:皮膚疾患
            row.add(getDataString(map, "HIFUSIKKAN"));
            // 076:皮膚疾患部位
            row.add(getDataString(map, "HIFUSIKKAN_BUI"));
            // 077:皮膚疾患程度
            row.add(getDataString(map, "HIFUSIKKAN_TEIDO"));
            // 078:関節の拘縮
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "KATA_KOUSHU_HIDARI"));
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_HIDARI"));
            sbBuf.append(getDataString(map, "MATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "MATA_KOUSHU_HIDARI"));
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_HIDARI"));
            if (sbBuf.toString().equals("00000000")) {
                row.add("0");
            } else {
                row.add("1");
            }
            // 079:肩関節
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "KATA_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 080:肘関節
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 081:股関節
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "MATA_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 082:肩関節
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 083:失調・不随意運動
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
            if (sbBuf.toString().equals("000000")) {
                row.add("0");
            } else {
                row.add("1");
            }
            // 084:上肢
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 085:体幹
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 086:下肢
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 087:全身図
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "INSURED_NO"));
            sbBuf.append(formatYYYYMMDD(map.getData("KINYU_DT")));
            imgFileName = sbBuf.toString();
            row.add(sbBuf.toString());

            // 088:現在、発生の可能性が高い病態
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "NYOUSIKKIN"));
            sbBuf.append(getDataString(map, "TENTOU_KOSSETU"));
            sbBuf.append(getDataString(map, "HAIKAI_KANOUSEI"));
            sbBuf.append(getDataString(map, "JOKUSOU_KANOUSEI"));
            sbBuf.append(getDataString(map, "ENGESEIHAIEN"));
            sbBuf.append(getDataString(map, "CHOUHEISOKU"));
            sbBuf.append(getDataString(map, "EKIKANKANSEN"));
            sbBuf.append(getDataString(map, "SINPAIKINOUTEIKA"));
            sbBuf.append(getDataString(map, "ITAMI"));
            sbBuf.append(getDataString(map, "DASSUI"));
            sbBuf.append(getDataString(map, "BYOUTAITA"));
            row.add(sbBuf.toString());
            // 089:現在、発生の可能性が高い病態・その他
            row.add(getDataString(map, "BYOUTAITA_NM"));
            // 090:発生の可能性が高い病態の対処方針
            sbBuf = new StringBuffer();
            if (getDataString(map, "TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "TENTOU_KOSSETU_TAISHO_HOUSIN").length() > 0) {
                sbBuf
                        .append(getDataString(map,
                                "TENTOU_KOSSETU_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "HAIKAI_KANOUSEI_TAISHO_HOUSIN").length() > 0) {
                sbBuf
                        .append(getDataString(map,
                                "HAIKAI_KANOUSEI_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "JOKUSOU_KANOUSEI_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map,
                        "JOKUSOU_KANOUSEI_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "ENGESEIHAIEN_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "ENGESEIHAIEN_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "CHOUHEISOKU_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "CHOUHEISOKU_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "EKIKANKANSEN_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "EKIKANKANSEN_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "SINPAIKINOUTEIKA_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map,
                        "SINPAIKINOUTEIKA_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (sbBuf.length() > 0) {
                sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // 最後の"、"を削除
                sbBuf.append("。"); // "。"を追加する
            }
            row.add(sbBuf.toString());

            // 091:医学的管理の必要性
            sbBuf = new StringBuffer();
            sbBuf
                    .append(getUnderlinedCheckValue(getDataString(map,
                            "HOUMON_SINRYOU"), getDataString(map,
                            "HOUMON_SINRYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMON_KANGO"), getDataString(map, "HOUMON_KANGO_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMON_REHA"), getDataString(map, "HOUMON_REHA_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "TUUSHO_REHA"), getDataString(map, "TUUSHO_REHA_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "TANKI_NYUSHO_RYOUYOU"), getDataString(map,
                    "TANKI_NYUSHO_RYOUYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONSIKA_SINRYOU"), getDataString(map,
                    "HOUMONSIKA_SINRYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONSIKA_EISEISIDOU"), getDataString(map,
                    "HOUMONSIKA_EISEISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONYAKUZAI_KANRISIDOU"), getDataString(map,
                    "HOUMONYAKUZAI_KANRISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONEIYOU_SHOKUJISIDOU"), getDataString(map,
                    "HOUMONEIYOU_SHOKUJISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "IGAKUTEKIKANRI_OTHER"), getDataString(map,
                    "IGAKUTEKIKANRI_OTHER_UL")));
            row.add(sbBuf.toString());
            // 092:医学的管理の必要性・その他
            row.add(getDataString(map, "IGAKUTEKIKANRI_OTHER_NM"));
            // 093:血圧について
            row.add(getDataString(map, "KETUATU"));
            // 094:血圧について・留意事項
            row.add(getDataString(map, "KETUATU_RYUIJIKOU"));
            // 095:嚥下について
            row.add(getDataString(map, "ENGE"));
            // 096:嚥下について・留意事項
            row.add(getDataString(map, "ENGE_RYUIJIKOU"));
            // 097:摂食について
            row.add(getDataString(map, "SESHOKU"));
            // 098:摂食について・留意事項
            row.add(getDataString(map, "SESHOKU_RYUIJIKOU"));
            // 099:移動について
            row.add(getDataString(map, "IDOU"));
            // 100:移動について・留意事項
            row.add(getDataString(map, "IDOU_RYUIJIKOU"));
            // 101:その他
            row.add(getDataString(map, "KAIGO_OTHER"));
            // 102:感染症の有無
            row.add(getDataString(map, "KANSENSHOU"));
            // 103:感染症の有無・詳細
            row.add(getDataString(map, "KANSENSHOU_NM"));
            // 104:その他特記すべき事項
            sbBuf = new StringBuffer();
            if (getDataString(map, "HASE_SCORE").length() > 0) {
                sbBuf.append("長谷川式 = ");
                sbBuf.append(getDataString(map, "HASE_SCORE"));
                sbBuf.append("点 (");
                sbBuf.append(getDataString(map, "HASE_SCR_DT"));
                sbBuf.append(")");
            }
            if (getDataString(map, "P_HASE_SCORE").length() > 0) {
                sbBuf.append(" (前回 ");
                sbBuf.append(getDataString(map, "P_HASE_SCORE"));
                sbBuf.append("点 (");
                sbBuf.append(getDataString(map, "P_HASE_SCR_DT"));
                sbBuf.append("))");
            }
            String hase = sbBuf.toString();
            if (hase.length() > 0) {
                hase += "";
            }
            sbBuf = new StringBuffer();
            if (getDataString(map, "INST_SEL_PR1").length() > 0) {
                sbBuf.append("施設選択(優先度) 1. ");
                String tmpPr = getDataString(map, "INST_SEL_PR1");
                String blank = "                ";
                sbBuf.append(tmpPr + blank.substring(0, 16 - tmpPr.length())); // 16文字にする
            }
            if (getDataString(map, "INST_SEL_PR2").length() > 0) {
                sbBuf.append("2. ");
                String tmpPr = getDataString(map, "INST_SEL_PR2");
                String blank = "                ";
                sbBuf.append(tmpPr + blank.substring(0, 15 - tmpPr.length())); // 15文字にする
            }
            String pr = sbBuf.toString();
            if (pr.length() > 0) {
                pr += "";
            }
            row.add(hase + pr
                    + getDataString(map, "IKN_TOKKI").replaceAll("\n", ""));

            // 1レコード追加
            cvs.addRow(row);

            // 画像出力
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" BODY_FIGURE");
            sb.append(" FROM");
            sb.append(" IKN_ORIGIN");
            sb.append(" WHERE");

            //2005-12-24 edit sta fujihara shin シングルクォーテーション削除
//            sb.append(" (IKN_ORIGIN.PATIENT_NO='");
//            sb.append(patientNo);
//            sb.append("')");
//            sb.append(" AND (IKN_ORIGIN.EDA_NO='");
//            sb.append(edaNo);
//            sb.append("')");
            sb.append(" (IKN_ORIGIN.PATIENT_NO=");
            sb.append(patientNo);
            sb.append(")");
            sb.append(" AND (IKN_ORIGIN.EDA_NO=");
            sb.append(edaNo);
            sb.append(")");
            //2005-12-24 edit end

            VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
            if (array.getDataSize() > 0) {
                // 人体図取得
                VRMap imgMap = (VRMap) array.getData();
                byte[] image = (byte[]) imgMap.getData("BODY_FIGURE");
                File imgFileJpg = new File(file.getParent()
                        + IkenshoConstants.LINE_SEPARATOR + imgFileName
                        + ".jpg");
                // File imgFileBmp = new File(file.getParent() + "\\" +
                // imgFileName + ".png");
                // jpg出力
                FileOutputStream input = new FileOutputStream(imgFileJpg);
                input.write(image);
                input.close();
                // ImageIO.write(ImageIO.read(imgFileJpg), "png", imgFileBmp);
            }

            // プログレスバー進行処理
            pbar.setValue(i);
            info.setText(i + "/" + max + " 出力");
            pbar.paintImmediately(pbar.getVisibleRect());
        }

        // CSV出力
        try {
            // 書き出し
            cvs.write(true, true);
        } catch (Exception ex) {
        }

        closeWindow();
    }

    /**
     * CSV出力に必要なデータを取得するSQL文を取得します。
     *
     * @return String
     */
    private String getSql() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" IKN_ORIGIN.PATIENT_NO");
        sb.append(",IKN_ORIGIN.EDA_NO");
        sb.append(",IKN_ORIGIN.INSURED_NO");
        sb.append(",IKN_BILL.FD_TIMESTAMP");
        sb.append(",IKN_ORIGIN.INSURER_NO");
        sb.append(",IKN_ORIGIN.INSURER_NM");
        sb.append(",IKN_BILL.JIGYOUSHA_NO");
        sb.append(",IKN_BILL.SINSEI_DT");
        sb.append(",IKN_ORIGIN.REQ_DT");
        sb.append(",IKN_ORIGIN.SEND_DT");
        sb.append(",IKN_ORIGIN.REQ_NO");
        sb.append(",IKN_BILL.DR_NO");
        sb.append(",IKN_ORIGIN.KIND");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",COMMON_IKN_SIS.PATIENT_KN");
        sb.append(",COMMON_IKN_SIS.PATIENT_NM");
        sb.append(",COMMON_IKN_SIS.BIRTHDAY");
        sb.append(",COMMON_IKN_SIS.AGE");
        sb.append(",COMMON_IKN_SIS.SEX");
        sb.append(",COMMON_IKN_SIS.POST_CD");
        sb.append(",COMMON_IKN_SIS.ADDRESS");
        sb.append(",COMMON_IKN_SIS.TEL1");
        sb.append(",COMMON_IKN_SIS.TEL2");
        sb.append(",COMMON_IKN_SIS.DR_NM");
        sb.append(",COMMON_IKN_SIS.MI_NM");
        sb.append(",COMMON_IKN_SIS.MI_POST_CD");
        sb.append(",COMMON_IKN_SIS.MI_ADDRESS");
        sb.append(",COMMON_IKN_SIS.MI_TEL1");
        sb.append(",COMMON_IKN_SIS.MI_TEL2");
        sb.append(",COMMON_IKN_SIS.MI_FAX1");
        sb.append(",COMMON_IKN_SIS.MI_FAX2");
        sb.append(",IKN_ORIGIN.DR_CONSENT");
        sb.append(",IKN_ORIGIN.LASTDAY");
        sb.append(",IKN_ORIGIN.IKN_CREATE_CNT");
        sb.append(",IKN_ORIGIN.TAKA");
        sb.append(",IKN_ORIGIN.TAKA_OTHER");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM1");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT1");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM2");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT2");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM3");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT3");
        sb.append(",COMMON_IKN_SIS.SHJ_ANT");
        sb.append(",COMMON_IKN_SIS.YKG_YOGO");
        sb.append(",COMMON_IKN_SIS.MT_STS");
        sb.append(",COMMON_IKN_SIS.MEDICINE1");
        sb.append(",COMMON_IKN_SIS.DOSAGE1");
        sb.append(",COMMON_IKN_SIS.UNIT1");
        sb.append(",COMMON_IKN_SIS.USAGE1");
        sb.append(",COMMON_IKN_SIS.MEDICINE2");
        sb.append(",COMMON_IKN_SIS.DOSAGE2");
        sb.append(",COMMON_IKN_SIS.UNIT2");
        sb.append(",COMMON_IKN_SIS.USAGE2");
        sb.append(",COMMON_IKN_SIS.MEDICINE3");
        sb.append(",COMMON_IKN_SIS.DOSAGE3");
        sb.append(",COMMON_IKN_SIS.UNIT3");
        sb.append(",COMMON_IKN_SIS.USAGE3");
        sb.append(",COMMON_IKN_SIS.MEDICINE4");
        sb.append(",COMMON_IKN_SIS.DOSAGE4");
        sb.append(",COMMON_IKN_SIS.UNIT4");
        sb.append(",COMMON_IKN_SIS.USAGE4");
        sb.append(",COMMON_IKN_SIS.MEDICINE5");
        sb.append(",COMMON_IKN_SIS.DOSAGE5");
        sb.append(",COMMON_IKN_SIS.UNIT5");
        sb.append(",COMMON_IKN_SIS.USAGE5");
        sb.append(",COMMON_IKN_SIS.MEDICINE6");
        sb.append(",COMMON_IKN_SIS.DOSAGE6");
        sb.append(",COMMON_IKN_SIS.UNIT6");
        sb.append(",COMMON_IKN_SIS.USAGE6");
        sb.append(",COMMON_IKN_SIS.TNT_KNR");
        sb.append(",COMMON_IKN_SIS.CHU_JOU_EIYOU");
        sb.append(",COMMON_IKN_SIS.TOUSEKI");
        sb.append(",COMMON_IKN_SIS.JINKOU_KOUMON");
        sb.append(",COMMON_IKN_SIS.OX_RYO");
        sb.append(",COMMON_IKN_SIS.JINKOU_KOKYU");
        sb.append(",COMMON_IKN_SIS.KKN_SEK_SHOCHI");
        sb.append(",COMMON_IKN_SIS.KEKN_EIYOU");
        sb.append(",COMMON_IKN_SIS.MONITOR");
        sb.append(",COMMON_IKN_SIS.JOKUSOU");
        sb.append(",COMMON_IKN_SIS.RYU_CATHETER");
        sb.append(",COMMON_IKN_SIS.NETAKIRI");
        sb.append(",COMMON_IKN_SIS.CHH_STS");
        sb.append(",IKN_ORIGIN.TANKI_KIOKU");
        sb.append(",IKN_ORIGIN.NINCHI");
        sb.append(",IKN_ORIGIN.DENTATU");
        sb.append(",IKN_ORIGIN.SHOKUJI");
        sb.append(",IKN_ORIGIN.GNS_GNC");
        sb.append(",IKN_ORIGIN.MOUSOU");
        sb.append(",IKN_ORIGIN.CHUYA");
        sb.append(",IKN_ORIGIN.BOUGEN");
        sb.append(",IKN_ORIGIN.BOUKOU");
        sb.append(",IKN_ORIGIN.TEIKOU");
        sb.append(",IKN_ORIGIN.HAIKAI");
        sb.append(",IKN_ORIGIN.FUSIMATU");
        sb.append(",IKN_ORIGIN.FUKETU");
        sb.append(",IKN_ORIGIN.ISHOKU");
        sb.append(",IKN_ORIGIN.SEITEKI_MONDAI");
        sb.append(",IKN_ORIGIN.GNS_GNC");
        sb.append(",IKN_ORIGIN.MOUSOU");
        sb.append(",IKN_ORIGIN.CHUYA");
        sb.append(",IKN_ORIGIN.BOUGEN");
        sb.append(",IKN_ORIGIN.BOUKOU");
        sb.append(",IKN_ORIGIN.TEIKOU");
        sb.append(",IKN_ORIGIN.HAIKAI");
        sb.append(",IKN_ORIGIN.FUSIMATU");
        sb.append(",IKN_ORIGIN.FUKETU");
        sb.append(",IKN_ORIGIN.ISHOKU");
        sb.append(",IKN_ORIGIN.SEITEKI_MONDAI");
        sb.append(",IKN_ORIGIN.MONDAI_OTHER_NM");
        sb.append(",IKN_ORIGIN.SEISIN");
        sb.append(",IKN_ORIGIN.SEISIN_NM");
        sb.append(",IKN_ORIGIN.SENMONI");
        sb.append(",IKN_ORIGIN.SENMONI_NM");
        sb.append(",IKN_ORIGIN.KIKIUDE");
        sb.append(",IKN_ORIGIN.WEIGHT");
        sb.append(",IKN_ORIGIN.HEIGHT");
        sb.append(",IKN_ORIGIN.SISIKESSON");
        sb.append(",IKN_ORIGIN.SISIKESSON_BUI");
        sb.append(",IKN_ORIGIN.SISIKESSON_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI");
        sb.append(",IKN_ORIGIN.MAHI_BUI");
        sb.append(",IKN_ORIGIN.MAHI_TEIDO");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA_BUI");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA_TEIDO");
        sb.append(",IKN_ORIGIN.JOKUSOU");
        sb.append(",IKN_ORIGIN.JOKUSOU_BUI");
        sb.append(",IKN_ORIGIN.JOKUSOU_TEIDO");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN_BUI");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN_TEIDO");
        sb.append(",IKN_ORIGIN.KATA_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.KATA_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.HIJI_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.HIJI_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.MATA_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.MATA_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.HIZA_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.HIZA_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.JOUSI_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.JOUSI_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.TAIKAN_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.TAIKAN_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.KASI_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.KASI_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",IKN_ORIGIN.NYOUSIKKIN");
        sb.append(",IKN_ORIGIN.TENTOU_KOSSETU");
        sb.append(",IKN_ORIGIN.HAIKAI_KANOUSEI");
        sb.append(",IKN_ORIGIN.JOKUSOU_KANOUSEI");
        sb.append(",IKN_ORIGIN.ENGESEIHAIEN");
        sb.append(",IKN_ORIGIN.CHOUHEISOKU");
        sb.append(",IKN_ORIGIN.EKIKANKANSEN");
        sb.append(",IKN_ORIGIN.SINPAIKINOUTEIKA");
        sb.append(",IKN_ORIGIN.ITAMI");
        sb.append(",IKN_ORIGIN.DASSUI");
        sb.append(",IKN_ORIGIN.BYOUTAITA");
        sb.append(",IKN_ORIGIN.BYOUTAITA_NM");
        sb.append(",IKN_ORIGIN.NYOUSIKKIN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.TENTOU_KOSSETU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.HAIKAI_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.ENGESEIHAIEN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.CHOUHEISOKU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.EKIKANKANSEN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.SINPAIKINOUTEIKA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.ITAMI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.DASSUI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.BYOUTAITA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.HOUMON_SINRYOU");
        sb.append(",IKN_ORIGIN.HOUMON_SINRYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMON_KANGO");
        sb.append(",IKN_ORIGIN.HOUMON_KANGO_UL");
        sb.append(",IKN_ORIGIN.HOUMON_REHA");
        sb.append(",IKN_ORIGIN.HOUMON_REHA_UL");
        sb.append(",IKN_ORIGIN.TUUSHO_REHA");
        sb.append(",IKN_ORIGIN.TUUSHO_REHA_UL");
        sb.append(",IKN_ORIGIN.TANKI_NYUSHO_RYOUYOU");
        sb.append(",IKN_ORIGIN.TANKI_NYUSHO_RYOUYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_SINRYOU");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_SINRYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_EISEISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_EISEISIDOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONYAKUZAI_KANRISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONYAKUZAI_KANRISIDOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONEIYOU_SHOKUJISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONEIYOU_SHOKUJISIDOU_UL");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER_UL");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER_NM");
        sb.append(",IKN_ORIGIN.KETUATU");
        sb.append(",IKN_ORIGIN.KETUATU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.SESHOKU");
        sb.append(",IKN_ORIGIN.SESHOKU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.ENGE");
        sb.append(",IKN_ORIGIN.ENGE_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.IDOU");
        sb.append(",IKN_ORIGIN.IDOU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.KAIGO_OTHER");
        sb.append(",IKN_ORIGIN.KANSENSHOU");
        sb.append(",IKN_ORIGIN.KANSENSHOU_NM");
        sb.append(",IKN_ORIGIN.HASE_SCORE");
        sb.append(",IKN_ORIGIN.HASE_SCR_DT");
        sb.append(",IKN_ORIGIN.P_HASE_SCORE");
        sb.append(",IKN_ORIGIN.P_HASE_SCR_DT");
        sb.append(",IKN_ORIGIN.INST_SEL_PR1");
        sb.append(",IKN_ORIGIN.INST_SEL_PR2");
        sb.append(",IKN_ORIGIN.IKN_TOKKI");
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,IKN_ORIGIN,IKN_BILL");
        sb.append(" WHERE");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_ORIGIN.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_BILL.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_ORIGIN.EDA_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_BILL.EDA_NO");
        return sb.toString();
    }

    /**
     * dataDB中で、条件に合うレコードが何番目にあるのかを取得します。
     *
     * @param dataDB VRArrayList
     * @param patientNo String
     * @param edaNo String
     * @return int
     */
    private int matchingData(VRArrayList dataDB, String patientNo, String edaNo) {
        for (int i = 0; i < dataDB.getDataSize(); i++) {
            VRMap map = (VRMap) dataDB.getData(i);
            if (String.valueOf(map.getData("PATIENT_NO")).equals(patientNo)) {
                if (String.valueOf(map.getData("EDA_NO")).equals(edaNo)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * VRHashMapのデータを文字列で取得します。
     *
     * @param map VRHashMap
     * @param key String
     * @return String
     */
    private String getDataString(VRMap map, String key) {
        if (map.getData(key) == null) {
            return "";
        }
        if (map.getData(key).equals("null")) {
            return "";
        }
        if (String.valueOf(map.getData(key)).equals("null")) {
            return "";
        }
        return String.valueOf(map.getData(key));
    }

    /**
     * 論理輪をビット列に変換します。
     *
     * @param ronriwa 論理輪
     * @param digit 桁数
     * @return ビット列(左から右へ)
     */
    private String getBitFromRonriwa(String ronriwa, int digit) {
        String bit = "";
        int wa = Integer.parseInt(ronriwa);
        for (int i = 0; i < digit; i++) {
            if ((wa % 2) == 1) {
                bit = "1" + bit;
            } else {
                bit = "0" + bit;
            }
            wa /= 2;
        }

        return bit;
    }

    /**
     * チェック有無、下線有無の2項目を結合し、0:チェック無、1:チェックのみ、2:チェック＋下線に変換します。
     *
     * @param check チェック有無("0", "1")
     * @param underline 下線有無("0", "1")
     * @return "0":チェック無、"1":チェックのみ、"2":チェック＋下線
     */
    private String getUnderlinedCheckValue(String check, String underline) {
        if (check.equals("1")) {
            if (underline.equals("1")) {
                return "2";
            } else {
                return "1";
            }
        } else {
            return "0";
        }
    }

    /**
     * yyyyMMdd形式の文字列に変換します。
     *
     * @param obj Object
     * @return String
     */
    private String formatYYYYMMDD(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof Date) {
            try {
                return VRDateParser.format((Date) obj, "yyyyMMdd");
            } catch (Exception ex) {

            }
        }

        return "";
    }

    /**
     * ddHHmmss形式の文字列に変換します。
     *
     * @param obj Object
     * @return String
     */
    private String formatDDHHMMSS(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof Date) {
            try {
                return VRDateParser.format((Date) obj, "ddHHmmss");
            } catch (Exception ex) {

            }
        }

        return "";
    }

    /**
     * 「不詳」付き不完全日付をCSV出力用形式に変換します。
     *
     * @param obj Object
     * @return String
     */
    private String formatUnknownDate(Object obj) {
        // 文字列に変換
        if (obj.equals("null")) {
            return "";
        }
        if (obj.equals("null")) {
            return "";
        }
        String date = String.valueOf(obj);

        // 判定
        if (date.substring(0, 2).equals("00")) { // 空
            return "";
        }
        if (date.substring(0, 4).equals("0000")) { // 空
            return "";
        }
        if (date.substring(0, 2).equals("不詳")) { // 不詳
            return "不詳";
        }
        if (date.substring(5, 7).equals("00")) { // 年まで
            return date.substring(0, 5);
        }
        if (date.substring(8, 10).equals("00")) { // 月まで
            return date.substring(0, 8);
        }
        return date;
    }
}
