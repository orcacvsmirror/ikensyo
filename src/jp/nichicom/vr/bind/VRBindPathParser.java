/** TODO <HEAD> */
package jp.nichicom.vr.bind;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

/**
 * �o�C���h�p�X��̓N���X�ł��B
 * <p>
 * XML�ɂ�����XPath�̂悤�ɁAVRBindSource���̔C�ӂ̈ʒu�����E��͂���@�\��L���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindSource
 */
public class VRBindPathParser {

    /**
     * �����K�w�������v�f�萔�ł��B
     */
    public static final Integer CURRENT_ELEMENT = new Integer(0);

    /**
     * �G�X�P�[�v�V�[�P���X��\�������萔�ł��B
     */
    protected static final char ESCAPE = '\\';

    /**
     * �w�肵���L�[�ɏ]���āA�\�[�X����f�[�^�����o���܂��B
     * 
     * @param key �L�[
     * @param source ���[�g�\�[�X
     * @return ���s�����ꍇ��false
     * @throws ParseException �p�X��͗�O
     */
    public static Object get(Object key, VRBindSource source)
            throws ParseException {
        if (source == null) {
            return null;
        }
        String keyText=String.valueOf(key);
        if(has(keyText, source)){
            //�����񉻂��Ĉ�v����ꍇ�͗D��
            return get(keyText, source);
        }
        return source.getData(key);
    }

    /**
     * �w�肵���p�X�ɏ]���āA�\�[�X����f�[�^�����o���܂��B
     * <p>
     * �p�X�͈ȉ��̃g�[�N���ɂ���ċ�؂��܂��B <br />
     * "/"�F�K�w����i�K���Ƃ��܂��B <br />
     * "."�F���݂̗v�f��\���܂��B <br />
     * [n]�Fn�Ԗڂ̉��ʗv�f��\���܂��B <br />
     * ���̑��C�ӂ̕�����F��������L�[�Ƃ��鉺�ʗv�f��\���܂��B
     * </p>
     * <p>
     * �y�o�C���h�p�X�̗�z <br />
     * "Node/Leaf"�F�\�[�X���̃L�[�uNode�v�œ��肳���v�f���̃L�[�uLeaf�v�œ��肳���v�f <br />
     * "[1]"�F�\�[�X���̓Y����[1]�œ��肳���v�f <br />
     * "."�F�\�[�X���ꎩ�g
     * </p>
     * 
     * @param path �w��p�X
     * @param source ���[�g�\�[�X
     * @return �f�[�^
     * @throws ParseException �p�X��͗�O
     */
    public static Object get(String path, VRBindSource source)
            throws ParseException {
        return getSource(path, source);
    }

    /**
     * �w�肵���L�[�̒l�����݂��邩��Ԃ��܂��B
     * 
     * @param key �L�[
     * @param source ���[�g�\�[�X
     * @return �w�肵���L�[�̒l�����݂��邩
     * @throws ParseException �p�X��͗�O
     */
    public static boolean has(Object key, VRBindSource source)
            throws ParseException {
        if (source instanceof Map) {
            return ((Map) source).containsKey(key);
        }
        if (key instanceof Integer) {
            return ((Integer) key).intValue() < source.getDataSize();
        }

        return false;

    }

    /**
     * �w�肵���p�X�̃f�[�^�����݂��邩��Ԃ��܂��B
     * <p>
     * �p�X�͈ȉ��̃g�[�N���ɂ���ċ�؂��܂��B <br />
     * "/"�F�K�w����i�K���Ƃ��܂��B <br />
     * "."�F���݂̗v�f��\���܂��B <br />
     * [n]�Fn�Ԗڂ̉��ʗv�f��\���܂��B <br />
     * ���̑��C�ӂ̕�����F��������L�[�Ƃ��鉺�ʗv�f��\���܂��B
     * </p>
     * <p>
     * �y�o�C���h�p�X�̗�z <br />
     * "Node/Leaf"�F�\�[�X���̃L�[�uNode�v�œ��肳���v�f���̃L�[�uLeaf�v�œ��肳���v�f <br />
     * "[1]"�F�\�[�X���̓Y����[1]�œ��肳���v�f <br />
     * "."�F�\�[�X���ꎩ�g
     * </p>
     * 
     * @param path �w��p�X
     * @param source ���[�g�\�[�X
     * @return �w�肵���p�X�̃f�[�^�����݂��邩
     * @throws ParseException �p�X��͗�O
     */
    public static boolean has(String path, VRBindSource source)
            throws ParseException {

        if (source == null) {
            return false;
        }

        ArrayList elements = parsePath(path);
        if (elements.isEmpty()) {
            return false;
        }

        // ��͊���
        VRBindSource currentSource = source;
        int end = elements.size();
        for (int i = 0; i < end; i++) {
            Object element = elements.get(i);

            if (element == CURRENT_ELEMENT) {
                continue;
            }

            if (element instanceof Integer) {
                element = currentSource.getData(((Integer) element).intValue());
            } else {
                if (currentSource instanceof Map) {
                    if (!((Map) currentSource).containsKey(element)) {
                        return false;
                    }
                }
                element = currentSource.getData(element);
            }

            if (element instanceof VRBindSource) {
                currentSource = (VRBindSource) element;
            } else {
                if (i + 1 >= end) {
                    // �ŏI�v�f�ł����true
                    return true;
                }
                return false;
            }
        }

        return true;
    }

