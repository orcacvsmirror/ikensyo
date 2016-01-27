package jp.nichicom.ac.core.version;

import java.text.Format;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.text.ACSQLSafeNullToZeroDoubleFormat;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.splash.ACSplash;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.io.VRCSVFile;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

/**
 * �o�[�W�������̍��ق�␳����N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/28
 */
public class ACVersionAdjuster {
    /**
     * SQL���ɂ�����p�����^�ւ̒u�������^�O�ł��B
     */
    public static final String TAG_OF_SQL_PARAMETER = "\\?";

//    /**
//     * �o�[�W�������ɂ������؂蕶���ł��B
//     */
//    public final String TAG_OF_VERSION_SEPARATER = "\\.";

    /**
     * SQL���̔��s������킷�X�V�����^�C�v�ł��B
     */
    public static final String TASK_TYPE_OF_SQL = "sql";

    /**
     * �N�G������������킷��r�Ώۃ^�C�v�ł��B
     */
    public static final String TARGET_TYPE_OF_QUERY_COUNT = "query_count";

    /**
     * �N�G���̐����ۂ�����킷��r�Ώۃ^�C�v�ł��B
     */
    public static final String TARGET_TYPE_OF_QUERY_SUCCESS = "query_success";

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACVersionAdjuster() {
        super();
    }

