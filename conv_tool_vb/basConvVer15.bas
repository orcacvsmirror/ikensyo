Attribute VB_Name = "basConvVer15"
Option Explicit

Public Sub ConvIKENSYO15()
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
                Set recIKENSYO_SEIKYU = MDBExecuteRecordSet("SELECT * FROM �ӌ��������� WHERE (���Ҕԍ� = " & recIKENSYO("���Ҕԍ�") & ") AND (�쐬NO = " & recIKENSYO("�쐬NO") & ")", adOpenForwardOnly, adLockReadOnly)
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
                'COMMON_IKN_SIS�ڍs����
                Call ConvCOMMON_IKN_SIS(intEda, recSIS, recSIS_OTHER, SIS)
                'SIS_ORIGIN�ڍs����
                Call ConvSIS_ORIGIN15(intEda, recSIS_OTHER)
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

Public Sub ConvINSURER()
'============================================================================================
'INSURER�ڍs�iVer1.5�AVer1.5.3���p�j
'============================================================================================
Dim recINSURER As Object
Dim strSQL As String
    
    '�ڍs���f�[�^���擾
    Set recINSURER = MDBExecuteRecordSet("SELECT * FROM �ی��҃}�X�^ ORDER BY �ی��Ҕԍ�", adOpenForwardOnly, adLockReadOnly)
        
    '�ڍs��Ƀf�[�^��}��
    Do Until recINSURER.EOF
                    
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrSec
            Call FDBTran(BeginTrans)
            '2006/02/11[Shin Fujihara] : add end

                    
            strSQL = ""
            strSQL = strSQL & "INSERT INTO INSURER ("
            strSQL = strSQL & "INSURER_NO,"
            strSQL = strSQL & "INSURER_NM,"
            strSQL = strSQL & "FD_OUTPUT_UMU,"
            strSQL = strSQL & "SEIKYUSHO_HAKKOU_PATTERN,"
            strSQL = strSQL & "SEIKYUSHO_OUTPUT_PATTERN,"
            strSQL = strSQL & "DR_NM_OUTPUT_UMU,"
            strSQL = strSQL & "HEADER_OUTPUT_UMU1,"
            strSQL = strSQL & "HEADER_OUTPUT_UMU2,"
            strSQL = strSQL & "ISS_INSURER_NO,"
            strSQL = strSQL & "ISS_INSURER_NM,"
            strSQL = strSQL & "SKS_INSURER_NO,"
            strSQL = strSQL & "SKS_INSURER_NM,"
            strSQL = strSQL & "ZAITAKU_SINKI_CHARGE,"
            strSQL = strSQL & "ZAITAKU_KEIZOKU_CHARGE,"
            strSQL = strSQL & "SISETU_SINKI_CHARGE,"
            strSQL = strSQL & "SISETU_KEIZOKU_CHARGE,"
            strSQL = strSQL & "SHOSIN_SINRYOUJO,"
            strSQL = strSQL & "SHOSIN_HOSPITAL,"
            strSQL = strSQL & "EXP_KS,"
            strSQL = strSQL & "EXP_KIK_MKI,"
            strSQL = strSQL & "EXP_KIK_KEKK,"
            strSQL = strSQL & "EXP_KKK_KKK,"
            strSQL = strSQL & "EXP_KKK_SKK,"
            strSQL = strSQL & "EXP_NITK,"
            strSQL = strSQL & "EXP_XRAY_TS,"
            strSQL = strSQL & "EXP_XRAY_SS,"
            strSQL = strSQL & "EXP_XRAY_FILM,"
            strSQL = strSQL & "SOUKATUHYOU_PRT,"
            strSQL = strSQL & "MEISAI_KIND,"
            strSQL = strSQL & "FURIKOMISAKI_PRT,"
            strSQL = strSQL & "SOUKATU_FURIKOMI_PRT,"
            strSQL = strSQL & "SOUKATUHYOU_PRT2,"
            strSQL = strSQL & "MEISAI_KIND2,"
            strSQL = strSQL & "FURIKOMISAKI_PRT2,"
            strSQL = strSQL & "SOUKATU_FURIKOMI_PRT2,"
            strSQL = strSQL & "LAST_TIME"
            strSQL = strSQL & ")"
            
            strSQL = strSQL & "VALUES ("
            strSQL = strSQL & "'" & recINSURER("�ی��Ҕԍ�") & "',"
            strSQL = strSQL & "'" & recINSURER("�ی��Җ���") & "',"
            strSQL = strSQL & Abs(CInt(recINSURER("FD�o�͗L��"))) & ","
            strSQL = strSQL & Abs(CInt(recINSURER("���������s�p�^�[��"))) & ","
            strSQL = strSQL & recINSURER("�������o�̓p�^�[��") + 1 & ","
            strSQL = strSQL & Abs(CInt(recINSURER("��t�����o�͗L��"))) & ","
            strSQL = strSQL & Abs(CInt(recINSURER("�w�b�_�o�͗L��"))) & ","
            strSQL = strSQL & Abs(CInt(recINSURER("�w�b�_2�o�͗L��"))) & ","
            strSQL = strSQL & "'" & recINSURER("�ӌ����쐬��������ی��Ҕԍ�") & "',"
            strSQL = strSQL & "'" & recINSURER("�ӌ����쐬��������ی��Җ���") & "',"
            strSQL = strSQL & "'" & recINSURER("�f�@�������p������ی��Ҕԍ�") & "',"
            strSQL = strSQL & "'" & recINSURER("�f�@�������p������ی��Җ���") & "',"
            strSQL = strSQL & recINSURER("�ӌ����쐬��_�ݑ�V�K") & ","
            strSQL = strSQL & recINSURER("�ӌ����쐬��_�ݑ�p��") & ","
            strSQL = strSQL & recINSURER("�ӌ����쐬��_�{�ݐV�K") & ","
            strSQL = strSQL & recINSURER("�ӌ����쐬��_�{�݌p��") & ","
            strSQL = strSQL & recINSURER("���f_�f�Ï�") & ","
            strSQL = strSQL & recINSURER("���f_�a�@") & ","
            strSQL = strSQL & recINSURER("������p_���t�̎�") & ","
            strSQL = strSQL & recINSURER("������p_���t��ʌ���_�������t��ʌ���") & ","
            strSQL = strSQL & recINSURER("������p_���t��ʌ���_���t�t���w�I����") & ","
            strSQL = strSQL & recINSURER("������p_�����w����_���t���w����") & ","
            strSQL = strSQL & recINSURER("������p_�����w����_�����w�I����") & ","
            strSQL = strSQL & recINSURER("������p_�A����ʕ����萫����ʌ���") & ","
            strSQL = strSQL & recINSURER("������p_X���B�e_�P���B�e") & ","
            strSQL = strSQL & recINSURER("������p_X���B�e_�ʐ^�f�f") & ","
            strSQL = strSQL & recINSURER("������p_X���B�e_�t�B����") & ","
            Select Case recINSURER("�����\��")
                Case 1, 2
                    strSQL = strSQL & "1,"
                Case 3
                    strSQL = strSQL & "2,"
                Case Else
                    strSQL = strSQL & recINSURER("�����\��") & ","
            End Select
            strSQL = strSQL & recINSURER("�������׎��") & ","
            strSQL = strSQL & recINSURER("�U�����") & ","
            strSQL = strSQL & recINSURER("�����\�U�����") & ","
            Select Case recINSURER("�����\��2")
                Case 1, 2
                    strSQL = strSQL & "1,"
                Case 3
                    strSQL = strSQL & "2,"
                Case Else
                    strSQL = strSQL & recINSURER("�����\��2") & ","
            End Select
            strSQL = strSQL & recINSURER("�������׎��2") & ","
            strSQL = strSQL & recINSURER("�U�����2") & ","
            strSQL = strSQL & recINSURER("�����\�U�����2") & ","
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

        
        recINSURER.MoveNext
        
    Loop
                                                            
