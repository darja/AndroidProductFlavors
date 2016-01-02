# BuildTypesDemo

Demonstration of configuring product flavors in Android app.

App is assumed to be distributed in two versions (free, premium) on three stores (Google, Amazon and Samsung). So, build script is configured to produce 12 apks.

* Proper project structure
* Specific libraries for Amazon and Samsung are configured to be added only to versions they are used in
* Flavors combination
* Own application id for each combination

Also there is In-App billing implemented for all stores, but this is just a quick-and-dirty implementation, not a comprehensive example.
