DROP TABLE IF EXISTS players;

CREATE TABLE players
(
    id                      BIGINT,
    nick_name               VARCHAR(256) NOT NULL,
    first_name              VARCHAR(256) NOT NULL,
    last_name               VARCHAR(256) NOT NULL,
    PRIMARY KEY (id)
);
