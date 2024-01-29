package org.iesvdm.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dao.PedidoDAOImpl;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoFormDTO;
import org.iesvdm.mapper.ComercialMapper;
import org.iesvdm.mapper.PedidoMapper;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

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
    private PedidoDAOImpl pedidoDAO;

    @Autowired
    private ComercialMapper comercialMapper;


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
        return pedidoDAO.getAllPComercialesByID(id);
    }

    public PedidoFormDTO pedidoCompletoComercial(int id) {

        PedidoFormDTO pedidoFormDTO = new PedidoFormDTO();

        List<Pedido> listaPedido = listAllPedidos(id);

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (Pedido p : listaPedido) {
            totalPedido = totalPedido.add(new BigDecimal(p.getTotal()));
        }


        BigDecimal media = totalPedido.divide(new BigDecimal(listaPedido.size()), 0);


        pedidoFormDTO.setMedia(media);
        pedidoFormDTO.setTotalPedido(totalPedido);
        return pedidoFormDTO;
    }

    public List<ComercialDTO> listAllByIDCliente(Integer id) {
        //Segun la DB Comercial -> Pedido cl_ID co_ID <- Cliente
        //Aqui tenemos la ID del cliente así que sacamos una lista de Pedido, filtramos por ID cliente y sacamos sus comerciales

      List<Pedido> listaPedidosCliente = pedidoDAO.getAllPClientesByID(id);

        // Creamos un conjunto para almacenar los comerciales sin duplicados porque solo nos interesa 1 unico comercial
                Set<ComercialDTO> comercialesSet = new HashSet<>();

                // Iteramos sobre la lista de pedidos del cliente
                for (Pedido pedido : listaPedidosCliente) {
                    // Obtenemos las IDs de cliente y comercial desde el pedido
                    int idComercial = pedido.getId_comercial();

                    //Con la funcion find Sacamos al comercial del pedido en concreto
                    Optional<Comercial> comercialOpt = comercialDAO.find(idComercial);

                    // Agregamos el comercial al conjunto
                    if (comercialOpt.isPresent()) {

                        //Sacamos el comercial
                        Comercial comercial = comercialOpt.get();
                        //Creamos un DTO con la info del Comercial inicial
                        ComercialDTO comercialD = comercialMapper.comercialAComercialDTO(comercial);
                        //Al DTO le ponemos la cantidad de Pedidos que tiene ese comercial
                        comercialD.setCantidadPedidos(pedidoDAO.getAllPComercialesByID(idComercial).size());
                        //Metemos el comercialDTO al set :D
                        comercialesSet.add(comercialD);

                    }
                }

                // Convertimos el conjunto a lista y lo mandamos al Controller para usarlo en detalle-cliente
                List<ComercialDTO> lista = new ArrayList<>(comercialesSet);


        return lista;
    }

}