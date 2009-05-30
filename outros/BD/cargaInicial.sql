use sisgestor;

insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('diretoria@sisgestor.com', 'Diretoria', 'DIR', null);
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('dti@sisgestor.com', 'Departamento de TI', 'DTI', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DIR'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('drh@sisgestor.com', 'Departamento de Recursos Humanos', 'DRH', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DIR'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('dds@sisgestor.com', 'Departamento de Desenvolvimento de Software', 'DDS', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DTI'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('die@sisgestor.com', 'Departamento de Infra-Estrutura', 'DIE', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DTI'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('nrq@sisgestor.com', 'N�cleo de Requisitos', 'NRQ', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DDS'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('nim@sisgestor.com', 'N�cleo de Implementa��o', 'NIM', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DDS'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('nts@sisgestor.com', 'N�cleo de Testes', 'NTS', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DDS'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('npr@sisgestor.com', 'N�cleo de Projetos', 'NPR', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DDS'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('dfi@sisgestor.com', 'Departamento de Finan�as', 'DFI', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DRH'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('dpe@sisgestor.com', 'Departamento Pessoal', 'DPE', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DRH'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('dcm@sisgestor.com', 'Departamento de Comunica��o', 'DCM', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DRH'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('nmk@sisgestor.com', 'N�cleo de Marketing', 'NMK', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DCM'));

insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'admin@sisgestor.com', 'admin', 'Administrador', 'admin', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DIR'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestorti@sisgestor.com', 'gestorti', 'Gestor DTI', 'gestorti', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DTI'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'userdti@sisgestor.com', 'userdti', 'Usu�rio DTI', 'userti', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DTI'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestordds@sisgestor.com', 'gestordds', 'Gestor DDS', 'gestordds', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DDS'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'userdds@sisgestor.com', 'userdds', 'Usu�rio DDS', 'userdds', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DDS'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestordie@sisgestor.com', 'gestordie', 'Gestor DIE', 'gestordie', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DIE'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'userdie@sisgestor.com', 'userdie', 'Usu�rio DIE', 'userdie', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DIE'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestornrq@sisgestor.com', 'gestornrq', 'Gestor NRQ', 'gestornrq', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NRQ'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'usernrq@sisgestor.com', 'usernrq', 'Usu�rio NRQ', 'usernrq', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NRQ'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestornim@sisgestor.com', 'gestornim', 'Gestor NIM', 'gestornim', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NIM'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'usernim@sisgestor.com', 'usernim', 'Usu�rio NIM', 'usernim', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NIM'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestornts@sisgestor.com', 'gestornts', 'Gestor NTS', 'gestornts', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NTS'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'usernts@sisgestor.com', 'usernts', 'Usu�rio NTS', 'usernts', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NTS'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestornpr@sisgestor.com', 'gestornpr', 'Gestor NPR', 'gestornpr', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NPR'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'usernpr@sisgestor.com', 'usernpr', 'Usu�rio NPR', 'usernpr', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NPR'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestordrh@sisgestor.com', 'gestordrh', 'Gestor DRH', 'gestordrh', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DRH'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'userdrh@sisgestor.com', 'userdrh', 'Usu�rio DRH', 'userdrh', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DRH'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestordfi@sisgestor.com', 'gestordfi', 'Gestor DFI', 'gestordfi', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DFI'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'userdfi@sisgestor.com', 'userdfi', 'Usu�rio DFI', 'userdfi', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DFI'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestordpe@sisgestor.com', 'gestordpe', 'Gestor DPE', 'gestordpe', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DPE'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'user1dpe@sisgestor.com', 'user1dpe', 'Usu�rio 1 DPE', 'user1dpe', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DPE'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'user2dpe@sisgestor.com', 'user2dpe', 'Usu�rio 2 DPE', 'user2dpe', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DPE'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestornmk@sisgestor.com', 'gestornmk', 'Gestor NMK', 'gestornmk', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NMK'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'usernmk@sisgestor.com', 'usernmk', 'Usu�rio NMK', 'usernmk', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NMK'));

insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (1, 'Acesso Min�mo ao SisGestor');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (2, 'Manter Departamento');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (3, 'Manter Usu�rio');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (4, 'Manter Workflow');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (5, 'Utilizar Registro de Workflow');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (6, 'Manter Anexos de Registro de Workflow');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (7, 'Extrair Relat�rios');

insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 1 from uur_usuario uur where uur.uur_login = 'admin');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 2 from uur_usuario uur where uur.uur_login = 'admin');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 3 from uur_usuario uur where uur.uur_login = 'admin');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 4 from uur_usuario uur where uur.uur_login = 'admin');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 5 from uur_usuario uur where uur.uur_login = 'admin');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 6 from uur_usuario uur where uur.uur_login = 'admin');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 1 from uur_usuario uur where uur.uur_login like 'gestor%');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 4 from uur_usuario uur where uur.uur_login like 'gestor%');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 5 from uur_usuario uur where uur.uur_login like 'gestor%');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 6 from uur_usuario uur where uur.uur_login like 'gestor%');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 1 from uur_usuario uur where uur.uur_login like 'user%');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 5 from uur_usuario uur where uur.uur_login like 'user%' and uur.uur_login != 'userdie');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 6 from uur_usuario uur where uur.uur_login like 'user%' and uur.uur_login != 'userdie');

