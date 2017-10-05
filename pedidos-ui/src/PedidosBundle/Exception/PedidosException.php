<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 12:41 PM
 */

namespace PedidosBundle\Exception;


class PedidosException extends \Exception
{
    /**
     * PedidosException constructor.
     * @param string $message
     */
    public function __construct($message)
    {
        parent::__construct($message);
        $this->message = $message;
    }
}

