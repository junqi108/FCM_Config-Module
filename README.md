# Fruit Crop Model - Config Module

The Configuration (Config) Module is a library for encapsulating the parsing and access logic for parameter files used in the DHS Platform.

## Table of Contents

- [Fruit Crop Model - Config Module](#fruit-crop-model---config-module)
  - [Table of Contents](#table-of-contents)
  - [Other Pages](#other-pages)
  - [Glossary](#glossary)
  - [Features](#features)
  - [Installation](#installation)
    - [Exporting JAR](#exporting-jar)
    - [Deploying to Maven Central Repository](#deploying-to-maven-central-repository)
  - [Usage](#usage)

## Other Pages

- [Auto-generated Documentation](https://aomedium.github.io/FCM_Config-Module/)
- [Usage in code](docs/usage.md)

## Glossary

## Features

-

Please refer to the [auto-generated documentation](https://aomedium.github.io/FCM_Config-Module/) for more details.

## Installation

### Exporting JAR

Run the following in the root directory of the repository to export the Maven project as a JAR file.

```sh
bash build-jar.sh
```

Navigate to the created `target` directory. You should find the generated JAR `fruitcropxl-config-0.0.1-SNAPSHOT.jar`.

### Deploying to Maven Central Repository

Ensure you have write access to the `io.github.fruitcropxl` Maven Central Repository namespace by configuring your `settings.xml` (see [Publishing By Using the Maven Plugin](https://central.sonatype.org/publish/publish-portal-maven/)).

You may be prompted for a GPG key to sign the files. Please see how to [configure and distribute a public GPG key](https://central.sonatype.org/publish/requirements/gpg/).

Run the following:

```sh
mvn clean deploy
```

Navigate to [Publishing Settings](https://central.sonatype.com/publishing/deployments) on the Maven Central Repository web interface. The deployment should be visible as either Pending or Validating.

Publish the deployment once its status has been changed to Validated.

## Usage

Please refer to the [usage documentation](docs/usage.md) for more details.
