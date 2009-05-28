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

insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'admin@sisgestor.com', 'admin', 'Administrador', 'admin', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DIR'));
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
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 5 from uur_usuario uur where uur.uur_login like 'user%');
insert into upm_usuario_permissao (UUR_ID, PRM_ID) (select uur.uur_id, 6 from uur_usuario uur where uur.uur_login like 'user%');

insert into wor_workflow (WOR_DESCRICAO, WOR_NOME, WOR_ATIVO) values ('Implementa��o do n�vel G do MPS.Br', 'N�vel G MPS.Br', 0);
insert into wor_workflow (WOR_DESCRICAO, WOR_NOME, WOR_ATIVO) values ('Faz toda a contrata��o de novos colaboradores da empresa.', 'Contrata��o de colaboradores', 0);
insert into wor_workflow (WOR_DESCRICAO, WOR_NOME, WOR_ATIVO) values ('Descreve o fluxo para gerir uma mudan�a', 'Gest�o de Mudan�as', 0);

insert into cam_campo (CAM_ID, CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (2, null, 'Conhecimento', 0, 5, 2);
insert into cam_campo (CAM_ID, CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (3, 'Orienta��o sexual', 'Sexo', 0, 4, 2);
insert into cam_campo (CAM_ID, CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (4, 'Conhecimento do idioma', 'Idioma', 0, 3, 2);
insert into cam_campo (CAM_ID, CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (5, 'Sal�rio para a vaga, em reais', 'Sal�rio', 0, 5, 2);
insert into cam_campo (CAM_ID, CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (6, 'Data de ocorr�ncia', 'Data de ocorr�ncia', 0, 1, 3);
insert into cam_campo (CAM_ID, CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (7, 'Defeito diagnosticado', 'Defeito', 0, 5, 3);
insert into cam_campo (CAM_ID, CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (8, 'Sistema em que ocorreu o problema', 'Sistema de origem', 0, 5, 3);
insert into cam_campo (CAM_ID, CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (9, 'Solu��o adotada para corre��o do problema', 'Solu��o adotada', 0, 5, 3);
insert into cam_campo (CAM_ID, CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (11, 'Plataforma em que ocorreu o problema', 'Plataforma', 0, 4, 3);
insert into cam_campo (CAM_ID, CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (12, null, 'Corre��o imediata', 0, 4, 3);

insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (1, 'Feminino', 0, 3);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (2, 'Masculino', 1, 3);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (3, 'Espanhol', 0, 4);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (4, 'Franc�s', 1, 4);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (5, 'Ingl�s', 2, 4);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (6, 'Japon�s', 3, 4);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (7, 'Mandarim', 4, 4);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (14, 'AiX', 0, 11);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (15, 'Mainframe', 1, 11);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (16, 'Unix', 2, 11);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (17, 'Windows', 3, 11);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (18, 'N�o', 0, 12);
insert into opc_opcao_campo (OPC_ID, OPC_DESCRICAO, OPC_VALOR, CAM_ID) values (19, 'Sim', 1, 12);

insert into pro_processo (PRO_ID, PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values (1, 'Defini��o Inicial do Projeto', 'Fase 1', 0, 0, 1);
insert into pro_processo (PRO_ID, PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values (2, 'Defini��o de Requisitos', 'Fase 2', 100, 0, 1);
insert into pro_processo (PRO_ID, PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values (3, 'An�lise & Projeto', 'Fase 3', 200, 0, 1);
insert into pro_processo (PRO_ID, PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values (4, 'Implementa��o', 'Fase 4', 300, 0, 1);
insert into pro_processo (PRO_ID, PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values (5, 'Testes', 'Fase 5', 400, 0, 1);
insert into pro_processo (PRO_ID, PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values (6, 'Implanta��o', 'Fase 6', 500, 0, 1);
insert into pro_processo (PRO_ID, PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values (7, 'Processo de sele��o para novos colaboradores da organiza��o.', 'Processo Seletivo', null, null, 2);
insert into pro_processo (PRO_ID, PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values (8, 'Solicita uma mudan�a', 'Solicita��o de Mudan�a', null, null, 3);

insert into trp_transacao_processo (TRP_ID, PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values (1, 1, 2);
insert into trp_transacao_processo (TRP_ID, PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values (2, 3, 4);
insert into trp_transacao_processo (TRP_ID, PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values (3, 4, 5);
insert into trp_transacao_processo (TRP_ID, PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values (4, 5, 6);
insert into trp_transacao_processo (TRP_ID, PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values (5, 2, 3);

insert into ati_atividade (ATI_ID, ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values (1, 'Levantar o problema a ser resolvido de forma a identificar as caracter�sticas de alto n�vel do sistema estabelecendo o escopo do projeto.', 'Definir Escopo do Projeto', null, null, 6, 1);
insert into ati_atividade (ATI_ID, ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values (2, 'Avaliar junto ao cliente o escopo do projeto definido estabelecendo um acordo sobre o mesmo.', 'Obter Aprova��o do Escopo do Projeto', null, null, 6, 1);
insert into ati_atividade (ATI_ID, ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values (3, 'Elaborar uma vis�o preliminar de quais ser�o as funcionalidades do sistema para permitir uma estimativa inicial.', 'Elaborar Estimativa Preliminar do Projeto', null, null, 6, 1);
insert into ati_atividade (ATI_ID, ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values (4, 'Realiza a aprova��o dos poss�veis selecionados', 'Aprovar Candidatos', 333, 134, 13, 7);
insert into ati_atividade (ATI_ID, ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values (5, 'Realiza o primeiro pagamento dos aprovados', 'Realizar Pagamento', 566, 118, 12, 7);
insert into ati_atividade (ATI_ID, ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values (6, 'Divulga pelos meios convenientes a vaga dispon�vel', 'Divulgar Vaga', 104, 145, 15, 7);
insert into ati_atividade (ATI_ID, ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values (7, 'Efetuar corre��o do problema diagnosticado', 'Corrigir um defeito', null, null, 5, 8);

insert into tra_transacao_atividade (TRA_ID, ATI_ID_ANTERIOR, ATI_ID_POSTERIOR) values (2, 6, 4);
insert into tra_transacao_atividade (TRA_ID, ATI_ID_ANTERIOR, ATI_ID_POSTERIOR) values (3, 4, 5);

insert into tar_tarefa (TAR_ID, TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values (1, 'Seleciona os curr�culos de acordo com a necessidade', 'Selecionar Curr�culos', 117, 101, 4, 21);
insert into tar_tarefa (TAR_ID, TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values (2, 'Realiza o teste de conhecimentos', 'Realizar Teste Escrito', 306, 106, 4, 21);
insert into tar_tarefa (TAR_ID, TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values (3, 'Realizar din�mica dos selecionados', 'Din�mica em Grupo', 510, 108, 4, 22);
insert into tar_tarefa (TAR_ID, TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values (4, 'Entrevista dos selecionados', 'Entrevista', 319, 276, 4, 20);
insert into tar_tarefa (TAR_ID, TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values (5, 'Elaborar material para divulga��o', 'Preparar Material de Divulga��o', 173, 114, 6, 24);
insert into tar_tarefa (TAR_ID, TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values (6, 'Realiza a divulga��o da vaga pelos meios dispon�veis', 'Realizar Divulga��o', 358, 105, 6, 24);
insert into tar_tarefa (TAR_ID, TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values (7, 'Realiza o dep�sito banc�rio, parte do primeiro sal�rio, para incentivo', 'Depositar', null, null, 5, 19);

insert into trt_transacao_tarefa (TRT_ID, TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values (1, 1, 2);
insert into trt_transacao_tarefa (TRT_ID, TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values (2, 2, 3);
insert into trt_transacao_tarefa (TRT_ID, TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values (3, 3, 4);
insert into trt_transacao_tarefa (TRT_ID, TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values (4, 2, 4);
insert into trt_transacao_tarefa (TRT_ID, TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values (5, 1, 4);
insert into trt_transacao_tarefa (TRT_ID, TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values (6, 5, 6);

