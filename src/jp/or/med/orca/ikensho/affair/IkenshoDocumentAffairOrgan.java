package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import jp.nichicom.ac.component.ACBindListCellRenderer;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.text.ACTextFieldDocument;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.component.IkenshoZipTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoHashableComboFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoDocumentAffairOrgan extends
        IkenshoTabbableChildAffairContainer {
    protected VRArrayList doctors;
    protected VRMap doctor;
    protected VRMap defaultDoctor;

    private VRLayout organLayout = new VRLayout();
    private ACLabelContainer organDoctorNames = new ACLabelContainer();
    private VRLabel faxSpacer = new VRLabel();
    private ACGroupBox organGroup = new ACGroupBox();
    private IkenshoTelTextField tel = new IkenshoTelTextField();
    private IkenshoZipTextField zip = new IkenshoZipTextField();
    private ACTextField organizationName = new ACTextField();
    private ACTextField address = new ACTextField();
    private ACLabelContainer faxs = new ACLabelContainer();
    private ACLabelContainer zips = new ACLabelContainer();
    private IkenshoTelTextField fax = new IkenshoTelTextField();
    private IkenshoDocumentTabTitleLabel organTitle = new IkenshoDocumentTabTitleLabel();
    private ACButton showAddOrgan = new ACButton();
    private VRLabel zipSpacer = new VRLabel();
    private ACLabelContainer addresss = new ACLabelContainer();
    private ACLabelContainer organizationNames = new ACLabelContainer();
    private ACLabelContainer tels = new ACLabelContainer();
    private VRLayout organGroupLayout = new VRLayout();
    private IkenshoReadOnlyCombo organDoctorName = new IkenshoReadOnlyCombo();
    private IkenshoTelTextField miCelTel = new IkenshoTelTextField();
    private ACLabelContainer miCelTels = new ACLabelContainer();
    private VRLabel miCelTelSpacer = new VRLabel();
    private VRLabel telSpacer = new VRLabel();
    private VRLabel showAddSpacer = new VRLabel();
    private ACGroupBox stringParameter = new ACGroupBox();
    private ACTextField doctorName = new ACTextField();
    private ACGroupBox hiddenParameter = new ACGroupBox();
    private ACTextField miStsOther = new ACTextField();
    private ACGroupBox integerParameter = new ACGroupBox();
    private ACIntegerCheckBox miDefault = new ACIntegerCheckBox();
    private VRPanel followDoctorContainer = new VRPanel();

    /**
     * 医師氏名コンテナを返します。
     * 
     * @return 医師氏名コンテナ
     */
    protected ACLabelContainer getDoctorNameContainer() {
        return organDoctorNames;
    }

    /**
     * 医師氏名コンポーネントを返します。
     * 
     * @return 医師氏名コンポーネント
     */
    protected ACComboBox getDoctorName() {
        return organDoctorName;
    }

    /**
     * 医療機関携帯電話コンテナを返します。
     * 
     * @return 医療機関携帯電話コンテナ
     */
    protected ACLabelContainer getCellularTELContainer() {
        return miCelTels;
    }

    /**
     * 医師氏名に連動するパラメタを内包するコンテナを返します。
     * 
     * @return 医師氏名に連動するパラメタを内包するコンテナ
     */
    protected VRPanel getFollowDoctorContainer() {
        return followDoctorContainer;
    }

    /**
     * 基底のグループを返します。
     * 
     * @return 基底のグループ
     */
    protected ACGroupBox getContentsGroup() {
        return organGroup;
    }

    public Component getPreviewFocusComponent() {
        return showAddOrgan;
    }

    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();

        
        Object obj = IkenshoCommon.getMatchRow(doctors, "DR_NM",
                getMasterSource());
        if (obj == null) {
            organDoctorName.getEditor().setItem(
                    VRBindPathParser.get("DR_NM", getMasterSource()));
        } else {
            // 2006/12/11[Tozo Tanaka] : add begin
            VRBindSource source = getMasterSource();
            if (source != null) {
                Object val = source.getData("DR_NO");
                if ((val == null) || "".equals(val)) {
                    // 医師番号がない→選択ではなくテキスト設定
                    Object name = VRBindPathParser.get("DR_NM", source);
                    if ((name != null) && (!"".equals(name))) {
                        organDoctorName.getEditor().setItem(name);
                        return;
                    }
                }

            }
            // 2006/12/11[Tozo Tanaka] : add end
            
            organDoctorName.setSelectedItem(obj);
        }
    }

    /** TODO <HEAD_IKENSYO> */
    protected class IkenshoReadOnlyCombo extends ACComboBox {
        public IkenshoReadOnlyCombo() {
            super();
            AbstractVRTextField field = (AbstractVRTextField) this.getEditor()
                    .getEditorComponent();
            field.setDocument(new IkenshoReadOnlyComboDocument(field));
        }

        public void setSelectedItem(Object anObject) {
            Component comp = getEditor().getEditorComponent();
            if (comp instanceof AbstractVRTextField) {
                Object doc = ((AbstractVRTextField) comp).getDocument();
                if (doc instanceof IkenshoReadOnlyComboDocument) {
                    IkenshoReadOnlyComboDocument document = (IkenshoReadOnlyComboDocument) doc;
                    document.setFreeSet(true);
                    super.setSelectedItem(anObject);
                    document.setFreeSet(false);
                    return;
                }
            }
            super.setSelectedItem(anObject);
        }
        
    }

    /** TODO <HEAD_IKENSYO> */
    protected class IkenshoReadOnlyComboDocument extends ACTextFieldDocument {
        protected boolean freeSet = false;

        /**
         * コンストラクタです。
         * 
         * @param textField 検査対象のテキストフィールド
         */
        public IkenshoReadOnlyComboDocument(AbstractVRTextField textField) {
            super(textField);
        }

        public void insertString(int offset, String str, AttributeSet attr)
                throws BadLocationException {
            if (!getTextField().hasFocus() || isFreeSet()) {
                super.insertString(offset, str, attr);
            }
        }

        public void remove(int offset, int length) throws BadLocationException {
            if (!getTextField().hasFocus() || isFreeSet()) {
                super.remove(offset, length);
            }
        }

        public boolean isFreeSet() {
            return freeSet;
        }

        public void setFreeSet(boolean freeSet) {
            this.freeSet = freeSet;
        }

    }

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        // ブランクで作成
        defaultDoctor = (VRMap) getFollowDoctorContainer().createSource();
        doctor = defaultDoctor;

        // 医者集合取得
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" DOCTOR");
        sb.append(" ORDER BY");
        sb.append(" DR_CD");
        doctors = (VRArrayList) dbm.executeQuery(sb.toString());

        organDoctorName.setFormat(new IkenshoHashableComboFormat(doctors,
                "DR_NM"));
        if (doctors.getDataSize() > 0) {
            // 選択解除を実現するため、初行に空データを挿入
            doctors.add(0, defaultDoctor);

            IkenshoCommon.applyComboModel(organDoctorName, doctors);
        }

    }

    public void doBeforeBindOnSelected() throws Exception {
        if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getMasterAffair()
                .getNowMode())) {
            setDefaultDoctorOnInsertInit();
        }
    }

    /**
     * デフォルト選択とされている医師を設定します。
     * 
     * @throws ParseException 解析例外
     * @return デフォルト医師を選択したか
     */
    protected boolean setDefaultDoctorOnInsertInit() throws ParseException {
        // デフォルト医師チェック
        int end = doctors.size();
        if (end == 2) {
            // 医療機関が1件しかない(添え字0はブランク行)場合はデフォルト選択
            organDoctorName.setSelectedItem(doctors.getData(1));
            return true;
        }
        for (int i = 0; i < end; i++) {
            VRMap row = (VRMap) doctors.getData(i);
            Object obj = VRBindPathParser.get("MI_DEFAULT", row);
            if (obj instanceof Integer) {
                if (((Integer) obj).intValue() == 1) {
                    organDoctorName.setSelectedItem(row);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 医師氏名が空でないかを返します。
     * 
     * @return 医師氏名が空でなければtrue
     */
    public boolean hasDoctorName() {
        Object obj = organDoctorName.getEditor().getItem();
        if ((obj == null) || "".equals(String.valueOf(obj))) {
            return false;
        }
        return true;
    }

    /**
     * コンストラクタです。
     */
    public IkenshoDocumentAffairOrgan() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hiddenParameter.setVisible(false);

        organDoctorName.setRenderer(new ACBindListCellRenderer("DR_NM"));

        organDoctorName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                changeDoctor(e);
            }
        });

        showAddOrgan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegistDoctor();
            }
        });

        Component comp = organDoctorName.getEditor().getEditorComponent();
        if (comp instanceof AbstractVRTextField) {
            AbstractVRTextField field = (AbstractVRTextField) comp;
            // field.setDocument(new IkenshoReadOnlyComboDocument(field));
            field.addKeyListener(new KeyListener() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        organDoctorName.hidePopup();
                    }
                }

                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        organDoctorName.hidePopup();
                    }
                }

                public void keyTyped(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        organDoctorName.hidePopup();
                    }
                }
            });
        }

    }

    /**
     * 選択している医師を内部変数へ反映させ、処理続行可能ならばtrueをかえします。
     * 
     * @return boolean 処理続行可能ならばtrue
     */
    protected boolean checkSelectedDoctor() {
        throw new RuntimeException(
                "明示的なOver rideが必要なcheckSelectedDoctorメソッドが呼ばれました");
    }

    /**
     * 医療機関の変更を処理します。
     * 
     * @param e イベント情報
     */
    protected void changeDoctor(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (!checkSelectedDoctor()) {
                return;
            }

            if ((getMasterSource() instanceof VRMap) && (doctor != null)) {
                VRMap map = (VRMap) getMasterSource();
                map.putAll(doctor);
                try {
                    getFollowDoctorContainer().bindSource();
                } catch (ParseException ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        }

    }

    /**
     * 医療機関登録画面を表示します。
     */
    protected void showRegistDoctor() {
        try {

            if (!canCallRegistDoctor()) {
                return;
            }

            VRMap param = new VRHashMap();
            param.setData("ACT", "insert");

            if (getMasterAffair() != null) {
                IkenshoTabbableAffairContainer info = getMasterAffair();
                info.fullApplySource();
                // 最新を適用

                VRBindSource bs = getMasterSource();
                bs.setData("TAB", new Integer(info.getSelctedTabIndex()));
                bs.setData("AFFAIR_MODE", info.getNowMode());
                param.setData("PREV_DATA", bs);
            }

            ACFrame.getInstance().next(
                    new ACAffairInfo(IkenshoIryouKikanJouhouShousai.class
                            .getName(), param, "医療機関情報詳細"));

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }

    /**
     * 医療機関登録画面に遷移してよいかを返します。
     * 
     * @return 医療機関登録画面に遷移してよいか
     */
    protected boolean canCallRegistDoctor() {
        throw new RuntimeException(
                "明示的なOver rideが必要なcanCallRegistDoctorメソッドが呼ばれました");
    }

    private void jbInit() throws Exception {
        setLayout(organLayout);

        organDoctorName.setColumns(15);
        organDoctorNames.setLayout(new BorderLayout());
//        organDoctorNames.setColumns(40);
        organDoctorNames.setText("医師氏名");
        organGroup.setLayout(new VRLayout());
        tel.setBindPathArea("MI_TEL1");
        tel.setBindPathNumber("MI_TEL2");
        tel.setEditable(false);
        zip.setBindPath("MI_POST_CD");
        zip.setEditable(false);
        zips.setText("郵便番号");
        organizationName.setEditable(false);
        organizationName.setBindPath("MI_NM");
        address.setEditable(false);
        address.setBindPath("MI_ADDRESS");
        followDoctorContainer.setLayout(organGroupLayout);
        faxs.setText("FAX番号");
        fax.setBindPathNumber("MI_FAX2");
        fax.setEditable(false);
        fax.setBindPathArea("MI_FAX1");
        organTitle.setText("医療機関");
//        showAddOrgan.setBounds(new Rectangle(0, 0, 113, 23));
        showAddOrgan.setToolTipText("「医療機関情報詳細」画面を表示します。");
        showAddOrgan.setMnemonic('D');
        showAddOrgan.setText("医療機関登録(D)");
        addresss.setText("所在地");
        organizationNames.setText("医療機関名");
        tels.setText("電話番号");
        organGroupLayout.setFitHLast(true);
        miCelTel.setBindPathArea("MI_CEL_TEL1");
        miCelTel.setBindPathNumber("MI_CEL_TEL2");
        miCelTel.setEditable(false);
        miCelTels.setText("携帯電話番号");
        stringParameter.setText("String");
        doctorName.setBindPath("DR_NM");
        hiddenParameter.setText("隠しパラメタ");
        miStsOther.setBindPath("MT_STS_OTHER");
        integerParameter.setText("Integer");
        miDefault.setText("MI_DEFAULT");
        miDefault.setBindPath("MI_DEFAULT");
        organDoctorNames.add(organDoctorName, BorderLayout.CENTER);
        miCelTels.add(miCelTel, null);
        organizationNames.add(organizationName, null);
        zips.add(zip, null);
        addresss.add(address, null);
        tels.add(tel, null);
        faxs.add(fax, null);

        hiddenParameter.add(integerParameter, null);
        hiddenParameter.add(stringParameter, null);
        stringParameter.add(miStsOther, null);
        stringParameter.add(doctorName, null);

        organGroup.add(followDoctorContainer, VRLayout.NORTH);

        followDoctorContainer.add(organDoctorNames, VRLayout.FLOW_INSETLINE);
        followDoctorContainer.add(showAddOrgan, VRLayout.FLOW_INSETLINE);
        followDoctorContainer
                .add(showAddSpacer, VRLayout.FLOW_INSETLINE_RETURN);

        followDoctorContainer.add(organizationNames,
                VRLayout.FLOW_INSETLINE_RETURN);
        followDoctorContainer.add(zips, VRLayout.FLOW_INSETLINE);
        followDoctorContainer.add(zipSpacer, VRLayout.FLOW_INSETLINE_RETURN);
        followDoctorContainer.add(addresss, VRLayout.FLOW_INSETLINE_RETURN);
        followDoctorContainer.add(tels, VRLayout.FLOW_INSETLINE);
        followDoctorContainer.add(telSpacer, VRLayout.FLOW_INSETLINE_RETURN);

        followDoctorContainer.add(faxs, VRLayout.FLOW_INSETLINE);
        followDoctorContainer.add(faxSpacer, VRLayout.FLOW_INSETLINE_RETURN);
        followDoctorContainer.add(miCelTels, VRLayout.FLOW_INSETLINE);
        followDoctorContainer.add(miCelTelSpacer,
                VRLayout.FLOW_INSETLINE_RETURN);

        followDoctorContainer.add(hiddenParameter, VRLayout.SOUTH);

        this.add(organTitle, VRLayout.NORTH);
        this.add(organGroup, VRLayout.CLIENT);
        integerParameter.add(miDefault, null);
    }

}
