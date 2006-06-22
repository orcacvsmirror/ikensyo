package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.im.InputSubset;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACTextArea;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRTableModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoExtraSpecialNoteDialog
    extends JDialog {
  private JPanel contentPane = new JPanel();
  private VRPanel client = new VRPanel();
  private VRPanel headerPnl = new VRPanel();
  private JLabel headerCaption = new JLabel();
  private ACButton edit = new ACButton();
  private ACTable table = new ACTable();
  private VRPanel insertPnl = new VRPanel();
  private ACButton insert = new ACButton();
//  private JScrollPane editAreaScr = new JScrollPane();
  private ACTextArea editArea = new ACTextArea();
  private VRPanel footerPnl = new VRPanel();
  private VRLabel footerCaption1 = new VRLabel();
  private VRLabel footerCaption2 = new VRLabel();
  private VRPanel footerBtnPnl = new VRPanel();
  private ACButton submit = new ACButton();
  private ACButton close = new ACButton();
  private VRPanel bottom = new VRPanel();

  private VRArrayList data;
  private int teikeiMaxLength;

  private String initText;
  protected int tkbKbn;

  /**
   * ���[�_�����[�h�ŕ\�����A���͂����e�L�X�g���e��Ԃ��܂��B
   * @param initText String
   * @return String
   */
  public String showModal(String initText) {
    this.initText = initText;

    editArea.setText(initText);
    setModal(true);
    setVisible(true);
//    show();

    return editArea.getText();
  }
  /**
   * ��^�敪��Ԃ��܂��B
   * @return ��^�敪
   */
  public int getKubun(){
    return tkbKbn;
  }
  /**
   * ��^�敪��ݒ肵�܂��B
   * @param kubun ��^�敪
   */
  protected void setKubun(int kubun){
    tkbKbn = kubun;
  }
  /**
   * �R���X�g���N�^�ł��B
   * @param title �^�C�g��
   * @param tkbKbn ��^�敪
   * @param maxLength �ő啶����
   * @param maxColumn �ő�s��
   * @param maxRow �ő��
   * @param teikeiMaxLength ��^���̍ő啶����
   */
  public IkenshoExtraSpecialNoteDialog(String title, int tkbKbn, int maxLength,
                                       int maxColumn, int maxRow,
                                       int teikeiMaxLength) {
    super(ACFrame.getInstance(), title, true);
    this.teikeiMaxLength = teikeiMaxLength;

    setKubun(tkbKbn);
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
      pack();
      initComponent();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
    table.setColumnModel(new VRTableColumnModel(
        new VRTableColumn[] {
        new VRTableColumn(0, 670, getTitle())
    }));


    if(maxRow>0){
      footerCaption2.setText("�i�ő�" + String.valueOf(maxLength) + "�����܂���" +
                             maxRow + "�s�ȓ��j");
//      editArea.setUseMaxRows(true);
      editArea.setMaxLength(maxLength);
      editArea.setRows(maxRow);
      editArea.setMaxRows(editArea.getRows());
      editArea.setColumns(maxColumn);
    }else{
      footerCaption2.setText("�i�ő�" + String.valueOf(maxLength) + "�����j");
      editArea.setRows(6);
      editArea.setColumns(80);
    }


    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if((!IkenshoCommon.isNullText(initText))&&(!editArea.getText().equals(initText))){
          //�ϓ�����
          if (ACMessageBox.show("�����́u" + getTitle() + "�v�̓��e��u�������܂����H",
                                     ACMessageBox.BUTTON_OKCANCEL,
                                     ACMessageBox.ICON_QUESTION,
                                     ACMessageBox.FOCUS_CANCEL) !=
              ACMessageBox.RESULT_OK) {
            return;
          }
        }
        initText = editArea.getText();
        closeWindow();
      }
    });
    close.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeWindow();
      }
    });

    insert.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        insertSelectText();
      }
    });

    table.addMouseListener(new MouseAdapter() {
//    table.getTable().addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          try {
            insertSelectText();
          }
          catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
          }
        }
      }
    });

    edit.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        if (new IkenshoTeikeibunEdit(getTitle(), IkenshoTeikeibunEdit.TEIKEIBUN,
                                    getKubun(), getTeikeiMaxLength()).showModal()) {
          try {
            doSelect();
          }
          catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
        }
      }
    });

    //���񏉊���
    try {
      doSelect();
    }
    catch (Exception ex) {
      IkenshoCommon.showExceptionMessage(ex);
    }
  }

  /**
   * �e�[�u���őI�𒆂̕������ǉ����܂��B
   */
  protected void insertSelectText() {
    if ( (table.getSelectedModelRow() < 0) ||
        (table.getSelectedModelRow() >= table.getRowCount())) {
      return;
    }
    String addText = String.valueOf( ( (VRMap) data.getData(table.
        getSelectedModelRow())).getData("TEIKEIBUN"));
    String nowText = editArea.getText();
//    int selS = editArea.getSelectionStart();
//    int selE = editArea.getSelectionEnd();
//    if((selS<0)||(selE>nowText.length())){
      editArea.setText(nowText + addText);
//    }else{
//      editArea.setText(nowText.substring(0, selS) + addText +
//                       nowText.substring(selE));
//    }

  }

  /**
   * ��^���̍ő啶���񒷂�Ԃ��܂��B
   * @return ��^���̍ő啶����
   */
  protected int getTeikeiMaxLength(){
    return teikeiMaxLength;
  }

  private void jbInit() throws Exception {
    contentPane = (JPanel)this.getContentPane();
    table.setColumnSort(false);
    editArea.setColumns(100);
    contentPane.add(client);

    bottom.setLayout(new VRLayout());
    client.setLayout(new VRLayout());
    client.add(headerPnl, VRLayout.NORTH);
    client.add(table, VRLayout.CLIENT);
    client.add(footerPnl, VRLayout.SOUTH);
    client.add(bottom, VRLayout.SOUTH);

    //header
    headerPnl.setLayout(new BorderLayout());
    headerPnl.add(headerCaption, BorderLayout.CENTER);
    headerPnl.add(edit, BorderLayout.EAST);
    headerCaption.setText("�u" + this.getTitle() + "�v�̕ҏW");
    edit.setText("�ҏW(E)");
    edit.setMnemonic('E');

    //�ҏW���}��
    insertPnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
    insertPnl.add(insert, null);
//    editAreaScr.getViewport().add(editArea);
    insert.setText("���I�����}��(I)");
    insert.setMnemonic('I');

    //�ҏW�̈�
    editArea.setRows(8);
    editArea.setLineWrap(true);
    editArea.setIMEMode(InputSubset.KANJI);

    bottom.add(editArea, VRLayout.LEFT);
    bottom.add(insertPnl, VRLayout.NORTH);

    //footer
    footerPnl.setLayout(new VRLayout());
    footerPnl.add(footerCaption1, VRLayout.FLOW_RETURN);
    footerPnl.add(footerCaption2, VRLayout.FLOW_RETURN);
    footerPnl.add(footerBtnPnl, VRLayout.EAST);
    footerCaption1.setText("���u" + this.getTitle() + "�v�ҏW�̈�i��ҏW�\�ł��j");
    footerCaption2.setForeground(IkenshoConstants.COLOR_BORDER_TEXT_FOREGROUND);
    footerBtnPnl.add(submit, VRLayout.FLOW);
    footerBtnPnl.add(close, VRLayout.FLOW);
    submit.setText("�m��(S)");
    submit.setMnemonic('S');
    close.setText("����(C)");
    close.setMnemonic('C');

  }

  /**
  * �ʒu�����������܂��B
  */
 private void initComponent() {
    //�E�B���h�E�̃T�C�Y
    setSize(new Dimension(700, 500));
    //�E�B���h�E�𒆉��ɔz�u
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    this.setLocation( (screenSize.width - frameSize.width) / 2,
                     (screenSize.height - frameSize.height) / 2);
  }

  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      closeWindow();
    }else{
      super.processWindowEvent(e);
    }
  }

  protected void closeWindow() {
    if(!editArea.getText().equals(initText)){
      //�ϓ�����
      switch (ACMessageBox.showYesNoCancel("�ύX����Ă��܂��B�m�肵�܂����H",
          "�m�肵�Ė߂�(S)", 'S', "�j�����Ė߂�(R)", 'R')) {
      case ACMessageBox.RESULT_YES:
        break;
      case ACMessageBox.RESULT_NO:
        editArea.setText(initText);
        break;
      default:
        return;
      }
    }

    //���g��j������
    this.dispose();
  }

  /**
   * �f�[�^��DB����擾���܂��B
   * @throws Exception
   */
  private void doSelect() throws Exception {
    //�f�[�^��DB����擾
    String sql = "SELECT TEIKEIBUN FROM TEIKEIBUN WHERE TKB_KBN = " +
        getKubun() +
        " ORDER BY TKB_CD";
    IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
    data = (VRArrayList) dbm.executeQuery(sql);

    //�e�[�u���̐ݒ�
    table.setModel(new VRTableModelAdapter(data, new String[] {
                                           "TEIKEIBUN"}));

    if (data.size() > 0) {
      table.setSelectedModelRow(0);
    }
  }
}
