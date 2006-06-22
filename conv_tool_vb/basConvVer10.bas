Attribute VB_Name = "basConvVer10"
Option Explicit

Public Sub ConvIKENSYO10()
'============================================================================================
'意見書情報移行
'============================================================================================
Dim recPAIENT As Object
Dim recPAIENT_MAX As Object
Dim recIKENSYO As Object
Dim recIKENSYO_KAIGO As Object
Dim recIKENSYO_OTHER As Object
Dim recIKENSYO_GRAPHIC As Object
Dim recSIS As Object
Dim recSIS_OTHER As Object
Dim strSQL As String
Dim intEda As Long

    '最新基本情報テーブルの取得
    Set recPAIENT = MDBExecuteRecordSet("SELECT * FROM 最新基本情報 ORDER BY 患者番号", adOpenForwardOnly, adLockReadOnly)
    Set recPAIENT_MAX = MDBExecuteRecordSet("SELECT MAX(患者番号) AS 患者番号_最大値 FROM 最新基本情報", adOpenForwardOnly, adLockReadOnly)
    
    '最新基本情報テーブルのトリガーを一旦停止
    Call FDBExecuteSQL("ALTER TRIGGER SET_PATIENT_ID INACTIVE")
    
    '最新基本情報数分処理を回す
    Do Until recPAIENT.EOF
    
        '============================================================================================
        '意見書
        '============================================================================================
        '意見書基本情報テーブルの取得
        Set recIKENSYO = MDBExecuteRecordSet("SELECT * FROM 意見書基本情報 WHERE (患者番号 = " & recPAIENT("患者番号") & ") ORDER BY 患者番号,作成NO DESC", adOpenForwardOnly, adLockReadOnly)
    
        If recIKENSYO.EOF = False Then
            '枝番初期化
            intEda = 1
            '意見書心身と介護の情報テーブルの取得
            Set recIKENSYO_KAIGO = MDBExecuteRecordSet("SELECT * FROM 意見書心身と介護の情報 WHERE (作成NO = " & recIKENSYO("作成NO") & ")", adOpenForwardOnly, adLockReadOnly)
            '意見書その他テーブルの取得
            Set recIKENSYO_OTHER = MDBExecuteRecordSet("SELECT * FROM 意見書その他 WHERE (患者番号 = " & recIKENSYO("患者番号") & ") AND (作成NO = " & recIKENSYO("作成NO") & ")", adOpenForwardOnly, adLockReadOnly)
            '全身グラフィックコマンドテーブルの取得
            Set recIKENSYO_GRAPHIC = MDBExecuteRecordSet("SELECT * FROM 全身図グラフィックコマンド WHERE (患者番号 = " & recIKENSYO("患者番号") & ") AND (作成NO = " & recIKENSYO("作成NO") & ")", adOpenForwardOnly, adLockReadOnly)
            'COMMON_IKN_SIS移行処理
            Call ConvCOMMON_IKN_SIS(intEda, recIKENSYO, recIKENSYO_OTHER, IKEN)
            'IKN_ORIGIN移行処理
            Call ConvIKN_ORIGIN(intEda, recIKENSYO, recIKENSYO_KAIGO, recIKENSYO_OTHER, VER10)
            'GRAPHICS_COMMAND移行処理
            Call ConvGRAPHICS_COMMAND(intEda, recIKENSYO, recIKENSYO_GRAPHIC)
        End If
        
        '============================================================================================
        '指示書
        '============================================================================================
        '指示書基本情報テーブルの取得
        Set recSIS = MDBExecuteRecordSet("SELECT * FROM 指示書基本情報 WHERE (患者番号 = " & recPAIENT("患者番号") & ") ORDER BY 患者番号,作成NO DESC", adOpenForwardOnly, adLockReadOnly)
        
        If recSIS.EOF = False Then
            '枝番初期化
            intEda = 1
            '指示書その他テーブルの取得
            Set recSIS_OTHER = MDBExecuteRecordSet("SELECT * FROM 指示書その他 WHERE (患者番号 = " & recSIS("患者番号") & ") AND (作成NO = " & recSIS("作成NO") & ")", adOpenForwardOnly, adLockReadOnly)
            'COMMON_IKN_SIS移行処理
            Call ConvCOMMON_IKN_SIS(intEda, recSIS, recSIS_OTHER, SIS)
            'SIS_ORIGIN移行処理
            Call ConvSIS_ORIGIN10(intEda, recSIS_OTHER)
        End If
        
        '最新基本情報テーブルを移行
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
        strSQL = strSQL & recPAIENT("患者番号") & ","
        strSQL = strSQL & "'" & recPAIENT("カルテ番号") & "',"
        strSQL = strSQL & "'" & recPAIENT("患者名") & "',"
        strSQL = strSQL & "'" & recPAIENT("患者名かな") & "',"
        strSQL = strSQL & recPAIENT("性別") & ","
        strSQL = strSQL & "'" & Format(recPAIENT("生年月日"), "yyyy/mm/dd") & "',"
        strSQL = strSQL & "'" & recPAIENT("年齢") & "',"
        strSQL = strSQL & "'" & AddHyphen(recPAIENT("郵便番号")) & "',"
        strSQL = strSQL & "'" & recPAIENT("住所") & "',"
        strSQL = strSQL & "'" & recPAIENT("電話局番") & "',"
        strSQL = strSQL & "'" & AddHyphen(recPAIENT("電話")) & "',"
        strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
        strSQL = strSQL & ")"
        
        Call FDBExecuteSQL(strSQL)
    
        recPAIENT.MoveNext
    
    Loop
    
    '最新基本情報テーブルの自動採番初期値を設定
    If Not IsNull(recPAIENT_MAX("患者番号_最大値")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_PATIENT to " & recPAIENT_MAX("患者番号_最大値"))
    End If
    '最新基本情報テーブルのトリガーを開始
    Call FDBExecuteSQL("ALTER TRIGGER SET_PATIENT_ID ACTIVE")

End Sub

Public Sub ConvDOCTOR10()
'============================================================================================
'DOCTOR移行（Ver1.0専用）
'============================================================================================
Dim recDOCTOR As Object
Dim recDOCTOR_MAX As Object
Dim strSQL As String
    
    '移行元データを取得
    Set recDOCTOR = MDBExecuteRecordSet("SELECT * FROM 医師マスタ ORDER BY 医師コード", adOpenForwardOnly, adLockReadOnly)
    Set recDOCTOR_MAX = MDBExecuteRecordSet("SELECT MAX(医師コード) AS 医師コード_最大値 FROM 医師マスタ", adOpenForwardOnly, adLockReadOnly)
    '移行先データのトリガーを一旦停止
    Call FDBExecuteSQL("ALTER TRIGGER SET_DOCTOR_ID INACTIVE")
    
    '移行先にデータを挿入
    Do Until recDOCTOR.EOF
                    
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
        strSQL = strSQL & "LAST_TIME"
        strSQL = strSQL & ")"
        
        strSQL = strSQL & " VALUES ("
        strSQL = strSQL & recDOCTOR("医師コード") & ","
        strSQL = strSQL & "'" & recDOCTOR("医師名") & "',"
        strSQL = strSQL & "'" & recDOCTOR("医療機関名") & "',"
        strSQL = strSQL & "'" & AddHyphen(recDOCTOR("医療機関郵便番号")) & "',"
        strSQL = strSQL & "'" & recDOCTOR("医療機関住所") & "',"
        strSQL = strSQL & "'" & recDOCTOR("医療機関電話局番") & "',"
        strSQL = strSQL & "'" & AddHyphen(recDOCTOR("医療機関電話")) & "',"
        strSQL = strSQL & "'" & recDOCTOR("医療機関FAX局番") & "',"
        strSQL = strSQL & "'" & AddHyphen(recDOCTOR("医療機関FAX")) & "',"
        strSQL = strSQL & "'" & recDOCTOR("携帯番号上") & "',"
        strSQL = strSQL & "'" & AddHyphen(recDOCTOR("携帯番号")) & "',"
        strSQL = strSQL & "'" & recDOCTOR("緊急時連絡先") & "',"
        strSQL = strSQL & "'" & recDOCTOR("不在時対応法") & "',"
        strSQL = strSQL & "'" & recDOCTOR("備考") & "',"
        strSQL = strSQL & "'" & recDOCTOR("事業者番号") & "',"
        strSQL = strSQL & Abs(CInt(recDOCTOR("医療機関デフォルト"))) & ","
        strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
        strSQL = strSQL & ")"
        
        Call FDBExecuteSQL(strSQL)
        
        recDOCTOR.MoveNext
        
    Loop
                                                          
    '移行先の自動採番初期値を設定
    If Not IsNull(recDOCTOR_MAX("医師コード_最大値")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_DOCTOR to " & recDOCTOR_MAX("医師コード_最大値"))
    End If
    '移行先データのトリガーを開始
    Call FDBExecuteSQL("ALTER TRIGGER SET_DOCTOR_ID ACTIVE")
                                                            
End Sub

Sub ConvSIS_ORIGIN10(pintEda As Long, precSIS_OTHER As Object)
'============================================================================================
'SIS_ORIGIN移行（Ver1.0専用）
'============================================================================================
Dim strSQL As String

    strSQL = ""
    strSQL = strSQL & "INSERT INTO SIS_ORIGIN ("
    strSQL = strSQL & "PATIENT_NO,"
    strSQL = strSQL & "EDA_NO,"
    If Not Trim$(precSIS_OTHER("記入日")) = "" Then
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
    strSQL = strSQL & "LAST_TIME"
    strSQL = strSQL & ")"

    strSQL = strSQL & " VALUES ("
    strSQL = strSQL & precSIS_OTHER("患者番号") & ","
    strSQL = strSQL & pintEda & ","
    If Not Trim$(precSIS_OTHER("記入日")) = "" Then
        strSQL = strSQL & "'" & Format(precSIS_OTHER("記入日"), "yyyy/mm/dd") & "',"
    End If
    strSQL = strSQL & precSIS_OTHER("指示書作成回数") & ","
    strSQL = strSQL & precSIS_OTHER("指示書有効期間") & ","
    strSQL = strSQL & precSIS_OTHER("指示書看護区分") & ","
    strSQL = strSQL & "'" & precSIS_OTHER("看護ステーション名") & "',"
    strSQL = strSQL & "'" & precSIS_OTHER("緊急時連絡先") & "',"
    strSQL = strSQL & "'" & precSIS_OTHER("不在時対応法") & "',"
    strSQL = strSQL & "'" & precSIS_OTHER("療養生活指導上留意事項") & "',"
    strSQL = strSQL & Abs(CInt(precSIS_OTHER("リハビリテーション指示有無"))) & ","
    strSQL = strSQL & "'" & precSIS_OTHER("リハビリテーション指示") & "',"
    strSQL = strSQL & Abs(CInt(precSIS_OTHER("褥瘡処置指示有無"))) & ","
    strSQL = strSQL & "'" & precSIS_OTHER("褥瘡処置指示") & "',"
    strSQL = strSQL & Abs(CInt(precSIS_OTHER("装着医療機器指示有無"))) & ","
    strSQL = strSQL & "'" & precSIS_OTHER("装着医療機器指示") & "',"
    strSQL = strSQL & Abs(CInt(precSIS_OTHER("留意指示事項他有無"))) & ","
    strSQL = strSQL & "'" & precSIS_OTHER("留意指示事項他") & "',"
    strSQL = strSQL & "'" & precSIS_OTHER("指示書特記事項") & "',"
    strSQL = strSQL & "'" & Format(precSIS_OTHER("新規作成日"), "yyyy/mm/dd hh:nn:ss") & "',"
    strSQL = strSQL & "'" & Format(precSIS_OTHER("更新日"), "yyyy/mm/dd hh:nn:ss") & "',"
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"

    '移行処理実行
    Call FDBExecuteSQL(strSQL)

End Sub

