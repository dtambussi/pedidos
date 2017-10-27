<?php
/**
 * Created by PhpStorm.
 * User: martin
 * Date: 10/25/17
 * Time: 12:10 PM
 */

namespace PedidosBundle\Dto;

use JMS\Serializer\Annotation\Type;

class UsuarioDto
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
    public $nickname; //String

    /**
     * @Type("array<PedidosBundle\Dto\RoleDto>")
     */
    public $roles; //array(Role)


    /**
     * @var InfoAdicionalDto
     * @Type("PedidosBundle\Dto\InfoAdicionalDto")
     */
    public $infoAdicional; //InfoAdicional

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
    public function getNickname()
    {
        return $this->nickname;
    }

    /**
     * @param string $nickname
     */
    public function setNickname($nickname)
    {
        $this->nickname = $nickname;
    }

    /**
     * @return mixed
     */
    public function getRoles()
    {
        return $this->roles;
    }

    /**
     * @param mixed $roles
     */
    public function setRoles($roles)
    {
        $this->roles = $roles;
    }

    /**
     * @return InfoAdicionalDto
     */
    public function getInfoAdicional()
    {
        return $this->infoAdicional;
    }

    /**
     * @param InfoAdicionalDto $infoAdicional
     */
    public function setInfoAdicional($infoAdicional)
    {
        $this->infoAdicional = $infoAdicional;
    }




}