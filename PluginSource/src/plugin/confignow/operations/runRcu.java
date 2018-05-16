package plugin.confignow.operations;

import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.plugin.PluginResult;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexInvalidArgumentException;
import flexagon.ff.common.core.logging.FlexLogger;
import plugin.confignow.configNowProperties;
import plugin.confignow.executionHelper;
import plugin.confignow.validationHelper;

/**
 * Created by Matt on 25/01/2018.
 */
public class runRcu extends AbstractPluginProvider {


    private static final String CLZ_NAM = addUser.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    private String mEnvironment;
    private String mConfigFile;
    private String mTrueFalse;

    /* Execute the plugin operation */
    @Override
    public PluginResult execute(){
        String method = "execute";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext context = getWorkflowExecutionContext();
        String command = "run_rcu";

        String installString = "-Ddrop.rcu.schemas=" + mTrueFalse;

        executionHelper.executeCommand(context, command, mEnvironment, mConfigFile, installString);

        LOG.logInfoExiting(method);
        return PluginResult.createPluginResult(context);
    }

    /* Validate that environment code has a folder which contains config file */
    @Override
    public void validate(){
        String method = "validate";
        LOG.logInfoEntering(method);
        WorkflowExecutionContext workflowExecutionContext = getWorkflowExecutionContext();

        String trueFalse = getStringInput(configNowProperties.INSTALL_LOCATION);
        if (trueFalse.equalsIgnoreCase("true")){
            this.mTrueFalse = "true";
        }else if (trueFalse.equalsIgnoreCase("false")){
            this.mTrueFalse = "false";
        }else{
            throw new FlexInvalidArgumentException(method, "Enter true or false for drop schemas");
        }

        String configFile = getStringInput(configNowProperties.FDCN_CONFIG_FILE);
        if(validationHelper.validateInputs(workflowExecutionContext, LOG, configFile)){
            this.mConfigFile = configFile;
            this.mEnvironment = workflowExecutionContext.getEnvironment().getCode();
        }

        LOG.logInfoExiting(method);
    }
}
