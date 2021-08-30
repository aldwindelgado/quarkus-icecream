# TODO
- Update this README

# Quarkus Ice Cream
A demo application using the Ice Cream repository and [Quarkus](https://quarkus.io/).

# Running the app
```shell
mvn quarkus:dev
```

You can also omit the `quarkus:dev` as it is the default goal declared on `pom.xml`.

# API Endpoints
## Ingredient
### Retrieve all ingredients
```shell
GET /ingredients
```

<details>
<summary>Sample cURL request and response</summary>

**Request**
```shell
curl --location --request GET 'localhost:6700/ingredients'
```

**Response 200 OK**
```http
HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: application/json

[
    {
        "name": "almonds",
        "products": [
            "Coffee Toffee Bar Crunch","Everything But The...\®",
            "Salted Caramel Almond","Urban Bourbon\™",
            "Vanilla Toffee Bar Crunch"
        ]
    },
    {
        "name": "annatto( color)",
        "products": [
            "Pumpkin Cheesecake"
        ]
    },
    {
        "name": "baking powder (sodium acid pyrophosphate,sodium bicarbonate,corn starch,monocalcium phosphate)",
        "products": [
            "Salted Caramel Core"
        ]
    },
    {
        "name": "baking powder (sodium acid pyrophosphate,sodium bicarbonate,corn starch,monocalcium phosphate,calcium sulfate)",
        "products": [
            "Red Velvet Cake"
        ]
    },
    {
        "name": "baking soda",
        "products": ["Chillin' the Roast\™","Chocolate Shake It\™",
            "One Sweet World",
            "Bourbon Pecan Pie",
            "Brownie Batter Core",
            "Coconuts for Caramel Core",
            "Cookies & Cream Cheesecake Core","Keep Caramel & Cookie On\™","Oat of This Swirled\™",
            "Salted Caramel Almond",
            "Salted Caramel Core","Urban Bourbon\™"
        ]
    },
    {
        "name": "bananas",
        "products": [
            "Banana Split","Chunky Monkey\®"
        ]
    },
    {
        "name": "barley malt",
        "products": ["Chocolate Shake It\™","Keep Caramel & Cookie On\™"
        ]
    },
   // etc, omitted for brevity
```

</details>

### Retrieve ingredient by name (case insensitive)
```shell
GET /ingredients/{name}
```

<details>
<summary>Sample cURL request and response</summary>

**Request**
```shell
curl --location --request GET 'localhost:6700/ingredients/cream'
```

**Response 200 OK**
```http
HTTP/1.1 200 OK
Content-Length: 1243
Content-Type: application/json

{
    "name": "cream",
    "products": [
        "Caramel Chocolate Cheesecake","Chillin' the Roast\™","Chocolate Shake It\™",
        "One Sweet World",
        "Banana Split","Blondie Ambition\™","Boom Chocolatta\™ Cookie Core",
        "Bourbon Pecan Pie",
        "Brownie Batter Core",
        "Cheesecake Brownie","Cherry Garcia\®",
        "Chocolate Chip Cookie Dough",
        "Chocolate Fudge Brownie","Chocolate Therapy\®","Chubby Hubby\®","Chunky Monkey\®","Cinnamon Buns\®",
        "Coconuts for Caramel Core",
        "Coffee Toffee Bar Crunch","Coffee, Coffee BuzzBuzzBuzz!\®",
        "Cookies & Cream Cheesecake Core","Everything But The...\®","Half Baked\®","Karamel Sutra\® Core","Keep Caramel & Cookie On\™",
        "Milk & Cookies",
        "Mint Chocolate Cookie","New York Super Fudge Chunk\®","Oat of This Swirled\™",
        "Peanut Buttah Cookie Core",
        "Peanut Butter Cup",
        "Peanut Butter Fudge Core","Phish Food\®",
        "Pistachio Pistachio",
        "Pumpkin Cheesecake",
        "Red Velvet Cake",
        "S'mores",
        "Salted Caramel Almond",
        "Salted Caramel Core","Spectacular Speculoos\™ Cookie Core",
        "Strawberry Cheesecake","The Tonight Dough\®","Triple Caramel Chunk\®","Truffle Kerfuffle\™","Urban Bourbon\™",
        "Vanilla",
        "Vanilla Caramel Fudge",
        "Vanilla Toffee Bar Crunch"
    ]
}
```

</details>

### Create a new ingredient
```shell
POST /ingredients
```

<details>
<summary>Sample cURL request and response</summary>

**Request**
```shell
curl --location --request POST 'localhost:6700/ingredients' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "ingredient 10"
}'
```

**Response 201 CREATED**
```http
HTTP/1.1 201 Created
Content-Length: 0
Location: http://localhost:6700/ingredients/ingredient%2010
```
</details>





## Sourcing Value
### Retrieve all sourcing values
```shell
GET /souring-values
```

<details>
<summary>Sample cURL request and response</summary>

**Request**
```shell
curl --location --request GET 'localhost:6700/sourcing-values'
```

**Response 200 OK**
```http
HTTP/1.1 200 OK
Content-Length: 6590
Content-Type: application/json

[
    {
        "name": "Cage-Free Eggs",
        "products": [
            "Caramel Chocolate Cheesecake","Chillin' the Roast\™","Chocolate Shake It\™",
            "One Sweet World",
            "Banana Split","Blondie Ambition\™","Boom Chocolatta\™ Cookie Core",
            "Bourbon Pecan Pie","Brewed to Matter\™",
            "Brownie Batter Core",
            "Cheesecake Brownie","Cherry Garcia\®",
            "Chocolate Chip Cookie Dough",
            "Chocolate Fudge Brownie","Chocolate Therapy\®","Chubby Hubby\®","Chunky Monkey\®","Cinnamon Buns\®",
            "Coconuts for Caramel Core",
            "Coffee Toffee Bar Crunch","Coffee, Coffee BuzzBuzzBuzz!\®",
            "Cookies & Cream Cheesecake Core","Everything But The...\®","Half Baked\®","Karamel Sutra\® Core","Keep Caramel & Cookie On\™",
            "Milk & Cookies",
            "Mint Chocolate Cookie","New York Super Fudge Chunk\®",
            "Peanut Buttah Cookie Core",
            "Peanut Butter Cup",
            "Peanut Butter Fudge Core","Peanut Butter World\®","Phish Food\®",
            "Pistachio Pistachio",
            "Pumpkin Cheesecake",
            "Red Velvet Cake",
            "S'mores",
            "Salted Caramel Almond",
            "Salted Caramel Core","Spectacular Speculoos\™ Cookie Core",
            "Strawberry Cheesecake","The Tonight Dough\®","Triple Caramel Chunk\®","Truffle Kerfuffle\™","Urban Bourbon\™",
            "Vanilla",
            "Vanilla Caramel Fudge",
            "Vanilla Toffee Bar Crunch"
        ]
    },
    {
        "name": "Caring Dairy",
        "products": [
            "Caramel Chocolate Cheesecake","Chillin' the Roast\™","Chocolate Shake It\™",
            "One Sweet World",
            "Banana Split","Blondie Ambition\™","Boom Chocolatta\™ Cookie Core",
            "Bourbon Pecan Pie","Brewed to Matter\™",
            "Brownie Batter Core",
            "Cheesecake Brownie","Cherry Garcia\®",
            "Chocolate Chip Cookie Dough",
            "Chocolate Fudge Brownie","Chocolate Therapy\®","Chubby Hubby\®","Chunky Monkey\®","Cinnamon Buns\®",
            "Coconuts for Caramel Core",
            "Coffee Toffee Bar Crunch","Coffee, Coffee BuzzBuzzBuzz!\®",
            "Cookies & Cream Cheesecake Core","Everything But The...\®","Half Baked\®","Karamel Sutra\® Core","Keep Caramel & Cookie On\™",
            "Milk & Cookies",
            "Mint Chocolate Cookie","New York Super Fudge Chunk\®","Oat of This Swirled\™",
            "Peanut Buttah Cookie Core",
            "Peanut Butter Cup",
            "Peanut Butter Fudge Core","Peanut Butter World\®","Phish Food\®",
            "Pistachio Pistachio",
            "Pumpkin Cheesecake",
            "Red Velvet Cake",
            "S'mores",
            "Salted Caramel Almond",
            "Salted Caramel Core","Spectacular Speculoos\™ Cookie Core",
            "Strawberry Cheesecake","The Tonight Dough\®","Triple Caramel Chunk\®","Urban Bourbon\™",
            "Vanilla",
            "Vanilla Caramel Fudge",
            "Vanilla Toffee Bar Crunch"
        ]
    },
    {
        "name": "Fairtrade",
        "products": [
            "Caramel Chocolate Cheesecake","Chillin' the Roast\™","Chocolate Shake It\™",
            "One Sweet World",
            "Banana Split","Blondie Ambition\™","Boom Chocolatta\™ Cookie Core",
            "Bourbon Pecan Pie","Brewed to Matter\™",
            "Brownie Batter Core",
            "Cheesecake Brownie","Cherry Garcia\®",
            "Chocolate Chip Cookie Dough",
            "Chocolate Fudge Brownie","Chocolate Therapy\®","Chubby Hubby\®","Chunky Monkey\®","Cinnamon Buns\®",
            "Coconuts for Caramel Core",
            "Coffee Toffee Bar Crunch","Coffee, Coffee BuzzBuzzBuzz!\®",
            "Cookies & Cream Cheesecake Core","Everything But The...\®","Half Baked\®","Karamel Sutra\® Core","Keep Caramel & Cookie On\™",
            "Milk & Cookies",
            "Mint Chocolate Cookie","New York Super Fudge Chunk\®","Oat of This Swirled\™",
            "Peanut Buttah Cookie Core",
            "Peanut Butter Cup",
            "Peanut Butter Fudge Core","Peanut Butter World\®","Phish Food\®",
            "Pistachio Pistachio",
            "Pumpkin Cheesecake",
            "Red Velvet Cake",
            "S'mores",
            "Salted Caramel Almond",
            "Salted Caramel Core","Spectacular Speculoos\™ Cookie Core",
            "Strawberry Cheesecake","The Tonight Dough\®","Triple Caramel Chunk\®","Truffle Kerfuffle\™","Urban Bourbon\™",
            "Vanilla",
            "Vanilla Caramel Fudge",
            "Vanilla Toffee Bar Crunch"
        ]
    },
    // etc, omitted for brevity
```

</details>

### Get sourcing value by name (case insensitive)
```shell
GET /sourcing-value/{name}
```

<details>
<summary>Sample cURL request and response</summary>

**Request**
```shell
curl --location --request GET 'localhost:6700/sourcing-values/non-GMO'
```

**Response 200 OK**
```http
HTTP/1.1 200 OK
Content-Length: 1300
Content-Type: application/json

{
    "name": "Non-GMO",
    "products": [
        "Caramel Chocolate Cheesecake",
        "Chillin' the Roast\\u2122",
        "Chocolate Shake It\\u2122",
        "One Sweet World",
        "Banana Split",
        "Blondie Ambition\\u2122",
        "Boom Chocolatta\\u2122 Cookie Core",
        "Bourbon Pecan Pie",
        "Brewed to Matter\\u2122",
        "Brownie Batter Core",
        "Cheesecake Brownie",
        "Cherry Garcia\\u00ae",
        "Chocolate Chip Cookie Dough",
        "Chocolate Fudge Brownie",
        "Chocolate Therapy\\u00ae",
        "Chubby Hubby\\u00ae",
        "Chunky Monkey\\u00ae",
        "Cinnamon Buns\\u00ae",
        "Coconuts for Caramel Core",
        "Coffee Toffee Bar Crunch",
        "Coffee, Coffee BuzzBuzzBuzz!\\u00ae",
        "Cookies & Cream Cheesecake Core",
        "Everything But The...\\u00ae",
        "Half Baked\\u00ae",
        "Karamel Sutra\\u00ae Core",
        "Keep Caramel & Cookie On\\u2122",
        "Milk & Cookies",
        "Mint Chocolate Cookie",
        "New York Super Fudge Chunk\\u00ae",
        "Oat of This Swirled\\u2122",
        "Peanut Buttah Cookie Core",
        "Peanut Butter Cup",
        "Peanut Butter Fudge Core",
        "Peanut Butter World\\u00ae",
        "Phish Food\\u00ae",
        "Pistachio Pistachio",
        "Pumpkin Cheesecake",
        "Red Velvet Cake",
        "S'mores",
        "Salted Caramel Almond",
        "Salted Caramel Core",
        "Spectacular Speculoos\\u2122 Cookie Core",
        "Strawberry Cheesecake",
        "The Tonight Dough\\u00ae",
        "Triple Caramel Chunk\\u00ae",
        "Truffle Kerfuffle\\u2122",
        "Urban Bourbon\\u2122",
        "Vanilla",
        "Vanilla Caramel Fudge",
        "Vanilla Toffee Bar Crunch"
    ]
}
```
</details>

### Create a new sourcing value
```shell
POST /sourcing-values
```

<details>
<summary>Sample cURL request and response</summary>

**Request**
```shell
curl --location --request POST 'localhost:6700/sourcing-values' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "sourcing value 1"
}'
```

**Response 201 CREATED**
```http
HTTP/1.1 201 Created
Content-Length: 0
Location: http://localhost:6700/sourcing-values/sourcing%20value%201
```

</details>
