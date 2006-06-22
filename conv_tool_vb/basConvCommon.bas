Attribute VB_Name = "basConvCommon"
Option Explicit

Public Sub ConvRENKEII()
'============================================================================================
'RENKEII�ڍs�i�S���ʁj
'============================================================================================
Dim recRENKEII As Object
Dim recRENKEII_MAX As Object
Dim strSQL As String
    
    '�ڍs���f�[�^���擾
    Set recRENKEII = MDBExecuteRecordSet("SELECT * FROM �A�g��}�X�^ ORDER BY �A�g��R�[�h", adOpenForwardOnly, adLockReadOnly)
    Set recRENKEII_MAX = MDBExecuteRecordSet("SELECT MAX(�A�g��R�[�h) AS �A�g��R�[�h_�ő�l FROM �A�g��}�X�^", adOpenForwardOnly, adLockReadOnly)
    '�ڍs��f�[�^�̃g���K�[����U��~
    Call FDBExecuteSQL("ALTER TRIGGER SET_RENKEII_ID INACTIVE")
    
    '�ڍs��Ƀf�[�^��}��
    Do Until recRENKEII.EOF
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrSec
            Call FDBTran(BeginTrans)
            '2006/02/11[Shin Fujihara] : add end
            strSQL = ""
            strSQL = strSQL & "INSERT INTO RENKEII ("
            strSQL = strSQL & "RENKEII_CD,"
            strSQL = strSQL & "DR_NM,"
            strSQL = strSQL & "SINRYOUKA,"
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
            strSQL = strSQL & "LAST_TIME"
            strSQL = strSQL & ")"
            
            strSQL = strSQL & " VALUES ("
            strSQL = strSQL & recRENKEII("�A�g��R�[�h") & ","
            strSQL = strSQL & "'" & recRENKEII("��t��") & "',"
            strSQL = strSQL & "'" & recRENKEII("�f�É�") & "',"
            strSQL = strSQL & "'" & recRENKEII("��Ë@�֖�") & "',"
            strSQL = strSQL & "'" & AddHyphen(recRENKEII("��Ë@�֗X�֔ԍ�")) & "',"
            strSQL = strSQL & "'" & recRENKEII("��Ë@�֏Z��") & "',"
            strSQL = strSQL & "'" & recRENKEII("��Ë@�֓d�b�ǔ�") & "',"
            strSQL = strSQL & "'" & AddHyphen(recRENKEII("��Ë@�֓d�b")) & "',"
            strSQL = strSQL & "'" & recRENKEII("��Ë@��FAX�ǔ�") & "',"
            strSQL = strSQL & "'" & AddHyphen(recRENKEII("��Ë@��FAX")) & "',"
            strSQL = strSQL & "'" & recRENKEII("�g�єԍ���") & "',"
            strSQL = strSQL & "'" & AddHyphen(recRENKEII("�g�єԍ�")) & "',"
            strSQL = strSQL & "'" & recRENKEII("�ً}���A����") & "',"
            strSQL = strSQL & "'" & recRENKEII("�s�ݎ��Ή��@") & "',"
            strSQL = strSQL & "'" & recRENKEII("���l") & "',"
            strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
            strSQL = strSQL & ")"
            
            Call FDBExecuteSQL(strSQL)
            '2006/02/11[Shin Fujihara] : add begin
            Call FDBTran(CommitTrans)
        On Error GoTo 0
