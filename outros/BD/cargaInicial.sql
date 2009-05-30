use sisgestor;

insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('diretoria@sisgestor.com', 'Diretoria', 'DIR', null);
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('dti@sisgestor.com', 'Departamento de TI', 'DTI', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DIR'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('drh@sisgestor.com', 'Departamento de Recursos Humanos', 'DRH', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DIR'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('dds@sisgestor.com', 'Departamento de Desenvolvimento de Software', 'DDS', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DTI'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('die@sisgestor.com', 'Departamento de Infra-Estrutura', 'DIE', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DTI'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('nrq@sisgestor.com', 'Núcleo de Requisitos', 'NRQ', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DDS'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('nim@sisgestor.com', 'Núcleo de Implementação', 'NIM', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DDS'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('nts@sisgestor.com', 'Núcleo de Testes', 'NTS', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DDS'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('npr@sisgestor.com', 'Núcleo de Projetos', 'NPR', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DDS'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('dfi@sisgestor.com', 'Departamento de Finanças', 'DFI', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DRH'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('dpe@sisgestor.com', 'Departamento Pessoal', 'DPE', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DRH'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('dcm@sisgestor.com', 'Departamento de Comunicação', 'DCM', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DRH'));
insert into dpr_departamento (DPR_EMAIL, DPR_NOME, DPR_SIGLA, DPR_ID_SUPERIOR) values ('nmk@sisgestor.com', 'Núcleo de Marketing', 'NMK', (select dpr1.DPR_ID from dpr_departamento dpr1 where dpr1.DPR_SIGLA = 'DCM'));

insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'admin@sisgestor.com', 'admin', 'Administrador', 'admin', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DIR'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestorti@sisgestor.com', 'gestorti', 'Gestor DTI', 'gestorti', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DTI'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'userdti@sisgestor.com', 'userdti', 'Usuário DTI', 'userti', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DTI'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestordds@sisgestor.com', 'gestordds', 'Gestor DDS', 'gestordds', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DDS'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'userdds@sisgestor.com', 'userdds', 'Usuário DDS', 'userdds', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DDS'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestordie@sisgestor.com', 'gestordie', 'Gestor DIE', 'gestordie', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DIE'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'userdie@sisgestor.com', 'userdie', 'Usuário DIE', 'userdie', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DIE'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestornrq@sisgestor.com', 'gestornrq', 'Gestor NRQ', 'gestornrq', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NRQ'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'usernrq@sisgestor.com', 'usernrq', 'Usuário NRQ', 'usernrq', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NRQ'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestornim@sisgestor.com', 'gestornim', 'Gestor NIM', 'gestornim', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NIM'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'usernim@sisgestor.com', 'usernim', 'Usuário NIM', 'usernim', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NIM'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestornts@sisgestor.com', 'gestornts', 'Gestor NTS', 'gestornts', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NTS'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'usernts@sisgestor.com', 'usernts', 'Usuário NTS', 'usernts', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NTS'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestornpr@sisgestor.com', 'gestornpr', 'Gestor NPR', 'gestornpr', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NPR'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'usernpr@sisgestor.com', 'usernpr', 'Usuário NPR', 'usernpr', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NPR'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestordrh@sisgestor.com', 'gestordrh', 'Gestor DRH', 'gestordrh', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DRH'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'userdrh@sisgestor.com', 'userdrh', 'Usuário DRH', 'userdrh', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DRH'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestordfi@sisgestor.com', 'gestordfi', 'Gestor DFI', 'gestordfi', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DFI'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'userdfi@sisgestor.com', 'userdfi', 'Usuário DFI', 'userdfi', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DFI'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestordpe@sisgestor.com', 'gestordpe', 'Gestor DPE', 'gestordpe', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DPE'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'user1dpe@sisgestor.com', 'user1dpe', 'Usuário 1 DPE', 'user1dpe', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DPE'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'user2dpe@sisgestor.com', 'user2dpe', 'Usuário 2 DPE', 'user2dpe', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'DPE'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (1, 'gestornmk@sisgestor.com', 'gestornmk', 'Gestor NMK', 'gestornmk', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NMK'));
insert into uur_usuario (UUR_CHEFE, UUR_EMAIL, UUR_LOGIN, UUR_NOME, UUR_SENHA, DPR_ID) values (0, 'usernmk@sisgestor.com', 'usernmk', 'Usuário NMK', 'usernmk', (select dpr.DPR_ID from dpr_departamento dpr where dpr.DPR_SIGLA = 'NMK'));

insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (1, 'Acesso Minímo ao SisGestor');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (2, 'Manter Departamento');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (3, 'Manter Usuário');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (4, 'Manter Workflow');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (5, 'Utilizar Registro de Workflow');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (6, 'Manter Anexos de Registro de Workflow');
insert into prm_permissao (PRM_ID, PRM_DESCRICAO) values (7, 'Extrair Relatórios');

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

insert into wor_workflow (WOR_DESCRICAO, WOR_NOME, WOR_ATIVO) values ('Implementação do nível G do MPS.Br', 'Nível G MPS.Br', 0);
insert into wor_workflow (WOR_DESCRICAO, WOR_NOME, WOR_ATIVO) values ('Faz toda a contratação de novos colaboradores da empresa.', 'Contratação de colaboradores', 1);
insert into wor_workflow (WOR_DESCRICAO, WOR_NOME, WOR_ATIVO) values ('Descreve o fluxo para gerir uma mudança', 'Gestão de Mudanças', 0);

insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (null, 'Conhecimento', 1, 5, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Contratação de colaboradores'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Orientação sexual', 'Sexo', 0, 4, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Contratação de colaboradores'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Conhecimento do idioma', 'Idioma', 0, 3, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Contratação de colaboradores'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Salário para a vaga, em reais', 'Salário', 1, 5, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Contratação de colaboradores'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Data de ocorrência', 'Data de ocorrência', 0, 1, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gestão de Mudanças'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Defeito diagnosticado', 'Defeito', 1, 5, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gestão de Mudanças'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Sistema em que ocorreu o problema', 'Sistema', 0, 5, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gestão de Mudanças'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Solução adotada para correção do problema', 'Solução adotada', 0, 5, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gestão de Mudanças'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values ('Plataforma em que ocorreu o problema', 'Plataforma', 0, 4, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gestão de Mudanças'));
insert into cam_campo (CAM_DESCRICAO, CAM_NOME, CAM_OBRIGATORIO, CAM_TIPO, WOR_ID) values (null, 'Correção imediata', 0, 4, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gestão de Mudanças'));

insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Feminino', 0, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Sexo'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Masculino', 1, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Sexo'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Espanhol', 0, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Idioma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Francês', 1, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Idioma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Inglês', 2, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Idioma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Japonês', 3, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Idioma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Mandarim', 4, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Idioma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('AIX', 0, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Plataforma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Mainframe', 1, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Plataforma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Unix', 2, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Plataforma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Windows', 3, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Plataforma'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Não', 0, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Correção imediata'));
insert into opc_opcao_campo (OPC_DESCRICAO, OPC_VALOR, CAM_ID) values ('Sim', 1, (select cam.cam_id from cam_campo cam where cam.cam_nome = 'Correção imediata'));

insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Definição Inicial do Projeto', 'Fase 1', 0, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Nível G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Definição de Requisitos', 'Fase 2', 100, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Nível G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Análise & Projeto', 'Fase 3', 200, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Nível G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Implementação', 'Fase 4', 300, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Nível G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Testes', 'Fase 5', 400, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Nível G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Implantação', 'Fase 6', 500, 0, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Nível G MPS.Br'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Processo de seleção para novos colaboradores da organização.', 'Processo Seletivo', null, null, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Contratação de colaboradores'));
insert into pro_processo (PRO_DESCRICAO, PRO_NOME, PRO_LEFT, PRO_TOP, WOR_ID) values ('Solicita uma mudança', 'Solicitação de Mudança', null, null, (select wor.WOR_ID from wor_workflow wor where wor.wor_nome = 'Gestão de Mudanças'));

insert into trp_transacao_processo (PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values ((select pro.pro_id from pro_processo pro where pro_nome = 'Fase 1'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 2'));
insert into trp_transacao_processo (PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values ((select pro.pro_id from pro_processo pro where pro_nome = 'Fase 2'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 3'));
insert into trp_transacao_processo (PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values ((select pro.pro_id from pro_processo pro where pro_nome = 'Fase 3'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 4'));
insert into trp_transacao_processo (PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values ((select pro.pro_id from pro_processo pro where pro_nome = 'Fase 4'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 5'));
insert into trp_transacao_processo (PRO_ID_ANTERIOR, PRO_ID_POSTERIOR) values ((select pro.pro_id from pro_processo pro where pro_nome = 'Fase 5'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 6'));

insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Levantar o problema a ser resolvido de forma a identificar as características de alto nível do sistema estabelecendo o escopo do projeto.', 'Definir Escopo do Projeto', null, null, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'NRQ'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 1'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Avaliar junto ao cliente o escopo do projeto definido estabelecendo um acordo sobre o mesmo.', 'Obter Aprovação do Escopo do Projeto', null, null, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'NRQ'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 1'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Elaborar uma visão preliminar de quais serão as funcionalidades do sistema para permitir uma estimativa inicial.', 'Elaborar Estimativa Preliminar do Projeto', null, null, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'NRQ'), (select pro.pro_id from pro_processo pro where pro_nome = 'Fase 1'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Realiza a aprovação dos possíveis selecionados', 'Aprovar Candidatos', 333, 134, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'DPE'), (select pro.pro_id from pro_processo pro where pro_nome = 'Processo Seletivo'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Realiza o primeiro pagamento dos aprovados', 'Realizar Pagamento', 566, 118, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'DFI'), (select pro.pro_id from pro_processo pro where pro_nome = 'Processo Seletivo'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Divulga pelos meios convenientes a vaga disponível', 'Divulgar Vaga', 104, 145, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'DCM'), (select pro.pro_id from pro_processo pro where pro_nome = 'Processo Seletivo'));
insert into ati_atividade (ATI_DESCRICAO, ATI_NOME, ATI_LEFT, ATI_TOP, DRP_ID, PRO_ID) values ('Efetuar correção do problema diagnosticado', 'Corrigir um defeito', null, null, (select dpr.dpr_id from dpr_departamento dpr where dpr.dpr_sigla = 'DIE'), (select pro.pro_id from pro_processo pro where pro_nome = 'Solicitação de Mudança'));

insert into tra_transacao_atividade (ATI_ID_ANTERIOR, ATI_ID_POSTERIOR) values ((select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Divulgar Vaga'), (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'));
insert into tra_transacao_atividade (ATI_ID_ANTERIOR, ATI_ID_POSTERIOR) values ((select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'), (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Realizar Pagamento'));

insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Seleciona os currículos de acordo com a necessidade', 'Selecionar Currículos', 117, 101, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'user1dpe'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Realiza o teste de conhecimentos', 'Realizar Teste Escrito', 306, 106, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'user1dpe'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Realizar dinâmica dos selecionados', 'Dinâmica em Grupo', 510, 108, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'user2dpe'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Entrevista dos selecionados', 'Entrevista', 319, 276, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Aprovar Candidatos'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'gestordpe'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Elaborar material para divulgação', 'Preparar Material de Divulgação', 173, 114, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Divulgar Vaga'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'usernmk'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Realiza a divulgação da vaga pelos meios disponíveis', 'Realizar Divulgação', 358, 105, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Divulgar Vaga'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'usernmk'));
insert into tar_tarefa (TAR_DESCRICAO, TAR_NOME, TAR_LEFT, TAR_TOP, ATI_ID, UUR_ID) values ('Realiza o depósito bancário, parte do primeiro salário, para incentivo', 'Depositar', null, null, (select ati.ati_id from ati_atividade ati where ati.ati_nome = 'Realizar Pagamento'), (select uur.uur_id from uur_usuario uur where uur.uur_login = 'userdfi'));

insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Selecionar Currículos'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Realizar Teste Escrito'));
insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Realizar Teste Escrito'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Dinâmica em Grupo'));
insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Selecionar Currículos'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Entrevista'));
insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Realizar Teste Escrito'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Entrevista'));
insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Dinâmica em Grupo'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Entrevista'));
insert into trt_transacao_tarefa (TAR_ID_ANTERIOR, TAR_ID_POSTERIOR) values ((select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Preparar Material de Divulgação'), (select tar.tar_id from tar_tarefa tar where tar.tar_nome = 'Realizar Divulgação'));
