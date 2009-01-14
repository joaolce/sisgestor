
/*!40100 SET CHARACTER SET latin1;*/
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ANSI';*/
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;*/


#
# Database structure for database 'sisgestor'
#

DROP DATABASE /*!32312 IF EXISTS*/ "sisgestor";

CREATE DATABASE "sisgestor";

USE "sisgestor";


#
# Table structure for table 'dpr_departamento'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "dpr_departamento" (
  "DPR_ID" int(11) NOT NULL AUTO_INCREMENT,
  "DPR_NOME" varchar(50) NOT NULL,
  "DPR_EMAIL" varchar(30) DEFAULT NULL,
  "DPR_SIGLA" char(10) NOT NULL,
  "DPR_ID_SUPERIOR" int(11) DEFAULT NULL,
  PRIMARY KEY ("DPR_ID"),
  UNIQUE KEY "DPR_SIGLA" ("DPR_SIGLA"),
  KEY "IR_DPR_DPR" ("DPR_ID_SUPERIOR"),
  CONSTRAINT "IR_DPR_DPR" FOREIGN KEY ("DPR_ID_SUPERIOR") REFERENCES "dpr_departamento" ("DPR_ID")
) AUTO_INCREMENT=2 /*!40100 DEFAULT CHARSET=latin1*/;



#
# Table structure for table 'prm_permissao'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "prm_permissao" (
  "PRM_ID" int(11) NOT NULL AUTO_INCREMENT,
  "PRM_DESCRICAO" varchar(30) NOT NULL,
  PRIMARY KEY ("PRM_ID")
) AUTO_INCREMENT=2 /*!40100 DEFAULT CHARSET=latin1*/;



#
# Table structure for table 'upm_usuario_permissao'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "upm_usuario_permissao" (
  "UUR_ID" int(11) NOT NULL,
  "PRM_ID" int(11) NOT NULL,
  KEY "FK7972EB89EA570FD8" ("PRM_ID"),
  KEY "FK7972EB897011A178" ("UUR_ID"),
  CONSTRAINT "FK7972EB897011A178" FOREIGN KEY ("UUR_ID") REFERENCES "uur_usuario" ("UUR_ID"),
  CONSTRAINT "FK7972EB89EA570FD8" FOREIGN KEY ("PRM_ID") REFERENCES "prm_permissao" ("PRM_ID")
) /*!40100 DEFAULT CHARSET=latin1*/;



#
# Table structure for table 'uur_usuario'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "uur_usuario" (
  "UUR_ID" int(11) NOT NULL AUTO_INCREMENT,
  "UUR_EMAIL" varchar(40) DEFAULT NULL, 	
  "UUR_LOGIN" char(15) NOT NULL,
  "UUR_NOME" varchar(150) NOT NULL,
  "UUR_SENHA" varchar(255) NOT NULL,
  "DPR_ID" int(11) NOT NULL,
  PRIMARY KEY ("UUR_ID"),
  UNIQUE KEY "UUR_LOGIN" ("UUR_LOGIN"),
  KEY "IR_DPR_UUR" ("DPR_ID"),
  CONSTRAINT "IR_DPR_UUR" FOREIGN KEY ("DPR_ID") REFERENCES "dpr_departamento" ("DPR_ID")
) AUTO_INCREMENT=2 /*!40100 DEFAULT CHARSET=latin1*/;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE;*/
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;*/


INSERT INTO `dpr_departamento` (`DPR_ID`,`DPR_NOME`,`DPR_EMAIL`,`DPR_SIGLA`,`DPR_ID_SUPERIOR`) 
	VALUES (1,'Departamento','email@mail.com','DP',NULL);

INSERT INTO `uur_usuario` (`UUR_ID`,`UUR_EMAIL`,`UUR_LOGIN`,`UUR_NOME`,`UUR_SENHA`,`DPR_ID`) 
	VALUES (1,'email@email.com','admin','Administrador','admin',1);

INSERT INTO `prm_permissao` (`PRM_ID`,`PRM_DESCRICAO`) 
	VALUES (1,'1');

INSERT INTO `upm_usuario_permissao` (`UUR_ID`,`PRM_ID`) 
	VALUES (1,1);
