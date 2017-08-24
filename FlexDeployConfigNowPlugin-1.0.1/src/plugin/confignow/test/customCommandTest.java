package plugin.confignow.test;

import flexagon.fd.core.PropertyValue;
import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.workflow.MockWorkflowExecutionContext;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexCheckedException;
import flexagon.ff.common.core.exceptions.FlexExternalProcessFailedException;
import org.junit.BeforeClass;
import org.junit.Test;
import plugin.confignow.configNowProperties;
import plugin.confignow.operations.customCommand;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matt Spencer on 24/08/2017.
 */
public class customCommandTest {
    static String fileName1 = "TestFile1.txt";
    static String subFolder = "subFolder";

    @BeforeClass
    public static void prepTests()
            throws IOException {
        WorkflowExecutionContext context = new MockWorkflowExecutionContext();
        File file = new File(context.getTempDirectory() + File.separator + subFolder + File.separator + fileName1);
        file.getParentFile().mkdir();
        file.createNewFile();
    }

    /* This test will vaildate correctly but we expect the confignow command to fail as it cannot create a local domain
     * The only place the externalprocessfailedexception is thrown is if the confignow command fails (will locally) */
    @Test(expected = FlexExternalProcessFailedException.class)
    public void createDomainCorrectConfig() throws FlexCheckedException {
        AbstractPluginProvider pluginProvider = new customCommand();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("simple12c", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.FDCN_COMMAND, new PropertyValue("my_custom_command", PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        pluginProvider.setWorkflowExecutionContext(context);

        pluginProvider.validate();
        pluginProvider.execute();
    }
}
