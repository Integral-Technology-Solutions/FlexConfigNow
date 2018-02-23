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
 * Created by Matt on 21/02/2018.
 */
public class installSoaSuite extends AbstractPluginProvider{

    private static final String CLZ_NAM = addUser.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    private String mEnvironment;
    private String mConfigFile;
    private String mInstallLoc1;
    private String mInstallLoc2;

    /* Execute the plugin operation */
    @Override
    public PluginResult execute(){
        String method = "execute";
        LOG.logInfoEntering(method);

        WorkflowExecutionContext context = getWorkflowExecutionContext();
        String command = "install_soa_suite";

        String install1 = "-Dsoa.suite.install.part1.zip=" + mInstallLoc1;
        String install2 = "-Dsoa.suite.install.part1.zip=" + mInstallLoc2;

        executionHelper.executeCommand(context, command, mEnvironment, mConfigFile, install1, install2);

        LOG.logInfoExiting(method);
        return PluginResult.createPluginResult(context);
    }

    /* Validate that environment code has a folder which contains config file */
    @Override
    public void validate(){
        String method = "validate";
        LOG.logInfoEntering(method);
        WorkflowExecutionContext workflowExecutionContext = getWorkflowExecutionContext();

        String install1 = getStringInput(configNowProperties.INSTALL_LOCATION);
        File fileLoc1 = new File(install1);
        if (!fileLoc1.exists()){
            throw new FlexInvalidArgumentException("Invalid Install file part 1 location: " + install1);
        }else {
            this.mInstallLoc1 = install1;
            LOG.logInfo(method, "Install file part 1 confirmed");
        }

        String install2 = getStringInput(configNowProperties.ENV_HOME);
        File fileLoc2 = new File(install2);
        if (!fileLoc2.exists()){
            throw new FlexInvalidArgumentException("Invalid Install file part 2 location" + install2);
        }else{
            this.mInstallLoc2 = install2;
            LOG.logInfo(method, "Install file part 2 confirmed");
        }

        String configFile = getStringInput(configNowProperties.FDCN_CONFIG_FILE);
        if(validationHelper.validateInputs(workflowExecutionContext, LOG, configFile)){
            this.mConfigFile = configFile;
            this.mEnvironment = workflowExecutionContext.getEnvironment().getCode();
        }

        LOG.logInfoExiting(method);
    }
}
