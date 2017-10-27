<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/27/17
 * Time: 12:47 PM
 */

namespace PedidosBundle\FormEntity;
use Symfony\Component\Validator\Constraints as Assert;


class LoginFormEntity
{
    /**
     * @Assert\NotBlank(message="Por favor ingrese su email")
     * @var string
     */
    private $email;

    /**
     * @Assert\NotBlank(message="Por favor, ingrese su contraseña")
     * @var string
     */
    private $password;

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
}