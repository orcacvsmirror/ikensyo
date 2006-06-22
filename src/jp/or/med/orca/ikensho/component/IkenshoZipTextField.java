package jp.or.med.orca.ikensho.component;

import java.awt.FlowLayout;
import java.awt.im.InputSubset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.event.IkenshoModifiedCheckTextFieldEvent;
import jp.or.med.orca.ikensho.event.IkenshoModifiedCheckTextFieldListener;
import jp.or.med.orca.ikensho.event.IkenshoZipTextFieldEvent;
import jp.or.med.orca.ikensho.event.IkenshoZipTextFieldListener;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoZipTextField extends JPanel implements
        IkenshoModifiedCheckTextFieldListener, VRBindable {
    private IkenshoModifiedCheckTextField zipUpper = new IkenshoModifiedCheckTextField();
    private IkenshoModifiedCheckTextField zipLower = new IkenshoModifiedCheckTextField();
    private JLabel separator = new VRLabel();
    private JTextField addressTextField;

    public static final int ZIP_EMPTY = 0;
    public static final int ZIP_HAS_ADDRESS = 1;
    public static final int ZIP_FORMAT_ERROR = 2;
    public static final int ZIP_HAS_NO_ADDRESS = 3;

    private String oldZipValue = "";

    public IkenshoZipTextField() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        FlowLayout flowLayout1 = new FlowLayout();
        flowLayout1.setVgap(0);
        flowLayout1.setHgap(0);
        this.setLayout(flowLayout1);
        this.setOpaque(false);

        zipUpper.setText("");
        zipUpper.setColumns(3);
        zipUpper.setMaxLength(3);
        zipUpper.setIMEMode(InputSubset.LATIN_DIGITS);
        zipUpper.setCharType(VRCharType.ONLY_DIGIT);
        zipUpper.addModiliedCheckTextFieldListener(this);
        separator.setText("-");
        zipLower.setText("");
        zipLower.setColumns(4);
        zipLower.setMaxLength(4);
        zipLower.setIMEMode(InputSubset.LATIN_DIGITS);
        zipLower.setCharType(VRCharType.ONLY_DIGIT);
        zipLower.addModiliedCheckTextFieldListener(this);

        this.add(zipUpper, VRLayout.FLOW);
        this.add(separator, VRLayout.FLOW);
        this.add(zipLower, VRLayout.FLOW);
    }

    /**
     * �X�֔ԍ���3�����̕�������擾���܂��B
     * 
     * @return String
     */
    public String getZipUpper() {
        String tmp = zipUpper.getText();
        if (tmp == null) {
            tmp = "";
        }
        return tmp;
    }

    /**
     * �X�֔ԍ���4�����̕�������擾���܂��B
     * 
     * @return String
     */
    public String getZipLower() {
        String tmp = zipLower.getText();
        if (tmp == null) {
            tmp = "";
        }
        return tmp;
    }

    /**
     * �X�֔ԍ����擾���܂��B
     * 
     * @return String
     */
    public String getZip() {
        String upper = getZipUpper();
        String lower = getZipLower();
        if (upper.equals("")) {
            if (lower.equals("")) {
                return "";
            }
        }
        return getZipUpper() + "-" + getZipLower();
    }

    /**
     * ���͂���Ă���X�֔ԍ������ɁA�Z�����擾����
     * 
     * @return �Z��(�擾�ł��Ȃ������ۂ�"")
     */
    public String getAdderss() {
        String zipU = zipUpper.getText();
        String zipL = zipLower.getText();
        String zip = "";
        String addr = "";

        // �����񒷃`�F�b�N
        if (zipU.trim().length() != 3) {
            return "";
        }
        if (zipL.trim().length() != 4) {
            return "";
        }

        // �X�֔ԍ��m��
        zip = zipU + "-" + zipL;

        // DB����Z�����擾
        try {
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sbSql = new StringBuffer();
            sbSql.append(" SELECT");
            sbSql.append(" m_post.address");
            sbSql.append(" FROM");
            sbSql.append(" m_post");
            sbSql.append(" WHERE");
            sbSql.append(" post_cd = '" + zip + "'");
            VRArrayList tbl = (VRArrayList) dbm.executeQuery(sbSql.toString());

            for (int i = 0; i < tbl.getDataSize(); i++) {
                VRMap row = (VRMap) tbl.getData(i);
                addr = row.getData("ADDRESS").toString();
            }
        } catch (Exception e) {
            ACCommon.getInstance().showExceptionMessage(e);
        }

        return addr;
    }

    /**
     * �Z���o�͐�ݒ���擾���܂��B
     * 
     * @return JTextField
     */
    public JTextField getAddressTextField() {
        return addressTextField;
    }

    /**
     * ���͗���enabled���擾���܂��B
     * 
     * @return boolean
     */
    public boolean isEnabled() {
        return zipUpper.isEnabled() && separator.isEnabled()
                && zipLower.isEnabled();
    }

    /**
     * ���͗���editable���擾���܂��B
     * 
     * @return boolean
     */
    public boolean isEditable() {
        return zipUpper.isEditable() && zipLower.isEditable();
    }

    public boolean isChildrenFocusable() {
        return zipUpper.isFocusable() && zipLower.isFocusable();
    }

    /**
     * �X�֔ԍ���3�����ɕ������ݒ肵�܂��B
     * 
     * @param zipUpper �X�֔ԍ���3��
     */
    public void setZipUpper(String zipUpper) {
        this.zipUpper.setText(zipUpper);
    }

    /**
     * �X�֔ԍ���4�����ɕ������ݒ肷��
     * 
     * @param zipLower �X�֔ԍ���4��
     */
    public void setZipLower(String zipLower) {
        this.zipLower.setText(zipLower);
    }

    /**
     * ���͗���enabled��ݒ肵�܂��B
     * 
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled) {
        zipUpper.setEnabled(enabled);
        zipLower.setEnabled(enabled);
        separator.setEnabled(enabled);
    }

    /**
     * ���͗���editable��ݒ肵�܂��B
     * 
     * @param editable boolean
     */
    public void setEditable(boolean editable) {
        zipUpper.setEditable(editable);
        zipLower.setEditable(editable);
    }

    public void setChildrenFocusable(boolean focusable) {
        zipUpper.setFocusable(focusable);
        zipLower.setFocusable(focusable);
    }

    /**
     * �Z���o�͐��ݒ肵�܂��B
     * 
     * @param addressTextField JTextField
     */
    public void setAddressTextField(JTextField addressTextField) {
        this.addressTextField = addressTextField;
    }

    /**
     * �X�֔ԍ��̃t�H�[�}�b�g�̔���E�Z���̎擾�E�C�x���g�̔��s
     */
    private void checkZip() {
        String zip = getZipUpper() + "-" + getZipLower();

        // �ύX��������΃X���[
        if (zip.equals(oldZipValue)) {
            return;
        }
        oldZipValue = zip;

        int addrFlg = 0;
        String addr = "";

        // �X�֔ԍ��̔���
        if (zip.equals("-")) { // ��
            addrFlg = ZIP_EMPTY;
            addr = "";
        } else { // ���͂���Ă���
            // �X�֔ԍ��`�����ǂ����̔���
            Pattern pattern = Pattern.compile("^[0-9]{3}-[0-9]{4}$");
            Matcher matcher = pattern.matcher(zip);
            boolean isMatched = matcher.matches();

            if (isMatched) {
                addr = getAdderss();
                if (addr.equals("")) { // �Y������Z�������݂��Ȃ�
                    addrFlg = ZIP_HAS_NO_ADDRESS;
                } else { // �Z���̎擾�ɐ���
                    addrFlg = ZIP_HAS_ADDRESS;

                    // �擾�����Z�����Z�����ɔ��f
                    if (addressTextField != null) {
                        addressTextField.setText(addr);
                    }
                }
            } else {
                addrFlg = ZIP_FORMAT_ERROR;
                addr = "";
            }
        }

        // �eVRLabelContainer�̐F�ύX
        setParentColor(addrFlg);

        // �C�x���g�̔��s
        fireConvert(new IkenshoZipTextFieldEvent(this, addrFlg, addr));
    }

    /**
     * �eVRLabelContainer�̐F��ύX����
     * 
     * @param addrFlg �X�֔ԍ����茋�ʃt���O
     */
    private void setParentColor(int addrFlg) {
        // �e��VRLabelContainer�łȂ��ꍇ�͏������Ȃ�
        if (!(getParent() instanceof VRLabelContainer)) {
            return;
        }

        VRLabelContainer parent = (VRLabelContainer) getParent();
        ACFrameEventProcesser processer = ACFrame.getInstance()
                .getFrameEventProcesser();
        switch (addrFlg) {
        case ZIP_FORMAT_ERROR:
            // �`�����s���ȏꍇ�͌x���F
            parent.setLabelFilled(true);
            if (processer != null) {
                parent.setForeground(processer.getContainerWarningForeground());
                parent.setBackground(processer.getContainerWarningBackground());
            }
            break;

        default:
            // ���̑��͒ʏ�F
            parent.setLabelFilled(false);
            if (processer != null) {
                parent.setForeground(processer.getContainerDefaultForeground());
                parent.setBackground(processer.getContainerDefaultBackground());
            }
            break;
        }
    }

    /**
     * TextField�̓��e�ύX���̃C�x���g
     * 
     * @param event �C�x���g���
     */
    public void textChanged(IkenshoModifiedCheckTextFieldEvent event) {
        checkZip();

        if (zipUpper.hasFocus()) {
            if (getZipUpper().length() >= zipUpper.getMaxLength()) {
                zipLower.requestFocus();
            }
        }
    }

    transient Vector zipPanelListeners = new Vector();

    public synchronized void addZipPanelListener(IkenshoZipTextFieldListener l) {
        zipPanelListeners.add(l);
    }

    public synchronized void removeZipPanelListener(
            IkenshoZipTextFieldListener l) {
        zipPanelListeners.remove(l);
    }

    /**
     * �X�֔ԍ����ύX���ꂽ�Ƃ��ɔ���
     * 
     * @param event ZipPanelEvent
     */
    protected void fireConvert(IkenshoZipTextFieldEvent event) {
        if (zipPanelListeners != null) {
            Vector listeners = zipPanelListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((IkenshoZipTextFieldListener) listeners.elementAt(i))
                        .convert(event);
            }
        }
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
            ((VRBindEventListener) it.next()).applySource(e);
        }
    }

    protected void fireBindSource() {
        Iterator it = listeners.iterator();
        VRBindEvent e = new VRBindEvent(this);
        while (it.hasNext()) {
            ((VRBindEventListener) it.next()).bindSource(e);
        }
    }

    public Object createSource() {
        return "";
    }

    public void bindSource() throws ParseException {
        // null�`�F�b�N
        Object obj = VRBindPathParser.get(getBindPath(), source);

        zipUpper.setText("");
        zipLower.setText("");
        if (obj == null) {
            return;
        }
        String zip = String.valueOf(obj);
        if (zip.equals("")) {
            return;
        }

        try {
            String[] zips = zip.split(separator.getText());
            zipUpper.setText(zips[0]);
            zipLower.setText(zips[1]);
        } catch (Exception ex) {
        }

        fireBindSource();
    }

    public void applySource() throws ParseException {
        String zip = zipUpper.getText() + separator.getText()
                + zipLower.getText();

        if (zipUpper.getText().equals("")) {
            if (zipLower.getText().equals("")) {
                zip = "";
            }
        }

        if (VRBindPathParser.set(getBindPath(), getSource(), zip)) {
            fireApplySource();
        }
    }

    public boolean isFocusable() {
        return super.isFocusable() || zipLower.isFocusable()
                || zipUpper.isFocusable();
    }

    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        zipLower.setFocusable(focusable);
        zipUpper.setFocusable(focusable);
    }
}
