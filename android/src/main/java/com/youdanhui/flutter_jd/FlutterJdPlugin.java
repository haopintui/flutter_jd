package com.youdanhui.flutter_jd;

import android.content.Intent;
import android.net.Uri;

import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterJdPlugin */
public class FlutterJdPlugin implements MethodCallHandler {
  public static FlutterJdPlugin instance;
  private static Registrar registrar;
  public final MethodChannel channel;

  private static KeplerAttachParameter mKeplerAttachParameter = new KeplerAttachParameter();
  public static android.os.Handler appHandler = new android.os.Handler();

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
//    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_jd");
//    channel.setMethodCallHandler(new FlutterJdPlugin());
    instance = new FlutterJdPlugin(registrar);
  }

  private FlutterJdPlugin(Registrar registrar) {
    FlutterJdPlugin.registrar = registrar;
    MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_jd");
    channel.setMethodCallHandler(this);
    this.channel = channel;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }
    else if(call.method.equals("openApp")){
      openApp(call,result);
    }
    else {
      result.notImplemented();
    }
  }

  public void openApp(MethodCall call, final Result result){
    String url = call.argument("url");
    String packageName = call.argument("packageName");

    OpenAppAction mOpenAppAction = new OpenAppAction() {
      @Override
      public void onStatus(final int status, final String url) {
        if (status == OpenAppAction.OpenAppAction_start) {
          //开始状态未必一定执行，
          //  dialogShow();
        } else {
          //mKelperTask = null;
          //   dialogDiss();
        }
        if (status == OpenAppAction.OpenAppAction_result_NoJDAPP) {
//              UmengEventHelper.onEvent("京东");
//              Intent intent = new Intent(registrar.activeContext(), WebViewActivity.class);
//              intent.putExtra("url",url);
//              registrar.activeContext().startActivity(intent);
          //未安装京东
        } else if (status == OpenAppAction.OpenAppAction_result_BlackUrl) {
          //不在白名单
        } else if (status == OpenAppAction.OpenAppAction_result_ErrorScheme) {
          //协议错误
        } else if (status == OpenAppAction.OpenAppAction_result_APP) {
          //呼京东成功
          Map<String,String> data = new HashMap<String,String>();
          data.put("status" , "1");
          result.success(data);
        } else if (status == OpenAppAction.OpenAppAction_result_NetError) {
//              ToastUtil.showTextToast(context,"网络异常，请稍后重试");
          //网络异常
        }
//        appHandler.post(new Runnable() {
//          @Override
//          public void run() {
//            if (status == OpenAppAction.OpenAppAction_start) {
//              //开始状态未必一定执行，
//              //  dialogShow();
//            } else {
//              //mKelperTask = null;
//              //   dialogDiss();
//            }
//            if (status == OpenAppAction.OpenAppAction_result_NoJDAPP) {
////              UmengEventHelper.onEvent("京东");
////              Intent intent = new Intent(registrar.activeContext(), WebViewActivity.class);
////              intent.putExtra("url",url);
////              registrar.activeContext().startActivity(intent);
//              //未安装京东
//            } else if (status == OpenAppAction.OpenAppAction_result_BlackUrl) {
//              //不在白名单
//            } else if (status == OpenAppAction.OpenAppAction_result_ErrorScheme) {
//              //协议错误
//            } else if (status == OpenAppAction.OpenAppAction_result_APP) {
//              //呼京东成功
//              Map<String,String> data = new HashMap<String,String>();
//              data.put("status" , "1");
//              result.success(data);
//            } else if (status == OpenAppAction.OpenAppAction_result_NetError) {
////              ToastUtil.showTextToast(context,"网络异常，请稍后重试");
//              //网络异常
//            }
//          }
//        }
//        );
      }
    };
    // 通过 url 呼京东主站
    // url 通过 url 呼京东主站的地址
    // mKeplerAttachParameter 存储第三方传入参数
    // mOpenAppAction 呼京东主站回调
    KeplerApiManager.getWebViewService().
            openAppWebViewPage(registrar.activeContext(),
                    url,
                    mKeplerAttachParameter,
                    mOpenAppAction);

  }

}
