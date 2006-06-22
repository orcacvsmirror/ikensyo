VERSION 5.00
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "COMDLG32.OCX"
Begin VB.Form frmM1 
   BorderStyle     =   1  '固定(実線)
   Caption         =   "医見書データ移行ツール Ver.1.2"
   ClientHeight    =   2115
   ClientLeft      =   45
   ClientTop       =   435
   ClientWidth     =   7365
   Icon            =   "frmM1.frx":0000
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   2115
   ScaleWidth      =   7365
   StartUpPosition =   2  '画面の中央
   Begin VB.Frame fraTo 
      Caption         =   "移行先FDBファイル"
      Height          =   1035
      Left            =   120
      TabIndex        =   3
      Top             =   960
      Width           =   4815
      Begin VB.CommandButton btnTo 
         Caption         =   "..."
         Height          =   375
         Left            =   4200
         Picture         =   "frmM1.frx":08CA
         TabIndex        =   6
         Top             =   240
         Width           =   375
      End
      Begin VB.TextBox txtTo 
         Height          =   375
         Left            =   240
         TabIndex        =   4
         Top             =   240
         Width           =   3975
      End
      Begin VB.Label lblInfo 
         Caption         =   "※日本語を含むファイルパスは指定しないでください。"
         ForeColor       =   &H000000FF&
         Height          =   270
         Left            =   210
         TabIndex        =   7
         Top             =   720
         Width           =   4290
      End
   End
   Begin VB.Frame fraFrom 
      Caption         =   "移行元MDBファイル"
      Height          =   735
      Left            =   120
      TabIndex        =   1
      Top             =   120
      Width           =   4815
      Begin VB.CommandButton btnFrom 
         Caption         =   "..."
         Height          =   375
         Left            =   4200
         Picture         =   "frmM1.frx":120E
         TabIndex        =   5
         Top             =   240
         Width           =   375
      End
      Begin VB.TextBox txtFrom 
         Height          =   375
         Left            =   240
         TabIndex        =   2
         Top             =   240
         Width           =   3975
      End
   End
   Begin VB.CommandButton btnStart 
      Caption         =   "開始"
      Height          =   1890
      Left            =   5040
      Picture         =   "frmM1.frx":1B52
      TabIndex        =   0
      Top             =   120
      Width           =   2175
   End
   Begin MSComDlg.CommonDialog dlgM1 
      Left            =   0
      Top             =   0
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   393216
   End
End
Attribute VB_Name = "frmM1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private Sub btnFrom_Click()

On Error GoTo Err_Cancel

    With frmM1
    
        .dlgM1.FileName = ""
        .dlgM1.CancelError = True
        .dlgM1.Filter = "Microsoft Office Access データベース（*.mdb）|*.mdb"
        .dlgM1.Flags = &H4
        .dlgM1.ShowOpen
        
        .txtFrom.Text = .dlgM1.FileName
    
    End With
    
    Exit Sub
    
Err_Cancel:

End Sub

Private Sub btnTo_Click()

On Error GoTo Err_Cancel

    With frmM1
    
        .dlgM1.FileName = ""
        .dlgM1.CancelError = True
        .dlgM1.Filter = "Firebird Database File （*.fdb）|*.fdb"
        .dlgM1.Flags = &H4
        .dlgM1.ShowOpen
        
        .txtTo.Text = .dlgM1.FileName
    
    End With
    
    Exit Sub

Err_Cancel:

End Sub

