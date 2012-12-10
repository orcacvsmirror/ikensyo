package jp.nichicom.ac.util.splash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import jp.nichicom.ac.util.ACMessageBoxDialog;
/**
 * システム共有スプラッシュチェインです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/03/07
 */
public class ACSplashChaine  {
    private static ACSplashChaine singleton;
    private List splashList;
    
    public void finalize(){
        closeAll();
    }
    
    /**
     * スプラッシュ一覧 を返します。
     * @return splashes スプラッシュ一覧
     */
    public List getList() {
        if(splashList==null){
            splashList=new ArrayList();
        }
        return splashList;
    }
    /**
     * コンストラクタです。
     */
    protected ACSplashChaine() {
        super();
    }
    /**
     * 唯一のインスタンスを返します。
     * @return インスタンス
     */
    public static ACSplashChaine getInstance(){
        if( singleton==null){
            singleton = new ACSplashChaine();
        }
        return singleton; 
    }

    /**
     * 最後に登録したスプラッシュを返します。
     * @return 最後に登録したスプラッシュ
     */
    public ACMessageBoxDialog getLastItem() {
        List list = getList();
        if(list.size()>0){
            return (ACMessageBoxDialog)list.get(list.size()-1);
        }
        return null;
    }

    /**
     * 登録されているすべてのスプラッシュを解放します。
     */
    public void closeAll(){
        Iterator it=iterator();
        while(it.hasNext()){
            ACSplashable spl=(ACSplashable)it.next();
            spl.close();
        }
//        clear();
    }
    /**
     * 指定番号のスプラッシュを解放します。
     * @param index 番号
     */
    public void close(int index){
        ((ACSplashable)remove(index)).close();
    }
    
    /**
     * Returns the number of elements in this list.
     *
     * @return  the number of elements in this list.
     */
    public int size() {
        return getList().size();
    }

    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    protected void clear() {
        getList().clear();
    }
    /**
     * Tests if this list has no elements.
     *
     * @return  <tt>true</tt> if this list has no elements;
     *          <tt>false</tt> otherwise.
     */
    public boolean isEmpty() {
        return getList().isEmpty();
    }
    /**
     * Returns the element at the specified position in this list.
     *
     * @param  index index of element to return.
     * @return the element at the specified position in this list.
     * @throws    IndexOutOfBoundsException if index is out of range <tt>(index
     *        &lt; 0 || index &gt;= size())</tt>.
     */
    public ACSplashable get(int index) {
        return (ACSplashable)getList().get(index);
    }
    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to removed.
     * @return the element that was removed from the list.
     * @throws    IndexOutOfBoundsException if index out of range <tt>(index
     *        &lt; 0 || index &gt;= size())</tt>.
     */
    public ACSplashable remove(int index) {
        return (ACSplashable)getList().remove(index);
    }
    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted.
     * @param element element to be inserted.
     * @throws    IndexOutOfBoundsException if index is out of range
     *        <tt>(index &lt; 0 || index &gt; size())</tt>.
     */
    public void add(int index, ACSplashable element) {
        getList().add(index, element);
    }
    /**
     * Searches for the first occurence of the given argument, testing 
     * for equality using the <tt>equals</tt> method. 
     *
     * @param   elem   an object.
     * @return  the index of the first occurrence of the argument in this
     *          list; returns <tt>-1</tt> if the object is not found.
     * @see     Object#equals(Object)
     */
    public int indexOf(ACSplashable o) {
        return getList().indexOf(o);
    }
    /**
     * Returns the index of the last occurrence of the specified object in
     * this list.
     *
     * @param   elem   the desired element.
     * @return  the index of the last occurrence of the specified object in
     *          this list; returns -1 if the object is not found.
     */
    public int lastIndexOf(ACSplashable o) {
        return getList().lastIndexOf(o);
    }
    /**
     * Appends the specified element to the end of this list.
     *
     * @param o element to be appended to this list.
     * @return <tt>true</tt> (as per the general contract of Collection.add).
     */
    public boolean add(ACSplashable o) {
       return getList().add(o);
    }
    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     *
     * @param elem element whose presence in this List is to be tested.
     * @return  <code>true</code> if the specified element is present;
     *      <code>false</code> otherwise.
     */
    public boolean contains(ACSplashable o) {
        return getList().contains(o);
    }
    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present (optional operation).  More formally,
     * removes an element <tt>e</tt> such that <tt>(o==null ? e==null :
     * o.equals(e))</tt>, if the collection contains one or more such
     * elements.  Returns <tt>true</tt> if the collection contained the
     * specified element (or equivalently, if the collection changed as a
     * result of the call).<p>
     *
     * This implementation iterates over the collection looking for the
     * specified element.  If it finds the element, it removes the element
     * from the collection using the iterator's remove method.<p>
     *
     * Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if the iterator returned by this
     * collection's iterator method does not implement the <tt>remove</tt>
     * method and this collection contains the specified object.
     *
     * @param o element to be removed from this collection, if present.
     * @return <tt>true</tt> if the collection contained the specified
     *         element.
     * @throws UnsupportedOperationException if the <tt>remove</tt> method is
     *        not supported by this collection.
     */
    public boolean remove(ACSplashable o) {
        return getList().remove(o);
    }
    /**
     * Returns an iterator over the elements in this list in proper
     * sequence. <p>
     *
     * This implementation returns a straightforward implementation of the
     * iterator interface, relying on the backing list's <tt>size()</tt>,
     * <tt>get(int)</tt>, and <tt>remove(int)</tt> methods.<p>
     *
     * Note that the iterator returned by this method will throw an
     * <tt>UnsupportedOperationException</tt> in response to its
     * <tt>remove</tt> method unless the list's <tt>remove(int)</tt> method is
     * overridden.<p>
     *
     * This implementation can be made to throw runtime exceptions in the face
     * of concurrent modification, as described in the specification for the
     * (protected) <tt>modCount</tt> field.
     *
     * @return an iterator over the elements in this list in proper sequence.
     * 
     * @see #modCount
     */
    public Iterator iterator() {
        return getList().iterator();
    }
    /**
     * Returns an iterator of the elements in this list (in proper sequence).
     * This implementation returns <tt>listIterator(0)</tt>.
     * 
     * @return an iterator of the elements in this list (in proper sequence).
     * 
     * @see #listIterator(int)
     */
    public ListIterator listIterator() {
        return getList().listIterator();
    }
    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     * @throws    IndexOutOfBoundsException if index out of range
     *        <tt>(index &lt; 0 || index &gt;= size())</tt>.
     */
    public Object set(int index, ACSplashable element) {
        return getList().set(index, element);
    }
}
