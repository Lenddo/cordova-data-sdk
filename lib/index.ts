declare var window: any

export class Status {
    public status: Number;
    public message: String;

    constructor(status: Number, message: String) {
        this.status = status;
        this.message = message;
    }

    success(): Boolean {
        if (this.status == 0) {
            return true;
        }
        return false;
    }
}

export enum Provider {
    FACEBOOK = "facebook",
    LINKEDIN = "linkedin",
    YAHOO = "yahoo",
    WINDOWSLIVE = "windowslive",
    GOOGLE = "google",
    KAKAOSTORY = "kakaostory",
    TWITTER = "twitter"
}

export class VerificationDataName {
    public first: String;
    public middle: String;
    public last: String;
}

export class VerificationDataPhone {
    public home: String;
    public mobile: String;
}

export class VerificationAddress {
    public line_1: String;
    public line_2: String;
    public city: String;
    public administrative_division: String;
    public country_code: String;
    public postal_code: String;
    public latitude: Number;
    public longitude: Number;
}

export class VerificationEmploymentPeriod {
    public start_date: String;
    public end_date: String;
}

export class VerificationData {
    public name: VerificationDataName;
    public date_of_birth: String;
    public employer: String;
    public university: String;
    public email: String;
    public phone: VerificationDataPhone;
    public address: VerificationAddress;
    public mothers_maiden_name: VerificationDataName;
    public employment_period: VerificationEmploymentPeriod;
}

export class ApplicationPartnerData {
    public reference_number: String;
    public application: any;
    public verification_data: VerificationData;
}

export interface DataSendingCallback {
    onDataSendingSuccess(result : any) : void;
    onDataSendingError(error : any) : void;
}

export class ClientOptions {
    private wifi_only: Boolean = false;
    private theme_color: String;
    private enable_sms: Boolean = true;
    private api_gateway_url: String = "";

    private disable_sms_body: Boolean = false;
    private disable_sms_data: Boolean = false;
    private disable_batt_charge_data: Boolean = false;
    private disable_browser_history_data: Boolean = false;
    private disable_calendar_event_data: Boolean = false;
    private disable_call_log_data: Boolean = false;
    private disable_contact_data: Boolean = false;
    private disable_gallery_meta_data: Boolean = false;
    private disable_installed_app_data: Boolean = false;
    private disable_location_data: Boolean = false;
    private log_display: Boolean = false;

    //Hashing
    private calendar_email_hashing: Boolean = false;
    private calendar_display_name_hashing: Boolean = false;
    private calendar_organizer_hashing: Boolean = false;
    private contact_email_hashing: Boolean = false;
    private contact_name_hashing: Boolean = false;
    private phone_number_hashing: Boolean = false;

    setEnableLogDisplay(value: Boolean) {
        this.log_display = value;
    }

    setWifiOnly(value: Boolean) {
        this.wifi_only = value;
    }

    isWifiOnly(): Boolean {
        return this.wifi_only;
    }

    setThemeColor(color: String) {
        this.theme_color = color;
    }

    getThemeColor(): String {
        return this.theme_color;
    }

    setEnableSms(enableSms: Boolean) {
        this.enable_sms = enableSms;
    }

    isSmsEnabled(): Boolean {
        return this.enable_sms;
    }

    setApiGatewayUrl(api_gateway_url: String) {
        this.api_gateway_url = api_gateway_url;
    }

    setCalendarEmailHashing(value: Boolean) {
        this.calendar_display_name_hashing = value;
    }

    getCalendarEmailHashing(): Boolean {
        return this.calendar_email_hashing;
    }

    setCalendarDisplayNameHashing(value: Boolean) {
        this.calendar_display_name_hashing = value;
    }

    setEnableCalendarOrganizerHashing(value: Boolean) {
        this.calendar_organizer_hashing = value;
    }

    setDisableSmsBody(value: Boolean) {
        this.disable_sms_body = value;
    }

    getDisableSmsBody(): Boolean {
        return this.disable_sms_body;
    }

    setDisableSmsData(value: Boolean) {
        this.disable_sms_data = value;
    }

    getDisableSmsData(): Boolean {
        return this.disable_sms_data;
    }

    setDisableCallLogData(value: Boolean) {
        this.disable_call_log_data = value;
    }

    getDisableContactData(): Boolean {
        return this.disable_contact_data;;
    }

    setDisableContactData(value: Boolean) {
        this.disable_contact_data = value;
    }

    getDisableCallLogData(): Boolean {
        return this.disable_call_log_data;
    }

    setDisableInstalledAppData(value: Boolean) {
        this.disable_installed_app_data = value;
    }

    getDisableInstalledAppData(): Boolean {
        return this.disable_installed_app_data;
    }

    setDisableBatteryChargeData(value: Boolean) {
        this.disable_batt_charge_data = value;
    }

    getDisableBatteryChargeData(): Boolean {
        return this.disable_batt_charge_data;
    }

    setDisableBrowserHistoryData(value: Boolean) {
        this.disable_browser_history_data = value;
    }

    getDisableBrowserHistoryData(): Boolean {
        return this.disable_browser_history_data;
    }

    setDisableCalendarEventData(value: Boolean) {
        this.disable_calendar_event_data = value;
    }

    getDisableLocationData(): Boolean {
        return this.disable_location_data;
    }

    setDisableLocationData(value: Boolean) {
        this.disable_location_data = value;
    }

    getDisableGalleryMetaData(): Boolean {
        return this.disable_gallery_meta_data;
    }

