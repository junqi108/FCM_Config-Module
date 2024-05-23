# Fruit Crop Model - Config Module

## Table of Contents

- [Fruit Crop Model - Config Module](#fruit-crop-model---config-module)
  - [Table of Contents](#table-of-contents)
  - [Installation](#installation)
    - [Exporting JAR](#exporting-jar)
  - [Usage](#usage)
    - [Initialise CONFIG singleton](#initialise-config-singleton)
    - [Reading files](#reading-files)
      - [Explanation](#explanation)
    - [Retrieving Data](#retrieving-data)
      - [Overview](#overview)
      - [Categories](#categories)
      - [Tables](#tables)

## Installation

### Exporting JAR

Run the following in the root directory of the repository to export the Maven project as a JAR file.

```sh
bash build-jar.sh
```

Navigate to the created `target` directory. You should find the generated JAR `fspm-config-0.0.1-SNAPSHOT.jar` (and its shade for Uber JAR development).

## Usage

### Initialise CONFIG singleton

Declare and instantiate a CONFIG global variable to be accessed throughout the system.

```java
public static final Config CONFIG = Config.getInstance();
```

### Reading files

Suppose we want to read the following JSON file (truncated for brevity) located at the path `./inputs/parameters/model.input.data.name.json`:

```json
{
  "name": "model.input.data.default",
  "class": "model.input.data",
  "metaclass": "document-category-name",
  "category": {
    "module_configuration": {
      "special_scenario": null,
      "species": "apple",
      ...
    },
    "model_functionality": {
      "calcLightInterception": true,
      "calcPotential_PT": true,
      ...
    },
    ...
  }
}
```

We can write the following to add this JSON file to CONFIG.

```java
// Read in JSON file and add as new group
try {
    CONFIG.addGroup("model.input.data.name", new JsonFileReader(
            "./inputs/parameters/model.input.data.name.json"));
} catch (FileNotFoundException e) {
    e.printStackTrace();
}
```

#### Explanation

Given this file is located at `./inputs/parameters/model.input.data.name.json`, we first:

Use a `JsonFileReader` to parse the document by providing the file path.

```java
new JsonFileReader("./inputs/parameters/model.input.data.name.json")
```

Add the document to the Config Module by:

1. Calling the `addGroup(String key, ConfigAdapter adapter)` method from the `CONFIG` singleton.
2. Providing the `JsonFileReader` as the adapter.

```java
CONFIG.addGroup(
      "model.input.data.name",
      new JsonFileReader("./inputs/parameters/model.input.data.name.json")
);
```

Surround the call with a try-catch statement to safeguard any FileNotFoundExceptions.

```java
try {
    CONFIG.addGroup("model.input.data.name", new JsonFileReader(
            "./inputs/parameters/model.input.data.name.json"));
} catch (FileNotFoundException e) {
    e.printStackTrace();
}
```

### Retrieving Data

#### Overview

Each document type is made up of different structures, such as categories, tables (or both).

For example, the `document-category-name` type consists of

#### Categories

```java

```

#### Tables
