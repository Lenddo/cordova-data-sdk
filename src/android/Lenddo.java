package org.apache.cordova.plugin;

import android.support.annotation.NonNull;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import com.lenddo.mobile.core.LenddoCoreInfo;
import com.lenddo.mobile.core.LenddoCoreUtils;
import com.lenddo.mobile.core.models.GovernmentId;
import com.lenddo.mobile.core.models.VerificationData;
import com.lenddo.mobile.datasdk.AndroidData;
import com.lenddo.mobile.datasdk.client.LenddoConstants;
import com.lenddo.mobile.datasdk.listeners.OnDataSendingCompleteCallback;
import com.lenddo.mobile.datasdk.models.ClientOptions;
import com.lenddo.mobile.datasdk.utils.AndroidDataUtils;
import com.lenddo.mobile.onboardingsdk.client.LenddoEventListener;
import com.lenddo.mobile.onboardingsdk.models.FormDataCollector;
import com.lenddo.mobile.onboardingsdk.utils.UIHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Lenddo extends CordovaPlugin {
    public static final String TAG = Lenddo.class.getName();
    public static UIHelper uiHelper;
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
                    webView.getEngine().evaluateJavascript("window.Lenddo.registerDataSendingCompletionCallback.error(" + jsonObject.toString() + ")", null);
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

        }  else if (action.equals("startOnboarding")) {
            JSONObject formDataObject = args.getJSONObject(0);
            Log.d(TAG, "formDataObject = " + formDataObject);
            final FormDataCollector formDataCollector = buildFormDataCollector(formDataObject, callbackContext);

            LenddoEventListener lenddoEventListener = new LenddoEventListener() {
                @Override
                public boolean onButtonClicked(FormDataCollector collector) {
                    collector.setApplicationId(formDataCollector.getApplicationId());
                    collector.setPartnerScriptId(formDataCollector.getPartnerScriptId());

                    return true;
                }

                @Override
                public void onAuthorizeStarted(FormDataCollector collector) {
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("callback", "onboarding_completion_callback");
                        jsonObject.put("status", "started");
                        jsonObject.put("code", 200);
                        jsonObject.put("message", "Onboarding has started");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.getEngine().evaluateJavascript("window.Lenddo.registerOnboardingCompletionCallback.success(" + jsonObject.toString() + ")", null);
                        }
                    });
                }

                @Override
                public void onAuthorizeComplete(FormDataCollector collector) {
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("callback", "onboarding_completion_callback");
                        jsonObject.put("status", "complete");
                        jsonObject.put("code", 200);
                        jsonObject.put("message", "Onboarding is complete");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.getEngine().evaluateJavascript("window.Lenddo.registerOnboardingCompletionCallback.success(" + jsonObject.toString() + ")", null);
                        }
                    });
                }

                @Override
                public void onAuthorizeCanceled(FormDataCollector collector) {
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("callback", "onboarding_completion_callback");
                        jsonObject.put("status", "cancelled");
                        jsonObject.put("code", 300);
                        jsonObject.put("message", "Onboarding is cancelled");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.getEngine().evaluateJavascript("window.Lenddo.registerOnboardingCompletionCallback.error(" + jsonObject.toString() + ")", null);

                        }
                    });
                }

                @Override
                public void onAuthorizeError(int statusCode, String rawResponse) {
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("callback", "onboarding_completion_callback");
                        jsonObject.put("status", "error");
                        jsonObject.put("code", statusCode);
                        jsonObject.put("message", rawResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cordova.getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            webView.getEngine().evaluateJavascript("window.Lenddo.registerOnboardingCompletionCallback.error(" + jsonObject.toString() + ")", null);

                        }
                    });
                }

                @Override
                public void onAuthorizeFailure(Throwable throwable) {
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("callback", "onboarding_completion_callback");
                        jsonObject.put("status", "failure");
                        jsonObject.put("code", 400);
                        jsonObject.put("message", throwable.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cordova.getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            webView.getEngine().evaluateJavascript("window.Lenddo.registerOnboardingCompletionCallback.error(" + jsonObject.toString() + ")", null);

                        }
                    });
                }
            };

            String title = "Confirm";
            String message = "Are you sure you want to cancel?";
            String okButton = "YES";
            String cancelButton = "NO";

            JSONObject cancelDialogTextJsonObject = formDataObject.optJSONObject("cancelDialogText");
            if (cancelDialogTextJsonObject != null && cancelDialogTextJsonObject.length() > 0) {
                String titleObj = cancelDialogTextJsonObject.optString("title");
                if (!titleObj.isEmpty()) {
                    title = titleObj;
                }
                String messageObj = cancelDialogTextJsonObject.optString("message");
                if (!messageObj.isEmpty()) {
                    message = messageObj;
                }
                String okButtonObj = cancelDialogTextJsonObject.optString("okButton");
                if (!okButtonObj.isEmpty()) {
                    okButton = okButtonObj;
                }
                String cancelButtonObj = cancelDialogTextJsonObject.optString("cancelButton");
                if (!cancelButtonObj.isEmpty()) {
                    cancelButton = cancelButtonObj;
                }
            }

            String apiRegion = formDataObject.optString("apiRegion");
            if (!apiRegion.isEmpty() && apiRegion.length() < 3) {
                UIHelper.setApiRegion(apiRegion);
            }

            uiHelper = new UIHelper(cordova.getActivity(), lenddoEventListener);
            uiHelper.customizeBackPopup(title, message, okButton, cancelButton);

            cordova.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    UIHelper.showAuthorize(cordova.getActivity(), uiHelper);
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
        } else if (action.equals("clear")) {
            AndroidData.clear(cordova.getActivity().getApplication());
            LenddoCoreInfo.initCoreInfo(cordova.getActivity().getApplication());
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

    @NonNull
    private FormDataCollector buildFormDataCollector(JSONObject optionsObject, final CallbackContext callbackContext) {
        final FormDataCollector formDataCollector = new FormDataCollector();
        String applicationId = optionsObject.optString("applicationId");
        Log.d(TAG, "optionsObject = " + optionsObject);

        if (!applicationId.isEmpty()) {
            formDataCollector.setApplicationId(applicationId);
        }
        String partnerScriptId = optionsObject.optString("partnerScriptId");
        if (!partnerScriptId.isEmpty()) {
            formDataCollector.setPartnerScriptId(partnerScriptId);
        }
        JSONObject verificationDataJsonObject = optionsObject.optJSONObject("verification_data");
        if (verificationDataJsonObject != null && verificationDataJsonObject.length() > 0) {
            VerificationData verification_data = new VerificationData();

            JSONObject nameJsonObject = verificationDataJsonObject.optJSONObject("name");
            if (nameJsonObject != null && nameJsonObject.length() > 0) {
                String first = nameJsonObject.optString("first", "");
                String middle = nameJsonObject.optString("middle", "");
                String last = nameJsonObject.optString("last", "");

                verification_data.name.first = first;
                verification_data.name.last = last;
                verification_data.name.middle = middle;
            }
            formDataCollector.setFirstName(verification_data.name.first);
            formDataCollector.setLastName(verification_data.name.last);
            formDataCollector.setMiddleName(verification_data.name.middle);

            JSONObject phoneJsonObject = verificationDataJsonObject.optJSONObject("phone");
            if (phoneJsonObject != null && phoneJsonObject.length() > 0) {
                String mobile = phoneJsonObject.optString("mobile", "");
                String home = phoneJsonObject.optString("home", "");

                verification_data.phone.mobile = mobile;
                verification_data.phone.home = home;
            }
            formDataCollector.setMobilePhone(verification_data.phone.mobile);
            formDataCollector.setHomePhone(verification_data.phone.home);

            JSONObject employmentPeriodJsonObject = verificationDataJsonObject.optJSONObject("employment_period");
            if (employmentPeriodJsonObject != null && employmentPeriodJsonObject.length() > 0) {
                String start_date = employmentPeriodJsonObject.optString("start_date", "");
                String end_date = employmentPeriodJsonObject.optString("end_date", "");

                verification_data.employment_period.start_date = start_date;
                verification_data.employment_period.end_date = end_date;
            }
            formDataCollector.setStartEmploymentDate(verification_data.employment_period.start_date);
            formDataCollector.setEndEmploymentDate(verification_data.employment_period.end_date);

            JSONObject mothersMaidenNameJsonObject = verificationDataJsonObject.optJSONObject("mothers_maiden_name");
            if (mothersMaidenNameJsonObject != null && mothersMaidenNameJsonObject.length() > 0) {
                String first = mothersMaidenNameJsonObject.optString("first", "");
                String middle = mothersMaidenNameJsonObject.optString("middle", "");
                String last = mothersMaidenNameJsonObject.optString("last", "");

                verification_data.mothers_maiden_name.first = first;
                verification_data.mothers_maiden_name.last = last;
                verification_data.mothers_maiden_name.middle = middle;
            }

            JSONObject addressJsonObject = verificationDataJsonObject.optJSONObject("address");
            if (addressJsonObject != null && addressJsonObject.length() > 0) {
                String line_1 = addressJsonObject.optString("line_1", "");
                String line_2 = addressJsonObject.optString("line_2", "");
                String city = addressJsonObject.optString("city", "");
                String administrative_division = addressJsonObject.optString("administrative_division", "");
                String country_code = addressJsonObject.optString("country_code", "");
                String postal_code = addressJsonObject.optString("postal_code", "");
                double latitude = addressJsonObject.optDouble("latitude", 0);
                double longitude = addressJsonObject.optDouble("longitude", 0);

                verification_data.address.line_1 = line_1;
                verification_data.address.line_2 = line_2;
                verification_data.address.city = city;
                verification_data.address.administrative_division = administrative_division;
                verification_data.address.country_code = country_code;
                verification_data.address.postal_code = postal_code;
                verification_data.address.latitude = latitude;
                verification_data.address.longitude = longitude;
            }
            formDataCollector.setAddress(verification_data.address);

            JSONArray governmentIdsJsonArray = verificationDataJsonObject.optJSONArray("government_ids");
            if (governmentIdsJsonArray != null && governmentIdsJsonArray.length() > 0) {
                for (int i = 0; i < governmentIdsJsonArray.length(); i++) {
                    JSONObject governmentIdJsonObject = governmentIdsJsonArray.optJSONObject(i);

                    if (governmentIdJsonObject != null && governmentIdJsonObject.length() > 0) {
                        String type = governmentIdJsonObject.optString("type", "");
                        String value = governmentIdJsonObject.optString("value", "");

                        GovernmentId governmentId = new GovernmentId(type, value);
                        verification_data.government_ids.add(governmentId);
                    }
                }
            }
            formDataCollector.setGovernmentIds(verification_data.government_ids);
        }

        JSONObject fieldsDataJsonObject = optionsObject.optJSONObject("fields");
        if (fieldsDataJsonObject != null && fieldsDataJsonObject.length() > 0) {
            Iterator<?> keys = fieldsDataJsonObject.keys();
            while(keys.hasNext()) {
                String key = (String) keys.next();
                String values = fieldsDataJsonObject.optString(key);
                formDataCollector.putField(key, values);
            }
        }

        String birthDay = optionsObject.optInt("birthDay");
        if (!birthDay.isEmpty()) {
            formDataCollector.setBirthDay(birthDay);
        }

        String birthMonth = optionsObject.optInt("birthMonth");
        if (!birthMonth.isEmpty()) {
            formDataCollector.setBirthMonth(birthMonth);
        }

        String birthYear = optionsObject.optInt("birthYear");
        if (!birthYear.isEmpty()) {
            formDataCollector.setBirthYear(birthYear);
        }

        return formDataCollector;
    }
}
