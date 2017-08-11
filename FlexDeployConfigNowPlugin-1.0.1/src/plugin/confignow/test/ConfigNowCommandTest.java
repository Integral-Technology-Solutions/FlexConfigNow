package plugin.confignow.test;

import flexagon.fd.core.PropertyValue;
import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.workflow.MockWorkflowExecutionContext;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexCheckedException;
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

    @Test
    public void correctInputsTestNoCustom()
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

    @Test(expected = FlexInvalidArgumentException.class)
    public void incorrectCommandTest()
        throws FlexInvalidArgumentException, FlexCheckedException {
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("actvate_cmposite", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("local", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("composites_example",PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    @Test(expected = FlexInvalidArgumentException.class)
    public void incorrectConfigFileTest()
        throws FlexInvalidArgumentException, FlexCheckedException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("activate_composite", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("local", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("composites_ex",PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

    @Test(expected = FlexInvalidArgumentException.class)
    public void incorrectEnvNameTest()
        throws FlexCheckedException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("activate_composite", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_ENVIRONMENT, new PropertyValue("my_env", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("composites_example",PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        plugin.setWorkflowExecutionContext(context);

        plugin.validate();
        plugin.execute();
    }

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

    @Test
    public void correctInputsWithCustom()
        throws FlexCheckedException{
        AbstractPluginProvider plugin = new ConfigNowCommand();

        String propertyReplacements = "soa.home=172.17.18.10";

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
