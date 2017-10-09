<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/9/17
 * Time: 4:14 PM
 */

namespace PedidosBundle\Dto;
use JMS\Serializer\Annotation\Type;


class PedidoDto
{
    /**
     * @var int
     * @Type("int")
     */
    public $id; //int

    /**
     * @var string
     * @Type("string")
     */
    public $status; //String

    /**
     * @var string
     * @Type("string")
     */
    public $estado; //String

    /**
     * @var string
     * @Type("string")
     */
    public $comentario; //String

    /**
     * @var boolean
     * @Type("boolean")
     */
    public $abonado; //boolean

    // public $mesa; //object TODO AGREGAR!
    // public $items; //array(Item) TODO AGREGAR!

    /**
     * @var string
     * @Type("string")
     */
    public $fechaCreacion; //Date

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
    } //Date
}
