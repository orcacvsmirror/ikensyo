Attribute VB_Name = "basDBMS_MDB"
Option Explicit

'トランザクション
Public Enum geTransactionType
    BeginTrans = 1
    CommitTrans = 2
    RollbackTrans = 3
End Enum
'カーソルタイプ
Public Enum CursorTypeEnum
    adOpenForwardOnly = 0
    adOpenKeyset = 1
    adOpenDynamic = 2
    adOpenStatic = 3
End Enum
'ロック
Public Enum LockTypeEnum
    adLockReadOnly = 1
    adLockPessimistic = 2
    adLockOptimistic = 3
    adLockBatchOptimistic = 4
End Enum
'スキーマ
Public Enum SchemaEnum
    adSchemaTables = 20
    adSchemaColumns = 4
End Enum

Private con As Object

Public Function MDBConnection(pstrConnectFile As String) As Boolean
'============================================================================================
'コネクション接続
'============================================================================================
On Error GoTo Err_Connection

        MDBConnection = False

        If Trim$(pstrConnectFile) = "" Then
            Exit Function
        End If
        
        If Trim$(Dir(pstrConnectFile)) = "" Then
            Exit Function
        End If

        'ADOコネクションオブジェクトの生成
        Set con = CreateObject("ADODB.Connection")
        con.Open "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" & pstrConnectFile & ";"
        MDBConnection = True
        
        Exit Function
        
Err_Connection:

    Call LogWrite(Error, Err.Description)
    
    Call MDBUnConnection

End Function

Public Function MDBUnConnection()
'============================================================================================
'コネクション解除
'============================================================================================
On Error GoTo Err_UnConnection
    
    con.Close
    Set con = Nothing
    
    Exit Function
    
Err_UnConnection:

    Call LogWrite(Error, Err.Description)

End Function

Public Function MDBOpenSchema(QueryType As SchemaEnum, Optional ByVal Criteria As Variant) As Object
'============================================================================================
'スキーマ取得
'============================================================================================
Dim rec As Object
On Error GoTo Err_OpenSchema

    Set rec = con.OpenSchema(QueryType, Criteria)
    
    Set MDBOpenSchema = rec
    
    Exit Function

Err_OpenSchema:

    Call LogWrite(Error, Err.Description)
    Set rec = Nothing

End Function

Public Function MDBExecuteRecordSet(ByVal pstrSQL As String, Optional ByVal plngCursorType As CursorTypeEnum = adOpenForwardOnly, Optional ByVal plngLockType As LockTypeEnum = adLockReadOnly) As Object
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
    Set MDBExecuteRecordSet = rec
    
    Exit Function
        
Err_OpenRecorSet:

    Call LogWrite(Error, Err.Description & "," & pstrSQL)
    Set rec = Nothing

End Function

Public Function MDBExecuteSQL(ByVal pstrSQL As String) As Boolean
'============================================================================================
'SQL文発行処理
'============================================================================================

On Error GoTo Err_Execute

    MDBExecuteSQL = False
    
    con.Execute pstrSQL

    MDBExecuteSQL = True
    Exit Function

Err_Execute:

    Call LogWrite(Error, Err.Description & "," & pstrSQL)

End Function

Public Function MDBTran(ByVal plngTran As geTransactionType) As Boolean
'============================================================================================
'トランザクション設定
'============================================================================================

On Error GoTo Err_Transaction
    
    MDBTran = False
        
    Select Case plngTran
        Case BeginTrans
            con.BeginTrans
        Case CommitTrans
            con.CommitTrans
        Case RollbackTrans
            con.RollbackTrans
    End Select
    
    MDBTran = True
    Exit Function
    
Err_Transaction:

    Call LogWrite(Error, Err.Description)

End Function


