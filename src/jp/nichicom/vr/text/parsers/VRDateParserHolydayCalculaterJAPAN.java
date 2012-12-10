/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;



/**
 * �a��p�̕W���j�Փ�����N���X�ł��B
 * <p>
 * �t���̓��A�H���̓��A�U��ւ��x���A�����̋x�������\�ł��B
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
            //3��
            if ((y >= 1900) && (2099 >= y)) {
                int springEquinoxDay = (int) Math.floor(0.24242d * y - Math.floor(y / 4.0d) + 35.84d);
                if (d == springEquinoxDay) {
                    holydays.add(new VRDateParserHolyday("�t���̓�"));
                    return;
                }
            }
            break;
        case Calendar.SEPTEMBER:
            //9��
            if ((y >= 1900) && (2099 >= y)) {
                int autumnalEquinoxDay = (int) Math.floor(0.24204d * y - Math.floor(y / 4.0d) + 39.01d);
                if (d == autumnalEquinoxDay) {
                    holydays.add(new VRDateParserHolyday("�H���̓�"));
                    return;
                }
            }
            break;
        }
        if (parameter == REPEAT_CHECK_FLAG) {
            //�d���ċA�h�~�̂��ߕԋp
            return;
        }
        
        if(!holydays.isEmpty()){
            //���łɑ��̏j�����Y��
            return;
        }

        int wday =cal.get(Calendar.DAY_OF_WEEK); 
        if(wday==Calendar.SUNDAY){
            //���������j�Ȃ�j���ł͂Ȃ�
            return;
        }        
        
        
        Calendar behindDay = Calendar.getInstance();

        //�O���`�F�b�N
        behindDay.setTime(cal.getTime());
        behindDay.add(Calendar.DATE, -1);
        ArrayList behindHolydays = new ArrayList();
        
        if(y<2007){
            //2006�N�܂ł͐U��ւ��x���͌��j�Ɍ���
            if (isEmptyHolyday(behindDay, behindHolydays)) {
                //�O���������Ȃ�j���ɂȂ蓾�Ȃ�
                return;
            }
            if (wday == Calendar.MONDAY) {
                // �U��ւ��x���ɊY��
                holydays.add(new VRDateParserHolyday("�U��ւ��x��"));
                return;
            }
        }else{
            //2007�N����̐U��ւ��x���͏j�������̕���            
            while(true){
                int bwday = behindDay.get(Calendar.DAY_OF_WEEK); 
                if (isEmptyHolyday(behindDay, behindHolydays)) {
                    //�O���͏j���ł͂Ȃ�
                    if(bwday!=Calendar.SUNDAY){
                        //���j�ȊO�Ȃ�U��ւ��x���ɂ͂Ȃ�Ȃ�
                        break;
                    }
                }else{
                    //�O���͏j��
                    if(bwday==Calendar.SUNDAY){
                        //�O���͓��j�̏j��
                        // �U��ւ��x���ɊY��
                        holydays.add(new VRDateParserHolyday("�U��ւ��x��"));
                        return;
                    }
                }
                //����ɑO�����`�F�b�N
                behindDay.add(Calendar.DATE, -1);
            }
            
        }

        //�����`�F�b�N
        behindDay.setTime(cal.getTime());
        behindDay.add(Calendar.DATE, 1);
        if (isEmptyHolyday(behindDay, behindHolydays)) {
            //�������j���łȂ���΍����̋x���ɂ͂Ȃ�Ȃ�
            return;
        }

        if(wday != Calendar.SUNDAY) {
            //���j�łȂ�
            behindDay.setTime(cal.getTime());
            behindDay.add(Calendar.DATE, -1);
            if (!isEmptyHolyday(behindDay, behindHolydays)) {
                //�O���̏j�����`�F�b�N
                boolean behindIsTransferHolyday = false;
                Iterator it=behindHolydays.iterator();
                while(it.hasNext()){
                    VRDateParserHolyday h=(VRDateParserHolyday)it.next();
                    if("�U��ւ��x��".equals(h.getId())){
                        behindIsTransferHolyday = true;
                        break;
                    }
                }
                if(!behindIsTransferHolyday){
                    //�O���̏j���͐U��ւ��x���ȊO�̏ꍇ
                    //�����̋x���ɊY��
                    holydays.add(new VRDateParserHolyday("�����̋x��"));
                }
            }
        }
        return;
    }
    /**
     * �w����ɏj�������݂��Ȃ�����Ԃ��܂��B
     * @param cal ������
     * @param holydays �j����`�~�ϐ�
     * @return �w����ɏj�������݂��Ȃ���
     */
    protected boolean isEmptyHolyday(Calendar cal, List holydays){
        holydays.clear();
        VRDateParser.getLocale(Locale.JAPAN).getHolydays().stockHolyday(cal, holydays, REPEAT_CHECK_FLAG);
        VRDateParser.getLocale(VRDateParser.FREE_LOCALE).getHolydays().stockHolyday(cal, holydays, REPEAT_CHECK_FLAG);
        return holydays.isEmpty();
    }
}