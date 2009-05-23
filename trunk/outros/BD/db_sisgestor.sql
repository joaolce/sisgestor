/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     11/5/2009 12:36:18                           */
/*==============================================================*/
drop database if exists sisgestor;

create database sisgestor;

drop table if exists sisgestor.PRO_PROCESSO;

drop table if exists sisgestor.TAR_TAREFA;

drop table if exists sisgestor.TRA_TRANSACAO_ATIVIDADE;

drop table if exists sisgestor.TRP_TRANSACAO_PROCESSO;

drop table if exists sisgestor.TRT_TRANSACAO_TAREFA;

drop table if exists sisgestor.UCA_USOWORKFLOW_CAMPO;

drop table if exists sisgestor.UPM_USUARIO_PERMISSAO;

drop table if exists sisgestor.UUR_USUARIO;

drop table if exists sisgestor.UWR_USO_WORKFLOW;

drop table if exists sisgestor.WOR_WORKFLOW;

/*==============================================================*/
/* Table: ANX_ANEXO                                             */
/*==============================================================*/
create table sisgestor.ANX_ANEXO
(
   ANX_ID               int not null auto_increment,
   ANX_CONTENT_TYPE     varchar(50) not null,
   ANX_DADOS            longblob not null,
   ANX_DATA_HORA_CRIACAO datetime not null,
   ANX_NOME             varchar(100) not null,
   UWR_ID               int not null,
   primary key (ANX_ID)
);

/*==============================================================*/
/* Table: ATI_ATIVIDADE                                         */
/*==============================================================*/
create table sisgestor.ATI_ATIVIDADE
(
   ATI_ID               int not null auto_increment,
   ATI_DESCRICAO        varchar(200) not null,
   ATI_NOME             varchar(100) not null,
   ATI_LEFT             int,
   ATI_TOP              int,
   DRP_ID               int not null,
   PRO_ID               int not null,
   primary key (ATI_ID)
);

/*==============================================================*/
/* Index: IX_DESCRICAO                                          */
/*==============================================================*/
create index IX_DESCRICAO on sisgestor.ATI_ATIVIDADE
(
   ATI_DESCRICAO
);

/*==============================================================*/
/* Index: IX_NOME                                               */
/*==============================================================*/
create index IX_NOME on sisgestor.ATI_ATIVIDADE
(
   ATI_NOME
);

/*==============================================================*/
/* Table: CAM_CAMPO                                             */
/*==============================================================*/
create table sisgestor.CAM_CAMPO
(
   CAM_ID               int not null auto_increment,
   CAM_DESCRICAO        varchar(200),
   CAM_NOME             varchar(100) not null,
   CAM_OBRIGATORIO      bit(1) not null,
   CAM_TIPO             smallint not null,
   WOR_ID               int not null,
   primary key (CAM_ID)
);

/*==============================================================*/
/* Table: DPR_DEPARTAMENTO                                      */
/*==============================================================*/
create table sisgestor.DPR_DEPARTAMENTO
(
   DPR_ID               int not null auto_increment,
   DPR_DATA_HORA_EXCLUSAO datetime,
   DPR_EMAIL            varchar(50),
   DPR_NOME             varchar(100) not null,
   DPR_SIGLA            char(10) not null,
   DPR_ID_SUPERIOR      int,
   primary key (DPR_ID)
);

/*==============================================================*/
/* Table: HUWR_USO_WORKFLOW                                     */
/*==============================================================*/
create table sisgestor.HUWR_USO_WORKFLOW
(
   HUWR_DATA_HORA       datetime not null,
   UWR_ID               int not null,
   UUR_ID               int not null,
   HUWR_ACAO            smallint not null,
   primary key (HUWR_DATA_HORA, UWR_ID)
);

/*==============================================================*/
/* Table: OPC_OPCAO_CAMPO                                       */
/*==============================================================*/
create table sisgestor.OPC_OPCAO_CAMPO
(
   OPC_ID               int not null auto_increment,
   OPC_DESCRICAO        char(20) not null,
   OPC_VALOR            int not null,
   CAM_ID               int not null,
   primary key (OPC_ID)
);

