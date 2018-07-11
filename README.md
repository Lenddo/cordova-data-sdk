---
Title: Lenddo Cordova Plugin
Description: Cordova bindings for the Lenddo Data SDK and Onboarding SDK
---

# cordova-plugin-lenddo

This plugin defines a global `Lenddo` object, which allows for using features of the Lenddo SDK.
Although the object is in the global scope, it is not available until after the `deviceready` event.

```js
document.addEventListener("deviceready", onDeviceReady, false);
function onDeviceReady() {
    Lenddo.initialize()
}
```

Report issues with this plugin on the [Apache Cordova issue tracker](https://issues.apache.org/jira/issues/?jql=project%20%3D%20CB%20AND%20status%20in%20%28Open%2C%20%22In%20Progress%22%2C%20Reopened%29%20AND%20resolution%20%3D%20Unresolved%20AND%20component%20%3D%20%22Plugin%20Device%22%20ORDER%20BY%20priority%20DESC%2C%20summary%20ASC%2C%20updatedDate%20DESC)


## Installation

    cordova plugin add cordova-plugin-lenddo

or if you are using ionic

    ionic cordova plugin add cordova-plugin-lenddo

if you have cloned the cordova-plugin-lenddo project locally you can also do

    ionic cordova plugin add /home/<user>/workspace/cordova-plugin-lenddo/

assuming you cloned the repository under workspace/cordova-plugin-lenddo under your home directory

## Introduction 

The Lenddo SDK (lenddosdk module) allows you to collect information in order for Lenddo to verify the user's information and enhance its scoring capabilities. The Lenddo SDK connects to user's social networks and also collects information and mobile data in the background and can be activated as soon as the user has downloaded the app, granted permissions and logged into the app.

## Pre-requisites

Before incorporating the Data SDK into your app, you should be provided with the following information:

*   Partner Script ID
*   Lenddo Score Service API Secret

Please ask for the information above from your Lenddo representative. If you have a dashboard account, these values
can also be found there.

There may be also other partner specific values that you are required to set.

## Android Platform Notes

### Data Collection Mechanism and Required Permissions

The LenddoSDK captures the following data stored on the phone consistent with the permissions defined (see section on adding permissions):

*   Contacts
*   SMS (Performed Periodically)
*   Call History (Performed Periodically)
*   User's Location (Performed Periodically)
*   User's Browsing history (Performed Periodically)
*   User’s Installed Apps
*   Calendar Events
*   Phone Number, Brand and Model

LenddoSDK will use information stored on the users' phone. It is advisable for all permissions to be added to your app to enable LenddoData to extract the necessary information for verification and scoring. The optimal permissions are already defined for you in the Libraries’ AndroidManifest.xml and are automatically added to your app using gradle when you rebuild the app after adding our SDK.

Aside from cordova requirements for building for the Android platform. Here are additional notes.

Make sure you have Android Studio properly setup and installed, please refer to the Google Developer site for the instructions [Android Studio Download and Installation Instructions.](https://developer.android.com/sdk/index.html) Currently the supported Android Studion IDE is up to version 3.x.x.

Below is the list of required permissions.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.READ_CALENDAR" />
<uses-permission android:name="android.permission.READ_CALL_LOG" />
<uses-permission android:name="com.android.browser.permission.READ_HISTORY_BOOKMARKS" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

If you do not want the all default permissions added, you manually have to remove permissions by editing the **platforms/android/cordova-plugin-lenddo/starter-lenddosdk/src/main/AndroidManifest.xml** and comment out permissions you do not wish to grant, however please note that the following permissions at the minimum are required for the operation of the SDK and should NOT be removed:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

It is also important that these permissions are consistent with the privacy policy of your app.

### Setting up Partner Script ID and API Secret
As said above to be able to use the cordova-plugin-lenddo you are required to have a Partner Script Id and an API Secret. Before initiliazing Lenddo class, we need to add them as part of manifest file application's meta-tag.
You can use ***cordova-custom-config*** to modify some native platform configuration files like manifest file or you can copy a code snippet in [config.xml](https://github.com/Lenddo/cordova-data-sdk-sample-app/blob/master/config.xml) of the sample app, as shown below.

```xml

<widget id="io.ionic.starter" xmlns:android="http://schemas.android.com/apk/res/android" ... >
  ...
  <platform name="android">
      ...

      <custom-config-file parent="./application" target="AndroidManifest.xml">
        <meta-data android:name="partnerScriptId" android:value="PARTNER_SCRIPT_ID" />
        <meta-data android:name="partnerApiSecret" android:value="API_SECRET" />
      </custom-config-file>
  </platform>
  ...

  <plugin name="cordova-custom-config" spec="^5.0.2" />
</widget>
```

### Javascript Example

For vanilla cordova apps you can do:

```javascript
var initLenddo = function() {
  Lenddo.initialize();
};

 document.addEventListener('deviceready', initLenddo, false);
```

### Typescript Example (Angular/Ionic)

You can look at the [sample app](https://github.com/Lenddo/cordova-data-sdk-sample-app) for more details.

First make sure you obtain the partnerScriptId and secret, information about this should be provided to you by your Lenddo contact.

For Ionic apps, you can set the partnerScriptId and secret under app.component.ts

```typescript
export class MyApp {
  rootPage:any = HomePage;

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen) {
    platform.ready().then(() => {
      //.....
      Lenddo.initialize();
    });
  }
}
```

#### Data collection
The plugin makes use of Promises to handle callbacks and error handling, here is a sample based on the sample app:

```typescript
    constructor() {
        this.service = Lenddo.getInstance();
    }

    startData() {
        let options = new ClientOptions;

        // Setup any options you would like, and will be discuss further in this guide
        options.setWifiOnly(true);

        this.service.setupData(options).then(()=> {
            // Make sure to call setupData() before calling startData()
            return service.startData(self.applicationId);
        }).catch((message) => {
            console.log("error: " + message.message);
        });
    }
```

Registering callbacks for data collection

```typescript
export class ScoringTabComponent implements DataSendingCallback {

    startData() {
         var self = this;

         // Register global error handlers for data sending
         self.service.setDataSendingCompleteCallback(self);

         // setup and start data collection
    }

    onDataSendingSuccess(result) {
        console.log("success: " + result);
    }

    onDataSendingError(error) {
        console.log("error: " + error);
    }
}

```
***Make sure to call setupData() and setDataSendingCompleteCallback() before calling startData()***

##### Options for Data collection

Various options can be set to control how the Data SDK sends data with [ClientOptions](https://github.com/Lenddo/cordova-data-sdk-sample-app/blob/master/src/components/scoring-tab/scoring-tab.ts#L150) class:

```typescript
  private setupOptions(): ClientOptions {
    let options = new ClientOptions;

    if (this.dataMode === 'wifi_and_mobile') {
      options.setWifiOnly(true);
    } else {
      options.setWifiOnly(false);
    }

    if (!this.enableCallLogs) {
      options.setDisableCallLogData(true);
    }

    if (!this.enableContacts) {
      options.setDisableContactData(true);
    }

    if (!this.enableCalendarEvents) {
      options.setDisableCalendarEventData(true);
    }

    if (!this.enableInstalledApps) {
      options.setDisableInstalledAppData(true);
    }

    if (!this.enableBrowserHistory) {
      options.setDisableBrowserHistoryData(true);
    }

    if (!this.enableLocationData) {
      options.setDisableLocationData(true);
    }

    if (!this.enableBatterycharge) {
      options.setDisableBatteryChargeData(true);
    }

    if (!this.enableGalleryMetaData) {
      options.setDisableGalleryMetaData(true);
    }

    if (!this.enableSMS) {
      options.setDisableSmsData(true);
    }

    if (!this.enableSMSbody) {
      options.setDisableSmsBody(true);
    }

    if (this.enablePhoneNumberHashing) {
      options.setEnablePhoneNumberHashing(true);
    }

    if (this.enableContactsNameHashing) {
      options.setEnableContactsNameHashing(true);
    }

    if (this.enableContactsEmailHashing) {
      options.setEnableContactsEmailHashing(true);
    }

    if (this.enableCalendarOrganizerHashing) {
      options.setEnableCalendarOrganizerHashing(true);
    }

    if (this.enableCalendarDisplayNameHashing) {
      options.setCalendarDisplayNameHashing(true);
    }

    if (this.enableCalendarEmailHashing) {
      options.setCalendarEmailHashing(true);
    }

    options.setEnableLogDisplay(this.enableLog);
    options.setApiGatewayUrl(this.apiGatewayUrl);
    return options;
  }
```

#### Connecting social network

When you are ready to start onboarding, you can then do something like

```typescript
    constructor() {
        this.service = Lenddo.getInstance();
    }

    startOnboarding() {
        var self = this;
        // Attach applicant information using VerificationData class
        var verification_data = new VerificationData;

        // FormDataCollector is a data model class use for attaching applicant information via VerificationData
        var formDataCollector = new FormDataCollector;
        // Make sure to assign an application id whenever doing onboarding
        formDataCollector.applicationId = this.applicationId;
        formDataCollector.verification_data = verification_data;

        var helper = new OnboardingHelper;
        helper.formDataCollector = formDataCollector;

        self.service.startOnboarding(helper);
    }
```

**FormDataCollector** is a data model class use for attaching applicant information, you can set the following data as follows

```typescript
    startOnboarding() {
        // Attach Applicant information using VerificationData class
        var verification_data = new VerificationData;

        // Name Details
        verification_data.name = new VerificationDataName;
        verification_data.name.first = self.firstName;
        verification_data.name.middle = self.middleName;
        verification_data.name.last = self.lastName;

        // Phone Details
        verification_data.phone = new VerificationDataPhone;
        verification_data.phone.home = self.homePhone;
        verification_data.phone.mobile = self.mobilePhone;

        // Birthday should be 'dd/MM/yyyy'
        verification_data.date_of_birth = new Date(self.dateOfBirth).toLocaleDateString('en-US');

        // Email
        verification_data.email = self.email;

        // University
        verification_data.university = self.university;

        // Current/ Last Employer
        verification_data.employer = self.employer;
        // Current/ Last Employment Date should be 'YYYY-MM-DDTHH:mm:ss.sssZ' or ISO 8601 date format
        verification_data.employment_period = new VerificationEmploymentPeriod;
        verification_data.employment_period.start_date = new Date(self.employmentStartDate).toISOString();
        verification_data.employment_period.end_date = new Date(self.employmentEndDate).toISOString();

        // Address
        verification_data.address = new VerificationAddress;
        verification_data.address.line_1 = self.line1;
        verification_data.address.line_2 = self.line2;
        verification_data.address.city = self.city;
        verification_data.address.administrative_division = self.administrativeDivision;
        verification_data.address.country_code = self.countryCode;
        verification_data.address.postal_code = self.postalCode;
        verification_data.address.latitude = self.latitude;
        verification_data.address.longitude = self.longitude;

        // Identification
        verification_data.government_ids = new Array<GovernmentId>();
        var governmentIdPassport = new GovernmentId();
        governmentIdPassport.type = "Passport";
        governmentIdPassport.value = "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";

        // After setting up applicant information assign it into FormDataCollector
    }
```
***Make sure to assign an application id whenever doing onboarding***

##### Handling Lenddo Onboarding Callback into your MainActivity

A callback will be sent to Activity that will run the web-view, this is an Activity life cycle related method. For the Onboarding to work properly this callback must be properly declare. You need to add the following into your MainActivity, assuming the you run the loadUrl from your MainActivity. You need to override **onBackPressed** and **onActivityResult** as shown below.

```java

import org.apache.cordova.plugin.Lenddo;

public class MainActivity extends CordovaActivity
{
    // Other method declaration

    @Override
    public void onBackPressed() {
        if (Lenddo.uiHelper != null && Lenddo.uiHelper.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Lenddo.uiHelper != null) {
            Lenddo.uiHelper.onActivityResult(requestCode, resultCode, data);
        }
    }
}


```

##### Customizing cancel dialog text

When onboarding procedure started, a default cancel dialog will be displayed when onBackPressed is call (as shown above). You can customize the text to be displayed in this dialog message.

```typescript
    constructor() {
        this.service = Lenddo.getInstance();
    }

    startOnboarding() {
        var self = this;

        // ... FormDataCollector declaration

        // Declare cancel dialog text with title, message, okButton, cancelButton
        var cancelDialogText = new CancelDialogText();
        cancelDialogText.title = "Ayaw mo na?";
        cancelDialogText.message = "Cgurado ka na bang aalis ka?";
        cancelDialogText.okButton = "Oo";
        cancelDialogText.cancelButton = "Hindi";

        var helper = new OnboardingHelper;
        helper.formDataCollector = formDataCollector;
        helper.cancelDialogText = cancelDialogText;

        self.service.startOnboarding(helper);
    }
```

##### Common error with gradle 3.x.x

When using gradle 3.x.x you might encounter this error during build.

```
* What went wrong:
Could not resolve all files for configuration ':cordova-plugin-lenddo:lenddosdk:debugCompileClasspath'.
> Could not find runtime.jar (android.arch.lifecycle:runtime:1.0.0).
  Searched in the following locations:
      https://jcenter.bintray.com/android/arch/lifecycle/runtime/1.0.0/runtime-1.0.0.jar

```

To fix this error, on your ***platform/android/build.gradle*** file, you need to add **google()** as repositories and place it above other repositories, as show below.

```gradle
    buildscript {
        repositories {
            google() // place google() above other repository
            jcenter()
            maven {
                url "https://maven.google.com"
            }
        }
        dependencies {
            // NOTE: Do not place your application dependencies here; they belong
            // in the individual module build.gradle files
            classpath 'com.android.tools.build:gradle:3.0.1'
        }
    }

    allprojects {
        repositories {
            google() // place google() above other repository
            jcenter()
            maven {
                url "https://maven.google.com"
            }
        }
    }

```



## IOS Platform Notes

The DataSDK is not available for IOS as such only stub functions are provided.

