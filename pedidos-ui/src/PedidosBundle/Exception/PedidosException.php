<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 12:41 PM
 */

namespace PedidosBundle\Exception;

use JMS\Serializer\Annotation\Type;

class PedidosException extends \Exception
{

    /**
     * @var string
     * @Type("string")
     */
    protected $message;

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

