INSERT INTO users(id, annual_goal, email, password)
VALUES ('468a2f60-f343-48d6-bf29-dc55d9c7c28a', 5, 'admin@email.com', '$2a$12$74fo6s.t3c7O954znaxr3uGjy.1W8eKvVZW8uffu59q8dfnDCX/k6');

INSERT INTO user_roles (user_id, role_id) VALUES ('468a2f60-f343-48d6-bf29-dc55d9c7c28a', 1);

INSERT INTO user_preferences(user_id, preference) 
    VALUES ('468a2f60-f343-48d6-bf29-dc55d9c7c28a', 'Theme=Dark');
INSERT INTO user_preferences(user_id, preference) 
    VALUES ('468a2f60-f343-48d6-bf29-dc55d9c7c28a', 'Language=es');
