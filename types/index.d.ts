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
    work_email: String;
    phone: VerificationDataPhone;
    address: VerificationAddress;
    mothers_maiden_name: VerificationDataName;
    employment_period: VerificationEmploymentPeriod;
    government_ids: Array<GovernmentId>;
}
export declare class ApplicationPartnerData {
    reference_number: String;
    application: any;
    verification_data: VerificationData;
}
export interface DataSendingCallback {
    onDataSendingSuccess(result: any): void;
    onDataSendingError(error: any): void;
}
export declare class ClientOptions {
    private wifi_only;
    private theme_color;
    private enable_sms;
    private api_gateway_url;
    private partner_script_id;
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
    private disable_telephony_data;
    private disable_stored_files_data;
    private disable_sensors_data;
    private disable_launchers_data;
    private disable_wifi_data;
    private disable_accounts_data;
    private disable_bluetooth_data;
    private disable_gmail_labels_data;
    private disable_periodical_data_gathering;
    private enable_sms_data;
    private enable_call_log_data;
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
    setPartnerScriptId(partner_script_id: String): void;
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
    getDisableTelephonyData(): Boolean;
    setDisableTelephonyData(value: Boolean): void;
    getDisableStoredFilesData(): Boolean;
    setDisableStoredFilesData(value: Boolean): void;
    getDisableSensorsData(): Boolean;
    setDisableSensorsData(value: Boolean): void;
    getDisableLaunchersData(): Boolean;
    setDisableLaunchersData(value: Boolean): void;
    getDisableWifiData(): Boolean;
    setDisableWifiData(value: Boolean): void;
    getDisableAccountsData(): Boolean;
    setDisableAccountsData(value: Boolean): void;
    getDisableBluetoothData(): Boolean;
    setDisableBluetoothData(value: Boolean): void;
    getDisableGmailLabelsData(): Boolean;
    setDisableGmailLabelsData(value: Boolean): void;
    getDisablePeriodicalDataGathering(): Boolean;
    setDisablePeriodicalDataGathering(value: Boolean): void;
    setEnablePhoneNumberHashing(value: Boolean): void;
    setEnableContactsEmailHashing(value: Boolean): void;
    setEnableContactsNameHashing(value: Boolean): void;
}
export declare class CancelDialogText {
    title: String;
    message: String;
    okButton: String;
    cancelButton: String;
}
export declare class GovernmentId {
    type: String;
    value: String;
}
export declare class AuthorizationStatus {
    client_id: String;
}
export declare class FormDataCollector {
    verification_data: VerificationData;
    applicationId: String;
    partnerScriptId: String;
    authorizationStatus: AuthorizationStatus;
    fields: any;
    applicationFormData: any;
    birthDay: Number;
    birthMonth: Number;
    birthYear: Number;
}
export declare class OnboardingHelper {
    formDataCollector: FormDataCollector;
    cancelDialogText: CancelDialogText;
    apiRegion: String;
}
export interface OnboardingCallback {
    onOnboardingSuccess(result : any) : void;
    onOnboardingError(error : any) : void;
}
export declare class InstallationInformation {
    applicationId: String;
    serviceToken: String;
    installationId: String;
    deviceId: String;
}
export declare class Lenddo {
    private static instance;
    /**
     *
     */
    constructor();
    /**
     * Returns and instance of the Lenddo Service if already set
     */
    static getInstance(): Lenddo;
    /**
     * Initialize Lenddo class wrapper
     */
    static initialize();
    /**
     * Setup the Lenddo Data SDK
     * @param options Various clientoptions to pass
     */
    setupData(options: ClientOptions): Promise<any>;
    /**
     * Register a callback handler for Data Sending
     * @param callback The callback object to be called
     */
    setDataSendingCompleteCallback(callback: DataSendingCallback): void;
    /**
     * Initialize the Lenddo SDK only if it has not been done before
     * @param options
     */
    setupDataIfNeeded(options: ClientOptions): Promise<any>;
    /**
     * Start data collection identified by the application ID
     * @param applicationId The application ID to use
     */
    startData(applicationId: String): Promise<any>;
    /**
     * Start onboarding identified by the formData
     * @param formData the OnboardingHelper to be use
     */
    startOnboarding(helper: OnboardingHelper): Promise<any>;
    /**
     * Register a callback handler for Onboarding
     * @param callback The callback object to be called
     */
    setOnboardingCompleteCallback(callback: OnboardingCallback): void;
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
