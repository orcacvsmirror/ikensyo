package jp.nichicom.vr.component;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.VetoableChangeListener;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JToolTip;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.RepaintManager;
import javax.swing.SortingFocusTraversalPolicy;
import javax.swing.TransferHandler;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * Swingコンポーネントインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JComponent
 * @see VRContainar
 */
public interface VRJComponentar extends VRContainar {

    /**
     * Registers <code>listener</code> so that it will receive
     * <code>AncestorEvents</code> when it or any of its ancestors move or are
     * made visible or invisible. Events are also sent when the component or its
     * ancestors are added or removed from the containment hierarchy.
     * 
     * @param listener the <code>AncestorListener</code> to register
     * @see AncestorEvent
     */
    public void addAncestorListener(AncestorListener listener);

    /**
     * Adds a <code>VetoableChangeListener</code> to the listener list. The
     * listener is registered for all properties.
     * 
     * @param listener the <code>VetoableChangeListener</code> to be added
     */
    public void addVetoableChangeListener(VetoableChangeListener listener);

    /**
     * Returns the <code>Component</code>'s "visible rect rectangle" - the
     * intersection of the visible rectangles for this component and all of its
     * ancestors. The return value is stored in <code>visibleRect</code>.
     * 
     * @param visibleRect a <code>Rectangle</code> computed as the
     *            intersection of all visible rectangles for this component and
     *            all of its ancestors -- this is the return value for this
     *            method
     * @see #getVisibleRect
     */
    public void computeVisibleRect(Rectangle visibleRect);

    /**
     * Returns the instance of <code>JToolTip</code> that should be used to
     * display the tooltip. Components typically would not override this method,
     * but it can be used to cause different tooltips to be displayed
     * differently.
     * 
     * @return the <code>JToolTip</code> used to display this toolTip
     */
    public JToolTip createToolTip();

    /**
     * Reports a bound property change.
     * 
     * @param propertyName the programmatic name of the property that was
     *            changed
     * @param oldValue the old value of the property (as a byte)
     * @param newValue the new value of the property (as a byte)
     */
    public void firePropertyChange(String propertyName, byte oldValue,
            byte newValue);

    /**
     * Reports a bound property change.
     * 
     * @param propertyName the programmatic name of the property that was
     *            changed
     * @param oldValue the old value of the property (as a char)
     * @param newValue the new value of the property (as a char)
     */
    public void firePropertyChange(String propertyName, char oldValue,
            char newValue);

    /**
     * Reports a bound property change.
     * 
     * @param propertyName the programmatic name of the property that was
     *            changed
     * @param oldValue the old value of the property (as a short)
     * @param newValue the old value of the property (as a short)
     */
    public void firePropertyChange(String propertyName, short oldValue,
            short newValue);

    /**
     * Reports a bound property change.
     * 
     * @param propertyName the programmatic name of the property that was
     *            changed
     * @param oldValue the old value of the property (as a long)
     * @param newValue the new value of the property (as a long)
     */
    public void firePropertyChange(String propertyName, long oldValue,
            long newValue);

    /**
     * Reports a bound property change.
     * 
     * @param propertyName the programmatic name of the property that was
     *            changed
     * @param oldValue the old value of the property (as a float)
     * @param newValue the new value of the property (as a float)
     */
    public void firePropertyChange(String propertyName, float oldValue,
            float newValue);

    /**
     * Reports a bound property change.
     * 
     * @param propertyName the programmatic name of the property that was
     *            changed
     * @param oldValue the old value of the property (as a double)
     * @param newValue the new value of the property (as a double)
     */
    public void firePropertyChange(String propertyName, double oldValue,
            double newValue);

    /**
     * Returns the object that will perform the action registered for a given
     * keystroke.
     * 
     * @return the <code>ActionListener</code> object invoked when the
     *         keystroke occurs
     */
    public ActionListener getActionForKeyStroke(KeyStroke aKeyStroke);

    /**
     * Returns the <code>ActionMap</code> used to determine what
     * <code>Action</code> to fire for particular <code>KeyStroke</code>
     * binding. The returned <code>ActionMap</code>, unless otherwise set,
     * will have the <code>ActionMap</code> from the UI set as the parent.
     * 
     * @return the <code>ActionMap</code> containing the key/action bindings
     * @since 1.3
     */
    public ActionMap getActionMap();

    /**
     * Returns an array of all the ancestor listeners registered on this
     * component.
     * 
     * @return all of the component's <code>AncestorListener</code>s or an
     *         empty array if no ancestor listeners are currently registered
     * @see #addAncestorListener
     * @see #removeAncestorListener
     * @since 1.4
     */
    public AncestorListener[] getAncestorListeners();

