package ar.edu.utn.frba.dds.models.factories.receptor_apertura;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.services.receptores.MqttReceptorApertura;

public class ReceptorAperturaFactory {

    public MqttReceptorApertura create(String url){
        Boolean hayQueConectarse = false;
        MqttReceptorApertura receptor1 = new MqttReceptorApertura();
        if (receptor1.getUrl() == null){
            hayQueConectarse = true;
        }
        receptor1.setUrl(url);

        if (hayQueConectarse){
            receptor1.conectarseATopics();
        }
        return receptor1;
    }

    public MqttReceptorApertura create(HeladerasController controller){
        MqttReceptorApertura receptor1 = new MqttReceptorApertura();
        receptor1.setController(controller);
        return receptor1;
    }

    public MqttReceptorApertura create(String url, HeladerasController controller){
        Boolean hayQueConectarse = false;
        MqttReceptorApertura receptor1 = new MqttReceptorApertura();
        if (receptor1.getUrl() == null){
            hayQueConectarse = true;
        }
        receptor1.setUrl(url);
        receptor1.setController(controller);

        if (hayQueConectarse){
            receptor1.conectarseATopics();
        }

        return receptor1;
    }


}
