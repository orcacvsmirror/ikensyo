Attribute VB_Name = "basMain"
Option Explicit

Public Const MSG_EXCEPTIONSYSTEM = "SystemException"
Public Const MSG_EXCEPTIONINFO = "InformationException"
Public Const MSG_INFO = "Information"

Public gCommandLine As String           '�R�}���h���C���ޔ�̈�
Public gIniFilePath As String           'INI�t�@�C���p�X
Public gNowDate As Date                 '�ڍs����

Public gFdbUser As String               'FDB���[�U��
Public gFdbPassword As String           'FDB�p�X���[�h

Public Enum DocKbn
    IKEN = 1
    SIS = 2
End Enum

Public Enum DBVersion
    UNKNOWN = -1
    VER10 = 0
    ver15 = 1
    VER153 = 2
    VER161 = 3
End Enum

'add sta shin fujihara 2006-2-22
Public gClearFinish As Boolean
'add end shin fujihara 2006-2-22

Public Function MainCheck() As Boolean
'============================================================================================
'�`�F�b�N����
'============================================================================================

    MainCheck = False

    With frmM1
    
        If Trim$(.txtFrom.Text) = "" Then
            Call MsgBox(.fraFrom.Caption & "�̏ꏊ�𐳂����w�肵�Ă��������B", vbInformation, MSG_EXCEPTIONINFO)
            .txtFrom.SetFocus
            Exit Function
        End If
    
        If Dir(Trim$(.txtFrom.Text)) = "" Then
            Call MsgBox(.fraFrom.Caption & "�Ɏw�肳�ꂽ�t�@�C���͑��݂��܂���B", vbInformation, MSG_EXCEPTIONINFO)
            .txtFrom.SetFocus
            Exit Function
        End If
    
        If Trim$(.txtTo.Text) = "" Then
            Call MsgBox(.fraTo.Caption & "�̏ꏊ�𐳂����w�肵�Ă��������B", vbInformation, MSG_EXCEPTIONINFO)
            .txtTo.SetFocus
            Exit Function
        End If
    
        If Dir(Trim$(.txtTo.Text)) = "" Then
            Call MsgBox(.fraTo.Caption & "�Ɏw�肳�ꂽ�t�@�C���͑��݂��܂���B", vbInformation, MSG_EXCEPTIONINFO)
            .txtTo.SetFocus
            Exit Function
        End If
        
        'add shin fujihara 2006-2-22
        If Len(Trim$(.txtTo.Text)) <> LenB(StrConv(Trim$(.txtTo.Text), vbFromUnicode)) Then
            Call MsgBox(.fraTo.Caption & "�̃t�@�C���p�X�ɓ��{�ꂪ�܂܂�Ă��܂��B", vbInformation, MSG_EXCEPTIONINFO)
            .txtTo.SetFocus
            Exit Function
        End If
        'add shin fujihara 2006-2-22
    
    End With
    
    MainCheck = True

End Function

Public Function GetDBVersion() As DBVersion
'============================================================================================
'DB�o�[�W�������擾
'============================================================================================
Dim rec As Object
Dim ini As String
Dim intCnt As Integer

Dim strCommon As String
Dim varVer10 As Variant
Dim varVer15 As Variant
Dim varVer153 As Variant
Dim varVer161Columns As Variant

