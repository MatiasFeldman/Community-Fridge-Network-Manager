package ar.edu.utn.frba.dds.utils.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PrettyProperties {
    private static PrettyProperties instance = null;
    private Properties prop;


    public static PrettyProperties getInstance() {
        if(instance == null) {
            instance = new PrettyProperties();
        }
        return instance;
    }

    private PrettyProperties() {
        this.prop = new Properties();
        this.readProperties();
    }

    private void readProperties() {
        try {
            InputStream input = new FileInputStream("config.properties");
            this.prop.load(input);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String propertyFromName(String name) {
        return this.prop.getProperty(name, null);
    }
}