    /**
     * Gets the <code>autoscrolls</code> property.
     * 
     * @return the value of the <code>autoscrolls</code> property
     * @see JViewport
     * @see #setAutoscrolls
     */
    public boolean getAutoscrolls();

    /**
     * Returns the border of this component or <code>null</code> if no border
     * is currently set.
     * 
     * @return the border object for this component
     * @see #setBorder
     */
    public Border getBorder();

    /**
     * Returns the value of the property with the specified key. Only properties
     * added with <code>putClientProperty</code> will return a non-<code>null</code>
     * value.
     * 
     * @param key the being queried
     * @return the value of this property or <code>null</code>
     * @see #putClientProperty
     */
    public Object getClientProperty(Object key);

    /**
     * Returns the condition that determines whether a registered action occurs
     * in response to the specified keystroke.
     * <p>
     * For Java 2 platform v1.3, a <code>KeyStroke</code> can be associated
     * with more than one condition. For example, 'a' could be bound for the two
     * conditions <code>WHEN_FOCUSED</code> and
     * <code>WHEN_IN_FOCUSED_WINDOW</code> condition.
     * 
     * @return the action-keystroke condition
     */
    public int getConditionForKeyStroke(KeyStroke aKeyStroke);

    /**
     * Returns the state of graphics debugging.
     * 
     * @return a bitwise OR'd flag of zero or more of the following options:
     *         <ul>
     *         <li>DebugGraphics.LOG_OPTION - causes a text message to be
     *         printed.
     *         <li>DebugGraphics.FLASH_OPTION - causes the drawing to flash
     *         several times.
     *         <li>DebugGraphics.BUFFERED_OPTION - creates an
     *         <code>ExternalWindow</code> that displays the operations
     *         performed on the View's offscreen buffer.
     *         <li>DebugGraphics.NONE_OPTION disables debugging.
     *         <li>A value of 0 causes no changes to the debugging options.
     *         </ul>
     * @see #setDebugGraphicsOptions
     */
    public int getDebugGraphicsOptions();

    /**
     * Returns the <code>InputMap</code> that is used when the component has
     * focus. This is convenience method for
     * <code>getInputMap(WHEN_FOCUSED)</code>.
     * 
     * @return the <code>InputMap</code> used when the component has focus
     * @since JDK1.3
     */
    public InputMap getInputMap();

    /**
     * Returns the <code>InputMap</code> that is used during
     * <code>condition</code>.
     * 
     * @param condition one of WHEN_IN_FOCUSED_WINDOW, WHEN_FOCUSED,
     *            WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
     * @return the <code>InputMap</code> for the specified
     *         <code>condition</code>
     * @since 1.3
     */
    public InputMap getInputMap(int condition);

    /**
     * Returns the input verifier for this component.
     * 
     * @return the <code>inputVerifier</code> property
     * @since 1.3
     * @see InputVerifier
     */
    public InputVerifier getInputVerifier();

    /**
     * Returns an <code>Insets</code> object containing this component's inset
     * values. The passed-in <code>Insets</code> object will be reused if
     * possible. Calling methods cannot assume that the same object will be
     * returned, however. All existing values within this object are
     * overwritten. If <code>insets</code> is null, this will allocate a new
     * one.
     * 
     * @param insets the <code>Insets</code> object, which can be reused
     * @return the <code>Insets</code> object
     * @see #getInsets
     */
    public Insets getInsets(Insets insets);

    /**
     * Returns the <code>Component</code> set by a prior call to
     * <code>setNextFocusableComponent(Component)</code> on this
     * <code>JComponent</code>.
     * 
     * @return the <code>Component</code> that will follow this
     *         <code>JComponent</code> in the focus traversal cycle, or
     *         <code>null</code> if none has been explicitly specified
     * @see #setNextFocusableComponent
     * @deprecated As of 1.4, replaced by <code>FocusTraversalPolicy</code>.
     */
    public Component getNextFocusableComponent();

    /**
     * Returns the <code>KeyStrokes</code> that will initiate registered
     * actions.
     * 
     * @return an array of <code>KeyStroke</code> objects
     * @see #registerKeyboardAction
     */
    public KeyStroke[] getRegisteredKeyStrokes();

    /**
     * Returns the <code>JRootPane</code> ancestor for this component.
     * 
     * @return the <code>JRootPane</code> that contains this component, or
     *         <code>null</code> if no <code>JRootPane</code> is found
     */
    public JRootPane getRootPane();

