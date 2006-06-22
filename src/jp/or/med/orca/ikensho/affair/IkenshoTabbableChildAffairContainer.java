package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComboBox;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoTabbableChildAffairContainer extends VRPanel {
    protected ArrayList innerBindComponents = new ArrayList();
    protected IkenshoTabbableAffairContainer masterAffair;

    /**
     * コンストラクタです。
     */
    public IkenshoTabbableChildAffairContainer() {
        super();
    }

    /**
     * 業務開始時にフォーカスをセットするコンポーネントを返します。
     * 
     * @return 業務開始時にフォーカスをセットするコンポーネント
     */
    public Component getFirstFocusComponent() {
        return null;
    }

    /**
     * コントロールとして警告処理を行ったかを返します。<br />
     * apply前に呼ばれます。
     * 
     * @throws Exception 処理例外
     * @return 警告処理を行ったか
     */
    boolean noControlWarning() throws Exception {
        return true;

    }

    /**
     * コントロールとして更新処理が可能かを返します。<br />
     * apply前に呼ばれます。
     * 
     * @throws Exception 処理例外
     * @return 更新処理が可能か
     */
    boolean noControlError() throws Exception {
        return true;
    }

    /**
     * データとして更新処理が可能かを返します。<br />
     * apply後に呼ばれます。
     * 
     * @throws Exception 処理例外
     * @return 更新処理が可能か
     */
    boolean canDataUpdate() throws Exception {
        return true;
    }

    /**
     * 文字列がNullまたは空文字であるかを返します。
     * 
     * @param text 評価文字列
     * @return 文字列がNullまたは空文字であるか
     */
    protected boolean isNullText(Object text) {
        return IkenshoCommon.isNullText(text);
    }

    /**
     * 追加走査対象のコンポーネントを追加します。<br />
     * VRコンポーネントに対し、コンテナとして明示的に追加したコンポーネントを追加します。
     * 
     * @param comp コンポーネント
     */
    protected void addInnerBindComponent(VRBindable comp) {
        innerBindComponents.add(comp);
    }

    /**
     * 追加走査対象のコンポーネントを削除します。<br />
     * VRコンポーネントに対し、コンテナとして明示的に追加したコンポーネントを削除します。
     * 
     * @param comp コンポーネント
     */
    protected void removeInnerBindComponent(VRBindable comp) {
        innerBindComponents.remove(comp);
    }

    /**
     * 追加走査対象のコンポーネントに参照先ソースを設定します。
     * 
     * @param source 参照先ソース
     */
    protected void setSourceInnerBindComponent(VRBindSource source) {
        Iterator it = innerBindComponents.iterator();
        while (it.hasNext()) {
            ((VRBindable) it.next()).setSource(source);
        }
    }

    /**
     * 追加走査対象のコンポーネントの値を参照先ソースに適用します。
     * 
     * @throws Exception 解析例外
     */
    protected void applySourceInnerBindComponent() throws Exception {
        Iterator it = innerBindComponents.iterator();
        while (it.hasNext()) {
            ((VRBindable) it.next()).applySource();
        }
    }

    /**
     * 追加走査対象のコンポーネントに参照先ソースの値を流し込みます。
     * 
     * @throws Exception 解析例外
     */
    protected void bindSourceInnerBindComponent() throws Exception {
        Iterator it = innerBindComponents.iterator();
        while (it.hasNext()) {
            ((VRBindable) it.next()).bindSource();
        }
    }

    /**
     * 追加走査対象のコンポーネントのデフォルトデータを格納したソースインスタンスを生成します。
     * 
     * @return 子要素インスタンス
     */
    public VRMap createSourceInnerBindComponent() {
        VRMap map = new VRHashMap();
        Iterator it = innerBindComponents.iterator();
        while (it.hasNext()) {
            VRBindable comp = (VRBindable) it.next();
            map.setData(comp.getBindPath(), comp.createSource());
        }
        return map;
    }

    /**
     * コンボへの定型文設定などDBへのアクセスを必要とする初期化処理を生成します。
     * 
     * @param dbm DBManager
     * @throws Exception 処理例外
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {

    }

    /**
     * 基底業務クラスが持つソースを返します。
     * 
     * @return 基底業務クラスが持つソース
     */
    public VRBindSource getMasterSource() {
        return getSource();
    }

    /**
     * 基底業務クラスが持つソースを設定します。
     * 
     * @param masterSource 基底業務クラスが持つソース
     */
    public void setMasterSource(VRBindSource masterSource) {
        setSource(masterSource);
    }

    /**
     * 前画面として遷移してきた時にフォーカスをセットするコンポーネントを返します。
     * 
     * @return 前画面として遷移してきた時にフォーカスをセットするコンポーネント
     */
    public Component getPreviewFocusComponent() {
        return null;
    }

    /**
     * 基底業務クラスを返します。
     * 
     * @return 基底業務クラス
     */
    public IkenshoTabbableAffairContainer getMasterAffair() {
        return masterAffair;
    }

    /**
     * 基底業務クラスを設定します。
     * 
     * @param masterAffair 基底業務クラス
     */
    public void setMasterAffair(IkenshoTabbableAffairContainer masterAffair) {
        this.masterAffair = masterAffair;
    }

    /**
     * 前画面復元用のデータを保存します。
     * 
     * @param param データ格納先
     * @throws Exception 処理例外
     */
    protected void savePreviewData(VRMap param) throws Exception {
        if (getMasterAffair() != null) {
            // 最新を適用
            IkenshoTabbableAffairContainer info = getMasterAffair();
            info.fullApplySource();

            VRBindSource bs = getMasterSource();

            bs.setData("TAB", new Integer(info.getSelctedTabIndex()));
            bs.setData("AFFAIR_MODE", info.getNowMode());
            param.setData("PREV_DATA", bs);
        }
    }

    /**
     * プールした定型文をコンボに設定します。
     * 
     * @param combo コンボ
     * @param code 定型文コード
     */
    protected void applyPoolTeikeibun(JComboBox combo, int code) {
        if (getMasterAffair() != null) {
            getMasterAffair().applyPoolTeikeibun(combo, code);
        }
    }

    /**
     * 検索後、適用を行なう直前に処理を行ないます。
     * 
     * @param dbm DBManager
     * @throws Exception 処理例外
     */
    public void doBeforeBindOnSelected() throws Exception {

    }
}
