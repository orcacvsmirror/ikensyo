package jp.nichicom.vr.component;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.IllegalComponentStateException;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.MenuComponent;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.dnd.DropTarget;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.im.InputContext;
import java.awt.im.InputMethodRequests;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.VolatileImage;
import java.awt.peer.ComponentPeer;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.EventListener;
import java.util.Locale;
import java.util.Set;

import javax.accessibility.AccessibleContext;

/**
 * コンポーネントインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Component
 */
public interface VRComponentar {

    /**
     * @deprecated As of JDK version 1.1, should register this component as
     *             ActionListener on component which fires action events.
     */
    public boolean action(Event evt, Object what);

    /**
     * Adds the specified popup menu to the component.
     * 
     * @param popup the popup menu to be added to the component.
     * @see #remove(MenuComponent)
     * @since JDK1.1
     */
    public void add(PopupMenu popup);

    /**
     * Adds the specified component listener to receive component events from
     * this component. If listener <code>l</code> is <code>null</code>, no
     * exception is thrown and no action is performed.
     * 
     * @param l the component listene.
     * @see java.awt.event.ComponentEvent
     * @see java.awt.event.ComponentListener
     * @see #removeComponentListener
     * @see #getComponentListeners
     * @since JDK1.1
     */
    public void addComponentListener(ComponentListener l);

    /**
     * Adds the specified focus listener to receive focus events from this
     * component when this component gains input focus. If listener
     * <code>l</code> is <code>null</code>, no exception is thrown and no
     * action is performed.
     * 
     * @param l the focus listener
     * @see java.awt.event.FocusEvent
     * @see java.awt.event.FocusListener
     * @see #removeFocusListener
     * @see #getFocusListeners
     * @since JDK1.1
     */
    public void addFocusListener(FocusListener l);

    /**
     * Adds the specified hierarchy bounds listener to receive hierarchy bounds
     * events from this component when the hierarchy to which this container
     * belongs changes. If listener <code>l</code> is <code>null</code>, no
     * exception is thrown and no action is performed.
     * 
     * @param l the hierarchy bounds listener
     * @see java.awt.event.HierarchyEvent
     * @see java.awt.event.HierarchyBoundsListener
     * @see #removeHierarchyBoundsListener
     * @see #getHierarchyBoundsListeners
     * @since 1.3
     */
    public void addHierarchyBoundsListener(HierarchyBoundsListener l);

    /**
     * Adds the specified hierarchy listener to receive hierarchy changed events
     * from this component when the hierarchy to which this container belongs
     * changes. If listener <code>l</code> is <code>null</code>, no
     * exception is thrown and no action is performed.
     * 
     * @param l the hierarchy listener
     * @see java.awt.event.HierarchyEvent
     * @see java.awt.event.HierarchyListener
     * @see #removeHierarchyListener
     * @see #getHierarchyListeners
     * @since 1.3
     */
    public void addHierarchyListener(HierarchyListener l);

    /**
     * Adds the specified input method listener to receive input method events
     * from this component. A component will only receive input method events
     * from input methods if it also overrides
     * <code>getInputMethodRequests</code> to return an
     * <code>InputMethodRequests</code> instance. If listener <code>l</code>
     * is <code>null</code>, no exception is thrown and no action is
     * performed.
     * 
     * @param l the input method listener
     * @see java.awt.event.InputMethodEvent
     * @see java.awt.event.InputMethodListener
     * @see #removeInputMethodListener
     * @see #getInputMethodListeners
     * @see #getInputMethodRequests
     * @since 1.2
     */
    public void addInputMethodListener(InputMethodListener l);

    /**
     * Adds the specified key listener to receive key events from this
     * component. If l is null, no exception is thrown and no action is
     * performed.
     * 
     * @param l the key listener.
     * @see java.awt.event.KeyEvent
     * @see java.awt.event.KeyListener
     * @see #removeKeyListener
     * @see #getKeyListeners
     * @since JDK1.1
     */
    public void addKeyListener(KeyListener l);

    /**
     * Adds the specified mouse listener to receive mouse events from this
     * component. If listener <code>l</code> is <code>null</code>, no
     * exception is thrown and no action is performed.
     * 
     * @param l the mouse listener
     * @see java.awt.event.MouseEvent
     * @see java.awt.event.MouseListener
     * @see #removeMouseListener
     * @see #getMouseListeners
     * @since JDK1.1
     */
    public void addMouseListener(MouseListener l);

    /**
     * Adds the specified mouse motion listener to receive mouse motion events
     * from this component. If listener <code>l</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     * 
     * @param l the mouse motion listener
     * @see java.awt.event.MouseEvent
     * @see java.awt.event.MouseMotionListener
     * @see #removeMouseMotionListener
     * @see #getMouseMotionListeners
     * @since JDK1.1
     */
    public void addMouseMotionListener(MouseMotionListener l);

    /**
     * Adds the specified mouse wheel listener to receive mouse wheel events
     * from this component. Containers also receive mouse wheel events from
     * sub-components. If l is null, no exception is thrown and no action is
     * performed.
     * 
     * @param l the mouse wheel listener.
     * @see java.awt.event.MouseWheelEvent
     * @see java.awt.event.MouseWheelListener
     * @see #removeMouseWheelListener
     * @see #getMouseWheelListeners
     * @since 1.4
     */
    public void addMouseWheelListener(MouseWheelListener l);

    /**
     * Makes this <code>Component</code> displayable by connecting it to a
     * native screen resource. This method is called internally by the toolkit
     * and should not be called directly by programs.
     * 
     * @see #isDisplayable
     * @see #removeNotify
     * @since JDK1.0
     */
    public void addNotify();

    /**
     * Adds a PropertyChangeListener to the listener list. The listener is
     * registered for all bound properties of this class, including the
     * following:
     * <ul>
     * <li>this Component's font ("font")</li>
     * <li>this Component's background color ("background")</li>
     * <li>this Component's foreground color ("foreground")</li>
     * <li>this Component's focusability ("focusable")</li>
     * <li>this Component's focus traversal keys enabled state
     * ("focusTraversalKeysEnabled")</li>
     * <li>this Component's Set of FORWARD_TRAVERSAL_KEYS
     * ("forwardFocusTraversalKeys")</li>
     * <li>this Component's Set of BACKWARD_TRAVERSAL_KEYS
     * ("backwardFocusTraversalKeys")</li>
     * <li>this Component's Set of UP_CYCLE_TRAVERSAL_KEYS
     * ("upCycleFocusTraversalKeys")</li>
     * </ul>
     * Note that if this Component is inheriting a bound property, then no event
     * will be fired in response to a change in the inherited property.
     * <p>
     * If listener is null, no exception is thrown and no action is performed.
     * 
     * @param listener the PropertyChangeListener to be added
     * @see #removePropertyChangeListener
     * @see #getPropertyChangeListeners
     * @see #addPropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Adds a PropertyChangeListener to the listener list for a specific
     * property. The specified property may be user-defined, or one of the
     * following:
     * <ul>
     * <li>this Component's font ("font")</li>
     * <li>this Component's background color ("background")</li>
     * <li>this Component's foreground color ("foreground")</li>
     * <li>this Component's focusability ("focusable")</li>
     * <li>this Component's focus traversal keys enabled state
     * ("focusTraversalKeysEnabled")</li>
     * <li>this Component's Set of FORWARD_TRAVERSAL_KEYS
     * ("forwardFocusTraversalKeys")</li>
     * <li>this Component's Set of BACKWARD_TRAVERSAL_KEYS
     * ("backwardFocusTraversalKeys")</li>
     * <li>this Component's Set of UP_CYCLE_TRAVERSAL_KEYS
     * ("upCycleFocusTraversalKeys")</li>
     * </ul>
     * Note that if this Component is inheriting a bound property, then no event
     * will be fired in response to a change in the inherited property.
     * <p>
     * If listener is null, no exception is thrown and no action is performed.
     * 
     * @param propertyName one of the property names listed above
     * @param listener the PropertyChangeListener to be added
     * @see #removePropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     * @see #getPropertyChangeListeners(java.lang.String)
     * @see #addPropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener);

    /**
     * Sets the <code>ComponentOrientation</code> property of this component
     * and all components contained within it.
     * 
     * @param orientation the new component orientation of this component and
     *            the components contained within it.
     * @exception NullPointerException if <code>orientation</code> is null.
     * @see #setComponentOrientation
     * @see #getComponentOrientation
     * @since 1.4
     */
    public void applyComponentOrientation(ComponentOrientation orientation);

    /**
     * Returns whether the Set of focus traversal keys for the given focus
     * traversal operation has been explicitly defined for this Component. If
     * this method returns <code>false</code>, this Component is inheriting
     * the Set from an ancestor, or from the current KeyboardFocusManager.
     * 
     * @param id one of KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
     *            KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, or
     *            KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS
     * @return <code>true</code> if the the Set of focus traversal keys for
     *         the given focus traversal operation has been explicitly defined
     *         for this Component; <code>false</code> otherwise.
     * @throws IllegalArgumentException if id is not one of
     *             KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
     *             KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, or
     *             KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS
     * @since 1.4
     */
    public boolean areFocusTraversalKeysSet(int id);

    /**
     * @deprecated As of JDK version 1.1, replaced by <code>getBounds()</code>.
     */
    public Rectangle bounds();

    /**
     * Returns the status of the construction of a screen representation of the
     * specified image.
     * <p>
     * This method does not cause the image to begin loading. An application
     * must use the <code>prepareImage</code> method to force the loading of
     * an image.
     * <p>
     * Information on the flags returned by this method can be found with the
     * discussion of the <code>ImageObserver</code> interface.
     * 
     * @param image the <code>Image</code> object whose status is being
     *            checked
     * @param observer the <code>ImageObserver</code> object to be notified as
     *            the image is being prepared
     * @return the bitwise inclusive <b>OR</b> of <code>ImageObserver</code>
     *         flags indicating what information about the image is currently
     *         available
     * @see #prepareImage(Image, int, int, java.awt.image.ImageObserver)
     * @see Toolkit#checkImage(Image, int, int, java.awt.image.ImageObserver)
     * @see java.awt.image.ImageObserver
     * @since JDK1.0
     */
    public int checkImage(Image image, ImageObserver observer);

