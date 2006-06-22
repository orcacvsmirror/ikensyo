Attribute VB_Name = "basLog"
Option Explicit

Public Enum LogErrType
    Info = 0
    Error = 1
End Enum

Private intFile As Integer

Public Sub LogOpen()
'============================================================================================
'ログファイルオープン
'============================================================================================

    'ログフォルダが存在しない場合は作成する
    On Error Resume Next
        Call MkDir(App.Path & "\logs")
    On Error GoTo 0
    
    intFile = FreeFile
    Open App.Path & "\logs\" & Format(Now, "yyyy-mm-dd-hhnnss") & ".log" For Output As intFile

End Sub

Public Sub LogClose()
'============================================================================================
'ログファイルクローズ
'============================================================================================

    Close #intFile

End Sub

Public Sub LogWrite(pType As LogErrType, pMessage As String)
'============================================================================================
'ログ書き込み
'============================================================================================
Dim strType As String
On Error GoTo Err_LogWrite

    'ログタイプ判定
    Select Case pType
        Case LogErrType.Info:
            strType = "INFO"
        Case LogErrType.Error:
            strType = "ERROR"
    End Select

    'ログファイルに書き込み\
    Print #intFile, Format(Now, "yyyy/mm/dd hh:nn:ss") & "," & strType & "," & pMessage
    Exit Sub

Err_LogWrite:

    'ログファイルを無理やりオープン
    Call LogOpen
    '書き込み
    Call LogWrite(pType, pMessage)

End Sub
