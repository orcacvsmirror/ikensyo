package jp.or.med.orca.ikensho.affair;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.util.ACDateUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;


// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
public class IkenshoTokubetsuHoumonKangoShijishoApplicant extends
        IkenshoHoumonKangoShijishoApplicant {

    protected ACButton twoWeekButton;
    
    public IkenshoTokubetsuHoumonKangoShijishoApplicant() {
        super();
        // 要介護認定の状況は消す
        youkaigoJoukyouGrp.setVisible(false);
        // 2週間設定ボタンの追加
        getTwoWeekButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // 入力チェック
                if ("".equals(sijiKikanFrom.getYear())
                        || "".equals(sijiKikanFrom.getMonth())
                        || "".equals(sijiKikanFrom.getDay())) {
                    ACMessageBox.showExclamation("指示期間「開始日付」に誤りがあります。");
                    sijiKikanFrom.requestChildFocus();
                    return;
                }
                // 日付型で取得
                Date fromDate = sijiKikanFrom.getDate();
                if(fromDate instanceof Date) {
                    // 2週間分追加
                     sijiKikanTo.setDate(ACDateUtilities.addDay(fromDate, 13));
                } else {
                    ACMessageBox.showExclamation("指示期間「開始日付」に誤りがあります。");
                    sijiKikanFrom.requestChildFocus();
                }
            }
          });
    }

    protected String getHoumonSijiSyoText() {
        return "特別訪問看護指示書";
    }

    protected String getSijiKikanContainerText() {
        return "特別看護指示期間";
    }

    /**
     * 訪問看護指示期間コンテナにコンポーネントを追加します。
     *
     */
    protected void addSijiKikanContainer() {
        sijiKikanContainer.add(sijiKikanFroms, VRLayout.FLOW);
        sijiKikanContainer.add(sijiKikanSep, VRLayout.FLOW);
        sijiKikanContainer.add(sijiKikanTos, VRLayout.FLOW);
        sijiKikanContainer.add(getTwoWeekButton(), VRLayout.FLOW);
        sijiKikanContainer.add(sijiKikanClear, VRLayout.FLOW);
    }

    /**
     * 2週間ボタンを取得します。
     * 
     * @return
     */
    public ACButton getTwoWeekButton() {
        if(twoWeekButton == null){
            twoWeekButton = new ACButton();
            twoWeekButton.setText("2週間");
            twoWeekButton.setMnemonic('2');
        }
        return twoWeekButton;
    }

    
}
// [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
