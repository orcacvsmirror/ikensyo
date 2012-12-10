/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


/**
 * VRDateParser���g�p����VRDateParserPattern�̊Ǘ��N���X�ł��B
 * <p>
 * �����̕������̓p�^�[�����W���Ƃ��ĕێ����܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserLocale
 * @see VRDateParserPattern
 */
public class VRDateParserPatterns {

    /**
     * VRDateParserPattern�C���X�^���X�𐶐����܂��B factory method Pattern
     * 
     * @return �C���X�^���X
     */
    protected static VRDateParserPattern createPattern() {
        return new VRDateParserPattern();
    }

    private ArrayList patterns;

    /**
     * �f�t�H���g�R���X�g���N�^
     */
    public VRDateParserPatterns() {
        patterns = new ArrayList();

    }

    /**
     * �p�^�[����ǉ����܂��B
     * 
     * @param pattern �p�^�[��
     * @throws ParseException �����̉�͎��s
     */
    public void addPattern(String pattern) throws ParseException {
        if ((pattern == null) || ("".equals(pattern))) {
            return;
        }
        VRDateParserPattern patternInstance = createPattern();
        patternInstance.setPattern(pattern);
        patterns.add(patternInstance);
    }

    /**
     * �p�^�[���W����Ԃ��܂��B
     * 
     * @return �p�^�[���W��
     */
    public ArrayList getPatterns() {
        return patterns;
    }

    /**
     * ���������͂��ē��t�Ƃ��ĕԂ��܂��B
     * 
     * @param target ��͑Ώ�
     * @param locale �Ώۃ��P�[��
     * @return ���t
     * @throws ParseException ��͎��s
     */
    public Calendar match(String target, VRDateParserLocale locale)
            throws ParseException {

        Iterator it = getPatterns().iterator();
        while (it.hasNext()) {
            Calendar cal = ((VRDateParserPattern) it.next()).match(target,
                    locale.getEras(), locale.getLocale());
            if (cal != null) {
                return cal;
            }
        }
        return null;
    }

    /**
     * �p�^�[���W����ݒ肵�܂��B
     * 
     * @param patterns �p�^�[���W��
     */
    public void setPatterns(ArrayList patterns) {
        this.patterns = patterns;
    }
}