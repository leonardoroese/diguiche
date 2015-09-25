CREATE TABLE mesa
(
  id serial NOT NULL,
  num character varying(20),
  code character varying(20),
  CONSTRAINT mesaid PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mesa
  OWNER TO dg;
GRANT ALL ON TABLE mesa TO public;
GRANT ALL ON TABLE mesa TO dg;