End Sub

Public Sub ConvJIGYOUSHA()
'============================================================================================
'JIGYOUSHA�ڍs�iVer1.5�AVer1.5.3�AVer1.6���p�j
'============================================================================================
Dim recJIGYOUSHA As Object
Dim strSQL As String

    '�ڍs���f�[�^���擾
    Set recJIGYOUSHA = MDBExecuteRecordSet("SELECT * FROM ���Ǝ҃}�X�^ ORDER BY ��t�R�[�h", adOpenForwardOnly, adLockReadOnly)

    '�ڍs��Ƀf�[�^��}��
    Do Until recJIGYOUSHA.EOF
    
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrSec
            Call FDBTran(BeginTrans)
            '2006/02/11[Shin Fujihara] : add end

            strSQL = ""
            strSQL = strSQL & "INSERT INTO JIGYOUSHA ("
            strSQL = strSQL & "DR_CD,"
            strSQL = strSQL & "INSURER_NO,"
            strSQL = strSQL & "JIGYOUSHA_NO,"
            strSQL = strSQL & "LAST_TIME"
            strSQL = strSQL & ")"
    
            strSQL = strSQL & "VALUES ("
            strSQL = strSQL & recJIGYOUSHA("��t�R�[�h") & ","
            strSQL = strSQL & "'" & recJIGYOUSHA("�ی��Ҕԍ�") & "',"
            strSQL = strSQL & "'" & recJIGYOUSHA("���ƎҔԍ�") & "',"
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

        recJIGYOUSHA.MoveNext

    Loop
                                                            
