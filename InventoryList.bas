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

	Dim LvBarang As ListView

	Dim BtnCreate As Button
	
	Dim AC As AppCompat
	Dim ABHelper As ACActionBar
	Private pContent As Panel
Private ActionBar As ACToolBarLight
End Sub

Sub Activity_Create(FirstTime As Boolean)


		Activity.LoadLayout("main")
pContent.LoadLayout("items")
	ActionBar.Title = "ITEMS JSKK"
	ActionBar.SubTitle = ""
		ABHelper.Initialize
ABHelper.ShowUpIndicator = True
ActionBar.InitMenuListener

	

	LvBarang.Initialize("LvBarang")

	BtnCreate.Initialize("BtnCreate")

	BtnCreate.Text = "Create Item"

	pContent.AddView(LvBarang, 0,0,100%x,85%y)

End Sub

Sub Activity_Resume
	'Refresh data
	FillLvBarang
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub FillLvBarang

	query = " SELECT kode, nama, id FROM " & Login.BarangTable
	DbUtils.ExecuteListView(Login.SQL, query, Null, 0, LvBarang, True)
End Sub

Sub BtnCreate_Click

	InventoryView.ID = -1
	StartActivity(InventoryView)
End Sub

Sub LvBarang_ItemClick (Position As Int, Value As Object)
	Dim v(2) As String = Value

	InventoryView.ID = v(2)
	StartActivity(InventoryView)	
End Sub

Sub ActionBar_NavigationItemClick
   Activity.Finish
End Sub