ErrSec:
        If Err.Number <> 0 Then
            'Debug.Print "���[���o�b�N"
            Call FDBTran(RollbackTrans)
        End If
        '2006/02/11[Shin Fujihara] : add end
        recRENKEII.MoveNext
        
    Loop
    
    '�ڍs��̎����̔ԏ����l��ݒ�
    If Not IsNull(recRENKEII_MAX("�A�g��R�[�h_�ő�l")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_RENKEII to " & recRENKEII_MAX("�A�g��R�[�h_�ő�l"))
    End If
    '�ڍs��f�[�^�̃g���K�[���J�n
    Call FDBExecuteSQL("ALTER TRIGGER SET_RENKEII_ID ACTIVE")
            
End Sub

Public Sub ConvSTATION()
'============================================================================================
'STATION�ڍs�i�S���ʁj
'============================================================================================
Dim recSTATION As Object
Dim recSTATION_MAX As Object
Dim strSQL As String
    
    '�ڍs���f�[�^���擾
    Set recSTATION = MDBExecuteRecordSet("SELECT * FROM �Ō�X�e�[�V�����}�X�^ ORDER BY �Ō�X�e�[�V�����R�[�h", adOpenForwardOnly, adLockReadOnly)
    Set recSTATION_MAX = MDBExecuteRecordSet("SELECT MAX(�Ō�X�e�[�V�����R�[�h) AS �Ō�X�e�[�V�����R�[�h_�ő�l FROM �Ō�X�e�[�V�����}�X�^", adOpenForwardOnly, adLockReadOnly)
    '�ڍs��f�[�^�̃g���K�[����U��~
    Call FDBExecuteSQL("ALTER TRIGGER SET_STATION_ID INACTIVE")
    
    '�ڍs��Ƀf�[�^��}��
    Do Until recSTATION.EOF
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrSec
            Call FDBTran(BeginTrans)
            '2006/02/11[Shin Fujihara] : add end
            strSQL = ""
            strSQL = strSQL & "INSERT INTO STATION ("
            strSQL = strSQL & "STATION_CD,"
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
            strSQL = strSQL & "LAST_TIME"
            strSQL = strSQL & ")"
            
            strSQL = strSQL & " VALUES ("
            strSQL = strSQL & recSTATION("�Ō�X�e�[�V�����R�[�h") & ","
            strSQL = strSQL & "'" & recSTATION("��t��") & "',"
            strSQL = strSQL & "'" & recSTATION("��Ë@�֖�") & "',"
            strSQL = strSQL & "'" & AddHyphen(recSTATION("��Ë@�֗X�֔ԍ�")) & "',"
            strSQL = strSQL & "'" & recSTATION("��Ë@�֏Z��") & "',"
            strSQL = strSQL & "'" & recSTATION("��Ë@�֓d�b�ǔ�") & "',"
            strSQL = strSQL & "'" & AddHyphen(recSTATION("��Ë@�֓d�b")) & "',"
            strSQL = strSQL & "'" & recSTATION("��Ë@��FAX�ǔ�") & "',"
            strSQL = strSQL & "'" & AddHyphen(recSTATION("��Ë@��FAX")) & "',"
            strSQL = strSQL & "'" & recSTATION("�g�єԍ���") & "',"
            strSQL = strSQL & "'" & AddHyphen(recSTATION("�g�єԍ�")) & "',"
            strSQL = strSQL & "'" & recSTATION("�ً}���A����") & "',"
            strSQL = strSQL & "'" & recSTATION("�s�ݎ��Ή��@") & "',"
            strSQL = strSQL & "'" & recSTATION("���l") & "',"
            strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
            strSQL = strSQL & ")"
            
            Call FDBExecuteSQL(strSQL)
            
            '2006/02/11[Shin Fujihara] : add begin
            Call FDBTran(CommitTrans)
        On Error GoTo 0
ErrSec:
        If Err.Number <> 0 Then
            'Debug.Print "���[���o�b�N"
            Call FDBTran(RollbackTrans)
        End If
        '2006/02/11[Shin Fujihara] : add end
        
        recSTATION.MoveNext
        
    Loop
                                                            
    '�ڍs��̎����̔ԏ����l��ݒ�
    If Not IsNull(recSTATION_MAX("�Ō�X�e�[�V�����R�[�h_�ő�l")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_STATION to " & recSTATION_MAX("�Ō�X�e�[�V�����R�[�h_�ő�l"))
    End If
    '�ڍs��f�[�^�̃g���K�[���J�n
    Call FDBExecuteSQL("ALTER TRIGGER SET_STATION_ID ACTIVE")
                                                            
End Sub

Public Function GetLogicalValue(pLogicalString As String) As Long
'============================================================================================
'�_���a�v�Z
'============================================================================================
Dim rtnLogicalValue As Long
Dim intCnt As Long
Dim colLogical As New Collection

    GetLogicalValue = 0
    
    For intCnt = 1 To Len(pLogicalString) Step 2
        colLogical.Add Mid(pLogicalString, intCnt, 2)
    Next intCnt
    
    rtnLogicalValue = 0
    For intCnt = 1 To colLogical.Count
        rtnLogicalValue = rtnLogicalValue + (2 ^ (Val(colLogical.Item(intCnt)) - 1))
    Next intCnt
    
    GetLogicalValue = rtnLogicalValue

End Function

Sub ConvIKN_ORIGIN(pintEda As Long, precIKENSYO As Object, precIKENSYO_KAIGO As Object, precIKENSYO_OTHER As Object, pintVer As DBVersion)
'============================================================================================
'IKN_ORIGIN�ڍs
'============================================================================================
Dim strSQL As String

    strSQL = ""
    strSQL = strSQL & "INSERT INTO IKN_ORIGIN ("
    strSQL = strSQL & "PATIENT_NO,"
    strSQL = strSQL & "EDA_NO,"
    '�t�H�[�}�b�g�������I��H17�ȍ~�ɂ���
    strSQL = strSQL & "FORMAT_KBN,"
    
    strSQL = strSQL & "DR_CONSENT,"
    strSQL = strSQL & "KINYU_DT,"
    strSQL = strSQL & "IKN_CREATE_CNT,"
    strSQL = strSQL & "LASTDAY,"
    strSQL = strSQL & "TAKA,"
    strSQL = strSQL & "TAKA_OTHER,"
    strSQL = strSQL & "TANKI_KIOKU,"
    strSQL = strSQL & "NINCHI,"
    strSQL = strSQL & "DENTATU,"
    strSQL = strSQL & "SHOKUJI,"
    strSQL = strSQL & "GNS_GNC,"
    strSQL = strSQL & "MOUSOU,"
    strSQL = strSQL & "CHUYA,"
    strSQL = strSQL & "BOUGEN,"
    strSQL = strSQL & "BOUKOU,"
    strSQL = strSQL & "TEIKOU,"
    strSQL = strSQL & "HAIKAI,"
    strSQL = strSQL & "FUSIMATU,"
    strSQL = strSQL & "FUKETU,"
    strSQL = strSQL & "ISHOKU,"
    strSQL = strSQL & "SEITEKI_MONDAI,"
    strSQL = strSQL & "MONDAI_OTHER,"
    strSQL = strSQL & "MONDAI_OTHER_NM,"
    strSQL = strSQL & "SEISIN,"
    strSQL = strSQL & "SEISIN_NM,"
    strSQL = strSQL & "SENMONI,"
    strSQL = strSQL & "SENMONI_NM,"
    strSQL = strSQL & "KIKIUDE,"
    strSQL = strSQL & "WEIGHT,"
    strSQL = strSQL & "HEIGHT,"
    strSQL = strSQL & "SISIKESSON,"
    strSQL = strSQL & "SISIKESSON_BUI,"
    strSQL = strSQL & "SISIKESSON_TEIDO,"
    strSQL = strSQL & "MAHI,"
    strSQL = strSQL & "MAHI_BUI,"
    strSQL = strSQL & "MAHI_TEIDO,"
    strSQL = strSQL & "KINRYOKU_TEIKA,"
    strSQL = strSQL & "KINRYOKU_TEIKA_BUI,"
    strSQL = strSQL & "KINRYOKU_TEIKA_TEIDO,"
    strSQL = strSQL & "JOKUSOU,"
    strSQL = strSQL & "JOKUSOU_BUI,"
    strSQL = strSQL & "JOKUSOU_TEIDO,"
    strSQL = strSQL & "HIFUSIKKAN,"
    strSQL = strSQL & "HIFUSIKKAN_BUI,"
    strSQL = strSQL & "HIFUSIKKAN_TEIDO,"
    strSQL = strSQL & "KATA_KOUSHU_MIGI,"
    strSQL = strSQL & "KATA_KOUSHU_HIDARI,"
    strSQL = strSQL & "HIJI_KOUSHU_MIGI,"
    strSQL = strSQL & "HIJI_KOUSHU_HIDARI,"
    strSQL = strSQL & "MATA_KOUSHU_MIGI,"
    strSQL = strSQL & "MATA_KOUSHU_HIDARI,"
    strSQL = strSQL & "HIZA_KOUSHU_MIGI,"
    strSQL = strSQL & "HIZA_KOUSHU_HIDARI,"
    strSQL = strSQL & "JOUSI_SICCHOU_MIGI,"
    strSQL = strSQL & "JOUSI_SICCHOU_HIDARI,"
    strSQL = strSQL & "KASI_SICCHOU_MIGI,"
    strSQL = strSQL & "KASI_SICCHOU_HIDARI,"
    strSQL = strSQL & "TAIKAN_SICCHOU_MIGI,"
    strSQL = strSQL & "TAIKAN_SICCHOU_HIDARI,"
    strSQL = strSQL & "NYOUSIKKIN,"
    strSQL = strSQL & "NYOUSIKKIN_TAISHO_HOUSIN,"
    strSQL = strSQL & "TENTOU_KOSSETU,"
    strSQL = strSQL & "TENTOU_KOSSETU_TAISHO_HOUSIN,"
    strSQL = strSQL & "HAIKAI_KANOUSEI,"
    strSQL = strSQL & "HAIKAI_KANOUSEI_TAISHO_HOUSIN,"
    strSQL = strSQL & "JOKUSOU_KANOUSEI,"
    strSQL = strSQL & "JOKUSOU_KANOUSEI_TAISHO_HOUSIN,"
    strSQL = strSQL & "ENGESEIHAIEN,"
    strSQL = strSQL & "ENGESEIHAIEN_TAISHO_HOUSIN,"
    strSQL = strSQL & "CHOUHEISOKU,"
    strSQL = strSQL & "CHOUHEISOKU_TAISHO_HOUSIN,"
    strSQL = strSQL & "EKIKANKANSEN,"
    strSQL = strSQL & "EKIKANKANSEN_TAISHO_HOUSIN,"
    strSQL = strSQL & "SINPAIKINOUTEIKA,"
    strSQL = strSQL & "SINPAIKINOUTEIKA_TAISHO_HOUSIN,"
    strSQL = strSQL & "ITAMI,"
    strSQL = strSQL & "ITAMI_TAISHO_HOUSIN,"
    strSQL = strSQL & "DASSUI,"
    strSQL = strSQL & "DASSUI_TAISHO_HOUSIN,"
    strSQL = strSQL & "BYOUTAITA,"
    strSQL = strSQL & "BYOUTAITA_TAISHO_HOUSIN,"
    strSQL = strSQL & "BYOUTAITA_NM,"
    strSQL = strSQL & "HOUMON_SINRYOU,"
    strSQL = strSQL & "HOUMON_SINRYOU_UL,"
    strSQL = strSQL & "HOUMON_KANGO,"
    strSQL = strSQL & "HOUMON_KANGO_UL,"
    strSQL = strSQL & "HOUMON_REHA,"
    strSQL = strSQL & "HOUMON_REHA_UL,"
    strSQL = strSQL & "TUUSHO_REHA,"
    strSQL = strSQL & "TUUSHO_REHA_UL,"
    strSQL = strSQL & "TANKI_NYUSHO_RYOUYOU,"
    strSQL = strSQL & "TANKI_NYUSHO_RYOUYOU_UL,"
    strSQL = strSQL & "HOUMONSIKA_SINRYOU,"
    strSQL = strSQL & "HOUMONSIKA_SINRYOU_UL,"
    strSQL = strSQL & "HOUMONSIKA_EISEISIDOU,"
    strSQL = strSQL & "HOUMONSIKA_EISEISIDOU_UL,"
    strSQL = strSQL & "HOUMONYAKUZAI_KANRISIDOU,"
    strSQL = strSQL & "HOUMONYAKUZAI_KANRISIDOU_UL,"
    strSQL = strSQL & "HOUMONEIYOU_SHOKUJISIDOU,"
    strSQL = strSQL & "HOUMONEIYOU_SHOKUJISIDOU_UL,"
    strSQL = strSQL & "IGAKUTEKIKANRI_OTHER,"
    strSQL = strSQL & "IGAKUTEKIKANRI_OTHER_UL,"
    strSQL = strSQL & "IGAKUTEKIKANRI_OTHER_NM,"
    strSQL = strSQL & "KETUATU,"
    strSQL = strSQL & "KETUATU_RYUIJIKOU,"
    strSQL = strSQL & "SESHOKU,"
    strSQL = strSQL & "SESHOKU_RYUIJIKOU,"
    strSQL = strSQL & "ENGE,"
    strSQL = strSQL & "ENGE_RYUIJIKOU,"
    strSQL = strSQL & "IDOU,"
    strSQL = strSQL & "IDOU_RYUIJIKOU,"
    strSQL = strSQL & "KAIGO_OTHER,"
    strSQL = strSQL & "KANSENSHOU,"
    strSQL = strSQL & "KANSENSHOU_NM,"
    strSQL = strSQL & "IKN_TOKKI,"
    strSQL = strSQL & "HASE_SCORE,"
    strSQL = strSQL & "HASE_SCR_DT,"
    strSQL = strSQL & "P_HASE_SCORE,"
    strSQL = strSQL & "P_HASE_SCR_DT,"
    strSQL = strSQL & "INST_SEL_PR1,"
    strSQL = strSQL & "INST_SEL_PR2,"
    If Not IsNull(precIKENSYO_OTHER("�S�g�}")) Then
        strSQL = strSQL & "BODY_FIGURE,"
    End If
    strSQL = strSQL & "INSURED_NO,"
    If Not Trim$(precIKENSYO_OTHER("�쐬�˗���")) = "" Then
        strSQL = strSQL & "REQ_DT,"
    End If
    strSQL = strSQL & "REQ_NO,"
    If Not Trim$(precIKENSYO_OTHER("���t��")) = "" Then
        strSQL = strSQL & "SEND_DT,"
    End If
    strSQL = strSQL & "KIND,"
    strSQL = strSQL & "INSURER_NO,"
    Select Case pintVer
        Case DBVersion.ver15, DBVersion.VER153, DBVersion.VER161
        strSQL = strSQL & "INSURER_NM,"
    End Select
    strSQL = strSQL & "CREATE_DT,"
    strSQL = strSQL & "KOUSIN_DT,"
    
    'csv�t�@�C���o�͂ŃG���[�ɂȂ�̂ŏ����l��ݒ�
    strSQL = strSQL & "ASSISTANCE_TOOL,"
    
    strSQL = strSQL & "LAST_TIME"
    strSQL = strSQL & ")"

    strSQL = strSQL & " VALUES ("
    strSQL = strSQL & precIKENSYO("���Ҕԍ�") & ","
    strSQL = strSQL & pintEda & ","
    '�t�H�[�}�b�g�敪�������I��H17�ȍ~�ɂ���
    strSQL = strSQL & "1,"
    
    strSQL = strSQL & precIKENSYO_OTHER("��t����") & ","
    strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("�L����"), "yyyy/mm/dd") & "',"
    strSQL = strSQL & precIKENSYO_OTHER("�ӌ����쐬��") & ","
    If Not Trim$(precIKENSYO("�ŏI�f�@��")) = "0000�N00��00��" Then
        strSQL = strSQL & "'" & Format(precIKENSYO("�ŏI�f�@��"), "yyyy/mm/dd") & "',"
    Else
        strSQL = strSQL & "Null,"
    End If
    strSQL = strSQL & GetLogicalValue(precIKENSYO("���Ȏ�f")) & ","
    strSQL = strSQL & "'" & precIKENSYO("���Ȏ�f���̑�") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("�Z���L��") & ","
    strSQL = strSQL & precIKENSYO_KAIGO("�F�m�\��") & ","
    strSQL = strSQL & precIKENSYO_KAIGO("�`�B�\��") & ","
    strSQL = strSQL & precIKENSYO_KAIGO("�H��") & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("��������"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�ϑz"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("����t�]"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�\��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�\�s"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("��R"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�p�j"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�s�n��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�s���s��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�ِH�s��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("���I���s��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("���s����"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("���s������") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("���_�_�o����") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("���_�_�o������") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("����") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("���㖼") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("�����r") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�̏d") & "',"
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�g��") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�l������"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�l����������") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("�l���������x") & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("���"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("��ვ���") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("��გ��x") & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�ؗ͒ቺ"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�ؗ͒ቺ����") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("�ؗ͒ቺ���x") & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("���"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("��ጕ���") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("��ጒ��x") & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�畆����"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�畆��������") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�畆�������x"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("���֐ߍS�k�E"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("���֐ߍS�k��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�I�֐ߍS�k�E"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�I�֐ߍS�k��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�Ҋ֐ߍS�k�E"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�Ҋ֐ߍS�k��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�G�֐ߍS�k�E"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�G�֐ߍS�k��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�㎈�����E"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�㎈������"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("���������E"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("����������"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�̊������E"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�̊�������"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�A����"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�A���֑Ώ����j") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�]�|����"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�]�|���ܑΏ����j") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�p�j�\��"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�p�j�\���Ώ����j") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("��ጉ\��"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("��ጉ\���Ώ����j") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�������x��"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�������x���Ώ����j") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("����"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("���ǑΏ����j") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�Պ�����"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�Պ������Ώ����j") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�S�x�@�\�ቺ"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�S�x�@�\�ቺ�Ώ����j") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�ɂ�"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�ɂݑΏ����j") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�E��"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�E���Ώ����j") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�a�ԑ�"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�a�ԑ��Ώ����j") & "',"
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�a�ԑ���") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K��f��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K��f�É���"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K��Ō�"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K��Ō쉺��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K�⃊�n�r���e�[�V����"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K�⃊�n�r���e�[�V��������"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�ʏ����n�r���e�[�V����"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�ʏ����n�r���e�[�V��������"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�Z�������×{���"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�Z�������×{��쉺��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K�⎕�Ȑf��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K�⎕�Ȑf�É���"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K�⎕�ȉq���w��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K�⎕�ȉq���w������"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K���܊Ǘ��w��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K���܊Ǘ��w������"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K��h�{�H���w��"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("�K��h�{�H���w������"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("��w�I�Ǘ���"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("��w�I�Ǘ�������"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("��w�I�Ǘ�����") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("��쌌��") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("��쌌�����ӎ���") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("���ېH") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("���ېH���ӎ���") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("��욋��") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("��욋�����ӎ���") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("���ړ�") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("���ړ����ӎ���") & "',"
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("��쑼") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("������") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("�����ǖ�") & "',"
    strSQL = strSQL & "'" & precIKENSYO_OTHER("�ӌ������L����") & "',"
    strSQL = strSQL & "'" & precIKENSYO_OTHER("���J�쎮�_��") & "',"
    If Trim$(precIKENSYO_OTHER("���J�쎮�_�����t")) = "0000�N00��" Then
        strSQL = strSQL & "'0000�N00��00��',"
    Else
        If Mid(Trim$(precIKENSYO_OTHER("���J�쎮�_�����t")), 6, 2) = "00" Then
            strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("���J�쎮�_�����t"), "gggee�N00��00��") & "',"
        Else
            strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("���J�쎮�_�����t"), "gggee�Nmm��00��") & "',"
        End If
    End If
    strSQL = strSQL & "'" & precIKENSYO_OTHER("���J�쎮�O��_��") & "',"
    
    If Trim$(precIKENSYO_OTHER("���J�쎮�O��_�����t")) = "0000�N00��" Then
        strSQL = strSQL & "'0000�N00��00��',"
    Else
        If Mid(Trim$(precIKENSYO_OTHER("���J�쎮�O��_�����t")), 6, 2) = "00" Then
            strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("���J�쎮�O��_�����t"), "gggee�N00��00��") & "',"
        Else
            strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("���J�쎮�O��_�����t"), "gggee�Nmm��00��") & "',"
        End If
    End If
    
    strSQL = strSQL & "'" & precIKENSYO_OTHER("�{�ݑI��D��x1") & "',"
    strSQL = strSQL & "'" & precIKENSYO_OTHER("�{�ݑI��D��x2") & "',"
    If Not IsNull(precIKENSYO_OTHER("�S�g�}")) Then
        strSQL = strSQL & precIKENSYO_OTHER("�S�g�}") & ","
    End If
    strSQL = strSQL & "'" & precIKENSYO_OTHER("��ی��Ҕԍ�") & "',"
    If Not Trim$(precIKENSYO_OTHER("�쐬�˗���")) = "" Then
        strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("�쐬�˗���"), "yyyy/mm/dd") & "',"
    End If
    strSQL = strSQL & "'" & precIKENSYO_OTHER("�˗��ԍ�") & "',"
    If Not Trim$(precIKENSYO_OTHER("���t��")) = "" Then
        strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("���t��"), "yyyy/mm/dd") & "',"
    End If
    strSQL = strSQL & precIKENSYO_OTHER("���") & ","
    strSQL = strSQL & "'" & precIKENSYO_OTHER("�ی��Ҕԍ�") & "',"
    Select Case pintVer
        Case DBVersion.ver15, DBVersion.VER153, DBVersion.VER161
        strSQL = strSQL & "'" & precIKENSYO_OTHER("�ی��Җ���") & "',"
    End Select
    strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("�V�K�쐬��"), "yyyy/mm/dd hh:nn:ss") & "',"
    strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("�X�V��"), "yyyy/mm/dd hh:nn:ss") & "',"
    
    'csv�t�@�C���o�͂ŃG���[�ɂȂ�̂ŏ����l��ݒ�
    strSQL = strSQL & "0,"
    
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"

    '�ڍs�������s
    Call FDBExecuteSQL(strSQL)

