
    alter table RSC_PROCEDIMENT 
       drop constraint RSC_PROCEDIMENT_UNITAT_FK;

    drop table if exists RSC_PROCEDIMENT cascade;

    drop table if exists RSC_UNITATORGANICA cascade;

    drop sequence if exists RSC_PROCEDIMENT_SEQ;

    drop sequence if exists RSC_UNITATORGANICA_SEQ;
