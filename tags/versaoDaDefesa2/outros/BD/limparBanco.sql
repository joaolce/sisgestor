use sisgestor;

delete from anx_anexo;
delete from trt_transacao_tarefa;
delete from tra_transacao_atividade;
delete from trp_transacao_processo;

delete from huwr_uso_workflow;
delete from uca_usoworkflow_campo;
delete from uwr_uso_workflow;
delete from opc_opcao_campo;
delete from cam_campo;
delete from tar_tarefa;
delete from ati_atividade;
delete from pro_processo;
delete from wor_workflow;

delete from upm_usuario_permissao;
delete from prm_permissao;
delete from uur_usuario;
update dpr_departamento set dpr_departamento.DPR_ID_SUPERIOR = null;
delete from dpr_departamento;