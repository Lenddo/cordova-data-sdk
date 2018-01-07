package org.apache.cordova.plugin;

import android.support.annotation.NonNull;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import com.lenddo.core.LenddoCoreUtils;
import com.lenddo.data.AndroidData;
import com.lenddo.data.client.LenddoConstants;
import com.lenddo.data.listeners.OnDataSendingCompleteCallback;
import com.lenddo.data.models.ClientOptions;
import com.lenddo.data.utils.AndroidDataUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Lenddo extends CordovaPlugin {
    public static final String TAG = Lenddo.class.getName();

    OnDataSendingCompleteCallback providerTokenSendingCompleteCallback = new OnDataSendingCompleteCallback() {
        @Override
        public void onDataSendingSuccess() {
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("callback", "register_provider_token_callback");
                jsonObject.put("status", "success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.getEngine().evaluateJavascript("window.Lenddo.registerDataSendingCompletionCallback.success(" + jsonObject.toString() +")", null);
                }
            });
        }

        @Override
        public void onDataSendingError(final int i, final String s) {
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("callback", "data_sending_completion_callback");
                jsonObject.put("status", "error");
                jsonObject.put("code", i);
                jsonObject.put("message", s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cordova.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    webView.getEngine().evaluateJavascript("window.Lenddo.registerDataSendingCompletionCallback.error(" + jsonObject.toString() + "')", null);

                }
            });
        }

        @Override
        public void onDataSendingFailed(Throwable throwable) {
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("callback", "data_sending_completion_callback");
                jsonObject.put("status", "error");
                jsonObject.put("code", 1);
                jsonObject.put("message", throwable.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.getEngine().evaluateJavascript("window.Lenddo.registerProviderTokenCallback.error(" + jsonObject.toString() + ")", null);
                }
            });
        }
    };

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("version")) {
            callbackContext.success(LenddoConstants.DATA_SDK_VERSION);
        } else if (action.equals("setup")) {
            final String psid = args.getString(0);
            final String secret = args.getString(1);
            JSONObject optionsObject = args.getJSONObject(2);

            final ClientOptions clientOptions = buildClientOptions(optionsObject, callbackContext);
            
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AndroidData.setup(cordova.getActivity().getApplication(), psid, secret, clientOptions);
                    callbackContext.success();
                }
            });


            return true;
        } else if (action.equals("start")) {
            final String applicationId = args.getString(0);
            Log.d(TAG, "start " + applicationId);
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    AndroidData.startAndroidData(cordova.getActivity(), applicationId);
                }
            });

        } else if (action.equals("statistics")) {
            JSONObject object = new JSONObject();
            if (AndroidData.statisticsEnabled(cordova.getActivity().getApplication())) {
                object.put("value", true);
                callbackContext.success(object);
            } else {
                object.put("value", false);
                callbackContext.success(object);
            }
        } else if (action.equals("profile_type")) {
            callbackContext.success(AndroidData.getProfileType(cordova.getActivity().getApplication()));
        } else if (action.equals("clear")) {
            AndroidData.clear(cordova.getActivity().getApplication());
        } else if (action.equals("send_partner_data")) {
            JSONObject partnerData = args.getJSONObject(0);
            AndroidData.sendPartnerApplicationData(cordova.getActivity(), partnerData.toString(), new OnDataSendingCompleteCallback() {
                @Override
                public void onDataSendingSuccess() {
                    Log.d(TAG, "registerPartnerApplicationCallback onDataSendingSuccess");
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("callback", "partner_application_callback");
                        jsonObject.put("status", "success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.getEngine().evaluateJavascript("window.Lenddo.registerPartnerApplicationCallback.success(" + jsonObject.toString() + ")", null);
                        }
                    });
                }

                @Override
                public void onDataSendingError(int i, String s) {
                    Log.d(TAG, "registerPartnerApplicationCallback onDataSendingError " + s);
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("callback", "partner_application_callback");
                        jsonObject.put("status", "error");
                        jsonObject.put("code", i);
                        jsonObject.put("message", s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.getEngine().evaluateJavascript("window.Lenddo.registerPartnerApplicationCallback.error(" + jsonObject.toString() + ")", null);
                        }
                    });
                }

                @Override
                public void onDataSendingFailed(Throwable throwable) {
                    JSONObject result = new JSONObject();
                    try {
                        result.put("code", 1);
                        result.put("message", throwable.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "registerPartnerApplicationCallback onDataSendingFailed " + throwable.getMessage());
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("callback", "partner_application_callback");
                        jsonObject.put("status", "error");
                        jsonObject.put("code", 1);
                        jsonObject.put("message", throwable.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.getEngine().evaluateJavascript("window.Lenddo.registerPartnerApplicationCallback.error(" + jsonObject.toString() + ")", null);
                        }
                    });

                }
            });
            callbackContext.success();
        } else if (action.equals("get_application_id")) {
            callbackContext.success(AndroidDataUtils.getApplicationId(cordova.getActivity()));
        } else if (action.equals("tokens")) {
            JSONObject tokens = new JSONObject();
            tokens.put("application_id", AndroidDataUtils.getApplicationId(cordova.getActivity()));
            tokens.put("service_token", AndroidDataUtils.getServiceToken(cordova.getActivity()));
            tokens.put("installation_id", LenddoCoreUtils.getInstallationId(cordova.getActivity()));
            tokens.put("device_id", AndroidDataUtils.getDeviceUID(cordova.getActivity()));
            callbackContext.success(tokens);
        } else if (action.equals("partner_token")) {
            String provider = args.getString(0);
            String accessToken = args.getString(1);
            String providerId = args.getString(2);
            long expiration = args.getLong(3);
            String extraData = args.getString(4);
            AndroidData.setProviderAccessToken(cordova.getActivity(), provider, accessToken, providerId, extraData, expiration, providerTokenSendingCompleteCallback);
        }
        return false;
    }

    @NonNull
    private ClientOptions buildClientOptions(JSONObject optionsObject, final CallbackContext callbackContext) {
        final ClientOptions clientOptions = new ClientOptions();

        clientOptions.registerPartnerApplicationCallback(new OnDataSendingCompleteCallback() {
            @Override
            public void onDataSendingSuccess() {
                Log.d(TAG, "registerPartnerApplicationCallback onDataSendingSuccess");
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("callback", "partner_application_callback");
                    jsonObject.put("status", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.getEngine().evaluateJavascript("window.Lenddo.registerPartnerApplicationCallback.success(" + jsonObject.toString() + ")", null);
                    }
                });
            }

            @Override
            public void onDataSendingError(int i, String s) {
                Log.d(TAG, "registerPartnerApplicationCallback onDataSendingError " + s);
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("callback", "partner_application_callback");
                    jsonObject.put("status", "error");
                    jsonObject.put("code", i);
                    jsonObject.put("message", s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.getEngine().evaluateJavascript("window.Lenddo.registerPartnerApplicationCallback.error(" + jsonObject.toString() + ")", null);
                    }
                });
            }

            @Override
            public void onDataSendingFailed(Throwable throwable) {
                Log.d(TAG, "registerPartnerApplicationCallback onDataSendingFailed " + throwable.getMessage());
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("callback", "partner_application_callback");
                    jsonObject.put("status", "error");
                    jsonObject.put("code", 1);
                    jsonObject.put("message", throwable.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.getEngine().evaluateJavascript("window.Lenddo.registerPartnerApplicationCallback.error(" + jsonObject.toString() + ")", null);
                    }
                });
            }
        });

        clientOptions.registerDataSendingCompletionCallback(new OnDataSendingCompleteCallback() {
            @Override
            public void onDataSendingSuccess() {
                Log.d(TAG, "registerDataSendingCompletionCallback onDataSendingSuccess ");

                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("callback", "data_sending_completion_callback");
                    jsonObject.put("status", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.getEngine().evaluateJavascript("window.Lenddo.registerDataSendingCompletionCallback.success(" + jsonObject.toString() + ")", null);
                    }
                });
            }

            @Override
            public void onDataSendingError(int i, String s) {
                Log.d(TAG, "registerDataSendingCompletionCallback onDataSendingError " + s);
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("callback", "data_sending_completion_callback");
                    jsonObject.put("status", "error");
                    jsonObject.put("code", i);
                    jsonObject.put("message", s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.getEngine().evaluateJavascript("window.Lenddo.registerDataSendingCompletionCallback.error(" + jsonObject.toString() + ")", null);

                    }
                });

            }

            @Override
            public void onDataSendingFailed(Throwable throwable) {
                Log.d(TAG, "registerDataSendingCompletionCallback onDataSendingFailed " + throwable.getMessage());
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("callback", "data_sending_completion_callback");
                    jsonObject.put("status", "error");
                    jsonObject.put("code", 1);
                    jsonObject.put("message", throwable.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.getEngine().evaluateJavascript("window.Lenddo.registerDataSendingCompletionCallback.error(" + jsonObject.toString() + ")", null);
                    }
                });
            }
        });

        clientOptions.registerProviderTokenCallback(providerTokenSendingCompleteCallback);

        clientOptions.setWifiOnly(optionsObject.optBoolean("wifi_only"));
        clientOptions.enableLogDisplay(optionsObject.optBoolean("log_display"));

        if (optionsObject.has("theme_color")) {
            clientOptions.setThemeColor(optionsObject.optString("theme_color"));
        }

        try {
            if (optionsObject.has("api_gateway_url") && !optionsObject.get("api_gateway_url").equals("")) {
                clientOptions.setApiGatewayUrl(optionsObject.optString("api_gateway_url", null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (optionsObject.optBoolean("calendar_email_hashing")) {
            clientOptions.enableCalendarEmailHashing();
        }

        if (optionsObject.optBoolean("disable_sms_body")) {
            clientOptions.disableSMSBodyCollection();
        }

        if (optionsObject.optBoolean("disable_sms_data")) {
            clientOptions.disableSMSDataCollection();
        }

        if (optionsObject.optBoolean("disable_batt_charge_data")) {
            clientOptions.disableBattChargeDataCollection();
        }

        if (optionsObject.optBoolean("disable_browser_history_data")) {
            clientOptions.disableBrowserHistoryDataCollection();
        }

        if (optionsObject.optBoolean("disable_calendar_event_data")) {
            clientOptions.disableCalendarEventDataCollection();
        }

        if (optionsObject.optBoolean("disable_call_log_data")) {
            clientOptions.disableCallLogDataCollection();
        }

        if (optionsObject.optBoolean("disable_contact_data")) {
            clientOptions.disableContactDataCollection();
        }

        if (optionsObject.optBoolean("disable_gallery_meta_data")) {
            clientOptions.disableGalleryMetaDataCollection();
        }

        if (optionsObject.optBoolean("disable_installed_app_data")) {
            clientOptions.disableInstalledAppDataCollection();
        }

        if (optionsObject.optBoolean("disable_location_data")) {
            clientOptions.disableLocationDataCollection();
        }

        //Hashing
        if (optionsObject.optBoolean("calendar_organizer_hashing")) {
            clientOptions.enableCalendarOrganizerHashing();
        }

        if (optionsObject.optBoolean("calendar_display_name_hashing")) {
            clientOptions.enableCalendarDisplayNameHashing();
        }

        if (optionsObject.optBoolean("phone_number_hashing")) {
            clientOptions.enablePhoneNumberHashing();
        }

        if (optionsObject.optBoolean("contacts_name_hashing")) {
            clientOptions.enableContactsNameHashing();
        }

        if (optionsObject.optBoolean("calendar_email_hashing")) {
            clientOptions.enableCalendarEmailHashing();
        }

        if (optionsObject.optBoolean("contacts_email_hashing")) {
            clientOptions.enableContactsEmailHashing();
        }

        return clientOptions;
    }
}
