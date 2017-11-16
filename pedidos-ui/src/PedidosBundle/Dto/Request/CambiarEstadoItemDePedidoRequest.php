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
     * AUN NO AGREGADO EN PROYECTO API!!
     * @var string
     */
    private $comentario;

    /**
     * @var boolean
     */
    private $abonado;

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
}
