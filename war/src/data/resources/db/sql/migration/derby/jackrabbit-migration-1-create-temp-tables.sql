# DbFileSystem
drop table COPY_JR_FSG_FSENTRY;

# PersistenceManager
drop table COPY_JR_DEFAULT_BUNDLE;
drop table COPY_JR_DEFAULT_REFS;
drop table COPY_JR_DEFAULT_BINVAL;
drop table COPY_JR_DEFAULT_NAMES;
drop table COPY_JR_LIVE_BUNDLE;
drop table COPY_JR_LIVE_REFS;
drop table COPY_JR_LIVE_BINVAL;
drop table COPY_JR_LIVE_NAMES;
drop table COPY_JR_V_BUNDLE;
drop table COPY_JR_V_REFS;
drop table COPY_JR_V_BINVAL;
drop table COPY_JR_V_NAMES;

# DbDataStore
drop table COPY_JR_DATASTORE;

# DbFileSystem - global
create table COPY_JR_FSG_FSENTRY (FSENTRY_PATH varchar(2048) not null, FSENTRY_NAME varchar(255) not null, FSENTRY_DATA blob(100M), FSENTRY_LASTMOD bigint not null, FSENTRY_LENGTH bigint not null);
create unique index COPY_JR_FSG_FSENTRY_IDX on COPY_JR_FSG_FSENTRY (FSENTRY_PATH, FSENTRY_NAME);

# PersistenceManager - default workspace
create table COPY_JR_DEFAULT_BUNDLE (NODE_ID_HI bigint not null, NODE_ID_LO bigint not null, BUNDLE_DATA blob(2G) not null, PRIMARY KEY (NODE_ID_HI, NODE_ID_LO));
create table COPY_JR_DEFAULT_REFS (NODE_ID_HI bigint not null, NODE_ID_LO bigint not null, REFS_DATA blob(2G) not null, PRIMARY KEY (NODE_ID_HI, NODE_ID_LO));
create table COPY_JR_DEFAULT_BINVAL (BINVAL_ID char(64) PRIMARY KEY, BINVAL_DATA blob(2G) not null);
create table COPY_JR_DEFAULT_NAMES (ID INTEGER GENERATED ALWAYS AS IDENTITY, NAME varchar(255) not null, PRIMARY KEY (ID, NAME));
# PersistenceManager - live workspace
create table COPY_JR_LIVE_BUNDLE (NODE_ID_HI bigint not null, NODE_ID_LO bigint not null, BUNDLE_DATA blob(2G) not null, PRIMARY KEY (NODE_ID_HI, NODE_ID_LO));
create table COPY_JR_LIVE_REFS (NODE_ID_HI bigint not null, NODE_ID_LO bigint not null, REFS_DATA blob(2G) not null, PRIMARY KEY (NODE_ID_HI, NODE_ID_LO));
create table COPY_JR_LIVE_BINVAL (BINVAL_ID char(64) PRIMARY KEY, BINVAL_DATA blob(2G) not null);
create table COPY_JR_LIVE_NAMES (ID INTEGER GENERATED ALWAYS AS IDENTITY, NAME varchar(255) not null, PRIMARY KEY (ID, NAME));
# PersistenceManager - versioning
create table COPY_JR_V_BUNDLE (NODE_ID_HI bigint not null, NODE_ID_LO bigint not null, BUNDLE_DATA blob(2G) not null, PRIMARY KEY (NODE_ID_HI, NODE_ID_LO));
create table COPY_JR_V_REFS (NODE_ID_HI bigint not null, NODE_ID_LO bigint not null, REFS_DATA blob(2G) not null, PRIMARY KEY (NODE_ID_HI, NODE_ID_LO));
create table COPY_JR_V_BINVAL (BINVAL_ID char(64) PRIMARY KEY, BINVAL_DATA blob(2G) not null);
create table COPY_JR_V_NAMES (ID INTEGER GENERATED ALWAYS AS IDENTITY, NAME varchar(255) not null, PRIMARY KEY (ID, NAME));

# DbDataStore
create table COPY_JR_DATASTORE (ID VARCHAR(255) PRIMARY KEY, LENGTH BIGINT, LAST_MODIFIED BIGINT, DATA BLOB);