Private Sub btnStart_Click()
Dim intVersion As Integer
Dim strMSG As String

    If MainCheck() Then
    
        '開始メッセージ
        'edit sta s-fujihara 2005/10/28 データベースファイルを空のファイルに置換する処理を追加
        'If MsgBox("データベース移行処理を開始してよろしいですか？", vbYesNo + vbInformation, MSG_INFO) = vbNo Then
        If MsgBox("データベース移行処理を開始してよろしいですか？" & vbCrLf & "（現在登録されている情報は削除されます。）", vbYesNo + vbInformation, MSG_INFO) = vbNo Then
            Exit Sub
        End If
        'edit end s-fujihara 2005/10/28
        
        'ログファイルオープン
        Call LogOpen
        
        '処理開始
        Call LogWrite(Info, "======================データベース移行処理開始======================")
    
        'マウスポインタを処理中に変更
        Screen.MousePointer = vbHourglass
    
        'バージョン情報取得
        intVersion = GetDBVersion()
        
        '移行日時を設定
        gNowDate = Now
        
        'edit sta s-fujihara 2005/10/28 データベースファイルを空のファイルに置換する処理を追加
        'バージョンの取得が行えていれば、ファイルの置換処理を行う
        If intVersion <> DBVersion.UNKNOWN Then
            If Not basConvCommon.ReplaceFDBFile(frmM1.txtTo.Text) Then
                strMSG = "データベースファイルの初期化に失敗しました。" & vbCrLf & "医見書Ver2.5を実行している場合は終了してください。"
                Call MsgBox(strMSG, vbCritical, MSG_EXCEPTIONSYSTEM)
                Call LogWrite(Error, strMSG)
                'マウスポインタ正常に戻す
                Screen.MousePointer = vbDefault
                'ログファイルクローズ
                Call LogClose
                '終了
                Exit Sub
            End If
        End If
        'edit end s-fujihara 2005/10/28
        
        'バージョンごとに処理を振り分け
        Select Case intVersion
            Case DBVersion.VER10
                'ログ出力
                Call LogWrite(Info, "移行元医見書バージョン:Ver1.0")
                'Ver1.0移行処理
                Call ConvVER10
            Case DBVersion.ver15
                'ログ出力
                Call LogWrite(Info, "移行元医見書バージョン:Ver1.5")
                'Ver1.5移行処理
                Call ConvVER15
            Case DBVersion.VER153
                'ログ出力
                Call LogWrite(Info, "移行元医見書バージョン:Ver1.5.3")
                'Ver1.5.3移行処理
                Call ConvVER153
            Case DBVersion.VER161
                'ログ出力
                Call LogWrite(Info, "移行元医見書バージョン:Ver1.6.1")
                'Ver1.6.1移行処理
                Call ConvVER161
            Case DBVersion.UNKNOWN
                strMSG = "移行元の意見書システムのバージョン判別に失敗しました。"
                Call MsgBox(strMSG, vbCritical, MSG_EXCEPTIONSYSTEM)
                Call LogWrite(Error, strMSG)
                'マウスポインタ正常に戻す
                Screen.MousePointer = vbDefault
                'ログファイルクローズ
                Call LogClose
                '終了
                Exit Sub
        End Select
    
        'マウスポインタ正常に戻す
        Screen.MousePointer = vbDefault
        
        '処理完了
        Call LogWrite(Info, "======================データベース移行処理完了======================")
        
        'ログファイルクローズ
        Call LogClose
        
        If gClearFinish = True Then
            '終了メッセージ
            Call MsgBox("データベース移行処理が完了しました。", vbInformation, MSG_INFO)
        End If
    
    End If

End Sub

Private Sub Form_Load()
Dim ini As String

    'INIファイルパス取得
    gIniFilePath = VB.App.Path & "\" & VB.App.EXEName & ".ini"

    'INIファイル存在確認
    If Dir(gIniFilePath) = "" Then
        Call MsgBox("INIファイルを準備してください。", vbCritical, MSG_EXCEPTIONSYSTEM)
        Unload Me
    End If

    'コマンドライン取得
    gCommandLine = Command()
    If Not Trim$(gCommandLine) = "" Then
        '移行先ファイル名に設定
        Me.txtTo.Text = Trim$(gCommandLine)
    End If

    'FDBユーザ名取得
    ini = String(511, Chr$(0))
    Call GetPrivateProfileString("FDB", "User", "", ini, Len(ini) + 1, gIniFilePath)
    gFdbUser = Trim$(Replace(ini, Chr$(0), ""))

    'FDBパスワード取得
    ini = String(511, Chr$(0))
    Call GetPrivateProfileString("FDB", "Password", "", ini, Len(ini) + 1, gIniFilePath)
    gFdbPassword = Trim$(Replace(ini, Chr$(0), ""))
    
    
    'add start shin fujihara 20060227
    'FDBファイルのデフォルトを設定
    frmM1.txtTo.Text = getFDBFilePath()
    'add end shin fujihara 20060227

End Sub

'add start shin fujihara 20060227
Private Function getFDBFilePath() As String
'ファイルの存在確認
Dim strFilename As String
' テキストを読みこむ
Dim fn As Integer
Dim strLine As String
Dim intI As Integer
Dim blnDBConfig As Boolean
Dim strDBFilePath As String

    strFilename = App.Path & "\IkensyoProperityXML.xml"
    
    If Dir(strFilename, vbNormal Or vbSystem Or vbReadOnly Or vbArchive Or vbHidden) = "" Then
        ' 存在しない
        strDBFilePath = "C:\Program Files\Ikensyo2.5\data\Ikensyo.fdb"
    Else
        ' 存在する
        blnDBConfig = False
        fn = FreeFile
        Open strFilename For Input As #fn
            Do Until EOF(fn)
                Line Input #fn, strLine
                If InStr(1, strLine, "<properities id=""DBConfig"">") <> 0 Then
                    blnDBConfig = True
                End If
                
                If blnDBConfig Then
                    If InStr(1, strLine, "<properity id=""Path"">") <> 0 Then
                        strDBFilePath = strLine
                        strDBFilePath = Replace(strDBFilePath, "<properity id=""Path"">", "")
                        strDBFilePath = Replace(strDBFilePath, "</properity>", "")
                        strDBFilePath = Replace(strDBFilePath, "/", "\")
                    End If
                End If
            Loop
        Close #fn
    End If
    
    getFDBFilePath = strDBFilePath
End Function
'add end shin fujihara 20060227

Private Sub Form_QueryUnload(Cancel As Integer, UnloadMode As Integer)

    Unload Me

End Sub
