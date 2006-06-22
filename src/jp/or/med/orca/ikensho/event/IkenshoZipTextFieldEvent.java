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
     * @param addr •ÏŠ·‚µ‚½ZŠ(•ÏŠ·‚Å‚«‚È‚©‚Á‚½ê‡‚Í"")
     */
    public IkenshoZipTextFieldEvent(Object source, int addrFlg, String addr) {
        super(source);
        this.addrFlg = addrFlg;
        this.addr = addr;
    }

    /**
     * —LŒø‚È—X•Ö”Ô†‚©(ZŠ‚Ìæ“¾‚É¬Œ÷‚µ‚½‚©)
     * @return true : —LŒø, false : –³Œø
     */
    public int getAddrFlg() {
        return addrFlg;
    }

    /**
     * —X•Ö”Ô†‚©‚çŠl“¾‚µ‚½ZŠ‚ğæ“¾‚·‚é
     * @return —X•Ö”Ô†‚É‘Î‰‚µ‚½ZŠ(æ“¾‚Å‚«‚Ä‚¢‚È‚¢ê‡‚Í"")
     */
    public String getAddr() {
        return addr;
    }
}
