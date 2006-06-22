package jp.or.med.orca.ikensho.component;

import java.awt.FlowLayout;
import java.awt.im.InputSubset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.text.VRCharType;

/** TODO <HEAD_IKENSYO> */
public class IkenshoLocalTelTextField extends JPanel implements VRBindable {
    private ACTextField local = new ACTextField();
    private ACTextField subscriber = new ACTextField();
    private JLabel sep = new JLabel();

    /**
     * コンストラクタ
     */
    public IkenshoLocalTelTextField() {
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
        FlowLayout flowLayout1 = new FlowLayout();
        flowLayout1.setVgap(0);
        flowLayout1.setHgap(0);
        this.setLayout(flowLayout1);
        this.setOpaque(false);
        this.add(local);
        this.add(sep);
        this.add(subscriber);

        local.setText("");
        local.setColumns(4);
        local.setMaxLength(4);
        local.setIMEMode(InputSubset.LATIN_DIGITS);
        local.setCharType(VRCharType.ONLY_DIGIT);
        sep.setText("-");
        subscriber.setText("");
        subscriber.setColumns(4);
        subscriber.setMaxLength(4);
        subscriber.setIMEMode(InputSubset.LATIN_DIGITS);
        subscriber.setCharType(VRCharType.ONLY_DIGIT);
    }

    /**
     * 値をクリアします。
     */
    public void clear() {
        setLocal("");
        setSubscriver("");
    }

    /**
     * 市内局番を設定します。
     * @param local 市内局番
     */
    public void setLocal(String local) {
        this.local.setText(local);
    }

    /**
     * 加入者番号を設定します。
     * @param subscriver 加入者番号
     */
    public void setSubscriver(String subscriver) {
        this.subscriber.setText(subscriver);
    }

    /**
     * 電話番号を設定します。
     * @param number 電話番号
     */
    public void setNumber(String number)  {
        try {
            //空白入力時はクリアする
            if (number.equals("")) {
                clear();
                return;
            }

            //分解して格納する
            String[] tel = number.split(sep.getText());

            if (tel.length == 2) {
                setLocal(tel[0]);
                setSubscriver(tel[1]);
            }
            else if(tel.length == 1) {
                //XXX- や -YYYY 対応
                clear(); //クリア
                int p = number.lastIndexOf(sep.getText()); //最後の区切り文字位置を取得
                if (p == 0) {
                    setSubscriver(tel[0]);
                }
                else if( p == number.length() - 1) {
                    setLocal(tel[0]);
                }
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
        local.setEnabled(enabled);
        subscriber.setEnabled(enabled);
        sep.setEnabled(enabled);
    }

    /**
     * 入力欄のeditableを設定します。
     * @param editable boolean
     */
    public void setEditable(boolean editable) {
        local.setEditable(editable);
        subscriber.setEditable(editable);
    }

    public void setChildrenFocusable(boolean focusable) {
        local.setFocusable(focusable);
        subscriber.setFocusable(focusable);
    }

    /**
     * 市内局番を取得します。
     * @return 市内局番
     */
    public String getLocal() {
        String local = this.local.getText();
        if (local == null) {
            local = "";
        }
        return local.trim();
    }

    /**
     * 加入者番号を取得します。
     * @return 加入者番号
     */
    public String getSubscriver() {
        String subscriver = this.subscriber.getText();
        if (subscriver == null) {
            subscriver = "";
        }
        return subscriver.trim();
    }

    /**
     * 電話番号を取得します。
     * @return 電話番号
     */
    public String getNumber() {
        String local = getLocal();
        String subscriver = getSubscriver();

        //未入力時は空白を返す
        if (local.equals("")) {
            if (subscriver.equals("")) {
                return "";
            }
        }

        //LLL-SSSSを返す
        return local + sep.getText() + subscriver;
    }

    /**
     * 入力欄のenabledを取得します。
     * @return boolean
     */
    public boolean isEnabled() {
        return local.isEnabled() && sep.isEnabled() && subscriber.isEnabled();
    }

    /**
     * 入力欄のeditableを取得します。
     * @return boolean
     */
    public boolean isEditable() {
        return local.isEditable() && subscriber.isEditable();
    }

    public boolean isChildrenFocusable() {
        return local.isFocusable() && subscriber.isFocusable();
    }

    protected boolean autoApplySource = false;
    protected String bindPath;
    protected ArrayList listeners = new ArrayList();
    protected VRBindSource source;

    public void addBindEventListener(VRBindEventListener listener) {
        listeners.add(listener);
    }

    public String getBindPath() {
        return bindPath;
    }

    public VRBindSource getSource() {
        return source;
    }

    public boolean isAutoApplySource() {
        return this.autoApplySource;
    }

    public void removeBindEventListener(VRBindEventListener listener) {
        listeners.remove(listener);
    }

    public void setAutoApplySource(boolean autoApplySource) {
        this.autoApplySource = autoApplySource;
    }

    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
    }

    public void setSource(VRBindSource source) {
        this.source = source;
    }

    protected void fireApplySource() {
        Iterator it = listeners.iterator();
        VRBindEvent e = new VRBindEvent(this);
        while (it.hasNext()) {
            ( (VRBindEventListener) it.next()).applySource(e);
        }
    }

    protected void fireBindSource() {
        Iterator it = listeners.iterator();
        VRBindEvent e = new VRBindEvent(this);
        while (it.hasNext()) {
            ( (VRBindEventListener) it.next()).bindSource(e);
        }
    }

    public Object createSource() {
        return "";
    }

    public void bindSource() throws ParseException {
        //nullチェック
        Object obj = VRBindPathParser.get(getBindPath(), source);
        if (obj == null) {
          clear();
          return;
        }

        //値を設定
        this.setNumber(String.valueOf(obj));

        //イベント発行
        fireBindSource();
    }

    public void applySource() throws ParseException {
        if (VRBindPathParser.set(getBindPath(), getSource(), this.getNumber())) {
            fireApplySource();
        }
    }
}
