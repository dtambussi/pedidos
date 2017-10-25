<?php
/**
 * Created by PhpStorm.
 * User: martin
 * Date: 10/25/17
 * Time: 12:19 PM
 */

namespace PedidosBundle\Dto;

use JMS\Serializer\Annotation\Type;

class RoleDto
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
    public $nombre;

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