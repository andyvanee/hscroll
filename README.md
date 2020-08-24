# HScroll Fabric Mod

This is some test code to see if I can work around a long-standing bug related to horizontal
scrolling, particularly on Mac which translates `Shift+Scroll` into a horizontal scroll, which
has no effect in Minecraft, but happens whenever you sneak and scroll your inventory slots.

The entire functionality is in
[MinecraftClientMixin.java](src/main/java/net/fabricmc/andyvanee/mixin/MinecraftClientMixin.java)
which basically swaps out the default scroll handler for a custom one which translates
horizontal scrolling into vertical scrolling.

I intend this as a purely temporary fix and proof of concept. One thing that I did not expect is
that scrolling on a touchpad or magic mouse actually scrolls the opposite way than you might
expect. This is likely due to "Natural Scrolling" on Mac and it's debatable whether the
horizontal scroll direction should be the reverse of what it currently is. With a Magic Mouse
the Shift+Scroll works exactly as expected, but a left swipe causes your inventory to scroll
right. Currently it works perfectly for my needs using a Logitech Mouse.

## Install

Using MultiMC - all that's required is to:

-   Download hscroll-1.16.2.001.jar from the [Releases Page](https://github.com/andyvanee/hscroll/releases)
-   Use Minecraft 1.16.2
-   `Edit Instance`
-   `Install Fabric`
-   Add hscroll-1.16.2.001.jar to 'Loader Mods'

## For developers

In order to build the jar file, run the following commands:

```
# Set up build environment
gradle wrapper
# Build the jar file
./gradlew build
```

The build output will be in the `build/libs` directory.

## License

MIT
