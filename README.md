# FindMyPharmacy

## Overview

A simple app that simulates the ability for a user to view a set of pharmacies, view various details
of each pharmacy, and order medications from those pharmacies.

This app is built using a simple MVP architecture rather than MVVM, as well as EventBus instead of
Kotlin Coroutines, to save time on building up the required infrastructure from scratch. In general,
I would prefer to use MVVM and Kotlin Coroutines to help take advantage of the built-in lifecycle
components and to make for a faster, and more resilient app experience.

It does makes use of Jetpack's Navigation library, though, and the Dagger Hilt library.

Although I am generally a fan of self-commenting code, I did try to be a bit more liberal with my
comments, particularly with the more notable fields/methods/classes.

What is missing:

- Unit tests for 1 of the 3 presenter classes, OrderPresenter, are incomplete because a refactor is
  needed to pull out the static use of Android's Location class.
- Finer separation at the data layer between repositories and remote/local data sources.
- Some pharmacies have a second address line JSON field that does not get returned for all pharmacy
  resources, and it would have been nice to use that for complete rendering of pharmacy addresses.

Other areas of improvements:

- Logging of state info to something such as Crashlytics when an exception is caught to help bug
  fixing efforts and possible reproduction.
- When a network error happens, it would be nice to provide a way for the user to retry the last
  action, such as via a clickable option in a Snackbar.
- Pulling the default set of pharmacies from a local JSON file rather than hard-coding the IDs of
  the handful of needed pharmacies.
- Saving of orders in persistent storage instead of in-memory.
- There are opportunities for concurrent network calls for independent sets of objects that could be
  handled by Kotlin Coroutines, although this would require further setup of dependency injection
  logic with coroutine dispatchers.
- Ensuring the repositories and data sources are all main-safe.  (Although no such need exists with
  the current architecture.)
- Adding a way to quickly scroll through the medication list by first letter as the Android Contacts
  app does would be nice, and allowing the user to do a keyword search would be even better.
- If the user was paying for the medications being ordered, having an intermediary verification
  screen that shows just the selected medications would be helpful.
- Adding unit tests for other layers of the app, such as for the repository classes.
- Fix the coloring of the FAB and menu icons.