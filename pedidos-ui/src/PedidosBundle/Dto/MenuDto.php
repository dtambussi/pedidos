<?php
namespace PedidosBundle\Dto;
use JMS\Serializer\Annotation\Type;

/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 12:32 PM
 */
class MenuDto
{
    /**
     * @var string
     * @Type("string")
     */
    private $id;

    /**
     * @var string
     * @Type("string")
     */
    private $status;

    /**
     * @var string
     * @Type("string")
     */
    private $nombre;

    /**
     * @return string
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param string $id
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
}