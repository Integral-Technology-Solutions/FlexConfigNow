package plugin.confignow.test;

import flexagon.fd.core.PropertyValue;
import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.workflow.MockWorkflowExecutionContext;
import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.exceptions.FlexCheckedException;
import org.junit.Test;
import plugin.confignow.configNowProperties;
import plugin.confignow.operations.runRcu;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matt on 25/01/2018.
 */
public class runRcuTest {

    @Test
    public void testRunRcu() throws FlexCheckedException {
        AbstractPluginProvider pluginProvider = new runRcu();

        ConcurrentHashMap<String, PropertyValue> inputs = new ConcurrentHashMap<>();
        inputs.put(configNowProperties.FDCN_CONFIG_FILE, new PropertyValue("simple12c", PropertyValue.PropertyTypeEnum.String, false));
        inputs.put(configNowProperties.INSTALL_LOCATION, new PropertyValue("true", PropertyValue.PropertyTypeEnum.String, false));

        WorkflowExecutionContext context = new MockWorkflowExecutionContext(inputs);
        pluginProvider.setWorkflowExecutionContext(context);

        pluginProvider.validate();
        pluginProvider.execute();
    }
}
