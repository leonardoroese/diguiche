CREATE TABLE terminal
(
  id serial NOT NULL,
  num character varying(20),
  code character varying(20),
  CONSTRAINT terminalid PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE terminal
  OWNER TO dg;
GRANT ALL ON TABLE terminal TO public;
GRANT ALL ON TABLE terminal TO dg;
