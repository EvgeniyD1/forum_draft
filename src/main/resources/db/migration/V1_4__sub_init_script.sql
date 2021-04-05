create table m_user_subscriptions
(
    subscriber_id bigint not null
        constraint m_user_subscriptions_subscriber_id_fkey
            references m_users,
    subscription_id bigint not null
        constraint m_user_subscriptions_subscription_id_fkey
            references m_users,
    constraint m_user_subscriptions_pkey
        primary key (subscriber_id, subscription_id)
);

alter table m_user_subscriptions owner to testuser;

