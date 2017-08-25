package plugin.confignow;

import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexExternalProcessFailedException;

import java.util.Arrays;

/**
 * Created by Matt on 25/08/2017.
 */
public class executionHelper {

    public static void executeCommand(WorkflowExecutionContext context, String command, String environment, String configFile){
        String[] commandLine = {"ConfigNOW", command, environment, configFile};

        BuildCommand buildCommand = new BuildCommand(context);
        buildCommand.setBuildCommand(commandLine);
        boolean result = buildCommand.runBuildCommand();

        if(!result){
            throw new FlexExternalProcessFailedException(0, Arrays.asList(buildCommand.getBuildCommand()));
        }
    }
}