    /**
     * �o�[�W�������ق�␳���܂��B
     * 
     * @param dbm DB�}�l�[�W��
     * @param taskFile ���ٕ␳��`
     * @param listener ���ٕ␳���X�i
     * @param splash �v���O���X�X�v���b�V��
     * @throws Exception ������O
     */
    public void adjustment(ACDBManager dbm, ACPropertyXML taskFile,
            ACVersionAdjustmentListener listener) throws Exception {
        // �o�[�W�����␳�^�X�N��S����
        int updateCount = taskFile.size();
        int updateIndex = 0;
        Iterator rootIt = taskFile.entrySet().iterator();
        while (rootIt.hasNext()) {
            updateIndex++;
            Map.Entry rootEnt = (Map.Entry) rootIt.next();
            VRMap check = (VRMap) rootEnt.getValue();
            Object obj = VRBindPathParser.get("compares", check);
            if (obj instanceof Map) {
                Map updateHash = new HashMap();
                boolean match = true;
                Iterator it = ((Map) obj).values().iterator();
                while (it.hasNext()) {
                    // ��r�^�X�N�𑖍�
                    VRMap comapare = (VRMap) it.next();
                    String target = String.valueOf(VRBindPathParser.get("target", comapare));
                    String operaiton = String.valueOf(VRBindPathParser.get("operaiton",
                            comapare));
                    String before = String.valueOf(VRBindPathParser.get("before", comapare));

                    if(TARGET_TYPE_OF_QUERY_COUNT.equalsIgnoreCase(target)){
                        //�N�G�����ʂ̌����Ŕ�r
                        VRList result =dbm.executeQuery(String.valueOf(VRBindPathParser.get("process", comapare)));
                        String size;
                        if(result==null){
                            size = "0";
                        }else{
                            size = String.valueOf(result.size()); 
                        }
                        if (!ACTextUtilities.compareVersionText(size, operaiton, before)) {
                            match = false;
                            break;
                        }
                    }else if(TARGET_TYPE_OF_QUERY_SUCCESS.equalsIgnoreCase(target)){
                        //�N�G���̐����ۂŔ�r
                        if("true".equalsIgnoreCase(before)){
                            //���������ꍇ�Ɏ��s
                            before = "1.0";
                        }else{
                            //���s�����ꍇ�Ɏ��s
                            before = "0.0";
                        }
                        String now = "1.0";
                        try{
                            dbm.executeQuery(String.valueOf(VRBindPathParser.get("process", comapare)));
                            //�N�G������
                        }catch(Exception ex){
                            //�N�G�����s
                            now = "0.0";
                        }
                        if (!ACTextUtilities.compareVersionText(now, operaiton, before)) {
                            match = false;
                            break;
                        }
                    }else{
                        // ���݂̃o�[�W�����ƍX�V�Ώۂ̃o�[�W�������r
                        if (!ACTextUtilities.compareVersionText(listener.getVersion(target), operaiton, before)) {
                            match = false;
                            break;
                        }
                        Object after = VRBindPathParser.get("after", comapare);
                        if (after != null) {
                            // �o�[�W�������̏��֗\��
                            updateHash.put(target, String.valueOf(after));
                        }
                    }

                }
                if (match) {
                    // �X�V���ׂ��ꍇ
                    dbm.beginTransaction();
                    try {
                        Map defineMap = new TreeMap();
                        obj = VRBindPathParser.get("define", check);
                        if (obj instanceof Map) {
                            // �ϐ���`
                            it = ((Map) obj).entrySet().iterator();
                            while (it.hasNext()) {
                                // ��`�^�X�N�𑖍�
                                Map.Entry taskEnt = (Map.Entry) it.next();
                                VRMap task = (VRMap) taskEnt.getValue();
                                try {
                                    parseDefine(dbm, defineMap, String
                                            .valueOf(taskEnt.getKey()),
                                            VRBindPathParser.get("type", task),
                                            VRBindPathParser.get("process",
                                                    task));
                                } catch (Exception ex) {
                                    if (!"true".equalsIgnoreCase(String
                                            .valueOf(VRBindPathParser.get(
                                                    "ignoreError", task)))) {
                                        // �G���[�𖳎�����I�v�V�������t������Ă��Ȃ���Η�O
                                        throw ex;
                                    }
                                }
                            }
                        }                        
                        
                        
                        obj = VRBindPathParser.get("tasks", check);
                        if (obj instanceof Map) {
                            String updateName = "[" + updateIndex + "/"
                                    + updateCount + "]"
                                    + String.valueOf(rootEnt.getKey()) + "-";
                            int taskCount = ((Map) obj).size();
                            int taskIndex = 0;
                            it = ((Map) obj).entrySet().iterator();
                            while (it.hasNext()) {
                                // �X�V�^�X�N�𑖍�
                                taskIndex++;
                                Map.Entry taskEnt = (Map.Entry) it.next();
                                String taskName = updateName + "[" + taskIndex
                                        + "/" + taskCount + "]"
                                        + String.valueOf(taskEnt.getKey())
                                        + "-";
                                VRMap task = (VRMap) taskEnt.getValue();
                                try {
                                    adjustment(dbm, listener, defineMap, taskName,
                                            VRBindPathParser.get("type", task),
                                            VRBindPathParser.get("process",
                                                    task), VRBindPathParser
                                                    .get("parameter", task));
                                } catch (Exception ex) {
                                    if (!"true".equalsIgnoreCase(String
                                            .valueOf(VRBindPathParser.get(
                                                    "ignoreError", task)))) {
                                        // �G���[�𖳎�����I�v�V�������t������Ă��Ȃ���Η�O
                                        throw ex;
                                    }
                                }
                            }
                        }
                        // �o�[�W�������̏���
                        boolean canCommit = true;
                        it = updateHash.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry ent = (Map.Entry) it.next();
                            if (!listener.setVersion(String.valueOf(ent
                                    .getKey()), String.valueOf(ent.getValue()))) {
                                // �V�����o�[�W�����̐ݒ�����ۂ��ꂽ�ꍇ
                                canCommit = false;
                                break;
                            }
                        }
                        if (canCommit) {
                            dbm.commitTransaction();
                        } else {
                            dbm.rollbackTransaction();
                        }
                    } catch (Exception ex) {
                        dbm.rollbackTransaction();
                        throw ex;
                    }
                }
            }
        }
        progress(listener, "�o�[�W�����␳����...", -1,-1);
    }
    /**
     * �ϐ���`����͂��܂��B
     * @param dbm DB�}�l�[�W��
     * @param defineMap �ϐ���`��
     * @param defineName ��`�ϐ���
     * @param processType �����^�C�v 
     * @param process ����
     * @throws Exception ������O
     */
    protected void parseDefine(ACDBManager dbm,Map defineMap, String defineName,
            Object processType, Object process)
    throws Exception {
        String typeText = String.valueOf(processType);
        if (TASK_TYPE_OF_SQL.equalsIgnoreCase(typeText)) {
            // SQL��
            String processText = String.valueOf(process);
            VRList list=dbm.executeQuery(processText);
            if(!list.isEmpty()){
                Object row=list.getData();
                if(row instanceof Map){
                    Object[] vals = ((Map)row).values().toArray();
                    if(vals.length==1){
                        //TODO ���݂̓N�G���̖߂�Ƃ���1�t�B�[���h�Ɍ��肵�đΉ�
                        defineMap.put(defineName, vals[0]);
                    }
                }
            }
        }        
    }

    /**
     * ���X�i�̃X�v���b�V���ɐi����񍐂��܂��B
     * 
     * @param listener ���X�i
     * @param taskName �^�X�N��
     * @param index �����ԍ�
     * @param count ������
     */
    protected void progress(ACVersionAdjustmentListener listener,
            String taskName, int index, int count) {
        if ((listener != null)
                && (listener.getProgressSplash() instanceof ACSplash)) {
            ACSplash splash = (ACSplash) listener.getProgressSplash();
            if(count>0){
                taskName = taskName  + "[" + index + "/" + count + "]";
            }
            splash.setMessage(taskName);
        }
    }

    /**
     * �X�V�^�X�N���������܂��B
     * 
     * @param dbm DB�}�l�[�W��
     * @param listener �i�����X�i
     * @param defineMap �ϐ���`
     * @param processType �����^�C�v
     * @param process �X�V����
     * @param paramterFile �X�V�p�����^�t�@�C���p�X
     * @throws Exception ������O
     */
    public void adjustment(ACDBManager dbm,
            ACVersionAdjustmentListener listener, 
            Map defineMap,
            String taskName,
            Object processType, Object process, Object paramterFile)
            throws Exception {
        String typeText = String.valueOf(processType);
        if (TASK_TYPE_OF_SQL.equalsIgnoreCase(typeText)) {
            // SQL��
            String processText = String.valueOf(process);
            
            //��`�ϐ���u��
            Iterator it = defineMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry ent=(Map.Entry)it.next();
                processText = processText.replaceAll(String.valueOf(ent.getKey()), String.valueOf(ent.getValue()));
            }
            
            if ((paramterFile == null) || "".equals(paramterFile)) {
                // �p�����^�t�@�C�����g�p
                progress(listener, taskName, 1, 1);
                dbm.executeUpdate(processText);
            } else {
                // �p�����^�t�@�C���g�p
                VRCSVFile f = new VRCSVFile(String.valueOf(paramterFile));
                f.read(true);
                if (!f.isEmpty()) {
                    int columns = f.getColumnCount();
                    Format[] formats = new Format[columns];
                    for (int i = 0; i < columns; i++) {
                        // �J�����w�b�_����X�L�[�}�����
                        String fmt = f.getColumnName(i);
                        if ("VARCHAR".equalsIgnoreCase(fmt)) {
                            formats[i] = ACConstants.FORMAT_SQL_STRING;
                        } else if ("INTEGER".equalsIgnoreCase(fmt)) {
                            formats[i] = ACConstants.FORMAT_SQL_NULL_TO_ZERO_INTEGER;
                        } else if ("TIMESTAMP".equalsIgnoreCase(fmt)) {
                            formats[i] = ACConstants.FORMAT_SQL_FULL_YMD_HMS;
                        } else if ("DATE".equalsIgnoreCase(fmt)) {
                            formats[i] = ACConstants.FORMAT_SQL_FULL_YMD;
                        } else if ("DOUBLE PRECISION".equalsIgnoreCase(fmt)) {
                            formats[i] = ACSQLSafeNullToZeroDoubleFormat
                                    .getInstance();
                        }
                    }
                    String[] sqls = processText.split(TAG_OF_SQL_PARAMETER);
                    int end = sqls.length;
                    int rows = f.getRowCount();
                    for (int r = 0; r < rows; r++) {
                        // �s�P�ʂɑ���
                        StringBuffer sb = new StringBuffer();
                        for (int c = 0; c < columns; c++) {
                            Object val = f.getValueAt(r, c);
                            Format fmt = formats[c];
                            if (fmt != null) {
                                // ���蓖�Ă�ꂽ�t�H�[�}�b�g������Ώ�����
                                val = fmt.format(val);
                            }
                            sb.append(sqls[c]);
                            sb.append(val);
                        }
                        for (int i = columns; i < end; i++) {
                            sb.append(sqls[i]);
                        }
                        if (sb.length() > 0) {
                            // SQL�����s
                            progress(listener, taskName, r, rows);
                            dbm.executeUpdate(sb.toString());
                        }
                    }
                }
            }
        }
    }

    /**
     * �o�[�W�������ق�␳���܂��B
     * 
     * @param dbm DB�}�l�[�W��
     * @param taskFile ���ٕ␳��`�t�@�C���p�X
     * @param listener ���ٕ␳���X�i
     * @throws Exception ������O
     */
    public void adjustment(ACDBManager dbm, String taskFile,
            ACVersionAdjustmentListener listener) throws Exception {
        ACPropertyXML verAdj = new ACPropertyXML(taskFile);
        if (verAdj.canRead()) {
            verAdj.read();
            adjustment(dbm, verAdj, listener);
        }
    }

