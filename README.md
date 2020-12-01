# Humla

Humla is a fork of [Jumble](https://github.com/acomminos/Jumble), an Android
service implementation of the Mumble protocol which was originally written by
Andrew Comminos as the backend of [Plumble](https://github.com/acomminos/Plumble).
Humla is the backend of [Mumla](https://gitlab.com/quite/mumla).

## About Jumble

The primary goal of the Jumble project is to encourage developers to embrace
the Mumble protocol on Android through a copylefted libre, complete, and stable
implementation. At the moment, development is focused on improving stability
and security.

Prior to the release of Jumble, all implementations of the Mumble protocol on
Android have been using the same non-free code developed by @pcgod. To ensure
the unencumbered use of Jumble, no sources or derivatives will be copied from
that project.

## Including in your project

Humla is a standard Android library project using the gradle build system.
[See here for instructions on how to include it into your Gradle project](http://tools.android.com/tech-docs/new-build-system/user-guide#TOC-Referencing-a-Library).

Currently, there is no tutorial to integrate Humla with your project. In the
mean time, please examine the exposed interface IHumlaService as well as
Mumla's implementation.

## License

Humla is now licensed under the GNU GPL v3+. See [LICENSE](LICENSE).
