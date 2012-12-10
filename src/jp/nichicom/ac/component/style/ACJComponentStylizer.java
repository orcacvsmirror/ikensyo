package jp.nichicom.ac.component.style;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.MenuComponent;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerListener;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
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
import java.beans.VetoableChangeListener;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.EventListener;
import java.util.Locale;
import java.util.Set;

import javax.accessibility.AccessibleContext;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.event.AncestorListener;

import jp.nichicom.vr.component.VRJComponentar;

/**
 * JComponentに対応したスタイライザです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see AbstractACComponentStylizer
 * @see VRJComponentar
 */
public class ACJComponentStylizer extends AbstractACComponentStylizer implements
        VRJComponentar {
    private boolean backgroundSet = false;
    private boolean borderSet = false;
    private boolean cursorSet = false;
    private boolean enabledSet = false;
    private boolean focusableSet = false;
    private boolean fontSet = false;
    private boolean foregroundSet = false;
    private JComponent innerComponent = new JLabel();
    private boolean opaqueSet = false;
    private boolean toolTipTextSet = false;
    private boolean visibleSet = false;

    /**
     * コンストラクタです。
     */
    public ACJComponentStylizer() {
        super();
    }

    public boolean action(Event evt, Object what) {

        return getComponent().action(evt, what);
    }

    public Component add(Component comp) {

        return getComponent().add(comp);
    }

    public Component add(Component comp, int index) {

        return getComponent().add(comp, index);
    }

    public void add(Component comp, Object constraints) {
        getComponent().add(comp, constraints);
    }

    public void add(Component comp, Object constraints, int index) {
        getComponent().add(comp, constraints, index);
    }

    public void add(PopupMenu popup) {
        getComponent().add(popup);
    }

    public Component add(String name, Component comp) {

        return getComponent().add(name, comp);
    }

    public void addAncestorListener(AncestorListener listener) {
        getComponent().addAncestorListener(listener);
    }

    public void addComponentListener(ComponentListener l) {
        getComponent().addComponentListener(l);
    }

    public void addContainerListener(ContainerListener l) {
        getComponent().addContainerListener(l);
    }

    public void addFocusListener(FocusListener l) {
        getComponent().addFocusListener(l);
    }

    public void addHierarchyBoundsListener(HierarchyBoundsListener l) {
        getComponent().addHierarchyBoundsListener(l);
    }

    public void addHierarchyListener(HierarchyListener l) {
        getComponent().addHierarchyListener(l);
    }

    public void addInputMethodListener(InputMethodListener l) {
        getComponent().addInputMethodListener(l);
    }

    public void addKeyListener(KeyListener l) {
        getComponent().addKeyListener(l);
    }

    public void addMouseListener(MouseListener l) {
        getComponent().addMouseListener(l);
    }

    public void addMouseMotionListener(MouseMotionListener l) {
        getComponent().addMouseMotionListener(l);
    }

    public void addMouseWheelListener(MouseWheelListener l) {
        getComponent().addMouseWheelListener(l);
    }

    public void addNotify() {
        getComponent().addNotify();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getComponent().addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        getComponent().addPropertyChangeListener(propertyName, listener);
    }

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        getComponent().addVetoableChangeListener(listener);
    }

    public void applyComponentOrientation(ComponentOrientation orientation) {
        getComponent().applyComponentOrientation(orientation);
    }

    public boolean areFocusTraversalKeysSet(int id) {

        return getComponent().areFocusTraversalKeysSet(id);
    }

    public Rectangle bounds() {

        return getComponent().bounds();
    }

    public int checkImage(Image image, ImageObserver observer) {

        return getComponent().checkImage(image, observer);
    }

    public int checkImage(Image image, int width, int height,
            ImageObserver observer) {

        return getComponent().checkImage(image, width, height, observer);
    }

    public void computeVisibleRect(Rectangle visibleRect) {
        getComponent().computeVisibleRect(visibleRect);
    }

    public boolean contains(int x, int y) {

        return getComponent().contains(x, y);
    }

    public boolean contains(Point p) {

        return getComponent().contains(p);
    }

    public int countComponents() {

        return getComponent().countComponents();
    }

    public Image createImage(ImageProducer producer) {

        return getComponent().createImage(producer);
    }

    public Image createImage(int width, int height) {

        return getComponent().createImage(width, height);
    }

    public JToolTip createToolTip() {
        return getComponent().createToolTip();
    }

    public VolatileImage createVolatileImage(int width, int height) {

        return getComponent().createVolatileImage(width, height);
    }

    public VolatileImage createVolatileImage(int width, int height,
            ImageCapabilities caps) throws AWTException {

        return getComponent().createVolatileImage(width, height, caps);
    }

    public void deliverEvent(Event e) {
        getComponent().deliverEvent(e);
    }

    public void disable() {
        getComponent().disable();
    }

    public void dispatchEvent(AWTEvent e) {
        getComponent().dispatchEvent(e);
    }

    public void doLayout() {
        getComponent().doLayout();
    }

    public void enable() {
        getComponent().enable();
    }

    public void enable(boolean b) {
        getComponent().enable(b);
    }

    public void enableInputMethods(boolean enable) {
        getComponent().enableInputMethods(enable);
    }

    public Component findComponentAt(int x, int y) {

        return getComponent().findComponentAt(x, y);
    }

    public Component findComponentAt(Point p) {

        return getComponent().findComponentAt(p);
    }

    public void firePropertyChange(String propertyName, byte oldValue,
            byte newValue) {
        getComponent().firePropertyChange(propertyName, oldValue, newValue);
    }

    public void firePropertyChange(String propertyName, char oldValue,
            char newValue) {
        getComponent().firePropertyChange(propertyName, oldValue, newValue);
    }

    public void firePropertyChange(String propertyName, double oldValue,
            double newValue) {
        getComponent().firePropertyChange(propertyName, oldValue, newValue);
    }

    public void firePropertyChange(String propertyName, float oldValue,
            float newValue) {
        getComponent().firePropertyChange(propertyName, oldValue, newValue);
    }

    public void firePropertyChange(String propertyName, long oldValue,
            long newValue) {
        getComponent().firePropertyChange(propertyName, oldValue, newValue);
    }

    public void firePropertyChange(String propertyName, short oldValue,
            short newValue) {
        getComponent().firePropertyChange(propertyName, oldValue, newValue);
    }

    public AccessibleContext getAccessibleContext() {

        return getComponent().getAccessibleContext();
    }

    public ActionListener getActionForKeyStroke(KeyStroke aKeyStroke) {
        return getComponent().getActionForKeyStroke(aKeyStroke);
    }

    public ActionMap getActionMap() {
        return getComponent().getActionMap();
    }

    public float getAlignmentX() {

        return getComponent().getAlignmentX();
    }

    public float getAlignmentY() {

        return getComponent().getAlignmentY();
    }

    public AncestorListener[] getAncestorListeners() {
        return getComponent().getAncestorListeners();
    }

    public boolean getAutoscrolls() {
        return getComponent().getAutoscrolls();
    }

    public Color getBackground() {

        return getComponent().getBackground();
    }

    public Border getBorder() {
        return getComponent().getBorder();
    }

    public Rectangle getBounds() {

        return getComponent().getBounds();
    }

    public Rectangle getBounds(Rectangle rv) {

        return getComponent().getBounds(rv);
    }

    public Object getClientProperty(Object key) {
        return getComponent().getClientProperty(key);
    }

    public ColorModel getColorModel() {

        return getComponent().getColorModel();
    }

    public Component getComponent(int n) {

        return getComponent().getComponent(n);
    }

    public Component getComponentAt(int x, int y) {

        return getComponent().getComponentAt(x, y);
    }

    public Component getComponentAt(Point p) {

        return getComponent().getComponentAt(p);
    }

    public int getComponentCount() {

        return getComponent().getComponentCount();
    }

    public ComponentListener[] getComponentListeners() {

        return getComponent().getComponentListeners();
    }

    public ComponentOrientation getComponentOrientation() {

        return getComponent().getComponentOrientation();
    }

    public Component[] getComponents() {

        return getComponent().getComponents();
    }

    public int getConditionForKeyStroke(KeyStroke aKeyStroke) {
        return getComponent().getConditionForKeyStroke(aKeyStroke);
    }

    public ContainerListener[] getContainerListeners() {

        return getComponent().getContainerListeners();
    }

    public Cursor getCursor() {

        return getComponent().getCursor();
    }

    public int getDebugGraphicsOptions() {
        return getComponent().getDebugGraphicsOptions();
    }

    public DropTarget getDropTarget() {

        return getComponent().getDropTarget();
    }

    public Container getFocusCycleRootAncestor() {

        return getComponent().getFocusCycleRootAncestor();
    }

    public FocusListener[] getFocusListeners() {

        return getComponent().getFocusListeners();
    }

    public Set getFocusTraversalKeys(int id) {

        return getComponent().getFocusTraversalKeys(id);
    }

    public boolean getFocusTraversalKeysEnabled() {

        return getComponent().getFocusTraversalKeysEnabled();
    }

    public FocusTraversalPolicy getFocusTraversalPolicy() {

        return getComponent().getFocusTraversalPolicy();
    }

    public Font getFont() {

        return getComponent().getFont();
    }

    public FontMetrics getFontMetrics(Font font) {

        return getComponent().getFontMetrics(font);
    }

    public Color getForeground() {

        return getComponent().getForeground();
    }

    public Graphics getGraphics() {

        return getComponent().getGraphics();
    }

    public GraphicsConfiguration getGraphicsConfiguration() {

        return getComponent().getGraphicsConfiguration();
    }

    public int getHeight() {

        return getComponent().getHeight();
    }

    public HierarchyBoundsListener[] getHierarchyBoundsListeners() {

        return getComponent().getHierarchyBoundsListeners();
    }

    public HierarchyListener[] getHierarchyListeners() {

        return getComponent().getHierarchyListeners();
    }

    public boolean getIgnoreRepaint() {

        return getComponent().getIgnoreRepaint();
    }

    public InputContext getInputContext() {

        return getComponent().getInputContext();
    }

    public InputMap getInputMap() {
        return getComponent().getInputMap();
    }

    public InputMap getInputMap(int condition) {
        return getComponent().getInputMap(condition);
    }

    public InputMethodListener[] getInputMethodListeners() {

        return getComponent().getInputMethodListeners();
    }

    public InputMethodRequests getInputMethodRequests() {

        return getComponent().getInputMethodRequests();
    }

    public InputVerifier getInputVerifier() {
        return getComponent().getInputVerifier();
    }

    public Insets getInsets() {

        return getComponent().getInsets();
    }

    public Insets getInsets(Insets insets) {
        return getComponent().getInsets(insets);
    }

    public KeyListener[] getKeyListeners() {

        return getComponent().getKeyListeners();
    }

    public LayoutManager getLayout() {

        return getComponent().getLayout();
    }

    public EventListener[] getListeners(Class getListeners) {

        return getComponent().getListeners(getListeners);
    }

    public Locale getLocale() {

        return getComponent().getLocale();
    }

    public Point getLocation() {

        return getComponent().getLocation();
    }

    public Point getLocation(Point rv) {

        return getComponent().getLocation(rv);
    }

    public Point getLocationOnScreen() {

        return getComponent().getLocationOnScreen();
    }

    public Dimension getMaximumSize() {

        return getComponent().getMaximumSize();
    }

    public Dimension getMinimumSize() {

        return getComponent().getMinimumSize();
    }

    public MouseListener[] getMouseListeners() {

        return getComponent().getMouseListeners();
    }

    public MouseMotionListener[] getMouseMotionListeners() {

        return getComponent().getMouseMotionListeners();
    }

    public MouseWheelListener[] getMouseWheelListeners() {

        return getComponent().getMouseWheelListeners();
    }

    public String getName() {

        return getComponent().getName();
    }

    public Component getNextFocusableComponent() {
        return getComponent().getNextFocusableComponent();
    }

    public Container getParent() {

        return getComponent().getParent();
    }

    public ComponentPeer getPeer() {

        return getComponent().getPeer();
    }

    public Dimension getPreferredSize() {

        return getComponent().getPreferredSize();
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {

        return getComponent().getPropertyChangeListeners();
    }

    public PropertyChangeListener[] getPropertyChangeListeners(
            String propertyName) {

        return getComponent().getPropertyChangeListeners(propertyName);
    }

    public KeyStroke[] getRegisteredKeyStrokes() {
        return getComponent().getRegisteredKeyStrokes();
    }

    public JRootPane getRootPane() {
        return getComponent().getRootPane();
    }

    public Dimension getSize() {

        return getComponent().getSize();
    }

    public Dimension getSize(Dimension rv) {

        return getComponent().getSize(rv);
    }

    public Toolkit getToolkit() {

        return getComponent().getToolkit();
    }

    public Point getToolTipLocation(MouseEvent event) {
        return getComponent().getToolTipLocation(event);
    }

    public String getToolTipText() {
        return getComponent().getToolTipText();
    }

    public String getToolTipText(MouseEvent event) {
        return getComponent().getToolTipText(event);
    }

    public Container getTopLevelAncestor() {
        return getComponent().getTopLevelAncestor();
    }

    public TransferHandler getTransferHandler() {
        return getComponent().getTransferHandler();
    }

    public Object getTreeLock() {

        return getComponent().getTreeLock();
    }

    public String getUIClassID() {
        return getComponent().getUIClassID();
    }

    public boolean getVerifyInputWhenFocusTarget() {
        return getComponent().getVerifyInputWhenFocusTarget();
    }

    public VetoableChangeListener[] getVetoableChangeListeners() {
        return getComponent().getVetoableChangeListeners();
    }

    public Rectangle getVisibleRect() {
        return getComponent().getVisibleRect();
    }

    public int getWidth() {

        return getComponent().getWidth();
    }

    public int getX() {

        return getComponent().getX();
    }

    public int getY() {

        return getComponent().getY();
    }

    public boolean gotFocus(Event evt, Object what) {

        return getComponent().gotFocus(evt, what);
    }

    public void grabFocus() {
        getComponent().grabFocus();
    }

    public boolean handleEvent(Event evt) {

        return getComponent().handleEvent(evt);
    }

    public boolean hasFocus() {

        return getComponent().hasFocus();
    }

    public void hide() {
        getComponent().hide();
    }

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int w,
            int h) {

        return getComponent().imageUpdate(img, infoflags, x, y, w, h);
    }

    public Insets insets() {

        return getComponent().insets();
    }

    public boolean inside(int x, int y) {

        return getComponent().inside(x, y);
    }

    public void invalidate() {
        getComponent().invalidate();
    }

    public boolean isAncestorOf(Component c) {

        return getComponent().isAncestorOf(c);
    }

    public boolean isBackgroundSet() {

        return getComponent().isBackgroundSet();
    }

    public boolean isCursorSet() {

        return getComponent().isCursorSet();
    }

    public boolean isDisplayable() {

        return getComponent().isDisplayable();
    }

    public boolean isDoubleBuffered() {

        return getComponent().isDoubleBuffered();
    }

    public boolean isEnabled() {

        return getComponent().isEnabled();
    }

    public boolean isFocusable() {

        return getComponent().isFocusable();
    }

    public boolean isFocusCycleRoot() {

        return getComponent().isFocusCycleRoot();
    }

    public boolean isFocusCycleRoot(Container container) {

        return getComponent().isFocusCycleRoot(container);
    }

    public boolean isFocusOwner() {

        return getComponent().isFocusOwner();
    }

    public boolean isFocusTraversable() {

        return getComponent().isFocusTraversable();
    }

    public boolean isFocusTraversalPolicySet() {

        return getComponent().isFocusTraversalPolicySet();
    }

    public boolean isFontSet() {

        return getComponent().isFontSet();
    }

    public boolean isForegroundSet() {

        return getComponent().isForegroundSet();
    }

    public boolean isLightweight() {

        return getComponent().isLightweight();
    }

    public boolean isManagingFocus() {

        return getComponent().isManagingFocus();

    }

    public boolean isMaximumSizeSet() {
        return getComponent().isMaximumSizeSet();
    }

    public boolean isMinimumSizeSet() {
        return getComponent().isMinimumSizeSet();
    }

    public boolean isOpaque() {

        return getComponent().isOpaque();
    }

    public boolean isOptimizedDrawingEnabled() {
        return getComponent().isOptimizedDrawingEnabled();
    }

    public boolean isPaintingTile() {
        return getComponent().isPaintingTile();
    }

    public boolean isPreferredSizeSet() {
        return getComponent().isPreferredSizeSet();
    }

    public boolean isRequestFocusEnabled() {
        return getComponent().isRequestFocusEnabled();
    }

    public boolean isShowing() {

        return getComponent().isShowing();
    }

    public boolean isValid() {

        return getComponent().isValid();
    }

    public boolean isValidateRoot() {
        return getComponent().isValidateRoot();
    }

    public boolean isVisible() {

        return getComponent().isVisible();
    }

    public boolean keyDown(Event evt, int key) {

        return getComponent().keyDown(evt, key);
    }

    public boolean keyUp(Event evt, int key) {

        return getComponent().keyUp(evt, key);
    }

    public void layout() {
        getComponent().layout();
    }

    public void list() {
        getComponent().list();
    }

    public void list(PrintStream out) {
        getComponent().list(out);
    }

    public void list(PrintStream out, int indent) {
        getComponent().list(out, indent);
    }

    public void list(PrintWriter out) {
        getComponent().list(out);
    }

    public void list(PrintWriter out, int indent) {
        getComponent().list(out, indent);
    }

    public Component locate(int x, int y) {

        return getComponent().locate(x, y);
    }

    public Point location() {

        return getComponent().location();
    }

    public boolean lostFocus(Event evt, Object what) {

        return getComponent().lostFocus(evt, what);
    }

    public Dimension minimumSize() {

        return getComponent().minimumSize();
    }

    public boolean mouseDown(Event evt, int x, int y) {

        return getComponent().mouseDown(evt, x, y);
    }

    public boolean mouseDrag(Event evt, int x, int y) {

        return getComponent().mouseDrag(evt, x, y);
    }

    public boolean mouseEnter(Event evt, int x, int y) {

        return getComponent().mouseEnter(evt, x, y);
    }

    public boolean mouseExit(Event evt, int x, int y) {

        return getComponent().mouseExit(evt, x, y);
    }

    public boolean mouseMove(Event evt, int x, int y) {

        return getComponent().mouseMove(evt, x, y);
    }

    public boolean mouseUp(Event evt, int x, int y) {

        return getComponent().mouseUp(evt, x, y);
    }

    public void move(int x, int y) {
        getComponent().move(x, y);
    }

    public void nextFocus() {
        getComponent().nextFocus();
    }

    public void paint(Graphics g) {
        getComponent().paint(g);
    }

    public void paintAll(Graphics g) {
        getComponent().paintAll(g);
    }

    public void paintComponents(Graphics g) {
        getComponent().paintComponents(g);
    }

    public void paintImmediately(int x, int y, int w, int h) {
        getComponent().paintImmediately(x, y, w, h);
    }

    public void paintImmediately(Rectangle r) {
        getComponent().paintImmediately(r);
    }

    public boolean postEvent(Event e) {

        return getComponent().postEvent(e);
    }

    public Dimension preferredSize() {

        return getComponent().preferredSize();
    }

    public boolean prepareImage(Image image, ImageObserver observer) {

        return getComponent().prepareImage(image, observer);
    }

    public boolean prepareImage(Image image, int width, int height,
            ImageObserver observer) {

        return getComponent().prepareImage(image, width, height, observer);
    }

    public void print(Graphics g) {
        getComponent().print(g);
    }

    public void printAll(Graphics g) {
        getComponent().printAll(g);
    }

    public void printComponents(Graphics g) {
        getComponent().printComponents(g);
    }

    public void putClientProperty(Object key, Object value) {

        getComponent().putClientProperty(key, value);

    }

    public void registerKeyboardAction(ActionListener anAction,
            KeyStroke aKeyStroke, int aCondition) {
        getComponent().registerKeyboardAction(anAction, aKeyStroke, aCondition);
    }

    public void registerKeyboardAction(ActionListener anAction,
            String aCommand, KeyStroke aKeyStroke, int aCondition) {
        getComponent().registerKeyboardAction(anAction, aCommand, aKeyStroke,
                aCondition);
    }

    public void remove(Component comp) {
        getComponent().remove(comp);
    }

    public void remove(int index) {
        getComponent().remove(index);
    }

    public void remove(MenuComponent popup) {
        getComponent().remove(popup);
    }

    public void removeAll() {
        getComponent().removeAll();
    }

    public void removeAncestorListener(AncestorListener listener) {
        getComponent().removeAncestorListener(listener);
    }

    public void removeComponentListener(ComponentListener l) {
        getComponent().removeComponentListener(l);
    }

    public void removeContainerListener(ContainerListener l) {
        getComponent().removeContainerListener(l);
    }

    public void removeFocusListener(FocusListener l) {
        getComponent().removeFocusListener(l);
    }

    public void removeHierarchyBoundsListener(HierarchyBoundsListener l) {
        getComponent().removeHierarchyBoundsListener(l);
    }

    public void removeHierarchyListener(HierarchyListener l) {
        getComponent().removeHierarchyListener(l);
    }

    public void removeInputMethodListener(InputMethodListener l) {
        getComponent().removeInputMethodListener(l);
    }

    public void removeKeyListener(KeyListener l) {
        getComponent().removeKeyListener(l);
    }

    public void removeMouseListener(MouseListener l) {
        getComponent().removeMouseListener(l);
    }

    public void removeMouseMotionListener(MouseMotionListener l) {
        getComponent().removeMouseMotionListener(l);
    }

    public void removeMouseWheelListener(MouseWheelListener l) {
        getComponent().removeMouseWheelListener(l);
    }

    public void removeNotify() {
        getComponent().removeNotify();
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getComponent().removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        getComponent().removePropertyChangeListener(propertyName, listener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        getComponent().removeVetoableChangeListener(listener);
    }

    public void repaint() {
        getComponent().repaint();
    }

    public void repaint(int x, int y, int width, int height) {
        getComponent().repaint(x, y, width, height);
    }

    public void repaint(long tm) {
        getComponent().repaint(tm);
    }

    public void repaint(long tm, int x, int y, int width, int height) {
        getComponent().repaint(tm, x, y, width, height);
    }

    public void repaint(Rectangle r) {
        getComponent().repaint(r);
    }

    public boolean requestDefaultFocus() {

        return getComponent().requestDefaultFocus();
    }

    public void requestFocus() {
        getComponent().requestFocus();
    }

    public boolean requestFocusInWindow() {

        return getComponent().requestFocusInWindow();
    }

    public void resetKeyboardActions() {
        getComponent().resetKeyboardActions();
    }

    public void reshape(int x, int y, int width, int height) {
        getComponent().reshape(x, y, width, height);
    }

    public void resize(Dimension d) {
        getComponent().resize(d);
    }

    public void resize(int width, int height) {
        getComponent().resize(width, height);
    }

    public void revalidate() {
        getComponent().revalidate();
    }

    public void scrollRectToVisible(Rectangle aRect) {
        getComponent().scrollRectToVisible(aRect);
    }

    public void setActionMap(ActionMap am) {
        getComponent().setActionMap(am);
    }

    public void setAlignmentX(float alignmentX) {

        getComponent().setAlignmentX(alignmentX);
    }

    public void setAlignmentY(float alignmentY) {
        getComponent().setAlignmentY(alignmentY);
    }

    public void setAutoscrolls(boolean autoscrolls) {
        getComponent().setAutoscrolls(autoscrolls);
    }

    public void setBackground(Color c) {
        getComponent().setBackground(c);
        backgroundSet = true;
    }

    public void setBorder(Border border) {
        getComponent().setBorder(border);
        borderSet = true;
    }

    public void setBounds(int x, int y, int width, int height) {
        getComponent().setBounds(x, y, width, height);
    }

    public void setBounds(Rectangle r) {
        getComponent().setBounds(r);
    }

    public void setComponentOrientation(ComponentOrientation o) {
        getComponent().setComponentOrientation(o);
    }

    public void setCursor(Cursor cursor) {
        getComponent().setCursor(cursor);
        cursorSet = true;
    }

    public void setDebugGraphicsOptions(int debugOptions) {
        getComponent().setDebugGraphicsOptions(debugOptions);
    }

    public void setDoubleBuffered(boolean aFlag) {
        getComponent().setDoubleBuffered(aFlag);
    }

    public void setDropTarget(DropTarget dt) {
        getComponent().setDropTarget(dt);
    }

    public void setEnabled(boolean b) {
        getComponent().setEnabled(b);
        enabledSet = true;
    }

    public void setFocusable(boolean focusable) {
        getComponent().setFocusable(focusable);
        focusableSet = true;
    }

    public void setFocusCycleRoot(boolean focusCycleRoot) {
        getComponent().setFocusCycleRoot(focusCycleRoot);
    }

    public void setFocusTraversalKeys(int id, Set keystrokes) {
        getComponent().setFocusTraversalKeys(id, keystrokes);
    }

    public void setFocusTraversalKeysEnabled(boolean focusTraversalKeysEnabled) {
        getComponent().setFocusTraversalKeysEnabled(focusTraversalKeysEnabled);
    }

    public void setFocusTraversalPolicy(FocusTraversalPolicy policy) {
        getComponent().setFocusTraversalPolicy(policy);
    }

    public void setFont(Font f) {
        getComponent().setFont(f);
        fontSet = true;
    }

    public void setForeground(Color c) {
        getComponent().setForeground(c);
        foregroundSet = true;
    }

    public void setIgnoreRepaint(boolean ignoreRepaint) {
        getComponent().setIgnoreRepaint(ignoreRepaint);
    }

    public void setInputMap(int condition, InputMap map) {
        getComponent().setInputMap(condition, map);
    }

    public void setInputVerifier(InputVerifier inputVerifier) {
        getComponent().setInputVerifier(inputVerifier);
    }

    public void setLayout(LayoutManager mgr) {
        getComponent().setLayout(mgr);
    }

    public void setLocale(Locale l) {
        getComponent().setLocale(l);
    }

    public void setLocation(int x, int y) {
        getComponent().setLocation(x, y);
    }

    public void setLocation(Point p) {
        getComponent().setLocation(p);
    }

    public void setMaximumSize(Dimension maximumSize) {
        getComponent().setMaximumSize(maximumSize);
    }

    public void setMinimumSize(Dimension minimumSize) {
        getComponent().setMinimumSize(minimumSize);
    }

    public void setName(String name) {
        getComponent().setName(name);
    }

    public void setNextFocusableComponent(Component aComponent) {
        getComponent().setNextFocusableComponent(aComponent);
    }

    public void setOpaque(boolean isOpaque) {
        getComponent().setOpaque(isOpaque);
        opaqueSet = true;
    }

    public void setPreferredSize(Dimension preferredSize) {
        getComponent().setPreferredSize(preferredSize);
    }

    public void setRequestFocusEnabled(boolean requestFocusEnabled) {
        getComponent().setRequestFocusEnabled(requestFocusEnabled);
    }

    public void setSize(Dimension d) {
        getComponent().setSize(d);
    }

    public void setSize(int width, int height) {
        getComponent().setSize(width, width);
    }

    public void setToolTipText(String text) {
        getComponent().setToolTipText(text);
        toolTipTextSet = true;
    }

    public void setTransferHandler(TransferHandler newHandler) {
        getComponent().setTransferHandler(newHandler);
    }

    public void setVerifyInputWhenFocusTarget(boolean verifyInputWhenFocusTarget) {
        getComponent()
                .setVerifyInputWhenFocusTarget(verifyInputWhenFocusTarget);
    }

    public void setVisible(boolean b) {
        getComponent().setVisible(b);
        visibleSet = true;
    }

    public void show() {
        getComponent().show();
    }

    public void show(boolean b) {
        getComponent().show(b);
    }

    public Dimension size() {

        return getComponent().size();
    }

    public void transferFocus() {
        getComponent().transferFocus();
    }

    public void transferFocusBackward() {
        getComponent().transferFocusBackward();
    }

    public void transferFocusDownCycle() {
        getComponent().transferFocusDownCycle();
    }

    public void transferFocusUpCycle() {
        getComponent().transferFocusUpCycle();
    }

    public void unregisterKeyboardAction(KeyStroke aKeyStroke) {
        getComponent().unregisterKeyboardAction(aKeyStroke);
    }

    public void update(Graphics g) {
        getComponent().update(g);
    }

    public void updateUI() {
        getComponent().updateUI();
    }

    public void validate() {
        getComponent().validate();
    }

    /**
     * 設定値格納用のコンポーネントを生成して返します。
     * 
     * @return 設定値格納用のコンポーネント
     */
    protected JComponent createComponent() {
        return new JLabel();
    }

    /**
     * 設定値格納用のコンポーネントを返します。
     * 
     * @return 設定値格納用のコンポーネント
     */
    protected JComponent getComponent() {
        if (innerComponent == null) {
            innerComponent = createComponent();
        }
        return innerComponent;
    }

    protected Component stylizeImpl(Component comp) {
        if (backgroundSet) {
            comp.setBackground(getBackground());
        }
        if (foregroundSet) {
            comp.setForeground(getForeground());
        }
        if (fontSet) {
            comp.setFont(getFont());
        }
        if (borderSet) {
            ((JComponent) comp).setBorder(getBorder());
        }
        if (cursorSet) {
            comp.setCursor(getCursor());
        }
        if (toolTipTextSet) {
            ((JComponent) comp).setToolTipText(getToolTipText());
        }
        if (enabledSet) {
            comp.setEnabled(isEnabled());
        }
        if (focusableSet) {
            comp.setFocusable(isFocusable());
        }
        if (opaqueSet) {
            ((JComponent) comp).setOpaque(isOpaque());
        }
        if (visibleSet) {
            comp.setVisible(isVisible());
        }

        return comp;
    }

}
