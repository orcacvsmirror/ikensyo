Attribute VB_Name = "basConvVer153"
Option Explicit

Public Sub ConvIKENSYO153()
'============================================================================================
'�ӌ������ڍs
'============================================================================================
Dim recPAIENT As Object
Dim recPAIENT_MAX As Object
Dim recIKENSYO As Object
Dim recIKENSYO_KAIGO As Object
Dim recIKENSYO_SEIKYU As Object
Dim recIKENSYO_OTHER As Object
Dim recIKENSYO_GRAPHIC As Object
Dim recSIS As Object
Dim recSIS_OTHER As Object
Dim recSIS_OTHER2 As Object
Dim strSQL As String
Dim intEda As Long

    '�ŐV��{���e�[�u���̎擾
    Set recPAIENT = MDBExecuteRecordSet("SELECT * FROM �ŐV��{��� ORDER BY ���Ҕԍ�", adOpenForwardOnly, adLockReadOnly)
    Set recPAIENT_MAX = MDBExecuteRecordSet("SELECT MAX(���Ҕԍ�) AS ���Ҕԍ�_�ő�l FROM �ŐV��{���", adOpenForwardOnly, adLockReadOnly)
    
    '�ŐV��{���e�[�u���̃g���K�[����U��~
    Call FDBExecuteSQL("ALTER TRIGGER SET_PATIENT_ID INACTIVE")
    
    '�ŐV��{��񐔕���������
    Do Until recPAIENT.EOF
    
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrSec
            Call FDBTran(BeginTrans)
            '2006/02/11[Shin Fujihara] : add end
    
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
                '�ӌ����������e�[�u���̎擾
    '            Set recIKENSYO_SEIKYU = MDBExecuteRecordSet("SELECT * FROM �ӌ��������� WHERE (���Ҕԍ� = " & recIKENSYO("���Ҕԍ�") & ") AND (�쐬NO = " & recIKENSYO("�쐬NO") & ")", adOpenForwardOnly, adLockReadOnly)
                'COMMON_IKN_SIS�ڍs����
                Call ConvCOMMON_IKN_SIS(intEda, recIKENSYO, recIKENSYO_OTHER, IKEN)
                'IKN_ORIGIN�ڍs����
                Call ConvIKN_ORIGIN(intEda, recIKENSYO, recIKENSYO_KAIGO, recIKENSYO_OTHER, ver15)
                'IKN_BILL�ڍs����
                Call ConvIKN_BILL(intEda, recIKENSYO)
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
                '�w�������̑�02�e�[�u���̎擾
                Set recSIS_OTHER2 = MDBExecuteRecordSet("SELECT * FROM �w�������̑�02 WHERE (���Ҕԍ� = " & recSIS("���Ҕԍ�") & ") AND (�쐬NO = " & recSIS("�쐬NO") & ")", adOpenForwardOnly, adLockReadOnly)
                'COMMON_IKN_SIS�ڍs����
                Call ConvCOMMON_IKN_SIS(intEda, recSIS, recSIS_OTHER, SIS)
                'SIS_ORIGIN�ڍs����
                Call ConvSIS_ORIGIN153(intEda, recSIS_OTHER, recSIS_OTHER2)
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
    
        '2006/02/11[Shin Fujihara] : add begin
            Call FDBTran(CommitTrans)
        On Error GoTo 0
ErrSec:
        If Err.Number <> 0 Then
            Call FDBTran(RollbackTrans)
        End If
        '2006/02/11[Shin Fujihara] : add end
    
        recPAIENT.MoveNext
    
    Loop
    
    '�ŐV��{���e�[�u���̎����̔ԏ����l��ݒ�
    If Not IsNull(recPAIENT_MAX("���Ҕԍ�_�ő�l")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_PATIENT to " & recPAIENT_MAX("���Ҕԍ�_�ő�l"))
    End If
    '�ŐV��{���e�[�u���̃g���K�[���J�n
    Call FDBExecuteSQL("ALTER TRIGGER SET_PATIENT_ID ACTIVE")

End Sub

Sub ConvSIS_ORIGIN153(pintEda As Long, precSIS_OTHER As Object, precSIS_OTHER2 As Object)
'============================================================================================
'SIS_ORIGIN�ڍs�iVer1.5.3��p�j
'============================================================================================
Dim strSQL As String
Dim blnFlg As Boolean
Dim blnOtherFlg As Boolean
    
    blnFlg = False
    On Error GoTo Error_Section
        precSIS_OTHER ("�K��Ō�w����")
        blnFlg = True
    On Error GoTo 0
    
Error_Section:

    blnOtherFlg = precSIS_OTHER2.EOF

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
    strSQL = strSQL & "SIJI_KIKAN_FROM,"
    strSQL = strSQL & "SIJI_KIKAN_TO,"
    strSQL = strSQL & "YOUKAIGO_JOUKYOU,"
    If blnFlg Then
        strSQL = strSQL & "HOUMON_SIJISYO,"
    End If
    strSQL = strSQL & "OTHER_STATION_SIJI,"
    If Not blnOtherFlg Then
        strSQL = strSQL & "OTHER_STATION_NM,"
    End If
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
    If Trim$(precSIS_OTHER("�w������From")) = "����00�N00��00��" Then
        strSQL = strSQL & "'0000�N00��00��',"
    Else
        strSQL = strSQL & "'" & precSIS_OTHER("�w������From") & "',"
    End If
    If Trim$(precSIS_OTHER("�w������To")) = "����00�N00��00��" Then
        strSQL = strSQL & "'0000�N00��00��',"
    Else
        strSQL = strSQL & "'" & precSIS_OTHER("�w������To") & "',"
    End If
    strSQL = strSQL & precSIS_OTHER("�v���F��̏�") & ","
    If blnFlg Then
'        strSQL = strSQL & Abs(CInt(precSIS_OTHER("�K��Ō�w����"))) & ","
        strSQL = strSQL & CInt(precSIS_OTHER("�K��Ō�w����")) & ","
    End If
    
    If blnOtherFlg Then
        strSQL = strSQL & "1,"
    Else
        If precSIS_OTHER2("���X�e�[�V�����w��") = "0" Then
            strSQL = strSQL & "1,"
        Else
            strSQL = strSQL & "2,"
        End If
        strSQL = strSQL & "'" & precSIS_OTHER2("�Ō�X�e�[�V������") & "',"
    End If
    
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"

    '�ڍs�������s
    Call FDBExecuteSQL(strSQL)

End Sub