    /**
     * Returns the tooltip location in this component's coordinate system. If
     * <code>null</code> is returned, Swing will choose a location. The
     * default implementation returns <code>null</code>.
     * 
     * @param event the <code>MouseEvent</code> that caused the
     *            <code>ToolTipManager</code> to show the tooltip
     * @return always returns <code>null</code>
     */
    public Point getToolTipLocation(MouseEvent event);

    /**
     * Returns the tooltip string that has been set with
     * <code>setToolTipText</code>.
     * 
     * @return the text of the tool tip
     */
    public String getToolTipText();

    /**
     * Returns the string to be used as the tooltip for <i>event</i>. By
     * default this returns any string set using <code>setToolTipText</code>.
     * If a component provides more extensive API to support differing tooltips
     * at different locations, this method should be overridden.
     */
    public String getToolTipText(MouseEvent event);

    /**
     * Returns the top-level ancestor of this component (either the containing
     * <code>Window</code> or <code>Applet</code>), or <code>null</code>
     * if this component has not been added to any container.
     * 
     * @return the top-level <code>Container</code> that this component is in,
     *         or <code>null</code> if not in any container
     */
    public Container getTopLevelAncestor();

    /**
     * Gets the <code>transferHandler</code> property.
     * 
     * @return the value of the <code>transferHandler</code> property
     * @see TransferHandler
     * @see #setTransferHandler
     * @since 1.4
     */
    public TransferHandler getTransferHandler();

    /**
     * Returns the <code>UIDefaults</code> key used to look up the name of the
     * <code>swing.plaf.ComponentUI</code> class that defines the look and
     * feel for this component. Most applications will never need to call this
     * method. Subclasses of <code>JComponent</code> that support pluggable
     * look and feel should override this method to return a
     * <code>UIDefaults</code> key that maps to the <code>ComponentUI</code>
     * subclass that defines their look and feel.
     * 
     * @return the <code>UIDefaults</code> key for a <code>ComponentUI</code>
     *         subclass
     * @see UIDefaults#getUI
     */
    public String getUIClassID();

    /**
     * Returns the value that indicates whether the input verifier for the
     * current focus owner will be called before this component requests focus.
     * 
     * @return value of the <code>verifyInputWhenFocusTarget</code> property
     * @see InputVerifier
     * @see #setInputVerifier
     * @see #getInputVerifier
     * @see #setVerifyInputWhenFocusTarget
     * @since 1.3
     */
    public boolean getVerifyInputWhenFocusTarget();

    /**
     * Returns an array of all the vetoable change listeners registered on this
     * component.
     * 
     * @return all of the component's <code>VetoableChangeListener</code>s or
     *         an empty array if no vetoable change listeners are currently
     *         registered
     * @see #addVetoableChangeListener
     * @see #removeVetoableChangeListener
     * @since 1.4
     */
    public VetoableChangeListener[] getVetoableChangeListeners();

    /**
     * Returns the <code>Component</code>'s "visible rectangle" - the
     * intersection of this component's visible rectangle:
     * 
     * <pre>
     * new Rectangle(0, 0, getWidth(), getHeight());
     * </pre>
     * 
     * and all of its ancestors' visible rectangles.
     * 
     * @return the visible rectangle
     */
    public Rectangle getVisibleRect();

    /**
     * Requests that this Component get the input focus, and that this
     * Component's top-level ancestor become the focused Window. This component
     * must be displayable, visible, and focusable for the request to be
     * granted.
     * <p>
     * This method is intended for use by focus implementations. Client code
     * should not use this method; instead, it should use
     * <code>requestFocus()</code>.
     * 
     * @see #requestFocus()
     */
    public void grabFocus();

    /**
     * Changes this <code>JComponent</code>'s focus traversal keys to
     * CTRL+TAB and CTRL+SHIFT+TAB. Also prevents
     * <code>SortingFocusTraversalPolicy</code> from considering descendants
     * of this JComponent when computing a focus traversal cycle.
     * 
     * @see java.awt.Component#setFocusTraversalKeys
     * @see SortingFocusTraversalPolicy
     * @deprecated As of 1.4, replaced by
     *             <code>Component.setFocusTraversalKeys(int, Set)</code> and
     *             <code>Container.setFocusCycleRoot(boolean)</code>.
     */
    public boolean isManagingFocus();

    /**
     * Returns true if the maximum size has been set to a non-<code>null</code>
     * value otherwise returns false.
     * 
     * @return true if <code>maximumSize</code> is non-<code>null</code>,
     *         false otherwise
     * @since 1.3
     */
    public boolean isMaximumSizeSet();

