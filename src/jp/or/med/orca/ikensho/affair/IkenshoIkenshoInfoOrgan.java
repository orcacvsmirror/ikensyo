package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ItemEvent;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACIntegerTextField;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoOrgan extends IkenshoDocumentAffairOrgan {
    private VRArrayList jigyoushas;

    private ACTextField organBankAccountNo = new ACTextField();
    private ACTextField organDoctorNo = new ACTextField();
    private ACLabelContainer organBankAccountTypes = new ACLabelContainer();
    private ACTextField organBankUser = new ACTextField();
    private VRLayout organBankLayout = new VRLayout();
    private ACLabelContainer organBankAccountNos = new ACLabelContainer();
    private ACLabelContainer organBankUsers = new ACLabelContainer();
    private ACLabelContainer organOpeners = new ACLabelContainer();
    private ACTextField organBankAccountType = new ACTextField();
    // private VRComboBox organJigyoushoNo = new VRComboBox();
    private IkenshoReadOnlyCombo organJigyoushoNo = new IkenshoReadOnlyCombo();
    private ACGroupBox organBankGroup = new ACGroupBox();
    private ACTextField organBankOffice = new ACTextField();
    private ACTextField organOpener = new ACTextField();
    private ACLabelContainer organJigyoushoNos = new ACLabelContainer();
    private ACLabelContainer organBankNames = new ACLabelContainer();
    private VRLabel organJigyoushoNoAbstraction = new VRLabel();
    //private VRLabel organBankOfficeNameSpacer = new VRLabel();
    private ACTextField organType = new ACTextField();
    private ACParentHesesPanelContainer organDoctorNos = new ACParentHesesPanelContainer();
    private ACLabelContainer organTypes = new ACLabelContainer();
    private ACLabelContainer organBankOffices = new ACLabelContainer();
    private VRLabel organTypeSpacer = new VRLabel();
    private ACTextField organBankName = new ACTextField();
    private VRLayout organJigyoushoNosLayout = new VRLayout();

    private Double defaultTax = new Double(0);
    private VRLabel organJigyoushoNoRegistAbstraction1 = new VRLabel();
    private VRPanel organJigyoushoNoAbstractions = new VRPanel();
    private VRLabel organJigyoushoNoRegistAbstraction2 = new VRLabel();
    
    private ACIntegerCheckBox doctorAddIT = new ACIntegerCheckBox();
    private ACIntegerTextField doctorCode = new ACIntegerTextField();

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" TAX");
        VRArrayList arrays = (VRArrayList) dbm.executeQuery(sb.toString());
        if (arrays.getDataSize() > 0) {
            defaultTax = Double.valueOf(String.valueOf(VRBindPathParser.get(
                    "TAX", (VRMap) arrays.getData())));
        }
        
        
        //2006/09/08[Tozo Tanaka] : delete begin