End Sub

Sub ConvCOMMON_IKN_SIS(pintEda As Long, precMAIN As Object, precMAIN_OTHER As Object, pintMode As DocKbn)
'============================================================================================
'COMMON_IKN_SIS�ڍs�i�S���ʁj
'============================================================================================
Dim strSQL As String

    strSQL = ""
    strSQL = strSQL & "INSERT INTO COMMON_IKN_SIS ("
    strSQL = strSQL & "PATIENT_NO,"
    strSQL = strSQL & "EDA_NO,"
    strSQL = strSQL & "DOC_KBN,"
    strSQL = strSQL & "PATIENT_NM,"
    strSQL = strSQL & "PATIENT_KN,"
    strSQL = strSQL & "SEX,"
    strSQL = strSQL & "BIRTHDAY,"
    strSQL = strSQL & "AGE,"
    strSQL = strSQL & "POST_CD,"
    strSQL = strSQL & "ADDRESS,"
    strSQL = strSQL & "TEL1,"
    strSQL = strSQL & "TEL2,"
    strSQL = strSQL & "SINDAN_NM1,"
    strSQL = strSQL & "SINDAN_NM2,"
    strSQL = strSQL & "SINDAN_NM3,"
    strSQL = strSQL & "HASHOU_DT1,"
    strSQL = strSQL & "HASHOU_DT2,"
    strSQL = strSQL & "HASHOU_DT3,"
    strSQL = strSQL & "MT_STS,"
    strSQL = strSQL & "MT_STS_OTHER,"
    strSQL = strSQL & "MEDICINE1,"
    strSQL = strSQL & "DOSAGE1,"
    strSQL = strSQL & "UNIT1,"
    strSQL = strSQL & "USAGE1,"
    strSQL = strSQL & "MEDICINE2,"
    strSQL = strSQL & "DOSAGE2,"
    strSQL = strSQL & "UNIT2,"
    strSQL = strSQL & "USAGE2,"
    strSQL = strSQL & "MEDICINE3,"
    strSQL = strSQL & "DOSAGE3,"
    strSQL = strSQL & "UNIT3,"
    strSQL = strSQL & "USAGE3,"
    strSQL = strSQL & "MEDICINE4,"
    strSQL = strSQL & "DOSAGE4,"
    strSQL = strSQL & "UNIT4,"
    strSQL = strSQL & "USAGE4,"
    strSQL = strSQL & "MEDICINE5,"
    strSQL = strSQL & "DOSAGE5,"
    strSQL = strSQL & "UNIT5,"
    strSQL = strSQL & "USAGE5,"
    strSQL = strSQL & "MEDICINE6,"
    strSQL = strSQL & "DOSAGE6,"
    strSQL = strSQL & "UNIT6,"
    strSQL = strSQL & "USAGE6,"
    strSQL = strSQL & "NETAKIRI,"
    strSQL = strSQL & "CHH_STS,"
    strSQL = strSQL & "SHJ_ANT,"
    strSQL = strSQL & "YKG_YOGO,"
    strSQL = strSQL & "TNT_KNR,"
    strSQL = strSQL & "YUEKI_PUMP,"
    strSQL = strSQL & "CHU_JOU_EIYOU,"
    strSQL = strSQL & "TOUSEKI,"
    strSQL = strSQL & "JD_FUKU,"
    strSQL = strSQL & "TOU_KYOUKYU,"
    strSQL = strSQL & "JINKOU_KOUMON,"
    strSQL = strSQL & "JINKOU_BOUKOU,"
    strSQL = strSQL & "OX_RYO,"
    strSQL = strSQL & "OX_RYO_RYO,"
    strSQL = strSQL & "JINKOU_KOKYU,"
    strSQL = strSQL & "JINKOU_KKY_HOUSIKI,"
    strSQL = strSQL & "JINKOU_KKY_SET,"
    strSQL = strSQL & "CANNULA,"
    strSQL = strSQL & "CANNULA_SIZE,"
    strSQL = strSQL & "KYUINKI,"
    strSQL = strSQL & "KKN_SEK_SHOCHI,"
    strSQL = strSQL & "KEKN_EIYOU,"
    strSQL = strSQL & "KEKN_EIYOU_METHOD,"
    strSQL = strSQL & "KEKN_EIYOU_SIZE,"
    strSQL = strSQL & "KEKN_EIYOU_CHG,"
    strSQL = strSQL & "CATHETER,"
    strSQL = strSQL & "RYU_CATHETER,"
    strSQL = strSQL & "RYU_CAT_SIZE,"
    strSQL = strSQL & "RYU_CAT_CHG,"
    strSQL = strSQL & "DOREN,"
    strSQL = strSQL & "DOREN_BUI,"
    strSQL = strSQL & "MONITOR,"
    strSQL = strSQL & "TOUTU,"
    strSQL = strSQL & "JOKUSOU_SHOCHI,"
    strSQL = strSQL & "SOUCHAKU_OTHER,"
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
    strSQL = strSQL & "LAST_TIME"
    strSQL = strSQL & ")"
    
    strSQL = strSQL & " VALUES ("
    strSQL = strSQL & precMAIN("���Ҕԍ�") & ","
    strSQL = strSQL & pintEda & ","
    strSQL = strSQL & pintMode & ","
    strSQL = strSQL & "'" & precMAIN("���Җ�") & "',"
    strSQL = strSQL & "'" & precMAIN("���Җ�����") & "',"
    strSQL = strSQL & precMAIN("����") & ","
    strSQL = strSQL & "'" & Format(precMAIN("���N����"), "yyyy/mm/dd") & "',"
    strSQL = strSQL & "'" & precMAIN("�N��") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN("�X�֔ԍ�")) & "',"
    strSQL = strSQL & "'" & precMAIN("�Z��") & "',"
    strSQL = strSQL & "'" & precMAIN("�d�b�ǔ�") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN("�d�b")) & "',"
    strSQL = strSQL & "'" & precMAIN("�f�f��1") & "',"
    strSQL = strSQL & "'" & precMAIN("�f�f��2") & "',"
    strSQL = strSQL & "'" & precMAIN("�f�f��3") & "',"
    strSQL = strSQL & "'" & precMAIN("���ǔN����1") & "',"
    strSQL = strSQL & "'" & precMAIN("���ǔN����2") & "',"
    strSQL = strSQL & "'" & precMAIN("���ǔN����3") & "',"
    strSQL = strSQL & "'" & precMAIN("�a�󎡗Ï��") & "',"
    strSQL = strSQL & "'" & precMAIN("�a�󎡗Ï�ԑ�") & "',"
    strSQL = strSQL & "'" & precMAIN("��ܖ�1") & "',"
    strSQL = strSQL & "'" & precMAIN("�p��1") & "',"
    strSQL = strSQL & "'" & precMAIN("�P��1") & "',"
    strSQL = strSQL & "'" & precMAIN("�p�@1") & "',"
    strSQL = strSQL & "'" & precMAIN("��ܖ�2") & "',"
    strSQL = strSQL & "'" & precMAIN("�p��2") & "',"
    strSQL = strSQL & "'" & precMAIN("�P��2") & "',"
    strSQL = strSQL & "'" & precMAIN("�p�@2") & "',"
    strSQL = strSQL & "'" & precMAIN("��ܖ�3") & "',"
    strSQL = strSQL & "'" & precMAIN("�p��3") & "',"
    strSQL = strSQL & "'" & precMAIN("�P��3") & "',"
    strSQL = strSQL & "'" & precMAIN("�p�@3") & "',"
    strSQL = strSQL & "'" & precMAIN("��ܖ�4") & "',"
    strSQL = strSQL & "'" & precMAIN("�p��4") & "',"
    strSQL = strSQL & "'" & precMAIN("�P��4") & "',"
    strSQL = strSQL & "'" & precMAIN("�p�@4") & "',"
    strSQL = strSQL & "'" & precMAIN("��ܖ�5") & "',"
    strSQL = strSQL & "'" & precMAIN("�p��5") & "',"
    strSQL = strSQL & "'" & precMAIN("�P��5") & "',"
    strSQL = strSQL & "'" & precMAIN("�p�@5") & "',"
    strSQL = strSQL & "'" & precMAIN("��ܖ�6") & "',"
    strSQL = strSQL & "'" & precMAIN("�p��6") & "',"
    strSQL = strSQL & "'" & precMAIN("�P��6") & "',"
    strSQL = strSQL & "'" & precMAIN("�p�@6") & "',"
    strSQL = strSQL & precMAIN("�Q������x") & ","
    strSQL = strSQL & precMAIN("�s����") & ","
    strSQL = strSQL & precMAIN("�Ǐ���萫") & ","
    strSQL = strSQL & precMAIN("�v����ԗ\��") & ","
    strSQL = strSQL & Abs(CInt(precMAIN("�_�H�Ǘ�"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("�A�t�|���v"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("���S�Ö��h�{"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("����"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("���������󗬑��u"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("���͉t�������u"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("�l�H���"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("�l�H�N��"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("�_�f�Ö@"))) & ","
    strSQL = strSQL & "'" & precMAIN("�_�f�Ö@��") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("�l�H�ċz��"))) & ","
    strSQL = strSQL & "'" & precMAIN("�l�H�ċz�����") & "',"
    strSQL = strSQL & "'" & precMAIN("�l�H�ċz��ݒ�") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("�C�ǃJ�j���[��"))) & ","
    strSQL = strSQL & "'" & precMAIN("�C�ǃJ�j���[���T�C�Y") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("�z����"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("�C�ǐ؊J���u"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("�o�ǉh�{"))) & ","
    strSQL = strSQL & "'" & precMAIN("�o�ǉh�{���@") & "',"
    strSQL = strSQL & "'" & precMAIN("�o�ǉh�{�T�C�Y") & "',"
    strSQL = strSQL & "'" & precMAIN("�o�ǉh�{����") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("�J�e�[�e��"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("���u�J�e�[�e��"))) & ","
    strSQL = strSQL & "'" & precMAIN("���u�J�e�[�e���T�C�Y") & "',"
    strSQL = strSQL & "'" & precMAIN("���u�J�e�[�e������") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("�h���[��"))) & ","
    strSQL = strSQL & "'" & precMAIN("�h���[������") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("���j�^�[����"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("�u�ɊŌ�"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("��n���u"))) & ","
    strSQL = strSQL & "'" & precMAIN("������Ë@�푼") & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("��t��") & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("��Ë@�֖�") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN_OTHER("��Ë@�֗X�֔ԍ�")) & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("��Ë@�֏Z��") & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("��Ë@�֓d�b�ǔ�") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN_OTHER("��Ë@�֓d�b")) & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("��Ë@��FAX�ǔ�") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN_OTHER("��Ë@��FAX")) & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("�g�єԍ���") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN_OTHER("�g�єԍ�")) & "',"
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"
    
    '�ڍs�������s
    Call FDBExecuteSQL(strSQL)

