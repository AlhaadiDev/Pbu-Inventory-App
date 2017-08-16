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
	

	Dim ID As Int
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Dim PnlBarang As Panel
	
	Dim LblKode As Label
	Dim LblNama As Label
	Dim LblKeterangan As Label
	Dim Lblkuantiti As Label
	Dim Lblstatus As Label
	
	Dim EdtKode As EditText
	Dim EdtNama As EditText
	Dim EdtKeterangan As EditText
	Dim EdtKuantiti As EditText
	Dim Edtstatus As EditText
	
	Dim BtnSave As Button
	Dim BtnDelete As Button
	
	Dim AC As AppCompat
	Dim ABHelper As ACActionBar
	Private pContent As Panel
Private ActionBar As ACToolBarLight
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	
		Activity.LoadLayout("main")
pContent.LoadLayout("create_items")
	ActionBar.Title = "CREATE ITEMS JSKK"
	ActionBar.SubTitle = ""
		ABHelper.Initialize
ABHelper.ShowUpIndicator = True
ActionBar.InitMenuListener



	InitObject
	
	SetLabel

	LoadBarang

	SetObjectView
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub InitObject
	LblKode.Initialize("")
	LblNama.Initialize("")
	LblKeterangan.Initialize("")
	Lblkuantiti.Initialize("")
	Lblstatus.Initialize("")

	EdtKode.Initialize("EdtKode")
	EdtNama.Initialize("EdtNama")
	EdtKeterangan.Initialize("EdtKeterangan")
	EdtKuantiti.Initialize("EdtKuantiti")
	Edtstatus.Initialize("EdtKuantiti")

	BtnSave.Initialize("BtnSave")
	BtnDelete.Initialize("BtnDelete")
	
	PnlBarang.Initialize("PnlBarang")
End Sub

Sub SetLabel
	LblKode.Text = "Code"
	LblNama.Text = "Name Items"
	LblKeterangan.Text = "Details Items"
	Lblkuantiti.Text = "Quantity Items"
	Lblstatus.Text = "Status Items"
	
	
	EdtKode.Enabled = False
	

	If ID = -1 Then
		BtnSave.Text = "Save"
		BtnDelete.Enabled = False
	Else
		BtnSave.Text = "Update"
		BtnDelete.Text = True
	End If
	BtnDelete.Text = "Delete"
End Sub

Sub LoadBarang

	Dim query As String
	query = " SELECT * FROM " & Login.BarangTable & " WHERE id = ?"

	Dim m As Map
	m = DbUtils.ExecuteMap(Login.SQL, query, Array As String(ID))

	If m.IsInitialized = False Then	
		If ID = -1 Then
			Dim kode As String = GenerateKode
			EdtKode.Text = kode
			EdtNama.Text = ""
			EdtKeterangan.Text = ""
			EdtKuantiti.Text = ""
			Edtstatus.Text = ""
		End If

	Else		
		If m.Get("kode") <> Null Then
		    EdtKode.Text = m.Get("kode")
		End If
		
		If m.Get("nama") <> Null Then 
			Activity.Title = "View: " & m.Get("nama")
		    EdtNama.Text = m.Get("nama")
		End If
		
		If m.Get("keterangan") <> Null Then
		    EdtKeterangan.Text = m.Get("keterangan")
		End If
		
		If m.Get("kuantiti") <> Null Then
		    EdtKuantiti.Text = m.Get("kuantiti")
		End If
		
		If m.Get("status") <> Null Then
		    Edtstatus.Text = m.Get("status")
		End If
	End If
End Sub

Sub SetObjectView	
	Dim ctop As Int = 20dip
	Dim labelHeight As Int = 30dip
	Dim textHeight As Int = 40dip
	

	PnlBarang.AddView(LblKode, 20dip, ctop, 100%x-40dip, 30dip) : ctop = ctop + labelHeight
	PnlBarang.AddView(EdtKode, 20dip, ctop, 100%x-40dip, 40dip) : ctop = ctop + textHeight	

	PnlBarang.AddView(LblNama, 20dip, ctop, 100%x-40dip, 30dip) : ctop = ctop + labelHeight
	PnlBarang.AddView(EdtNama, 20dip, ctop, 100%x-40dip, 40dip) : ctop = ctop + textHeight
	
	PnlBarang.AddView(Lblkuantiti, 20dip, ctop, 100%x-40dip, 30dip) : ctop = ctop + labelHeight
	PnlBarang.AddView(EdtKuantiti, 20dip, ctop, 100%x-40dip, 40dip) : ctop = ctop + textHeight
	
	PnlBarang.AddView(LblKeterangan, 20dip, ctop, 100%x-40dip, 30dip) : ctop = ctop + labelHeight
	PnlBarang.AddView(EdtKeterangan, 20dip, ctop, 100%x-40dip, 40dip) : ctop = ctop + textHeight
	
	PnlBarang.AddView(Lblstatus, 20dip, ctop, 100%x-40dip, 30dip) : ctop = ctop + labelHeight
	PnlBarang.AddView(Edtstatus, 20dip, ctop, 100%x-40dip, 40dip) : ctop = ctop + textHeight
	

	Activity.AddView(PnlBarang, 0,0,100%x, 85%y)
	Activity.AddView(BtnSave, 0, 85%y, 50%x, 15%y)
	Activity.AddView(BtnDelete, 50%x, 85%y, 50%x, 15%y)	
End Sub

Sub GenerateKode
	Dim q As String = "select id from " & Login.BarangTable	
	Dim qs As List = DbUtils.ExecuteMemoryTable(Login.SQL, q, Null, 0)
	
	Dim count As String = qs.Size + 1
	If count < 10 Then
		count = "000" & count
	Else If count < 100 Then
		count = "00" & count
	Else If count < 1000 Then
		count = "000" & count
	Else
		count = count
	End If
	
	Log("count: " & count)
	
	Dim kd As String = "ITEM-" & count
	
	Return kd
End Sub

Sub BtnSave_Click()
	Dim listOfMaps As List : listOfMaps.Initialize
	Dim m As Map : m.Initialize
	m.put("kode", EdtKode.Text)
	m.put("nama", EdtNama.Text)	
	m.put("keterangan", EdtKeterangan.Text)
	m.put("kuantiti", EdtKuantiti.Text)
	m.put("status", Edtstatus.Text)
	listOfMaps.Add(m)
	

	If ID = -1 Then
		DbUtils.InsertMaps(Login.SQL, Login.BarangTable, listOfMaps)
		ToastMessageShow("Barang has been created.", True)

		Activity.Finish
	Else
		Dim w As Map : w.Initialize
		w.Put("id", ID)
		DbUtils.UpdateRecord2(Login.SQL, Login.BarangTable, m, w)
		ToastMessageShow("Barang has been updated.", True)

		LoadBarang
		Activity.Finish
	End If	
End Sub

Sub BtnDelete_Click()
	Dim result As Int = Msgbox2( "Delete : " & EdtNama.Text & "?", "Confirmation", "Yes", "No", "", _ 
	    LoadBitmap (File.DirAssets, "confirm.png"))	
	If result = DialogResponse.Positive Then
		Dim w As Map : w.Initialize	
		w.Put("id", ID)
		DbUtils.DeleteRecord(Login.SQL, Login.BarangTable , w)
		ToastMessageShow("Item has been deleted.", True)
		Activity.Finish
	End If
End Sub

Sub ActionBar_NavigationItemClick
   Activity.Finish
End Sub