End Sub

Public Sub ConvDOCTOR15()
'============================================================================================
'DOCTOR�ڍs�iVer1.5�AVer1.5.3���p�j
'============================================================================================
Dim recDOCTOR As Object
Dim recDOCTOR_MAX As Object
Dim strSQL As String
    
    '�ڍs���f�[�^���擾
    Set recDOCTOR = MDBExecuteRecordSet("SELECT * FROM ��t�}�X�^ ORDER BY ��t�R�[�h", adOpenForwardOnly, adLockReadOnly)
    Set recDOCTOR_MAX = MDBExecuteRecordSet("SELECT MAX(��t�R�[�h) AS ��t�R�[�h_�ő�l FROM ��t�}�X�^", adOpenForwardOnly, adLockReadOnly)
    
    '�f�[�^�擾�Ɏ��s�����ꍇ�͂��̂܂܏I��
    If IsNull(recDOCTOR) Then
        Exit Sub
    End If
    
    If IsNull(recDOCTOR_MAX) Then
        Exit Sub
    End If
    
    
    '�ڍs��f�[�^�̃g���K�[����U��~
    Call FDBExecuteSQL("ALTER TRIGGER SET_DOCTOR_ID INACTIVE")
    
    '�ڍs��Ƀf�[�^��}��
    Do Until recDOCTOR.EOF
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrSec
            Call FDBTran(BeginTrans)
            '2006/02/11[Shin Fujihara] : add end
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
            strSQL = strSQL & "KAISETUSHA_NM,"
            strSQL = strSQL & "DR_NO,"
            strSQL = strSQL & "MI_KBN,"
            strSQL = strSQL & "BANK_NM,"
            strSQL = strSQL & "BANK_SITEN_NM,"
            strSQL = strSQL & "BANK_KOUZA_NO,"
            strSQL = strSQL & "BANK_KOUZA_KIND,"
            strSQL = strSQL & "FURIKOMI_MEIGI,"
            strSQL = strSQL & "LAST_TIME"
            strSQL = strSQL & ")"
            
            strSQL = strSQL & "VALUES ("
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
            strSQL = strSQL & "'" & recDOCTOR("�J�ݎҎ���") & "',"
            strSQL = strSQL & "'" & recDOCTOR("��t�ԍ�") & "',"
            strSQL = strSQL & recDOCTOR("�f�Ï���a�@�敪") & ","
            strSQL = strSQL & "'" & recDOCTOR("�U������Z�@�֖�") & "',"
            strSQL = strSQL & "'" & recDOCTOR("�U������Z�@�֎x�X��") & "',"
            strSQL = strSQL & "'" & recDOCTOR("�U��������ԍ�") & "',"
            strSQL = strSQL & recDOCTOR("�U����������") & ","
            strSQL = strSQL & "'" & recDOCTOR("�U���於�`�l") & "',"
            strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
            strSQL = strSQL & ")"
            
            'SQL�����s
            Call FDBExecuteSQL(strSQL)
        
        '2006/02/11[Shin Fujihara] : add begin
            Call FDBTran(CommitTrans)
        On Error GoTo 0
