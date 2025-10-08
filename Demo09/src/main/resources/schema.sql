CREATE TABLE pet_table (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    gender VARCHAR(1) CHECK (gender IN ('M','F')) NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(10) CHECK (type IN ('BIRD','CAT','DOG','FISH','RABBIT')) NOT NULL
);

CREATE TABLE domestic_pet_table (
    id INTEGER PRIMARY KEY,
    date_of_birth DATE NOT NULL,
    FOREIGN KEY (id) REFERENCES pet_table(id)
);

CREATE TABLE wild_pet_table (
    id INTEGER PRIMARY KEY,
    place_of_birth VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES pet_table(id)
);

CREATE TABLE owner_table (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    gender VARCHAR(1) CHECK (gender IN ('M','F')) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    mobile_number VARCHAR(10) NOT NULL UNIQUE,
    email_id VARCHAR(255) NOT NULL UNIQUE,
    pet_id INTEGER NOT NULL UNIQUE,
    FOREIGN KEY (pet_id) REFERENCES pet_table(id)
);
