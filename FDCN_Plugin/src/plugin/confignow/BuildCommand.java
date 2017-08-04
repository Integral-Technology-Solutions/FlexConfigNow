package plugin.confignow;

import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexInvalidArgumentException;
import flexagon.ff.common.core.externalprocess.ExternalProcess;
import flexagon.ff.common.core.logging.FlexLogger;

import java.io.File;

/**
 * Created by Matt on 3/08/2017.
 */
public class BuildCommand
{
    private static final String CLZ_NAM = BuildCommand.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    private String mBuildCommand;
    private String mConfigNowHome;

    public BuildCommand(WorkflowExecutionContext workflowExecutionContext) {
        mConfigNowHome = workflowExecutionContext.getInstallPluginsDirectory() + File.separator + "configNOW";
    }

    public void setBuildCommand(String commands){
        this.mBuildCommand = commands;
    }

    public void setConfigNowHome(String configNowHome){
        this.mConfigNowHome = configNowHome;
    }

    public String getBuildCommand(){
        return this.mBuildCommand;
    }

    public String getConfigNowHome(){
        return this.mConfigNowHome;
    }

    public void runBuildCommand(){
        // This is the method which will run command line arguments in the form:
        // confignow <operation_name> <environment> <properties file>
        // Calls ExternalProcess to run buildcommands

        String method = "runBuildCommand";
        String configNowHome = this.mConfigNowHome;
        // First, check that there is actually build commands to run
        if(!validateBuildCommands()){
            LOG.logSevere(method, "Build command validation failed!");
            LOG.logInfoExiting(method);
            throw new FlexInvalidArgumentException("Build Commands");
        }else {
            LOG.logInfo(method, "Creating mew external process with working directory of: " + configNowHome + " and command arguments of: " + getBuildCommand());
            ExternalProcess externalProcess = new ExternalProcess(new File(configNowHome), getBuildCommand());
            LOG.logInfo(method, "Executing external process");
            externalProcess.execute();
            LOG.logInfoExiting(method, "Execution complete");
        }
    }

    public boolean validateBuildCommands(){
        String method = "validateBuildCommads";
        String[] args = this.mBuildCommand.split("\\s");
        if (this.mBuildCommand == null){
            LOG.logSevere(method, "No build commands parsed");
            return false;
        }else if(this.mBuildCommand == "" || this.mBuildCommand == " "){
            LOG.logSevere(method, "No build commands parsed");
            return false;
        }else if (args.length != 4){
            LOG.logSevere(method, "Incorrect amount of build arguments parsed");
            return false;
        }else if (args[0] != "confignow"){
            LOG.logSevere(method, "First build argument needs to be 'confignow'");
            return false;
        }else{
            LOG.logInfo(method, "Build Commands verified");
            return true;
        }
    }
}
