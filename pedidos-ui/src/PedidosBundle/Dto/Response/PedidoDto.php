<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/9/17
 * Time: 4:14 PM
 */

namespace PedidosBundle\Dto\Response;
use JMS\Serializer\Annotation\Type;
use JMS\Serializer\Annotation\Exclude;
use PedidosBundle\Dto\ItemsByCategoriaDto;
use PedidosBundle\Dto\UsuarioDto;

// https://jsonutils.com/
class PedidoDto
{
    /**
     * @var int
     * @Type("int")
     */
    private $id; //int

    /**
     * @var string
     * @Type("string")
     */
    private $status; //String

    /**
     * @var string
     * @Type("string")
     */
    private $estado; //String

    /**
     * @var string
     * @Type("string")
     */
    private $comentario; //String

    /**
     * @var boolean
     * @Type("boolean")
     */
    private $abonado; //boolean


    /**
     * @var string
     * @Type("string")
     */
    private $destino;

    /**
     * @var array
     * @Type("array<PedidosBundle\Dto\Response\PedidoItem>")
     */
    private $items; //array(Item)

    /**
     * @var ItemsByCategoriaDto
     * @Exclude
     */
    private $itemsByCategoriaDto;

    /**
     * @var string
     * @Type("string")
     */
    private $fechaCreacion; //Date

    /**
     * @var string
     * @Type("string")
     */
    private $fechaUltimaModificacion;

    /**
     * @var UsuarioDto
     * @Type("PedidosBundle\Dto\UsuarioDto")
     */
    private $cliente;

    /**
     * @var UsuarioDto
     * @Type("PedidosBundle\Dto\UsuarioDto")
     */
    private $personalAsignado;

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
    }

    /**
     * @return array
     */
    public function getItems()
    {
        return $this->items;
    }

    /**
     * @param array $items
     */
    public function setItems($items)
    {
        $this->items = $items;
    }

    /**
     * @return ItemsByCategoriaDto
     */
    public function getItemsByCategoriaDto()
    {
        return $this->itemsByCategoriaDto;
    }

    /**
     * @param ItemsByCategoriaDto $itemsByCategoriaDto
     */
    public function setItemsByCategoriaDto($itemsByCategoriaDto)
    {
        $this->itemsByCategoriaDto = $itemsByCategoriaDto;
    }

    /**
     * @return UsuarioDto
     */
    public function getCliente()
    {
        return $this->cliente;
    }

    /**
     * @param UsuarioDto $cliente
     */
    public function setCliente($cliente)
    {
        $this->cliente = $cliente;
    }

    /**
     * @return UsuarioDto
     */
    public function getPersonalAsignado()
    {
        return $this->personalAsignado;
    }

    /**
     * @param UsuarioDto $personalAsignado
     */
    public function setPersonalAsignado($personalAsignado)
    {
        $this->personalAsignado = $personalAsignado;
    }

    /**
     * @return string
     */
    public function getDestino()
    {
        return $this->destino;
    }

    /**
     * @param string $destino
     */
    public function setDestino($destino)
    {
        $this->destino = $destino;
    }
}
