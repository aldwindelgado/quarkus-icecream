-- See flyway for more information about database automatic migration :)

-- starts the primary key value automatic generation at 5000 w/ increment of 1
CREATE SEQUENCE hibernate_sequence START 5000 INCREMENT 1;

----------------------------------------------------------------
-- CREATE TABLES and IMPORT TEST DATA
-----------------------------------------------------------------

CREATE TABLE product (
    id INT8 NOT NULL,
    name VARCHAR NOT NULL CONSTRAINT unq_product_name UNIQUE,
    image_closed TEXT,
    image_open TEXT,
    description TEXT,
    story TEXT,
    allergy_info VARCHAR,
    dietary_certifications VARCHAR,
    PRIMARY KEY (id)
);

CREATE TABLE sourcing_value (
    id INT8 NOT NULL,
    name VARCHAR NOT NULL CONSTRAINT unq_sourcingvalue_name UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE ingredient (
    id INT8 NOT NULL,
    name VARCHAR NOT NULL CONSTRAINT unq_ingredient_name UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE map_product_sourcingvalue (
    product_id INT8 NOT NULL,
    sourcing_value_id INT8 NOT NULL,
    PRIMARY KEY (product_id, sourcing_value_id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (sourcing_value_id) REFERENCES sourcing_value (id)
);

CREATE TABLE map_product_ingredient (
    product_id INT8 NOT NULL,
    ingredient_id INT8 NOT NULL,
    PRIMARY KEY (product_id, ingredient_id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredient (id)
);