    /**
     * Returns true if the minimum size has been set to a non-<code>null</code>
     * value otherwise returns false.
     * 
     * @return true if <code>minimumSize</code> is non-<code>null</code>,
     *         false otherwise
     * @since 1.3
     */
    public boolean isMinimumSizeSet();

    /**
     * Returns true if this component tiles its children -- that is, if it can
     * guarantee that the children will not overlap. The repainting system is
     * substantially more efficient in this common case. <code>JComponent</code>
     * subclasses that can't make this guarantee, such as
     * <code>JLayeredPane</code>, should override this method to return
     * false.
     * 
     * @return always returns true
     */
    public boolean isOptimizedDrawingEnabled();

    /**
     * Returns true if the component is currently painting a tile. If this
     * method returns true, paint will be called again for another tile. This
     * method returns false if you are not painting a tile or if the last tile
     * is painted. Use this method to keep some state you might need between
     * tiles.
     * 
     * @return true if the component is currently painting a tile, false
     *         otherwise
     */
    public boolean isPaintingTile();

    /**
     * Returns true if the preferred size has been set to a non-<code>null</code>
     * value otherwise returns false.
     * 
     * @return true if <code>preferredSize</code> is non-<code>null</code>,
     *         false otherwise
     * @since 1.3
     */
    public boolean isPreferredSizeSet();

    /**
     * Returns <code>true</code> if this <code>JComponent</code> should get
     * focus; otherwise returns <code>false</code>.
     * 
     * @return <code>true</code> if this component should get focus, otherwise
     *         returns <code>false</code>
     * @see #setRequestFocusEnabled
     * @see <a href="../../java/awt/doc-files/FocusSpec.html">Focus
     *      Specification</a>
     * @see java.awt.Component#isFocusable
     */
    public boolean isRequestFocusEnabled();

    /**
     * If this method returns true, <code>revalidate</code> calls by
     * descendants of this component will cause the entire tree beginning with
     * this root to be validated. Returns false by default.
     * <code>JScrollPane</code> overrides this method and returns true.
     * 
     * @return always returns false
     * @see #revalidate
     * @see java.awt.Component#invalidate
     * @see java.awt.Container#validate
     */
    public boolean isValidateRoot();

    /**
     * Paints the specified region in this component and all of its descendants
     * that overlap the region, immediately.
     * <p>
     * It's rarely necessary to call this method. In most cases it's more
     * efficient to call repaint, which defers the actual painting and can
     * collapse redundant requests into a single paint call. This method is
     * useful if one needs to update the display while the current event is
     * being dispatched.
     * 
     * @param x the x value of the region to be painted
     * @param y the y value of the region to be painted
     * @param w the width of the region to be painted
     * @param h the height of the region to be painted
     * @see #repaint
     */
    public void paintImmediately(int x, int y, int w, int h);

    /**
     * Paints the specified region now.
     * 
     * @param r a <code>Rectangle</code> containing the region to be painted
     */
    public void paintImmediately(Rectangle r);

    /**
     * Adds an arbitrary key/value "client property" to this component.
     * <p>
     * The <code>get/putClientProperty</code> methods provide access to a
     * small per-instance hashtable. Callers can use get/putClientProperty to
     * annotate components that were created by another module. For example, a
     * layout manager might store per child constraints this way. For example:
     * 
     * <pre>
     * componentA.putClientProperty(&quot;to the left of&quot;, componentB);
     * </pre>
     * 
     * If value is <code>null</code> this method will remove the property.
     * Changes to client properties are reported with
     * <code>PropertyChange</code> events. The name of the property (for the
     * sake of PropertyChange events) is <code>key.toString()</code>.
     * <p>
     * The <code>clientProperty</code> dictionary is not intended to support
     * large scale extensions to JComponent nor should be it considered an
     * alternative to subclassing when designing a new component.
     * 
     * @param key the new client property key
     * @param value the new client property value; if <code>null</code> this
     *            method will remove the property
     * @see #getClientProperty
     * @see #addPropertyChangeListener
     */
    public void putClientProperty(Object key, Object value);

