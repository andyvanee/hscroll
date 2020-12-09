BUILD_VERSION=001
VERSION_PROPS=$(shell find versions -name gradle.properties)
VERSION_NUMBERS=$(patsubst versions/%/gradle.properties,%,$(VERSION_PROPS))

DIST_TARGETS := $(patsubst %,dist/hscroll-%-$(BUILD_VERSION).jar,$(VERSION_NUMBERS))
BUILD_TARGETS := $(patsubst dist/hscroll-%-$(BUILD_VERSION).jar,versions/%/build/libs/hscroll.jar,$(DIST_TARGETS))
SOURCES=$(shell find . -name '*.java')

default: $(DIST_TARGETS)
	@echo Build complete

$(BUILD_TARGETS): build

.PHONY: build
build:
	./gradlew build

dist/hscroll-%-$(BUILD_VERSION).jar: versions/%/build/libs/hscroll.jar
	@mkdir -p $(@D)
	@cp $? $@

.PHONY: clean
clean:
	rm -rf versions/*/build
	rm -rf versions/*/.gradle
