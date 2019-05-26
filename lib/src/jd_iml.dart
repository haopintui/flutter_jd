import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:flutter_jd/src/sdk.dart';

final MethodChannel _channel = const MethodChannel('flutter_jd');

const _keyPlatform = "platform";
const _keyResult = "result";
const _keyErrorCode = "errorCode";
const _keyErrorMessage = "errorMessage";

Future<String> gePlatformVersion() async {
  // final String version = await _channel.invokeMethod('getPlatformVersion');
  // return version;
  return await _channel.invokeMethod("getPlatformVersion");
}

Future<String> get platformVersion async {
  final String version = await _channel.invokeMethod('getPlatformVersion');
  return version;
}

Future<bool> isInstall({String scheme,
  String packageName,
  }) async {
    Map des = {
    "scheme": scheme,
    "packageName": packageName
  };
  return await _channel.invokeMethod("isInstall", des);
  // Map result = await _channel.invokeMethod("isInstall", des);
  // return result["status"];
}

Future<InitAsyncResult> initTradeAsync(
    {bool debuggable = false, String version, String appkey, String secretkey}) async {
  Map result = await _channel.invokeMethod(
      "initTradeAsync", {"debuggable": debuggable, "version": version, "appkey": appkey, "secretkey": secretkey});
  assert(debuggable != null);
  return InitAsyncResult(
      platform: result[_keyPlatform],
      isSuccessful: result[_keyResult],
      errorMessage: result[_keyErrorMessage],
      errorCode: result[_keyErrorCode]);
}

Future<OpenResult> openApp({@required String url,
  String scheme,
  String packageName,
  }) async {
  assert(url != null );

  Map des = {
    "url": url,
    "scheme": scheme,
    "packageName": packageName
  };

  Map result = await _channel.invokeMethod("openApp", des);
  return OpenResult(
    status: result["status"]
  );
}