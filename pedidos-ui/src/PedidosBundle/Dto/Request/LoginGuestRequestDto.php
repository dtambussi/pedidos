<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/27/17
 * Time: 11:50 AM
 */

namespace PedidosBundle\Dto\Request;


class LoginGuestRequestDto
{
    /**
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