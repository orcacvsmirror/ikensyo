Attribute VB_Name = "basAPI"
Option Explicit

'NOTIFYICONDATAやShell_NotifyIconで使用する定数
Public Const NIM_ADD = &H0              'アイコンを追加
Public Const NIM_DELETE = &H2           'アイコンを削除
Public Const NIF_ICON = &H2             'アイコンを表示
Public Const NIF_MESSAGE = &H1          'メッセージを返す
Public Const NIF_TIP = &H4              'ツールチップを表示

'WMメッセージ
Public Const WM_MOUSEMOVE = &H200       'マウスが動いた
Public Const WM_LBUTTONDOWN = &H201     '左ボタンダウン
Public Const WM_LBUTTONUP = &H202       '左ボタンアップ
Public Const WM_LBUTTONDBLCLK = &H203   '左ダブルクリック
Public Const WM_RBUTTONDOWN = &H204     '右ボタンダウン
Public Const WM_RBUTTONUP = &H205       '右ボタンアップ
Public Const WM_RBUTTONDBLCLK = &H206   '右ダブルクリック

'Paivate定数
Private Const MAX_PATH = 260

Declare Function Shell_NotifyIcon Lib "shell32.dll" Alias _
    "Shell_NotifyIconA" (ByVal dwMessage As Long, lpData _
    As NOTIFYICONDATA) As Long
 
Type NOTIFYICONDATA
    cbSize                  As Long
    hwnd                    As Long             'Formのウィンドウハンドル
    uID                     As Long
    uFlags                  As Long             'フラグ
    uCallbackMessage        As Long
    hIcon                   As Long             '表示するアイコンのハンドル
    szTip(64)               As Byte             'ツールチップに表示する文字列
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
    ' strFileName : フルパスのファイル名
    ' 戻り値      : ファイル名だけが返る。
    Dim strBuffer   As String
    Dim lngResult   As Long
    Dim bytStr()    As Byte

    lngResult = PathFindFileName(strFileName)
    If lngResult <> 0 Then
        ' (MAX_PATH + 1)のバイト配列を用意する。
        ReDim bytStr(MAX_PATH + 1) As Byte
        ' 確保したバイト配列に得られた位置のメモリをコピーする。
        MoveMemory bytStr(0), ByVal lngResult, MAX_PATH + 1
        ' 配列を文字列に変換する。
        strBuffer = StrConv(bytStr(), vbUnicode)
        ' NULL文字までを切り出す。
        FindFileName = Left$(strBuffer, InStr(strBuffer, vbNullChar) - 1)
    End If
    
End Function
