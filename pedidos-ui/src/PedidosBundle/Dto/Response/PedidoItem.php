<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/10/17
 * Time: 11:50 AM
 */

namespace PedidosBundle\Dto\Response;
use JMS\Serializer\Annotation\Type;


use PedidosBundle\Dto\MenuItemDto;

class PedidoItem
{

    /**
     * @var boolean
     * @Type("boolean")
     */
    public $abonado; //boolean

    /**
     * @var int
     * @Type("int")
     */
    public $cantidad; //int

    /**
     * @var string
     * @Type("string")
     */
    public $comentario; //String

    /**
     * @var string
     * @Type("string")
     */
    public $estado; //String

    /**
     * @var string
     * @Type("string")
     */
    public $fechaUltimaModificacion; //Date

    /**
     * @var int
     * @Type("int")
     */
    public $id; //int

    /**
     * @var MenuItemDto
     * @Type("PedidosBundle\Dto\MenuItemDto")
     */
    public $itemDeMenu; //ItemDeMenu

    /**
     * @var object
     * @Type("object")
     */
    public $pedido; //object

    /**
     * @var string
     * @Type("string")
     */
    public $status;

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

    /**
     * @return int
     */
    public function getCantidad()
    {
        return $this->cantidad;
    }

    /**
     * @param int $cantidad
     */
    public function setCantidad($cantidad)
    {
        $this->cantidad = $cantidad;
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
    public function getEstado()
    {
        return $this->estado;
    }

    /**
     * @param string $estado
     */
    public function setEstado($estado)
    {
        $this->estado = $estado;
    }

    /**
     * @return string
     */
    public function getFechaUltimaModificacion()
    {
        return $this->fechaUltimaModificacion;
    }

    /**
     * @param string $fechaUltimaModificacion
     */
    public function setFechaUltimaModificacion($fechaUltimaModificacion)
    {
        $this->fechaUltimaModificacion = $fechaUltimaModificacion;
    }

    /**
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param int $id
     */
    public function setId($id)
    {
        $this->id = $id;
    }

    /**
     * @return MenuItemDto
     */
    public function getItemDeMenu()
    {
        return $this->itemDeMenu;
    }

    /**
     * @param MenuItemDto $itemDeMenu
     */
    public function setItemDeMenu($itemDeMenu)
    {
        $this->itemDeMenu = $itemDeMenu;
    }

    /**
     * @return object
     */
    public function getPedido()
    {
        return $this->pedido;
    }

    /**
     * @param object $pedido
     */
    public function setPedido($pedido)
    {
        $this->pedido = $pedido;
    }

    /**
     * @return string
     */
    public function getStatus()
    {
        return $this->status;
    }

    /**
     * @param string $status
     */
    public function setStatus($status)
    {
        $this->status = $status;
    } //String
}