package plugin.confignow.test;

import flexagon.fd.core.PropertyValue;
import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.workflow.MockWorkflowExecutionContext;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexCheckedException;
import flexagon.ff.common.core.exceptions.FlexExternalProcessFailedException;
import org.junit.Test;
import plugin.confignow.configNowProperties;
import plugin.confignow.operations.InstallOsb12c;
import plugin.confignow.operations.installOsb;

import java.util.concurrent.ConcurrentHashMap;

public class InstallOsb12cTest {

    @Test(expected = FlexExternalProcessFailedException.class)
    public void testInstallOsb12c() throws FlexCheckedException {
        AbstractPluginProvider pluginProvider = new InstallOsb12c();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("simple12c", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.INSTALL_LOCATION, new PropertyValue("C:/Users/Matt/Documents/Integral", PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        context.getProject().setName("demo");
        pluginProvider.setWorkflowExecutionContext(context);

        pluginProvider.validate();
        pluginProvider.execute();
    }

}
