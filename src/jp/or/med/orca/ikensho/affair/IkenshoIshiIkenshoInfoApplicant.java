package jp.or.med.orca.ikensho.affair;

/**
 * 
 * IkenshoIshiIkenshoInfoApplicantです。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/06
 */
public class IkenshoIshiIkenshoInfoApplicant extends
        IkenshoIkenshoInfoApplicantH18 {

    public IkenshoIshiIkenshoInfoApplicant() {
        try {
            jbInit();
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }
    
        private void jbInit() throws Exception {
          getAgreeGroup().setText("主治医として本意見書がサービス利用計画作成に利用されることに");
        }

        /**
         * 介護保険適用範囲内の年齢であるかをチェックします。
         */
        protected void checkWriteDateAge() {
            // メソッドのoverrideにより処理を行わせない。
        }
        
}
