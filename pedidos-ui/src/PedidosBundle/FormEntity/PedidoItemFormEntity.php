<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/9/17
 * Time: 12:19 PM
 */

namespace PedidosBundle\FormEntity;
use PedidosBundle\Dto\Request\ItemDePedidoRequestDto;
use Symfony\Component\Validator\Constraints as Assert;


class PedidoItemFormEntity
{
    /**
     * @Assert\NotBlank(message="Completar campo")
     * @var string
     * @Assert\Range(
     *      min = 1,
     *      max = 9,
     *      minMessage = "Seleccionar al menos {{ limit }} item",
     *      maxMessage = "No se pueden seleccionar más de {{ limit }} items"
     * )
     */
    private $cantidad;

    /**
     * @var string
     */
    private $comentario;

    /**
     * @var string
     */
    private $id;

    /**
     * PedidoItemFormEntity constructor.
     */
    public function __construct()
    {
        // Default value
        $this->cantidad = 1;
    }


    /**
     * @return mixed
     */
    public function getCantidad()
    {
        return $this->cantidad;
    }

    /**
     * @param mixed $cantidad
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

    public function toItemDePedidoRequestDto() {
        $result = new ItemDePedidoRequestDto();

        $result->setCantidad($this->cantidad);
        $result->setComentario($this->comentario);
        $result->setIdItemDeMenu($this->id);

        return $result;
    }
}