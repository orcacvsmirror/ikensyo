package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.im.InputSubset;
import java.text.ParseException;

import javax.swing.SwingConstants;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACOneDecimalDoubleTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/** TODO <HEAD_IKENSYO> */
public class IkenshoBloodChemistry extends IkenshoDialog {
    private VRPanel contents = new VRPanel();
    private VRPanel buttons = new VRPanel();
    private ACGroupBox pointGroup = new ACGroupBox();
    private ACLabelContainer points = new ACLabelContainer();
    private ACOneDecimalDoubleTextField point = new ACOneDecimalDoubleTextField();
    private VRLabel pointUnit = new VRLabel();
    private ACButton close = new ACButton();
    private ACButton apply = new ACButton();

    protected boolean changed = false;
    protected VRMap source;

    /**
     * モーダルモードで表示し、変更があったかを返します。
     * 
     * @return 変更があったか
     */
    public boolean showModal() {
        point.setSource(source);
        try {
            point.bindSource();
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }

        setVisible(true);
        // show();
        return changed;
    }

    /**
     * 位置を初期化します。
     */
    private void init() {
        // ウィンドウのサイズ
//        setSize(new Dimension(200, 130));
        try {
			//IkenshoLayoutAdjustment.AllAdjustment(this);
        	pack();
		} catch (Exception e) {

		}
        // ウィンドウを中央に配置
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    /**
     * コンストラクタです。
     * 
     * @param source データソース
     * @throws HeadlessException 処理例外
     */
    public IkenshoBloodChemistry(VRMap source) throws HeadlessException {
        super(ACFrame.getInstance(), "血液化学検査の設定", true);
        this.source = source;

        try {
            jbInit();
            pack();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        apply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ACCommon.getInstance().isNullText(point.getText())) {
                        ACMessageBox.show("点数を入力してください。");
                        return;
                    }

                    point.applySource();

                    changed = true;
                    dispose();
                } catch (ParseException ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

    }

    private void jbInit() throws Exception {
        pointGroup.setText("血液化学検査");
        point.setColumns(6);
        point.setHorizontalAlignment(SwingConstants.RIGHT);
        point.setBindPath("EXP_KKK_KKK");
        point.setMaxLength(6);
        point.setCharType(IkenshoConstants.CHAR_TYPE_DOUBLE1);
        point.setFormat(IkenshoConstants.FORMAT_DOUBLE1);
        pointUnit.setText("点");
        this.setTitle("血液化学検査の設定");
        close.setToolTipText("元の画面に戻ります。");
        close.setMnemonic('C');
        close.setText("閉じる(C)");
        apply.setToolTipText("現在入力されている値を保存します。");
        apply.setMnemonic('S');
        apply.setText("設定(S)");
        contents.setLayout(new BorderLayout());
        this.getContentPane().add(contents, BorderLayout.CENTER);
        contents.add(buttons, BorderLayout.SOUTH);
        buttons.add(apply, null);
        buttons.add(close, null);
        contents.add(pointGroup, BorderLayout.CENTER);
        pointGroup.add(points, null);
        points.add(point, null);
        points.add(pointUnit, null);
        point.setIMEMode(InputSubset.LATIN_DIGITS);
    }

}
