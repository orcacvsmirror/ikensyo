package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.im.InputSubset;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoACTextArea;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaHoumonKangoShijishoIryoukikan extends IkenshoHoumonKangoShijishoIryoukikan {

    protected ACLabelContainer jyohouSyudanContainer;
    private IkenshoOptionComboBox jyohouSyudan;

    /**
     * コンストラクタ
     */
    public IkenshoSeishinkaHoumonKangoShijishoIryoukikan() {
        super();
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初期処理
     */
    private void jbInit() throws Exception {
        // 他ステーションへの指示、たん吸引指示の非表示
        otherStationSijiUmuContainer.setVisible(false);
        otherStationSijiContainer.setVisible(false);
        kyuinStationSijiUmuContainer.setVisible(false);
        kyuinStationSijiContainer.setVisible(false);
        // 主治医との情報交換の手段
        getJyohouSyudanContainer().setText("情報交換の手段");
        getJyohouSyudanContainer().add(getJyohouSyudan(), null);
        getJyohouSyudan().setMaxLength(40);
        getJyohouSyudan().setColumns(40);
        getJyohouSyudan().setIMEMode(InputSubset.KANJI);
        getJyohouSyudan().setBindPath("JYOUHOU_SYUDAN");
        addInnerBindComponent(getJyohouSyudan());
    }

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        applyPoolTeikeibun(getJyohouSyudan(), IkenshoCommon.TEIKEI_JYOUHOU_SYUDAN);
        getJyohouSyudan().setOptionComboBoxParameters("情報交換の手段", IkenshoCommon.TEIKEI_JYOUHOU_SYUDAN, 40);
    }

    /**
     * followDoctorContainerへの追加を定義します。
     */
    protected void addFollowDoctorContainer(){
        getFollowDoctorContainer().add(getIryoukikanHeader(), VRLayout.NORTH);
        getFollowDoctorContainer().add(getKinkyuRenrakuContainer(), VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(getFuzaijiTaiouContainer(), VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(getJyohouSyudanContainer(), VRLayout.FLOW_INSETLINE_RETURN);
    }

    /**
     * jyohouSyudan を返します。
     * @return jyohouSyudan
     */
    protected IkenshoOptionComboBox getJyohouSyudan() {
        if(jyohouSyudan == null){
            jyohouSyudan = new IkenshoOptionComboBox();
        }
        return jyohouSyudan;
    }

    /**
     * jyohouSyudanContainer を返します。
     * @return jyohouSyudanContainer
     */
    protected ACLabelContainer getJyohouSyudanContainer() {
        if(jyohouSyudanContainer == null){
            jyohouSyudanContainer = new ACLabelContainer();
        }
        return jyohouSyudanContainer;
    }

    /**
     * 医師変更時処理
     * @return 医師変更時処理結果
     */
    protected boolean changeDoctor(ItemEvent e) {
        //バインド対象から一時的に除外する。
        VRBindSource oldSource = getJyohouSyudan().getSource();
        getJyohouSyudan().setSource(null);
        boolean returnValue = super.changeDoctor(e);
        getJyohouSyudan().setSource(oldSource);

        return returnValue;
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End