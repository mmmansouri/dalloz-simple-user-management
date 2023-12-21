CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO users(id,first_name,last_name,email) VALUES (uuid_generate_v4(),'Jacques' ,'Chirac','jchirac@gmail.com');
INSERT INTO users(id,first_name,last_name,email) VALUES (uuid_generate_v4(),'Barack' ,'Obama','bobama@gmail.com');
INSERT INTO users(id,first_name,last_name,email) VALUES (uuid_generate_v4(),'Omar' ,'Charif','ocharif@gmail.com');