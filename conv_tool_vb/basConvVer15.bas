Attribute VB_Name = "basConvVer15"
Option Explicit

Public Sub ConvIKENSYO15()
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
                'COMMON_IKN_SIS移行処理
                Call ConvCOMMON_IKN_SIS(intEda, recSIS, recSIS_OTHER, SIS)
                'SIS_ORIGIN移行処理
                Call ConvSIS_ORIGIN15(intEda, recSIS_OTHER)
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

Public Sub ConvINSURER()
'============================================================================================
'INSURER移行（Ver1.5、Ver1.5.3兼用）
'============================================================================================
Dim recINSURER As Object
Dim strSQL As String
    
    '移行元データを取得
    Set recINSURER = MDBExecuteRecordSet("SELECT * FROM 保険者マスタ ORDER BY 保険者番号", adOpenForwardOnly, adLockReadOnly)
        
    '移行先にデータを挿入
    Do Until recINSURER.EOF
                    
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrSec
            Call FDBTran(BeginTrans)
            '2006/02/11[Shin Fujihara] : add end

                    
            strSQL = ""
            strSQL = strSQL & "INSERT INTO INSURER ("
            strSQL = strSQL & "INSURER_NO,"
            strSQL = strSQL & "INSURER_NM,"
            strSQL = strSQL & "FD_OUTPUT_UMU,"
            strSQL = strSQL & "SEIKYUSHO_HAKKOU_PATTERN,"
            strSQL = strSQL & "SEIKYUSHO_OUTPUT_PATTERN,"
            strSQL = strSQL & "DR_NM_OUTPUT_UMU,"
            strSQL = strSQL & "HEADER_OUTPUT_UMU1,"
            strSQL = strSQL & "HEADER_OUTPUT_UMU2,"
            strSQL = strSQL & "ISS_INSURER_NO,"
            strSQL = strSQL & "ISS_INSURER_NM,"
            strSQL = strSQL & "SKS_INSURER_NO,"
            strSQL = strSQL & "SKS_INSURER_NM,"
            strSQL = strSQL & "ZAITAKU_SINKI_CHARGE,"
            strSQL = strSQL & "ZAITAKU_KEIZOKU_CHARGE,"
            strSQL = strSQL & "SISETU_SINKI_CHARGE,"
            strSQL = strSQL & "SISETU_KEIZOKU_CHARGE,"
            strSQL = strSQL & "SHOSIN_SINRYOUJO,"
            strSQL = strSQL & "SHOSIN_HOSPITAL,"
            strSQL = strSQL & "EXP_KS,"
            strSQL = strSQL & "EXP_KIK_MKI,"
            strSQL = strSQL & "EXP_KIK_KEKK,"
            strSQL = strSQL & "EXP_KKK_KKK,"
            strSQL = strSQL & "EXP_KKK_SKK,"
            strSQL = strSQL & "EXP_NITK,"
            strSQL = strSQL & "EXP_XRAY_TS,"
            strSQL = strSQL & "EXP_XRAY_SS,"
            strSQL = strSQL & "EXP_XRAY_FILM,"
            strSQL = strSQL & "SOUKATUHYOU_PRT,"
            strSQL = strSQL & "MEISAI_KIND,"
            strSQL = strSQL & "FURIKOMISAKI_PRT,"
            strSQL = strSQL & "SOUKATU_FURIKOMI_PRT,"
            strSQL = strSQL & "SOUKATUHYOU_PRT2,"
            strSQL = strSQL & "MEISAI_KIND2,"
            strSQL = strSQL & "FURIKOMISAKI_PRT2,"
            strSQL = strSQL & "SOUKATU_FURIKOMI_PRT2,"
            strSQL = strSQL & "LAST_TIME"
            strSQL = strSQL & ")"
            
            strSQL = strSQL & "VALUES ("
            strSQL = strSQL & "'" & recINSURER("保険者番号") & "',"
            strSQL = strSQL & "'" & recINSURER("保険者名称") & "',"
            strSQL = strSQL & Abs(CInt(recINSURER("FD出力有無"))) & ","
            strSQL = strSQL & Abs(CInt(recINSURER("請求書発行パターン"))) & ","
            strSQL = strSQL & recINSURER("請求書出力パターン") + 1 & ","
            strSQL = strSQL & Abs(CInt(recINSURER("医師氏名出力有無"))) & ","
            strSQL = strSQL & Abs(CInt(recINSURER("ヘッダ出力有無"))) & ","
            strSQL = strSQL & Abs(CInt(recINSURER("ヘッダ2出力有無"))) & ","
            strSQL = strSQL & "'" & recINSURER("意見書作成料請求先保険者番号") & "',"
            strSQL = strSQL & "'" & recINSURER("意見書作成料請求先保険者名称") & "',"
            strSQL = strSQL & "'" & recINSURER("診察･検査費用請求先保険者番号") & "',"
            strSQL = strSQL & "'" & recINSURER("診察･検査費用請求先保険者名称") & "',"
            strSQL = strSQL & recINSURER("意見書作成料_在宅新規") & ","
            strSQL = strSQL & recINSURER("意見書作成料_在宅継続") & ","
            strSQL = strSQL & recINSURER("意見書作成料_施設新規") & ","
            strSQL = strSQL & recINSURER("意見書作成料_施設継続") & ","
            strSQL = strSQL & recINSURER("初診_診療所") & ","
            strSQL = strSQL & recINSURER("初診_病院") & ","
            strSQL = strSQL & recINSURER("検査費用_血液採取") & ","
            strSQL = strSQL & recINSURER("検査費用_血液一般検査_末梢血液一般検査") & ","
            strSQL = strSQL & recINSURER("検査費用_血液一般検査_血液液化学的検査") & ","
            strSQL = strSQL & recINSURER("検査費用_血化学検査_血液化学検査") & ","
            strSQL = strSQL & recINSURER("検査費用_血化学検査_生化学的検査") & ","
            strSQL = strSQL & recINSURER("検査費用_尿中一般物質定性半定量検査") & ","
            strSQL = strSQL & recINSURER("検査費用_X線撮影_単純撮影") & ","
            strSQL = strSQL & recINSURER("検査費用_X線撮影_写真診断") & ","
            strSQL = strSQL & recINSURER("検査費用_X線撮影_フィルム") & ","
            Select Case recINSURER("総括表印字")
                Case 1, 2
                    strSQL = strSQL & "1,"
                Case 3
                    strSQL = strSQL & "2,"
                Case Else
                    strSQL = strSQL & recINSURER("総括表印字") & ","
            End Select
            strSQL = strSQL & recINSURER("請求明細種類") & ","
            strSQL = strSQL & recINSURER("振込先印字") & ","
            strSQL = strSQL & recINSURER("総括表振込先印字") & ","
            Select Case recINSURER("総括表印字2")
                Case 1, 2
                    strSQL = strSQL & "1,"
                Case 3
                    strSQL = strSQL & "2,"
                Case Else
                    strSQL = strSQL & recINSURER("総括表印字2") & ","
            End Select
            strSQL = strSQL & recINSURER("請求明細種類2") & ","
            strSQL = strSQL & recINSURER("振込先印字2") & ","
            strSQL = strSQL & recINSURER("総括表振込先印字2") & ","
            strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
            strSQL = strSQL & ")"
            
            Call FDBExecuteSQL(strSQL)
        
        '2006/02/11[Shin Fujihara] : add begin
            Call FDBTran(CommitTrans)
        On Error GoTo 0
