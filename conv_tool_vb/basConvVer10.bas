Attribute VB_Name = "basConvVer10"
Option Explicit

Public Sub ConvIKENSYO10()
'============================================================================================
'�ӌ������ڍs
'============================================================================================
Dim recPAIENT As Object
Dim recPAIENT_MAX As Object
Dim recIKENSYO As Object
Dim recIKENSYO_KAIGO As Object
Dim recIKENSYO_OTHER As Object
Dim recIKENSYO_GRAPHIC As Object
Dim recSIS As Object
Dim recSIS_OTHER As Object
Dim strSQL As String
Dim intEda As Long

    '�ŐV��{���e�[�u���̎擾
    Set recPAIENT = MDBExecuteRecordSet("SELECT * FROM �ŐV��{��� ORDER BY ���Ҕԍ�", adOpenForwardOnly, adLockReadOnly)
    Set recPAIENT_MAX = MDBExecuteRecordSet("SELECT MAX(���Ҕԍ�) AS ���Ҕԍ�_�ő�l FROM �ŐV��{���", adOpenForwardOnly, adLockReadOnly)
    
    '�ŐV��{���e�[�u���̃g���K�[����U��~
    Call FDBExecuteSQL("ALTER TRIGGER SET_PATIENT_ID INACTIVE")
    
    '�ŐV��{��񐔕���������
    Do Until recPAIENT.EOF
    
        '============================================================================================
        '�ӌ���
        '============================================================================================
        '�ӌ�����{���e�[�u���̎擾
        Set recIKENSYO = MDBExecuteRecordSet("SELECT * FROM �ӌ�����{��� WHERE (���Ҕԍ� = " & recPAIENT("���Ҕԍ�") & ") ORDER BY ���Ҕԍ�,�쐬NO DESC", adOpenForwardOnly, adLockReadOnly)
    
        If recIKENSYO.EOF = False Then
            '�}�ԏ�����
            intEda = 1
            '�ӌ����S�g�Ɖ��̏��e�[�u���̎擾
            Set recIKENSYO_KAIGO = MDBExecuteRecordSet("SELECT * FROM �ӌ����S�g�Ɖ��̏�� WHERE (�쐬NO = " & recIKENSYO("�쐬NO") & ")", adOpenForwardOnly, adLockReadOnly)
            '�ӌ������̑��e�[�u���̎擾
            Set recIKENSYO_OTHER = MDBExecuteRecordSet("SELECT * FROM �ӌ������̑� WHERE (���Ҕԍ� = " & recIKENSYO("���Ҕԍ�") & ") AND (�쐬NO = " & recIKENSYO("�쐬NO") & ")", adOpenForwardOnly, adLockReadOnly)
            '�S�g�O���t�B�b�N�R�}���h�e�[�u���̎擾
            Set recIKENSYO_GRAPHIC = MDBExecuteRecordSet("SELECT * FROM �S�g�}�O���t�B�b�N�R�}���h WHERE (���Ҕԍ� = " & recIKENSYO("���Ҕԍ�") & ") AND (�쐬NO = " & recIKENSYO("�쐬NO") & ")", adOpenForwardOnly, adLockReadOnly)
            'COMMON_IKN_SIS�ڍs����
            Call ConvCOMMON_IKN_SIS(intEda, recIKENSYO, recIKENSYO_OTHER, IKEN)
            'IKN_ORIGIN�ڍs����
            Call ConvIKN_ORIGIN(intEda, recIKENSYO, recIKENSYO_KAIGO, recIKENSYO_OTHER, VER10)
            'GRAPHICS_COMMAND�ڍs����
            Call ConvGRAPHICS_COMMAND(intEda, recIKENSYO, recIKENSYO_GRAPHIC)
        End If
        
        '============================================================================================
        '�w����
        '============================================================================================
        '�w������{���e�[�u���̎擾
        Set recSIS = MDBExecuteRecordSet("SELECT * FROM �w������{��� WHERE (���Ҕԍ� = " & recPAIENT("���Ҕԍ�") & ") ORDER BY ���Ҕԍ�,�쐬NO DESC", adOpenForwardOnly, adLockReadOnly)
        
        If recSIS.EOF = False Then
            '�}�ԏ�����
            intEda = 1
            '�w�������̑��e�[�u���̎擾
            Set recSIS_OTHER = MDBExecuteRecordSet("SELECT * FROM �w�������̑� WHERE (���Ҕԍ� = " & recSIS("���Ҕԍ�") & ") AND (�쐬NO = " & recSIS("�쐬NO") & ")", adOpenForwardOnly, adLockReadOnly)
            'COMMON_IKN_SIS�ڍs����
            Call ConvCOMMON_IKN_SIS(intEda, recSIS, recSIS_OTHER, SIS)
            'SIS_ORIGIN�ڍs����
            Call ConvSIS_ORIGIN10(intEda, recSIS_OTHER)
        End If
        
        '�ŐV��{���e�[�u�����ڍs
        strSQL = ""
        strSQL = strSQL & "INSERT INTO PATIENT ("
        strSQL = strSQL & "PATIENT_NO,"
        strSQL = strSQL & "CHART_NO,"
        strSQL = strSQL & "PATIENT_NM,"
        strSQL = strSQL & "PATIENT_KN,"
        strSQL = strSQL & "SEX,"
        strSQL = strSQL & "BIRTHDAY,"
        strSQL = strSQL & "AGE,"
        strSQL = strSQL & "POST_CD,"
        strSQL = strSQL & "ADDRESS,"
        strSQL = strSQL & "TEL1,"
        strSQL = strSQL & "TEL2,"
        strSQL = strSQL & "LAST_TIME"
        strSQL = strSQL & ")"
        
        strSQL = strSQL & " VALUES ("
        strSQL = strSQL & recPAIENT("���Ҕԍ�") & ","
        strSQL = strSQL & "'" & recPAIENT("�J���e�ԍ�") & "',"
        strSQL = strSQL & "'" & recPAIENT("���Җ�") & "',"
        strSQL = strSQL & "'" & recPAIENT("���Җ�����") & "',"
        strSQL = strSQL & recPAIENT("����") & ","
        strSQL = strSQL & "'" & Format(recPAIENT("���N����"), "yyyy/mm/dd") & "',"
        strSQL = strSQL & "'" & recPAIENT("�N��") & "',"
        strSQL = strSQL & "'" & AddHyphen(recPAIENT("�X�֔ԍ�")) & "',"
        strSQL = strSQL & "'" & recPAIENT("�Z��") & "',"
        strSQL = strSQL & "'" & recPAIENT("�d�b�ǔ�") & "',"
        strSQL = strSQL & "'" & AddHyphen(recPAIENT("�d�b")) & "',"
        strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
        strSQL = strSQL & ")"
        
        Call FDBExecuteSQL(strSQL)
    
        recPAIENT.MoveNext
    
    Loop
    
    '�ŐV��{���e�[�u���̎����̔ԏ����l��ݒ�
    If Not IsNull(recPAIENT_MAX("���Ҕԍ�_�ő�l")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_PATIENT to " & recPAIENT_MAX("���Ҕԍ�_�ő�l"))
    End If
    '�ŐV��{���e�[�u���̃g���K�[���J�n
    Call FDBExecuteSQL("ALTER TRIGGER SET_PATIENT_ID ACTIVE")

End Sub

Public Sub ConvDOCTOR10()
'============================================================================================
'DOCTOR�ڍs�iVer1.0��p�j
'============================================================================================
Dim recDOCTOR As Object
Dim recDOCTOR_MAX As Object
Dim strSQL As String
    
    '�ڍs���f�[�^���擾
    Set recDOCTOR = MDBExecuteRecordSet("SELECT * FROM ��t�}�X�^ ORDER BY ��t�R�[�h", adOpenForwardOnly, adLockReadOnly)
    Set recDOCTOR_MAX = MDBExecuteRecordSet("SELECT MAX(��t�R�[�h) AS ��t�R�[�h_�ő�l FROM ��t�}�X�^", adOpenForwardOnly, adLockReadOnly)
    '�ڍs��f�[�^�̃g���K�[����U��~
    Call FDBExecuteSQL("ALTER TRIGGER SET_DOCTOR_ID INACTIVE")
    
    '�ڍs��Ƀf�[�^��}��
    Do Until recDOCTOR.EOF
                    
        strSQL = ""
        strSQL = strSQL & "INSERT INTO DOCTOR ("
        strSQL = strSQL & "DR_CD,"
        strSQL = strSQL & "DR_NM,"
        strSQL = strSQL & "MI_NM,"
        strSQL = strSQL & "MI_POST_CD,"
        strSQL = strSQL & "MI_ADDRESS,"
        strSQL = strSQL & "MI_TEL1,"
        strSQL = strSQL & "MI_TEL2,"
        strSQL = strSQL & "MI_FAX1,"
        strSQL = strSQL & "MI_FAX2,"
        strSQL = strSQL & "MI_CEL_TEL1,"
        strSQL = strSQL & "MI_CEL_TEL2,"
        strSQL = strSQL & "KINKYU_RENRAKU,"
        strSQL = strSQL & "FUZAIJI_TAIOU,"
        strSQL = strSQL & "BIKOU,"
        strSQL = strSQL & "JIGYOUSHA_NO,"
        strSQL = strSQL & "MI_DEFAULT,"
        strSQL = strSQL & "LAST_TIME"
        strSQL = strSQL & ")"
        
        strSQL = strSQL & " VALUES ("
        strSQL = strSQL & recDOCTOR("��t�R�[�h") & ","
        strSQL = strSQL & "'" & recDOCTOR("��t��") & "',"
        strSQL = strSQL & "'" & recDOCTOR("��Ë@�֖�") & "',"
        strSQL = strSQL & "'" & AddHyphen(recDOCTOR("��Ë@�֗X�֔ԍ�")) & "',"
        strSQL = strSQL & "'" & recDOCTOR("��Ë@�֏Z��") & "',"
        strSQL = strSQL & "'" & recDOCTOR("��Ë@�֓d�b�ǔ�") & "',"
        strSQL = strSQL & "'" & AddHyphen(recDOCTOR("��Ë@�֓d�b")) & "',"
        strSQL = strSQL & "'" & recDOCTOR("��Ë@��FAX�ǔ�") & "',"
        strSQL = strSQL & "'" & AddHyphen(recDOCTOR("��Ë@��FAX")) & "',"
        strSQL = strSQL & "'" & recDOCTOR("�g�єԍ���") & "',"
        strSQL = strSQL & "'" & AddHyphen(recDOCTOR("�g�єԍ�")) & "',"
        strSQL = strSQL & "'" & recDOCTOR("�ً}���A����") & "',"
        strSQL = strSQL & "'" & recDOCTOR("�s�ݎ��Ή��@") & "',"
        strSQL = strSQL & "'" & recDOCTOR("���l") & "',"
        strSQL = strSQL & "'" & recDOCTOR("���ƎҔԍ�") & "',"
        strSQL = strSQL & Abs(CInt(recDOCTOR("��Ë@�փf�t�H���g"))) & ","
        strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
        strSQL = strSQL & ")"
        
        Call FDBExecuteSQL(strSQL)
        
        recDOCTOR.MoveNext
        
    Loop
                                                          
    '�ڍs��̎����̔ԏ����l��ݒ�
    If Not IsNull(recDOCTOR_MAX("��t�R�[�h_�ő�l")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_DOCTOR to " & recDOCTOR_MAX("��t�R�[�h_�ő�l"))
    End If
    '�ڍs��f�[�^�̃g���K�[���J�n
    Call FDBExecuteSQL("ALTER TRIGGER SET_DOCTOR_ID ACTIVE")
                                                            
End Sub

Sub ConvSIS_ORIGIN10(pintEda As Long, precSIS_OTHER As Object)
'============================================================================================
'SIS_ORIGIN�ڍs�iVer1.0��p�j
'============================================================================================
Dim strSQL As String

    strSQL = ""
    strSQL = strSQL & "INSERT INTO SIS_ORIGIN ("
    strSQL = strSQL & "PATIENT_NO,"
    strSQL = strSQL & "EDA_NO,"
    If Not Trim$(precSIS_OTHER("�L����")) = "" Then
        strSQL = strSQL & "KINYU_DT,"
    End If
    strSQL = strSQL & "SIJI_CREATE_CNT,"
    strSQL = strSQL & "SIJI_YUKOU_KIKAN,"
    strSQL = strSQL & "SIJI_KANGO_KBN,"
    strSQL = strSQL & "STATION_NM,"
    strSQL = strSQL & "KINKYU_RENRAKU,"
    strSQL = strSQL & "FUZAIJI_TAIOU,"
    strSQL = strSQL & "RSS_RYUIJIKOU,"
    strSQL = strSQL & "REHA_SIJI_UMU,"
    strSQL = strSQL & "REHA_SIJI,"
    strSQL = strSQL & "JOKUSOU_SIJI_UMU,"
    strSQL = strSQL & "JOKUSOU_SIJI,"
    strSQL = strSQL & "SOUCHAKU_SIJI_UMU,"
    strSQL = strSQL & "SOUCHAKU_SIJI,"
    strSQL = strSQL & "RYUI_SIJI_UMU,"
    strSQL = strSQL & "RYUI_SIJI,"
    strSQL = strSQL & "SIJI_TOKKI,"
    strSQL = strSQL & "CREATE_DT,"
    strSQL = strSQL & "KOUSIN_DT,"
    strSQL = strSQL & "LAST_TIME"
    strSQL = strSQL & ")"

    strSQL = strSQL & " VALUES ("
    strSQL = strSQL & precSIS_OTHER("���Ҕԍ�") & ","
    strSQL = strSQL & pintEda & ","
    If Not Trim$(precSIS_OTHER("�L����")) = "" Then
        strSQL = strSQL & "'" & Format(precSIS_OTHER("�L����"), "yyyy/mm/dd") & "',"
    End If
    strSQL = strSQL & precSIS_OTHER("�w�����쐬��") & ","
    strSQL = strSQL & precSIS_OTHER("�w�����L������") & ","
    strSQL = strSQL & precSIS_OTHER("�w�����Ō�敪") & ","
    strSQL = strSQL & "'" & precSIS_OTHER("�Ō�X�e�[�V������") & "',"
    strSQL = strSQL & "'" & precSIS_OTHER("�ً}���A����") & "',"
    strSQL = strSQL & "'" & precSIS_OTHER("�s�ݎ��Ή��@") & "',"
    strSQL = strSQL & "'" & precSIS_OTHER("�×{�����w���㗯�ӎ���") & "',"
    strSQL = strSQL & Abs(CInt(precSIS_OTHER("���n�r���e�[�V�����w���L��"))) & ","
    strSQL = strSQL & "'" & precSIS_OTHER("���n�r���e�[�V�����w��") & "',"
    strSQL = strSQL & Abs(CInt(precSIS_OTHER("��ጏ��u�w���L��"))) & ","
    strSQL = strSQL & "'" & precSIS_OTHER("��ጏ��u�w��") & "',"
    strSQL = strSQL & Abs(CInt(precSIS_OTHER("������Ë@��w���L��"))) & ","
    strSQL = strSQL & "'" & precSIS_OTHER("������Ë@��w��") & "',"
    strSQL = strSQL & Abs(CInt(precSIS_OTHER("���ӎw���������L��"))) & ","
    strSQL = strSQL & "'" & precSIS_OTHER("���ӎw��������") & "',"
    strSQL = strSQL & "'" & precSIS_OTHER("�w�������L����") & "',"
    strSQL = strSQL & "'" & Format(precSIS_OTHER("�V�K�쐬��"), "yyyy/mm/dd hh:nn:ss") & "',"
    strSQL = strSQL & "'" & Format(precSIS_OTHER("�X�V��"), "yyyy/mm/dd hh:nn:ss") & "',"
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"

    '�ڍs�������s
    Call FDBExecuteSQL(strSQL)

End Sub

