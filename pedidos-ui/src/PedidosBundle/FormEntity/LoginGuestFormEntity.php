<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/27/17
 * Time: 12:47 PM
 */

namespace PedidosBundle\FormEntity;
use Symfony\Component\Validator\Constraints as Assert;


class LoginGuestFormEntity
{
    /**
     * @Assert\NotBlank(message="Por favor ingrese un nickname")
     * @var string
     */
    private $nickname;

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
}