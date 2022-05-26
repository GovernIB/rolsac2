
    create sequence RSC_PROCEDIMENT_SEQ start 1 increment 1;
    create sequence RSC_UNITATORGANICA_SEQ start 1 increment 1;

    create table RSC_PROCEDIMENT (
       ID int8 not null,
        CODISIA varchar(8) not null,
        NOM varchar(50) not null,
        UNITATORGANICAID int8 not null,
        constraint RSC_PROCEDIMENT_PK primary key (ID)
    );

    create table RSC_UNITATORGANICA (
       ID int8 not null,
        CODIDIR3 varchar(9) not null,
        DATACREACIO date not null,
        ESTAT int4 not null,
        NOM varchar(50) not null,
        constraint RSC_UNITAT_PK primary key (ID)
    );

    create index RSC_PROCEDIMENT_PK_I on RSC_PROCEDIMENT (ID);
    create index RSC_PROCEDIMENT_CODISIA_UK_I on RSC_PROCEDIMENT (CODISIA);
    create index RSC_PROCEDIMENT_UNITAT_FK_I on RSC_PROCEDIMENT (UNITATORGANICAID);

    alter table RSC_PROCEDIMENT 
       add constraint RSC_PROCEDIMENT_CODISIA_UK unique (CODISIA);

    create index RSC_UNITAT_PK_I on RSC_UNITATORGANICA (ID);
    create index RSC_UNITAT_CODIDIR3_UK_I on RSC_UNITATORGANICA (CODIDIR3);

    alter table RSC_UNITATORGANICA 
       add constraint RSC_UNITAT_CODIDIR3_UK unique (CODIDIR3);

    alter table RSC_PROCEDIMENT 
       add constraint RSC_PROCEDIMENT_UNITAT_FK 
       foreign key (UNITATORGANICAID) 
       references RSC_UNITATORGANICA;
