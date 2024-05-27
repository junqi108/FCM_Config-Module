# Usage Documentation

## Initialise CONFIG singleton

Declare and instantiate a CONFIG global variable to be accessed throughout the system.

```java
public static final Config CONFIG = Config.getInstance();
```

## Reading files

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

### Explanation

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
Integer int1 = category.getInteger("int1");
```

We can simplify this approach by

#### Setting context for categories

We often want to

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

Similarly, we can simplify this access:

```java
ParamTable table1 = CONFIG.getGroup("document2",
    DocumentHybridCategoryNameGroup.class).getTableStore();

Integer int1 = table.getInteger("row1", "int1");
```
