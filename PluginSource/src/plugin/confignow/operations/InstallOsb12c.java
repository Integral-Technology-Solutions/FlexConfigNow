package plugin.confignow.operations;

import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.plugin.PluginResult;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexInvalidArgumentException;
import flexagon.ff.common.core.logging.FlexLogger;
import plugin.confignow.configNowProperties;
import plugin.confignow.executionHelper;
import plugin.confignow.validationHelper;

import java.io.File;

public class InstallOsb12c extends AbstractPluginProvider {

    private static final String CLZ_NAM = addUser.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    private String mEnvironment;
    private String mConfigFile;
    private String mInstallLoc;

    /* Execute the plugin operation */
    @Override
    public PluginResult execute(){
        String method = "execute";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext context = getWorkflowExecutionContext();
        String command = "install_osb12c";

        String installString = "-Dosb.install.zip=" + mInstallLoc;

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

        String installLoc = getStringInput(configNowProperties.INSTALL_LOCATION);
        File installHome = new File(installLoc);
        if (!installHome.exists()){
            throw new FlexInvalidArgumentException("Install Location doesn't exist");
        }else {
            this.mInstallLoc = installLoc;
            LOG.logInfo(method, "Install Location Validated");
        }

        String configFile = getStringInput(configNowProperties.FDCN_CONFIG_FILE);
        if(validationHelper.validateInputs(workflowExecutionContext, LOG, configFile)){
            this.mConfigFile = configFile;
            this.mEnvironment = workflowExecutionContext.getEnvironment().getCode();
        }

        LOG.logInfoExiting(method);
    }
}