Dim colVer10 As New Collection
Dim colVer15 As New Collection
Dim colVer153 As New Collection
Dim colVer161Columns As New Collection

    With frmM1
    
        GetDBVersion = UNKNOWN

        '���ʃe�[�u��
        ini = String(511, Chr$(0))
        Call GetPrivateProfileString("Version", "Common", "", ini, Len(ini) + 1, gIniFilePath)
        strCommon = Trim$(Replace(ini, Chr$(0), ""))
        
        'Ver1.0
        ini = String(511, Chr$(0))
        Call GetPrivateProfileString("Version", "1.0", "", ini, Len(ini) + 1, gIniFilePath)
        varVer10 = Split(strCommon & "," & Trim$(Replace(ini, Chr$(0), "")), ",")
    
        'Ver1.5
        ini = String(511, Chr$(0))
        Call GetPrivateProfileString("Version", "1.5", "", ini, Len(ini) + 1, gIniFilePath)
        varVer15 = Split(strCommon & "," & Trim$(Replace(ini, Chr$(0), "")), ",")
    
        'Ver1.5.3
        ini = String(511, Chr$(0))
        Call GetPrivateProfileString("Version", "1.5.3", "", ini, Len(ini) + 1, gIniFilePath)
        varVer153 = Split(strCommon & "," & Trim$(Replace(ini, Chr$(0), "")), ",")
    
        'Ver1.6.1Columns
        ini = String(511, Chr$(0))
        Call GetPrivateProfileString("Version", "1.6.1Columns", "", ini, Len(ini) + 1, gIniFilePath)
        varVer161Columns = Split(Trim$(Replace(ini, Chr$(0), "")), ",")
    
        '�R�l�N�V�����ڑ�
        If MDBConnection(Trim$(.txtFrom.Text)) Then
            Set rec = MDBOpenSchema(adSchemaTables)
            Do Until rec.EOF
                If rec("TABLE_TYPE") = "TABLE" Or rec("TABLE_TYPE") = "LINK" Then
                    colVer10.Add rec("TABLE_NAME"), rec("TABLE_NAME")
                    colVer15.Add rec("TABLE_NAME"), rec("TABLE_NAME")
                    colVer153.Add rec("TABLE_NAME"), rec("TABLE_NAME")
                End If
                rec.MoveNext
            Loop
        
            '1.5.3Columns���擾
            Set rec = MDBOpenSchema(adSchemaColumns, Array(Empty, Empty, "�w�������̑�", Empty))
            Do Until rec.EOF
                colVer161Columns.Add rec("COLUMN_NAME"), rec("COLUMN_NAME")
                rec.MoveNext
            Loop
        
        End If
                    
        'Ver1.0�`�F�b�N
        On Error Resume Next
            For intCnt = 0 To UBound(varVer10)
                colVer10.Remove (varVer10(intCnt))
            Next intCnt
            If Err.Number = 0 Then
                If colVer10.Count = 0 Then
                    'Ver1.0�Ɗm�肷��
                    GetDBVersion = VER10
                    Exit Function
                End If
            End If
        On Error GoTo 0
    
        'Ver1.5�`�F�b�N
        On Error Resume Next
            For intCnt = 0 To UBound(varVer15)
                colVer15.Remove (varVer15(intCnt))
            Next intCnt
            If Err.Number = 0 Then
                If colVer15.Count = 0 Then
                    'Ver1.5�Ɗm�肷��
                    GetDBVersion = ver15
                    Exit Function
                End If
            End If
        On Error GoTo 0
    
        'Ver1.5.3�`�F�b�N
        On Error Resume Next
            For intCnt = 0 To UBound(varVer153)
                colVer153.Remove (varVer153(intCnt))
            Next intCnt
            If Err.Number = 0 Then
                If colVer153.Count = 0 Then
                    
                    For intCnt = 0 To UBound(varVer161Columns)
                        colVer161Columns.Remove (varVer161Columns(intCnt))
                    Next intCnt
                    
                    If Err.Number = 0 Then
                        'Ver1.6.1�Ɗm�肷��
                        GetDBVersion = VER161
                    Else
                        'Ver1.5.3�Ɗm�肷��
                        GetDBVersion = VER153
                    End If
                
                    Exit Function
                End If
            End If
        On Error GoTo 0
    
    
        '�R�l�N�V��������
        Call MDBUnConnection
    
    End With
    

    
End Function

