Attribute VB_Name = "basConvVer161"
Option Explicit

Public Sub ConvIKENSYO161()
'============================================================================================
'意見書情報移行
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
Dim recSIS_OTHER2 As Object
Dim strSQL As String
Dim intEda As Long

    '最新基本情報テーブルの取得
    Set recPAIENT = MDBExecuteRecordSet("SELECT * FROM 最新基本情報 ORDER BY 患者番号", adOpenForwardOnly, adLockReadOnly)
    Set recPAIENT_MAX = MDBExecuteRecordSet("SELECT MAX(患者番号) AS 患者番号_最大値 FROM 最新基本情報", adOpenForwardOnly, adLockReadOnly)
    
    '最新基本情報テーブルのトリガーを一旦停止
    Call FDBExecuteSQL("ALTER TRIGGER SET_PATIENT_ID INACTIVE")
    
    '最新基本情報数分処理を回す
    Do Until recPAIENT.EOF
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrSec
            Call FDBTran(BeginTrans)
            '2006/02/11[Shin Fujihara] : add end
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
                '意見書請求書テーブルの取得
                Set recIKENSYO_SEIKYU = MDBExecuteRecordSet("SELECT * FROM 意見書請求書 WHERE (患者番号 = " & recIKENSYO("患者番号") & ") AND (作成NO = " & recIKENSYO("作成NO") & ")", adOpenForwardOnly, adLockReadOnly)
                'COMMON_IKN_SIS移行処理
                Call ConvCOMMON_IKN_SIS(intEda, recIKENSYO, recIKENSYO_OTHER, IKEN)
                'IKN_ORIGIN移行処理
                Call ConvIKN_ORIGIN(intEda, recIKENSYO, recIKENSYO_KAIGO, recIKENSYO_OTHER, ver15)
                'IKN_BILL移行処理
                Call ConvIKN_BILL(intEda, recIKENSYO)
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
                '指示書その他02テーブルの取得
                Set recSIS_OTHER2 = MDBExecuteRecordSet("SELECT * FROM 指示書その他02 WHERE (患者番号 = " & recSIS("患者番号") & ") AND (作成NO = " & recSIS("作成NO") & ")", adOpenForwardOnly, adLockReadOnly)
                'COMMON_IKN_SIS移行処理
                Call ConvCOMMON_IKN_SIS(intEda, recSIS, recSIS_OTHER, SIS)
                'SIS_ORIGIN移行処理
                Call ConvSIS_ORIGIN161(intEda, recSIS_OTHER, recSIS_OTHER2)
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
        
            '2006/02/11[Shin Fujihara] : add begin
            Call FDBTran(CommitTrans)
        On Error GoTo 0
ErrSec:
        If Err.Number <> 0 Then
            'Debug.Print "ロールバック"
            Call FDBTran(RollbackTrans)
        End If
        '2006/02/11[Shin Fujihara] : add end
    
        recPAIENT.MoveNext
    
    Loop
    
    '最新基本情報テーブルの自動採番初期値を設定
    If Not IsNull(recPAIENT_MAX("患者番号_最大値")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_PATIENT to " & recPAIENT_MAX("患者番号_最大値"))
    End If
    '最新基本情報テーブルのトリガーを開始
    Call FDBExecuteSQL("ALTER TRIGGER SET_PATIENT_ID ACTIVE")

End Sub

Sub ConvSIS_ORIGIN161(pintEda As Long, precSIS_OTHER As Object, precSIS_OTHER2 As Object)
'============================================================================================
'SIS_ORIGIN移行（Ver1.6.1専用）
'============================================================================================
Dim strSQL As String
Dim blnOtherFlg As Boolean

    blnOtherFlg = precSIS_OTHER2.EOF

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
    strSQL = strSQL & "SIJI_KIKAN_FROM,"
    strSQL = strSQL & "SIJI_KIKAN_TO,"
    strSQL = strSQL & "YOUKAIGO_JOUKYOU,"
    strSQL = strSQL & "HOUMON_SIJISYO,"
    strSQL = strSQL & "TENTEKI_SIJISYO,"
    strSQL = strSQL & "TENTEKI_FROM,"
    strSQL = strSQL & "TENTEKI_TO,"
    strSQL = strSQL & "TENTEKI_SIJI,"
    strSQL = strSQL & "OTHER_STATION_SIJI,"
    If Not blnOtherFlg Then
        strSQL = strSQL & "OTHER_STATION_NM,"
    End If
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
    If Trim$(precSIS_OTHER("指示期間From")) = "平成00年00月00日" Then
        strSQL = strSQL & "'0000年00月00日',"
    Else
        strSQL = strSQL & "'" & precSIS_OTHER("指示期間From") & "',"
    End If
    If Trim$(precSIS_OTHER("指示期間To")) = "平成00年00月00日" Then
        strSQL = strSQL & "'0000年00月00日',"
    Else
        strSQL = strSQL & "'" & precSIS_OTHER("指示期間To") & "',"
    End If
    strSQL = strSQL & precSIS_OTHER("要介護認定の状況") & ","
    'strSQL = strSQL & Abs(CInt(precSIS_OTHER("訪問看護指示書"))) & ","
    'strSQL = strSQL & Abs(CInt(precSIS_OTHER("点滴注射指示書"))) & ","
    'null 対応
    If IsNull(precSIS_OTHER("訪問看護指示書")) Then
        strSQL = strSQL & "0,"
    Else
        strSQL = strSQL & CInt(precSIS_OTHER("訪問看護指示書")) & ","
    End If
    If IsNull(precSIS_OTHER("点滴注射指示書")) Then
        strSQL = strSQL & "0,"
    Else
        strSQL = strSQL & CInt(precSIS_OTHER("点滴注射指示書")) & ","
    End If
    
    'null対応
    If IsNull(precSIS_OTHER("点滴注射指示期間From")) Then
        strSQL = strSQL & "'0000年00月00日',"
    Else
        If Trim$(precSIS_OTHER("点滴注射指示期間From")) = "平成00年00月00日" Then
            strSQL = strSQL & "'0000年00月00日',"
        Else
            strSQL = strSQL & "'" & precSIS_OTHER("点滴注射指示期間From") & "',"
        End If
    End If
    
    If IsNull(precSIS_OTHER("点滴注射指示期間To")) Then
        strSQL = strSQL & "'0000年00月00日',"
    Else
        If Trim$(precSIS_OTHER("点滴注射指示期間To")) = "平成00年00月00日" Then
            strSQL = strSQL & "'0000年00月00日',"
        Else
            strSQL = strSQL & "'" & precSIS_OTHER("点滴注射指示期間To") & "',"
        End If
    End If
    
    strSQL = strSQL & "'" & precSIS_OTHER("点滴注射指示") & "',"
    
    If blnOtherFlg Then
        strSQL = strSQL & "1,"
    Else
        If precSIS_OTHER2("他ステーション指示") = "0" Then
            strSQL = strSQL & "1,"
        Else
            strSQL = strSQL & "2,"
        End If
        strSQL = strSQL & "'" & precSIS_OTHER2("看護ステーション名") & "',"
    End If
    
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"

    '移行処理実行
    Call FDBExecuteSQL(strSQL)

End Sub

