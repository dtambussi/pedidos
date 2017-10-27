<?php
/**
 * Created by PhpStorm.
 * User: martin
 * Date: 10/25/17
 * Time: 12:05 PM
 */


namespace PedidosBundle\Dto;

use JMS\Serializer\Annotation\Type;


class SessionDeUsuarioDto
{
    /**
     * @var string
     * @Type("string")
     */
    public $id; //String

    /**
     * @var UsuarioDto
     * @Type("PedidosBundle\Dto\UsuarioDto")
     */
    public $usuario; //Usuario


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
     * @return UsuarioDto
     */
    public function getUsuario()
    {
        return $this->usuario;
    }

    /**
     * @param UsuarioDto $usuario
     */
    public function setUsuario($usuario)
    {
        $this->usuario = $usuario;
    }



}