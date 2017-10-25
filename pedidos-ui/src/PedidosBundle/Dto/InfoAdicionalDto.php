<?php
/**
 * Created by PhpStorm.
 * User: martin
 * Date: 10/25/17
 * Time: 12:14 PM
 */

namespace PedidosBundle\Dto;

use JMS\Serializer\Annotation\Type;

class InfoAdicionalDto
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
    public $nombre; //String
    /**
     * @var string
     * @Type("string")
     */
    public $apellido; //String
    /**
     * @var string
     * @Type("string")
     */
    public $email; //String
    /**
     * @var string
     * @Type("string")
     */
    public $password; //String

    /**
     * @var \DateTime
     * @Type("DateTime<'Y-m-d', 'UTC'>")
     */
    public $fechaNacimiento;

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

    /**
     * @return string
     */
    public function getApellido()
    {
        return $this->apellido;
    }

    /**
     * @param string $apellido
     */
    public function setApellido($apellido)
    {
        $this->apellido = $apellido;
    }

    /**
     * @return string
     */
    public function getEmail()
    {
        return $this->email;
    }

    /**
     * @param string $email
     */
    public function setEmail($email)
    {
        $this->email = $email;
    }

    /**
     * @return string
     */
    public function getPassword()
    {
        return $this->password;
    }

    /**
     * @param string $password
     */
    public function setPassword($password)
    {
        $this->password = $password;
    }

    /**
     * @return \DateTime
     */
    public function getFechaNacimiento()
    {
        return $this->fechaNacimiento;
    }

    /**
     * @param \DateTime $fechaNacimiento
     */
    public function setFechaNacimiento($fechaNacimiento)
    {
        $this->fechaNacimiento = $fechaNacimiento;
    }




}