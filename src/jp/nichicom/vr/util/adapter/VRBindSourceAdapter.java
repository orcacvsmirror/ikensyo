package jp.nichicom.vr.util.adapter;

import jp.nichicom.vr.bind.VRBindSource;

/**
 * バインドソースをラップする機構を実装したアダプタインターフェースです。
 * <p>
 * <code>VRBindSource</code> 形式のオブジェクトをラップして透過的に扱います。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see VRBindSource
 */
public interface VRBindSourceAdapter {
    /**
     * アダプティーとなるバインドソースを返します。
     * 
     * @return アダプティー
     */
    public VRBindSource getAdaptee();

}