/*==============================================================*/
/* Table: PRM_PERMISSAO                                         */
/*==============================================================*/
create table sisgestor.PRM_PERMISSAO
(
   PRM_ID               int not null auto_increment,
   PRM_DESCRICAO        varchar(200) not null,
   primary key (PRM_ID)
);

/*==============================================================*/
/* Table: PRO_PROCESSO                                          */
/*==============================================================*/
create table sisgestor.PRO_PROCESSO
(
   PRO_ID               int not null auto_increment,
   PRO_DESCRICAO        varchar(200) not null,
   PRO_NOME             varchar(100) not null,
   PRO_LEFT             int,
   PRO_TOP              int,
   WOR_ID               int not null,
   primary key (PRO_ID)
);

/*==============================================================*/
/* Index: IX_DESCRICAO                                          */
/*==============================================================*/
create index IX_DESCRICAO on sisgestor.PRO_PROCESSO
(
   PRO_DESCRICAO
);

/*==============================================================*/
/* Index: IX_NOME                                               */
/*==============================================================*/
create index IX_NOME on sisgestor.PRO_PROCESSO
(
   PRO_NOME
);

/*==============================================================*/
/* Table: TAR_TAREFA                                            */
/*==============================================================*/
create table sisgestor.TAR_TAREFA
(
   TAR_ID               int not null auto_increment,
   TAR_DESCRICAO        varchar(200) not null,
   TAR_NOME             varchar(100) not null,
   TAR_LEFT             int,
   TAR_TOP              int,
   ATI_ID               int not null,
   UUR_ID               int,
   primary key (TAR_ID)
);

/*==============================================================*/
/* Index: IX_DESCRICAO                                          */
/*==============================================================*/
create index IX_DESCRICAO on sisgestor.TAR_TAREFA
(
   TAR_DESCRICAO
);

/*==============================================================*/
/* Index: IX_NOME                                               */
/*==============================================================*/
create index IX_NOME on sisgestor.TAR_TAREFA
(
   TAR_NOME
);

/*==============================================================*/
/* Table: TRA_TRANSACAO_ATIVIDADE                               */
/*==============================================================*/
create table sisgestor.TRA_TRANSACAO_ATIVIDADE
(
   TRA_ID               int not null auto_increment,
   ATI_ID_ANTERIOR      int not null,
   ATI_ID_POSTERIOR     int not null,
   primary key (TRA_ID)
);

/*==============================================================*/
/* Table: TRP_TRANSACAO_PROCESSO                                */
/*==============================================================*/
create table sisgestor.TRP_TRANSACAO_PROCESSO
(
   TRP_ID               int not null auto_increment,
   PRO_ID_ANTERIOR      int not null,
   PRO_ID_POSTERIOR     int not null,
   primary key (TRP_ID)
);

/*==============================================================*/
/* Table: TRT_TRANSACAO_TAREFA                                  */
/*==============================================================*/
create table sisgestor.TRT_TRANSACAO_TAREFA
(
   TRT_ID               int not null auto_increment,
   TAR_ID_ANTERIOR      int not null,
   TAR_ID_POSTERIOR     int not null,
   primary key (TRT_ID)
);

/*==============================================================*/
/* Table: UCA_USOWORKFLOW_CAMPO                                 */
/*==============================================================*/
create table sisgestor.UCA_USOWORKFLOW_CAMPO
(
   UCA_ID               int not null auto_increment,
   UCA_VALOR            varchar(100) not null,
   CAM_ID               int not null,
   UWR_ID               int not null,
   primary key (UCA_ID)
);

/*==============================================================*/
/* Table: UPM_USUARIO_PERMISSAO                                 */
/*==============================================================*/
create table sisgestor.UPM_USUARIO_PERMISSAO
(
   UUR_ID               int not null,
   PRM_ID               int not null,
   primary key (UUR_ID, PRM_ID)
);

