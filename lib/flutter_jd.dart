import 'dart:async';

import 'package:flutter/services.dart';

class FlutterJd {
  static const MethodChannel _channel =
      const MethodChannel('flutter_jd');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
