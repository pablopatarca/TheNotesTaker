# TheNotesTaker

Notes app sample project



# Architecture

The architecture is built around
[Android Architecture Components](https://developer.android.com/topic/libraries/architecture/)
and follows the recommendations laid out in the
[Guide to App Architecture](https://developer.android.com/jetpack/docs/guide). Logic is kept away
from Activities and Fragments and moved to
[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)s.
Data is observed using
[Kotlin Flows](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
and the [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/)
binds UI components in layouts to the app's data sources.

The Repository layer handles data operations. User preferences and settings are stored in
DataStore, the repository modules are responsible for handling all data operations 
and abstracting the data sources
from the rest of the app.

A lightweight domain layer sits between the data layer
and the presentation layer, and handles discrete pieces of business logic off
the UI thread. See the `.\*UseCase.kt` files under `/domain`.

The [Navigation component](https://developer.android.com/guide/navigation) is used
to implement navigation in the app, handling Composable Screens or Fragment transactions 
and providing a consistent user experience.

[Room](https://developer.android.com/jetpack/androidx/releases/room) is used
for Full Text Search using [Fts4](https://developer.android.com/reference/androidx/room/Fts4)
to search for notes, tags and more.