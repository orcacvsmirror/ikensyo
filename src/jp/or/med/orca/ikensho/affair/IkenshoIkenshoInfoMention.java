package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Map;

import javax.swing.SwingConstants;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACBindListCellRenderer;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerTextField;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextArea;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.text.ACOneDecimalDoubleFormat;
import jp.nichicom.ac.text.ACSQLSafeNullToZeroIntegerFormat;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoInitialNegativeIntegerTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoMention extends
        IkenshoTabbableChildAffairContainer {
    private ACTextField mentionHasegawa1 = new ACTextField();
    private ACLabelContainer mentionCreateDates = new ACLabelContainer();
    private IkenshoEraDateTextField mentionShinseiDate = new IkenshoEraDateTextField();
    private IkenshoEraDateTextField mentionHasegawaDay1 = new IkenshoEraDateTextField();
    private ACTextField mentionHokenNo = new ACTextField();
    private VRLayout mentionLayout = new VRLayout();
    private VRLayout mentionSeikyushoGroupLayout = new VRLayout();
    // private JScrollPane mentionTokkiScroll = new JScrollPane();
    private IkenshoEraDateTextField mentionHasegawaDay2 = new IkenshoEraDateTextField();
    private VRPanel mentionTokkiMoreAbstractions;
    private ACTextField mentionHiHokenNo = new ACTextField();
    private ACLabelContainer mentionHasegawas;
    private ACGroupBox mentionSeikyushoGroup;
    private ACComboBox mentionShisetsu2 = new ACComboBox();
    private ACLabelContainer mentionShisetsus;
    private VRLabel mentionShisetsu2Head = new VRLabel();
    // private VRLabel mentionTokkiAbstraction2 = new VRLabel();
    private ACComboBox mentionHokenName = new ACComboBox();
    // private IkenshoDeselectableComboBox mentionHokenName = new
    // IkenshoDeselectableComboBox();
    private ACTextField mentionOrderNo = new ACTextField();
    private ACComboBox mentionShisetsu1 = new ACComboBox();
    private ACGroupBox mentionTokkiGroup;
    private ACTextField mentionHasegawa2 = new ACTextField();
    private ACLabelContainer mentionSendDates = new ACLabelContainer();
    private VRLabel mentionHasegawa1Tail = new VRLabel();
    private IkenshoEraDateTextField mentionCreateDate = new IkenshoEraDateTextField();
    private ACTextArea mentionTokki = new ACTextArea();
    private ACButton mentionAddHokensha = new ACButton();
    private VRLabel mentionHasegawa2Tail = new VRLabel();
    private ACLabelContainer mentionShinseiDates = new ACLabelContainer();
    private ACButton mentionShowTokkiMoreAbstractionChooser = new ACButton();
    private VRLabel mentionTokkiMoreAbstraction;
    private VRLabel mentionShisetsu1Head = new VRLabel();
    private ACLabelContainer mentionTokkis;
    private ACClearableRadioButtonGroup mentionCareType = new ACClearableRadioButtonGroup();
    private ACLabelContainer mentionOrderNos = new ACLabelContainer();
    private IkenshoEraDateTextField mentionSendDate = new IkenshoEraDateTextField();
    private ACLabelContainer mentionHiHokenNos = new ACLabelContainer();
    private IkenshoDocumentTabTitleLabel mentionTitle = new IkenshoDocumentTabTitleLabel();
    private ACButton mentionAddKensa = new ACButton();
    private ACLabelContainer mentionHokenNames = new ACLabelContainer();
    private VRLayout mentionTokkiGroupLayout = new VRLayout();
    private ACLabelContainer mentionCareTypes = new ACLabelContainer();
    private ACParentHesesPanelContainer mentionHokenNoHeses = new ACParentHesesPanelContainer();
    private VRLayout mentionShisetsusLayout = new VRLayout();
    private VRPanel mentionTokkiAbstractions;
    private ACLabel mentionTokkiAbstraction1 = new ACLabel();
    private VRLabel mentionTokkiAbstraction3 = new VRLabel();
    private VRPanel mentionDates = new VRPanel();
    private VRPanel mentionInsurers = new VRPanel();
    private VRLayout mentionInsurersLayout = new VRLayout();
    private ACLabelContainer mentionHasegawaDays1 = new ACLabelContainer();
    private ACLabelContainer mentionHasegawaDays2 = new ACLabelContainer();
    private ACLabelContainer mentionHasegawas2 = new ACLabelContainer();
    private ACParentHesesPanelContainer mentionHasegawaDayHeses2 = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer mentionHasegawaDaysHeses1 = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer mentionHasegawasHeses2 = new ACParentHesesPanelContainer();
    private VRArrayList insurers;
    private Object oldSelectHokenName;
    private ACLabelContainer mentionHasegawas1 = new ACLabelContainer();
    private VRLayout mentionHasegawasLayout = new VRLayout();
    private ACGroupBox hiddenParameters = new ACGroupBox();
    private ACGroupBox insureParameters = new ACGroupBox();
    private IkenshoInitialNegativeIntegerTextField s18 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s19 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s110 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s111 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s112 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s115 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s117 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s118 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s119 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s1110 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s1111 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s1112 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s1113 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s1114 = new IkenshoInitialNegativeIntegerTextField();
    private ACGroupBox ikenshoGroupBox1 = new ACGroupBox();
    private ACTextField insurerName = new ACTextField();
    
    private ACIntegerTextField mentionHokenType = new ACIntegerTextField();
    private ACIntegerTextField addITType = new ACIntegerTextField();

    protected boolean binding = false;


    // 2006/09/12[Tozo Tanaka] : add begin
    /**
     * 電子化加算区分(保険者)を返します。
     * @return 電子化加算区分(保険者)
     */
    public int getAddITType(){
        return ACCastUtilities.toInt(addITType.getText(), 1);
    }
    /**
     * 電子化加算区分(保険者)を設定します。
     * @param 電子化加算区分(保険者)
     */
    public void setAddITType(int val){
        addITType.setText(ACCastUtilities.toString(val,"1"));
    }
    // 2006/09/12[Tozo Tanaka] : add end
    
    
    /**
     * タイトル表示ラベルを返します。
     * 
     * @return タイトル表示ラベル
     */
    protected VRLabel getTitle() {
        return mentionTitle;
    }

    /**
     * 特記事項注釈1を返します。
     * 
     * @return 特記事項注釈1
     */
    protected ACLabel getTokkiAbstraction1() {
        return mentionTokkiAbstraction1;
    }

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {

        VRHashMapArrayToConstKeyArrayAdapter adapter = IkenshoCommon
                .geArrayAdapter(dbm, "INSTITUTION_NM", "M_INSTITUTION",
                        "INSTITUTION_KBN", IkenshoCommon.INSTITUTION_DEFAULTT,
                        "");
        if (adapter.getDataSize() > 0) {
            VRMap map = new VRHashMap();
            map.put("INSTITUTION_NM", "");
            ((VRArrayList) adapter.getAdaptee()).add(0, map);
            IkenshoCommon.applyComboModel(mentionShisetsu1, adapter);
        }

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" INSURER");
        
        sb.append(" WHERE");
        sb.append(" INSURER.INSURER_TYPE IN (0,");
        sb.append(getAllowedInsurerType());
        sb.append(")");
        
        sb.append(" ORDER BY");
        sb.append(" INSURER_NM");
        insurers = (VRArrayList) dbm.executeQuery(sb.toString());

        if (insurers.getDataSize() > 0) {
            VRMap blank = new VRHashMap();
            blank.putAll((Map) insurers.get(0));
            blank.setData("INSURER_NO", "");
            blank.setData("INSURER_NM", "");
            blank.setData("INSURER_TYPE", new Integer(0));
            insurers.add(0, blank);
            IkenshoCommon.applyComboModel(mentionHokenName, insurers);
        }
    }
    /**
     * 一覧に表示可能な保険者区分を返します。
     * @return 一覧に表示可能な保険者区分
     */
    protected int getAllowedInsurerType(){
        //1:主治医意見書のみの保険者は許可する
        return 1;
    }

    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();

        binding = true;
        IkenshoCommon.followComboIndex(insurers, "INSURER_NO",
                (VRMap) getMasterSource(), mentionHokenName);
        binding = false;
    }

    public boolean noControlError() {
        // エラーチェック
        IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();

        switch (mentionHasegawaDay1.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
        case IkenshoEraDateTextField.STATE_VALID:
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("未来の日付です。");
            mentionHasegawaDay1.requestChildFocus();
            return false;
        default:
            ACMessageBox.show("日付に誤りがあります。");
            mentionHasegawaDay1.requestChildFocus();
            return false;
        }

        switch (mentionHasegawaDay2.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
        case IkenshoEraDateTextField.STATE_VALID:
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("未来の日付です。");
            mentionHasegawaDay2.requestChildFocus();
            return false;
        default:
            ACMessageBox.show("日付に誤りがあります。");
            mentionHasegawaDay2.requestChildFocus();
            return false;
        }

        switch (mentionShinseiDate.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
            break;
        case IkenshoEraDateTextField.STATE_VALID:
            if (mentionShinseiDate.getDate().compareTo(
                    info.getApplicant().getWriteDate()) > 0) {
                // 記入日のほうが古い
                ACMessageBox.show("申請日は記入日以前でなければいけません。");
                mentionShinseiDate.requestChildFocus();
                return false;
            }
            if (mentionCreateDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) {
                if (mentionShinseiDate.getDate().compareTo(
                        mentionCreateDate.getDate()) > 0) {
                    // 作成依頼日の方が古い
                    ACMessageBox.show("申請日は作成依頼日以前でなければいけません。");
                    mentionShinseiDate.requestChildFocus();
                    return false;
                }
            }
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("未来の日付です。");
            mentionShinseiDate.requestChildFocus();
            return false;
        default:
            ACMessageBox.show("日付に誤りがあります。");
            mentionShinseiDate.requestChildFocus();
            return false;
        }

        switch (mentionCreateDate.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
            break;
        case IkenshoEraDateTextField.STATE_VALID:
            if (mentionCreateDate.getDate().compareTo(
                    info.getApplicant().getWriteDate()) > 0) {
                // 記入日のほうが古い
                ACMessageBox.show("作成依頼日は記入日以前でなければいけません。");
                mentionCreateDate.requestChildFocus();
                return false;
            }
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("未来の日付です。");
            mentionCreateDate.requestChildFocus();
            return false;
        default:
            ACMessageBox.show("日付に誤りがあります。");
            mentionCreateDate.requestChildFocus();
            return false;
        }

        switch (mentionSendDate.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
        case IkenshoEraDateTextField.STATE_FUTURE:
            break;
        case IkenshoEraDateTextField.STATE_VALID:
            if (info.getApplicant().getWriteDate().compareTo(
                    mentionSendDate.getDate()) > 0) {
                // 記入日のほうが古い
                ACMessageBox.show("記入日は送付日以前でなければいけません。");
                mentionSendDate.requestChildFocus();
                return false;
            }
            break;
        // case NCEraDateTextField.STATE_FUTURE:
        // NCMessageBox.showExclamation("未来の日付です。");
        // mentionSendDate.requestChildFocus();
        // return false;
        default:
            ACMessageBox.show("日付に誤りがあります。");
            mentionSendDate.requestChildFocus();
            return false;
        }

        return true;
    }

    /**
     * 検査点数の未指定チェックを行い、警告を発します。
     * 
     * @return 処理を続行して良いか
     */
    public boolean isTestPointCheckWarning() {
        IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();

        try {
            // if (
            // (!IkenshoCommon.isNullText(mentionHokenName.getSelectedItem()))
            // &&
            if ((mentionHokenName.getSelectedIndex() > 0)
                    && (mentionCareType.getSelectedIndex() != mentionCareType
                            .getNoSelectIndex())
                    && (info.getOrgan().hasDoctorName())) {

                // 検査点数指定要件を満たしている
                if (!IkenshoCommon.isBillSeted((VRMap) getMasterSource())) {
                    // 検査点数未指定
                    if (ACMessageBox.show("検査費用点数が設定されていませんがよろしいですか？",
                            ACMessageBox.BUTTON_OKCANCEL,
                            ACMessageBox.ICON_QUESTION,
                            ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                        return false;
                    }
                }
                if ((info.getBillHakkouKubun() == null)
                        || (info.getBillHakkouKubun().intValue() < 1)) {
                    // 未発行にする
                    info.setBillHakkouKubun(new Integer(1));
                }

            } else {
                // 発行対象外
                info.setBillHakkouKubun(new Integer(0));
            }
        } catch (ParseException ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }
        return true;
    }

    public boolean noControlWarning() {

        if (!IkenshoCommon.isNullText(mentionHiHokenNo.getText())) {
            if (mentionHiHokenNo.getText().length() < 10) {
                if (ACMessageBox.show("被保険者番号が10桁入力されていません。\nよろしいですか？",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                    mentionHiHokenNo.requestFocus();
                    return false;
                }
            }
        }
        // 2006/12/11[Tozo Tanaka] : add begin
        if (IkenshoCommon.isConvertedNoBill(getMasterSource())) {
            // 保険者選択済みかつ請求書出力パターンが0＝以降直後で請求データが不正
            if (ACMessageBox.showOkCancel("請求データは移行されていないため、保険者および医療機関を"
                    + ACConstants.LINE_SEPARATOR + "再選択しない限り請求書を印刷できません。"
                    + ACConstants.LINE_SEPARATOR + "よろしいですか？",
                    ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {

                return false;
            }
        }
        // 2006/12/11[Tozo Tanaka] : add end

        return true;
    }

    /**
     * コンストラクタです。
     */
    public IkenshoIkenshoInfoMention() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hiddenParameters.setVisible(false);

        mentionHokenName.setRenderer(new ACBindListCellRenderer("INSURER_NM"));

        mentionShisetsu1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                changeShisetsu(e);
            }
        });

        mentionShowTokkiMoreAbstractionChooser
                .addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        IkenshoExtraSpecialNoteDialog form = createMentionTeikeibunKubun();
                        mentionTokki.setText(form.showModal(mentionTokki
                                .getText()));
                        mentionTokki.requestFocus();
                    }
                });

        mentionAddHokensha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegistHokensya();
            }
        });

        mentionAddKensa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInputTestPoint();
            }
        });

        mentionHokenName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                changeInsurerName(e);
            }
        });
    }
    /**
     * 特記事項用の定型文ダイアログを返します。
     * @return 特記事項用の定型文ダイアログ
     */
    protected IkenshoExtraSpecialNoteDialog createMentionTeikeibunKubun(){
        return new IkenshoExtraSpecialNoteDialog(
                "その他特記すべき事項",
                IkenshoCommon.TEIKEI_MENTION_NAME, 400, 100, 8,
                50);
    }

    /**
     * 保険者名の変更を処理します。
     * 
     * @param e イベント情報
     */
    protected void changeInsurerName(ItemEvent e) {

        try {

            Object selectItem = mentionHokenName.getSelectedItem();
            if (oldSelectHokenName == selectItem) {
                // 変動なし
                return;
            }
            // boolean blankSelected = IkenshoCommon.isNullText(selectItem);
            boolean blankSelected = (mentionHokenName.getSelectedIndex() <= 0)
                    || IkenshoCommon.isNullText(selectItem);

            final int NO_ERROR = 0;
            final int WARNING_CLEAR = 2 << 0;
            final int WARNING_CHANGE = 2 << 1;
            final int MUST_CHANGE = 2 << 2;
            int checkFlag = NO_ERROR;

            switch (e.getStateChange()) {
            case ItemEvent.DESELECTED:
                if ((oldSelectHokenName != null) && blankSelected) {
                    if (mentionHokenName.hasFocus()) {
                        checkFlag |= WARNING_CLEAR;
                    }
                } else {
                    // 変動なし
                    return;
                }
                break;
            case ItemEvent.SELECTED:
                if (insurers != null) {
                    if (IkenshoCommon.isNullText(oldSelectHokenName)) {
                        // 前回未選択
                        if (blankSelected) {
                            // 変動なし
                            return;
                        }
                        checkFlag |= MUST_CHANGE;
                    } else {
                        // 前回選択からの変更
                        if (mentionHokenName.hasFocus()) {
                            if (blankSelected) {
                                // 選択→ブランクへ
                                checkFlag |= WARNING_CLEAR;
                            } else {
                                checkFlag |= WARNING_CHANGE;
                            }
                        }
                    }
                }
                break;
            default:
                return;
            }

            if ((checkFlag & WARNING_CLEAR) > 0) {
                // 解除警告
                if (ACMessageBox.show(
                        "設定されている請求先情報、意見書作成料および\n診察・検査点数がクリアされます。保険者をクリアしますか？",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                    // キャンセル
                    mentionHokenName
                            .setSelectedItemAbsolute(oldSelectHokenName);
                    return;
                }

            } else if ((checkFlag & WARNING_CHANGE) > 0) {
                // 変更警告
                if (ACMessageBox.show(
                        "意見書作成料および診察・検査点数が変わる可能性があります。\n保険者を変更しますか？\n\n["
                                + String.valueOf(((VRMap) oldSelectHokenName)
                                        .getData("INSURER_NM"))
                                + "]→["
                                + String.valueOf(((VRMap) e.getItem())
                                        .getData("INSURER_NM")) + "]",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                    // キャンセル
                    mentionHokenName
                            .setSelectedItemAbsolute(oldSelectHokenName);
                    return;
                }
            }

            IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();
            VRBindSource oldSource = getMasterSource();
            ACGroupBox pointGroup = info.getBill().getPointGroup();

            if (blankSelected) {
                oldSelectHokenName = null;

                // 初期値に振り直す
                insureParameters.setSource((VRBindSource) insureParameters
                        .createSource());
                insureParameters.bindSource();
                insureParameters.setSource(oldSource);
                insureParameters.applySource();

                mentionHokenNo.setText("");
                mentionHokenNo.applySource();
                
                mentionHokenType.setText("0");
                mentionHokenType.applySource();
                // 2006/02/07[Tozo Tanaka] : add begin
                addITType.setText("1");
                addITType.applySource();
                // 2006/09/07[Tozo Tanaka] : add end

                // 点数初期化
                // 2006/02/06[Tozo Tanaka] : replace begin
                // pointGroup.setSource((VRBindSource)pointGroup.createSource());
                VRBindSource source = (VRBindSource) pointGroup.createSource();
                Object tax = null;
                if (oldSource != null) {
                    // 消費税は維持する
                    tax = oldSource.getData("TAX");
                }
                source.setData("TAX", tax);
                pointGroup.setSource(source);
                // 2006/02/06[Tozo Tanaka] : replace end

                pointGroup.bindSource();
                pointGroup.setSource(oldSource);
                pointGroup.applySource();

                if (!binding) {
                    // ユーザによる選択(bind以外)の場合は切り替える
                    info.getBill().setOutputPattern(null);
                }
            } else {
                oldSelectHokenName = selectItem;

                VRMap insurer = null;
                insurer = (VRMap) insurers.getData(mentionHokenName
                        .getSelectedIndex());
                // insurer = (VRHashMap) insurers.getData(mentionHokenName.
                // getSelectedIndex() - 1);
                insureParameters.setSource(insurer);
                insureParameters.bindSource();
                insureParameters.setSource(oldSource);
                insureParameters.applySource();

                // 2006/09/12
                // Addition - begin [Masahiko Higuchi]
                // InnerBindComponentで処理が呼ばれた際に保険者の処理で
                // 一律新規保険者情報で情報を上書いてしまうので
                // 検索中で尚且つ更新モード以外の場合のみバインドする
                if (!((getMasterAffair() != null) && getMasterAffair()
                        .isNowSelectingByUpdate())) {
                    mentionHokenNo.setText(String.valueOf(VRBindPathParser.get(
                            mentionHokenNo.getBindPath(), insurer)));
                    mentionHokenNo.applySource();

                    mentionHokenType.setText(String.valueOf(VRBindPathParser
                            .get(mentionHokenType.getBindPath(), insurer)));
                    mentionHokenType.applySource();

                    // 2006/02/07[Tozo Tanaka] : add begin
                    addITType.setText(String.valueOf(VRBindPathParser.get(
                            addITType.getBindPath(), insurer)));
                    addITType.applySource();
                    // 2006/09/07[Tozo Tanaka] : add end
                }
                // Addition - end [Masahiko Higuchi]


                insurerName.setText(String.valueOf(VRBindPathParser.get(
                        insurerName.getBindPath(), insurer)));

                if ((!binding)
                        || (Integer.parseInt(String.valueOf(VRBindPathParser
                                .get("OUTPUT_PATTERN", oldSource))) < 0)) {
                    // ユーザによる選択(bind以外)の場合は切り替える
                    oldSource.setData("OUTPUT_PATTERN", insurer
                            .getData("SEIKYUSHO_OUTPUT_PATTERN"));
                    info.getBill().setOutputPattern(
                            oldSource.getData("OUTPUT_PATTERN"));
                }

                if ((checkFlag & (WARNING_CHANGE | MUST_CHANGE)) > 0) {
                    if (binding) {
                        Object taxObj = oldSource.getData("TAX");
                        if (taxObj instanceof Double) {
                            if (((Double) taxObj).doubleValue() < 0) {
                                binding = false;
                            }
                        }
                    }

                    if (!binding) {
                        // 意見書点数設定済みの場合に限る
                        VRMap map = new VRHashMap();
                        map.putAll((VRHashMap) oldSource);
                        map.putAll(insurer);
                        pointGroup.setSource(map);
                        pointGroup.bindSource();
                        pointGroup.setSource(oldSource);
                        pointGroup.applySource();

                        // 2006/02/07[Tozo Tanaka] : replace begin
//                        if(info.getOrgan().isDoctorAddIT()){
                        if (IkenshoCommon.canAddIT(ACCastUtilities.toInt(
                                ((IkenshoIkenshoInfo) getMasterAffair())
                                        .getFormatKubun(), 0), info.getOrgan()
                                .isDoctorAddIT(), insurer)){
                            // 2006/02/07[Tozo Tanaka] : replace begin
                            
                            // 電子化加算対象の医療機関を選択していた場合
                            //新たな初診点数に電子化加算分を加算する
                            double addIt = info.getBill().getShosinAddIT();
                            info.getBill().setShosinHospital(info.getBill().getShosinHospital()+addIt);
                            info.getBill().setShosinSinryoujo(info.getBill().getShosinSinryoujo()+addIt);
                        }
                        
                    }
                }

            }

            info.getOrgan().setJigyoushas();

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }
//  2006/02/07[Tozo Tanaka] : add begin
    /**
     * 電子化加算対象とするかを返します。
     * @param addIt 電子化加算対象区分
     * @return 電子化加算対象とするか
     */
    protected boolean canAddIt(int addItType){
        //(主治医意見書の場合、)電子化加算区分が0のときのみ、電子化加算対象とする。
        //※他の医見書で条件分岐を変更する場合は、overrideで対応すること。
        return addItType==0;
    }
    // 2006/02/07[Tozo Tanaka] : end begin
    
    
    protected void applySourceInnerBindComponent() throws Exception {
        super.applySourceInnerBindComponent();

        IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();
        if (info == null) {
            return;
        }

        VRBindSource source = getMasterSource();
        if (source == null) {
            return;
        }

        // 印刷・検査点数入力直前に計算

        VRMap insurer = getSelectedInsurer();
        if (insurer != null) {
            // //フィールド翻訳
            // if(VRBindPathParser.has("SEIKYUSHO_OUTPUT_PATTERN", insurer)){
            // source.setData("OUTPUT_PATTERN",
            // new VRInteger(String.valueOf(
            // VRBindPathParser.get("SEIKYUSHO_OUTPUT_PATTERN",
            // insurer))));
            // }

            VRPanel pointGroup = info.getBill();
            pointGroup.setSource(source);
            pointGroup.bindSource();

            // 保険者選択中
            int flag;
            flag = 0;
            if (mentionCareType.getSelectedIndex() == 2) {
                flag += 2;
            }
            if (!info.getApplicant().isFirstCreate()) {
                flag += 1;
            }
            source.setData("IKN_CHARGE", new Integer(flag));

            if (VRBindPathParser.has("TAX", source)) {
                // 保険者変更の際にTAXが設定されていなければ、その時点のTAXを設定する。
                Object obj = VRBindPathParser.get("TAX", source);
                if (obj instanceof Double) {
                     if (((Double) obj).doubleValue()<0) {
                        // 税金
                        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
                        IkenshoCommon.setTax(dbm, (VRMap) source);
                        dbm.finalize();
                    }
                }
            }

        } else {
            // 保険者未選択
            source.setData("IKN_CHARGE", new Integer(0));
        }

        // VRPanel pointGroup = info.getBill();
        // pointGroup.setSource(source);
        // pointGroup.bindSource();

    }

    /**
     * 介護施設の変更を処理します。
     * 
     * @param e イベント情報
     */
    protected void changeShisetsu(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Object nowSelect = mentionShisetsu2.getSelectedItem();

            Object[] newArray = new Object[mentionShisetsu1.getItemCount() - 1];
            int i = 0;
            int end = mentionShisetsu1.getSelectedIndex();
            for (; i < end; i++) {
                newArray[i] = mentionShisetsu1.getItemAt(i);
            }
            end = newArray.length;
            for (; i < end; i++) {
                newArray[i] = mentionShisetsu1.getItemAt(i + 1);
            }

            VRArrayList array = new VRArrayList(Arrays.asList(newArray));
            mentionShisetsu2.setModel(new ACComboBoxModelAdapter(array));

            if (mentionShisetsu1.getSelectedItem().equals(nowSelect)) {
                mentionShisetsu2.setSelectedIndex(-1);
            } else if (IkenshoCommon.isNullText(mentionShisetsu1
                    .getSelectedItem())) {
                // 初行
                mentionShisetsu2.setEnabled(false);
                mentionShisetsu2Head.setEnabled(false);
                mentionShisetsu2.setSelectedIndex(-1);
            } else {
                mentionShisetsu2.setSelectedItem(nowSelect);
                mentionShisetsu2.setEnabled(true);
                mentionShisetsu2Head.setEnabled(true);
            }

        }

    }

    public Component getPreviewFocusComponent() {
        return mentionAddHokensha;
    }

    /**
     * 選択中の保険者データを返します。
     * 
     * @return 選択中の保険者データ
     * @see 該当しなければnullを返します。
     */
    public VRMap getSelectedInsurer() {
        if ((mentionHokenName.getSelectedIndex() < 1) || (insurers == null)) {
            return null;
        }
        return (VRMap) insurers.getData(mentionHokenName.getSelectedIndex());
        // return (VRHashMap)
        // insurers.getData(mentionHokenName.getSelectedIndex()-1);
    }

    /**
     * 検査点数入力画面を表示します。
     */
    protected void showInputTestPoint() {
        if (mentionCareType.getSelectedIndex() == mentionCareType
                .getNoSelectIndex()) {
            ACMessageBox.showExclamation("種別を選択してください。");
            mentionCareType.requestChildFocus();
            return;
        }
        // if ( (mentionHokenName.getSelectedItem() == null) ||
        if ((mentionHokenName.getSelectedIndex() < 1)
                || ("".equals(mentionHokenName.getSelectedItem()))) {
            ACMessageBox.showExclamation("保険者をリストより選択してください。");
            mentionHokenName.requestFocus();
            return;
        }

        if (getMasterAffair() != null) {
            IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();

            if (!info.getOrgan().hasDoctorName()) {
                ACMessageBox.showExclamation("医師氏名をリストより選択してください。");
                getMasterAffair().setSelctedTab(info.getOrgan());
                info.getOrgan().getDoctorName().requestFocus();
                return;
            }

            try {
                info.fullApplySource();

                // 引継ぎパラメータの設定
                int flag = 0;
                switch (mentionCareType.getSelectedIndex()) {
                case 1:
                    flag |= IkenshoConsultationInfo.STATE_ZAITAKU;
                    break;
                case 2:
                    flag |= IkenshoConsultationInfo.STATE_SHISETSU;
                    break;
                }
                if (info.getApplicant().isFirstCreate()) {
                    flag |= IkenshoConsultationInfo.STATE_FIRST;
                } else {
                    flag |= IkenshoConsultationInfo.STATE_EVER;
                }

                VRMap map = (VRMap) getMasterSource();
                
                // 2006/12/11[Tozo Tanaka] : add begin
                if (IkenshoCommon.isConvertedNoBill(map)) {
                    //保険者選択済みかつ請求書出力パターンが0＝以降直後で請求データが不正
                    ACMessageBox
                            .showExclamation("請求データが移行されていないため、検査費用の入力が出来ません。"
                                    + ACConstants.LINE_SEPARATOR
                                    + "保険者および医療機関を再選択してください。");
                    return;
                }
                // 2006/12/11[Tozo Tanaka] : add end
                
                // 2006/08/02 - 医師意見書 電子化加算有無判断のためメソッド追加
                //2006/09/09 [Tozo Tanaka] : replace begin
//                // Addition - begin [Masahiko Higuchi]
//                addConsulationSource(map);
//                // Addition - end [Masahiko Higuchi]
                //2006/09/09 [Tozo Tanaka] : replace begin
                map.setData("DR_ADD_IT", new Integer(info.getOrgan()
                        .isDoctorAddIT() ? 1 : 0));
                //2006/09/09 [Tozo Tanaka] : replace end
                
                
                if (new IkenshoConsultationInfo(map, flag, 
                        //2006/09/07 [Tozo Tanaka] : add begin
                        ACCastUtilities.toInt(((IkenshoIkenshoInfo)getMasterAffair()).getFormatKubun(), 0)
                        //2006/09/07 [Tozo Tanaka] : add end
                ).showModal()) {
                    // 更新した
                    info.getBill().bindSource();
                    
                    //2006/09/09 [Tozo Tanaka] : remove begin
//                    //最新を取得しかつ更新したときのみ、医療機関の電子化加算区分を最新にする。
//                    Object obj = map.getData("NEW_DR_ADD_IT");
//                    if (obj != null) {
//                        if (new Integer(1).equals(map.getData("NEW_DR_ADD_IT"))) {
//                            info.getOrgan().setDoctorAddIT(true);
//                        } else {
//                            info.getOrgan().setDoctorAddIT(false);
//                        }
//                    }
                    //2006/09/09 [Tozo Tanaka] : remove end

                }
            } catch (Exception ex) {
                IkenshoCommon.showExceptionMessage(ex);
            }
        }
    }

    //2006/09/09 [Tozo Tanaka] : remove begin
//    /**
//     * 検査費用設定クラスに渡す値を追加します。
//     */
//    protected void addConsulationSource(VRMap src) throws Exception{
//        IkenshoIkenshoInfo info = (IkenshoIkenshoInfo)getMasterAffair();
//        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
//        StringBuffer sb = new StringBuffer();
//        VRArrayList doctorList = new VRArrayList();
//        
//        // 現在選択している医療機関情報を渡しておく
//        if(info.getOrgan() != null){
//            sb.append("SELECT");
//            sb.append(" DR_ADD_IT");
//            sb.append(" FROM");
//            sb.append(" DOCTOR");
//            sb.append(" WHERE");
//            sb.append("(");
//            sb.append("DR_CD = ");
//            sb.append(info.getOrgan().getDoctorCode());
//            sb.append(")");
//            doctorList = (VRArrayList) dbm.executeQuery(sb.toString());
//            dbm.finalize();
//            
//            if(!doctorList.isEmpty()){
//                VRMap map = (VRMap)doctorList.getData(0);
//                if(VRBindPathParser.has("DR_ADD_IT",map)){
//                    src.setData("DR_ADD_IT",map.getData("DR_ADD_IT"));
//                }
//            }else{
//                // 何も行わない
//            }
//            
//        }
//    }
    //2006/09/09 [Tozo Tanaka] : remove end

    
    /**
     * 医療機関登録画面を表示します。
     */
    public void showRegistHokensya() {
        try {
            VRMap param = new VRHashMap();
            param.setData("ACT", "insert");

            savePreviewData(param);

            ACFrame.getInstance().next(
                    new ACAffairInfo(IkenshoHokenshaShousai.class.getName(),
                            param, "保険者詳細"));

        } catch (Exception ex1) {
            IkenshoCommon.showExceptionMessage(ex1);
        }
    }

    private void jbInit() throws Exception {
        mentionCareType.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "１．在宅", "２．施設" }))));
        mentionCareType.setUseClearButton(false);

        setLayout(mentionLayout);
        mentionShisetsusLayout.setAutoWrap(false);
        getMentionShisetsus().setLayout(mentionShisetsusLayout);

        getMentionTokkiAbstractions().setLayout(new VRLayout());
        VRLayout mentionDatesLayout = new VRLayout();
        mentionDatesLayout.setHgap(0);
        mentionDates.setLayout(mentionDatesLayout);
        mentionInsurers.setLayout(mentionInsurersLayout);

        mentionCreateDates.setText("作成依頼日");
        mentionHasegawa1.setMaxLength(3);
        mentionHasegawa1.setBindPath("HASE_SCORE");
        mentionHasegawa1.setColumns(3);
        mentionHasegawa1.setHorizontalAlignment(SwingConstants.RIGHT);
        mentionHasegawa1.setCharType(IkenshoConstants.CHAR_TYPE_DOUBLE1);
        mentionHasegawa1.setIMEMode(InputSubset.LATIN_DIGITS);
        mentionShinseiDate.setBindPath("SINSEI_DT");
        mentionShinseiDate.setAgeVisible(false);
        mentionHasegawaDay1.setBindPath("HASE_SCR_DT");
        mentionHasegawaDay1.setAgeVisible(false);
        mentionHasegawaDay1.setDayVisible(false);
        mentionHasegawaDay1.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
        mentionHokenNo.setEditable(false);
        mentionHokenNo.setColumns(10);
        mentionHokenNo.setBindPath("INSURER_NO");
        mentionHokenType.setVisible(false);
        mentionHokenType.setBindPath("INSURER_TYPE");
        // 2006/02/07[Tozo Tanaka] : add begin
        addITType.setVisible(false);
        addITType.setBindPath("SHOSIN_ADD_IT_TYPE");
        // 2006/09/07[Tozo Tanaka] : add end

        mentionLayout.setFitHLast(true);
        mentionLayout.setFitVLast(true);
        mentionHasegawaDay2.setAgeVisible(false);
        mentionHasegawaDay2.setDayVisible(false);
        mentionHasegawaDay2.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
        mentionHasegawaDay2.setBindPath("P_HASE_SCR_DT");
        getMentionTokkiMoreAbstractions().setLayout(new BorderLayout());
        mentionHiHokenNo.setColumns(10);
        mentionHiHokenNo.setCharType(VRCharType.ONLY_ALNUM);
        mentionHiHokenNo.setBindPath("INSURED_NO");
        mentionHiHokenNo.setMaxLength(10);
        mentionHasegawasLayout.setAutoWrap(false);
        getMentionHasegawas().setLayout(mentionHasegawasLayout);
        getMentionHasegawas().setText("長谷川式 ＝");
        getMentionHasegawas()
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        getMentionHasegawas()
                .setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        getMentionHasegawas().setContentAreaFilled(true);
        getMentionSeikyushoGroup().setLayout(mentionSeikyushoGroupLayout);
        getMentionSeikyushoGroup()
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        getMentionSeikyushoGroup().setText("以下の項目は請求書作成用の項目です（必須項目は自治体により異なります）");
        mentionShisetsu2.setEnabled(false);
        mentionShisetsu2.setEditable(false);
        mentionShisetsu2.setBindPath("INST_SEL_PR2");
        getMentionShisetsus().setText("施設選択（優先度）");
        mentionShisetsu2Head.setEnabled(false);
        mentionShisetsu2Head.setText("　２．");
        // mentionTokkiAbstraction2.setText("結果も記載して下さい。");
        mentionHokenName.setEditable(false);
        mentionHokenName.setIMEMode(InputSubset.KANJI);
        mentionHokenName.setMinLength(100);
        mentionOrderNo.setColumns(10);
        mentionOrderNo.setBindPath("REQ_NO");
        mentionOrderNo.setMaxLength(10);
        getMentionTokkiMoreAbstraction()
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        mentionOrderNo.setCharType(VRCharType.ONLY_ALNUM);
        mentionShisetsu1.setPreferredSize(new Dimension(250, 19));
        mentionShisetsu1.setEditable(false);
        mentionShisetsu1.setBindPath("INST_SEL_PR1");
        getMentionTokkiGroup().setLayout(mentionTokkiGroupLayout);
        getMentionTokkiGroup().setText("その他特記すべき事項");
        mentionHasegawa2.setColumns(3);
        mentionHasegawa2.setHorizontalAlignment(SwingConstants.RIGHT);
        mentionHasegawa2.setBindPath("P_HASE_SCORE");
        mentionHasegawa2.setMaxLength(3);
        mentionHasegawa2.setIMEMode(InputSubset.LATIN_DIGITS);
        mentionHasegawa2.setCharType(IkenshoConstants.CHAR_TYPE_DOUBLE1);
        mentionSendDates.setText("送付日");
        mentionHasegawa1Tail.setText("点");
        mentionCreateDate.setBindPath("REQ_DT");
        mentionCreateDate.setAgeVisible(false);
        mentionTokki.setColumns(100);
        mentionTokki.setLineWrap(true);
        mentionTokki.setRows(8);
        mentionTokki.setBindPath("IKN_TOKKI");
        mentionTokki.setMaxLength(400);
        // mentionTokki.setMaxColumns(100);
        // mentionTokki.setUseMaxRows(true);
        mentionTokki.setMaxRows(mentionTokki.getRows());
        mentionTokki.setIMEMode(InputSubset.KANJI);
        mentionAddHokensha.setToolTipText("「保険者登録」画面を表示します。");
        mentionAddHokensha.setMnemonic('H');
        mentionAddHokensha.setText("保険者登録(H)");
        mentionHasegawa2Tail.setText("点");
        mentionShinseiDates.setText("申請日");
        mentionShowTokkiMoreAbstractionChooser.setBounds(new Rectangle(0, 0,
                113, 23));
        mentionShowTokkiMoreAbstractionChooser
                .setToolTipText("「その他特記事項」選択画面を表示します。");
        mentionShowTokkiMoreAbstractionChooser.setMnemonic('T');
        mentionShowTokkiMoreAbstractionChooser.setText("特記事項選択(T)");
        getMentionTokkiMoreAbstraction().setText("以下の項目は「主治医意見書」の必須項目ではありません。");
        getMentionTokkiMoreAbstraction().setBounds(new Rectangle(0, 0, 297, 15));
        mentionShisetsu1Head.setText("１．");
        mentionCareType.setMinimumSize(new Dimension(27, 23));
        mentionCareType.setBindPath("KIND");
        mentionOrderNos.setText("依頼番号");
        mentionSendDate.setBindPath("SEND_DT");
        mentionSendDate.setAllowedFutureDate(true);
        mentionSendDate.setAgeVisible(false);

        String osName = System.getProperty("os.name");
        if ((osName != null) && (osName.indexOf("Mac") >= 0)) {
            // Macは"桁数表示を削る"
            mentionHiHokenNos.setText("被保険者番号");
        } else {
            mentionHiHokenNos.setText("被保険者番号（英数10桁）");
        }

        mentionHiHokenNos.setContentAreaFilled(true);
        mentionTitle.setText("５．その他特記すべき事項");
        mentionAddKensa.setText("診察・検査内容入力(K)");
        mentionAddKensa.setToolTipText("「診察・検査内容入力」画面を表示します。");
        mentionAddKensa.setMnemonic('K');
        mentionAddKensa
                .setBackground(IkenshoConstants.COLOR_BUTTON_GREEN_FOREGROUND);
        mentionHokenNames.setText("保険者（番号）");
        mentionHokenNames.setLayout(new BorderLayout());
        mentionTokkiGroupLayout.setFitVLast(true);
        mentionTokkiGroupLayout.setFitHLast(true);
        mentionCareTypes.setText("種別");
        mentionSeikyushoGroupLayout.setHgrid(0);
        mentionSeikyushoGroupLayout.setHgap(0);
        mentionSeikyushoGroupLayout.setAutoWrap(false);
        mentionSeikyushoGroupLayout.setLabelMargin(0);
        mentionTokkiAbstraction1.setAutoWrap(true);
        mentionTokkiAbstraction1
                .setText("要介護認定に必要な医学的な意見等を記載して下さい。なお、専門医等に別途意見を求めた場合はその内容、結果も記載して下さい。");
        mentionTokkiAbstraction3.setText("（400文字または8行以内）");
        mentionTokkiAbstraction3
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        mentionHasegawaDays2.setChildFocusedOwner(false);
        ((VRLayout) mentionHasegawasHeses2.getLayout()).setAutoWrap(false);
        mentionHasegawasHeses2.setBeginText("（前回");
        hiddenParameters.setText("隠しパラメータ");
        insureParameters.setText("Integer");
        s18.setBindPath("FD_OUTPUT_UMU");
        s19.setBindPath("HEADER_OUTPUT_UMU2");
        s110.setBindPath("HEADER_OUTPUT_UMU1");
        s111.setBindPath("SEIKYUSHO_OUTPUT_PATTERN");
        s112.setBindPath("SEIKYUSHO_HAKKOU_PATTERN");
        s115.setBindPath("DR_NM_OUTPUT_UMU");
        s117.setBindPath("SOUKATU_FURIKOMI_PRT2");
        s118.setBindPath("FURIKOMISAKI_PRT2");
        s119.setBindPath("MEISAI_KIND2");
        s1110.setBindPath("SOUKATUHYOU_PRT2");
        s1111.setBindPath("FURIKOMISAKI_PRT");
        s1112.setBindPath("MEISAI_KIND");
        s1113.setBindPath("SOUKATUHYOU_PRT");
        s1114.setBindPath("SOUKATU_FURIKOMI_PRT");
        ikenshoGroupBox1.setText("String");
        insurerName.setBindPath("INSURER_NM");
        mentionInsurersLayout.setHgap(0);
        mentionInsurersLayout.setAutoWrap(false);
        mentionInsurersLayout.setLabelMargin(0);
        insureParameters.add(s1114, null);
        insureParameters.add(s1113, null);
        insureParameters.add(s1112, null);
        insureParameters.add(s1111, null);
        insureParameters.add(s1110, null);
        insureParameters.add(s119, null);
        insureParameters.add(s118, null);
        insureParameters.add(s117, null);
        insureParameters.add(s115, null);
        insureParameters.add(s112, null);
        insureParameters.add(s111, null);
        insureParameters.add(s110, null);
        insureParameters.add(s19, null);
        insureParameters.add(s18, null);
        getMentionHasegawas().add(mentionHasegawas1, VRLayout.FLOW);
        getMentionHasegawas().add(mentionHasegawaDays1, VRLayout.FLOW);
        mentionHasegawaDays1.add(mentionHasegawaDaysHeses1, null);
        mentionHasegawaDaysHeses1.add(mentionHasegawaDay1, null);
        mentionHasegawas1.add(mentionHasegawa1, null);
        mentionHasegawas1.add(mentionHasegawa1Tail, null);
        getMentionTokkiAbstractions().add(mentionTokkiAbstraction1, VRLayout.NORTH);
        // mentionTokkiAbstractions.add(mentionTokkiAbstraction2,
        // VRLayout.FLOW);
        getMentionTokkiAbstractions().add(mentionTokkiAbstraction3,
                VRLayout.FLOW_RETURN);
        getMentionTokkiAbstractions()
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        getMentionTokkis().setLayout(new VRLayout());
        getMentionTokkis().add(mentionTokki, VRLayout.LEFT);
        getMentionTokkiMoreAbstractions().add(getMentionTokkiMoreAbstraction(),
                BorderLayout.CENTER);
        getMentionTokkiMoreAbstractions().add(
                mentionShowTokkiMoreAbstractionChooser, BorderLayout.EAST);
        // 2006/07/24
        // 医師意見書 - 追加項目変更
        // Replace - begin [Masahiko Higuchi]
        addMentionTokkiGroupComponent();
            // mentionTokkiGroup.add(mentionTokkiAbstractions, VRLayout.NORTH);
            // mentionTokkiGroup.add(mentionTokkis, VRLayout.NORTH);
            // mentionTokkiGroup.add(mentionTokkiMoreAbstractions, VRLayout.NORTH);
            // mentionTokkiGroup.add(mentionHasegawas, VRLayout.NORTH);
            // mentionTokkiGroup.add(mentionShisetsus, VRLayout.NORTH);
        // Replace - end
        getMentionShisetsus().add(mentionShisetsu1Head, VRLayout.WEST);
        getMentionShisetsus().add(mentionShisetsu1, VRLayout.WEST);
        getMentionShisetsus().add(mentionShisetsu2Head, VRLayout.WEST);
        getMentionShisetsus().add(mentionShisetsu2, VRLayout.CLIENT);
        mentionShinseiDates.add(mentionShinseiDate, null);
        mentionHiHokenNos.add(mentionHiHokenNo, null);
        mentionDates.add(mentionShinseiDates, VRLayout.FLOW_INSETLINE);
        mentionDates.add(mentionCreateDates, VRLayout.FLOW_INSETLINE);
        mentionDates.add(mentionSendDates, VRLayout.FLOW_INSETLINE);
        getMentionSeikyushoGroup().add(mentionDates, VRLayout.WEST);

        mentionInsurers.add(mentionHiHokenNos, VRLayout.FLOW);
        mentionInsurers.add(mentionCareTypes, VRLayout.FLOW_RETURN);
        mentionInsurers.add(mentionOrderNos, VRLayout.FLOW);
        mentionInsurers.add(mentionAddHokensha, VRLayout.FLOW);
        mentionInsurers.add(mentionAddKensa, VRLayout.FLOW_RETURN);
        mentionInsurers.add(mentionHokenNames, VRLayout.FLOW_RETURN);
        getMentionSeikyushoGroup().add(mentionInsurers, VRLayout.CLIENT);
        mentionCareTypes.add(mentionCareType, null);
        mentionCreateDates.add(mentionCreateDate, null);
        mentionOrderNos.add(mentionOrderNo, null);
        mentionSendDates.add(mentionSendDate, null);
        mentionHokenNames.add(mentionHokenName, BorderLayout.CENTER);
        mentionHokenNames.add(mentionHokenNoHeses, BorderLayout.EAST);
        mentionHokenNoHeses.add(mentionHokenNo, null);
        mentionHokenNoHeses.add(mentionHokenType, null);
        // 2006/02/07[Tozo Tanaka] : add begin
        mentionHokenNoHeses.add(addITType, null);
        // 2006/09/07[Tozo Tanaka] : add end

        hiddenParameters.add(ikenshoGroupBox1, null);
        ikenshoGroupBox1.add(insurerName, null);
        hiddenParameters.add(insureParameters, null);
        // 2006/07/11
        // 医師意見書対応 - override用にメソッドに変更
        // Replece - begin [Masahiko Higuchi]
        addComponent();
            // this.add(mentionTitle, VRLayout.NORTH);
            // this.add(mentionTokkiGroup, VRLayout.NORTH);
            // this.add(mentionSeikyushoGroup, VRLayout.NORTH);
            // this.add(hiddenParameters, VRLayout.SOUTH);
        // Replace - end
        mentionHasegawas2.add(mentionHasegawa2, null);
        mentionHasegawas2.add(mentionHasegawa2Tail, null);
        getMentionHasegawas().add(mentionHasegawasHeses2, VRLayout.FLOW);
        mentionHasegawasHeses2.add(mentionHasegawas2, null);
        mentionHasegawasHeses2.add(mentionHasegawaDays2, null);
        mentionHasegawaDays2.add(mentionHasegawaDayHeses2, null);
        mentionHasegawaDayHeses2.add(mentionHasegawaDay2, null);
        
        
        //2006/08/11 Tozo TANAKA begin-add 平成のみ表示させる対応 
        mentionShinseiDate.setEraRange(3);
        mentionCreateDate.setEraRange(3);
        mentionSendDate.setEraRange(3);
        //2006/08/11 Tozo TANAKA begin-end 平成のみ表示させる対応
    }
    
    /**
     * overrideしてタブパネルへの追加順序を定義します。
     */
    protected void addComponent(){
        this.add(mentionTitle, VRLayout.NORTH);
        this.add(mentionTokkiGroup, VRLayout.NORTH);
        this.add(getMentionSeikyushoGroup(), VRLayout.NORTH);
        this.add(hiddenParameters, VRLayout.SOUTH);
    }
    
    /**
     * overrideして特別な医療グループの追加順序を定義します。
     */
    protected void addMentionTokkiGroupComponent(){
        getMentionTokkiGroup().add(getMentionTokkiAbstractions(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionTokkis(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionTokkiMoreAbstractions(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionHasegawas(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionShisetsus(), VRLayout.NORTH);
    }

    /**
     * 請求グループを返します。
     * @return
     */
    protected ACGroupBox getHiddenParameters() {
        if(hiddenParameters == null){
            hiddenParameters = new ACGroupBox();
        }
        return hiddenParameters;
    }
    /**
     * 請求書作成用グループを返します。
     * @return
     */
    protected ACGroupBox getMentionSeikyushoGroup() {
        if(mentionSeikyushoGroup == null){
            mentionSeikyushoGroup = new ACGroupBox();
        }
        return mentionSeikyushoGroup;
    }
    /**
     * 特記すべき事項グループを返します。
     * @return
     */
    protected ACGroupBox getMentionTokkiGroup() {
        if(mentionTokkiGroup == null){
            mentionTokkiGroup = new ACGroupBox();
        }
        return mentionTokkiGroup;
    }
    /**
     * 特記すべき事項パネルを返します。
     * @return
     */
    protected VRPanel getMentionTokkiAbstractions() {
        if(mentionTokkiAbstractions == null){
            mentionTokkiAbstractions = new VRPanel();
        }
        return mentionTokkiAbstractions;
    }
    /**
     * 特記すべき事項ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getMentionTokkis() {
        if(mentionTokkis == null){
            mentionTokkis = new ACLabelContainer();
        }
        return mentionTokkis;
    }
    /**
     *　します。
     * @return
     */
    protected VRPanel getMentionTokkiMoreAbstractions() {
        if(mentionTokkiMoreAbstractions == null){
            mentionTokkiMoreAbstractions = new VRPanel();
        }
        return mentionTokkiMoreAbstractions;
    }
    /**
     * 長谷川式ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getMentionHasegawas() {
        if(mentionHasegawas == null){
            mentionHasegawas = new ACLabelContainer();
        }
        return mentionHasegawas;
    }
    /**
     * 施設ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getMentionShisetsus() {
        if(mentionShisetsus == null){
            mentionShisetsus = new ACLabelContainer();
        }
        return mentionShisetsus;
    }

    protected VRLabel getMentionTokkiMoreAbstraction() {
        if(mentionTokkiMoreAbstraction == null){
            mentionTokkiMoreAbstraction = new VRLabel();
        }
        return mentionTokkiMoreAbstraction;
    }
    /**
     * 保険者名を返します。
     * @return 保険者名
     */
    public ACComboBox getMentionHokenName(){
        return mentionHokenName;
    }
 }