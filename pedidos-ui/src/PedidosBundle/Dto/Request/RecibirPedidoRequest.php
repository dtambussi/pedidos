<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 15/11/2017
 * Time: 10:38
 */

namespace PedidosBundle\Dto\Request;


class RecibirPedidoRequest
{
    /**
     * @var int
     */
    private $idPedido;

    /**
     * @var string
     */
    private $comentario;

    /**
     * @var string
     */
    private $destino;

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
}