insert into wor_workflow (WOR_DESCRICAO, WOR_NOME, WOR_ATIVO) values ('Implementa��o do n�vel G do MPS.Br', 'N�vel G MPS.Br', 0);
insert into wor_workflow (WOR_DESCRICAO, WOR_NOME, WOR_ATIVO) values ('Faz toda a contrata��o de novos colaboradores da empresa.', 'Contrata��o de colaboradores', 1);
insert into wor_workflow (WOR_DESCRICAO, WOR_NOME, WOR_ATIVO) values ('Descreve o fluxo para gerir uma mudan�a', 'Gest�o de Mudan�as', 0);

insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (null, 'Conhecimento', 1, 5, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Contrata��o de colaboradores'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Orienta��o sexual', 'Sexo', 0, 4, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Contrata��o de colaboradores'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Conhecimento do idioma', 'Idioma', 0, 3, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Contrata��o de colaboradores'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Sal�rio para a vaga, em reais', 'Sal�rio', 1, 5, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Contrata��o de colaboradores'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Data de ocorr�ncia', 'Data de ocorr�ncia', 0, 1, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gest�o de Mudan�as'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Defeito diagnosticado', 'Defeito', 1, 5, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gest�o de Mudan�as'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Sistema em que ocorreu o problema', 'Sistema', 0, 5, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gest�o de Mudan�as'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Solu��o adotada para corre��o do problema', 'Solu��o adotada', 0, 5, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gest�o de Mudan�as'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Plataforma em que ocorreu o problema', 'Plataforma', 0, 4, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gest�o de Mudan�as'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (null, 'Corre��o imediata', 0, 4, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gest�o de Mudan�as'));

insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Feminino', 0, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Sexo'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Masculino', 1, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Sexo'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Espanhol', 0, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Idioma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Franc�s', 1, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Idioma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Ingl�s', 2, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Idioma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Japon�s', 3, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Idioma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Mandarim', 4, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Idioma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('AIX', 0, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Plataforma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Mainframe', 1, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Plataforma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Unix', 2, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Plataforma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Windows', 3, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Plataforma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('N�o', 0, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Corre��o imediata'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Sim', 1, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Corre��o imediata'));

insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Defini��o Inicial do Projeto', 'Fase 1', 0, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'N�vel G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Defini��o de Requisitos', 'Fase 2', 100, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'N�vel G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('An�lise & Projeto', 'Fase 3', 200, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'N�vel G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Implementa��o', 'Fase 4', 300, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'N�vel G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Testes', 'Fase 5', 400, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'N�vel G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Implanta��o', 'Fase 6', 500, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'N�vel G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Processo de sele��o para novos colaboradores da organiza��o.', 'Processo Seletivo', null, null, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Contrata��o de colaboradores'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Solicita uma mudan�a', 'Solicita��o de Mudan�a', null, null, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gest�o de Mudan�as'));

insert into trp_transacao_processo (PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values ((select pro.pro_id from pro_processo pro where pro_nome = 'Fase 1'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 2'));
insert into trp_transacao_processo (PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values ((select pro.pro_id from pro_processo pro where pro_nome = 'Fase 2'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 3'));
insert into trp_transacao_processo (PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values ((select pro.pro_id from pro_processo pro where pro_nome = 'Fase 3'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 4'));
insert into trp_transacao_processo (PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values ((select pro.pro_id from pro_processo pro where pro_nome = 'Fase 4'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 5'));
insert into trp_transacao_processo (PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values ((select pro.pro_id from pro_processo pro where pro_nome = 'Fase 5'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 6'));

insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Levantar o problema a ser resolvido de forma a identificar as caracter�sticas de alto n�vel do sistema estabelecendo o escopo do projeto.', 'Definir Escopo do Projeto', null, null, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'NRQ'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 1'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Avaliar junto ao cliente o escopo do projeto definido estabelecendo um acordo sobre o mesmo.', 'Obter Aprova��o do Escopo do Projeto', null, null, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'NRQ'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 1'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Elaborar uma vis�o preliminar de quais ser�o as funcionalidades do sistema para permitir uma estimativa inicial.', 'Elaborar Estimativa Preliminar do Projeto', null, null, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'NRQ'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 1'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Realiza a aprova��o dos poss�veis selecionados', 'Aprovar Candidatos', 333, 134, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'DPE'), (select pro.pro_id from pro_processo pro where pro_nome = 'Processo Seletivo'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Realiza o primeiro pagamento dos aprovados', 'Realizar Pagamento', 566, 118, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'DFI'), (select pro.pro_id from pro_processo pro where pro_nome = 'Processo Seletivo'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Divulga pelos meios convenientes a vaga dispon�vel', 'Divulgar Vaga', 104, 145, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'DCM'), (select pro.pro_id from pro_processo pro where pro_nome = 'Processo Seletivo'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Efetuar corre��o do problema diagnosticado', 'Corrigir um defeito', null, null, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'DIE'), (select pro.pro_id from pro_processo pro where pro_nome = 'Solicita��o de Mudan�a'));

insert into tra_transacao_atividade (ATI_ID_ANTERIOR, ATI_ID_POSTERIOR) values ((select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Divulgar Vaga'), (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'));
insert into tra_transacao_atividade (ATI_ID_ANTERIOR, ATI_ID_POSTERIOR) values ((select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'), (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Realizar Pagamento'));

insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Seleciona os curr�culos de acordo com a necessidade', 'Selecionar Curr�culos', 117, 101, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'user1dpe'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Realiza o teste de conhecimentos', 'Realizar Teste Escrito', 306, 106, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'user1dpe'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Realizar din�mica dos selecionados', 'Din�mica em Grupo', 510, 108, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'user2dpe'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Entrevista dos selecionados', 'Entrevista', 319, 276, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'gestordpe'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Elaborar material para divulga��o', 'Preparar Material de Divulga��o', 173, 114, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Divulgar Vaga'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'usernmk'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Realiza a divulga��o da vaga pelos meios dispon�veis', 'Realizar Divulga��o', 358, 105, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Divulgar Vaga'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'usernmk'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Realiza o dep�sito banc�rio, parte do primeiro sal�rio, para incentivo', 'Depositar', null, null, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Realizar Pagamento'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'userdfi'));

insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Selecionar Curr�culos'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Realizar Teste Escrito'));
insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Realizar Teste Escrito'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Din�mica em Grupo'));
insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Selecionar Curr�culos'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Entrevista'));
insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Realizar Teste Escrito'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Entrevista'));
insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Din�mica em Grupo'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Entrevista'));
insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Preparar Material de Divulga��o'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Realizar Divulga��o'));
