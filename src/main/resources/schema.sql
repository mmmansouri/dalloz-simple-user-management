CREATE TABLE IF NOT EXISTS users (id uuid not null,
                                  first_name varchar not null,
                                  last_name varchar not null,
                                  email varchar not null,
                                  primary key (id),
                                  UNIQUE (email));