    /**
     * �w�肵���L�[�ɏ]���āA�\�[�X�Ƀf�[�^��ݒ肵�܂��B
     * 
     * @param key �L�[
     * @param source ���[�g�\�[�X
     * @param data �f�[�^
     * @return ���s�����ꍇ��false
     * @throws ParseException �p�X��͗�O
     */
    public static boolean set(Object key, VRBindSource source, Object data)
            throws ParseException {
        if (source == null) {
            return false;
        }
        source.setData(key, data);
        return true;
    }

    /**
     * �w�肵���p�X�ɏ]���āA�\�[�X�Ƀf�[�^��ݒ肵�܂��B
     * <p>
     * �w��p�X�����݂��Ȃ��ꍇ�A1�K�w�܂łȂ�V���ɍ쐬���܂��B<br />
     * ��2�K�w�ȏ�̏ꍇ�A�e�K�w��z��Ƃ��ׂ����n�b�V���Ƃ��ׂ����B���ɂȂ邽�ߎ����쐬������O�𔭍s���܂��B
     * </p>
     * <p>
     * �p�X�͈ȉ��̃g�[�N���ɂ���ċ�؂��܂��B <br />
     * "/"�F�K�w����i�K���Ƃ��܂��B <br />
     * "."�F���݂̗v�f��\���܂��B <br />
     * [n]�Fn�Ԗڂ̉��ʗv�f��\���܂��B <br />
     * ���̑��C�ӂ̕�����F��������L�[�Ƃ��鉺�ʗv�f��\���܂��B
     * </p>
     * <p>
     * �y�o�C���h�p�X�̗�z <br />
     * "Node/Leaf"�F�\�[�X���̃L�[�uNode�v�œ��肳���v�f���̃L�[�uLeaf�v�œ��肳���v�f <br />
     * "[1]"�F�\�[�X���̓Y����[1]�œ��肳���v�f <br />
     * "."�F�\�[�X���ꎩ�g
     * </p>
     * 
     * @param path �w��p�X
     * @param source ���[�g�\�[�X
     * @param data �f�[�^
     * @return ���s�����ꍇ��false
     * @throws ParseException �p�X��͗�O
     */
    public static boolean set(String path, VRBindSource source, Object data)
            throws ParseException {

        ArrayList elements = parsePath(path);
        if ((elements == null) || elements.isEmpty()) {
            return false;
        }

        // �K�w���
        Object obj = getLastSource(path, source, elements);
        if (!(obj instanceof VRBindSource)) {
            return false;
        }

        source = (VRBindSource) obj;

        // �ŏI�K�w�̗v�f�ɐݒ肷��
        Object element = elements.get(elements.size() - 1);
        if (element instanceof Integer) {
            source.setData(((Integer) element).intValue(), data);
        } else {
            source.setData(element, data);
        }

        return true;
    }

    /**
     * �o�C���h�p�X����͂��A�ŏI�K�w�̗v�f��Ԃ��܂��B
     * 
     * @param path �w��p�X
     * @param source ���[�g�\�[�X
     * @param elements �p�X�W��
     * @return �ŏI�K�w�̗v�f
     * @throws ParseException �p�X��͗�O
     */
    protected static VRBindSource getLastSource(String path,
            VRBindSource source, ArrayList elements) throws ParseException {

        int last = elements.size() - 1;
        if ((elements == null) || (last <= 0)) {
            return source;
        }

        if (!(source instanceof VRBindSource)) {
            return null;
        }

        // ���
        VRBindSource currentSource = source;
        for (int i = 0; i < last; i++) {
            Object element = elements.get(i);

            if (element == CURRENT_ELEMENT) {
                continue;
            }

            if (element instanceof Integer) {
                element = currentSource.getData(((Integer) element).intValue());
            } else {
                element = currentSource.getData(element);
            }

            if (element instanceof VRBindSource) {
                currentSource = (VRBindSource) element;
            } else {
                throw new ParseException(path, i);
            }
        }
        return currentSource;
    }

