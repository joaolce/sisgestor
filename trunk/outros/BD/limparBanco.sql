use sisgestor;

truncate table anx_anexo;
truncate table trt_transacao_tarefa;
truncate table tra_transacao_atividade;
truncate table trp_transacao_processo;

truncate table huwr_uso_workflow;
truncate table uca_usoworkflow_campo;
truncate table uwr_uso_workflow;
truncate table opc_opcao_campo;
truncate table cam_campo;
truncate table tar_tarefa;
truncate table ati_atividade;
truncate table pro_processo;
truncate table wor_workflow;

truncate table upm_usuario_permissao;
truncate table prm_permissao;
truncate table uur_usuario;
update dpr_departamento set dpr_departamento.DPR_ID_SUPERIOR = null;
truncate table dpr_departamento;