package jp.nichicom.vr.util;

import java.util.ArrayList;
import java.util.List;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;

/**
 * バインドソース機構を実装するListインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see List
 * @see VRBindSource
 * @see VRBindSourceEventListener
 */
public interface VRList extends List, VRBindSource, VRBindSourceEventListener {

}