    /**
     * This method is now obsolete, please use a combination of
     * <code>getActionMap()</code> and <code>getInputMap()</code> for
     * similiar behavior. For example, to bind the <code>KeyStroke</code>
     * <code>aKeyStroke</code>
     * to the <code>Action</code> <code>anAction</code> now use:
     * 
     * <pre>
     * component.getInputMap().put(aKeyStroke, aCommand);
     * component.getActionMap().put(aCommmand, anAction);
     * </pre>
     * 
     * The above assumes you want the binding to be applicable for
     * <code>WHEN_FOCUSED</code>. To register bindings for other focus states
     * use the <code>getInputMap</code> method that takes an integer.
     * <p>
     * Register a new keyboard action. <code>anAction</code> will be invoked
     * if a key event matching <code>aKeyStroke</code> occurs and
     * <code>aCondition</code> is verified. The <code>KeyStroke</code>
     * object defines a particular combination of a keyboard key and one or more
     * modifiers (alt, shift, ctrl, meta).
     * <p>
     * The <code>aCommand</code> will be set in the delivered event if
     * specified.
     * <p>
     * The <code>aCondition</code> can be one of: <blockquote>
     * <DL>
     * <DT>WHEN_FOCUSED
     * <DD>The action will be invoked only when the keystroke occurs while the
     * component has the focus.
     * <DT>WHEN_IN_FOCUSED_WINDOW
     * <DD>The action will be invoked when the keystroke occurs while the
     * component has the focus or if the component is in the window that has the
     * focus. Note that the component need not be an immediate descendent of the
     * window -- it can be anywhere in the window's containment hierarchy. In
     * other words, whenever <em>any</em> component in the window has the
     * focus, the action registered with this component is invoked.
     * <DT>WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
     * <DD>The action will be invoked when the keystroke occurs while the
     * component has the focus or if the component is an ancestor of the
     * component that has the focus.
     * </DL>
     * </blockquote>
     * <p>
     * The combination of keystrokes and conditions lets you define high level
     * (semantic) action events for a specified keystroke+modifier combination
     * (using the KeyStroke class) and direct to a parent or child of a
     * component that has the focus, or to the component itself. In other words,
     * in any hierarchical structure of components, an arbitrary key-combination
     * can be immediately directed to the appropriate component in the
     * hierarchy, and cause a specific method to be invoked (usually by way of
     * adapter objects).
     * <p>
     * If an action has already been registered for the receiving container,
     * with the same charCode and the same modifiers, <code>anAction</code>
     * will replace the action.
     * 
     * @param anAction the <code>Action</code> to be registered
     * @param aCommand the command to be set in the delivered event
     * @param aKeyStroke the <code>KeyStroke</code> to bind to the action
     * @param aCondition the condition that needs to be met, see above
     * @see KeyStroke
     */
    public void registerKeyboardAction(ActionListener anAction,
            String aCommand, KeyStroke aKeyStroke, int aCondition);

    /**
     * This method is now obsolete, please use a combination of
     * <code>getActionMap()</code> and <code>getInputMap()</code> for
     * similiar behavior.
     */
    public void registerKeyboardAction(ActionListener anAction,
            KeyStroke aKeyStroke, int aCondition);

    /**
     * Unregisters <code>listener</code> so that it will no longer receive
     * <code>AncestorEvents</code>.
     * 
     * @param listener the <code>AncestorListener</code> to be removed
     * @see #addAncestorListener
     */
    public void removeAncestorListener(AncestorListener listener);

    /**
     * Removes a <code>VetoableChangeListener</code> from the listener list.
     * This removes a <code>VetoableChangeListener</code> that was registered
     * for all properties.
     * 
     * @param listener the <code>VetoableChangeListener</code> to be removed
     */
    public void removeVetoableChangeListener(VetoableChangeListener listener);

    /**
     * Adds the specified region to the dirty region list if the component is
     * showing. The component will be repainted after all of the currently
     * pending events have been dispatched.
     * 
     * @param r a <code>Rectangle</code> containing the dirty region
     * @see java.awt.Component#isShowing
     * @see RepaintManager#addDirtyRegion
     */
    public void repaint(Rectangle r);

    /**
     * Requests focus on this <code>JComponent</code>'s
     * <code>FocusTraversalPolicy</code>'s default <code>Component</code>.
     * If this <code>JComponent</code> is a focus cycle root, then its
     * <code>FocusTraversalPolicy</code> is used. Otherwise, the
     * <code>FocusTraversalPolicy</code> of this <code>JComponent</code>'s
     * focus-cycle-root ancestor is used.
     * 
     * @see java.awt.FocusTraversalPolicy#getDefaultComponent
     * @deprecated As of 1.4, replaced by
     *             <code>FocusTraversalPolicy.getDefaultComponent(Container).requestFocus()</code>
     */
    public boolean requestDefaultFocus();

    /**
     * Unregisters all the bindings in the first tier <code>InputMaps</code>
     * and <code>ActionMap</code>. This has the effect of removing any local
     * bindings, and allowing the bindings defined in parent
     * <code>InputMap/ActionMaps</code> (the UI is usually defined in the
     * second tier) to persist.
     */
    public void resetKeyboardActions();

