package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;

/**
 * 
 * �ȈՃX�i�b�v�V���b�g�N���X�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/06/22
 */
public class IkenshoIkenshoSimpleSnapshot extends HashMap {
    /**
     * ��r�R���|�[�l���g�i�[�p�ϐ�
     */
    private ArrayList compList = new ArrayList(); 
    
    /**
     * Constructs an empty <tt>HashMap</tt> with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public IkenshoIkenshoSimpleSnapshot() {
        super();
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public IkenshoIkenshoSimpleSnapshot(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and load factor.
     *
     * @param  initialCapacity The initial capacity.
     * @param  loadFactor      The load factor.
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive.
     */
    public IkenshoIkenshoSimpleSnapshot(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Constructs a new <tt>HashMap</tt> with the same mappings as the
     * specified <tt>Map</tt>.  The <tt>HashMap</tt> is created with
     * default load factor (0.75) and an initial capacity sufficient to
     * hold the mappings in the specified <tt>Map</tt>.
     *
     * @param   m the map whose mappings are to be placed in this map.
     * @throws  NullPointerException if the specified map is null.
     */
    public IkenshoIkenshoSimpleSnapshot(Map m) {
        super(m);
    }
    
    /**
     * �X�i�b�v�V���b�g�̑ΏۂƂȂ�R���|�[�l���g���w�肵�܂��B
     * @param c
     */
    public void addComponent(Component c){
        compList.add(c);
    }

    /**
     * �ΏۃR���|�[�l���g���l���擾���L�����܂��B
     */
    public void simpleSnapshot() throws Exception{
        for(int i = 0; i < compList.size();i++){
            Object comp = compList.get(i);
            if (comp instanceof VRBindable) {
                // �V�KMap�p
                VRMap maps = new VRHashMap();
                VRBindable bind = (VRBindable) comp;
                // �o�C���h�p�X���L�[�ɂ��ē����̃\�[�X��ޔ�����B
                VRMap taihi = (VRHashMap)bind.getSource();
                bind.setSource(maps);
                bind.applySource();
                bind.setSource(taihi);
                this.put(bind.getBindPath(),maps.getData(bind.getBindPath()));
            }
        }
    }
    
    /**
     * �n���ꂽ�I�u�W�F�N�g����͂��đΉ�����o�C���h�p�X�Ɣ�r���܂��B
     * @return
     * @throws Exception
     */
    public boolean simpleIsModefield() throws Exception{
        for(int i = 0; i<compList.size();i++){
            Object comp = compList.get(i);
            if (comp instanceof VRBindable) {
                VRBindable newBind = (VRBindable) comp;
                // �ۑ��ς݂̒l�Ɣ�r
                Iterator it = this.entrySet().iterator();
                Map.Entry key = null;
                // �V�K�\�[�X
                VRMap mapSource = new VRHashMap();
                // �����߂��̂��߈ꎞ�I�ɑޔ�����
                VRMap taihi = (VRHashMap)newBind.getSource();
                newBind.setSource(mapSource);
                // �f�[�^���擾
                newBind.applySource();
                // �\�[�X�����߂�
                newBind.setSource(taihi);
                while(it.hasNext()){
                    key = (Map.Entry)it.next();
                    String sKey = ACCastUtilities.toString(key.getKey());
                    // ����̃L�[�����݂��Ă���ꍇ�͔�r����B
                    if(sKey.equals(newBind.getBindPath())){
                        if(newBind.getSource() instanceof HashMap){
                            // �l���r
                            if(!ACCastUtilities.toString(this.get(sKey)).equals(mapSource.get(newBind.getBindPath()))){
                                // �ύX�_����
                                return true;
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
}
