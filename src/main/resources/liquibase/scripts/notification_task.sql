--liquibase formatted sql

-- changeset aleksbratchin:create_notification_task
CREATE TABLE notification_task
(
    id              uuid NULL,
    chat_id           bigint    NOT NULL,
    message           text      NOT NULL,
    date_notification timestamp NOT NULL,
    notification_sent bool,
    CONSTRAINT notification_task_pk PRIMARY KEY (id)
);
