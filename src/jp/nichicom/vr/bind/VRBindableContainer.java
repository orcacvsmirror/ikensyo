package jp.nichicom.vr.bind;

/**
 * 内包する項目に対するバインド処理を一括して集約・頒布するバインド機構インターフェースです。
 * <p>
 * バインド機構に対応したコントロールのうち、パネルなど他の項目を包含するコンテナが実装します。
 * </p>
 * <p>
 * バインド機構に対応したコントロールの<code>createSource</code>は、自分自身が管理する値1つを返します。これに対し<code>VRContainerBindable</code>を実装したクラスは、内包する項目の<code>createSource</code>を個々に呼び出し、<code>List</code>あるいは<code>Map</code>に集約して返します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see VRBindable
 * @see VRBindModelable
 */
public interface VRBindableContainer extends VRBindable, VRBindModelable {

}
