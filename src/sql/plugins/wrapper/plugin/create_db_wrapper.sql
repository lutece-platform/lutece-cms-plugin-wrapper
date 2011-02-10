--
-- Structure for table htmlpage
--

DROP TABLE IF EXISTS wrapper;
CREATE TABLE wrapper (
  id_wrapper INT DEFAULT 0 NOT NULL,
  description varchar(255) DEFAULT '' NOT NULL,
  wrapper_url LONG VARCHAR,
  wrapper_styles LONG VARCHAR,
  status INT DEFAULT 0 NOT NULL,
  workgroup_key varchar(50) DEFAULT 'all' NOT NULL,  
  role varchar(50) DEFAULT 'none' NOT NULL,  
  PRIMARY KEY (id_wrapper)
);