<?php
namespace PedidosBundle\Dto;
use JMS\Serializer\Annotation\Type;

/**
 * Created by PhpStorm.
 * User: martin
 * Date: 10/30/17
 * Time: 15:32 PM
 */
class SugerenciaDto
{
    /**
     * @var int
     * @Type("int")
     */
    public $id;

    /**
     * @var string
     * @Type("string")
     */
    public $status;

    /**
     * @var string
     * @Type("string")
     */
    public $nombre;

    /**
     * @var string
     * @Type("string")
     */
    public $descripcion;

    /**
     * @var double
     * @Type("double")
     */
    public $precio;

    /**
     * @var MenuItemDto
     * @Type("PedidosBundle\Dto\MenuItemDto")
     */
    public $itemDeMenu;

    /**
     * @var int
     * @Type("int")
     */
    public $cantidadDisponible;

    /**
     * @var string
     * @Type("string")
     */
    public $fechaInicio;

    /**
     * @var string
     * @Type("string")
     */
    public $fechaFin;

    /**
     * @var string
     * @Type("string")
     */
    public $fechaCreacion;

    /**
     * @var string
     * @Type("string")
     */
    public $fechaUltimaModificacion;

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
    public function getDescripcion()
    {
        return $this->descripcion;
    }

    /**
     * @param string $descripcion
     */
    public function setDescripcion($descripcion)
    {
        $this->descripcion = $descripcion;
    }

    /**
     * @return float
     */
    public function getPrecio()
    {
        return $this->precio;
    }

    /**
     * @param float $precio
     */
    public function setPrecio($precio)
    {
        $this->precio = $precio;
    }

    /**
     * @return int
     */
    public function getCantidadDisponible()
    {
        return $this->cantidadDisponible;
    }

    /**
     * @param int $cantidadDisponible
     */
    public function setCantidadDisponible($cantidadDisponible)
    {
        $this->cantidadDisponible = $cantidadDisponible;
    }

    /**
     * @return string
     */
    public function getFechaInicio()
    {
        return $this->fechaInicio;
    }

    /**
     * @param string $fechaInicio
     */
    public function setFechaInicio($fechaInicio)
    {
        $this->fechaInicio = $fechaInicio;
    }

    /**
     * @return string
     */
    public function getFechaFin()
    {
        return $this->fechaFin;
    }

    /**
     * @param string $fechaFin
     */
    public function setFechaFin($fechaFin)
    {
        $this->fechaFin = $fechaFin;
    }

    /**
     * @return string
     */
    public function getFechaCreacion()
    {
        return $this->fechaCreacion;
    }

    /**
     * @param string $fechaCreacion
     */
    public function setFechaCreacion($fechaCreacion)
    {
        $this->fechaCreacion = $fechaCreacion;
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


}
