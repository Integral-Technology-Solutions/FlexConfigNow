package plugin.confignow;

import flexagon.fd.core.workflow.WorkflowExecutionContext;
import flexagon.ff.common.core.logging.FlexLogger;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Matt on 30/11/2017.
 */
public class artifactExtractor {

    private static final int BUFFER_SIZE = 4096;

    public void moveFiles(WorkflowExecutionContext context, FlexLogger LOG) throws IOException {
        String method = "moveFiles";
        LOG.logInfoEntering(method);
        String version = configNowProperties.Plugin_Version;
        // Grab the location of the saved artifacts from the build
        String artifactsLoc = context.getArtifactsDirectory() + File.separator + "configNOW.zip";
        // Grab the configNowHome so we can copy contents there
        String configNowHome = context.getInstallPluginsDirectory("IntegralConfigNOWPlugin", version) + File.separator + "configNOW";
        // Unzip artifacts dir into the configNowHome dir
        String response = unzip(artifactsLoc, configNowHome);
        LOG.logInfoExiting(method, response);
    }

    private String unzip(String zipFileLoc, String configNowLoc) throws IOException {
        File configNowDir = new File(configNowLoc);
        if (!configNowDir.exists()){
            return "ConfigNow Directory doesn't exist in plugins directory";
        }
        File zipFileDir = new File(zipFileLoc);
        if (!zipFileDir.exists()){
            return "Artifacts Directory doesn't exist";
        }

        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFileLoc));
        ZipEntry entry = zipInputStream.getNextEntry();
        while (entry != null){
            String filePath = configNowLoc + File.separator + entry.getName();
            if (!entry.isDirectory()){
                extractFile(zipInputStream, filePath);
            }else{
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipInputStream.closeEntry();
            entry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();

        return "Files Successfully extracted!";
    }

    public void extractFile(ZipInputStream zipInputStream, String filePath) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytes = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipInputStream.read(bytes)) != -1){
            bufferedOutputStream.write(bytes, 0 ,read);
        }
        bufferedOutputStream.close();
    }
}
