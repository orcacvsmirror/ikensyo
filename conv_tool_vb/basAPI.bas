Attribute VB_Name = "basAPI"
Option Explicit

'NOTIFYICONDATA��Shell_NotifyIcon�Ŏg�p����萔
Public Const NIM_ADD = &H0              '�A�C�R����ǉ�
Public Const NIM_DELETE = &H2           '�A�C�R�����폜
Public Const NIF_ICON = &H2             '�A�C�R����\��
Public Const NIF_MESSAGE = &H1          '���b�Z�[�W��Ԃ�
Public Const NIF_TIP = &H4              '�c�[���`�b�v��\��

'WM���b�Z�[�W
Public Const WM_MOUSEMOVE = &H200       '�}�E�X��������
Public Const WM_LBUTTONDOWN = &H201     '���{�^���_�E��
Public Const WM_LBUTTONUP = &H202       '���{�^���A�b�v
Public Const WM_LBUTTONDBLCLK = &H203   '���_�u���N���b�N
Public Const WM_RBUTTONDOWN = &H204     '�E�{�^���_�E��
Public Const WM_RBUTTONUP = &H205       '�E�{�^���A�b�v
Public Const WM_RBUTTONDBLCLK = &H206   '�E�_�u���N���b�N

'Paivate�萔
Private Const MAX_PATH = 260

Declare Function Shell_NotifyIcon Lib "shell32.dll" Alias _
    "Shell_NotifyIconA" (ByVal dwMessage As Long, lpData _
    As NOTIFYICONDATA) As Long
 
Type NOTIFYICONDATA
    cbSize                  As Long
    hwnd                    As Long             'Form�̃E�B���h�E�n���h��
    uID                     As Long
    uFlags                  As Long             '�t���O
    uCallbackMessage        As Long
    hIcon                   As Long             '�\������A�C�R���̃n���h��
    szTip(64)               As Byte             '�c�[���`�b�v�ɕ\�����镶����
End Type

Public Declare Function WritePrivateProfileString _
Lib "kernel32" Alias "WritePrivateProfileStringA" ( _
    ByVal lpApplicationName As String, _
    ByVal lpKeyName As Any, _
    ByVal lpString As Any, _
    ByVal lpFileName As String _
) As Long

Public Declare Function GetPrivateProfileString _
Lib "kernel32" Alias "GetPrivateProfileStringA" ( _
    ByVal lpApplicationName As String, _
    ByVal lpKeyName As Any, _
    ByVal lpDefault As String, _
    ByVal lpReturnedString As String, _
    ByVal nSize As Long, _
    ByVal lpFileName As String _
) As Long

Public Declare Function SetForegroundWindow _
Lib "user32" ( _
    ByVal hwnd As Long _
) As Long

Public Declare Function PathFindFileName _
Lib "SHLWAPI.DLL" Alias "PathFindFileNameA" _
    (ByVal pszPath As String _
) As Long

Private Declare Sub MoveMemory _
Lib "kernel32" Alias "RtlMoveMemory" _
    (pDest As Any, _
     pSource As Any, _
     ByVal ByteLen As Long)
     
Public Declare Sub Sleep _
Lib "kernel32" (ByVal dwMilliseconds As Long)

Public Function FindFileName(ByVal strFileName As String) As String
    ' strFileName : �t���p�X�̃t�@�C����
    ' �߂�l      : �t�@�C�����������Ԃ�B
    Dim strBuffer   As String
    Dim lngResult   As Long
    Dim bytStr()    As Byte

    lngResult = PathFindFileName(strFileName)
    If lngResult <> 0 Then
        ' (MAX_PATH + 1)�̃o�C�g�z���p�ӂ���B
        ReDim bytStr(MAX_PATH + 1) As Byte
        ' �m�ۂ����o�C�g�z��ɓ���ꂽ�ʒu�̃��������R�s�[����B
        MoveMemory bytStr(0), ByVal lngResult, MAX_PATH + 1
        ' �z��𕶎���ɕϊ�����B
        strBuffer = StrConv(bytStr(), vbUnicode)
        ' NULL�����܂ł�؂�o���B
        FindFileName = Left$(strBuffer, InStr(strBuffer, vbNullChar) - 1)
    End If
    
End Function
