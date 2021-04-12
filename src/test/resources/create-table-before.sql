delete from m_comments;
delete from m_messages;
delete from m_user_subscriptions;
delete from m_roles;
delete from m_users;

insert into m_users (id, active, password, username, email, phone_number, picture_name, activation_code) values
(1, true, '$2a$08$LGVqAQR5UHV4gZmtqgPwterEHJ8Lsl.vthhXDCeSOH6r5AsF58S0y', 'ADMIN', 'admin@email.com', 'ADMIN-1-phone' ,'logo-2.png',null),
(2, true, '$2a$08$CV4iWWezUmuEAC/elZyWteOecB5JPaozD.7yVtPV0FBG19DlyBv1W', 'user-1', 'user-1@email.com', 'user-1-phone' ,'logo-2.png',null),
(3, true, '$2a$08$Lcm4AnhYDPbKPwk3NN05bOA1eDYoEA01gjc6eVCkA5auJ./osP37.', 'user-2', 'user-2@email.com', 'user-2-phone' ,'logo-2.png',null),
(4, true, '$2a$08$.h6PVCm4GBMMqPVA1GOmuupytgMO.gV7HCr.UUP9caendSPe3ROam', 'user-3', 'user-3@email.com', 'user-3-phone' ,'logo-2.png',null),
(5, true, '$2a$08$IFSMrHSRtIQmWjqw9Fieleykv5bSqP2ww99xyvm51li2pkACPQrNy', 'user-4', 'user-4@email.com', 'user-4-phone' ,'logo-2.png',null),
(6, true, '$2a$08$CB2QikL797syDYYijsMqnOc7uv9dkf6uEhfGqh6syvxUf2DrmIWY2', 'user-5', 'user-5@email.com', 'user-5-phone' ,'logo-2.png',null),
(7, true, '$2a$08$cpKMmtKIhJfgPi7oSrufYuyNjGswkDyyLvMBCBnnFpdjZpTNDmYyy', 'user-6', 'user-6@email.com', 'user-6-phone' ,'logo-2.png',null),
(8, true, '$2a$08$l8LJwWb/q29AMnkdK2ov3OvhULdsMiaxTGV/AFB2F425MA44c9woC', 'user-7', 'user-7@email.com', 'user-7-phone' ,'logo-2.png',null),
(9, true, '$2a$08$2ZL.vZICvr4TMe62XmfCLuABRhpWWEEHPEbylpvhp8c6j1OMk6Rvm', 'user-8', 'user-8@email.com', 'user-8-phone' ,'logo-2.png',null),
(10, true, '$2a$08$yRsMMWUZiIWDoASunefoIenkMg/g2olAqQyjm8bMhK2fIMN5tOSAy', 'user-9', 'user-9@email.com', 'user-9-phone' ,'logo-2.png',null),
(11, true, '$2a$08$lRcKY4uNX5uPRkA1Bpa1luBLvc/aYLv8eVC/xpKGXkxUAlsX25Vua', 'user-10', 'user-10@email.com', 'user-10-phone' ,'logo-2.png',null);

INSERT INTO m_roles (user_id, role_name) VALUES (1, 'USER');
INSERT INTO m_roles (user_id, role_name) VALUES (1, 'ADMIN');
INSERT INTO m_roles (user_id, role_name) VALUES (2, 'USER');
INSERT INTO m_roles (user_id, role_name) VALUES (2, 'ADMIN');
INSERT INTO m_roles (user_id, role_name) VALUES (3, 'USER');
INSERT INTO m_roles (user_id, role_name) VALUES (3, 'ADMIN');
INSERT INTO m_roles (user_id, role_name) VALUES (4, 'USER');
INSERT INTO m_roles (user_id, role_name) VALUES (4, 'ADMIN');
INSERT INTO m_roles (user_id, role_name) VALUES (5, 'USER');
INSERT INTO m_roles (user_id, role_name) VALUES (6, 'USER');
INSERT INTO m_roles (user_id, role_name) VALUES (7, 'USER');
INSERT INTO m_roles (user_id, role_name) VALUES (8, 'USER');
INSERT INTO m_roles (user_id, role_name) VALUES (9, 'USER');
INSERT INTO m_roles (user_id, role_name) VALUES (10, 'USER');
INSERT INTO m_roles (user_id, role_name) VALUES (11, 'USER');

insert into m_messages(id, tag, text, user_id, filename, topic_name, time) values
(1, 'tag-1', 'text-admin-1', 1, null, 'admin-topic-1', '2021-04-04 16:40:52.000000'),
(2, 'tag-1', 'text-admin-2', 1, null, 'admin-topic-1', '2021-04-04 16:41:52.000000'),
(3, 'tag-1', 'text-admin-3', 1, null, 'admin-topic-1', '2021-04-04 16:42:52.000000'),
(4, 'tag-1', 'text-admin-4', 1, null, 'admin-topic-1', '2021-04-04 16:43:52.000000'),
(5, 'tag-2', 'text-user-1-1', 2, null, 'user-1-topic-1', '2021-04-04 16:44:52.000000'),
(6, 'tag-2', 'text-user-1-2', 2, null, 'user-1-topic-2', '2021-04-04 16:45:52.000000'),
(7, 'tag-2', 'text-user-1-3', 2, null, 'user-1-topic-3', '2021-04-04 16:46:52.000000'),
(8, 'tag-2', 'text-user-1-4', 2, null, 'user-1-topic-4', '2021-04-04 16:47:52.000000'),
(9, 'tag-3', 'text-user-2-1', 3, null, 'user-2-topic-1', '2021-04-04 16:48:52.000000'),
(10, 'tag-3', 'text-user-2-2', 3, null, 'user-2-topic-2', '2021-04-04 16:49:52.000000'),
(11, 'tag-4', 'text-user-3-1', 4, null, 'user-3-topic-1', '2021-04-04 16:50:52.000000'),
(12, 'tag-4', 'text-user-3-2', 4, null, 'user-3-topic-2', '2021-04-04 16:51:52.000000');

insert into m_comments(id, message_id, text, user_id, time, parent_id) values
(1, 1, 'nice', 1, '2021-04-04 18:02:14.000000', null),
(2, 1, 'not nice', 2, '2021-04-04 18:03:14.220000', 1),
(3, 1, 'very nice', 3, '2021-04-04 18:04:14.220000', null),
(4, 1, 'no', 2, '2021-04-04 18:05:14.220000', 3),
(5, 2, 'cool', 4, '2021-04-04 18:02:14.220000', null),
(6, 2, 'cool', 5, '2021-04-04 18:02:14.220000', 5);

alter sequence m_users_id_seq restart with 12;
alter sequence m_messages_id_seq restart with 13;
alter sequence m_comments_id_seq restart with 7;

INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (2, 1);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (3, 1);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (4, 1);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (5, 1);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (6, 1);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (7, 1);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (8, 1);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (9, 1);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (10, 1);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (11, 1);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (1, 2);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (3, 2);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (4, 2);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (5, 2);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (1, 3);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (2, 3);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (1, 4);
INSERT INTO m_user_subscriptions (subscriber_id, subscription_id) VALUES (6, 5);