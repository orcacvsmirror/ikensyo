package jp.nichicom.vr.component;

import java.awt.Component;
import java.awt.Container;
import java.awt.ContainerOrderFocusTraversalPolicy;
import java.awt.FocusTraversalPolicy;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ContainerListener;

/**
 * コンテナインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Container
 * @see VRComponentar
 */
public interface VRContainar extends VRComponentar {

    /**
     * Appends the specified component to the end of this container. This is a
     * convenience method for #addImpl.
     * <p>
     * Note: If a component has been added to a container that has been
     * displayed, <code>validate</code> must be called on that container to
     * display the new component. If multiple components are being added, you
     * can improve efficiency by calling <code>validate</code> only once,
     * after all the components have been added.
     * 
     * @param comp the component to be added
     * @see #validate
     * @see javax.swing.JComponent#revalidate()
     * @return the component argument
     */
    public Component add(Component comp);

    /**
     * Adds the specified component to this container at the given position.
     * This is a convenience method for #addImpl.
     * <p>
     * Note: If a component has been added to a container that has been
     * displayed, <code>validate</code> must be called on that container to
     * display the new component. If multiple components are being added, you
     * can improve efficiency by calling <code>validate</code> only once,
     * after all the components have been added.
     * 
     * @param comp the component to be added
     * @param index the position at which to insert the component, or
     *            <code>-1</code> to append the component to the end
     * @return the component <code>comp</code>
     * @see #remove
     * @see #validate
     * @see javax.swing.JComponent#revalidate()
     */
    public Component add(Component comp, int index);

    /**
     * Adds the specified component to the end of this container. Also notifies
     * the layout manager to add the component to this container's layout using
     * the specified constraints object. This is a convenience method for
     * #addImpl.
     * <p>
     * Note: If a component has been added to a container that has been
     * displayed, <code>validate</code> must be called on that container to
     * display the new component. If multiple components are being added, you
     * can improve efficiency by calling <code>validate</code> only once,
     * after all the components have been added.
     * 
     * @param comp the component to be added
     * @param constraints an object expressing layout contraints for this
     *            component
     * @see #validate
     * @see javax.swing.JComponent#revalidate()
     * @see LayoutManager
     * @since JDK1.1
     */
    public void add(Component comp, Object constraints);

    /**
     * Adds the specified component to this container with the specified
     * constraints at the specified index. Also notifies the layout manager to
     * add the component to the this container's layout using the specified
     * constraints object. This is a convenience method for #addImpl.
     * <p>
     * Note: If a component has been added to a container that has been
     * displayed, <code>validate</code> must be called on that container to
     * display the new component. If multiple components are being added, you
     * can improve efficiency by calling <code>validate</code> only once,
     * after all the components have been added.
     * 
     * @param comp the component to be added
     * @param constraints an object expressing layout contraints for this
     * @param index the position in the container's list at which to insert the
     *            component; <code>-1</code> means insert at the end component
     * @see #validate
     * @see javax.swing.JComponent#revalidate()
     * @see #remove
     * @see LayoutManager
     */
    public void add(Component comp, Object constraints, int index);

    /**
     * Adds the specified component to this container. This is a convenience
     * method for #addImpl.
     * <p>
     * This method is obsolete as of 1.1. Please use the method
     * <code>add(Component, Object)</code> instead.
     * 
     * @see #add(Component, Object)
     */
    public Component add(String name, Component comp);

    /**
     * Adds the specified container listener to receive container events from
     * this container. If l is null, no exception is thrown and no action is
     * performed.
     * 
     * @param l the container listener
     * @see #removeContainerListener
     * @see #getContainerListeners
     */
    public void addContainerListener(ContainerListener l);

    /**
     * @deprecated As of JDK version 1.1, replaced by getComponentCount().
     */
    public int countComponents();

    /**
     * Locates the visible child component that contains the specified position.
     * The top-most child component is returned in the case where there is
     * overlap in the components. If the containing child component is a
     * Container, this method will continue searching for the deepest nested
     * child component. Components which are not visible are ignored during the
     * search.
     * <p>
     * The findComponentAt method is different from getComponentAt in that
     * getComponentAt only searches the Container's immediate children; if the
     * containing component is a Container, findComponentAt will search that
     * child to find a nested component.
     * 
     * @param x the <i>x</i> coordinate
     * @param y the <i>y</i> coordinate
     * @return null if the component does not contain the position. If there is
     *         no child component at the requested point and the point is within
     *         the bounds of the container the container itself is returned.
     * @see Component#contains
     * @see #getComponentAt
     * @since 1.2
     */
    public Component findComponentAt(int x, int y);

