package plugin.confignow.operations;

import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.plugin.PluginResult;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexInvalidArgumentException;
import flexagon.ff.common.core.exceptions.FlexMissingArgumentException;
import flexagon.ff.common.core.logging.FlexLogger;
import plugin.confignow.configNowProperties;
import plugin.confignow.executionHelper;
import plugin.confignow.validationHelper;

import java.io.File;

/**
 * Created by Matt Spencer on 24/08/2017.
 */
public class customCommand extends AbstractPluginProvider{

    private static final String CLZ_NAM = customCommand.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);
    private String mCommand;
    private String mEnvironment;
    private String mConfigFile;

     /* Execute the plugin operation */
    @Override
    public PluginResult execute(){
        String method = "execute";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext context = getWorkflowExecutionContext();

        executionHelper.executeCommand(context, mCommand, mEnvironment, mConfigFile);

        LOG.logInfoExiting(method);
        return PluginResult.createPluginResult(context);
    }

    @Override
    public void validate(){
        String method = "validate";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext workflowExecutionContext = getWorkflowExecutionContext();
        String configNowHome = workflowExecutionContext.getInstallPluginsDirectory() + File.separator + "configNOW";

        String command = getStringInput(configNowProperties.FDCN_COMMAND);

        /* Validation of ConfigNOW command provided */
        File commandFile = new File(configNowHome + File.separator + "custom" + File.separator + "commands" + File.separator + command + ".py");
        if(command == null){
            throw new FlexMissingArgumentException("No ConfigNOW command provided");
        }else if(!commandFile.exists()){
            throw new FlexInvalidArgumentException("Invalid ConfigNOW command file provided");
        }else{
            this.mCommand = command;
            LOG.logInfo(method, "ConfigNOW command validated");
        }

        String configFile = getStringInput(configNowProperties.FDCN_CONFIG_FILE);
        if(validationHelper.validateInputs(workflowExecutionContext, LOG, configFile)){
            this.mConfigFile = configFile;
            this.mEnvironment = workflowExecutionContext.getEnvironment().getCode();
        }

        LOG.logInfoExiting(method);
    }
}
