package plugin.confignow.test;

import flexagon.fd.core.PropertyValue;
import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.workflow.MockWorkflowExecutionContext;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexCheckedException;
import flexagon.ff.common.core.exceptions.FlexExternalProcessFailedException;
import flexagon.ff.common.core.exceptions.FlexInvalidArgumentException;
import flexagon.ff.common.core.exceptions.FlexMissingArgumentException;
import org.junit.BeforeClass;
import org.junit.Test;
import plugin.confignow.configNowProperties;
import plugin.confignow.operations.ConfigNowCommand;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matt Spencer on 4/08/2017.
 */
public class ConfigNowCommandTest {
    static String fileName1 = "TestFile1.txt";
    static String subFolder = "subFolder";

    @BeforeClass
    public static void prepTests()
        throws IOException{
        WorkflowExecutionContext context = new MockWorkflowExecutionContext();
        File file = new File(context.getTempDirectory() + File.separator + subFolder + File.separator + fileName1);
        file.getParentFile().mkdir();
        file.createNewFile();
    }

    /* Test with all correct input arguments, no custom property replacement
    *  Build in ConfigNOW will take place and will complete succesfully */
    @Test
    public void correctInputsTestNoCustom()
        throws FlexCheckedException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("example", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("local", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("simple",PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    /* Test with all correct input arguments, no custom property replacement
    *  Build in ConfigNOW will take place but will fail */
    @Test(expected = FlexExternalProcessFailedException.class)
    public void correctInputsTestNoCustomBuildFail()
            throws FlexCheckedException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("activate_composite", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("local", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("composites_example",PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    /* Test with incorrect command entered, no custom property replacement
    *  Build in configNOW will not take place */
    @Test(expected = FlexInvalidArgumentException.class)
    public void incorrectCommandTest()
        throws FlexInvalidArgumentException, FlexCheckedException {
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("actvate_cmposite", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("local", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("simple",PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    /* Test with incorrect configuration file name, no custom property replacement
    *  Build in ConfigNOW will not take place */
    @Test(expected = FlexInvalidArgumentException.class)
    public void incorrectConfigFileTest()
        throws FlexInvalidArgumentException, FlexCheckedException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("example", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("local", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("composites_exmpl",PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    /* Test with incorrect environment name provided, no custom property replacement
    *  Build in ConfigNOW will not take place */
    @Test(expected = FlexInvalidArgumentException.class)
    public void incorrectEnvNameTest()
        throws FlexCheckedException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("example", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("my_env", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("simple",PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    /* Test with no command provided, no custom property replacement
    *  Build in ConfigNOW will not take place */
    @Test(expected = FlexMissingArgumentException.class)
    public void missingCommandTest()
        throws FlexCheckedException, FlexMissingArgumentException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("composites_example",PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("local", PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    /* Test with no config file provided, no custom property replacement
    *  Build in ConfigNOW will not take place */
    @Test(expected = FlexInvalidArgumentException.class)
    public void missingConfigFileTest()
        throws FlexCheckedException, FlexInvalidArgumentException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("activate_composite", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("local", PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    /* Test with no environment provided, no custom property replacement
    *  Build in ConfigNOW will not take place */
    @Test(expected = FlexMissingArgumentException.class)
    public void missingEnvironmentTest()
        throws FlexCheckedException, FlexMissingArgumentException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("activate_composite", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("composites_example", PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    /* Test with all correct inputs and custom property replacement
    *  ConfigNOW will run and build successfully */
    @Test
    public void correctInputsWithCustom()
        throws FlexCheckedException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        String propertyReplacements = "wls.oracle.home=C:/Invalid/Path/To/Oracle/Home";

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("example", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("local", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("simple",PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_TEXT, new PropertyValue(propertyReplacements, PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    /* Test with all correct inputs and custom property replacement
    *  ConfigNOW will run and build will fail */
    @Test(expected = FlexExternalProcessFailedException.class)
    public void correctInputsWithCustomBuildFail()
        throws FlexCheckedException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        String propertyReplacements = "wls.oracle.home=C:/Invalid/Path/To/Oracle/Home";

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("activate_composite", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("local", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("composites_example",PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_TEXT, new PropertyValue(propertyReplacements, PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }
}