    /**
     * Supports deferred automatic layout.
     * <p>
     * Calls <code>invalidate</code> and then adds this component's
     * <code>validateRoot</code> to a list of components that need to be
     * validated. Validation will occur after all currently pending events have
     * been dispatched. In other words after this method is called, the first
     * validateRoot (if any) found when walking up the containment hierarchy of
     * this component will be validated. By default, <code>JRootPane</code>,
     * <code>JScrollPane</code>, and <code>JTextField</code> return true
     * from <code>isValidateRoot</code>.
     * <p>
     * This method will automatically be called on this component when a
     * property value changes such that size, location, or internal layout of
     * this component has been affected. This automatic updating differs from
     * the AWT because programs generally no longer need to invoke
     * <code>validate</code> to get the contents of the GUI to update.
     * <p>
     * 
     * @see java.awt.Component#invalidate
     * @see java.awt.Container#validate
     * @see #isValidateRoot
     * @see RepaintManager#addInvalidComponent
     */
    public void revalidate();

    /**
     * Forwards the <code>scrollRectToVisible()</code> message to the
     * <code>JComponent</code>'s parent. Components that can service the
     * request, such as <code>JViewport</code>, override this method and
     * perform the scrolling.
     * 
     * @param aRect the visible <code>Rectangle</code>
     * @see JViewport
     */
    public void scrollRectToVisible(Rectangle aRect);

    /**
     * Sets the <code>ActionMap</code> to <code>am</code>. This does not
     * set the parent of the <code>am</code> to be the <code>ActionMap</code>
     * from the UI (if there was one), it is up to the caller to have done this.
     * 
     * @param am the new <code>ActionMap</code>
     * @since 1.3
     */
    public void setActionMap(ActionMap am);

    /**
     * Sets the the vertical alignment.
     * 
     * @param alignmentX the new vertical alignment
     * @see #getAlignmentX
     */
    public void setAlignmentX(float alignmentX);

    /**
     * Sets the the horizontal alignment.
     * 
     * @param alignmentY the new horizontal alignment
     * @see #getAlignmentY
     */
    public void setAlignmentY(float alignmentY);

    /**
     * Sets the <code>autoscrolls</code> property. If <code>true</code>
     * mouse dragged events will be synthetically generated when the mouse is
     * dragged outside of the component's bounds and mouse motion has paused
     * (while the button continues to be held down). The synthetic events make
     * it appear that the drag gesture has resumed in the direction established
     * when the component's boundary was crossed. Components that support
     * autoscrolling must handle <code>mouseDragged</code> events by calling
     * <code>scrollRectToVisible</code> with a rectangle that contains the
     * mouse event's location. All of the Swing components that support item
     * selection and are typically displayed in a <code>JScrollPane</code> (<code>JTable</code>,
     * <code>JList</code>, <code>JTree</code>, <code>JTextArea</code>,
     * and <code>JEditorPane</code>) already handle mouse dragged events in
     * this way. To enable autoscrolling in any other component, add a mouse
     * motion listener that calls <code>scrollRectToVisible</code>. For
     * example, given a <code>JPanel</code>, <code>myPanel</code>:
     * 
     * <pre>
     * MouseMotionListener doScrollRectToVisible = new MouseMotionAdapter() {
     *     public void mouseDragged(MouseEvent e) {
     *         Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
     *         ((JPanel) e.getSource()).scrollRectToVisible(r);
     *     }
     * };
     * myPanel.addMouseMotionListener(doScrollRectToVisible);
     * </pre>
     * 
     * The default value of the <code>autoScrolls</code> property is
     * <code>false</code>.
     * 
     * @param autoscrolls if true, synthetic mouse dragged events are generated
     *            when the mouse is dragged outside of a component's bounds and
     *            the mouse button continues to be held down; otherwise false
     * @see #getAutoscrolls
     * @see JViewport
     * @see JScrollPane
     */
    public void setAutoscrolls(boolean autoscrolls);

    /**
     * Sets the border of this component. The <code>Border</code> object is
     * responsible for defining the insets for the component (overriding any
     * insets set directly on the component) and for optionally rendering any
     * border decorations within the bounds of those insets. Borders should be
     * used (rather than insets) for creating both decorative and non-decorative
     * (such as margins and padding) regions for a swing component. Compound
     * borders can be used to nest multiple borders within a single component.
     * <p>
     * This is a bound property.
     * 
     * @param border the border to be rendered for this component
     * @see Border
     * @see CompoundBorder
     */
    public void setBorder(Border border);

