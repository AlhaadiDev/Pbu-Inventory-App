package pbu.inventory.apps;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class slidingpanels {
private static slidingpanels mostCurrent = new slidingpanels();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public pbu.inventory.apps.main _main = null;
public pbu.inventory.apps.login _login = null;
public pbu.inventory.apps.dbutils _dbutils = null;
public pbu.inventory.apps.inventorylist _inventorylist = null;
public pbu.inventory.apps.inventoryview _inventoryview = null;
public pbu.inventory.apps.home _home = null;
public pbu.inventory.apps.image _image = null;
public pbu.inventory.apps.about _about = null;
public pbu.inventory.apps.starter _starter = null;
public static class _slidingdata{
public boolean IsInitialized;
public boolean firstTime;
public int currentPanel;
public anywheresoftware.b4a.objects.PanelWrapper[] Panels;
public anywheresoftware.b4a.objects.AnimationWrapper[] LeftAnimations;
public anywheresoftware.b4a.objects.AnimationWrapper[] RightAnimations;
public int targetPanel;
public void Initialize() {
IsInitialized = true;
firstTime = false;
currentPanel = 0;
Panels = new anywheresoftware.b4a.objects.PanelWrapper[0];
{
int d0 = Panels.length;
for (int i0 = 0;i0 < d0;i0++) {
Panels[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
LeftAnimations = new anywheresoftware.b4a.objects.AnimationWrapper[0];
{
int d0 = LeftAnimations.length;
for (int i0 = 0;i0 < d0;i0++) {
LeftAnimations[i0] = new anywheresoftware.b4a.objects.AnimationWrapper();
}
}
;
RightAnimations = new anywheresoftware.b4a.objects.AnimationWrapper[0];
{
int d0 = RightAnimations.length;
for (int i0 = 0;i0 < d0;i0++) {
RightAnimations[i0] = new anywheresoftware.b4a.objects.AnimationWrapper();
}
}
;
targetPanel = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _animationend(anywheresoftware.b4a.BA _ba,pbu.inventory.apps.slidingpanels._slidingdata _sd) throws Exception{
int _i = 0;
 //BA.debugLineNum = 44;BA.debugLine="Sub AnimationEnd (sd As SlidingData)";
 //BA.debugLineNum = 45;BA.debugLine="sd.panels(sd.currentPanel).Left = 0 'Set the posi";
_sd.Panels[_sd.currentPanel].setLeft((int) (0));
 //BA.debugLineNum = 46;BA.debugLine="For i = 0 To sd.panels.Length - 1";
{
final int step2 = 1;
final int limit2 = (int) (_sd.Panels.length-1);
for (_i = (int) (0) ; (step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2); _i = ((int)(0 + _i + step2)) ) {
 //BA.debugLineNum = 47;BA.debugLine="If i <> sd.currentPanel Then sd.panels(i).Left =";
if (_i!=_sd.currentPanel) { 
_sd.Panels[_i].setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba));};
 }
};
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _changepanel(anywheresoftware.b4a.BA _ba,pbu.inventory.apps.slidingpanels._slidingdata _sd,boolean _left) throws Exception{
int _leftpanel = 0;
 //BA.debugLineNum = 26;BA.debugLine="Sub ChangePanel(sd As SlidingData, left As Boolean";
 //BA.debugLineNum = 27;BA.debugLine="If left Then";
if (_left) { 
 //BA.debugLineNum = 28;BA.debugLine="If sd.firstTime = False Then 'remove current pan";
if (_sd.firstTime==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 29;BA.debugLine="sd.leftAnimations(0).Start(sd.panels(sd.current";
_sd.LeftAnimations[(int) (0)].Start((android.view.View)(_sd.Panels[_sd.currentPanel].getObject()));
 }else {
 //BA.debugLineNum = 31;BA.debugLine="sd.firstTime = False";
_sd.firstTime = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 33;BA.debugLine="sd.leftAnimations(1).Start(sd.panels((sd.current";
_sd.LeftAnimations[(int) (1)].Start((android.view.View)(_sd.Panels[(int) ((_sd.currentPanel+1)%_sd.Panels.length)].getObject()));
 //BA.debugLineNum = 34;BA.debugLine="sd.currentPanel = (sd.currentPanel + 1) Mod sd.P";
_sd.currentPanel = (int) ((_sd.currentPanel+1)%_sd.Panels.length);
 }else {
 //BA.debugLineNum = 36;BA.debugLine="Dim leftPanel As Int";
_leftpanel = 0;
 //BA.debugLineNum = 37;BA.debugLine="leftPanel = (sd.currentPanel + sd.Panels.Length";
_leftpanel = (int) ((_sd.currentPanel+_sd.Panels.length-1)%_sd.Panels.length);
 //BA.debugLineNum = 38;BA.debugLine="sd.panels(leftPanel).left = -100%x";
_sd.Panels[_leftpanel].setLeft((int) (-anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba)));
 //BA.debugLineNum = 39;BA.debugLine="sd.rightAnimations(0).Start(sd.panels(sd.current";
_sd.RightAnimations[(int) (0)].Start((android.view.View)(_sd.Panels[_sd.currentPanel].getObject()));
 //BA.debugLineNum = 40;BA.debugLine="sd.rightAnimations(1).Start(sd.panels(leftPanel)";
_sd.RightAnimations[(int) (1)].Start((android.view.View)(_sd.Panels[_leftpanel].getObject()));
 //BA.debugLineNum = 41;BA.debugLine="sd.currentPanel = leftPanel";
_sd.currentPanel = _leftpanel;
 };
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _initialize(anywheresoftware.b4a.BA _ba,pbu.inventory.apps.slidingpanels._slidingdata _sd,int _slidingduration) throws Exception{
String _duration = "";
anywheresoftware.b4a.objects.AnimationWrapper[] _a = null;
int _i = 0;
 //BA.debugLineNum = 6;BA.debugLine="Sub Initialize (sd As SlidingData, SlidingDuration";
 //BA.debugLineNum = 7;BA.debugLine="duration = SlidingDuration";
_duration = BA.NumberToString(_slidingduration);
 //BA.debugLineNum = 9;BA.debugLine="Dim a(2) As Animation";
_a = new anywheresoftware.b4a.objects.AnimationWrapper[(int) (2)];
{
int d0 = _a.length;
for (int i0 = 0;i0 < d0;i0++) {
_a[i0] = new anywheresoftware.b4a.objects.AnimationWrapper();
}
}
;
 //BA.debugLineNum = 10;BA.debugLine="sd.LeftAnimations = a";
_sd.LeftAnimations = _a;
 //BA.debugLineNum = 11;BA.debugLine="Dim a(2) As Animation";
_a = new anywheresoftware.b4a.objects.AnimationWrapper[(int) (2)];
{
int d0 = _a.length;
for (int i0 = 0;i0 < d0;i0++) {
_a[i0] = new anywheresoftware.b4a.objects.AnimationWrapper();
}
}
;
 //BA.debugLineNum = 12;BA.debugLine="sd.RightAnimations = a";
_sd.RightAnimations = _a;
 //BA.debugLineNum = 14;BA.debugLine="For i = 0 To 1";
{
final int step6 = 1;
final int limit6 = (int) (1);
for (_i = (int) (0) ; (step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6); _i = ((int)(0 + _i + step6)) ) {
 //BA.debugLineNum = 15;BA.debugLine="sd.leftAnimations(i).InitializeTranslate(\"animat";
_sd.LeftAnimations[_i].InitializeTranslate(_ba,"animation"+BA.NumberToString(_i),(float) (0),(float) (0),(float) (-anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba)),(float) (0));
 //BA.debugLineNum = 16;BA.debugLine="sd.leftAnimations(i).Duration = SlidingDuration";
_sd.LeftAnimations[_i].setDuration((long) (_slidingduration));
 //BA.debugLineNum = 17;BA.debugLine="sd.rightAnimations(i).InitializeTranslate(\"anima";
_sd.RightAnimations[_i].InitializeTranslate(_ba,"animation"+BA.NumberToString(_i),(float) (0),(float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba)),(float) (0));
 //BA.debugLineNum = 18;BA.debugLine="sd.rightAnimations(i).Duration = SlidingDuration";
_sd.RightAnimations[_i].setDuration((long) (_slidingduration));
 }
};
 //BA.debugLineNum = 20;BA.debugLine="For i = 0 To sd.Panels.Length - 1";
{
final int step12 = 1;
final int limit12 = (int) (_sd.Panels.length-1);
for (_i = (int) (0) ; (step12 > 0 && _i <= limit12) || (step12 < 0 && _i >= limit12); _i = ((int)(0 + _i + step12)) ) {
 //BA.debugLineNum = 21;BA.debugLine="sd.Panels(i).Left = 100%x 'Move the panels right";
_sd.Panels[_i].setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba));
 }
};
 //BA.debugLineNum = 23;BA.debugLine="sd.firstTime = True";
_sd.firstTime = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Type SlidingData (firstTime As Boolean, currentPa";
;
 //BA.debugLineNum = 4;BA.debugLine="End Sub";
return "";
}
}
