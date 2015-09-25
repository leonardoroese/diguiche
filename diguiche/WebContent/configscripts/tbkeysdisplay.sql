CREATE TABLE keysdisplay
(
  idkey bigint NOT NULL,
  iddisplay bigint NOT NULL,
  dtview timestamp without time zone,
  CONSTRAINT kdispview PRIMARY KEY (idkey, iddisplay)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE keysdisplay
  OWNER TO dg;
GRANT ALL ON TABLE keysdisplay TO public;
GRANT ALL ON TABLE keysdisplay TO dg;
