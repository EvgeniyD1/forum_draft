WITH mx AS (SELECT MAX(id) AS id FROM forum_draft.public.m_users)
SELECT setval('forum_draft.public.m_users_id_seq', mx.id) AS curseq
FROM mx;

-- WITH mx AS (SELECT MAX(id) AS id FROM forum_draft.public.m_roles)
-- SELECT setval('forum_draft.public.m_roles_id_seq', mx.id) AS curseq
-- FROM mx;

WITH mx AS (SELECT MAX(id) AS id FROM forum_draft.public.m_messages)
SELECT setval('forum_draft.public.m_messages_id_seq', mx.id) AS curseq
FROM mx;

WITH mx AS (SELECT MAX(id) AS id FROM forum_draft.public.m_comments)
SELECT setval('forum_draft.public.m_comments_id_seq', mx.id) AS curseq
FROM mx;