/*==============================================================*/
/* Table: UUR_USUARIO                                           */
/*==============================================================*/
create table sisgestor.UUR_USUARIO
(
   UUR_ID               int not null auto_increment,
   UUR_CHEFE            bit(1) not null,
   UUR_DATA_HORA_EXCLUSAO datetime,
   UUR_EMAIL            varchar(50),
   UUR_LOGIN            char(15) not null,
   UUR_NOME             varchar(100) not null,
   UUR_SENHA            char(20) not null,
   DPR_ID               int not null,
   primary key (UUR_ID)
);

/*==============================================================*/
/* Table: UWR_USO_WORKFLOW                                      */
/*==============================================================*/
create table sisgestor.UWR_USO_WORKFLOW
(
   UWR_ID               int not null auto_increment,
   UWR_ANOTACAO         varchar(500),
   UWR_DATA_HORA_INICIO datetime,
   UWR_NUMERO           int not null,
   UWR_FINALIZADO       bit(1) not null,
   TAR_ID               int not null,
   WOR_ID               int not null,
   primary key (UWR_ID)
);

/*==============================================================*/
/* Table: WOR_WORKFLOW                                          */
/*==============================================================*/
create table sisgestor.WOR_WORKFLOW
(
   WOR_ID               int not null auto_increment,
   WOR_DESCRICAO        varchar(200) not null,
   WOR_NOME             varchar(100) not null,
   WOR_ATIVO            bit(1) not null,
   WOR_DATA_HORA_EXCLUSAO datetime,
   primary key (WOR_ID)
);

/*==============================================================*/
/* Index: IX_DESCRICAO                                          */
/*==============================================================*/
create index IX_DESCRICAO on sisgestor.WOR_WORKFLOW
(
   WOR_DESCRICAO
);

/*==============================================================*/
/* Index: IX_NOME                                               */
/*==============================================================*/
create index IX_NOME on sisgestor.WOR_WORKFLOW
(
   WOR_NOME
);

alter table sisgestor.ANX_ANEXO add constraint IR_UWR_ANX foreign key (UWR_ID)
      references sisgestor.UWR_USO_WORKFLOW (UWR_ID);

alter table sisgestor.ATI_ATIVIDADE add constraint IR_DPR_ATI foreign key (DRP_ID)
      references sisgestor.DPR_DEPARTAMENTO (DPR_ID);

alter table sisgestor.ATI_ATIVIDADE add constraint IR_PRO_ATI foreign key (PRO_ID)
      references sisgestor.PRO_PROCESSO (PRO_ID);

alter table sisgestor.CAM_CAMPO add constraint IR_WOR_CAM foreign key (WOR_ID)
      references sisgestor.WOR_WORKFLOW (WOR_ID);

alter table sisgestor.DPR_DEPARTAMENTO add constraint IR_DPR_DPR foreign key (DPR_ID_SUPERIOR)
      references sisgestor.DPR_DEPARTAMENTO (DPR_ID);

alter table sisgestor.HUWR_USO_WORKFLOW add constraint IR_UUR_HUWR foreign key (UUR_ID)
      references sisgestor.UUR_USUARIO (UUR_ID);

alter table sisgestor.HUWR_USO_WORKFLOW add constraint IR_UWR_HUWR foreign key (UWR_ID)
      references sisgestor.UWR_USO_WORKFLOW (UWR_ID);

alter table sisgestor.OPC_OPCAO_CAMPO add constraint IR_CAM_OPC foreign key (CAM_ID)
      references sisgestor.CAM_CAMPO (CAM_ID);

alter table sisgestor.PRO_PROCESSO add constraint IR_WOR_PRO foreign key (WOR_ID)
      references sisgestor.WOR_WORKFLOW (WOR_ID);

alter table sisgestor.TAR_TAREFA add constraint IR_ATI_TAR foreign key (ATI_ID)
      references sisgestor.ATI_ATIVIDADE (ATI_ID);

