Attribute VB_Name = "basDBMS_FDB"
Option Explicit
Private con As Object

Public Function FDBConnection(pstrConnectFile As String, pstrUserId As String, psrtPassword As String) As Boolean
'============================================================================================
'�R�l�N�V�����ڑ�
'============================================================================================
On Error GoTo Err_Connection

        FDBConnection = False

        '�t�@�C���p�X�m�F
        If Trim$(pstrConnectFile) = "" Then
            Exit Function
        End If
        
        '�t�@�C�����݊m�F
        If Trim$(Dir(pstrConnectFile)) = "" Then
            Exit Function
        End If

        '�R�l�N�V�����I�[�v��
        Set con = CreateObject("ADODB.Connection")
        con.Open "Provider=ZStyle IBOLE Provider;Password=" & psrtPassword & ";User ID=" & pstrUserId & ";Data Source=" & pstrConnectFile & ";"
        
        FDBConnection = True
        
        Exit Function
        
Err_Connection:

    '���O�o��
    Call LogWrite(Error, Err.Description)
    
    Call FDBUnConnection

End Function

Public Function FDBUnConnection()
'============================================================================================
'�R�l�N�V��������
'============================================================================================
On Error GoTo Err_UnConnection
    
    con.Close
    Set con = Nothing
    
    Exit Function

Err_UnConnection:

    '���O�o��
    Call LogWrite(Error, Err.Description)

End Function

Public Function FDBExecuteRecordSet(ByVal pstrSQL As String, Optional ByVal plngCursorType As CursorTypeEnum = adOpenForwardOnly, Optional ByVal plngLockType As LockTypeEnum = adLockReadOnly) As Object
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
    Set FDBExecuteRecordSet = rec
    
    Exit Function
        
Err_OpenRecorSet:

    Call LogWrite(Error, Err.Description & "," & pstrSQL)
    Set rec = Nothing

End Function

Public Function FDBExecuteSQL(ByVal pstrSQL As String) As Boolean
'============================================================================================
'SQL�����s����
'============================================================================================

On Error GoTo Err_Execute

    FDBExecuteSQL = False
    
    'add start s-fujihara 2005/10/28
    '�s���A�s���ǂ̕������F�m�ǂɒu������
    pstrSQL = Replace$(pstrSQL, "�s����", "�F�m��")
    pstrSQL = Replace$(pstrSQL, "�s��", "�F�m��")
    'add end s-fujihara 2005/10/28
    'add start s-fujihara 2005/12/12
    '�ȉ��A�R�a�����ύX�Ώ�
    pstrSQL = Replace$(pstrSQL, "�p�[�L���\���a", "�p�[�L���\���a�֘A����")
    pstrSQL = Replace$(pstrSQL, "�V���C�E�h���[�K�[�ǌ�Q", "���n���ޏk��")
    pstrSQL = Replace$(pstrSQL, "�V���C�h���[�K�[�ǌ�Q", "���n���ޏk��")
    pstrSQL = Replace$(pstrSQL, "�����֐߃��E�}�`", "�֐߃��E�}�`")
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
'�g�����U�N�V�����ݒ�
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