ErrSec:
        If Err.Number <> 0 Then
            Call FDBTran(RollbackTrans)
        End If
        '2006/02/11[Shin Fujihara] : add end

        
        recINSURER.MoveNext
        
    Loop
                                                            
End Sub

Public Sub ConvJIGYOUSHA()
'============================================================================================
'JIGYOUSHA移行（Ver1.5、Ver1.5.3、Ver1.6兼用）
'============================================================================================
Dim recJIGYOUSHA As Object
Dim strSQL As String

    '移行元データを取得
    Set recJIGYOUSHA = MDBExecuteRecordSet("SELECT * FROM 事業者マスタ ORDER BY 医師コード", adOpenForwardOnly, adLockReadOnly)

    '移行先にデータを挿入
    Do Until recJIGYOUSHA.EOF
    
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrSec
            Call FDBTran(BeginTrans)
            '2006/02/11[Shin Fujihara] : add end

            strSQL = ""
            strSQL = strSQL & "INSERT INTO JIGYOUSHA ("
            strSQL = strSQL & "DR_CD,"
            strSQL = strSQL & "INSURER_NO,"
            strSQL = strSQL & "JIGYOUSHA_NO,"
            strSQL = strSQL & "LAST_TIME"
            strSQL = strSQL & ")"
    
            strSQL = strSQL & "VALUES ("
            strSQL = strSQL & recJIGYOUSHA("医師コード") & ","
            strSQL = strSQL & "'" & recJIGYOUSHA("保険者番号") & "',"
            strSQL = strSQL & "'" & recJIGYOUSHA("事業者番号") & "',"
            strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
            strSQL = strSQL & ")"
    
            Call FDBExecuteSQL(strSQL)

        '2006/02/11[Shin Fujihara] : add begin
            Call FDBTran(CommitTrans)
        On Error GoTo 0
