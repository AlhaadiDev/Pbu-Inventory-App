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
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.


End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim AC As AppCompat
	Dim ABHelper As ACActionBar
	Private pContent As Panel
Private ActionBar As ACToolBarLight

	Dim scvTest As ScrollView
	Dim pnlTest As Panel
End Sub

Sub Activity_Create(FirstTime As Boolean)

Activity.LoadLayout("main")
	
	scvTest.Panel.LoadLayout("about")
	scvTest.Panel.Height = pnlTest.Height
	
	
	
	
		ActionBar.Title = "ABOUT JSKK"
	ActionBar.SubTitle = ""
	ABHelper.Initialize
ABHelper.ShowUpIndicator = True
ActionBar.InitMenuListener


End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ActionBar_NavigationItemClick
   Activity.Finish
End Sub

Sub edtItem_FocusChanged (HasFocus As Boolean)
	Dim Send As EditText
	
	If HasFocus Then
		Send = Sender
		
		scvTest.ScrollPosition = Send.Top - 10dip
	End If
End Sub
