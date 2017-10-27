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
    const SESSION_NAME = "usuarioDto";

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

    public function puedeCrearSugerencia() {
        return $this->roleExists(UsuarioRoleType::CREAR_SUGERENCIA);
    }

    public function puedeCrearPedido() {
        return $this->roleExists(UsuarioRoleType::CREAR_PEDIDO);
    }

    public function puedeListarPedidos() {
        return $this->roleExists(UsuarioRoleType::LISTAR_PEDIDOS);
    }

    public function puedeGenerarReporte() {
        return $this->roleExists(UsuarioRoleType::GENERAR_REPORTE_PEDIDOS);
    }

    private function roleExists($roleNombre) {
        if (empty($this->roles)) {
            return false;
        }

        $roleNombres = array_map(function(RoleDto $role) {
            return $role->getNombre();
        }, $this->roles);

        return in_array($roleNombre, $roleNombres);
    }
}