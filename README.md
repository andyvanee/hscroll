# HScroll Fabric Mod

> ** Unmaintained ** - I don't play Minecraft enough to warrant maintaining this mod and building for new releases.
> 
> There are links to some workarounds in [the issue thread here](https://bugs.mojang.com/browse/MC-121772) and the best workaround seems to be [MCMacOSInputFixes](https://github.com/hamarb123/MCMacOSInputFixes).

## Original README

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

-   Download the jar file for your Minecraft version (eg: MC 1.16.4 -> hscroll-1.16.4.00X.jar) from the
    [Releases Page](https://github.com/andyvanee/hscroll/releases)
-   `Edit Instance`
-   `Install Fabric` (tested with fabric loader 0.10.6)
-   Add the downloaded jar file to 'Loader Mods'

## For developers

In order to build the jar file, run the following commands:

```
# Set up build environment
gradle wrapper

# If you want the decompiled sources to browse
./gradlew genSources

# Build the jar file
./gradlew build
```

The build output will be in the `build/libs` directory.

See `gradle.properties` for building a specific version.

## TODO

-   The mod works as intended for vertical scrolling, with and without Shift,
    using all mice tested. It does not, however, behave as well for the Magic
    Mouse or Trackpad which have a horizontal scroll gesture. For these devices,
    a left swipe will result in the inventory scrolling right. I assume track
    balls would behave the same, but I don't have one to test. This situation is
    unavoidable as far as I can tell, since it's not feasible to distinguish
    between a Left/Right scroll via trackpad from an Up/Down+Shift scroll.

## License

MIT
