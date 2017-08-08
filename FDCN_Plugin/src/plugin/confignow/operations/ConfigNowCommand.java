package plugin.confignow.operations;

import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.plugin.PluginResult;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexInvalidArgumentException;
import flexagon.ff.common.core.exceptions.FlexMissingArgumentException;
import flexagon.ff.common.core.logging.FlexLogger;
import plugin.confignow.BuildCommand;
import plugin.confignow.configNowProperties;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Matt Spencer on 4/08/2017.
 */
public class ConfigNowCommand extends AbstractPluginProvider{

    private static final String CLZ_NAM = ConfigNowCommand.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    private ArrayList<String> configNowCommands = new ArrayList<>();

    public ConfigNowCommand(){
        configNowCommands.add("activate_composite");
        configNowCommands.add("add_tomcat_user");
        configNowCommands.add("add_user");
        configNowCommands.add("config_diff");
        configNowCommands.add("configure_datasource");
        configNowCommands.add("configure_domain");
        configNowCommands.add("configure_nodemanager");
        configNowCommands.add("configure_obpm");
        configNowCommands.add("create_domain");
        configNowCommands.add("db_switchover");
        configNowCommands.add("delete_domain");
        configNowCommands.add("deploy_apps");
        configNowCommands.add("deploy_composite");
        configNowCommands.add("deploy_composite_with_cp");
        configNowCommands.add("deploy_fuse_app");
        configNowCommands.add("deploy_jboss_app");
        configNowCommands.add("deploy_tomcat_app");
        configNowCommands.add("example");
        configNowCommands.add("example_using_ant");
        configNowCommands.add("install_activemq");
        configNowCommands.add("install_fmw_infra");
        configNowCommands.add("install_fuse");
        configNowCommands.add("install_jboss");
        configNowCommands.add("install_obpm");
        configNowCommands.add("install_osb");
        configNowCommands.add("install_osb10g");
        configNowCommands.add("install_osb12c");
        configNowCommands.add("install_soa_suite");
        configNowCommands.add("install_soa_suite_11g");
        configNowCommands.add("install_soa_suite_12c");
        configNowCommands.add("install_tomcat");
        configNowCommands.add("install_weblogic");
        configNowCommands.add("jms_fix");
        configNowCommands.add("list_composites");
        configNowCommands.add("list_deployed_apps");
        configNowCommands.add("list_deployed_apps_tomcat");
        configNowCommands.add("opatch_apply");
        configNowCommands.add("opatch_lsinventory");
        configNowCommands.add("opatch_rollback");
        configNowCommands.add("osb_fix");
        configNowCommands.add("password_encrypter");
        configNowCommands.add("redeploy_jboss_app");
        configNowCommands.add("reload_tomcat_app");
        configNowCommands.add("remote_deploy_app");
        configNowCommands.add("remote_undeploy_app");
        configNowCommands.add("restart_jboss");
        configNowCommands.add("retire_composite");
        configNowCommands.add("run_cli");
        configNowCommands.add("run_rcu");
        configNowCommands.add("run_rcu_legacy_ant");
        configNowCommands.add("set_default_composite");
        configNowCommands.add("setup_oer_reports");
        configNowCommands.add("show_config");
        configNowCommands.add("show_config_lineage");
        configNowCommands.add("shutdown_all_servers");
        configNowCommands.add("shutdown_domain_servers");
        configNowCommands.add("soa");
        configNowCommands.add("start_amq");
        configNowCommands.add("start_composite");
        configNowCommands.add("start_fuse");
        configNowCommands.add("start_jboss");
        configNowCommands.add("start_karaf_session");
        configNowCommands.add("start_tomcat");
        configNowCommands.add("start_wls_admin");
        configNowCommands.add("status_fuse");
        configNowCommands.add("stop_amq");
        configNowCommands.add("stop_composite");
        configNowCommands.add("stop_fuse");
        configNowCommands.add("stop_jboss");
        configNowCommands.add("stop_tomcat");
        configNowCommands.add("stop_wls_admin");
        configNowCommands.add("undeploy_composite");
        configNowCommands.add("undeploy_fuse_app");
        configNowCommands.add("undeploy_jboss_app");
        configNowCommands.add("undeploy_tomcat_app");
        configNowCommands.add("validate_config");
    }

    @Override
    public PluginResult execute(){
        String method = "execute";
        LOG.logInfoEntering(method);

        BuildCommand builder = new BuildCommand(getWorkflowExecutionContext());

        String command = getStringInput(configNowProperties.FDCN_COMMAND);
        String environment = getWorkflowExecutionContext().getEnvironment().getCode();
        String configFile = getStringInput(configNowProperties.FDCN_CONFIG_FILE);

        String[] commandLine = {"ConfigNOW", command, environment, configFile};

        builder.setBuildCommand(commandLine);
        builder.runBuildCommand();

        LOG.logInfoExiting(method);
        return PluginResult.createPluginResult(getWorkflowExecutionContext());
    }

    @Override
    public void validate(){
        String method = "validate";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext workflowExecutionContext = getWorkflowExecutionContext();
        String configNowHome = workflowExecutionContext.getInstallPluginsDirectory() + File.separator + "configNOW";

        String command = getStringInput(configNowProperties.FDCN_COMMAND);
        String environment = getWorkflowExecutionContext().getEnvironment().getCode();
        String configFileLoc = getStringInput(configNowProperties.FDCN_CONFIG_FILE);

        /* Validation of ConfigNOW command provided */
        if(command == null){
            throw new FlexMissingArgumentException("No ConfigNOW command provided");
        }else if(!configNowCommands.contains(command)){
            throw new FlexInvalidArgumentException("Invalid ConfigNOW command provided");
        }else{
            LOG.logInfo(method, "ConfigNOW command validated");
        }

        /* Validation of environment provided */
        File environmentDir = new File(configNowHome + File.separator + "config" + File.separator + "environments" + File.separator + environment);
        if(environment == null){
            throw new FlexMissingArgumentException("No environment directory provided");
        }else if (!environmentDir.exists()){
            throw new FlexInvalidArgumentException("Environment directory doesn't exist");
        }else{
            LOG.logInfo(method, "Environment directory validated");
        }

        File configFile = new File(configNowHome + File.separator + "config" + File.separator + "environments" + File.separator + environment + File.separator + configFileLoc + ".properties");
        if(!configFile.exists()){
            throw new FlexInvalidArgumentException("properties file name does not exist");
        }else{
            LOG.logInfo(method, "Properties file validated");
        }

        LOG.logInfoExiting(method);
    }
}
