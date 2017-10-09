<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/9/17
 * Time: 12:11 PM
 */

namespace PedidosBundle\Dto;


class ItemDePedidoRequestDto
{
    /**
     * @var int
     */
    private $idItemDeMenu;

    /**
     * @var int
     */
    private $cantidad;

    /**
     * @var string
     */
    private $comentario;

    /**
     * @return int
     */
    public function getIdItemDeMenu()
    {
        return $this->idItemDeMenu;
    }

    /**
     * @param int $idItemDeMenu
     */
    public function setIdItemDeMenu($idItemDeMenu)
    {
        $this->idItemDeMenu = $idItemDeMenu;
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
}