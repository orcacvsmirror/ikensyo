package jp.or.med.orca.ikensho.event;

import java.util.EventObject;
/** TODO <HEAD_IKENSYO> */
public class IkenshoZipTextFieldEvent extends EventObject {
    private int addrFlg;
    private String addr;

    /**
     *
     * @param source Object
     * @param addrFlg .ZIP_*
     * @param addr �ϊ������Z��(�ϊ��ł��Ȃ������ꍇ��"")
     */
    public IkenshoZipTextFieldEvent(Object source, int addrFlg, String addr) {
        super(source);
        this.addrFlg = addrFlg;
        this.addr = addr;
    }

    /**
     * �L���ȗX�֔ԍ���(�Z���̎擾�ɐ���������)
     * @return true : �L��, false : ����
     */
    public int getAddrFlg() {
        return addrFlg;
    }

    /**
     * �X�֔ԍ�����l�������Z�����擾����
     * @return �X�֔ԍ��ɑΉ������Z��(�擾�ł��Ă��Ȃ��ꍇ��"")
     */
    public String getAddr() {
        return addr;
    }
}
