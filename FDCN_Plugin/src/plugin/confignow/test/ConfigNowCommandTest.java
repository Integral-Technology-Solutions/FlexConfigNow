package plugin.confignow.test;

import flexagon.fd.core.PropertyValue;
import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.workflow.MockWorkflowExecutionContext;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexCheckedException;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
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
    public void activate_composite_properInputTest()
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
}
