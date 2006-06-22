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
     * @param addr 変換した住所(変換できなかった場合は"")
     */
    public IkenshoZipTextFieldEvent(Object source, int addrFlg, String addr) {
        super(source);
        this.addrFlg = addrFlg;
        this.addr = addr;
    }

    /**
     * 有効な郵便番号か(住所の取得に成功したか)
     * @return true : 有効, false : 無効
     */
    public int getAddrFlg() {
        return addrFlg;
    }

    /**
     * 郵便番号から獲得した住所を取得する
     * @return 郵便番号に対応した住所(取得できていない場合は"")
     */
    public String getAddr() {
        return addr;
    }
}
