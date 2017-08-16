package pbu.inventory.apps;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class inventoryview extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static inventoryview mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "pbu.inventory.apps", "pbu.inventory.apps.inventoryview");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (inventoryview).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "pbu.inventory.apps", "pbu.inventory.apps.inventoryview");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "pbu.inventory.apps.inventoryview", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (inventoryview) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (inventoryview) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return inventoryview.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (inventoryview) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (inventoryview) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        Object[] o;
        if (permissions.length > 0)
            o = new Object[] {permissions[0], grantResults[0] == 0};
        else
            o = new Object[] {"", false};
        processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static int _id = 0;
public anywheresoftware.b4a.objects.PanelWrapper _pnlbarang = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblkode = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnama = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblketerangan = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblkuantiti = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblstatus = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtkode = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtnama = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtketerangan = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtkuantiti = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtstatus = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsave = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndelete = null;
public de.amberhome.objects.appcompat.AppCompatBase _ac = null;
public de.amberhome.objects.appcompat.ACActionBar _abhelper = null;
public anywheresoftware.b4a.objects.PanelWrapper _pcontent = null;
public de.amberhome.objects.appcompat.ACToolbarLightWrapper _actionbar = null;
public pbu.inventory.apps.main _main = null;
public pbu.inventory.apps.login _login = null;
public pbu.inventory.apps.dbutils _dbutils = null;
public pbu.inventory.apps.inventorylist _inventorylist = null;
public pbu.inventory.apps.slidingpanels _slidingpanels = null;
public pbu.inventory.apps.home _home = null;
public pbu.inventory.apps.image _image = null;
public pbu.inventory.apps.about _about = null;
public pbu.inventory.apps.starter _starter = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _actionbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 243;BA.debugLine="Sub ActionBar_NavigationItemClick";
 //BA.debugLineNum = 244;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 245;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 46;BA.debugLine="Activity.LoadLayout(\"main\")";
mostCurrent._activity.LoadLayout("main",mostCurrent.activityBA);
 //BA.debugLineNum = 47;BA.debugLine="pContent.LoadLayout(\"create_items\")";
mostCurrent._pcontent.LoadLayout("create_items",mostCurrent.activityBA);
 //BA.debugLineNum = 48;BA.debugLine="ActionBar.Title = \"CREATE ITEMS JSKK\"";
mostCurrent._actionbar.setTitle((java.lang.CharSequence)("CREATE ITEMS JSKK"));
 //BA.debugLineNum = 49;BA.debugLine="ActionBar.SubTitle = \"\"";
mostCurrent._actionbar.setSubTitle((java.lang.CharSequence)(""));
 //BA.debugLineNum = 50;BA.debugLine="ABHelper.Initialize";
mostCurrent._abhelper.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 51;BA.debugLine="ABHelper.ShowUpIndicator = True";
mostCurrent._abhelper.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 52;BA.debugLine="ActionBar.InitMenuListener";
mostCurrent._actionbar.InitMenuListener();
 //BA.debugLineNum = 56;BA.debugLine="InitObject";
_initobject();
 //BA.debugLineNum = 58;BA.debugLine="SetLabel";
_setlabel();
 //BA.debugLineNum = 60;BA.debugLine="LoadBarang";
_loadbarang();
 //BA.debugLineNum = 62;BA.debugLine="SetObjectView";
