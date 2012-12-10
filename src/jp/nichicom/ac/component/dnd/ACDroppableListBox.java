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
     * ドラッグ開始時点のイベント を返します。
     * 
     * @return ドラッグ開始時点のイベント
     */
    public DragGestureEvent getBeginDragEvent() {
        return beginDragEvent;
    }

    /**
     * ドラッグ開始時点のイベント を設定します。
     * 
     * @param dragModifiers ドラッグ開始時点のイベント
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
     * 要素を追加します。
     * 
     * @param data 要素
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
     * 要素を設定します。
     * 
     * @param data 要素
     * @param index 行番号
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
     * 要素を返します。
     * 
     * @param index 行番号
     * @return data 要素
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
     * ドラッグしたデータの削除を実行します。
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
                    // 強制配列化
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
                    // 範囲外なら追加

                    if (insertPos == getDragRemoveIndex()) {
                        // 位置変動無し→何もしない
                        // (dropComplete=falseのまま)
                    } else {
                        int dropCount= getDropCount(event, values);
                        if (getDragRemoveIndex() > insertPos) {
                            // 挿入位置より前にドロップしたため、削除すべき位置もシフトする
                            setDragRemoveIndex(getDragRemoveIndex() + dropCount);
                        } else if ((getDragRemoveIndex() >= 0)
                                && (insertPos - dropCount == getDragRemoveIndex())) {
                            // 1個後ろへdrop挿入→drop位置の前への挿入なので変化しないが、この場合は交換
                            if (getModel() != null) {
                                if ((insertPos >= getModel().getSize())&&(!canInnerLastItemDrop())) {
                                    // 最後の要素をさらに下へドロップ→何もしない
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
                            // 他リストからのdrop→追加
                            for (int i = 0; i < end; i++) {
                                if (canOuterDrop(values[i])) {
                                    // 追加可能と判断された場合
                                    insertElementAt(
                                            filterOuterDropData(event, values[i]),
                                            insertPos + insCount++);
                                    dropComplete = true;
                                    alloweds.add(values[i]);
                                }
                            }
                        } else {
                            // 自分自身からのdrop→入れ替え
                            for (int i = 0; i < end; i++) {
                                if (canInnerDrop(values[i])) {
                                    // 追加可能と判断された場合
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
     * 最後の要素をさらに下の領域へドロップする操作を許すかを返します。
     * <p>標準ではfalse(許可しない)です。</p>
     * @return 最後の要素をさらに下の領域へドロップする操作を許すか
     */
    protected boolean canInnerLastItemDrop(){
       return false; 
    }
    
    /**
     * ドロップされた項目数を返します。
     * @param e イベント情報
     * @param values ドロップデータ
     * @return ドロップされた項目数
     */
    protected int getDropCount(ACDropEvent e, Object[] values){
        return values.length;
    }

    /**
     * dropSuccessイベントを発火します。
     * 
     * @param e イベント情報
     */
    protected void fireDropSuccess(ACDropEvent e) {
        ACDroppableListener[] listeners = getDroppableEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].dropSuccess(e);
        }
    }

    /**
     * dropRejectイベントを発火します。
     * 
     * @param e イベント情報
     */
    protected void fireDropReject(ACDropEvent e) {
        ACDroppableListener[] listeners = getDroppableEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].dropReject(e);
        }
    }
    /**
     * ドロップイベントリスナを返します。
     * 
     * @return ドロップイベントリスナ
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
     * ドロップによって追加されるデータを必要に応じて加工して返します。
     * 
     * @param e ドロップイベント情報
     * @param data 追加データ
     * @return 加工結果
     */
    protected Object filterOuterDropData(ACDropEvent e, Object data) {
        return data;
    }

    /**
     * ドロップによって移動されるデータを必要に応じて加工して返します。
     * 
     * @param e ドロップイベント情報
     * @param data 追加データ
     * @return 加工結果
     */
    protected Object filterInnerDropData(ACDropEvent e, Object data) {
        return data;
    }

    /**
     * 要素を挿入します。
     * 
     * @param data 要素
     * @param index 挿入位置
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
     * D&Dによる移動 を行なうか返します。
     * 
     * @return D&Dによる移動を行なう
     */
    public boolean isMoveMode() {
        return moveMode;
    }

    /**
     * D&Dによる貼り付けを受け入れるか を返します。
     * 
     * @return D&Dによる貼り付けを受け入れるか
     */
    public boolean isRecieveMode() {
        return recieveMode;
    }

    /**
     * 要素を削除します。
     * 
     * @param index 削除位置
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
     * 要素をすべて削除します。
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
     * D&Dによる移動 を行なう を設定します。
     * 
     * @param moveMode D&Dによる移動を行なう
     */
    public void setMoveMode(boolean moveMode) {
        this.moveMode = moveMode;
    }

    /**
     * D&Dによる貼り付けを受け入れるか を設定します。
     * 
     * @param recieveMode D&Dによる貼り付けを受け入れるか
     */
    public void setRecieveMode(boolean recieveMode) {
        this.recieveMode = recieveMode;
    }

    /**
     * 指定データの外部からのドロップを許容するかを返します。
     * <p>
     * overrideしてカスタマイズします。
     * </p>
     * 
     * @param data 追加予定のデータ
     * @return 指定データのドロップを許容するか
     */
    protected boolean canOuterDrop(Object data) {
        return true;
    }

    /**
     * 指定データの内部でのドロップを許容するかを返します。
     * <p>
     * overrideしてカスタマイズします。
     * </p>
     * 
     * @param data 移動予定のデータ
     * @return 指定データのドロップを許容するか
     */
    protected boolean canInnerDrop(Object data) {
        return true;
    }

    /**
     * ドラッグによって削除されるドラッグ項目位置 を返します。
     * 
     * @return ドラッグによって削除されるドラッグ項目位置
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
     * ドラッグによって削除されるドラッグ項目位置 を設定します。
     * 
     * @param dragRemoveIndex ドラッグによって削除されるドラッグ項目位置
     */
    protected void setDragRemoveIndex(int dragRemoveIndex) {
        this.dragRemoveIndex = dragRemoveIndex;
    }

    /**
     * ドロップを許可するデータフレーバクラスを返します。
     * @return データフレーバクラス
     */
    protected DataFlavor getSupportedDataFlavor(){
        return DataFlavor.stringFlavor;
    }
    /**
     * ドラッグに使用するフレーバ処理クラスを生成します。
     * @param values ドラッグデータ
     * @return フレーバ処理クラス
     */ 
    protected Transferable createTransfer(Object[] values){
        return new ACTransfer(values);
    }

    
    /**
     * 使用するデータ搬送体を生成します。
     * @return データ搬送体
     */
    protected TransferHandler createTransferHandler(){
        return new ACTransferHandler();
    }

}