    /**
     * Returns the status of the construction of a screen representation of the
     * specified image.
     * <p>
     * This method does not cause the image to begin loading. An application
     * must use the <code>prepareImage</code> method to force the loading of
     * an image.
     * <p>
     * The <code>checkImage</code> method of <code>Component</code> calls
     * its peer's <code>checkImage</code> method to calculate the flags. If
     * this component does not yet have a peer, the component's toolkit's
     * <code>checkImage</code> method is called instead.
     * <p>
     * Information on the flags returned by this method can be found with the
     * discussion of the <code>ImageObserver</code> interface.
     * 
     * @param image the <code>Image</code> object whose status is being
     *            checked
     * @param width the width of the scaled version whose status is to be
     *            checked
     * @param height the height of the scaled version whose status is to be
     *            checked
     * @param observer the <code>ImageObserver</code> object to be notified as
     *            the image is being prepared
     * @return the bitwise inclusive <b>OR</b> of <code>ImageObserver</code>
     *         flags indicating what information about the image is currently
     *         available
     * @see #prepareImage(Image, int, int, java.awt.image.ImageObserver)
     * @see Toolkit#checkImage(Image, int, int, java.awt.image.ImageObserver)
     * @see java.awt.image.ImageObserver
     * @since JDK1.0
     */
    public int checkImage(Image image, int width, int height,
            ImageObserver observer);

    /**
     * Checks whether this component "contains" the specified point, where
     * <code>x</code> and <code>y</code> are defined to be relative to the
     * coordinate system of this component.
     * 
     * @param x the <i>x</i> coordinate of the point
     * @param y the <i>y</i> coordinate of the point
     * @see #getComponentAt(int, int)
     * @since JDK1.1
     */
    public boolean contains(int x, int y);

    /**
     * Checks whether this component "contains" the specified point, where the
     * point's <i>x</i> and <i>y</i> coordinates are defined to be relative to
     * the coordinate system of this component.
     * 
     * @param p the point
     * @see #getComponentAt(Point)
     * @since JDK1.1
     */
    public boolean contains(Point p);

    /**
     * Creates an image from the specified image producer.
     * 
     * @param producer the image producer
     * @return the image produced
     * @since JDK1.0
     */
    public Image createImage(ImageProducer producer);

    /**
     * Creates an off-screen drawable image to be used for double buffering.
     * 
     * @param width the specified width
     * @param height the specified height
     * @return an off-screen drawable image, which can be used for double
     *         buffering. The return value may be <code>null</code> if the
     *         component is not displayable. This will always happen if
     *         <code>GraphicsEnvironment.isHeadless()</code> returns
     *         <code>true</code>.
     * @see #isDisplayable
     * @see GraphicsEnvironment#isHeadless
     * @since JDK1.0
     */
    public Image createImage(int width, int height);

    /**
     * Creates a volatile off-screen drawable image to be used for double
     * buffering.
     * 
     * @param width the specified width.
     * @param height the specified height.
     * @return an off-screen drawable image, which can be used for double
     *         buffering. The return value may be <code>null</code> if the
     *         component is not displayable. This will always happen if
     *         <code>GraphicsEnvironment.isHeadless()</code> returns
     *         <code>true</code>.
     * @see java.awt.image.VolatileImage
     * @see #isDisplayable
     * @see GraphicsEnvironment#isHeadless
     * @since 1.4
     */
    public VolatileImage createVolatileImage(int width, int height);

    /**
     * Creates a volatile off-screen drawable image, with the given
     * capabilities. The contents of this image may be lost at any time due to
     * operating system issues, so the image must be managed via the
     * <code>VolatileImage</code> interface.
     * 
     * @param width the specified width.
     * @param height the specified height.
     * @param caps the image capabilities
     * @exception AWTException if an image with the specified capabilities
     *                cannot be created
     * @return a VolatileImage object, which can be used to manage surface
     *         contents loss and capabilities.
     * @see java.awt.image.VolatileImage
     * @since 1.4
     */
    public VolatileImage createVolatileImage(int width, int height,
            ImageCapabilities caps) throws AWTException;

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>dispatchEvent(AWTEvent e)</code>.
     */
    public void deliverEvent(Event e);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>setEnabled(boolean)</code>.
     */
    public void disable();

    /**
     * Dispatches an event to this component or one of its sub components. Calls
     * <code>processEvent</code> before returning for 1.1-style events which
     * have been enabled for the <code>Component</code>.
     * 
     * @param e the event
     */
    public void dispatchEvent(AWTEvent e);

    /**
     * Prompts the layout manager to lay out this component. This is usually
     * called when the component (more specifically, container) is validated.
     * 
     * @see #validate
     * @see LayoutManager
     */
    public void doLayout();

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>setEnabled(boolean)</code>.
     */
    public void enable();

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>setEnabled(boolean)</code>.
     */
    public void enable(boolean b);

    /**
     * Enables or disables input method support for this component. If input
     * method support is enabled and the component also processes key events,
     * incoming events are offered to the current input method and will only be
     * processed by the component or dispatched to its listeners if the input
     * method does not consume them. By default, input method support is
     * enabled.
     * 
     * @param enable true to enable, false to disable
     * @since 1.2
     */
    public void enableInputMethods(boolean enable);

    /**
     * Gets the <code>AccessibleContext</code> associated with this
     * <code>Component</code>. The method implemented by this base class
     * returns null. Classes that extend <code>Component</code> should
     * implement this method to return the <code>AccessibleContext</code>
     * associated with the subclass.
     * 
     * @return the <code>AccessibleContext</code> of this
     *         <code>Component</code>
     */
    public AccessibleContext getAccessibleContext();

    /**
     * Returns the alignment along the x axis. This specifies how the component
     * would like to be aligned relative to other components. The value should
     * be a number between 0 and 1 where 0 represents alignment along the
     * origin, 1 is aligned the furthest away from the origin, 0.5 is centered,
     * etc.
     */
    public float getAlignmentX();

    /**
     * Returns the alignment along the y axis. This specifies how the component
     * would like to be aligned relative to other components. The value should
     * be a number between 0 and 1 where 0 represents alignment along the
     * origin, 1 is aligned the furthest away from the origin, 0.5 is centered,
     * etc.
     */
    public float getAlignmentY();

    /**
     * Gets the background color of this component.
     * 
     * @return this component's background color; if this component does not
     *         have a background color, the background color of its parent is
     *         returned
     * @see #setBackground
     * @since JDK1.0
     */
    public Color getBackground();

    /**
     * Gets the bounds of this component in the form of a <code>Rectangle</code>
     * object. The bounds specify this component's width, height, and location
     * relative to its parent.
     * 
     * @return a rectangle indicating this component's bounds
     * @see #setBounds
     * @see #getLocation
     * @see #getSize
     */
    public Rectangle getBounds();

    /**
     * Stores the bounds of this component into "return value" <b>rv</b> and
     * return <b>rv</b>. If rv is <code>null</code> a new
     * <code>Rectangle</code> is allocated. This version of
     * <code>getBounds</code> is useful if the caller wants to avoid
     * allocating a new <code>Rectangle</code> object on the heap.
     * 
     * @param rv the return value, modified to the components bounds
     * @return rv
     */
    public Rectangle getBounds(Rectangle rv);

    /**
     * Gets the instance of <code>ColorModel</code> used to display the
     * component on the output device.
     * 
     * @return the color model used by this component
     * @see java.awt.image.ColorModel
     * @see java.awt.peer.ComponentPeer#getColorModel()
     * @see Toolkit#getColorModel()
     * @since JDK1.0
     */
    public ColorModel getColorModel();

    /**
     * Determines if this component or one of its immediate subcomponents
     * contains the (<i>x</i>,&nbsp;<i>y</i>) location, and if so, returns
     * the containing component. This method only looks one level deep. If the
     * point (<i>x</i>,&nbsp;<i>y</i>) is inside a subcomponent that itself
     * has subcomponents, it does not go looking down the subcomponent tree.
     * <p>
     * The <code>locate</code> method of <code>Component</code> simply
     * returns the component itself if the (<i>x</i>,&nbsp;<i>y</i>)
     * coordinate location is inside its bounding box, and <code>null</code>
     * otherwise.
     * 
     * @param x the <i>x</i> coordinate
     * @param y the <i>y</i> coordinate
     * @return the component or subcomponent that contains the (<i>x</i>,&nbsp;<i>y</i>)
     *         location; <code>null</code> if the location is outside this
     *         component
     * @see #contains(int, int)
     * @since JDK1.0
     */
    public Component getComponentAt(int x, int y);

    /**
     * Returns the component or subcomponent that contains the specified point.
     * 
     * @param p the point
     * @see java.awt.Component#contains
     * @since JDK1.1
     */
    public Component getComponentAt(Point p);

    /**
     * Returns an array of all the component listeners registered on this
     * component.
     * 
     * @return all of this comonent's <code>ComponentListener</code>s or an
     *         empty array if no component listeners are currently registered
     * @see #addComponentListener
     * @see #removeComponentListener
     * @since 1.4
     */
    public ComponentListener[] getComponentListeners();

    /**
     * Retrieves the language-sensitive orientation that is to be used to order
     * the elements or text within this component. <code>LayoutManager</code>
     * and <code>Component</code> subclasses that wish to respect orientation
     * should call this method to get the component's orientation before
     * performing layout or drawing.
     * 
     * @see ComponentOrientation
     */
    public ComponentOrientation getComponentOrientation();

    /**
     * Gets the cursor set in the component. If the component does not have a
     * cursor set, the cursor of its parent is returned. If no cursor is set in
     * the entire hierarchy, <code>Cursor.DEFAULT_CURSOR</code> is returned.
     * 
     * @see #setCursor
     * @since JDK1.1
     */
    public Cursor getCursor();

