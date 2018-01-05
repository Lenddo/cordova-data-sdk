---
title: Lenddo Cordova Plugin
description: Cordova bindings for the Lenddo Data SDK
---

# cordova-plugin-lenddo

This plugin defines a global `Lenddo` object, which allows for using features of the Lenddo SDK.
Although the object is in the global scope, it is not available until after the `deviceready` event.

```js
document.addEventListener("deviceready", onDeviceReady, false);
function onDeviceReady() {
    var partnerId = "<your partner ID>"
    var secret = "<your application secret>"
    var options = {}
    Lenddo.setup(partnerId, secret, options)
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

The Lenddo Data SDK (LenddoDataSDK) Cordova plugin allows you to collect information in order for Lenddo to verify the user's information and enhance its scoring capabilities. The LenddoDataSDK collects information in the background and can be activated as soon as the user has downloaded the app, given permissions and logged into the app.

## Pre-requisites 

Before incorporating the Data SDK into your app, you should be provided with the following information:

*   Partner Script ID
*   Lenddo Score Service API Secret

Please ask for the information above from your Lenddo representative. If you have a dashboard account, these values
can also be found there.

There may be also other partner specific values that you are required to set.

## Data Collection Mechanism and Required Permissions

The LenddoDataSDK captures the following data stored on the phone consistent with the permissions defined (see section on adding permissions):

*   Contacts
*   SMS (Performed Periodically)
*   Call History (Performed Periodically)
*   User's Location (Performed Periodically)
*   User's Browsing history (Performed Periodically)
*   User’s Installed Apps
*   Calendar Events
*   Phone Number, Brand and Model

LenddoDataSDK will use information stored on the users' phone. It is advisable for all permissions to be added to your app to enable LenddoData to extract the necessary information for verification and scoring. The optimal permissions are already defined for you in the Libraries’ AndroidManifest.xml and are automatically added to your app using gradle when you rebuild the app after adding our SDK.

## Android Platform Notes
Aside from cordova requirements for building for the Android platform. Here are additional notes.

Make sure you have Android Studio properly setup and installed, please refer to the Google Developer site for the instructions [Android Studio Download and Installation Instructions.](https://developer.android.com/sdk/index.html) Currently the supported IDE is up to version 2.3.3. The SDK is not compatible with the Beta release of Android Studio. Also, use API 25 Build tools. API 26 build tools are not yet compatible as of this moment.

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

If you do not want the all default permissions added, you manually have to remove permissions by editing the **platforms/android/cordova-plugin-lenddo/starter-lenddodatasdk/src/main/AndroidManifest.xml** and comment out permissions you do not wish to grant, however please note that the following permissions at the minimum are required for the operation of the SDK and should NOT be removed:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

It is also important that these permissions are consistent with the privacy policy of your app.


## IOS Platform Notes

The DataSDK is not available for IOS as such only stub functions are provided.

## Javascript Example

For vanilla cordova apps you can do:

```javascript
var setLenddo = function() {
  Lenddo.setInstance('PARTNER_SCRIPT_ID', 'SECRET');
};

 document.addEventListener('deviceready', setLenddo, false);
```

## Typescript Example (Angular/Ionic)

First make sure you obtain the partnerScriptId and secret, information about this should be provided to you by your Lenddo contact.

For Ionic apps, you can set the partnerScriptId and secret under app.component.ts

```typescript
export class MyApp {
  rootPage:any = HomePage;

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen) {
    platform.ready().then(() => {
      //.....
      Lenddo.setInstance('PARTNER_SCRIPT_ID', 'SECRET');
    });
  }
}
```

When you are ready to start data collection, you can then do something like

```typescript
  constructor() {
    this.service = Lenddo.getInstance();
  }

  startLenddo() {
      let options = new ClientOptions;
      //Setup any options you would like
      this.service.setup(options);
      this.service.start('USER_OR_APPLICATION_ID').then((status)=> { console.log("send data success)});
  }
```

The plugin makes use of Promises to handle callbacks and error handling, here is a sample based on the sample app:

```typescript
     let options = new ClientOptions;

     //sample options
     options.setWifiOnly(true);

     let service = Lenddo.getInstance(); //must have already been initialized with setInstance prior
     service.setup(options).then(()=> {
        return service.start(self.applicationId);
    }).
    then(()=>{ console.log("success") }).
    catch((message) => { 
        console.log("error: " + message.message);
    });
```

For Android M, the setup call can cause the permissions request dialog to popup if not all app permissions required by Lenddo have been granted, so it is best to plan when the setup method is called.

## Options

Various options can be set to control how the Data SDK sends data:

