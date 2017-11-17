<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 5:39 PM
 */

namespace PedidosBundle\Dto;


class EstadoPedidoType
{
    const GENERADO = "Generado";
    const PENDIENTE = "Pendiente";
    const CONFECCIONADO = "Confeccionado";
    const ENTREGADO = "Entregado";
    const CANCELADO = "Cancelado";
    const EN_CURSO = "En_Curso";

}