Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region
#Extends: android.support.v7.app.AppCompatActivity
Sub Process_Globals

	Dim SQL As SQL

	Dim DBDir As String : DBDir = File.DirDefaultExternal

	Dim DBName As String : DBName = "jskk.db"

	Dim UserTable As String = "user"
	Dim BarangTable As String = "barang"
	Public intro,intro2 As MediaPlayer
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Private EdtUsername As EditText
	Private EdtPassword As EditText
	Private BtnLogin As Button
	
		Dim AC As AppCompat
	Dim ABHelper As ACActionBar
	Private pContent As Panel
Private ActionBar As ACToolBarLight

	Dim a As ICOSFlip3DView
	Private ImageView1 As ImageView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	
	
	Activity.LoadLayout("main")
	pContent.LoadLayout("login")
	ActionBar.Title = "LOGIN"
	ActionBar.SubTitle = ""
	a.Flip3DView("a",ImageView1,180,2000,a.FLIP_HORIZONTAL)
	a.StartAnim(ImageView1)
	a.AutoRepeat
	intro.Initialize2("intro")
	intro.Load(File.DirAssets, "enterauthorizationcode.mp3")
	intro.Play
	
	intro2.Initialize2("intro2")
	intro2.Load(File.DirAssets, "access_granted.mp3")
	
	Activity.AddMenuItem("Exit", "Menu")
	EdtUsername.RequestFocus

	If FirstTime Then	
	
		If File.Exists(DBDir, DBName) = False Then
			File.Copy(File.DirAssets, DBName, DBDir, DBName)
		End If
		

		SQL.Initialize(DBDir, DBName, True)
	End If
End Sub

Sub Activity_Resume
	EdtUsername.RequestFocus
	EdtUsername.Text=""
	EdtPassword.Text=""

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub BtnLogin_Click

	If EdtUsername.Text = "" Then
		

				
		Msgbox("Please enter Username", "Warning")
		Return
	End If

	If EdtPassword.Text = "" Then
		Msgbox("Please enter Password", "Warning")
		Return
	End If

	Dim m As Map = CheckLogin(EdtUsername.Text, EdtPassword.Text) 
	

	If m.IsInitialized = True Then
		intro2.Play
		Msgbox("Hye, " & m.Get("username") & CRLF & _
		"Welcome to PBUSCAPP", "SUCCESS")
		StartActivity(Home)		
	Else
		Msgbox("Username @ Password is wrong", "Failed Login")
	End If
End Sub

Sub CheckLogin(Username As String, Password As String) As Map
	Dim Query As String
	Query = "select * from " & UserTable & " where username = ? and password = ?"
	Dim M As Map = DbUtils.ExecuteMap(SQL, Query, Array As String(Username, Password))
	Return M
End Sub

Sub Menu_Click()
Dim bmp As Bitmap
Dim choice As Int
bmp.Initialize(File.DirAssets, "help.png")
choice = Msgbox2(" Quit now ?", "Comfirmation ", "Yes", "", "No", bmp)
If choice = DialogResponse.POSITIVE Then 
ExitApplication 
  
End If  
End Sub
