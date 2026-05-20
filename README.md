# Gluon Attach Ads Example

This repository provides an example for the use of the gluon attach ads module.

## Building the App

To build the app You will need some prerequisites first:

1. Clone and build the attach repo containing the ads module:

```
git clone https://github.com/pabulaner/attach.git
git checkout feature/ads
./gradlew clean publishToMavenLocal
```

2. Clone and build the gluonfx-maven-plugin repo containing the ads enum constant:

```
git clone https://github.com/pabulaner/gluonfx-maven-plugin.git
mvn clean install
```

If You have done everything correctly, You will now be able to build and package the app:

```
mvn -Pandroid gluonfx:build gluonfx:package
```

## Using the App

The app allows You to play with the different methods used for initializing the ads service and loading and showing ads.

### Initializing the Ads Service

To initialize the ads service simply click on the first two buttons in that order:

1. create
2. initialize

This will create the service and initialize the Google Mobile Ads SDK.
You can look at the log output to see when initialization is complete and there occurred any errors.

### Banner Ad

To load and show a banner ad simply click the following buttons in the "Banner Ad" section in that order:

1. new
2. setAdUnitId
3. setAdLayout
4. setAdSize
5. load
6. show

This will create a new banner ad and set the required values like the ad unit ID.
It then loads and shows the ad.

### Interstitial and Rewarded Ad

To load and show an interstitial or rewarded ad simply click the following buttons in the corresponding section in that order:

1. load
2. show

This will load and show the ad.
You might need to wait some time after the load button has been clicked as the ad needs to be loaded.
You will see a message in the log output that will tell You when the ad is loaded and ready to be shown.