    /**
     * Gets the <code>DropTarget</code> associated with this
     * <code>Component</code>.
     */

    public DropTarget getDropTarget();

    /**
     * Returns the Container which is the focus cycle root of this Component's
     * focus traversal cycle. Each focus traversal cycle has only a single focus
     * cycle root and each Component which is not a Container belongs to only a
     * single focus traversal cycle. Containers which are focus cycle roots
     * belong to two cycles: one rooted at the Container itself, and one rooted
     * at the Container's nearest focus-cycle-root ancestor. For such
     * Containers, this method will return the Container's nearest focus-cycle-
     * root ancestor.
     * 
     * @return this Component's nearest focus-cycle-root ancestor
     * @see Container#isFocusCycleRoot()
     * @since 1.4
     */
    public Container getFocusCycleRootAncestor();

    /**
     * Returns an array of all the focus listeners registered on this component.
     * 
     * @return all of this component's <code>FocusListener</code>s or an
     *         empty array if no component listeners are currently registered
     * @see #addFocusListener
     * @see #removeFocusListener
     * @since 1.4
     */
    public FocusListener[] getFocusListeners();

    /**
     * Returns the Set of focus traversal keys for a given traversal operation
     * for this Component. (See <code>setFocusTraversalKeys</code> for a full
     * description of each key.)
     * <p>
     * If a Set of traversal keys has not been explicitly defined for this
     * Component, then this Component's parent's Set is returned. If no Set has
     * been explicitly defined for any of this Component's ancestors, then the
     * current KeyboardFocusManager's default Set is returned.
     * 
     * @param id one of KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
     *            KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, or
     *            KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS
     * @return the Set of AWTKeyStrokes for the specified operation. The Set
     *         will be unmodifiable, and may be empty. null will never be
     *         returned.
     * @see #setFocusTraversalKeys
     * @see KeyboardFocusManager#FORWARD_TRAVERSAL_KEYS
     * @see KeyboardFocusManager#BACKWARD_TRAVERSAL_KEYS
     * @see KeyboardFocusManager#UP_CYCLE_TRAVERSAL_KEYS
     * @throws IllegalArgumentException if id is not one of
     *             KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
     *             KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, or
     *             KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS
     * @since 1.4
     */
    public Set getFocusTraversalKeys(int id);

    /**
     * Returns whether focus traversal keys are enabled for this Component.
     * Components for which focus traversal keys are disabled receive key events
     * for focus traversal keys. Components for which focus traversal keys are
     * enabled do not see these events; instead, the events are automatically
     * converted to traversal operations.
     * 
     * @return whether focus traversal keys are enabled for this Component
     * @see #setFocusTraversalKeysEnabled
     * @see #setFocusTraversalKeys
     * @see #getFocusTraversalKeys
     * @since 1.4
     */
    public boolean getFocusTraversalKeysEnabled();

    /**
     * Gets the font of this component.
     * 
     * @return this component's font; if a font has not been set for this
     *         component, the font of its parent is returned
     * @see #setFont
     * @since JDK1.0
     */
    public Font getFont();

    /**
     * Gets the font metrics for the specified font.
     * 
     * @param font the font for which font metrics is to be obtained
     * @return the font metrics for <code>font</code>
     * @see #getFont
     * @see #getPeer
     * @see java.awt.peer.ComponentPeer#getFontMetrics(Font)
     * @see Toolkit#getFontMetrics(Font)
     * @since JDK1.0
     */
    public FontMetrics getFontMetrics(Font font);

    /**
     * Gets the foreground color of this component.
     * 
     * @return this component's foreground color; if this component does not
     *         have a foreground color, the foreground color of its parent is
     *         returned
     * @see #setForeground
     * @since JDK1.0
     */
    public Color getForeground();

    /**
     * Creates a graphics context for this component. This method will return
     * <code>null</code> if this component is currently not displayable.
     * 
     * @return a graphics context for this component, or <code>null</code> if
     *         it has none
     * @see #paint
     * @since JDK1.0
     */
    public Graphics getGraphics();

    /**
     * Gets the <code>GraphicsConfiguration</code> associated with this
     * <code>Component</code>. If the <code>Component</code> has not been
     * assigned a specific <code>GraphicsConfiguration</code>, the
     * <code>GraphicsConfiguration</code> of the <code>Component</code>
     * object's top-level container is returned. If the <code>Component</code>
     * has been created, but not yet added to a <code>Container</code>, this
     * method returns <code>null</code>.
     * 
     * @return the <code>GraphicsConfiguration</code> used by this
     *         <code>Component</code> or <code>null</code>
     * @since 1.3
     */
    public GraphicsConfiguration getGraphicsConfiguration();

    /**
     * Returns the current height of this component. This method is preferable
     * to writing <code>component.getBounds().height</code.,
     * or <code>component.getSize().height</code> because it
     * doesn't cause any heap allocations.
     *
     * @return the current height of this component
     * @since 1.2
     */
    public int getHeight();

    /**
     * Returns an array of all the hierarchy bounds listeners registered on this
     * component.
     * 
     * @return all of this component's <code>HierarchyBoundsListener</code>s
     *         or an empty array if no hierarchy bounds listeners are currently
     *         registered
     * @see #addHierarchyBoundsListener
     * @see #removeHierarchyBoundsListener
     * @since 1.4
     */
    public HierarchyBoundsListener[] getHierarchyBoundsListeners();

    /**
     * Returns an array of all the hierarchy listeners registered on this
     * component.
     * 
     * @return all of this component's <code>HierarchyListener</code>s or an
     *         empty array if no hierarchy listeners are currently registered
     * @see #addHierarchyListener
     * @see #removeHierarchyListener
     * @since 1.4
     */
    public HierarchyListener[] getHierarchyListeners();

    /**
     * @return whether or not paint messages received from the operating system
     *         should be ignored.
     * @since 1.4
     * @see #setIgnoreRepaint
     */
    public boolean getIgnoreRepaint();

    /**
     * Gets the input context used by this component for handling the
     * communication with input methods when text is entered in this component.
     * By default, the input context used for the parent component is returned.
     * Components may override this to return a private input context.
     * 
     * @return the input context used by this component; <code>null</code> if
     *         no context can be determined
     * @since 1.2
     */
    public InputContext getInputContext();

    /**
     * Returns an array of all the input method listeners registered on this
     * component.
     * 
     * @return all of this component's <code>InputMethodListener</code>s or
     *         an empty array if no input method listeners are currently
     *         registered
     * @see #addInputMethodListener
     * @see #removeInputMethodListener
     * @since 1.4
     */
    public InputMethodListener[] getInputMethodListeners();

    /**
     * Gets the input method request handler which supports requests from input
     * methods for this component. A component that supports on-the-spot text
     * input must override this method to return an
     * <code>InputMethodRequests</code> instance. At the same time, it also
     * has to handle input method events.
     * 
     * @return the input method request handler for this component,
     *         <code>null</code> by default
     * @see #addInputMethodListener
     * @since 1.2
     */
    public InputMethodRequests getInputMethodRequests();

    /**
     * Returns an array of all the key listeners registered on this component.
     * 
     * @return all of this component's <code>KeyListener</code>s or an empty
     *         array if no key listeners are currently registered
     * @see #addKeyListener
     * @see #removeKeyListener
     * @since 1.4
     */
    public KeyListener[] getKeyListeners();

    /**
     * Returns an array of all the objects currently registered as
     * <code><em>Foo</em>Listener</code>s upon this <code>Component</code>.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     * <p>
     * You can specify the <code>listenerType</code> argument with a class
     * literal, such as <code><em>Foo</em>Listener.class</code>. For
     * example, you can query a <code>Component</code> <code>c</code> for
     * its mouse listeners with the following code:
     * 
     * <pre>
     * MouseListener[] mls = (MouseListener[]) (c.getListeners(MouseListener.class));
     * </pre>
     * 
     * If no such listeners exist, this method returns an empty array.
     * 
     * @param listenerType the type of listeners requested; this parameter
     *            should specify an interface that descends from
     *            <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *         <code><em>Foo</em>Listener</code>s on this component, or an
     *         empty array if no such listeners have been added
     * @exception ClassCastException if <code>listenerType</code> doesn't
     *                specify a class or interface that implements
     *                <code>java.util.EventListener</code>
     * @see #getComponentListeners
     * @see #getFocusListeners
     * @see #getHierarchyListeners
     * @see #getHierarchyBoundsListeners
     * @see #getKeyListeners
     * @see #getMouseListeners
     * @see #getMouseMotionListeners
     * @see #getMouseWheelListeners
     * @see #getInputMethodListeners
     * @see #getPropertyChangeListeners
     * @since 1.3
     */
    //public EventListener[] getListeners(Class listenerType);

    /**
     * Gets the locale of this component.
     * 
     * @return this component's locale; if this component does not have a
     *         locale, the locale of its parent is returned
     * @see #setLocale
     * @exception IllegalComponentStateException if the <code>Component</code>
     *                does not have its own locale and has not yet been added to
     *                a containment hierarchy such that the locale can be
     *                determined from the containing parent
     * @since JDK1.1
     */
    public Locale getLocale();

    /**
     * Gets the location of this component in the form of a point specifying the
     * component's top-left corner. The location will be relative to the
     * parent's coordinate space.
     * <p>
     * Due to the asynchronous nature of native event handling, this method can
     * return outdated values (for instance, after several calls of
     * <code>setLocation()</code> in rapid succession). For this reason, the
     * recommended method of obtaining a component's position is within
     * <code>java.awt.event.ComponentListener.componentMoved()</code>, which
     * is called after the operating system has finished moving the component.
     * </p>
     * 
     * @return an instance of <code>Point</code> representing the top-left
     *         corner of the component's bounds in the coordinate space of the
     *         component's parent
     * @see #setLocation
     * @see #getLocationOnScreen
     * @since JDK1.1
     */
    public Point getLocation();

    /**
     * Stores the x,y origin of this component into "return value" <b>rv</b>
     * and return <b>rv</b>. If rv is <code>null</code> a new
     * <code>Point</code> is allocated. This version of
     * <code>getLocation</code> is useful if the caller wants to avoid
     * allocating a new <code>Point</code> object on the heap.
     * 
     * @param rv the return value, modified to the components location
     * @return rv
     */
    public Point getLocation(Point rv);

    /**
     * Gets the location of this component in the form of a point specifying the
     * component's top-left corner in the screen's coordinate space.
     * 
     * @return an instance of <code>Point</code> representing the top-left
     *         corner of the component's bounds in the coordinate space of the
     *         screen
     * @throws <code>IllegalComponentStateException</code> if the component is
     *             not showing on the screen
     * @see #setLocation
     * @see #getLocation
     */
    public Point getLocationOnScreen();

    /**
     * Gets the maximum size of this component.
     * 
     * @return a dimension object indicating this component's maximum size
     * @see #getMinimumSize
     * @see #getPreferredSize
     * @see LayoutManager
     */
    public Dimension getMaximumSize();

    /**
     * Gets the mininimum size of this component.
     * 
     * @return a dimension object indicating this component's minimum size
     * @see #getPreferredSize
     * @see LayoutManager
     */
    public Dimension getMinimumSize();

    /**
     * Returns an array of all the mouse listeners registered on this component.
     * 
     * @return all of this component's <code>MouseListener</code>s or an
     *         empty array if no mouse listeners are currently registered
     * @see #addMouseListener
     * @see #removeMouseListener
     * @since 1.4
     */
    public MouseListener[] getMouseListeners();

    /**
     * Returns an array of all the mouse motion listeners registered on this
     * component.
     * 
     * @return all of this component's <code>MouseMotionListener</code>s or
     *         an empty array if no mouse motion listeners are currently
     *         registered
     * @see #addMouseMotionListener
     * @see #removeMouseMotionListener
     * @since 1.4
     */
    public MouseMotionListener[] getMouseMotionListeners();

    /**
     * Returns an array of all the mouse wheel listeners registered on this
     * component.
     * 
     * @return all of this component's <code>MouseWheelListener</code>s or an
     *         empty array if no mouse wheel listeners are currently registered
     * @see #addMouseWheelListener
     * @see #removeMouseWheelListener
     * @since 1.4
     */
    public MouseWheelListener[] getMouseWheelListeners();

    /**
     * Gets the name of the component.
     * 
     * @return this component's name
     * @see #setName
     * @since JDK1.1
     */
    public String getName();

    /**
     * Gets the parent of this component.
     * 
     * @return the parent container of this component
     * @since JDK1.0
     */
    public Container getParent();

    /** saves an internal cache of FontMetrics for better performance * */

    /**
     * @deprecated As of JDK version 1.1, programs should not directly
     *             manipulate peers; replaced by
     *             <code>boolean isDisplayable()</code>.
     */
    public ComponentPeer getPeer();

    /**
     * Gets the preferred size of this component.
     * 
     * @return a dimension object indicating this component's preferred size
     * @see #getMinimumSize
     * @see LayoutManager
     */
    public Dimension getPreferredSize();

    /**
     * Returns an array of all the property change listeners registered on this
     * component.
     * 
     * @return all of this component's <code>PropertyChangeListener</code>s
     *         or an empty array if no property change listeners are currently
     *         registered
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     * @see #getPropertyChangeListeners(java.lang.String)
     * @see java.beans.PropertyChangeSupport#getPropertyChangeListeners
     * @since 1.4
     */
    public PropertyChangeListener[] getPropertyChangeListeners();

    /**
     * Returns an array of all the listeners which have been associated with the
     * named property.
     * 
     * @return all of the <code>PropertyChangeListeners</code> associated with
     *         the named property or an empty array if no listeners have been
     *         added
     * @see #addPropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     * @see #removePropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     * @see #getPropertyChangeListeners
     * @since 1.4
     */
    public PropertyChangeListener[] getPropertyChangeListeners(
            String propertyName);

    /**
     * Returns the size of this component in the form of a
     * <code>Dimension</code> object. The <code>height</code> field of the
     * <code>Dimension</code> object contains this component's height, and the
     * <code>width</code> field of the <code>Dimension</code> object
     * contains this component's width.
     * 
     * @return a <code>Dimension</code> object that indicates the size of this
     *         component
     * @see #setSize
     * @since JDK1.1
     */
    public Dimension getSize();

    /**
     * Stores the width/height of this component into "return value" <b>rv</b>
     * and return <b>rv</b>. If rv is <code>null</code> a new
     * <code>Dimension</code> object is allocated. This version of
     * <code>getSize</code> is useful if the caller wants to avoid allocating
     * a new <code>Dimension</code> object on the heap.
     * 
     * @param rv the return value, modified to the components size
     * @return rv
     */
    public Dimension getSize(Dimension rv);

    /**
     * Gets the toolkit of this component. Note that the frame that contains a
     * component controls which toolkit is used by that component. Therefore if
     * the component is moved from one frame to another, the toolkit it uses may
     * change.
     * 
     * @return the toolkit of this component
     * @since JDK1.0
     */
    public Toolkit getToolkit();

    /**
     * Gets this component's locking object (the object that owns the thread
     * sychronization monitor) for AWT component-tree and layout operations.
     * 
     * @return this component's locking object
     */
    public Object getTreeLock();

    /**
     * Returns the current width of this component. This method is preferable to
     * writing <code>component.getBounds().width</code>, or
     * <code>component.getSize().width</code> because it doesn't cause any
     * heap allocations.
     * 
     * @return the current width of this component
     * @since 1.2
     */
    public int getWidth();

    /**
     * Returns the current x coordinate of the components origin. This method is
     * preferable to writing <code>component.getBounds().x</code>, or
     * <code>component.getLocation().x</code> because it doesn't cause any
     * heap allocations.
     * 
     * @return the current x coordinate of the components origin
     * @since 1.2
     */
    public int getX();

    /**
     * Returns the current y coordinate of the components origin. This method is
     * preferable to writing <code>component.getBounds().y</code>, or
     * <code>component.getLocation().y</code> because it doesn't cause any
     * heap allocations.
     * 
     * @return the current y coordinate of the components origin
     * @since 1.2
     */
    public int getY();

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             processFocusEvent(FocusEvent).
     */
    public boolean gotFocus(Event evt, Object what);

    /**
     * @deprecated As of JDK version 1.1 replaced by processEvent(AWTEvent).
     */
    public boolean handleEvent(Event evt);

    /**
     * Returns <code>true</code> if this <code>Component</code> is the focus
     * owner. This method is obsolete, and has been replaced by
     * <code>isFocusOwner()</code>.
     * 
     * @return <code>true</code> if this <code>Component</code> is the focus
     *         owner; <code>false</code> otherwise
     * @since 1.2
     */
    public boolean hasFocus();

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>setVisible(boolean)</code>.
     */
    public void hide();

