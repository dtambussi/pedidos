<?php
namespace PedidosBundle\Dto;
use JMS\Serializer\Annotation\Type;

/**
 * Created by PhpStorm.
 * User: martin
 * Date: 10/30/17
 * Time: 15:32 PM
 */
class ItemReportePedidosDto
{
    /**
     * @var string
     * @Type("string")
     */
    public $categoria;

    /**
     * @var string
     * @Type("string")
     */
    public $nombre;

    /**
     * @var string
     * @Type("string")
     */
    public $tag;

    /**
     * @var int
     * @Type("int")
     */
    public $cantidad;

    /**
     * @return string
     */
    public function getCategoria()
    {
        return $this->categoria;
    }

    /**
     * @param string $categoria
     */
    public function setCategoria($categoria)
    {
        $this->categoria = $categoria;
    }

    /**
     * @return string
     */
    public function getNombre()
    {
        return $this->nombre;
    }

    /**
     * @param string $nombre
     */
    public function setNombre($nombre)
    {
        $this->nombre = $nombre;
    }

    /**
     * @return string
     */
    public function getTag()
    {
        return $this->tag;
    }

    /**
     * @param string $tag
     */
    public function setTag($tag)
    {
        $this->tag = $tag;
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



}
