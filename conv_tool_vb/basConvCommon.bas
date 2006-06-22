Attribute VB_Name = "basConvCommon"
Option Explicit

Public Sub ConvRENKEII()
'============================================================================================
'RENKEII移行（全共通）
'============================================================================================
Dim recRENKEII As Object
Dim recRENKEII_MAX As Object
Dim strSQL As String
    
    '移行元データを取得
    Set recRENKEII = MDBExecuteRecordSet("SELECT * FROM 連携医マスタ ORDER BY 連携医コード", adOpenForwardOnly, adLockReadOnly)
    Set recRENKEII_MAX = MDBExecuteRecordSet("SELECT MAX(連携医コード) AS 連携医コード_最大値 FROM 連携医マスタ", adOpenForwardOnly, adLockReadOnly)
    '移行先データのトリガーを一旦停止
    Call FDBExecuteSQL("ALTER TRIGGER SET_RENKEII_ID INACTIVE")
    
    '移行先にデータを挿入
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
            strSQL = strSQL & recRENKEII("連携医コード") & ","
            strSQL = strSQL & "'" & recRENKEII("医師名") & "',"
            strSQL = strSQL & "'" & recRENKEII("診療科") & "',"
            strSQL = strSQL & "'" & recRENKEII("医療機関名") & "',"
            strSQL = strSQL & "'" & AddHyphen(recRENKEII("医療機関郵便番号")) & "',"
            strSQL = strSQL & "'" & recRENKEII("医療機関住所") & "',"
            strSQL = strSQL & "'" & recRENKEII("医療機関電話局番") & "',"
            strSQL = strSQL & "'" & AddHyphen(recRENKEII("医療機関電話")) & "',"
            strSQL = strSQL & "'" & recRENKEII("医療機関FAX局番") & "',"
            strSQL = strSQL & "'" & AddHyphen(recRENKEII("医療機関FAX")) & "',"
            strSQL = strSQL & "'" & recRENKEII("携帯番号上") & "',"
            strSQL = strSQL & "'" & AddHyphen(recRENKEII("携帯番号")) & "',"
            strSQL = strSQL & "'" & recRENKEII("緊急時連絡先") & "',"
            strSQL = strSQL & "'" & recRENKEII("不在時対応法") & "',"
            strSQL = strSQL & "'" & recRENKEII("備考") & "',"
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
        recRENKEII.MoveNext
        
    Loop
    
    '移行先の自動採番初期値を設定
    If Not IsNull(recRENKEII_MAX("連携医コード_最大値")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_RENKEII to " & recRENKEII_MAX("連携医コード_最大値"))
    End If
    '移行先データのトリガーを開始
    Call FDBExecuteSQL("ALTER TRIGGER SET_RENKEII_ID ACTIVE")
            
End Sub

Public Sub ConvSTATION()
'============================================================================================
'STATION移行（全共通）
'============================================================================================
Dim recSTATION As Object
Dim recSTATION_MAX As Object
Dim strSQL As String
    
    '移行元データを取得
    Set recSTATION = MDBExecuteRecordSet("SELECT * FROM 看護ステーションマスタ ORDER BY 看護ステーションコード", adOpenForwardOnly, adLockReadOnly)
    Set recSTATION_MAX = MDBExecuteRecordSet("SELECT MAX(看護ステーションコード) AS 看護ステーションコード_最大値 FROM 看護ステーションマスタ", adOpenForwardOnly, adLockReadOnly)
    '移行先データのトリガーを一旦停止
    Call FDBExecuteSQL("ALTER TRIGGER SET_STATION_ID INACTIVE")
    
    '移行先にデータを挿入
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
            strSQL = strSQL & recSTATION("看護ステーションコード") & ","
            strSQL = strSQL & "'" & recSTATION("医師名") & "',"
            strSQL = strSQL & "'" & recSTATION("医療機関名") & "',"
            strSQL = strSQL & "'" & AddHyphen(recSTATION("医療機関郵便番号")) & "',"
            strSQL = strSQL & "'" & recSTATION("医療機関住所") & "',"
            strSQL = strSQL & "'" & recSTATION("医療機関電話局番") & "',"
            strSQL = strSQL & "'" & AddHyphen(recSTATION("医療機関電話")) & "',"
            strSQL = strSQL & "'" & recSTATION("医療機関FAX局番") & "',"
            strSQL = strSQL & "'" & AddHyphen(recSTATION("医療機関FAX")) & "',"
            strSQL = strSQL & "'" & recSTATION("携帯番号上") & "',"
            strSQL = strSQL & "'" & AddHyphen(recSTATION("携帯番号")) & "',"
            strSQL = strSQL & "'" & recSTATION("緊急時連絡先") & "',"
            strSQL = strSQL & "'" & recSTATION("不在時対応法") & "',"
            strSQL = strSQL & "'" & recSTATION("備考") & "',"
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
        
        recSTATION.MoveNext
        
    Loop
                                                            
    '移行先の自動採番初期値を設定
    If Not IsNull(recSTATION_MAX("看護ステーションコード_最大値")) Then
        Call FDBExecuteSQL("SET GENERATOR GEN_STATION to " & recSTATION_MAX("看護ステーションコード_最大値"))
    End If
    '移行先データのトリガーを開始
    Call FDBExecuteSQL("ALTER TRIGGER SET_STATION_ID ACTIVE")
                                                            
End Sub

Public Function GetLogicalValue(pLogicalString As String) As Long
'============================================================================================
'論理和計算
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
'IKN_ORIGIN移行
'============================================================================================
Dim strSQL As String

    strSQL = ""
    strSQL = strSQL & "INSERT INTO IKN_ORIGIN ("
    strSQL = strSQL & "PATIENT_NO,"
    strSQL = strSQL & "EDA_NO,"
    'フォーマットを強制的にH17以降にする
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
    If Not IsNull(precIKENSYO_OTHER("全身図")) Then
        strSQL = strSQL & "BODY_FIGURE,"
    End If
    strSQL = strSQL & "INSURED_NO,"
    If Not Trim$(precIKENSYO_OTHER("作成依頼日")) = "" Then
        strSQL = strSQL & "REQ_DT,"
    End If
    strSQL = strSQL & "REQ_NO,"
    If Not Trim$(precIKENSYO_OTHER("送付日")) = "" Then
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
    
    'csvファイル出力でエラーになるので初期値を設定
    strSQL = strSQL & "ASSISTANCE_TOOL,"
    
    strSQL = strSQL & "LAST_TIME"
    strSQL = strSQL & ")"

    strSQL = strSQL & " VALUES ("
    strSQL = strSQL & precIKENSYO("患者番号") & ","
    strSQL = strSQL & pintEda & ","
    'フォーマット区分を強制的にH17以降にする
    strSQL = strSQL & "1,"
    
    strSQL = strSQL & precIKENSYO_OTHER("医師同意") & ","
    strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("記入日"), "yyyy/mm/dd") & "',"
    strSQL = strSQL & precIKENSYO_OTHER("意見書作成回数") & ","
    If Not Trim$(precIKENSYO("最終診察日")) = "0000年00月00日" Then
        strSQL = strSQL & "'" & Format(precIKENSYO("最終診察日"), "yyyy/mm/dd") & "',"
    Else
        strSQL = strSQL & "Null,"
    End If
    strSQL = strSQL & GetLogicalValue(precIKENSYO("他科受診")) & ","
    strSQL = strSQL & "'" & precIKENSYO("他科受診その他") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("短期記憶") & ","
    strSQL = strSQL & precIKENSYO_KAIGO("認知能力") & ","
    strSQL = strSQL & precIKENSYO_KAIGO("伝達能力") & ","
    strSQL = strSQL & precIKENSYO_KAIGO("食事") & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("幻視幻聴"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("妄想"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("昼夜逆転"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("暴言"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("暴行"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("抵抗"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("徘徊"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("不始末"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("不潔行為"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("異食行動"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("性的問題行動"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("問題行動他"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("問題行動他名") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("精神神経疾患") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("精神神経疾患名") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("専門医") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("専門医名") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("利き腕") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("体重") & "',"
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("身長") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("四肢欠損"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("四肢欠損部位") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("四肢欠損程度") & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("麻痺"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("麻痺部位") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("麻痺程度") & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("筋力低下"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("筋力低下部位") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("筋力低下程度") & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("褥瘡"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("褥瘡部位") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("褥瘡程度") & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("皮膚疾患"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("皮膚疾患部位") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("皮膚疾患程度"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("肩関節拘縮右"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("肩関節拘縮左"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("肘関節拘縮右"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("肘関節拘縮左"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("股関節拘縮右"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("股関節拘縮左"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("膝関節拘縮右"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("膝関節拘縮左"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("上肢失調右"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("上肢失調左"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("下肢失調右"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("下肢失調左"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("体幹失調右"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("体幹失調左"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("尿失禁"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("尿失禁対処方針") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("転倒骨折"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("転倒骨折対処方針") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("徘徊可能性"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("徘徊可能性対処方針") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("褥瘡可能性"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("褥瘡可能性対処方針") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("嚥下性肺炎"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("嚥下性肺炎対処方針") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("腸閉塞"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("腸閉塞対処方針") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("易感感染"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("易感感染対処方針") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("心肺機能低下"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("心肺機能低下対処方針") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("痛み"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("痛み対処方針") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("脱水"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("脱水対処方針") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("病態他"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("病態他対処方針") & "',"
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("病態他名") & "',"
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問診療"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問診療下線"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問看護"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問看護下線"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問リハビリテーション"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問リハビリテーション下線"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("通所リハビリテーション"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("通所リハビリテーション下線"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("短期入所療養介護"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("短期入所療養介護下線"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問歯科診療"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問歯科診療下線"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問歯科衛生指導"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問歯科衛生指導下線"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問薬剤管理指導"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問薬剤管理指導下線"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問栄養食事指導"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("訪問栄養食事指導下線"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("医学的管理他"))) & ","
    strSQL = strSQL & Abs(CInt(precIKENSYO_KAIGO("医学的管理他下線"))) & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("医学的管理他名") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("介護血圧") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("介護血圧留意事項") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("介護摂食") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("介護摂食留意事項") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("介護嚥下") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("介護嚥下留意事項") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("介護移動") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("介護移動留意事項") & "',"
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("介護他") & "',"
    strSQL = strSQL & precIKENSYO_KAIGO("感染症") & ","
    strSQL = strSQL & "'" & precIKENSYO_KAIGO("感染症名") & "',"
    strSQL = strSQL & "'" & precIKENSYO_OTHER("意見書特記事項") & "',"
    strSQL = strSQL & "'" & precIKENSYO_OTHER("長谷川式点数") & "',"
    If Trim$(precIKENSYO_OTHER("長谷川式点数日付")) = "0000年00月" Then
        strSQL = strSQL & "'0000年00月00日',"
    Else
        If Mid(Trim$(precIKENSYO_OTHER("長谷川式点数日付")), 6, 2) = "00" Then
            strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("長谷川式点数日付"), "gggee年00月00日") & "',"
        Else
            strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("長谷川式点数日付"), "gggee年mm月00日") & "',"
        End If
    End If
    strSQL = strSQL & "'" & precIKENSYO_OTHER("長谷川式前回点数") & "',"
    
    If Trim$(precIKENSYO_OTHER("長谷川式前回点数日付")) = "0000年00月" Then
        strSQL = strSQL & "'0000年00月00日',"
    Else
        If Mid(Trim$(precIKENSYO_OTHER("長谷川式前回点数日付")), 6, 2) = "00" Then
            strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("長谷川式前回点数日付"), "gggee年00月00日") & "',"
        Else
            strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("長谷川式前回点数日付"), "gggee年mm月00日") & "',"
        End If
    End If
    
    strSQL = strSQL & "'" & precIKENSYO_OTHER("施設選択優先度1") & "',"
    strSQL = strSQL & "'" & precIKENSYO_OTHER("施設選択優先度2") & "',"
    If Not IsNull(precIKENSYO_OTHER("全身図")) Then
        strSQL = strSQL & precIKENSYO_OTHER("全身図") & ","
    End If
    strSQL = strSQL & "'" & precIKENSYO_OTHER("被保険者番号") & "',"
    If Not Trim$(precIKENSYO_OTHER("作成依頼日")) = "" Then
        strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("作成依頼日"), "yyyy/mm/dd") & "',"
    End If
    strSQL = strSQL & "'" & precIKENSYO_OTHER("依頼番号") & "',"
    If Not Trim$(precIKENSYO_OTHER("送付日")) = "" Then
        strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("送付日"), "yyyy/mm/dd") & "',"
    End If
    strSQL = strSQL & precIKENSYO_OTHER("種別") & ","
    strSQL = strSQL & "'" & precIKENSYO_OTHER("保険者番号") & "',"
    Select Case pintVer
        Case DBVersion.ver15, DBVersion.VER153, DBVersion.VER161
        strSQL = strSQL & "'" & precIKENSYO_OTHER("保険者名称") & "',"
    End Select
    strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("新規作成日"), "yyyy/mm/dd hh:nn:ss") & "',"
    strSQL = strSQL & "'" & Format(precIKENSYO_OTHER("更新日"), "yyyy/mm/dd hh:nn:ss") & "',"
    
    'csvファイル出力でエラーになるので初期値を設定
    strSQL = strSQL & "0,"
    
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"

    '移行処理実行
    Call FDBExecuteSQL(strSQL)

End Sub

Sub ConvCOMMON_IKN_SIS(pintEda As Long, precMAIN As Object, precMAIN_OTHER As Object, pintMode As DocKbn)
'============================================================================================
'COMMON_IKN_SIS移行（全共通）
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
    strSQL = strSQL & precMAIN("患者番号") & ","
    strSQL = strSQL & pintEda & ","
    strSQL = strSQL & pintMode & ","
    strSQL = strSQL & "'" & precMAIN("患者名") & "',"
    strSQL = strSQL & "'" & precMAIN("患者名かな") & "',"
    strSQL = strSQL & precMAIN("性別") & ","
    strSQL = strSQL & "'" & Format(precMAIN("生年月日"), "yyyy/mm/dd") & "',"
    strSQL = strSQL & "'" & precMAIN("年齢") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN("郵便番号")) & "',"
    strSQL = strSQL & "'" & precMAIN("住所") & "',"
    strSQL = strSQL & "'" & precMAIN("電話局番") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN("電話")) & "',"
    strSQL = strSQL & "'" & precMAIN("診断名1") & "',"
    strSQL = strSQL & "'" & precMAIN("診断名2") & "',"
    strSQL = strSQL & "'" & precMAIN("診断名3") & "',"
    strSQL = strSQL & "'" & precMAIN("発症年月日1") & "',"
    strSQL = strSQL & "'" & precMAIN("発症年月日2") & "',"
    strSQL = strSQL & "'" & precMAIN("発症年月日3") & "',"
    strSQL = strSQL & "'" & precMAIN("病状治療状態") & "',"
    strSQL = strSQL & "'" & precMAIN("病状治療状態他") & "',"
    strSQL = strSQL & "'" & precMAIN("薬剤名1") & "',"
    strSQL = strSQL & "'" & precMAIN("用量1") & "',"
    strSQL = strSQL & "'" & precMAIN("単位1") & "',"
    strSQL = strSQL & "'" & precMAIN("用法1") & "',"
    strSQL = strSQL & "'" & precMAIN("薬剤名2") & "',"
    strSQL = strSQL & "'" & precMAIN("用量2") & "',"
    strSQL = strSQL & "'" & precMAIN("単位2") & "',"
    strSQL = strSQL & "'" & precMAIN("用法2") & "',"
    strSQL = strSQL & "'" & precMAIN("薬剤名3") & "',"
    strSQL = strSQL & "'" & precMAIN("用量3") & "',"
    strSQL = strSQL & "'" & precMAIN("単位3") & "',"
    strSQL = strSQL & "'" & precMAIN("用法3") & "',"
    strSQL = strSQL & "'" & precMAIN("薬剤名4") & "',"
    strSQL = strSQL & "'" & precMAIN("用量4") & "',"
    strSQL = strSQL & "'" & precMAIN("単位4") & "',"
    strSQL = strSQL & "'" & precMAIN("用法4") & "',"
    strSQL = strSQL & "'" & precMAIN("薬剤名5") & "',"
    strSQL = strSQL & "'" & precMAIN("用量5") & "',"
    strSQL = strSQL & "'" & precMAIN("単位5") & "',"
    strSQL = strSQL & "'" & precMAIN("用法5") & "',"
    strSQL = strSQL & "'" & precMAIN("薬剤名6") & "',"
    strSQL = strSQL & "'" & precMAIN("用量6") & "',"
    strSQL = strSQL & "'" & precMAIN("単位6") & "',"
    strSQL = strSQL & "'" & precMAIN("用法6") & "',"
    strSQL = strSQL & precMAIN("寝たきり度") & ","
    strSQL = strSQL & precMAIN("痴呆状況") & ","
    strSQL = strSQL & precMAIN("症状安定性") & ","
    strSQL = strSQL & precMAIN("要介護状態予後") & ","
    strSQL = strSQL & Abs(CInt(precMAIN("点滴管理"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("輸液ポンプ"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("中心静脈栄養"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("透析"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("自動腹膜灌流装置"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("透析液供給装置"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("人工肛門"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("人工膀胱"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("酸素療法"))) & ","
    strSQL = strSQL & "'" & precMAIN("酸素療法量") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("人工呼吸器"))) & ","
    strSQL = strSQL & "'" & precMAIN("人工呼吸器方式") & "',"
    strSQL = strSQL & "'" & precMAIN("人工呼吸器設定") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("気管カニューレ"))) & ","
    strSQL = strSQL & "'" & precMAIN("気管カニューレサイズ") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("吸引器"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("気管切開処置"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("経管栄養"))) & ","
    strSQL = strSQL & "'" & precMAIN("経管栄養方法") & "',"
    strSQL = strSQL & "'" & precMAIN("経管栄養サイズ") & "',"
    strSQL = strSQL & "'" & precMAIN("経管栄養交換") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("カテーテル"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("留置カテーテル"))) & ","
    strSQL = strSQL & "'" & precMAIN("留置カテーテルサイズ") & "',"
    strSQL = strSQL & "'" & precMAIN("留置カテーテル交換") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("ドレーン"))) & ","
    strSQL = strSQL & "'" & precMAIN("ドレーン部位") & "',"
    strSQL = strSQL & Abs(CInt(precMAIN("モニター測定"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("疼痛看護"))) & ","
    strSQL = strSQL & Abs(CInt(precMAIN("褥創処置"))) & ","
    strSQL = strSQL & "'" & precMAIN("装着医療機器他") & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("医師名") & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("医療機関名") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN_OTHER("医療機関郵便番号")) & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("医療機関住所") & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("医療機関電話局番") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN_OTHER("医療機関電話")) & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("医療機関FAX局番") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN_OTHER("医療機関FAX")) & "',"
    strSQL = strSQL & "'" & precMAIN_OTHER("携帯番号上") & "',"
    strSQL = strSQL & "'" & AddHyphen(precMAIN_OTHER("携帯番号")) & "',"
    strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
    strSQL = strSQL & ")"
    
    '移行処理実行
    Call FDBExecuteSQL(strSQL)

End Sub

Sub ConvGRAPHICS_COMMAND(pintEda As Long, precIKENSYO As Object, precIKENSYO_GRAPHIC As Object)
'============================================================================================
'GRAPHICS_COMMAND移行（全共通）
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
        strSQL = strSQL & precIKENSYO("患者番号") & ","
        strSQL = strSQL & pintEda & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("コマンド番号") & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("X座標") & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("Y座標") & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("部位") & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("SX座標") & ","
        strSQL = strSQL & precIKENSYO_GRAPHIC("SY座標") & ","
        strSQL = strSQL & "'" & precIKENSYO_GRAPHIC("文字列") & "',"
        strSQL = strSQL & "'" & Format(gNowDate, "yyyy/mm/dd hh:nn:ss") & "'"
        strSQL = strSQL & ")"
    
        '移行処理実行
        Call FDBExecuteSQL(strSQL)
        
        precIKENSYO_GRAPHIC.MoveNext
        
    Loop

End Sub

Function AddHyphen(pstrValue As String) As String
'============================================================================================
'郵便番号・電話番号にハイフンを付加する
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
'指定されたFDBファイルをデータの入っていないFDBファイルと置換する
'============================================================================================
Dim strPath As String
Dim strFileName As String
Dim strZeroFile As String

    ReplaceFDBFile = False
    
    strZeroFile = App.Path & "\CONVERT.DAT"
    
    'コピー元のファイルがあるかチェック
    If Dir(strZeroFile) = "" Then
        Call LogWrite(Error, "CONVERT.DATが見つかりません。")
        Exit Function
    End If

    On Error GoTo ERR_SECTION
        strPath = Left$(pstrFilePath, InStrRev(pstrFilePath, "\"))
        strFileName = Dir(pstrFilePath)
        strFileName = Left$(strFileName, InStrRev(strFileName, ".") - 1)
        
        'ファイルのバックアップを作成
        Name pstrFilePath As strPath & GetBackupFileName(strPath, 0)
        '空白のデータベースファイルをコピー
        Call FileCopy(strZeroFile, pstrFilePath)
    
        ReplaceFDBFile = True
    On Error GoTo 0

ERR_SECTION:
End Function

Function GetBackupFileName(pstrPath As String, pintCount) As String
'============================================================================================
'退避用のファイル名を取得する
'============================================================================================
Dim strFilaName As String

    If pintCount = 0 Then
        strFilaName = "convert.old"
    Else
        strFilaName = "convert" & CStr(pintCount) & ".old"
    End If
    
    '同名ファイルが存在する
    If Dir(pstrPath & strFilaName) <> "" Then
        GetBackupFileName = GetBackupFileName(pstrPath, pintCount + 1)
    Else
        GetBackupFileName = strFilaName
    End If

End Function