    /**
     * Locates the visible child component that contains the specified point.
     * The top-most child component is returned in the case where there is
     * overlap in the components. If the containing child component is a
     * Container, this method will continue searching for the deepest nested
     * child component. Components which are not visible are ignored during the
     * search.
     * <p>
     * The findComponentAt method is different from getComponentAt in that
     * getComponentAt only searches the Container's immediate children; if the
     * containing component is a Container, findComponentAt will search that
     * child to find a nested component.
     * 
     * @param p the point.
     * @return null if the component does not contain the position. If there is
     *         no child component at the requested point and the point is within
     *         the bounds of the container the container itself is returned.
     * @see Component#contains
     * @see #getComponentAt
     * @since 1.2
     */
    public Component findComponentAt(Point p);

    /**
     * Gets the nth component in this container.
     * 
     * @param n the index of the component to get.
     * @return the n<sup>th</sup> component in this container.
     * @exception ArrayIndexOutOfBoundsException if the n<sup>th</sup> value
     *                does not exist.
     */
    public Component getComponent(int n);

    /**
     * Gets the number of components in this panel.
     * 
     * @return the number of components in this panel.
     * @see #getComponent
     * @since JDK1.1
     */
    public int getComponentCount();

    /**
     * Gets all the components in this container.
     * 
     * @return an array of all the components in this container.
     */
    public Component[] getComponents();

    /**
     * Returns an array of all the container listeners registered on this
     * container.
     * 
     * @return all of this container's <code>ContainerListener</code>s or an
     *         empty array if no container listeners are currently registered
     * @see #addContainerListener
     * @see #removeContainerListener
     * @since 1.4
     */
    public ContainerListener[] getContainerListeners();

    /**
     * Returns the focus traversal policy that will manage keyboard traversal of
     * this Container's children, or null if this Container is not a focus cycle
     * root. If no traversal policy has been explicitly set for this Container,
     * then this Container's focus-cycle-root ancestor's policy is returned.
     * 
     * @return this Container's focus traversal policy, or null if this
     *         Container is not a focus cycle root.
     * @see #setFocusTraversalPolicy
     * @see #setFocusCycleRoot
     * @see #isFocusCycleRoot
     * @since 1.4
     */
    public FocusTraversalPolicy getFocusTraversalPolicy();

    /**
     * Determines the insets of this container, which indicate the size of the
     * container's border.
     * <p>
     * A <code>Frame</code> object, for example, has a top inset that
     * corresponds to the height of the frame's title bar.
     * 
     * @return the insets of this container.
     * @see Insets
     * @see LayoutManager
     * @since JDK1.1
     */
    public Insets getInsets();

    /**
     * Gets the layout manager for this container.
     * 
     * @see #doLayout
     * @see #setLayout
     */
    public LayoutManager getLayout();

    /**
     * @deprecated As of JDK version 1.1, replaced by <code>getInsets()</code>.
     */
    public Insets insets();

    /**
     * Checks if the component is contained in the component hierarchy of this
     * container.
     * 
     * @param c the component
     * @return <code>true</code> if it is an ancestor; <code>false</code>
     *         otherwise.
     * @since JDK1.1
     */
    public boolean isAncestorOf(Component c);

    /**
     * Returns whether this Container is the root of a focus traversal cycle.
     * Once focus enters a traversal cycle, typically it cannot leave it via
     * focus traversal unless one of the up- or down-cycle keys is pressed.
     * Normal traversal is limited to this Container, and all of this
     * Container's descendants that are not descendants of inferior focus cycle
     * roots. Note that a FocusTraversalPolicy may bend these restrictions,
     * however. For example, ContainerOrderFocusTraversalPolicy supports
     * implicit down-cycle traversal.
     * 
     * @return whether this Container is the root of a focus traversal cycle
     * @see #setFocusCycleRoot
     * @see #setFocusTraversalPolicy
     * @see #getFocusTraversalPolicy
     * @see ContainerOrderFocusTraversalPolicy
     * @since 1.4
     */
    public boolean isFocusCycleRoot();

