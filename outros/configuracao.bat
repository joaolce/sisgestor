@ECHO OFF

echo Iniciando atualizacoes. Aguarde...

IF EXIST %JBOSS_HOME%\server\default\conf\login-config.xml (
	del "%JBOSS_HOME%"\server\default\conf\login-config.xml
)
	
IF EXIST %JBOSS_HOME%\server\default\conf\sisgestor.keystore (	
	del "%JBOSS_HOME%"\server\default\conf\sisgestor.keystore
)

IF EXIST %JBOSS_HOME%\server\default\deploy\sisgestor-ds.xml (
	del "%JBOSS_HOME%"\server\default\deploy\sisgestor-ds.xml
)

IF EXIST %JBOSS_HOME%\server\default\deploy\mail-service.xml (
	del "%JBOSS_HOME%"\server\default\deploy\mail-service.xml
)
	
IF EXIST %JBOSS_HOME%\server\default\deploy\jboss-web.deployer\server.xml (	
	del "%JBOSS_HOME%"\server\default\deploy\jboss-web.deployer\server.xml
)


IF EXIST %JBOSS_HOME%\server\default\lib\hibernate-annotations.jar (
	del "%JBOSS_HOME%"\server\default\lib\hibernate-annotations.jar
)

IF EXIST %JBOSS_HOME%\server\default\lib\hibernate-commons-annotations-3.1.0.GA.jar (
	del "%JBOSS_HOME%"\server\default\lib\hibernate-commons-annotations-3.1.0.GA.jar
)

IF EXIST %JBOSS_HOME%\server\default\lib\hibernate3.jar (
	del "%JBOSS_HOME%"\server\default\lib\hibernate3.jar
)

IF EXIST %JBOSS_HOME%\server\default\lib\jcl-over-slf4j-1.5.6.jar (
	del "%JBOSS_HOME%"\server\default\lib\jcl-over-slf4j-1.5.6.jar
)

IF EXIST %JBOSS_HOME%\server\default\lib\mysql.jar (
	del "%JBOSS_HOME%"\server\default\lib\mysql.jar
)

IF EXIST %JBOSS_HOME%\server\default\lib\slf4j-api-1.5.6.jar (
	del "%JBOSS_HOME%\server\default\lib\slf4j-api-1.5.6.jar
)

IF EXIST %JBOSS_HOME%\server\default\lib\slf4j-simple-1.5.6.jar (
	del "%JBOSS_HOME%"\server\default\lib\slf4j-simple-1.5.6.jar
)

echo Copiando arquivos de configuracoes

copy configuracao\jboss\login-config.xml "%JBOSS_HOME%"\server\default\conf
copy configuracao\jboss\sisgestor.keystore "%JBOSS_HOME%"\server\default\conf
copy configuracao\jboss\mail-service.xml "%JBOSS_HOME%"\server\default\deploy
copy configuracao\jboss\sisgestor-ds.xml "%JBOSS_HOME%"\server\default\deploy
copy configuracao\jboss\server.xml "%JBOSS_HOME%"\server\default\deploy\jboss-web.deployer

echo Copiando libs necessarias

copy libs\hibernate-annotations.jar "%JBOSS_HOME%"\server\default\lib
copy libs\hibernate-commons-annotations-3.1.0.GA.jar "%JBOSS_HOME%"\server\default\lib
copy libs\hibernate3.jar "%JBOSS_HOME%"\server\default\lib
copy libs\jcl-over-slf4j-1.5.6.jar "%JBOSS_HOME%"\server\default\lib
copy libs\mysql.jar "%JBOSS_HOME%"\server\default\lib
copy libs\slf4j-api-1.5.6.jar "%JBOSS_HOME%"\server\default\lib
copy libs\slf4j-simple-1.5.6.jar "%JBOSS_HOME%"\server\default\lib


echo Atualizacoes realizadas com sucesso. 

pause

