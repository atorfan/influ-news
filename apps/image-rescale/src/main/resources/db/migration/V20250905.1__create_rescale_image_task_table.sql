CREATE TABLE rescale_image_task
(
    id                  UUID PRIMARY KEY NOT NULL,
    created_at          TIMESTAMP        NOT NULL,
    original_image_hash VARCHAR          NOT NULL,
    width               INTEGER          NOT NULL,
    height              INTEGER          NOT NULL,
    image_url           VARCHAR          NOT NULL
);