    /**
     * Repaints the component when the image has changed. This
     * <code>imageUpdate</code> method of an <code>ImageObserver</code> is
     * called when more information about an image which had been previously
     * requested using an asynchronous routine such as the
     * <code>drawImage</code> method of <code>Graphics</code> becomes
     * available. See the definition of <code>imageUpdate</code> for more
     * information on this method and its arguments.
     * <p>
     * The <code>imageUpdate</code> method of <code>Component</code>
     * incrementally draws an image on the component as more of the bits of the
     * image are available.
     * <p>
     * If the system property <code>awt.image.incrementaldraw</code> is
     * missing or has the value <code>true</code>, the image is incrementally
     * drawn. If the system property has any other value, then the image is not
     * drawn until it has been completely loaded.
     * <p>
     * Also, if incremental drawing is in effect, the value of the system
     * property <code>awt.image.redrawrate</code> is interpreted as an integer
     * to give the maximum redraw rate, in milliseconds. If the system property
     * is missing or cannot be interpreted as an integer, the redraw rate is
     * once every 100ms.
     * <p>
     * The interpretation of the <code>x</code>, <code>y</code>,
     * <code>width</code>, and <code>height</code> arguments depends on the
     * value of the <code>infoflags</code> argument.
     * 
     * @param img the image being observed
     * @param infoflags see <code>imageUpdate</code> for more information
     * @param x the <i>x</i> coordinate
     * @param y the <i>y</i> coordinate
     * @param w the width
     * @param h the height
     * @return <code>false</code> if the infoflags indicate that the image is
     *         completely loaded; <code>true</code> otherwise.
     * @see java.awt.image.ImageObserver
     * @see Graphics#drawImage(Image, int, int, Color,
     *      java.awt.image.ImageObserver)
     * @see Graphics#drawImage(Image, int, int, java.awt.image.ImageObserver)
     * @see Graphics#drawImage(Image, int, int, int, int, Color,
     *      java.awt.image.ImageObserver)
     * @see Graphics#drawImage(Image, int, int, int, int,
     *      java.awt.image.ImageObserver)
     * @see java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int,
     *      int, int, int)
     * @since JDK1.0
     */
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int w,
            int h);

    /**
     * @deprecated As of JDK version 1.1, replaced by contains(int, int).
     */
    public boolean inside(int x, int y);

    /**
     * Invalidates this component. This component and all parents above it are
     * marked as needing to be laid out. This method can be called often, so it
     * needs to execute quickly.
     * 
     * @see #validate
     * @see #doLayout
     * @see LayoutManager
     * @since JDK1.0
     */
    public void invalidate();

    /**
     * Returns whether the background color has been explicitly set for this
     * Component. If this method returns <code>false</code>, this Component
     * is inheriting its background color from an ancestor.
     * 
     * @return <code>true</code> if the background color has been explicitly
     *         set for this Component; <code>false</code> otherwise.
     * @since 1.4
     */
    public boolean isBackgroundSet();

    /**
     * Returns whether the cursor has been explicitly set for this Component. If
     * this method returns <code>false</code>, this Component is inheriting
     * its cursor from an ancestor.
     * 
     * @return <code>true</code> if the cursor has been explicitly set for
     *         this Component; <code>false</code> otherwise.
     * @since 1.4
     */
    public boolean isCursorSet();

    /**
     * Determines whether this component is displayable. A component is
     * displayable when it is connected to a native screen resource.
     * <p>
     * A component is made displayable either when it is added to a displayable
     * containment hierarchy or when its containment hierarchy is made
     * displayable. A containment hierarchy is made displayable when its
     * ancestor window is either packed or made visible.
     * <p>
     * A component is made undisplayable either when it is removed from a
     * displayable containment hierarchy or when its containment hierarchy is
     * made undisplayable. A containment hierarchy is made undisplayable when
     * its ancestor window is disposed.
     * 
     * @return <code>true</code> if the component is displayable,
     *         <code>false</code> otherwise
     * @see Container#add(Component)
     * @see Window#pack
     * @see Window#show
     * @see Container#remove(Component)
     * @see Window#dispose
     * @since 1.2
     */
    public boolean isDisplayable();

    /**
     * Returns true if this component is painted to an offscreen image
     * ("buffer") that's copied to the screen later. Component subclasses that
     * support double buffering should override this method to return true if
     * double buffering is enabled.
     * 
     * @return false by default
     */
    public boolean isDoubleBuffered();

    /**
     * Determines whether this component is enabled. An enabled component can
     * respond to user input and generate events. Components are enabled
     * initially by default. A component may be enabled or disabled by calling
     * its <code>setEnabled</code> method.
     * 
     * @return <code>true</code> if the component is enabled,
     *         <code>false</code> otherwise
     * @see #setEnabled
     * @since JDK1.0
     */
    public boolean isEnabled();

    /**
     * Returns whether this Component can be focused.
     * 
     * @return <code>true</code> if this Component is focusable;
     *         <code>false</code> otherwise.
     * @see #setFocusable
     * @since 1.4
     */
    public boolean isFocusable();

    /**
     * Returns whether the specified Container is the focus cycle root of this
     * Component's focus traversal cycle. Each focus traversal cycle has only a
     * single focus cycle root and each Component which is not a Container
     * belongs to only a single focus traversal cycle.
     * 
     * @param container the Container to be tested
     * @return <code>true</code> if the specified Container is a focus-cycle-
     *         root of this Component; <code>false</code> otherwise
     * @see Container#isFocusCycleRoot()
     * @since 1.4
     */
    public boolean isFocusCycleRoot(Container container);

    /**
     * Returns <code>true</code> if this <code>Component</code> is the focus
     * owner.
     * 
     * @return <code>true</code> if this <code>Component</code> is the focus
     *         owner; <code>false</code> otherwise
     * @since 1.4
     */
    public boolean isFocusOwner();

    /**
     * Returns whether this <code>Component</code> can become the focus owner.
     * 
     * @return <code>true</code> if this <code>Component</code> is
     *         focusable; <code>false</code> otherwise
     * @see #setFocusable
     * @since JDK1.1
     * @deprecated As of 1.4, replaced by <code>isFocusable()</code>.
     */
    public boolean isFocusTraversable();

    /**
     * Returns whether the font has been explicitly set for this Component. If
     * this method returns <code>false</code>, this Component is inheriting
     * its font from an ancestor.
     * 
     * @return <code>true</code> if the font has been explicitly set for this
     *         Component; <code>false</code> otherwise.
     * @since 1.4
     */
    public boolean isFontSet();

    /**
     * Returns whether the foreground color has been explicitly set for this
     * Component. If this method returns <code>false</code>, this Component
     * is inheriting its foreground color from an ancestor.
     * 
     * @return <code>true</code> if the foreground color has been explicitly
     *         set for this Component; <code>false</code> otherwise.
     * @since 1.4
     */
    public boolean isForegroundSet();

    /**
     * A lightweight component doesn't have a native toolkit peer. Subclasses of
     * <code>Component</code> and <code>Container</code>, other than the
     * ones defined in this package like <code>Button</code> or
     * <code>Scrollbar</code>, are lightweight. All of the Swing components
     * are lightweights.
     * <p>
     * This method will always return <code>false</code> if this component is
     * not displayable because it is impossible to determine the weight of an
     * undisplayable component.
     * 
     * @return true if this component has a lightweight peer; false if it has a
     *         native peer or no peer
     * @see #isDisplayable
     * @since 1.2
     */
    public boolean isLightweight();

    /**
     * Returns true if this component is completely opaque, returns false by
     * default.
     * <p>
     * An opaque component paints every pixel within its rectangular region. A
     * non-opaque component paints only some of its pixels, allowing the pixels
     * underneath it to "show through". A component that does not fully paint
     * its pixels therefore provides a degree of transparency. Only lightweight
     * components can be transparent.
     * <p>
     * Subclasses that guarantee to always completely paint their contents
     * should override this method and return true. All of the "heavyweight" AWT
     * components are opaque.
     * 
     * @return true if this component is completely opaque
     * @see #isLightweight
     * @since 1.2
     */
    public boolean isOpaque();

    /**
     * Determines whether this component is showing on screen. This means that
     * the component must be visible, and it must be in a container that is
     * visible and showing.
     * 
     * @return <code>true</code> if the component is showing,
     *         <code>false</code> otherwise
     * @see #setVisible
     * @since JDK1.0
     */
    public boolean isShowing();

    /**
     * Determines whether this component is valid. A component is valid when it
     * is correctly sized and positioned within its parent container and all its
     * children are also valid. Components are invalidated when they are first
     * shown on the screen.
     * 
     * @return <code>true</code> if the component is valid, <code>false</code>
     *         otherwise
     * @see #validate
     * @see #invalidate
     * @since JDK1.0
     */
    public boolean isValid();

    // Event source interfaces

    /**
     * Determines whether this component should be visible when its parent is
     * visible. Components are initially visible, with the exception of top
     * level components such as <code>Frame</code> objects.
     * 
     * @return <code>true</code> if the component is visible,
     *         <code>false</code> otherwise
     * @see #setVisible
     * @since JDK1.0
     */
    public boolean isVisible();

    /**
     * @deprecated As of JDK version 1.1, replaced by processKeyEvent(KeyEvent).
     */
    public boolean keyDown(Event evt, int key);

    /**
     * @deprecated As of JDK version 1.1, replaced by processKeyEvent(KeyEvent).
     */
    public boolean keyUp(Event evt, int key);

    /**
     * @deprecated As of JDK version 1.1, replaced by <code>doLayout()</code>.
     */
    public void layout();

    /**
     * Prints a listing of this component to the standard system output stream
     * <code>System.out</code>.
     * 
     * @see java.lang.System#out
     * @since JDK1.0
     */
    public void list();

    /**
     * Prints a listing of this component to the specified output stream.
     * 
     * @param out a print stream
     * @since JDK1.0
     */
    public void list(PrintStream out);

    /**
     * Prints out a list, starting at the specified indentation, to the
     * specified print stream.
     * 
     * @param out a print stream
     * @param indent number of spaces to indent
     * @see java.io.PrintStream#println(java.lang.Object)
     * @since JDK1.0
     */
    public void list(PrintStream out, int indent);

    /**
     * Prints a listing to the specified print writer.
     * 
     * @param out the print writer to print to
     * @since JDK1.1
     */
    public void list(PrintWriter out);

    /**
     * Prints out a list, starting at the specified indentation, to the
     * specified print writer.
     * 
     * @param out the print writer to print to
     * @param indent the number of spaces to indent
     * @see java.io.PrintStream#println(java.lang.Object)
     * @since JDK1.1
     */
    public void list(PrintWriter out, int indent);

    /**
     * @deprecated As of JDK version 1.1, replaced by getComponentAt(int, int).
     */
    public Component locate(int x, int y);

    /**
     * @deprecated As of JDK version 1.1, replaced by <code>getLocation()</code>.
     */
    public Point location();

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             processFocusEvent(FocusEvent).
     */
    public boolean lostFocus(Event evt, Object what);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>getMinimumSize()</code>.
     */
    public Dimension minimumSize();

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             processMouseEvent(MouseEvent).
     */
    public boolean mouseDown(Event evt, int x, int y);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             processMouseMotionEvent(MouseEvent).
     */
    public boolean mouseDrag(Event evt, int x, int y);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             processMouseEvent(MouseEvent).
     */
    public boolean mouseEnter(Event evt, int x, int y);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             processMouseEvent(MouseEvent).
     */
    public boolean mouseExit(Event evt, int x, int y);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             processMouseMotionEvent(MouseEvent).
     */
    public boolean mouseMove(Event evt, int x, int y);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             processMouseEvent(MouseEvent).
     */
    public boolean mouseUp(Event evt, int x, int y);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>setLocation(int, int)</code>.
     */
    public void move(int x, int y);

    /**
     * @deprecated As of JDK version 1.1, replaced by transferFocus().
     */
    public void nextFocus();

    /**
     * Paints this component.
     * <p>
     * This method is called when the contents of the component should be
     * painted; such as when the component is first being shown or is damaged
     * and in need of repair. The clip rectangle in the <code>Graphics</code>
     * parameter is set to the area which needs to be painted. Subclasses of
     * <code>Component</code> that override this method need not call
     * <code>super.paint(g)</code>.
     * <p>
     * For performance reasons, <code>Component</code>s with zero width or
     * height aren't considered to need painting when they are first shown, and
     * also aren't considered to need repair.
     * <p>
     * <b>Note</b>: For more information on the paint mechanisms utilitized by
     * AWT and Swing, including information on how to write the most efficient
     * painting code, see <a
     * href="http://java.sun.com/products/jfc/tsc/articles/painting/index.html">Painting
     * in AWT and Swing</a>.
     * 
     * @param g the graphics context to use for painting
     * @see #update
     * @since JDK1.0
     */
    public void paint(Graphics g);

    /**
     * Paints this component and all of its subcomponents.
     * <p>
     * The origin of the graphics context, its (<code>0</code>,&nbsp;<code>0</code>)
     * coordinate point, is the top-left corner of this component. The clipping
     * region of the graphics context is the bounding rectangle of this
     * component.
     * 
     * @param g the graphics context to use for painting
     * @see #paint
     * @since JDK1.0
     */
    public void paintAll(Graphics g);

    /**
     * @deprecated As of JDK version 1.1, replaced by dispatchEvent(AWTEvent).
     */
    public boolean postEvent(Event e);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>getPreferredSize()</code>.
     */
    public Dimension preferredSize();

    /**
     * Prepares an image for rendering on this component. The image data is
     * downloaded asynchronously in another thread and the appropriate screen
     * representation of the image is generated.
     * 
     * @param image the <code>Image</code> for which to prepare a screen
     *            representation
     * @param observer the <code>ImageObserver</code> object to be notified as
     *            the image is being prepared
     * @return <code>true</code> if the image has already been fully prepared;
     *         <code>false</code> otherwise
     * @since JDK1.0
     */
    public boolean prepareImage(Image image, ImageObserver observer);

    /**
     * Prepares an image for rendering on this component at the specified width
     * and height.
     * <p>
     * The image data is downloaded asynchronously in another thread, and an
     * appropriately scaled screen representation of the image is generated.
     * 
     * @param image the instance of <code>Image</code> for which to prepare a
     *            screen representation
     * @param width the width of the desired screen representation
     * @param height the height of the desired screen representation
     * @param observer the <code>ImageObserver</code> object to be notified as
     *            the image is being prepared
     * @return <code>true</code> if the image has already been fully prepared;
     *         <code>false</code> otherwise
     * @see java.awt.image.ImageObserver
     * @since JDK1.0
     */
    public boolean prepareImage(Image image, int width, int height,
            ImageObserver observer);

    /**
     * Prints this component. Applications should override this method for
     * components that must do special processing before being printed or should
     * be printed differently than they are painted.
     * <p>
     * The default implementation of this method calls the <code>paint</code>
     * method.
     * <p>
     * The origin of the graphics context, its (<code>0</code>,&nbsp;<code>0</code>)
     * coordinate point, is the top-left corner of this component. The clipping
     * region of the graphics context is the bounding rectangle of this
     * component.
     * 
     * @param g the graphics context to use for printing
     * @see #paint(Graphics)
     * @since JDK1.0
     */
    public void print(Graphics g);

    /**
     * Prints this component and all of its subcomponents.
     * <p>
     * The origin of the graphics context, its (<code>0</code>,&nbsp;<code>0</code>)
     * coordinate point, is the top-left corner of this component. The clipping
     * region of the graphics context is the bounding rectangle of this
     * component.
     * 
     * @param g the graphics context to use for printing
     * @see #print(Graphics)
     * @since JDK1.0
     */
    public void printAll(Graphics g);

    /**
     * Removes the specified popup menu from the component.
     * 
     * @param popup the popup menu to be removed
     * @see #add(PopupMenu)
     * @since JDK1.1
     */
    public void remove(MenuComponent popup);

    /**
     * Removes the specified component listener so that it no longer receives
     * component events from this component. This method performs no function,
     * nor does it throw an exception, if the listener specified by the argument
     * was not previously added to this component. If listener <code>l</code>
     * is <code>null</code>, no exception is thrown and no action is
     * performed.
     * 
     * @param l the component listener
     * @see java.awt.event.ComponentEvent
     * @see java.awt.event.ComponentListener
     * @see #addComponentListener
     * @see #getComponentListeners
     * @since JDK1.1
     */
    public void removeComponentListener(ComponentListener l);

    /**
     * Removes the specified focus listener so that it no longer receives focus
     * events from this component. This method performs no function, nor does it
     * throw an exception, if the listener specified by the argument was not
     * previously added to this component. If listener <code>l</code> is
     * <code>null</code>, no exception is thrown and no action is performed.
     * 
     * @param l the focus listener
     * @see java.awt.event.FocusEvent
     * @see java.awt.event.FocusListener
     * @see #addFocusListener
     * @see #getFocusListeners
     * @since JDK1.1
     */
    public void removeFocusListener(FocusListener l);

    /**
     * Removes the specified hierarchy bounds listener so that it no longer
     * receives hierarchy bounds events from this component. This method
     * performs no function, nor does it throw an exception, if the listener
     * specified by the argument was not previously added to this component. If
     * listener <code>l</code> is <code>null</code>, no exception is thrown
     * and no action is performed.
     * 
     * @param l the hierarchy bounds listener
     * @see java.awt.event.HierarchyEvent
     * @see java.awt.event.HierarchyBoundsListener
     * @see #addHierarchyBoundsListener
     * @see #getHierarchyBoundsListeners
     * @since 1.3
     */
    public void removeHierarchyBoundsListener(HierarchyBoundsListener l);

    /**
     * Removes the specified hierarchy listener so that it no longer receives
     * hierarchy changed events from this component. This method performs no
     * function, nor does it throw an exception, if the listener specified by
     * the argument was not previously added to this component. If listener
     * <code>l</code> is <code>null</code>, no exception is thrown and no
     * action is performed.
     * 
     * @param l the hierarchy listener
     * @see java.awt.event.HierarchyEvent
     * @see java.awt.event.HierarchyListener
     * @see #addHierarchyListener
     * @see #getHierarchyListeners
     * @since 1.3
     */
    public void removeHierarchyListener(HierarchyListener l);

    /**
     * Removes the specified input method listener so that it no longer receives
     * input method events from this component. This method performs no
     * function, nor does it throw an exception, if the listener specified by
     * the argument was not previously added to this component. If listener
     * <code>l</code> is <code>null</code>, no exception is thrown and no
     * action is performed.
     * 
     * @param l the input method listener
     * @see java.awt.event.InputMethodEvent
     * @see java.awt.event.InputMethodListener
     * @see #addInputMethodListener
     * @see #getInputMethodListeners
     * @since 1.2
     */
    public void removeInputMethodListener(InputMethodListener l);

    /**
     * Removes the specified key listener so that it no longer receives key
     * events from this component. This method performs no function, nor does it
     * throw an exception, if the listener specified by the argument was not
     * previously added to this component. If listener <code>l</code> is
     * <code>null</code>, no exception is thrown and no action is performed.
     * 
     * @param l the key listener
     * @see java.awt.event.KeyEvent
     * @see java.awt.event.KeyListener
     * @see #addKeyListener
     * @see #getKeyListeners
     * @since JDK1.1
     */
    public void removeKeyListener(KeyListener l);

    /**
     * Removes the specified mouse listener so that it no longer receives mouse
     * events from this component. This method performs no function, nor does it
     * throw an exception, if the listener specified by the argument was not
     * previously added to this component. If listener <code>l</code> is
     * <code>null</code>, no exception is thrown and no action is performed.
     * 
     * @param l the mouse listener
     * @see java.awt.event.MouseEvent
     * @see java.awt.event.MouseListener
     * @see #addMouseListener
     * @see #getMouseListeners
     * @since JDK1.1
     */
    public void removeMouseListener(MouseListener l);

    /**
     * Removes the specified mouse motion listener so that it no longer receives
     * mouse motion events from this component. This method performs no
     * function, nor does it throw an exception, if the listener specified by
     * the argument was not previously added to this component. If listener
     * <code>l</code> is <code>null</code>, no exception is thrown and no
     * action is performed.
     * 
     * @param l the mouse motion listener
     * @see java.awt.event.MouseEvent
     * @see java.awt.event.MouseMotionListener
     * @see #addMouseMotionListener
     * @see #getMouseMotionListeners
     * @since JDK1.1
     */
    public void removeMouseMotionListener(MouseMotionListener l);

    /**
     * Removes the specified mouse wheel listener so that it no longer receives
     * mouse wheel events from this component. This method performs no function,
     * nor does it throw an exception, if the listener specified by the argument
     * was not previously added to this component. If l is null, no exception is
     * thrown and no action is performed.
     * 
     * @param l the mouse wheel listener.
     * @see java.awt.event.MouseWheelEvent
     * @see java.awt.event.MouseWheelListener
     * @see #addMouseWheelListener
     * @see #getMouseWheelListeners
     * @since 1.4
     */
    public void removeMouseWheelListener(MouseWheelListener l);

    /**
     * Makes this <code>Component</code> undisplayable by destroying it native
     * screen resource. This method is called by the toolkit internally and
     * should not be called directly by programs.
     * 
     * @see #isDisplayable
     * @see #addNotify
     * @since JDK1.0
     */
    public void removeNotify();

    /**
     * Removes a PropertyChangeListener from the listener list. This method
     * should be used to remove PropertyChangeListeners that were registered for
     * all bound properties of this class.
     * <p>
     * If listener is null, no exception is thrown and no action is performed.
     * 
     * @param listener the PropertyChangeListener to be removed
     * @see #addPropertyChangeListener
     * @see #getPropertyChangeListeners
     * @see #removePropertyChangeListener(java.lang.String,java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Removes a PropertyChangeListener from the listener list for a specific
     * property. This method should be used to remove PropertyChangeListeners
     * that were registered for a specific bound property.
     * <p>
     * If listener is null, no exception is thrown and no action is performed.
     * 
     * @param propertyName a valid property name
     * @param listener the PropertyChangeListener to be removed
     * @see #addPropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     * @see #getPropertyChangeListeners(java.lang.String)
     * @see #removePropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener);

    /**
     * Repaints this component.
     * <p>
     * If this component is a lightweight component, this method causes a call
     * to this component's <code>paint</code> method as soon as possible.
     * Otherwise, this method causes a call to this component's
     * <code>update</code> method as soon as possible.
     * <p>
     * <b>Note</b>: For more information on the paint mechanisms utilitized by
     * AWT and Swing, including information on how to write the most efficient
     * painting code, see <a
     * href="http://java.sun.com/products/jfc/tsc/articles/painting/index.html">Painting
     * in AWT and Swing</a>.
     * 
     * @see #update(Graphics)
     * @since JDK1.0
     */
    public void repaint();

    /**
     * Repaints the specified rectangle of this component.
     * <p>
     * If this component is a lightweight component, this method causes a call
     * to this component's <code>paint</code> method as soon as possible.
     * Otherwise, this method causes a call to this component's
     * <code>update</code> method as soon as possible.
     * <p>
     * <b>Note</b>: For more information on the paint mechanisms utilitized by
     * AWT and Swing, including information on how to write the most efficient
     * painting code, see <a
     * href="http://java.sun.com/products/jfc/tsc/articles/painting/index.html">Painting
     * in AWT and Swing</a>.
     * 
     * @param x the <i>x</i> coordinate
     * @param y the <i>y</i> coordinate
     * @param width the width
     * @param height the height
     * @see #update(Graphics)
     * @since JDK1.0
     */
    public void repaint(int x, int y, int width, int height);

    /**
     * Repaints the component. If this component is a lightweight component,
     * this results in a call to <code>paint</code> within <code>tm</code>
     * milliseconds.
     * <p>
     * <b>Note</b>: For more information on the paint mechanisms utilitized by
     * AWT and Swing, including information on how to write the most efficient
     * painting code, see <a
     * href="http://java.sun.com/products/jfc/tsc/articles/painting/index.html">Painting
     * in AWT and Swing</a>.
     * 
     * @param tm maximum time in milliseconds before update
     * @see #paint
     * @see #update(Graphics)
     * @since JDK1.0
     */
    public void repaint(long tm);

    /**
     * Repaints the specified rectangle of this component within <code>tm</code>
     * milliseconds.
     * <p>
     * If this component is a lightweight component, this method causes a call
     * to this component's <code>paint</code> method. Otherwise, this method
     * causes a call to this component's <code>update</code> method.
     * <p>
     * <b>Note</b>: For more information on the paint mechanisms utilitized by
     * AWT and Swing, including information on how to write the most efficient
     * painting code, see <a
     * href="http://java.sun.com/products/jfc/tsc/articles/painting/index.html">Painting
     * in AWT and Swing</a>.
     * 
     * @param tm maximum time in milliseconds before update
     * @param x the <i>x</i> coordinate
     * @param y the <i>y</i> coordinate
     * @param width the width
     * @param height the height
     * @see #update(Graphics)
     * @since JDK1.0
     */
    public void repaint(long tm, int x, int y, int width, int height);

    /**
     * Requests that this Component get the input focus, and that this
     * Component's top-level ancestor become the focused Window. This component
     * must be displayable, visible, and focusable for the request to be
     * granted. Every effort will be made to honor the request; however, in some
     * cases it may be impossible to do so. Developers must never assume that
     * this Component is the focus owner until this Component receives a
     * FOCUS_GAINED event. If this request is denied because this Component's
     * top-level Window cannot become the focused Window, the request will be
     * remembered and will be granted when the Window is later focused by the
     * user.
     * <p>
     * This method cannot be used to set the focus owner to no Component at all.
     * Use <code>KeyboardFocusManager.clearGlobalFocusOwner()</code> instead.
     * <p>
     * Because the focus behavior of this method is platform-dependent,
     * developers are strongly encouraged to use
     * <code>requestFocusInWindow</code> when possible.
     * 
     * @see #requestFocusInWindow
     * @see java.awt.event.FocusEvent
     * @see #addFocusListener
     * @see #isFocusable
     * @see #isDisplayable
     * @see KeyboardFocusManager#clearGlobalFocusOwner
     * @since JDK1.0
     */
    public void requestFocus();

    /**
     * Requests that this Component get the input focus, if this Component's
     * top-level ancestor is already the focused Window. This component must be
     * displayable, visible, and focusable for the request to be granted. Every
     * effort will be made to honor the request; however, in some cases it may
     * be impossible to do so. Developers must never assume that this Component
     * is the focus owner until this Component receives a FOCUS_GAINED event.
     * <p>
     * This method returns a boolean value. If <code>false</code> is returned,
     * the request is <b>guaranteed to fail</b>. If <code>true</code> is
     * returned, the request will succeed <b>unless</b> it is vetoed, or an
     * extraordinary event, such as disposal of the Component's peer, occurs
     * before the request can be granted by the native windowing system. Again,
     * while a return value of <code>true</code> indicates that the request is
     * likely to succeed, developers must never assume that this Component is
     * the focus owner until this Component receives a FOCUS_GAINED event.
     * <p>
     * This method cannot be used to set the focus owner to no Component at all.
     * Use <code>KeyboardFocusManager.clearGlobalFocusOwner()</code> instead.
     * <p>
     * The focus behavior of this method can be implemented uniformly across
     * platforms, and thus developers are strongly encouraged to use this method
     * over <code>requestFocus</code> when possible. Code which relies on
     * <code>requestFocus</code> may exhibit different focus behavior on
     * different platforms.
     * 
     * @return <code>false</code> if the focus change request is guaranteed to
     *         fail; <code>true</code> if it is likely to succeed
     * @see #requestFocus
     * @see java.awt.event.FocusEvent
     * @see #addFocusListener
     * @see #isFocusable
     * @see #isDisplayable
     * @see KeyboardFocusManager#clearGlobalFocusOwner
     * @since 1.4
     */
    public boolean requestFocusInWindow();

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>setBounds(int, int, int, int)</code>.
     */
    public void reshape(int x, int y, int width, int height);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>setSize(Dimension)</code>.
     */
    public void resize(Dimension d);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>setSize(int, int)</code>.
     */
    public void resize(int width, int height);

    /**
     * Sets the background color of this component.
     * <p>
     * The background color affects each component differently and the parts of
     * the component that are affected by the background color may differ
     * between operating systems.
     * 
     * @param c the color to become this component's color; if this parameter is
     *            <code>null</code>, then this component will inherit the
     *            background color of its parent
     * @see #getBackground
     * @since JDK1.0
     */
    public void setBackground(Color c);

    /**
     * Moves and resizes this component. The new location of the top-left corner
     * is specified by <code>x</code> and <code>y</code>, and the new size
     * is specified by <code>width</code> and <code>height</code>.
     * 
     * @param x the new <i>x</i>-coordinate of this component
     * @param y the new <i>y</i>-coordinate of this component
     * @param width the new <code>width</code> of this component
     * @param height the new <code>height</code> of this component
     * @see #getBounds
     * @see #setLocation(int, int)
     * @see #setLocation(Point)
     * @see #setSize(int, int)
     * @see #setSize(Dimension)
     * @since JDK1.1
     */
    public void setBounds(int x, int y, int width, int height);

    /**
     * Moves and resizes this component to conform to the new bounding rectangle
     * <code>r</code>. This component's new position is specified by
     * <code>r.x</code> and <code>r.y</code>, and its new size is specified
     * by <code>r.width</code> and <code>r.height</code>
     * 
     * @param r the new bounding rectangle for this component
     * @see #getBounds
     * @see #setLocation(int, int)
     * @see #setLocation(Point)
     * @see #setSize(int, int)
     * @see #setSize(Dimension)
     * @since JDK1.1
     */
    public void setBounds(Rectangle r);

    /**
     * Sets the language-sensitive orientation that is to be used to order the
     * elements or text within this component. Language-sensitive
     * <code>LayoutManager</code> and <code>Component</code> subclasses will
     * use this property to determine how to lay out and draw components.
     * <p>
     * At construction time, a component's orientation is set to
     * <code>ComponentOrientation.UNKNOWN</code>, indicating that it has not
     * been specified explicitly. The UNKNOWN orientation behaves the same as
     * <code>ComponentOrientation.LEFT_TO_RIGHT</code>.
     * <p>
     * To set the orientation of a single component, use this method. To set the
     * orientation of an entire component hierarchy, use
     * {@link #applyComponentOrientation applyComponentOrientation}.
     * 
     * @see ComponentOrientation
     */
    public void setComponentOrientation(ComponentOrientation o);

    /**
     * Sets the cursor image to the specified cursor. This cursor image is
     * displayed when the <code>contains</code> method for this component
     * returns true for the current cursor location, and this Component is
     * visible, displayable, and enabled. Setting the cursor of a
     * <code>Container</code> causes that cursor to be displayed within all of
     * the container's subcomponents, except for those that have a non-<code>null</code>
     * cursor.
     * 
     * @param cursor One of the constants defined by the <code>Cursor</code>
     *            class; if this parameter is <code>null</code> then this
     *            component will inherit the cursor of its parent
     * @see #isEnabled
     * @see #isShowing
     * @see #getCursor
     * @see #contains
     * @see Toolkit#createCustomCursor
     * @see Cursor
     * @since JDK1.1
     */
    public void setCursor(Cursor cursor);

    /**
     * Associate a <code>DropTarget</code> with this component. The
     * <code>Component</code> will receive drops only if it is enabled.
     * 
     * @see #isEnabled
     * @param dt The DropTarget
     */

    public void setDropTarget(DropTarget dt);

    /**
     * Enables or disables this component, depending on the value of the
     * parameter <code>b</code>. An enabled component can respond to user
     * input and generate events. Components are enabled initially by default.
     * <p>
     * Note: Disabling a lightweight component does not prevent it from
     * receiving MouseEvents.
     * 
     * @param b If <code>true</code>, this component is enabled; otherwise
     *            this component is disabled
     * @see #isEnabled
     * @see #isLightweight
     * @since JDK1.1
     */
    public void setEnabled(boolean b);

    /**
     * Sets the focusable state of this Component to the specified value. This
     * value overrides the Component's default focusability.
     * 
     * @param focusable indicates whether this Component is focusable
     * @see #isFocusable
     * @since 1.4
     */
    public void setFocusable(boolean focusable);

    /**
     * Sets the focus traversal keys for a given traversal operation for this
     * Component.
     * <p>
     * The default values for a Component's focus traversal keys are
     * implementation-dependent. Sun recommends that all implementations for a
     * particular native platform use the same default values. The
     * recommendations for Windows and Unix are listed below. These
     * recommendations are used in the Sun AWT implementations. <table border=1
     * summary="Recommended default values for a Component's focus traversal
     * keys">
     * <tr>
     * <th>Identifier</th>
     * <th>Meaning</th>
     * <th>Default</th>
     * </tr>
     * <tr>
     * <td>KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS</td>
     * <td>Normal forward keyboard traversal</td>
     * <td>TAB on KEY_PRESSED, CTRL-TAB on KEY_PRESSED</td>
     * </tr>
     * <tr>
     * <td>KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS</td>
     * <td>Normal reverse keyboard traversal</td>
     * <td>SHIFT-TAB on KEY_PRESSED, CTRL-SHIFT-TAB on KEY_PRESSED</td>
     * </tr>
     * <tr>
     * <td>KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS</td>
     * <td>Go up one focus traversal cycle</td>
     * <td>none</td>
     * </tr>
     * </table> To disable a traversal key, use an empty Set;
     * Collections.EMPTY_SET is recommended.
     * <p>
     * Using the AWTKeyStroke API, client code can specify on which of two
     * specific KeyEvents, KEY_PRESSED or KEY_RELEASED, the focus traversal
     * operation will occur. Regardless of which KeyEvent is specified, however,
     * all KeyEvents related to the focus traversal key, including the
     * associated KEY_TYPED event, will be consumed, and will not be dispatched
     * to any Component. It is a runtime error to specify a KEY_TYPED event as
     * mapping to a focus traversal operation, or to map the same event to
     * multiple default focus traversal operations.
     * <p>
     * If a value of null is specified for the Set, this Component inherits the
     * Set from its parent. If all ancestors of this Component have null
     * specified for the Set, then the current KeyboardFocusManager's default
     * Set is used.
     * 
     * @param id one of KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
     *            KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, or
     *            KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS
     * @param keystrokes the Set of AWTKeyStroke for the specified operation
     * @see #getFocusTraversalKeys
     * @see KeyboardFocusManager#FORWARD_TRAVERSAL_KEYS
     * @see KeyboardFocusManager#BACKWARD_TRAVERSAL_KEYS
     * @see KeyboardFocusManager#UP_CYCLE_TRAVERSAL_KEYS
     * @throws IllegalArgumentException if id is not one of
     *             KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
     *             KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, or
     *             KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS, or if
     *             keystrokes contains null, or if any Object in keystrokes is
     *             not an AWTKeyStroke, or if any keystroke represents a
     *             KEY_TYPED event, or if any keystroke already maps to another
     *             focus traversal operation for this Component
     * @since 1.4
     */
    //public void setFocusTraversalKeys(int id, Set keystrokes);

    /**
     * Sets whether focus traversal keys are enabled for this Component.
     * Components for which focus traversal keys are disabled receive key events
     * for focus traversal keys. Components for which focus traversal keys are
     * enabled do not see these events; instead, the events are automatically
     * converted to traversal operations.
     * 
     * @param focusTraversalKeysEnabled whether focus traversal keys are enabled
     *            for this Component
     * @see #getFocusTraversalKeysEnabled
     * @see #setFocusTraversalKeys
     * @see #getFocusTraversalKeys
     * @since 1.4
     */
    public void setFocusTraversalKeysEnabled(boolean focusTraversalKeysEnabled);

    /**
     * Sets the font of this component.
     * 
     * @param f the font to become this component's font; if this parameter is
     *            <code>null</code> then this component will inherit the font
     *            of its parent
     * @see #getFont
     * @since JDK1.0
     */
    public void setFont(Font f);

    /**
     * Sets the foreground color of this component.
     * 
     * @param c the color to become this component's foreground color; if this
     *            parameter is <code>null</code> then this component will
     *            inherit the foreground color of its parent
     * @see #getForeground
     * @since JDK1.0
     */
    public void setForeground(Color c);

    /**
     * Sets whether or not paint messages received from the operating system
     * should be ignored. This does not affect paint events generated in
     * software by the AWT, unless they are an immediate response to an OS-level
     * paint message.
     * <p>
     * This is useful, for example, if running under full-screen mode and better
     * performance is desired, or if page-flipping is used as the buffer
     * strategy.
     * 
     * @since 1.4
     * @see #getIgnoreRepaint
     * @see Canvas#createBufferStrategy
     * @see Window#createBufferStrategy
     * @see java.awt.image.BufferStrategy
     * @see GraphicsDevice#setFullScreenWindow
     */
    public void setIgnoreRepaint(boolean ignoreRepaint);

    /**
     * Sets the locale of this component. This is a bound property.
     * 
     * @param l the locale to become this component's locale
     * @see #getLocale
     * @since JDK1.1
     */
    public void setLocale(Locale l);

    /**
     * Moves this component to a new location. The top-left corner of the new
     * location is specified by the <code>x</code> and <code>y</code>
     * parameters in the coordinate space of this component's parent.
     * 
     * @param x the <i>x</i>-coordinate of the new location's top-left corner
     *            in the parent's coordinate space
     * @param y the <i>y</i>-coordinate of the new location's top-left corner
     *            in the parent's coordinate space
     * @see #getLocation
     * @see #setBounds
     * @since JDK1.1
     */
    public void setLocation(int x, int y);

    /**
     * Moves this component to a new location. The top-left corner of the new
     * location is specified by point <code>p</code>. Point <code>p</code>
     * is given in the parent's coordinate space.
     * 
     * @param p the point defining the top-left corner of the new location,
     *            given in the coordinate space of this component's parent
     * @see #getLocation
     * @see #setBounds
     * @since JDK1.1
     */
    public void setLocation(Point p);

    /**
     * Sets the name of the component to the specified string.
     * 
     * @param name the string that is to be this component's name
     * @see #getName
     * @since JDK1.1
     */
    public void setName(String name);

    /**
     * Resizes this component so that it has width <code>d.width</code> and
     * height <code>d.height</code>.
     * 
     * @param d the dimension specifying the new size of this component
     * @see #setSize
     * @see #setBounds
     * @since JDK1.1
     */
    public void setSize(Dimension d);

    /**
     * Resizes this component so that it has width <code>width</code> and
     * height <code>height</code>.
     * 
     * @param width the new width of this component in pixels
     * @param height the new height of this component in pixels
     * @see #getSize
     * @see #setBounds
     * @since JDK1.1
     */
    public void setSize(int width, int height);

    /**
     * Shows or hides this component depending on the value of parameter
     * <code>b</code>.
     * 
     * @param b if <code>true</code>, shows this component; otherwise, hides
     *            this component
     * @see #isVisible
     * @since JDK1.1
     */
    public void setVisible(boolean b);

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>setVisible(boolean)</code>.
     */
    public void show();

    /**
     * @deprecated As of JDK version 1.1, replaced by
     *             <code>setVisible(boolean)</code>.
     */
    public void show(boolean b);

    /**
     * @deprecated As of JDK version 1.1, replaced by <code>getSize()</code>.
     */
    public Dimension size();

    /**
     * Returns a string representation of this component and its values.
     * 
     * @return a string representation of this component
     * @since JDK1.0
     */
    public String toString();

    /**
     * Transfers the focus to the next component, as though this Component were
     * the focus owner.
     * 
     * @see #requestFocus()
     * @since JDK1.1
     */
    public void transferFocus();

    /**
     * Transfers the focus to the previous component, as though this Component
     * were the focus owner.
     * 
     * @see #requestFocus()
     * @since 1.4
     */
    public void transferFocusBackward();

    /**
     * Transfers the focus up one focus traversal cycle. Typically, the focus
     * owner is set to this Component's focus cycle root, and the current focus
     * cycle root is set to the new focus owner's focus cycle root. If, however,
     * this Component's focus cycle root is a Window, then the focus owner is
     * set to the focus cycle root's default Component to focus, and the current
     * focus cycle root is unchanged.
     * 
     * @see #requestFocus()
     * @see Container#isFocusCycleRoot()
     * @see Container#setFocusCycleRoot(boolean)
     * @since 1.4
     */
    public void transferFocusUpCycle();

    /**
     * Updates this component.
     * <p>
     * If this component is not a lightweight component, the AWT calls the
     * <code>update</code> method in response to a call to
     * <code>repaint</code>. You can assume that the background is not
     * cleared.
     * <p>
     * The <code>update</code>method of <code>Component</code> calls this
     * component's <code>paint</code> method to redraw this component. This
     * method is commonly overridden by subclasses which need to do additional
     * work in response to a call to <code>repaint</code>. Subclasses of
     * Component that override this method should either call
     * <code>super.update(g)</code>, or call <code>paint</code> directly.
     * <p>
     * The origin of the graphics context, its (<code>0</code>,&nbsp;<code>0</code>)
     * coordinate point, is the top-left corner of this component. The clipping
     * region of the graphics context is the bounding rectangle of this
     * component.
     * <p>
     * <b>Note</b>: For more information on the paint mechanisms utilitized by
     * AWT and Swing, including information on how to write the most efficient
     * painting code, see <a
     * href="http://java.sun.com/products/jfc/tsc/articles/painting/index.html">Painting
     * in AWT and Swing</a>.
     * 
     * @param g the specified context to use for updating
     * @see #paint
     * @see #repaint()
     * @since JDK1.0
     */
    public void update(Graphics g);

    /**
     * Ensures that this component has a valid layout. This method is primarily
     * intended to operate on instances of <code>Container</code>.
     * 
     * @see #invalidate
     * @see #doLayout()
     * @see LayoutManager
     * @see Container#validate
     * @since JDK1.0
     */
    public void validate();
}
