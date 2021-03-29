# Munroes Project

This library contains an implementation of the Munroes project,
part of the interview process for xDesign.

## API

### Endpoints

#### URI:

`GET /munroes`

#### Query Parameters

| Name      | Type             | Description                                  | Default Value | Notes |
|-----------|------------------|----------------------------------------------|---------------|-------|
| limit     | Positive Integer | The maximum number of results to return      | Unlimited     | |
| minHeight | Positive Number  | The minimum height of munroes to return      | No min        | If maxHeight given, this must be less |
| maxHeight | Positive Number  | The maximum height of munroes to return      | No max        | If minHeight given, this must be greater |
| type      | `MUN` or `TOP`   | The category of munroes to return            | Any           | Multiple can be specified, however given there are two options, specifying both is the same as specifying none |
| sort      | String           | The property and sort-order to be sorted on  | No sorting    | See below for more details. |

#### Sorting

Sorting is supported via the `sort` query parameter.  This parameter should be in the form:
`{property}[-{order}]`

Where `{property}` is one of `name`, `height`, `category` or `gridReference`,
and (if given) `order` is one of `ASC` or `DES`.  If `order` is not specified for a property then `ASC` is used.

Multiple `sort` parameters can be provided to detail what to do in the case of matches (for example, in height) for 
earlier `sort` specifications.  These are handled in priority of the order the are provided in the query.

Examples:

* `?sort=name`
* `?sort=name-DES`
* `?sort=height-DES&sort=name`

#### Response

A JSON array detailing the munroes which matched the specified query parameters.

Example:

```json
[
  {
    "name": "Beinn Teallach",
    "height": 914.6,
    "category": "Munro",
    "gridReference": "NN361859"
  },
  {
    "name": "Meall Ghaordaidh",
    "height": 1039.8,
    "category": "Munro",
    "gridReference": "NN514397"
  }
]
```

## Authors

* Liam Bryan