    /**
     * Enables or disables diagnostic information about every graphics operation
     * performed within the component or one of its children.
     * 
     * @param debugOptions determines how the component should display the
     *            information; one of the following options:
     *            <ul>
     *            <li>DebugGraphics.LOG_OPTION - causes a text message to be
     *            printed.
     *            <li>DebugGraphics.FLASH_OPTION - causes the drawing to flash
     *            several times.
     *            <li>DebugGraphics.BUFFERED_OPTION - creates an
     *            <code>ExternalWindow</code> that displays the operations
     *            performed on the View's offscreen buffer.
     *            <li>DebugGraphics.NONE_OPTION disables debugging.
     *            <li>A value of 0 causes no changes to the debugging options.
     *            </ul>
     *            <code>debugOptions</code> is bitwise OR'd into the current
     *            value
     */
    public void setDebugGraphicsOptions(int debugOptions);

    /** Buffering * */

    /**
     * Sets whether the this component should use a buffer to paint. If set to
     * true, all the drawing from this component will be done in an offscreen
     * painting buffer. The offscreen painting buffer will the be copied onto
     * the screen. Swings painting system always uses a maximum of one double
     * buffer. If a <code>Component</code> is buffered and one of its ancestor
     * is also buffered, the ancestor buffer will be used.
     * 
     * @param aFlag if true, set this component to be double buffered
     */
    public void setDoubleBuffered(boolean aFlag);

    /**
     * Sets the <code>InputMap</code> to use under the condition
     * <code>condition</code> to <code>map</code>. A <code>null</code>
     * value implies you do not want any bindings to be used, even from the UI.
     * This will not reinstall the UI <code>InputMap</code> (if there was
     * one). <code>condition</code> has one of the following values:
     * <ul>
     * <li><code>WHEN_IN_FOCUSED_WINDOW</code>
     * <li><code>WHEN_FOCUSED</code>
     * <li><code>WHEN_ANCESTOR_OF_FOCUSED_COMPONENT</code>
     * </ul>
     * If <code>condition</code> is <code>WHEN_IN_FOCUSED_WINDOW</code> and
     * <code>map</code> is not a <code>ComponentInputMap</code>, an
     * <code>IllegalArgumentException</code> will be thrown. Similarly, if
     * <code>condition</code> is not one of the values listed, an
     * <code>IllegalArgumentException</code> will be thrown.
     * 
     * @param condition one of the values listed above
     * @param map the <code>InputMap</code> to use for the given condition
     * @exception IllegalArgumentException if <code>condition</code> is
     *                <code>WHEN_IN_FOCUSED_WINDOW</code> and <code>map</code>
     *                is not an instance of <code>ComponentInputMap</code>;
     *                or if <code>condition</code> is not one of the legal
     *                values specified above
     * @since 1.3
     */
    public void setInputMap(int condition, InputMap map);

    /**
     * Sets the input verifier for this component.
     * 
     * @param inputVerifier the new input verifier
     * @since 1.3
     * @see InputVerifier
     */
    public void setInputVerifier(InputVerifier inputVerifier);

    /**
     * Sets the maximum size of this component to a constant value. Subsequent
     * calls to <code>getMaximumSize</code> will always return this value; the
     * component's UI will not be asked to compute it. Setting the maximum size
     * to <code>null</code> restores the default behavior.
     * 
     * @param maximumSize a <code>Dimension</code> containing the desired
     *            maximum allowable size
     * @see #getMaximumSize
     */
    public void setMaximumSize(Dimension maximumSize);

    /**
     * Sets the minimum size of this component to a constant value. Subsequent
     * calls to <code>getMinimumSize</code> will always return this value; the
     * component's UI will not be asked to compute it. Setting the minimum size
     * to <code>null</code> restores the default behavior.
     * 
     * @param minimumSize the new minimum size of this component
     * @see #getMinimumSize
     */
    public void setMinimumSize(Dimension minimumSize);

    /**
     * Overrides the default <code>FocusTraversalPolicy</code> for this
     * <code>JComponent</code>'s focus traversal cycle by unconditionally
     * setting the specified <code>Component</code> as the next
     * <code>Component</code> in the cycle, and this <code>JComponent</code>
     * as the specified <code>Component</code>'s previous
     * <code>Component</code> in the cycle.
     * 
     * @param aComponent the <code>Component</code> that should follow this
     *            <code>JComponent</code> in the focus traversal cycle
     * @see #getNextFocusableComponent
     * @see java.awt.FocusTraversalPolicy
     * @deprecated As of 1.4, replaced by <code>FocusTraversalPolicy</code>
     */
    public void setNextFocusableComponent(Component aComponent);