End Sub

Sub ConvGRAPHICS_COMMAND(pintEda As Long, precIKENSYO As Object, precIKENSYO_GRAPHIC As Object)
'============================================================================================
'GRAPHICS_COMMAND�ڍs�i�S���ʁj
'============================================================================================
Dim strSQL As String

    Do Until precIKENSYO_GRAPHIC.EOF

        strSQL = ""
        strSQL = strSQL & "INSERT INTO GRAPHICS_COMMAND ("
        strSQL = strSQL & "PATIENT_NO,"
        strSQL = strSQL & "EDA_NO,"
        strSQL = strSQL & "CMD_NO,"
        strSQL = strSQL & "X,"
        strSQL = strSQL & "Y,"
        strSQL = strSQL & "BUI,"
        strSQL = strSQL & "SX,"
        strSQL = strSQL & "SY,"
        strSQL = strSQL & "STRING,"
        strSQL = strSQL & "LAST_TIME"
        strSQL = strSQL & ")"
    
        strSQL = strSQL & " VALUES ("
        strSQL = strSQL & precIKENSYO("���Ҕԍ�") & ","
        strSQL = strSQL & pintEda & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("�R�}���h�ԍ�") & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("X���W") & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("Y���W") & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("����") & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("SX���W") & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("SY���W") & ","
        strSQL = strSQL & "'" & precIKENSYO_GRAPHIC("������") & "',"
        strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
        strSQL = strSQL & ")"
    
        '�ڍs�������s
        Call FDBExecuteSQL(strSQL)
        
        precIKENSYO_GRAPHIC.MoveNext
        
    Loop

