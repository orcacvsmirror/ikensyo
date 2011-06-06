package jp.or.med.orca.ikensho.component;

import java.awt.Color;
import java.awt.Component;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParseException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;

import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.text.AbstractVRTextDocument;


public class IkenshoTextFieldDocumentLimit extends IkenshoACTextArea implements Document  {
	
	private AbstractVRTextDocument model = null;
	private boolean absoluteEditable = false;
	
	//�x����\�����Ȃ��s���̏��
	private int pageBreakLimitRows = Integer.MAX_VALUE;
	//�x����\�����Ȃ��������̏��
	//��2�o�C�g�����ŃJ�E���g�B
	private int pageBreakLimitLength = Integer.MAX_VALUE;
	
	//���͂���Ă��镶������\������e�L�X�g�t�B�[���h
	private JLabel alertLabel = null;
	private String alertLabelFormat = null;
	private JCheckBox alertCheck = null;
	private String alertCheckFormat = null;
	
	public IkenshoTextFieldDocumentLimit() {
        super();
		model = (AbstractVRTextDocument)getMainContent().getDocument();
        
		getMainContent().setDocument(this);
	}
	
	public void setPageBreakLimitRow(int limitRows) {
		pageBreakLimitRows = limitRows;
	}
	
	public void setPageBreakLimitLength(int limitLength) {
		pageBreakLimitLength = limitLength;
	}
	
	/*
	 * setAlertLabel/setAlertCheck
	 * 
	 * �w�肳�ꂽ���x����`�F�b�N�{�b�N�X�̃e�L�X�g�ɁA���݃e�L�X�g�G���A��  
	 * ���͂���Ă��镶�����̃X�e�[�^�X��\������B
	 * 
	 * ���x����`�F�b�N�{�b�N�X��ݒ肷��O�ɁA���Ƀe�L�X�g���ݒ肳��Ă���K�v�A���B
	 * 
	 * ���҂��Ă���e�L�X�g
	 * �u"�����̗̈��{0}�����ȏ�^{1}�s�ȏ�̓��͂ł́A���[��2���ň������܂��B(����{2}����{3}�s��)"�v
	 * 
	 * {0}��{1}�̉ӏ��𐔒l�Œu�����ĕ\������B
	 * �܂��A���y�[�W����������f�[�^�ƂȂ����ꍇ�́A�t�H���g�J���[��Color.RED�ɕύX����B
	 */
	public void setAlertLabel(JLabel label) {
		alertLabel = label;
		alertLabelFormat = label.getText();
		
		setFitState();
	}
	
	public void setAlertCheck(JCheckBox check) {
		alertCheck = check;
		alertCheckFormat = check.getText();
		
		setFitState();
	}
	
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		
        //�Ƃ肠���������
        //�ő啶�����ƍő�s���̃`�F�b�N�͌���model������Ă����B
        model.insertString(offset, str, attr);
        
        //���ꂽ��̏�Ԃ𔻒�
        setFitState();
        
	}
    
    public String getText() {
        String txt;
        try {
            txt = getText(0, getLength());
        } catch (BadLocationException e) {
            txt = null;
        }
        return txt;
    }
    
	public void remove(int offs, int len) throws BadLocationException {
		model.remove(offs, len);
		if(!absoluteEditable) {
			setFitState();
		}
	}
    
    private void setFitState(){
        String text = getText();
    	
    	String[] lines = ACTextUtilities.separateLineWrapOnByte(text, getColumns());
        int totalLineCount = lines.length;
        int totalWordCount = text.replaceAll("[\r\n]", "").length();
        
        writeMessage(isPageBreak(totalLineCount, totalWordCount), totalWordCount, totalLineCount);
    }
    
    public boolean isPageBreak() {
        String text = getText();
    	
    	String[] lines = ACTextUtilities.separateLineWrapOnByte(text, getColumns());
        int totalLineCount = lines.length;
        int totalWordCount = text.replaceAll("[\r\n]", "").length();
        
    	return isPageBreak(totalLineCount, totalWordCount);
    }
    
    private boolean isPageBreak(int totalLineCount, int totalWordCount) {
        if ((pageBreakLimitRows <= totalLineCount) || (pageBreakLimitLength <= totalWordCount)){
        	return true;
        } else {
        	return false;
        }
    }
    
    private void writeMessage(boolean isPageBreak, int wordCount, int lineCount) {
		Object[] param = new Object[]{
				Integer.toString(pageBreakLimitLength),
				Integer.toString(pageBreakLimitRows),
				Integer.toString(wordCount),
				Integer.toString(lineCount)};
		
		if (alertLabel != null) {
			alertLabel.setText(MessageFormat.format(alertLabelFormat, param));
			changeForeground(alertLabel, isPageBreak);
		}
		
		if (alertCheck != null) {
			alertCheck.setText(MessageFormat.format(alertCheckFormat, param));
			changeForeground(alertCheck, isPageBreak);
		}
    }
    
    private void changeForeground(Component c, boolean isPageBreak) {
    	Color color = null;
    	if (isPageBreak) {
    		color = Color.RED;
    	} else {
    		color = Color.BLACK;
    	}
    	
		if (!c.getForeground().equals(color)) {
			c.setForeground(color);
		}
    }
	

    
    
	public int getLength() {
		return model.getLength();
	}

	public void addDocumentListener(DocumentListener listener) {
		model.addDocumentListener(listener);
	}

	public void removeDocumentListener(DocumentListener listener) {
		model.removeDocumentListener(listener);
	}

	public void addUndoableEditListener(UndoableEditListener listener) {
		model.addUndoableEditListener(listener);
	}

	public void removeUndoableEditListener(UndoableEditListener listener) {
		model.removeUndoableEditListener(listener);
	}

	public Object getProperty(Object key) {
		return model.getProperty(key);
	}

	public void putProperty(Object key, Object value) {
		model.putProperty(key, value);
	}

	public void getText(int offset, int length, Segment txt) throws BadLocationException {
		model.getText(offset, length, txt);
	}
	
	public String getText(int offset, int length) throws BadLocationException {
		return model.getText(offset, length);
	}

	public Position getStartPosition() {
		return model.getStartPosition();
	}

	public Position getEndPosition() {
		return model.getEndPosition();
	}

	public Position createPosition(int offs) throws BadLocationException {
		return model.createPosition(offs);
	}

	public Element[] getRootElements() {
		return model.getRootElements();
	}

	public Element getDefaultRootElement() {
		return model.getDefaultRootElement();
	}

	public void render(Runnable r) {
		model.render(r);
	}
	
    public void setAbsoluteEditable(boolean a ) {
        model.setAbsoluteEditable(a);
    }
    
    public void bindSource() throws ParseException {
        if (!VRBindPathParser.has(getBindPath(), getSource())) {
            return;
        }

        Object obj = VRBindPathParser.get(getBindPath(), getSource());

        // ���͐�������
        setAbsoluteEditable(true);

        if (obj == null) {
            super.setText("");
        } else {
            Format fmt = getFormat();
            if (fmt != null) {
                super.setText(fmt.format(obj));
            } else {
                String text = String.valueOf(obj);
                super.setText(text);
            }

        }
        // ���͐������A
        setAbsoluteEditable(false);

        fireBindSource(new VRBindEvent(this));
    }
    
    /**
     * ���͒l��ݒ肵�܂��B
     * 
     * @param text ���͒l
     */
    public void setText(String text) {
        setAbsoluteEditable(true);
        super.setText(text);
        // ���͐������A
        setAbsoluteEditable(false);
    }
    
}
