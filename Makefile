MAJOR_VERSION=hscroll-1.16
MINOR_VERSIONS := 1 2 3 4
BUILD_VERSION=002
TARGET_VERSIONS=$(patsubst %,$(MAJOR_VERSION).%-$(BUILD_VERSION),$(MINOR_VERSIONS))

SOURCES=$(shell find . -name '*.java')

BUILD_TARGETS := $(patsubst %,build/libs/%.jar,$(TARGET_VERSIONS))
TARGETS := $(patsubst %,dist/%.jar,$(TARGET_VERSIONS))

default: $(TARGETS)

# MC version is the $* variable
build/libs/hscroll-%-$(BUILD_VERSION).jar: $(SOURCES)
	./gradlew build -Pminecraft_version=$* -Pyarn_mappings=$*+build.1 -Pmod_version=$*-$(BUILD_VERSION)

dist/%.jar: build/libs/%.jar
	@mkdir -p $(@D)
	cp $? $@
