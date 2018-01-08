var exec = require('cordova/exec');

var Lenddo = {
  setup: function (partnerScriptId, applicationSecret, callback, options) {
    exec(function (winParam) { 
        callback.success(winParam);
      },
      function (error) {
        callback.error(error);
      },
      "Lenddo",
      "setup",
      [partnerScriptId, applicationSecret, options]);
  },
  setGlobalDataSendingCallback: function(callback) {
    Lenddo.registerDataSendingCompletionCallbackGlobal = {
      success: function (param) {
          if (param.status === 'error') {
            callback.onDataSendingError(param.message)
          }
          else {
            callback.onDataSendingSuccess(param);
          }
      },
      error: function (message) {
        callback.onDataSendingError(message);
      }
    };
  },
  start: function (applicationId, callback) {
    Lenddo.registerDataSendingCompletionCallbackLocal = callback;
    exec(function (winParam) { },
      function (error) { },
      "Lenddo",
      "start",
      [applicationId]);
  },
  clear: function () {
    exec(function (winParam) { },
      function (error) { },
      "Lenddo",
      "clear",
      []);
  },
  submitApplicationData: function (partnerData, callback) {
      Lenddo.registerPartnerApplicationCallback = callback;
      exec(function (winParam) {
      },
      function (error) {
      },
      "Lenddo",
      "send_partner_data",
      [partnerData]);
  },
  version: function (callback) {
    exec(function (winParam) {
      callback(winParam);
    },
      function (error) { },
      "Lenddo",
      "version",
      []);
  },
  installInformation: function (callback) {
    exec(function (winParam) {
      callback(winParam);
    },
      function (error) { },
      "Lenddo",
      "tokens",
      []);
  },
  submitProviderToken: function (callback, provider,
    access_token, provider_id, expiration, extra_data) {
      exec(function (winParam) {
        callback.success();
      },
      function (error) {
        callback.error(error);
      },
      "Lenddo",
      "partner_token",
      [provider, access_token, provider_id, expiration, extra_data]);
  },
  hasStatistics: function (callback) {
    exec(function (winParam) {
      if (winParam.value) {
        callback.available();
      } else {
        callback.missing();
      }
    },
      function (error) {
        callback.error(error);
      },
      "Lenddo",
      "statistics",
      []);
  },
  registerPartnerApplicationCallback: {
    success: function(result) {},
    error: function(error) {}
  },
  registerDataSendingCompletionCallback: {
    success: function(result) {
      Lenddo.registerDataSendingCompletionCallbackGlobal.success(result);
      return Lenddo.registerDataSendingCompletionCallbackLocal.success(result);
    },
    error: function(error) {
      Lenddo.registerDataSendingCompletionCallbackGlobal.error(error);
      return Lenddo.registerDataSendingCompletionCallbackLocal.error(error);
    }
  },
  registerProviderTokenCallback: {
    success: function(result) {},
    error: function(error) {}
  },
  registerDataSendingCompletionCallbackGlobal: {
    success: function(result) {},
    error: function(error) {}
  },
  registerDataSendingCompletionCallbackLocal: {
    success: function(result) {},
    error: function(error) {}
  }
}

module.exports = Lenddo;
