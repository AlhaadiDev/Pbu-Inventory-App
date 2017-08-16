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

public class login extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static login mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "pbu.inventory.apps", "pbu.inventory.apps.login");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (login).");
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
		activityBA = new BA(this, layout, processBA, "pbu.inventory.apps", "pbu.inventory.apps.login");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "pbu.inventory.apps.login", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (login) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (login) Resume **");
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
		return login.class;
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
        BA.LogInfo("** Activity (login) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (login) Resume **");
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
public static anywheresoftware.b4a.sql.SQL _sql = null;
public static String _dbdir = "";
public static String _dbname = "";
public static String _usertable = "";
public static String _barangtable = "";
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _intro = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _intro2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtusername = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtpassword = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlogin = null;
public de.amberhome.objects.appcompat.AppCompatBase _ac = null;
public de.amberhome.objects.appcompat.ACActionBar _abhelper = null;
public anywheresoftware.b4a.objects.PanelWrapper _pcontent = null;
public de.amberhome.objects.appcompat.ACToolbarLightWrapper _actionbar = null;
public giuseppe.salvi.icos.library.ICOSFlip3DView _a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public pbu.inventory.apps.main _main = null;
public pbu.inventory.apps.dbutils _dbutils = null;
public pbu.inventory.apps.inventorylist _inventorylist = null;
public pbu.inventory.apps.inventoryview _inventoryview = null;
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
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 40;BA.debugLine="Activity.LoadLayout(\"main\")";
mostCurrent._activity.LoadLayout("main",mostCurrent.activityBA);
 //BA.debugLineNum = 41;BA.debugLine="pContent.LoadLayout(\"login\")";
mostCurrent._pcontent.LoadLayout("login",mostCurrent.activityBA);
 //BA.debugLineNum = 42;BA.debugLine="ActionBar.Title = \"LOGIN\"";
mostCurrent._actionbar.setTitle((java.lang.CharSequence)("LOGIN"));
 //BA.debugLineNum = 43;BA.debugLine="ActionBar.SubTitle = \"\"";
mostCurrent._actionbar.setSubTitle((java.lang.CharSequence)(""));
 //BA.debugLineNum = 44;BA.debugLine="a.Flip3DView(\"a\",ImageView1,180,2000,a.FLIP_HORIZ";
mostCurrent._a.Flip3DView(mostCurrent.activityBA,"a",(android.view.View)(mostCurrent._imageview1.getObject()),(float) (180),(long) (2000),mostCurrent._a.FLIP_HORIZONTAL);
 //BA.debugLineNum = 45;BA.debugLine="a.StartAnim(ImageView1)";
mostCurrent._a.StartAnim((android.view.View)(mostCurrent._imageview1.getObject()));
 //BA.debugLineNum = 46;BA.debugLine="a.AutoRepeat";
mostCurrent._a.AutoRepeat();
 //BA.debugLineNum = 47;BA.debugLine="intro.Initialize2(\"intro\")";
_intro.Initialize2(processBA,"intro");
 //BA.debugLineNum = 48;BA.debugLine="intro.Load(File.DirAssets, \"enterauthorizationcod";
_intro.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"enterauthorizationcode.mp3");
 //BA.debugLineNum = 49;BA.debugLine="intro.Play";
_intro.Play();
 //BA.debugLineNum = 51;BA.debugLine="intro2.Initialize2(\"intro2\")";
_intro2.Initialize2(processBA,"intro2");
 //BA.debugLineNum = 52;BA.debugLine="intro2.Load(File.DirAssets, \"access_granted.mp3\")";
_intro2.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"access_granted.mp3");
 //BA.debugLineNum = 54;BA.debugLine="Activity.AddMenuItem(\"Exit\", \"Menu\")";
mostCurrent._activity.AddMenuItem("Exit","Menu");
 //BA.debugLineNum = 55;BA.debugLine="EdtUsername.RequestFocus";
mostCurrent._edtusername.RequestFocus();
 //BA.debugLineNum = 57;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 59;BA.debugLine="If File.Exists(DBDir, DBName) = False Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_dbdir,_dbname)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 60;BA.debugLine="File.Copy(File.DirAssets, DBName, DBDir, DBName";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_dbname,_dbdir,_dbname);
 };
 //BA.debugLineNum = 64;BA.debugLine="SQL.Initialize(DBDir, DBName, True)";