    setDisableGalleryMetaData(value: Boolean) {
        this.disable_gallery_meta_data = value;
    }

    setEnablePhoneNumberHashing(value: Boolean) {
        this.phone_number_hashing = value;
    }

    setEnableContactsEmailHashing(value: Boolean) {
        this.contact_email_hashing = value;
    }

    setEnableContactsNameHashing(value: Boolean) {
        this.contact_name_hashing;
    }
}

export class InstallationInformation {
    public applicationId: String;
    public serviceToken: String;
    public installationId: String;
    public deviceId : String;
}

export class Lenddo {
    partnerScriptId: String;
    secret: String;
    private static instance: Lenddo;

    /**
     * 
     * @param partnerScriptId PartnerScript Id
     * @param secret Secret
     */
    constructor(partnerScriptId: String, secret: String) {
        this.partnerScriptId = partnerScriptId;
        this.secret = secret;
    }

    /**
     * Returns and instance of the Lenddo Service if already set
     */
    public static getInstance(): Lenddo {
        if (!Lenddo.instance) {
            console.warn("instance not yet initialized");
            Lenddo.instance = new Lenddo('', '');
        }
        return Lenddo.instance;
    }

    /**
     * Assigns a partnerScriptId and secret to the Lenddo Service
     * @param partnerScriptId 
     * @param secret 
     */
    public static setInstance(partnerScriptId: String, secret: String): Lenddo {
        if (!Lenddo.instance) {
            Lenddo.instance = new Lenddo(partnerScriptId, secret);
        } else {
            Lenddo.instance.partnerScriptId = partnerScriptId;
            Lenddo.instance.secret = secret;
        }
        return Lenddo.instance;
    }

    /**
     * Initialize the Lenddo Data SDK
     * @param options Various clientoptions to pass
     */
    setup(options: ClientOptions): Promise<any> {
        let self = this;
        if (self.partnerScriptId === '' || self.secret === '') {
            console.log("partnerScriptid or secret is empty")
            return Promise.reject({status: 'error', code: 1, message: "partnerScriptid or secret is empty"});
        }

        let resolver = function (resolve: Function, reject: Function) {
            let callback = {
                success: function (param: any) {
                    if (param.status === 'error') {
                        reject(param.message);
                    } else {
                        resolve(param);
                    }
                },

                error: function (message: any) {
                    reject(message);
                }
            }
            window.Lenddo.setup(self.partnerScriptId, self.secret, callback, options)
        }

        return new Promise<any>(resolver);
    }

    /**
     * Register a callback handler for Data Sending
     * @param callback The callback object to be called
     */
    setDataSendingCompleteCallback(callback : DataSendingCallback) {
        window.Lenddo.setGlobalDataSendingCallback(callback);
    }

    /**
     * Initialize the Lenddo SDK only if it has not been done before
     * @param options 
     */
    setupIfNeeded(options : ClientOptions) : Promise<any> {
        return this.hasStatistics().then((result : Boolean) => {
            if (result) {
                return this.setup(options);
            } else {
                return Promise.resolve(true);
            }
        })
    }

    /**
     * Start data collection identified by the application ID
     * @param applicationId The application ID to use
     */
    start(applicationId: String): Promise<any> {
        let resolver = function (resolve: Function, reject: Function) {
            let callback = {
                success: function (param: any) {
                    if (param.status === 'error') {
                        reject(param.message);
                    } else {
                        resolve(param);
                    }
                },

                error: function (message: any) {
                    reject(message);
                }
            }
            window.Lenddo.start(applicationId, callback);
        }
        return new Promise<any>(resolver);
    }

    /**
     * Submit partner data
     * @param partnerData The partner data
     * @param callback 
     */
    submitApplicationData(partnerData: ApplicationPartnerData): Promise<Status> {
        let resolver = function (resolve: Function, reject: Function) {
            window.Lenddo.submitApplicationData(partnerData, {
                success: function () {
                    resolve(true);
                },
                error: function (error: Status) {
                    reject(error);
                }
            })
        }
        return new Promise<Status>(resolver);
    }

    /**
     * Clear all locally stored lenddo analytics data on the device
     */
    clear() {
        window.Lenddo.clear();
    }

    hasStatistics(): Promise<Boolean> {
        let resolver = function (resolve: Function, reject: Function) {
            window.Lenddo.hasStatistics({
                available: function () {
                    resolve(true);
                },
                missing: function () {
                    resolve(false);
                },
                error: function (error: String) {
                    reject(error);
                }
            })
        }
        return new Promise<Boolean>(resolver);
    }

    /**
     * Gets installation tokens currently stored by the data SDK
     */
    installInformation(): Promise<InstallationInformation> {
        let resolver = function (resolve: Function, reject: Function) {
            window.Lenddo.installInformation((params: any) => {
                var installation = new InstallationInformation();
                installation.installationId = params.installation_id;
                installation.applicationId = params.application_id;
                installation.serviceToken = params.service_token;
                installation.deviceId = params.device_id;
                resolve(installation);
            })
        }
        return new Promise<InstallationInformation>(resolver);
    }

    submitProviderToken(provider: Provider, accessToken: String, providerId: String,
        expiration: Number, extraData: String): Promise<Status> {
        let resolver = function (resolve: Function, reject: Function) {
            window.Lenddo.submitProviderToken({
                success: function () {
                    resolve(new Status(0, "success"));
                },
                error: function (error: any) {
                    reject(new Status(-1, error));
                }
            }, provider, accessToken, providerId, expiration, extraData);
        }
        return new Promise<Status>(resolver);
    }
}