package ar.edu.utn.frba.dds.models.factories.receptor_apertura;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.MqttReceptorApertura;

import java.util.UUID;

public class ReceptorAperturaFactory {

    public MqttReceptorApertura create(String url, UUID idHeladera){
        Boolean hayQueConectarse = false;
        MqttReceptorApertura receptor1 = new MqttReceptorApertura();
        if (receptor1.getUrl() == null){
            hayQueConectarse = true;
        }
        receptor1.setIdHeladera(idHeladera);
        receptor1.setUrl(url);

        if (hayQueConectarse){
            receptor1.conectarseATopics();
        }
        return receptor1;
    }

    public MqttReceptorApertura create(UUID idHeladera){
        return new MqttReceptorApertura(idHeladera);
    }

    public MqttReceptorApertura create(HeladerasController controller, UUID idHeladera){
        MqttReceptorApertura receptor1 = new MqttReceptorApertura(idHeladera);
        receptor1.setController(controller);
        return receptor1;
    }

    public MqttReceptorApertura create(String url, UUID idHeladera, HeladerasController controller){
        Boolean hayQueConectarse = false;
        MqttReceptorApertura receptor1 = new MqttReceptorApertura();
        if (receptor1.getUrl() == null){
            hayQueConectarse = true;
        }

        receptor1.setIdHeladera(idHeladera);
        receptor1.setUrl(url);
        receptor1.setController(controller);

        if (hayQueConectarse){
            receptor1.conectarseATopics();
        }

        return receptor1;
    }


}
