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
     * ���[�_�����[�h�ŕ\�����A�ύX������������Ԃ��܂��B
     * 
     * @return �ύX����������
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
     * �ʒu�����������܂��B
     */
    private void init() {
        // �E�B���h�E�̃T�C�Y
//        setSize(new Dimension(200, 130));
        try {
			//IkenshoLayoutAdjustment.AllAdjustment(this);
        	pack();
		} catch (Exception e) {

		}
        // �E�B���h�E�𒆉��ɔz�u
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
     * �R���X�g���N�^�ł��B
     * 
     * @param source �f�[�^�\�[�X
     * @throws HeadlessException ������O
     */
    public IkenshoBloodChemistry(VRMap source) throws HeadlessException {
        super(ACFrame.getInstance(), "���t���w�����̐ݒ�", true);
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
                        ACMessageBox.show("�_������͂��Ă��������B");
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
        pointGroup.setText("���t���w����");
        point.setColumns(6);
        point.setHorizontalAlignment(SwingConstants.RIGHT);
        point.setBindPath("EXP_KKK_KKK");
        point.setMaxLength(6);
        point.setCharType(IkenshoConstants.CHAR_TYPE_DOUBLE1);
        point.setFormat(IkenshoConstants.FORMAT_DOUBLE1);
        pointUnit.setText("�_");
        this.setTitle("���t���w�����̐ݒ�");
        close.setToolTipText("���̉�ʂɖ߂�܂��B");
        close.setMnemonic('C');
        close.setText("����(C)");
        apply.setToolTipText("���ݓ��͂���Ă���l��ۑ����܂��B");
        apply.setMnemonic('S');
        apply.setText("�ݒ�(S)");
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
