
    create sequence RSC_PROCEDIMENT_SEQ start with 1 increment by  1;
    create sequence RSC_UNITATORGANICA_SEQ start with 1 increment by  1;

    create table RSC_PROCEDIMENT (
        ID number(19,0) not null,
        CODISIA varchar2(8 char) not null,
        NOM varchar2(50 char) not null,
        UNITATORGANICAID number(19,0) not null
    );

    create table RSC_UNITATORGANICA (
        ID number(19,0) not null,
        CODIDIR3 varchar2(9 char) not null,
        DATACREACIO date not null,
        ESTAT number(10,0) not null,
        NOM varchar2(50 char) not null
    );

    create index RSC_PROCEDIMENT_PK_I on RSC_PROCEDIMENT (ID);
    create index RSC_PROCEDIMENT_CODISIA_UK_I on RSC_PROCEDIMENT (CODISIA);
    create index RSC_PROCEDIMENT_UNITAT_FK_I on RSC_PROCEDIMENT (UNITATORGANICAID);

    alter table RSC_PROCEDIMENT
        add constraint RSC_PROCEDIMENT_PK primary key (ID);

    alter table RSC_PROCEDIMENT
        add constraint RSC_PROCEDIMENT_CODISIA_UK unique (CODISIA);

    create index RSC_UNITAT_PK_I on RSC_UNITATORGANICA (ID);
    create index RSC_UNITAT_CODIDIR3_UK_I on RSC_UNITATORGANICA (CODIDIR3);

    alter table RSC_UNITATORGANICA
        add constraint RSC_UNITAT_PK primary key (ID);

    alter table RSC_UNITATORGANICA
        add constraint RSC_UNITAT_CODIDIR3_UK unique (CODIDIR3);

    alter table RSC_PROCEDIMENT
        add constraint RSC_PROCEDIMENT_UNITAT_FK
        foreign key (UNITATORGANICAID)
        references RSC_UNITATORGANICA;

    -- Grants per l'usuari www_rolsac2
    -- seqüències
    GRANT SELECT, ALTER ON RSC_PROCEDIMENT_SEQ TO WWW_ROLSAC2;
    GRANT SELECT, ALTER ON RSC_UNITATORGANICA_SEQ TO WWW_ROLSAC2;
    -- taules
    GRANT SELECT, INSERT, UPDATE, DELETE ON RSC_PROCEDIMENT TO WWW_ROLSAC2;
    GRANT SELECT, INSERT, UPDATE, DELETE ON RSC_UNITATORGANICA TO WWW_ROLSAC2;


