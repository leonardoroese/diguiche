CREATE TABLE display
(
  id serial NOT NULL,
  num character varying(20),
  code character varying(20),
  CONSTRAINT displayid PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE display
  OWNER TO dg;
GRANT ALL ON TABLE display TO public;
GRANT ALL ON TABLE display TO dg;
