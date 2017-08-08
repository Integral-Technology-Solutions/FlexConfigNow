package plugin.confignow;

import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexInvalidArgumentException;
import flexagon.ff.common.core.externalprocess.ExternalProcess;
import flexagon.ff.common.core.logging.FlexLogger;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Matt Spencer on 3/08/2017.
 */
public class BuildCommand
{
    private static final String CLZ_NAM = BuildCommand.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    private String[] mBuildCommand;
    private String mConfigNowHome;

    public BuildCommand(WorkflowExecutionContext workflowExecutionContext) {
        mConfigNowHome = workflowExecutionContext.getInstallPluginsDirectory() + File.separator + "configNOW";
    }

    public void setBuildCommand(String[] commands){
        this.mBuildCommand = commands;
    }

    public String[] getBuildCommand(){
        return this.mBuildCommand;
    }

    public void runBuildCommand(){
        String method = "runBuildCommand";
        if(!validateBuildCommands()){
            LOG.logSevere(method, "Build command validation failed!");
            LOG.logInfoExiting(method);
            throw new FlexInvalidArgumentException("Build Commands");
        }
        LOG.logInfo(method, "Creating new external process with working directory of: " + mConfigNowHome + " and command arguments of: " + getBuildCommand());
        ExternalProcess externalProcess = new ExternalProcess(new File(mConfigNowHome));
        configureExecution();
        externalProcess.setCommands(Arrays.asList(getBuildCommand()));
        LOG.logInfo(method, "Executing external process");
        externalProcess.execute();
        LOG.logInfoExiting(method, "Execution complete");
    }

    private boolean validateBuildCommands(){
        String method = "validateBuildCommads";
        if (this.mBuildCommand == null){
            LOG.logSevere(method, "No build commands parsed");
            return false;
        }else if (mBuildCommand.length != 4){
            LOG.logSevere(method, "Incorrect amount of build arguments parsed");
            return false;
        }else if (!mBuildCommand[0].trim().equals("ConfigNOW")){
            LOG.logSevere(method, "First build argument needs to be 'confignow'");
            return false;
        }else{
            LOG.logInfo(method, "Build Commands verified");
            return true;
        }
    }

    /* This helper function edits the first build command argument
    *  It is responsible for determining the OS type and applying
    *  the correct extension to ConfigNOW (.sh or .cmd) and also
    *  applying the correct path to the file */
    private void configureExecution(){
        String OS = System.getProperty("os.name").toLowerCase();
        String fileExt;
        if(OS.indexOf("win") >= 0){
            fileExt = ".cmd";
        }else{
            fileExt = ".sh";
        }
        this.mBuildCommand[0] = mConfigNowHome + File.separator + mBuildCommand[0] + fileExt;
    }
}
