<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 14/11/2017
 * Time: 18:30
 */

namespace PedidosBundle\Dto\Request;


class CambiarEstadoItemDePedidoRequest
{
    /**
     * @var string
     */
    private $idItemDePedido;

    /**
     * @var string
     */
    private $estadoItemDePedido;

    /**
     * @return string
     */
    public function getIdItemDePedido()
    {
        return $this->idItemDePedido;
    }

    /**
     * @param string $idItemDePedido
     */
    public function setIdItemDePedido($idItemDePedido)
    {
        $this->idItemDePedido = $idItemDePedido;
    }

    /**
     * @return string
     */
    public function getEstadoItemDePedido()
    {
        return $this->estadoItemDePedido;
    }

    /**
     * @param string $estadoItemDePedido
     */
    public function setEstadoItemDePedido($estadoItemDePedido)
    {
        $this->estadoItemDePedido = $estadoItemDePedido;
    }
}
