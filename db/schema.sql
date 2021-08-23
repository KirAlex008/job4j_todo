create TABLE if not exists item (
                      id serial primary key,
                      description varchar(200) NOT NULL,
                      created timestamp NOT NULL,
                      done boolean NOT NULL,
                      user_id integer references users(id)

);

create TABLE if not exists users (
                      id serial primary key,
                      name varchar(200) NOT NULL,
                      email varchar(200) NOT NULL UNIQUE,
                      password varchar(200) NOT NULL
);