//    /**
//     * �h�b�g(.)��؂�̃o�[�W���������r���A�X�V���K�v�ł��邩��Ԃ��܂��B
//     * 
//     * @param now ���݂̃o�[�W�������
//     * @param operation ��r���Z�q
//     * @param value ��r�Ώۂ̃o�[�W�������
//     * @return �X�V���K�v�ł��邩
//     */
//    protected boolean mustUpdate(String now, String operation, String value) {
//        if ((now == null) || "".equals(now) || (value == null)
//                || "".equals(value)) {
//            // NULL�`�F�b�N
//            return false;
//        }
//
//        String[] nows = now.split(TAG_OF_VERSION_SEPARATER);
//        String[] values = value.split(TAG_OF_VERSION_SEPARATER);
//        int nowLen = nows.length;
//        int valLen = values.length;
//        int maxLen = Math.max(nowLen, valLen);
//        if (maxLen == 0) {
//            return false;
//        }
//
//        // �}�C�i�[�o�[�W�����܂ł��낦��
//        int[] nowVers = new int[maxLen];
//        int[] valVers = new int[maxLen];
//        for (int i = 0; i < nowLen; i++) {
//            nowVers[i] = Integer.parseInt(nows[i]);
//        }
//        for (int i = 0; i < valLen; i++) {
//            valVers[i] = Integer.parseInt(values[i]);
//        }
//
//        // ��r�J�n
//        // ���S��v������
//        boolean equal = true;
//        for (int i = 0; i < maxLen; i++) {
//            if (nowVers[i] != valVers[i]) {
//                equal = false;
//                break;
//            }
//        }
//
//        if (operation.indexOf("!") >= 0) {
//            // ��v���Ȃ���΍X�V�Ώ�
//            if (!equal) {
//                return true;
//            }
//        } else {
//            if (operation.indexOf("=") >= 0) {
//                // ��v������X�V�Ώ�
//                if (equal) {
//                    return true;
//                }
//            } else {
//                // ��v�͍X�V�ΏۂłȂ��̂Ɉ�v���Ă����ꍇ�͑ΏۊO
//                if (equal) {
//                    return false;
//                }
//            }
//            if (operation.indexOf("<") >= 0) {
//                // ���݂̃o�[�W�����̂ق����Ⴏ��΍X�V�Ώ�
//                boolean match = true;
//                for (int i = 0; i < maxLen; i++) {
//                    if (nowVers[i] < valVers[i]) {
//                        break;
//                    }
//                    if (nowVers[i] > valVers[i]) {
//                        match = false;
//                        break;
//                    }
//                }
//                if (match) {
//                    return true;
//                }
//            }
//            if (operation.indexOf(">") >= 0) {
//                // ���݂̃o�[�W�����̂ق���������΍X�V�Ώ�
//                boolean match = true;
//                for (int i = 0; i < maxLen; i++) {
//                    if (nowVers[i] > valVers[i]) {
//                        break;
//                    }
//                    if (nowVers[i] < valVers[i]) {
//                        match = false;
//                        break;
//                    }
//                }
//                if (match) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }

}
