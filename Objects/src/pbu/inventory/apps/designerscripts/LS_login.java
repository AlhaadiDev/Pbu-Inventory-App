package pbu.inventory.apps.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_login{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("label1").vw.setTop((int)((35d / 100 * height) - (views.get("label1").vw.getHeight())));
views.get("edtusername").vw.setTop((int)((47d / 100 * height) - (views.get("edtusername").vw.getHeight())));
views.get("label2").vw.setTop((int)((57d / 100 * height) - (views.get("label2").vw.getHeight())));
views.get("edtpassword").vw.setTop((int)((69d / 100 * height) - (views.get("edtpassword").vw.getHeight())));
views.get("btnlogin").vw.setTop((int)((82d / 100 * height) - (views.get("btnlogin").vw.getHeight())));
views.get("imageview1").vw.setTop((int)((23d / 100 * height) - (views.get("imageview1").vw.getHeight())));
views.get("imageview1").vw.setLeft((int)((11d / 100 * height)));

}
}