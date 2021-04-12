/*WITH mx AS (SELECT MAX(id) AS id FROM forum_draft.public.m_users)
SELECT setval('forum_draft.public.m_users_id_seq', mx.id) AS curseq
FROM mx;

WITH mx AS (SELECT MAX(id) AS id FROM forum_draft.public.m_messages)
SELECT setval('forum_draft.public.m_messages_id_seq', mx.id) AS curseq
FROM mx;

WITH mx AS (SELECT MAX(id) AS id FROM forum_draft.public.m_comments)
SELECT setval('forum_draft.public.m_comments_id_seq', mx.id) AS curseq
FROM mx;*/


alter sequence m_users_id_seq restart with 62;
alter sequence m_messages_id_seq restart with 86;
alter sequence m_comments_id_seq restart with 134;