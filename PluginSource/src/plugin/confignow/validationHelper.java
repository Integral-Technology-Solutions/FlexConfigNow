package plugin.confignow;

import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexInvalidArgumentException;
import flexagon.ff.common.core.exceptions.FlexMissingArgumentException;
import flexagon.ff.common.core.logging.FlexLogger;

import java.io.File;
import java.io.IOException;

/**
 * Created by Matt on 25/08/2017.
 */
public class validationHelper{

    public static boolean validateInputs(WorkflowExecutionContext workflowExecutionContext, FlexLogger LOG, String configFileLoc) {
        try {
            artifactExtractor artifactExtractor = new artifactExtractor();
            artifactExtractor.moveFiles(workflowExecutionContext, LOG);
        }catch (IOException e){
            LOG.logSevere("moveFiles", "Failure occurred while transferring files from artifacts dir.");
        }

        String method = "validate";
        String version = configNowProperties.Plugin_Version;
        String configNowHome = workflowExecutionContext.getInstallPluginsDirectory("IntegralConfigNOWPlugin", version) + File.separator + "configNOW";
        String environment = workflowExecutionContext.getEnvironment().getCode();
        String projectName = workflowExecutionContext.getProject().getName();

        /* Validation of environment provided */
        String pathName = configNowHome + File.separator + projectName + File.separator + "config" + File.separator + "environments" + File.separator + environment;
        File environmentDir = new File(pathName);
        if(environment == null){
            throw new FlexMissingArgumentException("No environment directory provided");
        }else if (!environmentDir.exists()){
            LOG.logSevere(method, "path not found: " + pathName);
            throw new FlexInvalidArgumentException("Environment directory doesn't exist: " + pathName);
        }else{
            LOG.logInfo(method, "Environment directory validated");
        }

        /* Validation of config file */
        String fileName = pathName + File.separator + configFileLoc + ".properties";
        File configFile = new File(fileName);
        if(configFileLoc == null){
            throw new FlexMissingArgumentException("No config file provided");
        }else if(!configFile.exists()){
            LOG.logSevere(method, "File not found at: " + fileName);
            throw new FlexInvalidArgumentException("properties file name does not exist: " + fileName);
        }else{
            LOG.logInfo(method, "Properties file validated");
        }

        return true;
    }
}
