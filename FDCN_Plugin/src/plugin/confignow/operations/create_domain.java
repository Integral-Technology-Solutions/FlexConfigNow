package plugin.confignow.operations;

import flexagon.fd.core.Environment;
import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.plugin.PluginResult;
import flexagon.ff.common.core.exceptions.FlexCheckedException;
import flexagon.ff.common.core.logging.FlexLogger;
import plugin.confignow.BuildCommand;
import plugin.confignow.configNowProperties;

import java.io.File;

/**
 * Created by Matt Spencer on 7/07/2017.
 */
public class create_domain extends AbstractPluginProvider
{
    private static final String CLZ_NAM = create_domain.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    public create_domain()
    {
        super();
    }

    /* This method will create a new domain */
    @Override
    public PluginResult execute()
            throws FlexCheckedException {
        String methodName = "execute";
        LOG.logInfoEntering(methodName);
        /* This should:
        *   - Copy correct config files into correct directories (assume correct config files in SCM)
        *   - Create BuildCommand class and use to get information and perform operations
        *   - Set correct build commands on BuildCommand object
        *   - run build commands using BuildCommand class */

        // Get configNOW home and the environment code
        BuildCommand builder = new BuildCommand(getWorkflowExecutionContext());

        String env = Environment.class.getName();

        // Create commands which need running to run configNOW
        String commands = "confignow create_domain " + env + " simple";
        // run desired commands
        builder.setBuildCommand(commands);
        builder.runBuildCommand();

        LOG.logInfoExiting(methodName);
        return PluginResult.createPluginResult(getWorkflowExecutionContext());
    }

    /* This method is responsible for validating all inputs which are required to run exist */
    @Override
    public void validate()
            throws FlexCheckedException {
        String methodName = "validate";
        LOG.logInfoEntering(methodName);

        //Oracle Home check
        String oracle_home = getStringInput(configNowProperties.FDCN_ORACLE_HOME);
        if(oracle_home != null){
            if(!oracle_home.endsWith(File.separator)){
                oracle_home = oracle_home + File.separator;
            }
            File file = new File(oracle_home);
            if (!file.exists()){
                throw new FlexCheckedException(configNowProperties.FDCN_INVALID_PATH, "Oracle home could not be found");
            }
        }
        //WLS name check
        String WLS_name = getStringInput(configNowProperties.FDCN_WLS_NAME);
        if(WLS_name != null){
            File file = new File(oracle_home + WLS_name);
            if(!file.exists()){
                throw new FlexCheckedException(configNowProperties.FDCN_INVALID_PATH, "WLS name does not exist in oracle home");
            }
        }
        //JDK home check if provided
        String JdkHome = getStringInput(configNowProperties.FDCN_JDK_HOME);
        if(JdkHome != null){
            File file = new File(JdkHome);
            if(!file.exists()){
                throw new FlexCheckedException(configNowProperties.FDCN_INVALID_PATH, "WLS JDK path is invalid");
            }
        }
        //WLS domain dir check
        String WlsDomainDir = getStringInput(configNowProperties.FDCN_WLS_DOMAIN_DIR);
        if(WlsDomainDir != null){
            if(!WlsDomainDir.endsWith(File.separator)){
                WlsDomainDir = WlsDomainDir + File.separator;
            }
            File file = new File(WlsDomainDir);
            if(!file.exists()){
                throw new FlexCheckedException(configNowProperties.FDCN_INVALID_PATH, "WLS Domain path is invalid");
            }
        }
        //Valid domain name provided
        String domainName = getStringInput(configNowProperties.FDCN_DOMAIN_NAME);
        if(domainName != null){
            File file = new File(WlsDomainDir + domainName);
            if(file.exists()){
                throw new FlexCheckedException(configNowProperties.FDCN_DOMAIN_EXISTS, "A WLS domain with this name already exists, please select a new name");
            }
        }
    }

}