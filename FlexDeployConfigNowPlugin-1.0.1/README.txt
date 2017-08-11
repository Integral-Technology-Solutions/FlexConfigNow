					ConfigNOW - FlexDeploy Plugin
***************************************************************************************************************

REQUIREMENTS:
	- The endpoint machine must have a supported Java version installed.
	- Any operations which are being performed need to have required infrastructure installed on the local
	  machine. Ie: to run the create_domain operation, the endpoint machine must have weblogic installed
	  and correctly configured.
	- The FlexDeploy properties must be configured correctly in accordance with the endpoint.

***************************************************************************************************************

ConfigNOW is packaged inside of the FlexDeploy plugin. All ConfigNOW commands are ran locally on the endpoint.

***************************************************************************************************************

When using plugin operations across multiple environments, Ie: DEV, Test, UAT + Prod, ensure that source control
has config files for each environment endpoint in the file structure:
	$ConfigNOW_Home/config/environments/$FD_ENVIRONMENT_CODE/properties_file.properties