    /**
     * If true the component paints every pixel within its bounds. Otherwise,
     * the component may not paint some or all of its pixels, allowing the
     * underlying pixels to show through.
     * <p>
     * The default value of this property is false for <code>JComponent</code>.
     * However, the default value for this property on most standard
     * <code>JComponent</code> subclasses (such as <code>JButton</code> and
     * <code>JTree</code>) is look-and-feel dependent.
     * 
     * @param isOpaque true if this component should be opaque
     * @see #isOpaque
     */
    public void setOpaque(boolean isOpaque);

    /**
     * Sets the preferred size of this component. If <code>preferredSize</code>
     * is <code>null</code>, the UI will be asked for the preferred size.
     */
    public void setPreferredSize(Dimension preferredSize);

    /**
     * Provides a hint as to whether or not this <code>JComponent</code>
     * should get focus. This is only a hint, and it is up to consumers that are
     * requesting focus to honor this property. This is typically honored for
     * mouse operations, but not keyboard operations. For example, look and
     * feels could verify this property is true before requesting focus during a
     * mouse operation. This would often times be used if you did not want a
     * mouse press on a <code>JComponent</code> to steal focus, but did want
     * the <code>JComponent</code> to be traversable via the keyboard. If you
     * do not want this <code>JComponent</code> focusable at all, use the
     * <code>setFocusable</code> method instead.
     * 
     * @param requestFocusEnabled Indicates if you want this JComponent to be
     *            focusable or not
     * @see <a href="../../java/awt/doc-files/FocusSpec.html">Focus
     *      Specification</a>
     * @see java.awt.Component#setFocusable
     */
    public void setRequestFocusEnabled(boolean requestFocusEnabled);

    /**
     * Registers the text to display in a tool tip. The text displays when the
     * cursor lingers over the component.
     * <p>
     * See <a
     * href="http://java.sun.com/docs/books/tutorial/uiswing/components/tooltip.html">How
     * to Use Tool Tips</a> in <em>The Java Tutorial</em> for further
     * documentation.
     * 
     * @param text the string to display; if the text is <code>null</code>,
     *            the tool tip is turned off for this component
     */
    public void setToolTipText(String text);

    /**
     * Sets the <code>transferHandler</code> property, which is
     * <code>null</code> if the component does not support data transfer
     * operations.
     * <p>
     * If <code>newHandler</code> is not null, and the system property
     * <code>suppressSwingDropSupport</code> is not true, this will install a
     * <code>DropTarget</code> on the <code>JComponent</code>. The default
     * for the system property is false, so that a <code>DropTarget</code>
     * will be added.
     * 
     * @param newHandler mechanism for transfer of data to and from the
     *            component
     * @see TransferHandler
     * @see #getTransferHandler
     * @since 1.4
     */
    public void setTransferHandler(TransferHandler newHandler);

    /**
     * Sets the value to indicate whether input verifier for the current focus
     * owner will be called before this component requests focus. The default is
     * true. Set to false on components such as a Cancel button or a scrollbar,
     * which should activate even if the input in the current focus owner is not
     * "passed" by the input verifier for that component.
     * 
     * @param verifyInputWhenFocusTarget value for the
     *            <code>verifyInputWhenFocusTarget</code> property
     * @see InputVerifier
     * @see #setInputVerifier
     * @see #getInputVerifier
     * @see #getVerifyInputWhenFocusTarget
     * @since 1.3
     */
    public void setVerifyInputWhenFocusTarget(boolean verifyInputWhenFocusTarget);

    /**
     * This method is now obsolete. To unregister an existing binding you can
     * either remove the binding from the <code>ActionMap/InputMap</code>, or
     * place a dummy binding the <code>InputMap</code>. Removing the binding
     * from the <code>InputMap</code> allows bindings in parent
     * <code>InputMap</code>s to be active, whereas putting a dummy binding
     * in the <code>InputMap</code> effectively disables the binding from ever
     * happening.
     * <p>
     * Unregisters a keyboard action. This will remove the binding from the
     * <code>ActionMap</code> (if it exists) as well as the
     * <code>InputMap</code>s.
     */
    public void unregisterKeyboardAction(KeyStroke aKeyStroke);

    /**
     * Resets the UI property to a value from the current look and feel.
     * <code>JComponent</code> subclasses must override this method like this:
     * 
     * <pre>
     *         public void updateUI() {
     *            setUI((SliderUI)UIManager.getUI(this);
     *         }
     * </pre>
     * 
     * @see UIManager#getLookAndFeel
     * @see UIManager#getUI
     */
    public void updateUI();

}