Public Sub ConvVER10()
'============================================================================================
'Ver1.0�ڍs����
'============================================================================================
    With frmM1
    
        '�R�l�N�V�����ڑ�
        'edit sta shin fujihara 2006-2-22
        'Call MDBConnection(Trim$(.txtFrom.Text))
        'Call FDBConnection(Trim$(.txtTo.Text), gFdbUser, gFdbPassword)
        If MDBConnection(Trim$(.txtFrom.Text)) = False Then
            Call MsgBox("�ڍs���f�[�^�x�[�X�̐ڑ��Ɏ��s���܂���", vbExclamation, MSG_EXCEPTIONSYSTEM)
            Exit Sub
        End If
        If FDBConnection(Trim$(.txtTo.Text), gFdbUser, gFdbPassword) = False Then
            Call MsgBox("�ڍs��f�[�^�x�[�X�̐ڑ��Ɏ��s���܂���", vbExclamation, MSG_EXCEPTIONSYSTEM)
            Exit Sub
        End If
        'edit end shin fujihara 2006-2-22
 
        '�ӌ������ڍs
        Call ConvIKENSYO10
        '��t�}�X�^�ڍs
        Call ConvDOCTOR10
        '���X�e�[�V�����}�X�^�ڍs
        Call ConvSTATION
        '�A�g��}�X�^�ڍs
        Call ConvRENKEII
        
        '�R�l�N�V�����ؒf
        Call MDBUnConnection
        Call FDBUnConnection
        
        'add sta shin fujihara 2006-2-22
        gClearFinish = True
        'add end shin fujihara 2006-2-22
    
    End With

End Sub

Public Sub ConvVER15()
'============================================================================================
'Ver1.5�ڍs����
'============================================================================================
    With frmM1
    
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrorSec
        '2006/02/11[Shin Fujihara] : add end
           '�R�l�N�V�����ڑ�
           'edit sta shin fujihara 2006-2-22
           'Call MDBConnection(Trim$(.txtFrom.Text))
           'Call FDBConnection(Trim$(.txtTo.Text), gFdbUser, gFdbPassword)
            If MDBConnection(Trim$(.txtFrom.Text)) = False Then
                Call MsgBox("�ڍs���f�[�^�x�[�X�̐ڑ��Ɏ��s���܂���", vbExclamation, MSG_EXCEPTIONSYSTEM)
                Exit Sub
            End If
            If FDBConnection(Trim$(.txtTo.Text), gFdbUser, gFdbPassword) = False Then
                Call MsgBox("�ڍs��f�[�^�x�[�X�̐ڑ��Ɏ��s���܂���", vbExclamation, MSG_EXCEPTIONSYSTEM)
                Exit Sub
            End If
            'edit end shin fujihara 2006-2-22
           
    
           '�ӌ������ڍs
           Call ConvIKENSYO15
           '��t�}�X�^�ڍs
           Call ConvDOCTOR15
           '���X�e�[�V�����}�X�^�ڍs
           Call ConvSTATION
           '�A�g��}�X�^�ڍs
           Call ConvRENKEII
           '���Ǝ҃}�X�^�ڍs
           Call ConvJIGYOUSHA
           '�ی��҃}�X�^�ڍs
           Call ConvINSURER
        
        '2006/02/11[Shin Fujihara] : del begin
        '�R�l�N�V�����ؒf
        'Call MDBUnConnection
        'Call FDBUnConnection
        '2006/02/11[Shin Fujihara] : del end
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo 0
        '2006/02/11[Shin Fujihara] : add end
        'add sta shin fujihara 2006-2-22
        gClearFinish = True
        'add end shin fujihara 2006-2-22
    End With

'2006/02/11[Shin Fujihara] : add begin
ErrorSec:
        '�R�l�N�V�����ؒf
        Call MDBUnConnection
        Call FDBUnConnection
'2006/02/11[Shin Fujihara] : add end
End Sub