_setobjectview();
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _btndelete_click() throws Exception{
int _result = 0;
anywheresoftware.b4a.objects.collections.Map _w = null;
 //BA.debugLineNum = 231;BA.debugLine="Sub BtnDelete_Click()";
 //BA.debugLineNum = 232;BA.debugLine="Dim result As Int = Msgbox2( \"Delete : \" & EdtNam";
_result = anywheresoftware.b4a.keywords.Common.Msgbox2("Delete : "+mostCurrent._edtnama.getText()+"?","Confirmation","Yes","No","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"confirm.png").getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 234;BA.debugLine="If result = DialogResponse.Positive Then";
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 235;BA.debugLine="Dim w As Map : w.Initialize";
_w = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 235;BA.debugLine="Dim w As Map : w.Initialize";
_w.Initialize();
 //BA.debugLineNum = 236;BA.debugLine="w.Put(\"id\", ID)";
_w.Put((Object)("id"),(Object)(_id));
 //BA.debugLineNum = 237;BA.debugLine="DbUtils.DeleteRecord(Login.SQL, Login.BarangTabl";
mostCurrent._dbutils._deleterecord(mostCurrent.activityBA,mostCurrent._login._sql,mostCurrent._login._barangtable,_w);
 //BA.debugLineNum = 238;BA.debugLine="ToastMessageShow(\"Item has been deleted.\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Item has been deleted.",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 239;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static String  _btnsave_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _listofmaps = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.Map _w = null;
 //BA.debugLineNum = 204;BA.debugLine="Sub BtnSave_Click()";
 //BA.debugLineNum = 205;BA.debugLine="Dim listOfMaps As List : listOfMaps.Initialize";
_listofmaps = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 205;BA.debugLine="Dim listOfMaps As List : listOfMaps.Initialize";
_listofmaps.Initialize();
 //BA.debugLineNum = 206;BA.debugLine="Dim m As Map : m.Initialize";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 206;BA.debugLine="Dim m As Map : m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 207;BA.debugLine="m.put(\"kode\", EdtKode.Text)";
_m.Put((Object)("kode"),(Object)(mostCurrent._edtkode.getText()));
 //BA.debugLineNum = 208;BA.debugLine="m.put(\"nama\", EdtNama.Text)";
_m.Put((Object)("nama"),(Object)(mostCurrent._edtnama.getText()));
 //BA.debugLineNum = 209;BA.debugLine="m.put(\"keterangan\", EdtKeterangan.Text)";
_m.Put((Object)("keterangan"),(Object)(mostCurrent._edtketerangan.getText()));
 //BA.debugLineNum = 210;BA.debugLine="m.put(\"kuantiti\", EdtKuantiti.Text)";
_m.Put((Object)("kuantiti"),(Object)(mostCurrent._edtkuantiti.getText()));
 //BA.debugLineNum = 211;BA.debugLine="m.put(\"status\", Edtstatus.Text)";
_m.Put((Object)("status"),(Object)(mostCurrent._edtstatus.getText()));
 //BA.debugLineNum = 212;BA.debugLine="listOfMaps.Add(m)";
_listofmaps.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 215;BA.debugLine="If ID = -1 Then";
if (_id==-1) { 
 //BA.debugLineNum = 216;BA.debugLine="DbUtils.InsertMaps(Login.SQL, Login.BarangTable,";
mostCurrent._dbutils._insertmaps(mostCurrent.activityBA,mostCurrent._login._sql,mostCurrent._login._barangtable,_listofmaps);
 //BA.debugLineNum = 217;BA.debugLine="ToastMessageShow(\"Barang has been created.\", Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Barang has been created.",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 219;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 221;BA.debugLine="Dim w As Map : w.Initialize";
_w = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 221;BA.debugLine="Dim w As Map : w.Initialize";
_w.Initialize();
 //BA.debugLineNum = 222;BA.debugLine="w.Put(\"id\", ID)";
_w.Put((Object)("id"),(Object)(_id));
 //BA.debugLineNum = 223;BA.debugLine="DbUtils.UpdateRecord2(Login.SQL, Login.BarangTab";
mostCurrent._dbutils._updaterecord2(mostCurrent.activityBA,mostCurrent._login._sql,mostCurrent._login._barangtable,_m,_w);
 //BA.debugLineNum = 224;BA.debugLine="ToastMessageShow(\"Barang has been updated.\", Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Barang has been updated.",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 226;BA.debugLine="LoadBarang";
_loadbarang();
 //BA.debugLineNum = 227;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _generatekode() throws Exception{
String _q = "";
anywheresoftware.b4a.objects.collections.List _qs = null;
String _count = "";
String _kd = "";
 //BA.debugLineNum = 182;BA.debugLine="Sub GenerateKode";
 //BA.debugLineNum = 183;BA.debugLine="Dim q As String = \"select id from \" & Login.Baran";
_q = "select id from "+mostCurrent._login._barangtable;
 //BA.debugLineNum = 184;BA.debugLine="Dim qs As List = DbUtils.ExecuteMemoryTable(Login";
_qs = new anywheresoftware.b4a.objects.collections.List();
_qs = mostCurrent._dbutils._executememorytable(mostCurrent.activityBA,mostCurrent._login._sql,_q,(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
 //BA.debugLineNum = 186;BA.debugLine="Dim count As String = qs.Size + 1";
_count = BA.NumberToString(_qs.getSize()+1);
 //BA.debugLineNum = 187;BA.debugLine="If count < 10 Then";
if ((double)(Double.parseDouble(_count))<10) { 
 //BA.debugLineNum = 188;BA.debugLine="count = \"000\" & count";
_count = "000"+_count;
 }else if((double)(Double.parseDouble(_count))<100) { 
 //BA.debugLineNum = 190;BA.debugLine="count = \"00\" & count";
_count = "00"+_count;
 }else if((double)(Double.parseDouble(_count))<1000) { 
 //BA.debugLineNum = 192;BA.debugLine="count = \"000\" & count";
_count = "000"+_count;
 }else {
 //BA.debugLineNum = 194;BA.debugLine="count = count";
_count = _count;
 };
 //BA.debugLineNum = 197;BA.debugLine="Log(\"count: \" & count)";
anywheresoftware.b4a.keywords.Common.Log("count: "+_count);
 //BA.debugLineNum = 199;BA.debugLine="Dim kd As String = \"ITEM-\" & count";
_kd = "ITEM-"+_count;
 //BA.debugLineNum = 201;BA.debugLine="Return kd";
if (true) return _kd;
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim PnlBarang As Panel";
mostCurrent._pnlbarang = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim LblKode As Label";
mostCurrent._lblkode = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim LblNama As Label";
mostCurrent._lblnama = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim LblKeterangan As Label";
mostCurrent._lblketerangan = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim Lblkuantiti As Label";
mostCurrent._lblkuantiti = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim Lblstatus As Label";
mostCurrent._lblstatus = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim EdtKode As EditText";
mostCurrent._edtkode = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim EdtNama As EditText";
mostCurrent._edtnama = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim EdtKeterangan As EditText";
mostCurrent._edtketerangan = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim EdtKuantiti As EditText";
mostCurrent._edtkuantiti = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim Edtstatus As EditText";
mostCurrent._edtstatus = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim BtnSave As Button";
mostCurrent._btnsave = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim BtnDelete As Button";
mostCurrent._btndelete = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim AC As AppCompat";
mostCurrent._ac = new de.amberhome.objects.appcompat.AppCompatBase();
 //BA.debugLineNum = 37;BA.debugLine="Dim ABHelper As ACActionBar";
mostCurrent._abhelper = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 38;BA.debugLine="Private pContent As Panel";
mostCurrent._pcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private ActionBar As ACToolBarLight";
mostCurrent._actionbar = new de.amberhome.objects.appcompat.ACToolbarLightWrapper();
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _initobject() throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub InitObject";
 //BA.debugLineNum = 74;BA.debugLine="LblKode.Initialize(\"\")";
mostCurrent._lblkode.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 75;BA.debugLine="LblNama.Initialize(\"\")";
mostCurrent._lblnama.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 76;BA.debugLine="LblKeterangan.Initialize(\"\")";
mostCurrent._lblketerangan.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 77;BA.debugLine="Lblkuantiti.Initialize(\"\")";
mostCurrent._lblkuantiti.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 78;BA.debugLine="Lblstatus.Initialize(\"\")";
mostCurrent._lblstatus.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 80;BA.debugLine="EdtKode.Initialize(\"EdtKode\")";
mostCurrent._edtkode.Initialize(mostCurrent.activityBA,"EdtKode");
 //BA.debugLineNum = 81;BA.debugLine="EdtNama.Initialize(\"EdtNama\")";
mostCurrent._edtnama.Initialize(mostCurrent.activityBA,"EdtNama");
 //BA.debugLineNum = 82;BA.debugLine="EdtKeterangan.Initialize(\"EdtKeterangan\")";
mostCurrent._edtketerangan.Initialize(mostCurrent.activityBA,"EdtKeterangan");
 //BA.debugLineNum = 83;BA.debugLine="EdtKuantiti.Initialize(\"EdtKuantiti\")";
mostCurrent._edtkuantiti.Initialize(mostCurrent.activityBA,"EdtKuantiti");
 //BA.debugLineNum = 84;BA.debugLine="Edtstatus.Initialize(\"EdtKuantiti\")";
mostCurrent._edtstatus.Initialize(mostCurrent.activityBA,"EdtKuantiti");
 //BA.debugLineNum = 86;BA.debugLine="BtnSave.Initialize(\"BtnSave\")";
mostCurrent._btnsave.Initialize(mostCurrent.activityBA,"BtnSave");
 //BA.debugLineNum = 87;BA.debugLine="BtnDelete.Initialize(\"BtnDelete\")";
mostCurrent._btndelete.Initialize(mostCurrent.activityBA,"BtnDelete");
 //BA.debugLineNum = 89;BA.debugLine="PnlBarang.Initialize(\"PnlBarang\")";
mostCurrent._pnlbarang.Initialize(mostCurrent.activityBA,"PnlBarang");
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _loadbarang() throws Exception{
String _query = "";
anywheresoftware.b4a.objects.collections.Map _m = null;
String _kode = "";
 //BA.debugLineNum = 113;BA.debugLine="Sub LoadBarang";
 //BA.debugLineNum = 115;BA.debugLine="Dim query As String";
_query = "";
 //BA.debugLineNum = 116;BA.debugLine="query = \" SELECT * FROM \" & Login.BarangTable & \"";
_query = " SELECT * FROM "+mostCurrent._login._barangtable+" WHERE id = ?";
 //BA.debugLineNum = 118;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 119;BA.debugLine="m = DbUtils.ExecuteMap(Login.SQL, query, Array As";
_m = mostCurrent._dbutils._executemap(mostCurrent.activityBA,mostCurrent._login._sql,_query,new String[]{BA.NumberToString(_id)});
 //BA.debugLineNum = 121;BA.debugLine="If m.IsInitialized = False Then";
if (_m.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 122;BA.debugLine="If ID = -1 Then";
if (_id==-1) { 
 //BA.debugLineNum = 123;BA.debugLine="Dim kode As String = GenerateKode";
_kode = _generatekode();
 //BA.debugLineNum = 124;BA.debugLine="EdtKode.Text = kode";
mostCurrent._edtkode.setText((Object)(_kode));
 //BA.debugLineNum = 125;BA.debugLine="EdtNama.Text = \"\"";
mostCurrent._edtnama.setText((Object)(""));
 //BA.debugLineNum = 126;BA.debugLine="EdtKeterangan.Text = \"\"";
mostCurrent._edtketerangan.setText((Object)(""));
 //BA.debugLineNum = 127;BA.debugLine="EdtKuantiti.Text = \"\"";
mostCurrent._edtkuantiti.setText((Object)(""));
 //BA.debugLineNum = 128;BA.debugLine="Edtstatus.Text = \"\"";
mostCurrent._edtstatus.setText((Object)(""));
 };
 }else {
 //BA.debugLineNum = 132;BA.debugLine="If m.Get(\"kode\") <> Null Then";
if (_m.Get((Object)("kode"))!= null) { 
 //BA.debugLineNum = 133;BA.debugLine="EdtKode.Text = m.Get(\"kode\")";
mostCurrent._edtkode.setText(_m.Get((Object)("kode")));
 };
 //BA.debugLineNum = 136;BA.debugLine="If m.Get(\"nama\") <> Null Then";
if (_m.Get((Object)("nama"))!= null) { 
 //BA.debugLineNum = 137;BA.debugLine="Activity.Title = \"View: \" & m.Get(\"nama\")";
mostCurrent._activity.setTitle((Object)("View: "+BA.ObjectToString(_m.Get((Object)("nama")))));
 //BA.debugLineNum = 138;BA.debugLine="EdtNama.Text = m.Get(\"nama\")";
mostCurrent._edtnama.setText(_m.Get((Object)("nama")));
 };
 //BA.debugLineNum = 141;BA.debugLine="If m.Get(\"keterangan\") <> Null Then";
if (_m.Get((Object)("keterangan"))!= null) { 
 //BA.debugLineNum = 142;BA.debugLine="EdtKeterangan.Text = m.Get(\"keterangan\")";
mostCurrent._edtketerangan.setText(_m.Get((Object)("keterangan")));
 };
 //BA.debugLineNum = 145;BA.debugLine="If m.Get(\"kuantiti\") <> Null Then";
if (_m.Get((Object)("kuantiti"))!= null) { 
 //BA.debugLineNum = 146;BA.debugLine="EdtKuantiti.Text = m.Get(\"kuantiti\")";
mostCurrent._edtkuantiti.setText(_m.Get((Object)("kuantiti")));
 };
 //BA.debugLineNum = 149;BA.debugLine="If m.Get(\"status\") <> Null Then";
if (_m.Get((Object)("status"))!= null) { 
 //BA.debugLineNum = 150;BA.debugLine="Edtstatus.Text = m.Get(\"status\")";
mostCurrent._edtstatus.setText(_m.Get((Object)("status")));
 };
 };
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 12;BA.debugLine="Dim ID As Int";
_id = 0;
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _setlabel() throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="Sub SetLabel";
 //BA.debugLineNum = 93;BA.debugLine="LblKode.Text = \"Code\"";
mostCurrent._lblkode.setText((Object)("Code"));
 //BA.debugLineNum = 94;BA.debugLine="LblNama.Text = \"Name Items\"";
mostCurrent._lblnama.setText((Object)("Name Items"));
 //BA.debugLineNum = 95;BA.debugLine="LblKeterangan.Text = \"Details Items\"";
mostCurrent._lblketerangan.setText((Object)("Details Items"));
 //BA.debugLineNum = 96;BA.debugLine="Lblkuantiti.Text = \"Quantity Items\"";
mostCurrent._lblkuantiti.setText((Object)("Quantity Items"));
 //BA.debugLineNum = 97;BA.debugLine="Lblstatus.Text = \"Status Items\"";
mostCurrent._lblstatus.setText((Object)("Status Items"));
 //BA.debugLineNum = 100;BA.debugLine="EdtKode.Enabled = False";
mostCurrent._edtkode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 103;BA.debugLine="If ID = -1 Then";
if (_id==-1) { 
 //BA.debugLineNum = 104;BA.debugLine="BtnSave.Text = \"Save\"";
mostCurrent._btnsave.setText((Object)("Save"));
 //BA.debugLineNum = 105;BA.debugLine="BtnDelete.Enabled = False";
mostCurrent._btndelete.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 107;BA.debugLine="BtnSave.Text = \"Update\"";
mostCurrent._btnsave.setText((Object)("Update"));
 //BA.debugLineNum = 108;BA.debugLine="BtnDelete.Text = True";
mostCurrent._btndelete.setText((Object)(anywheresoftware.b4a.keywords.Common.True));
 };
 //BA.debugLineNum = 110;BA.debugLine="BtnDelete.Text = \"Delete\"";
mostCurrent._btndelete.setText((Object)("Delete"));
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _setobjectview() throws Exception{
int _ctop = 0;
int _labelheight = 0;
int _textheight = 0;
 //BA.debugLineNum = 155;BA.debugLine="Sub SetObjectView";
 //BA.debugLineNum = 156;BA.debugLine="Dim ctop As Int = 20dip";
_ctop = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20));
 //BA.debugLineNum = 157;BA.debugLine="Dim labelHeight As Int = 30dip";
_labelheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30));
 //BA.debugLineNum = 158;BA.debugLine="Dim textHeight As Int = 40dip";
_textheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40));
 //BA.debugLineNum = 161;BA.debugLine="PnlBarang.AddView(LblKode, 20dip, ctop, 100%x-40d";
mostCurrent._pnlbarang.AddView((android.view.View)(mostCurrent._lblkode.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),_ctop,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 161;BA.debugLine="PnlBarang.AddView(LblKode, 20dip, ctop, 100%x-40d";
_ctop = (int) (_ctop+_labelheight);
 //BA.debugLineNum = 162;BA.debugLine="PnlBarang.AddView(EdtKode, 20dip, ctop, 100%x-40d";
mostCurrent._pnlbarang.AddView((android.view.View)(mostCurrent._edtkode.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),_ctop,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 162;BA.debugLine="PnlBarang.AddView(EdtKode, 20dip, ctop, 100%x-40d";
_ctop = (int) (_ctop+_textheight);
 //BA.debugLineNum = 164;BA.debugLine="PnlBarang.AddView(LblNama, 20dip, ctop, 100%x-40d";
mostCurrent._pnlbarang.AddView((android.view.View)(mostCurrent._lblnama.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),_ctop,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 164;BA.debugLine="PnlBarang.AddView(LblNama, 20dip, ctop, 100%x-40d";
_ctop = (int) (_ctop+_labelheight);
 //BA.debugLineNum = 165;BA.debugLine="PnlBarang.AddView(EdtNama, 20dip, ctop, 100%x-40d";
mostCurrent._pnlbarang.AddView((android.view.View)(mostCurrent._edtnama.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),_ctop,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 165;BA.debugLine="PnlBarang.AddView(EdtNama, 20dip, ctop, 100%x-40d";
_ctop = (int) (_ctop+_textheight);
 //BA.debugLineNum = 167;BA.debugLine="PnlBarang.AddView(Lblkuantiti, 20dip, ctop, 100%x";
mostCurrent._pnlbarang.AddView((android.view.View)(mostCurrent._lblkuantiti.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),_ctop,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 167;BA.debugLine="PnlBarang.AddView(Lblkuantiti, 20dip, ctop, 100%x";
_ctop = (int) (_ctop+_labelheight);
 //BA.debugLineNum = 168;BA.debugLine="PnlBarang.AddView(EdtKuantiti, 20dip, ctop, 100%x";
mostCurrent._pnlbarang.AddView((android.view.View)(mostCurrent._edtkuantiti.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),_ctop,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 168;BA.debugLine="PnlBarang.AddView(EdtKuantiti, 20dip, ctop, 100%x";
_ctop = (int) (_ctop+_textheight);
 //BA.debugLineNum = 170;BA.debugLine="PnlBarang.AddView(LblKeterangan, 20dip, ctop, 100";
mostCurrent._pnlbarang.AddView((android.view.View)(mostCurrent._lblketerangan.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),_ctop,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 170;BA.debugLine="PnlBarang.AddView(LblKeterangan, 20dip, ctop, 100";
_ctop = (int) (_ctop+_labelheight);
 //BA.debugLineNum = 171;BA.debugLine="PnlBarang.AddView(EdtKeterangan, 20dip, ctop, 100";
mostCurrent._pnlbarang.AddView((android.view.View)(mostCurrent._edtketerangan.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),_ctop,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 171;BA.debugLine="PnlBarang.AddView(EdtKeterangan, 20dip, ctop, 100";
_ctop = (int) (_ctop+_textheight);
 //BA.debugLineNum = 173;BA.debugLine="PnlBarang.AddView(Lblstatus, 20dip, ctop, 100%x-4";
mostCurrent._pnlbarang.AddView((android.view.View)(mostCurrent._lblstatus.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),_ctop,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 173;BA.debugLine="PnlBarang.AddView(Lblstatus, 20dip, ctop, 100%x-4";
_ctop = (int) (_ctop+_labelheight);
 //BA.debugLineNum = 174;BA.debugLine="PnlBarang.AddView(Edtstatus, 20dip, ctop, 100%x-4";
mostCurrent._pnlbarang.AddView((android.view.View)(mostCurrent._edtstatus.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),_ctop,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 174;BA.debugLine="PnlBarang.AddView(Edtstatus, 20dip, ctop, 100%x-4";
_ctop = (int) (_ctop+_textheight);
 //BA.debugLineNum = 177;BA.debugLine="Activity.AddView(PnlBarang, 0,0,100%x, 85%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._pnlbarang.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (85),mostCurrent.activityBA));
 //BA.debugLineNum = 178;BA.debugLine="Activity.AddView(BtnSave, 0, 85%y, 50%x, 15%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._btnsave.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (85),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),mostCurrent.activityBA));
 //BA.debugLineNum = 179;BA.debugLine="Activity.AddView(BtnDelete, 50%x, 85%y, 50%x, 15%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._btndelete.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (85),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),mostCurrent.activityBA));
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
}
