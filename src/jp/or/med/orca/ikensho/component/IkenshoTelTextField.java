package jp.or.med.orca.ikensho.component;

import java.awt.FlowLayout;
import java.awt.im.InputSubset;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.vr.text.VRCharType;

/** TODO <HEAD_IKENSYO> */
public class IkenshoTelTextField extends JPanel {
    private FlowLayout flowLayout1 = new FlowLayout();
    private ACTextField area = new ACTextField();
    private IkenshoLocalTelTextField number = new IkenshoLocalTelTextField();
    private JLabel sep = new JLabel();

    /**
     * コンストラクタ
     */
    public IkenshoTelTextField() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初期化
     * @throws Exception
     */
    private void jbInit() throws Exception {
        flowLayout1.setVgap(0);
        flowLayout1.setHgap(0);
        this.setLayout(flowLayout1);
        this.setOpaque(false);
        this.add(area);
        this.add(sep);
        this.add(number);

        area.setText("");
        area.setColumns(5);
        area.setMaxLength(5);
        area.setIMEMode(InputSubset.LATIN_DIGITS);
        area.setCharType(VRCharType.ONLY_DIGIT);
        sep.setText("-");
    }

    /**
     * 値をクリアします。
     */
    public void clear() {
        setArea("");
        number.clear();
    }

    /**
     * 市外局番を設定します。
     * @param area String
     */
    public void setArea(String area) {
        this.area.setText(area);
    }

    /**
     * 市内局番を設定します。
     * @param local 市内局番
     */
    public void setLocal(String local) {
        number.setLocal(local);
    }

    /**
     * 加入者番号を設定します。
     * @param subscriver 加入者番号
     */
    public void setSubscriver(String subscriver) {
        number.setSubscriver(subscriver);
    }

    /**
     * 市内局番、加入者番号を設定します。
     * @param number (市内局番)-(加入者番号)
     */
    public void setNumber(String number) {
        this.number.setNumber(number);
    }

    /**
     * 市内局番、加入者番号を設定します。
     * @param local 市内局番
     * @param subscriver 加入者番号
     */
    public void setNumber(String local, String subscriver) {
        setLocal(local);
        setSubscriver(subscriver);
    }

    /**
     * 電話番号を設定します。
     * @param telNo 電話番号
     */
    public void setTelNo(String telNo) {
        try {
            //空白入力時はクリアする
            if (telNo.equals("")) {
                clear();
                return;
            }

            //分解して格納する
            String[] tel = telNo.split(sep.getText());

            if (tel.length == 3) {
                setArea(tel[0]);
                setLocal(tel[1]);
                setSubscriver(tel[2]);
            }
            else if (tel.length == 2) {
                setArea("");
                setLocal(tel[0]);
                setSubscriver(tel[1]);
            }
            else {
                clear();
            }
        }
        catch (Exception ex) {
          ACCommon.getInstance().showExceptionMessage(ex);
        }
    }

    /**
     * 入力欄のenabledを設定します。
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled) {
        area.setEnabled(enabled);
        number.setEnabled(enabled);
        sep.setEnabled(enabled);
    }

    /**
     * 入力欄のeditableを設定します。
     * @param editable boolean
     */
    public void setEditable(boolean editable) {
        area.setEditable(editable);
        number.setEditable(editable);
    }

    public void setChildrenFocusable(boolean focusable) {
        area.setFocusable(focusable);
        number.setChildrenFocusable(focusable);
    }

    /**
     * 市外局番を取得します。
     * @return 市外局番
     */
    public String getArea() {
        String area = this.area.getText();
        if (area == null) {
            area = "";
        }
        return area.trim();
    }

    /**
     * 市内局番を取得します。
     * @return String
     */
    public String getLocal() {
        return number.getLocal();
    }

    /**
     * 加入者番号を取得します。
     * @return String
     */
    public String getSubscriver() {
        return number.getSubscriver();
    }

    /**
     * 市内局番、加入者番号を取得します。
     * @return (市内局番)-(加入者番号)
     */
    public String getNumber() {
        return number.getNumber();
    }

    /**
     * 電話番号を取得します。
     * @return 電話番号
     */
    public String getTelNo() {
        String area = getArea();
        String num = getNumber();

        //未入力時は空白を返す
        if (area.equals("")) {
            if (num.equals("")) {
                return "";
            }
        }
        else {
            area += sep.getText();
        }

        //AAA-LLL-SSSSを返す
        return area + num;
    }

    /**
     * 入力欄のenabledを取得します。
     * @return boolean
     */
    public boolean isEnabled() {
        return area.isEnabled() && sep.isEnabled() && number.isEnabled();
    }

    /**
     * 入力欄のeditableを取得します。
     * @return boolean
     */
    public boolean isEditable() {
        return area.isEditable() && number.isEditable();
    }

    public boolean isChildrenFocusable() {
        return area.isFocusable() && number.isChildrenFocusable();
    }

    public String bindPathArea;
    public String bindPathNumber;

    public void setAutoApplySource(
        boolean autoApplySourceArea, boolean autoApplySourceNumber) {
            area.setAutoApplySource(autoApplySourceArea);
            number.setAutoApplySource(autoApplySourceNumber);
    }

    public void setBindPath(String bindPathArea, String bindPathNumber) {
        area.setBindPath(bindPathArea);
        number.setBindPath(bindPathNumber);
    }

    public void setBindPathArea(String bindPathArea) {
        area.setBindPath(bindPathArea);
    }

    public void setBindPathNumber(String bindPathNumber) {
        number.setBindPath(bindPathNumber);
    }

    public String getBindPathArea() {
        return area.getBindPath();
    }

    public String getBindPathNumber() {
        return number.getBindPath();
    }

    public Object createSource() {
        area.createSource();
        number.createSource();
        return "";
    }

    public void bindSource() throws ParseException {
        area.bindSource();
        number.bindSource();
    }

    public void applySource() throws ParseException {
        area.applySource();
        number.applySource();
    }

    public boolean isFocusable(){
     return super.isFocusable()||area.isFocusable()||number.isChildrenFocusable();
    }
    public void setFocusable(boolean focusable){
      super.setFocusable(focusable);
      area.setFocusable(focusable);
      number.setChildrenFocusable(focusable);
    }

}
