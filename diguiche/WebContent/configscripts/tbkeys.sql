CREATE TABLE keys
(
  id serial NOT NULL,
  mesa bigint NOT NULL,
  tipo character varying(1),
  seq integer,
  dtreg timestamp without time zone,
  dtview timestamp without time zone,
  viewer character varying(20),
  CONSTRAINT senhaid PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE keys
  OWNER TO dg;
GRANT ALL ON TABLE keys TO public;
GRANT ALL ON TABLE keys TO dg;
