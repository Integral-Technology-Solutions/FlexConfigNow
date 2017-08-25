package plugin.confignow;

import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexInvalidArgumentException;
import flexagon.ff.common.core.exceptions.FlexMissingArgumentException;
import flexagon.ff.common.core.logging.FlexLogger;

import java.io.File;

/**
 * Created by Matt on 25/08/2017.
 */
public class validationHelper{

    public static boolean validateInputs(WorkflowExecutionContext workflowExecutionContext, FlexLogger LOG, String configFileLoc){
        String method = "validate";
        String configNowHome = workflowExecutionContext.getInstallPluginsDirectory() + File.separator + "configNOW";
        String environment = workflowExecutionContext.getEnvironment().getCode();

        /* Validation of environment provided */
        File environmentDir = new File(configNowHome + File.separator + "config" + File.separator + "environments" + File.separator + environment);
        if(environment == null){
            throw new FlexMissingArgumentException("No environment directory provided");
        }else if (!environmentDir.exists()){
            throw new FlexInvalidArgumentException("Environment directory doesn't exist");
        }else{
            LOG.logInfo(method, "Environment directory validated");
        }

        /* Validation of config file */
        File configFile = new File(configNowHome + File.separator + "config" + File.separator + "environments" + File.separator + environment + File.separator + configFileLoc + ".properties");
        if(configFileLoc == null){
            throw new FlexMissingArgumentException("No config file provided");
        }else if(!configFile.exists()){
            throw new FlexInvalidArgumentException("properties file name does not exist");
        }else{
            LOG.logInfo(method, "Properties file validated");
        }

        return true;
    }
}
