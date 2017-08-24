package plugin.confignow.operations;

import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.plugin.PluginResult;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexExternalProcessFailedException;
import flexagon.ff.common.core.exceptions.FlexInvalidArgumentException;
import flexagon.ff.common.core.exceptions.FlexMissingArgumentException;
import flexagon.ff.common.core.logging.FlexLogger;
import plugin.confignow.BuildCommand;
import plugin.confignow.configNowProperties;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Matt Spencer on 24/08/2017.
 */
public class createDomain extends AbstractPluginProvider{

    private static final String CLZ_NAM = createDomain.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    private String mEnvironment;
    private String mConfigFile;

    /* Execute the plugin operation */
    @Override
    public PluginResult execute(){
        String method = "execute";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext context = getWorkflowExecutionContext();

        String[] commandLine = {"ConfigNOW", "create_domain", mEnvironment, mConfigFile};

        BuildCommand buildCommand = new BuildCommand(context);
        buildCommand.setBuildCommand(commandLine);
        boolean result = buildCommand.runBuildCommand();

        if(!result){
            throw new FlexExternalProcessFailedException(0, Arrays.asList(buildCommand.getBuildCommand()));
        }

        LOG.logInfoExiting(method);
        return PluginResult.createPluginResult(context);
    }

    /* Validate that environment code has a folder which contains config file */
    @Override
    public void validate(){
        String method = "validate";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext workflowExecutionContext = getWorkflowExecutionContext();
        String configNowHome = workflowExecutionContext.getInstallPluginsDirectory() + File.separator + "configNOW";
        String environment = workflowExecutionContext.getEnvironment().getCode();
        String configFileLoc = getStringInput(configNowProperties.FDCN_CONFIG_FILE);

        /* Validation of environment provided */
        File environmentDir = new File(configNowHome + File.separator + "config" + File.separator + "environments" + File.separator + environment);
        if(environment == null){
            throw new FlexMissingArgumentException("No environment directory provided");
        }else if (!environmentDir.exists()){
            throw new FlexInvalidArgumentException("Environment directory doesn't exist");
        }else{
            this.mEnvironment = environment;
            LOG.logInfo(method, "Environment directory validated");
        }

        /* Validation of config file */
        File configFile = new File(configNowHome + File.separator + "config" + File.separator + "environments" + File.separator + environment + File.separator + configFileLoc + ".properties");
        if(configFileLoc == null){
            throw new FlexMissingArgumentException("No config file provided");
        }else if(!configFile.exists()){
            throw new FlexInvalidArgumentException("properties file name does not exist");
        }else{
            this.mConfigFile = configFileLoc;
            LOG.logInfo(method, "Properties file validated");
        }

        LOG.logInfoExiting(method);
    }
}
