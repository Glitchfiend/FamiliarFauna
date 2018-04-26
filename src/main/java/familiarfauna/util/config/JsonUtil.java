package familiarfauna.util.config;

import java.io.File;
import java.lang.reflect.Type;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import familiarfauna.core.FamiliarFauna;

public class JsonUtil
{
    public static final Gson SERIALIZER = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T getOrCreateConfigFile(File configDir, String configName, T defaults, Type type)
    {
        File configFile = new File(configDir, configName);

        //No config file, so create default config:
        if (!configFile.exists())
        {
            writeFile(configFile, defaults);
        }

        try
        {
            return (T)SERIALIZER.fromJson(FileUtils.readFileToString(configFile), type);
        }
        catch (Exception e)
        {
            FamiliarFauna.logger.error("Error parsing config from json: " + configFile.toString(), e);
        }

        return null;
    }

    protected static boolean writeFile(File outputFile, Object obj)
    {
        try
        {
            FileUtils.write(outputFile, SERIALIZER.toJson(obj));
            return true;
        }
        catch (Exception e)
        {
            FamiliarFauna.logger.error("Error writing config file " + outputFile.getAbsolutePath() + ": " + e.getMessage());
            return false;
        }
    }
}