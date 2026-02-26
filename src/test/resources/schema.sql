CREATE TABLE IF NOT EXISTS roles
(
    id bigserial,
    name character varying(100) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id),
    CONSTRAINT ukofx66keruapi6vyqpv6f2or37 UNIQUE (name)
);


CREATE TABLE IF NOT EXISTS users
(
    id uuid NOT NULL,
    annual_goal integer NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT idx_email UNIQUE (email),
    CONSTRAINT users_annual_goal_check CHECK (annual_goal >= 1)
);

CREATE TABLE IF NOT EXISTS  user_roles
(
    user_id uuid NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id)
        REFERENCES public.roles (id),
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id)
        REFERENCES public.users (id)
);

CREATE TABLE IF NOT EXISTS books
(
    id bigserial,
    description text,
    is_lent boolean NOT NULL,
    isbn character varying(13) NOT NULL,
    owner_id uuid NOT NULL,
    rating integer,
    status character varying(255) NOT NULL,
    thumbnail text,
    title character varying(255) NOT NULL,
    CONSTRAINT books_pkey PRIMARY KEY (id),
    CONSTRAINT idx_isbn UNIQUE (isbn),
    CONSTRAINT books_rating_check CHECK (rating >= 1 AND rating <= 5),
    CONSTRAINT books_status_check CHECK (status in ('DESEADO', 'COMPRADO', 'LEYENDO', 'LEIDO', 'ABANDONADO'))
);