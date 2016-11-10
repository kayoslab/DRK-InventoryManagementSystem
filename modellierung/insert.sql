INSERT INTO `Operation` (title)
VALUES
    ('create-user'),
    ('delete-user'),
    ('modify-user'),
    ('user-login'),
    ('user-logout'),
    ('create-userRight'),
    ('delete-userRight'),
    ('modify-userRight'),
    ('create-material'),
    ('delete-material'),
    ('modify-material'),
    ('material-increase'),
    ('material-decrease'),
    ('material-correction');

INSERT INTO `GroupRight` (title)
VALUES
    ('create-user'),
    ('delete-user'),
    ('modify-user'),
    ('create-userRight'),
    ('delete-userRight'),
    ('modify-userRight'),
    ('create-material'),
    ('delete-material'),
    ('modify-material'),
    ('material-increase'),
    ('material-decrease'),
    ('material-correction'),
    ('consumable-material'),
    ('medical-material'),
    ('devices');

INSERT INTO `Group` (title)
VALUES
    ('admin'),
    ('user');

INSERT INTO `GroupHasRights`
VALUES
    (1,1),
    (1,2),
    (1,3),
    (1,4),
    (1,5),
    (1,6),
    (1,7),
    (1,8),
    (1,9),
    (1,10),
    (1,11),
    (1,12);

/* pw = md5(test) */
INSERT INTO `User` (username, firstname, name, password, passwordChanged)
VALUES ('root', 'test', 'test', '098f6bcd4621d373cade4e832627b4f6', 0);

INSERT INTO `UserIsMemberOfGroup`
VALUES (1,1);

INSERT INTO `Logbook` (title, material_id, operation_id, user_id)
VALUES ('Initialisation', NULL, 6, 1);