ErrSec:
        If Err.Number <> 0 Then
            Call FDBTran(RollbackTrans)
        End If
        '2006/02/11[Shin Fujihara] : add end

        recJIGYOUSHA.MoveNext

    Loop
                                                            
End Sub

Public Sub ConvDOCTOR15()
'============================================================================================
'DOCTOR移行（Ver1.5、Ver1.5.3兼用）
'============================================================================================
Dim recDOCTOR As Object
Dim recDOCTOR_MAX As Object
Dim strSQL As String
    
    '移行元データを取得
    Set recDOCTOR = MDBExecuteRecordSet("SELECT * FROM 医師マスタ ORDER BY 医師コード", adOpenForwardOnly, adLockReadOnly)
    Set recDOCTOR_MAX = MDBExecuteRecordSet("SELECT MAX(医師コード) AS 医師コード_最大値 FROM 医師マスタ", adOpenForwardOnly, adLockReadOnly)
    
    'データ取得に失敗した場合はそのまま終了
    If IsNull(recDOCTOR) Then
        Exit Sub
    End If
    
    If IsNull(recDOCTOR_MAX) Then
        Exit Sub
    End If
    
    
    '移行先データのトリガーを一旦停止
    Call FDBExecuteSQL("ALTER TRIGGER SET_DOCTOR_ID INACTIVE")
    
    '移行先にデータを挿入
    Do Until recDOCTOR.EOF
        '2006/02/11[Shin Fujihara] : add begin
        On Error GoTo ErrSec
            Call FDBTran(BeginTrans)
            '2006/02/11[Shin Fujihara] : add end
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
            strSQL = strSQL & "KAISETUSHA_NM,"
            strSQL = strSQL & "DR_NO,"
            strSQL = strSQL & "MI_KBN,"
            strSQL = strSQL & "BANK_NM,"
            strSQL = strSQL & "BANK_SITEN_NM,"
            strSQL = strSQL & "BANK_KOUZA_NO,"
            strSQL = strSQL & "BANK_KOUZA_KIND,"
            strSQL = strSQL & "FURIKOMI_MEIGI,"
            strSQL = strSQL & "LAST_TIME"
            strSQL = strSQL & ")"
            
            strSQL = strSQL & "VALUES ("
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
            strSQL = strSQL & "'" & recDOCTOR("開設者氏名") & "',"
            strSQL = strSQL & "'" & recDOCTOR("医師番号") & "',"
            strSQL = strSQL & recDOCTOR("診療所･病院区分") & ","
            strSQL = strSQL & "'" & recDOCTOR("振込先金融機関名") & "',"
            strSQL = strSQL & "'" & recDOCTOR("振込先金融機関支店名") & "',"
            strSQL = strSQL & "'" & recDOCTOR("振込先口座番号") & "',"
            strSQL = strSQL & recDOCTOR("振込先口座種類") & ","
            strSQL = strSQL & "'" & recDOCTOR("振込先名義人") & "',"
            strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
            strSQL = strSQL & ")"
            
            'SQL文実行
            Call FDBExecuteSQL(strSQL)
        
        '2006/02/11[Shin Fujihara] : add begin
            Call FDBTran(CommitTrans)
        On Error GoTo 0
