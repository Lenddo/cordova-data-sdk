var Status = /** @class */ (function () {
    function Status(status, message) {
        this.status = status;
        this.message = message;
    }
    Status.prototype.success = function () {
        if (this.status == 0) {
            return true;
        }
        return false;
    };
    return Status;
}());
export { Status };
export var Provider;
(function (Provider) {
    Provider["FACEBOOK"] = "facebook";
    Provider["LINKEDIN"] = "linkedin";
    Provider["YAHOO"] = "yahoo";
    Provider["WINDOWSLIVE"] = "windowslive";
    Provider["GOOGLE"] = "google";
    Provider["KAKAOSTORY"] = "kakaostory";
    Provider["TWITTER"] = "twitter";
})(Provider || (Provider = {}));
var VerificationDataName = /** @class */ (function () {
    function VerificationDataName() {
    }
    return VerificationDataName;
}());
export { VerificationDataName };
var VerificationDataPhone = /** @class */ (function () {
    function VerificationDataPhone() {
    }
    return VerificationDataPhone;
}());
export { VerificationDataPhone };
var VerificationAddress = /** @class */ (function () {
    function VerificationAddress() {
    }
    return VerificationAddress;
}());
export { VerificationAddress };
var VerificationEmploymentPeriod = /** @class */ (function () {
    function VerificationEmploymentPeriod() {
    }
    return VerificationEmploymentPeriod;
}());
export { VerificationEmploymentPeriod };
var VerificationData = /** @class */ (function () {
    function VerificationData() {
    }
    return VerificationData;
}());
export { VerificationData };
var ApplicationPartnerData = /** @class */ (function () {
    function ApplicationPartnerData() {
    }
    return ApplicationPartnerData;
}());
export { ApplicationPartnerData };
var ClientOptions = /** @class */ (function () {
    function ClientOptions() {
        this.wifi_only = false;
        this.enable_sms = true;
        this.api_gateway_url = "";
        this.disable_sms_body = false;
        this.disable_sms_data = false;
        this.disable_batt_charge_data = false;
        this.disable_browser_history_data = false;
        this.disable_calendar_event_data = false;
        this.disable_call_log_data = false;
        this.disable_contact_data = false;
        this.disable_gallery_meta_data = false;
        this.disable_installed_app_data = false;
        this.disable_location_data = false;
        this.log_display = false;
        //Hashing
        this.calendar_email_hashing = false;
        this.calendar_display_name_hashing = false;
        this.calendar_organizer_hashing = false;
        this.contact_email_hashing = false;
        this.contact_name_hashing = false;
        this.phone_number_hashing = false;
    }
    ClientOptions.prototype.setEnableLogDisplay = function (value) {
        this.log_display = value;
    };
    ClientOptions.prototype.setWifiOnly = function (value) {
        this.wifi_only = value;
    };
    ClientOptions.prototype.isWifiOnly = function () {
        return this.wifi_only;
    };
    ClientOptions.prototype.setThemeColor = function (color) {
        this.theme_color = color;
    };
    ClientOptions.prototype.getThemeColor = function () {
        return this.theme_color;
    };
    ClientOptions.prototype.setEnableSms = function (enableSms) {
        this.enable_sms = enableSms;
    };
    ClientOptions.prototype.isSmsEnabled = function () {
        return this.enable_sms;
    };
    ClientOptions.prototype.setApiGatewayUrl = function (api_gateway_url) {
        this.api_gateway_url = api_gateway_url;
    };
    ClientOptions.prototype.setCalendarEmailHashing = function (value) {
        this.calendar_display_name_hashing = value;
    };
    ClientOptions.prototype.getCalendarEmailHashing = function () {
        return this.calendar_email_hashing;
    };
    ClientOptions.prototype.setCalendarDisplayNameHashing = function (value) {
        this.calendar_display_name_hashing = value;
    };
    ClientOptions.prototype.setEnableCalendarOrganizerHashing = function (value) {
        this.calendar_organizer_hashing = value;
    };
    ClientOptions.prototype.setDisableSmsBody = function (value) {
        this.disable_sms_body = value;
    };
    ClientOptions.prototype.getDisableSmsBody = function () {
        return this.disable_sms_body;
    };
    ClientOptions.prototype.setDisableSmsData = function (value) {
        this.disable_sms_data = value;
    };
    ClientOptions.prototype.getDisableSmsData = function () {
        return this.disable_sms_data;
    };
    ClientOptions.prototype.setDisableCallLogData = function (value) {
        this.disable_call_log_data = value;
    };
    ClientOptions.prototype.getDisableContactData = function () {
        return this.disable_contact_data;
        ;
    };
    ClientOptions.prototype.setDisableContactData = function (value) {
        this.disable_contact_data = value;
    };
    ClientOptions.prototype.getDisableCallLogData = function () {
        return this.disable_call_log_data;
    };
    ClientOptions.prototype.setDisableInstalledAppData = function (value) {
        this.disable_installed_app_data = value;
    };
    ClientOptions.prototype.getDisableInstalledAppData = function () {
        return this.disable_installed_app_data;
    };
    ClientOptions.prototype.setDisableBatteryChargeData = function (value) {
        this.disable_batt_charge_data = value;
    };
    ClientOptions.prototype.getDisableBatteryChargeData = function () {
        return this.disable_batt_charge_data;
    };
    ClientOptions.prototype.setDisableBrowserHistoryData = function (value) {
        this.disable_browser_history_data = value;
    };
    ClientOptions.prototype.getDisableBrowserHistoryData = function () {
        return this.disable_browser_history_data;
    };
    ClientOptions.prototype.setDisableCalendarEventData = function (value) {
        this.disable_calendar_event_data = value;
    };
    ClientOptions.prototype.getDisableLocationData = function () {
        return this.disable_location_data;
    };
    ClientOptions.prototype.setDisableLocationData = function (value) {
        this.disable_location_data = value;
    };
    ClientOptions.prototype.getDisableGalleryMetaData = function () {
        return this.disable_gallery_meta_data;
    };
    ClientOptions.prototype.setDisableGalleryMetaData = function (value) {
        this.disable_gallery_meta_data = value;
    };
    ClientOptions.prototype.setEnablePhoneNumberHashing = function (value) {
        this.phone_number_hashing = value;
    };
    ClientOptions.prototype.setEnableContactsEmailHashing = function (value) {
        this.contact_email_hashing = value;
    };
    ClientOptions.prototype.setEnableContactsNameHashing = function (value) {
        this.contact_name_hashing;
    };
    return ClientOptions;
}());
export { ClientOptions };
var InstallationInformation = /** @class */ (function () {
    function InstallationInformation() {
    }
    return InstallationInformation;
}());
export { InstallationInformation };
var Lenddo = /** @class */ (function () {
    /**
     *
     * @param partnerScriptId PartnerScript Id
     * @param secret Secret
     */
    function Lenddo(partnerScriptId, secret) {
        this.partnerScriptId = partnerScriptId;
        this.secret = secret;
    }
    /**
     * Returns and instance of the Lenddo Service if already set
     */
    Lenddo.getInstance = function () {
        if (!Lenddo.instance) {
            console.warn("instance not yet initialized");
            Lenddo.instance = new Lenddo('', '');
        }
        return Lenddo.instance;
    };
    /**
     * Assigns a partnerScriptId and secret to the Lenddo Service
     * @param partnerScriptId
     * @param secret
     */
    Lenddo.setInstance = function (partnerScriptId, secret) {
        if (!Lenddo.instance) {
            Lenddo.instance = new Lenddo(partnerScriptId, secret);
        }
        else {
            Lenddo.instance.partnerScriptId = partnerScriptId;
            Lenddo.instance.secret = secret;
        }
        return Lenddo.instance;
    };
    /**
     * Initialize the Lenddo Data SDK
     * @param options Various clientoptions to pass
     */
    Lenddo.prototype.setup = function (options) {
        var self = this;
        if (self.partnerScriptId === '' || self.secret === '') {
            console.log("partnerScriptid or secret is empty");
            return Promise.reject({ status: 'error', code: 1, message: "partnerScriptid or secret is empty" });
        }
        var resolver = function (resolve, reject) {
            var callback = {
                success: function (param) {
                    if (param.status === 'error') {
                        reject(param.message);
                    }
                    else {
                        resolve(param);
                    }
                },
                error: function (message) {
                    reject(message);
                }
            };
            window.Lenddo.setup(self.partnerScriptId, self.secret, callback, options);
        };
        return new Promise(resolver);
    };
    /**
     * Register a callback handler for Data Sending
     * @param callback The callback object to be called
     */
    Lenddo.prototype.setDataSendingCompleteCallback = function (callback) {
        window.Lenddo.setGlobalDataSendingCallback(callback);
    };
    /**
     * Initialize the Lenddo SDK only if it has not been done before
     * @param options
     */
    Lenddo.prototype.setupIfNeeded = function (options) {
        var _this = this;
        return this.hasStatistics().then(function (result) {
            if (result) {
                return _this.setup(options);
            }
            else {
                return Promise.resolve(true);
            }
        });
    };
    /**
     * Start data collection identified by the application ID
     * @param applicationId The application ID to use
     */
    Lenddo.prototype.start = function (applicationId) {
        var resolver = function (resolve, reject) {
            var callback = {
                success: function (param) {
                    if (param.status === 'error') {
                        reject(param.message);
                    }
                    else {
                        resolve(param);
                    }
                },
                error: function (message) {
                    reject(message);
                }
            };
            window.Lenddo.start(applicationId, callback);
        };
        return new Promise(resolver);
    };
    /**
     * Submit partner data
     * @param partnerData The partner data
     * @param callback
     */
    Lenddo.prototype.submitApplicationData = function (partnerData) {
        var resolver = function (resolve, reject) {
            window.Lenddo.submitApplicationData(partnerData, {
                success: function () {
                    resolve(true);
                },
                error: function (error) {
                    reject(error);
                }
            });
        };
        return new Promise(resolver);
    };
    /**
     * Clear all locally stored lenddo analytics data on the device
     */
    Lenddo.prototype.clear = function () {
        window.Lenddo.clear();
    };
    Lenddo.prototype.hasStatistics = function () {
        var resolver = function (resolve, reject) {
            window.Lenddo.hasStatistics({
                available: function () {
                    resolve(true);
                },
                missing: function () {
                    resolve(false);
                },
                error: function (error) {
                    reject(error);
                }
            });
        };
        return new Promise(resolver);
    };
    /**
     * Gets installation tokens currently stored by the data SDK
     */
    Lenddo.prototype.installInformation = function () {
        var resolver = function (resolve, reject) {
            window.Lenddo.installInformation(function (params) {
                var installation = new InstallationInformation();
                installation.installationId = params.installation_id;
                installation.applicationId = params.application_id;
                installation.serviceToken = params.service_token;
                installation.deviceId = params.device_id;
                resolve(installation);
            });
        };
        return new Promise(resolver);
    };
    Lenddo.prototype.submitProviderToken = function (provider, accessToken, providerId, expiration, extraData) {
        var resolver = function (resolve, reject) {
            window.Lenddo.submitProviderToken({
                success: function () {
                    resolve(new Status(0, "success"));
                },
                error: function (error) {
                    reject(new Status(-1, error));
                }
            }, provider, accessToken, providerId, expiration, extraData);
        };
        return new Promise(resolver);
    };
    return Lenddo;
}());
export { Lenddo };
//# sourceMappingURL=index.js.map