package plugin.confignow.operations;

import flexagon.fd.core.plugin.AbstractPluginProvider;
import flexagon.fd.core.plugin.PluginResult;
import flexagon.ff.common.core.logging.FlexLogger;

/**
 * Created by Matt on 4/08/2017.
 */
public class activate_composite extends AbstractPluginProvider {

    private static final String CLZ_NAM = create_domain.class.getName();
    private static final FlexLogger LOG = FlexLogger.getLogger(CLZ_NAM);

    public activate_composite(){super();}

    @Override
    public PluginResult execute(){
        String method = "execute";
        LOG.logInfoEntering(method);


        LOG.logInfoExiting(method);
        return PluginResult.createPluginResult(getWorkflowExecutionContext());
    }

    @Override
    public void validate(){
        String method = "validate";
        LOG.logInfoEntering(method);
        /* Validate:
        *   - soa.host
        *   - wls.server.soa_server1.listener.port
        *   - wls.admin.username
        *   - wls.admin.password
        *   - composite.name
        *   - composite.revision
        * Uses config file: composites_example.properties */


        LOG.logInfoExiting(method, "validation complete");
    }
}
