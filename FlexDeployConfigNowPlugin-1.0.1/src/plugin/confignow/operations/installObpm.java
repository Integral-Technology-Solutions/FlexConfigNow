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

/**
 * Created by Matt on 25/01/2018.
 */
public class installObpm extends AbstractPluginProvider {
    private static final String CLZ_NAM = addUser.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    private String mEnvironment;
    private String mConfigFile;
    private String mInstallLoc;
    private String mObpmHome;

    /* Execute the plugin operation */
    @Override
    public PluginResult execute(){
        String method = "execute";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext context = getWorkflowExecutionContext();
        String command = "install_obpm";

        String installString = "-Dobpm.install.file=" + mInstallLoc;
        String obpmHome = "-Dobpm.home=" + mObpmHome;

        executionHelper.executeCommand(context, command, mEnvironment, mConfigFile, installString, obpmHome);

        LOG.logInfoExiting(method);
        return PluginResult.createPluginResult(context);
    }

    /* Validate that environment code has a folder which contains config file */
    @Override
    public void validate(){
        String method = "validate";
        LOG.logInfoEntering(method);
        WorkflowExecutionContext workflowExecutionContext = getWorkflowExecutionContext();

        String obpmHome = getStringInput(configNowProperties.ENV_HOME);
        File obpmFileHome = new File(obpmHome);
        if (!obpmFileHome.exists()){
            throw new FlexInvalidArgumentException("Invalid OBPM home provided");
        }else{
            this.mObpmHome = obpmHome;
            LOG.logInfo(method, "OBPM home confirmed");
        }

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
