# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-dontwarn android.util.Singleton
#-keep  class android.util.**{*;}
#-keepclassmembers class android.util.**{*;}



-keep interface com.squareup.lib.utils.IProguard {*;}
-keep interface com.squareup.lib.utils.IProguard$* {*;}

-keep class * implements com.squareup.lib.utils.IProguard$ProtectClassAndMembers {*;}
-keepclassmembers class * implements com.squareup.lib.utils.IProguard$ProtectClassAndMembers {*;}
#-keepclassmembers class * implements android.view.View$* {*;}

#��������Ա��������
-keepclassmembers class * implements com.squareup.lib.utils.IProguard$ProtectMembers {*;}

#������������������

-keep class * implements com.squareup.lib.utils.IProguard$ProtectClassAndConstruct
-keepclassmembers class * implements com.squareup.lib.utils.IProguard$ProtectClassAndConstruct{
	<init>(...);
}

#������������, ������...)��ʾ���������Ĳ���; *** -- ��ʾ�������ͣ���һ������; **��ʾ�����������ַ�;
#*��ʾ�����������ַ��������������ķָ��; ? ��ʾ�����ַ��� %��ʾ�����������
-keepclassmembers class * implements com.squareup.lib.utils.IProguard$ProtectConstructs{
	<init>(...);
}

#����������
-keep class * implements com.squareup.lib.utils.IProguard$ProtectClass {*;}
-keep interface * extends com.squareup.lib.utils.IProguard$ProtectClass {*;}



#DroidPlugin
-dontwarn com.morgoo.**
-keep class com.morgoo.**{*;}
-keep interface com.morgoo.**{*;}
-keepclassmembers interface com.morgoo.**{*;}

-dontwarn com.squareup.lip.**
-keep class com.squareup.lip.**{*;}
-keep class android.support.**{*;}
#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.**{*;}
-keepclassmembers class com.squareup.okhttp.**{*;}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}
-keepclassmembers interface okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}
-keepclassmembers interface okio.**{*;}

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclassmembers class retrofit2.** { *; }

#eventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
##---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.AppCompatActivity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.FragmentActivity

-keepclasseswithmembernames class * {
     native <methods>;
}

-keepclassmembers class * {
     native <methods>;
}
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
##---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}