//        //2006/02/12[Tozo Tanaka] : add begin 
//        getMasterAffair().getTabs().addChangeListener(new ChangeListener(){
//            public void stateChanged(ChangeEvent e) {
//              getDoctorName().requestFocus();
//            }
//        });
//        //2006/02/12[Tozo Tanaka] : add end
        //2006/09/08[Tozo Tanaka] : delete end
    }

    /**
     * コンストラクタです。
     */
    public IkenshoIkenshoInfoOrgan() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        organJigyoushoNoAbstraction.setVisible(false);
        organJigyoushoNoRegistAbstraction1.setVisible(false);
        organJigyoushoNoRegistAbstraction2.setVisible(false);

    }
    
    private boolean organUserChanging = false;
    /**
     * ユーザ操作による医療機関の変更中であるかを返します。
     * @return ユーザ操作による医療機関の変更中であるか
     */
    protected boolean isOrganUserChanging(){
        return organUserChanging ;
    }
    /**
     * ユーザ操作による医療機関の変更中であるかを設定します。
     * @param organUserChanging ユーザ操作による医療機関の変更中であるか
     */
    protected void setOrganUserChanging(boolean organUserChanging ){
        this.organUserChanging  = organUserChanging;
    }

    protected boolean checkSelectedDoctor() {
        // 2006/09/21[Tozo Tanaka] : add begin
        setOrganUserChanging(true);
        // 2006/09/21[Tozo Tanaka] : add end
        
        // 2006/02/11[Tozo Tanaka] : replace begin
        // return setJigyoushas();
        boolean result = setJigyoushas();
        
        // 2006/09/21[Tozo Tanaka] : add begin
        setOrganUserChanging(false);
        // 2006/09/21[Tozo Tanaka] : add end

        
        // 2006/09/11[Tozo Tanaka] : add begin
        if ((getMasterAffair() != null)
                && getMasterAffair().isNowSelectingByUpdate()) {
            // 検索処理中でかつ更新モードのとき
            //→上書き転記しない
        }else{
            // 2006/09/11[Tozo Tanaka] : add end

            VRBindSource source = getMasterSource();
            if ((source != null) && (source instanceof VRMap)) {
                // 口座の転記
                VRMap map = (VRMap) source;
                if (doctor != null) {
                    map
                            .setData("KOUZA_MEIGI", doctor
                                    .getData("FURIKOMI_MEIGI"));
                    map.setData("KOUZA_NO", doctor.getData("BANK_KOUZA_NO"));
                    map
                            .setData("KOUZA_KIND", doctor
                                    .getData("BANK_KOUZA_KIND"));

                    // 2006/02/11[Tozo Tanaka] : add begin
                    // 医療機関の施設区分(MI_KBN)を請求情報の初診対象(SHOSIN)に転記する
                    map.setData("SHOSIN", doctor.getData("MI_KBN"));
                    if (getMasterAffair() instanceof IkenshoIkenshoInfo) {
                        ((IkenshoIkenshoInfo) getMasterAffair()).getBill()
                                .setShoshin(doctor.getData("MI_KBN"));
                    }
                    // 2006/02/11[Tozo Tanaka] : add end

                }
            }
            //2006/09/11[Tozo Tanaka] : add begin
        }
        //2006/09/11[Tozo Tanaka] : add end
        return result;

        //2006/02/11[Tozo Tanaka] : replace end
    }

    protected void applySourceInnerBindComponent() throws Exception {
        super.applySourceInnerBindComponent();

        VRBindSource source = getMasterSource();
        if ((source != null) && (source instanceof VRMap)) {
            VRMap map = (VRMap) source;
            if (getSelectedJigyousha() != null) {
                map.putAll(getSelectedJigyousha());
            } else if (organJigyoushoNo.getEditor().getItem() != null) {
                map.put("JIGYOUSHA_NO", String.valueOf(organJigyoushoNo
                        .getEditor().getItem()));
            } else {
                map.put("JIGYOUSHA_NO", "");
            }

            Object shoshinObj = VRBindPathParser.get("MI_KBN", map);
            if (shoshinObj != null) {
                // 履歴にしか存在しない医療機関を選択していた場合、MI_KBNは取得できない
                // そういった履歴で検査点数を再入力するために、有効なMI_KBNをSHOSINに転記する
                VRBindPathParser.set("SHOSIN", map, shoshinObj);
            } else {
                // MI_KBNを取得できずSHOSINが有効であれば、SHOSINをMI_KBNに転記する
                shoshinObj = VRBindPathParser.get("SHOSIN", map);
                if (shoshinObj != null) {
                    VRBindPathParser.set("MI_KBN", map, shoshinObj);
                }
            }
        }
    }

    protected void bindSourceInnerBindComponent() throws Exception {
        Object jigyoushaNo = null;
        VRBindSource source = getMasterSource();
        if ((source != null) && (source instanceof VRMap)) {
            jigyoushaNo = VRBindPathParser.get("JIGYOUSHA_NO", source);
        }

        // 履歴情報を退避
        Object meigi = source.getData("KOUZA_MEIGI");
        Object no = source.getData("KOUZA_NO");
        Object kind = source.getData("KOUZA_KIND");

        super.bindSourceInnerBindComponent();

        if (kind != null) {
            // bindによる医療機関の再選択で履歴が消されてしまうため、再bind
            source.setData("FURIKOMI_MEIGI", meigi);
            source.setData("BANK_KOUZA_NO", no);
            source.setData("BANK_KOUZA_KIND", kind);
            getFollowDoctorContainer().bindSource();
        }

        // 2006/09/21[Tozo Tanaka] : add begin
        setJigyoushoNosOnSelectingByUpdate(jigyoushaNo);
        // 2006/09/21[Tozo Tanaka] : add end
        if (jigyoushaNo != null) {
            // 2006/09/20[Tozo Tanaka] : add begin
            //選択させる。
            organJigyoushoNo.setSelectedItem(jigyoushaNo);
            // 2006/09/20[Tozo Tanaka] : add end
            organJigyoushoNo.getEditor().setItem(jigyoushaNo);
            VRBindPathParser.set("JIGYOUSHA_NO", source, jigyoushaNo);
            
            checkOrganAbstractionVisible();
        }

    }

    /**
     * 選択中の事業者データを返します。
     * 
     * @return 選択中の事業者データ
     */
    public VRMap getSelectedJigyousha() {
        if ((organJigyoushoNo.getSelectedIndex() >= 0) && (jigyoushas != null)) {
            return (VRMap) jigyoushas.getData(organJigyoushoNo
                    .getSelectedIndex());
        }
        return null;
    }

    /**
     * 履歴表示時に事業所番号の入力候補を復元します。
     * 
     * @param jigyoushaNo 履歴の事業所番号
     */
    protected void setJigyoushoNosOnSelectingByUpdate(Object jigyoushaNo) {
        if ((getMasterAffair() != null)
                && getMasterAffair().isNowSelectingByUpdate()) {
            // 履歴表示時
            if ((jigyoushaNo == null) || "".equals(jigyoushaNo)) {
                // 事業所番号がない場合
                // 事業所番号コンボの内容を空で生成する。
                organJigyoushoNo.setModel(new DefaultComboBoxModel());
            } else {
                // 事業所番号を有する場合
                // 事業所番号コンボの内容を履歴の事業所番号のみ追加して生成する。
                organJigyoushoNo.setModel(new DefaultComboBoxModel(
                        new Object[] { jigyoushaNo }));
            }
        }
    }

    /**
     * 事業者一覧を再構成します。
     * 
     * @return 再構成したか
     */
    protected boolean setJigyoushas() {

        try {

            Object selectItem = getDoctorName().getSelectedItem();
            // 2006/09/21[Tozo Tanaka] : replace begin
            //if ((selectItem instanceof String)|| (selectItem instanceof Integer)) {
            if ((selectItem instanceof String)|| (selectItem instanceof Integer)||(selectItem==null)) {
            // 2006/09/21[Tozo Tanaka] : replace end
                // 医師氏名コンボのフォーカスロストによって強制的に文字列データを設定されてしまった場合
                if (doctor == defaultDoctor) {
                    
                    // 2006/09/21[Tozo Tanaka] : add begin
                    if (
                            // 履歴表示によるbindは除外する。
                    (getMasterAffair() instanceof IkenshoIkenshoInfo)
                            && (!getMasterAffair().isNowSelectingByUpdate())
                            // ユーザ操作による医師氏名変更は除外する。
                            && (!isOrganUserChanging())
                    ) {
                        IkenshoIkenshoInfoMention mention = ((IkenshoIkenshoInfo) getMasterAffair())
                                .getMention();
                        if (mention != null) {
                            // 保険者名にフォーカスがある→ユーザ操作
                            VRMap hoken = mention.getSelectedInsurer();
                            if (hoken != null) {
                                // 保険者選択済み
                                IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
                                StringBuffer sb = new StringBuffer();
                                String txt;
                                sb.append("SELECT");
                                sb.append(" *");
                                sb.append(" FROM");
                                sb.append(" JIGYOUSHA,DOCTOR,INSURER");
                                sb.append(" WHERE");
                                sb.append("(");
                                sb.append("DOCTOR.DR_NM = ");
                                txt = IkenshoConstants.FORMAT_PASSIVE_STRING
                                        .format(getDoctorName().getEditor()
                                                .getItem());
                                if("NULL".equals(txt)){
                                    txt = "''";
                                }
                                sb.append( txt);
                                sb.append(")AND(");
                                sb.append("DOCTOR.MI_NM = ");
                                txt = IkenshoConstants.FORMAT_PASSIVE_STRING
                                        .format(getOrganizationName().getText());
                                if("NULL".equals(txt)){
                                    txt = "''";
                                }
                                sb.append( txt);
                                sb.append(")AND(");
                                sb.append("INSURER.INSURER_NO = ");
                                sb.append(IkenshoCommon.getDBSafeString(
                                        "INSURER_NO", hoken));
                                sb.append(")AND(");
                                sb.append("INSURER.INSURER_TYPE = ");
                                sb.append(IkenshoCommon.getDBSafeNumber(
                                        "INSURER_TYPE", hoken));
                                sb.append(")AND(");
                                sb.append("JIGYOUSHA.DR_CD = DOCTOR.DR_CD");
                                sb.append(")AND(");
                                sb
                                        .append("JIGYOUSHA.INSURER_NO = INSURER.INSURER_NO");
                                sb.append(")");
                                jigyoushas = (VRArrayList) dbm.executeQuery(sb
                                        .toString());
                                dbm.finalize();

                                // 取得した事業所番号候補を設定する
                                IkenshoCommon
                                        .applyComboModel(
                                                organJigyoushoNo,
                                                new VRHashMapArrayToConstKeyArrayAdapter(
                                                        jigyoushas,
                                                        "JIGYOUSHA_NO"));

                                if (organJigyoushoNo.getItemCount() > 0) {
                                    Object val = organJigyoushoNo.getItemAt(0);
                                    // doctor.setData("JIGYOUSHA_NO",
                                    // String.valueOf(val));
                                    organJigyoushoNo.setSelectedItem(val);
                                    getMasterSource().setData("JIGYOUSHA_NO",
                                            String.valueOf(val));
//                                } else if (getDoctorName().isFocusOwner()
//                                        || getDoctorName().getEditor()
//                                                .getEditorComponent()
//                                                .isFocusOwner()) {
//                                    //事業所番号の候補が0件かつ、医師氏名のコンボにフォーカスが当たっている（ユーザ操作の場合）のみ、テキストもクリアする。
//                                    getMasterSource().setData("JIGYOUSHA_NO", "");
                                }

                            }else{
                                getMasterSource().setData("JIGYOUSHA_NO", "");
                                organJigyoushoNo.setModel(new DefaultComboBoxModel());
                            }
                            checkOrganAbstractionVisible();
                        }
                    }
                    // 2006/09/21[Tozo Tanaka] : add end
                    
                    
                    return false;
                }
                // 直前までに選択していた医師として解釈させる
                selectItem = doctor;
                // return false;
            }

            if ((doctors != null) && (selectItem instanceof VRMap)
                    && (selectItem != defaultDoctor)) {
                if (getMasterAffair() == null) {
                    return false;
                }
                IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();
                if (info.getMention() == null) {
                    return false;
                }

                doctor = (VRMap) selectItem;
                VRMap hoken = info.getMention().getSelectedInsurer();
                if (hoken != null) {
                    IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
                    StringBuffer sb = new StringBuffer();
                    sb.append("SELECT");
                    sb.append(" *");
                    sb.append(" FROM");
                    sb.append(" JIGYOUSHA");
                    sb.append(" WHERE");
                    sb.append("(");
                    sb.append("DR_CD = ");
                    sb.append(IkenshoCommon.getDBSafeNumber("DR_CD", doctor));
                    sb.append(")AND(");
                    sb.append("INSURER_NO = ");
                    sb.append(IkenshoCommon
                            .getDBSafeString("INSURER_NO", hoken));
                    sb.append(")AND(");
                    sb.append("INSURER_TYPE = ");
                    sb.append(IkenshoCommon
                            .getDBSafeNumber("INSURER_TYPE", hoken));
                    sb.append(")");
                    jigyoushas = (VRArrayList) dbm.executeQuery(sb.toString());
                    dbm.finalize();

                    IkenshoCommon.applyComboModel(organJigyoushoNo,
                            new VRHashMapArrayToConstKeyArrayAdapter(
                                    jigyoushas, "JIGYOUSHA_NO"));
                    // 2006/09/20[Tozo Tanaka] : remove begin
//                    if ((organJigyoushoNo.getItemCount() > 0)
//                            && (organJigyoushoNo.getSelectedIndex() < 0)) {
//                        Object val = organJigyoushoNo.getItemAt(0);
//                        organJigyoushoNo.setSelectedItem(val);
//
//                        doctor.setData("JIGYOUSHA_NO", String.valueOf(val));
//                    }
                        // 2006/09/20[Tozo Tanaka] : remove end
                    // 2006/09/20[Tozo Tanaka] : add begin
                    if (organJigyoushoNo.getItemCount() > 0){
                        Object val = organJigyoushoNo.getItemAt(0);
                        doctor.setData("JIGYOUSHA_NO", String.valueOf(val));
                        organJigyoushoNo.setSelectedItem(val);
                        getMasterSource().setData("JIGYOUSHA_NO", String.valueOf(val));
                    }else if(getDoctorName().isFocusOwner()|| getDoctorName().getEditor().getEditorComponent().isFocusOwner()){
                        //事業所番号の候補が0件かつ、医師氏名のコンボにフォーカスが当たっている（ユーザ操作の場合）のみ、テキストもクリアする。
                        getMasterSource().setData("JIGYOUSHA_NO", "");
                    }
                    // 2006/09/20[Tozo Tanaka] : add end
                } else {
                    // 2006/09/20[Tozo Tanaka] : add begin
                    getMasterSource().setData("JIGYOUSHA_NO", "");
                    // 2006/09/20[Tozo Tanaka] : add end
                    organJigyoushoNo.setModel(new DefaultComboBoxModel());
                }
            } else {
                doctor = defaultDoctor;
                organJigyoushoNo.setModel(new DefaultComboBoxModel());
            }

            checkOrganAbstractionVisible();

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }
        return true;
    }

    /**
     * 事業所番号の補足説明文の表示状態を切り替えます。
     * 
     * @throws ParseException 解析例外
     */
    protected void checkOrganAbstractionVisible() throws ParseException {

        if (organJigyoushoNo.getItemCount() > 0) {
            // 事業者番号を選択可能なら警告無し
            organJigyoushoNoAbstraction.setVisible(false);
            organJigyoushoNoRegistAbstraction1.setVisible(false);
            organJigyoushoNoRegistAbstraction2.setVisible(false);
        } else {

            if (IkenshoCommon.isBillSeted((VRMap) getMasterSource())) {
                // 請求用件を満たしているが事業所がなければ事業所情報無しの警告
                organJigyoushoNoAbstraction.setVisible(false);
                organJigyoushoNoRegistAbstraction1.setVisible(true);
                organJigyoushoNoRegistAbstraction2.setVisible(true);
            } else {
                IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();
                VRMap insurer = info.getMention().getSelectedInsurer();

                Object obj = getDoctorName().getEditor().getItem();
                if ((insurer != null) && (obj != null) && (!"".equals(obj))) {
                    // 医師氏名は選択されていないが、履歴情報として保持しているなら事業所情報無しの警告
                    organJigyoushoNoAbstraction.setVisible(false);
                    organJigyoushoNoRegistAbstraction1.setVisible(true);
                    organJigyoushoNoRegistAbstraction2.setVisible(true);
                } else {
                    // 医師氏名の履歴もなければ保険者を選ぶよう警告
                    organJigyoushoNoAbstraction.setVisible(true);
                    organJigyoushoNoRegistAbstraction1.setVisible(false);
                    organJigyoushoNoRegistAbstraction2.setVisible(false);
                }
            }
        }

    }

    protected boolean canCallRegistDoctor() {
        if (ACMessageBox
                .show(
                        "既存の医療機関を更新する場合（事業所番号を登録する場合含む）\n[メインメニュー]-[医療機関情報登録/更新]にて行って下さい。\n\n新規の医療機関の登録を行いますか？",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
            return false;
        }
        return true;
    }

    private void jbInit() throws Exception {
    	  // 2009/01/06 [Mizuki Tsutsumi] : edit begin
    	////////// コントロール初期化 //////////
    	//医師番号
        organDoctorNos.setBeginText("（医師番号");
        organDoctorNo.setBindPath("DR_NO");
        organDoctorNo.setColumns(10);
        organDoctorNo.setEditable(false);
        organDoctorNos.add(organDoctorNo, null);

        
        //開設者氏名
        organOpeners.setText("開設者氏名");
        organOpener.setEditable(false);
        organOpener.setColumns(20);
        organOpener.setBindPath("KAISETUSHA_NM");
        organOpeners.add(organOpener, null);
        
        //診療所・病院区分
        organTypes.setText("診療所・病院区分");
        organType.setEditable(false);
        organType.setColumns(20);
        organType.setBindPath("MI_KBN");
        organType.setFormat(IkenshoConstants.FORMAT_SHINRYOUJYO_TYPE);
        organTypes.add(organType, null);

        //事業所番号
        organJigyoushoNosLayout.setHgap(2);
        organJigyoushoNosLayout.setAutoWrap(false);
        organJigyoushoNos.setText("事業所番号");
        organJigyoushoNos.setLayout(organJigyoushoNosLayout);
        organJigyoushoNo.setPreferredSize(new Dimension(130, 19));
        organJigyoushoNo.setEditable(true);
        organJigyoushoNo.setMaxLength(10);
        organJigyoushoNo.setBindPath("JIGYOUSHA_NO");
        organJigyoushoNos.add(organJigyoushoNo, VRLayout.FLOW);
        
        organJigyoushoNoAbstractions.setLayout(new BorderLayout());
        organJigyoushoNoAbstraction.setText("←事業所番号を選択するには保険者を選択してください。");
        organJigyoushoNoAbstraction.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        organJigyoushoNoRegistAbstraction1
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        organJigyoushoNoRegistAbstraction1
                .setText("←該当する保険者についての事業所番号が登録されていません。");
        organJigyoushoNoRegistAbstraction2
                .setText("[メインメニュー]-[医療機関情報登録/更新]にて登録してください。");
        organJigyoushoNoRegistAbstraction2
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        organJigyoushoNoAbstractions.add(organJigyoushoNoAbstraction, BorderLayout.CENTER);
        organJigyoushoNoAbstractions.add(organJigyoushoNoRegistAbstraction1, BorderLayout.NORTH);
        organJigyoushoNoAbstractions.add(organJigyoushoNoRegistAbstraction2, BorderLayout.SOUTH);
        organJigyoushoNos.add(organJigyoushoNoAbstractions, VRLayout.FLOW);

        
        //========== 振込先情報 ==========
        organBankLayout.setFitHLast(true);
        organBankLayout.setAutoWrap(false);
        organBankGroup.setLayout(organBankLayout);
        organBankGroup.setText("振込先情報");

        //金融機関名
        organBankNames.setText("金融機関名");
        organBankName.setEditable(false);
        organBankName.setColumns(25);
        organBankName.setBindPath("BANK_NM");
        organBankNames.add(organBankName, null);

        //支店名
        organBankOffices.setText("支店名");
        organBankOffice.setPreferredSize(new Dimension(131, 20));
        organBankOffice.setEditable(false);
        organBankOffice.setColumns(25);
        organBankOffice.setBindPath("BANK_SITEN_NM");
        organBankOffices.add(organBankOffice, null);

        //名義人
        organBankUsers.setText("名義人");
        organBankUser.setEditable(false);
        organBankUser.setColumns(15);
        organBankUser.setBindPath("FURIKOMI_MEIGI");
        organBankUsers.add(organBankUser, null);

        //口座番号
        organBankAccountNos.setText("口座番号");
        organBankAccountNo.setBindPath("BANK_KOUZA_NO");
        organBankAccountNo.setColumns(25);
        organBankAccountNo.setEditable(false);
        organBankAccountNos.add(organBankAccountNo, null);
        
        //口座種類
        organBankAccountTypes.setText("口座種類");
        organBankAccountType.setFormat(IkenshoConstants.FORMAT_BANK_ACCOUNT_TYPE);
        organBankAccountType.setEditable(false);
        organBankAccountType.setBindPath("BANK_KOUZA_KIND");
//      [ID:0000514][Tozo TANAKA] 2009/09/11 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        organBankAccountType.setColumns(4);
//      [ID:0000514][Tozo TANAKA] 2009/09/11 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        organBankAccountTypes.add(organBankAccountType, null);
    	//==============================
        
        //////////// コントロールの配置 //////////
        getDoctorNameContainer().add(organDoctorNos, BorderLayout.EAST);

        getFollowDoctorContainer().add(organOpeners, VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(organTypes, VRLayout.FLOW_INSETLINE);
        getFollowDoctorContainer().add(organTypeSpacer, VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(organJigyoushoNos, VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(organBankGroup, VRLayout.FLOW_RETURN);

        //振込先情報
        organBankGroup.add(organBankNames, VRLayout.FLOW_INSETLINE_RETURN);
        organBankGroup.add(organBankOffices, VRLayout.FLOW_INSETLINE_RETURN);
        //organBankGroup.add(organBankOfficeNameSpacer, VRLayout.FLOW_INSETLINE_RETURN);
        organBankGroup.add(organBankUsers, VRLayout.FLOW_INSETLINE_RETURN);
        organBankGroup.add(organBankAccountNos, VRLayout.FLOW_INSETLINE);
        organBankGroup.add(organBankAccountTypes, VRLayout.FLOW_INSETLINE_RETURN);
        // 2009/01/06 [Mizuki Tsutsumi] : edit end
        
        //電子化加算対象の事業所であるか
        doctorAddIT.setVisible(false);
        doctorAddIT.setBindPath("DR_ADD_IT");
        getFollowDoctorContainer().add(doctorAddIT, VRLayout.FLOW);

        //医療機関コード
        doctorCode.setVisible(false);
        doctorCode.setBindPath("DR_CD");
        getFollowDoctorContainer().add(doctorCode, VRLayout.FLOW);
    }

    protected boolean changeDoctor(ItemEvent e) {
        try {
            // これまで選択していた医療機関は電子化加算の対象であるか
            
            //2006/09/07 [Tozo Tanaka] : replace begin
            //boolean oldDoctorIsAddIt = isDoctorAddIT();
            IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();
            if (info.getMention() == null) {
                return false;
            }
            int formatKubun = ACCastUtilities.toInt(info.getFormatKubun(), 0);

            boolean oldDoctorIsAddIt = IkenshoCommon.canAddIT(formatKubun,
                    isDoctorAddIT(), info.getMention().getAddITType());
            //2006/09/07 [Tozo Tanaka] : replace end
            
            if (super.changeDoctor(e)) {
                // 医療機関変更ありの場合
                //2006/09/07 [Tozo Tanaka] : replace begin
                //boolean nowDoctorIsAddIt = isDoctorAddIT();
                boolean nowDoctorIsAddIt = IkenshoCommon.canAddIT(formatKubun,
                        isDoctorAddIT(), info.getMention().getAddITType());
                // 2006/09/07 [Tozo Tanaka] : replace end
                if (oldDoctorIsAddIt != nowDoctorIsAddIt) {
                    // 電子化加算対象に変更あり
                    info = (IkenshoIkenshoInfo) getMasterAffair();
                    if (info.getBill() != null) {
                        // 請求タブが定義されている場合
                        double addIT = info.getBill().getShosinAddIT();
                        double shosinHos = info.getBill().getShosinHospital();
                        double shosinSin = info.getBill().getShosinSinryoujo();
                        if (oldDoctorIsAddIt) {
                            // 元は電子化加算あり→電子化加算なし
                            // 初診点数から選択している保険者の電子化加算点数を減算する
                            shosinHos -= addIT;
                            shosinSin -= addIT;
                        } else {
                            // 元は電子化加算なし→電子化加算あり
                            // 初診点数に選択している保険者の電子化加算点数を減算する
                            shosinHos += addIT;
                            shosinSin += addIT;
                        }
                        info.getBill().setShosinHospital(shosinHos);
                        info.getBill().setShosinSinryoujo(shosinSin);
                    }
                }
                return true;
            }
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
        return false;
    }
    
    /**
     * 電子化加算の対象となる医療機関であるかを返します。
     * @return 電子化加算の対象となる医療機関であるか
     */
    public boolean isDoctorAddIT(){
        return doctorAddIT.isSelected();
    }
    /**
     * 電子化加算の対象となる医療機関であるかを設定します。
     * @param val 電子化加算の対象となる医療機関であるか
     */
    public void setDoctorAddIT(boolean val){
        doctorAddIT.setSelected(val);
    }
    
    /**
     * 現在選択中の医療機関コードを返します。
     * @return
     * @throws Exception
     */
    public int getDoctorCode() throws Exception{
        return ACCastUtilities.toInt(doctorCode.getText(),0);
    }
    
    /**
     * 現在選択中の医療機関コードを設定します。
     * @param val
     * @throws Exception
     */
    public void setDoctorCode(int val) throws Exception{
        doctorCode.setText(ACCastUtilities.toString(val));
    }
    
    

}
