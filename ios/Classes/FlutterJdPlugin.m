#import "FlutterJdPlugin.h"
#import <JDSDK/JDKeplerSDK.h>

@implementation FlutterJdPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"flutter_jd"
            binaryMessenger:[registrar messenger]];
  FlutterJdPlugin* instance = [[FlutterJdPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  } else {
    result(FlutterMethodNotImplemented);
  }
}

-(void)init:(FlutterMethodCall *)call result:(FlutterResult)result
{
    NSString *appkey = call.arguments[@"appkey"];
    NSString *appsecret = call.arguments[@"appsecret"];
    
    //注册Kepler服务
    [[KeplerApiManager sharedKPService]asyncInitSdk:appkey secretKey:appsecret sucessCallback:^{
        NSLog(@"注册成功");
        result(@{
                 @"status":@"1"
                 });
    } failedCallback:^(NSError *error) {
        NSLog(@"注册失败");
        result(@{
                 @"status":@"0"
                 });
    }];
}

-(void)openApp:(FlutterMethodCall *)call result:(FlutterResult)result
{
    //    NSString *scheme = call.arguments[@"scheme"];
    //    NSString *packageName = call.arguments[@"packageName"];
    NSString *url = call.arguments[@"url"];
    
    if ([[UIApplication sharedApplication]canOpenURL:[NSURL URLWithString:[NSString stringWithFormat:@"openapp.jdmobile://"]]]) {//判断是否安装京东app
        [[KeplerApiManager sharedKPService]openKeplerPageWithURL:url userInfo:nil failedCallback:^(NSInteger code, NSString *url) {
            NSLog(@"code:%ld,url:%@",(long)code,url);
            result(@{
                     @"status":@"1"
                     });
        }];
    }else{
        result(@{
                 @"status":@"0"
                 });
    }
}

@end