    /**
     * �w�肵���p�X���w���o�C���h�\�[�X��Ԃ��܂��B
     * 
     * @param path �w��p�X
     * @param source ���[�g�\�[�X
     * @return �o�C���h�\�[�X
     * @throws ParseException �p�X��͗�O
     */
    protected static Object getSource(String path, VRBindSource source)
            throws ParseException {

        ArrayList elements = parsePath(path);
        if ((elements == null) || elements.isEmpty()) {
            return null;
        }

        // �K�w���
        Object obj = getLastSource(path, source, elements);
        if (!(obj instanceof VRBindSource)) {
            return null;
        }

        source = (VRBindSource) obj;

        // �ŏI�K�w�̗v�f����擾����
        Object element = elements.get(elements.size() - 1);
        if (element instanceof Integer) {
            return source.getData(((Integer) element).intValue());
        } else {
            return source.getData(element);
        }

    }

    /**
     * ���������͂��ăp�X�v�f�𐔒l��������ŕԂ��܂��B
     * 
     * @param element ������
     * @return �p�X�v�f
     * @throws ParseException ��͗�O
     */
    protected static Object parse(String element) throws ParseException {
        final char BEGIN_BRAKET = '[';
        final String CURRENT_DOT = ".";

        if ((element.length()==0)|| CURRENT_DOT.equals(element)) {
            // .
            return CURRENT_ELEMENT;
        }else if (element.charAt(0) == BEGIN_BRAKET) {
            // ����
            return Integer.valueOf(element.substring(1, element.length() - 1));
        } else {
            // ���̑��̕�����
            StringBuffer sb = new StringBuffer();

            int end = element.length();
            for (int i = 0; i < end; i++) {
                char current = element.charAt(i);
                if (current == ESCAPE) {
                    i++;
                    if (i >= end) {
                        // �����̃G�X�P�[�v�͖���
                        continue;
                    }
                    current = element.charAt(i);
                }
                sb.append(current);
            }
            return sb.toString();

        }

    }

    /**
     * �w�肵���o�C���h�p�X����͂��ėv�f�W���Ƃ��ĕԂ��܂��B
     * <p>
     * �p�X�͈ȉ��̃g�[�N���ɂ���ċ�؂��܂��B <br />
     * "/"�F�K�w����i�K���Ƃ��܂��B <br />
     * "."�F���݂̗v�f��\���܂��B <br />
     * [n]�Fn�Ԗڂ̉��ʗv�f��\���܂��B <br />
     * ���̑��C�ӂ̕�����F��������L�[�Ƃ��鉺�ʗv�f��\���܂��B
     * </p>
     * <p>
     * �y�o�C���h�p�X�̗�z <br />
     * "Node/Leaf"�F�\�[�X���̃L�[�uNode�v�œ��肳���v�f���̃L�[�uLeaf�v�œ��肳���v�f <br />
     * "[1]"�F�\�[�X���̓Y����[1]�œ��肳���v�f <br />
     * "."�F�\�[�X���ꎩ�g
     * </p>
     * 
     * @param path �o�C���h�p�X
     * @return �v�f�W��
     * @throws ParseException �p�X��͗�O
     */
    public static ArrayList parsePath(String path) throws ParseException {
        ArrayList elements = new ArrayList();
        if ((path == null) || ("".equals(path))) {
            return elements;
        }

        final char TOKEN = '/';

        StringBuffer sb = new StringBuffer();

        int end = path.length();
        for (int i = 0; i < end; i++) {
            char current = path.charAt(i);
            switch (current) {
            case ESCAPE:
                if (i + 1 >= end) {
                    // �����̃G�X�P�[�v�͖���
                    continue;
                } else if (path.charAt(i + 1) == TOKEN) {
                    // �g�[�N���G�X�P�[�v
                    sb.append(path.substring(i, i + 2));
                    i++;
                    continue;
                }
                break;
            case TOKEN:
                // �؂�o��
                elements.add(parse(sb.toString()));

                sb = new StringBuffer();
                continue;
            }
            sb.append(current);

        }

        String last = sb.toString();
        if (!("".equals(last))) {
            // �Ō�̗v�f��ǉ�
            elements.add(parse(last));
        }
        return elements;
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected VRBindPathParser() {

    }

}