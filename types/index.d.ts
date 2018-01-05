export declare class Status {
    status: Number;
    message: String;
    constructor(status: Number, message: String);
    success(): Boolean;
}
export declare enum Provider {
    FACEBOOK = "facebook",
    LINKEDIN = "linkedin",
    YAHOO = "yahoo",
    WINDOWSLIVE = "windowslive",
    GOOGLE = "google",
    KAKAOSTORY = "kakaostory",
    TWITTER = "twitter",
}
export declare class VerificationDataName {
    first: String;
    middle: String;
    last: String;
}
export declare class VerificationDataPhone {
    home: String;
    mobile: String;
}
export declare class VerificationAddress {
    line_1: String;
    line_2: String;
    city: String;
    administrative_division: String;
    country_code: String;
    postal_code: String;
    latitude: Number;
    longitude: Number;
}
export declare class VerificationEmploymentPeriod {
    start_date: String;
    end_date: String;
}
export declare class VerificationData {
    name: VerificationDataName;
    date_of_birth: String;
    employer: String;
    university: String;
    email: String;
    phone: VerificationDataPhone;
    address: VerificationAddress;
    mothers_maiden_name: VerificationDataName;
    employment_period: VerificationEmploymentPeriod;
}
export declare class ApplicationPartnerData {
    reference_number: String;
    application: any;
    verification_data: VerificationData;
}
export declare class ClientOptions {
    private wifi_only;
    private theme_color;
    private enable_sms;
    private api_gateway_url;
    private disable_sms_body;
    private disable_sms_data;
    private disable_batt_charge_data;
    private disable_browser_history_data;
    private disable_calendar_event_data;
    private disable_call_log_data;
    private disable_contact_data;
    private disable_gallery_meta_data;
    private disable_installed_app_data;
    private disable_location_data;
    private log_display;
    private calendar_email_hashing;
    private calendar_display_name_hashing;
    private calendar_organizer_hashing;
    private contact_email_hashing;
    private contact_name_hashing;
    private phone_number_hashing;
    setEnableLogDisplay(value: Boolean): void;
    setWifiOnly(value: Boolean): void;
    isWifiOnly(): Boolean;
    setThemeColor(color: String): void;
    getThemeColor(): String;
    setEnableSms(enableSms: Boolean): void;
    isSmsEnabled(): Boolean;
    setApiGatewayUrl(api_gateway_url: String): void;
    setCalendarEmailHashing(value: Boolean): void;
    getCalendarEmailHashing(): Boolean;
    setCalendarDisplayNameHashing(value: Boolean): void;
    setEnableCalendarOrganizerHashing(value: Boolean): void;
    setDisableSmsBody(value: Boolean): void;
    getDisableSmsBody(): Boolean;
    setDisableSmsData(value: Boolean): void;
    getDisableSmsData(): Boolean;
    setDisableCallLogData(value: Boolean): void;
    getDisableContactData(): Boolean;
    setDisableContactData(value: Boolean): void;
    getDisableCallLogData(): Boolean;
    setDisableInstalledAppData(value: Boolean): void;
    getDisableInstalledAppData(): Boolean;
    setDisableBatteryChargeData(value: Boolean): void;
    getDisableBatteryChargeData(): Boolean;
    setDisableBrowserHistoryData(value: Boolean): void;
    getDisableBrowserHistoryData(): Boolean;
    setDisableCalendarEventData(value: Boolean): void;
    getDisableLocationData(): Boolean;
    setDisableLocationData(value: Boolean): void;
    getDisableGalleryMetaData(): Boolean;
    setDisableGalleryMetaData(value: Boolean): void;
    setEnablePhoneNumberHashing(value: Boolean): void;
    setEnableContactsEmailHashing(value: Boolean): void;
    setEnableContactsNameHashing(value: Boolean): void;
}
export declare class InstallationInformation {
    applicationId: String;
    serviceToken: String;
    installationId: String;
    deviceId: String;
}
export declare class Lenddo {
    partnerScriptId: String;
    secret: String;
    private static instance;
    /**
     *
     * @param partnerScriptId PartnerScript Id
     * @param secret Secret
     */
    constructor(partnerScriptId: String, secret: String);
    static getInstance(): Lenddo;
    static setInstance(partnerScriptId: String, secret: String): Lenddo;
    /**
     * Initialize the Lenddo Data SDK
     * @param options Various clientoptions to pass
     */
    setup(options: ClientOptions): Promise<any>;
    /**
     * Initialize the Lenddo SDK only if it has not been done before
     * @param options
     */
    setupIfNeeded(options: ClientOptions): Promise<any>;
    /**
     * Start data collection identified by the application ID
     * @param applicationId The application ID to use
     */
    start(applicationId: String): Promise<any>;
    /**
     * Submit partner data
     * @param partnerData The partner data
     * @param callback
     */
    submitApplicationData(partnerData: ApplicationPartnerData): Promise<Status>;
    /**
     * Clear all locally stored lenddo analytics data on the device
     */
    clear(): void;
    hasStatistics(): Promise<Boolean>;
    /**
     * Gets installation tokens currently stored by the data SDK
     */
    installInformation(): Promise<InstallationInformation>;
    submitProviderToken(provider: Provider, accessToken: String, providerId: String, expiration: Number, extraData: String): Promise<Status>;
}
