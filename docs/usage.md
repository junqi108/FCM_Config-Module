# Usage Documentation

## Table of Contents

- [Usage Documentation](#usage-documentation)
  - [Table of Contents](#table-of-contents)
  - [Initialise CONFIG Singleton](#initialise-config-singleton)
  - [Reading Files](#reading-files)
    - [Using FileReaders](#using-filereaders)
  - [Retrieving Data](#retrieving-data)
    - [Overview](#overview)
    - [Document Types](#document-types)
    - [Categories](#categories)
      - [Nested Access Calls](#nested-access-calls)
      - [CategoryStores Context \& Flattened Categories](#categorystores-context--flattened-categories)
    - [Tables](#tables)

## Initialise CONFIG Singleton

Declare and instantiate a CONFIG global variable to be accessed throughout the system.

```java
public static final Config CONFIG = Config.getInstance();
```

## Reading Files

Suppose we want to read the following JSON file (truncated for brevity) located at the path `./inputs/parameters/model.input.data.name.json`:

```json
{
  "name": "model.input.data.default",
  "class": "model.input.data",
  "metaclass": "document-category-name",
  "category": {
    "module_configuration": {
      "special_scenario": null,
      "species": "apple"
    },
    "model_functionality": {
      "calcLightInterception": true,
      "calcPotential_PT": true
    }
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

### Using FileReaders

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

## Retrieving Data

### Overview

Each document type is made up of different structures, such as categories, tables (or both).

For example, the `document-category-name` type consists of a collection of categories, while the `document-hybrid-category-name` type consists of both category and table collections.

Below is an example of a `document-hybrid-category-name` type in a file named `hybrid-format.json`:

```json
{
  "name": "hybrid-format",
  "metaclass": "document-hybrid-category-name",
  "category": {
    "category1": {
      "field1": "value1"
    }
  },

  "tables": ["table1", "table2"],

  "table1": {
    "layer_1": {},
    "layer_2": {}
  },
  "table2": {
    "layer_1": {}
  }
}
```

The structure of the Config Module is similar to the structure of these documents.

| Documents  | Config Module | Example (from above) |
| ---------- | ------------- | -------------------- |
| Document   | Group         | `hybrid-format.json` |
| Categories | CategoryStore | `category`           |
| Category   | Category      | `category1`          |
| Name       | Parameter     | `field1`             |

Below is a representation of the document hierarchies for `document-category-name` and `document-hybrid-category-name`.

- Document (document-category-name type)

  - CategoryStore
    - Category
      - Parameter

- Document (document-hybrid-category-name type)
  - CategoryStore
    - Category
      - Parameter
  - TableStore
    - Table
      - Parameter

### Document Types

During retrieval, it is necessary to specify which type of document in order to access their corresponding collections.

```java
// document-category-name type
DocumentCategoryNameGroup document1 = CONFIG.getGroup("document1",
    DocumentCategoryNameGroup.class);

// document-hybrid-category-name type
DocumentHybridCategoryNameGroup document2 = CONFIG.getGroup("document2",
    DocumentHybridCategoryNameGroup.class);
```

### Categories

The collection of categories can be retrieved with the following:

```java
DocumentCategoryNameGroup document1 = CONFIG.getGroup("document1",
    DocumentCategoryNameGroup.class);

CategoryStore store = document2.getCategoryStore();
```

The parameters stored within these categories can then be accessed according to their type.

```java
ParamCategory category1 = store.getCategory("category1");
Integer int1 = category1.getInteger("int1");
```

#### Nested Access Calls

We can simplify retrieving this integer in a number of ways depending on whether it is necessary to declare a local variable reference for a group, store, category, etc.

```java
// Original method
DocumentCategoryNameGroup document1 = CONFIG.getGroup("document1",
    DocumentCategoryNameGroup.class);
CategoryStore store = document2.getCategoryStore();
ParamCategory category1 = store.getCategory("category1");
Integer int1 = category1.getInteger("int1");

// Omit group declaration
CategoryStore document1 = CONFIG.getGroup("document1",
    DocumentCategoryNameGroup.class).getCategoryStore();
ParamCategory category1 = store.getCategory("category1");
Integer int1 = category1.getInteger("int1");

// Omit group and store declarations
ParamCategory category1 = CONFIG.getGroup("document1",
    DocumentCategoryNameGroup.class).getCategoryStore()
    .getCategory("category1");
Integer int1 = category1.getInteger("int1");
```

#### CategoryStores Context & Flattened Categories

We often want to retrieve parameters from different categories in a local scope. This can be done with the following:

```java
CategoryStore store = document.getCategoryStore();

ParamCategory integers = store.getCategory("integers");
Integer int1 = integers.getInteger("int1");

ParamCategory doubles = store.getCategory("doubles");
Double double1 = doubles.getDouble("double1");

ParamCategory strings = store.getCategory("strings");
String string1 = strings.getString("string1");
```

However, it may be cumbersome needing to specify the category each time, especially when we know the parameters in the group **all have unique keys**.

Rather than needing to explicitly specify each category, we can opt to enable _Flattened Categories_. This allows us to access all parameters within this `CategoryStore` without their categories.

```java
CategoryStore store = document1.getCategoryStore();
store.useFlattenedCategories = true;

Integer int1 = integers.getInteger("int1");
Double double1 = doubles.getDouble("double1");
String string1 = strings.getString("string1");

store.useFlattenedCategories = false;
```

Note: **Flattened categories should be used sparingly with caution due to the following:**

- Only works if we are certain the group does not contain duplicate keys, i.e: each parameter has a unique key in the document.
- Remember to set `useFlattenedCategories` back to `false` immediately after the parameters have been accessed.
  - This reenforces the rule that parameters must be accessed through categories.

### Tables

The collection of tables can be retrieved with the following:

```java
DocumentHybridCategoryNameGroup document2 = CONFIG.getGroup("document2",
    DocumentHybridCategoryNameGroup.class);

TableStore store = document2.getTableStore();
```

The parameters can then be accessed by specifying the row and column within the table.

```java
ParamTable table1 = store.getTable("table1");
Integer int1 = table.getInteger("row1", "int1");
```

Similar to categories, we can also simplify this access:

```java
// Omit group and store declarations
ParamTable table1 = CONFIG.getGroup("document2",
    DocumentHybridCategoryNameGroup.class).getTableStore();

Integer int1 = table.getInteger("row1", "int1");
```
