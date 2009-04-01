package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.component.ACOneDecimalDoubleTextField;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.text.ACOneDecimalDoubleFormat;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoInitialNegativeIntegerTextField;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoBill extends IkenshoTabbableChildAffairContainer {
  private ACOneDecimalDoubleTextField s112 = new ACOneDecimalDoubleTextField();
  private ACTextField s1114 = new ACTextField();
  private ACOneDecimalDoubleTextField s111 = new ACOneDecimalDoubleTextField();
  private IkenshoInitialNegativeIntegerTextField s2 = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField s5 = new IkenshoInitialNegativeIntegerTextField();
  private ACTextField s1119 = new ACTextField();
  private IkenshoInitialNegativeIntegerTextField s11 = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField syoshinTaishou = new IkenshoInitialNegativeIntegerTextField();
  private ACOneDecimalDoubleTextField s1110 = new ACOneDecimalDoubleTextField();
  private IkenshoInitialNegativeIntegerTextField outputPattern = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField s9 = new IkenshoInitialNegativeIntegerTextField();
  private ACGroupBox ikenshoGroupBox4 = new ACGroupBox();
  private ACOneDecimalDoubleTextField s113 = new ACOneDecimalDoubleTextField();
  private IkenshoInitialNegativeIntegerTextField s12 = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField s4 = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField s3 = new IkenshoInitialNegativeIntegerTextField();
  private ACTextField s11112 = new ACTextField();
  private ACGroupBox ikenshoGroupBox1 = new ACGroupBox();
  private IkenshoInitialNegativeIntegerTextField s15 = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField syoshin = new IkenshoInitialNegativeIntegerTextField();
  private ACOneDecimalDoubleTextField s116 = new ACOneDecimalDoubleTextField();
  private ACTextField s1117 = new ACTextField();
  private ACGroupBox ikenshoGroupBox2 = new ACGroupBox();
  private ACTextField s1116 = new ACTextField();
  private IkenshoInitialNegativeIntegerTextField s14 = new IkenshoInitialNegativeIntegerTextField();
  private ACGroupBox ikenshoGroupBox3 = new ACGroupBox();
  private ACTextField s11110 = new ACTextField();
  private ACOneDecimalDoubleTextField s114 = new ACOneDecimalDoubleTextField();
  private ACOneDecimalDoubleTextField s118 = new ACOneDecimalDoubleTextField();
  private ACOneDecimalDoubleTextField shosinHospital = new ACOneDecimalDoubleTextField();
  private IkenshoInitialNegativeIntegerTextField s13 = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField s8 = new IkenshoInitialNegativeIntegerTextField();
  private ACTextField s11111 = new ACTextField();
  private IkenshoInitialNegativeIntegerTextField s16 = new IkenshoInitialNegativeIntegerTextField();
  private ACOneDecimalDoubleTextField shosinSinryoujo = new ACOneDecimalDoubleTextField();
  private ACOneDecimalDoubleTextField s119 = new ACOneDecimalDoubleTextField();
  private IkenshoInitialNegativeIntegerTextField iknCharge = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField s10 = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField s7 = new IkenshoInitialNegativeIntegerTextField();
  private ACOneDecimalDoubleTextField s1113 = new ACOneDecimalDoubleTextField();
  private ACOneDecimalDoubleTextField s117 = new ACOneDecimalDoubleTextField();
  private IkenshoInitialNegativeIntegerTextField s6 = new IkenshoInitialNegativeIntegerTextField();
  private ACTextField s1118 = new ACTextField();
  private ACTextField fdTimestanp = new ACTextField();
  private ACOneDecimalDoubleTextField s115 = new ACOneDecimalDoubleTextField();
  private ACTextField s1115 = new ACTextField();
  private ACGroupBox billInfoGroup = new ACGroupBox();
  private ACGroupBox pointGroup = new ACGroupBox();
  private ACGroupBox ikenshoGroupBox7 = new ACGroupBox();
  private ACGroupBox ikenshoGroupBox8 = new ACGroupBox();
  private ACOneDecimalDoubleTextField shosinAddIT = new ACOneDecimalDoubleTextField();

  // 2009/01/09[Tozo Tanaka] : add begin
  private ACOneDecimalDoubleTextField expXrayDigitalManagement = new ACOneDecimalDoubleTextField();
  private ACOneDecimalDoubleTextField expXrayDigitalFilm = new ACOneDecimalDoubleTextField();
  private ACOneDecimalDoubleTextField expXrayDigitalImaging = new ACOneDecimalDoubleTextField();
  private IkenshoInitialNegativeIntegerTextField xrayDigitalManagement = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField xrayDigitalFilm = new IkenshoInitialNegativeIntegerTextField();
  private IkenshoInitialNegativeIntegerTextField xrayDigitalImaging = new IkenshoInitialNegativeIntegerTextField();
  // 2009/01/09[Tozo Tanaka] : add end

  /**
   * 検査点数グループを返します。
   * @return 検査点数グループ
   */
  public ACGroupBox getPointGroup(){
    return pointGroup;
  }

  /**
   * 請求書出力区分を設定します。
   * @param obj Object
   */
  public void setOutputPattern(Object obj){
    if (obj != null) {
      outputPattern.setText(String.valueOf(obj));
    }
  }
  //2006/02/11[Tozo Tanaka] : add begin
  //TODO canChange?
  /**
   * 初診対象を設定します。
   * @param obj Object
   */
  public void setShoshin(Object obj){
    if (obj != null) {
        syoshin.setText(String.valueOf(obj));
    }else{
        syoshin.setText("0");
    }
  }
  //2006/02/11[Tozo Tanaka] : add end
  
  public IkenshoIkenshoInfoBill() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    ikenshoGroupBox4.setText("Date");
    s9.setBindPath("BLD_KAGAKU_SEIKAGAKUTEKIKENSA");
    outputPattern.setBindPath("OUTPUT_PATTERN");
    s1110.setBindPath("EXP_KIK_KEKK");
    syoshinTaishou.setBindPath("SHOSIN_TAISHOU");
    s11.setBindPath("ZAITAKU_SINKI_CHARGE");
    s1119.setBindPath("BLD_KAGAKU_TEKIYOU");
    s5.setBindPath("BLD_SAISHU");
    s2.setBindPath("XRAY_TANJUN_SATUEI");
    s111.setBindPath("TAX");
    s1114.setBindPath("SKS_INSURER_NM");
    s112.setBindPath("EXP_XRAY_FILM");
    s113.setBindPath("EXP_XRAY_SS");
    shosinAddIT.setBindPath("SHOSIN_ADD_IT");
    s12.setBindPath("ZAITAKU_KEIZOKU_CHARGE");
    s4.setBindPath("XRAY_FILM");
    s3.setBindPath("XRAY_SHASIN_SINDAN");
    s11112.setBindPath("SHOSIN_TEKIYOU");
    ikenshoGroupBox1.setText("String");
    s15.setBindPath("SISETU_SINKI_CHARGE");
    syoshin.setBindPath("SHOSIN");
    s116.setBindPath("EXP_KKK_SKK");
    s1117.setBindPath("ISS_INSURER_NO");
    ikenshoGroupBox2.setText("Integer");
    s1116.setBindPath("ISS_INSURER_NM");
    s14.setBindPath("SISETU_KEIZOKU_CHARGE");
    ikenshoGroupBox3.setText("Double");
    s11110.setBindPath("BLD_IPPAN_TEKIYOU");
    s114.setBindPath("EXP_XRAY_TS");
    s118.setBindPath("EXP_KIK_MKI");
    shosinHospital.setBindPath("SHOSIN_HOSPITAL");
    s13.setBindPath("FD_OUTPUT_KBN");
    s8.setBindPath("BLD_KAGAKU_KETUEKIKAGAKUKENSA");
    s11111.setBindPath("XRAY_TEKIYOU");
    s16.setBindPath("HAKKOU_KBN");
    shosinSinryoujo.setBindPath("SHOSIN_SINRYOUJO");
    s119.setBindPath("EXP_KS");
    iknCharge.setBindPath("IKN_CHARGE");
    s10.setBindPath("NYO_KENSA");
    s7.setBindPath("BLD_IPPAN_EKIKAGAKUTEKIKENSA");
    s1113.setBindPath("SHOSIN_OTHER");
    s117.setBindPath("EXP_KKK_KKK");
    s6.setBindPath("BLD_IPPAN_MASHOU_KETUEKI");
    s1118.setBindPath("NYO_KENSA_TEKIYOU");
    fdTimestanp.setBindPath("FD_TIMESTAMP");
    fdTimestanp.setFormat(IkenshoConstants.FORMAT_ERA_HMS);
    s115.setBindPath("EXP_NITK");
    s1115.setBindPath("SKS_INSURER_NO");
    billInfoGroup.setText("請求情報");
    pointGroup.setText("診察・検査費用点数");
    ikenshoGroupBox7.setText("String");
    ikenshoGroupBox8.setText("Integer");
    ikenshoGroupBox3.add(s1110, null);
    ikenshoGroupBox3.add(shosinSinryoujo, null);
    ikenshoGroupBox3.add(shosinHospital, null);
    ikenshoGroupBox3.add(s1113, null);
    ikenshoGroupBox3.add(s119, null);
    ikenshoGroupBox3.add(s118, null);
    ikenshoGroupBox3.add(s117, null);
    ikenshoGroupBox3.add(s116, null);
    ikenshoGroupBox3.add(s115, null);
    ikenshoGroupBox3.add(s114, null);
    ikenshoGroupBox3.add(s113, null);
    ikenshoGroupBox3.add(s112, null);
    ikenshoGroupBox3.add(s111, null);
    ikenshoGroupBox3.add(shosinAddIT, null);
    ikenshoGroupBox1.add(s11112, null);
    ikenshoGroupBox1.add(s11111, null);
    ikenshoGroupBox1.add(s11110, null);
    ikenshoGroupBox1.add(s1119, null);
    ikenshoGroupBox1.add(s1118, null);
    this.add(billInfoGroup, null);
    billInfoGroup.add(ikenshoGroupBox4, null);
    ikenshoGroupBox4.add(fdTimestanp, null);
    billInfoGroup.add(ikenshoGroupBox2, null);
    billInfoGroup.add(ikenshoGroupBox1, null);
    this.add(pointGroup, null);
    pointGroup.add(ikenshoGroupBox3, null);
    pointGroup.add(ikenshoGroupBox7, null);
    ikenshoGroupBox7.add(s1116, null);
    ikenshoGroupBox7.add(s1115, null);
    ikenshoGroupBox7.add(s1114, null);
    ikenshoGroupBox7.add(s1117, null);
    pointGroup.add(ikenshoGroupBox8, null);
    ikenshoGroupBox8.add(s12, null);
    ikenshoGroupBox8.add(s11, null);
    ikenshoGroupBox8.add(s15, null);
    ikenshoGroupBox8.add(s14, null);
    ikenshoGroupBox2.add(syoshinTaishou, null);
    ikenshoGroupBox2.add(outputPattern, null);
    ikenshoGroupBox2.add(s13, null);
    ikenshoGroupBox2.add(s16, null);
    ikenshoGroupBox2.add(iknCharge, null);
    ikenshoGroupBox2.add(syoshin, null);
    ikenshoGroupBox2.add(s2, null);
    ikenshoGroupBox2.add(s3, null);
    ikenshoGroupBox2.add(s4, null);
    ikenshoGroupBox2.add(s5, null);
    ikenshoGroupBox2.add(s6, null);
    ikenshoGroupBox2.add(s7, null);
    ikenshoGroupBox2.add(s8, null);
    ikenshoGroupBox2.add(s9, null);
    ikenshoGroupBox2.add(s10, null);

    // 2009/01/09[Tozo Tanaka] : add begin
    expXrayDigitalManagement.setBindPath("EXP_XRAY_DIGITAL_MANAGEMENT");
    expXrayDigitalFilm.setBindPath("EXP_XRAY_DIGITAL_FILM");
    expXrayDigitalImaging.setBindPath("EXP_XRAY_DIGITAL_IMAGING");
    xrayDigitalManagement.setBindPath("XRAY_DIGITAL_MANAGEMENT");
    xrayDigitalFilm.setBindPath("XRAY_DIGITAL_FILM");
    xrayDigitalImaging.setBindPath("XRAY_DIGITAL_IMAGING");
    ikenshoGroupBox3.add(expXrayDigitalManagement, null);
    ikenshoGroupBox3.add(expXrayDigitalFilm, null);
    ikenshoGroupBox3.add(expXrayDigitalImaging, null);
    ikenshoGroupBox2.add(xrayDigitalManagement, null);
    ikenshoGroupBox2.add(xrayDigitalFilm, null);
    ikenshoGroupBox2.add(xrayDigitalImaging, null);
    // 2009/01/09[Tozo Tanaka] : add end
 
  }

  /**
   * 電子化加算点数を返します。
   * @return 電子化加算点数
   */
  public double getShosinAddIT(){
      return ACCastUtilities.toDouble(shosinAddIT.getText(), 0);
  }
  /**
   * 電子化加算点数を設定します。
   * @param 電子化加算点数
   */
  public void setShosinAddIT(double val){
      shosinAddIT.setText(ACOneDecimalDoubleFormat.getInstance().format(new Double(val)));
  }
  
  
  /**
   * 病院の初診点数を返します。
   * @return 病院の初診点数
   */
  public double getShosinHospital(){ 
      return  ACCastUtilities.toDouble(shosinHospital.getText(), 0);
  }
  /**
   * 病院の初診点数を設定します。
   * @param 病院の初診点数
   */
  public void setShosinHospital(double val){
      shosinHospital.setText(ACOneDecimalDoubleFormat.getInstance().format(new Double(val)));
  }
  
  /**
   * 診療所の初診点数を返します。
   * @return 診療所の初診点数
   */
  public double getShosinSinryoujo(){ 
      return  ACCastUtilities.toDouble(shosinSinryoujo.getText(), 0);
  }
  /**
   * 診療所の初診点数を設定します。
   * @param 診療所の初診点数
   */
  public void setShosinSinryoujo(double val){
      shosinSinryoujo.setText(ACOneDecimalDoubleFormat.getInstance().format(new Double(val)));
  }


}
