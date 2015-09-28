CREATE TABLE sync
(
  tipo character varying(1) NOT NULL,
  devid bigint NOT NULL,
  dtsync timestamp without time zone,
  ip character varying(40),
  CONSTRAINT syncid PRIMARY KEY (tipo, devid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sync
  OWNER TO dg;
GRANT ALL ON TABLE sync TO public;
GRANT ALL ON TABLE sync TO dg;
