Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region Module Attributes
	#FullScreen: False
	#IncludeTitle: True
#End Region
#Extends: android.support.v7.app.AppCompatActivity

Sub Process_Globals

Public intro,intro2 As MediaPlayer

End Sub

Sub Globals


		Dim AC As AppCompat
	Dim ABHelper As ACActionBar
	Private pContent As Panel
Private ActionBar As ACToolBarLight
Dim BtnBarang As Button

	Private btnLogOut As Button
	Private btnImage As Button
	Private btnAbout As Button
	Private btnItem As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)

	
		
	Activity.LoadLayout("main")
	pContent.LoadLayout("menu")
	ActionBar.Title = "MENU"
	ActionBar.SubTitle = ""
	Activity.AddMenuItem("About Me", "Menu")
	
	intro.Initialize2("intro")
	intro.Load(File.DirAssets, "wlcome.mp3")
	intro.Play
	
	intro2.Initialize2("intro2")
	intro2.Load(File.DirAssets, "enterauthorizationcode.mp3")
End Sub



Sub Activity_Resume
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	
End Sub


Sub Menu_Click()
Msgbox("PBUSCAPP Develop By Nazrul Wazir @ 2016", "About Me")
End Sub

Sub btnLogOut_Click
	Dim bmp As Bitmap
Dim choice As Int
bmp.Initialize(File.DirAssets, "help.png")
choice = Msgbox2(" Log Out now ?", "Comfirmation ", "Yes", "", "No", bmp)
If choice = DialogResponse.POSITIVE Then 
		intro2.Play
Activity.Finish
End If
End Sub

Sub btnImage_Click
	StartActivity(image)
End Sub

Sub btnAbout_Click
	StartActivity(about)
End Sub

Sub btnItem_Click
	StartActivity(InventoryList)
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean
  If KeyCode = KeyCodes.KEYCODE_BACK  Then  
 
    Select Msgbox2("Log Out Now?", "", "OK", "","Cancel", Null)
    Case DialogResponse.NEGATIVE
      Return True
    Case DialogResponse.CANCEL
      Return True
    Case DialogResponse.POSITIVE
		intro2.Play
	 Msgbox("Thank You!","")
	Activity.Finish
    End Select

  End If
  End Sub