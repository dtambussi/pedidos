<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 14/11/2017
 * Time: 18:26
 */

namespace PedidosBundle\Dto\Request;

class CambiarEstadoDePedidoRequest
{

    /**
     * @var int
     */
    private $idPedido;

    /**
     * @var string
     */
    private $estadoPedido;

    /**
     * @var string
     */
    private $destino;

    /**
     * @var string
     */
    private $comentario;

    /**
     * @var bool
     */
    private $abonado;

    /**
     * @var array<CambiarEstadoItemDePedidoRequest>
     */
    private $cambiosDeEstadoSobreItems;

    /**
     * CambiarEstadoDePedidoRequest constructor.
     */
    public function __construct()
    {
        $this->cambiosDeEstadoSobreItems = array();
    }

    /**
     * @return int
     */
    public function getIdPedido()
    {
        return $this->idPedido;
    }

    /**
     * @param int $idPedido
     */
    public function setIdPedido($idPedido)
    {
        $this->idPedido = $idPedido;
    }

    /**
     * @return string
     */
    public function getEstadoPedido()
    {
        return $this->estadoPedido;
    }

    /**
     * @param string $estadoPedido
     */
    public function setEstadoPedido($estadoPedido)
    {
        $this->estadoPedido = $estadoPedido;
    }

    /**
     * @return string
     */
    public function getDestino()
    {
        return $this->destino;
    }

    /**
     * @param string $destino
     */
    public function setDestino($destino)
    {
        $this->destino = $destino;
    }

    /**
     * @return string
     */
    public function getComentario()
    {
        return $this->comentario;
    }

    /**
     * @param string $comentario
     */
    public function setComentario($comentario)
    {
        $this->comentario = $comentario;
    }

    /**
     * @return boolean
     */
    public function isAbonado()
    {
        return $this->abonado;
    }

    /**
     * @param boolean $abonado
     */
    public function setAbonado($abonado)
    {
        $this->abonado = $abonado;
    }

    public function addCambiarEstadoItem(CambiarEstadoItemDePedidoRequest $itemRequest) {
        array_push($this->cambiosDeEstadoSobreItems, $itemRequest);
    }

    /**
     * @return array
     */
    public function getCambiosDeEstadoSobreItems()
    {
        return $this->cambiosDeEstadoSobreItems;
    }

    /**
     * @param array $cambiosDeEstadoSobreItems
     */
    public function setCambiosDeEstadoSobreItems($cambiosDeEstadoSobreItems)
    {
        $this->cambiosDeEstadoSobreItems = $cambiosDeEstadoSobreItems;
    }
}
