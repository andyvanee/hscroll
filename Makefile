MAJOR_VERSION=hscroll-1.16
MINOR_VERSIONS := 1 2 3 4
BUILD_VERSION=002
TARGET_VERSIONS=$(patsubst %,$(MAJOR_VERSION).%-$(BUILD_VERSION),$(MINOR_VERSIONS))

SOURCES=$(shell find . -name '*.java')

BUILD_TARGETS := $(patsubst %,build/libs/%.jar,$(TARGET_VERSIONS))
TARGETS := $(patsubst %,dist/%.jar,$(TARGET_VERSIONS))

default: $(TARGETS)

build/libs/hscroll-1.16.1-$(BUILD_VERSION).jar: $(SOURCES)
	./gradlew build -Pminecraft_version=1.16.1 -Pyarn_mappings=1.16.1+build.21 -Ploader_version=0.10.8 -Pfabric_version=0.18.0+build.387-1.16.1 -Pmod_version=1.16.1-$(BUILD_VERSION)
build/libs/hscroll-1.16.2-$(BUILD_VERSION).jar: $(SOURCES)
	./gradlew build -Pminecraft_version=1.16.2 -Pyarn_mappings=1.16.2+build.47 -Ploader_version=0.10.8 -Pfabric_version=0.27.1+1.16 -Pmod_version=1.16.2-$(BUILD_VERSION)
build/libs/hscroll-1.16.3-$(BUILD_VERSION).jar: $(SOURCES)
	./gradlew build -Pminecraft_version=1.16.3 -Pyarn_mappings=1.16.3+build.47 -Ploader_version=0.10.8 -Pfabric_version=0.27.1+1.16 -Pmod_version=1.16.3-$(BUILD_VERSION)
build/libs/hscroll-1.16.4-$(BUILD_VERSION).jar: $(SOURCES)
	./gradlew build -Pminecraft_version=1.16.4 -Pyarn_mappings=1.16.4+build.7 -Ploader_version=0.10.8 -Pfabric_version=0.27.1+1.16 -Pmod_version=1.16.4-$(BUILD_VERSION)

dist/%.jar: build/libs/%.jar
	@mkdir -p $(@D)
	cp $? $@

clean:
	rm -rf build dist
