# HeidiSQL Dump 
#
# --------------------------------------------------------
# Host:                 127.0.0.1
# Database:             sisgestor
# Server version:       5.1.22-rc-community
# Server OS:            Win32
# Target-Compatibility: Standard ANSI SQL
# HeidiSQL version:     3.2 Revision: 1129
# --------------------------------------------------------

/*!40100 SET CHARACTER SET latin1;*/
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ANSI';*/
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;*/


#
# Dumping data for table 'dpr_departamento'
#

LOCK TABLES "dpr_departamento" WRITE;
/*!40000 ALTER TABLE "dpr_departamento" DISABLE KEYS;*/
INSERT INTO "dpr_departamento" ("DPR_ID", "DPR_EMAIL", "DPR_NOME", "DPR_SIGLA", "DPR_ID_SUPERIOR") VALUES
	(1,'email@mail.com','Departamento','Departamento',NULL);
/*!40000 ALTER TABLE "dpr_departamento" ENABLE KEYS;*/
UNLOCK TABLES;


#
# Dumping data for table 'prm_permissao'
#

LOCK TABLES "prm_permissao" WRITE;
/*!40000 ALTER TABLE "prm_permissao" DISABLE KEYS;*/
INSERT INTO "prm_permissao" ("PRM_ID", "PRM_DESCRICAO") VALUES
	(1,'Acesso mínimo ao SisGestor');
INSERT INTO "prm_permissao" ("PRM_ID", "PRM_DESCRICAO") VALUES
	(2,'Manter Departamento');
INSERT INTO "prm_permissao" ("PRM_ID", "PRM_DESCRICAO") VALUES
	(3,'Manter Usuário');
INSERT INTO "prm_permissao" ("PRM_ID", "PRM_DESCRICAO") VALUES
	(4,'Manter Workflow');
/*!40000 ALTER TABLE "prm_permissao" ENABLE KEYS;*/
UNLOCK TABLES;


#
# Dumping data for table 'upm_usuario_permissao'
#

LOCK TABLES "upm_usuario_permissao" WRITE;
/*!40000 ALTER TABLE "upm_usuario_permissao" DISABLE KEYS;*/
INSERT INTO "upm_usuario_permissao" ("UUR_ID", "PRM_ID") VALUES
	(1,2);
INSERT INTO "upm_usuario_permissao" ("UUR_ID", "PRM_ID") VALUES
	(1,1);
INSERT INTO "upm_usuario_permissao" ("UUR_ID", "PRM_ID") VALUES
	(1,3);
	INSERT INTO "upm_usuario_permissao" ("UUR_ID", "PRM_ID") VALUES
	(1,4);
/*!40000 ALTER TABLE "upm_usuario_permissao" ENABLE KEYS;*/
UNLOCK TABLES;


#
# Dumping data for table 'uur_usuario'
#

LOCK TABLES "uur_usuario" WRITE;
/*!40000 ALTER TABLE "uur_usuario" DISABLE KEYS;*/
INSERT INTO "uur_usuario" ("UUR_ID", "UUR_CHEFE", "UUR_EMAIL", "UUR_LOGIN", "UUR_NOME", "UUR_SENHA", "DPR_ID") VALUES
	(1,'0','','admin','1','1',1);
/*!40000 ALTER TABLE "uur_usuario" ENABLE KEYS;*/
UNLOCK TABLES;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE;*/
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;*/
