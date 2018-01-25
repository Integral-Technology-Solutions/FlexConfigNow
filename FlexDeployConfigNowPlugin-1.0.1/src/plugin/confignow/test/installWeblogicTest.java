package plugin.confignow.test;

import flexagon.fd.core.PropertyValue;
import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.workflow.MockWorkflowExecutionContext;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexCheckedException;
import flexagon.ff.common.core.exceptions.FlexExternalProcessFailedException;
import org.junit.Test;
import plugin.confignow.configNowProperties;
import plugin.confignow.operations.installWeblogic;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matt on 25/01/2018.
 */
public class installWeblogicTest {

    @Test(expected = FlexExternalProcessFailedException.class)
    public void testInstallWeblogic() throws FlexCheckedException {
        AbstractPluginProvider pluginProvider = new installWeblogic();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("simple12c", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.INSTALL_LOCATION, new PropertyValue("C:/Users/Matt/Documents/Integral", PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        pluginProvider.setWorkflowExecutionContext(context);

        pluginProvider.validate();
        pluginProvider.execute();
    }
}
