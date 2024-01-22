package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComercialService {

    private ComercialDAO comercialDAO;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired
    public ComercialService(ComercialDAO comercialDAO) {
        this.comercialDAO = comercialDAO;
    }

    @Autowired
    private PedidoDAO pedidoDAO;



    public List<Comercial> listAll() {

        return comercialDAO.getAll();

    }

    public Comercial one(Integer id) {
        Optional<Comercial> optionalComercial = comercialDAO.find(id);
        if (optionalComercial.isPresent())
            return optionalComercial.get();
        else
            return null;
    }

    public void newComercial(Comercial comercial) {
        comercialDAO.create(comercial);
    }

    public void replaceComercial(Comercial comercial) {

        comercialDAO.update(comercial);

    }

    public void deleteComercial(int id) {

        comercialDAO.delete(id);

    }

    public List<Pedido> listAllPedidos(Integer id) {
        return pedidoDAO.getAllIDComercial(id);
    }

    /* TODO Vamos a plantear la 3.6... Necesitamos hacer lo siguiente:
    * 1. Añade las estadísticas de total y media de pedidos del comercial en su detalle.
    * Utiliza un DTO para transferir a la vista las estadísticas de inteligencia de pedidos
    * (Vamos a hacer lógica real -> Definición de DTO y uso, como lo vamos a crear y el stream que haga esta lógica funcionará así:
    * primero sacamos el total de dinero por pedido pongamos 2000€ * comisión, luego la media seria dividirlo por el nº de pedidos.
    * El objetivo, ver cuanto se ha de pagar a un Comercial por los Pedidos que ha logrado y ver cual sería su "media".
    * )
    *
    * 2. Posteriormente, resalta con verde las líneas de pedido máximo y con amarillo las líneas de pedido mínimo. Pon leyendas indicando qué significa cada color.
    * (Creamos cajitas con los colores y la leyenda )
    *
    * 3. Muestra un listado adicional con los clientes ordenados por cuantía de pedido de mayor a menor. El listado iría a continuación del listado de pedidos
    * (Stream -> sacamos todos los pedidos a partir de un cliente (1-*) (Se podria recorrer al reves? todos los pedidos de 1 cliente vs el cliente de varios pedidos¿?)
    * Estamos ante una estructura de linea continua puesto que un Pedido no puede existir sin un Comercial y un Cliente)
    * */

}
