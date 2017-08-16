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

public class image extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static image mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "pbu.inventory.apps", "pbu.inventory.apps.image");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (image).");
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
		activityBA = new BA(this, layout, processBA, "pbu.inventory.apps", "pbu.inventory.apps.image");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "pbu.inventory.apps.image", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (image) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (image) Resume **");
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
		return image.class;
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
        BA.LogInfo("** Activity (image) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (image) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _tmranimation = null;
public static int _currentpanelbeforepaused = 0;
public static anywheresoftware.b4a.objects.Timer _tmrslider = null;
public anywheresoftware.b4a.objects.drawable.BitmapDrawable _bmp0 = null;
public anywheresoftware.b4a.objects.drawable.BitmapDrawable _bmp1 = null;
public anywheresoftware.b4a.objects.drawable.BitmapDrawable _bmp2 = null;
public anywheresoftware.b4a.objects.drawable.BitmapDrawable _bmp3 = null;
public pbu.inventory.apps.slidingpanels._slidingdata _sd = null;
public static float _startx = 0f;
public static float _starty = 0f;
public static int _slidingduration = 0;
public static int _offsetx = 0;
public anywheresoftware.b4a.objects.ImageViewWrapper[] _imgs = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cd2 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _introwel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnkata = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvideo = null;
public de.amberhome.objects.appcompat.AppCompatBase _ac = null;
public de.amberhome.objects.appcompat.ACActionBar _abhelper = null;
public anywheresoftware.b4a.objects.PanelWrapper _pcontent = null;
public de.amberhome.objects.appcompat.ACToolbarLightWrapper _actionbar = null;
public pbu.inventory.apps.main _main = null;
public pbu.inventory.apps.login _login = null;
public pbu.inventory.apps.dbutils _dbutils = null;
public pbu.inventory.apps.inventorylist _inventorylist = null;
public pbu.inventory.apps.inventoryview _inventoryview = null;
public pbu.inventory.apps.slidingpanels _slidingpanels = null;
public pbu.inventory.apps.home _home = null;
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
 //BA.debugLineNum = 150;BA.debugLine="Sub ActionBar_NavigationItemClick";
 //BA.debugLineNum = 151;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper[] _panels = null;
int _i = 0;
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 40;BA.debugLine="Activity.LoadLayout(\"main\")";
mostCurrent._activity.LoadLayout("main",mostCurrent.activityBA);
 //BA.debugLineNum = 41;BA.debugLine="pContent.LoadLayout(\"image\")";
mostCurrent._pcontent.LoadLayout("image",mostCurrent.activityBA);
 //BA.debugLineNum = 42;BA.debugLine="ActionBar.Title = \"IMAGE PBU\"";
mostCurrent._actionbar.setTitle((java.lang.CharSequence)("IMAGE PBU"));
 //BA.debugLineNum = 43;BA.debugLine="ActionBar.SubTitle = \"\"";
mostCurrent._actionbar.setSubTitle((java.lang.CharSequence)(""));
 //BA.debugLineNum = 45;BA.debugLine="ABHelper.Initialize";
mostCurrent._abhelper.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 46;BA.debugLine="ABHelper.ShowUpIndicator = True";
mostCurrent._abhelper.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 47;BA.debugLine="ActionBar.InitMenuListener";
mostCurrent._actionbar.InitMenuListener();
 //BA.debugLineNum = 51;BA.debugLine="Dim panels(5) As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper[(int) (5)];
{
int d0 = _panels.length;
for (int i0 = 0;i0 < d0;i0++) {
_panels[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 52;BA.debugLine="cd.Initialize(Colors.black,10dip)";
mostCurrent._cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 53;BA.debugLine="cd2.Initialize(Colors.DarkGray,10dip)";
mostCurrent._cd2.Initialize(anywheresoftware.b4a.keywords.Common.Colors.DarkGray,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 54;BA.debugLine="For i = 0 To 4";
{
final int step11 = 1;
final int limit11 = (int) (4);
for (_i = (int) (0) ; (step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11); _i = ((int)(0 + _i + step11)) ) {
 //BA.debugLineNum = 55;BA.debugLine="panels(i).Initialize(\"panels\")";
_panels[_i].Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 56;BA.debugLine="imgs(i).Initialize(\"imgs\")";
mostCurrent._imgs[_i].Initialize(mostCurrent.activityBA,"imgs");
 //BA.debugLineNum = 57;BA.debugLine="pContent.Color=Colors.White";
mostCurrent._pcontent.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 58;BA.debugLine="pContent.AddView(panels(i),0%x,0%y,100%x,88%y)";
mostCurrent._pcontent.AddView((android.view.View)(_panels[_i].getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (88),mostCurrent.activityBA));
 //BA.debugLineNum = 59;BA.debugLine="panels(i).SetBackgroundImage(LoadBitmapSample(F";
_panels[_i].SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString((_i+1))+".jpg",_panels[_i].getWidth(),_panels[_i].getHeight()).getObject()));
 //BA.debugLineNum = 60;BA.debugLine="pContent.AddView(imgs(i),offsetX,(panels(i).Top";
mostCurrent._pcontent.AddView((android.view.View)(mostCurrent._imgs[_i].getObject()),_offsetx,(int) ((_panels[_i].getTop()+_panels[_i].getHeight())+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 61;BA.debugLine="If i = 0 Then";
if (_i==0) { 
 //BA.debugLineNum = 62;BA.debugLine="imgs(i).Background = cd2";
mostCurrent._imgs[_i].setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd2.getObject()));
 }else {
 //BA.debugLineNum = 64;BA.debugLine="imgs(i).Background = cd";
mostCurrent._imgs[_i].setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd.getObject()));
 };
 //BA.debugLineNum = 66;BA.debugLine="offsetX = offsetX + 10dip";
_offsetx = (int) (_offsetx+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 }
};
 //BA.debugLineNum = 68;BA.debugLine="sd.Initialize";
mostCurrent._sd.Initialize();
 //BA.debugLineNum = 69;BA.debugLine="sd.panels = panels";
mostCurrent._sd.Panels = _panels;
 //BA.debugLineNum = 70;BA.debugLine="SlidingPanels.Initialize(sd, SlidingDuration)";
mostCurrent._slidingpanels._initialize(mostCurrent.activityBA,mostCurrent._sd,_slidingduration);
 //BA.debugLineNum = 71;BA.debugLine="sd.targetPanel = -1";
mostCurrent._sd.targetPanel = (int) (-1);
 //BA.debugLineNum = 72;BA.debugLine="sd.currentPanel = currentPanelBeforePaused - 1";
mostCurrent._sd.currentPanel = (int) (_currentpanelbeforepaused-1);
 //BA.debugLineNum = 73;BA.debugLine="ChangePanel(True)";
_changepanel(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 74;BA.debugLine="tmrSlider.Initialize(\"tmrSlider\",5000)";
_tmrslider.Initialize(processBA,"tmrSlider",(long) (5000));
 //BA.debugLineNum = 75;BA.debugLine="tmrSlider.Enabled = True";
_tmrslider.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public static String  _animation1_animationend() throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub Animation1_AnimationEnd";
 //BA.debugLineNum = 102;BA.debugLine="SlidingPanels.AnimationEnd(sd)";
mostCurrent._slidingpanels._animationend(mostCurrent.activityBA,mostCurrent._sd);
 //BA.debugLineNum = 103;BA.debugLine="If sd.targetPanel >= 0 Then";
if (mostCurrent._sd.targetPanel>=0) { 
 //BA.debugLineNum = 104;BA.debugLine="tmrAnimation.Enabled = True";
_tmranimation.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 105;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _changepanel(boolean _left) throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Sub ChangePanel(Left As Boolean)";
 //BA.debugLineNum = 98;BA.debugLine="SlidingPanels.ChangePanel(sd, Left)";
mostCurrent._slidingpanels._changepanel(mostCurrent.activityBA,mostCurrent._sd,_left);
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _continuejumping() throws Exception{
int _i = 0;
 //BA.debugLineNum = 121;BA.debugLine="Sub ContinueJumping";
 //BA.debugLineNum = 122;BA.debugLine="If sd.targetPanel < 0 Or sd.targetPanel = sd.curr";
if (mostCurrent._sd.targetPanel<0 || mostCurrent._sd.targetPanel==mostCurrent._sd.currentPanel) { 
 //BA.debugLineNum = 123;BA.debugLine="sd.targetPanel = -1";
mostCurrent._sd.targetPanel = (int) (-1);
 //BA.debugLineNum = 124;BA.debugLine="Animation1_AnimationEnd";
_animation1_animationend();
 //BA.debugLineNum = 125;BA.debugLine="For i = 0 To 1";
{
final int step4 = 1;
final int limit4 = (int) (1);
for (_i = (int) (0) ; (step4 > 0 && _i <= limit4) || (step4 < 0 && _i >= limit4); _i = ((int)(0 + _i + step4)) ) {
 //BA.debugLineNum = 126;BA.debugLine="sd.leftAnimations(i).Duration = SlidingDuration";
mostCurrent._sd.LeftAnimations[_i].setDuration((long) (_slidingduration));
 //BA.debugLineNum = 127;BA.debugLine="sd.rightAnimations(i).Duration = SlidingDuratio";
mostCurrent._sd.RightAnimations[_i].setDuration((long) (_slidingduration));
 }
};
 //BA.debugLineNum = 129;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 131;BA.debugLine="SlidingPanels.ChangePanel(sd, sd.targetPanel > sd";
mostCurrent._slidingpanels._changepanel(mostCurrent.activityBA,mostCurrent._sd,mostCurrent._sd.targetPanel>mostCurrent._sd.currentPanel);
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim bmp0, bmp1, bmp2, bmp3 As BitmapDrawable";
mostCurrent._bmp0 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
mostCurrent._bmp1 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
mostCurrent._bmp2 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
mostCurrent._bmp3 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 16;BA.debugLine="Dim sd As SlidingData";
mostCurrent._sd = new pbu.inventory.apps.slidingpanels._slidingdata();
 //BA.debugLineNum = 17;BA.debugLine="Dim startX, startY As Float";
_startx = 0f;
_starty = 0f;
 //BA.debugLineNum = 18;BA.debugLine="Dim SlidingDuration As Int";
_slidingduration = 0;
 //BA.debugLineNum = 19;BA.debugLine="SlidingDuration = 700";
_slidingduration = (int) (700);
 //BA.debugLineNum = 20;BA.debugLine="Dim offsetX As Int = 45%x";
_offsetx = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA);
 //BA.debugLineNum = 21;BA.debugLine="Dim imgs(5) As ImageView";
mostCurrent._imgs = new anywheresoftware.b4a.objects.ImageViewWrapper[(int) (5)];
{
int d0 = mostCurrent._imgs.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._imgs[i0] = new anywheresoftware.b4a.objects.ImageViewWrapper();
}
}
;
 //BA.debugLineNum = 22;BA.debugLine="Dim cd , cd2 As ColorDrawable";
mostCurrent._cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
mostCurrent._cd2 = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 23;BA.debugLine="Dim  introwel As MediaPlayer";
mostCurrent._introwel = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btnKata As Button";
mostCurrent._btnkata = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btnVideo As Button";
mostCurrent._btnvideo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim AC As AppCompat";
mostCurrent._ac = new de.amberhome.objects.appcompat.AppCompatBase();
 //BA.debugLineNum = 29;BA.debugLine="Dim ABHelper As ACActionBar";
mostCurrent._abhelper = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 30;BA.debugLine="Private pContent As Panel";
mostCurrent._pcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private ActionBar As ACToolBarLight";
mostCurrent._actionbar = new de.amberhome.objects.appcompat.ACToolbarLightWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _jumptopanel(int _target) throws Exception{
int _i = 0;
 //BA.debugLineNum = 113;BA.debugLine="Sub JumpToPanel (Target As Int)";
 //BA.debugLineNum = 114;BA.debugLine="sd.targetPanel = Target";
mostCurrent._sd.targetPanel = _target;
 //BA.debugLineNum = 115;BA.debugLine="For i = 0 To 1";
{
final int step2 = 1;
final int limit2 = (int) (1);
for (_i = (int) (0) ; (step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2); _i = ((int)(0 + _i + step2)) ) {
 //BA.debugLineNum = 116;BA.debugLine="sd.leftAnimations(i).Duration = SlidingDuration";
mostCurrent._sd.LeftAnimations[_i].setDuration((long) (_slidingduration/(double)2));
 //BA.debugLineNum = 117;BA.debugLine="sd.rightAnimations(i).Duration = SlidingDuration";
mostCurrent._sd.RightAnimations[_i].setDuration((long) (_slidingduration/(double)2));
 }
};
 //BA.debugLineNum = 119;BA.debugLine="ContinueJumping";
_continuejumping();
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _panels_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Sub Panels_Touch (Action As Int, X As Float, Y As";
 //BA.debugLineNum = 135;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,mostCurrent._pcontent.ACTION_DOWN,mostCurrent._pcontent.ACTION_UP)) {
case 0: {
 //BA.debugLineNum = 137;BA.debugLine="startX = X";
_startx = _x;
 //BA.debugLineNum = 138;BA.debugLine="startY = Y";
_starty = _y;
 break; }
case 1: {
 //BA.debugLineNum = 140;BA.debugLine="If Abs(Y - startY) > 20%y Then Return";
if (anywheresoftware.b4a.keywords.Common.Abs(_y-_starty)>anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA)) { 
if (true) return "";};
 //BA.debugLineNum = 141;BA.debugLine="If X - startX > 30%x  Then";
if (_x-_startx>anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 142;BA.debugLine="ChangePanel(False)";
_changepanel(anywheresoftware.b4a.keywords.Common.False);
 }else if(_startx-_x>anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 144;BA.debugLine="ChangePanel(True)";
_changepanel(anywheresoftware.b4a.keywords.Common.True);
 };
 break; }
}
;
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim tmrAnimation As Timer";
_tmranimation = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 9;BA.debugLine="Dim currentPanelBeforePaused As Int";
_currentpanelbeforepaused = 0;
 //BA.debugLineNum = 10;BA.debugLine="Dim tmrSlider As Timer";
_tmrslider = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _tmranimation_tick() throws Exception{
 //BA.debugLineNum = 108;BA.debugLine="Sub tmrAnimation_Tick";
 //BA.debugLineNum = 109;BA.debugLine="tmrAnimation.Enabled = False";
_tmranimation.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 110;BA.debugLine="ContinueJumping";
_continuejumping();
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _tmrslider_tick() throws Exception{
int _i = 0;
 //BA.debugLineNum = 85;BA.debugLine="Sub tmrSlider_Tick";
 //BA.debugLineNum = 86;BA.debugLine="ChangePanel(True)";
_changepanel(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 87;BA.debugLine="For i = 0 To 4";
{
final int step2 = 1;
final int limit2 = (int) (4);
for (_i = (int) (0) ; (step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2); _i = ((int)(0 + _i + step2)) ) {
 //BA.debugLineNum = 88;BA.debugLine="If i = sd.currentPanel Then";
if (_i==mostCurrent._sd.currentPanel) { 
 //BA.debugLineNum = 89;BA.debugLine="imgs(i).Background = cd2";
mostCurrent._imgs[_i].setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd2.getObject()));
 }else {
 //BA.debugLineNum = 91;BA.debugLine="imgs(i).Background = cd";
mostCurrent._imgs[_i].setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd.getObject()));
 };
 }
};
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
}