ErrSec:
        If Err.Number <> 0 Then
            Call FDBTran(RollbackTrans)
        End If
        '2006/02/11[Shin Fujihara] : add end
        
        recDOCTOR.MoveNext
        
    Loop
                                                          
    '移行先の自動採番初期値を設定
    If Not IsNull(recDOCTOR_MAX("医師コード_最大値")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_DOCTOR to " & recDOCTOR_MAX("医師コード_最大値"))
    End If
    '移行先データのトリガーを開始
    Call FDBExecuteSQL("ALTER TRIGGER SET_DOCTOR_ID ACTIVE")
                                                            
End Sub

Sub ConvIKN_BILL(pintEda As Long, precIKENSYO_SEIKYU As Object)
'============================================================================================
'IKN_BILL移行（Ver1.5専用）
'============================================================================================
Dim strSQL As String

    strSQL = ""
    strSQL = strSQL & "INSERT INTO IKN_BILL ("
    strSQL = strSQL & "PATIENT_NO,"
    strSQL = strSQL & "EDA_NO,"
    
    
    strSQL = strSQL & "BANK_NM,"
    strSQL = strSQL & "BANK_SITEN_NM,"
    strSQL = strSQL & "KOUZA_NO,"
    strSQL = strSQL & "KOUZA_KIND,"
    strSQL = strSQL & "KOUZA_MEIGI,"
    strSQL = strSQL & "JIGYOUSHA_NO,"
    strSQL = strSQL & "KAISETUSHA_NM,"
    strSQL = strSQL & "DR_NO,"
    strSQL = strSQL & "IKN_CHARGE,"
    strSQL = strSQL & "SHOSIN_TAISHOU,"
    strSQL = strSQL & "SHOSIN,"
    strSQL = strSQL & "SHOSIN_TEKIYOU,"
    strSQL = strSQL & "XRAY_TANJUN_SATUEI,"
    strSQL = strSQL & "XRAY_SHASIN_SINDAN,"
    strSQL = strSQL & "XRAY_FILM,"
    strSQL = strSQL & "XRAY_TEKIYOU,"
    strSQL = strSQL & "BLD_SAISHU,"
    strSQL = strSQL & "BLD_IPPAN_MASHOU_KETUEKI,"
    strSQL = strSQL & "BLD_IPPAN_EKIKAGAKUTEKIKENSA,"
    strSQL = strSQL & "BLD_IPPAN_TEKIYOU,"
    strSQL = strSQL & "BLD_KAGAKU_KETUEKIKAGAKUKENSA,"
    strSQL = strSQL & "BLD_KAGAKU_SEIKAGAKUTEKIKENSA,"
    strSQL = strSQL & "BLD_KAGAKU_TEKIYOU,"
    strSQL = strSQL & "NYO_KENSA,"
    strSQL = strSQL & "NYO_KENSA_TEKIYOU,"
    strSQL = strSQL & "ZAITAKU_SINKI_CHARGE,"
    strSQL = strSQL & "ZAITAKU_KEIZOKU_CHARGE,"
    strSQL = strSQL & "SISETU_SINKI_CHARGE,"
    strSQL = strSQL & "SISETU_KEIZOKU_CHARGE,"
    strSQL = strSQL & "SHOSIN_SINRYOUJO,"
    strSQL = strSQL & "SHOSIN_HOSPITAL,"
    strSQL = strSQL & "SHOSIN_OTHER,"
    strSQL = strSQL & "EXP_KS,"
    strSQL = strSQL & "EXP_KIK_MKI,"
    strSQL = strSQL & "EXP_KIK_KEKK,"
    strSQL = strSQL & "EXP_KKK_KKK,"
    strSQL = strSQL & "EXP_KKK_SKK,"
    strSQL = strSQL & "EXP_NITK,"
    strSQL = strSQL & "EXP_XRAY_TS,"
    strSQL = strSQL & "EXP_XRAY_SS,"
    strSQL = strSQL & "EXP_XRAY_FILM,"
    strSQL = strSQL & "TAX,"
    strSQL = strSQL & "OUTPUT_PATTERN,"
    strSQL = strSQL & "ISS_INSURER_NO,"
    strSQL = strSQL & "ISS_INSURER_NM,"
    strSQL = strSQL & "SKS_INSURER_NO,"
    strSQL = strSQL & "SKS_INSURER_NM,"
    strSQL = strSQL & "FD_OUTPUT_KBN,"
    'strSQL = strSQL & "FD_TIMESTAMP,"
    
'    If Not IsNull(precIKENSYO_SEIKYU("FD出力用タイムスタンプ")) Then
'        If Not Trim$(precIKENSYO_SEIKYU("FD出力用タイムスタンプ")) = "" Then
'            strSQL = strSQL & "FD_TIMESTAMP,"
'        End If
'    End If
'    If Not IsNull(precIKENSYO_SEIKYU("申請日")) Then
'        If Not Trim$(precIKENSYO_SEIKYU("申請日")) = "" Then
'            strSQL = strSQL & "SINSEI_DT,"
'        End If
'    End If

    strSQL = strSQL & "HAKKOU_KBN,"
    strSQL = strSQL & "LAST_TIME"
    strSQL = strSQL & ")"

    strSQL = strSQL & " VALUES ("
    strSQL = strSQL & precIKENSYO_SEIKYU("患者番号") & ","
    strSQL = strSQL & pintEda & ","
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "2,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "'',"
    strSQL = strSQL & "0,"
    'strSQL = strSQL & "'',"
'    If Not IsNull(precIKENSYO_SEIKYU("FD出力用タイムスタンプ")) Then
'        If Not Trim$(precIKENSYO_SEIKYU("FD出力用タイムスタンプ")) = "" Then
'            strSQL = strSQL & "'" & Format(precIKENSYO_SEIKYU("FD出力用タイムスタンプ"), "yyyy/mm/dd hh:nn:ss") & "',"
'        End If
'    End If
'    If Not IsNull(precIKENSYO_SEIKYU("申請日")) Then
'        If Not Trim$(precIKENSYO_SEIKYU("申請日")) = "" Then
'            strSQL = strSQL & "'" & Format(precIKENSYO_SEIKYU("申請日"), "yyyy/mm/dd hh:nn:ss") & "',"
'        End If
'    End If
    strSQL = strSQL & "0,"
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"

    'SQL文実行
    Call FDBExecuteSQL(strSQL)
    
End Sub

Sub ConvSIS_ORIGIN15(pintEda As Long, precSIS_OTHER As Object)
'============================================================================================
'SIS_ORIGIN移行（Ver1.5専用）
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
    strSQL = strSQL & "SIJI_KIKAN_FROM,"
    strSQL = strSQL & "SIJI_KIKAN_TO,"
    strSQL = strSQL & "YOUKAIGO_JOUKYOU,"
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
'    strSQL = strSQL & "'" & precSIS_OTHER("指示期間From") & "',"
'    strSQL = strSQL & "'" & precSIS_OTHER("指示期間To") & "',"
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
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"

    '移行処理実行
    Call FDBExecuteSQL(strSQL)

End Sub


