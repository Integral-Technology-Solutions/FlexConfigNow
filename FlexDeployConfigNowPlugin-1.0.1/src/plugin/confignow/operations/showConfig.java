package plugin.confignow.operations;

import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.plugin.PluginResult;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.logging.FlexLogger;
import plugin.confignow.configNowProperties;
import plugin.confignow.executionHelper;
import plugin.confignow.validationHelper;

/**
 * Created by Matt on 25/01/2018.
 */
public class showConfig extends AbstractPluginProvider {

    private static final String CLZ_NAM = startFuse.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    private String mEnvironment;
    private String mConfigFile;

    /* Execute the plugin operation */
    @Override
    public PluginResult execute(){
        String method = "execute";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext context = getWorkflowExecutionContext();
        String command = "show_config";

        executionHelper.executeCommand(context, command, mEnvironment, mConfigFile);

        LOG.logInfoExiting(method);
        return PluginResult.createPluginResult(context);
    }

    /* Validate that environment code has a folder which contains config file */
    @Override
    public void validate(){
        String method = "validate";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext workflowExecutionContext = getWorkflowExecutionContext();
        String configFile = getStringInput(configNowProperties.FDCN_CONFIG_FILE);
        if(validationHelper.validateInputs(workflowExecutionContext, LOG, configFile)){
            this.mConfigFile = configFile;
            this.mEnvironment = workflowExecutionContext.getEnvironment().getCode();
        }

        LOG.logInfoExiting(method);
    }
}