Public Sub ConvVER153()
'============================================================================================
'Ver1.5.3�ڍs����
'============================================================================================
    With frmM1
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrorSec
        '2006/02/11[Shin Fujihara] : add end
           '�R�l�N�V�����ڑ�
           'edit sta shin fujihara 2006-2-22
           'Call MDBConnection(Trim$(.txtFrom.Text))
           'Call FDBConnection(Trim$(.txtTo.Text), gFdbUser, gFdbPassword)
            If MDBConnection(Trim$(.txtFrom.Text)) = False Then
                Call MsgBox("�ڍs���f�[�^�x�[�X�̐ڑ��Ɏ��s���܂���", vbExclamation, MSG_EXCEPTIONSYSTEM)
                Exit Sub
            End If
            If FDBConnection(Trim$(.txtTo.Text), gFdbUser, gFdbPassword) = False Then
                Call MsgBox("�ڍs��f�[�^�x�[�X�̐ڑ��Ɏ��s���܂���", vbExclamation, MSG_EXCEPTIONSYSTEM)
                Exit Sub
            End If
            'edit end shin fujihara 2006-2-22

    
           '�ӌ������ڍs
           Call ConvIKENSYO153
           '��t�}�X�^�ڍs
           Call ConvDOCTOR15
           '���X�e�[�V�����}�X�^�ڍs
           Call ConvSTATION
           '�A�g��}�X�^�ڍs
           Call ConvRENKEII
           '���Ǝ҃}�X�^�ڍs
           Call ConvJIGYOUSHA
           '�ی��҃}�X�^�ڍs
           Call ConvINSURER
        
        '2006/02/11[Shin Fujihara] : del begin
        '�R�l�N�V�����ؒf
        'Call MDBUnConnection
        'Call FDBUnConnection
        '2006/02/11[Shin Fujihara] : del end
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo 0
        '2006/02/11[Shin Fujihara] : add end
        'add sta shin fujihara 2006-2-22
        gClearFinish = True
        'add end shin fujihara 2006-2-22
    End With
'2006/02/11[Shin Fujihara] : add begin
ErrorSec:
        '�R�l�N�V�����ؒf
        Call MDBUnConnection
        Call FDBUnConnection
'2006/02/11[Shin Fujihara] : add end
End Sub

Public Sub ConvVER161()
'============================================================================================
'Ver1.6.1�ڍs����
'============================================================================================
    With frmM1
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrorSec
        '2006/02/11[Shin Fujihara] : add end
        '�R�l�N�V�����ڑ�
        'edit sta shin fujihara 2006-2-22
        'Call MDBConnection(Trim$(.txtFrom.Text))
        'Call FDBConnection(Trim$(.txtTo.Text), gFdbUser, gFdbPassword)
        If MDBConnection(Trim$(.txtFrom.Text)) = False Then
            Call MsgBox("�ڍs���f�[�^�x�[�X�̐ڑ��Ɏ��s���܂���", vbExclamation, MSG_EXCEPTIONSYSTEM)
            Exit Sub
        End If
        If FDBConnection(Trim$(.txtTo.Text), gFdbUser, gFdbPassword) = False Then
            Call MsgBox("�ڍs��f�[�^�x�[�X�̐ڑ��Ɏ��s���܂���", vbExclamation, MSG_EXCEPTIONSYSTEM)
            Exit Sub
        End If
        'edit sta shin fujihara 2006-2-22
        
 
        '�ӌ������ڍs
        Call ConvIKENSYO161
        '��t�}�X�^�ڍs
        Call ConvDOCTOR15
        '���X�e�[�V�����}�X�^�ڍs
        Call ConvSTATION
        '�A�g��}�X�^�ڍs
        Call ConvRENKEII
        '���Ǝ҃}�X�^�ڍs
        Call ConvJIGYOUSHA
        '�ی��҃}�X�^�ڍs
        Call ConvINSURER
        
        '2006/02/11[Shin Fujihara] : del begin
        '�R�l�N�V�����ؒf
        'Call MDBUnConnection
        'Call FDBUnConnection
        '2006/02/11[Shin Fujihara] : del end
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo 0
        '2006/02/11[Shin Fujihara] : add end
        'add sta shin fujihara 2006-2-22
        gClearFinish = True
        'add end shin fujihara 2006-2-22
    End With
'2006/02/11[Shin Fujihara] : add begin
ErrorSec:
        '�R�l�N�V�����ؒf
        Call MDBUnConnection
        Call FDBUnConnection
'2006/02/11[Shin Fujihara] : add end
End Sub

