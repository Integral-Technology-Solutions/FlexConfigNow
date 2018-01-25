package plugin.confignow.test;

import flexagon.fd.core.PropertyValue;
import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.workflow.MockWorkflowExecutionContext;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexCheckedException;
import flexagon.ff.common.core.exceptions.FlexExternalProcessFailedException;
import org.junit.Test;
import plugin.confignow.configNowProperties;
import plugin.confignow.operations.installObpm;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matt on 25/01/2018.
 */
public class installObpmTest {

    @Test(expected = FlexExternalProcessFailedException.class)
    public void testInstallObpm() throws FlexCheckedException {
        AbstractPluginProvider pluginProvider = new installObpm();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("simple12c", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.INSTALL_LOCATION, new PropertyValue("C:/Users/Matt/Documents/Integral", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.ENV_HOME, new PropertyValue("C:/Users/Matt/Documents/Integral", PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        pluginProvider.setWorkflowExecutionContext(context);

        pluginProvider.validate();
        pluginProvider.execute();
    }
}