End Sub

Function AddHyphen(pstrValue As String) As String
'============================================================================================
'�X�֔ԍ��E�d�b�ԍ��Ƀn�C�t����t������
'============================================================================================
    AddHyphen = pstrValue

    On Error GoTo Error_Section
        If (Len(Trim$(pstrValue)) <> 0) And (InStr(pstrValue, "-") = 0) Then
            AddHyphen = "-" & pstrValue
        End If
    On Error GoTo 0

Exit Function
Error_Section:
    AddHyphen = pstrValue
End Function

Function ReplaceFDBFile(ByVal pstrFilePath As String) As Boolean
'============================================================================================
'�w�肳�ꂽFDB�t�@�C�����f�[�^�̓����Ă��Ȃ�FDB�t�@�C���ƒu������
'============================================================================================
Dim strPath As String
Dim strFileName As String
Dim strZeroFile As String

    ReplaceFDBFile = False
    
    strZeroFile = App.Path & "\CONVERT.DAT"
    
    '�R�s�[���̃t�@�C�������邩�`�F�b�N
    If Dir(strZeroFile) = "" Then
        Call LogWrite(Error, "CONVERT.DAT��������܂���B")
        Exit Function
    End If

    On Error GoTo ERR_SECTION
        strPath = Left$(pstrFilePath, InStrRev(pstrFilePath, "\"))
        strFileName = Dir(pstrFilePath)
        strFileName = Left$(strFileName, InStrRev(strFileName, ".") - 1)
        
        '�t�@�C���̃o�b�N�A�b�v���쐬
        Name pstrFilePath As strPath & GetBackupFileName(strPath, 0)
        '�󔒂̃f�[�^�x�[�X�t�@�C�����R�s�[
        Call FileCopy(strZeroFile, pstrFilePath)
    
        ReplaceFDBFile = True
    On Error GoTo 0

ERR_SECTION:
End Function

Function GetBackupFileName(pstrPath As String, pintCount) As String
'============================================================================================
'�ޔ�p�̃t�@�C�������擾����
'============================================================================================
Dim strFilaName As String

    If pintCount = 0 Then
        strFilaName = "convert.old"
    Else
        strFilaName = "convert" & CStr(pintCount) & ".old"
    End If
    
    '�����t�@�C�������݂���
    If Dir(pstrPath & strFilaName) <> "" Then
        GetBackupFileName = GetBackupFileName(pstrPath, pintCount + 1)
    Else
        GetBackupFileName = strFilaName
    End If

End Function

