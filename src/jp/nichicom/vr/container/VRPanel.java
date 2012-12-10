/** TODO <HEAD> */
package jp.nichicom.vr.container;

import java.awt.LayoutManager;

import jp.nichicom.vr.bind.VRBindModelable;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.bind.event.VRBindModelEventListener;

/**
 * バインド機構を実装したパネルです。
 * <p>
 * モデルバインド機構も実装しています。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractVRPanel
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see VRBindModelable
 * @see VRBindModelEventListener
 */
public class VRPanel extends AbstractVRPanel {

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public VRPanel() {
        super();
    }

    /**
     * Creates a new <code>JPanel</code> with <code>FlowLayout</code>
     * and the specified buffering strategy.
     * If <code>isDoubleBuffered</code> is true, the <code>JPanel</code>
     * will use a double buffer.
     *
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free 
     *        updates
     */
    public VRPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    /**
     * Create a new buffered JPanel with the specified layout manager
     *
     * @param layout  the LayoutManager to use
     */
    public VRPanel(LayoutManager layout) {
        super(layout);
    }

    /**
     * Creates a new JPanel with the specified layout manager and buffering
     * strategy.
     *
     * @param layout  the LayoutManager to use
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free 
     *        updates
     */
    public VRPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

}