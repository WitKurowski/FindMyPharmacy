# FindMyPharmacy

## Overview
A simple app that simulates the ability for a user to view a set of pharmacies, view various details
of each pharmacy, and order medications from those pharmacies.

This app is built using a simple MVP architecture rather than MVVM, as well as EventBus instead of
Kotlin Coroutines, to save time on building up the required infrastructure from scratch.  In 
general, I would prefer to use MVVM and Kotlin Coroutines to help take advantage of the built-in
lifecycle components and to make for a faster, and more resilient app experience.

It does makes use of Jetpack's Navigation library, though, and the Dagger Hilt library.

What is missing:
- Unit tests for 1 of the 3 presenter classes, OrderPresenter, are incomplete because a refactor is
  needed to pull out the static use of Android's Location class.
- Finer separation at the data layer between repositories and remote/local data sources.

Other areas of improvements:
- Pulling the default set of pharmacies from a local JSON file rather than hard-coding the IDs of
  the handful of needed pharmacies.
- Saving of orders in persistent storage instead of in-memory
- There are opportunities for concurrent network calls for independent sets of objects that could be
  handled by Kotlin Coroutines, although this would require further setup of dependency injection
  logic with coroutine dispatchers
- The colors of the FAB and menu icons are not optimized for the rest of the color scheme.