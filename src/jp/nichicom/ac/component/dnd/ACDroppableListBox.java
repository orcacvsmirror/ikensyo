package jp.nichicom.ac.component.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

import jp.nichicom.ac.component.ACMutableListBox;
import jp.nichicom.ac.component.dnd.event.ACDropEvent;
import jp.nichicom.ac.component.dnd.event.ACDroppableListener;
import jp.nichicom.vr.util.adapter.VRBindSourceAdapter;

public class ACDroppableListBox extends ACMutableListBox  implements
        DragSourceListener, DropTargetListener, DragGestureListener, ACDroppable {
    private boolean moveMode = true;
    private boolean recieveMode = true;
    private int dragRemoveIndex = -1;
    private DragGestureEvent beginDragEvent = null;

    /**
     * �h���b�O�J�n���_�̃C�x���g ��Ԃ��܂��B
     * 
     * @return �h���b�O�J�n���_�̃C�x���g
     */
    public DragGestureEvent getBeginDragEvent() {
        return beginDragEvent;
    }

    /**
     * �h���b�O�J�n���_�̃C�x���g ��ݒ肵�܂��B
     * 
     * @param dragModifiers �h���b�O�J�n���_�̃C�x���g
     */
    public void setBeginDragEvent(DragGestureEvent beginDragEvent) {
        this.beginDragEvent = beginDragEvent;
    }

    /**
     * Constructs a <code>JList</code> with an empty model.
     */
    public ACDroppableListBox() {
        super();
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified, non-<code>null</code> model. All <code>JList</code>
     * constructors delegate to this one.
     * 
     * @param dataModel the data model for this list
     * @exception IllegalArgumentException if <code>dataModel</code> is
     *                <code>null</code>
     */
    public ACDroppableListBox(ListModel dataModel) {
        super(dataModel);
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified array. This constructor just delegates to the
     * <code>ListModel</code> constructor.
     * 
     * @param listData the array of Objects to be loaded into the data model
     */
    public ACDroppableListBox(Object[] listData) {
        super(listData);
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified <code>Vector</code>. This constructor just delegates to the
     * <code>ListModel</code> constructor.
     * 
     * @param listData the <code>Vector</code> to be loaded into the data
     *            model
     */
    public ACDroppableListBox(Vector listData) {
        super(listData);
    }

    /**
     * �v�f��ǉ����܂��B
     * 
     * @param data �v�f
     */
    public void addElement(Object data) {
        Object model = getModel();
        while (model instanceof VRBindSourceAdapter) {
            model = ((VRBindSourceAdapter) model).getAdaptee();
        }
        if (model instanceof List) {
            ((List) model).add(data);
        } else if (model instanceof DefaultListModel) {
            ((DefaultListModel) model).addElement(data);
        }
    }

    /**
     * �v�f��ݒ肵�܂��B
     * 
     * @param data �v�f
     * @param index �s�ԍ�
     */
    public void setElement(Object data, int index) {
        Object model = getModel();
        while (model instanceof VRBindSourceAdapter) {
            model = ((VRBindSourceAdapter) model).getAdaptee();
        }
        if (model instanceof List) {
            ((List) model).set(index, data);
        } else if (model instanceof DefaultListModel) {
            ((DefaultListModel) model).setElementAt(data, index);
        }
    }

    /**
     * �v�f��Ԃ��܂��B
     * 
     * @param index �s�ԍ�
     * @return data �v�f
     */
    public Object getElement(int index) {
        Object model = getModel();
        while (model instanceof VRBindSourceAdapter) {
            model = ((VRBindSourceAdapter) model).getAdaptee();
        }
        if (model instanceof List) {
            return ((List) model).get(index);
        } else if (model instanceof DefaultListModel) {
            return ((DefaultListModel) model).getElementAt(index);
        }
        return null;
    }

    public void dragDropEnd(DragSourceDropEvent dsde) {
        if (isMoveMode()) {
            if (dsde.getDropSuccess()) {
                if (getDragRemoveIndex() >= 0) {
                    removeDragedData();
                }

            }
        }
        setDragRemoveIndex(-1);
    }

    /**
     * �h���b�O�����f�[�^�̍폜�����s���܂��B
     */
    protected void removeDragedData() {
        int idx=getDragRemoveIndex();
        if((getItemCount()>idx)&&(idx>=0)){
            removeElementAt(idx);
        }
    }

    public void dragEnter(DragSourceDragEvent dsde) {
    }

    public void dragEnter(DropTargetDragEvent dtde) {
        dtde.acceptDrag(DnDConstants.ACTION_MOVE);
    }

    public void dragExit(DragSourceEvent dse) {
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void dragGestureRecognized(DragGestureEvent event) {

        Object[] values = getSelectedValues();

        if ((values != null) && (values.length > 0)) {
            setBeginDragEvent(event);
//Object o=event.getTriggerEvent();
            setDragRemoveIndex(getSelectedIndices()[0]);
            event.startDrag(DragSource.DefaultCopyDrop,
                    createTransfer(values), this);
        }

    }
    
    public void dragOver(DragSourceDragEvent dsde) {

    }

    public void dragOver(DropTargetDragEvent dtde) {
    }

    public void drop(DropTargetDropEvent dtde) {
        ACDropEvent event = new ACDropEvent(this);
        event.setDropTargetDropEvent(dtde);
        if (!isRecieveMode()) {
            dtde.rejectDrop();
            fireDropReject(event);
            return;
        }
        
        boolean dropComplete = false;
        try {

            Transferable transferable = dtde.getTransferable();
            if (transferable.isDataFlavorSupported(getSupportedDataFlavor())) {
                dtde.acceptDrop(DnDConstants.ACTION_MOVE);

                Object data = transferable
                        .getTransferData(getSupportedDataFlavor());

                if (data != null) {
                    Object[] values;
                    // �����z��
                    if (data instanceof Object[]) {
                        values = (Object[]) data;
                    } else {
                        values = new Object[] { data };
                    }
                    event.setDropRequestValues(values);

                    int itemEnd = getLastVisibleIndex() + 1;
                    int insertPos = itemEnd;
                    for (int i = getFirstVisibleIndex(); i < itemEnd; i++) {
                        java.awt.Rectangle rect = getCellBounds(i, i);

                        if ((rect != null)
                                && (rect.contains(dtde.getLocation()))) {
                            insertPos = i;
                            break;
                        }
                    }
                    // �͈͊O�Ȃ�ǉ�

                    if (insertPos == getDragRemoveIndex()) {
                        // �ʒu�ϓ��������������Ȃ�
                        // (dropComplete=false�̂܂�)
                    } else {
                        int dropCount= getDropCount(event, values);
                        if (getDragRemoveIndex() > insertPos) {
                            // �}���ʒu���O�Ƀh���b�v�������߁A�폜���ׂ��ʒu���V�t�g����
                            setDragRemoveIndex(getDragRemoveIndex() + dropCount);
                        } else if ((getDragRemoveIndex() >= 0)
                                && (insertPos - dropCount == getDragRemoveIndex())) {
                            // 1����drop�}����drop�ʒu�̑O�ւ̑}���Ȃ̂ŕω����Ȃ����A���̏ꍇ�͌���
                            if (getModel() != null) {
                                if ((insertPos >= getModel().getSize())&&(!canInnerLastItemDrop())) {
                                    // �Ō�̗v�f������ɉ��փh���b�v���������Ȃ�
                                    dtde.getDropTargetContext().dropComplete(
                                            false);
                                    return;
                                }
                            }
                            insertPos++;
                        }

                        int end = values.length;
                        int insCount = 0;
                        ArrayList alloweds = new ArrayList();
                        if (getDragRemoveIndex() < 0) {
                            // �����X�g�����drop���ǉ�
                            for (int i = 0; i < end; i++) {
                                if (canOuterDrop(values[i])) {
                                    // �ǉ��\�Ɣ��f���ꂽ�ꍇ
                                    insertElementAt(
                                            filterOuterDropData(event, values[i]),
                                            insertPos + insCount++);
                                    dropComplete = true;
                                    alloweds.add(values[i]);
                                }
                            }
                        } else {
                            // �������g�����drop������ւ�
                            for (int i = 0; i < end; i++) {
                                if (canInnerDrop(values[i])) {
                                    // �ǉ��\�Ɣ��f���ꂽ�ꍇ
                                    Object insData = filterInnerDropData(event, values[i]);
                                    int insPos = insertPos + insCount++;
                                    if(insPos < getItemCount()){
                                        insertElementAt(insData,insPos);
                                    }else{
                                        addElement(insData);
                                    }
                                    dropComplete = true;
                                    alloweds.add(values[i]);
                                }
                            }
                        }
                        event.setDropSuccessValues(alloweds.toArray());
                    }
                }
                dtde.getDropTargetContext().dropComplete(dropComplete);
            } else {
                dtde.rejectDrop();
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            dtde.rejectDrop();
        }
        
        if(dropComplete){
            fireDropSuccess(event);
        }else{
            fireDropReject(event);
        }
    }
    
    /**
     * �Ō�̗v�f������ɉ��̗̈�փh���b�v���鑀�����������Ԃ��܂��B
     * <p>�W���ł�false(�����Ȃ�)�ł��B</p>
     * @return �Ō�̗v�f������ɉ��̗̈�փh���b�v���鑀���������
     */
    protected boolean canInnerLastItemDrop(){
       return false; 
    }
    
    /**
     * �h���b�v���ꂽ���ڐ���Ԃ��܂��B
     * @param e �C�x���g���
     * @param values �h���b�v�f�[�^
     * @return �h���b�v���ꂽ���ڐ�
     */
    protected int getDropCount(ACDropEvent e, Object[] values){
        return values.length;
    }

    /**
     * dropSuccess�C�x���g�𔭉΂��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireDropSuccess(ACDropEvent e) {
        ACDroppableListener[] listeners = getDroppableEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].dropSuccess(e);
        }
    }

    /**
     * dropReject�C�x���g�𔭉΂��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireDropReject(ACDropEvent e) {
        ACDroppableListener[] listeners = getDroppableEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].dropReject(e);
        }
    }
    /**
     * �h���b�v�C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �h���b�v�C�x���g���X�i
     */
    public synchronized ACDroppableListener[] getDroppableEventListeners() {
        return (ACDroppableListener[]) (getListeners(ACDroppableListener.class));
    }

    public void addDroppableListener(ACDroppableListener l) {
        this.listenerList.add(ACDroppableListener.class, l);
    }

    public void removeDroppableListener(ACDroppableListener l) {
        this.listenerList.remove(ACDroppableListener.class, l);
    }
    
    
    public void dropActionChanged(DragSourceDragEvent dsde) {
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    /**
     * �h���b�v�ɂ���Ēǉ������f�[�^��K�v�ɉ����ĉ��H���ĕԂ��܂��B
     * 
     * @param e �h���b�v�C�x���g���
     * @param data �ǉ��f�[�^
     * @return ���H����
     */
    protected Object filterOuterDropData(ACDropEvent e, Object data) {
        return data;
    }

    /**
     * �h���b�v�ɂ���Ĉړ������f�[�^��K�v�ɉ����ĉ��H���ĕԂ��܂��B
     * 
     * @param e �h���b�v�C�x���g���
     * @param data �ǉ��f�[�^
     * @return ���H����
     */
    protected Object filterInnerDropData(ACDropEvent e, Object data) {
        return data;
    }

    /**
     * �v�f��}�����܂��B
     * 
     * @param data �v�f
     * @param index �}���ʒu
     */
    public void insertElementAt(Object data, int index) {
        Object model = getModel();
        while (model instanceof VRBindSourceAdapter) {
            model = ((VRBindSourceAdapter) model).getAdaptee();
        }
        if (model instanceof List) {
            ((List) model).add(index, data);
        } else if (model instanceof DefaultListModel) {
            ((DefaultListModel) model).insertElementAt(data, index);
        }
    }

    /**
     * D&D�ɂ��ړ� ���s�Ȃ����Ԃ��܂��B
     * 
     * @return D&D�ɂ��ړ����s�Ȃ�
     */
    public boolean isMoveMode() {
        return moveMode;
    }

    /**
     * D&D�ɂ��\��t�����󂯓���邩 ��Ԃ��܂��B
     * 
     * @return D&D�ɂ��\��t�����󂯓���邩
     */
    public boolean isRecieveMode() {
        return recieveMode;
    }

    /**
     * �v�f���폜���܂��B
     * 
     * @param index �폜�ʒu
     */
    public void removeElementAt(int index) {
        Object model = getModel();
        while (model instanceof VRBindSourceAdapter) {
            model = ((VRBindSourceAdapter) model).getAdaptee();
        }
        if (model instanceof List) {
            ((List) model).remove(index);
        } else if (model instanceof DefaultListModel) {
            ((DefaultListModel) model).removeElementAt(index);
        }
    }
    
    /**
     * �v�f�����ׂč폜���܂��B
     */
    public void removeAllElement() {
        Object model = getModel();
        while (model instanceof VRBindSourceAdapter) {
            model = ((VRBindSourceAdapter) model).getAdaptee();
        }
        if (model instanceof List) {
            ((List) model).clear();
        } else if (model instanceof DefaultListModel) {
            ((DefaultListModel) model).removeAllElements();
        }
    }
    

    /**
     * D&D�ɂ��ړ� ���s�Ȃ� ��ݒ肵�܂��B
     * 
     * @param moveMode D&D�ɂ��ړ����s�Ȃ�
     */
    public void setMoveMode(boolean moveMode) {
        this.moveMode = moveMode;
    }

    /**
     * D&D�ɂ��\��t�����󂯓���邩 ��ݒ肵�܂��B
     * 
     * @param recieveMode D&D�ɂ��\��t�����󂯓���邩
     */
    public void setRecieveMode(boolean recieveMode) {
        this.recieveMode = recieveMode;
    }

    /**
     * �w��f�[�^�̊O������̃h���b�v�����e���邩��Ԃ��܂��B
     * <p>
     * override���ăJ�X�^�}�C�Y���܂��B
     * </p>
     * 
     * @param data �ǉ��\��̃f�[�^
     * @return �w��f�[�^�̃h���b�v�����e���邩
     */
    protected boolean canOuterDrop(Object data) {
        return true;
    }

    /**
     * �w��f�[�^�̓����ł̃h���b�v�����e���邩��Ԃ��܂��B
     * <p>
     * override���ăJ�X�^�}�C�Y���܂��B
     * </p>
     * 
     * @param data �ړ��\��̃f�[�^
     * @return �w��f�[�^�̃h���b�v�����e���邩
     */
    protected boolean canInnerDrop(Object data) {
        return true;
    }

    /**
     * �h���b�O�ɂ���č폜�����h���b�O���ڈʒu ��Ԃ��܂��B
     * 
     * @return �h���b�O�ɂ���č폜�����h���b�O���ڈʒu
     */
    protected int getDragRemoveIndex() {
        return dragRemoveIndex;
    }

    protected void initComponent() {
        super.initComponent();

        setDropTarget(new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE,
                this, true));
        DragSource dragSource = DragSource.getDefaultDragSource();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setTransferHandler(createTransferHandler());
        if (dragSource != null) {
            dragSource.createDefaultDragGestureRecognizer(this,
                    DnDConstants.ACTION_COPY_OR_MOVE, this);
        }
        setModel(new DefaultListModel());

    }

    /**
     * �h���b�O�ɂ���č폜�����h���b�O���ڈʒu ��ݒ肵�܂��B
     * 
     * @param dragRemoveIndex �h���b�O�ɂ���č폜�����h���b�O���ڈʒu
     */
    protected void setDragRemoveIndex(int dragRemoveIndex) {
        this.dragRemoveIndex = dragRemoveIndex;
    }

    /**
     * �h���b�v��������f�[�^�t���[�o�N���X��Ԃ��܂��B
     * @return �f�[�^�t���[�o�N���X
     */
    protected DataFlavor getSupportedDataFlavor(){
        return DataFlavor.stringFlavor;
    }
    /**
     * �h���b�O�Ɏg�p����t���[�o�����N���X�𐶐����܂��B
     * @param values �h���b�O�f�[�^
     * @return �t���[�o�����N���X
     */ 
    protected Transferable createTransfer(Object[] values){
        return new ACTransfer(values);
    }

    
    /**
     * �g�p����f�[�^�����̂𐶐����܂��B
     * @return �f�[�^������
     */
    protected TransferHandler createTransferHandler(){
        return new ACTransferHandler();
    }

}
