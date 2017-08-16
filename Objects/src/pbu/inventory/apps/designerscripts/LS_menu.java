package pbu.inventory.apps.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_menu{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("imageview1").vw.setTop((int)((25d / 100 * height) - (views.get("imageview1").vw.getHeight())));
views.get("imageview1").vw.setLeft((int)((13d / 100 * height)));
views.get("btnitem").vw.setTop((int)((45d / 100 * height) - (views.get("btnitem").vw.getHeight())));
views.get("btnitem").vw.setLeft((int)((25d / 100 * height)));
views.get("btnimage").vw.setTop((int)((61d / 100 * height) - (views.get("btnimage").vw.getHeight())));
views.get("btnimage").vw.setLeft((int)((25d / 100 * height)));
views.get("btnabout").vw.setTop((int)((77d / 100 * height) - (views.get("btnabout").vw.getHeight())));
views.get("btnabout").vw.setLeft((int)((25d / 100 * height)));
views.get("btnlogout").vw.setTop((int)((94d / 100 * height) - (views.get("btnlogout").vw.getHeight())));
views.get("btnlogout").vw.setLeft((int)((25d / 100 * height)));

}
}