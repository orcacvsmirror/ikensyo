VERSION 5.00
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "COMDLG32.OCX"
Begin VB.Form frmM1 
   BorderStyle     =   1  '�Œ�(����)
   Caption         =   "�㌩���f�[�^�ڍs�c�[�� Ver.1.2"
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
   StartUpPosition =   2  '��ʂ̒���
   Begin VB.Frame fraTo 
      Caption         =   "�ڍs��FDB�t�@�C��"
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
         Caption         =   "�����{����܂ރt�@�C���p�X�͎w�肵�Ȃ��ł��������B"
         ForeColor       =   &H000000FF&
         Height          =   270
         Left            =   210
         TabIndex        =   7
         Top             =   720
         Width           =   4290
      End
   End
   Begin VB.Frame fraFrom 
      Caption         =   "�ڍs��MDB�t�@�C��"
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
      Caption         =   "�J�n"
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
        .dlgM1.Filter = "Microsoft Office Access �f�[�^�x�[�X�i*.mdb�j|*.mdb"
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
        .dlgM1.Filter = "Firebird Database File �i*.fdb�j|*.fdb"
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
    
        '�J�n���b�Z�[�W
        'edit sta s-fujihara 2005/10/28 �f�[�^�x�[�X�t�@�C������̃t�@�C���ɒu�����鏈����ǉ�
        'If MsgBox("�f�[�^�x�[�X�ڍs�������J�n���Ă�낵���ł����H", vbYesNo + vbInformation, MSG_INFO) = vbNo Then
        If MsgBox("�f�[�^�x�[�X�ڍs�������J�n���Ă�낵���ł����H" & vbCrLf & "�i���ݓo�^����Ă�����͍폜����܂��B�j", vbYesNo + vbInformation, MSG_INFO) = vbNo Then
            Exit Sub
        End If
        'edit end s-fujihara 2005/10/28
        
        '���O�t�@�C���I�[�v��
        Call LogOpen
        
        '�����J�n
        Call LogWrite(Info, "======================�f�[�^�x�[�X�ڍs�����J�n======================")
    
        '�}�E�X�|�C���^���������ɕύX
        Screen.MousePointer = vbHourglass
    
        '�o�[�W�������擾
        intVersion = GetDBVersion()
        
        '�ڍs������ݒ�
        gNowDate = Now
        
        'edit sta s-fujihara 2005/10/28 �f�[�^�x�[�X�t�@�C������̃t�@�C���ɒu�����鏈����ǉ�
        '�o�[�W�����̎擾���s���Ă���΁A�t�@�C���̒u���������s��
        If intVersion <> DBVersion.UNKNOWN Then
            If Not basConvCommon.ReplaceFDBFile(frmM1.txtTo.Text) Then
                strMSG = "�f�[�^�x�[�X�t�@�C���̏������Ɏ��s���܂����B" & vbCrLf & "�㌩��Ver2.5�����s���Ă���ꍇ�͏I�����Ă��������B"
                Call MsgBox(strMSG, vbCritical, MSG_EXCEPTIONSYSTEM)
                Call LogWrite(Error, strMSG)
                '�}�E�X�|�C���^����ɖ߂�
                Screen.MousePointer = vbDefault
                '���O�t�@�C���N���[�Y
                Call LogClose
                '�I��
                Exit Sub
            End If
        End If
        'edit end s-fujihara 2005/10/28
        
        '�o�[�W�������Ƃɏ�����U�蕪��
        Select Case intVersion
            Case DBVersion.VER10
                '���O�o��
                Call LogWrite(Info, "�ڍs���㌩���o�[�W����:Ver1.0")
                'Ver1.0�ڍs����
                Call ConvVER10
            Case DBVersion.ver15
                '���O�o��
                Call LogWrite(Info, "�ڍs���㌩���o�[�W����:Ver1.5")
                'Ver1.5�ڍs����
                Call ConvVER15
            Case DBVersion.VER153
                '���O�o��
                Call LogWrite(Info, "�ڍs���㌩���o�[�W����:Ver1.5.3")
                'Ver1.5.3�ڍs����
                Call ConvVER153
            Case DBVersion.VER161
                '���O�o��
                Call LogWrite(Info, "�ڍs���㌩���o�[�W����:Ver1.6.1")
                'Ver1.6.1�ڍs����
                Call ConvVER161
            Case DBVersion.UNKNOWN
                strMSG = "�ڍs���̈ӌ����V�X�e���̃o�[�W�������ʂɎ��s���܂����B"
                Call MsgBox(strMSG, vbCritical, MSG_EXCEPTIONSYSTEM)
                Call LogWrite(Error, strMSG)
                '�}�E�X�|�C���^����ɖ߂�
                Screen.MousePointer = vbDefault
                '���O�t�@�C���N���[�Y
                Call LogClose
                '�I��
                Exit Sub
        End Select
    
        '�}�E�X�|�C���^����ɖ߂�
        Screen.MousePointer = vbDefault
        
        '��������
        Call LogWrite(Info, "======================�f�[�^�x�[�X�ڍs��������======================")
        
        '���O�t�@�C���N���[�Y
        Call LogClose
        
        If gClearFinish = True Then
            '�I�����b�Z�[�W
            Call MsgBox("�f�[�^�x�[�X�ڍs�������������܂����B", vbInformation, MSG_INFO)
        End If
    
    End If

End Sub

Private Sub Form_Load()
Dim ini As String

    'INI�t�@�C���p�X�擾
    gIniFilePath = VB.App.Path & "\" & VB.App.EXEName & ".ini"

    'INI�t�@�C�����݊m�F
    If Dir(gIniFilePath) = "" Then
        Call MsgBox("INI�t�@�C�����������Ă��������B", vbCritical, MSG_EXCEPTIONSYSTEM)
        Unload Me
    End If

    '�R�}���h���C���擾
    gCommandLine = Command()
    If Not Trim$(gCommandLine) = "" Then
        '�ڍs��t�@�C�����ɐݒ�
        Me.txtTo.Text = Trim$(gCommandLine)
    End If

    'FDB���[�U���擾
    ini = String(511, Chr$(0))
    Call GetPrivateProfileString("FDB", "User", "", ini, Len(ini) + 1, gIniFilePath)
    gFdbUser = Trim$(Replace(ini, Chr$(0), ""))

    'FDB�p�X���[�h�擾
    ini = String(511, Chr$(0))
    Call GetPrivateProfileString("FDB", "Password", "", ini, Len(ini) + 1, gIniFilePath)
    gFdbPassword = Trim$(Replace(ini, Chr$(0), ""))
    
    
    'add start shin fujihara 20060227
    'FDB�t�@�C���̃f�t�H���g��ݒ�
    frmM1.txtTo.Text = getFDBFilePath()
    'add end shin fujihara 20060227

End Sub

'add start shin fujihara 20060227
Private Function getFDBFilePath() As String
'�t�@�C���̑��݊m�F
Dim strFilename As String
' �e�L�X�g��ǂ݂���
Dim fn As Integer
Dim strLine As String
Dim intI As Integer
Dim blnDBConfig As Boolean
Dim strDBFilePath As String

    strFilename = App.Path & "\IkensyoProperityXML.xml"
    
    If Dir(strFilename, vbNormal Or vbSystem Or vbReadOnly Or vbArchive Or vbHidden) = "" Then
        ' ���݂��Ȃ�
        strDBFilePath = "C:\Program Files\Ikensyo2.5\data\Ikensyo.fdb"
    Else
        ' ���݂���
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
