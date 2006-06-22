Attribute VB_Name = "basLog"
Option Explicit

Public Enum LogErrType
    Info = 0
    Error = 1
End Enum

Private intFile As Integer

Public Sub LogOpen()
'============================================================================================
'���O�t�@�C���I�[�v��
'============================================================================================

    '���O�t�H���_�����݂��Ȃ��ꍇ�͍쐬����
    On Error Resume Next
        Call MkDir(App.Path & "\logs")
    On Error GoTo 0
    
    intFile = FreeFile
    Open App.Path & "\logs\" & Format(Now, "yyyy-mm-dd-hhnnss") & ".log" For Output As intFile

End Sub

Public Sub LogClose()
'============================================================================================
'���O�t�@�C���N���[�Y
'============================================================================================

    Close #intFile

End Sub

Public Sub LogWrite(pType As LogErrType, pMessage As String)
'============================================================================================
'���O��������
'============================================================================================
Dim strType As String
On Error GoTo Err_LogWrite

    '���O�^�C�v����
    Select Case pType
        Case LogErrType.Info:
            strType = "INFO"
        Case LogErrType.Error:
            strType = "ERROR"
    End Select

    '���O�t�@�C���ɏ�������\
    Print #intFile, Format(Now, "yyyy/mm/dd hh:nn:ss") & "," & strType & "," & pMessage
    Exit Sub

Err_LogWrite:

    '���O�t�@�C���𖳗����I�[�v��
    Call LogOpen
    '��������
    Call LogWrite(pType, pMessage)

End Sub