ErrSec:
        If Err.Number <> 0 Then
            Call FDBTran(RollbackTrans)
        End If
        '2006/02/11[Shin Fujihara] : add end
        
        recDOCTOR.MoveNext
        
    Loop
                                                          
    '�ڍs��̎����̔ԏ����l��ݒ�
    If Not IsNull(recDOCTOR_MAX("��t�R�[�h_�ő�l")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_DOCTOR to " & recDOCTOR_MAX("��t�R�[�h_�ő�l"))
    End If
    '�ڍs��f�[�^�̃g���K�[���J�n
    Call FDBExecuteSQL("ALTER TRIGGER SET_DOCTOR_ID ACTIVE")
                                                            
End Sub

Sub ConvIKN_BILL(pintEda As Long, precIKENSYO_SEIKYU As Object)
'============================================================================================
'IKN_BILL�ڍs�iVer1.5��p�j
'============================================================================================
Dim strSQL As String

    strSQL = ""
    strSQL = strSQL & "INSERT INTO IKN_BILL ("
    strSQL = strSQL & "PATIENT_NO,"
    strSQL = strSQL & "EDA_NO,"
    
    
    strSQL = strSQL & "BANK_NM,"
    strSQL = strSQL & "BANK_SITEN_NM,"
    strSQL = strSQL & "KOUZA_NO,"
    strSQL = strSQL & "KOUZA_KIND,"
    strSQL = strSQL & "KOUZA_MEIGI,"
    strSQL = strSQL & "JIGYOUSHA_NO,"
    strSQL = strSQL & "KAISETUSHA_NM,"
    strSQL = strSQL & "DR_NO,"
    strSQL = strSQL & "IKN_CHARGE,"
    strSQL = strSQL & "SHOSIN_TAISHOU,"
    strSQL = strSQL & "SHOSIN,"
    strSQL = strSQL & "SHOSIN_TEKIYOU,"
    strSQL = strSQL & "XRAY_TANJUN_SATUEI,"
    strSQL = strSQL & "XRAY_SHASIN_SINDAN,"
    strSQL = strSQL & "XRAY_FILM,"
    strSQL = strSQL & "XRAY_TEKIYOU,"
    strSQL = strSQL & "BLD_SAISHU,"
    strSQL = strSQL & "BLD_IPPAN_MASHOU_KETUEKI,"
    strSQL = strSQL & "BLD_IPPAN_EKIKAGAKUTEKIKENSA,"
    strSQL = strSQL & "BLD_IPPAN_TEKIYOU,"
    strSQL = strSQL & "BLD_KAGAKU_KETUEKIKAGAKUKENSA,"
    strSQL = strSQL & "BLD_KAGAKU_SEIKAGAKUTEKIKENSA,"
    strSQL = strSQL & "BLD_KAGAKU_TEKIYOU,"
    strSQL = strSQL & "NYO_KENSA,"
    strSQL = strSQL & "NYO_KENSA_TEKIYOU,"
    strSQL = strSQL & "ZAITAKU_SINKI_CHARGE,"
    strSQL = strSQL & "ZAITAKU_KEIZOKU_CHARGE,"
    strSQL = strSQL & "SISETU_SINKI_CHARGE,"
    strSQL = strSQL & "SISETU_KEIZOKU_CHARGE,"
    strSQL = strSQL & "SHOSIN_SINRYOUJO,"
    strSQL = strSQL & "SHOSIN_HOSPITAL,"
    strSQL = strSQL & "SHOSIN_OTHER,"
    strSQL = strSQL & "EXP_KS,"
    strSQL = strSQL & "EXP_KIK_MKI,"
    strSQL = strSQL & "EXP_KIK_KEKK,"
    strSQL = strSQL & "EXP_KKK_KKK,"
    strSQL = strSQL & "EXP_KKK_SKK,"
    strSQL = strSQL & "EXP_NITK,"
    strSQL = strSQL & "EXP_XRAY_TS,"
    strSQL = strSQL & "EXP_XRAY_SS,"
    strSQL = strSQL & "EXP_XRAY_FILM,"
    strSQL = strSQL & "TAX,"
    strSQL = strSQL & "OUTPUT_PATTERN,"
    strSQL = strSQL & "ISS_INSURER_NO,"
    strSQL = strSQL & "ISS_INSURER_NM,"
    strSQL = strSQL & "SKS_INSURER_NO,"
    strSQL = strSQL & "SKS_INSURER_NM,"
    strSQL = strSQL & "FD_OUTPUT_KBN,"
    'strSQL = strSQL & "FD_TIMESTAMP,"
    
'    If Not IsNull(precIKENSYO_SEIKYU("FD�o�͗p�^�C���X�^���v")) Then
'        If Not Trim$(precIKENSYO_SEIKYU("FD�o�͗p�^�C���X�^���v")) = "" Then
'            strSQL = strSQL & "FD_TIMESTAMP,"
'        End If
'    End If
'    If Not IsNull(precIKENSYO_SEIKYU("�\����")) Then
'        If Not Trim$(precIKENSYO_SEIKYU("�\����")) = "" Then
'            strSQL = strSQL & "SINSEI_DT,"
'        End If
'    End If

    strSQL = strSQL & "HAKKOU_KBN,"
    strSQL = strSQL & "LAST_TIME"
    strSQL = strSQL & ")"

    strSQL = strSQL & " VALUES ("
    strSQL = strSQL & precIKENSYO_SEIKYU("���Ҕԍ�") & ","
    strSQL = strSQL & pintEda & ","
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "2,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    'strSQL = strSQL & "'',"
'    If Not IsNull(precIKENSYO_SEIKYU("FD�o�͗p�^�C���X�^���v")) Then
'        If Not Trim$(precIKENSYO_SEIKYU("FD�o�͗p�^�C���X�^���v")) = "" Then
'            strSQL = strSQL & "'" & Format(precIKENSYO_SEIKYU("FD�o�͗p�^�C���X�^���v"), "yyyy/mm/dd hh:nn:ss") & "',"
'        End If
'    End If
'    If Not IsNull(precIKENSYO_SEIKYU("�\����")) Then
'        If Not Trim$(precIKENSYO_SEIKYU("�\����")) = "" Then
'            strSQL = strSQL & "'" & Format(precIKENSYO_SEIKYU("�\����"), "yyyy/mm/dd hh:nn:ss") & "',"
'        End If
'    End If
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"

    'SQL�����s
    Call FDBExecuteSQL(strSQL)
    
End Sub

Sub ConvSIS_ORIGIN15(pintEda As Long, precSIS_OTHER As Object)
'============================================================================================
'SIS_ORIGIN�ڍs�iVer1.5��p�j
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
    strSQL = strSQL & "SIJI_KIKAN_FROM,"
    strSQL = strSQL & "SIJI_KIKAN_TO,"
    strSQL = strSQL & "YOUKAIGO_JOUKYOU,"
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
'    strSQL = strSQL & "'" & precSIS_OTHER("�w������From") & "',"
'    strSQL = strSQL & "'" & precSIS_OTHER("�w������To") & "',"
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
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"

    '�ڍs�������s
    Call FDBExecuteSQL(strSQL)

End Sub