_sql.Initialize(_dbdir,_dbname,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 69;BA.debugLine="EdtUsername.RequestFocus";
mostCurrent._edtusername.RequestFocus();
 //BA.debugLineNum = 70;BA.debugLine="EdtUsername.Text=\"\"";
mostCurrent._edtusername.setText((Object)(""));
 //BA.debugLineNum = 71;BA.debugLine="EdtPassword.Text=\"\"";
mostCurrent._edtpassword.setText((Object)(""));
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _btnlogin_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _m = null;
 //BA.debugLineNum = 79;BA.debugLine="Sub BtnLogin_Click";
 //BA.debugLineNum = 81;BA.debugLine="If EdtUsername.Text = \"\" Then";
if ((mostCurrent._edtusername.getText()).equals("")) { 
 //BA.debugLineNum = 85;BA.debugLine="Msgbox(\"Please enter Username\", \"Warning\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Please enter Username","Warning",mostCurrent.activityBA);
 //BA.debugLineNum = 86;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 89;BA.debugLine="If EdtPassword.Text = \"\" Then";
if ((mostCurrent._edtpassword.getText()).equals("")) { 
 //BA.debugLineNum = 90;BA.debugLine="Msgbox(\"Please enter Password\", \"Warning\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Please enter Password","Warning",mostCurrent.activityBA);
 //BA.debugLineNum = 91;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 94;BA.debugLine="Dim m As Map = CheckLogin(EdtUsername.Text, EdtPa";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = _checklogin(mostCurrent._edtusername.getText(),mostCurrent._edtpassword.getText());
 //BA.debugLineNum = 97;BA.debugLine="If m.IsInitialized = True Then";
if (_m.IsInitialized()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 98;BA.debugLine="intro2.Play";
_intro2.Play();
 //BA.debugLineNum = 99;BA.debugLine="Msgbox(\"Hye, \" & m.Get(\"username\") & CRLF & _";
anywheresoftware.b4a.keywords.Common.Msgbox("Hye, "+BA.ObjectToString(_m.Get((Object)("username")))+anywheresoftware.b4a.keywords.Common.CRLF+"Welcome to PBUSCAPP","SUCCESS",mostCurrent.activityBA);
 //BA.debugLineNum = 101;BA.debugLine="StartActivity(Home)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._home.getObject()));
 }else {
 //BA.debugLineNum = 103;BA.debugLine="Msgbox(\"Username @ Password is wrong\", \"Failed L";
anywheresoftware.b4a.keywords.Common.Msgbox("Username @ Password is wrong","Failed Login",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.Map  _checklogin(String _username,String _password) throws Exception{
String _query = "";
anywheresoftware.b4a.objects.collections.Map _m = null;
 //BA.debugLineNum = 107;BA.debugLine="Sub CheckLogin(Username As String, Password As Str";
 //BA.debugLineNum = 108;BA.debugLine="Dim Query As String";
_query = "";
 //BA.debugLineNum = 109;BA.debugLine="Query = \"select * from \" & UserTable & \" where us";
_query = "select * from "+_usertable+" where username = ? and password = ?";
 //BA.debugLineNum = 110;BA.debugLine="Dim M As Map = DbUtils.ExecuteMap(SQL, Query, Arr";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = mostCurrent._dbutils._executemap(mostCurrent.activityBA,_sql,_query,new String[]{_username,_password});
 //BA.debugLineNum = 111;BA.debugLine="Return M";
if (true) return _m;
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return null;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 23;BA.debugLine="Private EdtUsername As EditText";
mostCurrent._edtusername = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private EdtPassword As EditText";
mostCurrent._edtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private BtnLogin As Button";
mostCurrent._btnlogin = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim AC As AppCompat";
mostCurrent._ac = new de.amberhome.objects.appcompat.AppCompatBase();
 //BA.debugLineNum = 28;BA.debugLine="Dim ABHelper As ACActionBar";
mostCurrent._abhelper = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 29;BA.debugLine="Private pContent As Panel";
mostCurrent._pcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private ActionBar As ACToolBarLight";
mostCurrent._actionbar = new de.amberhome.objects.appcompat.ACToolbarLightWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim a As ICOSFlip3DView";
mostCurrent._a = new giuseppe.salvi.icos.library.ICOSFlip3DView();
 //BA.debugLineNum = 33;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _menu_click() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
int _choice = 0;
 //BA.debugLineNum = 114;BA.debugLine="Sub Menu_Click()";
 //BA.debugLineNum = 115;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 116;BA.debugLine="Dim choice As Int";
_choice = 0;
 //BA.debugLineNum = 117;BA.debugLine="bmp.Initialize(File.DirAssets, \"help.png\")";
_bmp.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"help.png");
 //BA.debugLineNum = 118;BA.debugLine="choice = Msgbox2(\" Quit now ?\", \"Comfirmation \", \"";
_choice = anywheresoftware.b4a.keywords.Common.Msgbox2(" Quit now ?","Comfirmation ","Yes","","No",(android.graphics.Bitmap)(_bmp.getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 119;BA.debugLine="If choice = DialogResponse.POSITIVE Then";
if (_choice==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 120;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 };
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim SQL As SQL";
_sql = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 10;BA.debugLine="Dim DBDir As String : DBDir = File.DirDefaultExte";
_dbdir = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim DBDir As String : DBDir = File.DirDefaultExte";
_dbdir = anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal();
 //BA.debugLineNum = 12;BA.debugLine="Dim DBName As String : DBName = \"jskk.db\"";
_dbname = "";
 //BA.debugLineNum = 12;BA.debugLine="Dim DBName As String : DBName = \"jskk.db\"";
_dbname = "jskk.db";
 //BA.debugLineNum = 14;BA.debugLine="Dim UserTable As String = \"user\"";
_usertable = "user";
 //BA.debugLineNum = 15;BA.debugLine="Dim BarangTable As String = \"barang\"";
_barangtable = "barang";
 //BA.debugLineNum = 16;BA.debugLine="Public intro,intro2 As MediaPlayer";
_intro = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
_intro2 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
}
