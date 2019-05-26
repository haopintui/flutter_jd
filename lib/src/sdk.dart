
class OpenResult {
  final String status;

  OpenResult({this.status});

}

class InitAsyncResult {
  final String platform;
  final bool isSuccessful;
  final int errorCode;
  final String errorMessage;

  InitAsyncResult(
      {this.platform, this.isSuccessful, this.errorCode, this.errorMessage});
}