    /**
     * Returns whether the focus traversal policy has been explicitly set for
     * this Container. If this method returns <code>false</code>, this
     * Container will inherit its focus traversal policy from an ancestor.
     * 
     * @return <code>true</code> if the focus traversal policy has been
     *         explicitly set for this Container; <code>false</code>
     *         otherwise.
     * @since 1.4
     */
    public boolean isFocusTraversalPolicySet();

    /**
     * Paints each of the components in this container.
     * 
     * @param g the graphics context.
     * @see Component#paint
     * @see Component#paintAll
     */
    public void paintComponents(Graphics g);

    /**
     * Prints each of the components in this container.
     * 
     * @param g the graphics context.
     * @see Component#print
     * @see Component#printAll
     */
    public void printComponents(Graphics g);

    /**
     * Removes the specified component from this container.
     * 
     * @param comp the component to be removed
     * @see #add
     */
    public void remove(Component comp);

    /**
     * Removes the component, specified by <code>index</code>, from this
     * container.
     * 
     * @param index the index of the component to be removed.
     * @see #add
     * @since JDK1.1
     */
    public void remove(int index);

    /**
     * Removes all the components from this container.
     * 
     * @see #add
     * @see #remove
     */
    public void removeAll();

    /**
     * Removes the specified container listener so it no longer receives
     * container events from this container. If l is null, no exception is
     * thrown and no action is performed.
     * 
     * @param l the container listener
     * @see #addContainerListener
     * @see #getContainerListeners
     */
    public void removeContainerListener(ContainerListener l);

    /**
     * Sets whether this Container is the root of a focus traversal cycle. Once
     * focus enters a traversal cycle, typically it cannot leave it via focus
     * traversal unless one of the up- or down-cycle keys is pressed. Normal
     * traversal is limited to this Container, and all of this Container's
     * descendants that are not descendants of inferior focus cycle roots. Note
     * that a FocusTraversalPolicy may bend these restrictions, however. For
     * example, ContainerOrderFocusTraversalPolicy supports implicit down-cycle
     * traversal.
     * 
     * @param focusCycleRoot indicates whether this Container is the root of a
     *            focus traversal cycle
     * @see #isFocusCycleRoot()
     * @see #setFocusTraversalPolicy
     * @see #getFocusTraversalPolicy
     * @see ContainerOrderFocusTraversalPolicy
     * @since 1.4
     */
    public void setFocusCycleRoot(boolean focusCycleRoot);

    /**
     * Sets the focus traversal policy that will manage keyboard traversal of
     * this Container's children, if this Container is a focus cycle root. If
     * the argument is null, this Container inherits its policy from its focus-
     * cycle-root ancestor. If the argument is non-null, this policy will be
     * inherited by all focus-cycle-root children that have no keyboard-
     * traversal policy of their own (as will, recursively, their focus-cycle-
     * root children).
     * <p>
     * If this Container is not a focus cycle root, the policy will be
     * remembered, but will not be used or inherited by this or any other
     * Containers until this Container is made a focus cycle root.
     * 
     * @param policy the new focus traversal policy for this Container
     * @see #getFocusTraversalPolicy
     * @see #setFocusCycleRoot
     * @see #isFocusCycleRoot
     * @since 1.4
     */
    public void setFocusTraversalPolicy(FocusTraversalPolicy policy);

    /**
     * Sets the layout manager for this container.
     * 
     * @param mgr the specified layout manager
     * @see #doLayout
     * @see #getLayout
     */
    public void setLayout(LayoutManager mgr);

    /**
     * Transfers the focus down one focus traversal cycle. If this Container is
     * a focus cycle root, then the focus owner is set to this Container's
     * default Component to focus, and the current focus cycle root is set to
     * this Container. If this Container is not a focus cycle root, then no
     * focus traversal operation occurs.
     * 
     * @see Component#requestFocus()
     * @see #isFocusCycleRoot
     * @see #setFocusCycleRoot
     * @since 1.4
     */
    public void transferFocusDownCycle();

}
