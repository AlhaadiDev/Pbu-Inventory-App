package pbu.inventory.apps.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_about{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[about/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 4;BA.debugLine="ImageView1.Bottom=30%y"[about/General script]
views.get("imageview1").vw.setTop((int)((30d / 100 * height) - (views.get("imageview1").vw.getHeight())));
//BA.debugLineNum = 5;BA.debugLine="ImageView1.Left=50dip"[about/General script]
views.get("imageview1").vw.setLeft((int)((50d * scale)));
//BA.debugLineNum = 7;BA.debugLine="Label1.Bottom=40%y"[about/General script]
views.get("label1").vw.setTop((int)((40d / 100 * height) - (views.get("label1").vw.getHeight())));
//BA.debugLineNum = 8;BA.debugLine="Label1.Left=30dip"[about/General script]
views.get("label1").vw.setLeft((int)((30d * scale)));
//BA.debugLineNum = 10;BA.debugLine="ImageView2.Bottom=75%y"[about/General script]
views.get("imageview2").vw.setTop((int)((75d / 100 * height) - (views.get("imageview2").vw.getHeight())));
//BA.debugLineNum = 11;BA.debugLine="ImageView2.Left=100dip"[about/General script]
views.get("imageview2").vw.setLeft((int)((100d * scale)));
//BA.debugLineNum = 13;BA.debugLine="Label2.Bottom=85%y"[about/General script]
views.get("label2").vw.setTop((int)((85d / 100 * height) - (views.get("label2").vw.getHeight())));
//BA.debugLineNum = 14;BA.debugLine="Label2.Left=60dip"[about/General script]
views.get("label2").vw.setLeft((int)((60d * scale)));
//BA.debugLineNum = 16;BA.debugLine="Label3.Bottom=95%y"[about/General script]
views.get("label3").vw.setTop((int)((95d / 100 * height) - (views.get("label3").vw.getHeight())));
//BA.debugLineNum = 17;BA.debugLine="Label3.Left=10dip"[about/General script]
views.get("label3").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 19;BA.debugLine="ImageView3.Bottom=130%y"[about/General script]
views.get("imageview3").vw.setTop((int)((130d / 100 * height) - (views.get("imageview3").vw.getHeight())));
//BA.debugLineNum = 20;BA.debugLine="ImageView3.Left=100dip"[about/General script]
views.get("imageview3").vw.setLeft((int)((100d * scale)));
//BA.debugLineNum = 22;BA.debugLine="Label4.Bottom=140%y"[about/General script]
views.get("label4").vw.setTop((int)((140d / 100 * height) - (views.get("label4").vw.getHeight())));
//BA.debugLineNum = 23;BA.debugLine="Label4.Left=60dip"[about/General script]
views.get("label4").vw.setLeft((int)((60d * scale)));

}
}