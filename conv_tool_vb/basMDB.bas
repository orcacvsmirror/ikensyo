Attribute VB_Name = "basDBMS_MDB"
Option Explicit

'�g�����U�N�V����
Public Enum geTransactionType
    BeginTrans = 1
    CommitTrans = 2
    RollbackTrans = 3
End Enum
'�J�[�\���^�C�v
Public Enum CursorTypeEnum
    adOpenForwardOnly = 0
    adOpenKeyset = 1
    adOpenDynamic = 2
    adOpenStatic = 3
End Enum
'���b�N
Public Enum LockTypeEnum
    adLockReadOnly = 1
    adLockPessimistic = 2
    adLockOptimistic = 3
    adLockBatchOptimistic = 4
End Enum
'�X�L�[�}
Public Enum SchemaEnum
    adSchemaTables = 20
    adSchemaColumns = 4
End Enum

Private con As Object

Public Function MDBConnection(pstrConnectFile As String) As Boolean
'============================================================================================
'�R�l�N�V�����ڑ�
'============================================================================================
On Error GoTo Err_Connection

        MDBConnection = False

        If Trim$(pstrConnectFile) = "" Then
            Exit Function
        End If
        
        If Trim$(Dir(pstrConnectFile)) = "" Then
            Exit Function
        End If

        'ADO�R�l�N�V�����I�u�W�F�N�g�̐���
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
'�R�l�N�V��������
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
'�X�L�[�}�擾
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
'���R�[�h�Z�b�g�擾
'============================================================================================
Dim rec As Object

On Error GoTo Err_OpenRecorSet
    
    '���R�[�h�Z�b�g�I�u�W�F�N�g�̐���
    Set rec = CreateObject("ADODB.Recordset")
    '���R�[�h�Z�b�g�̃I�[�v��
    rec.Open pstrSQL, con, plngCursorType, plngLockType
    '���R�[�h�Z�b�g��Ԃ�
    Set MDBExecuteRecordSet = rec
    
    Exit Function
        
Err_OpenRecorSet:

    Call LogWrite(Error, Err.Description & "," & pstrSQL)
    Set rec = Nothing

End Function

Public Function MDBExecuteSQL(ByVal pstrSQL As String) As Boolean
'============================================================================================
'SQL�����s����
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
'�g�����U�N�V�����ݒ�
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