alter table sisgestor.TAR_TAREFA add constraint IR_UUR_TAR foreign key (UUR_ID)
      references sisgestor.UUR_USUARIO (UUR_ID);

alter table sisgestor.TRA_TRANSACAO_ATIVIDADE add constraint IR_ATI_TRA1 foreign key (ATI_ID_ANTERIOR)
      references sisgestor.ATI_ATIVIDADE (ATI_ID);

alter table sisgestor.TRA_TRANSACAO_ATIVIDADE add constraint IR_ATI_TRA2 foreign key (ATI_ID_POSTERIOR)
      references sisgestor.ATI_ATIVIDADE (ATI_ID);

alter table sisgestor.TRP_TRANSACAO_PROCESSO add constraint IR_PRO_TRP1 foreign key (PRO_ID_ANTERIOR)
      references sisgestor.PRO_PROCESSO (PRO_ID);

alter table sisgestor.TRP_TRANSACAO_PROCESSO add constraint IR_PRO_TRP2 foreign key (PRO_ID_POSTERIOR)
      references sisgestor.PRO_PROCESSO (PRO_ID);

alter table sisgestor.TRT_TRANSACAO_TAREFA add constraint IR_TAR_TRT1 foreign key (TAR_ID_ANTERIOR)
      references sisgestor.TAR_TAREFA (TAR_ID);

alter table sisgestor.TRT_TRANSACAO_TAREFA add constraint IR_TAR_TRT2 foreign key (TAR_ID_POSTERIOR)
      references sisgestor.TAR_TAREFA (TAR_ID);

alter table sisgestor.UCA_USOWORKFLOW_CAMPO add constraint IR_CAM_UCA foreign key (CAM_ID)
      references sisgestor.CAM_CAMPO (CAM_ID);

alter table sisgestor.UCA_USOWORKFLOW_CAMPO add constraint IR_UWR_UCA foreign key (UWR_ID)
      references sisgestor.UWR_USO_WORKFLOW (UWR_ID);

alter table sisgestor.UPM_USUARIO_PERMISSAO add constraint IR_PRM_UPM foreign key (PRM_ID)
      references sisgestor.PRM_PERMISSAO (PRM_ID);

alter table sisgestor.UPM_USUARIO_PERMISSAO add constraint IR_UUR_UPM foreign key (UUR_ID)
      references sisgestor.UUR_USUARIO (UUR_ID);

alter table sisgestor.UUR_USUARIO add constraint IR_DPR_UUR foreign key (DPR_ID)
      references sisgestor.DPR_DEPARTAMENTO (DPR_ID);

alter table sisgestor.UWR_USO_WORKFLOW add constraint IR_TAR_UWR foreign key (TAR_ID)
      references sisgestor.TAR_TAREFA (TAR_ID);

alter table sisgestor.UWR_USO_WORKFLOW add constraint IR_WOR_UWR foreign key (WOR_ID)
      references sisgestor.WOR_WORKFLOW (WOR_ID);

UPDATE mysql.user SET `Select_priv` = 'Y', `Insert_priv` = 'Y', `Update_priv` = 'Y', `Delete_priv` = 'Y', `Create_priv` = 'N', `Drop_priv` = 'N', `Reload_priv` = 'N', `Shutdown_priv` = 'N', `Process_priv` = 'N', `File_priv` = 'N', `Grant_priv` = 'N', `References_priv` = 'N', `Index_priv` = 'N', `Alter_priv` = 'N', `Show_db_priv` = 'N', `Super_priv` = 'N', `Create_tmp_table_priv` = 'N', `Lock_tables_priv` = 'Y', `Execute_priv` = 'N', `Repl_slave_priv` = 'N', `Repl_client_priv` = 'N', `Create_view_priv` = 'N', `Show_view_priv` = 'N', `Create_routine_priv` = 'N', `Alter_routine_priv` = 'N', `Create_user_priv` = 'N', `Event_priv` = 'N', `Trigger_priv` = 'N' WHERE Host = '%' AND User = 'sisgestor'