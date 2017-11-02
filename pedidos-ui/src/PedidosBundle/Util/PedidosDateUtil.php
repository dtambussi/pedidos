<?php


namespace PedidosBundle\Util;

/**
 * Created by PhpStorm.
 * User: martin
 * Date: 10/31/17
 * Time: 12:32 PM
 */
class PedidosDateUtil
{
    public static function toPedidosApiFormat($date,$inputFormat = 'd/m/Y'){
        return \DateTime::createFromFormat($inputFormat, $date)->format("Y-m-d\TH:i:s-03:00");
    }

}