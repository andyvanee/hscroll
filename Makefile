MAJOR_VERSION=hscroll-1.18
MINOR_VERSIONS := 1 2
BUILD_VERSION=001
TARGET_VERSIONS=$(patsubst %,$(MAJOR_VERSION).%-$(BUILD_VERSION),$(MINOR_VERSIONS))

SOURCES=$(shell find . -name '*.java')

BUILD_TARGETS := $(patsubst %,build/libs/%.jar,$(TARGET_VERSIONS))
TARGETS := $(patsubst %,dist/%.jar,$(TARGET_VERSIONS))

default: $(TARGETS)

build/libs/hscroll-1.18.2-$(BUILD_VERSION).jar: $(SOURCES)
	./gradlew build -Pminecraft_version=1.18.2 -Pyarn_mappings=1.18.2+build.2 -Ploader_version=0.13.3 -Pfabric_version=0.28.5+1.15 -Pmod_version=1.18.2-$(BUILD_VERSION)
build/libs/hscroll-1.18.1-$(BUILD_VERSION).jar: $(SOURCES)
	./gradlew build -Pminecraft_version=1.18.1 -Pyarn_mappings=1.18.1+build.22 -Ploader_version=0.13.3 -Pfabric_version=0.28.5+1.15 -Pmod_version=1.18.1-$(BUILD_VERSION)

dist/%.jar: build/libs/%.jar
	@mkdir -p $(@D)
	cp $? $@

clean:
	rm -rf build dist
