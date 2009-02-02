alter table sisgestor.DPR_DEPARTAMENTO 
DROP 
    foreign key IR_DPR_DPR; 
alter table sisgestor.UPM_USUARIO_PERMISSAO 
DROP 
    foreign key IR_PRM_UPM; 
alter table sisgestor.UPM_USUARIO_PERMISSAO 
DROP 
    foreign key IR_UUR_UPM; 
alter table sisgestor.UUR_USUARIO 
DROP 
    foreign key IR_DPR_UUR; 
DROP 
    table if exists sisgestor.DPR_DEPARTAMENTO; 
DROP 
    table if exists sisgestor.PRM_PERMISSAO; 
DROP 
    table if exists sisgestor.UPM_USUARIO_PERMISSAO; 
DROP 
    table if exists sisgestor.UUR_USUARIO; 
CREATE 
    table sisgestor.DPR_DEPARTAMENTO 
    ( 
        DPR_ID integer not null auto_increment, 
        DPR_EMAIL varchar(40), 
        DPR_NOME varchar(50) not null, 
        DPR_SIGLA CHAR(10) not null, 
        DPR_ID_SUPERIOR integer, 
        primary key (DPR_ID), 
        unique (DPR_SIGLA) 
    ) 
    ENGINE= InnoDB; 
CREATE 
    table sisgestor.PRM_PERMISSAO 
    ( 
        PRM_ID integer not null auto_increment, 
        PRM_DESCRICAO varchar(30) not null, 
        primary key (PRM_ID) 
    ) 
    ENGINE= InnoDB; 
CREATE 
    table sisgestor.UPM_USUARIO_PERMISSAO 
    ( 
        UUR_ID integer not null, 
        PRM_ID integer not null, 
        primary key (PRM_ID, UUR_ID) 
    ) 
    ENGINE= InnoDB; 
CREATE 
    table sisgestor.UUR_USUARIO 
    ( 
        UUR_ID integer not null auto_increment, 
        UUR_CHEFE CHAR(1) not null, 
        UUR_EMAIL varchar(40), 
        UUR_LOGIN CHAR(15) not null, 
        UUR_NOME varchar(150) not null, 
        UUR_SENHA CHAR(20) not null, 
        DPR_ID integer not null, 
        primary key (UUR_ID), 
        unique (UUR_LOGIN) 
    ) 
    ENGINE= InnoDB; 
alter table sisgestor.DPR_DEPARTAMENTO add index IR_DPR_DPR (DPR_ID_SUPERIOR), add constraint IR_DPR_DPR foreign key (DPR_ID_SUPERIOR) references sisgestor.DPR_DEPARTAMENTO (DPR_ID); 
alter table sisgestor.UPM_USUARIO_PERMISSAO add index IR_PRM_UPM (PRM_ID), add constraint IR_PRM_UPM foreign key (PRM_ID) references sisgestor.PRM_PERMISSAO (PRM_ID); 
alter table sisgestor.UPM_USUARIO_PERMISSAO add index IR_UUR_UPM (UUR_ID), add constraint IR_UUR_UPM foreign key (UUR_ID) references sisgestor.UUR_USUARIO (UUR_ID); 
alter table sisgestor.UUR_USUARIO add index IR_DPR_UUR (DPR_ID), add constraint IR_DPR_UUR foreign key (DPR_ID) references sisgestor.DPR_DEPARTAMENTO (DPR_ID); 

INSERT INTO `dpr_departamento` (`DPR_ID`,`DPR_NOME`,`DPR_EMAIL`,`DPR_SIGLA`,`DPR_ID_SUPERIOR`) 
	VALUES (1,'Departamento','email@mail.com','DP',NULL);

INSERT INTO `uur_usuario` (`UUR_ID`,`UUR_EMAIL`,`UUR_LOGIN`,`UUR_NOME`,`UUR_SENHA`,`DPR_ID`,`UUR_CHEFE`) 
	VALUES (1,'email@email.com','admin','Administrador','admin',1, 1);

INSERT INTO `prm_permissao` (`PRM_ID`,`PRM_DESCRICAO`) 
	VALUES (1,'1');

INSERT INTO `upm_usuario_permissao` (`UUR_ID`,`PRM_ID`) 
	VALUES (1,1);
