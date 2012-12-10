package jp.nichicom.ac.text;

import java.util.ArrayList;

import javax.swing.text.JTextComponent;

import jp.nichicom.ac.ACConstants;

/**
 * �e�L�X�g�֘A�̔ėp���\�b�h���W�߂��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public class ACTextUtilities {
    /**
     * �o�[�W�������ɂ�����K��̋�؂蕶���ł��B
     */
    public final static String DEFAULT_VERSION_SEPARATER = "\\.";

    private static ACTextUtilities singleton;

    /**
     * �h�b�g(.)��؂�̃o�[�W���������r���A��r���ʂ�Ԃ��܂��B
     * <p>
     * ��r���Z�q�ɂ́A�ȉ��̂����ꂩ���w�肵�܂��B<br/>
     * &lt;�@�@�F�@���傫��<br/>
     * &gt;�@�@�F�@��菬����<br/>
     * &lt;=�@�F�@�ȏ�<br/>
     * &gt;=�@�F�@�ȉ�<br/>
     * ==�@�F�@��v<br/>
     * !=�@ �F�@�s��v
     * </p>
     * <p>
     * �o�[�W�����͋�؂育�Ƃɐ��l�ɕϊ����đ召��r���s���܂��B
     * </p>
     * 
     * @param now ���݂̃o�[�W�������
     * @param operation ��r���Z�q
     * @param value ��r�Ώۂ̃o�[�W�������
     * @return �X�V���K�v�ł��邩
     */
    public static boolean compareVersionText(String now, String operation, String value) {
        return compareVersionText(now, operation, value, DEFAULT_VERSION_SEPARATER);
    }

    /**
     * �w�蕶����؂�̃o�[�W���������r���A��r���ʂ�Ԃ��܂��B
     * <p>
     * ��r���Z�q�ɂ́A�ȉ��̂����ꂩ���w�肵�܂��B<br/>
     * &lt;�@�@�F�@���傫��<br/>
     * &gt;�@�@�F�@��菬����<br/>
     * &lt;=�@�F�@�ȏ�<br/>
     * &gt;=�@�F�@�ȉ�<br/>
     * ==�@�F�@��v<br/>
     * !=�@ �F�@�s��v
     * </p>
     * <p>
     * �o�[�W�����̋�؂蕶���ɂ́A���K�\���`���ŋ�؂蕶�����w�肵�܂��B<br/>
     * �i"."��؂�̏ꍇ�́A"\."���w�肵�܂��B)
     * </p>
     * <p>
     * �o�[�W�����͋�؂育�Ƃɐ��l�ɕϊ����đ召��r���s���܂��B
     * </p>
     * 
     * @param now ���݂̃o�[�W�������
     * @param operation ��r���Z�q
     * @param value ��r�Ώۂ̃o�[�W�������
     * @param versionSeparatoer �o�[�W�����̋�؂蕶��
     * @return �X�V���K�v�ł��邩
     */
    public static boolean compareVersionText(String now, String operation, String value, String versionSeparatoer) {
        if ((now == null) || "".equals(now) || (value == null)
                || "".equals(value)) {
            // NULL�`�F�b�N
            return false;
        }

        String[] nows = now.split(versionSeparatoer);
        String[] values = value.split(versionSeparatoer);
        int nowLen = nows.length;
        int valLen = values.length;
        int maxLen = Math.max(nowLen, valLen);
        if (maxLen == 0) {
            return false;
        }

        // �}�C�i�[�o�[�W�����܂ł��낦��
        int[] nowVers = new int[maxLen];
        int[] valVers = new int[maxLen];
        for (int i = 0; i < nowLen; i++) {
            nowVers[i] = Integer.parseInt(nows[i]);
        }
        for (int i = 0; i < valLen; i++) {
            valVers[i] = Integer.parseInt(values[i]);
        }

        // ��r�J�n
        // ���S��v������
        boolean equal = true;
        for (int i = 0; i < maxLen; i++) {
            if (nowVers[i] != valVers[i]) {
                equal = false;
                break;
            }
        }

        if (operation.indexOf("!") >= 0) {
            // ��v���Ȃ���΍X�V�Ώ�
            if (!equal) {
                return true;
            }
        } else {
            if (operation.indexOf("=") >= 0) {
                // ��v������X�V�Ώ�
                if (equal) {
                    return true;
                }
            } else {
                // ��v�͍X�V�ΏۂłȂ��̂Ɉ�v���Ă����ꍇ�͑ΏۊO
                if (equal) {
                    return false;
                }
            }
            if (operation.indexOf("<") >= 0) {
                // ���݂̃o�[�W�����̂ق����Ⴏ��΍X�V�Ώ�
                boolean match = true;
                for (int i = 0; i < maxLen; i++) {
                    if (nowVers[i] < valVers[i]) {
                        break;
                    }
                    if (nowVers[i] > valVers[i]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
            if (operation.indexOf(">") >= 0) {
                // ���݂̃o�[�W�����̂ق���������΍X�V�Ώ�
                boolean match = true;
                for (int i = 0; i < maxLen; i++) {
                    if (nowVers[i] > valVers[i]) {
                        break;
                    }
                    if (nowVers[i] < valVers[i]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }

        return false;
    }

//    /**
//     * �C���X�^���X���擾���܂��B
//     * 
//     * @deprecated ����static���\�b�h���Ă�ł��������B
//     * @return �C���X�^���X
//     */
//    public static ACTextUtilities getInstance() {
//        if (singleton == null) {
//            singleton = new ACTextUtilities();
//        }
//        return singleton;
//    }

    /**
     * �z������s�����Ō������܂��B
     * 
     * @param array �z��
     * @return ���`����������
     */
    public static String concatLineWrap(String[] array) {
        int end = array.length;
        if (end > 1) {
            // 2�s�ȏ�Ȃ�Ή��s�����Ō���
            StringBuffer sb = new StringBuffer();
            sb.append(array[0]);
            for (int i = 1; i < end; i++) {
                sb.append(ACConstants.LINE_SEPARATOR);
                sb.append(array[i]);
            }
            // ���s�R�[�h�Ō��������������Ԃ�
            return sb.toString();
        } else if (end == 1) {
            // 1�s
            return array[0];
        } else {
            // 0�s
            return "";
        }
    }

    /**
     * �o�C�g�P�ʂŁA���s�����Ɛ܂�Ԃ�����������ɐ؂蕪�����z����ēx���s�����Ō����������ʂ�Ԃ��܂��B
     * 
     * @param text �ϊ��Ώ�
     * @param columns �܂�Ԃ�������
     * @return ���`����������
     */
    public static String concatLineWrapOnByte(String text, int columns) {
        return concatLineWrap(separateLineWrapOnByte(text, columns));
    }

    /**
     * �����P�ʂŁA���s�����Ɛ܂�Ԃ�����������ɐ؂蕪�����z����ēx���s�����Ō����������ʂ�Ԃ��܂��B
     * 
     * @param text �ϊ��Ώ�
     * @param columns �܂�Ԃ�������
     * @return ���`����������
     */
    public static String concatLineWrapOnChar(String text, int columns) {
        return concatLineWrap(separateLineWrapOnChar(text, columns));
    }

    /**
     * ������Null�܂��͋󕶎��ł��邩��Ԃ��܂��B
     * 
     * @param comp �]���������getText�Ŏ擾�\�ȃR���|�[�l���g
     * @return ������Null�܂��͋󕶎��ł��邩
     */
    public static boolean isNullText(JTextComponent comp) {
        return isNullText(comp.getText());
    }

    /**
     * ������Null�܂��͋󕶎��ł��邩��Ԃ��܂��B
     * 
     * @param obj �]��������
     * @return ������Null�܂��͋󕶎��ł��邩
     */
    public static boolean isNullText(Object obj) {
        if (obj == null) {
            return true;
        }
        String text = String.valueOf(obj);
        if (text == null) {
            return true;
        }
        final char HALF_SPACE = ' ';
        final char FULL_SPACE = '�@';
        int i;
        int end = text.length();
        for (i = 0; i < end; i++) {
            char c = text.charAt(i);
            if ((c != HALF_SPACE) && (c != FULL_SPACE)) {
                break;
            }
        }
        if (i >= end) {
            return true;
        }
        return false;
    }

    /**
     * ���s��������ɐ؂蕪�����z���Ԃ��܂��B
     * 
     * @param text �ϊ��Ώ�
     * @return �؂蕪�����z��
     */
    public static String[] separateLineWrap(String text) {
        return separateLineWrapImpl(text, Integer.MAX_VALUE);
    }
    /**
     * ���s��������ɐ؂蕪�����z���Ԃ��܂��B
     * 
     * @param text �ϊ��Ώ�
     * @param limit �ő啪����
     * @return �؂蕪�����z��
     */
    public static String[] separateLineWrapLimit(String text, int limit) {
        return separateLineWrapImpl(text, limit);
    }
    /**
     * �o�C�g�P�ʂŁA���s�����Ɛ܂�Ԃ�����������ɐ؂蕪�����z���Ԃ��܂��B
     * 
     * @param text �ϊ��Ώ�
     * @param columns �܂�Ԃ�������
     * @return �؂蕪�����z��
     */
    public static String[] separateLineWrapOnByte(String text, int columns) {
        ArrayList lines = new ArrayList();

        StringBuffer sb = new StringBuffer();
        int count = 0;
        int end = text.length();
        boolean wrap = false;
        boolean justColumnWrap = false;
        char lastWraper = 0;
        for (int i = 0; i < end; i++) {
            // 1���������o���đ���
            char c = text.charAt(i);
            if ((c == '\r') || (c == '\n')) {
                // ���s����
                if (wrap && (lastWraper != c)) {
                    // ���s��Ԃ��قȂ���s�����i2��������Ȃ���s�����j
                    // �����s��ԉ���
                    wrap = false;
                } else {
                    if(!justColumnWrap){
                        lines.add(sb.toString());
                        sb = new StringBuffer();
                        count = 0;
                    }
                    wrap = true;
                    lastWraper = c;
                }
                justColumnWrap = false;
                continue;
            } else {
                // ���s�����ȊO�����s��Ԃ�����
                wrap = false;
                justColumnWrap = false;
            }
            int byteSize =text.substring(i, i + 1).getBytes().length; 
            count += byteSize;
            if (columns >= count) {
                //���v�o�C�g����1�s�̃o�C�g���ȉ��ł���΍s���Ƃ��Ēǉ�
                sb.append(c);
            }
            if (columns <= count) {
                // �܂�Ԃ�
                lines.add(sb.toString());
                sb = new StringBuffer();
                if (columns < count) {
                    //�s�̃o�C�g���Ɠ����ł͂Ȃ����߂����ꍇ(�c��1�o�C�g�ɑ΂���2�o�C�g������^����)�͎��s�ɑ���
                    sb.append(c);
                    count = byteSize;
                }else{
                    count = 0;
                }
                justColumnWrap = true;
            }
        }
        // ���s��ǉ�
        String line = sb.toString();
        if (line.length() > 0) {
            lines.add(line);
        }

        Object[] array = lines.toArray();
        end = array.length;
        String[] result = new String[end];
        System.arraycopy(array, 0, result, 0, end);
        return result;
    }

    /**
     * �����P�ʂŁA���s�����Ɛ܂�Ԃ�����������ɐ؂蕪�����z���Ԃ��܂��B
     * 
     * @param text �ϊ��Ώ�
     * @param columns �܂�Ԃ�������
     * @return �؂蕪�����z��
     */
    public static String[] separateLineWrapOnChar(String text, int columns) {
        ArrayList lines = new ArrayList();

        StringBuffer sb = new StringBuffer();
        int count = 0;
        int end = text.length();
        boolean wrap = false;
        boolean justColumnWrap = false;
        char lastWraper = 0;
        for (int i = 0; i < end; i++) {
            // 1���������o���đ���
            char c = text.charAt(i);
            if ((c == '\r') || (c == '\n')) {
                // ���s����
                if (wrap && (lastWraper != c)) {
                    // ���s��Ԃ��قȂ���s�����i2��������Ȃ���s�����j
                    // �����s��ԉ���
                    wrap = false;
                } else {
                    if(!justColumnWrap){
                        lines.add(sb.toString());
                        sb = new StringBuffer();
                        count = 0;
                    }
                    wrap = true;
                    lastWraper = c;
                }
                justColumnWrap = false;
                continue;
            } else {
                // ���s�����ȊO�����s��Ԃ�����
                wrap = false;
                justColumnWrap = false;
            }
            sb.append(c);
            count++;
            if (columns <= count) {
                // �܂�Ԃ�
                lines.add(sb.toString());
                sb = new StringBuffer();
                count = 0;
                justColumnWrap = true;
            }
        }
        // ���s��ǉ�
        String line = sb.toString();
        if (line.length() > 0) {
            lines.add(line);
        }

        Object[] array = lines.toArray();
        end = array.length;
        String[] result = new String[end];
        System.arraycopy(array, 0, result, 0, end);
        return result;
    }

    /**
     * null�Ȃ�΋󕶎���Ԃ��A����ȊO�͂��̂܂ܕԂ��܂��B
     * 
     * @param src ��r������
     * @return �ϊ�����
     */
    public static String toBlankIfNull(String src) {
        if (src == null) {
            return "";
        }
        return src;
    }

    /**
     * �O��̔��p�E�S�p�󔒂��������ĕԂ��܂��B
     * 
     * @param src �ϊ���
     * @return �󔒏�������
     */
    public static String trim(String src) {
        if (src == null) {
            return null;
        }

        final char HALF_SPACE = ' ';
        final char FULL_SPACE = '�@';
        int i;
        int end = src.length();
        for (i = 0; i < end; i++) {
            char c = src.charAt(i);
            if ((c != HALF_SPACE) && (c != FULL_SPACE)) {
                for (int j = end - 1; i <= j; j--) {
                    c = src.charAt(j);
                    if ((c != HALF_SPACE) && (c != FULL_SPACE)) {
                        if (i == 0) {
                            if (j == end - 1) {
                                // ���H�Ȃ�
                                return src;
                            }
                            // �㔼trim
                            return src.substring(0, j + 1);
                        }
                        if (j == end - 1) {
                            // �O��trim
                            return src.substring(i);
                        }
                        // �O���trim
                        return src.substring(i, j + 1);
                    }
                }
                return src;
            }
        }
        //���ׂċ�
        return "";
    }

    
    /**
     * ���s��������ɐ؂蕪�����z���Ԃ��܂��B
     * 
     * @param text �ϊ��Ώ�
     * @param limit �ő啪����
     * @return �؂蕪�����z��
     */
    protected static String[] separateLineWrapImpl(String text, int limit) {
        ArrayList lines = new ArrayList();

        StringBuffer sb = new StringBuffer();
        int count = 0;
        int end = text.length();
        boolean wrap = false;
        boolean justColumnWrap = false;
        char lastWraper = 0;
        for (int i = 0; i < end; i++) {
            // 1���������o���đ���
            char c = text.charAt(i);
            if ((c == '\r') || (c == '\n')) {
                // ���s����
                if (wrap && (lastWraper != c)) {
                    // ���s��Ԃ��قȂ���s�����i2��������Ȃ���s�����j
                    // �����s��ԉ���
                    wrap = false;
                } else {
                    if(!justColumnWrap){
                        lines.add(sb.toString());
                        sb = new StringBuffer();
                        count = 0;
                        if(lines.size()>=limit){
                            //�������E
                            int lastIndex= lines.size()-1;
                            lines.set(lastIndex, lines.get(lastIndex)+text.substring(i));
                            break;
                        }
                    }
                    wrap = true;
                    lastWraper = c;
                }
                justColumnWrap = false;
                continue;
            } else {
                // ���s�����ȊO�����s��Ԃ�����
                wrap = false;
                justColumnWrap = false;
            }
            sb.append(c);
            count++;
        }
        // ���s��ǉ�
        String line = sb.toString();
        if (line.length() > 0) {
            lines.add(line);
        }

        Object[] array = lines.toArray();
        end = array.length;
        String[] result = new String[end];
        System.arraycopy(array, 0, result, 0, end);
        return result;
    }    
    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACTextUtilities() {

    }

}
