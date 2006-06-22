Attribute VB_Name = "basDBMS_FDB"
Option Explicit
Private con As Object

Public Function FDBConnection(pstrConnectFile As String, pstrUserId As String, psrtPassword As String) As Boolean
'============================================================================================
'コネクション接続
'============================================================================================
On Error GoTo Err_Connection

        FDBConnection = False

        'ファイルパス確認
        If Trim$(pstrConnectFile) = "" Then
            Exit Function
        End If
        
        'ファイル存在確認
        If Trim$(Dir(pstrConnectFile)) = "" Then
            Exit Function
        End If

        'コネクションオープン
        Set con = CreateObject("ADODB.Connection")
        con.Open "Provider=ZStyle IBOLE Provider;Password=" & psrtPassword & ";User ID=" & pstrUserId & ";Data Source=" & pstrConnectFile & ";"
        
        FDBConnection = True
        
        Exit Function
        
Err_Connection:

    'ログ出力
    Call LogWrite(Error, Err.Description)
    
    Call FDBUnConnection

End Function

Public Function FDBUnConnection()
'============================================================================================
'コネクション解除
'============================================================================================
On Error GoTo Err_UnConnection
    
    con.Close
    Set con = Nothing
    
    Exit Function

Err_UnConnection:

    'ログ出力
    Call LogWrite(Error, Err.Description)

End Function

Public Function FDBExecuteRecordSet(ByVal pstrSQL As String, Optional ByVal plngCursorType As CursorTypeEnum = adOpenForwardOnly, Optional ByVal plngLockType As LockTypeEnum = adLockReadOnly) As Object
'============================================================================================
'レコードセット取得
'============================================================================================
Dim rec As Object

On Error GoTo Err_OpenRecorSet
    
    'レコードセットオブジェクトの生成
    Set rec = CreateObject("ADODB.Recordset")
    'レコードセットのオープン
    rec.Open pstrSQL, con, plngCursorType, plngLockType
    'レコードセットを返す
    Set FDBExecuteRecordSet = rec
    
    Exit Function
        
Err_OpenRecorSet:

    Call LogWrite(Error, Err.Description & "," & pstrSQL)
    Set rec = Nothing

End Function

Public Function FDBExecuteSQL(ByVal pstrSQL As String) As Boolean
'============================================================================================
'SQL文発行処理
'============================================================================================

On Error GoTo Err_Execute

    FDBExecuteSQL = False
    
    'add start s-fujihara 2005/10/28
    '痴呆、痴呆症の文字列を認知症に置換する
    pstrSQL = Replace$(pstrSQL, "痴呆症", "認知症")
    pstrSQL = Replace$(pstrSQL, "痴呆", "認知症")
    'add end s-fujihara 2005/10/28
    'add start s-fujihara 2005/12/12
    '以下、３病名も変更対象
    pstrSQL = Replace$(pstrSQL, "パーキンソン病", "パーキンソン病関連疾患")
    pstrSQL = Replace$(pstrSQL, "シャイ・ドレーガー症候群", "多系統萎縮症")
    pstrSQL = Replace$(pstrSQL, "シャイドレーガー症候群", "多系統萎縮症")
    pstrSQL = Replace$(pstrSQL, "慢性関節リウマチ", "関節リウマチ")
    'add end s-fujihara 2005/12/12
    
    con.Execute pstrSQL

    FDBExecuteSQL = True
    Exit Function

Err_Execute:

    Call LogWrite(Error, Err.Description & "," & pstrSQL)

End Function

'2006/02/11[Shin Fujihara] : add begin
Public Function FDBTran(ByVal plngTran As geTransactionType) As Boolean
'============================================================================================
'トランザクション設定
'============================================================================================

On Error GoTo Err_Transaction
    
    FDBTran = False
        
    Select Case plngTran
        Case BeginTrans
            con.BeginTrans
        Case CommitTrans
            con.CommitTrans
        Case RollbackTrans
            con.RollbackTrans
    End Select
    
    FDBTran = True
    Exit Function
    
Err_Transaction:

    Call LogWrite(Error, Err.Description)

End Function
'2006/02/11[Shin Fujihara] : add end
