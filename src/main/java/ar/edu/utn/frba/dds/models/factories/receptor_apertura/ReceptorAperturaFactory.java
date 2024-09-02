package ar.edu.utn.frba.dds.models.factories.receptor_apertura;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.MqttReceptorApertura;

import java.util.UUID;

public class ReceptorAperturaFactory {

    public MqttReceptorApertura create(String url, Heladera heladera){
        Boolean hayQueConectarse = false;
        MqttReceptorApertura receptor1 = new MqttReceptorApertura();
        if (receptor1.getUrl() == null){
            hayQueConectarse = true;
        }
        receptor1.setHeladera(heladera);
        receptor1.setUrl(url);

        if (hayQueConectarse){
            receptor1.conectarseATopics();
        }
        return receptor1;
    }

    public MqttReceptorApertura create(Heladera heladera){
        return new MqttReceptorApertura(heladera);
    }

    public MqttReceptorApertura create(HeladerasController controller, Heladera heladera){
        MqttReceptorApertura receptor1 = new MqttReceptorApertura(heladera);
        receptor1.setController(controller);
        return receptor1;
    }

    public MqttReceptorApertura create(String url, Heladera heladera, HeladerasController controller){
        Boolean hayQueConectarse = false;
        MqttReceptorApertura receptor1 = new MqttReceptorApertura();
        if (receptor1.getUrl() == null){
            hayQueConectarse = true;
        }

        receptor1.setHeladera(heladera);
        receptor1.setUrl(url);
        receptor1.setController(controller);

        if (hayQueConectarse){
            receptor1.conectarseATopics();
        }

        return receptor1;
    }


}
