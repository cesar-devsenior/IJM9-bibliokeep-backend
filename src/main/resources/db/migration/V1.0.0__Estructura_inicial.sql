CREATE TABLE books (
    id bigserial,
    description text,
    is_lent boolean NOT NULL,
    isbn varchar(13) NOT NULL,
    owner_id uuid NOT NULL,
    rating integer,
    status varchar(255) NOT NULL,
    thumbnail text,
    title varchar(255) NOT NULL,
    CONSTRAINT books_pkey PRIMARY KEY (id),
    CONSTRAINT idx_isbn UNIQUE (isbn),
    CONSTRAINT books_rating_check CHECK (rating >= 1 AND rating <= 5),
    CONSTRAINT books_status_check CHECK (status::text = ANY (ARRAY['DESEADO'::character varying, 'COMPRADO'::character varying, 'LEYENDO'::character varying, 'LEIDO'::character varying, 'ABANDONADO'::character varying]::text[]))
);

CREATE TABLE book_authors (
    book_id bigint NOT NULL,
    author varchar(255),
    CONSTRAINT book_authors_book_fk FOREIGN KEY (book_id) REFERENCES books (id)
);

CREATE TABLE loans (
    id bigserial,
    contact_name varchar(255) NOT NULL,
    due_date date NOT NULL,
    loan_date date NOT NULL,
    returned boolean NOT NULL,
    book_id bigint NOT NULL,
    CONSTRAINT loans_pkey PRIMARY KEY (id),
    CONSTRAINT loans_book_fk FOREIGN KEY (book_id) REFERENCES books (id)
);

CREATE TABLE roles
(
    id bigserial,
    name varchar(100) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id),
    CONSTRAINT roles_name_uk UNIQUE (name)
);

CREATE TABLE users (
    id uuid NOT NULL,
    annual_goal integer NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT idx_email UNIQUE (email),
    CONSTRAINT users_annual_goal_check CHECK (annual_goal >= 1)
);

CREATE TABLE user_preferences (
    user_id uuid NOT NULL,
    preference varchar(255),
    CONSTRAINT user_preferences_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE user_roles (
    user_id uuid NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_roles_role_fk FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT user_roles_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);
