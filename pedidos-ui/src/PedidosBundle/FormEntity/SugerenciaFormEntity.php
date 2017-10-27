<?php
/**
 * Created by PhpStorm.
 * User: martin
 * Date: 27/10/17
 * Time: 14:44
 */

namespace PedidosBundle\FormEntity;
use Symfony\Component\Validator\Constraints as Assert;


class SugerenciaFormEntity
{
    /**
     * @Assert\NotBlank(message="El nombre es obligatorio")
     * @var string
     */
    private $nombre;

    /**
     * @Assert\NotBlank(message="La descripción es obligatoria")
     * @var string
     */
    private $descripcion;

    /**
     * @var string $precio
     * @Assert\NotBlank(message="El precio es olbigatorio")
     * @Assert\Type(
     *     type="double",
     *     message="The value {{ value }} is not a valid {{ type }}."
     * )
     */
    private $precio;

    /**
     * @Assert\NotBlank(message="Completar campo")
     * @var string
     * @Assert\Range(
     *      min = 1,
     *      max = 99,
     *      minMessage = "Seleccionar al menos {{ limit }} item",
     *      maxMessage = "No se pueden seleccionar más de {{ limit }} items"
     * )
     */
    private $cantidad;


    /**
     * @var string $fechaInicio
     * @Assert\DateTime(format="d/m/Y H:i")
     */
    private $fechaInicio;

    /**
     * @var string $fechaFin
     * @Assert\DateTime(format="d/m/Y H:i")
     */
    private $fechaFin;



    /**
     * SugenrenciaFormEntuty constructor.
     */
    public function __construct()
    {
        // Default value
        $this->cantidad = 1;
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
     * @return mixed
     */
    public function getPrecio()
    {
        return $this->precio;
    }

    /**
     * @param mixed $precio
     */
    public function setPrecio($precio)
    {
        $this->precio = $precio;
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


}