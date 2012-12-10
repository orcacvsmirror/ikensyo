/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;



/**
 * ˜a—ï—p‚Ì•W€jÕ“ú“Á’èƒNƒ‰ƒX‚Å‚·B
 * <p>
 * t•ª‚Ì“úAH•ª‚Ì“úAU‚è‘Ö‚¦‹x“úA‘–¯‚Ì‹x“ú‚ğ“Á’è‰Â”\‚Å‚·B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/06/31
 * @see VRDateParser
 * @see VRDateParserHolydayCalculatable
 */
public class VRDateParserHolydayCalculaterJAPAN implements
        VRDateParserHolydayCalculatable {
    private static final Boolean REPEAT_CHECK_FLAG = new Boolean(true);

    public void stockHolyday(Calendar cal, List holydays, Object parameter) {

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE);

        switch (m) {
        case Calendar.MARCH:
            //3Œ
            if ((y >= 1900) && (2099 >= y)) {
                int springEquinoxDay = (int) Math.floor(0.24242d * y - Math.floor(y / 4.0d) + 35.84d);
                if (d == springEquinoxDay) {
                    holydays.add(new VRDateParserHolyday("t•ª‚Ì“ú"));
                    return;
                }
            }
            break;
        case Calendar.SEPTEMBER:
            //9Œ
            if ((y >= 1900) && (2099 >= y)) {
                int autumnalEquinoxDay = (int) Math.floor(0.24204d * y - Math.floor(y / 4.0d) + 39.01d);
                if (d == autumnalEquinoxDay) {
                    holydays.add(new VRDateParserHolyday("H•ª‚Ì“ú"));
                    return;
                }
            }
            break;
        }
        if (parameter == REPEAT_CHECK_FLAG) {
            //d•¡Ä‹A–h~‚Ì‚½‚ß•Ô‹p
            return;
        }
        
        if(!holydays.isEmpty()){
            //‚·‚Å‚É‘¼‚Ìj“ú‚ªŠY“–
            return;
        }

        int wday =cal.get(Calendar.DAY_OF_WEEK); 
        if(wday==Calendar.SUNDAY){
            //“–“ú‚ª“ú—j‚È‚çj“ú‚Å‚Í‚È‚¢
            return;
        }        
        
        
        Calendar behindDay = Calendar.getInstance();

        //‘O“úƒ`ƒFƒbƒN
        behindDay.setTime(cal.getTime());
        behindDay.add(Calendar.DATE, -1);
        ArrayList behindHolydays = new ArrayList();
        
        if(y<2007){
            //2006”N‚Ü‚Å‚ÍU‚è‘Ö‚¦‹x“ú‚ÍŒ—j‚ÉŒÀ’è
            if (isEmptyHolyday(behindDay, behindHolydays)) {
                //‘O“ú‚ª•½“ú‚È‚çj“ú‚É‚È‚è“¾‚È‚¢
                return;
            }
            if (wday == Calendar.MONDAY) {
                // U‚è‘Ö‚¦‹x“ú‚ÉŠY“–
                holydays.add(new VRDateParserHolyday("U‚è‘Ö‚¦‹x“ú"));
                return;
            }
        }else{
            //2007”N‚©‚ç‚ÌU‚è‘Ö‚¦‹x“ú‚Íj“ú–¾‚¯‚Ì•½“ú            
            while(true){
                int bwday = behindDay.get(Calendar.DAY_OF_WEEK); 
                if (isEmptyHolyday(behindDay, behindHolydays)) {
                    //‘O“ú‚Íj“ú‚Å‚Í‚È‚¢
                    if(bwday!=Calendar.SUNDAY){
                        //“ú—jˆÈŠO‚È‚çU‚è‘Ö‚¦‹x“ú‚É‚Í‚È‚ç‚È‚¢
                        break;
                    }
                }else{
                    //‘O“ú‚Íj“ú
                    if(bwday==Calendar.SUNDAY){
                        //‘O“ú‚Í“ú—j‚Ìj“ú
                        // U‚è‘Ö‚¦‹x“ú‚ÉŠY“–
                        holydays.add(new VRDateParserHolyday("U‚è‘Ö‚¦‹x“ú"));
                        return;
                    }
                }
                //‚³‚ç‚É‘O“ú‚ğƒ`ƒFƒbƒN
                behindDay.add(Calendar.DATE, -1);
            }
            
        }

        //—‚“úƒ`ƒFƒbƒN
        behindDay.setTime(cal.getTime());
        behindDay.add(Calendar.DATE, 1);
        if (isEmptyHolyday(behindDay, behindHolydays)) {
            //—‚“ú‚ªj“ú‚Å‚È‚¯‚ê‚Î‘–¯‚Ì‹x“ú‚É‚Í‚È‚ç‚È‚¢
            return;
        }

        if(wday != Calendar.SUNDAY) {
            //“ú—j‚Å‚È‚¢
            behindDay.setTime(cal.getTime());
            behindDay.add(Calendar.DATE, -1);
            if (!isEmptyHolyday(behindDay, behindHolydays)) {
                //‘O“ú‚Ìj“ú‚ğƒ`ƒFƒbƒN
                boolean behindIsTransferHolyday = false;
                Iterator it=behindHolydays.iterator();
                while(it.hasNext()){
                    VRDateParserHolyday h=(VRDateParserHolyday)it.next();
                    if("U‚è‘Ö‚¦‹x“ú".equals(h.getId())){
                        behindIsTransferHolyday = true;
                        break;
                    }
                }
                if(!behindIsTransferHolyday){
                    //‘O“ú‚Ìj“ú‚ÍU‚è‘Ö‚¦‹x“úˆÈŠO‚Ìê‡
                    //‘–¯‚Ì‹x“ú‚ÉŠY“–
                    holydays.add(new VRDateParserHolyday("‘–¯‚Ì‹x“ú"));
                }
            }
        }
        return;
    }
    /**
     * w’è“ú‚Éj“ú‚ª‘¶İ‚µ‚È‚¢‚©‚ğ•Ô‚µ‚Ü‚·B
     * @param cal ŒŸ¸“ú
     * @param holydays j“ú’è‹`’~Ïæ
     * @return w’è“ú‚Éj“ú‚ª‘¶İ‚µ‚È‚¢‚©
     */
    protected boolean isEmptyHolyday(Calendar cal, List holydays){
        holydays.clear();
        VRDateParser.getLocale(Locale.JAPAN).getHolydays().stockHolyday(cal, holydays, REPEAT_CHECK_FLAG);
        VRDateParser.getLocale(VRDateParser.FREE_LOCALE).getHolydays().stockHolyday(cal, holydays, REPEAT_CHECK_FLAG);
        return holydays.isEmpty();
    }
}